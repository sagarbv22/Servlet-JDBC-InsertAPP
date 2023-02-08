package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Insert
 */
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection = null;
	Statement stmt = null;

	@Override
	public void init() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Class is loaded");
			String url = getInitParameter("url");
			String username = getInitParameter("username");
			String password = getInitParameter("password");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection is established...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String address = request.getParameter("address");
		System.out.println(name + " " + age + " " + address);
		String insertQuery = String.format("insert into Student(sname,sage,saddress) values('%s',%d,'%s')", name, age,
				address);
		if (connection != null)
			try {
				stmt = connection.createStatement();

				if (stmt != null) {
					int i = stmt.executeUpdate(insertQuery);

					if (i == 1) {
						out.println("<center>");
						out.println("<h1 style='color:green;'>Student Details Inserted..</h1>");
						out.println("<table border='1'>");
						out.println("<tr><th>NAME</th><th>AGE</th><th>ADDRESS</th></tr>");
						out.println("<tr><td>" + name + "</td><td>" + age + "</td><td>" + address + "</td></tr>");
						out.println("</table>");
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
