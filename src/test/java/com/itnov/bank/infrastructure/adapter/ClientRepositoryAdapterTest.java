package com.itnov.bank.infrastructure.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.infrastructure.db.ITNovBankDB;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryAdapterTest {
	
	@InjectMocks
	private ClientRepositoryAdapter repository;
	
	static Client client1;
	static Account account1;
	
	@BeforeAll
	public static void init() {
		client1 = Client.builder().build();
		account1 = Account.builder().build();
		Account account2 = Account.builder().build();
		client1.addAccount(account1);
		client1.addAccount(account2);
		Client client2 = Client.builder().build();
		Account account3 = Account.builder().build();
		Account account4 = Account.builder().build();
		Account account5 = Account.builder().build();
		client2.addAccount(account3);
		client2.addAccount(account4);
		client2.addAccount(account5);
		ITNovBankDB.BANK_DB.add(client1);
		ITNovBankDB.BANK_DB.add(client2);
	}
	
	@Test
	public void shouldFindClientById() {
		// Given
		String clientId = client1.getId();
		// When
		Client actual = repository.findClient(clientId).get();
		// Then
		assertThat(actual).isEqualTo(client1);
	}
	
	@Test
	public void shouldFindAllClientAccount() {
		// Given
		String clientId = client1.getId();
		// When
		Set<Account> actual = repository.findAccounts(clientId);
		// Then
		assertThat(actual).usingRecursiveComparison().isEqualTo(client1.getAccounts());
	}
	
	@Test
	public void shouldFindClientAccountById() {
		// Given
		String clientId = client1.getId();
		String accountId = account1.getId();
		// When
		Account actual = repository.findAccount(clientId, accountId).get();
		// Then
		assertThat(actual).isEqualTo(account1);
	}


}
