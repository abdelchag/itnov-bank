package com.itnov.bank.domain.adapter;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.Operation;
import com.itnov.bank.domain.model.infraPort.AccountRepository;
import com.itnov.bank.domain.model.infraPort.ClientRepository;
import com.itnov.bank.domain.model.port.ClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceAdapter implements ClientService {

	private final ClientRepository clientRepository;
	private final AccountRepository accountRepository;

	@Override
	public Set<Account> findAccounts(String clientId) {
		return clientRepository.findAccounts(clientId);
	}

	@Override
	public Client credit(String clientId, String accountId, double amount) {
		Account account = clientRepository.findAccount(clientId, accountId).get();
		accountRepository.credit(account, amount);
		return this.clientRepository.findClient(clientId).get();
	}

	@Override
	public Client debit(String clientId, String accountId, double amount) throws Exception {
		Account account = clientRepository.findAccount(clientId, accountId).get();
		accountRepository.debit(account, amount);
		return this.clientRepository.findClient(clientId).get();
	}

	@Override
	public Client transfer(String clientId, String fromAccountId, String toAccountId, double amount) throws Exception {
		Account fromAccount = clientRepository.findAccount(clientId, fromAccountId).get();
		Account toAccount = clientRepository.findAccount(clientId, toAccountId).get();
		accountRepository.debit(fromAccount, amount);
		accountRepository.credit(toAccount, amount);
		return clientRepository.findClient(clientId).get();
	}

	@Override
	public Set<Operation> getOperationHistory(String clientId) {
		return clientRepository.getOperationHistory(clientId);
	}
	
	

}
