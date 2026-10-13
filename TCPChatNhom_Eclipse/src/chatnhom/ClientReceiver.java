
package chatnhom;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver extends Thread {

	private final BufferedReader in;

	public ClientReceiver(BufferedReader in) {
		this.in = in;
	}

	@Override
	public void run() {

		try {
			String tinNhan;

			// Lien tuc nhan tin tu Server
			while ((tinNhan = in.readLine()) != null) {
				System.out.println(tinNhan);
			}

		} catch (IOException e) {
			// Dung nhan khi Socket dong
		}
	}
}
