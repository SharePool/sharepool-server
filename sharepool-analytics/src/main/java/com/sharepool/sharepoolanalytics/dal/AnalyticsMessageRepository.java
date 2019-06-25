package com.sharepool.sharepoolanalytics.dal;

import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnalyticsMessageRepository extends CrudRepository<AnalyticsMessage, Long> {

    List<AnalyticsMessage> getAllByPayerIdAndCreationTimeIsBetween(Long payerId, LocalDateTime from, LocalDateTime to);

    List<AnalyticsMessage> getAllByPayerId(Long payerId);
}
