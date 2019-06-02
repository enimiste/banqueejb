package com.nouni.tuto.banque.metier;

import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

@Remote
public interface AccountStoreRemote {
	public String create(double soldeInitial);
	public List<Account> listAll();
	public Account findByRef(String ref);
	public List<Account> withSoldeLessThan(double solde);
	public void virer(String source, String destination, double montant);
}
