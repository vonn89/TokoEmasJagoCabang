/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangDetailDAO;
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
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PenjualanCabangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.cell.CheckBoxTableCell;
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
public class DataPenjualanCabangController  {

    @FXML private TableView<PenjualanAntarCabangHead> penjualanHeadTable;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> cabangTujuanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalBeratColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, Boolean> statusTerimaColumn;
    @FXML private TableColumn<PenjualanAntarCabangHead, String> kodeSalesColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalPenjualanLabel;
    private Main mainApp;   
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
        cabangTujuanColumn.setCellValueFactory(cellData -> cellData.getValue().cabangTujuanProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        statusTerimaColumn.setCellValueFactory(cellData -> {
            return new SimpleBooleanProperty(Boolean.parseBoolean(cellData.getValue().getStatusTerima()));
        });
        statusTerimaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer i) -> new SimpleBooleanProperty(Boolean.parseBoolean(penjualanHeadTable.getItems().get(i).getStatusTerima()))));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Penjualan Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newPenjualan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
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
                        MenuItem addNew = new MenuItem("New Penjualan Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenjualan();
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Penjualan Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPenjualan(item);
                        });
                        MenuItem print = new MenuItem("Print Surat Penjualan Cabang");
                        print.setOnAction((ActionEvent e) -> {
                            printPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(print);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPenjualan(row.getItem());
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
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getPenjualan();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanAntarCabangHead>> task = new Task<List<PenjualanAntarCabangHead>>() {
            @Override 
            public List<PenjualanAntarCabangHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<PenjualanAntarCabangHead> listPenjualan = PenjualanAntarCabangHeadDAO.
                        getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabang.getKodeCabang(), "%","%","false");
                    return listPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanAntarCabangHead t : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglPenjualan())))||
                        checkColumn(t.getCabangTujuan())||
                        checkColumn(t.getStatusTerima())||
                        checkColumn(t.getKodeSales())||
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
        double totalPenjualan = 0;
        for(PenjualanAntarCabangHead p : filterData){
            totalPenjualan = totalPenjualan + p.getTotalHarga();
        }
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    private void newPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PenjualanCabang.fxml");
        PenjualanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPenjualan();
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Cabang tujuan belum dipilih");
            else if(controller.listPenjualanAntarCabangDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data penjualan masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                PenjualanAntarCabangHead p = new PenjualanAntarCabangHead();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                p.setTglPenjualan(Function.getSystemDate());
                                p.setKodeCabang(cabang.getKodeCabang());
                                p.setCabangTujuan(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                                p.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                                double totalBerat = 0;
                                double totalPenjualan = 0;
                                for(PenjualanAntarCabangDetail d : controller.listPenjualanAntarCabangDetail){
                                    totalBerat = totalBerat + d.getBerat();
                                    totalPenjualan = totalPenjualan + d.getHarga();
                                }
                                p.setTotalBerat(totalBerat);
                                p.setTotalHarga(totalPenjualan);
                                p.setStatusTerima("false");
                                p.setTglTerima("2000-01-01 00:00:00");
                                p.setSalesTerima("");
                                p.setStatusBatal("false");
                                p.setTglBatal("2000-01-01 00:00:00");
                                p.setUserBatal("");
                                p.setListPenjualanAntarCabangDetail(controller.listPenjualanAntarCabangDetail);
                                return Service.savePenjualanCabang(conPusat, conCabang, p);
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((ev) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan cabang berhasil disimpan");
                        if(controller.printSuratPenjualanCabangCheck.isSelected()){
                            try{
                                for(PenjualanAntarCabangDetail d : controller.listPenjualanAntarCabangDetail){
                                    d.setPenjualanAntarCabangHead(p);
                                }
                                PrintOut po = new PrintOut();
                                po.printSuratPenjualanCabang(controller.listPenjualanAntarCabangDetail);
                            }catch(Exception e){
                                mainApp.showMessage(Modality.NONE, "Error", e.toString());
                            }
                        }
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
    private void detailPenjualan(PenjualanAntarCabangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PenjualanCabang.fxml");
        PenjualanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPenjualan(p);
    }
    private void batalPenjualan(PenjualanAntarCabangHead p){
        try{
            if(!p.getNoPenjualan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan tidak dapat dibatal, karena sudah berbeda tanggal");
            }else if(p.getStatusTerima().equals("true")){
                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan tidak dapat dibatal, karena sudah diterima cabang");
            }else if(p.getStatusBatal().equals("true")){
                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan tidak dapat dibatal, karena sudah dibatalkan");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal penjualan "+p.getNoPenjualan()+" ?");
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
                                    controller.passwordField.getText(), "Batal Penjualan Cabang")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                                p.setStatusBatal("true");
                                                p.setTglBatal(Function.getSystemDate());
                                                p.setUserBatal(controller.usernameField.getText());
                                                return Service.saveBatalPenjualanCabang(conPusat, conCabang, p);
                                            }
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPenjualan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan cabang berhasil dibatal");
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
    private void printPenjualan(PenjualanAntarCabangHead p){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<PenjualanAntarCabangDetail> listPenjualan = PenjualanAntarCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualan());
            for(PenjualanAntarCabangDetail d : listPenjualan){
                d.setPenjualanAntarCabangHead(p);
            }
            PrintOut po = new PrintOut();
            po.printSuratPenjualanCabang(listPenjualan);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
