import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;



public class Sim {
	private ArrayList<ArrayList<String>> tokenizedSentences;
	public String TRAIN_DOCS;
	public static void main(String[] args) {
		Sim test = new Sim();
		test.TRAIN_DOCS=args[1];


		if(args[0].equals("baseline")){
			test.beaseLineTest();
		}
		else if(args[0].equals("tf-idf")){
			test.tfidfTest();
		}
		else if(args[0].equals("wordnet")){
			test.WNLineTest();
		}
		else if(args[0].equals("word2vec")){
			test.word2VecTest();
		}
		else if(args[0].equals("custom")){
			test.word2VecTest();
		}
		else if(args[0].equals("extention")){
			test.extention();
		}
		else if(args[0].equals("extention2")){
			test.extention2();
		}
		//test.readAcityTest();
		//test.beaseLineTest();
		//test.custom();
		//test.WNLineTest();
		//test.word2VecTest();
		//test.tfidfTest();
		//test.extention();
		/*ILexicalDatabase db = new NictWordNet();

		RelatednessCalculator wu = new WuPalmer(db);

		System.out.println(wu.calcRelatednessOfWords("bicycle", "motorcycle"));*/

	}


	public void readAcityTest(String filename){

		Read read = new Read(filename);
		Tokenizer tokenizer = new Tokenizer();
		ArrayList<String> lines = new ArrayList<String>();
		lines = read.getLines();
		//System.out.println(lines.size());
		for(String sentence: lines){
			tokenizer.tokenize(sentence);

		}
		tokenizedSentences = tokenizer.getTokenized_lines();
		/*for(ArrayList<String> sen: tokenizedSentences){
			System.out.println(sen);
		}*/	
	}
	public void extention(){
		Set<String> words = new HashSet<String>();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size()-1; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			/*for(String s: words){
				System.out.println(s);
			}*/
			Extention1 extention = new Extention1(twoSilimarSentences, words);	
			similarityVector.add(extention.wuSim());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);

	}
	public void extention2(){
		Set<String> words = new HashSet<String>();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		Extention2 extention2 = new Extention2();	
		for(int i=0; i<tokenizedSentences.size()-1; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			/*for(String s: words){
				System.out.println(s);
			}*/
			extention2.update(twoSilimarSentences, words);
			similarityVector.add(extention2.wuSim());	
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);

	}
	public void beaseLineTest(){
		Set<String> words = new HashSet<String>();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size()-1; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			/*for(String s: words){
				System.out.println(s);
			}*/
			Baseline baseLine = new Baseline(twoSilimarSentences, words);	
			similarityVector.add(baseLine.baseLineCosine());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);
	}
	public void word2VecTest(){
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		Word2vec.createVecHash();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size(); i=i+2){
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			Word2vec word2Vec = new Word2vec(twoSilimarSentences);
			similarityVector.add(word2Vec.baseLineCosine());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);
	}

	public void custom(){
		Set<String> words = new HashSet<String>();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		Custom custom = new Custom();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size()-1; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			/*for(String s: words){
				System.out.println(s);
			}*/
			custom.update(twoSilimarSentences, words);
			similarityVector.add(custom.customCosine());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);
	}
	public void tfidfTest(){ 
		Tfidf.createIdfHash();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		Set<String> words = new HashSet<String>();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size()-2; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			Tfidf tfIdf = new Tfidf(twoSilimarSentences, words);	
			similarityVector.add(tfIdf.baseLineCosine());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);
	}



	public void WNLineTest() {
		Set<String> words = new HashSet<String>();
		ArrayList<Double> similarityVector = new ArrayList<Double>();
		WordNet WN = new WordNet();
		this.readAcityTest("STS.input."+ TRAIN_DOCS + ".txt");
		for(int i=0; i<tokenizedSentences.size()-1; i=i+2){
			words.clear();
			ArrayList<ArrayList<String>> twoSilimarSentences = new ArrayList<ArrayList<String>>();
			twoSilimarSentences.add(tokenizedSentences.get(i));
			twoSilimarSentences.add(tokenizedSentences.get(i+1));
			SetWords setWords = new SetWords(twoSilimarSentences);
			words = setWords.getWords();
			/*for(String s: words){
				System.out.println(s);
			}*/

			WN.update(twoSilimarSentences, words);
			similarityVector.add(WN.WNCosine());
		}
		Write.writeRes("STS.gs."+ TRAIN_DOCS + ".txt", similarityVector);
	}
}

