package servlet;

import dao.MysqlDB;
import redis.clients.jedis.Jedis;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                    int user_id = rs.getInt("id");
                    session.setAttribute("user_id",user_id);

                    // get feeds from queue
                    Jedis jedis = new Jedis("localhost",9779);
                    String key = "follower_id_" + user_id;
                    if(jedis.exists(key)){
                        List<String> list = jedis.lrange(key,0,10);
                        for(int i = 0; i < list.size(); i++){
                            int event_id = Integer.parseInt(list.get(i));
                            sql = "select table_name, record_id from events where event_id = ?";
                            rs = MysqlDB.queryEvent(sql,event_id);
                            String table_name = rs.getString("table_name");
                            int record_id = rs.getInt("record_id");
                            sql = "select user_id, statue from ? where id = ?";
                            rs = MysqlDB.queryStatue(sql,table_name,record_id);
                            String statue_user = rs.getString("user_id");
                            String statue = rs.getString("statue");
                        }
                    }


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
