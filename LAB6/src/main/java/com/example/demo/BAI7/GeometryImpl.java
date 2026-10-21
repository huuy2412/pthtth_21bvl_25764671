package com.example.demo.BAI7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GeometryImpl extends UnicastRemoteObject implements Geometry {
	public GeometryImpl() throws RemoteException {
		super();
	}

	@Override
	public double rectangleArea(double width, double height) throws RemoteException {
		return width * height;
	}

	@Override
	public double circleArea(double radius) throws RemoteException {
		return Math.PI * radius * radius;
	}

	@Override
	public double triangleArea(double base, double height) throws RemoteException {
		return (base * height) / 2;
	}
}
