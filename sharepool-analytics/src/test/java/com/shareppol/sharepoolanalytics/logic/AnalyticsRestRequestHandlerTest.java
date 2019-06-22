package com.shareppol.sharepoolanalytics.logic;

import com.shareppol.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.shareppol.sharepoolanalytics.domain.AnalyticsEntry;
import com.shareppol.sharepoolanalytics.domain.AnalyticsMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalyticsRestRequestHandlerTest {

    @Autowired
    AnalyticsMessageRepository analyticsMessageRepository;

    @Autowired
    AnalyticsRestRequestHandler requestHandler;

    private void insertAnalyticsMessages() {
        analyticsMessageRepository.save(new AnalyticsMessage(1L, 2L, 41.3, 1.2, LocalDateTime.now().minusDays(3)));
        analyticsMessageRepository.save(new AnalyticsMessage(2L, 3L, 23.0, 0.75, LocalDateTime.now().minusDays(3)));

        analyticsMessageRepository.save(new AnalyticsMessage(3L, 6L, 102.5, 5.1, LocalDateTime.now().minusDays(2)));
        analyticsMessageRepository.save(new AnalyticsMessage(4L, 8L, 9.7, 0.45, LocalDateTime.now().minusDays(2)));

        analyticsMessageRepository.save(new AnalyticsMessage(5L, 10L, 65.8, 3.5, LocalDateTime.now().minusDays(1)));
    }

    @Test
    public void getAnalyticsForTimeSpan() {
        insertAnalyticsMessages();
        List<AnalyticsEntry> entries = requestHandler.getAnalyticsForTimeSpan(LocalDate.now().minusDays(4), LocalDate.now());

        assertEquals(3, entries.size());
    }
}