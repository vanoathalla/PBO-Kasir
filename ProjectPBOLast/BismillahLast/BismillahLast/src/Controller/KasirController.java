package Controller;

import java.sql.*;

import Connector.Connector;
import Model.Barang;
import Model.ModelKasir;
import Model.Transaksi;

public class KasirController extends Connector {

    // Deklarasi objek modelKasir dari class ModelKasir
    private ModelKasir modelKasir;

    // Deklarasi objek transaksi dari class Transaksi
    private Transaksi transaksi;

    // Statement untuk eksekusi query SQL
    private Statement statement;

    // ResultSet untuk menyimpan hasil query
    private ResultSet rs;

    // Konstruktor dengan parameter ModelKasir
    public KasirController(ModelKasir modelKasir) {
        this.modelKasir = modelKasir; // Inisialisasi modelKasir
    }

    // Konstruktor dengan parameter Transaksi
    public KasirController(Transaksi transaksi) {
        this.transaksi = transaksi; // Inisialisasi transaksi
    }

    // Konstruktor tanpa parameter
    public KasirController() {
        // Inisialisasi objek transaksi dengan nilai default (0 dan string kosong)
        this.transaksi = new Transaksi(0, 0, 0, 0, "", "", "");
    }

    // Method untuk melakukan pengecekan login kasir
    public boolean cekLogin() {
        try {
            // Membuat statement SQL dari koneksi
            statement = conn.createStatement();

            // Menjalankan query login dari modelKasir
            rs = statement.executeQuery(modelKasir.login());

            // Jika ada data yang cocok dengan username dan password
            if (rs.next()) {
                System.out.println("Login Sukses"); // Cetak login sukses
                return true; // Mengembalikan nilai true jika login berhasil
            } else {
                System.out.println("Login Gagal"); // Cetak login gagal
                return false; // Mengembalikan nilai false jika login gagal
            }
        } catch (Exception e) {
            e.printStackTrace(); // Menampilkan error jika terjadi exception
            return false; // Mengembalikan nilai false jika terjadi error
        }
    }

    // Method untuk menyimpan data transaksi ke database
    public void insertTransaksi(Transaksi transaksi) {
        // Memanggil method insertTransaksi dari ModelKasir
        modelKasir.insertTransaksi(transaksi);
    }

    // Method untuk menyimpan detail transaksi (barang dibeli)
    public void insertDetailTransaksi(Barang barang){
        // Memanggil method insertDetailTransaksi dari objek transaksi
        transaksi.insertDetailTransaksi(barang);
    }

    // Method untuk mengambil daftar transaksi dalam bentuk array 2 dimensi
    public String[][] daftarTransaksi(){
        // Memanggil method readData dari objek transaksi
        return transaksi.readData();
    }

    // Method untuk mengambil detail transaksi berdasarkan ID transaksi
    public String[][] detailTransaksi(int id){
        // Memanggil method readDetail dari objek transaksi
        return transaksi.readDetail(id);
    }

    // Method untuk mengambil informasi umum dari transaksi berdasarkan ID
    public String[] infoTransaksi(int id){
        // Memanggil method infoTransaksi dari objek transaksi
        return transaksi.infoTransaksi(id);
    }

    // Method untuk menghapus transaksi dari database berdasarkan ID
    public void hapusTransaksi(int id){
        // Memanggil method hapusTransaksi dari objek transaksi
        transaksi.hapusTransaksi(id);
    }
}
