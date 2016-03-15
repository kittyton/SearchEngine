package com.ir.dao.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;

import com.ir.bean.SearchResult;

public class IR_Search {
	public IR_Search(){
	}
	private List resultList=new ArrayList<SearchResult>();
	
	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public Query parse(String squery,String[] fields,Analyzer analyzer) throws ParseException{
		BooleanQuery bQuery=new BooleanQuery();
		//�������е�fields
		for(int i=0;i<fields.length;i++){
			QueryParser parser=new QueryParser(fields[i],analyzer);
			parser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query=parser.parse(squery);
			bQuery.add(query, BooleanClause.Occur.SHOULD);
		}
		return bQuery;
	}
	
	public void indexSearch(String searchType,String searchKey,String rank) throws IOException, ParseException{	
//	    System.out.println("++ʹ�������ķ�ʽ����++");
//		System.out.println("searchKey="+searchKey);
		//����������λ�ý���indexSearcher
		IndexSearcher searcher=new IndexSearcher(PathProperties.INDEX_STORE_PATH);
		//���ö�fields����
		String[] fields={"title","content"};
		Query query=parse(searchKey,fields,new MMAnalyzer());
		//�������������
		Sort sort=new Sort();
		if(rank.equals("default")){
		}
		else{
		SortField sfield=new SortField(rank,SortField.STRING,true);//��ʱ��,�ȶȵĽ�������
		sort.setSort(sfield);
		}
		Hits hits=searcher.search(query, sort);
		for(int i=0;i<hits.length();i++){
			Document doc=hits.doc(i);
			
			String fileName=doc.getField("fileName").stringValue();
			String title=doc.getField("title").stringValue();
			String content=doc.getField("content").stringValue();
			String url=doc.getField("url").stringValue();
			String date=doc.getField("date").stringValue();
			int docId = hits.id(i);
			IR_GenerateAbstract irg=new IR_GenerateAbstract();
			String summary=irg.generateSummary(searchKey, content, fileName);
			SearchResult result=new SearchResult();
			result.setContent(content);
			result.setFileName(fileName);
			result.setTitle(title);
			result.setUrl(url);
			result.setFrequency(1);
			result.setDocID(docId);
			result.setDate(date);
			result.setSummary(summary);
			resultList.add(result);
		
		}
	}

}
