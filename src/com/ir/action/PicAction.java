package com.ir.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ir.dao.process.PathProperties;
import com.opensymphony.xwork2.ActionSupport;

public class PicAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void getPic(String urll,String path){
	//	String cmd ="cmd /c d:\\url2bmp.exe -url "+url+" -format png -file "+path+" -wx 1024 -wy 768 -removesb -notinteractive";
		String cmd  = "cmd /c " + PathProperties.IECAPT_PATH + " --url="+urll+" --out="+path;
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String show() throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse response = ServletActionContext.getResponse();  
		
	    PrintWriter out = response.getWriter();  
	    //以时间命名，保证不重复，不冲突
	    String picName=System.getProperty("user.name")+System.nanoTime()+".png";
	    
	//    String picName="tttt.png";
	//    if(url.equals("http://www.baidu.com/"))picName="baidu.png";
	//    if(url.equals("http://www.sogou.com/"))picName="sogou.png";
	    //真实的WebApp路径下！
	    String path = PathProperties.PICTURE_STORE_PATH + picName;
	    getPic(url, path);
	    Thread.sleep(6000);
	    out.print(picName);  
	    out.flush();  
	    out.close();  
		return NONE;
	}

	
}
