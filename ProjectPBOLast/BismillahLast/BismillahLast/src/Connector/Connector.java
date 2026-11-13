package Connector;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Connector
 */
public class Connector {
    private static final String DBURL = "jdbc:mysql://localhost:3306/kasir?useSSL=false&serverTimezone=UTC";
    private static final String DBUSERNAME = "root";
    private static final String DBPASSWORD = "";

    protected Connection conn;

    public Connector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            System.out.println("Koneksi Sukses");
        } catch (Exception ex) {
            System.out.println("failed: " + ex.getMessage());
        }
    }
}
