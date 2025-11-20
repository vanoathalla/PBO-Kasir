 package Model.Interface;
import Model.Barang;

public interface InterfaceTransaksi {
    //abstraction
    public void insertDetailTransaksi(Barang barang);
    public String[][] readData();
    public int getLastIdTransaksi();
    public String[][] readDetail(int id);
    public String[] infoTransaksi(int id);
    public void hapusTransaksi(int id);
}