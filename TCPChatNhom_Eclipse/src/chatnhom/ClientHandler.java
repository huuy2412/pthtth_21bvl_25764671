
package chatnhom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {

	private final Socket socket;
	private PrintWriter out;
	private String ten;
	private volatile String phong;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	public String getPhong() {
		return phong;
	}

	// Gui thong bao ve Client
	public void gui(String tinNhan) {
		if (out != null) {
			out.println(tinNhan);
		}
	}

	// Hien thi danh sach phong
	private void hienPhong() {

		gui("===== DANH SACH PHONG =====");

		for (int i = 0; i < Server.ROOMS.length; i++) {
			gui((i + 1) + ". " + Server.ROOMS[i]);
		}

		gui("Nhap so phong (1-3), hoac /exit:");
	}

	@Override
	public void run() {

		try (Socket s = socket;
				BufferedReader in = new BufferedReader(
						new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));

				PrintWriter writer = new PrintWriter(
						new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

			out = writer;

			// Yeu cau nhap ten
			gui("Nhap ten cua ban (hoac /exit):");

			String nhap;

			while ((nhap = in.readLine()) != null) {

				nhap = nhap.trim();

				if (nhap.equalsIgnoreCase("/exit")) {
					gui("Tam biet!");
					return;
				}

				if (!nhap.isEmpty()) {
					ten = nhap;
					break;
				}

				gui("Ten khong duoc de trong. Nhap lai:");
			}

			if (ten == null)
				return;

			System.out.println(ten + " da ket noi: " + socket.getRemoteSocketAddress());

			gui("Xin chao " + ten + "!");
			hienPhong();

			// Xu ly tin nhan Client gui len
			while ((nhap = in.readLine()) != null) {

				nhap = nhap.trim();

				// Thoat khoi Server
				if (nhap.equalsIgnoreCase("/exit")) {
					gui("Da ngat ket noi Server.");
					break;
				}

				// Neu chua chon phong
				if (phong == null) {

					int soPhong;

					try {
						soPhong = Integer.parseInt(nhap);
					} catch (NumberFormatException e) {
						soPhong = -1;
					}

					if (soPhong >= 1 && soPhong <= Server.ROOMS.length) {

						phong = Server.ROOMS[soPhong - 1];

						gui("Ban da vao phong " + phong + ".");
						gui("Nhap tin nhan, /quit de roi phong, " + "/exit de thoat.");

						Server.guiTrongPhong(phong, "[He thong] " + ten + " da vao phong.");

					} else {
						gui("Chon phong khong hop le.");
						hienPhong();
					}

					// Roi phong va quay lai danh sach
				} else if (nhap.equalsIgnoreCase("/quit")) {

					String phongCu = phong;
					phong = null;

					Server.guiTrongPhong(phongCu, "[He thong] " + ten + " da roi phong.");

					gui("Ban da roi phong " + phongCu + ".");
					hienPhong();

					// Gui tin cho nhung nguoi cung phong
				} else if (!nhap.isEmpty()) {

					Server.guiTrongPhong(phong, "[" + phong + "] " + ten + ": " + nhap);
				}
			}

		} catch (IOException e) {
			System.out.println("Client bi loi: " + e.getMessage());

		} finally {

			String phongCu = phong;
			phong = null;

			Server.xoaClient(this);

			if (ten != null) {

				if (phongCu != null) {
					Server.guiTrongPhong(phongCu, "[He thong] " + ten + " da ngat ket noi.");
				}

				System.out.println(ten + " da ngat ket noi.");
			}
		}
	}
}
