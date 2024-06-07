package com.itnov.bank.domain.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Account {

	@Default
	private String id = UUID.randomUUID().toString();
	@Default
	private double balance = 0;
	@Default
	private double overdraft = 0;	
	@Default
	private Set<Operation> operations = new HashSet<>();

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}
	
	public boolean isOverdraft(double amount) {
		return this.balance - amount < this.overdraft;
	}
	
	
}
