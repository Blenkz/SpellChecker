package spellchecker.test;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentMap;

import org.junit.Before;
import org.junit.Test;

import spellchecker.data.DictionaryData;
import spellchecker.data.WordText;
import spellchecker.loader.FileLoad;
import spellchecker.verification.SpellCheck;

public class SpellCheckTest {

	DictionaryData dictionary;
	SpellCheck sc;
	ConcurrentMap<WordText, Boolean>  text;
	
	@Test
	public void testDictionaryData() {
		assertTrue(!dictionary.toString().equals(""));
	}
	
	@Test
	public void testTextData() {
		assertTrue(text.size()>0);
	}
	
	@Test
	public void testNoDictionaryData() {
		dictionary = FileLoad.loadDictionary("Files//Test3.txt");
		assertTrue(dictionary.toString().equals(""));
	}
	
	@Test
	public void testNoTextData() {
		text = FileLoad.loadText("Files//Test3.txt");
		assertTrue(text.size()==0);
	}
	
	@Test
	public void testSpellCheck() {
		SpellCheck spell = (SpellCheck)sc;
		assertTrue(spell.getText().size()==0);
	}
	
	@Before
	public void initialize(){
		dictionary = FileLoad.loadDictionary("Files//Test.txt");
		text = FileLoad.loadText("Files//Test2.txt");
		sc = new SpellCheck(dictionary,text);
	}

}
