/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import java.util.List;
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
public class PembelianDetail {

    private final StringProperty noPembelian = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty beratKotor = new SimpleDoubleProperty();
    private final DoubleProperty beratBersih = new SimpleDoubleProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final StringProperty suratPembelian = new SimpleStringProperty();
    private final DoubleProperty hargaKomp = new SimpleDoubleProperty();
    private final DoubleProperty hargaBeli = new SimpleDoubleProperty();
    private final StringProperty statusAmbil = new SimpleStringProperty();
    private final StringProperty noAmbilBarang = new SimpleStringProperty();
    private PembelianHead pembelianHead;
    private List<PembelianDetail> listPembelianDetail;

    public List<PembelianDetail> getListPembelianDetail() {
        return listPembelianDetail;
    }

    public void setListPembelianDetail(List<PembelianDetail> listPembelianDetail) {
        this.listPembelianDetail = listPembelianDetail;
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

    public double getHargaKomp() {
        return hargaKomp.get();
    }

    public void setHargaKomp(double value) {
        hargaKomp.set(value);
    }

    public DoubleProperty hargaKompProperty() {
        return hargaKomp;
    }

    public double getPersentaseEmas() {
        return persentaseEmas.get();
    }

    public void setPersentaseEmas(double value) {
        persentaseEmas.set(value);
    }

    public DoubleProperty persentaseEmasProperty() {
        return persentaseEmas;
    }

    public PembelianHead getPembelianHead() {
        return pembelianHead;
    }

    public void setPembelianHead(PembelianHead pembelianHead) {
        this.pembelianHead = pembelianHead;
    }
    
    public String getNoAmbilBarang() {
        return noAmbilBarang.get();
    }

    public void setNoAmbilBarang(String value) {
        noAmbilBarang.set(value);
    }

    public StringProperty noAmbilBarangProperty() {
        return noAmbilBarang;
    }

    public String getStatusAmbil() {
        return statusAmbil.get();
    }

    public void setStatusAmbil(String value) {
        statusAmbil.set(value);
    }

    public StringProperty statusAmbilProperty() {
        return statusAmbil;
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

    public String getSuratPembelian() {
        return suratPembelian.get();
    }

    public void setSuratPembelian(String value) {
        suratPembelian.set(value);
    }

    public StringProperty suratPembelianProperty() {
        return suratPembelian;
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

    public double getBeratBersih() {
        return beratBersih.get();
    }

    public void setBeratBersih(double value) {
        beratBersih.set(value);
    }

    public DoubleProperty beratBersihProperty() {
        return beratBersih;
    }

    public double getBeratKotor() {
        return beratKotor.get();
    }

    public void setBeratKotor(double value) {
        beratKotor.set(value);
    }

    public DoubleProperty beratKotorProperty() {
        return beratKotor;
    }

    public int getQty() {
        return qty.get();
    }

    public void setQty(int value) {
        qty.set(value);
    }

    public IntegerProperty qtyProperty() {
        return qty;
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

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public String getNoPembelian() {
        return noPembelian.get();
    }

    public void setNoPembelian(String value) {
        noPembelian.set(value);
    }

    public StringProperty noPembelianProperty() {
        return noPembelian;
    }
    
}
