package Model;

public class Barang {
    // variabel
    String nama;
    int harga;
    int kuantitas;
    int totalHarga;

    // Konstruktor untuk inisialisasi objek Barang
    public Barang(String nama, int harga, int kuantitas) {
        this.nama = nama;
        this.harga = harga;
        this.kuantitas = kuantitas;
        this.totalHarga = harga * kuantitas;
    }

    // Method untuk mendapatkan nama barang
    public String getNama() {
        return nama;
    }

    // Method untuk mendapatkan harga per unit barang
    public int getHarga() {
        return harga;
    }

    // Method untuk mendapatkan jumlah barang
    public int getKuantitas() {
        return kuantitas;
    }

    // Method untuk mendapatkan total harga barang
    public int getTotalHarga() {
        return totalHarga;
    }
}
