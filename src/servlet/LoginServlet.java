package servlet;

import dao.MysqlDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        String sql;
        String errMsg = "";
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("password");

        try{
            sql = "Select id, pwd From Users where name = ?";
            ResultSet rs = MysqlDB.query(sql,userName);
            if(rs.next()){
                if (rs.getString("pwd").equals(pwd)){
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName",userName);
                    int uid = rs.getInt("id");
                    session.setAttribute("uid",uid);
                    MysqlDB.closeAll();
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request,response);
                } else errMsg += "您的用户密码错误，请重新输入";
            }else errMsg += "您的用户名不存在，请先注册";
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(!errMsg.equals("")){
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            request.setAttribute("errMsg",errMsg);
            rd.forward(request,response);
        }
    }
}
