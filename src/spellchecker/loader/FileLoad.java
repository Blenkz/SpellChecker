package spellchecker.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import spellchecker.data.DictionaryData;
import spellchecker.data.WordText;

public class FileLoad {

	private static String textFile;

	/**
	 * Load the dictionary file and return the Dictionary OBject
	 * 
	 * @param filename
	 *            The file path
	 * @return Dictionary Object full of words
	 */
	public static DictionaryData loadDictionary(String filename) {

		List<String> words = new ArrayList<String>();
		String line;
		DictionaryData data = new DictionaryData();
		try {

			FileReader fileReader;

			fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim() != "") {

					words.add(line);
					data.addWord(line);
				}
			}
			fileReader.close();
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Load the text file in a HashMap for concurrency
	 * 
	 * @param filename
	 *            The file to load
	 * @return A map containing the objects
	 */
	public static ConcurrentMap<WordText, Boolean> loadText(String filename) {

		if (!filename.equals(""))
			textFile = filename;
		ConcurrentMap<WordText, Boolean> words = new ConcurrentHashMap<WordText, Boolean>();
		String line;
		String[] split;
		int order = 0;
		try {

			FileReader fileReader;

			fileReader = new FileReader(textFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim() != "") {
					split = line.split(" ");
					for (String w : split) {
						words.put(new WordText(w, true, order), false);
						order++;
					}
				}
			}
			fileReader.close();
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return words;
	}

	/**
	 * Writes the text to the file
	 * 
	 * @param content
	 *            The text that needs to be saved
	 */
	public static void fileSave(String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			System.out.println(textFile);
			fw = new FileWriter(textFile);
			bw = new BufferedWriter(fw);
			bw.write(content.trim());
			bw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
