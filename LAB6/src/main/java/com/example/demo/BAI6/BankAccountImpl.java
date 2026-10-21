package com.example.demo.BAI6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankAccountImpl extends UnicastRemoteObject implements BankAccount {
	private double balance = 1000000;
	public BankAccountImpl() throws RemoteException {
		super();
	}

	@Override
	public synchronized double getBalance() throws RemoteException {
		return balance;
	}

	@Override
	public synchronized void deposit(double amount) throws RemoteException {
		if (!Double.isFinite(amount) || amount <= 0 || !Double.isFinite(balance + amount)) {
			throw new RemoteException("So tien gui khong hop le!");
		}
		balance += amount;
		System.out.println("Client da gui: " + amount);
	}

	@Override
	public synchronized boolean withdraw(double amount) throws RemoteException {
		if (!Double.isFinite(amount) || amount <= 0 || amount > balance) {
			return false;
		}
		balance -= amount;
		System.out.println("Client da rut: " + amount);
		return true;
	}
}
