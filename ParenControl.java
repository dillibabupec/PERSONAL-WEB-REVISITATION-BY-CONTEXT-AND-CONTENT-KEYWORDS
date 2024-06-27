package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;
public class ParenControl extends HttpsServlet {
String uname,catg,tagn,tagnm,link,datecu;
java.util.Date datec;
java.sql.Date sqlDate;
List all,linkn,tgname;
/**
	 * Constructor of the object.
	 */
	public ParenControl() {
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
		HttpSession session=request.getSession();
		response.setContentType("text/html");
		all=new ArrayList();		
		linkn=new ArrayList();     
 	    tgname=new ArrayList();
 	    
		uname=request.getParameter("usrs");
		catg=request.getParameter("categry");
		tagn=request.getParameter("mtags");
		datecu=request.getParameter("cdate");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			datec = (Date) formatter.parse(datecu);
			sqlDate = new java.sql.Date(datec.getTime());
			System.out.println("Date "+sqlDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DBServices dbs=new DBServices();
		all=dbs.selectAll();
		
		int ind=all.indexOf("*");
	    int en = all.size();
	   
	    linkn.addAll(all.subList(0, ind));   	
	    tgname.addAll(all.subList(ind+1, en));
	    
		
		StringTokenizer st=new StringTokenizer(tagn,"*");
		while(st.hasMoreTokens())
		{
			tagnm=st.nextToken();
			System.out.println("st"+tagnm);
			
			for(int i=0;i<tgname.size();i++)
			{
			
				String tagname=(String)tgname.get(i);
//				System.out.println();
//				System.out.println("alltags======>"+tagname);
//				System.out.println();
//				System.out.println();
				if(tagname.contains(tagnm))
				{
					//System.out.println(" found");
					String linkname=(String)linkn.get(i);
					dbs.insertUserTags(uname, catg, tagnm,linkname);
					
					String s = "Success";
					request.setAttribute("msg", s);
				}
				else
				{
					System.out.println("");
				}
				
			}
			
			//link=(String)dbs.selectLink(tagnm);
			//dbs.insertUserTags(uname, catg, tagn,link);
			//dbs.insertTag(uname, tagnm, catg, link,"Public",sqlDate);
		}
		
		
		
		
		//DBServices dbs1=new DBServices();
		//dbs1.insertTag(uname, tagn, catg, link,"Private", dtec);
		
		
        
		RequestDispatcher req = request.getRequestDispatcher("Parental.jsp");
	    req.forward(request, response);
		
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
