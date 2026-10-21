package com.example.demo.BAI3;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1101);
			PrimeImpl prime = new PrimeImpl();
			Naming.rebind("rmi://localhost:1101/PrimeService", prime);
			System.out.println("Server BAI3 da khoi dong!");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
