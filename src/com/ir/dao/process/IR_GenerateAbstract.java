package com.ir.dao.process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class IR_GenerateAbstract {

	/**
	 * @param args
	 */
	public List splitKeyWord(String keyWord){
		List words=new ArrayList<String>();
		String[] result=keyWord.split(" ");
		for(int i=0;i<result.length;i++){
			if(result[i].equals(""))
			{
				continue;
			}
			else
				words.add(result[i]);
		}
		return words;
	}
	 public String generateSummary(String keyword,String searchResult,String fileName){
	    	String summary="This is the summary";
	    	int size=0;
	    	int fromIndex=0;
	    	int count=0;
	    	StringBuffer sb=new StringBuffer();
	    	//如果结果小于200，则全部设为摘要
	    	if(searchResult.length()<200)
	    	{
	    		//System.out.println("searchResult长度小于200");
	    		summary=searchResult;
	    	}
	        //结果大于200,进行截取
	    	else{
	    		List keywords=splitKeyWord(keyword);
	    		Set summarySet=new LinkedHashSet();
	    		for(int i=0;i<keywords.size();i++){
	    			String currentKeyWord=(String) keywords.get(i);
	    			//System.out.println("currentKeyWord="+currentKeyWord);
	    		
	    	while(fromIndex!=-1){
		    	int keywordIndex=searchResult.indexOf(currentKeyWord,fromIndex);
		    	count++;
		    //	System.out.println("count="+count);
		    //	System.out.println("keywordIndex="+keywordIndex);
		    	if(keywordIndex==-1&&count==1){
		    		//System.out.println("第一次查找没有找到keyword");
		    		//summary=searchResult;
		    		summarySet.add(searchResult);
		    //		System.out.println("summary="+summary);
		    		count=0;
		    		break;
		    	}
		    	else if(keywordIndex==-1&&count>1){
		    	//	System.out.println("已经找不到keyword");
		    		break;
		    	}
		    	//有多处匹配的地方
		    	else{
		    	int endIndex=searchResult.indexOf("。", keywordIndex);      //查找keyword后面的第一个句号
		    	int temp=0;
		    	temp=fromIndex;
		    	fromIndex=endIndex;
		    	if(endIndex==-1)
		    		endIndex=keywordIndex+keyword.length();
		    	int beginIndex=searchResult.lastIndexOf("。", keywordIndex);//查找keyword前面的第一个句号
		    	
		    	if(beginIndex==-1){                   //如果前面没有句号则从开始处0返回
		    		beginIndex=temp-1;
		    	}
		    	summary=searchResult.substring(beginIndex+1, endIndex);
		    	summarySet.add(summary);
		    	}
		    	}//while结束
	    		}//for结束,得到一个summarSet
	    		Iterator it=summarySet.iterator();
	    		while(it.hasNext()){
	    			sb.append(it.next());
	    		}
	    	summary=sb.toString();  //统一对summarySet进行处理
	    	//设置摘要长128
	    	if(summary.length()>=200)
	    		summary=summary.substring(0, 200);
	    	}
	    	return summary;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IR_GenerateAbstract g=new IR_GenerateAbstract();
           String keyword="詹姆斯    篮球";
           List result=g.splitKeyWord(keyword);
           for(int i=0;i<result.size();i++){
        	   System.out.println(result.get(i));
           }
           Set<String> s=new HashSet();
           String str="我爱我的祖国中国,它是詹姆斯的圣地。一颗著名的东方明珠中国。詹姆斯篮球屹立于世界强国之林，中国叫撒设计的。的哈市将房间开发的精髓咖啡壶";
           System.out.println(g.generateSummary("中国   詹姆斯", str, "d"));
	}

}
