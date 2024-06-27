package logic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;

public class Register extends HttpsServlet {
	String ui,uname,d,o,b,gender,pw,rpw,contact,email,ad;
	/**
	 * Constructor of the object.
	 */
	public Register() {
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
		
	       
		//Get user input from Register.jsp
		        
		         ui = request.getParameter("ui");
		         uname = request.getParameter("uname");
		         d = request.getParameter("d");
		         o = request.getParameter("m");	
		         b = request.getParameter("y");       
		         gender = request.getParameter("gen");
		         pw = request.getParameter("pw");
		         rpw = request.getParameter("rpw");
		         contact = request.getParameter("con");
		         email = request.getParameter("e");       
		         ad = request.getParameter("ad");
		                
		        System.out.println("uname....."+uname);      
		        System.out.println("date......"+d);
		        System.out.println("o....."+o);
		        System.out.println("b.........."+b);
		        String dob = (d+"/"+o+"/"+b);
		       
		        System.out.println("gender....."+gender);
		        System.out.println("pw....."+pw);
		        System.out.println("Contact...."+contact);
		        System.out.println("email...."+email);
		        System.out.println("ADD....."+ad);
		                   
		     
		        try
		        {
		           
		//insert user details to register table in db            	
		        	DBServices dbs=new DBServices();
			    	dbs.insertRegis(ui, uname, dob, gender, pw, rpw, contact, email, ad);              
		                                
		            String s = "Your Registration Completed..........";
		            request.setAttribute("sta", s);
		            RequestDispatcher req = request.getRequestDispatcher("Register.jsp");
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
