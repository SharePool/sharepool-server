package com.sharepool.server.dal;

import org.springframework.data.repository.CrudRepository;

import com.sharepool.server.domain.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
