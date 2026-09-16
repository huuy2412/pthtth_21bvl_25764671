package com.example.lab4.bai1_BTDX;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class HostURIInspector {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java HostURIInspector <hostname> <URI>");
            return;
        }
        String hostname = args[0];
        String uriText = args[1];
        
        try {
            InetAddress[] addresses =
                    InetAddress.getAllByName(hostname);
            System.out.println("Hostname: " + hostname);
            for (InetAddress address : addresses) {
                System.out.println("--------------------");
                System.out.println("IP: " + address.getHostAddress());

                if (address instanceof Inet4Address) {
                    System.out.println("Type: IPv4");
                } else if (address instanceof Inet6Address) {
                    System.out.println("Type: IPv6");
                }
                System.out.println("Loopback: " + address.isLoopbackAddress());
                System.out.println("Site local: " + address.isSiteLocalAddress());
            }

        } catch (UnknownHostException e) {
            System.err.println("Khong phan giai duoc hostname: " + hostname);
        }

        try {
            URI uri = new URI(uriText);
            System.out.println("\n===== URI INFORMATION =====");
            System.out.println("Scheme: " + uri.getScheme());
            System.out.println("Host: " + uri.getHost());
            System.out.println("Port: " + uri.getPort());
            System.out.println("Path: " + uri.getPath());
            System.out.println("Query: " + uri.getQuery());
            System.out.println("Fragment: " + uri.getFragment());

        } catch (URISyntaxException e) {
            System.err.println("URI khong hop le: " + uriText);
        }
    }
}
