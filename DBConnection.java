

package DBServices;



import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    private Connection con = null;
    private static DBConnection dbc;
    DBConnection() {
	  	try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/collaborativetag", "root", "root");
	  	} catch(Exception e) {
			e.printStackTrace();
		}
	}
    public static DBConnection getInstance(){
    	if(dbc==null){
    		synchronized (DBConnection.class) {
				if(dbc==null)
					dbc=new DBConnection();
			}
    	}
    	return dbc;
	}
    public Connection getConnection() {
    	return con;
    }
}