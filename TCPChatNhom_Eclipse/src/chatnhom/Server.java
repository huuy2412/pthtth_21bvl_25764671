
package chatnhom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

	public static final int PORT = 5000;
	public static final String[] ROOMS = { "Study", "Game", "TraiNghiem" };

	private static final List<ClientHandler> CLIENTS = new CopyOnWriteArrayList<>();

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			System.out.println("SERVER DANG CHAY - PORT " + PORT);
			System.out.println("Dang cho Client ket noi...");

			while (true) {

				Socket socket = serverSocket.accept();

				// Tao doi tuong Thread rieng cho moi Client
				ClientHandler client = new ClientHandler(socket);
				CLIENTS.add(client);

				// Bat dau Thread
				client.start();
			}

		} catch (IOException e) {
			System.out.println("Loi Server: " + e.getMessage());
		}
	}

	// Gui tin nhan cho cac Client trong cung phong
	static void guiTrongPhong(String phong, String tinNhan) {

		for (ClientHandler client : CLIENTS) {

			if (phong.equals(client.getPhong())) {
				client.gui(tinNhan);
			}
		}
	}

	// Xoa Client khi ngat ket noi
	static void xoaClient(ClientHandler client) {
		CLIENTS.remove(client);
	}
}
