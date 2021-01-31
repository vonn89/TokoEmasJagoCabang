/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class User {

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private List<Otoritas> otoritas = new ArrayList<>();
    private List<Verifikasi> verifikasi = new ArrayList<>();

    public List<Otoritas> getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(List<Otoritas> otoritas) {
        this.otoritas = otoritas;
    }

    public List<Verifikasi> getVerifikasi() {
        return verifikasi;
    }

    public void setVerifikasi(List<Verifikasi> verifikasi) {
        this.verifikasi = verifikasi;
    }

    
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
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
    
}
