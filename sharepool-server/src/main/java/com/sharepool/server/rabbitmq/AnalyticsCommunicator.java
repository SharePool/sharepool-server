package com.sharepool.server.rabbitmq;

import com.sharepool.server.domain.Expense;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsCommunicator {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public AnalyticsCommunicator(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendAnalyticsData(Expense expense) {
        AnalyticsMessage analyticsMessage = new AnalyticsMessage();

        analyticsMessage.setExpenseId(expense.getId());
        analyticsMessage.setTourId(expense.getTour().getId());
        analyticsMessage.setPayerId(expense.getPayer().getId());
        analyticsMessage.setKilometers(expense.getTour().getKilometers());
        analyticsMessage.setCreationTime(expense.getCreationDate());

        Double sumGasConsumption = (expense.getReceiver().getGasConsumption() / 100) * analyticsMessage.getKilometers();
        analyticsMessage.setSumGasConsumption(sumGasConsumption);

        rabbitTemplate.convertAndSend(queue.getName(), analyticsMessage);
    }

}
