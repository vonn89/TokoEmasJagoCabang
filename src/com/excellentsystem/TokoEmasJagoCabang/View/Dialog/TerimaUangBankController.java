/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.TambahUangCabang;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class TerimaUangBankController  {

    @FXML public TextField noTambahField;
    @FXML private TextField tglTambahField;
    @FXML private TextField userTambahField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField tipeKasirField;
    @FXML private TextField jumlahRpField;
    @FXML private GridPane gridPane;
    @FXML private TextField tglTerimaField;
    @FXML private TextField userTerimaField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noTambahField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                getTambahUangCabang();
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
    public void newTerimaUangBank(){
        gridPane.setVisible(false);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        stage.setHeight(320);
    }
    public TambahUangCabang tambahUangCabang;
    public void getTambahUangCabang(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    tambahUangCabang = TambahUangCabangDAO.get(conPusat, noTambahField.getText());
                    if(tambahUangCabang==null)
                        return "No pindah yang dimasukan masih kosong atau salah";
                    else if(!tambahUangCabang.getKodeCabang().equals(cabang.getKodeCabang()))
                        return "Tidak dapat diterima, karena no transaksi untuk cabang "+tambahUangCabang.getKodeCabang();
                    else if(tambahUangCabang.getStatusTerima().equals("true"))
                        return "Tidak dapat diterima, karena no pindah sudah pernah diterima";
                    else if(tambahUangCabang.getStatusBatal().equals("true"))
                        return "Tidak dapat diterima, karena no pindah sudah dibatalkan";
                    else if(tambahUangCabang.getStatusTerima().equals("false")&&
                            tambahUangCabang.getStatusBatal().equals("false")){
                        return "true";
                    }else 
                        return "Tidak dapat diterima, karena status pindah salah";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            if(task.getValue().equals("true")){
                try{
                    noTambahField.setText(tambahUangCabang.getNoTambahUang());
                    tglTambahField.setText(tglLengkap.format(tglSql.parse(tambahUangCabang.getTglTambah())));
                    userTambahField.setText(tambahUangCabang.getKodeUser());
                    kodeCabangField.setText(tambahUangCabang.getKodeCabang());
                    tipeKasirField.setText(tambahUangCabang.getTipeKasir());
                    jumlahRpField.setText(rp.format(tambahUangCabang.getJumlahRp()));
                    saveButton.requestFocus();
                }catch(Exception ex){
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            }else
                mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void detailTerimaUangBank(Keuangan k){
        Task<TambahUangCabang> task = new Task<TambahUangCabang>() {
            @Override 
            public TambahUangCabang call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return TambahUangCabangDAO.get(conPusat, k.getKeterangan());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            try{
                mainApp.closeLoading();
                TambahUangCabang tu = task.getValue();
                
                noTambahField.setText(tu.getNoTambahUang());
                tglTambahField.setText(tglLengkap.format(tglSql.parse(tu.getTglTambah())));
                userTambahField.setText(tu.getKodeUser());
                kodeCabangField.setText(tu.getKodeCabang());
                tipeKasirField.setText(tu.getTipeKasir());
                jumlahRpField.setText(rp.format(tu.getJumlahRp()));
                tglTerimaField.setText(tglLengkap.format(tglSql.parse(tu.getTglTerima())));
                userTerimaField.setText(tu.getUserTerima());
                gridPane.setVisible(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                stage.setHeight(360);
                
            }catch(Exception e){
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
