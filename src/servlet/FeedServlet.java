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

/**
 * Created by kunqi
 * ON Apr/18/2019 16:57
 */
public class FeedServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            int user_id = Integer.valueOf(session.getAttribute("user_id").toString());
            String statue = request.getParameter("statue");

            String sql = "INSERT into statues (user_id, statue) values (?,?)";
            MysqlDB.insertStatue(sql,user_id,statue);

            sql = "select id from statues order by id desc limit 1";
            ResultSet rs = MysqlDB.query(sql);
            rs.first();
            int record_id = rs.getInt("id");

            sql = "INSERT into events (user_id, operation, table_name, record_id, type) values (?,?,?,?,?)";
            MysqlDB.insertEvent(sql,user_id,"insert","statues",record_id,"createStatue");

            sql = "select id from events order by id desc limit 1";
            rs = MysqlDB.query(sql);
            rs.first();
            int event_id = rs.getInt("id");

            sql = "select friend_id from friends where user_id = ? and is_faked = 0 and is_blocked = 0";
            rs = MysqlDB.queryFriends(sql,user_id);

            sql = "insert into follow_feeds (user_id,event_id) values(?,?)";
            Jedis jedis = new Jedis("localhost");
            while(rs.next()){
                int friend_id = rs.getInt("friend_id");
                String key = "follower_id_" + friend_id;
                jedis.lpush(key,String.valueOf(event_id));
                MysqlDB.insertFollowFeed(sql, friend_id, event_id);
            }
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request,response);


        }catch(SQLException e){
            e.printStackTrace();
        };

    }
}
