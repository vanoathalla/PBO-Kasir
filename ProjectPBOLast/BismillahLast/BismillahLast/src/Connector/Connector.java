package Connector;

import java.sql.*;

/**
 * Connector
 */
public class Connector {
    String DBurl = "jdbc:mysql://localhost/kasir";
    String DBusername = "root";
    String DBpassword = "";
    public Connection conn;
    Statement statement;

    public Connector(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(DBurl, DBusername, DBpassword);
            System.out.println("Koneksi Sukses");
        }
        catch(Exception ex){
            System.out.println("failed" + ex.getMessage());
        }
    }
    
}