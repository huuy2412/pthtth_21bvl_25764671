package com.example.demo.BAI3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Prime extends Remote {
	boolean isPrime(int n) throws RemoteException;
}
