package com.shareppol.sharepoolanalytics.logic;

import com.shareppol.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.shareppol.sharepoolanalytics.domain.AnalyticsEntry;
import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import com.shareppol.sharepoolanalytics.rest.ServerClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnalyticsRestRequestHandler {

    private final AnalyticsMessageRepository analyticsMessageRepository;
    private final ServerClient serverClient;

    public AnalyticsRestRequestHandler(AnalyticsMessageRepository analyticsMessageRepository, ServerClient serverClient) {
        this.analyticsMessageRepository = analyticsMessageRepository;
        this.serverClient = serverClient;
    }

    // TODO: return map with date as key
    public List<AnalyticsEntry> getAnalyticsForTimeSpan(String userToken, LocalDate from, LocalDate to) {

        if (userToken != null) {

            Long userId = serverClient.getUserIdByToken(userToken);

            List<AnalyticsMessage> analyticsMessages = analyticsMessageRepository.getAllByPayerIdAndCreationTimeIsBetween(userId, from.atStartOfDay(), to.atStartOfDay().plusDays(1));
            List<AnalyticsEntry> analyticsEntries = new ArrayList<>();

            analyticsMessages.stream().collect(Collectors.groupingBy(analyticsMessage -> analyticsMessage.getCreationTime().toLocalDate())).forEach((date, messages) -> {
                AnalyticsEntry entry = new AnalyticsEntry();

                entry.setDate(date);
                entry.setKmSum(messages.stream().mapToDouble(AnalyticsMessage::getKilometers).sum());
                entry.setLitersGasSaved(messages.stream().mapToDouble(AnalyticsMessage::getSumGasConsumption).sum());

                analyticsEntries.add(entry);
            });

            return analyticsEntries;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing user-token!");
        }


    }
}
