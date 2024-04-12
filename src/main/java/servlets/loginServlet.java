package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String uemail = request.getParameter("username");
		String upwd = request.getParameter("password"); 
		
		
		HttpSession session =request.getSession();
		RequestDispatcher RD=null;
		Connection conn =null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/userDB?useSSL=false", "root", "root");
			 
			 PreparedStatement ps=conn.prepareStatement("select * from users where uemail=? and upwd=?");
			 
			 ps.setString(1,uemail);
			 ps.setString(2, upwd);
			 
			ResultSet rs =ps.executeQuery();
			
			if(rs.next()) {
				session.setAttribute("name",rs.getString("uname"));
				RD=request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status", "failed");
				RD=request.getRequestDispatcher("login.jsp");
			}
			
			 RD.forward(request, response);
			 
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}

}
