package com.sharepool.server.rabbitmq;

import com.sharepool.server.domain.Expense;
import com.sharepool.server.logic.expense.ExpenseMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsCommunicator {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ExpenseMapper expenseMapper;

    public AnalyticsCommunicator(RabbitTemplate rabbitTemplate, Queue queue, ExpenseMapper expenseMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.expenseMapper = expenseMapper;
    }

    public void sendAnalyticsData(Expense expense) {
        AnalyticsMessage analyticsMessage = expenseMapper.expenseToAnalyticsMessage(expense);

        Double sumGasConsumption = (expense.getReceiver().getGasConsumption() / 100) * analyticsMessage.getKilometers();
        analyticsMessage.setSumGasConsumption(sumGasConsumption);

        rabbitTemplate.convertAndSend(queue.getName(), analyticsMessage);
    }

}
