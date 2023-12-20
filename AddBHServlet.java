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

@WebServlet("/AddBHServlet")
public class AddBHServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String MBH = request.getParameter("MABAIHAT");
        String TBH = request.getParameter("TenBaiHat");
        String GD = request.getParameter("GiaiDieu");
        String MNS = request.getParameter("MANHACSI");
        String GC = request.getParameter("GhiChu");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/duc", "root", "12345678");

            String sql = "INSERT INTO BAIHAT (MABAIHAT, TenBaiHat, GiaiDieu, MANHACSI, GhiChu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, MBH);
            pstmt.setString(2, TBH);
            pstmt.setString(3, GD);
            pstmt.setString(4, MNS);
            pstmt.setString(5, GC);

            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            response.sendRedirect("baihat?table=BAIHAT"); // Chuyển về trang danh sách sau khi thêm
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
