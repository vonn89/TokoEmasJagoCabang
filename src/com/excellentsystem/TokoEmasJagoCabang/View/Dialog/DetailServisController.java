/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.ServisDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailServisController {

    @FXML private GridPane gridPane;
    
    @FXML private TextField noServisField;
    @FXML private TextField tglTerimaField;
    @FXML private TextField salesTerimaField;
    @FXML private TextField tglAmbilField;
    @FXML private TextField salesAmbilField;
    
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private TextField namaBarangField;
    @FXML private TextField beratField;
    @FXML private TextField kategoriServisField;
    
    @FXML private TextField jumlahPembayaranField;
    @FXML private TextField jenisPembayaranField;
    @FXML private TextField keteranganField;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setDetailServis(String noServis){
        Task<Servis> task = new Task<Servis>() {
            @Override 
            public Servis call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return ServisDAO.get(conCabang, noServis);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            try{
                mainApp.closeLoading();
                Servis s = task.getValue();
                
                noServisField.setText(s.getNoServis());
                tglTerimaField.setText(tglLengkap.format(tglSql.parse(s.getTglServis())));
                salesTerimaField.setText(s.getKodeSales());

                namaField.setText(s.getNama());
                alamatField.setText(s.getAlamat());
                noTelpField.setText(s.getNoTelp());
                namaBarangField.setText(s.getNamaBarang());
                beratField.setText(gr.format(s.getBerat()));
                kategoriServisField.setText(s.getKategoriServis());

                if(s.getStatusAmbil().equals("true")){
                    tglAmbilField.setText(tglLengkap.format(tglSql.parse(s.getTglAmbil())));
                    salesAmbilField.setText(s.getSalesAmbil());

                    jumlahPembayaranField.setText(rp.format(s.getJumlahPembayaran()));
                    jenisPembayaranField.setText(s.getJenisPembayaran());
                    keteranganField.setText(s.getKeteranganPembayaran());

                    gridPane.setVisible(true);
                    stage.setHeight(650);
                }else{
                    gridPane.setVisible(false);
                    stage.setHeight(435);
                }
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
