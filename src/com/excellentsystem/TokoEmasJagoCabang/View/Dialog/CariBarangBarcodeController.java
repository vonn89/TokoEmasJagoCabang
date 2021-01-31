/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class CariBarangBarcodeController  {

    @FXML public TableView<Barang> barangTable;
    @FXML private TableColumn<Barang, String> kodeBarangColumn;
    @FXML private TableColumn<Barang, String> kodeBarcodeColumn;
    @FXML private TableColumn<Barang, String> namaBarangColumn;
    @FXML private TableColumn<Barang, String> kategoriColumn;
    @FXML private TableColumn<Barang, String> jenisColumn;
    @FXML private TableColumn<Barang, String> kodeInternColumn;
    @FXML private TableColumn<Barang, String> kadarColumn;
    @FXML private TableColumn<Barang, Number> beratColumn;
    @FXML private TableColumn<Barang, Number> beratAsliColumn;
    @FXML private TableColumn<Barang, String> inputDateColumn;
    @FXML private TableColumn<Barang, String> inputByColumn;
    @FXML private TableColumn<Barang, String> asalBarangColumn;
    
    @FXML private TextField searchField;
    private ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private ObservableList<Barang> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        jenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        inputDateColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getInputDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        inputDateColumn.setComparator(Function.sortDate(tglLengkap));
        inputByColumn.setCellValueFactory(cellData -> cellData.getValue().inputByProperty());
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().asalBarangProperty());
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        menu.getItems().addAll(refresh);
        barangTable.setContextMenu(menu);
        barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<Barang>(){
                @Override
                public void updateItem(Barang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getBarang();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
        barangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        getBarang();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    private void getBarang(){
        Task<List<Barang>> task = new Task<List<Barang>>() {
            @Override 
            public List<Barang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<Barang> listBarang = BarangDAO.getAllByKategoriAndJenisAndStatus(conCabang, "%","%","true");
                    return listBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBarang.clear();
            allBarang.addAll(task.getValue());
            barangTable.refresh();
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
    private void searchBarang() {
        try{
            filterData.clear();
            for (Barang temp : allBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodeBarang())||
                        checkColumn(temp.getKodeBarcode())||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(temp.getKodeKategori())||
                        checkColumn(temp.getKodeJenis())||
                        checkColumn(temp.getKodeIntern())||
                        checkColumn(temp.getKadar())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getInputDate())))||
                        checkColumn(temp.getInputBy())||
                        checkColumn(temp.getAsalBarang())||
                        checkColumn(gr.format(temp.getBeratAsli()))||
                        checkColumn(gr.format(temp.getBerat())))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
