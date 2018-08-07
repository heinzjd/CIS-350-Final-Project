package termProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


/***********************************************************************
 * Term Project
 * 
 * GUI
 * 
 * @author Jeff Heinz & Harman Rai
 * @version 7/14/2018
 **********************************************************************/

@SuppressWarnings("serial")
public class SpellCheckGUI extends JFrame implements ActionListener
{	
	/** Object from EnglishSpellCheck class */
	SpellCheck spellCheck;

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
	
	/** integer for language */
	int lang = 0;

	/** menu items in each of the menus */
	private JMenuBar menus;
	private JMenu fileMenu;
	private JMenu actionMenu;
	private JMenuItem openItem;
	private JMenuItem quitItem;
	private JMenuItem saveItem;
	private JMenuItem addWordItem;

	private JMenuItem englishItem;
	private JMenuItem spanishItem;
	private JMenuItem frenchItem;
	private JMenuItem italianItem;
	private JMenuItem germanItem;
	private JMenuItem norwegianItem;
	


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
		// Create menu items
		menus = new JMenuBar();
		setJMenuBar(menus);
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		openItem = new JMenuItem("Open File");
		quitItem = new JMenuItem("Quit");
		saveItem = new JMenuItem("Save File");
		//action menu items (languages)
		addWordItem = new JMenuItem("Add To Dictionary");
		englishItem = new JMenuItem("English");
		spanishItem = new JMenuItem("Spanish");
		frenchItem = new JMenuItem("French");
		italianItem = new JMenuItem("Italian");
		germanItem = new JMenuItem("German");
		norwegianItem = new JMenuItem("Norwegian");


		//adding items to menu bar
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		//action  menu items
		actionMenu.add(addWordItem);
		actionMenu.addSeparator();
		actionMenu.add(englishItem);
		actionMenu.add(spanishItem);
		actionMenu.add(frenchItem);
		actionMenu.add(italianItem);
		actionMenu.add(germanItem);
		actionMenu.add(norwegianItem);


		menus.add(fileMenu);
		menus.add(actionMenu);

		// Add actionListeners
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		quitItem.addActionListener(this);
		addWordItem.addActionListener(this);
		englishItem.addActionListener(this);
		spanishItem.addActionListener(this);
		frenchItem.addActionListener(this);
		italianItem.addActionListener(this);
		germanItem.addActionListener(this);
		norwegianItem.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints loc;


		loc = new GridBagConstraints();

		textArea = new JTextArea(40,100);
		textArea.setLineWrap(true);
		JScrollPane jScrollPane = new JScrollPane(textArea);
		incorrectWords = new JList<String>();
		loc.insets = new Insets(4, 4, 4, 4);

		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 0;
		add(textLabel = new JLabel("Input Text"), loc);       
		loc.gridx = 1;
		loc.gridy = 0;
		loc.insets = new Insets(4, 4, 4, 4);
		add(jScrollPane,loc);


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

		if (openItem == e.getSource()) {
			loadFromFile();
		}

		if (saveItem == e.getSource()) {
			saveToFile();
		}

		if(quitItem == e.getSource()){
			System.exit(1);
		}
		
		if(addWordItem == e.getSource()){
			addToDict();
		}
		
		if(englishItem == e.getSource()){
			lang = 1;
		}
		
		if(spanishItem == e.getSource()){
			lang = 2;
		}
		
		if(frenchItem == e.getSource()){
			lang = 3;
		}
		
		if(italianItem == e.getSource()){
			lang = 4;
		}
		
		if(germanItem == e.getSource()){
			lang = 5;
		}
		
		if(norwegianItem == e.getSource()){
			lang = 6;
		}
	}

	/***********************************************************
	 * Helps run spell checking for input text
	 **********************************************************/
	private void checkGUI()
	{
		if(lang > 0)
		spellCheck = new SpellCheck(lang);
			
		else
		spellCheck = new SpellCheck();

		total = Integer.toString(spellCheck.getAmount());
		
		String t = textArea.getText();

		spellCheck.setText(t);

		words = spellCheck.getMisspelled();

		Highlighter highlighter = textArea.getHighlighter();
		HighlightPainter painter = 
				new DefaultHighlighter.DefaultHighlightPainter(Color.pink);

		highlighter.removeAllHighlights();

		for(int i=0; i<words.length; i++)
		{

			int pos = 0;
			// Search for pattern
			while ((pos = t.indexOf(words[i], pos)) >= 0) {

				try {
					highlighter.addHighlight(pos, pos + words[i].length(), painter);
				} catch (BadLocationException e) {
					//Auto-generated catch block
					e.printStackTrace();
				}             
				pos += words[i].length();

			}
		}

		incorrectWords.setListData(words);

		total = Integer.toString(spellCheck.getAmount());

		totalLabel.setText(total);


	}

	/*****************************************************************
	 * Display JFileChooser to load a saved file
	 ****************************************************************/
	private void loadFromFile(){
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);

		// only process if user clicked OK
		if (status == JFileChooser.APPROVE_OPTION) 
		{
			try
			{
				String filename = chooser.getSelectedFile().getAbsolutePath();
//				FileReader reader = new FileReader(filename);
				BufferedReader reader = new BufferedReader(
				        new InputStreamReader(
				                new FileInputStream(filename), "UTF-8"));
				
				textArea.read(reader, filename); //Object of JTextArea
			}
			catch(Exception ex)
			{
				//  error message
			}   
		}
	}

	/*****************************************************************
	 * Display JFileChooser to save current JTextArea
	 ****************************************************************/
	private void saveToFile()
	{
		// Display JFileChooser and wait for response
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);

		// only process if user clicked Save
		if (status == JFileChooser.APPROVE_OPTION) 
		{
			String filename = chooser.getSelectedFile().getAbsolutePath();
			
			Writer fstream = null;
			try {
			    fstream = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);		

				textArea.write(fstream);
			}
			catch(Exception ex)
			{
				//  error message
			}
		}       
	}
	
	/*****************************************************************
	 * Add word to dictionary
	 ****************************************************************/
	private void addToDict()
	{
		String dictFile;
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
		String newWord = JOptionPane.showInputDialog(this, "Enter Word to add:");
		try {
		    Files.write(
		    		Paths.get(dictFile),
		    		newWord.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}

}

