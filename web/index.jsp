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
    </Style>
  </head>
  <body>
    <div class="center">
      <h1 style="font-size:400%">General Search Engine</h1>
      <form action="/servlet/HomepageServlet" method="GET">
        <input style="width:450px;height:35px" type="text" name="query">
        <input style="width:70px;height:15px" type="submit" value="search">
      </form>
    </div>
  </body>
</html>
