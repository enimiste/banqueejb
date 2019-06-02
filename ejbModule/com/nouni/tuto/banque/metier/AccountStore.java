package com.nouni.tuto.banque.metier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class AccountStore
 */
@Singleton(name = "account-store-ejb", mappedName = "ejb/account-store-ejb")
public class AccountStore implements AccountStoreRemote, AccountStoreLocal {

	AtomicInteger id;

	Map<String, Account> items;

	/**
	 * Default constructor.
	 */
	public AccountStore() {
		this.id = new AtomicInteger(1000);
		this.items = new ConcurrentHashMap<String, Account>();
	}
	
	@PostConstruct
	public void seed() {
		create(1000);
		create(4000);
		create(0);
	}

	@Override
	public void updateSolde(String ref, double newSolde) {
		Optional.ofNullable(findByRef(ref)).ifPresent(c -> {
			c.setSolde(newSolde);
		});
	}

	@Override
	public void delete(String ref) {
		items.remove(ref);
	}

	@Override
	public void retirer(String ref, double montant) {
		Optional.ofNullable(findByRef(ref)).ifPresent(c -> {
			c.retirer(montant);
		});
	}

	@Override
	public void verser(String ref, double montant) {
		Optional.ofNullable(findByRef(ref)).ifPresent(c -> {
			c.verser(montant);
		});
	}

	@Override
	public String create(double soldeInitial) {
		String ref = String.format("AC-%d", id.getAndIncrement());
		Account acc = new Account(ref, soldeInitial, Calendar.getInstance().getTime());
		acc.markAsCreated();
		items.put(ref, acc);
		return acc.getRef();
	}

	@Override
	public List<Account> listAll() {
		return new ArrayList<Account>(items.values());
	}

	@Override
	public Account findByRef(String ref) {
		return items.get(ref);
	}

	@Override
	public List<Account> withSoldeLessThan(double solde) {
		return items.values().stream().filter(c -> {
			return c.getSolde() <= solde;
		}).collect(Collectors.toList());
	}

	@Override
	public void virer(String source, String destination, double montant) {
		Optional.ofNullable(findByRef(source)).ifPresent(s -> {
			Optional.ofNullable(findByRef(destination)).ifPresent(d -> {
				double mnt = Math.abs(montant);
				s.virerOut(mnt, destination);
				d.virerIn(mnt, source);
			});
		});
	}

}
