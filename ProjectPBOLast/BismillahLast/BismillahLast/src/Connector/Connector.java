package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Creates a connection to the local cashier database. */
public class Connector {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/kasir_db?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";

    protected Connection conn;

    public Connector() {
        String url = System.getProperty("kasir.db.url", DEFAULT_URL);
        String username = System.getProperty("kasir.db.username", DEFAULT_USERNAME);
        String password = System.getProperty("kasir.db.password", DEFAULT_PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi database berhasil");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Koneksi database gagal: " + ex.getMessage());
            conn = null;
        }
    }
}
