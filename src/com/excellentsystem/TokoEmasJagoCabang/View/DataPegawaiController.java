/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PegawaiDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailPegawaiController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataPegawaiController  {

    @FXML private TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> kodePegawaiColumn;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    @FXML private TableColumn<Pegawai, String> jenisKelaminColumn;
    @FXML private TableColumn<Pegawai, String> alamatColumn;
    @FXML private TableColumn<Pegawai, String> noTelpColumn;
    @FXML private TableColumn<Pegawai, String> jabatanColumn;
    @FXML private TableColumn<Pegawai, String> tanggalMasukColumn;
    @FXML private TableColumn<Pegawai, String> tanggalKeluarColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPegawaiField;
    private ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private ObservableList<Pegawai> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    public void initialize() {
        kodePegawaiColumn.setCellValueFactory(cellData -> cellData.getValue().kodePegawaiProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jenisKelaminColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKelaminProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        jabatanColumn.setCellValueFactory(cellData -> cellData.getValue().jabatanProperty());
        tanggalMasukColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglMasuk())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalMasukColumn.setComparator(Function.sortDate(tglNormal));
        tanggalKeluarColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglKeluar())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalKeluarColumn.setComparator(Function.sortDate(tglNormal));
        final ContextMenu menu = new ContextMenu();
        MenuItem importSales = new MenuItem("Import Sales");
        importSales.setOnAction((ActionEvent event) -> {
            importSales();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPegawai();
        });
        menu.getItems().addAll(importSales, refresh);
        pegawaiTable.setContextMenu(menu);
        pegawaiTable.setRowFactory(table -> {
            TableRow<Pegawai> row = new TableRow<Pegawai>(){
                @Override
                public void updateItem(Pegawai item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem importSales = new MenuItem("Import Sales");
                        importSales.setOnAction((ActionEvent event) -> {
                            importSales();
                        });
                        MenuItem detail = new MenuItem("Detail Sales");
                        detail.setOnAction((ActionEvent event) -> {
                            detailSales(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getPegawai();
                        });
                        rowMenu.getItems().addAll(importSales, detail, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailSales(row.getItem());
                }
            });
            return row;
        });
        allPegawai.addListener((ListChangeListener.Change<? extends Pegawai> change) -> {
            searchPegawai();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPegawai();
        });
        filterData.addAll(allPegawai);
        pegawaiTable.setItems(filterData);
    }    
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPegawai();
    }
    private void getPegawai(){
        Task<List<Pegawai>> task = new Task<List<Pegawai>>() {
            @Override 
            public List<Pegawai> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return PegawaiDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPegawai.clear();
            allPegawai.addAll(task.getValue());
            pegawaiTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPegawai() {
        try{
            filterData.clear();
            for (Pegawai temp : allPegawai) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodePegawai())||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getTglMasuk())))||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getTglKeluar())))||
                        checkColumn(temp.getNama())||
                        checkColumn(temp.getAlamat())||
                        checkColumn(temp.getNoTelp())||
                        checkColumn(temp.getJenisKelamin())||
                        checkColumn(temp.getJabatan()))
                        filterData.add(temp);
                }
            }
            totalPegawaiField.setText("Total Sales : "+rp.format(filterData.size()));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void detailSales(Pegawai p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawai(p);
    }
    private void importSales(){
        MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                "Import data sales dari server pusat ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            return Service.importSales(conPusat, conCabang);
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data sales berhasil diimport");
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
