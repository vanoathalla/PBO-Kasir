package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Connector.Connector;
import Model.Interface.InterfaceTransaksi;

public class Transaksi extends Connector implements InterfaceTransaksi {
    private int id;
    private int jumlahBarang;
    private int totalHarga;
    private int bayar;
    private int kembalian;
    private String metode;
    private String tanggal;
    private String kasir;

    public Transaksi(int jumlahBarang, int totalHarga, int bayar, int kembalian,
            String metode, String tanggal, String kasir) {
        this.jumlahBarang = jumlahBarang;
        this.totalHarga = totalHarga;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.metode = metode;
        this.tanggal = tanggal;
        this.kasir = kasir;
    }

    public void insertDetailTransaksi(Barang barang) {
        if (conn == null || barang == null) {
            return;
        }
        String sql = "INSERT INTO detail_transaksi "
                + "(id_transaksi, nama_barang, harga, kuantitas, total_harga) VALUES (?, ?, ?, ?, ?)";
        try {
            int transactionId = getLastIdTransaksi();
            if (transactionId <= 0) {
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, transactionId);
                ps.setString(2, barang.getNama());
                ps.setInt(3, barang.getHarga());
                ps.setInt(4, barang.getKuantitas());
                ps.setInt(5, barang.getTotalHarga());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastIdTransaksi() {
        if (conn == null) {
            return 0;
        }
        try (Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM transaksi")) {
            return rs.next() ? rs.getInt("id") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String[][] readData() {
        List<String[]> rows = new ArrayList<>();
        if (conn == null) {
            return new String[0][8];
        }
        String sql = "SELECT id, jumlah_barang, total_harga, bayar, kembalian, metode, tanggal, kasir "
                + "FROM transaksi ORDER BY id DESC";
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                rows.add(new String[] {
                    rs.getString("id"), rs.getString("jumlah_barang"), rs.getString("total_harga"),
                    rs.getString("bayar"), rs.getString("kembalian"), rs.getString("metode"),
                    rs.getString("tanggal"), rs.getString("kasir")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows.toArray(new String[rows.size()][8]);
    }

    public String[][] readDetail(int id) {
        List<String[]> rows = new ArrayList<>();
        if (conn == null) {
            return new String[0][4];
        }
        String sql = "SELECT nama_barang, harga, kuantitas, total_harga "
                + "FROM detail_transaksi WHERE id_transaksi = ? ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new String[] { rs.getString("nama_barang"), rs.getString("harga"),
                        rs.getString("kuantitas"), rs.getString("total_harga") });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows.toArray(new String[rows.size()][4]);
    }

    public String[] infoTransaksi(int id) {
        String[] data = new String[] { "", "", "", "", "", "" };
        if (conn == null) {
            return data;
        }
        String sql = "SELECT tanggal, metode, total_harga, bayar, kembalian, kasir FROM transaksi WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data[0] = rs.getString("tanggal");
                    data[1] = rs.getString("metode");
                    data[2] = rs.getString("total_harga");
                    data[3] = rs.getString("bayar");
                    data[4] = rs.getString("kembalian");
                    data[5] = rs.getString("kasir");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void hapusTransaksi(int id) {
        if (conn == null) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM transaksi WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public int getBayar() {
        return bayar;
    }

    public int getKembalian() {
        return kembalian;
    }

    public String getMetode() {
        return metode;
    }

    public String getDate() {
        return tanggal;
    }

    public String getKasir() {
        return kasir;
    }
}
