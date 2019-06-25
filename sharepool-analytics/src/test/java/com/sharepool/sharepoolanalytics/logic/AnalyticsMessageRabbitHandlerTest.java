package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalyticsMessageRabbitHandlerTest {

    @Autowired
    AnalyticsMessageRepository analyticsMessageRepository;

    @Autowired
    AnalyticsMessageRabbitHandler rabbitHandler;

    @Test
    public void resolveAnalyticsMessage() {
        rabbitHandler.resolveAnalyticsMessage(new AnalyticsMessage(2L, 5L, 3L, 14.2, 5.1, LocalDateTime.now().minusDays(1)));
        rabbitHandler.resolveAnalyticsMessage(new AnalyticsMessage(3L, 6L, 3L, 31.9, 9.8, LocalDateTime.now()));

        assertEquals(2, analyticsMessageRepository.getAllByPayerId(3L).size());
    }
}