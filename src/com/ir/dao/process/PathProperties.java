package com.ir.dao.process;

import java.io.IOException;
import java.util.Properties;

public class PathProperties {
	
	public static String INDEX_STORE_PATH;
	public static String AUTO_COMPLETION_DICTIONARY_PATH;
	public static String RELATED_SEARCH_DICTIONARY_PATH;
	public static String TOP_SEARCH_DICIONARY_PATH;
	public static String PICTURE_STORE_PATH;
	public static String IECAPT_PATH;
	
	public static void init(String path) {
		Properties prop = new Properties();
		try {
			prop.load(PathProperties.class.getResourceAsStream(path));
			INDEX_STORE_PATH = prop.getProperty("INDEX_STORE_PATH");
			AUTO_COMPLETION_DICTIONARY_PATH = prop.getProperty("AUTO_COMPLETION_DICTIONARY_PATH");
			RELATED_SEARCH_DICTIONARY_PATH = prop.getProperty("RELATED_SEARCH_DICTIONARY_PATH");
			TOP_SEARCH_DICIONARY_PATH = prop.getProperty("TOP_SEARCH_DICIONARY_PATH");
			PICTURE_STORE_PATH = prop.getProperty("PICTURE_STORE_PATH");
			IECAPT_PATH = prop.getProperty("IECAPT_PATH");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
