/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
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
public class HutangHead {

    private final StringProperty noHutang = new SimpleStringProperty();
    private final StringProperty tglHutang = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty kodeMember = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty maksPinjaman = new SimpleDoubleProperty();
    private final DoubleProperty totalHutang = new SimpleDoubleProperty();
    private final IntegerProperty lamaPinjam = new SimpleIntegerProperty();
    private final DoubleProperty bungaPersen = new SimpleDoubleProperty();
    private final DoubleProperty bungaKomp = new SimpleDoubleProperty();
    private final DoubleProperty bungaRp = new SimpleDoubleProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty statusHilang = new SimpleStringProperty();
    private final StringProperty statusLunas = new SimpleStringProperty();
    private final StringProperty tglLunas = new SimpleStringProperty();
    private final StringProperty salesLunas = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<HutangDetail> listHutangDetail;
    private Member member;
    
    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public List<HutangDetail> getListHutangDetail() {
        return listHutangDetail;
    }

    public void setListHutangDetail(List<HutangDetail> listHutangDetail) {
        this.listHutangDetail = listHutangDetail;
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

    public String getStatusLunas() {
        return statusLunas.get();
    }

    public void setStatusLunas(String value) {
        statusLunas.set(value);
    }

    public StringProperty statusLunasProperty() {
        return statusLunas;
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

    public String getSalesLunas() {
        return salesLunas.get();
    }

    public void setSalesLunas(String value) {
        salesLunas.set(value);
    }

    public StringProperty salesLunasProperty() {
        return salesLunas;
    }

    public String getTglLunas() {
        return tglLunas.get();
    }

    public void setTglLunas(String value) {
        tglLunas.set(value);
    }

    public StringProperty tglLunasProperty() {
        return tglLunas;
    }


    public String getStatusHilang() {
        return statusHilang.get();
    }

    public void setStatusHilang(String value) {
        statusHilang.set(value);
    }

    public StringProperty statusHilangProperty() {
        return statusHilang;
    }

    public double getBungaRp() {
        return bungaRp.get();
    }

    public void setBungaRp(double value) {
        bungaRp.set(value);
    }

    public DoubleProperty bungaRpProperty() {
        return bungaRp;
    }

    public double getBungaKomp() {
        return bungaKomp.get();
    }

    public void setBungaKomp(double value) {
        bungaKomp.set(value);
    }

    public DoubleProperty bungaKompProperty() {
        return bungaKomp;
    }

    public double getBungaPersen() {
        return bungaPersen.get();
    }

    public void setBungaPersen(double value) {
        bungaPersen.set(value);
    }

    public DoubleProperty bungaPersenProperty() {
        return bungaPersen;
    }

    public int getLamaPinjam() {
        return lamaPinjam.get();
    }

    public void setLamaPinjam(int value) {
        lamaPinjam.set(value);
    }

    public IntegerProperty lamaPinjamProperty() {
        return lamaPinjam;
    }

    public double getTotalHutang() {
        return totalHutang.get();
    }

    public void setTotalHutang(double value) {
        totalHutang.set(value);
    }

    public DoubleProperty totalHutangProperty() {
        return totalHutang;
    }

    public double getMaksPinjaman() {
        return maksPinjaman.get();
    }

    public void setMaksPinjaman(double value) {
        maksPinjaman.set(value);
    }

    public DoubleProperty maksPinjamanProperty() {
        return maksPinjaman;
    }

    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
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

    public String getTglHutang() {
        return tglHutang.get();
    }

    public void setTglHutang(String value) {
        tglHutang.set(value);
    }

    public StringProperty tglHutangProperty() {
        return tglHutang;
    }

    public String getNoHutang() {
        return noHutang.get();
    }

    public void setNoHutang(String value) {
        noHutang.set(value);
    }

    public StringProperty noHutangProperty() {
        return noHutang;
    }
    
}
