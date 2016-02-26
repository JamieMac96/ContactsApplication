//code to determine if an input is correct

public class Test
{
	public static void main(String [] args)
	{
		String [] elements;
		String input = "i blaha,jason,069696969,047474744,fbi hq";
		String firsLetter = input.substring(0,1).toLowerCase();
		
		input = input.trim().replace(firsLetter + " ", "");
		elements = input.split(",");
		
		System.out.println("firstLetter:" + firsLetter);
		for(int i = 0;i < elements.length;i++)
			System.out.println(elements[i]);
	}
	
}