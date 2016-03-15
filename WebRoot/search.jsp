<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page import="com.ir.bean.SearchResult"%>
<%@ page import="com.ir.bean.TopSearch"%>
<%@ page import="com.ir.dao.process.KeywordProcess" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link style="text/css" rel="stylesheet"  href="css/certaininfo.css"/>
<script src="jquery/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="jquery/jquery.autocomplete.js"></script>
	<link rel="Stylesheet" type="text/css" href="jquery/jquery.autocomplete.css" />
			<script type="text/javascript">
	var request;
	try {
		request = new XMLHttpRequest();
	} catch (trymicrosoft) {
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (othermicrosoft) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (failed) {
				request = false;
			}
		}
	}
	if (!request)
		alert("Error initializing XMLHttpRequest!");

	this.screenshotPreview = function() {
		/* CONFIG */

		xOffset = 175;
		yOffset = 30;

		//loading img
		xxOffset = 140;
		yyOffset = 20;

		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result

		/* END CONFIG */
		var timer;
		$("a.screenshot")
				.hover(
						function() {
							var self = this;
							var url = "getPic.action";
							$(self).css("color", "red");

							timer = setTimeout(
									function() {
										timer = 0;
										if (self.rel == "") {//判断是否载入过，如果没有载入，则第一次载入
											document
													.getElementById("loadingImg").style.display = "block";
											request.open("post", url, true);
											request
													.setRequestHeader(
															'Content-type',
															'application/x-www-form-urlencoded');
											request.setRequestHeader(
													'User-Agent', 'XMLHTTP');
											request.send("url=" + self.href);
											request.onreadystatechange = function(
													e) {
												if (request.responseText != "") {
													document
															.getElementById("loadingImg").style.display = "none";
													$("body").append("<img id='screenshot' src='http://localhost:8080/SearchEngine/Pic/"+ request.responseText +"' alt='url preview' />");
												//	document
												//			.getElementById("screenshot").src = "http://localhost:8080/SearchEngine/Pic/"
												//			+ request.responseText;
													self.rel = request.responseText;
													document
															.getElementById("screenshot").style.display = "none";

													$("#screenshot")
															.css(
																	"top",
																	(e.pageY - xOffset)
																			+ "px")
															.css(
																	"left",
																	(e.pageX + yOffset)
																			+ "px")
															.fadeIn("fast");

												}
											};
										} else {//若载入过，则直接利用之前的图
											document
													.getElementById("screenshot").src = "http://localhost:8080/Preview/Pic/"
													+ self.rel;
											document
													.getElementById("screenshot").style.display = "block";

											$("#screenshot").css("top",
													(e.pageY - xOffset) + "px")
													.css(
															"left",
															(e.pageX + yOffset)
																	+ "px")
													.fadeIn("fast");
										}
									}, 2000);

						},
						function() {
							if (timer) {
								clearTimeout(timer);
								timer = 0;
								$(this).css("color", "");
								return;
							}
							$(this).css("color", "");
							document.getElementById("screenshot").style.display = "none";
						});
		$("a.screenshot").mousemove(
				function(e) {
					$("#screenshot").css("top", (e.pageY - xOffset) + "px")
							.css("left", (e.pageX + yOffset) + "px");
					$("#loadingImg").css("top", (e.pageY - xxOffset) + "px")
							.css("left", (e.pageX + yyOffset) + "px");

				});
	};

	// starting the script on page load
	$(document).ready(function() {
		screenshotPreview();
	});
</script>
<script type="text/javascript">
		var v_product_Store ;
		$().ready(function() {
			$("#SearchText").autocomplete("findresult.action", {  //当用户输入关键字的时候 ，通过 url的方式调用action的findStoreProdctListNameForJson方法
				minChars: 1,  //最小显示条数
				max: 12,  //最大显示条数
				autoFill: false,
				dataType : "json",  //指定数据类型的渲染方式
				extraParams: 
		        {   
		             productName: function() 
		              { 
		               return $("#SearchText").val();    //url的参数传递
		              }   
		           },

				 //进行对返回数据的格式处理
        		 parse: function(data) 
         			{
		             var rows = [];
		               for(var i=0; i<data.length; i++)
		           		 {	
			                 rows[rows.length] = {
			                   data:data[i],
			                   value:data[i],
			                  //result里面显示的是要返回到列表里面的值  
			                   result:data[i]
			                 };
		               }           
	               	return rows;
	           	},
				formatItem: function(item) {
					//没有特殊的要求,直接返回了
                  		return item;
				}
			});
		
		});
		
		
	</script>		

			<style>
