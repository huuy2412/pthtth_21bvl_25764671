package com.example.demo.BAI8;

import java.rmi.Naming;
import java.util.Scanner;
import java.util.Locale;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {

			FlightService flight = (FlightService) Naming.lookup("rmi://localhost:1106/FlightService");

			while (true) {
				System.out.println("\n===== HE THONG DAT VE MAY BAY =====");
				System.out.println("1. Kiem tra so ghe trong");
				System.out.println("2. Dat ve may bay");
				System.out.println("3. Huy ve may bay");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");

				String chon = sc.nextLine();

				switch (chon) {
					case "1": {
						System.out.print("Nhap ma chuyen bay: ");
						String ma = sc.nextLine().trim().toUpperCase(Locale.ROOT);
						int soGhe = flight.availableSeats(ma);
						if (soGhe == -1) {
							System.out.println("Khong tim thay chuyen bay!");
						} else {
							System.out.println("So ghe con trong: " + soGhe);
						}
						break;
					}
	
					case "2": {
						System.out.print("Nhap ma chuyen bay: ");
						String ma = sc.nextLine().trim().toUpperCase(Locale.ROOT);
						
						if (flight.availableSeats(ma) == -1) {
							System.out.println("Khong tim thay chuyen bay!");
							break;
						}
	
						System.out.print("Nhap so ghe can dat: ");
	
						try {
							int seats = Integer.parseInt(sc.nextLine());
							boolean result = flight.bookTicket(ma, seats);
	
							if (result) {
								System.out.println("Dat ve thanh cong!");
								System.out.println("So ghe con lai: " + flight.availableSeats(ma));
							} else {
								System.out.println("Dat ve that bai! Kiem tra so ghe.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Vui long nhap so ghe hop le!");
						}
						break;
					}
	
					case "3": {
						System.out.print("Nhap ma chuyen bay: ");
						String ma = sc.nextLine().trim().toUpperCase(Locale.ROOT);
	
						if (flight.availableSeats(ma) == -1) {
							System.out.println("Khong tim thay chuyen bay!");
							break;
						}
						System.out.print("Nhap so ghe can huy: ");
	
						try {
							int seats = Integer.parseInt(sc.nextLine());
							boolean result = flight.cancelBooking(ma, seats);
	
							if (result) {
								System.out.println("Huy ve thanh cong!");
								System.out.println("So ghe hien tai: " + flight.availableSeats(ma));
							} else {
								System.out.println("Huy ve that bai!");
							}
						} catch (NumberFormatException e) {
							System.out.println("Vui long nhap so ghe hop le!");
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
