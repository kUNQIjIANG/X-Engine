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

public class RegisterServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String sql;
        String errMsg = "";
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("password");
        String confPwd = request.getParameter("confirmPassword");

        if (userName.length() == 0) errMsg += "注册失败，用户名不能为空";
        else if (pwd.length() == 0) errMsg += "注册失败，密码不能为空";
        else if (confPwd.length() == 0) errMsg += "注册失败，确认密码不能为空";
        else if (!confPwd.equals(pwd)) errMsg += "注册失败，确认密码不一致";
        else {
            try{
                sql = "Select * From Users Where name = ?";
                ResultSet rs = MysqlDB.query(sql,userName);
                if (rs.next()) errMsg = "用户名已经存在，请重设用户名";
                else{
                    sql = "Insert into Users (name,pwd) values(?,?)";
                    boolean addUser = MysqlDB.insert(sql,userName,pwd);
                    if (!addUser) errMsg += "注册用户失败";
                    else{
                        HttpSession session = request.getSession(true);
                        session.setAttribute("userName",userName);
                        RequestDispatcher rd = request.getRequestDispatcher("/welcome.jsp");
                        rd.forward(request,response);
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        if (!errMsg.equals("")){
            RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
            request.setAttribute("errMsg",errMsg);
            rd.forward(request,response);
        }


    }
}
