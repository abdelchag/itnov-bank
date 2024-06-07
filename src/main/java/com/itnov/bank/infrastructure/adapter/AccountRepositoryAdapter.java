package com.itnov.bank.infrastructure.adapter;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Operation;
import com.itnov.bank.domain.model.OperationType;
import com.itnov.bank.domain.model.infraPort.AccountRepository;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

	private void doOperation(Account account, double amount, OperationType type) {
		double newBalance = account.getBalance() + amount;
		Operation operation = Operation.builder().date(new Date()).amount(Math.abs(amount)).type(type).soldeAfter(newBalance)
				.build();
		account.setBalance(newBalance);
		account.addOperation(operation);
	}

	@Override
	public Account credit(Account account, double amount) {
		doOperation(account, amount, OperationType.CREDIT);
		return account;
	}

	@Override
	public Account debit(Account account, double amount) throws Exception {
		if (account.isOverdraft(amount)) {
			throw new Exception("Overdrafted");
		}
		amount = amount * -1;
		doOperation(account, amount, OperationType.DEBIT);
		return account;
	}

}
