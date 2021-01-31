/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailKategoriBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.HargaRosokController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
 * @author Yunaz
 */
public class DataKategoriBarangController  {
    
    @FXML private TableView<Kategori> kategoriTable;
    @FXML private TableColumn<Kategori, String> kodeKategoriColumn;
    @FXML private TableColumn<Kategori, String> namaKategoriColumn;
    @FXML private TableColumn<Kategori, Number> hargaBeliColumn;
    @FXML private TableColumn<Kategori, Number> hargaJualColumn;
    
    @FXML private TableView<Jenis> jenisTable;
    @FXML private TableColumn<Jenis, String> kodeJenisColumn;
    @FXML private TableColumn<Jenis, String> namaJenisColumn;
    @FXML private TableColumn<Jenis, Boolean> verifikasiColumn;
    
    @FXML private CheckBox checkVerifikasi;
    private ObservableList<Kategori> allKategori = FXCollections.observableArrayList();
    private ObservableList<Jenis> allJenis = FXCollections.observableArrayList();
    private List<Jenis> listJenis = new ArrayList<>();
    private Main mainApp;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().namaKategoriProperty());
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaJenisColumn.setCellValueFactory(cellData -> cellData.getValue().namaJenisProperty());
        verifikasiColumn.setCellValueFactory(cellData -> {
            return new SimpleBooleanProperty(Boolean.parseBoolean(cellData.getValue().getVerifikasi()));
        });
        verifikasiColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer i) -> new SimpleBooleanProperty(Boolean.parseBoolean(jenisTable.getItems().get(i).getVerifikasi()))));
        
        kategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectKategori(newValue));
        
        ContextMenu kategoriMenu = new ContextMenu();
        MenuItem rosok = new MenuItem("Ubah Harga Rosok");
        rosok.setOnAction((ActionEvent) -> {
            ubahHargaRosok();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            getKategori();
        });
        kategoriMenu.getItems().addAll(rosok, refresh);
        kategoriTable.setContextMenu(kategoriMenu);
        kategoriTable.setRowFactory(t -> {
            TableRow<Kategori> row = new TableRow<Kategori>(){
                @Override
                public void updateItem(Kategori item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(kategoriMenu);
                    }else{
                        MenuItem editKategori = new MenuItem("Ubah Kategori");
                        editKategori.setOnAction((ActionEvent) -> {
                            editKategori(item);
                        });
                        MenuItem rosok = new MenuItem("Ubah Harga Rosok");
                        rosok.setOnAction((ActionEvent) -> {
                            ubahHargaRosok();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            getKategori();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(editKategori, rosok, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        editKategori(row.getItem());
                }
            });
            return row;
        });
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
                        saveJenis(row.getItem());
                    }
                }
            });
            return row;
        });
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        kategoriTable.setItems(allKategori);
        jenisTable.setItems(allJenis);
        getKategori();
    }
    private void getKategori(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    allKategori.clear();
                    allKategori.addAll(KategoriDAO.getAll(conCabang));
                    listJenis.clear();
                    listJenis.addAll(JenisDAO.getAll(conCabang));
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            kategoriTable.refresh();
            jenisTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void selectKategori(Kategori k){
        allJenis.clear();
        if(k!=null){
            for(Jenis j : listJenis){
                if(j.getKodeKategori().equals(k.getKodeKategori()))
                    allJenis.add(j);
            }
        }
        jenisTable.refresh();
    }
    @FXML
    private void checkVerifikasi(){
        for(Jenis j : allJenis){
            j.setVerifikasi(String.valueOf(checkVerifikasi.isSelected()));
            saveJenis(j);
        }
        jenisTable.refresh();
    }
    private void ubahHargaRosok(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/HargaRosok.fxml");
        HargaRosokController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.getHargaRosok();
        controller.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        sistem.setHargaRosok(Double.parseDouble(controller.hargaRosokField.getText().replaceAll(",", "")));
                        return Service.saveUpdateSistem(conCabang, sistem);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Harga Rosok berhasil disimpan");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void editKategori(Kategori k){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailKategoriBarang.fxml");
        DetailKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategoriBarang(k);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
            else if(controller.namaKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama kategori masih kosong");
            else if(controller.hargaBeliField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
            else if(controller.hargaJualField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            k.setNamaKategori(controller.namaKategoriField.getText());
                            k.setHargaBeli(Double.parseDouble(controller.hargaBeliField.getText().replaceAll(",", "")));
                            k.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                            return Service.saveUpdateKategoriBarang(conCabang, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Barang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void saveJenis(Jenis j){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return Service.saveUpdateJenisBarang(conCabang, j);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            String status = task.getValue();
            if(status.equals("true")){
                jenisTable.refresh();
                mainApp.showMessage(Modality.NONE, "Success", "Jenis barang berhasil disimpan");
            }else{
                mainApp.showMessage(Modality.NONE, "Failed", status);
                getKategori();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
}
