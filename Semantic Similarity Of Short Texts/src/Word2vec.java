import java.util.ArrayList;
import java.util.HashMap;


public class Word2vec {
	private static HashMap<String, double[]> vecHash = new HashMap<String, double[]>();
	private ArrayList<String> firstSen = new ArrayList<String>();
	private ArrayList<String> secondSen = new ArrayList<String>();
	
	
	public Word2vec(ArrayList<ArrayList<String>> twoSilimarSentences) {
		this.firstSen = twoSilimarSentences.get(0);
		this.secondSen = twoSilimarSentences.get(1);
	}
	
	public double[] countVec(ArrayList<String> sen){
		double[] vecArray = new double [100];
		for(String ss: sen){
			if(vecHash.containsKey(ss)){
				double[] vecArray2 = new double [100];
				vecArray2 = vecHash.get(ss);
				for(int i=0; i<100; i++){
					vecArray[i] = vecArray[i] + vecArray2[i];
				}
			}
		}
		return vecArray;
	}
	
	public double baseLineCosine(){
		double [] firstSenWeight = new double[100];
		double [] secondSenWeight = new double[100]; 
		firstSenWeight = countVec(firstSen);
		secondSenWeight =countVec(secondSen);
		double count = 0;
		double lenghtfirst = 0;
		double lenghtsecond = 0;
		for(int j=0; j<100; j++){
			count = count + firstSenWeight[j]*secondSenWeight[j];
			lenghtfirst = lenghtfirst + Math.pow(firstSenWeight[j], 2);
			lenghtsecond = lenghtsecond + Math.pow(secondSenWeight[j], 2);
		}
		double similarity = count / ((Math.sqrt(lenghtfirst)) * (Math.sqrt(lenghtsecond)));
		similarity = similarity* 5;
		double roundOff = (double) Math.round(similarity * 10) / 10;
		//similarityVector.add(roundOff);
		//wirte in a file 
		return(roundOff);
	}



	public static void createVecHash(){
		Read read = new Read("vec.txt");
		ArrayList<String> lines = read.getLines();
		for(String ss: lines){
			String[] words = ss.split(" ");
			if(words[0].matches("[a-zA-Z]+")){
				double[] vec = new double[100];
				for(int i=1; i<words.length; i++){
					vec[i-1] = Double.parseDouble(words[i]);
				}
				vecHash.put(words[0], vec);
			}
		}
		/*System.out.println(vecHash.size());
		double[] value = vecHash.get("for");
		for (double n: value)
		 System.out.print(n + " ");*/
	}
}
