package com.example.lab4.network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public class HostInspector {
    public static void main(String[] args){
        if (args.length != 1){
            System.out.println("Usage: java HostInspector <hostname>");
            return;
        }
        try{
            InetAddress[] addresses = InetAddress.getAllByName(args[0]);

            System.out.println("IP addresses for " + args[0] + ":");
            for (InetAddress address : addresses) {
                System.out.println("--------------------");
                System.out.println("IP: " +address.getHostAddress());
            }

            System.out.println("Canonical hostname: " + addresses[0].getCanonicalHostName());

            System.out.println("Loopback: "+ addresses[0].isLoopbackAddress());

            System.out.println("Site local: " +addresses[0].isSiteLocalAddress());

            if (addresses[0] instanceof Inet4Address) {
                System.out.println("Type: IPv4");
            } else if (addresses[0] instanceof Inet6Address) {
                System.out.println("Type: IPv6");
            }
        } catch (java.net.UnknownHostException e){
            System.out.println("Unknown host: " + args[0]);
        }
    }
}
