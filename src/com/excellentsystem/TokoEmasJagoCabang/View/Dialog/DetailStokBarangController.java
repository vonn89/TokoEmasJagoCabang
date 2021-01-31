/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokBarangDAO;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import java.sql.Connection;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailStokBarangController  {

    @FXML private TableView<StokBarang> stokBarangTable;
    @FXML private TableColumn<StokBarang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarang, String> namaBarangColumn;
    @FXML private TableColumn<StokBarang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarang, Number> beratAkhirColumn;
    
    @FXML private Label tanggalLabel;
    @FXML private Label kodeJenisLabel;
    @FXML private TextField searchField;
    
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().namaBarangProperty());
        
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> getTableCell(rp));
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> getTableCell(rp));
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> getTableCell(rp));
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> getTableCell(rp));
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            stokBarangTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokBarang> row = new TableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            stokBarangTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(kartu);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setBarang(String tanggal, String kodeJenis){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<StokBarang> listStokBarang = StokBarangDAO.getAllByTanggalAndGudangAndKategoriAndJenis(
                            conCabang, tanggal, cabang.getKodeCabang(),"%", kodeJenis);
                    for(StokBarang s : listStokBarang){
                        s.setBarang(BarangDAO.get(conCabang, s.getKodeBarcode()));
                    }
                    return listStokBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                tanggalLabel.setText(tglNormal.format(tglBarang.parse(tanggal)));
                kodeJenisLabel.setText(kodeJenis);
                allBarang.clear();
                allBarang.addAll(task.getValue());
                stokBarangTable.refresh();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
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
            for (StokBarang b : allBarang) {
                if(searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(b);
                else if(checkColumn(b.getBarang().getKodeKategori())|| 
                        checkColumn(b.getBarang().getKodeBarcode())|| 
                        checkColumn(b.getBarang().getKodeBarang())|| 
                        checkColumn(tglLengkap.format(tglSql.parse(b.getBarang().getInputDate())))|| 
                        checkColumn(b.getBarang().getInputBy())|| 
                        checkColumn(b.getBarang().getKadar())|| 
                        checkColumn(b.getBarang().getKodeIntern())|| 
                        checkColumn(b.getBarang().getNamaBarang())|| 
                        checkColumn(b.getBarang().getAsalBarang())|| 
                        checkColumn(b.getBarang().getKodeJenis()))
                    filterData.add(b);
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void detailBarang(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s.getBarang());
    }
    private void kartuStok(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/KartuStokBarang.fxml");
        KartuStokBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarcode(s.getKodeBarcode());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
