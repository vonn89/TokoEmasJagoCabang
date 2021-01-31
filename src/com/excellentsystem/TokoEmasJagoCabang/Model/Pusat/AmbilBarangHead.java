/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class AmbilBarangHead {
    //tt_ambil_barang_head
    private final StringProperty noAmbilBarang = new SimpleStringProperty();
    private final StringProperty tglAmbilBarang = new SimpleStringProperty();
    private final StringProperty tglPembelianMulai = new SimpleStringProperty();
    private final StringProperty tglPembelianAkhir = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBeratKotor = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratBersih = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratPersen = new SimpleDoubleProperty();
    private final DoubleProperty totalPembelian = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty statusTerima = new SimpleStringProperty();
    private final StringProperty tglTerima = new SimpleStringProperty();
    private final StringProperty userTerima = new SimpleStringProperty();
    private List<AmbilBarangDetail> detail;

    public String getStatusTerima() {
        return statusTerima.get();
    }

    public void setStatusTerima(String value) {
        statusTerima.set(value);
    }

    public StringProperty statusTerimaProperty() {
        return statusTerima;
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

    public String getUserTerima() {
        return userTerima.get();
    }

    public void setUserTerima(String value) {
        userTerima.set(value);
    }

    public StringProperty userTerimaProperty() {
        return userTerima;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getTglPembelianAkhir() {
        return tglPembelianAkhir.get();
    }

    public void setTglPembelianAkhir(String value) {
        tglPembelianAkhir.set(value);
    }

    public StringProperty tglPembelianAkhirProperty() {
        return tglPembelianAkhir;
    }

    public String getTglPembelianMulai() {
        return tglPembelianMulai.get();
    }

    public void setTglPembelianMulai(String value) {
        tglPembelianMulai.set(value);
    }

    public StringProperty tglPembelianMulaiProperty() {
        return tglPembelianMulai;
    }
    
    public double getTotalPembelian() {
        return totalPembelian.get();
    }

    public void setTotalPembelian(double value) {
        totalPembelian.set(value);
    }

    public DoubleProperty totalPembelianProperty() {
        return totalPembelian;
    }

    public double getTotalBeratPersen() {
        return totalBeratPersen.get();
    }

    public void setTotalBeratPersen(double value) {
        totalBeratPersen.set(value);
    }

    public DoubleProperty totalBeratPersenProperty() {
        return totalBeratPersen;
    }

    public double getTotalBeratBersih() {
        return totalBeratBersih.get();
    }

    public void setTotalBeratBersih(double value) {
        totalBeratBersih.set(value);
    }

    public DoubleProperty totalBeratBersihProperty() {
        return totalBeratBersih;
    }

    public double getTotalBeratKotor() {
        return totalBeratKotor.get();
    }

    public void setTotalBeratKotor(double value) {
        totalBeratKotor.set(value);
    }

    public DoubleProperty totalBeratKotorProperty() {
        return totalBeratKotor;
    }

    public int getTotalQty() {
        return totalQty.get();
    }

    public void setTotalQty(int value) {
        totalQty.set(value);
    }

    public IntegerProperty totalQtyProperty() {
        return totalQty;
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

    public String getTglAmbilBarang() {
        return tglAmbilBarang.get();
    }

    public void setTglAmbilBarang(String value) {
        tglAmbilBarang.set(value);
    }

    public StringProperty tglAmbilBarangProperty() {
        return tglAmbilBarang;
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

    public List<AmbilBarangDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<AmbilBarangDetail> detail) {
        this.detail = detail;
    }
    
    
}
