package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.http.servlet.HttpsServlet;

import DBServices.DBServices;
public class UploadFile extends HttpsServlet {
public HttpSession sessi;
	/**
	 * Constructor of the object.
	 */
	public UploadFile() {
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
		sessi=request.getSession();
		// upload a file code
				
		String contentType = request.getContentType();
		System.out.println("Content type is :: " +contentType);
		//out.println(contentType);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
		DataInputStream in = new DataInputStream(request.getInputStream());
		int formDataLength = request.getContentLength();
				
		if((formDataLength/1000000)<12){
		byte dataBytes[] = new byte[formDataLength];
	
		int byteRead = 0;
		int totalBytesRead = 0;
		while (totalBytesRead < formDataLength) {
		byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
		totalBytesRead += byteRead;
		}
		String file="";		
		file = new String(dataBytes);
		String saveFilename;
		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		
		int slashsplit = saveFile.indexOf("/");
		int contentsplit = saveFile.indexOf("Content-Type:");
		String checkcontenttype = saveFile.substring(contentsplit,slashsplit);
		//System.out.println("upload File"+checkcontenttype);
		String uploadstate="";
		String ufilename="",ufileurl="",ffilename="";
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));		
		ufilename=saveFile.toString().trim();
		StringTokenizer st=new StringTokenizer(ufilename, ".");
		ffilename=(String)st.nextToken();
	
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,contentType.length());
		int pos;
		pos = file.indexOf("filename=\"");

		pos = file.indexOf("\n", pos) + 1;

		pos = file.indexOf("\n", pos) + 1;

		pos = file.indexOf("\n", pos) + 1;

		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
		
		File root = new File("webapps/CollaborativeTagging/Upload");
		String ipaddress = InetAddress.getLocalHost().getHostAddress();
		String portnumber = String.valueOf(request.getServerPort());
		
		if(checkcontenttype.contains("image"))	{
			
			
			File newfolder = new File(root, "/Images");
			newfolder.mkdir();			
			String path1 = "webapps\\CollaborativeTagging\\Upload\\Images";             
            String files;

            File folder = new File(path1);
            if(folder.exists())
            {
                File[] listOfFiles = folder.listFiles(); 
                //System.out.println("upload File"+checkcontenttype);
                if(listOfFiles.length>0){
                
                
                for (int i = 0; i < listOfFiles.length; i++) 
                {
        			
                 if (listOfFiles[i].isFile()) 
                 {
                     files = listOfFiles[i].getName();
                  
                     if(files.equals(saveFile))
                     {
                    	 System.out.println("file already uploaded");
                    	 uploadstate="File Already uploaded....";
             			 request.setAttribute("uploadstate", uploadstate);
             			 request.setAttribute("templ","");
             			 break;
                     }
                     else
                     {
                    	ufileurl="http://localhost:"+portnumber+"/CollaborativeTagging/Upload/Images/"+saveFile;
             			saveFilename = "webapps/CollaborativeTagging/Upload/Images/"+ saveFile;
             			FileOutputStream fileOut = new FileOutputStream(saveFilename);
            			fileOut.write(dataBytes, startPos, (endPos - startPos));
            			fileOut.flush();
            			fileOut.close();
            			System.out.println("File Uploaded SuccessFully..... " +saveFilename);
            			request.setAttribute("templ",saveFilename);
            			uploadstate="Upload Success.....";
            			request.setAttribute("uploadstate", uploadstate);
            			
            			DBServices dbs=new DBServices();
            			dbs.insertFile(ffilename, ufileurl);
                     }
                   }
                   }}
                else
                {
               	ufileurl="http://localhost:"+portnumber+"/CollaborativeTagging/Upload/Images/"+saveFile;
        			saveFilename = "webapps/CollaborativeTagging/Upload/Images/"+ saveFile;
        			FileOutputStream fileOut = new FileOutputStream(saveFilename);
       			fileOut.write(dataBytes, startPos, (endPos - startPos));
       			fileOut.flush();
       			fileOut.close();
       			System.out.println("File Uploaded SuccessFully..... " +saveFilename);
       			request.setAttribute("templ",saveFilename);
       			uploadstate="Upload Success.....";
       			request.setAttribute("uploadstate", uploadstate);
       			
       			DBServices dbs=new DBServices();
       			dbs.insertFile(ffilename, ufileurl);
                }
                
            }
                     
            System.out.println("linkname===>"+ufileurl);

			sessi.setAttribute("linkname", ufileurl);
	    	RequestDispatcher rd =request.getRequestDispatcher("AddBookmark.jsp");
	    	rd.forward(request, response);
	        	
			
			}
		else if(checkcontenttype.contains("video"))
		{
			
			File newfolder = new File(root, "/Videos");
			newfolder.mkdir();			
			String path1 = "webapps\\CollaborativeTagging\\Upload\\Videos";             
            String files;

            File folder = new File(path1);
            if(folder.exists())
            {
                File[] listOfFiles = folder.listFiles(); 
                System.out.println("upload File"+checkcontenttype);
                if(listOfFiles.length>0){
                
                
                for (int i = 0; i < listOfFiles.length; i++) 
                {
        			
                 if (listOfFiles[i].isFile()) 
                 {
                     files = listOfFiles[i].getName();
                  
                     if(files.equals(saveFile))
                     {
                    	 System.out.println("file already uploaded");
                    	 uploadstate="File Already uploaded....";
             			 request.setAttribute("uploadstate", uploadstate);
             			 request.setAttribute("templ","");
             			 break;
                     }
                     else
                     {
                    	ufileurl="http://localhost:"+portnumber+"/CollaborativeTagging/Upload/Videos/"+saveFile;
             			saveFilename = "webapps/CollaborativeTagging/Upload/Videos/"+ saveFile;
             			FileOutputStream fileOut = new FileOutputStream(saveFilename);
            			fileOut.write(dataBytes, startPos, (endPos - startPos));
            			fileOut.flush();
            			fileOut.close();
            			System.out.println("File Uploaded SuccessFully..... " +saveFilename);
            			request.setAttribute("templ",saveFilename);
            			uploadstate="Upload Success.....";
            			request.setAttribute("uploadstate", uploadstate);
            			
            			DBServices dbs=new DBServices();
            			dbs.insertFile(ffilename, ufileurl);
                     }
                   }
                   }}
                else
                {
               	ufileurl="http://localhost:"+portnumber+"/CollaborativeTagging/Upload/Videos/"+saveFile;
        			saveFilename = "webapps/CollaborativeTagging/Upload/Videos/"+ saveFile;
        			FileOutputStream fileOut = new FileOutputStream(saveFilename);
       			fileOut.write(dataBytes, startPos, (endPos - startPos));
       			fileOut.flush();
       			fileOut.close();
       			System.out.println("File Uploaded SuccessFully..... " +saveFilename);
       			request.setAttribute("templ",saveFilename);
       			uploadstate="Upload Success.....";
       			request.setAttribute("uploadstate", uploadstate);
       			
       			DBServices dbs=new DBServices();
       			dbs.insertFile(ffilename, ufileurl);
                }
                
            }
                     
            System.out.println("linkname===>"+ufileurl);
            sessi.setAttribute("linkname", ufileurl);
	    	RequestDispatcher rd =request.getRequestDispatcher("AddBookmark.jsp");
	    	rd.forward(request, response);
		}
			
		
		else
		{
			uploadstate="Upload Failed.....";
			System.out.print(uploadstate);
			request.setAttribute("uploadstate", uploadstate);
			RequestDispatcher rd =request.getRequestDispatcher("multimediaSearch.jsp");
			rd.forward(request, response);
		}
		}
		else
		{
			String uploadstate="Upload Failed.Insufficient space....";
			System.out.print(uploadstate);
			request.setAttribute("uploadstate", uploadstate);
			RequestDispatcher rd =request.getRequestDispatcher("multimediaSearch.jsp");
			rd.forward(request, response);
		}
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
