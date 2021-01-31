/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.excellentsystem.TokoEmasJagoCabang.PrintOut;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author TMJ-Office
 */
public class Faktur {
 
    private String nomorFaktur;
    private String tanggal;
    private String nama;
    private String alamat;
    private String notelp;
    private String sales;
    private String jam;
    private String total;
    private String terbilang;
    private List<ItemFaktur> listItemFaktur = new ArrayList<ItemFaktur>();
 
    public Faktur(String nomorFaktur,String tanggal,String nama,String alamat,String notelp,String sales,String jam,String total,String terbilang) {
        this.nomorFaktur = nomorFaktur;
        this.tanggal = tanggal;
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.sales = sales;
        this.jam = jam;
        this.total=total;
        this.terbilang=terbilang;
    }
 
    public String getNomorFaktur() {
        return nomorFaktur;
    }
    public String gettanggal() {
        return tanggal;
    }
    public String getnama() {
        return nama;
    }
    public String getalamat() {
        return alamat;
    }
    public String getnotelp() {
        return notelp;
    }
    public String getsales() {
        return sales;
    }
    public String getjam() {
        return jam;
    }
    public String gettotal() {
        return total;
    }public String getterbilang() {
        return terbilang;
    }
    
 
    public List<ItemFaktur> getListItemFaktur() {
        return listItemFaktur;
    }
 
    public void tambahItemFaktur(ItemFaktur itemFaktur) {
        this.listItemFaktur.add(itemFaktur);
    }

}
