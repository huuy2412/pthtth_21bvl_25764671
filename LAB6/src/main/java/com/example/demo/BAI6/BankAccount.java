package com.example.demo.BAI6;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankAccount extends Remote {
	double getBalance() throws RemoteException;
	void deposit(double amount) throws RemoteException;
	boolean withdraw(double amount) throws RemoteException;
}
