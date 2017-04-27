package spellchecker.test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import spellchecker.data.DictionaryData;
import spellchecker.data.WordText;
import spellchecker.loader.FileLoad;
import spellchecker.verification.SpellCheck;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictionaryData dict = FileLoad.loadDictionary("Files//DictionaryTest.txt");
		ConcurrentMap<WordText, Boolean> text = FileLoad.loadText("Files//TextTest.txt");

		
		ExecutorService es = Executors.newFixedThreadPool(10);
		Runnable sc = new SpellCheck(dict, text);
		for(int i = 0;i<text.size();i++){
			es.execute(sc);
		}
		es.shutdown();
		while(!es.isTerminated()){
			//Threads are still running
		}
		
		System.out.println();
		for(WordText wd:((SpellCheck) sc).getText()){
			System.out.println(wd.getWord() + " is " + wd.isCorrect());
		}
		
		
		
	}

}
