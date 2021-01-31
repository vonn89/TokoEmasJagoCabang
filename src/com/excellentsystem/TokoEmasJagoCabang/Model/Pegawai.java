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
public class Pegawai {

    private final StringProperty kodePegawai = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty jenisKelamin = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty jabatan = new SimpleStringProperty();
    private final StringProperty tglMasuk = new SimpleStringProperty();
    private final StringProperty tglKeluar = new SimpleStringProperty();
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

    public String getTglKeluar() {
        return tglKeluar.get();
    }

    public void setTglKeluar(String value) {
        tglKeluar.set(value);
    }

    public StringProperty tglKeluarProperty() {
        return tglKeluar;
    }

    public String getTglMasuk() {
        return tglMasuk.get();
    }

    public void setTglMasuk(String value) {
        tglMasuk.set(value);
    }

    public StringProperty tglMasukProperty() {
        return tglMasuk;
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public void setJabatan(String value) {
        jabatan.set(value);
    }

    public StringProperty jabatanProperty() {
        return jabatan;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getJenisKelamin() {
        return jenisKelamin.get();
    }

    public void setJenisKelamin(String value) {
        jenisKelamin.set(value);
    }

    public StringProperty jenisKelaminProperty() {
        return jenisKelamin;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getKodePegawai() {
        return kodePegawai.get();
    }

    public void setKodePegawai(String value) {
        kodePegawai.set(value);
    }

    public StringProperty kodePegawaiProperty() {
        return kodePegawai;
    }
    
}
