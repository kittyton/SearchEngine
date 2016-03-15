package com.ir.dao.process;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IR_Search se=new IR_Search();
		/*String summary=se.generateSummary("中国", "中新网1月2日电据ATP中文网消息，多哈" +
				"公开赛昨天波折不断，三位排名前十的球员穆雷、费雷尔和加斯奎特爆冷出局，" +
				"只有头号种子纳达尔苦战三盘过关。赛后穆雷谈到自己的身体并不在最佳，但是对于前半程的表现满意。而纳达尔也表示，自己的表现优于首轮。　　“我觉得第二盘自己有点慢下来了，无论是发球时速还是移动。并不是某个特别的部位感到酸痛，好像关节的感觉比肌肉的感觉强一些，打这种级别的比赛还是感觉有点酸痛。" +
				"”穆雷在赛后说，“不过我打得还不错，今天有一些球打得很好。比赛开始的时候移动也很好，" +
				"这是积极的信号。我之前也说过最好的恢复方式就是通过比赛，今天就是个开头。”" +
				"这是穆雷在9月中旬接受背部手术之后的第一站正式比赛，接下来他将出征墨尔本。　　" +
				"而对于梅耶尔来说，能够在3比6、0比3的局面下逆转，也是相当的激动人心。“当时我觉得比赛已经结束了，" +
				"但是之后我尝试更具攻击性，更冒险一些。当然他没有在自己的最佳水准。”德国人赛后说，" +
				"“我在赛前就知道，如果我有机会赢他，那也许就是在今天。他有很久没有参赛了，所以我把握住了机会。”梅耶尔将在1/4决赛迎战罗马尼亚人哈内斯库。　　而只有头号种子纳达尔为顶尖球员保存了颜面，他也是五位参赛的TOP10球员唯一进入八强的。“我在球场上花了很多时间，然后赢得了比赛，这是很重要的事情。我觉得今天的表现比昨天强很多" +
				"。我的对手也表现得很好，很有侵略性。”纳达尔在第二盘1-4落后时追平了比分，可惜在抢七中一上来就0-5落后丢掉了一盘。下轮比赛他将对阵此前6胜0负的拉脱维亚人古尔比斯，“他是你不愿意在巡回赛中遇到的球员，他的击球非常重，" +
				"发球也非常强，无论是一发还是二发。他在底线的击球很快，正反手都是这样，是一位很难击败的球员。”window.HLBath=1;(来源:中国新闻网)",
				"FengBowen26001217416017.txt");
		System.out.println(summary);*/
	//	String searchResult="我爱我的祖国中国。一颗著名的东方明珠中国。屹立于世界强国之林，中国叫撒设计的。";
		String searchResult="showPlayer({id:/pvservice/xml//2014/8/19/e059d68e-286b-442a-a9fd-a210e3fcab06.xml,skin:2,norecomm:1,height:411,width:548})青奥村：体验文化感知中国";
		int fromIndex=0;
		String keyword="中国";
		String summary="this is the summary";
		while(fromIndex!=-1){
	    	int keywordIndex=searchResult.indexOf(keyword,fromIndex);
	    	System.out.println("keywordIndex="+keywordIndex);
	    	if(keywordIndex==-1){
	    		System.out.println("已经找不到keyword");
	    		break;
	    	}
	    	else{
	    	int endIndex=searchResult.indexOf("。", keywordIndex);      //查找keyword后面的第一个句号
	    	System.out.println("endIndex="+endIndex);
	    	int temp=0;
	    	temp=fromIndex;
	    	fromIndex=endIndex;
	    	if(endIndex==-1)
	    		endIndex=keywordIndex+keyword.length();
	    	int beginIndex=searchResult.lastIndexOf("。", keywordIndex);//查找keyword前面的第一个句号
	    	
	    	if(beginIndex==-1){                   //如果前面没有句号则从开始处0返回
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
