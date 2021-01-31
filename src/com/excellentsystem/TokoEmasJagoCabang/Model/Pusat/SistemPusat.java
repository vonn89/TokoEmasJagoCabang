/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model.Pusat;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class SistemPusat {
    //tm_system
    private final StringProperty version = new SimpleStringProperty();
    private final StringProperty tglSystem = new SimpleStringProperty();
    private final StringProperty tglMulaiDatabase = new SimpleStringProperty();
    private final DoubleProperty hargaEmas = new SimpleDoubleProperty();

    public String getTglMulaiDatabase() {
        return tglMulaiDatabase.get();
    }

    public void setTglMulaiDatabase(String value) {
        tglMulaiDatabase.set(value);
    }

    public StringProperty tglMulaiDatabaseProperty() {
        return tglMulaiDatabase;
    }

    

    public double getHargaEmas() {
        return hargaEmas.get();
    }

    public void setHargaEmas(double value) {
        hargaEmas.set(value);
    }

    public DoubleProperty hargaEmasProperty() {
        return hargaEmas;
    }

    public String getTglSystem() {
        return tglSystem.get();
    }

    public void setTglSystem(String value) {
        tglSystem.set(value);
    }

    public StringProperty tglSystemProperty() {
        return tglSystem;
    }
    public String getVersion() {
        return version.get();
    }

    public void setVersion(String value) {
        version.set(value);
    }

    public StringProperty versionProperty() {
        return version;
    }
    
}
