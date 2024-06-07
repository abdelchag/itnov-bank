package com.itnov.bank.infrastructure.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Operation;
import com.itnov.bank.domain.model.OperationType;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryAdapterTest {

	@InjectMocks
	private AccountRepositoryAdapter repository;

	@Test
	public void shouldCreditGivenClientAccount() {
		// Given
		double givenBalance = 100.5;
		Account account = Account.builder().balance(givenBalance).build();
		double givenAmount = 50;
		double expectedBalance = givenBalance + givenAmount;
		Operation expectedOperation = Operation.builder().amount(givenAmount).soldeAfter(expectedBalance)
				.type(OperationType.CREDIT).build();
		// When
		Account actual = repository.credit(account, givenAmount);
		// Then
		assertThat(actual.getBalance()).isEqualTo(expectedBalance);
		assertThat(actual.getOperations()).hasSize(1);
		assertThat(actual.getOperations()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("date")
				.contains(expectedOperation);
	}

	@Test
	public void shouldDebitGivenClientAccount() throws Exception {
		// Given
		double givenBalance = 100.5;
		Account account = Account.builder().balance(givenBalance).build();
		double givenAmount = 50;
		double expectedBalance = givenBalance - givenAmount;
		Operation expectedOperation = Operation.builder().amount(givenAmount).soldeAfter(expectedBalance)
				.type(OperationType.DEBIT).build();
		// When
		Account actual = repository.debit(account, givenAmount);
		// Then
		assertThat(actual.getBalance()).isEqualTo(expectedBalance);
		assertThat(actual.getOperations()).hasSize(1);
		assertThat(actual.getOperations()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("date")
				.contains(expectedOperation);
	}

	@Test
	public void shouldThrowExceptionWhenDebitOverdraftClientAccount() throws Exception {
		// Given
		double givenBalance = 30.8;
		Account account = Account.builder().balance(givenBalance).build();
		double givenAmount = 50;
		// When
		assertThatThrownBy(() -> repository.debit(account, givenAmount))
				// Then
				.isInstanceOf(Exception.class).hasMessage("Overdrafted");
	}

}
