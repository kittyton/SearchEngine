package com.ir.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class AutoCompleteAction extends ActionSupport implements RequestAware,
		SessionAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productName;




	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

	private List<String> content;

	Map<String, Object> session;
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session=session;
	}

	Map<String, Object> request;
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	
	public String findresult(){
		try {
			String newName = new String(productName.getBytes("ISO-8859-1"),"utf-8");
			List<String> list = new ArrayList<String>();
//			if(newName.equals("我")){
//				list.add("我们");
//				list.add("我家");
//				list.add("我们的");
//				list.add("我的作业");
//			}
//			if(newName.equals("我们")){
//				list.add("我们");
//				list.add("我们的");
//			}
			list = AutoCompletionProcessor.getInstance().getTopWords(newName, 6);
			setContent(list);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

}
