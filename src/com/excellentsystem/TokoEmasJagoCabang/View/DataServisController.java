/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.ServisDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.AmbilServisController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailServisController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.TerimaServisController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
public class DataServisController  {

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
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> statusCombo;
    private Main mainApp;   
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
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusYears(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Terima Servis");
        addNew.setOnAction((ActionEvent e) -> {
            newServis();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getServis();
        });
        rowMenu.getItems().addAll(addNew, refresh);
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
                        MenuItem addNew = new MenuItem("New Terima Servis");
                        addNew.setOnAction((ActionEvent e) -> {
                            newServis();
                        });
                        MenuItem detail = new MenuItem("Detail Servis");
                        detail.setOnAction((ActionEvent e) -> {
                            detailServis(item);
                        });
                        MenuItem ambil = new MenuItem("Ambil Servis");
                        ambil.setOnAction((ActionEvent e) -> {
                            ambilServis(item);
                        });
                        MenuItem batalTerima = new MenuItem("Batal Terima Servis");
                        batalTerima.setOnAction((ActionEvent e) -> {
                            batalTerimaServis(item);
                        });
                        MenuItem batalAmbil = new MenuItem("Batal Ambil Servis");
                        batalAmbil.setOnAction((ActionEvent e) -> {
                            batalAmbilServis(item);
                        });
                        MenuItem print = new MenuItem("Print Surat Servis");
                        print.setOnAction((ActionEvent e) -> {
                            printServis(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getServis();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        if(item.getStatusAmbil().equals("false") && item.getStatusBatal().equals("false")){
                            rowMenu.getItems().add(ambil);
                            rowMenu.getItems().add(batalTerima);
                            rowMenu.getItems().add(print);
                        }
                        if(item.getStatusAmbil().equals("true") && item.getStatusBatal().equals("false")){
                            rowMenu.getItems().add(batalAmbil);
                        }
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
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.addAll("Belum Diambil","Sudah Diambil" ,"Semua");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Belum Diambil");
        getServis();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getServis(){
        Task<List<Servis>> task = new Task<List<Servis>>() {
            @Override 
            public List<Servis> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    String status = "%";
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Diambil")){
                        status = "false";
                    }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Sudah Diambil")){
                        status = "true";
                    }
                    List<Servis> listServis = ServisDAO.getAllByDateAndStatus(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                        status, "false");
                    return listServis;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Diambil")){
                tglAmbilColumn.setVisible(false);
                salesAmbilColumn.setVisible(false);
                jumlahPembayaranColumn.setVisible(false);
            }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Sudah Diambil")){
                tglAmbilColumn.setVisible(true);
                salesAmbilColumn.setVisible(true);
                jumlahPembayaranColumn.setVisible(true);
            }else{
                tglAmbilColumn.setVisible(true);
                salesAmbilColumn.setVisible(true);
                jumlahPembayaranColumn.setVisible(true);
            }
            allServis.clear();
            allServis.addAll(task.getValue());
            servisTable.refresh();
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
    private void newServis(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/TerimaServis.fxml");
        TerimaServisController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setServis();
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.namaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama pelanggan masih kosong");
            else if(controller.alamatField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Alamat pelanggan masih kosong");
            else if(controller.namaBarangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            else if(controller.beratField.getText().equals("0") || controller.beratField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(controller.kategoriServisField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori servis masih kosong");
            else if(controller.salesTerimaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.salesTerimaField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Servis s = new Servis();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            s.setNoServis("");
                            s.setTglServis(Function.getSystemDate());
                            if(controller.m==null){
                                s.setKodeMember("");
                                s.setNama(controller.namaField.getText());
                                s.setAlamat(controller.alamatField.getText());
                                s.setNoTelp(controller.noTelpField.getText());
                            }else{
                                s.setKodeMember(controller.m.getKodeMember());
                                s.setNama(controller.m.getNama());
                                s.setAlamat(controller.m.getAlamat());
                                s.setNoTelp(controller.m.getNoTelp());
                            }
                            s.setNamaBarang(controller.namaBarangField.getText());
                            s.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            s.setKategoriServis(controller.kategoriServisField.getText());
                            s.setJumlahPembayaran(0);
                            s.setJenisPembayaran("");
                            s.setKeteranganPembayaran("");
                            s.setKodeSales(Function.ceksales(controller.salesTerimaField.getText()));
                            s.setStatusAmbil("false");
                            s.setTglAmbil(controller.tglAmbilPicker.getValue().toString()+" 00:00:00");
                            s.setSalesAmbil("");
                            s.setStatusBatal("false");
                            s.setTglBatal("2000-01-01 00:00:00");
                            s.setUserBatal("");
                            System.out.println(s.getTglAmbil());
                            return Service.saveTerimaServis(conCabang, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((ev) -> {
                    mainApp.closeLoading();
                    getServis();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Terima servis berhasil disimpan");
                        if(controller.printSuratServisCheck.isSelected()){
                            try{
                                List<Servis> listServis = new ArrayList<>();
                                listServis.add(s);
                                PrintOut po = new PrintOut();
                                po.printSuratServis(listServis);
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
    private void detailServis(Servis s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailServis.fxml");
        DetailServisController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailServis(s.getNoServis());
    }
    private void ambilServis(Servis s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AmbilServis.fxml");
        AmbilServisController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setServis(s);
        controller.saveButton.setOnAction((event) -> {
            if(controller.salesAmbilField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.salesAmbilField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            if(controller.cashButton.isSelected()){
                                s.setJumlahPembayaran(Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")));
                                s.setJenisPembayaran("Cash");
                                s.setKeteranganPembayaran("");
                                s.setStatusAmbil("true");
                                s.setTglAmbil(Function.getSystemDate());
                                s.setSalesAmbil(controller.salesAmbilField.getText());
                                return Service.saveAmbilServis(conCabang, s);
                            }else if(controller.rewardPoinButton.isSelected()){
                                if(controller.member==null){
                                    return "Member belum dipilih";
                                }else{
                                    int poin = controller.member.getPoin();
                                    if(poin<Integer.parseInt(controller.poinDigunakanField.getText().replaceAll(",", ""))){
                                        return "Poin yang akan digunakan melebih dari poin yang tersisa";
                                    }else{
                                        s.setJumlahPembayaran(Double.parseDouble(controller.jumlahRpDigunakanField.getText().replaceAll(",", "")));
                                        s.setJenisPembayaran("Reward Poin");
                                        s.setKeteranganPembayaran(controller.member.getKodeMember());
                                        s.setStatusAmbil("true");
                                        s.setTglAmbil(Function.getSystemDate());
                                        s.setSalesAmbil(controller.salesAmbilField.getText());
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            return Service.saveAmbilServisPoin(conPusat, conCabang, s);
                                        }
                                    }
                                }
                            }else{
                                return "Pembayaran belum dipilih";
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getServis();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Ambil servis berhasil disimpan");
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
    private void batalTerimaServis(Servis s){
        if(s.getStatusAmbil().equals("true"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah diambil");
        else if(s.getStatusBatal().equals("true"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah dibatal");
        else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal terima servis "+s.getNoServis()+" ?");
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
                                controller.passwordField.getText(), "Batal Terima Servis")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                        s.setStatusBatal("true");
                                        s.setTglBatal(Function.getSystemDate());
                                        s.setUserBatal(controller.usernameField.getText());
                                        return Service.saveBatalTerimaServis(conCabang, s);
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getServis();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Terima servis berhasil dibatal");
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
    private void batalAmbilServis(Servis s){
        if(s.getStatusAmbil().equals("false"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena belum diambil/sudah pernah di batal ambil");
        else if(s.getStatusBatal().equals("true"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah dibatal");
        else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal ambil servis "+s.getNoServis()+" ?");
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
                                controller.passwordField.getText(), "Batal Ambil Servis")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                        if(s.getJenisPembayaran().equals("Reward Poin")){
                                            try(Connection conPusat = KoneksiPusat.getConnection()){
                                                return Service.saveBatalAmbilServisPoin(conPusat, conCabang, s, controller.usernameField.getText());
                                            }
                                        }else
                                            return Service.saveBatalAmbilServis(conCabang, s);
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getServis();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Ambil servis berhasil dibatal");
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
    private void printServis(Servis s){
        try{
            List<Servis> listServis = new ArrayList<>();
            listServis.add(s);
            PrintOut po = new PrintOut();
            po.printSuratServis(listServis);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
