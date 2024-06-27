package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;

public class TagRetrieve extends HttpsServlet {
	String catgr;
	List alltag;
	Set tgnms;
	String tg="";
	/**
	 * Constructor of the object.
	 */
	public TagRetrieve() {
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
		PrintWriter out = response.getWriter();
		//response.setContentType("text/html");
	    catgr=request.getParameter("catg");
	    //System.out.println("catgry"+catgr);
	    
	    DBServices dbs=new DBServices();
	    alltag=dbs.retrieveTag(catgr);
	    //System.out.println("alltag"+alltag);
	    
	    
	    tgnms=new HashSet();
	    
	    for(int i=0;i<alltag.size();i++)
	    {
	    	String tgname=(String)alltag.get(i);
	    	//System.out.println(tgname);
	    	//tgnms.clear();
	    	if(tgname.contains("("))
	    	{
	    		StringTokenizer st=new StringTokenizer(tgname,"( , )");
    			
    	    	while (st.hasMoreElements()) {
    				
    				String tgn=(String)st.nextElement();
    				//System.out.println("split"+tgn);
    				
    				tgnms.add(tgn);	    				
    			
    			}
	    	}
	    	else
	    	{
	    		tgnms.add(tgname);	
	    	}
	    }
	    //System.out.println("set "+tgnms);
	    Iterator tagval=tgnms.iterator();
	    while(tagval.hasNext())
	    {
	    	String tgva=(String)tagval.next();
	    	tg=tg+"*"+tgva;
	    	
	    }
	    //System.out.println("tgnm"+tg);
	    out.write(tg);
	    //System.out.println("tags in tag retrieve"+tg);
	    tg="";
	    //out.println(tg);
	    
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
