package com.ir.dao.process;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IR_Search se=new IR_Search();
		/*String summary=se.generateSummary("�й�", "������1��2�յ��ATP��������Ϣ�����" +
				"���������첨�۲��ϣ���λ����ǰʮ����Ա���ס����׶��ͼ�˹���ر�����֣�" +
				"ֻ��ͷ�������ɴ����ս���̹��ء���������̸���Լ������岢������ѣ����Ƕ���ǰ��̵ı������⡣���ɴ��Ҳ��ʾ���Լ��ı����������֡��������Ҿ��õڶ����Լ��е��������ˣ������Ƿ���ʱ�ٻ����ƶ���������ĳ���ر�Ĳ�λ�е���ʹ������ؽڵĸо��ȼ���ĸо�ǿһЩ�������ּ���ı������Ǹо��е���ʹ��" +
				"������������˵���������Ҵ�û�����������һЩ���úܺá�������ʼ��ʱ���ƶ�Ҳ�ܺã�" +
				"���ǻ������źš���֮ǰҲ˵����õĻָ���ʽ����ͨ��������������Ǹ���ͷ����" +
				"����������9����Ѯ���ܱ�������֮��ĵ�һվ��ʽ��������������������ī����������" +
				"������÷Ү����˵���ܹ���3��6��0��3�ľ�������ת��Ҳ���൱�ļ������ġ�����ʱ�Ҿ��ñ����Ѿ������ˣ�" +
				"����֮���ҳ��Ը��߹����ԣ���ð��һЩ����Ȼ��û�����Լ������ˮ׼�����¹�������˵��" +
				"��������ǰ��֪����������л���Ӯ������Ҳ������ڽ��졣���кܾ�û�в����ˣ������Ұ���ס�˻��ᡣ��÷Ү������1/4����ӭս���������˹���˹�⡣������ֻ��ͷ�������ɴ��Ϊ������Ա���������棬��Ҳ����λ������TOP10��ԱΨһ�����ǿ�ġ����������ϻ��˺ܶ�ʱ�䣬Ȼ��Ӯ���˱��������Ǻ���Ҫ�����顣�Ҿ��ý���ı��ֱ�����ǿ�ܶ�" +
				"���ҵĶ���Ҳ���ֵúܺã����������ԡ����ɴ���ڵڶ���1-4���ʱ׷ƽ�˱ȷ֣���ϧ��������һ������0-5��󶪵���һ�̡����ֱ������������ǰ6ʤ0��������ά���˹Ŷ���˹���������㲻Ը����Ѳ��������������Ա�����Ļ���ǳ��أ�" +
				"����Ҳ�ǳ�ǿ��������һ�����Ƕ��������ڵ��ߵĻ���ܿ죬�����ֶ�����������һλ���ѻ��ܵ���Ա����window.HLBath=1;(��Դ:�й�������)",
				"FengBowen26001217416017.txt");
		System.out.println(summary);*/
	//	String searchResult="�Ұ��ҵ�����й���һ�������Ķ��������й�������������ǿ��֮�֣��й�������Ƶġ�";
		String searchResult="showPlayer({id:/pvservice/xml//2014/8/19/e059d68e-286b-442a-a9fd-a210e3fcab06.xml,skin:2,norecomm:1,height:411,width:548})��´壺�����Ļ���֪�й�";
		int fromIndex=0;
		String keyword="�й�";
		String summary="this is the summary";
		while(fromIndex!=-1){
	    	int keywordIndex=searchResult.indexOf(keyword,fromIndex);
	    	System.out.println("keywordIndex="+keywordIndex);
	    	if(keywordIndex==-1){
	    		System.out.println("�Ѿ��Ҳ���keyword");
	    		break;
	    	}
	    	else{
	    	int endIndex=searchResult.indexOf("��", keywordIndex);      //����keyword����ĵ�һ�����
	    	System.out.println("endIndex="+endIndex);
	    	int temp=0;
	    	temp=fromIndex;
	    	fromIndex=endIndex;
	    	if(endIndex==-1)
	    		endIndex=keywordIndex+keyword.length();
	    	int beginIndex=searchResult.lastIndexOf("��", keywordIndex);//����keywordǰ��ĵ�һ�����
	    	
	    	if(beginIndex==-1){                   //���ǰ��û�о����ӿ�ʼ��0����
	    		beginIndex=temp-1;
	    	}
	    	System.out.println("beginIndex="+beginIndex);
	    	System.out.println("beginIndex+1="+(beginIndex+1)+"  endIndex+1="+(endIndex+1));
	    	summary=searchResult.substring(beginIndex+1, endIndex);
	    	System.out.println("summary="+summary);
	    	//System.out.println("searchResult.len="+searchResult.length()+"  keywordIndex="+keywordIndex+"  beginIndex="+beginIndex+"  endIndex="+endIndex);
	    	
	    	}
	    	}

	}

}
