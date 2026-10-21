package com.example.demo.BAI7;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Geometry geometry = (Geometry) Naming.lookup("rmi://localhost:1105/GeometryService");
			while (true) {
				System.out.println("\n===== TINH DIEN TICH HINH HOC =====");
				System.out.println("1. Hinh chu nhat");
				System.out.println("2. Hinh tron");
				System.out.println("3. Hinh tam giac");
				System.out.println("0. Thoat");
				System.out.print("Chon chuc nang: ");
				String chon = sc.nextLine();

				try {
					switch (chon) {
						case "1": {
							System.out.print("Nhap chieu rong: ");
							double width = Double.parseDouble(sc.nextLine());
							System.out.print("Nhap chieu dai: ");
							double height = Double.parseDouble(sc.nextLine());
							if (!hopLe(width) || !hopLe(height)) {
								System.out.println("Kich thuoc khong hop le!");
								break;
							}
							double s = geometry.rectangleArea(width, height);
							System.out.println("Dien tich hinh chu nhat: " + s);
							break;
						}
	
						case "2": {
							System.out.print("Nhap ban kinh: ");
							double radius = Double.parseDouble(sc.nextLine());
							if (!hopLe(radius)) {
								System.out.println("Ban kinh khong hop le!");
								break;
							}
							double s = geometry.circleArea(radius);
							System.out.println("Dien tich hinh tron: " + s);
							break;
						}
	
						case "3": {
							System.out.print("Nhap day tam giac: ");
							double base = Double.parseDouble(sc.nextLine());
							System.out.print("Nhap chieu cao: ");
							double height = Double.parseDouble(sc.nextLine());
							if (!hopLe(base) || !hopLe(height)) {
								System.out.println("Kich thuoc khong hop le!");
								break;
							}
							double s = geometry.triangleArea(base, height);
							System.out.println("Dien tich hinh tam giac: " + s);
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
				} catch (NumberFormatException e) {
					System.out.println("Vui long nhap so hop le!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean hopLe(double x) {
		return Double.isFinite(x) && x > 0;
	}
}
