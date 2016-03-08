import java.util.*;
import java.io.*;
public class ContactsApplication
{
    public static String firstLetter = "";
    public static ArrayList<ArrayList<String>> contactInfo = new ArrayList<ArrayList<String>>();
    public static Scanner sc = new Scanner(System.in);
	public static void main(String [] args) throws IOException
    {
	/*
		The main method first checks if the user has entered entered a command line argument. If they have not
		an error message is displayed and the program ends. If the user did enter a command line argument 
		the program checks if it matches the .txt file pattern. After this we move on to checking if the file 
		that the user specified in the command line argument actually exists. If not the user can create the 
		file. After this process and if a file exists we move into the area of the program that drives the main
		functionality of the program.first we set the multi D arraylist to have 6 sublists and pull in any data from
		the file that might exist. Then the first call of the menuMethod occurs after which we move into a while
		loop that runs until the user decides to quit by entering 'q'. During each iteration of the while one of 
		main functions of the program is called and then the menu is displayed once again so that the user can 
		choose once again. When the loop is terminated the updated data in the arraylist is moved to the file.
	*/	
        String pattern = "([^\\s]+(\\.(?i)(txt|yml|csv))$)";
        boolean alreadyExisted = true;
        
        if(args.length != 1)
			System.out.print("Java Usage: java ContactsApp Anyfile.txt");
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
                    PrintWriter out = new PrintWriter(fileName);
                    out.print("");
                    out.close();
                }
            }
            if(contacts.exists())
            {
				instructions();
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
						editRemoveProcess(elements, "removed.");
                    else if(firstLetter.equals("e"))
						editRemoveProcess(elements, "edited.");
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
	
	
	public static void instructions()
	{
	/*	This method gives the user the option of seeing a table that gives an outline of how to use the application.
		It is called at the beginnig of the program and has no arguments and no return. - Rory Egan*/
		boolean invalidInput = true;
		String response;
		String exit = "";

		System.out.print("\nWould you like instructions on how to use this application? (Y/y/N/n): ");
		response = valOne();
		if(response.equals("y") || response.equals("n"))
		{
			invalidInput = false;
			if(response.equals("y")) 
			{
				System.out.println("*************************************************************************************************");
				System.out.println("************************************** INSTRUCTION FORMAT ***************************************");
				System.out.println("*QUIT: q                                                                                        *");
				System.out.println("*DISPLAY: d                                                                                     *");
				System.out.println("*EDIT: e Surname,Forename,Contact Data,New Contact Data                                         *");
				System.out.println("*REMOVE: r Surname,Forename,Contact Data                                                        *");
				System.out.println("*INSERT: i Surname,Forename,Contact Data,Contact Data,Contact Data,Contact Data                 *");
				System.out.println("***Note: At least one piece of contact data must be entered to insert a contact                 *");
				System.out.println("***Note: The four contact data categories are: Landline, Mobile, Email and Postal Address       *");
				System.out.println("***Note: postal addresses must contain at least two pieces of info and must be space seperated  *");
				System.out.println("*************************************************************************************************");
				System.out.println("\n\nPress any key to go to main menu.");
				exit = sc.nextLine();
			}
		}
    }
    
    public static void getData(File contacts) throws IOException
    {
		//This method fills the contactInfo arraylist with the data from the file using a nested for inside a while loop. - Jamie MacManus
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
			System.out.println("\n"+"Please enter an operation "+"\n"+
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
	/*
		This method has the purpose of validating the user's input. It is called from the menuMethod with its
		only argument being the user's input. It returns the boolean valid. The main functionality comes from
		a large body of if else statements that specify cases in which the input would be considered invalid.
		At the end of the method another method is called which validates further. -Jamie MacManus
	*/
        String options = "iredsq";
        String alphabetic = "\\w+\\.?";
		String pattern = "[i|r|e|d|s|q] .+";
		String pattern2 = "[a-zA-Z]+";
        String [] elements;
        boolean valid = true;
		
		input = input.trim();
		if(!(input.equals("q") || input.equals("d")))
		{
			if(input.length() < 3)
			{	
				valid = false;
				System.out.print("Error! No appropriate input or arguments found.\n");
			}
			else if(!input.matches(pattern))
			{
				valid = false;
				System.out.print("Error! Invalid input or incorrect format.\n");
			}	
		}
		if(valid)
		{
			firstLetter = input.substring(0,1).toLowerCase();
			input = input.trim().replaceFirst(firstLetter, "");
			elements = input.split(",");
			for(int i = 0; i < elements.length; i++)
				elements[i] = elements[i].trim();
			if(elements.length > 6)
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
				else if(firstLetter.equals("s"))
				{
					if((elements[0].startsWith("0")) && elements[0].length() > 4)
					{
						valid = false;
						System.out.print("Error! Number prefixes can be at most 4 numbers long.\n");
					}	
					else if(elements[0].length() != 1 && elements[0].matches(pattern2))
					{	
						valid = false;
						System.out.print("Error! You can only search by name using first letters of surnames.\n");
					}	
					else if(!elements[0].startsWith("@") && elements[0].contains("@"))
					{
						valid = false;
						System.out.print("Error! You can only search for emails using @\nfollowed by the domain name eg @gmail.com.\n");
					}	
				}	
			}	
			else if(firstLetter.equals("e"))
			{
				if(elements.length != 4)
				{	
					System.out.print("Error! When editing a contact you must enter the the name of the contact followed by:"
						+ "\nThe old contact data and the new contact data.\n");
					valid = false;
				}
				else if(findType(elements[2],0) != findType(elements[3],0))
				{
					System.out.print("Error! The new piece of information must be of the same type.\n");
					valid = false;
				}	
			}
			if(valid && (!elements[0].isEmpty()))
				valid = checkTypes(elements ,valid);//method was getting horrifically long.
		}
		return valid;
    }
	
	
    public static int findType(String pieceOfData, int callType)
    {
    /*
		This method takes the arguments pieceOfData(user input data) and callType
		(distinguishes the origins of the call). It returns the type of contact data that was passed
		down to it as an int. A series of if else's are used here once again to determine the type
		of contact data. - Jamie MacManus
	*/
        int type = 0;
        String namePattern = "^[\\p{L} .'-]+$";
        pieceOfData = pieceOfData.trim();
		if(callType == 0)
		{
			if(pieceOfData.contains(" "))
				type = 5;
			else if(pieceOfData.matches(namePattern))
				type = 1;
		}	
		if(pieceOfData.startsWith("08"))
			type = 3;
        else if(pieceOfData.startsWith("0"))
			type = 2;
        else if(pieceOfData.contains("@"))
			type = 4;
        return type;
    }
	
	public static boolean checkTypes(String [] elements, boolean valid)
	{
	/*
		This method takes the string array elements, the boolean valid and returns the same boolean.
		It is called from the validateAll method and uses a for loop and a series of if else if 
		statements to check if each piece of data conforms to the rules that apply to it's type. -Jamie MacManus
	*/
		int [] types = {0,2,1,1,1,1};
		int type;
		for(int i = 0; i < elements.length && valid; i++)
		{
			type = findType(elements[i], 0);
			if(i < 2 && type != 1 && !("s".equals(firstLetter)))
			{
				valid = false;
				System.out.print("Error! Your first two arguments must be the contact's name\n");
			}	
			if((type != 0 && elements.length == 1 && firstLetter.equals("s")))
				valid = true;
			else if(type == 2)
				valid = valNumber(elements[i], 1);
			else if(type == 3)
				valid = valNumber(elements[i], 0);
			else if(type == 4)
				valid = valEmail(elements[i]);
			else if(!(type == 1 || type == 5))
			{	
				valid = false;
				System.out.print("We could not determine the type of contact data you entered.\n");
			}	
			if(type != 0 && !valid)
			{
				types[type]--;
				if(firstLetter.equals("e")&& i==2)
					types[type]++;
				if(types[type] < 0)
				{
					System.out.print("Error! You entered the same type of contact data twice.\n");
					valid = false;
				}	
			}	
		}
		return valid;
	}
    
    public static boolean valEmail(String email)
    {
	/*	Short method that takes an email address as an argument and returns a boolean that states
		whether or not it is valid. It is used to validate email addresses and uses a regex to do so.
		If an invalid email address is entered an error message is displayed.- Jamie MacManus
	*/
        String pattern  = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        boolean valid = false;
        if(email.matches(pattern))
			valid = true;
		if(!valid)
			System.out.print("Invalid email address.\n");
        return valid;
    }
    
    public static String valOne()
    {
		//simple validation for when the user is prompted for a yes or no answer. - Jamie MacManus
        String userInput = "";
        userInput = sc.nextLine().toLowerCase().trim();
        while(!userInput.equals("y") && !userInput.equals("n"))
        {
            System.out.print("\nInvalid Input. Enter (Y/y/N/n): ");
            userInput = sc.nextLine().toLowerCase().trim();
        }
        return userInput;
    }
   
    public static boolean valNumber(String number, int type) 
	{
		/*
		Takes the mobile number as a string and the type(landline or mobile) and returns whether or not it is valid using a boolean.
		First it checks which type of number is entered, populating the array validCases as necessary. Then we move into a if which if
		true triggers a for loop to check if the prefix entered is one of the valid cases.- Jamie MacManus
		*/
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
		
		if(number.matches("[0-9]+-[0-9]+")) 
		{
			numberSplit = number.split("-");
			length = numberSplit[1].length();
			if((length ==  7 && type == 0) || type == 1 && (length > 4 && length < 8))
				for(int i = 0;i < validCases.length; i++)
					if(validCases[i].equals(numberSplit[0]))
						valid = true;
			if(!valid)
			System.out.print("Invalid phone number.\n");		
		}
		else
			System.out.print("Error! Please Enter the prefix followed by a dash\nfollowed by the rest of your number\n");
		return valid;
	}
	
    public static void display()
    {
	/*	This method takes no arguments and has no return. A for loop is used to call DisplayProcess
		which performs the main functionality of displaying. Appropriate messages are output based 
		on what is found in the multi D arraylist -William O'Leary*/
        int counter = 0;
        int totalContacts = contactInfo.get(0).size();
        int Screens = 0;
        for(int i = 0; i <contactInfo.get(0).size(); i++)
        {
            DisplayProcess(counter,totalContacts,Screens, i);
            counter++;
        }
		if(contactInfo.get(0).size() > 0)
			System.out.println("\n"+ "All contacts displayed,"+"\n"+"Returning to menu:");
		else
			System.out.println("\nNo contacts found.\n\nReturning to menu:");
    }
    
	public static void insert(String[] elementsFromMenu)
	{
	/*	This method has the purpose of inserting contacts into the correct position in the multi D arraylist.
		It takes the elements array and has no return. - Rory Egan.*/
        int index;
		int type = 0;
        String current, next;
		String [] elements = new String[6];
        boolean locationFound = false;
		
		Arrays.fill(elements,"");
		
		for(int i = 0; i < elementsFromMenu.length; i++)
		{
			type = findType(elementsFromMenu[i], 0);
			if(i == 0)
				elements[0] = elementsFromMenu[0];
			else
				elements[type] = elementsFromMenu[i];
		}
		
		if(!repeatedUniqueInfo(elements)) 
		{
			if (contactInfo.get(0).size() == 0) 
			{
				for (int i = 0; i < elements.length; i++)
					contactInfo.get(i).add(0, elements[i]);
			} 
			else if (elements[0].compareToIgnoreCase(contactInfo.get(0).get(0)) < 0)
			{
				for (int i = 0; i < elements.length; i++)
					contactInfo.get(i).add(0, elements[i]);
			} 
			else if (((contactInfo.get(0).get(contactInfo.get(0).size() - 1)).compareToIgnoreCase(elements[0])) < 0)
			{
				for (int i = 0; i < elements.length; i++)
					contactInfo.get(i).add(contactInfo.get(i).size(), elements[i]);
			} 	
			else 
			{
				for (index = 0; index < contactInfo.size() - 1 && !locationFound; index++) 
				{
					if(elements[0].equals(contactInfo.get(0).get(index)))
					{	
						if(elements[1].compareToIgnoreCase(contactInfo.get(1).get(index)) <= 0)
						{	
							for(int i = 0; i < elements.length;i++)
								contactInfo.get(i).add(index, elements[i]);
							locationFound = true;
						}	
						else 
						{
							for(int i = 0; i < elements.length;i++)
								contactInfo.get(i).add(index + 1, elements[i]);
							locationFound = true;
						}	
					}	
					else
					{
						current = contactInfo.get(0).get(index);
						next = contactInfo.get(0).get(index + 1);
						if (elements[0].compareToIgnoreCase(current) > 0 && elements[0].compareToIgnoreCase(next) < 0)
						{
							for (int i = 0; i < elements.length; i++)
								contactInfo.get(i).add(index + 1, elements[i]);
							locationFound = true;
						}
					}
				}
			}
			System.out.print("Contact added.\n");
		}
    }
    
	public static boolean repeatedUniqueInfo(String [] elements)
	{
		/*argument: elements array.Return: boolean duplicate. Function: determines if one of the pieces of contact information
		in the elements array is already contained in the contactsInfo arraylist. (a name, address or landline can be the
		same but not other pieces of info)- Jamie MacManus.*/
		String uniqueInfo = "";
		boolean duplicate = false;
		for(int i = 0; i < contactInfo.get(0).size();i++)
		{
			for(int k = 2; k < 5 && !duplicate; k++)
			{	
				uniqueInfo += contactInfo.get(k).get(i);
				if(uniqueInfo.contains(elements[k]) && elements[k] != "")
				{
					System.out.print("Error! A contact already contains a piece of information entered.\n");
					duplicate = true;
				}
			}	
		}	
		return duplicate;
	}

	public static void editRemoveProcess(String [] elements, String isRemove) 
	{
		/*this method is called either by the error or remove method and depending on which will search 
		the mulitdimensional arraylist for the part the user wishes to remove or edit using for loops and 
		if statements as well as a try and catch in case of errors - cian whelan*/
		boolean contactFound = false;
		String word = elements[2];
		int choice = findType(word, 0);
		String name1 = elements[0] + elements[1];
		String name2 = "";
		try
		{
			for(int i = 0; i < contactInfo.size() && !contactFound; i++)
			{
				name2 = contactInfo.get(0).get(i) + contactInfo.get(1).get(i);
				if(contactInfo.get(choice).get(i).equals(word) && name1.equals(name2))
				{
					contactFound = true;
					if(isRemove.equals("removed."))
						for(int j = 0; j < 6; j++)
							contactInfo.get(j).remove(i);
					else if(isRemove.equals("edited."))
						contactInfo.get(choice).set(i, elements[3]);
				}
			}
			if(contactFound = true)
				System.out.println("Contact " + isRemove);
			else
				System.out.println("Contact not found please try again");
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Error! We could not find the piece of data that you wanted to be "  + isRemove);
		}
	}
    
    public static void search(String [] elements)
    {
        /* 	main search method for the search operation checks if the input the user provided is in the
			file and displays the contact that matches or else states contact not found - William O'Leary*/
        String Input = elements[0];
		int choice = findType(Input, 1);
        int counter = 0;
        int totalContacts = contactInfo.get(0).size();
        int Screens = 0;
        boolean found = false;
        for(int i = 0; i <totalContacts; i++)
        {
            if(contactInfo.get(choice).get(i).contains(Input))
            {
                DisplayProcess(counter,totalContacts,Screens, i);
                counter++;
                found = true;
            }
        }
        if(found == false)
			System.out.println("\nContact not found.");
        System.out.println("\n"+"Returning to main menu.");
    }
  
	
    public static void DisplayProcess(int counter,int totalContacts, int Screens, int i)
    {
    // display processing method which is called upon to print contacts wanted- in seperate method as it's used multiple times - William O'Leary
        String [] InfoTypes = {"Surname:   \t","Forename:\t","Landline:\t",
        "Mobile:     \t", "Email address:\t", "postal address:\t"};
		
		System.out.println("_______________________"+
        "\n"+"contact "+(counter+1));
        for(int j = 0; j < contactInfo.size(); j++ )
			System.out.println(InfoTypes[j]+contactInfo.get(j).get(i));
        if((counter+1) % 4 == 0)
        {
            Screens++;
            System.out.println("_______________________"+
            "\n"+ totalContacts+" total contacts.");
            if(!(totalContacts % 4 == 0 ))
				System.out.println(Screens+" out of "+ ((totalContacts/4)+1)+" Screens displayed.");
            else
                System.out.println(Screens+" out of "+ ((totalContacts/4))+" Screens displayed.");
            System.out.println("Hit any character to continue.");
            String next = sc.nextLine();
        }
    }
    
    public static void moveToFile(String fileName) throws IOException
    {
	//Finally moving data from the multi D arraylist to the file using a nested for loop.Takes filename and has
	//no return.Jamie MacManus
        PrintWriter toFile = new PrintWriter(fileName);
        
        for(int i = 0; i < contactInfo.get(0).size(); i++)
        {
            for(int j = 0;j < 5; j++)
				toFile.print(contactInfo.get(j).get(i) + ",");
            toFile.println(contactInfo.get(5).get(i));
        }
        toFile.close();
    }
}