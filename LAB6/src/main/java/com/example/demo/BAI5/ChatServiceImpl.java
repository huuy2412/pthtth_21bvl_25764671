package com.example.demo.BAI5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
	private Scanner sc = new Scanner(System.in);
	private String reply = "";
	public ChatServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public synchronized void sendMessage(String message) throws RemoteException {
		System.out.println("\nClient: " + message);
		System.out.print("Server nhap phan hoi: ");
		System.out.flush();
		reply = sc.nextLine();
	}

	@Override
	public synchronized String getReply() throws RemoteException {
		return reply;
	}
}
