/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SetoranCabang;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class SetorUangKasController  {

    @FXML private TextField noSetorField;
    @FXML private TextField tglSetorField;
    @FXML public ComboBox<String> tipeKasirCombo;
    @FXML public TextField jumlahRpField;
    @FXML private Label kodeUserLabel;
    @FXML private TextField kodeUserField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(jumlahRpField, rp);
        tipeKasirCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                jumlahRpField.requestFocus();
                jumlahRpField.selectAll();
            }
        });
        jumlahRpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        ObservableList<String> allTipeKasir = FXCollections.observableArrayList();
        allTipeKasir.addAll("Kasir","RR");
        tipeKasirCombo.setItems(allTipeKasir);
        tipeKasirCombo.getSelectionModel().select("Kasir");
    }
    public void newSetor(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return SetoranCabangDAO.getId(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            noSetorField.setText(task.getValue());
            tglSetorField.setText(Function.getSystemDate());
            
            kodeUserField.setVisible(false);
            kodeUserLabel.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void detailSetoran(Keuangan k){
        Task<SetoranCabang> task = new Task<SetoranCabang>() {
            @Override 
            public SetoranCabang call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return SetoranCabangDAO.get(conPusat, k.getKeterangan());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            SetoranCabang s = task.getValue();
            
            noSetorField.setText(s.getNoSetor());
            tglSetorField.setText(s.getTglSetor());
            tipeKasirCombo.getSelectionModel().select(s.getTipeKasir());
            jumlahRpField.setText(rp.format(s.getJumlahRp()));
            kodeUserField.setText(s.getKodeUser());

            tipeKasirCombo.setDisable(true);
            jumlahRpField.setDisable(true);
            kodeUserLabel.setVisible(true);
            kodeUserField.setVisible(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
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
