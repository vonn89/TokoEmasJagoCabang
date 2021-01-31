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
public class KategoriTransaksi {

    private final StringProperty kodeTransaksi = new SimpleStringProperty();
    private final StringProperty kategoriTransaksi = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getKategoriTransaksi() {
        return kategoriTransaksi.get();
    }

    public void setKategoriTransaksi(String value) {
        kategoriTransaksi.set(value);
    }

    public StringProperty kategoriTransaksiProperty() {
        return kategoriTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi.get();
    }

    public void setKodeTransaksi(String value) {
        kodeTransaksi.set(value);
    }

    public StringProperty kodeTransaksiProperty() {
        return kodeTransaksi;
    }

    
}
