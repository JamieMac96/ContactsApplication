import java.util.*;
import java.io.*;
public class BasicOutline
{
	public static void main(String [] args) throws IOException
	{	
		String pattern = ".*\\.txt";
		
		if(args.length != 1)
			System.out.print("Java Usage: java ContactsApp Anyfile.txt");//these check the user's input.
		else if(!(args[0].matches(pattern)))
			System.out.print("Your command line argument must be a .txt file.");
		else
		{	
			Scanner sc = new Scanner(System.in);
			String createFile = "", fromMenu = "";
			File contacts = new File(args[0]);
	
			if(!contacts.exists())
			{
				System.out.print("No such file found. Would you like to create a file? (Y/y/N/n): ");
				createFile = sc.nextLine();//validate()
				if(createFile.equals("y"))
				{
					FileWriter aFileWriter = new FileWriter(args[0]);
					PrintWriter out = new PrintWriter(aFileWriter);
					out.print("");
					out.close();
					aFileWriter.close(); //(not sure if these should be closed here or later on)
				}
			}
			
			if(contacts.exists())//this is where the main part of the program begins.
			{	
				ArrayList<ArrayList<String>> contactInfo = new ArrayList<ArrayList<String>>();
				for(int i = 0; i < 6; i++)
					contactInfo.add(new ArrayList<String>());	//arrayList with 6 columns
				
				fromMenu = menuMethod();//validate(should probably call from the menuMethod itself)
				
				while(!fromMenu.equals("q"))
				{	
					if(fromMenu.equals("i"))
						insert();
					else if(fromMenu.equals("r"))
						remove();
					else if(fromMenu.equals("e"))
						edit();
					else if(fromMenu.equals("d"))
						display();
					else if(fromMenu.equals("s"))
						search();
			
					fromMenu = menuMethod();//validate
				}
			}		
		}
	}	
	
	public static void insert()
	{
		
		
	}
	
	public static void remove()
	{
		
		
	}
	
	public static void edit()
	{
		
		
	}
	
	public static void display()
	{
		
		
	}
	
	public static void search()
	{
		
		
	}
}

