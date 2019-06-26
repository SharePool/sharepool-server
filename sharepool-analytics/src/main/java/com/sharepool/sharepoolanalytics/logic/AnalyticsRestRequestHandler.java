package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsEntry;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.rest.ServerClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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

    public Map<Long, AnalyticsEntry> getAnalyticsForTimeSpan(String userToken, Long startTimestamp, Long endTimestamp) {
        if (startTimestamp == null && endTimestamp == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must at least set a start timestamp.");
        }

        if (startTimestamp != null && endTimestamp == null) {
            endTimestamp = System.currentTimeMillis();
        }

        if (userToken != null) {
            Long userId = serverClient.getUserIdByToken(userToken);

            List<AnalyticsMessage> analyticsMessages = analyticsMessageRepository
                    .getAllByPayerIdAndCreationTimestampIsBetween(
                            userId,
                            startTimestamp,
                            endTimestamp
                    );
            Map<Long, AnalyticsEntry> analyticsEntries = new HashMap<>();

            analyticsMessages.stream()
                    .collect(Collectors.groupingBy(AnalyticsMessage::getCreationTimestamp))
                    .forEach((date, messages) -> {
                        AnalyticsEntry entry = new AnalyticsEntry();

                        entry.setCreationTimestamp(date);
                        entry.setKmSum(messages.stream().mapToDouble(AnalyticsMessage::getKilometers).sum());
                        entry.setLitersGasSaved(messages.stream()
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
