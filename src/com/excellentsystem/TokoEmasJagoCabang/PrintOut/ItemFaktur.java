/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.PrintOut;
import java.math.BigDecimal;
/**
 *
 * @author TMJ-Office
 */
public class ItemFaktur {
    
 

 
    private String Barang;
    private String Berat;
    private String Kadar;
    private String Kode;
    private String Harga;
 
    public ItemFaktur(String Barang, String Berat, String Kadar, String Kode, String Harga) {
        this.Barang = Barang;
        this.Berat = Berat;
        this.Kadar = Kadar;
        this.Kode = Kode;
        this.Harga = Harga;
    }
 
    public String getBarang() {
        return Barang;
    }
 
    public String getBerat() {
        return Berat;
    }
    
    public String getKadar() {
        return Kadar;
    }
    
    public String getKode() {
        return Kode;
    }
 
    public String getHarga() {
        return Harga;
    }
 

}
