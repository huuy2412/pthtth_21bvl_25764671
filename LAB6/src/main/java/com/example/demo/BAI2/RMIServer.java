package com.example.demo.BAI2;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1100);
			CalculatorImpl calculator = new CalculatorImpl();
			Naming.rebind("rmi://localhost:1100/CalculatorService", calculator);
			System.out.println("Server BAI2 da khoi dong!");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
