package DBServices;

import java.sql.Connection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
public class DBServices {
	
        TreeMap<String,Integer> tm=new TreeMap<String,Integer>();      
        DBConnection dbc = DBConnection.getInstance();
        Connection con = dbc.getConnection();  
        ResultSet rs; 
        int incr=0;
      //function to retrieve username and password from regis table 
        public boolean testLogin(String uname,String pwd)
        {
       	
       	 boolean check=false;
       	 
       	 try {
				Statement st = con.createStatement();
	            rs = st.executeQuery("select username,pwd from register where username='"+uname+"' and pwd='"+pwd+"'");
	            check=rs.next();  
	           	              
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        	 
       	 return check;
        }
         
         public void insertRegis(String ui,String uname,String dob,String gender,String pw,String rpw,String contact,String email,String ad)
         {
        	 try{
        	 Statement st = con.createStatement();
             String qu = ("insert into register values('"+ui+"','"+uname+"','"+dob+"','"+gender+"','"+pw+"','"+rpw+"','"+contact+"','"+email+"','"+ad+"')");
                             
             st.executeUpdate(qu); 
        	 }
        	 catch (SQLException e) {
  				e.printStackTrace();
  			}
         }
         
         public boolean updateRegis(String ui,String uname,String dob,String gender,String pw,String rpw,String contact,String email,String ad)
         {
        	 boolean check=false;
             Statement st;
			try {
				st = con.createStatement();
				String qu="update register set userid='"+ui+"',username='"+uname+"',dob='"+dob+"',gender='"+gender+"',pwd='"+pw+"',rpw='"+rpw+"',contact='"+contact+"',email='"+email+"',address='"+ad+"' where userid='"+ui+"'";	                          
	            st.executeUpdate(qu); 
	            check=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return check;
             
         }
         
         public String selectRegis(String user,String pass)
         {
        	 
        	 String ui="",uname="",dob="",gend="",pw="",rpw="",cont="",ema="",address="",all="";
        	 Statement st;
 			try {
 				st = con.createStatement();
 				ResultSet rs = st.executeQuery("select * from register where username='"+user+"' and pwd='"+pass+"'");
 				while(rs.next()){
 					ui=rs.getString("userid");
 	            	uname=rs.getString("username");
 	            	 	            	
 	            	dob=rs.getString("dob");	            	
 	            	gend=rs.getString("gender");
 	            	pw=rs.getString("pwd");
 	            	rpw=rs.getString("rpw");
 	            	
 	            	cont=rs.getString("contact");
 	            	ema=rs.getString("email");
 	            	address=rs.getString("address");
		            System.out.println("address"+address);             
	              }
 				all=ui+"!"+uname+"!"+dob+"!"+gend+"!"+pw+"!"+rpw+"!"+cont+"!"+ema+"!"+address;
 	
 	            
 	            
 			} catch (SQLException e) {
 				
 				e.printStackTrace();
 			}
 			return all;
         
         }
         
         public String displayRegis()
         {
        	 
        	 String ui="",uname="",dob="",gend="",pw="",rpw="",cont="",ema="",address="",all="";
        	 Statement st;
 			try {
 				st = con.createStatement();
 				ResultSet rs = st.executeQuery("select * from register");
 				while(rs.next()){
 					ui=rs.getString("uid");
 	            	uname=rs.getString("uname");
 	            	 	            	
 	            	dob=rs.getString("dob");	            	
 	            	gend=rs.getString("gender");
 	            	pw=rs.getString("pw");
 	            	rpw=rs.getString("repw");
 	            	
 	            	cont=rs.getString("contact");
 	            	ema=rs.getString("email");
 	            	address=rs.getString("addr");
		                          
	              }
 				all=ui+"!"+uname+"!"+dob+"!"+gend+"!"+pw+"!"+rpw+"!"+cont+"!"+ema+"!"+address;
 	
 	            
 	            
 			} catch (SQLException e) {
 				e.printStackTrace();
 			}
 			return all;
         
         }
         
         public void insertTag(String uname,String tag,String categor,String link,String accs,java.sql.Date dtec)
         {
        	 System.out.println("DBService"+dtec);
        	 try{
            	 Statement st = con.createStatement();
                 String qu = ("insert into tag values('"+uname+"','"+tag+"','"+categor+"','"+link+"','"+accs+"','"+dtec+"')");
                                 
                 st.executeUpdate(qu); 
            	 }
            	 catch (SQLException e) {
      				e.printStackTrace();
      			}
         }
         public void selectTag(String tag,String categor)
         {
        	 Statement st;
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select tagn,category from tag");
  			
  				while(rs.next()){
  					
  	            	    
  	            
  			}
  			}catch (SQLException e) {
  				
  				e.printStackTrace();
  			}
         }
         
         public String selectLink(String tag)
         {
        	 Statement st;
        	 String lnk="";
        	 System.out.println("tag in selectlink"+tag);
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select link from tag where tagn='"+tag+"'");
  			
  				while(rs.next()){
  					lnk=(String)rs.getString("link");
  	            	    
  	            
  			}
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			return lnk;
         }
         
         public List selectUsrs(String adm)
         {
        	 Statement st;
        	 String lnk="";
        	 List usrs= new ArrayList();

  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select usrname from adduser where admin='"+adm+"'");
  			
  				while(rs.next()){
  					//lnk=(String)rs.getString("usrname");
  	            	usrs.add(rs.getString("usrname"));    
  	            
  			}
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			return usrs;
         }
         
         public List selectCategry(String adm)
         {
        	 Statement st;
        	 
        	 List ctgy= new ArrayList();

  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select distinct category from tag where username='"+adm+"'");
  			
  				while(rs.next()){
  					//lnk=(String)rs.getString("usrname");
  					ctgy.add(rs.getString("category"));    
  	            
  			}
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			return ctgy;
         }
         
         public String selectTrendLink(java.sql.Date bdat,java.sql.Date cdat)
         {
        	 Statement st;
        	 String lnk="",llink="";
        	
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select link from tag where bdate>='"+bdat+"' and bdate<='"+cdat+"' and access='Public'");
  			
  				while(rs.next()){
  					lnk=(String)rs.getString("link");
  	            	llink=llink+"*"+lnk;
  	            
  			}
  			}catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			return llink;
         }
         

