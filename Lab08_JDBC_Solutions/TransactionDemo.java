import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDemo {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sanpham.db";
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

            String sql1 = "UPDATE SanPham SET gia = gia + 50 WHERE maSP = ?";
            try (PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
                pstmt1.setString(1, "SP01");
                pstmt1.executeUpdate();
            }

            String sql2 = "UPDATE SanPham SET soluong = soluong - 5 WHERE maSP = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                pstmt2.setString(1, "SP02");
                pstmt2.executeUpdate();
            }

            conn.commit();
            System.out.println("Giao dịch (Transaction) thực hiện thành công và đã được Commit!");

        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi, tiến hành Rollback giao dịch: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
