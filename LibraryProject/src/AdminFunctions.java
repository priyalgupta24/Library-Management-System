import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class AdminFunctions {
	
	private DatabaseConnection db=new DatabaseConnection();
	private Connection conn;
	private Statement stmt;
	private ResultSet resSet, resSet1;
	Scanner scan = new Scanner(System.in);
	public AdminFunctions() {
		// TODO Auto-generated constructor stub
		db.startConnection();
		conn=db.getConn();
		stmt=db.getStmt();
	}
	
	public int chooseOptions() {
		System.out.println("Choose any one of the following options:");
		System.out.println("1: To add a book in the database");
		System.out.println("2: To delete a book from the database");
		System.out.println("3: To update details of a book");
		System.out.println("4: To update availability of a book");
		System.out.println("5: To view books issued to a particular student");
		int option=scan.nextInt();
		return option;
	}
	
	public void addBook() {
		try{
			System.out.println("Enter book ID: ");
			String bookId=scan.nextLine();
			bookId=scan.nextLine();
			System.out.println("Enter book name: ");
			String bookName=scan.nextLine();
			System.out.println("Enter book author: ");
			String bookAuthor=scan.nextLine();
			String bookStatus="Available";
			
			String query=" insert into books (book_id, book_title, book_author, book_status)" + " values (?, ?, ?, ?)"  ;
			
			PreparedStatement pre = conn.prepareStatement(query);
			pre.setString(1, bookId);
			pre.setString(2, bookName);
			pre.setString(3, bookAuthor);
			pre.setString(4, bookStatus);
			
			pre.execute();
			
			resSet=stmt.executeQuery("select * from books");  
			while(resSet.next())  
			System.out.println(resSet.getString(1)+"         "+resSet.getString(2)+"                   "+resSet.getString(3)+"               "+resSet.getString(4));
			
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	}
	
	public void deleteBook() {
		try{ 
			System.out.println("Enter the ID of the book you want to delete. ");
			String bookId = scan.next();
			String query= "delete from books where book_id = ? ";
			
			PreparedStatement pre = conn.prepareStatement(query);
			pre.setString(1, bookId);
			
			pre.execute();
			resSet=stmt.executeQuery("select * from books");  
			while(resSet.next())  
			System.out.println(resSet.getString(1)+"         "+resSet.getString(2)+"                   "+resSet.getString(3)+"               "+resSet.getString(4));
		
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}	
	}
	
	public void updateBook() {
		try {
			System.out.println("Enter the ID of the book you want to update.");
			String bookId=scan.next();
			String cont="yes";
			while(!cont.equals("exit"))
			{
				System.out.println("1 :- To update book ID");
				System.out.println("2 :- To update book title");
				System.out.println("3 :- To update book author");
				int choose=scan.nextInt();
				if(choose==1)
				{
					System.out.println("Enter new ID.");
					String bookIdNew = scan.next();
					resSet=stmt.executeQuery("select * from books");  
					int temp1=0;
					while(resSet.next())  
					{
						if(bookIdNew.equals(resSet.getString(1)))
						{
							temp1=1;
							System.out.println("This book ID already exists");
							break;
						}
					}
					if(temp1==0)
					{
						String query= "update books set book_id = ? where book_id = ? ";
						PreparedStatement pre = conn.prepareStatement(query);
						pre.setString(1, bookIdNew);
						pre.setString(2, bookId);
						
						pre.execute();
						System.out.println("Updated");
					}
				}
				else if(choose==2)
				{
					System.out.println("Enter new title.");
					String bookNameNew = scan.nextLine();
					bookNameNew = scan.nextLine();
					String query= "update books set book_title = ? where book_id = ? ";
					PreparedStatement pre = conn.prepareStatement(query);
					pre.setString(1, bookNameNew);
					pre.setString(2, bookId);
					
					pre.execute();
					System.out.println("Updated");
				}
				else if(choose==3)
				{
					System.out.println("Enter new author.");
					String bookAuthorNew = scan.nextLine();
					bookAuthorNew = scan.nextLine();
					String query= "update books set book_author = ? where book_id = ? ";
					PreparedStatement pre = conn.prepareStatement(query);
					pre.setString(1, bookAuthorNew);
					pre.setString(2, bookId);
					
					pre.execute();
					System.out.println("Updated");
				}
				System.out.println("To update further type 'yes' else type 'exit'.");
				String ss=scan.next();
				cont=ss.toLowerCase();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void updateStatus() {
		try {
			System.out.println("Enter the ID of the book whose status you want to update. ");
			String bookId = scan.next();
			System.out.println("Enter the new status Issued or Available");
			String bookStatus = scan.next();
			String query= "update books set book_status = ? where book_id = ? ";
			PreparedStatement pre = conn.prepareStatement(query);
			pre.setString(1, bookStatus);
			pre.setString(2, bookId);
			
			pre.execute();
			
			System.out.println("Updated");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void issuedToStudent() {
		try {
			System.out.println("Enter the ID of a student to view the books issued to him/her.");
			String studentId = scan.next();
			int flag=0;
			String query="select * from lib_transaction where trn_mem_id = ? ";
			PreparedStatement pre = conn.prepareStatement(query);
			pre.setString(1, studentId);
			
			resSet1=pre.executeQuery();
			int i=1;
			String[] arr = new String[50];
			int cnt=0;
			while(resSet1.next())  
			{
				arr[cnt]=resSet1.getString(2);
				cnt=cnt+1;
			}
			if(cnt==0)
				System.out.println("No books are issued to this particular student.");
			else {
				query="select * from books";
				pre = conn.prepareStatement(query);
				
				resSet1=pre.executeQuery();
				int k=0;
				while(resSet1.next() && k<cnt) {
					if(resSet1.getString(1).equals(arr[k]))
					{
						k+=1;
						System.out.println("Book "+i+": "+resSet1.getString(1)+"   "+resSet1.getString(2));
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Hey");
			System.out.println(e);
		}
	}
}
