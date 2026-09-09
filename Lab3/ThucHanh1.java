package Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ThucHanh1 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        int count = 0;
        System.out.println("nhap van ban, nhap q để ket thuc: ");
        try {
            while (true) {
                String line = br.readLine();
                if (line == null || line.equalsIgnoreCase("q")) {
                    break;
                }
                count++;
                System.out.println("So dong da nhap: " + count + " - Noi dung: " + line);
            }
        } catch (IOException e) {
            System.out.println("Loi doc du lieu : " + e.getMessage());
        }
        System.out.println("Tong so dong da nhap: " + count);
    }

}
