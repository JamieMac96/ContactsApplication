import java.util.*;
import java.io.*;
public class ContactsApplication
{
	public static String firstLetter = "";
	public static ArrayList<ArrayList<String>> contactInfo = new ArrayList<ArrayList<String>>();
	public static Scanner sc = new Scanner(System.in);
	public static void main(String [] args) throws IOException
	{	
		String pattern = ".*\\.txt";
		boolean alreadyExisted = true;
		
		if(args.length != 1)
			System.out.print("Java Usage: java ContactsApp Anyfile.txt");//these check the user's input.
		else if(!(args[0].matches(pattern)))
			System.out.print("Your command line argument must be a .txt file.");
		else
		{	
			String [] elements;
			String createFile = "", fileName = args[0];
			File contacts = new File(fileName);
	
			if(!contacts.exists())
			{
				alreadyExisted = false;
				System.out.print("No such file found. Would you like to create a file? (Y/y/N/n): ");
				createFile = valOne();
				if(createFile.equals("y"))
				{
					FileWriter aFileWriter = new FileWriter(fileName);
					PrintWriter out = new PrintWriter(aFileWriter);
					out.print("");
					out.close();
					aFileWriter.close(); //(not sure if these should be closed here or later on)
				}
			}
				
			
			if(contacts.exists())//this is where the main part of the program begins.
			{	
				for(int i = 0; i < 6; i++)
					contactInfo.add(new ArrayList<String>());	
				
				if(alreadyExisted)
					getData(contacts);
				
				elements = menuMethod();
				while(!firstLetter.equals("q"))
				{	
					if(firstLetter.equals("i"))
						insert(elements);
					else if(firstLetter.equals("r"))
						remove();
					else if(firstLetter.equals("e"))
						edit();
					else if(firstLetter.equals("d"))
						display();
					else if(firstLetter.equals("s"))
						search(elements);
			
					elements = menuMethod();
				}
				moveToFile(fileName);
			}		
		}
	}
	
	public static void getData(File contacts) throws IOException
	{
		Scanner in = new Scanner(contacts);
		String [] lineOfFile;
		
		while(in.hasNext())
		{
			lineOfFile = in.nextLine().split(",", -1); 
			for(int i = 0; i < 6; i++)
				contactInfo.get(i).add(lineOfFile[i]);
		}
	}
	
	public static String [] menuMethod()
	{
		/* Simple menu method that will repeat if user enters an invalid inpuut - William O'Leary*/
		boolean valid = false;
		String input= "";
		String [] elements = new String[0];
		while(valid == false)
		{
			System.out.println("\nMAIN MENU:");
			System.out.println("Please enter an operation "+"\n"+
			"you wish to use.");
			System.out.println("i(nsert)"+"\t"+"r(emove)"+"\t"+"e(dit)"+"\n"+
			"d(isplay)"+"\t"+"s(earch)"+"\t"+"q(uit)");
			input = sc.nextLine();
			valid = validateAll(input);	
			if(valid)
			{
				
				input = input.trim().replaceFirst(firstLetter + " ", "");
				elements = input.split(",");
			}	
		}
		return elements;
	}
	
	public static boolean validateAll(String input)
	{
		String options = "iredsq";
		String alphabetic = "\\w+\\.?";
		firstLetter = input.substring(0,1).toLowerCase();
		String [] elements;
		int type = 0;
		boolean valid = true;
		
		input = input.trim().replaceFirst(firstLetter + " ", "");
		elements = input.split(",");
		
		if(!(options.contains(firstLetter)))
		{	
			System.out.println("Error! Please enter the first letter of one of the menu options\n");
			valid = false;
		}	
		else if(elements.length > 6)
		{
			System.out.print("Error! your contact cannot have more than 6 pieces of information.");
			valid = false;
		}	
		else if(elements.length < 3)
		{
			if(firstLetter.equals("e") || firstLetter.equals("i") || firstLetter.equals("r"))
			{	
				System.out.print("Error! This action requires between three and six pieces of contact information.\n");
				valid = false;
			}	
			else if(firstLetter.equals("s") && elements.length == 2)
			{	
				System.out.print("Error! When searching, only one piece of information can be searched for at once.");
				valid = false;
			}	
		}	
		if(elements.length != 0)
		{
			for(int i = 0; i < elements.length && valid; i++)
			{
				type = findType(elements[i]);
				if((type != 0 && elements.length == 1 && firstLetter.equals("s")))
					valid = true;
				else if(type == 2)
					valid = valNumber(elements[i], 1);
				else if(type == 3)
					valid = valNumber(elements[i], 0);
				else if(type == 4)
					valid = valEmail(elements[i]);
				else if(!(type == 1 || type == 5))
					valid = false;
				if(!valid)
					System.out.print("\nError! One or more of your entries was invalid.\n");
			}	
		
		}
		return valid;
	}
	
	public static int findType(String pieceOfData)
	{
		//types; names = 1, landline = 2, mobile = 3, emaill = 4, postal = 5.I chose theese numbers because they
		//can be used as a subscript for the multi D arraylist(not sure if useful).
		int type = 0;
		String namePattern = "^[\\p{L} .'-]+$";
		pieceOfData = pieceOfData.trim();
		
		if(pieceOfData.matches(namePattern))
			type = 1;
		else if(pieceOfData.startsWith("08"))
			type = 3;
		else if(pieceOfData.startsWith("06"))
			type = 2;
		else if(pieceOfData.contains("@"))
			type = 4;
		else if(pieceOfData.contains(" "))
			type = 5;
		
		//if type is zero we can assume it's an invalid piece of data(once you're not passing the wrong arguments to it)
		return type;
	}
	
