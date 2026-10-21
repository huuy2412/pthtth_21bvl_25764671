package com.example.demo.BAI5;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1103);
			ChatServiceImpl chat = new ChatServiceImpl();
			Naming.rebind("rmi://localhost:1103/ChatService", chat);
			System.out.println("Server Chat RMI da khoi dong!");
			System.out.println("Dang cho Client gui tin nhan...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
