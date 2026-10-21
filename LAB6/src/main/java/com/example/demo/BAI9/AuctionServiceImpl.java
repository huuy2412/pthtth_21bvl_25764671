package com.example.demo.BAI9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class AuctionServiceImpl extends UnicastRemoteObject implements AuctionService {

	private static class Product {
		String name;
		double startingPrice;
		double highestPrice;
		String highestBidder;

		Product(String name, double startingPrice) {
			this.name = name;
			this.startingPrice = startingPrice;
			this.highestPrice = startingPrice;
			this.highestBidder = null;
		}
	}

	private final Map<String, Product> products = new LinkedHashMap<>();
	private final Map<String, AuctionCallback> bidders = new HashMap<>();

	public AuctionServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public synchronized boolean addProduct(String name, double startingPrice) throws RemoteException {
		if (name == null || name.isBlank() || !Double.isFinite(startingPrice) || startingPrice <= 0) {
			return false;
		}
		String key = name.trim().toLowerCase(Locale.ROOT);
		if (products.containsKey(key)) {
			return false;
		}
		products.put(key, new Product(name.trim(), startingPrice));
		System.out.println("Da them san pham: " + name);
		return true;
	}

	@Override
	public synchronized String listProducts() throws RemoteException {
		if (products.isEmpty()) {
			return "Chua co san pham dau gia!";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== DANH SACH DAU GIA =====\n");
		for (Product p : products.values()) {
			sb.append("San pham: ").append(p.name).append("\n");
			sb.append("Gia khoi diem: ").append(p.startingPrice).append("\n");
			sb.append("Gia hien tai: ").append(p.highestPrice).append("\n");
			sb.append("Nguoi tra cao nhat: ").append(p.highestBidder == null ? "Chua co" : p.highestBidder)
					.append("\n---------------------------\n");
		}
		return sb.toString();
	}

	@Override
	public synchronized void registerBidder(String bidderName, AuctionCallback callback) throws RemoteException {
		if (bidderName == null || bidderName.isBlank() || callback == null) {
			throw new RemoteException("Thong tin nguoi dau gia khong hop le!");
		}
		bidders.put(bidderName, callback);
		System.out.println("Bidder ket noi: " + bidderName);
	}

	@Override
	public synchronized void unregisterBidder(String bidderName) throws RemoteException {

		bidders.remove(bidderName);
		System.out.println("Bidder ngat ket noi: " + bidderName);
	}

	@Override
	public boolean placeBid(String bidderName, String productName, double amount) throws RemoteException {
		AuctionCallback oldCallback = null;
		String actualName;
		synchronized (this) {
			if (bidderName == null || bidderName.isBlank() || productName == null || productName.isBlank()
					|| !Double.isFinite(amount) || amount <= 0) {
				return false;
			}
			if (!bidders.containsKey(bidderName)) {
				return false;
			}
			String key = productName.trim().toLowerCase(Locale.ROOT);
			Product p = products.get(key);
			if (p == null || amount <= p.highestPrice) {
				return false;
			}
			if (p.highestBidder != null && !p.highestBidder.equals(bidderName)) {
				oldCallback = bidders.get(p.highestBidder);
			}
			p.highestPrice = amount;
			p.highestBidder = bidderName;
			actualName = p.name;
		}

		System.out.println(bidderName + " da tra gia " + amount + " cho " + actualName);
		if (oldCallback != null) {
			try {
				oldCallback.onOutbid(actualName, amount, bidderName);
			} catch (RemoteException e) {
				System.out.println("Khong gui duoc thong bao den Bidder cu.");
			}
		}
		return true;
	}
}
