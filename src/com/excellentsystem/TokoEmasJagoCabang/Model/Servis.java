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
public class Servis {

    private final StringProperty noServis = new SimpleStringProperty();
    private final StringProperty tglServis = new SimpleStringProperty();
    private final StringProperty kodeMember = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final StringProperty kategoriServis = new SimpleStringProperty();
    private final DoubleProperty jumlahPembayaran = new SimpleDoubleProperty();
    private final StringProperty jenisPembayaran = new SimpleStringProperty();
    private final StringProperty keteranganPembayaran = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty statusAmbil = new SimpleStringProperty();
    private final StringProperty tglAmbil = new SimpleStringProperty();
    private final StringProperty salesAmbil = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public double getJumlahPembayaran() {
        return jumlahPembayaran.get();
    }

    public void setJumlahPembayaran(double value) {
        jumlahPembayaran.set(value);
    }

    public DoubleProperty jumlahPembayaranProperty() {
        return jumlahPembayaran;
    }

    public String getKeteranganPembayaran() {
        return keteranganPembayaran.get();
    }

    public void setKeteranganPembayaran(String value) {
        keteranganPembayaran.set(value);
    }

    public StringProperty keteranganPembayaranProperty() {
        return keteranganPembayaran;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran.get();
    }

    public void setJenisPembayaran(String value) {
        jenisPembayaran.set(value);
    }

    public StringProperty jenisPembayaranProperty() {
        return jenisPembayaran;
    }
    
    public String getStatusBatal() {
        return statusBatal.get();
    }

    public void setStatusBatal(String value) {
        statusBatal.set(value);
    }

    public StringProperty statusBatalProperty() {
        return statusBatal;
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

    public String getSalesAmbil() {
        return salesAmbil.get();
    }

    public void setSalesAmbil(String value) {
        salesAmbil.set(value);
    }

    public StringProperty salesAmbilProperty() {
        return salesAmbil;
    }

    public String getTglAmbil() {
        return tglAmbil.get();
    }

    public void setTglAmbil(String value) {
        tglAmbil.set(value);
    }

    public StringProperty tglAmbilProperty() {
        return tglAmbil;
    }

    public String getKategoriServis() {
        return kategoriServis.get();
    }

    public void setKategoriServis(String value) {
        kategoriServis.set(value);
    }

    public StringProperty kategoriServisProperty() {
        return kategoriServis;
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

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getKodeMember() {
        return kodeMember.get();
    }

    public void setKodeMember(String value) {
        kodeMember.set(value);
    }

    public StringProperty kodeMemberProperty() {
        return kodeMember;
    }

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
    }

    public String getTglServis() {
        return tglServis.get();
    }

    public void setTglServis(String value) {
        tglServis.set(value);
    }

    public StringProperty tglServisProperty() {
        return tglServis;
    }

    public String getNoServis() {
        return noServis.get();
    }

    public void setNoServis(String value) {
        noServis.set(value);
    }

    public StringProperty noServisProperty() {
        return noServis;
    }
    
}
