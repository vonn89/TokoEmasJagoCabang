/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.CariHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PelunasanHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.TerimaHutangController;
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
public class DataPelunasanHutangController  {

    @FXML private TableView<HutangHead> terimaHutangTable;
    @FXML private TableColumn<HutangHead, String> noHutangColumn;
    @FXML private TableColumn<HutangHead, String> tglLunasColumn;
    @FXML private TableColumn<HutangHead, String> salesLunasColumn;
    @FXML private TableColumn<HutangHead, String> tglHutangColumn;
    @FXML private TableColumn<HutangHead, String> kodeSalesColumn;
    @FXML private TableColumn<HutangHead, String> namaColumn;
    @FXML private TableColumn<HutangHead, String> alamatColumn;
    @FXML private TableColumn<HutangHead, Number> totalBeratColumn;
    @FXML private TableColumn<HutangHead, Number> totalHutangColumn;
    @FXML private TableColumn<HutangHead, Number> bungaPersenColumn;
    @FXML private TableColumn<HutangHead, Number> lamaPinjamColumn;
    @FXML private TableColumn<HutangHead, Number> bungaKompColumn;
    @FXML private TableColumn<HutangHead, Number> bungaRpColumn;
    @FXML private TableColumn<HutangHead, String> keteranganColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHutangLabel;
    @FXML private Label totalBungaLabel;
    private Main mainApp;   
    private final ObservableList<HutangHead> allHutang = FXCollections.observableArrayList();
    private final ObservableList<HutangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglHutangColumn.setComparator(Function.sortDate(tglLengkap));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHutangColumn.setCellValueFactory(cellData -> cellData.getValue().totalHutangProperty());
        totalHutangColumn.setCellFactory(col -> getTableCell(rp));
        bungaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> getTableCell(gr));
        bungaKompColumn.setCellValueFactory(cellData -> cellData.getValue().bungaKompProperty());
        bungaKompColumn.setCellFactory(col -> getTableCell(rp));
        bungaRpColumn.setCellValueFactory(cellData -> cellData.getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> getTableCell(rp));
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        lamaPinjamColumn.setCellValueFactory(cellData -> cellData.getValue().lamaPinjamProperty());
        lamaPinjamColumn.setCellFactory(col -> getTableCell(rp));
        tglLunasColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglLunas())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglLunasColumn.setComparator(Function.sortDate(tglLengkap));
        salesLunasColumn.setCellValueFactory(cellData -> cellData.getValue().salesLunasProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cari = new MenuItem("Cari Hutang");
        cari.setOnAction((ActionEvent e) -> {
            cariHutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getHutang();
        });
        rowMenu.getItems().addAll(cari, refresh);
        terimaHutangTable.setContextMenu(rowMenu);
        terimaHutangTable.setRowFactory(table -> {
            TableRow<HutangHead> row = new TableRow<HutangHead>() {
                @Override
                public void updateItem(HutangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cari = new MenuItem("Cari Hutang");
                        cari.setOnAction((ActionEvent e) -> {
                            cariHutang();
                        });
                        MenuItem detailTerima = new MenuItem("Detail Terima Hutang");
                        detailTerima.setOnAction((ActionEvent e) -> {
                            detailHutang(item);
                        });
                        MenuItem detail = new MenuItem("Detail Pelunasan Hutang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPelunasanHutang(item);
                        });
                        MenuItem print = new MenuItem("Print Bukti Pelunasan Hutang");
                        print.setOnAction((ActionEvent e) -> {
                            printBuktiPelunasanHutang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pelunasan Hutang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPelunasanHutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getHutang();
                        });
                        rowMenu.getItems().add(cari);
                        rowMenu.getItems().add(detailTerima);
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
                        detailPelunasanHutang(row.getItem());
                }
            });
            return row;
        });
        allHutang.addListener((ListChangeListener.Change<? extends HutangHead> change) -> {
            searchHutang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        filterData.addAll(allHutang);
        terimaHutangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getHutang();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getHutang(){
        Task<List<HutangHead>> task = new Task<List<HutangHead>>() {
            @Override 
            public List<HutangHead> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<HutangHead> listHutangHead = HutangHeadDAO.getAllByTglLunas(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    return listHutangHead;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allHutang.clear();
            allHutang.addAll(task.getValue());
            terimaHutangTable.refresh();
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
    private void searchHutang() {
        try{
            filterData.clear();
            for (HutangHead d : allHutang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getNoHutang())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglHutang())))||
                        checkColumn(d.getKodeSales())||
                        checkColumn(d.getNama())||
                        checkColumn(d.getAlamat())||
                        checkColumn(gr.format(d.getTotalBerat()))||
                        checkColumn(rp.format(d.getTotalHutang()))||
                        checkColumn(gr.format(d.getBungaPersen()))||
                        checkColumn(rp.format(d.getBungaRp()))||
                        checkColumn(rp.format(d.getLamaPinjam()))||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglLunas())))||
                        checkColumn(d.getStatusHilang())||
                        checkColumn(d.getSalesLunas()))
                        filterData.add(d);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalHutang = 0;
        double totalBunga = 0;
        for(HutangHead d : filterData){
            totalBerat = totalBerat + d.getTotalBerat();
            totalHutang = totalHutang + d.getTotalHutang();
            totalBunga = totalBunga + d.getBungaRp();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHutangLabel.setText(rp.format(totalHutang));
        totalBungaLabel.setText(rp.format(totalBunga));
    }
    private void detailHutang(HutangHead h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/TerimaHutang.fxml");
        TerimaHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailHutang(h);
    }
    private void detailPelunasanHutang(HutangHead h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PelunasanHutang.fxml");
        PelunasanHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailHutang(h);
    }
    private void batalPelunasanHutang(HutangHead h){
        if(!h.getTglLunas().substring(0,10).equals(sistem.getTglSystem())){
            mainApp.showMessage(Modality.NONE, "Warning", "Pelunasan hutang tidak dapat dibatal, karena sudah berbeda tanggal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal pelunasan hutang "+h.getNoHutang()+" ?");
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
                                controller.passwordField.getText(), "Batal Pelunasan Hutang")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                        return Service.saveBatalPelunasanHutang(conCabang, h, controller.usernameField.getText());
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getHutang();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Pelunasan hutang berhasil dibatal");
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
    private void cariHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/CariHutang.fxml");
        CariHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }
    private void printBuktiPelunasanHutang(HutangHead h){    
        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
            List<HutangDetail> listHutang = HutangDetailDAO.getAllByNoHutang(conCabang, h.getNoHutang());
            for(HutangDetail d : listHutang){
                d.setHutangHead(h);
            }
            PrintOut po = new PrintOut();
            po.printBuktiPelunasanGadai(listHutang);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
