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
public class Sistem {

    private final StringProperty version = new SimpleStringProperty();
    private final StringProperty tglSystem = new SimpleStringProperty();
    private final StringProperty tglMulaiDatabase = new SimpleStringProperty();
    private final StringProperty ipServerPusat = new SimpleStringProperty();
    private final DoubleProperty biayaKartuMember = new SimpleDoubleProperty();
    private final DoubleProperty getPoinPembayaranPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPoin = new SimpleDoubleProperty();
    private final DoubleProperty hargaRosok = new SimpleDoubleProperty();

    public double getNilaiPoin() {
        return nilaiPoin.get();
    }

    public void setNilaiPoin(double value) {
        nilaiPoin.set(value);
    }

    public DoubleProperty nilaiPoinProperty() {
        return nilaiPoin;
    }

    public double getGetPoinPembayaranPenjualan() {
        return getPoinPembayaranPenjualan.get();
    }

    public void setGetPoinPembayaranPenjualan(double value) {
        getPoinPembayaranPenjualan.set(value);
    }

    public DoubleProperty getPoinPembayaranPenjualanProperty() {
        return getPoinPembayaranPenjualan;
    }

    public double getBiayaKartuMember() {
        return biayaKartuMember.get();
    }

    public void setBiayaKartuMember(double value) {
        biayaKartuMember.set(value);
    }

    public DoubleProperty biayaKartuMemberProperty() {
        return biayaKartuMember;
    }

    public String getVersion() {
        return version.get();
    }

    public void setVersion(String value) {
        version.set(value);
    }

    public StringProperty versionProperty() {
        return version;
    }

    public String getTglMulaiDatabase() {
        return tglMulaiDatabase.get();
    }

    public void setTglMulaiDatabase(String value) {
        tglMulaiDatabase.set(value);
    }

    public StringProperty tglMulaiDatabaseProperty() {
        return tglMulaiDatabase;
    }


    public String getIpServerPusat() {
        return ipServerPusat.get();
    }

    public void setIpServerPusat(String value) {
        ipServerPusat.set(value);
    }

    public StringProperty ipServerPusatProperty() {
        return ipServerPusat;
    }


    public double getHargaRosok() {
        return hargaRosok.get();
    }

    public void setHargaRosok(double value) {
        hargaRosok.set(value);
    }

    public DoubleProperty hargaRosokProperty() {
        return hargaRosok;
    }


    public String getTglSystem() {
        return tglSystem.get();
    }

    public void setTglSystem(String value) {
        tglSystem.set(value);
    }

    public StringProperty tglSystemProperty() {
        return tglSystem;
    }

    
}
