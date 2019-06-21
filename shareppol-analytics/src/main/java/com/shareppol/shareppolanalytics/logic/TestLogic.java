package com.shareppol.shareppolanalytics.logic;

import com.shareppol.shareppolanalytics.dal.TestObjectRepository;
import com.shareppol.shareppolanalytics.domain.TestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestLogic {

    @Autowired
    private TestObjectRepository repo;

    @PostConstruct
    private void init() {
        repo.save(new TestObject());
    }
}
