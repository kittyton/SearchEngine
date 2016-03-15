package com.ir.dao.preprocess;

public class SourcePreprocess {
public  void prePrcocess(String inputDir,String outputDir){
	
}
public String replace(String str,String filteredStr){
	//System.out.println("传过来的title="+str+"  被过滤的字符串是="+filteredStr);
	String afterReplace=null;
	int index=str.indexOf(filteredStr);
	if(index!=-1){
		afterReplace=str.replaceAll(filteredStr,"");
	}
	else
		afterReplace=str;
	return afterReplace;
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SourcePreprocess pre=new SourcePreprocess();
		String str="中国的&nbsp詹姆斯，世界的&nbsp詹姆斯";
		String result=pre.replace(str, "&nbsp");
		System.out.println(result);
		

	}

}
