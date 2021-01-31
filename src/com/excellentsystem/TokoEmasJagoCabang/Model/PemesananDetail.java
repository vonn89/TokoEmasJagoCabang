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
public class PemesananDetail {

    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty ongkos = new SimpleDoubleProperty();
    private final DoubleProperty harga = new SimpleDoubleProperty();
    private PemesananHead pemesananHead;

    public PemesananHead getPemesananHead() {
        return pemesananHead;
    }

    public void setPemesananHead(PemesananHead pemesananHead) {
        this.pemesananHead = pemesananHead;
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

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double value) {
        harga.set(value);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }

    public double getOngkos() {
        return ongkos.get();
    }

    public void setOngkos(double value) {
        ongkos.set(value);
    }

    public DoubleProperty ongkosProperty() {
        return ongkos;
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

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }

    public String getNoPemesanan() {
        return noPemesanan.get();
    }

    public void setNoPemesanan(String value) {
        noPemesanan.set(value);
    }

    public StringProperty noPemesananProperty() {
        return noPemesanan;
    }
    
}
