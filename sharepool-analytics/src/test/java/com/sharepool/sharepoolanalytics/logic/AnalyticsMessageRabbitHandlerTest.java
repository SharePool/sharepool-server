package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.rabbitmq.AnalyticsMessageReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalyticsMessageRabbitHandlerTest {

    @MockBean
    private AnalyticsMessageReceiver analyticsMessageReceiver;

    @Autowired
    private AnalyticsMessageRepository analyticsMessageRepository;

    @Autowired
    private AnalyticsMessageRabbitHandler rabbitHandler;

    @Test
    public void resolveAnalyticsMessage() {
        rabbitHandler.resolveAnalyticsMessage(new AnalyticsMessage(2L, 5L, 3L, 14.2, 5.1, System.currentTimeMillis()));
        rabbitHandler.resolveAnalyticsMessage(new AnalyticsMessage(3L, 6L, 3L, 31.9, 9.8, System.currentTimeMillis()));

        assertEquals(2, analyticsMessageRepository.getAllByPayerId(3L).size());
    }
}