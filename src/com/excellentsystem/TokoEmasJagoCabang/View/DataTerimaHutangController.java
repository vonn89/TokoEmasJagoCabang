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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PelunasanHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.TerimaHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import static java.lang.Math.ceil;
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
import javafx.scene.control.ComboBox;
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
public class DataTerimaHutangController {

    @FXML private TableView<HutangHead> terimaHutangTable;
    @FXML private TableColumn<HutangHead, String> noHutangColumn;
    @FXML private TableColumn<HutangHead, String> tglHutangColumn;
    @FXML private TableColumn<HutangHead, String> kodeSalesColumn;
    @FXML private TableColumn<HutangHead, String> namaColumn;
    @FXML private TableColumn<HutangHead, String> alamatColumn;
    @FXML private TableColumn<HutangHead, Number> totalBeratColumn;
    @FXML private TableColumn<HutangHead, Number> totalHutangColumn;
    @FXML private TableColumn<HutangHead, Number> bungaPersenColumn;
    @FXML private TableColumn<HutangHead, Number> bungaRpColumn;
    @FXML private TableColumn<HutangHead, String> statusHilangColumn;
    @FXML private TableColumn<HutangHead, Number> lamaPinjamColumn;
    @FXML private TableColumn<HutangHead, String> tglLunasColumn;
    @FXML private TableColumn<HutangHead, String> salesLunasColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHutangLabel;
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
        bungaRpColumn.setCellValueFactory(cellData -> cellData.getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> getTableCell(rp));
        statusHilangColumn.setCellValueFactory(cellData -> cellData.getValue().statusHilangProperty());
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
        MenuItem addNew = new MenuItem("New Terima Hutang");
        addNew.setOnAction((ActionEvent e) -> {
            newHutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getHutang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
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
                        MenuItem addNew = new MenuItem("New Terima Hutang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newHutang();
                        });
                        MenuItem detailTerima = new MenuItem("Detail Terima Hutang");
                        detailTerima.setOnAction((ActionEvent e) -> {
                            detailHutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pelunasan Hutang");
                        bayar.setOnAction((ActionEvent e) -> {
                            pelunasanHutang(item);
                        });
                        MenuItem detailPelunasan = new MenuItem("Detail Pelunasan Hutang");
                        detailPelunasan.setOnAction((ActionEvent e) -> {
                            detailPelunasanHutang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Hutang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalHutang(item);
                        });
                        MenuItem print = new MenuItem("Print Surat Hutang");
                        print.setOnAction((ActionEvent e) -> {
                            printSuratHutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getHutang();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detailTerima);
                        if(item.getStatusLunas().equals("false")){
                            rowMenu.getItems().add(batal);
                            rowMenu.getItems().add(bayar);
                            rowMenu.getItems().add(print);
                        }
                        if(item.getStatusLunas().equals("true")){
                            rowMenu.getItems().add(detailPelunasan);
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailHutang(row.getItem());
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
        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.addAll("Semua","Lunas","Belum Lunas");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Semua");
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
                    String status = "%";
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Lunas"))
                        status = "true";
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas"))
                        status = "false";
                    List<HutangHead> listHutangHead = HutangHeadDAO.getAllByDateAndStatusLunasAndStatusBatal(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status,"false");
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
        for(HutangHead d : filterData){
            totalBerat = totalBerat + d.getTotalBerat();
            totalHutang = totalHutang + d.getTotalHutang();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHutangLabel.setText(rp.format(totalHutang));
    }
    private void newHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/TerimaHutang.fxml");
        TerimaHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewHutang();
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.pelangganUmumRadio.isSelected() && controller.namaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama pelanggan masih kosong");
            else if(controller.listHutangDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data barang masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else if(controller.totalPinjamanField.getText().equals("")||controller.totalPinjamanField.getText().equals("0"))
                mainApp.showMessage(Modality.NONE, "Warning", "Total pinjaman masih kosong");
            else if(controller.bungaPersenField.getText().equals("") || controller.bungaPersenField.getText().equals("0"))
                mainApp.showMessage(Modality.NONE, "Warning", "Bunga persen masih kosong");
            else{
                if(Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", ""))>
                        Double.parseDouble(controller.totalMaxPinjamanLabel.getText().replaceAll(",", ""))){
                    MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                            "Total pinjaman melebihi jumlah maksimal pinjaman ?");
                    x.OK.setOnAction((ActionEvent ex) -> {
                        mainApp.closeMessage();
                        checkBungaPersen(controller, stage);
                    });
                }else{
                    checkBungaPersen(controller, stage);
                }
            }
        });
    }
    private void checkBungaPersen(TerimaHutangController controller, Stage stage){
        double bunga = 0;
        for(BungaHutang b : controller.listBungaHutang){
            if(b.getMinJumlahRp()<=Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", "")) && 
                    Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", ""))<=b.getMaxJumlahRp())
                bunga = b.getBungaPersen();
        }
        if(bunga != Double.parseDouble(controller.bungaPersenField.getText().replaceAll(",", ""))){
            Stage child = new Stage();
            FXMLLoader verifikasiLoader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
            VerifikasiController verifikasiController = verifikasiLoader.getController();
            verifikasiController.setMainApp(mainApp, stage, child);
            verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    mainApp.closeDialog(stage, child);
                    if(Function.verifikasi(verifikasiController.usernameField.getText(), verifikasiController.passwordField.getText(),"Terima Hutang")){
                        saveTerimaHutang(controller, stage);
                    }else{
                        mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                    }
                }
            });
        }else{
            saveTerimaHutang(controller, stage);
        }
    }
    private void saveTerimaHutang(TerimaHutangController controller, Stage stage){
        HutangHead hutang = new HutangHead();
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    hutang.setTglHutang(Function.getSystemDate());
                    if(controller.m==null){
                        hutang.setKodeMember("");
                        hutang.setNama(controller.namaField.getText());
                        hutang.setAlamat(controller.alamatField.getText());
                        hutang.setNoTelp(controller.noTelpField.getText());
                    }else{
                        hutang.setMember(controller.m);
                        hutang.setKodeMember(controller.m.getKodeMember());
                        hutang.setNama(controller.m.getNama());
                        hutang.setAlamat(controller.m.getAlamat());
                        hutang.setNoTelp(controller.m.getNoTelp());
                    }
                    double totalBerat = 0;
                    double totalNilaiJual = 0;
                    int noUrut = 1;
                    for(HutangDetail d : controller.listHutangDetail){
                        d.setNoUrut(noUrut);
                        noUrut = noUrut + 1;
                        totalBerat = totalBerat + d.getBerat();
                        totalNilaiJual = totalNilaiJual + d.getNilaiJual();
                    }
                    hutang.setListHutangDetail(controller.listHutangDetail);
                    hutang.setTotalBerat(totalBerat);
                    hutang.setMaksPinjaman(Double.parseDouble(controller.totalMaxPinjamanLabel.getText().replaceAll(",", "")));
                    hutang.setTotalHutang(Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", "")));
                    hutang.setLamaPinjam(0);
                    hutang.setBungaPersen(Double.parseDouble(controller.bungaPersenField.getText().replaceAll(",", "")));
                    hutang.setBungaKomp(Math.ceil(Double.parseDouble(controller.bungaRpField.getText().replaceAll(",", ""))/30/500)*500);
                    hutang.setBungaRp(Math.ceil(Double.parseDouble(controller.bungaRpField.getText().replaceAll(",", ""))/30/500)*500);
                    hutang.setKeterangan("");
                    hutang.setStatusHilang("false");
                    hutang.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                    hutang.setStatusLunas("false");
                    hutang.setTglLunas("2000-01-01 00:00:00");
                    hutang.setSalesLunas("");
                    hutang.setStatusBatal("false");
                    hutang.setTglBatal("2000-01-01 00:00:00");
                    hutang.setUserBatal("");
                    return Service.saveTerimaHutang(conCabang, hutang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            getHutang();
            String status = task.getValue();
            if(status.equals("true")){
                mainApp.closeDialog(mainApp.MainStage, stage);
                mainApp.showMessage(Modality.NONE, "Success", "Terima hutang berhasil disimpan");
                if(controller.printSuratHutangCheck.isSelected()){
                    try{
                        for(HutangDetail d : controller.listHutangDetail){
                            d.setHutangHead(hutang);
                        }
                        PrintOut po = new PrintOut();
                        po.printSuratHutang(controller.listHutangDetail);
                        po.printSuratHutangInternal(controller.listHutangDetail);
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
    private void detailHutang(HutangHead h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/TerimaHutang.fxml");
        TerimaHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailHutang(h);
    }
    private void batalHutang(HutangHead h){
        try{
            if(!h.getNoHutang().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Hutang tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal hutang "+h.getNoHutang()+" ?");
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
                                    controller.passwordField.getText(), "Batal Terima Hutang")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            h.setStatusBatal("true");
                                            h.setTglBatal(Function.getSystemDate());
                                            h.setUserBatal(controller.usernameField.getText());
                                            return Service.saveBatalTerimaHutang(conCabang, h);
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
                                        mainApp.showMessage(Modality.NONE, "Success", "Hutang berhasil dibatal");
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
    private void pelunasanHutang(HutangHead h){
        if(h.getStatusLunas().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Hutang sudah lunas");
        }else{
            Stage stage = new Stage();
            FXMLLoader loaderPembayaran = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PelunasanHutang.fxml");
            PelunasanHutangController controller = loaderPembayaran.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.noHutangField.setText(h.getNoHutang());
            controller.getHutang();
            controller.saveButton.setOnAction((ev) -> {
                if(controller.kodeSalesField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
                else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
                else if(controller.pembayaranPinjamanField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman masih kosong");
                else if(controller.pembayaranBungaField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran bunga masih kosong");
                else if(controller.bungaPersenBaruField.getText().equals("") || controller.bungaPersenBaruField.getText().equals("0"))
                    mainApp.showMessage(Modality.NONE, "Warning", "Bunga persen masih kosong");
                else if(Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", ""))>
                        Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", "")))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman melebihi total pinjaman");
                else{
                    double bungaKomp = Double.parseDouble(controller.bungaRpField.getText().replaceAll(",", ""));
                    double bayarBunga = Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", ""));
                    double bayarPinjaman = Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", ""));
                    double sisaPinjaman = Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", ""));
                    double bungaBaru = Double.parseDouble(controller.bungaPersenBaruField.getText().replaceAll(",", ""));
                    double bunga = 0;
                    for(BungaHutang b : controller.listBungaHutang){
                        if(b.getMinJumlahRp()<=sisaPinjaman && sisaPinjaman<=b.getMaxJumlahRp())
                            bunga = b.getBungaPersen();
                    }
                    if(bayarBunga<bungaKomp || bunga!=bungaBaru){
                        Stage child = new Stage();
                        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                        VerifikasiController verifikasiController = loader.getController();
                        verifikasiController.setMainApp(mainApp, stage, child);
                        verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                            if (keyEvent.getCode() == KeyCode.ENTER)  {
                                mainApp.closeDialog(stage, child);
                                if(Function.verifikasi(verifikasiController.usernameField.getText(), verifikasiController.passwordField.getText(),"Pelunasan Hutang")){
                                    savePelunasanHutang(controller, stage, h);
                                }else{
                                    mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                                }
                            }
                        });
                    }else{
                        savePelunasanHutang(controller, stage, h);
                    }
                }
            });
        }
    }
    private HutangHead hutangBaru;
    private void savePelunasanHutang(PelunasanHutangController controller, Stage stage, HutangHead h){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    h.setTglLunas(Function.getSystemDate());
                    h.setSalesLunas(Function.ceksales(controller.kodeSalesField.getText()));
                    h.setBungaRp(Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", "")));
                    h.setStatusLunas("true");
                    h.setListHutangDetail(controller.allDetail);
                    
                    hutangBaru = null;
                    if(Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", ""))>0){
                        hutangBaru = new HutangHead();
                        hutangBaru.setTglHutang(h.getTglLunas());
                        hutangBaru.setKodeMember(h.getKodeMember());
                        hutangBaru.setNama(h.getNama());
                        hutangBaru.setAlamat(h.getAlamat());
                        hutangBaru.setNoTelp(h.getNoTelp());
                        int noUrut = 1;
                        double totalBerat = 0;
                        double totalNilaiJual = 0;
                        hutangBaru.setListHutangDetail(controller.allDetail);
                        for(HutangDetail d : hutangBaru.getListHutangDetail()){
                            d.setNoUrut(noUrut);
                            d.setNilaiJual(ceil(d.getKategori().getHargaJual()*d.getBerat()/500)*500);
                            
                            noUrut = noUrut + 1;
                            totalBerat = totalBerat + d.getBerat();
                            totalNilaiJual = totalNilaiJual + d.getNilaiJual();
                        }
                        hutangBaru.setTotalBerat(totalBerat);
                        hutangBaru.setMaksPinjaman(totalNilaiJual*70/100);
                        hutangBaru.setTotalHutang(Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", "")));
                        hutangBaru.setLamaPinjam(0);
                        hutangBaru.setBungaPersen(Double.parseDouble(controller.bungaPersenBaruField.getText().replaceAll(",", "")));
                        hutangBaru.setBungaKomp(Math.ceil(Double.parseDouble(controller.bungaRpBaruField.getText().replaceAll(",", ""))/30/500)*500);
                        hutangBaru.setBungaRp(Math.ceil(Double.parseDouble(controller.bungaRpBaruField.getText().replaceAll(",", ""))/30/500)*500);
                        hutangBaru.setKeterangan("No Hutang Lama : "+h.getNoHutang()+"\n"
                                + "Pinjaman : "+rp.format(h.getTotalHutang())+"\n"
                                + "Cicil Pinjaman : "+rp.format(Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", "")))+"\n"
                                + "Bunga Dibayar : "+rp.format(Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", ""))));
                        hutangBaru.setStatusHilang("false");
                        hutangBaru.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                        hutangBaru.setStatusLunas("false");
                        hutangBaru.setTglLunas("2000-01-01 00:00:00");
                        hutangBaru.setSalesLunas("");
                        hutangBaru.setStatusBatal("false");
                        hutangBaru.setTglBatal("2000-01-01 00:00:00");
                        hutangBaru.setUserBatal("");
                    }
                    return Service.savePelunasanHutang(conCabang, h, hutangBaru);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            getHutang();
            String status = task.getValue();
            if(status.equals("true")){
                if(hutangBaru!=null){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Bayar bunga/cicil hutang berhasil disimpan");
                    if(controller.printSuratHutangCheckBox.isSelected()){
                        try{
                            for(HutangDetail d : controller.allDetail){
                                d.setHutangHead(hutangBaru);
                            }
                            PrintOut po = new PrintOut();
                            po.printSuratHutang(controller.allDetail);
                            po.printSuratHutangInternal(controller.allDetail);
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }else{
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Pelunasan hutang berhasil disimpan");
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
    private void detailPelunasanHutang(HutangHead h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PelunasanHutang.fxml");
        PelunasanHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailHutang(h);
    }
    private void printSuratHutang(HutangHead h){    
        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
            List<HutangDetail> listHutang = HutangDetailDAO.getAllByNoHutang(conCabang, h.getNoHutang());
            for(HutangDetail d : listHutang){
                d.setHutangHead(h);
            }
            PrintOut po = new PrintOut();
            po.printSuratHutang(listHutang);
            po.printSuratHutangInternal(listHutang);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
