/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class LogHarga {

    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty hargaBeli = new SimpleDoubleProperty();
    private final DoubleProperty hargaJual = new SimpleDoubleProperty();

    public double getPersentaseEmas() {
        return persentaseEmas.get();
    }

    public void setPersentaseEmas(double value) {
        persentaseEmas.set(value);
    }

    public DoubleProperty persentaseEmasProperty() {
        return persentaseEmas;
    }


    public double getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(double value) {
        hargaJual.set(value);
    }

    public DoubleProperty hargaJualProperty() {
        return hargaJual;
    }

    public double getHargaBeli() {
        return hargaBeli.get();
    }

    public void setHargaBeli(double value) {
        hargaBeli.set(value);
    }

    public DoubleProperty hargaBeliProperty() {
        return hargaBeli;
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

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }
    
}
