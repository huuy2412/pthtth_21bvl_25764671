import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProductManagement {
    private static final String URL = "jdbc:sqlite:sanpham.db";

    public static void main(String[] args) {
        initDatabase();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== QUẢN LÝ DANH MỤC SẢN PHẨM ===");
            System.out.println("1. Thêm sản phẩm mới (Create)");
            System.out.println("2. Liệt kê / Tìm kiếm sản phẩm (Read)");
            System.out.println("3. Cập nhật giá/số lượng sản phẩm (Update)");
            System.out.println("4. Xóa sản phẩm (Delete)");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 5) break;

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    displayOrSearchProducts(scanner);
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        scanner.close();
    }

    private static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS SanPham (" +
                     "maSP TEXT PRIMARY KEY, " +
                     "tenSP TEXT NOT NULL, " +
                     "gia REAL, " +
                     "soluong INTEGER)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Đã khởi tạo cơ sở dữ liệu và bảng SanPham thành công.");
        } catch (SQLException e) {
            System.out.println("Lỗi khởi tạo DB: " + e.getMessage());
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm: ");
        String maSP = scanner.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        String tenSP = scanner.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        double gia = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập số lượng: ");
        int soluong = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO SanPham(maSP, tenSP, gia, soluong) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maSP);
            pstmt.setString(2, tenSP);
            pstmt.setDouble(3, gia);
            pstmt.setInt(4, soluong);
            pstmt.executeUpdate();
            System.out.println("Thêm sản phẩm thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi thêm sản phẩm (Có thể trùng mã SP): " + e.getMessage());
        }
    }

    private static void displayOrSearchProducts(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm cần tìm (để trống nếu muốn xem tất cả): ");
        String keyword = scanner.nextLine();

        String sql = "SELECT * FROM SanPham";
        if (!keyword.trim().isEmpty()) {
            sql += " WHERE tenSP LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (!keyword.trim().isEmpty()) {
                pstmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
            System.out.printf("%-10s | %-20s | %-10s | %-10s\n", "Mã SP", "Tên SP", "Giá", "Số lượng");
            System.out.println("---------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10s | %-20s | %-10.2f | %-10d\n",
                        rs.getString("maSP"),
                        rs.getString("tenSP"),
                        rs.getDouble("gia"),
                        rs.getInt("soluong"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        }
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm cần cập nhật: ");
        String maSP = scanner.nextLine();

        System.out.print("Nhập giá mới: ");
        double gia = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập số lượng mới: ");
        int soluong = Integer.parseInt(scanner.nextLine());

        String sql = "UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, gia);
            pstmt.setInt(2, soluong);
            pstmt.setString(3, maSP);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy mã sản phẩm cần cập nhật.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật: " + e.getMessage());
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String maSP = scanner.nextLine();

        String sql = "DELETE FROM SanPham WHERE maSP = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maSP);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Xóa sản phẩm thành công!");
            } else {
                System.out.println("Không tìm thấy sản phẩm để xóa.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa: " + e.getMessage());
        }
    }
}
