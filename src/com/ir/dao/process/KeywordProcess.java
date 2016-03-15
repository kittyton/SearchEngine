package com.ir.dao.process;

import java.util.ArrayList;
import java.util.List;

public class KeywordProcess {
     private String keyWord;
     
 	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

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
	

}
