/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataCabangController  {

    @FXML private TableView<Cabang> cabangTable;
    @FXML private TableColumn<Cabang, String> kodeCabangColumn;
    @FXML private TableColumn<Cabang, String> namaCabangColumn;
    @FXML private TableColumn<Cabang, String> ipServerColumn;
    
    private ObservableList<Cabang> allCabang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        namaCabangColumn.setCellValueFactory(cellData -> cellData.getValue().namaCabangProperty());
        ipServerColumn.setCellValueFactory(cellData -> cellData.getValue().ipServerProperty());
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem importCabang = new MenuItem("Import Cabang");
        importCabang.setOnAction((ActionEvent) -> {
            importCabang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            getCabang();
        });
        rowMenu.getItems().addAll(importCabang, refresh);
        cabangTable.setContextMenu(rowMenu);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        cabangTable.setItems(allCabang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        getCabang();
    }
    private void getCabang(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return CabangDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allCabang.clear();
            allCabang.addAll(task.getValue());
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
    private void importCabang(){
        MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                "Import data cabang dari server pusat ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            return Service.importCabang(conPusat, conCabang);
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCabang();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data cabang berhasil diimport");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", message);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
