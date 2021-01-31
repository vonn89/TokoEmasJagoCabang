/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PemesananHead {

    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final StringProperty tglPemesanan = new SimpleStringProperty();
    private final StringProperty kodeMember = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty totalPemesanan = new SimpleDoubleProperty();
    private final DoubleProperty titipUang = new SimpleDoubleProperty();
    private final DoubleProperty sisaPembayaran = new SimpleDoubleProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty statusAmbil = new SimpleStringProperty();
    private final StringProperty tglAmbil = new SimpleStringProperty();
    private final StringProperty salesAmbil = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<PemesananDetail> listPemesananDetail;

    
    public List<PemesananDetail> getListPemesananDetail() {
        return listPemesananDetail;
    }

    public void setListPemesananDetail(List<PemesananDetail> listPemesananDetail) {
        this.listPemesananDetail = listPemesananDetail;
    }
    
    public double getTotalPemesanan() {
        return totalPemesanan.get();
    }

    public void setTotalPemesanan(double value) {
        totalPemesanan.set(value);
    }

    public DoubleProperty totalPemesananProperty() {
        return totalPemesanan;
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

    public double getSisaPembayaran() {
        return sisaPembayaran.get();
    }

    public void setSisaPembayaran(double value) {
        sisaPembayaran.set(value);
    }

    public DoubleProperty sisaPembayaranProperty() {
        return sisaPembayaran;
    }

    public double getTitipUang() {
        return titipUang.get();
    }

    public void setTitipUang(double value) {
        titipUang.set(value);
    }

    public DoubleProperty titipUangProperty() {
        return titipUang;
    }

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
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

    public String getTglPemesanan() {
        return tglPemesanan.get();
    }

    public void setTglPemesanan(String value) {
        tglPemesanan.set(value);
    }

    public StringProperty tglPemesananProperty() {
        return tglPemesanan;
    }

    public String getNoPemesanan() {
        return noPemesanan.get();
    }

    public void setNoPemesanan(String value) {
        noPemesanan.set(value);
    }

    public StringProperty noPemesananProperty() {
        return noPemesanan;
    }
    
}
