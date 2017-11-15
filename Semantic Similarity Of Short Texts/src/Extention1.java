import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;


public class Extention1 {
	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	//private ArrayList<Double> similarityVector = new ArrayList<Double>();
	private Set<String> words = new HashSet<String>();

	ILexicalDatabase db = new NictWordNet();

	RelatednessCalculator wu = new WuPalmer(db);

	
	
	public Extention1(ArrayList<ArrayList<String>> twoSilimarSentences,
			Set<String> words) {
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
		this.words = words;
	}
	
	public double wuSim(){
		int [] firstSenNum = new int[words.size()];
		int [] secondSenNum = new int[words.size()];
		int i = 0; 
		for(String ss: words){
			if(firstSen.contains(ss)){
				firstSenNum[i] = 1;
			}
			if(secondSen.contains(ss)){
				secondSenNum[i] = 1;
			}
			i= i+1;	
		}
			
		String[] wordsArray = words.toArray(new String[words.size()]);
		
		int [][] wordSimilarity = new int[words.size()][words.size()];
		for(int m=0; m<words.size(); m++){
			for(int n=0; n<words.size(); n++){
				wordSimilarity[m][n] = wu.calcRelatednessOfWords (wordsArray[m], wordsArray[n]) >= 0.8 ? 1 : 0 ;
			}
		}
		
		double count = 0;
		double lenghtfirst = 0;
		double lenghtsecond = 0;
		for(int j=0; j<words.size(); j++){
			lenghtfirst  = lenghtfirst  + Math.pow(firstSenNum[j], 2);
			lenghtsecond = lenghtsecond + Math.pow(secondSenNum[j], 2);
		}
		
		double soorat = calcMatrix(wordSimilarity,firstSenNum,secondSenNum);
		double similarity = soorat / ((Math.sqrt(lenghtfirst)) * (Math.sqrt(lenghtsecond)));
		similarity = similarity * 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		//similarityVector.add(roundOff);
		return(roundOff);
	}

	private double calcMatrix(int[][] wordSimilarity, int[] firstSenNum,int[] secondSenNum) {
		// TODO Auto-generated method stub
		int sz = firstSenNum.length;
		int [] res= new int[sz];
		for(int i=0;i<sz;i++){
			int sum=0;
			for(int j=0;j<sz;j++){
				sum += firstSenNum[j] * wordSimilarity[j][i];
			}
			res[i] = sum;
		}
		int out=0;
		for(int i=0;i<sz;i++){
			out += res[i] * secondSenNum[i];
		}
		return out;
	}
	
	

}
