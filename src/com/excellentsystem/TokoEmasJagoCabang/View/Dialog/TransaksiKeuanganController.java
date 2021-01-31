/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import com.excellentsystem.TokoEmasJagoCabang.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class TransaksiKeuanganController  {

    @FXML public ComboBox<String> tipeKasirCombo;
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public TextField deskripsiField;
    @FXML public TextField jumlahRpField;
    @FXML public TextField kodeSalesField;
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(jumlahRpField, rp);
        tipeKasirCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kategoriCombo.requestFocus();
            }
        });
        kategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                deskripsiField.requestFocus();
                deskripsiField.selectAll();
            }
        });
        deskripsiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                jumlahRpField.requestFocus();
                jumlahRpField.selectAll();
            }
        });
        jumlahRpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.requestFocus();
                kodeSalesField.selectAll();
            }
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals(""))
                    saveButton.fire();
            }
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setKategori(List<KategoriTransaksi> listKategoriTransaksi){
        ObservableList<String> allTipeKasir = FXCollections.observableArrayList();
        allTipeKasir.addAll("Kasir","RR");
        tipeKasirCombo.setItems(allTipeKasir);
        tipeKasirCombo.getSelectionModel().select("Kasir");
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        for(KategoriTransaksi k : listKategoriTransaksi){
            allKategori.add(k.getKodeTransaksi());
        }
        kategoriCombo.setItems(allKategori);
    }
    public void setRevisiTransaksi(Keuangan k){
        tipeKasirCombo.getSelectionModel().select(k.getTipeKasir());
        kategoriCombo.getSelectionModel().select(k.getKategori());
        deskripsiField.setText(k.getKeterangan());
        jumlahRpField.setText(rp.format(k.getJumlahRp()));
        kodeSalesField.setText(k.getKodeSales());
        
        tipeKasirCombo.setDisable(true);
        jumlahRpField.setDisable(true);
        kodeSalesField.setDisable(true);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
