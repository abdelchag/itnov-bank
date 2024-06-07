package com.itnov.bank.domain.model.infraPort;

import com.itnov.bank.domain.model.Account;

public interface AccountRepository {
	
	Account credit(Account account, double amount);
	Account debit(Account account, double amount) throws Exception;

}
