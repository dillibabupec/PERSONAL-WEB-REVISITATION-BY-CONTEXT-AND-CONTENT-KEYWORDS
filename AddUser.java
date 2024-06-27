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

public class AddUser extends HttpsServlet {

	/**
	 * Constructor of the object.
	 */
	String adname,child,uname,pwd;
	public HttpSession sessi;
	public AddUser() {
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
		sessi=request.getSession();  
		adname=(String)sessi.getAttribute("username");
		
		uname=request.getParameter("usern");
		pwd=request.getParameter("passw");
		
		 try
	        {
	           
	//insert user details to register table in db            	
	        	DBServices dbs=new DBServices();
		    	dbs.insertUser(adname,uname, pwd);              
	                                
	            String s = "User added successfully";
	            request.setAttribute("msg1", s);
	            RequestDispatcher req = request.getRequestDispatcher("Profile.jsp");
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
