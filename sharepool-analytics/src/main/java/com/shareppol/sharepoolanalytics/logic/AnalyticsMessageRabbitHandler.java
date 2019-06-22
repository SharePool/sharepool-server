package com.shareppol.sharepoolanalytics.logic;

import com.shareppol.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsMessageRabbitHandler {

    private final AnalyticsMessageRepository analyticsMessageRepository;

    public AnalyticsMessageRabbitHandler(AnalyticsMessageRepository analyticsMessageRepository) {
        this.analyticsMessageRepository = analyticsMessageRepository;
    }

    public void resolveAnalyticsMessage(AnalyticsMessage analyticsMessage) {
        analyticsMessageRepository.save(analyticsMessage);
    }
}
