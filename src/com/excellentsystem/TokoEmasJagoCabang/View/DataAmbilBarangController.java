/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.AmbilBarangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.AmbilBarangHeadDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangHead;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.AmbilBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailAmbilBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class DataAmbilBarangController  {

    @FXML private TableView<AmbilBarangHead> ambilBarangTable;
    @FXML private TableColumn<AmbilBarangHead, String> noAmbilBarangColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglAmbilColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglPembelianMulaiColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglPembelianAkhirColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalQtyColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratKotorColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratBersihColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratPersenColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalPembelianColumn;
    @FXML private TableColumn<AmbilBarangHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalPembelianLabel;
    private Main mainApp;   
    private final ObservableList<AmbilBarangHead> allAmbilBarang = FXCollections.observableArrayList();
    private final ObservableList<AmbilBarangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noAmbilBarangColumn.setCellValueFactory(cellData -> cellData.getValue().noAmbilBarangProperty());
        tglAmbilColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglAmbilBarang())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglAmbilColumn.setComparator(Function.sortDate(tglLengkap));
        tglPembelianMulaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglPembelianMulai())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianMulaiColumn.setComparator(Function.sortDate(tglNormal));
        tglPembelianAkhirColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglPembelianAkhir())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianAkhirColumn.setComparator(Function.sortDate(tglNormal));
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratKotorProperty());
        totalBeratKotorColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratBersihProperty());
        totalBeratBersihColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratPersenProperty());
        totalBeratPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> getTableCell(rp));
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Ambil Barang");
        addNew.setOnAction((ActionEvent e) -> {
            newAmbilBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getAmbilBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        ambilBarangTable.setContextMenu(rowMenu);
        ambilBarangTable.setRowFactory(table -> {
            TableRow<AmbilBarangHead> row = new TableRow<AmbilBarangHead>() {
                @Override
                public void updateItem(AmbilBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Ambil Barang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newAmbilBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Ambil Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailAmbilBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Ambil Barang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalAmbilBarang(item);
                        });
                        MenuItem print = new MenuItem("Print Surat Ambil Barang");
                        print.setOnAction((ActionEvent e) -> {
                            printBuktiAmbilBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getAmbilBarang();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(print);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailAmbilBarang(row.getItem());
                }
            });
            return row;
        });
        allAmbilBarang.addListener((ListChangeListener.Change<? extends AmbilBarangHead> change) -> {
            searchAmbilBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchAmbilBarang();
        });
        filterData.addAll(allAmbilBarang);
        ambilBarangTable.setItems(filterData);
    }     
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getAmbilBarang();
    } 
    @FXML
    private void getAmbilBarang(){
        Task<List<AmbilBarangHead>> task = new Task<List<AmbilBarangHead>>() {
            @Override 
            public List<AmbilBarangHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return AmbilBarangHeadDAO.getAllByDateAndCabangAndStatusTerimaAndStatusBatal(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabang.getKodeCabang(), "%", "false");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allAmbilBarang.clear();
            allAmbilBarang.addAll(task.getValue());
            ambilBarangTable.refresh();
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
    private void searchAmbilBarang() {
        try{
            filterData.clear();
            for (AmbilBarangHead t : allAmbilBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoAmbilBarang())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglTerima())))||
                        checkColumn(tglNormal.format(tglBarang.parse(t.getTglPembelianMulai())))||
                        checkColumn(tglNormal.format(tglBarang.parse(t.getTglPembelianAkhir())))||
                        checkColumn(t.getKodeUser())||
                        checkColumn(rp.format(t.getTotalQty()))||
                        checkColumn(gr.format(t.getTotalBeratKotor()))||
                        checkColumn(gr.format(t.getTotalBeratBersih()))||
                        checkColumn(gr.format(t.getTotalBeratPersen()))||
                        checkColumn(rp.format(t.getTotalPembelian())))
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
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalBeratPersen = 0;
        double totalPembelian = 0;
        for(AmbilBarangHead t : filterData){
            totalQty = totalQty + t.getTotalQty();
            totalBeratKotor = totalBeratKotor + t.getTotalBeratKotor();
            totalBeratBersih = totalBeratBersih + t.getTotalBeratBersih();
            totalBeratPersen = totalBeratPersen + t.getTotalBeratPersen();
            totalPembelian = totalPembelian + t.getTotalPembelian();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratKotorLabel.setText(gr.format(totalBeratKotor));
        totalBeratBersihLabel.setText(gr.format(totalBeratBersih));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
        totalPembelianLabel.setText(rp.format(totalPembelian));
    }
    private void newAmbilBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AmbilBarang.fxml");
        AmbilBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.allPembelianDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data ambil barang masih kosong");
            else{
                Stage child = new Stage();
                FXMLLoader verifikasiloader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController verifikasiController = verifikasiloader.getController();
                verifikasiController.setMainApp(mainApp, stage, child);
                verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(stage, child);
                        if(Function.verifikasi(verifikasiController.usernameField.getText(), 
                                verifikasiController.passwordField.getText(), "Ambil Barang")){

                            AmbilBarangHead a = new AmbilBarangHead();
                            List<AmbilBarangDetail> listAmbilBarangDetail = new ArrayList<>();
                            Task<String> task = new Task<String>(){
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            a.setTglAmbilBarang(Function.getSystemDate());
                                            a.setTglPembelianMulai(controller.tglMulaiPicker.getValue().toString());
                                            a.setTglPembelianAkhir(controller.tglAkhirPicker.getValue().toString());
                                            a.setKodeCabang(cabang.getKodeCabang());
                                            a.setTotalQty(Integer.parseInt(controller.totalQtyLabel.getText().replaceAll(",", "")));
                                            a.setTotalBeratKotor(Double.parseDouble(controller.totalBeratKotorLabel.getText().replaceAll(",", "")));
                                            a.setTotalBeratBersih(Double.parseDouble(controller.totalBeratBersihLabel.getText().replaceAll(",", "")));
                                            a.setTotalBeratPersen(Double.parseDouble(controller.totalBeratPersenLabel.getText().replaceAll(",", "")));
                                            a.setTotalPembelian(Double.parseDouble(controller.totalPembelianLabel.getText().replaceAll(",", "")));
                                            a.setKodeUser(verifikasiController.usernameField.getText());
                                            a.setStatusTerima("false");
                                            a.setTglTerima("2000-01-01 00:00:00");
                                            a.setUserTerima("");
                                            a.setStatusBatal("false");
                                            a.setTglBatal("2000-01-01 00:00:00");
                                            a.setUserBatal("");
                                            
                                            for(PembelianDetail d : controller.allPembelianDetail){
                                                AmbilBarangDetail ab = new AmbilBarangDetail();
                                                ab.setNoAmbilBarang("");
                                                ab.setNoPembelian(d.getNoPembelian());
                                                ab.setNoUrut(d.getNoUrut());
                                                ab.setTglPembelian(d.getPembelianHead().getTglPembelian());
                                                ab.setKodeSales(d.getPembelianHead().getKodeSales());
                                                ab.setKodeKategori(d.getKodeKategori());
                                                ab.setKodeJenis(d.getKodeJenis());
                                                ab.setNamaBarang(d.getNamaBarang());
                                                ab.setQty(d.getQty());
                                                ab.setBeratKotor(d.getBeratKotor());
                                                ab.setBeratBersih(d.getBeratBersih());
                                                ab.setPersentaseEmas(d.getPersentaseEmas());
                                                ab.setBeratPersen(d.getBeratPersen());
                                                ab.setSuratPembelian(d.getSuratPembelian());
                                                ab.setHargaKomp(d.getHargaKomp());
                                                ab.setHargaBeli(d.getHargaBeli());
                                                listAmbilBarangDetail.add(ab);
                                            }
                                            a.setDetail(listAmbilBarangDetail);
                                            
                                            return Service.saveAmbilBarang(conPusat, conCabang, a, controller.allPembelianDetail);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((ev) -> {
                                mainApp.closeLoading();
                                getAmbilBarang();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.closeDialog(mainApp.MainStage, stage);
                                    mainApp.showMessage(Modality.NONE, "Success", "Ambil barang berhasil disimpan");
                                    try{
                                        for(AmbilBarangDetail d : listAmbilBarangDetail){
                                            d.setAmbilBarangHead(a);
                                        }
                                        PrintOut po = new PrintOut();
                                        po.printBuktiAmbilBarang(listAmbilBarangDetail);
                                    }catch(Exception e){
                                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                    }
                                }else
                                    mainApp.showMessage(Modality.NONE, "Failed", status);
                            });
                            task.setOnFailed((e) -> {
                                task.getException().printStackTrace();
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
            }
        });
    }
    private void detailAmbilBarang(AmbilBarangHead a){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailAmbilBarang.fxml");
        DetailAmbilBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setAmbilBarang(a);
    }
    private void batalAmbilBarang(AmbilBarangHead a){
        if(!a.getTglAmbilBarang().substring(0,10).equals(sistem.getTglSystem())){
            mainApp.showMessage(Modality.NONE, "Warning", "Ambil barang tidak dapat dibatal, "
                    + "karena sudah berbeda tanggal");
        }else if (a.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Ambil barang tidak dapat dibatal, "
                    + "karena sudah dibatal");
        }else if (a.getStatusTerima().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Ambil barang tidak dapat dibatal, "
                    + "karena sudah diterima");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal ambil barang "+a.getNoAmbilBarang()+" ?");
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
                                controller.passwordField.getText(), "Batal Ambil Barang")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            a.setStatusBatal("true");
                                            a.setTglBatal(Function.getSystemDate());
                                            a.setUserBatal(controller.usernameField.getText());
                                            return Service.saveBatalAmbilBarang(conPusat, conCabang, a);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getAmbilBarang();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Ambil barang berhasil dibatal");
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
    private void printBuktiAmbilBarang(AmbilBarangHead a){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<AmbilBarangDetail> listAmbil = AmbilBarangDetailDAO.getAllByNoAmbilBarang(conPusat, a.getNoAmbilBarang());
            for(AmbilBarangDetail d : listAmbil){
                d.setAmbilBarangHead(a);
            }
            PrintOut po = new PrintOut();
            po.printBuktiAmbilBarang(listAmbil);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
