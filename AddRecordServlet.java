import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddRecordServlet")
public class AddRecordServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String MNS = request.getParameter("MANHACSI");
        String TNS = request.getParameter("TenNhacSi");
        String DC = request.getParameter("DiaChi");
        String SDT = request.getParameter("SoDienThoai");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/duc", "root", "12345678");

            String sql = "INSERT INTO NHACSI (MANHACSI, TenNhacSi, DiaChi, SoDienThoai) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, MNS);
            pstmt.setString(2, TNS);
            pstmt.setString(3, DC);
            pstmt.setString(4, SDT);

            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            response.sendRedirect("nhacsi?table=NHACSI"); // Chuyển về trang danh sách sau khi thêm
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
