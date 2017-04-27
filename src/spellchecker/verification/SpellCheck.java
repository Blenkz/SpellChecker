package spellchecker.verification;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.*;

import spellchecker.data.DictionaryData;
import spellchecker.data.WordText;

public class SpellCheck extends Observable implements Runnable  {

	private DictionaryData data;
	private List<WordText> textComplete;
	private List<WordText> textFull;

	public SpellCheck(DictionaryData data, ConcurrentMap<WordText, Boolean> text) {
		this.data = data;
		textComplete = new ArrayList<WordText>(text.keySet());
		textFull = new ArrayList<WordText>();
	}

	private void checkSpelling() {
		WordText wt;
		// Do checking here
		synchronized (this) {
			int index = 0;
			while(textComplete.get(index).isChecked() && index < textComplete.size()){
				index++;
			}

			wt = textComplete.remove(index);
			
		}
		if (wt.getWord().indexOf(".") > -1 || wt.getWord().indexOf("!") > -1
				|| wt.getWord().indexOf("?") > -1 || wt.getWord().indexOf(";") > -1
				|| wt.getWord().indexOf(":") > -1) {

			wt.setCorrect(
					data.checkSpelling(wt.getWord().substring(0, wt.getWord().length() - 1)));
		} else
			wt.setCorrect(data.checkSpelling(wt.getWord()));

		textFull.add(wt);

	}

	public List<WordText> getText() {
		return textFull;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.checkSpelling();
		System.out.println(Thread.activeCount());
	}

}
