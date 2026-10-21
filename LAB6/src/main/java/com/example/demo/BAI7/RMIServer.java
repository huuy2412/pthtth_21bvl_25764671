package com.example.demo.BAI7;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1105);
			GeometryImpl geometry = new GeometryImpl();
			Naming.rebind("rmi://localhost:1105/GeometryService", geometry);
			System.out.println("Server BAI7 da khoi dong!");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
