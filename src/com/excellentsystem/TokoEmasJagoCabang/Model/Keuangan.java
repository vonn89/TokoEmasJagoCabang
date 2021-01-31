/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class Keuangan {

    private final StringProperty noKeuangan = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty tglKeuangan = new SimpleStringProperty();
    private final StringProperty tipeKasir = new SimpleStringProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private List<Keuangan> listKeuangan = new ArrayList<>();
    private PenjualanHead penjualanHead;

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }
    
    
    public List<Keuangan> getListKeuangan() {
        return listKeuangan;
    }

    public void setListKeuangan(List<Keuangan> listKeuangan) {
        this.listKeuangan = listKeuangan;
    }

    public PenjualanHead getPenjualanHead() {
        return penjualanHead;
    }

    public void setPenjualanHead(PenjualanHead penjualanHead) {
        this.penjualanHead = penjualanHead;
    }
    
    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }


    public String getTipeKasir() {
        return tipeKasir.get();
    }

    public void setTipeKasir(String value) {
        tipeKasir.set(value);
    }

    public StringProperty tipeKasirProperty() {
        return tipeKasir;
    }

    public String getTglKeuangan() {
        return tglKeuangan.get();
    }

    public void setTglKeuangan(String value) {
        tglKeuangan.set(value);
    }

    public StringProperty tglKeuanganProperty() {
        return tglKeuangan;
    }

    public String getNoKeuangan() {
        return noKeuangan.get();
    }

    public void setNoKeuangan(String value) {
        noKeuangan.set(value);
    }

    public StringProperty noKeuanganProperty() {
        return noKeuangan;
    }
    
}