#screenshot {
	height: 1000px;
	width: 350px;
	position: absolute;
	border: 4px solid #66b3ff;
	background: #fff;
	display: none;
	color: #fff;
}


#loadingImg {
	position: absolute;
	display: none;
	margin-top: 100px;
	width: 33px;
	height: 33px;
}
</style>
</head>

<body>
<div class="left">
<a href="index.jsp"><image src="pic/logo.png" height="90px" width="90px"/></a>
</div>
<div class="middle">
<div class="form-wrapper cf">
<form action="search">
	<input name="search" id="SearchText" type="text"  value="<s:property value="#session.keyword"/>" placeholder="Search here..." required>
	<button type="submit">Search</button>
</form>

</div>
<table style="margin-top:10px">
<tr style="vertical-align: top">
<td>千度为您找到相关结果:<s:property value="totalcount"/></td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
 <td> 
<s:form action="search" theme="simple">
<s:radio list="#{'date':'时间','heat':'热度'}" name="rank" value="#{rank }"></s:radio>
<s:submit value="高级搜索"></s:submit>
</s:form>
</td> 
</tr>
</table>
<div class="show">
<div class="showleft" id="showleft">
<sx:head/>
<div id="show_item">
<% 
	List<SearchResult> shortList=(List<SearchResult>)request.getAttribute("shortList");
	String key = (String)session.getAttribute("keyword");
	KeywordProcess pro=new KeywordProcess();
	List keyWords=pro.splitKeyWord(key);
for(SearchResult result:shortList)
{ 
%>
<div class="showitem">
<div class="title"><a href=<%=result.getUrl() %> target="_blank" class="screenshot" >
<%
String title=result.getTitle();
for(int i=0;i<keyWords.size();i++)
{
String k=(String)keyWords.get(i);
System.out.println("keyWords["+i+"]="+k);
title=title.replace(k,"<span style='color:#F00'>"+k+"</span>");
}
 %>
<%=title  %></a>
</div>
<div class="content">
<% 
String con=result.getContent();
for(int i=0;i<keyWords.size();i++)
{
String k=(String)keyWords.get(i);
System.out.println("keyWords["+i+"]="+k);
con=con.replace(k,"<span style='color:#F00'>"+k+"</span>");
}
%>
<%=con %>
</div>
<div class="foot"><span class="link"><a href=<%=result.getUrl() %> target="_blank"><%=result.getUrl() %></a></span><span><%=result.getDate() %></span><span class="shot">&nbsp;<a href=""></a></span></div>
</div>
<%} %>

</div>
<div id="relative" class="show_relative">
<table  style = "margin-top:20px;font-size:18px" class="relative" cellpadding="10px">
<tr style="font-size:20px;font-weight:bold "><td>相关搜索</td></tr>
<%
   List<String> relatedStrings=(List<String>)request.getAttribute("relatedStringList");
%>


<tr>
	<td><a href="search.action?search=<%=relatedStrings.get(0) %>"><% if(relatedStrings.size()>=1)%><%=relatedStrings.get(0) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(1) %>"><% if(relatedStrings.size()>=2)%><%=relatedStrings.get(1) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(2) %>"><% if(relatedStrings.size()>=3)%><%=relatedStrings.get(2) %><%else %><%="" %></a></td>
</tr>
<tr>
	<td><a href="search.action?search=<%=relatedStrings.get(3) %>"><% if(relatedStrings.size()>=4)%><%=relatedStrings.get(3) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(4) %>"><% if(relatedStrings.size()>=5)%><%=relatedStrings.get(4) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(5) %>"><% if(relatedStrings.size()>=6)%><%=relatedStrings.get(5) %><%else %><%="" %></a></td>
</tr>
<tr>
	<td><a href="search.action?search=<%=relatedStrings.get(6) %>"><% if(relatedStrings.size()>=7)%><%=relatedStrings.get(6) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(7) %>"><% if(relatedStrings.size()>=8)%><%=relatedStrings.get(7) %><%else %><%="" %></a></td>
	<td><a href="search.action?search=<%=relatedStrings.get(8) %>"><% if(relatedStrings.size()>=9)%><%=relatedStrings.get(8) %><%else %><%="" %></a></td>
</tr>
</table>

