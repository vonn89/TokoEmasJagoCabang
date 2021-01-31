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
public class StokOpnameDetail {

    private final StringProperty noStokOpname = new SimpleStringProperty();
    private final IntegerProperty stokBarang = new SimpleIntegerProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty kadar = new SimpleStringProperty();
    private final StringProperty kodeIntern = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty beratAsli = new SimpleDoubleProperty();

    public String getKadar() {
        return kadar.get();
    }

    public void setKadar(String value) {
        kadar.set(value);
    }

    public StringProperty kadarProperty() {
        return kadar;
    }


    public double getBeratAsli() {
        return beratAsli.get();
    }

    public void setBeratAsli(double value) {
        beratAsli.set(value);
    }

    public DoubleProperty beratAsliProperty() {
        return beratAsli;
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

    public String getKodeIntern() {
        return kodeIntern.get();
    }

    public void setKodeIntern(String value) {
        kodeIntern.set(value);
    }

    public StringProperty kodeInternProperty() {
        return kodeIntern;
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

    public String getKodeBarang() {
        return kodeBarang.get();
    }

    public void setKodeBarang(String value) {
        kodeBarang.set(value);
    }

    public StringProperty kodeBarangProperty() {
        return kodeBarang;
    }

    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }

    public int getStokBarang() {
        return stokBarang.get();
    }

    public void setStokBarang(int value) {
        stokBarang.set(value);
    }

    public IntegerProperty stokBarangProperty() {
        return stokBarang;
    }

    public String getNoStokOpname() {
        return noStokOpname.get();
    }

    public void setNoStokOpname(String value) {
        noStokOpname.set(value);
    }

    public StringProperty noStokOpnameProperty() {
        return noStokOpname;
    }
    
}
