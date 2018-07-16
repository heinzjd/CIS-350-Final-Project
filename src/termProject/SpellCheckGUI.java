package termProject;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/***********************************************************************
 * Term Project
 * 
 * GUI
 * 
 * @author Jeff Heinz & Harman Rai
 * @version 7/14/2018
 **********************************************************************/

public class SpellCheckGUI extends JFrame implements ActionListener
{
	/** Object from EnglishSpellCheck class */
	EnglishSpellCheck englishCheck;

	/** Spell check button */
	JButton check;

	/** Label for total misspelled and where to enter text */
	JLabel totalLabel, textLabel;
	
	/** Field for text */
	JTextArea textArea;

	/** String for total */
	String total;
	
	/** JList for misspelled words */
	JList<String> incorrectWords;
	
	/** String array to put in JList */
	String[] words = new String[1000];

	
	/********************************************************************
	 * Main Method
	 ****************************************************************/ 
	public static void main(String args[])
	{
		SpellCheckGUI gui = new SpellCheckGUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Spell Checker");
		gui.pack();
		gui.setVisible(true);
	}

	/*****************************************************************
	 * constructor installs all of the GUI components
	 ****************************************************************/    
	public SpellCheckGUI()
	{
		englishCheck = new EnglishSpellCheck();

		total = Integer.toString(englishCheck.getAmount());

		setLayout(new GridBagLayout());
		GridBagConstraints loc;


		loc = new GridBagConstraints();

		textArea = new JTextArea(20,100);
		incorrectWords = new JList<String>();
		loc.insets = new Insets(4, 4, 4, 4);

		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 0;
		add(textLabel = new JLabel("Input Text"), loc);       
		loc.gridx = 1;
		loc.gridy = 0;
		loc.insets = new Insets(4, 4, 4, 4);
		add(textArea,loc);


		check = new JButton("Run Spell Check");
		loc.gridx = 1;
		loc.gridy = 1;
		loc.insets = new Insets(4, 4, 4, 4);
		add(check, loc);

		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 2;
		loc.insets.bottom = 20;
		add( new JLabel("Total Misspelled:"), loc);

		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 2;
		loc.insets.bottom = 20;
		add(totalLabel = new JLabel(total),loc);
		
		JScrollPane listScroller = new JScrollPane(incorrectWords);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 3;
		loc.insets = new Insets(4, 4, 4, 4);
		add(listScroller, loc);
		
		check.addActionListener(this);      
	}
	/*****************************************************************
	 * This method is called when any button is clicked.  The proper
	 * internal method is called as needed.
	 * 
	 * @param e the event that was fired
	 ****************************************************************/       
	public void actionPerformed(ActionEvent e)
	{
		JComponent buttonPressed = (JComponent)e.getSource();
		if (buttonPressed == check)
			checkGUI();
	}

	/***********************************************************
	 * Helps run spell checking for input text
	 **********************************************************/
	private void checkGUI()
	{
		String t = textArea.getText();

		englishCheck.setText(t);
		
		words = englishCheck.getMisspelled();
				
		incorrectWords.setListData(words);
		
		total = Integer.toString(englishCheck.getAmount());
		
		totalLabel.setText(total);
		
		
	}

}

