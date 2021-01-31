/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

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
public class StokOpnameHead {

    private final StringProperty noStokOpname = new SimpleStringProperty();
    private final StringProperty tglStokOpname = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private List<StokOpnameDetail> listStokOpnameDetail;

    public List<StokOpnameDetail> getListStokOpnameDetail() {
        return listStokOpnameDetail;
    }

    public void setListStokOpnameDetail(List<StokOpnameDetail> listStokOpnameDetail) {
        this.listStokOpnameDetail = listStokOpnameDetail;
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

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }

    public String getTglStokOpname() {
        return tglStokOpname.get();
    }

    public void setTglStokOpname(String value) {
        tglStokOpname.set(value);
    }

    public StringProperty tglStokOpnameProperty() {
        return tglStokOpname;
    }

    public String getNoStokOpname() {
        return noStokOpname.get();
    }

    public void setNoStokOpname(String value) {
        noStokOpname.set(value);
    }

    public StringProperty noStokOpnameProperty() {
        return noStokOpname;
    }
    
}
