package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Connector.Connector;
import Model.Barang;
import Model.ModelKasir;
import Model.Transaksi;

public class KasirController extends Connector {

    private ModelKasir modelKasir;
    private Transaksi transaksi;
    private Statement statement;
    private ResultSet rs;

    public KasirController(ModelKasir modelKasir) {
        this.modelKasir = modelKasir;
    }

    public KasirController(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public KasirController() {
        this.transaksi = new Transaksi(0, 0, 0, 0, "", "", "");
    }

    public boolean cekLogin() {
        if (modelKasir == null) {
            return false;
        }

        try {
            String query = "SELECT * FROM kasir WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, modelKasir.getUsername().trim());
            preparedStatement.setString(2, modelKasir.getPassword());

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Login Sukses");
                return true;
            }

            System.out.println("Login Gagal");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertTransaksi(Transaksi transaksi) {
        modelKasir.insertTransaksi(transaksi);
    }

    public void insertDetailTransaksi(Barang barang) {
        transaksi.insertDetailTransaksi(barang);
    }

    public String[][] daftarTransaksi() {
        return transaksi.readData();
    }

    public String[][] detailTransaksi(int id) {
        return transaksi.readDetail(id);
    }

    public String[] infoTransaksi(int id) {
        return transaksi.infoTransaksi(id);
    }

    public void hapusTransaksi(int id) {
        transaksi.hapusTransaksi(id);
    }
}
