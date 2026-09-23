package com.example.lab4.bai5_BTDX;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    public static void main(String[] args) {
        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );
                PrintWriter writer = new PrintWriter(
                        socket.getOutputStream(),
                        true,
                        StandardCharsets.UTF_8
                );
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối tới server.");
            // Thread nhận dữ liệu từ server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (Exception e) {
                    System.out.println("Đã ngắt kết nối server.");
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();
            // Gửi dữ liệu
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                writer.println(input);
                if (input.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Không kết nối được server: "+ e.getMessage());
        }
    }
}