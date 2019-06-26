package com.sharepool.sharepoolanalytics.rabbitmq;

import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.logic.AnalyticsMessageRabbitHandler;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsMessageReceiver {

    private static final String queueName = "sharepool-analytics";

    private final AnalyticsMessageRabbitHandler requestHandler;

    public AnalyticsMessageReceiver(AnalyticsMessageRabbitHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @RabbitListener(queues = queueName)
    public void receiveMessage(AnalyticsMessage analyticsMessage) {
        System.out.println("Received <" + analyticsMessage.toString() + ">");

        requestHandler.resolveAnalyticsMessage(analyticsMessage);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }
}
