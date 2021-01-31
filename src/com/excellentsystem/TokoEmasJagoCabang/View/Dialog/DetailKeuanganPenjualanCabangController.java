/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
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
public class DetailKeuanganPenjualanCabangController {

    @FXML private Label title;
    @FXML private TableView<PenjualanAntarCabangHead> penjualanHeadTable;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> tglPembelianColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> cabangAsalColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> cabangTujuanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> salesJualColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> salesTerimaColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalBeratColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalPenjualanColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPenjualanStringLabel;
    @FXML private Label totalPenjualanLabel;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<PenjualanAntarCabangHead> allPenjualan = FXCollections.observableArrayList();
    private final ObservableList<PenjualanAntarCabangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        cabangAsalColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        cabangTujuanColumn.setCellValueFactory(cellData -> cellData.getValue().cabangTujuanProperty());
        salesJualColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        salesTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().salesTerimaProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanHeadTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        penjualanHeadTable.setContextMenu(rowMenu);
        penjualanHeadTable.setRowFactory(table -> {
            TableRow<PenjualanAntarCabangHead> row = new TableRow<PenjualanAntarCabangHead>() {
                @Override
                public void updateItem(PenjualanAntarCabangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detailBeli = new MenuItem("Detail Pembelian Cabang");
                        detailBeli.setOnAction((ActionEvent e) -> {
                            detailPembelian(item);
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            penjualanHeadTable.refresh();
                        });
                        if(title.getText().matches("Pembelian Antar Cabang - .*"))
                            rowMenu.getItems().add(detailBeli);
                        if(title.getText().matches("Penjualan Antar Cabang - .*"))
                            rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(title.getText().matches("Pembelian Antar Cabang - .*"))
                            detailPembelian(row.getItem());
                        else if(title.getText().matches("Penjualan Antar Cabang - .*"))
                            detailPenjualan(row.getItem());
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanAntarCabangHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanHeadTable.setItems(filterData);
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
    public void getPenjualan(String tglAwal, String tglAkhir, String kategori){
        Task<List<PenjualanAntarCabangHead>> task = new Task<List<PenjualanAntarCabangHead>>() {
            @Override 
            public List<PenjualanAntarCabangHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<PenjualanAntarCabangHead> listPenjualan = PenjualanAntarCabangHeadDAO.
                        getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                            conPusat, tglAwal, tglAkhir, cabang.getKodeCabang(), kategori.split(" - ")[1],"%","false");
                    return listPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            title.setText(kategori);
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
            hitungTotal();
            tglPembelianColumn.setVisible(false);
            cabangAsalColumn.setVisible(false);
            salesTerimaColumn.setVisible(false);
            totalPenjualanStringLabel.setText("Total Penjualan");
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void getPembelian(String tglAwal, String tglAkhir, String kategori){
        Task<List<PenjualanAntarCabangHead>> task = new Task<List<PenjualanAntarCabangHead>>() {
            @Override 
            public List<PenjualanAntarCabangHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<PenjualanAntarCabangHead> listPenjualan = PenjualanAntarCabangHeadDAO.
                            getAllByTglTerimaAndCabangAsalAndCabangTujuan(conPusat, tglAwal, tglAkhir, kategori.split(" - ")[1], cabang.getKodeCabang());
                    return listPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            title.setText(kategori);
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
            hitungTotal();
            cabangTujuanColumn.setVisible(false);
            totalPenjualanStringLabel.setText("Total Pembelian");
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanAntarCabangHead d : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglPenjualan())))||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglTerima())))||
                        checkColumn(rp.format(d.getTotalHarga()))||
                        checkColumn(d.getCabangTujuan())||
                        checkColumn(d.getKodeCabang())||
                        checkColumn(d.getSalesTerima())||
                        checkColumn(d.getKodeSales()))
                        filterData.add(d);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalPenjualan = 0;
        for(PenjualanAntarCabangHead p : filterData){
            totalPenjualan = totalPenjualan + p.getTotalHarga();
        }
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    private void detailPenjualan(PenjualanAntarCabangHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PenjualanCabang.fxml");
        PenjualanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPenjualan(p);
    }
    private void detailPembelian(PenjualanAntarCabangHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PembelianCabang.fxml");
        PembelianCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPembelian(p);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
