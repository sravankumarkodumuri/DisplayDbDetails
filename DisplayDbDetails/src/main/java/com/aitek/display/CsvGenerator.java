package com.aitek.display;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CsvGenerator
 */
public class CsvGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CsvGenerator() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"output.csv\"");
		
		HttpSession session= request.getSession();
		@SuppressWarnings("unchecked")
		ArrayList<Employee> list = (ArrayList<Employee>) session.getAttribute("list");
		StringBuffer sb = new StringBuffer();
		for (Employee emp : list) {
			sb.append(emp.getEid());
			sb.append("|");
			sb.append(emp.getEname());
			sb.append("|");
			sb.append(emp.getSal());
			sb.append("|");
			sb.append(emp.getAddress());
			sb.append("\n");
		}
		String ssb = sb.toString();		
		
	    try
	    {
	        OutputStream outputStream = response.getOutputStream();
	        outputStream.write(ssb.getBytes());
	        outputStream.flush();
	        outputStream.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
}
