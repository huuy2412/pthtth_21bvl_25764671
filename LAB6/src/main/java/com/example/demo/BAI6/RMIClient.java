package com.example.demo.BAI6;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			BankAccount bank = (BankAccount) Naming.lookup("rmi://localhost:1104/BankService");
			while (true) {
				System.out.println("\n===== QUAN LY TAI KHOAN NGAN HANG =====");
				System.out.println("1. Kiem tra so du");
				System.out.println("2. Gui tien");
				System.out.println("3. Rut tien");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");
				String chon = sc.nextLine();

				switch (chon) {
					case "1": {
						System.out.println("So du hien tai: " + bank.getBalance() + " VND");
						break;
					}
	
					case "2": {
						System.out.print("Nhap so tien can gui: ");
						double amount = Double.parseDouble(sc.nextLine());
						if (!Double.isFinite(amount) || amount <= 0) {
							System.out.println("So tien khong hop le!");
							break;
						}
						bank.deposit(amount);
						System.out.println("Gui tien thanh cong!");
						System.out.println("So du moi: " + bank.getBalance() + " VND");
						break;
					}
	
					case "3": {
						System.out.print("Nhap so tien can rut: ");
						double amount = Double.parseDouble(sc.nextLine());
						if (!Double.isFinite(amount) || amount <= 0) {
							System.out.println("So tien khong hop le!");
							break;
						}
						boolean result = bank.withdraw(amount);
						if (result) {
							System.out.println("Rut tien thanh cong!");
							System.out.println("So du moi: " + bank.getBalance() + " VND");
						} else {
							System.out.println("Rut tien that bai! So du khong du.");
						}
						break;
					}
	
					case "0": {
						System.out.println("Thoat chuong trinh!");
						return;
					}
					default: {
						System.out.println("Lua chon khong hop le!");
					}
				}
			}

		} catch (NumberFormatException e) {
			System.out.println("Vui long nhap so hop le!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
