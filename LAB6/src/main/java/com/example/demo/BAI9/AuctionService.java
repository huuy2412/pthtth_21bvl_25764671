package com.example.demo.BAI9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuctionService extends Remote {
	boolean addProduct(String name, double startingPrice) throws RemoteException;
	String listProducts() throws RemoteException;
	boolean placeBid(String bidderName, String productName, double amount) throws RemoteException;
	void registerBidder(String bidderName, AuctionCallback callback) throws RemoteException;
	void unregisterBidder(String bidderName) throws RemoteException;
}
