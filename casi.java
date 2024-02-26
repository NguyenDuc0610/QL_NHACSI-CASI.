import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/casi")
public class casi extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public casi() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/duc", "root", "12345678");

            String tableName = req.getParameter("table");
            String searchMCS = req.getParameter("searchMACASI");
            
             out.println("<!DOCTYPE html>");
out.println("<html>");
out.println("<head>");
out.println("<meta charset='UTF-8'>");
out.println("<style>");
out.println("body {display: flex; flex-direction: column; align-items: center; justify-content: center; height: 140vh; margin: 0; margin-bottom: 0;}");
out.println("table {border-collapse: collapse; width: 80%;}");
out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
out.println("th {background-color: #f2f2f2;}");
out.println("nav { background-color: #333; overflow: hidden; }");
out.println("nav a { float: left; display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
out.println("nav a#searchButton { background-color: #4CAF50; }"); // Green color for Tìm Kiếm
out.println("nav a#addButton { background-color: #3498db; }"); // Blue color for Thêm Mới
out.println("nav a:hover { background-color: #ddd; color: black; }");
out.println("</style>");
out.println("<script>");
out.println("function showTable(tableName) {");
out.println("  window.location.href = 'casi?table=' + encodeURIComponent(tableName);");
out.println("}");
out.println("</script>");
out.println("</head>");
out.println("<body>");
out.println("<h2 style='color: #fff; background: linear-gradient(to right, #4CAF50, #336699); padding: 15px; border-radius: 8px; border: 2px solid #336699; text-align: center;'>CA SĨ</h2>");

// Menu
out.println("<nav>");
out.println("<a id='addButton' href='#' onclick='showAddForm()'>Thêm Mới</a>");
out.println("<a id='searchButton' href='#' onclick='showSearchForm()'>Tìm Kiếm</a>");  
out.println("</nav>");
out.println("<body>");

out.println("<div id='searchForm' style='display:none;'>");
out.println("<h2>Tìm Kiếm Thông Tin Ca Sĩ </h2>");
out.println("<form method='get'>");
out.println(" <label for='searchMACASI'>Mã ca sĩ:</label>");
out.println(" <input type='text' id='searchMACASI' name='searchMACASI'>");
out.println(" <input type='submit' value='Tìm Kiếm'>");
out.println("</form>");
out.println("</div>");

out.println("<script>");
out.println("function showSearchForm() {");
out.println("  var searchForm = document.getElementById('searchForm');");
out.println("  searchForm.style.display = 'block';");
out.println("}");
out.println("</script>");


out.println("<div id='addForm' style='display:none;'>");
out.println("<h3>Thêm mới ca sĩ</h3>");
out.println("<form method='post' action='AddCASIServlet'>");
out.println("Mã ca sĩ: <input type='text' name='MACASI'><br>");
out.println("Tên ca sĩ: <input type='text' name='TenCasi'><br>");
out.println("Địa chỉ: <input type='text' name='DiaChi'><br>");
out.println("Số điện thoại: <input type='text' name='SoDienThoai'><br>");
out.println("<input type='submit' value='Thêm Mới'>");
out.println("</form>");
out.println("</div>");

out.println("<script>");
out.println("function showAddForm() {");
out.println("  var addForm = document.getElementById('addForm');");
out.println("  addForm.style.display = 'block';");
out.println("}");
out.println("</script>");
            Statement stmt = null;

            try {
                if (tableName == null || tableName.isEmpty() || tableName.equals("CASI")) {
       
                    stmt = con.createStatement();
                    String sql1 = "SELECT * FROM CASI";
        String sql2 = "SELECT * FROM CASI WHERE MACASI LIKE '%" + searchMCS + "%'";
                    ResultSet rs;
                     if (searchMCS != null && !searchMCS.isEmpty()) {
            rs = stmt.executeQuery(sql2);
        } else {
            rs = stmt.executeQuery(sql1);
        }

                    out.println("<table border=1 width=50% height=50%>");
                    out.println("<tr><th>Mã ca sĩ</th><th>Tên ca sĩ</th><th>Địa chỉ</th><th>Số điện thoại</th></tr>");

                    while (rs.next()) {
                        String MCS = rs.getString("MACASI");
                        String TCS = rs.getString("TenCaSi");
                        String DC = rs.getString("DiaChi");
                        String SDT = rs.getString("SoDienThoai");

                        out.println("<tr><td>" + MCS + "</td><td>" + TCS + "</td><td>" + DC + "</td><td>" + SDT
            + "</td><td><a href='EditCASIRecordServlet?MACASI=" + MCS + "'>Edit</a></td>"
            + "<td><a href='DeleteCASIRecordServlet?MACASI=" + MCS + "'>Delete</a></td></tr>");
                    }
                }
            } finally {
                if (stmt != null) {
                    stmt.close(); // Đóng Statement
                }
                con.close(); // Đóng Connection
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
