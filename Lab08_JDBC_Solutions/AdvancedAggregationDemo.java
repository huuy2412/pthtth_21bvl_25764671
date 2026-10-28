import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdvancedAggregationDemo {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:nhanvien.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS NhanVien (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "ten TEXT NOT NULL, " +
                                    "chuc_vu TEXT, " +
                                    "luong REAL)";
            stmt.executeUpdate(createTableSQL);

            stmt.executeUpdate("INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES('Nguyễn Văn A', 'Developer', 1500.0)");
            stmt.executeUpdate("INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES('Trần Thị B', 'Tester', 1200.0)");
            stmt.executeUpdate("INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES('Lê Văn C', 'Developer', 1800.0)");
            stmt.executeUpdate("INSERT INTO NhanVien(ten, chuc_vu, luong) VALUES('Phạm Thị D', 'Manager', 2500.0)");

            System.out.println("=== KẾT QUẢ TRUY VẤN NÂNG CAO ===");

            String sqlSumAvg = "SELECT SUM(luong) AS TongLuong, AVG(luong) LuongTB FROM NhanVien";
            try (ResultSet rs = stmt.executeQuery(sqlSumAvg)) {
                if (rs.next()) {
                    System.out.println("Tổng quỹ lương: " + rs.getDouble("TongLuong"));
                    System.out.println("Lương trung bình: " + rs.getDouble("LuongTB"));
                }
            }

            String sqlMinMax = "SELECT MAX(luong) AS MaxLuong, MIN(luong) AS MinLuong FROM NhanVien";
            try (ResultSet rs = stmt.executeQuery(sqlMinMax)) {
                if (rs.next()) {
                    System.out.println("Lương cao nhất: " + rs.getDouble("MaxLuong"));
                    System.out.println("Lương thấp nhất: " + rs.getDouble("MinLuong"));
                }
            }

            String sqlGroupBy = "SELECT chuc_vu, COUNT(*) AS SoLuongNV FROM NhanVien GROUP BY chuc_vu";
            try (ResultSet rs = stmt.executeQuery(sqlGroupBy)) {
                System.out.println("\nThống kê số lượng nhân viên theo chức vụ:");
                while (rs.next()) {
                    System.out.println("- Chức vụ: " + rs.getString("chuc_vu") + " | Số lượng: " + rs.getInt("SoLuongNV"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn tổng hợp: " + e.getMessage());
        }
    }
}
