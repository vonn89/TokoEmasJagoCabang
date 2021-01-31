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
public class HancurDetail {
    //tt_hancur_detail
    private final StringProperty noHancur = new SimpleStringProperty();
    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
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
    private final DoubleProperty persentaseRosok = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenRosok = new SimpleDoubleProperty();

    private HancurHead hancurHead;

    public HancurHead getHancurHead() {
        return hancurHead;
    }

    public void setHancurHead(HancurHead hancurHead) {
        this.hancurHead = hancurHead;
    }
    
    
    public double getBeratPersenRosok() {
        return beratPersenRosok.get();
    }

    public void setBeratPersenRosok(double value) {
        beratPersenRosok.set(value);
    }

    public DoubleProperty beratPersenRosokProperty() {
        return beratPersenRosok;
    }

    public double getPersentaseRosok() {
        return persentaseRosok.get();
    }

    public void setPersentaseRosok(double value) {
        persentaseRosok.set(value);
    }

    public DoubleProperty persentaseRosokProperty() {
        return persentaseRosok;
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

    public String getKodeIntern() {
        return kodeIntern.get();
    }

    public void setKodeIntern(String value) {
        kodeIntern.set(value);
    }

    public StringProperty kodeInternProperty() {
        return kodeIntern;
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

    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
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


    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }


    public String getNoHancur() {
        return noHancur.get();
    }

    public void setNoHancur(String value) {
        noHancur.set(value);
    }

    public StringProperty noHancurProperty() {
        return noHancur;
    }
    
}
