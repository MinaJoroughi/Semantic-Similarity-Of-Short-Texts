import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SetWords {
	private ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
	private Set<String> words = new HashSet<String>();

	public SetWords(ArrayList<ArrayList<String>> twoSilimarSentences) {
		this.twoSilimarSentences = twoSilimarSentences;
		set();
	}
	
	public void set(){
		words = new HashSet<String> (this.twoSilimarSentences.get(0));
		Set<String> s2 = new HashSet<String> (this.twoSilimarSentences.get(1));
		words.addAll(s2);
	}

	public Set<String> getWords() {
		return words;
	}
}
