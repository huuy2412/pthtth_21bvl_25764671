
package com.example.demo.BAI4;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Contact contact = (Contact) Naming.lookup("rmi://localhost:1102/ContactService");
			while (true) {
				System.out.println("\n===== QUAN LY DANH BA =====");
				System.out.println("1. Them lien he");
				System.out.println("2. Tim kiem lien he");
				System.out.println("3. Sua lien he");
				System.out.println("4. Xoa lien he");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");
				String chon = sc.nextLine();
				switch (chon) {
					case "1": {
						System.out.print("Nhap ten: ");
						String name = sc.nextLine().trim();
						System.out.print("Nhap so dien thoai: ");
						String phone = sc.nextLine().trim();
						if (name.isEmpty() || phone.isEmpty()) {
							System.out.println("Ten va so dien thoai khong duoc rong!");
							break;
						}
						contact.addContact(name, phone);
						System.out.println("Them lien he thanh cong!");
						break;
					}
	
					case "2": {
						System.out.print("Nhap ten can tim: ");
						String name = sc.nextLine().trim();
						String phone = contact.findContact(name);
						if (phone != null) {
							System.out.println("Ten: " + name);
							System.out.println("So dien thoai: " + phone);
						} else {
							System.out.println("Khong tim thay lien he!");
						}
						break;
					}
	
					case "3": {
						System.out.print("Nhap ten can sua: ");
						String name = sc.nextLine().trim();
						String oldPhone = contact.findContact(name);
						if (oldPhone != null) {
							System.out.print("Nhap so dien thoai moi: ");
							String newPhone = sc.nextLine().trim();
							if (newPhone.isEmpty()) {
								System.out.println("So dien thoai khong duoc rong!");
								break;
							}
							contact.addContact(name, newPhone);
							System.out.println("Cap nhat thanh cong!");
						} else {
							System.out.println("Khong tim thay lien he!");
						}
						break;
					}
	
					case "4": {
						System.out.print("Nhap ten can xoa: ");
						String name = sc.nextLine().trim();
						boolean result = contact.deleteContact(name);
						if (result) {
							System.out.println("Xoa thanh cong!");
						} else {
							System.out.println("Khong tim thay lien he!");
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
