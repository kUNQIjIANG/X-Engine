<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>General Search Engine</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/result.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="container">
	<div id="hd" class="ue-clear">
    	<div class="logo"></div>
    	<form action = "loginAction.do">
        <div class="inputArea">
        	<input type="text" class="searchInput" name="inputMessage" />
            <input type="submit" class="searchButton" />
        </div>
        </form>
    </div>

	<div id="bd" class="ue-clear">
        <div id="main">
            <div class="resultArea">
            	<p class="resultTotal">
                	<span class="info">找到约&nbsp;<span class="totalResult"><%=request.getAttribute("newsNum") %></span>&nbsp;条结果(用时<span class="time"><%=request.getAttribute("searchTime") %></span>毫秒)</span>
                </p>
				
				
				
                <div class="resultList">
                    
                    <%
                    for(int i = 0;i<Integer.parseInt(request.getAttribute("newsNum").toString());i++){
                    	
                    	out.println("<div class=\"resultItem\">");
                    	
                    	out.println("<div class=\"itemHead\">");
                    	out.println("<a href=\""+request.getAttribute("newsUrl"+i)+"\""+"  target=\"_blank\" class=\"title\">");
                    	//out.println("<span class=\"keyWord\">");
                    	out.println(request.getAttribute("newsTitle"+i));
                    	out.println("<br>");
                    	//out.println("</span>");
                    	out.println("</a>");
                    	out.println("</div>");
                    	/*
                    	out.println("<div class=\"itemBody\">");

                    	String newsbody = request.getAttribute("newsBody"+i).toString();
                    	if(newsbody.length()>81)
                    		newsbody = newsbody.substring(0, 80);
                    	out.println(newsbody);
                    	out.println("<br>");
                    	out.println("</div>");
                    	*/

                    	
                    	out.println("<div class=\"itemFoot\">");
                    	//out.println("<span class=\"info\">");
                    	out.println("<label>发布时间：</label>");
                    	//out.println("<span class=\"value\">");
                    	out.println(request.getAttribute("newsDate"+i));
                    	//out.println("<br>");
                    	//out.println("<br>");
                    	//out.println("</span>");
                    	//out.println("</span>");
                    	out.println("</div>");
                    	
                    	out.println(" </div>");
                    }
                    %>
                    	
                   
                </div>
 					
            </div>
        </div><!-- End of main -->
    </div><!--End of bd-->
</div>

<div id="foot">Copyright &copy;X-ENGINE 版权所有  E-mail:X-Engine@aliyun.com</div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>

</html>