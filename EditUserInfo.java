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

public class EditUserInfo extends HttpsServlet {

	/**
	 * Constructor of the object.
	 */
	public EditUserInfo() {
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
		String ui = request.getParameter("uid");
        String uname = request.getParameter("uname");

        String dob = request.getParameter("birthd");

        String gender = request.getParameter("gen");
        String pw = request.getParameter("pw");
        String rpw = request.getParameter("rpw");
        String contact = request.getParameter("con");
        String email = request.getParameter("e");       
        String ad = request.getParameter("ad");
                
        //System.out.println("uname....."+uname);   
        //System.out.println("gender....."+gender);
        //System.out.println("pw....."+pw);
        //System.out.println("Contact...."+contact);
        //System.out.println("email...."+email);
        //System.out.println("ADDRE....."+ad);
        
        try
        {
        	DBServices dbs=new DBServices();
	    	boolean check=dbs.updateRegis(ui, uname, dob, gender, pw, rpw, contact, email, ad);
	    
	    	
	    	if(check)
    		{
	    	String val=dbs.selectRegis( uname, pw);
	    	String[] parts = val.split("!");
	    	
	    	request.getSession().setAttribute("userid",parts[0] );
        	request.getSession().setAttribute("username",parts[1] );
        		 		
        	request.getSession().setAttribute("birthd",parts[2]);
        		
        	request.getSession().setAttribute("gend",parts[3]);
        	request.getSession().setAttribute("pwd",parts[4]);
        
        	request.getSession().setAttribute("rpwd",parts[5]);
        	request.getSession().setAttribute("contact",parts[6]);
        	request.getSession().setAttribute("mail",parts[7]);
        	
        	
        	
        	request.getSession().setAttribute("address",parts[8]);
        	
     	
            String s = "Your profile updated..........";
            request.setAttribute("sta", s);
            RequestDispatcher req = request.getRequestDispatcher("UserPage.jsp");
            req.forward(request, response);
    		}
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
