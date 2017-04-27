package spellchecker.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import spellchecker.data.DictionaryData;
import spellchecker.data.WordText;
import spellchecker.loader.FileLoad;
import spellchecker.verification.SpellCheck;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

public class MainWindow {

	private JFrame frame;
	private DictionaryData dictionary;
	private ConcurrentMap<WordText, Boolean> text;
	private JTextPane textPane;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 568, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		list = new JList<String>();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(6, 37, 556, 264);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textPane = new JTextPane();
		textPane.setBounds(196, 30, 340, 193);
		panel.add(textPane);

		JLabel lblNewLabel = new JLabel("Text");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(334, 6, 61, 16);
		panel.add(lblNewLabel);

		JLabel lblDictionary = new JLabel("Dictionary");
		lblDictionary.setHorizontalAlignment(SwingConstants.CENTER);
		lblDictionary.setBounds(52, 6, 83, 16);
		panel.add(lblDictionary);

		JButton DictBtn = new JButton("Load Dictionary");
		DictBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter textFilter = new ExtensionFileFilter(null, new String[] { "txt" });
				fileChooser.setFileFilter(textFilter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getName());
					loadDictionaryFile(selectedFile.getAbsolutePath());
				}
			}
		});
		DictBtn.setBounds(29, 229, 136, 29);
		panel.add(DictBtn);

		JButton LoadTxtBtn = new JButton("Load Text");
		LoadTxtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter textFilter = new ExtensionFileFilter(null, new String[] { "txt" });
				fileChooser.setFileFilter(textFilter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getName());
					loadTextFile(selectedFile.getAbsolutePath());
				}

			}
		});
		LoadTxtBtn.setBounds(313, 229, 117, 29);
		panel.add(LoadTxtBtn);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(29, 30, 155, 193);
		panel.add(scrollPane);

		JLabel lblSpellChecker = new JLabel("Spell Checker");
		lblSpellChecker.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpellChecker.setBounds(221, 6, 136, 19);
		frame.getContentPane().add(lblSpellChecker);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveTextFile();
			}
		});
		saveButton.setBounds(319, 313, 117, 29);
		frame.getContentPane().add(saveButton);

		JButton btnNewButton = new JButton("Check Spelling");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkSpelling();
			}
		});
		btnNewButton.setBounds(110, 313, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}

	/**
	 * Load the dictionary file that contains the list of words
	 * @param path	Absolute Path to the file
	 */
	private void loadDictionaryFile(String path) {
		list.setModel(new DefaultListModel<String>());
		dictionary = FileLoad.loadDictionary(path);

		listModel = new DefaultListModel<String>();
		
		for(String w:dictionary.getDictionary())
			listModel.addElement(w);
		
		
		list.setModel(listModel);
		list.setVisible(true);
		
		scrollPane.getViewport().add(list);

		if (dictionary.toString() == "")
			dictionary = null;

	}

	/**
	 * Load the text file that contains the text to be checked
	 * @param path	Absolute path to the file
	 */
	private void loadTextFile(String path) {
		text = FileLoad.loadText(path);
		String textFull = "";

		List<WordText> keys = new ArrayList<WordText>(text.keySet());
		Collections.sort(keys);
		
		for(WordText wt: keys)
		{
			textFull += wt.getWord() + " ";
		}
		
		textPane.setText(textFull);
		
		if(text.size()==0)
			text= null;
	}

	/**
	 * Write the contents of the Text Pane to a file
	 */
	private void saveTextFile() {
		FileLoad.fileSave(textPane.getText());
		text = FileLoad.loadText("");
		JOptionPane.showMessageDialog(frame, "File was saved successfully!", "Success", 1);
	}

	/**
	 * Check the spelling in the text pane
	 */
	private void checkSpelling() {
		//Make sure the dictionary and text have both been loaded
		if (dictionary == null)
			JOptionPane.showMessageDialog(frame, "Please Load a Dictionary File", "No Dictionary", 1);
		else if (text == null)
			JOptionPane.showMessageDialog(frame, "Please Load a Text File", "No Text", 1);
		else {
			//Erase the text pane to display the checked text
			textPane.setText("");
			
			//Create a thread pool to check 10 words at a time
			ExecutorService es = Executors.newFixedThreadPool(10);
			Runnable sc = new SpellCheck(dictionary, text);
			for(int i = 0;i<text.size();i++){
				es.execute(sc);
			}
			es.shutdown();
		
			while(!es.isTerminated()){
				//Threads are still running
			}
			
			StyledDocument doc = textPane.getStyledDocument();
			Style style = textPane.addStyle("Text Style", null);
	        

			List<WordText> keys = ((SpellCheck) sc).getText();
			Collections.sort(keys);
			
			for(WordText wt: keys)
			{
				//If the spelling is correct, leave the font black
				//If it is incorrect, change the font to red
				if(wt.isCorrect())
					StyleConstants.setForeground(style, Color.black);
				else
					StyleConstants.setForeground(style, Color.red);
				
				try {
					doc.insertString(doc.getLength(), wt.getWord() + " ", style);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			StyleConstants.setForeground(style, Color.black);
		}
	}

	/*
	 * This is an inner class that filters out file extensions. 
	 * Only text files will be selectable in the file chooser
	 */
	class ExtensionFileFilter extends FileFilter {
		String description;

		String extensions[];

		public ExtensionFileFilter(String description, String extension) {
			this(description, new String[] { extension });
		}

		public ExtensionFileFilter(String description, String extensions[]) {
			if (description == null) {
				this.description = extensions[0];
			} else {
				this.description = description;
			}
			this.extensions = (String[]) extensions.clone();
			toLower(this.extensions);
		}

		private void toLower(String array[]) {
			for (int i = 0, n = array.length; i < n; i++) {
				array[i] = array[i].toLowerCase();
			}
		}

		public String getDescription() {
			return description;
		}

		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			} else {
				String path = file.getAbsolutePath().toLowerCase();
				for (int i = 0, n = extensions.length; i < n; i++) {
					String extension = extensions[i];
					if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
