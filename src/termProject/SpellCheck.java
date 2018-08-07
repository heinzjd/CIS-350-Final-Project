package termProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/***********************************************************************
 * Term Project
 * 
 * GUI
 * 
 * @author Jeff Heinz & Harman Rai
 * @version 7/14/2018
 **********************************************************************/

public class SpellCheck 
{
	/** String array for dictionary words */
	private String dictFile = "/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBank.txt";

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
	public SpellCheck()
	{         
		dictionary = dictionarySetup();
	} 
	
	public SpellCheck(int i)
	{         
		setLanguage(i);
		
		dictionary = dictionarySetup();
	} 

	public String[] dictionarySetup()
	{
		String content = null;
		
		try
		{
			content = new String (Files.readAllBytes(Paths.get(dictFile)));
			
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

	public void setLanguage(int lang)
	{
		switch (lang)
		{
		case 1:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBank.txt");
		break;
		case 2:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBankSpanish.txt");
		break;
		case 3:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBankFrench.txt");
		break;
		case 4:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBankItalian.txt");
		break;
		case 5:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBankGerman.txt");
		break;
		case 6:  dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBankNorw.txt");
		break;
		default: dictFile = ("/var/host/media/removable/SD Card/CIS 350/Workspace/CIS 350 Final Project/src/wordBank.txt");
		break;
		}
	}
}