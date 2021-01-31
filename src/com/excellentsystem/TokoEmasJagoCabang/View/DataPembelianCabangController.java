/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PembelianCabangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataPembelianCabangController  {

    @FXML private TableView<PenjualanAntarCabangHead> pembelianHeadTable;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> tglPembelianColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> cabangAsalColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> salesJualColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalBeratColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> kodeSalesColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalPembelianLabel;
    private Main mainApp;   
    private final ObservableList<PenjualanAntarCabangHead> allPembelian = FXCollections.observableArrayList();
    private final ObservableList<PenjualanAntarCabangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        cabangAsalColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        salesJualColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().salesTerimaProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Pembelian Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pembelianHeadTable.setContextMenu(rowMenu);
        pembelianHeadTable.setRowFactory(table -> {
            TableRow<PenjualanAntarCabangHead> row = new TableRow<PenjualanAntarCabangHead>() {
                @Override
                public void updateItem(PenjualanAntarCabangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pembelian Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Pembelian Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPembelian(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembelian(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPembelian(row.getItem());
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PenjualanAntarCabangHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
        pembelianHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getPembelian();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getPembelian(){
        Task<List<PenjualanAntarCabangHead>> task = new Task<List<PenjualanAntarCabangHead>>() {
            @Override 
            public List<PenjualanAntarCabangHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<PenjualanAntarCabangHead> listPenjualan = PenjualanAntarCabangHeadDAO.
                            getAllByTglTerimaAndCabangAsalAndCabangTujuan(conPusat, 
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", cabang.getKodeCabang());
                    return listPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
            pembelianHeadTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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
    private void searchPembelian() {
        try{
            filterData.clear();
            for (PenjualanAntarCabangHead t : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglTerima())))||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglPenjualan())))||
                        checkColumn(t.getCabangTujuan())||
                        checkColumn(t.getKodeCabang())||
                        checkColumn(t.getKodeSales())||
                        checkColumn(t.getSalesTerima())||
                        checkColumn(gr.format(t.getTotalBerat()))||
                        checkColumn(rp.format(t.getTotalHarga())))
                        filterData.add(t);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalPembelian = 0;
        for(PenjualanAntarCabangHead p : filterData){
            totalPembelian = totalPembelian + p.getTotalHarga();
        }
        totalPembelianLabel.setText(rp.format(totalPembelian));
    }
    private void newPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PembelianCabang.fxml");
        PembelianCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewPembelian();
        controller.saveButton.setOnAction((event) -> {
            if(controller.penjualanAntarCabangHead==null)
                mainApp.showMessage(Modality.NONE, "Warning", "No Penjualan masih kosong / tidak sesuai");
            else if(controller.listPembelianAntarCabangDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data pembelian masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                controller.penjualanAntarCabangHead.setStatusTerima("true");
                                controller.penjualanAntarCabangHead.setTglTerima(Function.getSystemDate());
                                controller.penjualanAntarCabangHead.setSalesTerima(controller.kodeSalesField.getText());
                                return Service.savePembelianCabang(conPusat, conCabang, controller.penjualanAntarCabangHead);
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    String status = task.getValue();
                    if(status.equals("true")){
                        String message = "\n";
                        for(PenjualanAntarCabangDetail d : controller.penjualanAntarCabangHead.getListPenjualanAntarCabangDetail()){
                            if(!d.getKodeBarcode().equals(d.getKodeBarcodeBaru()))
                                message = message + "kode barcode = "+d.getKodeBarcode()+" diganti dengan kode barcode baru = "+d.getKodeBarcodeBaru()+"\n";
                        }
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian cabang berhasil disimpan"+message);
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
    private void detailPembelian(PenjualanAntarCabangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PembelianCabang.fxml");
        PembelianCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPembelian(p);
    }
    private void batalPembelian(PenjualanAntarCabangHead p){
        try{
            if(!p.getNoPenjualan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Pembelian tidak dapat dibatal, karena sudah berbeda tanggal");
            }else if(p.getStatusBatal().equals("true")){
                mainApp.showMessage(Modality.NONE, "Warning", "Pembelian tidak dapat dibatal, karena sudah dibatalkan");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal pembelian "+p.getNoPenjualan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, mainApp.MainStage, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(mainApp.MainStage, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Pembelian Cabang")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                                p.setStatusTerima("false");
                                                p.setTglBatal("2000-01-01 00:00:00");
                                                p.setUserBatal("");
                                                return Service.saveBatalPembelianCabang(conPusat, conCabang, p);
                                            }
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPembelian();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian cabang berhasil dibatal");
                                    }else
                                        mainApp.showMessage(Modality.NONE, "Failed", status);
                                });
                                task.setOnFailed((e) -> {
                                    mainApp.closeLoading();
                                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                                });
                                new Thread(task).start();

                            }else{
                                mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                        + "atau otoritas tidak diijinkan");
                            }
                        }
                    });
                });
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
