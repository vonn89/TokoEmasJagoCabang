/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class Member {

    private final StringProperty kodeMember = new SimpleStringProperty();
    private final StringProperty noRfid = new SimpleStringProperty();
    private final StringProperty noKartu = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty kelurahan = new SimpleStringProperty();
    private final StringProperty kecamatan = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty pekerjaan = new SimpleStringProperty();
    private final StringProperty identitas = new SimpleStringProperty();
    private final StringProperty noIdentitas = new SimpleStringProperty();
    private final IntegerProperty poin = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglDaftar = new SimpleStringProperty();
    private final StringProperty salesDaftar = new SimpleStringProperty();

    public String getNoRfid() {
        return noRfid.get();
    }

    public void setNoRfid(String value) {
        noRfid.set(value);
    }

    public StringProperty noRfidProperty() {
        return noRfid;
    }

    public int getPoin() {
        return poin.get();
    }

    public void setPoin(int value) {
        poin.set(value);
    }

    public IntegerProperty poinProperty() {
        return poin;
    }

    public String getSalesDaftar() {
        return salesDaftar.get();
    }

    public void setSalesDaftar(String value) {
        salesDaftar.set(value);
    }

    public StringProperty salesDaftarProperty() {
        return salesDaftar;
    }

    public String getTglDaftar() {
        return tglDaftar.get();
    }

    public void setTglDaftar(String value) {
        tglDaftar.set(value);
    }

    public StringProperty tglDaftarProperty() {
        return tglDaftar;
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


    public String getNoIdentitas() {
        return noIdentitas.get();
    }

    public void setNoIdentitas(String value) {
        noIdentitas.set(value);
    }

    public StringProperty noIdentitasProperty() {
        return noIdentitas;
    }

    public String getIdentitas() {
        return identitas.get();
    }

    public void setIdentitas(String value) {
        identitas.set(value);
    }

    public StringProperty identitasProperty() {
        return identitas;
    }

    public String getPekerjaan() {
        return pekerjaan.get();
    }

    public void setPekerjaan(String value) {
        pekerjaan.set(value);
    }

    public StringProperty pekerjaanProperty() {
        return pekerjaan;
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

    public String getKecamatan() {
        return kecamatan.get();
    }

    public void setKecamatan(String value) {
        kecamatan.set(value);
    }

    public StringProperty kecamatanProperty() {
        return kecamatan;
    }

    public String getKelurahan() {
        return kelurahan.get();
    }

    public void setKelurahan(String value) {
        kelurahan.set(value);
    }

    public StringProperty kelurahanProperty() {
        return kelurahan;
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


    public String getNoKartu() {
        return noKartu.get();
    }

    public void setNoKartu(String value) {
        noKartu.set(value);
    }

    public StringProperty noKartuProperty() {
        return noKartu;
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
    
}
