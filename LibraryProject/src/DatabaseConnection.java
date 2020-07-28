import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
	
	private Connection conn;
	private Statement stmt;
	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
		
	}
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void startConnection() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/library_system","root","Priyal*1998");  
			//here library_sytem is database name, "root" is username and "Priyal*1998" is password  
			stmt=conn.createStatement();  
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	}
	public void closeConnection() {
		try {
			conn.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
