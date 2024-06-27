package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DBServices.DBConnection;
import DBServices.DBServices;

public class BookmarkLink extends HttpsServlet {
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
   	    List linkset=new ArrayList();
		List linkwords=new ArrayList();
   		DBServices dbs=new DBServices();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try
		{
		String s=(String)request.getParameter("clickname");
		 if(!s.equalsIgnoreCase(null))
		    {
			System.out.println("clickname : "+s);
			linkset=dbs.selectLinkctgry(s);
			System.out.println("linkURL in BookmarkLink : "+linkset);
			request.setAttribute("select", s);
			request.setAttribute("listlink", linkset);
			RequestDispatcher rd=request.getRequestDispatcher("bookmarklink.jsp");
			rd.forward(request, response);	
		    }
	    }catch(NullPointerException e)
	    {
			String s1=(String)request.getParameter("clickurl");	
		    System.out.println("clickurl : "+s1);
			linkwords=dbs.selectLinkwords(s1);
			System.out.println("linkwords in BookmarkLink : "+linkwords);
			request.setAttribute("listwords", linkwords);
			RequestDispatcher rd=request.getRequestDispatcher("bookmarklink.jsp");
			rd.forward(request, response);	
	    }
	   
	  
		
 }

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
