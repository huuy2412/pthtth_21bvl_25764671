package com.example.demo.BAI8;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1106);
			FlightServiceImpl flight = new FlightServiceImpl();
			Naming.rebind("rmi://localhost:1106/FlightService", flight);
			System.out.println("Server BAI8 da khoi dong!");
			System.out.println("Danh sach chuyen bay:");
			System.out.println("VN101 - 50 ghe");
			System.out.println("VN202 - 30 ghe");
			System.out.println("VJ303 - 40 ghe");
			System.out.println("Dang cho Client ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
