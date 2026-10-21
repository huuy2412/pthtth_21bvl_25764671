package com.example.demo.BAI3;

import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Prime prime = (Prime) Naming.lookup("rmi://localhost:1101/PrimeService");
			System.out.print("Nhap so nguyen n: ");
			int n = sc.nextInt();
			boolean result = prime.isPrime(n);
			if (result) {
				System.out.println(n + " la so nguyen to.");
			} else {
				System.out.println(n + " khong phai la so nguyen to.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
