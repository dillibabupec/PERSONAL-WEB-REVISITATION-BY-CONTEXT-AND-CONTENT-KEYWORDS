package logic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpSession;

import com.http.servlet.HttpsServlet;

import java.io.*;
import java.util.*;

public class GoogApi extends HttpsServlet {
	File f;
	FileReader inputFile;
	BufferedReader bufferReader;
	String titl="",link="",des="";
	String titlr="",linkr="",desr="";
	String qry,query,mode,mode1;
	String clickmode="";
	String[] arr;
	Vector tit;
	Vector alltitle;
	public HttpSession sess;
	
	/**
	 * Constructor of the object.
	 */
	public GoogApi() {
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
		String output;
        
        String line;
        desr="";
        titlr="";
        linkr="";
        arr= new String[15];
        int i=0;
		String key="AIzaSyCeUrl7gFCnSwD5BXd0dcyFSRGCWjtmYEM";
		tit=new Vector();
		qry=request.getParameter("query");
		alltitle=new Vector();
		query=qry.replace(" ", "+");
		mode=request.getParameter("on");
		mode1=request.getParameter("off");
		
//		try{
//			if((mode==null)||(mode1==null))
//			{
//				System.out.println("null");
//			}
//			}
//			catch(NullPointerException e)
//			{
//				
//			}
			
        try{
		if((mode1==null)&&(mode.equals("Online")))
		{
			System.out.println("YES");
			 URL url = new URL(
					 "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ query + "&alt=json");
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("GET");
			        conn.setRequestProperty("Accept", "application/json");
			        try{   
			        	System.out.println("inside try");
			        BufferedReader br = new BufferedReader(new InputStreamReader(
			              (conn.getInputStream())));
			        System.out.println(" BufferedReader br : "+br);
			        System.out.println("Output from Server .... \n");
			        
	                
			        while ((output = br.readLine()) != null) {
			        if(output.contains("\"title\": \"")){
			        	
				           titl=output.substring(output.indexOf("\"title\": \"")+("\"title\": \"").length());			           
					       System.out.println(titl);
				           tit.add(titl);
				      
			        }

			        if(output.contains("\"link\": \"")){
				          link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));			         
				          linkr=linkr+link+"*";			         
				               
			        }

			        if(output.contains("\"snippet\": \"")){
			   		       des=output.substring(output.indexOf("\"snippet\": \"")+("\"snippet\": \"").length(), output.indexOf("\","));		   		 
			   		       desr=desr+des+"*";
			   		       
			        }

			        }
			        
			        for(int t=3;t<tit.size();t++)
			        {
			        	alltitle.add(tit.get(t));
			        }
			        System.out.println("all title"+alltitle);
			        
			        clickmode="Online";
			        request.setAttribute("Mode", clickmode);
			        request.setAttribute("TitleR", alltitle);
			        request.setAttribute("LinkR", linkr);
			        request.setAttribute("DescR", desr);
			         
			        conn.disconnect();
			           
			           
			         }
			         catch(Exception e){
			                    System.out.println("Error while reading file line by line:" 
			                    + e.getMessage());                      
			            }
		}
		else if((mode==null)&&(mode1.equals("Offline")))
		{
			System.out.println("mode : "+mode);
			System.out.println("mode1 : "+mode1);
			
			if(qry.equalsIgnoreCase("Violin"))
	        {
				System.out.println("qry : "+qry);
	        	f=new File("webapps/CollaborativeTagging/links/Violin.txt");
	        	     	
	        }
	        else if(qry.equalsIgnoreCase("Cricket"))		       
			{
	        	System.out.println("qry : "+qry);
			     f=new File("webapps/CollaborativeTagging/links/Cricket.txt");
			        	
		    }
	        else if(qry.equalsIgnoreCase("Android"))		       
			{
	        	System.out.println("qry : "+qry);
			     f=new File("webapps/CollaborativeTagging/links/Android.txt");
			        	
		    }
	        else if(qry.equalsIgnoreCase("Java"))		       
			{
	        	System.out.println("qry : "+qry);
			     f=new File("webapps/CollaborativeTagging/links/Java.txt");
		    }
	        try{

	         //Create object of FileReader		        
	         inputFile = new FileReader(f);
	        
	         //Instantiate the BufferedReader Class
	         bufferReader = new BufferedReader(inputFile);
	         
	         //Variable to hold the one line data
             desr="";
             titlr="";
             linkr="";
             

          // Read file line by line and print on the console
	         while ((line = bufferReader.readLine()) != null)   {

	        	 
	        
	         if(line.contains("Title:")){
	        	 
	               titl=line.substring(line.indexOf("Title:")+("Title:").length());
	               System.out.println(titl);
		           tit.add(titl);
	               
	         }
	         if(line.contains("Link:")){

	               link=line.substring(line.indexOf("Link:")+("Link:").length(),line.indexOf(","));
	               linkr=linkr+link+"*";
	               System.out.println("Link   "+linkr);
	              
             }
	         if(line.contains("Description")){

			 		des=line.substring(line.indexOf("Description:")+("Description:").length());
			 		desr=desr+des+"*";
			 		System.out.println("Desc   "+desr);
	         }
	         }
	         
	         clickmode="Offline";
		     request.setAttribute("Mode", clickmode);

	         request.setAttribute("TitleR", tit);
	         request.setAttribute("LinkR", linkr);
	         request.setAttribute("DescR", desr);
	         
	         
             //System.out.println("Result"+desr);
             
	         //Close the buffer reader
	         bufferReader.close();
	         }
	         catch(Exception e){
	                    System.out.println("Error while reading file line by line:" 
	                    + e.getMessage());                      
	            }
	         

		}
        }
        catch(NullPointerException pce)
        {
        	pce.printStackTrace();
        }
			
	      
		        sess=request.getSession();  
		 		sess.setAttribute("Query",query );
		        
