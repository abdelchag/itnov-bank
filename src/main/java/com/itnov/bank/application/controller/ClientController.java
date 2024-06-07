package com.itnov.bank.application.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itnov.bank.application.controller.request.OperationRequest;
import com.itnov.bank.application.controller.request.TransferRequest;
import com.itnov.bank.domain.model.Account;
import com.itnov.bank.domain.model.Client;
import com.itnov.bank.domain.model.Operation;
import com.itnov.bank.domain.model.OperationType;
import com.itnov.bank.domain.model.port.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientAdapter;

	@GetMapping("/{id}/accounts")
	public Set<Account> getAccounts(@PathVariable("id") String clientId) {
		return this.clientAdapter.findAccounts(clientId);
	}

	@PutMapping("/{id}")
	public Client operation(@PathVariable("id") String clientId, @RequestBody OperationRequest request) throws Exception {
		return request.getType() == OperationType.CREDIT
				? this.clientAdapter.credit(clientId, request.getAccountId(), request.getAmount())
				: this.clientAdapter.debit(clientId, request.getAccountId(), request.getAmount());
	}

	@PutMapping("/{id}/transfer")
	public Client transfer(@PathVariable("id") String clientId, @RequestBody TransferRequest request) throws Exception {
		return this.clientAdapter.transfer(clientId, request.getFromAccountId(), request.getToAccountId(),
				request.getAmount());

	}
	
	@GetMapping("/{id}/operations")
	public Set<Operation> operationHistory(@PathVariable("id") String clientId) {
		return this.clientAdapter.getOperationHistory(clientId);
	}

}
