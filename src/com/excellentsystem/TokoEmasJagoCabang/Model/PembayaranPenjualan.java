/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PembayaranPenjualan {

    private final StringProperty noPembayaran = new SimpleStringProperty();
    private final StringProperty tglPembayaran = new SimpleStringProperty();
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty jenisPembayaran = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty jumlahPembayaran = new SimpleDoubleProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private PenjualanHead penjualanHead;
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public PenjualanHead getPenjualanHead() {
        return penjualanHead;
    }

    public void setPenjualanHead(PenjualanHead penjualanHead) {
        this.penjualanHead = penjualanHead;
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
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

    public double getJumlahPembayaran() {
        return jumlahPembayaran.get();
    }

    public void setJumlahPembayaran(double value) {
        jumlahPembayaran.set(value);
    }

    public DoubleProperty jumlahPembayaranProperty() {
        return jumlahPembayaran;
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

    public String getJenisPembayaran() {
        return jenisPembayaran.get();
    }

    public void setJenisPembayaran(String value) {
        jenisPembayaran.set(value);
    }

    public StringProperty jenisPembayaranProperty() {
        return jenisPembayaran;
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

    public String getTglPembayaran() {
        return tglPembayaran.get();
    }

    public void setTglPembayaran(String value) {
        tglPembayaran.set(value);
    }

    public StringProperty tglPembayaranProperty() {
        return tglPembayaran;
    }

    public String getNoPembayaran() {
        return noPembayaran.get();
    }

    public void setNoPembayaran(String value) {
        noPembayaran.set(value);
    }

    public StringProperty noPembayaranProperty() {
        return noPembayaran;
    }
    
}
