<%--
  Created by IntelliJ IDEA.
  User: kunqi
  Date: 2019-03-21
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>

</head>
<body>
    <!--输出错误提示信息 -->
    <span style="color:red; font-weight:bold">
        <%
            if(request.getAttribute("errMsg") != null){
                out.println(request.getAttribute("errMsg") + "<br/>");
            }
        %>
    </span>
    请输入用户名和密码：
    <form id="login" method="post" action="login">
        用户名：<input type="text" name="userName" /><br/>
        密码&nbsp;<input type="password" name="password"/><br/>
        <input type="submit" value="登陆"/><br/>
    </form>
    <a href="register.jsp">注册</a>
</body>
</html>
