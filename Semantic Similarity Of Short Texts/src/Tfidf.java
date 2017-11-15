import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Tfidf {
	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	//private ArrayList<Double> similarityVector = new ArrayList<Double>();
	private Set<String> words = new HashSet<String>();
	private static HashMap<String, Double> idfHash = new HashMap<String, Double>();
	private double [] firstSenWeight ;
	private double [] secondSenWeight;

	public Tfidf(ArrayList<ArrayList<String>> twoSilimarSentences,
			Set<String> words) {
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
		this.words = words;
		firstSenWeight = new double[words.size()];
		secondSenWeight = new double[words.size()];
		
	}
	
	public double[] getFirstSenWeight() {
		return firstSenWeight;
	}

	public double[] getSecondSenWeight() {
		return secondSenWeight;
	}
	
	public double baseLineCosine(){ 
		HashMap<String, Integer> firstSenhmap = new HashMap<String,Integer>();
		HashMap<String, Integer> secondSenhmap = new HashMap<String,Integer>();
		for(int i=0; i<firstSen.size(); i++){
			String word = firstSen.get(i);
			if(!firstSenhmap.containsKey(word)){
				firstSenhmap.put(word, 1);
			}
			else{
				int c = firstSenhmap.get(word) + 1;
				firstSenhmap.put(word, c);	
			}
		}
		for(int i=0; i<secondSen.size(); i++){
			String word = secondSen.get(i);
			if(!secondSenhmap.containsKey(word)){
				secondSenhmap.put(word, 1);
			}
			else{
				int c = secondSenhmap.get(word) + 1;
				secondSenhmap.put(word, c);	
			}
		}	
		int i = 0;
		for(String ss: words){
			if(idfHash.containsKey(ss)){
				double idf = idfHash.get(ss);
				if(firstSenhmap.containsKey(ss)){
					firstSenWeight[i] = firstSenhmap.get(ss) * idf;
				}
				if(secondSenhmap.containsKey(ss)){
					secondSenWeight[i] = secondSenhmap.get(ss) * idf;
				}
			}
			i= i+1;	
		}
		double count = 0;
		double lenghtfirst = 0;
		double lenghtsecond = 0;
		for(int j=0; j<words.size(); j++){
			count = count + firstSenWeight[j]*secondSenWeight[j];
			lenghtfirst = lenghtfirst + Math.pow(firstSenWeight[j], 2);
			lenghtsecond = lenghtsecond + Math.pow(secondSenWeight[j], 2);
		}
		double similarity = count / ((Math.sqrt(lenghtfirst)) * (Math.sqrt(lenghtsecond)));
		similarity = similarity* 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		//similarityVector.add(roundOff);
		return(roundOff );
	}

	public static void createIdfHash(){
		Read read = new Read("idf.txt");
		ArrayList<String> lines = read.getLines();
		for(String ss: lines){
			String[] words = ss.split(" ");
			char[] myNameChars = words[0].toCharArray();
			for(int i=0;i<words[0].length();i++){
				if(Character.isLetter(myNameChars[i]) == false){
					myNameChars[i] = ' ';
				}
			}
			ss = String.valueOf(myNameChars);
			//if(ss.matches("[a-zA-Z]+")){
				idfHash.put(ss, Double.parseDouble(words[1]));
			//}
		}
	}
}
