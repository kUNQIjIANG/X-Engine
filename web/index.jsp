<%--
  Created by IntelliJ IDEA.
  User: kunqi
  Date: 2019-01-27
  Time: 00:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$General Search Engine$</title>
    <Style>
      .center{
        position: fixed;
        top: 30%;
        left: 45%;
        transform: translate(-30%,-50%);
      }
      .feeds{
        position: fixed;
        top: 60%;
        left: 45%;
        transform: translate(-30%,-50%);
      }
    </Style>
  </head>
  <body>
    <div class="center">
      <h1 style="font-size:400%">General Search Engine</h1>
      <form action="/servlet/HomepageServlet" method="GET">
        <input style="width:450px;height:35px" type="text" name="query">
        <input style="width:70px;height:15px" type="submit" value="search">
      </form>
      <form action="/servlet/FeedServlet" method="GET">
        <input style="width:450px;height:35px" type="text" name="statue">
        <input style="width:70px;height:15px" type="submit" value="post">
      </form>
      <!--a href="https://www.baidu.com" onclick="recordClick()" target="_blank">baidu</a-->
    </div>
    <div class="feeds">
      <p>新动态</p>
      <ul>
        <%
          if(request.getAttribute("feedNum") != null) {
            for (int i = 0; i < Integer.parseInt(request.getAttribute("feedNum").toString()); i++) {
              int user_id = Integer.parseInt(request.getAttribute("statue_user" + i).toString());
              String statue = request.getAttribute("statue" + i).toString();
              out.println("<li> statue: " + statue + " from user_" + user_id + "</li>");
            }
          }else{
            out.println("<li> no statue </li?");
          }
        %>
      </ul>
    </div>
    <script>
      function recordClick() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200){

          }
        };
        xhttp.open("GET","/servlet/RecordClickServlet",true);
        xhttp.send();
      }
    </script>
  </body>
</html>
