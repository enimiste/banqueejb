package com.nouni.tuto.banque.services;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.nouni.tuto.banque.metier.Account;
import com.nouni.tuto.banque.metier.AccountStoreLocal;

@WebService(serviceName = "account-ws")
public class AccountWs {

	@EJB
	protected AccountStoreLocal metier;
	
	@WebMethod
	public List<Account> listAllAccounts() {
		return metier.listAll();
	}
}
