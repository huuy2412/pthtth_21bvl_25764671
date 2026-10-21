package com.example.demo.BAI9;

import java.rmi.Naming;
import java.util.Scanner;

public class SellerClient {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			AuctionService auction = (AuctionService) Naming.lookup("rmi://localhost:1107/AuctionService");
			while (true) {
				System.out.println("\n===== MENU SELLER =====");
				System.out.println("1. Them san pham");
				System.out.println("2. Xem danh sach san pham");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");
				String chon = sc.nextLine();
				switch (chon) {
				
				case "1": {
					System.out.print("Nhap ten san pham: ");
					String name = sc.nextLine();
					System.out.print("Nhap gia khoi diem: ");
					try {
						double price = Double.parseDouble(sc.nextLine());
						boolean result = auction.addProduct(name, price);
						if (result) {
							System.out.println("Them san pham thanh cong!");
						} else {
							System.out.println("Them that bai! Kiem tra ten va gia.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Gia khong hop le!");
					}
					break;
				}

				case "2": {
					System.out.println(auction.listProducts());
					break;
				}

				case "0": {
					System.out.println("Seller da thoat!");
					return;
				}

				default: {
					System.out.println("Lua chon khong hop le!");
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
