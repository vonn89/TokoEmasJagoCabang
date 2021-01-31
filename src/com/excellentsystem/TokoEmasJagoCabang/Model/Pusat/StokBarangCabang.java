/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class StokBarangCabang {
    //tt_stok_barang_cabang
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    
    private final IntegerProperty stokAwal = new SimpleIntegerProperty();
    private final DoubleProperty beratAwal = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenAwal = new SimpleDoubleProperty();
    private final DoubleProperty nilaiAwal = new SimpleDoubleProperty();
    
    private final IntegerProperty stokMasuk = new SimpleIntegerProperty();
    private final DoubleProperty beratMasuk = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenMasuk = new SimpleDoubleProperty();
    private final DoubleProperty nilaiMasuk = new SimpleDoubleProperty();
    
    private final IntegerProperty stokKeluar = new SimpleIntegerProperty();
    private final DoubleProperty beratKeluar = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenKeluar = new SimpleDoubleProperty();
    private final DoubleProperty nilaiKeluar = new SimpleDoubleProperty();
    
    private final IntegerProperty stokAkhir = new SimpleIntegerProperty();
    private final DoubleProperty beratAkhir = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenAkhir = new SimpleDoubleProperty();
    private final DoubleProperty nilaiAkhir = new SimpleDoubleProperty();

    public double getNilaiAkhir() {
        return nilaiAkhir.get();
    }

    public void setNilaiAkhir(double value) {
        nilaiAkhir.set(value);
    }

    public DoubleProperty nilaiAkhirProperty() {
        return nilaiAkhir;
    }

    public double getBeratPersenAkhir() {
        return beratPersenAkhir.get();
    }

    public void setBeratPersenAkhir(double value) {
        beratPersenAkhir.set(value);
    }

    public DoubleProperty beratPersenAkhirProperty() {
        return beratPersenAkhir;
    }

    public double getNilaiKeluar() {
        return nilaiKeluar.get();
    }

    public void setNilaiKeluar(double value) {
        nilaiKeluar.set(value);
    }

    public DoubleProperty nilaiKeluarProperty() {
        return nilaiKeluar;
    }

    public double getBeratPersenKeluar() {
        return beratPersenKeluar.get();
    }

    public void setBeratPersenKeluar(double value) {
        beratPersenKeluar.set(value);
    }

    public DoubleProperty beratPersenKeluarProperty() {
        return beratPersenKeluar;
    }

    public double getNilaiMasuk() {
        return nilaiMasuk.get();
    }

    public void setNilaiMasuk(double value) {
        nilaiMasuk.set(value);
    }

    public DoubleProperty nilaiMasukProperty() {
        return nilaiMasuk;
    }

    public double getBeratPersenMasuk() {
        return beratPersenMasuk.get();
    }

    public void setBeratPersenMasuk(double value) {
        beratPersenMasuk.set(value);
    }

    public DoubleProperty beratPersenMasukProperty() {
        return beratPersenMasuk;
    }

    public double getNilaiAwal() {
        return nilaiAwal.get();
    }

    public void setNilaiAwal(double value) {
        nilaiAwal.set(value);
    }

    public DoubleProperty nilaiAwalProperty() {
        return nilaiAwal;
    }

    public double getBeratPersenAwal() {
        return beratPersenAwal.get();
    }

    public void setBeratPersenAwal(double value) {
        beratPersenAwal.set(value);
    }

    public DoubleProperty beratPersenAwalProperty() {
        return beratPersenAwal;
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

    public int getStokMasuk() {
        return stokMasuk.get();
    }

    public void setStokMasuk(int value) {
        stokMasuk.set(value);
    }

    public IntegerProperty stokMasukProperty() {
        return stokMasuk;
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

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
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

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
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

    public String getKodeBarang() {
        return kodeBarang.get();
    }

    public void setKodeBarang(String value) {
        kodeBarang.set(value);
    }

    public StringProperty kodeBarangProperty() {
        return kodeBarang;
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
