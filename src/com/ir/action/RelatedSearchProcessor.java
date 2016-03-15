package com.ir.action;

import java.io.*;
import java.util.*;

public class RelatedSearchProcessor implements Serializable {
	
	private static RelatedSearchProcessor processor = null;
	private static Object lock = new Object();
	
	private Map<String, QueryInfo> dictionary;
	
	private float substrParaRatio = 10;
	private float freqParaRatio = 1;
	private float docParaRatio = 1;
	
	public static RelatedSearchProcessor getInstance() {
		if(processor == null)
			processor = new RelatedSearchProcessor();
		return processor;
	}
	
	private RelatedSearchProcessor() {
	}
	
	public void initDictionary() {
		dictionary = new HashMap<String, QueryInfo>();
	}
	
	public void loadDictionary(String filePath) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		dictionary = (Map<String, QueryInfo>)ois.readObject();
		ois.close();
	}
	
	public void saveDictionary(String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(dictionary);
		oos.close();
	}
	
	public void add(String query, int[] docIds) {
		synchronized (lock) {
			boolean isContained = dictionary.containsKey(query);
			if (!isContained)
				dictionary.put(query, new QueryInfo(1, docIds));
			else {
				QueryInfo queryInfo = dictionary.get(query);
				queryInfo.freq++;
				for(int i = 0; i < 10; i++)
					queryInfo.docIds[i] = Integer.MAX_VALUE;			
				for(int i = 0; i < 10 && i < docIds.length; i++)
					queryInfo.docIds[i] = docIds[i];			
				Arrays.sort(queryInfo.docIds);
			}
		}
	}
	
	public List<String> getTopQueries(String query, int k) {
		List<Unit> units = new ArrayList<Unit>();
		List<String> topQueries = new ArrayList<String>();
		QueryInfo queryInfo = dictionary.get(query);
		
		synchronized (lock) {
			Iterator<Map.Entry<String, QueryInfo>> iterator = dictionary
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, QueryInfo> entry = iterator.next();
				String key = entry.getKey();
				QueryInfo value = entry.getValue();
				if(query.equals(key))
					continue;
				
				float substrPara = 0, freqPara = 0, docPara = 0;
				if(key.contains(query))
					substrPara = substrParaRatio;
				freqPara = freqParaRatio * (float)Math.log(value.freq);
				docPara = docParaRatio * intersect(queryInfo.docIds, value.docIds);
				float score = substrPara + freqPara + docPara;
				
				units.add(new Unit(key, score));
			}
		}
		
		Collections.sort(units);
		if (units.size() > k)
			units = units.subList(0, k);
		Iterator<Unit> i = units.iterator();
		while (i.hasNext()) {
			Unit unit = i.next();
			topQueries.add(unit.query);
		}
		return topQueries;
	}
	
	public void print() {
		Iterator<Map.Entry<String, QueryInfo>> iterator = dictionary.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, QueryInfo> entry = iterator.next();
			String query = entry.getKey();
			QueryInfo qi = entry.getValue();
			System.out.println(query + ": " + qi.freq);
		}
	}
	
	private int intersect(int x[], int y[]) {
		int i = 0, j = 0, count = 0;
		
		while(i != 10 && j != 10) {
			if(x[i] == y[j]) {
				count++;
				i++;
				j++;
			}
			else if(x[i] < y[j])
				i++;
			else j++;
		}
		
		return count;
	}
	
	private class Unit implements Comparable<Unit> {
		
		public String query;
		public float score;
		
		public Unit(String query, float score) {
			this.query = query;
			this.score = score;
		}

		public int compareTo(Unit u) {
			// TODO Auto-generated method stub
			if(u.score - score > 0)
				return 1;
			if(u.score - score < 0)
				return -1;
			return 0;
		}
	}
	
	private class QueryInfo implements Serializable {
		
		public int freq;
		public int[] docIds;
		
		public QueryInfo(int freq, int[] docIds) {
			this.freq = freq;
			this.docIds = new int[10];
			
			for(int i = 0; i < 10; i++)
				this.docIds[i] = Integer.MAX_VALUE;
			
			for(int i = 0; i < 10 && i < docIds.length; i++)
				this.docIds[i] = docIds[i];
			
			Arrays.sort(this.docIds);
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		int[] docIds = new int[]{0,1,2,3,4,5,6,7,8,9};
		RelatedSearchProcessor processor = RelatedSearchProcessor.getInstance();
//		processor.initDictionary();
//		processor.add("UCAS", docIds);
//		docIds = new int[]{3,4,5};
//		processor.add("loli", docIds);
//		docIds = new int[]{1};
//		processor.add("laura", docIds);
//		processor.add("laura", docIds);
//		processor.add("laura", docIds);
//		processor.add("laura", docIds);
//		processor.add("laura", docIds);
		
		processor.loadDictionary("G:\\related.dat");
		
		List<String> strs = processor.getTopQueries("UCAS", 5);
		for(String str : strs)
			System.out.println(str);
		
		processor.saveDictionary("G:\\related.dat");
		System.out.println("Done.");
	}

}