         public List selectLatestTag(java.sql.Date bdat,java.sql.Date cdat)
         {
        	 Statement st;
        	
        	 List tagna= new ArrayList();
        	 List lin= new ArrayList();
        	 List res = new ArrayList();
        	
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select tagn,link from tag where bdate>='"+bdat+"' and bdate<='"+cdat+"' and access='Public'");
  			
  				while(rs.next()){
  					
 					tagna.add(rs.getString("tagn")); 
 					lin.add(rs.getString("link"));
 	            	             
	              }
 			    
 	 		     res.addAll(tagna);
 	 		     res.add("*");
 	 		     res.addAll(lin);
  				
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
 
  			return res;
         }
         
         public List selectTrendingTag(java.sql.Date bdat,java.sql.Date cdat)
         {
        	 Statement st;
        	
        	 List tagna= new ArrayList();
        	 List res = new ArrayList();
        	
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select distinct query from search where cdate>='"+bdat+"' and cdate<='"+cdat+"'");
  			
  				while(rs.next()){
  					
 					tagna.add(rs.getString("query"));     	
 	            	             
	              }
 			    
 	 		     res.addAll(tagna);
  				
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
 
  			return res;
         }
         
         
         public void insertUser(String adname,String uname,String pwd)
         {
        	 try{
        	 Statement st = con.createStatement();
             String qu = ("insert into adduser values('"+adname+"','"+uname+"','"+pwd+"')");
                             
             st.executeUpdate(qu); 
        	 }
        	 catch (SQLException e) {
  				e.printStackTrace();
  			}
         }
         
         public void insertFile(String uname,String uurl)
         {
        	 try{
        	 Statement st = con.createStatement();
             String qu = ("insert into upload values('"+uname+"','"+uurl+"')");
                             
             st.executeUpdate(qu); 
        	 }
        	 catch (SQLException e) {
  				e.printStackTrace();
  			}
         }
         
