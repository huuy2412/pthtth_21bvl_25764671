package com.example.demo.BAI1;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			HelloImpl hello = new HelloImpl();
			Naming.rebind("rmi://localhost:1099/HelloService", hello);
			System.out.println("Server RMI da khoi dong!");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
