package Model;

import java.sql.*;
import Connector.Connector;
import Model.Interface.InterfaceTransaksi;

public class Transaksi extends Connector implements InterfaceTransaksi {
    private int id, jumlahBarang, totalHarga, bayar, kembalian;
    private String metode, tanggal, kasir;
    private Statement statement;

    // Konstruktor untuk objek Transaksi
    public Transaksi(int jumlahBarang, int totalHarga, int bayar, int kembalian, String metode, String tanggal, String kasir) {
        this.jumlahBarang = jumlahBarang;
        this.totalHarga = totalHarga;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.metode = metode;
        this.tanggal = tanggal;
        this.kasir = kasir;
    }

    // Method memasukkan detail transaksi ke database
    public void insertDetailTransaksi(Barang barang) {
        id = getLastIdTransaksi(); // Mendapatkan ID transaksi terakhir
        try {
            statement = conn.createStatement();
            String query = "INSERT INTO detail_transaksi (id_transaksi, nama_barang, harga, kuantitas, total_harga) VALUES ('"
                    + id + "', '" + barang.getNama() + "', '" + barang.getHarga() + "', '" + barang.getKuantitas()
                    + "', '" + barang.getTotalHarga() + "')";
            statement.executeUpdate(query); // Menjalankan query
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method mendapatkan ID transaksi terakhir dari database
    public int getLastIdTransaksi() {
        try {
            statement = conn.createStatement(); 
            String query = "SELECT MAX(id) as id FROM transaksi";
            ResultSet rs = statement.executeQuery(query); // Menjalankan query
            if (rs.next()) {
                id = rs.getInt("id"); // Mengambil ID transaksi terakhir
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return id;
    }

    // Method mendapatkan jumlah data transaksi
    @SuppressWarnings("finally")
    private int getJumlahDataTransaksi(){
        int jumlah = 0;
        try {
            statement = conn.createStatement();
            String query = "SELECT COUNT(*) as jumlah FROM transaksi";
            ResultSet rs = statement.executeQuery(query); // Menjalankan query
            if(rs.next()){
                jumlah = rs.getInt("jumlah"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            return jumlah;
        }
    }

    // Method mendapatkan jumlah data detail transaksi berdasarkan ID transaksi
    @SuppressWarnings("finally")
    private int getJumlahDataDetail(int id){
        int jumlah = 0;
        try {
            statement = conn.createStatement();
            String query = "SELECT COUNT(*) as jumlah FROM detail_transaksi WHERE id_transaksi = '" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                jumlah = rs.getInt("jumlah");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            return jumlah;
        }
    }

    // Method membaca data transaksi dari database
    @SuppressWarnings("finally")
    public String[][] readData() {
        int jumlah = getJumlahDataTransaksi(); // Mendapatkan jumlah data transaksi
        String data[][] = new String[jumlah][8]; // Array untuk menyimpan data transaksi
        try {
            statement = conn.createStatement();
            String query = "SELECT * FROM transaksi";
            ResultSet rs = statement.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("id");
                data[i][1] = rs.getString("jumlah_barang");
                data[i][2] = rs.getString("total_harga");
                data[i][3] = rs.getString("bayar");
                data[i][4] = rs.getString("kembalian");
                data[i][5] = rs.getString("metode");
                data[i][6] = rs.getString("tanggal");
                data[i][7] = rs.getString("kasir");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    // Method membaca detail transaksi berdasarkan ID transaksi
    @SuppressWarnings("finally")
    public String[][] readDetail(int id) {
        int jumlah = getJumlahDataDetail(id); // Mendapatkan jumlah data detail transaksi
        String data[][] = new String[jumlah][4]; // Array untuk menyimpan data detail transaksi
        try {
            statement = conn.createStatement();
            String query = "SELECT * FROM detail_transaksi where id_transaksi = '" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("nama_barang");
                data[i][1] = rs.getString("harga");
                data[i][2] = rs.getString("kuantitas");
                data[i][3] = rs.getString("total_harga");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    // Method mendapatkan informasi transaksi berdasarkan ID transaksi
    @SuppressWarnings("finally")
    public String[] infoTransaksi(int id){
        String data[] = new String[6]; // Array untuk menyimpan informasi transaksi
        try {
            statement = conn.createStatement();
            String query = "SELECT tanggal, metode, total_harga, bayar, kembalian, kasir FROM transaksi WHERE id = '" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                data[0] = rs.getString("tanggal");
                data[1] = rs.getString("metode");
                data[2] = rs.getString("total_harga");
                data[3] = rs.getString("bayar");
                data[4] = rs.getString("kembalian");
                data[5] = rs.getString("kasir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            return data;
        }
    }

    // Method menghapus transaksi berdasarkan ID transaksi
    public void hapusTransaksi(int id){
        try {
            statement = conn.createStatement();
            String query = "DELETE FROM transaksi WHERE id = '" + id + "'";
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter jumlah barang
    public int getJumlahBarang() {
        return jumlahBarang;
    }

    // Getter total harga
    public int getTotalHarga() {
        return totalHarga;
    }

    // Getter jumlah bayar
    public int getBayar() {
        return bayar;
    }

    // Getter jumlah kembalian
    public int getKembalian() {
        return kembalian;
    }

    // Getter metode pembayaran
    public String getMetode() {
        return metode;
    }

    // Getter tanggal transaksi
    public String getDate() {
        return tanggal;
    }

    // Getter nama kasir
    public String getKasir() {
        return kasir;
    }
}
