package com.example.demo.BAI9;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1107);
			AuctionServiceImpl auction = new AuctionServiceImpl();
			Naming.rebind("rmi://localhost:1107/AuctionService", auction);
			System.out.println("Server dau gia da khoi dong!");
			System.out.println("Dang cho Seller va Bidder ket noi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
