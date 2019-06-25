package com.sharepool.sharepoolanalytics.rabbitmq;

import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.logic.AnalyticsMessageRabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static final String queueName = "sharepool-analytics";

    private final AnalyticsMessageRabbitHandler requestHandler;

    public Receiver(AnalyticsMessageRabbitHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RabbitListener(queues = queueName)
    public void receiveMessage(AnalyticsMessage analyticsMessage) {
        System.out.println("Received <" + analyticsMessage.toString() + ">");

        requestHandler.resolveAnalyticsMessage(analyticsMessage);
    }

}
