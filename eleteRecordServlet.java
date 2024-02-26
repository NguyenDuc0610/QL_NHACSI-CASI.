import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteRecordServlet")
public class DeleteRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/duc", "root", "12345678");

            String MANHACSI = request.getParameter("MANHACSI");

            if (MANHACSI != null && !MANHACSI.isEmpty()) {


                String sql = "DELETE FROM NHACSI WHERE MANHACSI = ?";
                
              
                PreparedStatement statement = con.prepareStatement(sql);
                
              
                statement.setString(1, MANHACSI);
                
            
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    out.println("Xóa nhạc sĩ thành công!");
                } else {
                    out.println("Không tìm thấy nhạc số " + MANHACSI + " để xóa.");
                }

            } else {
                out.println("Lỗi: Nhạc sĩ cần xóa không được cung cấp."); 
            }

            con.close();
            
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
