package spellchecker.data;

import java.util.*;

/**
 * The dictionary object containing all of the words
 * @author Blenkz
 *
 */
public class DictionaryData {
	
	private List<String> dict;
	

	
	public DictionaryData(){
		dict = new ArrayList<String>();
		
	}
	
	public List<String> getDictionary(){
		return dict;
	}
	
	public void addWord(String word){
		dict.add(word.toLowerCase());
		
		Collections.sort(dict);
	}
	
	public boolean checkSpelling(String word){
		System.out.println("Does Dictionary Contain: " + word + "?: " + dict.contains(word.toLowerCase()));
		if(dict.contains(word.toLowerCase()))
				return true;
		
		return false;
	}
	
	public String toString(){
		String display = "";
		
		for(String word:dict)
			display += word + "\n";
		
		return display;
	}
	

}
