import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.POS;


public class Custom {
	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	//private ArrayList<Double> similarityVector = new ArrayList<Double>();
	private Set<String> words = new HashSet<String>();


	public Dictionary dict;

	public Custom() {
		String path = File.separator + "/home/mjorough/Downloads/dict";
		URL url;
		try {
			url = new URL("file", null, path);
			dict = new Dictionary( url);
			dict.open();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int whatisPOS(String in){
		if(dict.getIndexWord (in, POS.NOUN ) != null)
			return 0;
		if(dict.getIndexWord (in, POS.VERB ) != null)
			return 1;
		if(dict.getIndexWord (in, POS.ADJECTIVE ) != null)
			return 2;
		if(dict.getIndexWord (in, POS.ADVERB ) != null)
			return 3;
		return -1;
	}
	
	public double customCosine(){
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
		
		//similarityVector.add(roundOff);
		double z =calcZarib();
		similarity =similarity*z;
		
		similarity = similarity * 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		
		return (roundOff);

	}

	private double calcZarib() {
		// TODO Auto-generated method stub
		ArrayList<String> A = this.firstSen;
		ArrayList<String> B = this.secondSen;
		
		
		Double t = (double ) A.size()-B.size();
		t = Math.abs(t);
		
		int [] histA = new int[4];
		int [] histB = new int[4];
		for(String ss:A){
			int h=whatisPOS(ss) ;
			if (h== -1) continue;
			histA[h]++;
		}
		for(String ss:B){
			int h=whatisPOS(ss) ;
			if (h== -1) continue;
			histB[h]++;
		}
		double dist=0;
		double La=0,Lb=0;
		for(int i=0;i<4;i++){
			La += histA[i]*histA[i];
			Lb += histB[i]*histB[i];
			dist += histA[i]*histB[i];
		}
		double sr = Math.sqrt(La)*Math.sqrt(Lb);
		dist = dist/sr;
		
		int sm = Math.max(A.size(),B.size());
		if( t >= sm/7.0)
			dist *= 0.85;
		return dist;
	}

	public void update(ArrayList<ArrayList<String>> twoSilimarSentences, Set<String> words) {
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
		this.words = words;
		
	}

}
