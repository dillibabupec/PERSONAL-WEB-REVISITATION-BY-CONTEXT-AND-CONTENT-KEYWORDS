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


public class SearchFile extends HttpsServlet {
String val,uurl,res;
	/**
	 * Constructor of the object.
	 */
	public SearchFile() {
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
		
		val=request.getParameter("query");

		DBServices dbs=new DBServices();
		uurl=dbs.selectFile(val);
		//System.out.println("url==========="+uurl);
		if(uurl.isEmpty())
		{
			res="No search results found";
			request.setAttribute("Result", res);
			request.setAttribute("urlfname", uurl);
		}
		else
		{
			
			res="Result found";
			request.setAttribute("Result", res);
			request.setAttribute("urlfname", uurl);
		}
	
		
		RequestDispatcher rd =request.getRequestDispatcher("multimediaSearch.jsp");
		rd.forward(request, response);
		
		
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
