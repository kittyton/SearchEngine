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
	    	//������С��200����ȫ����ΪժҪ
	    	if(searchResult.length()<200)
	    	{
	    		//System.out.println("searchResult����С��200");
	    		summary=searchResult;
	    	}
	        //�������200,���н�ȡ
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
		    		//System.out.println("��һ�β���û���ҵ�keyword");
		    		//summary=searchResult;
		    		summarySet.add(searchResult);
		    //		System.out.println("summary="+summary);
		    		count=0;
		    		break;
		    	}
		    	else if(keywordIndex==-1&&count>1){
		    	//	System.out.println("�Ѿ��Ҳ���keyword");
		    		break;
		    	}
		    	//�жദƥ��ĵط�
		    	else{
		    	int endIndex=searchResult.indexOf("��", keywordIndex);      //����keyword����ĵ�һ�����
		    	int temp=0;
		    	temp=fromIndex;
		    	fromIndex=endIndex;
		    	if(endIndex==-1)
		    		endIndex=keywordIndex+keyword.length();
		    	int beginIndex=searchResult.lastIndexOf("��", keywordIndex);//����keywordǰ��ĵ�һ�����
		    	
		    	if(beginIndex==-1){                   //���ǰ��û�о����ӿ�ʼ��0����
		    		beginIndex=temp-1;
		    	}
		    	summary=searchResult.substring(beginIndex+1, endIndex);
		    	summarySet.add(summary);
		    	}
		    	}//while����
	    		}//for����,�õ�һ��summarSet
	    		Iterator it=summarySet.iterator();
	    		while(it.hasNext()){
	    			sb.append(it.next());
	    		}
	    	summary=sb.toString();  //ͳһ��summarySet���д���
	    	//����ժҪ��128
	    	if(summary.length()>=200)
	    		summary=summary.substring(0, 200);
	    	}
	    	return summary;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IR_GenerateAbstract g=new IR_GenerateAbstract();
           String keyword="ղķ˹    ����";
           List result=g.splitKeyWord(keyword);
           for(int i=0;i<result.size();i++){
        	   System.out.println(result.get(i));
           }
           Set<String> s=new HashSet();
           String str="�Ұ��ҵ�����й�,����ղķ˹��ʥ�ء�һ�������Ķ��������й���ղķ˹��������������ǿ��֮�֣��й�������Ƶġ��Ĺ��н����俪���ľ��迧�Ⱥ�";
           System.out.println(g.generateSummary("�й�   ղķ˹", str, "d"));
	}

}
