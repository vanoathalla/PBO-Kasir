package Model;

import java.sql.*;
import Connector.Connector;
import Model.Interface.Kasir;

public class ModelKasir extends Connector implements Kasir {
    // variabel
    private String username, password;
    Statement statement;

    // Konstruktor untuk objek ModelKasir
    public ModelKasir(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter untuk username
    public String getUsername() {
        return username;
    }

    // Setter untuk username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter untuk password
    public String getPassword() {
        return password;
    }

    // Setter untuk password
    public void setPassword(String password) {
        this.password = password;
    }

    // Method membuat query login
    public String login() {
        // Membuat query untuk mengecek apakah username dan password sesuai
        return "SELECT * FROM kasir WHERE username = '" + getUsername() + "' AND password = '"
                + getPassword() + "'";
    }

    // Method memasukkan data transaksi ke database
    public void insertTransaksi(Transaksi transaksi) {
        try {
            statement = conn.createStatement();
            // query untuk memasukkan data transaksi ke tabel transaksi
            statement.executeUpdate("INSERT INTO transaksi (jumlah_barang, total_harga, bayar, kembalian, metode, tanggal, kasir) VALUES ('" + transaksi.getJumlahBarang() + "','" + transaksi.getTotalHarga() + "','" + transaksi.getBayar() + "','" + transaksi.getKembalian() + "','" + transaksi.getMetode() + "','" + transaksi.getDate() + "','" + transaksi.getKasir() + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
