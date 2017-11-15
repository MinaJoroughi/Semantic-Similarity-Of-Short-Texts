import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Baseline {
	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	//private ArrayList<Double> similarityVector = new ArrayList<Double>();
	private Set<String> words = new HashSet<String>();

	
	public Baseline(ArrayList<ArrayList<String>> twoSilimarSentences,
			Set<String> words) {
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
		this.words = words;
	}
	
	public double baseLineCosine(){
		int [] firstSenNum = new int[words.size()];
		int [] secondSenNum = new int[words.size()];
		ArrayList <String > temp  = new ArrayList<String>(words);
		for(int i=0;i<words.size();i++){
			String ss = temp.get(i);
			if(firstSen.contains(ss))
				firstSenNum[i] = 1;
			
			if(secondSen.contains(ss))
				secondSenNum[i] = 1;
			
		}
		double count = 0;
		double lenghtfirst = 0;
		double lenghtsecond = 0;
		for(int j=0; j<words.size(); j++){
			count = count + firstSenNum[j]*secondSenNum[j];
			lenghtfirst = lenghtfirst + Math.pow(firstSenNum[j], 2);
			lenghtsecond = lenghtsecond + Math.pow(secondSenNum[j], 2);
		}
		double similarity = count / (Math.sqrt(lenghtfirst) * Math.sqrt(lenghtsecond));
		similarity = similarity * 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		//similarityVector.add(roundOff);
		return roundOff;
		
	}
	
}
