package com.itnov.bank.domain.model.infraPort;

import java.util.Optional;
import java.util.Set;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.Operation;

public interface ClientRepository {

	Optional<Client> findClient(String clientId);
	
	Set<Account> findAccounts(String clientId);
	
	Optional<Account> findAccount(String clientId, String accountId);
	
	Set<Operation> getOperationHistory(String clientId);

	
}
