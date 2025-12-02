package model;

public class Barang {
    private int id;
    private String kode_barang;
    private String nama_barang;
    private int harga;
    private int stok;

    public Barang() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKode_barang() { return kode_barang; }
    public void setKode_barang(String kode_barang) { this.kode_barang = kode_barang; }

    public String getNama_barang() { return nama_barang; }
    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}
