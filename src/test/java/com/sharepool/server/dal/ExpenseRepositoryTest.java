package com.sharepool.server.dal;

import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenseRepositoryTest {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Test
	public void testCreateExpense() {
		Expense expense = new Expense(
				"This is a description",
				LocalDate.of(2012, 12, 12),
				Currency.getInstance(Locale.GERMANY),
				1,
				new User("user1", "Tobias", "Kaderle", "tobias.k@mail.at", "somehash", Collections.emptySet()),
				new User("user2", "Jan", "Wiesbauer", "jan.w@mail.com", "somehash", Collections.emptySet()),
				new Tour()
		);

		Expense savedExpense = expenseRepository.save(expense);

		Assert.assertEquals(expense, savedExpense);
	}
}