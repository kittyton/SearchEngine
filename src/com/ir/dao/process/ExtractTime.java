package com.ir.dao.process;

import java.util.Random;

public class ExtractTime {
	
	//输入url，返回时间，格式为year/month/day
	public static String extractTime(String url){
		String time = null;
		if(url.startsWith("http://sports.sina.com.cn")){
			String temp[] = url.split("/");
			time = temp[temp.length-2];
			
		}
		else if(
		   url.startsWith("http://cbachina.sports.sohu.com")||
		   url.startsWith("http://f1.sohu.com")||
		   url.startsWith("http://sports.sohu.com")
				){
			String temp[] = url.split("/");
			time = temp[temp.length-2].substring(0,4)+"-"+temp[temp.length-2].substring(4,6)+"-"+temp[temp.length-2].substring(6,8);
			
		}
		else if(url.startsWith("http://sports.qq.com/")) {
			String str = url.substring(23, 31);
			time = str.substring(0, 4) + '-' + str.substring(4, 6) + '-' + str.substring(6, 8);
		}
		
		else if(url.startsWith("http://sports.people.com.cn/")) {
			String str = url.substring(30, 39);
			str = str.replace("/", "");
			time = str.substring(0, 4) + '-' + str.substring(4, 6) + '-' + str.substring(6, 8);
		}
		System.out.println(time);
		return time;
	}
	
	public static String random(){
		Random ra =new Random();
		return Integer.toString(ra.nextInt(1000));
	}
	
	public static void main(String [] args){
		String url1 = "http://sports.sina.com.cn/nba/2014-11-09/08427402632.shtml";
		String url2 = "http://sports.sohu.com/20141109/n405903996.shtml";
		String url3 = "http://sports.people.com.cn/n/2014/0101/c22137-23996715.html";
		String url4 = "http://sports.qq.com/a/20140101/000095.htm";

		extractTime(url1);
		extractTime(url2);
		extractTime(url3);
		extractTime(url4);
		random();
	}
}
