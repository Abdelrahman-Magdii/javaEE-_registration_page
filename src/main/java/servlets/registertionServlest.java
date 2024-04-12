package servlets;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/registration")
public class registertionServlest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String uname = request.getParameter("name");
        String upwd = request.getParameter("pass");
        String uemail = request.getParameter("email");
        String umobile = request.getParameter("contact");

        Connection conn = null;
        RequestDispatcher RD = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userDB?useSSL=false", "root", "root");

            java.sql.PreparedStatement ps = conn.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");

            ps.setString(1, uname);
            ps.setString(2, upwd);
            ps.setString(3, uemail);
            ps.setString(4, umobile);

            int rowCount = ps.executeUpdate();

            RD = request.getRequestDispatcher("registration.jsp");

            if (rowCount > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }

            RD.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }

	
	}

}
