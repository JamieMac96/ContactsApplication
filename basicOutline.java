/*
import java.util.Scanner;
imprt java.io.*;
public class P2Outline
{
	public static void main(String [] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		FileWriter aFileWriter = new FileWriter("Contacts.txt");
		PrintWriter out = new PrintWriter(aFileWriter);
		File Contacts = new File(fileName);
		
		String fileName = "contacts.txt";
		String loadFile = "", createFile = "", firstContact = "", fromMenu = "";
		String [] callType = {"i", "r", "e", "d", "s"};
		
		System.out.print("would you like to try to load a contacts file (y/Y/n/N)");
		loadFile = sc.nextLine;
		if(loadFile.equals("y"))
		{
			if(Contacts.exists())
			{
				System.out.print("File exists");
			}
			else
			{
				System.out.print("No file exists. Would you like to create one? (y/Y/n/N): ");
				createFile = sc.nextLine();
				if(createFile.equals("y"))
				{
					System.out.print("Enter your first contact to create the file: ");
					firstContact = sc.nextLine();
					out.print(firstContact + "\n");
					out.close();
					aFileWriter.close(); (not sure if these should be closed here or later on)
				}
			}
			
			System.out.print("i(nsert)\tr(emove)\te(dit)\nd(isplay)\ts(earch)\tq(uit)");
			fromMenu = sc.nextLine();
			
			while(Contacts.exists() && !fromMenu.equals("q"))
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
				
				System.out.print("i(nsert)\tr(emove)\te(dit)\nd(isplay)\ts(earch)\tq(uit)");
				fromMenu = sc.nextLine();
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

*/