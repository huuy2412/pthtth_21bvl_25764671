package com.example.demo.BAI4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Contact extends Remote {
	void addContact(String name, String phone) throws RemoteException;
	String findContact(String name) throws RemoteException;
	boolean deleteContact(String name) throws RemoteException;
}
