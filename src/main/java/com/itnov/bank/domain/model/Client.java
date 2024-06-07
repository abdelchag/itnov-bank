package com.itnov.bank.domain.model;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Client {

	@Default
	private String id = UUID.randomUUID().toString();
	@Default
	private Set<Account> accounts = new HashSet<>();
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

}
