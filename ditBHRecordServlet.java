import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/EditBHRecordServlet")
public class EditBHRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu bài hát</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String MBH = request.getParameter("MABAIHAT");
        
        out.println("<h2>Form Sửa Dữ Liệu bài hát</h2>");
        out.println("<form action='EditBHRecordServlet' method='post'>");
       
        out.println("  <input type='hidden' name='MABAIHAT' value='" + MBH+ "'/>");
        
        out.println("  <label for='TenBaiHat'>Tên bài hát:</label>");
        out.println("  <input type='text' id='TenBaiHat' name='TenBaiHat' required><br>");

        out.println("  <label for='GiaiDieu'>Giai điệu:</label>"); 
        out.println("  <input type='text' id='GiaiDieu' name='GiaiDieu' required><br>");

        out.println("  <label for='MANHACSI'>Mã nhạc sĩ:</label>");
        out.println("  <input type='text' id='MANHACSI' name='MANHACSI' required><br>");  

        out.println("  <label for='GhiChu'>Ghi chú:</label>"); 
        out.println("  <input type='text' id='GhiChu' name='GhiChu' required><br><br>");
        out.println("  <input type='submit' value='Lưu'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
     String MBH = request.getParameter("MABAIHAT");
    String TBH = request.getParameter("TenBaiHat");
    String GD = request.getParameter("GiaiDieu");
    String MNS = request.getParameter("MANHACSI");
        String GC = request.getParameter("GhiChu");

    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi tải driver MySQL: " + e.getMessage());
            return;
        }
    // Kết nối đến cơ sở dữ liệu
        String jdbcUrl = "jdbc:mysql://localhost:3306/duc";
        String username = "root";
        String password = "12345678";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

    // Câu lệnh SQL update
    String sql = "UPDATE BAIHAT SET TenBaiHat = ?, GiaiDieu = ?, MANHACSI = ?, GhiChu = ? " + 
                "WHERE MABAIHAT = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    
    statement.setString(1, TBH);
    statement.setString(2, GD); 
    statement.setString(3,MNS);
    statement.setString(4, GC);
        statement.setString(5, MBH);


    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy bài hát!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}
