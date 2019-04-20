package dao;

import java.sql.*;
import java.util.Enumeration;

public class MysqlDB {
    private static String url;
    private static String user;
    private static String pwd;
    private static Connection conn;
    private static PreparedStatement pstm;
    private static ResultSet res;
    static {
        url = "jdbc:mysql://localhost:3306/feeds";
        user = "root";
        pwd = "666";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pwd);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection(url,user,pwd);
    }

    public static ResultSet queryFriends(String sql, int user_id)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,user_id);
        res = pstm.executeQuery();
        return res;
    }

    public static ResultSet queryEvent(String sql, int event_id)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,event_id);
        res = pstm.executeQuery();
        return res;
    }

    //redis 没有queue, 去follow_feeds里拉
    public static ResultSet queryEventId(String sql, int user_id)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,user_id);
        res = pstm.executeQuery();
        return res;
    }


    public static ResultSet queryStatue(String sql, int record_id)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,record_id);
        res = pstm.executeQuery();
        return res;
    }


    public static ResultSet query(String sql, String... args)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            pstm.setString(i, args[i - 1]);
        }
        res = pstm.executeQuery();
        return res;
    }


    public static boolean insert(String sql, String... args)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++){
            pstm.setString(i,args[i-1]);
        }
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static boolean insertClick(String sql, int uid, String userName, String url)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,uid);
        pstm.setString(2,userName);
        pstm.setString(3,url);
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static boolean insertStatue(String sql, int user_id, String statue)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,user_id);
        pstm.setString(2,statue);
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static boolean insertFollowFeed(String sql, int user_id, int event_id)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,user_id);
        pstm.setInt(2,event_id);
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static boolean insertEvent(String sql, int user_id, String operation, String table_name, int record_id, String type)
            throws SQLException{
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,user_id);
        pstm.setString(2,operation);
        pstm.setString(3,table_name);
        pstm.setInt(4,record_id);
        pstm.setString(5,type);
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }


    public static void closeAll()
        throws SQLException{
        if (conn != null) conn.close();
        if (pstm != null) pstm.close();
        if (res != null) res.close();
        /**
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                //LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                //LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }
        }
         */
    }

    public static void main(String[] args) throws Exception{
        String sql = "Select * From Users Where name = ?";
        //sql = "select friend_id from friends where user_id = ? and is_faked = 0 and is_blocked = 0";
        //ResultSet rs = MysqlDB.queryFriends(sql,1);
        //while(rs.next()) System.out.println(rs.getInt("friend_id"));
        //ResultSet rs = query(sql,"x-man");
        //boolean addUser = insert(sql,"test","123");
        //System.out.println(addUser);
        sql = "select id from statues order by id desc limit 2";
        ResultSet rs = MysqlDB.query(sql);
        rs.first();
        System.out.println(rs.getInt("id"));
    }
}
