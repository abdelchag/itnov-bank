package com.itnov.bank.domain.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.infraPort.AccountRepository;
import com.itnov.bank.domain.model.infraPort.ClientRepository;
import com.itnov.bank.infrastructure.db.ITNovBankDB;

@ExtendWith(MockitoExtension.class)
public class ClientServiceAdapterTest {

	@Mock
	private ClientRepository clientRepository;
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	private ClientServiceAdapter adapter;

	static Client client1;
	static Account account1;
	static Account account2;

	@BeforeAll
	public static void init() {
		client1 = Client.builder().build();
		account1 = Account.builder().build();
		account2 = Account.builder().build();
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
	public void shouldFindClientAccounts() {
		// Given
		String clientId = client1.getId();
		when(clientRepository.findAccounts(clientId)).thenReturn(client1.getAccounts());
		// When
		Set<Account> actual = adapter.findAccounts(clientId);
		// Then
		assertThat(actual).usingRecursiveComparison().isEqualTo(client1.getAccounts());
	}

	@Test
	public void shouldCreditClientAccount() {
		// Given
		String clientId = client1.getId();
		String accountId = account1.getId();
		double givenAmount = 80.5;
		when(clientRepository.findAccount(clientId, accountId)).thenReturn(Optional.of(account1));
		when(accountRepository.credit(account1, givenAmount)).thenReturn(account1);
		when(clientRepository.findClient(clientId)).thenReturn(Optional.of(client1));
		// When
		Client actual = adapter.credit(clientId, accountId, givenAmount);
		// Then
		assertThat(actual).isEqualTo(client1);
		verify(clientRepository).findAccount(clientId, accountId);
		verify(accountRepository).credit(account1, givenAmount);
		verify(clientRepository).findClient(clientId);
	}

	@Test
	public void shouldDebitClientAccount() throws Exception {
		// Given
		String clientId = client1.getId();
		String accountId = account1.getId();
		double givenAmount = 80.5;
		when(clientRepository.findAccount(clientId, accountId)).thenReturn(Optional.of(account1));
		when(accountRepository.debit(account1, givenAmount)).thenReturn(account1);
		when(clientRepository.findClient(clientId)).thenReturn(Optional.of(client1));
		// When
		Client actual = adapter.debit(clientId, accountId, givenAmount);
		// Then
		assertThat(actual).isEqualTo(client1);
		verify(clientRepository).findAccount(clientId, accountId);
		verify(accountRepository).debit(account1, givenAmount);
		verify(clientRepository).findClient(clientId);
	}

	@Test
	public void shouldThrowExceptionWhenDebitClientAccount() throws Exception {
		// Given
		String clientId = client1.getId();
		String accountId = account1.getId();
		double givenAmount = 80.5;
		when(clientRepository.findAccount(clientId, accountId)).thenReturn(Optional.of(account1));
		when(accountRepository.debit(account1, givenAmount)).thenThrow(Exception.class);
		// When
		assertThatThrownBy(() -> adapter.debit(clientId, accountId, givenAmount)).isInstanceOf(Exception.class);
		// Then
		verify(clientRepository).findAccount(clientId, accountId);
		verify(accountRepository).debit(account1, givenAmount);
		verify(clientRepository, never()).findClient(clientId);
	}

	@Test
	public void shouldTransferAmount() throws Exception {
		// Given
		String clientId = client1.getId();
		String accountId1 = account1.getId();
		String accountId2 = account2.getId();
		double givenAmount = 80.5;
		when(clientRepository.findAccount(clientId, accountId1)).thenReturn(Optional.of(account1));
		when(clientRepository.findAccount(clientId, accountId2)).thenReturn(Optional.of(account2));
		when(accountRepository.debit(account1, givenAmount)).thenReturn(account1);
		when(accountRepository.credit(account2, givenAmount)).thenReturn(account1);
		when(clientRepository.findClient(clientId)).thenReturn(Optional.of(client1));
		// When
		Client actual = adapter.transfer(clientId, accountId1, accountId2, givenAmount);
		// Then
		assertThat(actual).isEqualTo(client1);
		verify(clientRepository).findAccount(clientId, accountId1);
		verify(clientRepository).findAccount(clientId, accountId2);
		verify(accountRepository).debit(account1, givenAmount);
		verify(accountRepository).credit(account2, givenAmount);
		verify(clientRepository).findClient(clientId);
	}
	
	@Test
	public void shouldThrowExceptionWhenTransferAmount() throws Exception {
		// Given
		String clientId = client1.getId();
		String accountId1 = account1.getId();
		String accountId2 = account2.getId();
		double givenAmount = 80.5;
		when(clientRepository.findAccount(clientId, accountId1)).thenReturn(Optional.of(account1));
		when(clientRepository.findAccount(clientId, accountId2)).thenReturn(Optional.of(account2));
		when(accountRepository.debit(account1, givenAmount)).thenThrow(Exception.class);
		// When
		assertThatThrownBy(() -> adapter.transfer(clientId, accountId1, accountId2, givenAmount)).isInstanceOf(Exception.class);
		// Then
		verify(clientRepository).findAccount(clientId, accountId1);
		verify(clientRepository).findAccount(clientId, accountId2);
		verify(accountRepository).debit(account1, givenAmount);
		verify(accountRepository, never()).credit(account2, givenAmount);
		verify(clientRepository, never()).findClient(clientId);
	}
	
}
