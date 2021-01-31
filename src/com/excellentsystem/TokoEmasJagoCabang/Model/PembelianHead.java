/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Model;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PembelianHead {

    private final StringProperty noPembelian = new SimpleStringProperty();
    private final StringProperty tglPembelian = new SimpleStringProperty();
    private final StringProperty kodeMember = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final DoubleProperty totalBeratKotor = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratBersih = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratPersen = new SimpleDoubleProperty();
    private final DoubleProperty totalPembelian = new SimpleDoubleProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<PembelianDetail> listPembelianDetail;
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public List<PembelianDetail> getListPembelianDetail() {
        return listPembelianDetail;
    }

    public void setListPembelianDetail(List<PembelianDetail> listPembelianDetail) {
        this.listPembelianDetail = listPembelianDetail;
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

    public double getTotalPembelian() {
        return totalPembelian.get();
    }

    public void setTotalPembelian(double value) {
        totalPembelian.set(value);
    }

    public DoubleProperty totalPembelianProperty() {
        return totalPembelian;
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

    public String getTglPembelian() {
        return tglPembelian.get();
    }

    public void setTglPembelian(String value) {
        tglPembelian.set(value);
    }

    public StringProperty tglPembelianProperty() {
        return tglPembelian;
    }

    public String getNoPembelian() {
        return noPembelian.get();
    }

    public void setNoPembelian(String value) {
        noPembelian.set(value);
    }

    public StringProperty noPembelianProperty() {
        return noPembelian;
    }
    
}
