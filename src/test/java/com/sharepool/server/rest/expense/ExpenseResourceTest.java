package com.sharepool.server.rest.expense;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.logic.user.UserRestRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ExpenseResourceTest extends AbstractUtilTest {

    @Autowired
    private ExpenseResource expenseResource;

    @Autowired
    private UserRestRequestHandler userRestRequestHandler;

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void testRequestConfirmWorkflow() {
    }
}