package logic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

import java.text.SimpleDateFormat;
import java.util.*;

import DBServices.DBServices;
public class HomePage extends HttpsServlet {
List all;
List tgname,link,trtag,linkn;
Map<String, Integer> map;
Set tagset;
	/**
	 * Constructor of the object.
	 */
	public HomePage() {
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

		response.setContentType("text/html");
		try
		{
			DBServices db=new DBServices();
	  		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  		//String dayst;
	  		ArrayList<String> lin=new ArrayList<String>();
	  		List lindis=new ArrayList();
	  		java.util.Date datec;
	  		java.sql.Date curDate,befDate;
	  		Calendar now = Calendar.getInstance();
	  		
	  
	  		String cdate=   now.get(Calendar.YEAR)+ "-" +(now.get(Calendar.MONTH) + 1) + "-" +now.get(Calendar.DATE);
	  		datec =formatter.parse(cdate);
			curDate = new java.sql.Date(datec.getTime());
	  	    System.out.println("Current date : " +curDate);

	  	    // add days to current date using Calendar.add method
	  	    now.add(Calendar.DATE, -7);
	  	 
	  	    String bef=  now.get(Calendar.YEAR)+ "-" +(now.get(Calendar.MONTH) + 1)+ "-"+ now.get(Calendar.DATE) ;
	  	    datec =formatter.parse(bef);
			befDate = new java.sql.Date(datec.getTime());
	  	    //System.out.println("date after one week : " + befDate);
			String query;
			int i=0;
	    	
			
			//Trending Links
	    	String lnk=db.selectTrendLink(befDate, curDate);
	    	StringTokenizer stt=new StringTokenizer(lnk,"*");
	    	while(stt.hasMoreElements()){
	    		String splnk=stt.nextToken();
	    		lin.add(splnk);
	    	}
	    	
	    	System.out.println("List: " + lin);
	    	
	    	
	        map = new TreeMap<String,Integer>(Collections.reverseOrder());
	    	for(String t: lin) {
	    	    if(map.keySet().contains(t))
	    	    {
	    	       map.put(t, map.get(t)+1);

	    	    }else
	    	    {
	    	        map.put(t, 1);
	    	    }
	    	}
	    	System.out.println("=link="+map);	   
	    	
	    	//Latest tags
	    	all=db.selectLatestTag(befDate, curDate);
	    	
	    	int ind=all.indexOf("*");
    	    int en = all.size();
    	    
    	    tgname=new ArrayList();
    	    tgname.addAll(all.subList(0, ind));
    	        	        	    
    	    tagset=new HashSet();
    	    for(int j=0;j<tgname.size();j++)
    	    {
    	    	String tagname=(String)tgname.get(j);
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
    	    //System.out.println("linkname "+link);
    	    
    	    linkn=new ArrayList();
    	    linkn.addAll(all.subList(ind+1, en));
	    	
	    	//Trending tags
	    	trtag=db.selectTrendingTag(befDate, curDate);
	    	
	    	request.setAttribute("trendlink", map);
	    	request.setAttribute("latsttags", tagset);
	    	//request.setAttribute("latstlink", linkn);
	    	request.setAttribute("trendtags", trtag);
	    	
	    	RequestDispatcher req = request.getRequestDispatcher("home.jsp");
		    req.forward(request, response);
	    	
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
