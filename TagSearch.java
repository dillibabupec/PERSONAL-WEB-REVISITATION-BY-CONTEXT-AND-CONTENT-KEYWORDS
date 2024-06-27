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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
public class TagSearch extends HttpsServlet {
	String un,tagn,accs,acces,chkser;
	List tgname,link,linkn,othertagn;
	List linkfilter,tgnamefilter;
	Vector filttag;
	java.util.Date datec;
	/**
	 * Constructor of the object.
	 */
	public TagSearch() {
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
  		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		un=request.getParameter("usrn");
		//System.out.println("username====>"+un);
		tagn=request.getParameter("tquery");
		
		accs=request.getParameter("searc");
		//System.out.println("searc"+accs);
		chkser=request.getParameter("sear");
		//System.out.println("search"+chkser);
		try{
		if((accs==null)||(chkser==null))
		{
			System.out.println("null");
		}
		}
		catch(NullPointerException e)
		{
			
		}
		java.sql.Date curDate,befDate;
  		Calendar now = Calendar.getInstance();
  		
  		
  		String cdate=   now.get(Calendar.YEAR)+ "-" +(now.get(Calendar.MONTH) + 1) + "-" +now.get(Calendar.DATE);
  		try {
			datec =formatter.parse(cdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		curDate = new java.sql.Date(datec.getTime());
  	    System.out.println("Current date : " +curDate);
		
  	    DBServices dbs1=new DBServices();
		dbs1.insertSearchQuery(tagn, curDate);
		
		link=new ArrayList();
 	    linkn=new ArrayList();
 	    othertagn=new ArrayList();
 	    tgname=new ArrayList();
		try{
		if((chkser==null)&&(accs.equalsIgnoreCase("Search in your bookmarks")))
		{
			String tg;
			acces="Private";
			DBServices dbs=new DBServices();
    	    List all=dbs.searchTag(un, acces);
    	    
    	    int ind=all.indexOf("*");
    	    int en = all.size();
    	   
    	    link.addAll(all.subList(0, ind));   	
    	    tgname.addAll(all.subList(ind+1, en));
    	   
    	    
    	    for(int i=0;i<tgname.size();i++)
    	    {
    	    	
    	    	tg=(String)tgname.get(i);
    	    	if(tg.contains("("))
        	    {
    	    		//System.out.println("mtagnames"+tg);
    	    		 StringBuffer sb=null;
    	    		if(tg.contains(tagn))
    				{
    	    			//System.out.println("mtagnames"+tg);
    	    			//System.out.println();
    	    			 sb=new StringBuffer();
    	    			StringTokenizer st=new StringTokenizer(tg,"( , )");
    	    			
    	    	    	while (st.hasMoreElements()) {
    	    				//System.out.println(st.nextElement());
    	    				String tgn=(String)st.nextElement();
    	    				//System.out.println("split"+tgn);
    	    				if(tgn.equalsIgnoreCase(tagn))
    	    				{
    	    					linkn.add(link.get(i));
    	    					//System.out.println("linkname"+linkn);
    	    				}
    	    				else{
    	    					
    	    					sb.append(tgn+",");
    	    					
    	    					
    	    				}   	    				
    	    			
    	    			}
	    	    		othertagn.add(sb.deleteCharAt(sb.toString().length()-1));
    				}
    	    		
    	    	}
    	    	else
    	    	{
    	    		
    	    		
    	    	if(tg.equalsIgnoreCase(tagn)) 	    		
    				{
    	    		
    					linkn.add(link.get(i));
    					othertagn.add("");
    					//System.out.println("stagnames"+tg);
    					//System.out.println("linkname"+linkn);
    				}
    	    	}
    	    		
    	    }
    	   
    	   // System.out.println("tagname"+tgname);
    	      	    
//    	    String []appArray = new String[x];
//    	    appArray=(String[]) li.toArray(appArray);
//    	    
//    	    for(int i=0;i<appArray.length;i++)
//    	    System.out.println(appArray[i]);
    	    
    	    request.setAttribute("lnkname", linkn);
    	    request.setAttribute("othertag", othertagn);
    	    
    	  
		}
		else if((accs==null)&&(chkser.equalsIgnoreCase("Search in All")))
		{
			//acces="Public";
			String tg;
			DBServices dbs=new DBServices();
			
//Retrieve public tags and link
			
    	    List all=dbs.searchAll();
    	      	    
    	    int ind=all.indexOf("*");
    	    int en = all.size();
    	    
    	    link=new ArrayList();    	   
    	    link.addAll(all.subList(0, ind));
       
    	    tgname=new ArrayList();
    	    tgname.addAll(all.subList(ind+1, en));
    	    
  
//Retrieve filter tags and link    	    
    	        	   
    	    List allfilter=dbs.searchFilter(un,tagn);
    	    //System.out.println("allfilter======>"+allfilter);
    	   
    	    String filter=(String)allfilter.get(0);
    	    if(filter.equals("*"))
    	    {
    	    	//System.out.println("no filteration");
    	    	filttag=new Vector();
    	    	filttag=dbs.searchFilterTag(un);
    	    	//System.out.println("filter tag=========================="+filttag);
    	    	 for(int i=0;i<tgname.size();i++)
    	    	    {
    	    	    	
    	    	    	tg=(String)tgname.get(i);
    	    	    	if(tg.contains("("))
    	        	    {
    	    	    		 StringBuffer sb=null;
    	    	    		if(tg.contains(tagn))
    	    				{
//    	    	    			System.out.println("mtagnames"+tg);
//    	    	    			System.out.println();
    	    	    			 sb=new StringBuffer();
    	    	    			StringTokenizer st=new StringTokenizer(tg,"( , )");
    	    	    			
    	    	    	    	while (st.hasMoreElements()) {
    	    	    				//System.out.println(st.nextElement());
    	    	    				String tgn=(String)st.nextElement();
    	    	    				//System.out.println("split"+tgn);
    	    	    				if(tgn.equalsIgnoreCase(tagn))
    	    	    				{
    	    	    					linkn.add(link.get(i));
//    	    	    					System.out.println("linkname"+linkn);
    	    	    				}
    	    	    				else{
//    	    	    					System.out.println("filter tag");
    	    	    					//for(int k=0;k<filttag.size();k++)
    	    	    					//{
    	    	    						//String filt=(String)filttag.get(k);
    	    	    					if(filttag.contains(tgn)){
    	    	    					 System.out.println("no tags");
    	    	    					 sb.append("-");
    	    	    					 }
    	    	    						
    	    	    					else{
    	    	    					 sb.append(tgn+",");}
    	    	    					System.out.println("sb=====>"+sb);
    	    	    					//}
    	    	    					
    	    	    					
    	    	    					
    	    	    				}   	    				
    	    	    			
    	    	    			}
    	    	    	    	if(sb.equals("-"))
    	    	    	    		othertagn.add(sb);
    	    	    	    	else
    	    	    	    		othertagn.add(sb.deleteCharAt(sb.toString().length()-1));
    	    	    	    
    	    	    	    		
    	    				}
    	    	    		
    	    	    	}
    	    	    	else
    	    	    	{
    	    	    		
    	    	    		
    	    	    	if(tg.equalsIgnoreCase(tagn)) 	    		
    	    				{
    	    	    		
    	    					linkn.add(link.get(i));
    	    					othertagn.add("");
    	    					//System.out.println("stagnames"+tg);
    	    					//System.out.println("linkname"+linkn);
    	    				}
    	    	    	}
    	    	    		
    	    	    }
    	    }
    	    
    	    else
    	    {
    	    	System.out.println("filteration");
    	    	int indf=allfilter.indexOf("*");
    	    	int enf = allfilter.size();
    	    
    	    	linkfilter=new ArrayList();    	   
    	    	linkfilter.addAll(allfilter.subList(0, indf));
    	    
    	    	System.out.println("linkfilter"+linkfilter);
    	             	      	    
    	    	tgnamefilter=new ArrayList();
    	    	tgnamefilter.addAll(allfilter.subList(indf+1, enf));
    	    
    	    	//System.out.println("tagfilter"+tgnamefilter);
    	    	
    	    	
    	    	System.out.println();
    	    	for(int i=0;i<tgnamefilter.size();i++)
    	        {
    	   	    for(int j=0;j<tgname.size();j++)
    	    	{
    	   	    	String taggen=(String)tgname.get(j);
    	   	    	String filtertag=(String)tgnamefilter.get(i);
    	    	    
    	    	    System.out.println("tgnamefilter"+filtertag);
    	    	    if(taggen.contains(filtertag))
   	    	    	{
    	    	    	System.out.println("");
    	    	    }
    	    	    else
    	    	    {
    	    	    	System.out.println("");
    	    	    	
    	    	    		//////////////////////////////////////////////////
  	    	    		
    	    	    		if(taggen.contains("("))
        	        	    {
        	    	    		 StringBuffer sb=null;
        	    	    		if(taggen.contains(tagn))
        	    				{
        	    	    			System.out.println("mtagnames"+taggen);
        	    	    			System.out.println();
        	    	    			 sb=new StringBuffer();
        	    	    			StringTokenizer st=new StringTokenizer(taggen,"( , )");
        	    	    			
        	    	    	    	while (st.hasMoreElements()) {
        	    	    				//System.out.println(st.nextElement());
        	    	    				String tgn=(String)st.nextElement();
        	    	    				System.out.println("split"+tgn);
        	    	    				if(tgn.equalsIgnoreCase(tagn))
        	    	    				{
        	    	    					linkn.add(link.get(i));
        	    	    					System.out.println("linkname"+linkn);
        	    	    				}
        	    	    				else{
//        	    	    					for(int k=0;k<filttag.size();k++)
//        	    	    					{
//        	    	    						String filt=(String)filttag.get(k);
//        	    	    					if(tgn.equalsIgnoreCase(filt))
//        	    	    						System.out.println("no tags");
//        	    	    					else
        	    	    					 sb.append(tgn+",");
        	    	    					//}
        	    	    					
        	    	    				}   	    				
        	    	    			
        	    	    			}
        		    	    		othertagn.add(sb.deleteCharAt(sb.toString().length()-1));
        	    				}
        	    	    		
        	    	    	}
        	    	    	else
        	    	    	{
        	    	    		
        	    	    		
        	    	    	if(taggen.equalsIgnoreCase(tagn)) 	    		
        	    				{
        	    	    		
        	    					linkn.add(link.get(i));
        	    					othertagn.add("");
        	    					//System.out.println("stagnames"+tg);
        	    					//System.out.println("linkname"+linkn);
        	    				}
        	    	    	}
        	    	    		
    	    	    		/////////////////////////////////////////////////////////////////
    	    	    		
    	
    	   	    	}
    	     	}
    	       }
    	    }

    	 
  //new
    	
    	    
    	    
  //old code  	    
    	       	      	   
//    	    for(int i=0;i<tgname.size();i++)
//    	    {
//    	    	
//    	    	tg=(String)tgname.get(i);
//    	    	if(tg.contains("("))
//        	    {
//    	    		//System.out.println("mtagnames"+tg);
//    	    		 StringBuffer sb=null;
//    	    		if(tg.contains(tagn))
//    				{
//    	    			System.out.println("mtagnames"+tg);
//    	    			System.out.println();
//    	    			 sb=new StringBuffer();
//    	    			StringTokenizer st=new StringTokenizer(tg,"( , )");
//    	    			
//    	    	    	while (st.hasMoreElements()) {
//    	    				//System.out.println(st.nextElement());
//    	    				String tgn=(String)st.nextElement();
//    	    				System.out.println("split"+tgn);
//    	    				if(tgn.equalsIgnoreCase(tagn))
//    	    				{
//    	    					linkn.add(link.get(i));
//    	    					System.out.println("linkname"+linkn);
//    	    				}
//    	    				else{
//    	    					
//    	    					sb.append(tgn+",");
//    	    					
//    	    					
//    	    				}   	    				
//    	    			
//    	    			}
//	    	    		othertagn.add(sb.deleteCharAt(sb.toString().length()-1));
//    				}
//    	    		
//    	    	}
//    	    	else
//    	    	{
//    	    		
//    	    		
//    	    	if(tg.equalsIgnoreCase(tagn)) 	    		
//    				{
//    	    		
//    					linkn.add(link.get(i));
//    					othertagn.add("");
//    					//System.out.println("stagnames"+tg);
//    					//System.out.println("linkname"+linkn);
//    				}
//    	    	}
//    	    		
//    	    }
//    	   
    	  
		}
		String resl="";
		
		  //System.out.println("othertag"+othertagn);
		if(linkn.isEmpty())
		{
			 resl="No search results found";
			
			 request.setAttribute("result",resl );
			 request.setAttribute("lnkname", linkn);
			 request.setAttribute("othertag",  othertagn);
			 RequestDispatcher req1 = request.getRequestDispatcher("TagSearch.jsp");
		     req1.forward(request, response);
			
			 
		}
		else
		{
			 resl="Results found";
			
			 request.setAttribute("result",resl );
			 request.setAttribute("lnkname", linkn);
			 request.setAttribute("othertag", othertagn);
			 RequestDispatcher req1 = request.getRequestDispatcher("TagSearch.jsp");
		     req1.forward(request, response);
		}
		
  	    
		 }
		 
		 catch(NullPointerException pce)
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
