package Lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ThucHanh4 {
    public static void main(String[] args) {
        Path input = Path.of("data", "products.csv");
        Path report = Path.of("data", "report.txt");
        List<Product> products = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            reader.readLine();
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.out.println("Bỏ qua dòng " + lineNumber);
                    continue;
                }
                try {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int quantity = Integer.parseInt(parts[3].trim());
                    Product product = new Product(
                            code,
                            name,
                            price,
                            quantity);
                    products.add(product);
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Dòng "
                                    + lineNumber
                                    + " không hợp lệ: "
                                    + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Không đọc được CSV: " + e.getMessage());
            return;
        }
        double total = 0;
        System.out.println(
                "===== DANH SÁCH SẢN PHẨM =====");
        for (Product product : products) {
            System.out.println(product);
            total += product.inventoryValue();
        }
        System.out.printf("Tổng giá trị tồn kho: %,.0f VND%n", total);
        try (
                BufferedWriter writer = Files.newBufferedWriter(report, StandardCharsets.UTF_8)) {
            writer.write("Số sản phẩm: " + products.size());
            writer.newLine();
            writer.write(
                    String.format("Tổng giá trị tồn kho: %,.0f VND", total));
            writer.newLine();
            System.out.println("Đã tạo file report.txt");
        } catch (IOException e) {
            System.out.println("Không ghi được báo cáo: " + e.getMessage());
        }
    }

    static class Product {
        private final String code;
        private final String name;
        private final double unitPrice;
        private final int quantity;

        public Product(
                String code,
                String name,
                double unitPrice,
                int quantity) {
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("Mã không được rỗng");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Tên không được rỗng");
            }
            if (unitPrice <= 0 || quantity < 0) {
                throw new IllegalArgumentException("Giá hoặc số lượng không hợp lệ");
            }
            this.code = code;
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public double inventoryValue() {
            return unitPrice * quantity;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s - %s: %,.0f VND",
                    code,
                    name,
                    inventoryValue());
        }
    }
}
