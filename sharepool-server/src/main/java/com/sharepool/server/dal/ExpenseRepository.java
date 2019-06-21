package com.sharepool.server.dal;

import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List<Expense> findAllByReceiver(User receiver);

    List<Expense> findAllByPayer(User payer);

    List<Expense> findAllByPayerAndReceiver(User payer, User receiver);
}
