package com.sharepool.sharepoolanalytics.logic;

import com.sharepool.sharepoolanalytics.dal.AnalyticsMessageRepository;
import com.sharepool.sharepoolanalytics.domain.AnalyticsEntry;
import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import com.sharepool.sharepoolanalytics.rest.ServerClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalyticsRestRequestHandlerTest {

    @Autowired
    AnalyticsMessageRepository analyticsMessageRepository;

    AnalyticsRestRequestHandler requestHandler;

    @Mock
    ServerClient serverClient;

    @Before
    public void setUp() {
        requestHandler = new AnalyticsRestRequestHandler(analyticsMessageRepository, serverClient);
        when(serverClient.getUserIdByToken(anyString())).thenReturn(4L);
    }

    private void insertAnalyticsMessages() {
        analyticsMessageRepository.save(new AnalyticsMessage(1L, 2L, 4L, 41.3, 1.2, LocalDateTime.now().minusDays(3)));
        analyticsMessageRepository.save(new AnalyticsMessage(2L, 3L, 4L, 23.0, 0.75, LocalDateTime.now().minusDays(3)));

        analyticsMessageRepository.save(new AnalyticsMessage(3L, 6L, 4L, 102.5, 5.1, LocalDateTime.now().minusDays(2)));
        analyticsMessageRepository.save(new AnalyticsMessage(4L, 8L, 4L, 9.7, 0.45, LocalDateTime.now().minusDays(2)));

        analyticsMessageRepository.save(new AnalyticsMessage(5L, 10L, 4L, 65.8, 3.5, LocalDateTime.now().minusDays(1)));
    }

    @Test
    public void getAnalyticsForTimeSpan() {
        insertAnalyticsMessages();
        Map<LocalDate, AnalyticsEntry> analyticsData = requestHandler.getAnalyticsForTimeSpan("", LocalDate.now().minusDays(4), LocalDate.now());

        assertEquals(3, analyticsData.size());
    }
}