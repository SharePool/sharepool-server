package com.shareppol.sharepoolanalytics.dal;

import com.shareppol.sharepoolanalytics.domain.TestObject;
import org.springframework.data.repository.CrudRepository;

public interface TestObjectRepository extends CrudRepository<TestObject, Long> {
}
