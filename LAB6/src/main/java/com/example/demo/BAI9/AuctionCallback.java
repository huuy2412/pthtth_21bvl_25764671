package com.example.demo.BAI9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuctionCallback extends Remote {
    void onOutbid(String productName, double newPrice, String newBidder) throws RemoteException;
}
