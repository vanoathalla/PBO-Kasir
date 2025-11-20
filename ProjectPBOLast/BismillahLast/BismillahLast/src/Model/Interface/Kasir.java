package Model.Interface;
import Model.Transaksi;

public interface Kasir {
    public String login();
    public void insertTransaksi(Transaksi transaksi);
}