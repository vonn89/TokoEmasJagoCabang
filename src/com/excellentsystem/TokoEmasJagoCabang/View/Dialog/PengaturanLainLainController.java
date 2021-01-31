/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BungaHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.SistemDAO;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoCabang.Function.setNumberField;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Sistem;
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
 * @author excellent
 */
public class PengaturanLainLainController {

    @FXML private TextField biayaKartuField;
    
    @FXML private TableView<BungaHutang> bungaHutangTable;
    @FXML private TableColumn<BungaHutang, Number> minPinjamanColumn;
    @FXML private TableColumn<BungaHutang, Number> maxPinjamanColumn;
    @FXML private TableColumn<BungaHutang, Number> bungaPersenColumn;
    
    @FXML private TextField minPinjamanField;
    @FXML private TextField maxPinjamanField;
    @FXML private TextField bungaPersenField;
    private ObservableList<BungaHutang> allBungaHutang = FXCollections.observableArrayList();
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        minPinjamanColumn.setCellValueFactory(cellData -> cellData.getValue().minJumlahRpProperty());
        minPinjamanColumn.setCellFactory(col -> getTableCell(rp));
        maxPinjamanColumn.setCellValueFactory(cellData -> cellData.getValue().maxJumlahRpProperty());
        maxPinjamanColumn.setCellFactory(col -> getTableCell(rp));
        bungaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBungaHutang();
        });
        rm.getItems().addAll(refresh);
        bungaHutangTable.setContextMenu(rm);
        bungaHutangTable.setRowFactory(table -> {
            TableRow<BungaHutang> row = new TableRow<BungaHutang>(){
                @Override
                public void updateItem(BungaHutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Bunga Hutang");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteBungaHutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getBungaHutang();
                        });
                        rm.getItems().addAll(hapus,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        minPinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                maxPinjamanField.requestFocus();
                maxPinjamanField.selectAll();
            }
        });
        maxPinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                bungaPersenField.requestFocus();
                bungaPersenField.selectAll();
            }
        });
        bungaPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                addBungaHutang();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        setNumberField(biayaKartuField, rp);
        setNumberField(minPinjamanField, rp);
        setNumberField(maxPinjamanField, rp);
        setNumberField(bungaPersenField, gr);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        bungaHutangTable.setItems(allBungaHutang);
        getBiaya();
        getBungaHutang();
    }
    private void getBiaya(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    Sistem temp = SistemDAO.get(conCabang);
                    sistem.setBiayaKartuMember(temp.getBiayaKartuMember());
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            biayaKartuField.setText(rp.format(sistem.getBiayaKartuMember()));
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBungaHutang(){
        Task<List<BungaHutang>> task = new Task<List<BungaHutang>>() {
            @Override 
            public List<BungaHutang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return BungaHutangDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBungaHutang.clear();
            allBungaHutang.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void addBungaHutang(){
        if(minPinjamanField.getText().equals(""))
            minPinjamanField.setText("0");
        if(maxPinjamanField.getText().equals(""))
            maxPinjamanField.setText("0");
        if(bungaPersenField.getText().equals(""))
            bungaPersenField.setText("0");
        BungaHutang b = new BungaHutang();
        b.setMinJumlahRp(Double.parseDouble(minPinjamanField.getText().replaceAll(",", "")));
        b.setMaxJumlahRp(Double.parseDouble(maxPinjamanField.getText().replaceAll(",", "")));
        b.setBungaPersen(Double.parseDouble(bungaPersenField.getText().replaceAll(",", "")));
        allBungaHutang.add(b);
        bungaHutangTable.refresh();
    }
    private void deleteBungaHutang(BungaHutang b){
        allBungaHutang.remove(b);
        bungaHutangTable.refresh();
    }
    @FXML
    private void save(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    sistem.setBiayaKartuMember(Double.parseDouble(biayaKartuField.getText().replaceAll(",", "")));
                    return Service.savePengaturanLainLain(conCabang, sistem, allBungaHutang);
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
                mainApp.showMessage(Modality.NONE, "Success", "Pengaturan lain-lain berhasil disimpan");
            }else
                mainApp.showMessage(Modality.NONE, "Failed", status);
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
