package com.example.demo.BAI8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class FlightServiceImpl extends UnicastRemoteObject implements FlightService {

	private HashMap<String, Integer> flights = new HashMap<>();
	private HashMap<String, Integer> booked = new HashMap<>();

	public FlightServiceImpl() throws RemoteException {
		super();

		// Danh sach chuyen bay demo
		flights.put("VN101", 50);
		flights.put("VN202", 30);
		flights.put("VJ303", 40);

		booked.put("VN101", 0);
		booked.put("VN202", 0);
		booked.put("VJ303", 0);
	}

	@Override
	public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
		if (!flights.containsKey(flightNumber) || seats <= 0) {
			return false;
		}

		int available = flights.get(flightNumber);

		if (available >= seats) {
			flights.put(flightNumber, available - seats);
			booked.put(flightNumber, booked.get(flightNumber) + seats);
			System.out.println("Da dat " + seats + " ghe cho chuyen bay " + flightNumber);
			return true;
		}
		return false;
	}

	@Override
	public synchronized int availableSeats(String flightNumber) throws RemoteException {
		if (flights.containsKey(flightNumber)) {
			return flights.get(flightNumber);
		}
		return -1;
	}

	@Override
	public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
		if (!flights.containsKey(flightNumber) || seats <= 0) {
			return false;
		}

		int soGheDaDat = booked.get(flightNumber);

		if (soGheDaDat >= seats) {
			booked.put(flightNumber, soGheDaDat - seats);
			flights.put(flightNumber, flights.get(flightNumber) + seats);
			System.out.println("Da huy " + seats + " ghe cua chuyen bay " + flightNumber);
			return true;
		}
		return false;
	}
}
