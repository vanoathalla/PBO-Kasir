package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Connector.Connector;
import Model.Interface.Kasir;

public class ModelKasir extends Connector implements Kasir {
    private String username;
    private String password;

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

    /** Kept for compatibility with the original project interface. */
    public String login() {
        return "SELECT * FROM kasir WHERE username = '" + getUsername() + "' AND password = '" + getPassword() + "'";
    }

    /**
     * Persists the transaction and all its items atomically.
     * Returns false if the transaction could not be inserted.
     */
    public boolean simpanTransaksi(Transaksi transaksi, List<Barang> barang) throws SQLException {
        if (conn == null) {
            throw new SQLException("Tidak dapat terhubung ke database kasir_db.");
        }
        if (transaksi == null || barang == null || barang.isEmpty()) {
            throw new SQLException("Transaksi dan daftar barang wajib diisi.");
        }

        final String insertTransaksi = "INSERT INTO transaksi "
                + "(jumlah_barang, total_harga, bayar, kembalian, metode, tanggal, kasir) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        final String insertDetail = "INSERT INTO detail_transaksi "
                + "(id_transaksi, nama_barang, harga, kuantitas, total_harga) VALUES (?, ?, ?, ?, ?)";

        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            int transactionId;
            try (PreparedStatement ps = conn.prepareStatement(insertTransaksi, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, transaksi.getJumlahBarang());
                ps.setInt(2, transaksi.getTotalHarga());
                ps.setInt(3, transaksi.getBayar());
                ps.setInt(4, transaksi.getKembalian());
                ps.setString(5, transaksi.getMetode());
                ps.setString(6, transaksi.getDate());
                ps.setString(7, transaksi.getKasir());
                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Transaksi tidak berhasil disimpan.");
                }
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Database tidak mengembalikan ID transaksi.");
                    }
                    transactionId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(insertDetail)) {
                for (Barang item : barang) {
                    ps.setInt(1, transactionId);
                    ps.setString(2, item.getNama());
                    ps.setInt(3, item.getHarga());
                    ps.setInt(4, item.getKuantitas());
                    ps.setInt(5, item.getTotalHarga());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException rollbackError) {
                ex.addSuppressed(rollbackError);
            }
            throw ex;
        } finally {
            try {
                conn.setAutoCommit(originalAutoCommit);
            } catch (SQLException ex) {
                System.err.println("Tidak dapat memulihkan mode auto-commit: " + ex.getMessage());
            }
        }
    }

    /** Legacy API retained for callers from the original application. */
    public void insertTransaksi(Transaksi transaksi) {
        if (conn == null) {
            System.err.println("Transaksi gagal: koneksi database tidak tersedia.");
            return;
        }
        String sql = "INSERT INTO transaksi (jumlah_barang, total_harga, bayar, kembalian, metode, tanggal, kasir) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transaksi.getJumlahBarang());
            ps.setInt(2, transaksi.getTotalHarga());
            ps.setInt(3, transaksi.getBayar());
            ps.setInt(4, transaksi.getKembalian());
            ps.setString(5, transaksi.getMetode());
            ps.setString(6, transaksi.getDate());
            ps.setString(7, transaksi.getKasir());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
