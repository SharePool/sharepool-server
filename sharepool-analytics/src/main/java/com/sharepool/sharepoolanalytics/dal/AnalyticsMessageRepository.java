package com.sharepool.sharepoolanalytics.dal;

import com.sharepool.sharepoolanalytics.domain.AnalyticsMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalyticsMessageRepository extends CrudRepository<AnalyticsMessage, Long> {

    List<AnalyticsMessage> getAllByPayerIdAndCreationTimestampIsBetween(Long payerId, Long from, Long to);

    List<AnalyticsMessage> getAllByPayerId(Long payerId);
}
