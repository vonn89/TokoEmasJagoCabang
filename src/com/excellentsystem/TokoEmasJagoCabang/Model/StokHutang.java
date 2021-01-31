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
public class StokHutang {

    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty tglHutang = new SimpleStringProperty();
    private final IntegerProperty stokAwal = new SimpleIntegerProperty();
    private final DoubleProperty beratAwal = new SimpleDoubleProperty();
    private final DoubleProperty jumlahAwal = new SimpleDoubleProperty();
    private final IntegerProperty stokMasuk = new SimpleIntegerProperty();
    private final DoubleProperty beratMasuk = new SimpleDoubleProperty();
    private final DoubleProperty jumlahMasuk = new SimpleDoubleProperty();
    private final IntegerProperty stokKeluar = new SimpleIntegerProperty();
    private final DoubleProperty beratKeluar = new SimpleDoubleProperty();
    private final DoubleProperty jumlahKeluar = new SimpleDoubleProperty();
    private final IntegerProperty stokAkhir = new SimpleIntegerProperty();
    private final DoubleProperty beratAkhir = new SimpleDoubleProperty();
    private final DoubleProperty jumlahAkhir = new SimpleDoubleProperty();

    public double getJumlahAkhir() {
        return jumlahAkhir.get();
    }

    public void setJumlahAkhir(double value) {
        jumlahAkhir.set(value);
    }

    public DoubleProperty jumlahAkhirProperty() {
        return jumlahAkhir;
    }

    public double getBeratAkhir() {
        return beratAkhir.get();
    }

    public void setBeratAkhir(double value) {
        beratAkhir.set(value);
    }

    public DoubleProperty beratAkhirProperty() {
        return beratAkhir;
    }

    public int getStokAkhir() {
        return stokAkhir.get();
    }

    public void setStokAkhir(int value) {
        stokAkhir.set(value);
    }

    public IntegerProperty stokAkhirProperty() {
        return stokAkhir;
    }

    public double getJumlahKeluar() {
        return jumlahKeluar.get();
    }

    public void setJumlahKeluar(double value) {
        jumlahKeluar.set(value);
    }

    public DoubleProperty jumlahKeluarProperty() {
        return jumlahKeluar;
    }

    public double getBeratKeluar() {
        return beratKeluar.get();
    }

    public void setBeratKeluar(double value) {
        beratKeluar.set(value);
    }

    public DoubleProperty beratKeluarProperty() {
        return beratKeluar;
    }

    public int getStokKeluar() {
        return stokKeluar.get();
    }

    public void setStokKeluar(int value) {
        stokKeluar.set(value);
    }

    public IntegerProperty stokKeluarProperty() {
        return stokKeluar;
    }

    public double getJumlahMasuk() {
        return jumlahMasuk.get();
    }

    public void setJumlahMasuk(double value) {
        jumlahMasuk.set(value);
    }

    public DoubleProperty jumlahMasukProperty() {
        return jumlahMasuk;
    }

    public double getBeratMasuk() {
        return beratMasuk.get();
    }

    public void setBeratMasuk(double value) {
        beratMasuk.set(value);
    }

    public DoubleProperty beratMasukProperty() {
        return beratMasuk;
    }

    public int getStokMasuk() {
        return stokMasuk.get();
    }

    public void setStokMasuk(int value) {
        stokMasuk.set(value);
    }

    public IntegerProperty stokMasukProperty() {
        return stokMasuk;
    }

    public double getJumlahAwal() {
        return jumlahAwal.get();
    }

    public void setJumlahAwal(double value) {
        jumlahAwal.set(value);
    }

    public DoubleProperty jumlahAwalProperty() {
        return jumlahAwal;
    }

    public double getBeratAwal() {
        return beratAwal.get();
    }

    public void setBeratAwal(double value) {
        beratAwal.set(value);
    }

    public DoubleProperty beratAwalProperty() {
        return beratAwal;
    }

    public int getStokAwal() {
        return stokAwal.get();
    }

    public void setStokAwal(int value) {
        stokAwal.set(value);
    }

    public IntegerProperty stokAwalProperty() {
        return stokAwal;
    }

    public String getTglHutang() {
        return tglHutang.get();
    }

    public void setTglHutang(String value) {
        tglHutang.set(value);
    }

    public StringProperty tglHutangProperty() {
        return tglHutang;
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
