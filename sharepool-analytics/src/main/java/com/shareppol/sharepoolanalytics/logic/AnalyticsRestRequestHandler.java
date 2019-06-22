package com.shareppol.sharepoolanalytics.logic;

import com.shareppol.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.shareppol.sharepoolanalytics.domain.AnalyticsEntry;
import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnalyticsRestRequestHandler {

    private final AnalyticsMessageRepository analyticsMessageRepository;

    public AnalyticsRestRequestHandler(AnalyticsMessageRepository analyticsMessageRepository) {
        this.analyticsMessageRepository = analyticsMessageRepository;
    }

    public List<AnalyticsEntry> getAnalyticsForTimeSpan(LocalDate from, LocalDate to) {
        List<AnalyticsMessage> analyticsMessages = analyticsMessageRepository.getAllByDateIsBetween(from.atStartOfDay(), to.atStartOfDay().plusDays(1));
        List<AnalyticsEntry> analyticsEntries = new ArrayList<>();

        analyticsMessages.stream().collect(Collectors.groupingBy(analyticsMessage -> analyticsMessage.getDate().toLocalDate())).forEach((date, messages) -> {
            AnalyticsEntry entry = new AnalyticsEntry();

            entry.setDate(date);
            entry.setKmSum(messages.stream().mapToDouble(AnalyticsMessage::getKilometers).sum());
            entry.setLitersGasSaved(messages.stream().mapToDouble(AnalyticsMessage::getSumGasConsumption).sum());

            analyticsEntries.add(entry);
        });

        return analyticsEntries;
    }
}
