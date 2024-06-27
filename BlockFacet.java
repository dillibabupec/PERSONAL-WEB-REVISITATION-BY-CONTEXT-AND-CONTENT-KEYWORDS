package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;

public class BlockFacet extends HttpsServlet {

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
		String rol[]=request.getParameterValues("parent");
		for(String s:rol)
		{
			String st=s.trim();
		if(st.equalsIgnoreCase("Specific"))
		{
		RequestDispatcher rd=request.getRequestDispatcher("Parental.jsp");
		rd.forward(request, response);
		}else
		{
		RequestDispatcher rd=request.getRequestDispatcher("Facetparental.jsp");
		rd.forward(request, response);
		}
			
		}
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
		List tagsLinks=new ArrayList();
		TreeMap taglink=new TreeMap();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DBServices dbs=new DBServices();
		
		String uname=request.getParameter("usrs");
		String catg=request.getParameter("categry");
		dbs.facetBlock(uname,catg);
		
		
		tagsLinks=dbs.showTag(catg);
		System.out.println("tags & Links from db : "+tagsLinks);
		
	
		
		List tagset,linkset;
		List links=new ArrayList();	    	   
		List tgname=new ArrayList();
		
		int ind=tagsLinks.indexOf("*");
	    int en = tagsLinks.size();
	    System.out.println("ind : "+ind+"  en : "+en);
	    
	    tgname.addAll(tagsLinks.subList(0,ind));
	    System.out.println("tgname : "+tgname);
	    
	    links.addAll(tagsLinks.subList(ind+1, en));
	    System.out.println("links : "+links);
	    		
	    tagset=new ArrayList();
	    linkset=new ArrayList();
	    //System.out.println("tag name in bookmark===>"+tgname);
	    for(int i=0;i<tgname.size();i++)
	    {
	    	String tagname=(String)tgname.get(i);
	    	
	    	if(tagname.contains("("))
	    	{
	    	StringTokenizer sttag=new StringTokenizer(tagname,"( , )");
    		while (sttag.hasMoreElements()) {
    			
				String tgnm=(String)sttag.nextElement();	
			    String d=(String) links.get(i);
				//System.out.println("sttag===>"+tgn);
				tagset.add(tgnm);
				taglink.put(tgnm, d);    								    	    									    	    				    										    	   
			}
	    	}
	    	else
	    	{
	    		 String d=(String) links.get(i);
	    		 tagset.add(tagname);
	    		 taglink.put(tagname, d);    
	    	}
	    }
	    System.out.println("tags-- tagset : "+tagset);
	    System.out.println("link-- taglink : "+taglink);
		
	    Iterator<Map.Entry<String, String>> entries = taglink.entrySet().iterator();
    	while (entries.hasNext()) {
    	    Map.Entry<String, String> entry = entries.next();
    	    String tag=entry.getKey();
    	    String link=entry.getValue();
    	    dbs.facetOvaerallBlock(uname,catg,tag,link);
    	}
	
		String s = "Success";
		request.setAttribute("msg", s);
		RequestDispatcher rd=request.getRequestDispatcher("Facetparental.jsp");
		rd.forward(request, response);
	}

}
