package com.shareppol.sharepoolanalytics.rabbitmq;

import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import com.shareppol.sharepoolanalytics.logic.AnalyticsMessageRabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final AnalyticsMessageRabbitHandler requestHandler;

    public Receiver(AnalyticsMessageRabbitHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RabbitListener(queues = {"sharepool-analytics"})
    public void receiveMessage(AnalyticsMessage analyticsMessage) {
        System.out.println("Received <" + analyticsMessage.toString() + ">");

        requestHandler.resolveAnalyticsMessage(analyticsMessage);
    }

}
