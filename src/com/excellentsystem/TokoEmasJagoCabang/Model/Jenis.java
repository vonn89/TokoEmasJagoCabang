/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class Jenis {

    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty namaJenis = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty verifikasi = new SimpleStringProperty();
    private Kategori kategori;

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    
    
    public String getVerifikasi() {
        return verifikasi.get();
    }

    public void setVerifikasi(String value) {
        verifikasi.set(value);
    }

    public StringProperty verifikasiProperty() {
        return verifikasi;
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

    public String getNamaJenis() {
        return namaJenis.get();
    }

    public void setNamaJenis(String value) {
        namaJenis.set(value);
    }

    public StringProperty namaJenisProperty() {
        return namaJenis;
    }

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }
    
}
