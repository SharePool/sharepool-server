package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
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
