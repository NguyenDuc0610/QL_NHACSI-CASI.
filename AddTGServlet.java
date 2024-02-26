import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddTGServlet")
public class AddTGServlet    extends HttpServlet {
    // Servlet code


    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String MCS = request.getParameter("MACASI");
String MBH = request.getParameter("MABAIHAT");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/duc", "root", "12345678");

            String sql = "INSERT INTO THOIGIAN (MACASI, MABAIHAT) " + 
             "VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {

 // Set the values in the correct order
pstmt.setString(1, MCS);
pstmt.setString(2, MBH); 

pstmt.executeUpdate();


  pstmt.executeUpdate();

}

            con.close();

            // Use a context-relative URL for redirection
            response.sendRedirect(request.getContextPath() + "/Xem?table=qua_trinh_di_chuyen");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}
