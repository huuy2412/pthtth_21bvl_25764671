package com.example.demo.BAI6;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1104);
			BankAccountImpl bank = new BankAccountImpl();
			Naming.rebind("rmi://localhost:1104/BankService", bank);
			System.out.println("Server ngan hang da khoi dong!");
			System.out.println("So du ban dau: " + bank.getBalance());
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
