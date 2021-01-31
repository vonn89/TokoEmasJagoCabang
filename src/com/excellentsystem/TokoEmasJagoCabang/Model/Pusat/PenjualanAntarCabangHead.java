/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PenjualanAntarCabangHead {
    //tt_penjualan_antar_cabang_head
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty tglPenjualan = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty cabangTujuan = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalHarga = new SimpleDoubleProperty();
    private final StringProperty statusTerima = new SimpleStringProperty();
    private final StringProperty tglTerima = new SimpleStringProperty();
    private final StringProperty salesTerima = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    private List<PenjualanAntarCabangDetail> listPenjualanAntarCabangDetail;

    public List<PenjualanAntarCabangDetail> getListPenjualanAntarCabangDetail() {
        return listPenjualanAntarCabangDetail;
    }

    public void setListPenjualanAntarCabangDetail(List<PenjualanAntarCabangDetail> listPenjualanAntarCabangDetail) {
        this.listPenjualanAntarCabangDetail = listPenjualanAntarCabangDetail;
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

    public String getSalesTerima() {
        return salesTerima.get();
    }

    public void setSalesTerima(String value) {
        salesTerima.set(value);
    }

    public StringProperty salesTerimaProperty() {
        return salesTerima;
    }

    public String getTglTerima() {
        return tglTerima.get();
    }

    public void setTglTerima(String value) {
        tglTerima.set(value);
    }

    public StringProperty tglTerimaProperty() {
        return tglTerima;
    }

    public String getStatusTerima() {
        return statusTerima.get();
    }

    public void setStatusTerima(String value) {
        statusTerima.set(value);
    }

    public StringProperty statusTerimaProperty() {
        return statusTerima;
    }

    public double getTotalHarga() {
        return totalHarga.get();
    }

    public void setTotalHarga(double value) {
        totalHarga.set(value);
    }

    public DoubleProperty totalHargaProperty() {
        return totalHarga;
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

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
    }

    public String getCabangTujuan() {
        return cabangTujuan.get();
    }

    public void setCabangTujuan(String value) {
        cabangTujuan.set(value);
    }

    public StringProperty cabangTujuanProperty() {
        return cabangTujuan;
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

    public String getTglPenjualan() {
        return tglPenjualan.get();
    }

    public void setTglPenjualan(String value) {
        tglPenjualan.set(value);
    }

    public StringProperty tglPenjualanProperty() {
        return tglPenjualan;
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
