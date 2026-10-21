package com.example.demo.BAI2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
	int add(int a, int b) throws RemoteException;
}
