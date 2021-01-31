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
public class BatalLunas {

    private final StringProperty noBatalLunas = new SimpleStringProperty();
    private final StringProperty noHutang = new SimpleStringProperty();
    private final StringProperty tglBatalLunas = new SimpleStringProperty();
    private final DoubleProperty totalPinjaman = new SimpleDoubleProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }


    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
    }

    public double getTotalPinjaman() {
        return totalPinjaman.get();
    }

    public void setTotalPinjaman(double value) {
        totalPinjaman.set(value);
    }

    public DoubleProperty totalPinjamanProperty() {
        return totalPinjaman;
    }

    public String getTglBatalLunas() {
        return tglBatalLunas.get();
    }

    public void setTglBatalLunas(String value) {
        tglBatalLunas.set(value);
    }

    public StringProperty tglBatalLunasProperty() {
        return tglBatalLunas;
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

    public String getNoBatalLunas() {
        return noBatalLunas.get();
    }

    public void setNoBatalLunas(String value) {
        noBatalLunas.set(value);
    }

    public StringProperty noBatalLunasProperty() {
        return noBatalLunas;
    }
    
}
