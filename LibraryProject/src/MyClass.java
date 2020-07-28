import java.util.Scanner;

public class MyClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Scanner scan = new Scanner(System.in);
			System.out.println("Who do you want to login as: admin or user?");
			
			String s=scan.nextLine();
			String userType= s.toLowerCase();
			
			if(userType.equals("admin"))
			{
				String adminPassword="admin123";
				System.out.println("Enter your password:");
				String pass=scan.next();
				if(pass.equals(adminPassword))
				{
					String adminActive="Yes";
					String adminActive1=adminActive.toLowerCase();
					AdminFunctions ad = new AdminFunctions();
					while(adminActive1.equals("yes"))
					{
						int option=ad.chooseOptions();
						
						if(option==1)
							ad.addBook();
						else if(option==2)
							ad.deleteBook();
						else if(option==3)
							ad.updateBook();
						else if(option==4)
							ad.updateStatus();
						else if(option==5)
							ad.issuedToStudent();
						else
							System.out.println("Kindly choose from the given options.");
						System.out.println("Do you want to continue?(Yes/No)");
						adminActive=scan.next();
						adminActive1=adminActive.toLowerCase();
					}
					System.out.println("Thank You");
				}
				else
					System.out.println("Wrong Password");
			}
			else if(userType.equals("user"))
			{
				System.out.println("Enter your ID:");
				String studID=scan.next();
				String userActive="Yes";
				String userActive1=userActive.toLowerCase();
				
				UserFunctions user = new UserFunctions();
				
				if(user.userLogin(studID))
				{
					while(userActive1.equals("yes"))
					{
						int opt = user.chooseOptions();
						if(opt==1)
							user.bookSearch();
						else if(opt==2)						
							user.issueBook(studID);
						else if(opt==3)
							user.checkStatus(studID);
						else if(opt==4)
							user.returnBook(studID);
						System.out.println("Do you want to continue?(Yes/No)");
						userActive=scan.next();
						userActive1=userActive.toLowerCase();
					}
					System.out.println("Thank You");
				}
					
			}
	
		}
}
	
