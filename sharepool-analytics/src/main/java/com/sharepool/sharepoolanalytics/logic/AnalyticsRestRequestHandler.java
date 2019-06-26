package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsEntry;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.rest.ServerClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnalyticsRestRequestHandler {

    private final AnalyticsMessageRepository analyticsMessageRepository;
    private final ServerClient serverClient;

    public AnalyticsRestRequestHandler(AnalyticsMessageRepository analyticsMessageRepository, ServerClient serverClient) {
        this.analyticsMessageRepository = analyticsMessageRepository;
        this.serverClient = serverClient;
    }

    public Map<LocalDate, AnalyticsEntry> getAnalyticsForTimeSpan(String userToken, LocalDate from, LocalDate to) {
        if (from == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must at least set a start timestamp.");
        }

        if (to == null) {
            to = LocalDate.now();
        }

        if (userToken != null) {
            Long userId = serverClient.getUserIdByToken(userToken);

            Long fromTimeStamp = from.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
            Long toTimeStamp = to.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
            List<AnalyticsMessage> analyticsMessages = analyticsMessageRepository
                    .getAllByPayerIdAndCreationTimestampIsBetween(
                            userId,
                            fromTimeStamp,
                            toTimeStamp
                    );

            Map<LocalDate, AnalyticsEntry> analyticsEntries = new HashMap<>();
            analyticsMessages.stream()
                    .collect(Collectors.groupingBy(message -> {
                        Instant instant = Instant.ofEpochSecond(message.getCreationTimestamp());
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        return localDateTime.toLocalDate();
                    }))
                    .forEach((date, message) -> {
                        AnalyticsEntry entry = new AnalyticsEntry();

                        entry.setCreationDate(date);
                        entry.setKmSum(message.stream().mapToDouble(AnalyticsMessage::getKilometers).sum());

                        entry.setLitersGasSaved(message.stream()
                                .mapToDouble(AnalyticsMessage::getSumGasConsumption)
                                .sum());

                        analyticsEntries.put(date, entry);
                    });

            return analyticsEntries;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing user-token!");
        }

    }
}
