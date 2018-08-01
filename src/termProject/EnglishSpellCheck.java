package termProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EnglishSpellCheck 
{
	/** String array for dictionary words */
	private String[] dictionary;

	/** String array for input words */
	private String[] input;	
	
	/** String array for incorrect words */
	private String[] incorrect;
	
	/** Integer variable for total incorrect words */
	private int total;
	
	/*******************************************************************
	 * Default constructor for instance variables of class 
	 * EnglishSpellCheck
	 ******************************************************************/
	public EnglishSpellCheck()
	{         
        dictionary = dictionarySetup();
	} 
 
    public static String[] dictionarySetup()
    {
        String content = null;
 
        try
        {
            content = new String (Files.readAllBytes(Paths.get
            		("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBank.txt")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
 
        return content.split("\\t| |\\n|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|=|\\\"");
    } 
	
	
	public void setText(String t)
	{
		input = t.split("\\t| |\\n|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|=|\\\"");
	}
	
	public int getAmount()
	{
		return total;
	}
	
	public String[] getMisspelled()
	{
		int counter = 0;
		ArrayList<String> temp = new ArrayList<String>();
		boolean flag = true;
		
		for(int i=0; i < input.length; i++)
		{
			for(int j=0; j<dictionary.length; j++)
			{
				if(input[i].equalsIgnoreCase(dictionary[j]))
				{
					flag = false;
				}
			}
			if(flag)
			{
				temp.add(input[i]);

				counter++;
			}
			flag = true;
		}
		
		total = counter;
		
		incorrect = temp.toArray(new String[0]);
		
		return incorrect;
	}
}