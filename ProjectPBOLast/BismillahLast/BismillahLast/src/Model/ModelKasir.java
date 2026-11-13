package Model;

import java.sql.Statement;

import Connector.Connector;
import Model.Interface.Kasir;

public class ModelKasir extends Connector implements Kasir {
    private String username, password;
    Statement statement;

    public ModelKasir(String username, String password) {
        this.username = username == null ? "" : username.trim();
        this.password = password == null ? "" : password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public String login() {
        return "SELECT * FROM kasir WHERE username = '" + getUsername() + "' AND password = '" + getPassword() + "'";
    }

    public void insertTransaksi(Transaksi transaksi) {
        try {
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO transaksi (jumlah_barang, total_harga, bayar, kembalian, metode, tanggal, kasir) VALUES ('" + transaksi.getJumlahBarang() + "','" + transaksi.getTotalHarga() + "','" + transaksi.getBayar() + "','" + transaksi.getKembalian() + "','" + transaksi.getMetode() + "','" + transaksi.getDate() + "','" + transaksi.getKasir() + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
