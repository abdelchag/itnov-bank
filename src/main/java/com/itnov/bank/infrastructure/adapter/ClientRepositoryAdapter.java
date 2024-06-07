package com.itnov.bank.infrastructure.adapter;

import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.Operation;
import com.itnov.bank.domain.model.infraPort.ClientRepository;
import com.itnov.bank.infrastructure.db.ITNovBankDB;

@Repository
public class ClientRepositoryAdapter implements ClientRepository {

	@Override
	public Optional<Client> findClient(String clientId) {
		return ITNovBankDB.BANK_DB.stream().filter(client -> clientId.equals(client.getId())).findFirst();
	}

	@Override
	public Set<Account> findAccounts(String clientId) {
		return this.findClient(clientId).map(Client::getAccounts).orElse(new HashSet<>());
	}

	@Override
	public Optional<Account> findAccount(String clientId, String accountId) {
		return this.findAccounts(clientId).stream().filter(account -> accountId.equals(account.getId())).findFirst();
	}

	@Override
	public Set<Operation> getOperationHistory(String clientId) {
		return this.findAccounts(clientId).stream().map(Account::getOperations).flatMap(operation -> operation.stream())
				.sorted()
				.collect(Collectors.toSet());
	}

}
