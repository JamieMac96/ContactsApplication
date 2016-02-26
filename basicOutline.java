import java.util.*;
import java.io.*;
public class BasicOutline
{
	/* **Added in some validation methods. Let me know if they're not compatible with your methods.
		I have no problem changing them ** */
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
			String createFile = "", fileName = args[0], input = "";
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
					contactInfo.add(new ArrayList<String>());	//arrayList with 6 columns
				
				if(alreadyExisted)
					getData(contacts);
				
				elements = menuMethod(input);//validate(should probably call from the menuMethod itself)

				while(!elements[0].equals("q"))
				{
					if(input.startsWith("i")) 
						insert(elements);
					else if(elements[0].equals("r"))
						remove();
					else if(elements[0].equals("e"))
						edit();
					else if(elements[0].equals("d"))
						display();
					else if(elements[0].equals("s"))
					search();
					
				elements = menuMethod(input);
				}
				moveToFile(fileName);
			}		
		}
	}	
	
	public static String insert(String[] elements)
	{
		System.out.print("INSERTIONBEGINS");

		String contactDetails = "", email = "", forename = "", surname = "", postalAddress = "", landline = "", mobile = "";
		boolean validEmail = false;
		boolean validPhone = false;
		int type;

		elements[1] = surname;
		elements[2] = forename;

		for(int i = 0; i < elements.length; i++) {
			type = findType(elements[i]);
			if(type == 0)	elements[i] = "";
			else if(type == 3) landline = elements[i];
			else if(type == 4) mobile = elements[i];
			else if(type == 5) email = elements[i];
			else if(type == 6) postalAddress = elements[i];
		}

		if(elements[1] == null || elements[2] == null) {
			System.out.print("You must provide both a forename and a surname for your entry");
		}

		validEmail = valEmail(email);
		if(!validEmail)
		System.out.print("You have entered an invalid email, please try again.");

		/*validLandline = valNumber(landline, 1);
		if(!validLandline)
		System.out.print("You have entered an invalid landline, please try again.");

		validMobile = valNumber(mobile, 0);
		if(!validMobile)
		System.out.print("You have entered an invalid mobile number, please try again.")*/


		
		System.out.println(forename);
		System.out.println(surname);
		System.out.println(landline);
		System.out.println(mobile);
		System.out.println(email);
		System.out.println(postalAddress);
		
		
		return contactDetails;
	}
	
	public static void remove()
	{
		/*
			to validate the non name entry you should call the findType() method then call the relevant validation method.
			if the entry are valid then remove the contact from the arraylist.
		*/
		
	}
	
	public static void edit()
	{
		/*
			check if the data entered is in the multi d arrayList
			if it is then call the validation method for the piece of information that will be replacing the old information.
		*/
		
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
            System.out.println("_______________________"+
            "\n"+"contact "+(counter+1));
            counter++;
            for(int j = 0; j < contactInfo.size() ; j++ )
            {
				System.out.println(InfoTypes[j]+contactInfo.get(j).get(i));
            }
            if((i+1) % 4 == 0)
            {
                Screens++;
                System.out.println("_______________________"+
                "\n"+ TotalContacts+" total contacts.");
                if(!(TotalContacts % 4 == 0 ))
                {
                    System.out.println(Screens+" out of "+ ((TotalContacts/4)+1)+" Screens displayed.");
                }
                else
                {
                    System.out.println(Screens+" out of "+ ((TotalContacts/4))+" Screens displayed.");
                }
                System.out.println("Hit space to continue.");//didn't appear to be working for any character
                Scanner red = new Scanner(System.in);
                String next = red.nextLine();
            }
        }
        System.out.println("\n"+"Returning to menu:");
    }
	
	public static void search()
    {
     
    }
	
	public static String[] menuMethod(String input)
	{
		/* Simple menu method that will repeat if user enters an invalid inpuut - William O'Leary*/
		boolean valid = false;
		Scanner in = new Scanner(System.in);
		String [] elements = new String[0];
		while(!valid)
		{
			System.out.println("i(nsert)" + "\t" + "r(emove)" + "\t" + "e(dit)" + "\n"+
			"d(isplay)" + "\t" + "s(earch)" + "\t" + "q(uit)");
			input = in.nextLine();
			elements = input.split(",");
			String options = "iredsq";
			if (!(options.contains(input.substring(0,1))))
				System.out.println("Error! Please enter the first letter of one of the menu options");
			else if(elements.length < 1 || elements.length > 7)
				System.out.println("Error! you can enter at");
			else
			valid = true;
		}
		elements[0].replace("i |r |e |d |s |q ", "");
		return elements;
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
		
			number = number.trim();
			numberSplit = number.split(" |-");
			length = numberSplit[1].length();
			if((length ==  7 && type == 0)|| type == 1 && (length > 4 && length < 8))
				for(int i = 0;i < validCases.length; i++)
					if(validCases[i].equals(numberSplit[0]))
						valid = true;		
		return valid;
	}
	
	public static boolean valEmail(String email) 
	{
		String pattern  = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
		boolean valid = false;
		if(email.matches(pattern))
			valid = true;
			
		return valid;
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
	
	public static int findType(String pieceOfData)
	{
		//types landline = 2, mobile = 3, emaill = 4, postal = 5.I chose theese numbers because they
		//can be used as a subscript for the multi D arraylist(not sure if useful).
		int type = 0;
		String pattern1 = "{2,4}[0-9]-{5,7}[0-9]";
		pieceOfData = pieceOfData.trim();
		
		if(pieceOfData.matches(pattern1))
			type = 2;
		else if(pieceOfData.startsWith("08"))
			type = 3;
		else if(pieceOfData.contains("@"))
			type = 4;
		else if(pieceOfData.contains(" "))
			type = 5;
		
		//if type is zero we can assume it's an invalid piece of data(once you're not passing the wrong arguments to it)
		return type;
	}
}