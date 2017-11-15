import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.IndexWord;
import edu.mit.jwi.item.POS;
import net.didion.jwnl.JWNLException;



public class WordNet {

	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	//private ArrayList<Double> similarityVector = new ArrayList<Double>();
	private Set<String> words = new HashSet<String>();


	public void update(ArrayList<ArrayList<String>> twoSilimarSentences, Set<String> words){
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
		this.words = words;
	}

	public static void main(String[] args) throws JWNLException, IOException {
		WordNet wn = new WordNet();
		
		System.out.println(wn.findSynset("we"));
		/*IIndexWord idxWord = dict.getIndexWord ("happy", POS.ADJECTIVE );
		for(IWordID wordID: idxWord.getWordIDs()){
			IWord word = dict.getWord (wordID);         
			System.out.println("Id = " + wordID);
			System.out.println(" synset = " + word.getSynset().getWords());
		}*/
	}

	Dictionary dict;
	public WordNet(){
		String path = File.separator + "/home/mjorough/Downloads/dict";
		//String path = File.separator + "/../../dict";
		URL url;
		try {
			url = new URL("file", null, path);
			dict = new Dictionary( url);
			dict.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> findSynset(String in){
		ArrayList<String> res= new ArrayList<String>();
		//System.out.println(in);
		IIndexWord idxWord = dict.getIndexWord (in, POS.NOUN );
		if(idxWord == null)
			idxWord = dict.getIndexWord (in, POS.VERB );
		if(idxWord == null)
			idxWord = dict.getIndexWord (in, POS.ADJECTIVE );
		if(idxWord == null)
			idxWord = dict.getIndexWord (in, POS.ADVERB );
		if(idxWord == null){
			//System.err.println("Error "+in);
			return null;
		}
		for(IWordID wordID: idxWord.getWordIDs()){
			IWord word = dict.getWord (wordID);         
			//System.out.println(" synset = " + word.getSynset().getWords() );
			List<IWord> temp = word.getSynset().getWords();
			for( int i=0;i<temp.size()/2+1;i++){
				res.add(temp.get(i).getLemma());
			}
		}
		return res;
	}
	
	public boolean isSynonim(String word1,String word2){
		ArrayList<String> list1 = findSynset(word1);
		if(list1==null) return false;
		if(list1.contains(word2))
			return true;
		return false;
	}
	
	public double WNCosine(){
		int [] firstSenNum = new int[words.size()];
		int [] secondSenNum = new int[words.size()];
		int i = 0; 
		for(String ss: words){
			for(String sss:firstSen ){
				if (isSynonim(ss, sss)){
					firstSenNum[i] = 1;
					break;
				}
			}
			for(String sss:secondSen ){
				if (isSynonim(ss, sss)){
					secondSenNum[i] = 1;
					break;
				}
			}
			i= i+1;	
		}
		double count = 0;
		double lenghtfirst = 0;
		double lenghtsecond = 0;
		for(int j=0; j<words.size(); j++){
			count = count + firstSenNum[j]*secondSenNum[j];
			lenghtfirst = lenghtfirst + Math.pow(firstSenNum[j], 2);
			lenghtsecond = lenghtsecond + Math.pow(secondSenNum[j], 2);
		}
		double similarity = count / ((Math.sqrt(lenghtfirst)) * (Math.sqrt(lenghtsecond)));
		similarity = similarity * 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		//similarityVector.add(roundOff);
		return(roundOff );

	}
}