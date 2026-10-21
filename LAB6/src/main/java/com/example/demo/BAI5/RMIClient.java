package com.example.demo.BAI5;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			ChatService chat = (ChatService) Naming.lookup("rmi://localhost:1103/ChatService");
			System.out.println("Da ket noi den Server Chat!");
			while (true) {
				System.out.print("\nClient nhap tin nhan: ");
				String message = sc.nextLine();
				if (message.equalsIgnoreCase("exit")) {
					System.out.println("Da thoat Chat!");
					break;
				}
				chat.sendMessage(message);
				String reply = chat.getReply();
				System.out.println("Server: " + reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
