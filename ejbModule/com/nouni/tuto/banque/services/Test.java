package com.nouni.tuto.banque.services;

import java.util.Optional;

import com.nouni.tuto.banque.metier.AccountStore;
import com.nouni.tuto.banque.metier.AccountStoreLocal;

public class Test {

	public static void main(String[] args) {
		AccountStoreLocal metier = new AccountStore();
		metier.create(2500);
		metier.listAll().forEach(System.out::println);
		Optional.ofNullable(metier.findByRef("AC-1000")).ifPresent(System.out::println);
		metier.create(0);
		metier.listAll().forEach(System.out::println);
		metier.retirer("AC-1000", 500);
		metier.verser("AC-1001",300);
		metier.virer("AC-1000", "AC-1001", 1200);
		Optional.ofNullable(metier.findByRef("AC-1000")).ifPresent(a->a.getEvents().forEach(e -> System.out.println("\t" + e)));
		System.out.println("");
		Optional.ofNullable(metier.findByRef("AC-1001")).ifPresent(a->a.getEvents().forEach(e -> System.out.println("\t" + e)));
		metier.listAll().forEach(System.out::println);
	}

}
