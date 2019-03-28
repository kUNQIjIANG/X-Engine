package dao;

import java.sql.*;

public class MysqlDB {
    private static String url;
    private static String user;
    private static String pwd;
    private static Connection conn;
    private static PreparedStatement pstm;
    private static ResultSet res;
    static {
        url = "jdbc:mysql://localhost:3306/X_db";
        user = "root";
        pwd = "666666";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection(url,user,pwd);
    }

    public static ResultSet query(String sql, String... args)
            throws SQLException{
        conn = getConn();
        pstm = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            pstm.setString(i, args[i - 1]);
        }
        res = pstm.executeQuery();
        return res;
    }

    public static boolean insert(String sql, String... args)
            throws SQLException{
        conn = getConn();
        pstm = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++){
            pstm.setString(i,args[i-1]);
        }
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static boolean insertClick(String sql, int uid, String userName, String url)
            throws SQLException{
        conn = getConn();
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,uid);
        pstm.setString(2,userName);
        pstm.setString(3,url);
        if (pstm.executeUpdate() != 1) return false;
        return true;
    }

    public static void closeAll()
        throws SQLException{
        if (conn != null) conn.close();
        if (pstm != null) pstm.close();
        if (res != null) res.close();
    }

    public static void main(String[] args) throws Exception{
        String sql = "Select * From Users Where name = ?";
        sql = "Insert into Users values(?,?,?)";
        //ResultSet rs = query(sql,"x-man");
        boolean addUser = insert(sql,"test","123");
        System.out.println(addUser);
    }
}
