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
 * @author excellent
 */
public class PenjualanAntarCabangDetail {
    //tt_penjualan_antar_cabang_detail
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty kodeIntern = new SimpleStringProperty();
    private final StringProperty kadar = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty beratAsli = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final StringProperty inputDate = new SimpleStringProperty();
    private final StringProperty inputBy = new SimpleStringProperty();
    private final StringProperty asalBarang = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty harga = new SimpleDoubleProperty();
    private final StringProperty kodeBarcodeBaru = new SimpleStringProperty();
    private PenjualanAntarCabangHead penjualanAntarCabangHead;

    public PenjualanAntarCabangHead getPenjualanAntarCabangHead() {
        return penjualanAntarCabangHead;
    }

    public void setPenjualanAntarCabangHead(PenjualanAntarCabangHead penjualanAntarCabangHead) {
        this.penjualanAntarCabangHead = penjualanAntarCabangHead;
    }

    public String getKodeBarcodeBaru() {
        return kodeBarcodeBaru.get();
    }

    public void setKodeBarcodeBaru(String value) {
        kodeBarcodeBaru.set(value);
    }

    public StringProperty kodeBarcodeBaruProperty() {
        return kodeBarcodeBaru;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getAsalBarang() {
        return asalBarang.get();
    }

    public void setAsalBarang(String value) {
        asalBarang.set(value);
    }

    public StringProperty asalBarangProperty() {
        return asalBarang;
    }

    public String getInputBy() {
        return inputBy.get();
    }

    public void setInputBy(String value) {
        inputBy.set(value);
    }

    public StringProperty inputByProperty() {
        return inputBy;
    }

    public String getInputDate() {
        return inputDate.get();
    }

    public void setInputDate(String value) {
        inputDate.set(value);
    }

    public StringProperty inputDateProperty() {
        return inputDate;
    }

    public double getNilaiPokok() {
        return nilaiPokok.get();
    }

    public void setNilaiPokok(double value) {
        nilaiPokok.set(value);
    }

    public DoubleProperty nilaiPokokProperty() {
        return nilaiPokok;
    }

    public double getBeratPersen() {
        return beratPersen.get();
    }

    public void setBeratPersen(double value) {
        beratPersen.set(value);
    }

    public DoubleProperty beratPersenProperty() {
        return beratPersen;
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

    public String getKadar() {
        return kadar.get();
    }

    public void setKadar(String value) {
        kadar.set(value);
    }

    public StringProperty kadarProperty() {
        return kadar;
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

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
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

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double value) {
        harga.set(value);
    }

    public DoubleProperty hargaProperty() {
        return harga;
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

    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }

    public String getNoPenjualan() {
        return noPenjualan.get();
    }

    public void setNoPenjualan(String value) {
        noPenjualan.set(value);
    }

    public StringProperty noPenjualanProperty() {
        return noPenjualan;
    }
    
}
