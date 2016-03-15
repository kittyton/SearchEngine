package com.ir.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;

import com.ir.bean.SearchResult;
import com.ir.bean.TopSearch;
import com.ir.dao.process.IR_Search;
import com.ir.dao.process.TopSearchProcessor;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport implements RequestAware,ParameterAware{
	private String search;
	private List resultList;
	private int pageNow=1;
	private int pageTotal;
	private int pageSize=10;
	private String newSearch;
	private int totalcount=0;
	private List<String> relatedStringList = new ArrayList<String>();
	private List<TopSearch> topSearchList = new ArrayList<TopSearch>();
	private List<SearchResult> shortList=new ArrayList<SearchResult>();
	
	private String rank="default";
	
	
	
	

	public List<TopSearch> getTopSearchList() {
		return topSearchList;
	}

	public void setTopSearchList(List<TopSearch> topSearchList) {
		this.topSearchList = topSearchList;
	}

	public String getNewSearch() {
		return newSearch;
	}

	public void setNewSearch(String newSearch) {
		this.newSearch = newSearch;
	}

	public List<String> getRelatedStringList() {
		return relatedStringList;
	}

	public void setRelatedStringList(List<String> relatedStringList) {
		this.relatedStringList = relatedStringList;
	}

	public List<SearchResult> getShortList() {
		return shortList;
	}

	public void setShortList(List<SearchResult> shortList) {
		this.shortList = shortList;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearch() {
		return search;
	}  

	public void setSearch(String search) {
		this.search = search;
	}
	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String execute() throws Exception{
		String newSearch;
		ActionContext ac=ActionContext.getContext();
		Map session=ac.getSession();
		if(search!=null){
			newSearch = new String(search.getBytes("ISO-8859-1"),"UTF-8");
			session.put("keyword", newSearch);
		}else{
			newSearch=(String)session.get("keyword");
		}
		IR_Search searcher=new IR_Search();
		searcher.indexSearch("content", newSearch,rank);
		resultList=searcher.getResultList();
		totalcount=resultList.size();
		for(int i=0;i<resultList.size();i++){
			Object ob=resultList.get(i);
			SearchResult result=(SearchResult) ob;
		}
		pageTotal=resultList.size()%pageSize==0?resultList.size()/pageSize:resultList.size()/pageSize+1;
		//以上获取所有searchResult
		//ActionContext ac=ActionContext.getContext();
		//Map session=ac.getSession();
		
		int temp=0;
		temp=pageNow*pageSize;
		if(temp<=resultList.size()){
		for(int i=temp-pageSize;i<temp;i++){
			shortList.add((SearchResult)resultList.get(i));
		}}
		if(temp>resultList.size()){
			temp=resultList.size();
			for(int i=(pageNow-1)*pageSize;i<temp;i++){
				shortList.add((SearchResult)resultList.get(i));
			}
		}
		//session.put("itemList", shortList);	
		
		int if10= 10<resultList.size()?10:resultList.size();
		int[] docIds = new int[if10];
		for(int i=0;i<if10;i++){
			docIds[i]=((SearchResult)resultList.get(i)).getDocID();
		}
		
		RelatedSearchProcessor.getInstance().add(newSearch, docIds);
		relatedStringList = RelatedSearchProcessor.getInstance().getTopQueries(newSearch, 9);
		
		topSearchList = TopSearchProcessor.getIntance().getTopSearches();
		
		return SUCCESS;
		
	}

	public String responseAjax() throws Exception{
		
		ActionContext ac=ActionContext.getContext();
		Map session=ac.getSession();
		if(search!=null){
			newSearch = new String(search.getBytes("ISO-8859-1"),"UTF-8");
			session.put("keyword", newSearch);
		}else{
			newSearch=(String)session.get("keyword");
		}
		IR_Search searcher=new IR_Search();
		searcher.indexSearch("content", newSearch,rank);
		resultList=searcher.getResultList();
		totalcount=resultList.size();
		for(int i=0;i<resultList.size();i++){
			Object ob=resultList.get(i);
			SearchResult result=(SearchResult) ob;
		}
		pageTotal=resultList.size()%pageSize==0?resultList.size()/pageSize:resultList.size()/pageSize+1;
		int temp=0;
		temp=pageNow*pageSize;
		if(temp<=resultList.size()){
		for(int i=temp-pageSize;i<temp;i++){
			shortList.add((SearchResult)resultList.get(i));
		}}
		if(temp>resultList.size()){
			temp=resultList.size();
			for(int i=(pageNow-1)*pageSize;i<temp;i++){
				shortList.add((SearchResult)resultList.get(i));
			}
		}	
		
		int if10= 10<resultList.size()?10:resultList.size();
		int[] docIds = new int[if10];
		for(int i=0;i<if10;i++){
			docIds[i]=((SearchResult)resultList.get(i)).getDocID();
		}
		
		RelatedSearchProcessor.getInstance().add(newSearch, docIds);
		relatedStringList = RelatedSearchProcessor.getInstance().getTopQueries(newSearch, 9);
		
		topSearchList = TopSearchProcessor.getIntance().getTopSearches();
		return SUCCESS;
		
	}
	Map<String,Object> request;
	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}

	@Override
	public void setParameters(Map<String, String[]> arg0) {
		// TODO Auto-generated method stub
		
	}

}
