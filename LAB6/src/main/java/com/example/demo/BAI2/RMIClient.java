package com.example.demo.BAI2;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1100/CalculatorService");
			System.out.print("Nhap so a: ");
			int a = sc.nextInt();
			System.out.print("Nhap so b: ");
			int b = sc.nextInt();
			int tong = calculator.add(a, b);
			System.out.println("Tong hai so = " + tong);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
