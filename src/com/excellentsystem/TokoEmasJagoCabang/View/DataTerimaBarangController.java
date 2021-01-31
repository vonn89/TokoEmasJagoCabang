/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahHead;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailTerimaBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.TerimaBarangController;
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
public class DataTerimaBarangController {

    @FXML private TableView<PindahHead> terimaBarangTable;
    @FXML private TableColumn<PindahHead, String> noPindahColumn;
    @FXML private TableColumn<PindahHead, String> tglPindahColumn;
    @FXML private TableColumn<PindahHead, String> userPindahColumn;
    @FXML private TableColumn<PindahHead, Number> totalQtyColumn;
    @FXML private TableColumn<PindahHead, Number> totalBeratColumn;
    @FXML private TableColumn<PindahHead, String> tglTerimaColumn;
    @FXML private TableColumn<PindahHead, String> salesTerimaColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    private Main mainApp;   
    private final ObservableList<PindahHead> allTerimaBarang = FXCollections.observableArrayList();
    private final ObservableList<PindahHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPindahColumn.setCellValueFactory(cellData -> cellData.getValue().noPindahProperty());
        tglPindahColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPindah())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPindahColumn.setComparator(Function.sortDate(tglLengkap));
        tglTerimaColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglTerimaColumn.setComparator(Function.sortDate(tglLengkap));
        userPindahColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        salesTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().userTerimaProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Terima Barang");
        addNew.setOnAction((ActionEvent e) -> {
            newTerimaBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTerimaBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        terimaBarangTable.setContextMenu(rowMenu);
        terimaBarangTable.setRowFactory(table -> {
            TableRow<PindahHead> row = new TableRow<PindahHead>() {
                @Override
                public void updateItem(PindahHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Terima Barang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newTerimaBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Terima Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailTerimaBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Barang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalTerimaBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getTerimaBarang();
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
                        detailTerimaBarang(row.getItem());
                }
            });
            return row;
        });
        allTerimaBarang.addListener((ListChangeListener.Change<? extends PindahHead> change) -> {
            searchTerimaBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchTerimaBarang();
        });
        filterData.addAll(allTerimaBarang);
        terimaBarangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getTerimaBarang();
    } 
    @FXML
    private void getTerimaBarang(){
        Task<List<PindahHead>> task = new Task<List<PindahHead>>() {
            @Override 
            public List<PindahHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<PindahHead> listTerima = PindahHeadDAO.getAllByTglTerimaAndCabang(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                        cabang.getKodeCabang());
                    return listTerima;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allTerimaBarang.clear();
            allTerimaBarang.addAll(task.getValue());
            terimaBarangTable.refresh();
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
    private void searchTerimaBarang() {
        try{
            filterData.clear();
            for (PindahHead t : allTerimaBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoPindah())||
                        checkColumn(t.getKodeUser())||
                        checkColumn(t.getUserTerima())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglPindah())))||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglTerima())))||
                        checkColumn(rp.format(t.getTotalQty()))||
                        checkColumn(gr.format(t.getTotalBerat())))
                        filterData.add(t);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        for(PindahHead t : filterData){
            totalQty = totalQty + 1;
            totalBerat = totalBerat + t.getTotalBerat();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }
    private void newTerimaBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TerimaBarang.fxml");
        TerimaBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.listPindahDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data terima barang masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                controller.pindahHead.setStatusTerima("true");
                                controller.pindahHead.setTglTerima(Function.getSystemDate());
                                controller.pindahHead.setUserTerima(controller.kodeSalesField.getText());
                                controller.pindahHead.setListPindahDetail(controller.listPindahDetail);
                                return Service.saveTerimaBarang(conPusat, conCabang, controller.pindahHead);
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTerimaBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Terima barang berhasil disimpan");
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
    private void detailTerimaBarang(PindahHead t){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailTerimaBarang.fxml");
        DetailTerimaBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setTerimaHead(t);
    }
    private void batalTerimaBarang(PindahHead p){
        if(!p.getTglTerima().substring(0,10).equals(sistem.getTglSystem())){
            mainApp.showMessage(Modality.NONE, "Warning", "Terima barang tidak dapat dibatal, "
                    + "karena sudah berbeda tanggal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal terima barang "+p.getNoPindah()+" ?");
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
                                controller.passwordField.getText(), "Batal Terima Barang")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            p.setStatusTerima("false");
                                            p.setTglTerima("2000-01-01 00:00:00");
                                            p.setUserTerima("");
                                            return Service.saveBatalTerimaBarang(conPusat, conCabang, p);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getTerimaBarang();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Terima barang berhasil dibatal");
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
    }
}
