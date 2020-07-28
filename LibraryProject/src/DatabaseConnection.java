import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
	
	Connection con;
	Statement stmt, st1;
	ResultSet rs, rs1,rs2;
	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
		
	}
	public void startConnection() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/library_system","root","pass");  
			//here library_sytem is database name, "root" is username and "pass" is password  
			stmt=con.createStatement();  
			st1 = con.createStatement();
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	}
	public void closeConnection() {
		try {
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