         public List searchTag(String un,String acc)
         {
        	 List li = new ArrayList();
        	 List tagna= new ArrayList();
        	 List res = new ArrayList();
        	 Statement st;
 			try {
 				st = con.createStatement();
 				ResultSet rs = st.executeQuery("select link,tagn from tag where username='"+un+"' and access='"+acc+"'");
 			
 				while(rs.next()){
 					li.add(rs.getString("link"));
 					tagna.add(rs.getString("tagn"));    	
 	            	             
	              }
 		     res.addAll(li);
 		     res.add("*");
 		     res.addAll(tagna);
 	          
 	            
 			} catch (SQLException e) {
 				e.printStackTrace();
 			}
 			return res;
         
         }
         
         public List searchAll(String una)
         {
        	 
        	 List li = new ArrayList();
        	 List tagna= new ArrayList();
        	 List res = new ArrayList();
        	 Statement st;
 			try {
 				st = con.createStatement();
 				
 				ResultSet rs = st.executeQuery("select link,tagn from tag where username='"+una+"' or access='Public'");
 				while(rs.next()){
 					li.add(rs.getString("link"));
 					tagna.add(rs.getString("tagn"));     	
 	            	             
	              }
 				
 				 res.addAll(li);
 	 		     res.add("*");
 	 		     res.addAll(tagna);
 	 		     
 	 		     //System.out.println("res"+res);
 	            
 			} catch (SQLException e) {
 				e.printStackTrace();
 			}
 			return res;
         
         }
         
         public void insertUserTags(String uname,String categor,String tag)
         {

        	 try{
            	 Statement st = con.createStatement();
                 String qu = ("insert into filteration values('"+uname+"','"+categor+"','"+tag+"')");
                                 
                 st.executeUpdate(qu); 
            	 }
            	 catch (SQLException e) {
      				e.printStackTrace();
      			}
         }
         
         public void insertSearchQuery(String qry,java.sql.Date dtec)
         {

        	 try{
            	 Statement st = con.createStatement();
                 String qu = ("insert into search values('"+qry+"','"+dtec+"')");
                                 
                 st.executeUpdate(qu); 
            	 }
            	 catch (SQLException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
         }
         public List category()
         {
        	 
        	
        	 List res = new ArrayList();
        	 Statement st;
 			try {
 				st = con.createStatement();
 				
 				ResultSet rs = st.executeQuery("select DISTINCT category from tag");
 				while(rs.next()){
 					res.add(rs.getString("category"));
 					   	
 	            	             
	              }
 				
 			
 	 		     
 	 		     //System.out.println("res"+res);
 	            
 			} catch (SQLException e) {
 				e.printStackTrace();
 			}
 			return res;
         
         }
         
         public String selectTag(String tag)
         {
        	 Statement st;
        	 String tg="",ctg="";
        	
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select tagn from tag where category='"+tag+"' and access='Public'");
  			
  				while(rs.next()){
  					tg=(String)rs.getString("tagn");
  	            	ctg=ctg+"*"+tg;    
  	            
  			}
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("tag= "+ctg);
  			return ctg;
         }
         
         public List showTag(String catg)
         {
        	 Statement st;
        	 String tg,lnk;
        	 List tagnls=new ArrayList();
        	 List lnkls=new ArrayList();
        	 List res=new ArrayList();
        	
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select tagn,link from tag where category='"+catg+"' and access='Public'");
  			
  				while(rs.next()){
  					tagnls.add(rs.getString("tagn"));
  					lnkls.add(rs.getString("link"));
  	            	
  	            
  			   }
  				res.addAll(tagnls);
  				res.add("*");
  				res.addAll(lnkls);
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			return res;
         }
         
         public String selectFile(String name)
         {
        	 Statement st;
        	 String url="";
  			try {
  				st = con.createStatement();
  				ResultSet rs = st.executeQuery("select ufileurl from upload where ufilename='"+name+"'");
  			
  				while(rs.next()){
  					url=(String)rs.getString("ufileurl");
  					System.out.println("url"+url);
  		  			 
  	            
  			}
  			}catch (SQLException e) {
  				e.printStackTrace();
  			}
  			return url;
         }
         
         
        }
