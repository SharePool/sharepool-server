package com.shareppol.sharepoolanalytics.logic;

import com.shareppol.sharepoolanalytics.dal.TestObjectRepository;
import com.shareppol.sharepoolanalytics.domain.TestObject;
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
