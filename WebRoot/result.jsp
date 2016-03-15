<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ir.bean.SearchResult"%>
<%@ page import="com.ir.dao.process.KeywordProcess" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
   <title>
   result
   </title>
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

		alert("dsafasdf");
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


  </head>
  
  <body>
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
<div class="title"><a href=<%=result.getUrl() %> class="screenshot" >
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
<table class="relative" cellpadding="10px">
<tr><td><h4>相关搜索</h4></td></tr>
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
            [下一页]<a href="http://sports.sohu.com/20141109/n405896913.shtml" class="screenshot" >tttt</a>
        </s:else>
  <s:if test="pageTotal != pageNow">
            [<sx:a targets="showleft" href="%{url_last}">末页</sx:a>]
        </s:if>
  <s:else>
            [末页]
        </s:else>
  第${pageNow}页/ 共${pageTotal}页
</div>
<img id="loadingImg" src="images/loading4.gif" />
</body>
</html>
