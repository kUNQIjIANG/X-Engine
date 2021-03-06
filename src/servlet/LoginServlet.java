package servlet;

import com.sun.deploy.net.HttpRequest;
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

    public ResultSet getStatueFeed(int event_id){
        try {
            String sql = "select table_name, record_id from events where id = ?";
            ResultSet rs = MysqlDB.queryEvent(sql, event_id);
            rs.first();
            String table_name = rs.getString("table_name");
            int record_id = rs.getInt("record_id");
            sql = "select user_id, statue from " + table_name + " where id = ?";
            rs = MysqlDB.queryStatue(sql, record_id);
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setStatueInSession(HttpServletRequest request, ResultSet rs, int i){
        try {
            rs.first();
            String statue_user = rs.getString("user_id");
            String statue = rs.getString("statue");
            request.setAttribute("statue_user" + i, statue_user);
            request.setAttribute("statue" + i, statue);
        }catch( SQLException e){
            e.printStackTrace();
        }

    }

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        String sql;
        String errMsg = "";
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("password");

        try{
            sql = "Select id, password From Users where name = ?";
            ResultSet rs = MysqlDB.query(sql,userName);
            if(rs.next()){
                if (rs.getString("password").equals(pwd)){
                    HttpSession session = request.getSession(true);
                    //session.setAttribute("userName",userName);
                    int user_id = rs.getInt("id");
                    session.setAttribute("user_id",user_id);

                    // get feeds from queue
                    Jedis jedis = new Jedis("localhost",6379);
                    String key = "follower_id_" + user_id;
                    if(jedis.exists(key)){
                        System.out.println("feed from redis");
                        List<String> list = jedis.lrange(key,0,10);
                        request.setAttribute("feedNum", list.size());
                        for(int i = 0; i < list.size(); i++){
                            int event_id = Integer.parseInt(list.get(i));
                            rs = getStatueFeed(event_id);
                            setStatueInSession(request,rs,i);
                        }
                    }else{
                        System.out.println("feed from mysql");
                        sql = "select event_id from follow_feeds where user_id = ? order by event_id DESC limit 10";
                        rs = MysqlDB.queryEventId(sql,user_id);
                        int feedNum = rs.getFetchSize();
                        request.setAttribute("feedNum",feedNum);
                        int i = 0;
                        while(rs.next()){
                            int event_id = rs.getInt("event_id");
                            rs = getStatueFeed(event_id);
                            setStatueInSession(request,rs,i);
                            i++;
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
