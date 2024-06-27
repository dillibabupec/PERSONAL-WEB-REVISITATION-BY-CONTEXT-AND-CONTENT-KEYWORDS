package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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

public class FacetSearch extends HttpsServlet {

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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		List<String> ctgrylst=new ArrayList<String>();
		DBServices dbs=new DBServices();
		List DummyCategry=new ArrayList();
		
		DummyCategry=dbs.category();
		System.out.println("DummyCategry : "+DummyCategry);
		
		String login=(String)session.getAttribute("username");
		System.out.println("Login : "+login);
		
		ctgrylst=dbs.blockFacets(login);
		System.out.println("Blocked catetgory : "+ctgrylst);

     		System.out.println("inside else");
     		 Iterator itr=ctgrylst.iterator();
	 	        while(itr.hasNext())
	 	        {
 	            String s=(String) itr.next();
 	                    
 	             for(int i=0;i<DummyCategry.size();i++)
 	             {
 	                        String s1=(String) DummyCategry.get(i);
 	                        if(s.equalsIgnoreCase(s1))
 	                        {
 	                        	DummyCategry.remove(s1);
 	                        }
 	              }
 	        }
 	        System.out.println("ctgry Last : "+DummyCategry);
 	        System.out.println("Blokctgry Last : "+ctgrylst);
 	        
 		    request.setAttribute("Dummy", DummyCategry);    
 			RequestDispatcher rd=request.getRequestDispatcher("facetSearch.jsp");
 			rd.forward(request, response);	
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
		String tgn="";
		List settag=new ArrayList();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DBServices dbs=new DBServices();
		String c=request.getParameter("category");
		System.out.println("c: "+c);
		
		String tags=dbs.selectTag(c);
		System.out.println("tags from db : "+tags);
		
		StringTokenizer stg=new StringTokenizer(tags,"*");
    	while(stg.hasMoreElements()){
    	String tagnm=stg.nextToken().toString();
    		
    		if(tagnm.contains("("))
    		{
    		StringTokenizer st=new StringTokenizer(tagnm,"( , )");
    		while (st.hasMoreElements()) {
				 tgn=(String)st.nextElement();						    	    								    	    				
				 settag.add(tgn);
			}									    									    		
    		}
    		else
    		{
    			tgn=tagnm;
    			settag.add(tgn);
    		}
    	}
		System.out.println("tags : "+settag);
		
		request.setAttribute("catg",c);
		request.setAttribute("tagsfacet",settag);
		RequestDispatcher rd=request.getRequestDispatcher("facetSearch.jsp");
		rd.forward(request, response);	
		
	}


}
