package logic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;

public class AddBookmark extends HttpsServlet {
String uname,link,tag,bkname;
public HttpSession sessi;
	/**
	 * Constructor of the object.
	 */
	public AddBookmark() {
		super();
	}
	

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
	    		
		link=request.getParameter("link");
		tag=request.getParameter("tagn");
		bkname=request.getParameter("bookm");
		//System.out.println("link clicked    "+link);
		
		sessi=request.getSession();  
		
		sessi.setAttribute("linkname", link);
		
		 try
	        {
	           
	            RequestDispatcher req = request.getRequestDispatcher("AddBookmark.jsp");
	    	    req.forward(request, response);
	            
	        }
	        catch(Exception pce)
	        {
	            pce.printStackTrace();
	        }
		
		
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
