/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.ServisDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import java.sql.Connection;
import java.text.ParseException;
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
public class DetailKeuanganServisController  {

    @FXML private TableView<Servis> servisTable;
    @FXML private TableColumn<Servis, String> noServisColumn;
    @FXML private TableColumn<Servis, String> tglServisColumn;
    @FXML private TableColumn<Servis, String> salesTerimaColumn;
    @FXML private TableColumn<Servis, String> namaColumn;
    @FXML private TableColumn<Servis, String> alamatColumn;
    @FXML private TableColumn<Servis, String> namaBarangColumn;
    @FXML private TableColumn<Servis, Number> beratColumn;
    @FXML private TableColumn<Servis, String> kategoriServisColumn;
    @FXML private TableColumn<Servis, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<Servis, String> jenisPembayaranColumn;
    @FXML private TableColumn<Servis, String> tglAmbilColumn;
    @FXML private TableColumn<Servis, String> salesAmbilColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalServisLabel;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<Servis> allServis = FXCollections.observableArrayList();
    private final ObservableList<Servis> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noServisColumn.setCellValueFactory(cellData -> cellData.getValue().noServisProperty());
        tglServisColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglServis())));
            } catch (ParseException ex) {
                return null;
            }
        });
        salesTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        kategoriServisColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriServisProperty());
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        jenisPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jenisPembayaranProperty());
        tglAmbilColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglAmbil())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglAmbilColumn.setComparator(Function.sortDate(tglLengkap));
        salesAmbilColumn.setCellValueFactory(cellData -> cellData.getValue().salesAmbilProperty());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            servisTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        servisTable.setContextMenu(rowMenu);
        servisTable.setRowFactory(table -> {
            TableRow<Servis> row = new TableRow<Servis>() {
                @Override
                public void updateItem(Servis item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Servis");
                        detail.setOnAction((ActionEvent e) -> {
                            detailServis(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            servisTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailServis(row.getItem());
                }
            });
            return row;
        });
        allServis.addListener((ListChangeListener.Change<? extends Servis> change) -> {
            searchServis();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchServis();
        });
        filterData.addAll(allServis);
        servisTable.setItems(filterData);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
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
    public void getServis(String tglAwal, String tglAkhir){
        Task<List<Servis>> task = new Task<List<Servis>>() {
            @Override 
            public List<Servis> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return ServisDAO.getAllByTglAmbil(conCabang, tglAwal, tglAkhir);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allServis.clear();
            allServis.addAll(task.getValue());
            servisTable.refresh();
            hitungTotal();
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
    private void searchServis() {
        try{
            filterData.clear();
            for (Servis s : allServis) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(s);
                else{
                    if(checkColumn(s.getNoServis())||
                        checkColumn(tglLengkap.format(tglSql.parse(s.getTglServis())))||
                        checkColumn(s.getKodeSales())||
                        checkColumn(s.getNama())||
                        checkColumn(s.getAlamat())||
                        checkColumn(s.getNamaBarang())||
                        checkColumn(gr.format(s.getBerat()))||
                        checkColumn(s.getKategoriServis())||
                        checkColumn(rp.format(s.getJumlahPembayaran()))||
                        checkColumn(s.getJenisPembayaran())||
                        checkColumn(tglLengkap.format(tglSql.parse(s.getTglAmbil())))||
                        checkColumn(s.getSalesAmbil()))
                        filterData.add(s);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalServis = 0;
        for(Servis s : filterData){
            totalServis = totalServis + s.getJumlahPembayaran();
        }
        totalServisLabel.setText(rp.format(totalServis));
    }
    private void detailServis(Servis s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailServis.fxml");
        DetailServisController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailServis(s.getNoServis());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
