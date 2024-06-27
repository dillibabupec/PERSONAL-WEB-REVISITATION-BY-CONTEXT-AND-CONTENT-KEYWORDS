package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;
public class SaveTag extends HttpsServlet {
	
	String usrname,tgname,categry,link,accs,datecu,mtgname;
	java.util.Date datec;
	java.sql.Date sqlDate;
	/**
	 * Constructor of the object.
	 */
	public SaveTag() {
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
		
		usrname=request.getParameter("usrn");
		tgname=request.getParameter("tagn");
		categry=request.getParameter("category");
		link=request.getParameter("lnk");
		accs=request.getParameter("acs");
		datecu=request.getParameter("cdate");
		
		if(categry=="music")
		{
			
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		

		try {
			datec = (Date) formatter.parse(datecu);
			sqlDate = new java.sql.Date(datec.getTime());
			System.out.println("Date in savetag"+sqlDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
        	DBServices dbs=new DBServices();
        	if(tgname.contains(","))        
        		mtgname="("+tgname+")";        	
        	else//{
        		mtgname=tgname;
       	
        	if(accs.equals("Public"))
        		
            	dbs.insertTag("general",mtgname,categry,link,accs,sqlDate);
        	else
        		dbs.insertTag(usrname,mtgname,categry,link,accs,sqlDate);
        	//}
        	//
        	
                                
            String s = "Bookmarking Done";
            request.setAttribute("msg", s);
            
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
