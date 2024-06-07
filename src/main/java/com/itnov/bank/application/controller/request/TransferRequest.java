package com.itnov.bank.application.controller.request;

import lombok.Data;

@Data
public class TransferRequest {
	private String fromAccountId;
	private String toAccountId;
	private double amount;
}
