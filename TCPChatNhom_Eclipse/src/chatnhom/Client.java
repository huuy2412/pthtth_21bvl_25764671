
package chatnhom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		try (Socket socket = new Socket("127.0.0.1", Server.PORT);

				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

			System.out.println("Da ket noi Server 127.0.0.1:" + Server.PORT);

			// Su dung class Thread da tach rieng
			ClientReceiver receiver = new ClientReceiver(in);

			receiver.setDaemon(true);
			receiver.start();

			// Nhap du lieu tu ban phim
			Scanner banPhim = new Scanner(System.in, StandardCharsets.UTF_8);

			while (banPhim.hasNextLine()) {

				String nhap = banPhim.nextLine();

				out.println(nhap);

				if (nhap.trim().equalsIgnoreCase("/exit")) {
					break;
				}
			}

			System.out.println("Client da thoat.");

		} catch (IOException e) {
			System.out.println("Khong the ket noi: " + e.getMessage());
		}
	}
}
