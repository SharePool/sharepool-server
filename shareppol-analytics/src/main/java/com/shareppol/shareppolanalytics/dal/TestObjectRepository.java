package com.shareppol.shareppolanalytics.dal;

import com.shareppol.shareppolanalytics.domain.TestObject;
import org.springframework.data.repository.CrudRepository;

public interface TestObjectRepository extends CrudRepository<TestObject, Long> {
}
