package com.itnov.bank.domain.model.port;

import java.util.Set;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.Operation;


public interface ClientService {

	Set<Account> findAccounts(String clientId);

	Client credit(String clientId, String accountId, double amount);

	Client debit(String clientId, String accountId, double amount) throws Exception;
	
	Client transfer(String clientId, String fromAccountId, String toAccountId, double amount) throws Exception;
	
	Set<Operation> getOperationHistory(String clientId);

}
