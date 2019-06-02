package com.nouni.tuto.banque.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account implements Serializable {
	protected String ref;
	protected double solde;
	protected Date dateCreation;
	protected List<AccountEvent> events = new ArrayList<Account.AccountEvent>();

	public Account() {
		super();
	}

	public Account(String ref, double solde, Date dateCreation) {
		super();
		this.ref = ref;
		this.solde = solde;
		this.dateCreation = dateCreation;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		double tmp = this.solde;
		this.solde = solde;
		events.add(new Account.SoldeUpdated(ref, Calendar.getInstance().getTime(), this.solde, tmp, solde));
	}

	public List<AccountEvent> getEvents() {
		return events;
	}

	public void setEvents(List<AccountEvent> events) {
		this.events = events;
	}

	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * 
	 */
	public void markAsCreated() {
		events.add(new Account.AccountCreated(ref, Calendar.getInstance().getTime(), this.solde, 0, solde));
	}

	/**
	 * 
	 * @param mnt
	 */
	public void retirer(double mnt) {
		double tmp = this.solde;
		this.solde = this.solde - Math.abs(mnt);
		events.add(new Account.RetraitDone(ref, Calendar.getInstance().getTime(), this.solde, tmp, mnt));

	}

	/**
	 * 
	 * @param mnt
	 */
	public void verser(double mnt) {
		double tmp = this.solde;
		this.solde = this.solde + Math.abs(mnt);
		events.add(new Account.VersementDone(ref, Calendar.getInstance().getTime(), this.solde, tmp, mnt));
	}

	/**
	 * 
	 * @param mnt
	 */
	public void virerIn(double mnt, String sourceRef) {
		double tmp = this.solde;
		this.solde = this.solde + Math.abs(mnt);
		events.add(new Account.VirementInDone(ref, Calendar.getInstance().getTime(), this.solde, tmp, sourceRef, mnt));
	}

	/**
	 * 
	 * @param mnt
	 */
	public void virerOut(double mnt, String destRef) {
		double tmp = this.solde;
		this.solde = this.solde - Math.abs(mnt);
		events.add(new Account.VirementOutDone(ref, Calendar.getInstance().getTime(), this.solde, tmp, destRef, mnt));
	}

	@Override
	public String toString() {
		return "Account [ref=" + ref + ", solde=" + solde + "]";
	}

	public static abstract class AccountEvent implements Serializable  {
		public String accountRef;
		public Date at;
		public double postEventSolde;
		public double preEventSolde;

		public AccountEvent(String accountRef, Date at, double postEventSolde, double preEventSolde) {
			super();
			this.accountRef = accountRef;
			this.at = at;
			this.postEventSolde = postEventSolde;
			this.preEventSolde = preEventSolde;
		}
	}

	public static class AccountCreated extends AccountEvent {
		public double soldeInitial;

		public AccountCreated(String accountRef, Date at, double postEventSolde, double preEventSolde,
				double soldeInitial) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.soldeInitial = soldeInitial;
		}

		@Override
		public String toString() {
			return "AccountCreated [soldeInitial=" + soldeInitial + ", accountRef=" + accountRef + ", at=" + at
					+ ", postEventSolde=" + postEventSolde + ", preEventSolde=" + preEventSolde + "]";
		}

	}

	public static class VirementInDone extends AccountEvent {
		public String sourceAccountRef;
		public double montant;

		public VirementInDone(String accountRef, Date at, double postEventSolde, double preEventSolde,
				String sourceAccountRef, double montant) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.sourceAccountRef = sourceAccountRef;
			this.montant = montant;
		}

		@Override
		public String toString() {
			return "VirementInDone [sourceAccountRef=" + sourceAccountRef + ", montant=" + montant + ", accountRef="
					+ accountRef + ", at=" + at + ", postEventSolde=" + postEventSolde + ", preEventSolde="
					+ preEventSolde + "]";
		}

	}

	public static class VirementOutDone extends AccountEvent {
		public String desAccountRef;
		public double montant;

		public VirementOutDone(String accountRef, Date at, double postEventSolde, double preEventSolde,
				String sourceAccountRef, double montant) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.desAccountRef = sourceAccountRef;
			this.montant = montant;
		}

		@Override
		public String toString() {
			return "VirementOutDone [destAccountRef=" + desAccountRef + ", montant=" + montant + ", accountRef="
					+ accountRef + ", at=" + at + ", postEventSolde=" + postEventSolde + ", preEventSolde="
					+ preEventSolde + "]";
		}

	}

	public static class VersementDone extends AccountEvent {
		public double montant;

		public VersementDone(String accountRef, Date at, double postEventSolde, double preEventSolde, double montant) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.montant = montant;
		}

		@Override
		public String toString() {
			return "VersementDone [montant=" + montant + ", accountRef=" + accountRef + ", at=" + at
					+ ", postEventSolde=" + postEventSolde + ", preEventSolde=" + preEventSolde + "]";
		}

	}

	public static class RetraitDone extends AccountEvent {
		public double montant;

		public RetraitDone(String accountRef, Date at, double postEventSolde, double preEventSolde, double montant) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.montant = montant;
		}

		@Override
		public String toString() {
			return "RetraitDone [montant=" + montant + ", accountRef=" + accountRef + ", at=" + at + ", postEventSolde="
					+ postEventSolde + ", preEventSolde=" + preEventSolde + "]";
		}

	}

	public static class SoldeUpdated extends AccountEvent {
		public double solde;

		public SoldeUpdated(String accountRef, Date at, double postEventSolde, double preEventSolde, double solde) {
			super(accountRef, at, postEventSolde, preEventSolde);
			this.solde = solde;
		}

		@Override
		public String toString() {
			return "SoldeUpdated [solde=" + solde + ", accountRef=" + accountRef + ", at=" + at + ", postEventSolde="
					+ postEventSolde + ", preEventSolde=" + preEventSolde + "]";
		}

	}
}
