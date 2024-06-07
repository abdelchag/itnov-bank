package com.itnov.bank.application.controller.request;

import com.itnov.bank.domain.model.OperationType;

import lombok.Data;

@Data
public class OperationRequest {
	private OperationType type;
	private String accountId;
	private double amount;
}
