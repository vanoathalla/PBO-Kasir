package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Connector.Connector;
import Model.Barang;
import Model.ModelKasir;
import Model.Transaksi;

public class KasirController extends Connector {

    private ModelKasir modelKasir;
    private Transaksi transaksi;

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
        if (modelKasir == null || conn == null) {
            return false;
        }

        String query = "SELECT 1 FROM kasir WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, modelKasir.getUsername());
            ps.setString(2, modelKasir.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Login gagal diproses: " + e.getMessage());
            return false;
        }
    }

    public boolean simpanTransaksi(Transaksi transaksi, List<Barang> barang) throws SQLException {
        if (modelKasir == null) {
            throw new SQLException("Data kasir tidak tersedia.");
        }
        return modelKasir.simpanTransaksi(transaksi, barang);
    }

    public void insertTransaksi(Transaksi transaksi) {
        if (modelKasir != null) {
            modelKasir.insertTransaksi(transaksi);
        }
    }

    public void insertDetailTransaksi(Barang barang) {
        if (transaksi != null) {
            transaksi.insertDetailTransaksi(barang);
        }
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
