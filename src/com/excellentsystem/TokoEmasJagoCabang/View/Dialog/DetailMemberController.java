/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailMemberController  {
    
    @FXML public TextField noRfidField;
    @FXML public TextField noKartuField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField kelurahanField;
    @FXML public TextField kecamatanField;
    @FXML public TextField noTelpField;
    @FXML public TextField pekerjaanField;
    @FXML public ComboBox<String> identitasCombo;
    @FXML public TextField noIdentitasField;
    @FXML public TextField tglDaftarField;
    @FXML public TextField salesDaftarField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    @FXML private GridPane gridPane;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noRfidField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                noKartuField.requestFocus();
                noKartuField.selectAll();
            }
        });
        noKartuField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                namaField.requestFocus();
                namaField.selectAll();
            }
        });
        namaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){  
                alamatField.requestFocus();
                alamatField.selectAll();
            }
        });
        alamatField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                kelurahanField.requestFocus();
                kelurahanField.selectAll();
            }
        });
        kelurahanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                kecamatanField.requestFocus();
                kecamatanField.selectAll();
            }
        });
        kecamatanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                noTelpField.requestFocus();
                noTelpField.selectAll();
            }
        });
        noTelpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                pekerjaanField.requestFocus();
                pekerjaanField.selectAll();
            }
        });
        pekerjaanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                identitasCombo.requestFocus();
            }
        });
        identitasCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                noIdentitasField.requestFocus();
                noIdentitasField.selectAll();
            }
        });
        noIdentitasField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                salesDaftarField.requestFocus();
                salesDaftarField.selectAll();
            }
        });
        salesDaftarField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                salesDaftarField.setText(Function.ceksales(salesDaftarField.getText()));
                if(!salesDaftarField.getText().equals(""))
                    saveButton.fire();
            }
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        ObservableList<String> identitas = FXCollections.observableArrayList();
        identitas.clear();
        identitas.add("KTP");
        identitas.add("SIM");
        identitas.add("Lainnya..");
        identitasCombo.setItems(identitas);
        identitasCombo.getSelectionModel().select("KTP");
    }
    public void setMember(Member m){
        try{
            noRfidField.setText(m.getNoRfid());
            noRfidField.setDisable(true);
            noKartuField.setText(m.getNoKartu());
            noKartuField.setDisable(true);
            namaField.setText(m.getNama());
            alamatField.setText(m.getAlamat());
            kelurahanField.setText(m.getKelurahan());
            kecamatanField.setText(m.getKecamatan());
            noTelpField.setText(m.getNoTelp());
            pekerjaanField.setText(m.getPekerjaan());
            identitasCombo.getSelectionModel().select(m.getIdentitas());
            noIdentitasField.setText(m.getNoIdentitas());
            tglDaftarField.setText(tglLengkap.format(tglSql.parse(m.getTglDaftar())));
            salesDaftarField.setText(m.getSalesDaftar());
            salesDaftarField.setDisable(true);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void lihatMember(String kodeMember){
        Task<Member> task = new Task<Member>() {
            @Override 
            public Member call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return MemberDAO.get(conPusat, kodeMember);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                Member m = task.getValue();
                
                noRfidField.setDisable(true);
                noKartuField.setDisable(true);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                kelurahanField.setDisable(true);
                kecamatanField.setDisable(true);
                noTelpField.setDisable(true);
                pekerjaanField.setDisable(true);
                identitasCombo.setDisable(true);
                noIdentitasField.setDisable(true);
                salesDaftarField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                gridPane.getRowConstraints().get(15).setMinHeight(0);
                gridPane.getRowConstraints().get(15).setPrefHeight(0);
                gridPane.getRowConstraints().get(15).setMaxHeight(0);

                noRfidField.setText(m.getNoRfid());
                noKartuField.setText(m.getNoKartu());
                namaField.setText(m.getNama());
                alamatField.setText(m.getAlamat());
                kelurahanField.setText(m.getKelurahan());
                kecamatanField.setText(m.getKecamatan());
                noTelpField.setText(m.getNoTelp());
                pekerjaanField.setText(m.getPekerjaan());
                identitasCombo.getSelectionModel().select(m.getIdentitas());
                noIdentitasField.setText(m.getNoIdentitas());
                tglDaftarField.setText(tglLengkap.format(tglSql.parse(m.getTglDaftar())));
                salesDaftarField.setText(m.getSalesDaftar());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
