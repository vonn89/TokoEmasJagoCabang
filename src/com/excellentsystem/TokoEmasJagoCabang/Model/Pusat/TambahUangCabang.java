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
public class TambahUangCabang {
    //tt_tambah_uang_cabang
    private final StringProperty noTambahUang = new SimpleStringProperty();
    private final StringProperty tglTambah = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty tipeKasir = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusTerima = new SimpleStringProperty();
    private final StringProperty tglTerima = new SimpleStringProperty();
    private final StringProperty userTerima = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public String getStatusBatal() {
        return statusBatal.get();
    }

    public void setStatusBatal(String value) {
        statusBatal.set(value);
    }

    public StringProperty statusBatalProperty() {
        return statusBatal;
    }

    public String getStatusTerima() {
        return statusTerima.get();
    }

    public void setStatusTerima(String value) {
        statusTerima.set(value);
    }

    public StringProperty statusTerimaProperty() {
        return statusTerima;
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

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
    }

    public String getUserTerima() {
        return userTerima.get();
    }

    public void setUserTerima(String value) {
        userTerima.set(value);
    }

    public StringProperty userTerimaProperty() {
        return userTerima;
    }

    public String getTglTerima() {
        return tglTerima.get();
    }

    public void setTglTerima(String value) {
        tglTerima.set(value);
    }

    public StringProperty tglTerimaProperty() {
        return tglTerima;
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

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
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

    public String getTglTambah() {
        return tglTambah.get();
    }

    public void setTglTambah(String value) {
        tglTambah.set(value);
    }

    public StringProperty tglTambahProperty() {
        return tglTambah;
    }

    public String getNoTambahUang() {
        return noTambahUang.get();
    }

    public void setNoTambahUang(String value) {
        noTambahUang.set(value);
    }

    public StringProperty noTambahUangProperty() {
        return noTambahUang;
    }
    
}
