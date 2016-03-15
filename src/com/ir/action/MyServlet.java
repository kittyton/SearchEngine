package com.ir.action;


import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ir.dao.process.PathProperties;
import com.ir.dao.process.TopSearchProcessor;

public class MyServlet extends HttpServlet implements ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 431692023955024149L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("constructing...");
		
		PathProperties.init("/path.properties");
		AutoCompletionProcessor processor = AutoCompletionProcessor.getInstance();
		RelatedSearchProcessor relatedProcessor = RelatedSearchProcessor.getInstance();
		TopSearchProcessor topProcessor = TopSearchProcessor.getIntance();
		try {
			relatedProcessor.loadDictionary(PathProperties.RELATED_SEARCH_DICTIONARY_PATH);
			processor.loadDictionary(PathProperties.AUTO_COMPLETION_DICTIONARY_PATH);
			topProcessor.loadDictionary(PathProperties.TOP_SEARCH_DICIONARY_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			RelatedSearchProcessor.getInstance().saveDictionary(PathProperties.RELATED_SEARCH_DICTIONARY_PATH);
			TopSearchProcessor.getIntance().saveDictionary(PathProperties.TOP_SEARCH_DICIONARY_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
