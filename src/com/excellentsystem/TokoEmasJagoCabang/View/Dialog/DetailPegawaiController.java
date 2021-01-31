/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailPegawaiController {

    @FXML private TextField kodePegawaiField;
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private TextField jenisKelaminField;
    @FXML private TextField jabatanField;
    @FXML private TextField tglMasukField;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {}    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setPegawai(Pegawai p){
        try{
            kodePegawaiField.setDisable(true);
            kodePegawaiField.setText(p.getKodePegawai());
            namaField.setText(p.getNama());
            alamatField.setText(p.getAlamat());
            noTelpField.setText(p.getNoTelp());
            jenisKelaminField.setText(p.getJenisKelamin());
            jabatanField.setText(p.getJabatan());
            tglMasukField.setText(tglNormal.format(tglBarang.parse(p.getTglMasuk())));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
