package com.example.lab4.bai2_BTDX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DigitServer {

    private static final int PORT = 5000;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Digit Server listening on port " + PORT);
            while (true) {
                try (Socket socket = server.accept()) {
                    serve(socket);
                } catch (IOException e) {
                    System.err.println("Lỗi client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không mở được server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));

            PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),true)
        ) {

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("QUIT")) {
                    out.println("OK BYE");
                    break;
                }
                String response = convert(request);
                out.println(response);
            }
        }
    }

    static String convert(String input) {
        String[] words = {
            "không",
            "một",
            "hai",
            "ba",
            "bốn",
            "năm",
            "sáu",
            "bảy",
            "tám",
            "chín"
        };

        if (input.length() != 1) {
            return "ERR INVALID_DIGIT";
        }

        char c = input.charAt(0);

        if (c < '0' || c > '9') {
            return "ERR INVALID_DIGIT";
        }

        int number = c - '0';

        return words[number];
    }
}