package com.example.demo.BAI3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeImpl extends UnicastRemoteObject implements Prime {
	public PrimeImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean isPrime(int n) throws RemoteException {
		if (n < 2) {
			return false;
		}
		for (int i = 2; i <= n / i; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
