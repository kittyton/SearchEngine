package com.ir.dao.process;

import java.io.*;
import java.util.*;

import com.ir.bean.TopSearch;

public class TopSearchProcessor implements Serializable {
	
	private static TopSearchProcessor processor = null;
	private static Object lock = new Object();
	public final static long TIMESPAN = 60 * 1000;
	public final static long DAYSPAN = 24 * 60 * 60 * 1000;
	
	private Map<String, List<Long>> dictionary;
	private TopSearch[] topSearches;
	private long invokeTime;
	
	public static TopSearchProcessor getIntance() {
		if(processor == null) {
			processor = new TopSearchProcessor();
			processor.invokeTime = 0;
		}
		return processor;
	}
	
	private TopSearchProcessor() {
	}
	
	private void initDictionary() {
		dictionary = new HashMap<String,  List<Long>>();
		topSearches = new TopSearch[10];
		for(int i = 0; i < 10; i++)
			topSearches[i] = null;
	}
	
	private void updateTopSearches(long now) {
		List<Unit> units = new ArrayList<Unit>();
		Iterator<Map.Entry<String, List<Long>>> iterator = dictionary
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<Long>> entry = iterator.next();
			String query = entry.getKey();
			List<Long> dateList = entry.getValue();
			int count = 0;
			Iterator<Long> i = dateList.iterator();
			while (i.hasNext()) {
				Long date = i.next();
				if (now - date <= DAYSPAN)
					count++;
			}
			units.add(new Unit(query, count));
		}
		Collections.sort(units);
		for (int i = 0; i < 10 && i < units.size(); i++) {
			Unit u = units.get(i);
			topSearches[i] = new TopSearch(u.query, u.count);
		}
	}

	public void loadDictionary(String filePath) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		topSearches = (TopSearch[])ois.readObject();
		dictionary = (Map<String, List<Long>>)ois.readObject();
		ois.close();
	}
	
	public void saveDictionary(String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(topSearches);
		oos.writeObject(dictionary);
		oos.close();
	}
	
	public void add(String query) {
		synchronized (lock) {
			boolean isContained = dictionary.containsKey(query);
			if (!isContained) {
				List<Long> dateList = new ArrayList<Long>();
				dateList.add(new Date().getTime());
				dictionary.put(query, dateList);
			} else
				dictionary.get(query).add(new Date().getTime());
		}
	}
	
	public List<TopSearch> getTopSearches() {
		Date now = new Date();
		if(now.getTime() - invokeTime > TopSearchProcessor.TIMESPAN) {
			invokeTime = now.getTime();
			updateTopSearches(now.getTime());
		}
		
		List<TopSearch> topSearches = new ArrayList<TopSearch>();
		for(int i = 0; i < 10; i++) {
			if(this.topSearches[i] == null)
				break;
			topSearches.add(this.topSearches[i]);
		}
		return topSearches;
	}
	
	private class Unit implements Comparable<Unit> {
		
		public String query;
		public int count;
		
		public Unit(String query, int count) {
			this.query = query;
			this.count = count;
		}

		@Override
		public int compareTo(Unit u) {
			// TODO Auto-generated method stub
			return u.count - count;
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		TopSearchProcessor pro = TopSearchProcessor.getIntance();
//		pro.initDictionary();
//		pro.add("");
//		pro.add("西甲");
//		pro.add("2014亚冠冠军");
//		pro.add("英超");
//		pro.add("nba全明星投票");
//		pro.add("欧冠");
//		pro.add("nba历史得分榜");
//		pro.add("德甲");
//		pro.add("意甲");
//		pro.add("篮球公园");
//		pro.add("英超积分榜");
//		pro.add("edg战队");
//		
//		pro.saveDictionary("G:\\Eclipse Projects\\Search Engine\\top.dat");
		pro.loadDictionary("G:\\Eclipse Projects\\Search Engine\\top.dat");
		
		List<TopSearch> top = pro.getTopSearches();
		for (int i = 0; i < top.size(); i++) {
			System.out.println(top.get(i).query);
			System.out.println(top.get(i).count);
		}
	}
 
}
