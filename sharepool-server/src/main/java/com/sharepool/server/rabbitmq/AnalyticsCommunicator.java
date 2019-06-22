package com.sharepool.server.rabbitmq;

import com.sharepool.server.domain.Expense;
import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsCommunicator {

    private final RabbitTemplate rabbitTemplate;

    public AnalyticsCommunicator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAnalyticsData(Expense expense) {
        AnalyticsMessage analyticsMessage = new AnalyticsMessage();

        analyticsMessage.setExpenseId(expense.getId());
        analyticsMessage.setKilometers(expense.getTour().getKilometers());
        analyticsMessage.setDate(expense.getCreationDate());
        analyticsMessage.setTourId(expense.getTour().getId());

        Double sumGasConsumption = (expense.getReceiver().getGasConsumption() / 100) * analyticsMessage.getKilometers();
        analyticsMessage.setSumGasConsumption(sumGasConsumption);

        rabbitTemplate.convertAndSend("sharepool-exchange", "sharepool-analytics", analyticsMessage);
    }

}
