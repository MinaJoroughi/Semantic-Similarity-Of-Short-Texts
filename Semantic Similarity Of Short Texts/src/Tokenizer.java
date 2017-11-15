import java.util.ArrayList;

import org.hamcrest.core.IsAnything;


public class Tokenizer {
	private ArrayList<ArrayList<String>> tokenized_lines = new ArrayList<ArrayList<String>>();
	
	public void tokenize (String line){
		String [] sentence = line.split("\t");
		
		for(String ss: sentence){
			ArrayList<String> cline = new ArrayList<String>();
			char[] myNameChars = ss.toCharArray();
			for(int i=0;i<ss.length();i++){
				if(Character.isLetter(myNameChars[i]) == false){
					myNameChars[i] = ' ';
				}
			}
			ss = String.valueOf(myNameChars);
			String[] words = ss.split(" +");
			for(String w: words){
				String lemma = w.toLowerCase().trim();
		    	if(lemma.length() == 0)
		    		continue;
		    	
				cline.add(w);
			}
			tokenized_lines.add(cline);	
		}
	}

	public ArrayList<ArrayList<String>> getTokenized_lines() {
		return tokenized_lines;
	}

}