		        RequestDispatcher req = request.getRequestDispatcher("Search.jsp");
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


// BufferedReader br = new BufferedReader(new InputStreamReader(
//      (conn.getInputStream())));

//String output;
//System.out.println("Output from Server .... \n");
//while ((output = br.readLine()) != null) {
//System.out.println(output);
//if(output.contains("\"title\": \"")){
//   String title=output.substring(output.indexOf("\"title\": \"")+("\"title\": \"").length());
// System.out.println(title);
//}
//if(output.contains("\"snippet\": \"")){
//        String desc=output.substring(output.indexOf("\"snippet\": \"")+("\"snippet\": \"").length(), output.indexOf("\","));
//      System.out.println(desc);
//}
//if(output.contains("\"link\": \"")){
//   String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
//   System.out.println(link);       //Will print the google search links
//}
//}

//if(qry.equalsIgnoreCase("Android"))		       
//{
//	f=new File("webapps/CollaborativeTagging/links/Android.txt");
//	//f="webapps/CollaborativeTagging/links/Android.txt";
//}
//else if(qry.equalsIgnoreCase("Java"))
//{
//	f=new File("webapps/CollaborativeTagging/links/Java.txt");
//	
//}
//else if(qry.equalsIgnoreCase("TajMahal"))
//{
//	f=new File("webapps/CollaborativeTagging/links/TajMahal.txt");
//			        	
//}
//else if(qry.equalsIgnoreCase("Cricket"))		       
//{
//	f=new File("webapps/CollaborativeTagging/links/Cricket.txt");
//	
//}
//else if((qry.equalsIgnoreCase("TheHindu"))||(qry.equalsIgnoreCase("Hindu")))
//{
//	f=new File("webapps/CollaborativeTagging/links/TheHindu.txt");
//	
//}
// if(qry.equalsIgnoreCase("Violin"))
//{
//	f=new File("webapps/CollaborativeTagging/links/Violin.txt");
//	     	
//}
// else if(qry.equalsIgnoreCase("Cricket"))		       
// {
//     f=new File("webapps/CollaborativeTagging/links/Cricket.txt");
//        	
//  }
// else if(qry.equalsIgnoreCase("Cricket"))		       
// {
//     f=new File("webapps/CollaborativeTagging/links/Android.txt");
//        	
//  }
////Create object of FileReader		        
//inputFile = new FileReader(f);
//
////Instantiate the BufferedReader Class
//bufferReader = new BufferedReader(inputFile);


// Read file line by line and print on the console
//   while ((line = bufferReader.readLine()) != null)   {
//
//  	 
//  
//   if(line.contains("Title:")){
//  	 
//         titl=line.substring(line.indexOf("Title:")+("Title:").length());
//       
//         
//   }
//   if(line.contains("Link:")){
//
//         link=line.substring(line.indexOf("Link:")+("Link:").length(),line.indexOf(","));
//        
//        
//         //des=des+line+"*";
//}
//   if(line.contains("Description")){
//
//	 					 	des=line.substring(line.indexOf("Description:")+("Description:").length());
//	 					 
//
//   }
//   }