import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserFunctions {

	DatabaseConnection db=new DatabaseConnection();
	Connection con;
	Statement stmt, st1;
	ResultSet rs, rs1;
	Scanner scan = new Scanner(System.in);
	public UserFunctions() {
		// TODO Auto-generated constructor stub
		db.startConnection();
		con=db.con;
		stmt=db.stmt;
		st1=db.stmt;
	}
	
	public boolean userLogin(String studID) {
		try {
			rs=stmt.executeQuery("select * from lib_member");
			int flag=0;
			while(rs.next())
			{
				if(studID.equals(rs.getString(1)))
				{
					flag=1;
					System.out.println("Enter your password");
					String studPassword=scan.next();
					if(studPassword.equals(rs.getString(3)))
					{
						flag=2;
						return true;
					}
					else
					{
						System.out.println("The password you entered is incorrect.");
						flag=1;
						break;
					}
				}
			}
			if(flag==0)
			{
				System.out.println("Invalid student ID");
			}
			return false;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public int chooseOptions() {
		System.out.println("Choose any one of the following options:");
		System.out.println("1 :-  To search for a book.");
		System.out.println("2 :-  To issue a book.");
		System.out.println("3 :-  To check the status of the books issued.");
		System.out.println("4 :-  To return a book.");
		int opt=scan.nextInt();
		return opt;
	}
	
	public void bookSearch() {
		try{
			System.out.println("Enter the ID of the desired book.");
			String bookId = scan.next();
			int tem=0;
			rs=stmt.executeQuery("select * from books");
			while(rs.next())
			{
				if(bookId.equals(rs.getString(1)))
				{
					tem=1;
					System.out.println(rs.getString(1)+"         "+rs.getString(2)+"         "+rs.getString(3)+"         "+rs.getString(4));
					break;
				}
			}
			if(tem==0) 
			{
				System.out.println("Invalid book ID");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void issueBook(String studID) {
		try {
			String noOfBooks = "select * from lib_transaction where trn_mem_id = ?";
			PreparedStatement noOfBooksQuery = con.prepareStatement(noOfBooks);
			
			noOfBooksQuery.setString(1, studID);
			ResultSet numBooks=noOfBooksQuery.executeQuery();
			
			int cnt=0;
			while(numBooks.next())
			{
				cnt++;
			}
			if(cnt==4)
			{
				System.out.println("You already have four books issued under your ID.");
			}
			else
			{
				System.out.println("Enter the book ID");
				String bookId=scan.next();
				
				String query = "select * from books where book_id = ?";
				PreparedStatement pre = con.prepareStatement(query);
				pre.setString(1, bookId);
				ResultSet rs1 = pre.executeQuery();
				
				String s1;
				if(rs1.next())
				{
					String s2=rs1.getString(4);
					s1=s2.toLowerCase();
		
					if(s1.equals("issued"))
					{
						System.out.println("Required book is not available.");
					}
					else
					{
						
						String q= "SELECT DATE_ADD( ? , INTERVAL 15 DAY )";
						PreparedStatement pre1 = con.prepareStatement(q);
						
						rs=stmt.executeQuery("select curdate()");
						
						if(rs.next())
						pre1.setDate(1, rs.getDate(1));
						ResultSet rs2 = pre1.executeQuery();
						
						String q1=" Insert into lib_transaction (trn_mem_id,trn_book_id,trn_issue_dt,trn_receive_dt)"+ "VALUES( ?,?,?,?)";
						PreparedStatement pre2 = con.prepareStatement(q1);
						pre2.setString(1, studID);
						pre2.setString(2, bookId);
//								if(rs.next())
						pre2.setDate(3, rs.getDate(1));
						if(rs2.next())
						pre2.setDate(4, rs2.getDate(1));
						
						pre2.execute();
						System.out.println("Your book is issued");
						
						String q2= "update books set book_status = ? where book_id = ? ";
						PreparedStatement pre3 = con.prepareStatement(q2);
						pre3.setString(1, "Issued");
						pre3.setString(2, bookId);
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void checkStatus(String studID) {
		try {
			rs1=stmt.executeQuery("select * from lib_transaction");
			String query="select * from lib_transaction where trn_mem_id = ? ";
			PreparedStatement pre = con.prepareStatement(query);
			pre.setString(1, studID);
			
			rs1=pre.executeQuery();
			int i=1;
			String[] arr = new String[50];
			int cnt=0;
			while(rs1.next())  
			{
				arr[cnt]=rs1.getString(2);
				cnt=cnt+1;
			}
			if(cnt==0)
				System.out.println("No books are issued");
			else {
				query="select * from books";
				pre = con.prepareStatement(query);
				
				rs1=pre.executeQuery();
				int k=0;
				while(rs1.next() && k<cnt) {
					if(rs1.getString(1).equals(arr[k]))
					{
						k+=1;
						System.out.println("Book "+i+": "+rs1.getString(1)+"   "+rs1.getString(2));
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void returnBook(String studID) {
		try {
			System.out.println("Enter the book ID");
			String bookID = scan.next();
			
			String q1 = "select * from lib_transaction where trn_book_id = ?";
			PreparedStatement pre = con.prepareStatement(q1);
			pre.setString(1, bookID);
			
			rs= pre.executeQuery();
			if(rs.next())
			{
				if(studID.equals(rs.getString(1)))
				{
					ResultSet rs2=stmt.executeQuery("select curdate()");
					
					String q2 = "SELECT DATEDIFF( ? , ? )";
					PreparedStatement pre1 = con.prepareStatement(q2);
					if(rs2.next())
					pre1.setDate(1, rs2.getDate(1));
					pre1.setDate(2, rs.getDate(4));
					
					ResultSet rs1= pre1.executeQuery();
					
					if(rs1.next())
					{
						int difference = rs1.getInt(1);
						if(difference<=0)
						{
							System.out.println("No fine is applicable to you.");
							System.out.println("Your book is successfully returned");
							String q3 = "delete from lib_transaction where trn_book_id = ?";
							PreparedStatement pre2 = con.prepareStatement(q3);
							pre2.setString(1, bookID);
							
							
							pre2.execute();
						}
						else
						{
							int fine=difference*5;
						
							System.out.println("You have a fine of Rs."+fine+" for the book ID: "+bookID);
						}
					}
				}
				else
				{
					System.out.println("You are not the owner of the book.");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
