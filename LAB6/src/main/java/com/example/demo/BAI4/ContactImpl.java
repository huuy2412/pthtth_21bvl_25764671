package com.example.demo.BAI4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactImpl extends UnicastRemoteObject implements Contact {
	private HashMap<String, String> danhBa = new HashMap<>();
	public ContactImpl() throws RemoteException {
		super();
	}

	@Override
	public synchronized void addContact(String name, String phone) throws RemoteException {
		danhBa.put(name, phone);
		System.out.println("Da luu lien he: " + name + " - " + phone);
	}

	@Override
	public synchronized String findContact(String name) throws RemoteException {
		return danhBa.get(name);
	}

	@Override
	public synchronized boolean deleteContact(String name) throws RemoteException {
		if (danhBa.containsKey(name)) {
			danhBa.remove(name);
			System.out.println("Da xoa lien he: " + name);
			return true;
		}
		return false;
	}
}
