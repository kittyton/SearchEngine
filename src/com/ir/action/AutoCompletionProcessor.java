package com.ir.action;

import java.io.*;
import java.util.*;

import org.apache.lucene.index.*;

public class AutoCompletionProcessor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3009959163118645722L;

	private static AutoCompletionProcessor processor = null;
	
	private List<Unit> dictionary = null;
	
	public static AutoCompletionProcessor getInstance() {
		if(processor == null)
			processor = new AutoCompletionProcessor();
		return processor;
	}
	
	private AutoCompletionProcessor() {}
	
	public void initDictionary(String indexStoreDir) throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(indexStoreDir);
		dictionary = new ArrayList<Unit>();
		TermEnum terms = reader.terms();
		while(terms.next()) {
			Term term = terms.term();			
			if (term.field().equals("body")) {
				String word = term.text();
				int df = reader.docFreq(term);
				dictionary.add(new Unit(word, df));
			}
		}
		
		removeDuplicate();
		Collections.sort(dictionary);
	}
	
	public List<String> getTopWords(String prefix, int k) {
		List<String> topWords = new ArrayList<String>();
		Iterator<Unit> iterator = dictionary.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.word.startsWith(prefix)) {
				topWords.add(unit.word);
				k--;
				if (k == 0)
					break;
			}
		}
		return topWords;
	}
	
	public void loadDictionary(String filePath) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		dictionary = (List<Unit>)ois.readObject();
		ois.close();
	}
	
	public void saveDictionary(String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(dictionary);
		oos.close();
	}
	
	public void print() {
		Iterator<Unit> iterator = dictionary.iterator();
		while(iterator.hasNext()){
			Unit unit = iterator.next();
			System.out.println(unit.word + ": " + unit.df);
		}
	}
	
	private void removeDuplicate() {
		Set<Unit> hashSet = new HashSet<Unit>();
		List<Unit> rawDictionary = dictionary;
		dictionary = new ArrayList<Unit>();
		Iterator<Unit> iterator = rawDictionary.iterator();
		while(iterator.hasNext()) {
			Unit unit = iterator.next();
			if(hashSet.add(unit))
					dictionary.add(unit);
		}
	}
	
	private class Unit implements Comparable<Unit>, Serializable{
		
		public String word;
		public int df;
		
		public Unit(String word, int df) {
			this.word = word;
			this.df = df;
		}

		public int compareTo(Unit u) {
			// TODO Auto-generated method stub
			return u.df - df;
		}
	}
	
	public static void main(String[] args) throws CorruptIndexException, IOException, ClassNotFoundException {
		AutoCompletionProcessor processor = AutoCompletionProcessor.getInstance();
		//processor.initDictionary("k:\\index\\");
		//processor.saveDictionary("E:\\javaee\\SearchEngine\\auto.dat");
		processor.loadDictionary("E:\\javaee\\SearchEngine\\auto.dat");
		
		List<String> topWords = processor.getTopWords("Èü", 5);
		for (int i = 0; i < topWords.size(); i++)
			System.out.println(topWords.get(i));
	}
}
