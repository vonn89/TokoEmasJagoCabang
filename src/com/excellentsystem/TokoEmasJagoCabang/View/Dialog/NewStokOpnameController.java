/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class NewStokOpnameController  {

    @FXML private TableView<Jenis> jenisTable;
    @FXML private TableColumn<Jenis, String> kodeJenisColumn;
    @FXML private TableColumn<Jenis, Boolean> checkedColumn;
    
    @FXML private CheckBox checkAll;
    @FXML public Button saveButton;
    public ObservableList<Jenis> allJenis = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        checkedColumn.setCellValueFactory(cellData -> {
            return new SimpleBooleanProperty(Boolean.parseBoolean(cellData.getValue().getVerifikasi()));
        });
        checkedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer i) -> new SimpleBooleanProperty(Boolean.parseBoolean(jenisTable.getItems().get(i).getVerifikasi()))));
        
        jenisTable.setRowFactory(table -> {
            TableRow<Jenis> row = new TableRow<Jenis>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().getVerifikasi().equals("true"))
                            row.getItem().setVerifikasi("false");
                        else
                            row.getItem().setVerifikasi("true");
                        jenisTable.refresh();
                    }
                }
            });
            return row;
        });
    }   
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        jenisTable.setItems(allJenis);
        getJenis();
    }  
    private void getJenis(){
        Task<List<Jenis>> task = new Task<List<Jenis>>() {
            @Override 
            public List<Jenis> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<Jenis> listJenis = JenisDAO.getAll(conCabang);
                    for(Jenis j : listJenis){
                        j.setVerifikasi("false");
                    }
                    return listJenis;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allJenis.clear();
            allJenis.addAll(task.getValue());
            jenisTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAll(){
        for(Jenis j : allJenis){
            j.setVerifikasi(String.valueOf(checkAll.isSelected()));
        }
        jenisTable.refresh();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
