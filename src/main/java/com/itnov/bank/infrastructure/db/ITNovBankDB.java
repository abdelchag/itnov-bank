package com.itnov.bank.infrastructure.db;

import java.util.Set;
import java.util.HashSet;

import com.itnov.bank.domain.model.Client;

public interface ITNovBankDB {
	Set<Client> BANK_DB = new HashSet<>();
}
