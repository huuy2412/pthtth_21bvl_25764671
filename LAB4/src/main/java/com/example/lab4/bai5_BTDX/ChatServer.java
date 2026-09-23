package com.example.lab4.bai5_BTDX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private static final int PORT = 5000;
    // Danh sách client thread-safe
    private static final ConcurrentHashMap<String, ClientHandler> clients
            = new ConcurrentHashMap<>();
    // Thread pool
    private static final ExecutorService pool
            = Executors.newFixedThreadPool(20);
    public static void main(String[] args) {
        System.out.println("Chat Server đang chạy tại port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client kết nối: "+ socket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private String nickname;
        private BufferedReader reader;
        private PrintWriter writer;
        public ClientHandler(Socket socket) {this.socket = socket;}
        @Override
        public void run() {
            try {
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8)
                );
                writer = new PrintWriter(socket.getOutputStream(),true,StandardCharsets.UTF_8);
                chooseNickname();
                if (nickname == null) {
                    return;
                }
                writer.println("OK. Xin chào " + nickname);
                String command;
                while ((command = reader.readLine()) != null) {
                    command = command.trim();
                    if (command.equalsIgnoreCase("USERS")) {
                        showUsers();
                    } else if (command.toUpperCase().startsWith("MSG ")) {
                        String message = command.substring(4).trim();
                        if (message.isEmpty()) {
                            writer.println("ERROR: Nội dung tin nhắn không được rỗng");
                        } else {
                            broadcast(message);
                        }
                    } else if (command.equalsIgnoreCase("QUIT")) {
                        writer.println("BYE");
                        break;
                    } else {
                        writer.println("ERROR: Lệnh hợp lệ: USERS, MSG noi_dung, QUIT");
                    }
                }
            } catch (IOException e) {
                System.out.println("Client mất kết nối: "+ socket.getRemoteSocketAddress());
            } finally {
                removeClient();
            }
        }
        private void chooseNickname() throws IOException {
            while (true) {
                writer.println("NICK?");
                String name = reader.readLine();
                if (name == null) {
                    return;
                }
                name = name.trim();
                if (name.isEmpty()) {
                    writer.println("ERROR: Nickname không được rỗng");
                    continue;
                }
                if (name.contains(" ")) {
                    writer.println("ERROR: Nickname không được chứa khoảng trắng");
                    continue;
                }
                // putIfAbsent giúp tránh 2 client chọn cùng tên
                ClientHandler oldClient
                        = clients.putIfAbsent(name, this);
                if (oldClient == null) {
                    nickname = name;
                    break;
                } else {
                    writer.println("ERROR: Nickname đã tồn tại");
                }
            }
        }
        private void showUsers() {
            writer.println(
                    "USERS: "
                    + String.join(", ", clients.keySet())
            );
        }
        private void broadcast(String message) {
            String result= "[" + nickname + "]: " + message;
            for (ClientHandler client : clients.values()) {
                // Không gửi lại cho chính người gửi
                if (client != this) {
                    client.writer.println(result);
                }
            }
        }
        private void removeClient() {
            if (nickname != null) {
                clients.remove(nickname, this);
                System.out.println(nickname + " đã rời server");
            }
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}