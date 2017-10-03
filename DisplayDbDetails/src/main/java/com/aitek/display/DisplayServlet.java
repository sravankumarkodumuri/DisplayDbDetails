package com.aitek.display;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DisplayServlet
 */
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String driver = "org.postgresql.Driver";
		String userName = "postgres";
		String password = "manager";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, userName, password);

			String sqlCommand = "select * from public.employee";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlCommand);
			
			List<Employee> list = new ArrayList<Employee>();
			while(rs.next()){
				
				Employee emp= new Employee();
				emp.setEid(rs.getInt("eid"));
				emp.setEname(rs.getString("ename"));
				emp.setSal(rs.getLong("salary"));
				emp.setAddress(rs.getString("address"));
				list.add(emp);
			}
			
			HttpSession session= request.getSession();
			session.setAttribute("list", list);

			out.println("<table>");
			for (Employee emp : list) {
				out.println("<tr>");
				out.println("<td>" + emp.getEid() + "</td>");
				out.println("<td>" + emp.getEname() + "</td>");
				out.println("<td>" + emp.getSal() + "</td>");
				out.println("<td>" + emp.getAddress() + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			
			//out.println("<br/><a href='/resources/output.csv' download='output.csv'><input type='button' value='export'></a>");

			RequestDispatcher rd= request.getRequestDispatcher("csv");
			rd.include(request, response);
			
			
			
			out.println("<br/><form action='csv' method='get'><input type='submit' name='submit' value='export' download/></form>");
//			request.setAttribute("list", list);
//			RequestDispatcher rd = request.getRequestDispatcher("/searchview.jsp");
//			rd.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
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
