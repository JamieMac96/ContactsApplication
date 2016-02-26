//dunno if this works for multiple contacts yet.

public static void display(String fileName)
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
                System.out.println("Hit any character to continue.");
                Scanner red = new Scanner(System.in);
                String next = red.nextLine();
            }
        }
        System.out.println("\n"+"Returning to menu:");
    }