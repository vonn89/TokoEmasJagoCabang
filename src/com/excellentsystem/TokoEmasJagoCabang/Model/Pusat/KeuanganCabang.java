/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class KeuanganCabang {
    //tt_keuangan_cabang
    private final StringProperty noKeuangan = new SimpleStringProperty();
    private final StringProperty tglKeuangan = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty tipeKasir = new SimpleStringProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty deskripsi = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();

    public String getTglKeuangan() {
        return tglKeuangan.get();
    }

    public void setTglKeuangan(String value) {
        tglKeuangan.set(value);
    }

    public StringProperty tglKeuanganProperty() {
        return tglKeuangan;
    }

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
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


    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getDeskripsi() {
        return deskripsi.get();
    }

    public void setDeskripsi(String value) {
        deskripsi.set(value);
    }

    public StringProperty deskripsiProperty() {
        return deskripsi;
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

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
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
