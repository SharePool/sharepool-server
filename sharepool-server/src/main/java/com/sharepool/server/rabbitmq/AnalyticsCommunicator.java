package com.sharepool.server.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AnalyticsCommunicator {

    private final RabbitTemplate rabbitTemplate;

    public AnalyticsCommunicator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        rabbitTemplate.convertAndSend("sharepool-exchange", "sharepool-analytics", "Hello from RabbitMQ!");
    }

}