</div>
<div id="pages">
<!-- 首页 -->
  <s:url id="url_first" value="ajaxsearch">
   <s:param name="pageNow" value="1"></s:param>
   <s:param name="rank" value="%{rank}"></s:param>
  </s:url>
  <!-- 上一页 -->
  <s:url id="url_pre" value="ajaxsearch">
   <s:param name="pageNow" value="pageNow-1"></s:param>
   <s:param name="rank" value="%{rank}"></s:param>
  </s:url>
  <!-- 下一页 -->
  <s:url id="url_next" value="ajaxsearch">
   <s:param name="pageNow" value="pageNow+1"></s:param>
   <s:param name="rank" value="%{rank}"></s:param>
  </s:url>
  <!-- 末页 -->
  <s:url id="url_last" value="ajaxsearch">
   <s:param name="pageNow" value="pageTotal"></s:param>
   <s:param name="rank" value="%{rank}"></s:param>
  </s:url>
  
   <!-- 如果不是首页则提供首页的链接,如果是首页则不提供链接,以下类似 -->
  <s:if test="pageNow != 1">
            [<sx:a targets="showleft" href="%{url_first}">首页</sx:a>]   
              </s:if>
  <s:else>
            [首页]
        </s:else>
  <s:if test="pageNow>1">
            [<sx:a targets="showleft" href="%{url_pre}">上一页</sx:a>] 
        </s:if>
  <s:else>
            [上一页] 
        </s:else>
  <s:if test=" pageTotal > pageNow ">
            [<sx:a targets="showleft" href="%{url_next}">下一页</sx:a>]
        </s:if>
  <s:else>
            [下一页]
        </s:else>
  <s:if test="pageTotal != pageNow">
            [<sx:a targets="showleft" href="%{url_last}">末页</sx:a>]
        </s:if>
  <s:else>
            [末页]
        </s:else>
  第${pageNow}页/ 共${pageTotal}页
</div>
 
</div>

		
<div class="showright">
<table class="topSearch" cellpadding="10px">
<%
   List<TopSearch> topSearchStrings=(List<TopSearch>)request.getAttribute("topSearchList");
%>

<h4 style="margin-left:20px;margin-bottom:3px;">大家都在搜</h4>
<tr style="background-color:#FFBBFF;height:15px; font-size:15px;color:#666666">
	<td style="border-style:blue;width:70%;text-align:left;">身边热词</td>
	<td style="border-style:none;text-align:center">人气指数</td>
</tr>
<tr>
<% if(topSearchStrings.size()>=1){%>
	<td style="width:70%;text-align:left;"><img src="images/1.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(0).query %>" style=""><%=topSearchStrings.get(0).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(0).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=2){%>
	<td style="width:70%;text-align:left;"><img src="images/2.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(1).query %>" style=""><%=topSearchStrings.get(1).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(1).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=3){%>
	<td style="width:70%;text-align:left;"><img src="images/3.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(2).query %>" style=""><%=topSearchStrings.get(2).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(2).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=4){%>
	<td style="width:70%;text-align:left;"><img src="images/4.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(3).query %>" style=""><%=topSearchStrings.get(3).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(3).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=5){%>
	<td style="width:70%;text-align:left;"><img src="images/5.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(4).query %>" style=""><%=topSearchStrings.get(4).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(4).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=6){%>
	<td style="width:70%;text-align:left;"><img src="images/6.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(5).query %>" style=""><%=topSearchStrings.get(5).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(5).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=7){%>
	<td style="width:70%;text-align:left;"><img src="images/7.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(6).query %>" style=""><%=topSearchStrings.get(6).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(6).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=8){%>
	<td style="width:70%;text-align:left;"><img src="images/8.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(7).query %>" style=""><%=topSearchStrings.get(7).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(7).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=9){%>
	<td style="width:70%;text-align:left;"><img src="images/9.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(8).query %>" style=""><%=topSearchStrings.get(8).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(8).count %></td>
	</tr>
<%} %>
</tr>
<tr>
<% if(topSearchStrings.size()>=10){%>
	<td style="width:70%;text-align:left;"><img src="images/10.png" style="height:15px;width:15px;"/> <a href="search.action?search=<%=topSearchStrings.get(9).query %>" style=""><%=topSearchStrings.get(9).query %></a></td>
	<td style="text-align:center"><%=topSearchStrings.get(9).count %></td>
	</tr>
<%} %>
</tr>

</table>
</div>
   <div class="last">
©2014 Qiandu 使用千度前必读 京ICP证8888888号</br>
中国科学院大学</br>
信息检索大作业
</div>
</div>
</div>
<div class="right"></div>
<!--<img id="screenshot" src="images/tttt.png" />
		  -->	<img id="loadingImg" src="images/loading4.gif" />
		  
</body>
</html>
