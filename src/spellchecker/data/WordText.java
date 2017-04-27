package spellchecker.data;

/**
 * Hold a word from the text with its order within the text
 * @author Brendan Blencowe
 *
 */
public class WordText implements Comparable<WordText> {

	private String word;
	private boolean correct;
	private int order;
	private boolean checked;
	
	public WordText(String word,Boolean correct, int order) {
		this.word = word;
		this.correct = correct;
		this.order = order;
		this.checked = false;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public int compareTo(WordText other) {
	    return Integer.compare(this.order, other.order);
	}
	
	
}
