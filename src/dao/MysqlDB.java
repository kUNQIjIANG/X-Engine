package dao;

import java.sql.*;

public class MysqlDB {
    private static String url;
    private static String user;
    private static String pwd;
    static {
        url = "jdbc:mysql://localhost:3306/usersDB";
        user = "root";
        pwd = "66666666";
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection(url,user,pwd);
    }

    public static ResultSet query(String sql, String... args)
            throws SQLException{
        Connection conn = getConn();
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) {
            pStmt.setString(i, args[i - 1]);
        }
        return pStmt.executeQuery();
    }

    public static boolean insert(String sql, String... args)
            throws SQLException{
        Connection conn = getConn();
        PreparedStatement pStmt = conn.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++){
            pStmt.setString(i,args[i-1]);
        }
        if (pStmt.executeUpdate() != 1) return false;
        return true;
    }

    public static void closeAll(Connection conn, PreparedStatement pStmt, ResultSet rs)
        throws SQLException{
        if (conn != null) conn.close();
        if (pStmt != null) pStmt.close();
        if (rs != null) rs.close();
    }
}
