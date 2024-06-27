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

public class Login extends HttpsServlet {
	String user,pass;
	public HttpSession sessi;
	/**
	 * Constructor of the object.
	 */
	public Login() {
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
		user = request.getParameter("user");
        pass = request.getParameter("pass");
        
        try
        {
        		DBServices db=new DBServices();
        		boolean check=db.testLogin(user, pass);
        		if(check)
        		{
        			
        			DBServices dbs=new DBServices();
        			String dbval=dbs.selectRegis(user, pass);
            
        			String[] parts = dbval.split("!");  
                                         
        			//System.out.println("parts"+parts[0]);
            

        			sessi=request.getSession();  
       			    sessi.setAttribute("userid",parts[0] );
       			    sessi.setAttribute("username",parts[1] );           		 		
        			sessi.setAttribute("birthd",parts[2]);            		
        			sessi.setAttribute("gend",parts[3]);
        			sessi.setAttribute("pwd",parts[4]);           
        			sessi.setAttribute("rpwd",parts[5]);
        			sessi.setAttribute("contact",parts[6]);
        			sessi.setAttribute("mail",parts[7]);
         			sessi.setAttribute("address",parts[8]);
        			
        		  RequestDispatcher req1 = request.getRequestDispatcher("Profile.jsp");
                  req1.forward(request, response);
        			
            }
            
           
            else
            {
            	System.out.println("Invalid user");
            	String s = "Invalid username or password";
                request.setAttribute("inf", s);
            	RequestDispatcher req = request.getRequestDispatcher("index.jsp");
                req.forward(request, response);
            		 
            }
            		
            	      	
                    
        }
        catch(Exception x)
        {
            System.out.println(x);
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
