/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriTransaksiDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DataKategoriTransaksiController  {

    @FXML private TableView<KategoriTransaksi> kategoriTable;
    @FXML private TableColumn<KategoriTransaksi, String> kodeTransaksiColumn;
    @FXML private TableColumn<KategoriTransaksi, String> kategoriTransaksiColumn;
    @FXML private TextField kodeTransaksiField;
    @FXML private ComboBox<String> kategoriTransaksiCombo;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private String status = "";
    private ObservableList<KategoriTransaksi> allKategoriTransaksi = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeTransaksiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeTransaksiProperty());
        kategoriTransaksiColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriTransaksiProperty());
        
        kategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectKategoriTransaksi(newValue));
        
        final ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Kategori Transaksi");
        addNew.setOnAction((ActionEvent event) -> {
            newKategoriTransaksi();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKategoriTransaksi();
        });
        menu.getItems().addAll(addNew,refresh);
        kategoriTable.setContextMenu(menu);
        kategoriTable.setRowFactory(table -> {
            TableRow<KategoriTransaksi> row = new TableRow<KategoriTransaksi>(){
                @Override
                public void updateItem(KategoriTransaksi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Kategori Transaksi");
                        addNew.setOnAction((ActionEvent event) -> {
                            newKategoriTransaksi();
                        });
                        MenuItem hapus = new MenuItem("Hapus Kategori Transaksi");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriTransaksi(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriTransaksi();
                        });
                        rowMenu.getItems().addAll(addNew,hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeTransaksiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kategoriTransaksiCombo.requestFocus();
            }
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        kategoriTable.setItems(allKategoriTransaksi);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        allKategori.add("Pendapatan Lain-lain");
        allKategori.add("Beban Gaji");
        allKategori.add("Beban Administrasi");
        allKategori.add("Beban Operasional");
        allKategori.add("Beban Pajak");
        allKategori.add("Beban Penyusutan");
        allKategori.add("Beban Lain-lain");
        kategoriTransaksiCombo.setItems(allKategori);
        getKategoriTransaksi();
    }
    private void getKategoriTransaksi(){
        Task<List<KategoriTransaksi>> task = new Task<List<KategoriTransaksi>>() {
            @Override 
            public List<KategoriTransaksi> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KategoriTransaksiDAO.getAllByStatus(conCabang, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKategoriTransaksi.clear();
            allKategoriTransaksi.addAll(task.getValue());
            kategoriTable.refresh();
            reset();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void reset(){
        status="";
        kodeTransaksiField.setText("");
        kategoriTransaksiCombo.getSelectionModel().clearSelection();
        kodeTransaksiField.setDisable(true);
        kategoriTransaksiCombo.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
    }
    private void selectKategoriTransaksi(KategoriTransaksi k){
        if(k!=null){
            status = "update";
            kodeTransaksiField.setDisable(true);
            kategoriTransaksiCombo.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            kodeTransaksiField.setText(k.getKodeTransaksi());
            kategoriTransaksiCombo.getSelectionModel().select(k.getKategoriTransaksi());
        }
    }
    private void newKategoriTransaksi(){
        status = "new";
        kodeTransaksiField.setDisable(false);
        kategoriTransaksiCombo.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        kodeTransaksiField.setText("");
        kategoriTransaksiCombo.getSelectionModel().clearSelection();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void saveKategoriTransaksi(){
        if(kodeTransaksiField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode transaksi masih kosong");
        else if(kategoriTransaksiCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kategori transaksi belum dipilih");
        else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        KategoriTransaksi k = new KategoriTransaksi();
                        k.setKodeTransaksi(kodeTransaksiField.getText());
                        k.setKategoriTransaksi(kategoriTransaksiCombo.getSelectionModel().getSelectedItem());
                        k.setStatus("true");
                        if(status.equals("update"))
                            return Service.saveUpdateKategoriTransaksi(conCabang, k);
                        else if(status.equals("new"))
                            return Service.saveNewKategoriTransaksi(conCabang, k);
                        else
                            return "false";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategoriTransaksi();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data kategori transaksi berhasil disimpan");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", message);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void deleteKategoriTransaksi(KategoriTransaksi k){
        MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                "Hapus transaksi "+k.getKodeTransaksi()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        k.setStatus("false");
                        return Service.saveUpdateKategoriTransaksi(conCabang, k);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategoriTransaksi();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori transaksi berhasil dihapus");
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
