package com.example.demo.BAI4;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1102);
			ContactImpl contact = new ContactImpl();
			Naming.rebind("rmi://localhost:1102/ContactService", contact);
			System.out.println("Server BAI4 da khoi dong!");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
