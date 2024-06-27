package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.servlet.HttpsServlet;

public class TagSuppression extends HttpsServlet {
	String tagname;
	/**
	 * Constructor of the object.
	 */
	public TagSuppression() {
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
		Vector kwVec=new Vector();
	    Vector domVec=new Vector();
		response.setContentType("text/html");
		tagname=request.getParameter("tag");
		System.out.println("tagname : "+tagname);
		SynsetMean smD=new SynsetMean();
		try{
		String vecStringCon=smD.getDom(tagname);
		System.out.println("suppression"+vecStringCon);
		
		StringTokenizer stk=new StringTokenizer(vecStringCon, "*");
        String vecString=stk.nextToken().toString();
        String domString=stk.nextToken().toString();
        
        String oth="others";
//        System.out.println("Vec String "+vecString);
//        System.out.println("Dom String "+domString);
        
       
        if(!domString.equals("")&& !domString.equalsIgnoreCase("dom"))
        {
           domVec.add(domString.trim());
        }
      
          
            if(vecString.contains("["))
            {
                String tempStr=vecString.replace("[","").replace("]","");
                String[] tmparr=tempStr.split("\\,") ;
                for(int f=0;f<tmparr.length;f++)
                {
                    kwVec.add(tmparr[f].trim());
                    
                }
              }
           else{
                    kwVec.add(vecString.trim());
          
           }
          
            
            if(domString.equalsIgnoreCase("dom"))
            	 domVec.add("others");
            else if(domString.equalsIgnoreCase("others"))
            	 System.out.println("kwVec"+kwVec);
            else
                kwVec.add(domString.trim());
            
//            System.out.println("kwVec"+kwVec);
//            System.out.println("domVec"+domVec);
            
		request.setAttribute("suppr", kwVec);
		request.setAttribute("categry", domVec);
		RequestDispatcher req = request.getRequestDispatcher("AddBookmark.jsp");
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
