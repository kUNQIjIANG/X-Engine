package servlet;

import dao.MysqlDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by kunqi
 * ON Mar/27/2019 18:31
 */
public class RecordClickServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            //String userName = request.getParameter("userName");
            //int uid = Integer.valueOf(request.getParameter("uid"));
            HttpSession session = request.getSession();
            String userName = session.getAttribute("userName").toString();
            int uid = Integer.valueOf(session.getAttribute("uid").toString());
            String url = request.getParameter("url");
            System.out.println(userName);
            System.out.println(uid);
            String sql = "INSERT into UserDocs (uid, userName, url) values (?,?,?)";
            boolean addClick = MysqlDB.insertClick(sql, uid, userName, url);
            MysqlDB.closeAll();
            System.out.println(addClick);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
            {doGet(request,response);}
}
