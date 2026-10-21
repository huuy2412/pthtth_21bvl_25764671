package com.example.demo.BAI1;
import java.rmi.Naming;

public class RMIClient {

	public static void main(String[] args) {
		try {
			Hello hello = (Hello) Naming.lookup("rmi://localhost:1099/HelloService");
			String result = hello.sayHello();
			System.out.println("Ket qua tu Server: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
