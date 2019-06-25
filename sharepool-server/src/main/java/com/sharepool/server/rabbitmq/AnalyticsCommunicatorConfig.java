package com.sharepool.server.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalyticsCommunicatorConfig {

    @Bean
    public Queue queue() {
        return new Queue("sharepool-analytics");
    }
}