	public static boolean valEmail(String email) 
	{
		String pattern  = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
		boolean valid = false;
		if(email.matches(pattern))
			valid = true;
			
		return valid;
	}
	
	public static String valOne()
	{
		String createFile = "";
		createFile = sc.nextLine();
		createFile = createFile.toLowerCase().trim();
		while(!createFile.equals("y") && !createFile.equals("n"))
		{
			System.out.print("\nInvalid Input. Enter (Y/y/N/n): ");
			createFile = sc.nextLine();
		}
		return createFile;
	}
	
	public static boolean valNumber(String number, int type) //one argument is the number one indicates if it's a landline or mobile. 0=mobile
	{		
		String [] numberSplit;
		String [] validCases;
		int length = 0;
		boolean valid = false;
		if(type == 0)
			validCases = new String[]{"083", "085", "086", "087", "089"};
		
		else
			validCases = new String[]{"01", "02", "021", "023", "024", "025", "026", "027", "028", "029", "0402", "0404", "041", "042", 
				"043", "044", "045", "046", "047", "049", "0504", "0505", "051", "052", "053", "056", "057", "058", "059",
				"061", "062", "063", "064", "065", "066", "067", "068", "069", "071", "074", "090", "091", "093", "094",
				"095","096", "097", "098", "099"};
		
		numberSplit = number.split("-");
		length = numberSplit[1].length();
		if((length ==  7 && type == 0)|| type == 1 && (length > 4 && length < 8))
			for(int i = 0;i < validCases.length; i++)
				if(validCases[i].equals(numberSplit[0]))
					valid = true;		
		
		
		return valid;
	}
	
	public static void display()
    {
        String [] InfoTypes = {"Surname:   \t","Forename:\t","Landline:\t",
        "Mobile:     \t", "Email address:\t", "postal address:\t"};
        int counter = 0;
        int TotalContacts = contactInfo.get(0).size();
        int Screens = 0;
        for(int i = 0; i <contactInfo.get(0).size(); i++)
        {
            DisplayProcess(counter,TotalContacts,Screens, InfoTypes, i);
            counter++;
        }
        System.out.println("\n"+ "All contacts displayed,"+"\n"+"Returning to menu:");
    }
	
	public static void insert(String[] elements)
	{
		
	}
	
	public static void remove()
	{
		
		
	}
	
	public static void edit()
	{
		
		
	}
	
	public static void search(String [] elements)
    {
        // main search driver calls on the searchmenu and searchdisplay methods - William O'Leary
        int Choice;
		String Input = elements[0];
        if(Input.startsWith("08"))
            Choice = 3;
        else if(Input.startsWith("0"))
            Choice = 2;
        else if(Input.startsWith("@"))
            Choice = 4;
        else
            Choice = 0;
        SearchDisplay(Choice, Input);
        System.out.println("\n"+"Returning to main menu.");
    }
	
	public static void SearchDisplay(int TypeNum, String Input) 
    {
        /*display method for the search operation checks if the input the user
        provided is in the file and displays the contact that matches or else states contact not found - William O'Leary*/
        String [] InfoTypes = {"Surname:   \t","Forename:\t","Landline:\t",
        "Mobile:     \t", "Email address:\t", "postal address:\t"};
        int counter = 0;
        int TotalContacts = contactInfo.get(0).size();
        int Screens = 0;
        boolean found = false;
        for(int i = 0; i <TotalContacts; i++)
        {
            if(contactInfo.get(TypeNum).get(i).contains(Input))
            {
                DisplayProcess(counter,TotalContacts,Screens, InfoTypes, i);
                counter++;
                found = true;
            }
        }
        if(found == false)
            System.out.println("\nContact not found.\nNote you can only search using:" +
				"\nfirst letters of a surname, landline area codes, mobile prefixes, and email domain names.");
    }
	
	 public static void DisplayProcess(int counter,int TotalContacts, int Screens, String [] InfoTypes, int i)
    {
        // display processing method which is called upon to print contacts wanted- in seperate method as its used multiple times - William O'Leary
        System.out.println("_______________________"+
        "\n"+"contact "+(counter+1));
        for(int j = 0; j < contactInfo.get(i).size(); j++ )
            System.out.println(InfoTypes[j]+contactInfo.get(j).get(i));
        if((counter+1) % 4 == 0)
        {
            Screens++;
            System.out.println("_______________________"+
            "\n"+ TotalContacts+" total contacts.");
            if(!(TotalContacts % 4 == 0 ))
                System.out.println(Screens+" out of "+ ((TotalContacts/4)+1)+" Screens displayed.");
            else
                System.out.println(Screens+" out of "+ ((TotalContacts/4))+" Screens displayed.");
            System.out.println("Hit any character to continue.");
            String next = sc.nextLine();
        }
    }
	
	public static void moveToFile(String fileName) throws IOException
	{
		PrintWriter toFile = new PrintWriter(fileName);
		
		for(int i = 0; i < contactInfo.get(0).size(); i++)
		{
			for(int j = 0;j < 5; j++)
				toFile.print(contactInfo.get(j).get(i) + ",");
			toFile.println(contactInfo.get(5).get(i));//so that no comma is put at the end of the line.
		}
		toFile.close();
	}
}