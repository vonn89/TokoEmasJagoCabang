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
public class HancurHead {
    //tt_hancur_head
    private final StringProperty noHancur = new SimpleStringProperty();
    private final StringProperty tglHancur = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratAsli = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratPersen = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    private List<HancurDetail> listHancurDetail;

    public List<HancurDetail> getListHancurDetail() {
        return listHancurDetail;
    }

    public void setListHancurDetail(List<HancurDetail> listHancurDetail) {
        this.listHancurDetail = listHancurDetail;
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

    public double getTotalBeratAsli() {
        return totalBeratAsli.get();
    }

    public void setTotalBeratAsli(double value) {
        totalBeratAsli.set(value);
    }

    public DoubleProperty totalBeratAsliProperty() {
        return totalBeratAsli;
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

    public double getTotalBeratPersen() {
        return totalBeratPersen.get();
    }

    public void setTotalBeratPersen(double value) {
        totalBeratPersen.set(value);
    }

    public DoubleProperty totalBeratPersenProperty() {
        return totalBeratPersen;
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

    public String getTglHancur() {
        return tglHancur.get();
    }

    public void setTglHancur(String value) {
        tglHancur.set(value);
    }

    public StringProperty tglHancurProperty() {
        return tglHancur;
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
