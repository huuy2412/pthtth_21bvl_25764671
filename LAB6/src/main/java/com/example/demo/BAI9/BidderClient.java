package com.example.demo.BAI9;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class BidderClient {

	private static class BidderCallbackImpl extends UnicastRemoteObject implements AuctionCallback {

		public BidderCallbackImpl() throws RemoteException {
			super();
		}

		@Override
		public void onOutbid(String productName, double newPrice, String newBidder) throws RemoteException {

			System.out.println("\n===== THONG BAO DAU GIA =====");
			System.out.println("Ban da bi vuot gia!");
			System.out.println("San pham: " + productName);
			System.out.println("Nguoi tra gia moi: " + newBidder);
			System.out.println("Gia cao nhat: " + newPrice);
			System.out.println("============================");
		}
	}

	public static void main(String[] args) {

		AuctionService auction = null;
		BidderCallbackImpl callback = null;
		String bidderName = null;

		try (Scanner sc = new Scanner(System.in)) {

			auction = (AuctionService) Naming.lookup("rmi://localhost:1107/AuctionService");

			System.out.print("Nhap ten nguoi dau gia: ");
			bidderName = sc.nextLine().trim();

			if (bidderName.isEmpty()) {
				System.out.println("Ten khong duoc rong!");
				return;
			}

			callback = new BidderCallbackImpl();
			auction.registerBidder(bidderName, callback);

			System.out.println("Da ket noi Server dau gia!");

			while (true) {
				System.out.println("\n===== MENU BIDDER =====");
				System.out.println("1. Xem san pham dau gia");
				System.out.println("2. Dat gia thau");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");

				String chon = sc.nextLine();

				switch (chon) {

				case "1": {
					System.out.println(auction.listProducts());
					break;
				}

				case "2": {
					System.out.print("Nhap ten san pham: ");
					String productName = sc.nextLine();

					System.out.print("Nhap gia muon dau: ");

					try {
						double amount = Double.parseDouble(sc.nextLine());

						boolean result = auction.placeBid(bidderName, productName, amount);

						if (result) {
							System.out.println("Dat gia thanh cong!");
							System.out.println(auction.listProducts());
						} else {
							System.out.println("Dat gia that bai! Kiem tra san pham va gia.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Gia khong hop le!");
					}
					break;
				}

				case "0": {
					System.out.println("Thoat dau gia!");
					return;
				}

				default: {
					System.out.println("Lua chon khong hop le!");
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (auction != null && bidderName != null && callback != null) {
				try {
					auction.unregisterBidder(bidderName);
				} catch (Exception e) {
					System.out.println("Khong the huy dang ky Bidder.");
				}
			}

			if (callback != null) {
				try {
					UnicastRemoteObject.unexportObject(callback, true);
				} catch (Exception e) {
					// Da ngat ket noi callback
				}
			}
		}
	}
}
