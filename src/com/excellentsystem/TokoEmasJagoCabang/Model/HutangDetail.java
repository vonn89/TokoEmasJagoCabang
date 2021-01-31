/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

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
public class HutangDetail {

    private final StringProperty noHutang = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty nilaiJual = new SimpleDoubleProperty();
    private Kategori kategori;
    private HutangHead hutangHead;

    public HutangHead getHutangHead() {
        return hutangHead;
    }

    public void setHutangHead(HutangHead hutangHead) {
        this.hutangHead = hutangHead;
    }
    
    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    
    public double getNilaiJual() {
        return nilaiJual.get();
    }

    public void setNilaiJual(double value) {
        nilaiJual.set(value);
    }

    public DoubleProperty nilaiJualProperty() {
        return nilaiJual;
    }


    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }


    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }

    public String getNamaBarang() {
        return namaBarang.get();
    }

    public void setNamaBarang(String value) {
        namaBarang.set(value);
    }

    public StringProperty namaBarangProperty() {
        return namaBarang;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }


    public String getNoHutang() {
        return noHutang.get();
    }

    public void setNoHutang(String value) {
        noHutang.set(value);
    }

    public StringProperty noHutangProperty() {
        return noHutang;
    }
    
}
