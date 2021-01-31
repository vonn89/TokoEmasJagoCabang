/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import java.text.ParseException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailKeuanganController  {

    @FXML public TableView<Keuangan> keuanganTable;
    @FXML private TableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TableColumn<Keuangan, String> keteranganColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TableColumn<Keuangan, String> kodeSalesColumn;
    
    @FXML private Label tipeKasirLabel;
    @FXML private Label kategoriLabel;
    @FXML private Label totalLabel;
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().noKeuanganProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglKeuangan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglKeuanganColumn.setComparator(Function.sortDate(tglLengkap));
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        keuanganTable.setItems(allKeuangan);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setKeuangan(Keuangan k){
        tipeKasirLabel.setText(k.getTipeKasir());
        kategoriLabel.setText(k.getKategori());
        allKeuangan.clear();
        allKeuangan.addAll(k.getListKeuangan());
        keuanganTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double total = 0;
        for(Keuangan k : allKeuangan){
            total = total + k.getJumlahRp();
        }
        totalLabel.setText(rp.format(total));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
