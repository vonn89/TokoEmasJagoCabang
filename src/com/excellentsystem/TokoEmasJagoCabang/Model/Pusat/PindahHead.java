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
public class PindahHead {
    //tt_pindah_head
    private final StringProperty noPindah = new SimpleStringProperty();
    private final StringProperty tglPindah = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusTerima = new SimpleStringProperty();
    private final StringProperty tglTerima = new SimpleStringProperty();
    private final StringProperty userTerima = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<PindahDetail> listPindahDetail;
    
    private final StringProperty kodeGudang = new SimpleStringProperty();

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }

    public List<PindahDetail> getListPindahDetail() {
        return listPindahDetail;
    }

    public void setListPindahDetail(List<PindahDetail> listPindahDetail) {
        this.listPindahDetail = listPindahDetail;
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

    public String getStatusTerima() {
        return statusTerima.get();
    }

    public void setStatusTerima(String value) {
        statusTerima.set(value);
    }

    public StringProperty statusTerimaProperty() {
        return statusTerima;
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


    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
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

    public String getTglPindah() {
        return tglPindah.get();
    }

    public void setTglPindah(String value) {
        tglPindah.set(value);
    }

    public StringProperty tglPindahProperty() {
        return tglPindah;
    }

    public String getNoPindah() {
        return noPindah.get();
    }

    public void setNoPindah(String value) {
        noPindah.set(value);
    }

    public StringProperty noPindahProperty() {
        return noPindah;
    }
}
