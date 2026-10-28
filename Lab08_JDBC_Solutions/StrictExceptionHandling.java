import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StrictExceptionHandling {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sanpham.db";

        String sql = "SELECT * FROM SanPham";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Danh sách sản phẩm (Xử lý ngoại lệ nghiêm ngặt):");
            while (rs.next()) {
                System.out.println("- " + rs.getString("tenSP") + " | Giá: " + rs.getDouble("gia"));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
