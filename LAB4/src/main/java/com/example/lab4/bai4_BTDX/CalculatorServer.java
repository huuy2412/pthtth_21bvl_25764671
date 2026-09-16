package com.example.lab4.bai4_BTDX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalculatorServer {

    private static final int PORT = 5000;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Calculator Server listening on port " + PORT);
            while (true) {
                try (Socket socket = server.accept()) {
                    serve(socket);
                } catch (IOException e) {
                    System.err.println("Loi Client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Khong mo duoc Server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));

            PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8),
                true
            )
        ) {

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("QUIT")) {out.println("OK BYE");
                    break;
                }

                String response = calculate(request);
                out.println(response);
            }
        }
    }

    static String calculate(String request) {
        String[] parts = request.trim().split("\\s+");

        if (parts.length != 4 ||
            !parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";
        }

        String operator = parts[1];
        if (!operator.equals("+") &&
            !operator.equals("-") &&
            !operator.equals("*") &&
            !operator.equals("/")) {
            return "ERR UNSUPPORTED_OPERATOR";
        }

        try {
            BigDecimal a = new BigDecimal(parts[2]);
            BigDecimal b = new BigDecimal(parts[3]);
            BigDecimal result;
            switch (operator) {
                case "+":
                    result = a.add(b);
                    break;
                case "-":
                    result = a.subtract(b);
                    break;
                case "*":
                    result = a.multiply(b);
                    break;
                case "/":

                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        return "ERR DIVIDE_BY_ZERO";
                    }
                    result = a.divide(b, MathContext.DECIMAL64);
                    break;
                default:
                    return "ERR UNSUPPORTED_OPERATOR";
            }
            return "OK " +
                result.stripTrailingZeros().toPlainString();
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";
        }
    }
}