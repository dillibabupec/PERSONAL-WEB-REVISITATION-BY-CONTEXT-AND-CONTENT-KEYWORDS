package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;
public class Bookmark extends HttpsServlet {
	String query;
	int i=0;
	List category=new ArrayList();
	String qrytag="";
	Map<String, Integer> map; 
	String ctgry,tgs;
	List alltgs; 
	List tgname,link;
	Set tagset;
	/**
	 * Constructor of the object.
	 */
	public Bookmark() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List rmvCtgry=new ArrayList();
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		map= new HashMap<String,Integer>();
		DBServices dbs=new DBServices();
		category=(ArrayList)dbs.category();
		
		Set <String> set=new HashSet<String>();
    	int len=0;
    	String tgn="";
    	for(int j=0;j<category.size();j++)
    	{
    		String catg=(String)category.get(j);
  		
	    	String tgname=dbs.selectTag(catg);
	    	StringTokenizer stg=new StringTokenizer(tgname,"*");
	    	while(stg.hasMoreElements()){
	    	String tagnm=stg.nextToken().toString();
	    		
	    		if(tagnm.contains("("))
	    		{
	    		StringTokenizer st=new StringTokenizer(tagnm,"( , )");
	    		while (st.hasMoreElements()) {
	    			
    				 tgn=(String)st.nextElement();						    	    								    	    				
    				 set.add(tgn);
					    								    	    									    	    				    										    	   
    			}									    									    		
	    		
	    		}
	    		else
	    		{
	    			tgn=tagnm;
	    			set.add(tgn);
	    		    	    										    	    					
	    		}
	    		len=set.size();	
	    		map.put(catg,len);
	    	}
	    	//System.out.println("set-----"+set);
    		set.clear();
    	}
    	
    	System.out.println("map : "+map);
    	String name=(String)session.getAttribute("username");
    	System.out.println("name : "+name);
    	rmvCtgry=dbs.blockFacets(name);
    	System.out.println("rmvCtgry : "+rmvCtgry);
    	
    	for(int i=0;i<rmvCtgry.size();i++)
          {
    		String s1=(String) rmvCtgry.get(i);
	        map.remove(s1);
          }
    	System.out.println("Rmvmap : "+map);
    	//sess=request.getSession();  
        //sess.setAttribute("hmap", map);
    	request.setAttribute("hmap", map);
    	session.setAttribute("hmapp", map);
         
        RequestDispatcher req = request.getRequestDispatcher("Bookmark.jsp");
 	    req.forward(request, response);
		
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		alltgs=new ArrayList();
	
		ctgry=(String)request.getParameter("categry");
		
		//System.out.println("categry in"+ctgry);
		DBServices dbs=new DBServices();
		alltgs=dbs.showTag(ctgry);
		
		link=new ArrayList();	    	   
 	    tgname=new ArrayList();
		
		int ind=alltgs.indexOf("*");
	    int en = alltgs.size();
	   
	    tgname.addAll(alltgs.subList(0,ind));
	    tagset=new HashSet();
	    //System.out.println("tag name in bookmark===>"+tgname);
	    for(int i=0;i<tgname.size();i++)
	    {
	    	String tagname=(String)tgname.get(i);
	    	if(tagname.contains("("))
	    	{
	    	StringTokenizer sttag=new StringTokenizer(tagname,"( , )");
    		while (sttag.hasMoreElements()) {
    			
				String tgn=(String)sttag.nextElement();	
			
				//System.out.println("sttag===>"+tgn);
				tagset.add(tgn);
				    								    	    									    	    				    										    	   
			}
	    	}
	    	else
	    	{
	    		 tagset.add(tagname);
	    	}
	    }
	    //link.addAll(alltgs.subList(ind+1, en));	            	  

	    //request.setAttribute("links", link);
		request.setAttribute("tags", tagset);
	
		RequestDispatcher req = request.getRequestDispatcher("Result.jsp");
		//req.include(request, response);
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
