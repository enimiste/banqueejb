package com.nouni.tuto.banque.metier;

import java.util.List;
import java.util.Optional;

import javax.ejb.Local;

@Local
public interface AccountStoreLocal {
	public String create(double soldeInitial);
	public void updateSolde(String ref, double newSolde);
	public List<Account> listAll();
	public Account findByRef(String ref);
	public void delete(String ref);
	public List<Account> withSoldeLessThan(double solde);
	public void retirer(String ref, double montant);
	public void verser(String ref, double montant);
	public void virer(String source, String destination, double montant);
}
