package com.itnov.bank.domain.model;

import java.util.Comparator;
import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode(of = "date")
public class Operation implements Comparator<Operation> {
	
	private Date date;
	private double amount;
	private OperationType type;
	private double soldeAfter;
	
	@Override
	public int compare(Operation o1, Operation o2) {
		return o1.date.compareTo(o2.date);
	}
	

}
