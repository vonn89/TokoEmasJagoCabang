/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author excellent
 */
public class BungaHutang {

    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final DoubleProperty minJumlahRp = new SimpleDoubleProperty();
    private final DoubleProperty maxJumlahRp = new SimpleDoubleProperty();
    private final DoubleProperty bungaPersen = new SimpleDoubleProperty();

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
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

    public double getMaxJumlahRp() {
        return maxJumlahRp.get();
    }

    public void setMaxJumlahRp(double value) {
        maxJumlahRp.set(value);
    }

    public DoubleProperty maxJumlahRpProperty() {
        return maxJumlahRp;
    }

    public double getMinJumlahRp() {
        return minJumlahRp.get();
    }

    public void setMinJumlahRp(double value) {
        minJumlahRp.set(value);
    }

    public DoubleProperty minJumlahRpProperty() {
        return minJumlahRp;
    }
    
}
