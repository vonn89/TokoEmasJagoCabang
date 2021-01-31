/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriTransaksiDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SetoranCabang;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class KeuanganController {

    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> tipeKasirCombo;
    @FXML private TextField saldoAwalField;
    @FXML private TextField saldoAkhirField;
    
    @FXML private StackPane pane; 
    private GridPane gridPane; 
    
    private double saldoAwal = 0;
    private double saldoAkhir = 0;
    private List<KategoriTransaksi> listKategoriTransaksi = new ArrayList<>();
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu menu = new ContextMenu();
        MenuItem newTransaksi = new MenuItem("New Transaksi Keuangan");
        newTransaksi.setOnAction((ActionEvent event) -> {
            newTransaksi();
        });
        MenuItem setorUang = new MenuItem("Setor Uang Kas");
        setorUang.setOnAction((ActionEvent event) -> {
            setorUangKas();
        });
        MenuItem terimaUangBank = new MenuItem("Terima Uang Bank");
        terimaUangBank.setOnAction((ActionEvent event) -> {
            terimaUangBank();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        menu.getItems().addAll(newTransaksi, setorUang, terimaUangBank, refresh);
        pane.setOnContextMenuRequested((e) -> {
            menu.show(pane, e.getScreenX(), e.getScreenY());
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.8);
        stage.setWidth(mainApp.MainStage.getWidth()*0.4);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> allTipeKasir = FXCollections.observableArrayList();
        allTipeKasir.addAll("Kasir","RR");
        tipeKasirCombo.setItems(allTipeKasir);
        tipeKasirCombo.getSelectionModel().select("Kasir");
    }
    @FXML
    private void getKeuangan(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection con = KoneksiCabang.getConnection(cabang)){
                    listKategoriTransaksi.clear();
                    listKategoriTransaksi.addAll(KategoriTransaksiDAO.getAllByStatus(con, "true"));
                    saldoAwal = KeuanganDAO.getSaldoBefore(con, 
                            tipeKasirCombo.getSelectionModel().getSelectedItem(), "Kas", tglAwalPicker.getValue().toString());
                    saldoAkhir = KeuanganDAO.getSaldoAfter(con, 
                            tipeKasirCombo.getSelectionModel().getSelectedItem(), "Kas", tglAkhirPicker.getValue().toString());
                    List<Keuangan> listKeuangan = KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(con, 
                        tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), tipeKasirCombo.getSelectionModel().getSelectedItem(),
                        "Kas", "%", "%");
                    return listKeuangan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            saldoAwalField.setText(rp.format(saldoAwal));
            saldoAkhirField.setText(rp.format(saldoAkhir));
            allKeuangan.clear();
            allKeuangan.addAll(task.getValue());
            setGridPane();
        });
        task.setOnFailed((e) -> {task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setGridPane(){
        try {
            pane.getChildren().clear();
            gridPane = new GridPane();
            
            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(100, 100, 200, Priority.ALWAYS, HPos.RIGHT, true));
            
            List<Keuangan> group = new ArrayList<>();
            for(Keuangan k : allKeuangan){
                boolean status = true;
                for(Keuangan g : group){
                    if(k.getKategori().equals(g.getKategori())){
                        g.getListKeuangan().add(k);
                        g.setJumlahRp(g.getJumlahRp()+k.getJumlahRp());
                        status = false;
                    }
                }
                if(status){
                    Keuangan head = new Keuangan();
                    head.setNoKeuangan("");
                    head.setTglKeuangan("");
                    head.setTipeKasir(k.getTipeKasir());
                    head.setTipeKeuangan(k.getTipeKeuangan());
                    head.setKategori(k.getKategori());
                    head.setKeterangan("");
                    head.setJumlahRp(k.getJumlahRp());
                    head.setKodeSales("");
                    head.setListKeuangan(new ArrayList<>());
                    head.getListKeuangan().add(k);
                    group.add(head);
                }
            }
            int totalRows =  group.size() ;
            for(int i = 0 ; i<totalRows ; i++){
                gridPane.getRowConstraints().add(new RowConstraints(40,40,40));
                if(i%2==0)
                    addBackground(i);
            }
            int row = 0;
            row = addRow(group, "Penjualan Umum", row);
            row = addRow(group, "Poin", row);
            row = addRow(group, "Pembelian Umum", row);
            row = addRow(group, "Orang Kurang", row);
            row = addRow(group, "Orang Bayar", row);
            row = addRow(group, "Terima Pemesanan", row);
            row = addRow(group, "Ambil Pemesanan", row);
            row = addRow(group, "Batal Pemesanan", row);
            for(Keuangan k : group){
                if(k.getKategori().matches("Penjualan Cabang - .*")){
                    addHyperLinkText(k.getKategori(), 0, row, k);
                    addText(rp.format(k.getJumlahRp()), 1,row);
                    row = row + 1;
                }
            }
            for(Keuangan k : group){
                if(k.getKategori().matches("Pembelian Cabang - .*")){
                    addHyperLinkText(k.getKategori(), 0, row, k);
                    addText(rp.format(k.getJumlahRp()), 1,row);
                    row = row + 1;
                }
            }
            row = addRow(group, "Servis", row);
            row = addRow(group, "Pendaftaran Member", row);
            row = addRow(group, "Ganti Kartu Member", row);
            row = addRow(group, "Terima Hutang", row);
            row = addRow(group, "Hutang Lunas", row);
            row = addRow(group, "Hutang Bunga", row);
            for(Keuangan k : group){
                if(!k.getKategori().equals("Penjualan Umum") && !k.getKategori().equals("Poin") &&
                       !k.getKategori().equals("Orang Kurang") && !k.getKategori().equals("Orang Bayar") &&
                        !k.getKategori().equals("Terima Pemesanan") && !k.getKategori().equals("Ambil Pemesanan") &&
                        !k.getKategori().equals("Batal Pemesanan") && !k.getKategori().equals("Pembelian Umum") &&
                        !k.getKategori().equals("Servis") && !k.getKategori().equals("Pendaftaran Member") &&
                        !k.getKategori().equals("Ganti Kartu Member") && !k.getKategori().equals("Setor Uang Kas") &&
                        !k.getKategori().equals("Terima Uang Bank") && !k.getKategori().equals("Terima Hutang") && 
                        !k.getKategori().equals("Hutang Lunas") && !k.getKategori().equals("Hutang Bunga") &&
                        !k.getKategori().matches("Pembelian Cabang - .*") && !k.getKategori().matches("Penjualan Cabang - .*")){
                    addHyperLinkText(k.getKategori(), 0, row, k);
                    addText(rp.format(k.getJumlahRp()), 1,row);
                    row = row + 1;
                }
            }
            row = addRow(group, "Setor Uang Kas", row);
            row = addRow(group, "Terima Uang Bank", row);
            
            gridPane.setPadding(new Insets(5));
            pane.getChildren().add(gridPane);
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    private int addRow(List<Keuangan> group, String kategori, int row){
        for(Keuangan k : group){
            if(k.getKategori().equals(kategori)){
                addHyperLinkText(k.getKategori(), 0, row, k);
                addText(rp.format(k.getJumlahRp()), 1,row);
                row = row + 1;
            }
        }
        return row;
    }
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor6,-10%);");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addText(String text,int column, int row){
        if(text.startsWith("-")){
            Label label = new Label(text);
            label.setStyle("-fx-font-size:14;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill:seccolor1;");
            gridPane.add(label, column, row);
        }else{
            Label label = new Label(text);
            label.setStyle("-fx-font-size:14;"
                    + "-fx-font-weight: bold;");
            gridPane.add(label, column, row);
        }
    }
    private void addHyperLinkText(String text, int column, int row, Keuangan k){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setOnAction((e) -> {
            detailKeuangan(text, k);
        });
        gridPane.add(hyperlink, column, row);
    }
    
    private void newTransaksi(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/TransaksiKeuangan.fxml");
        TransaksiKeuanganController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setKategori(listKategoriTransaksi);
        controller.saveButton.setOnAction((event) -> {
            if(controller.tipeKasirCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe kasir belum dipilih");
            else if(controller.kategoriCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(controller.deskripsiField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "deskripsi masih kosong");
            else if(controller.jumlahRpField.getText().equals("0") || controller.jumlahRpField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            return Service.saveTransaksiKas(conCabang, controller.tipeKasirCombo.getSelectionModel().getSelectedItem(), 
                                    controller.kategoriCombo.getSelectionModel().getSelectedItem(), controller.deskripsiField.getText(), 
                                    Double.parseDouble(controller.jumlahRpField.getText().replaceAll(",", "")), controller.kodeSalesField.getText());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKeuangan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(stage ,child);
                        mainApp.showMessage(Modality.NONE, "Success", "Transaksi keuangan berhasil disimpan");
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
    private void setorUangKas(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/SetorUangKas.fxml");
        SetorUangKasController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.newSetor();
        controller.saveButton.setOnAction((event) -> {
            if(controller.tipeKasirCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe kasir belum dipilih");
            else if(controller.jumlahRpField.getText().equals("0") || controller.jumlahRpField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            else{
                Stage v = new Stage();
                FXMLLoader verifikasiLoader = mainApp.showDialog(child, v, "View/Dialog/Verifikasi.fxml");
                VerifikasiController verifikasiController = verifikasiLoader.getController();
                verifikasiController.setMainApp(mainApp, child, v);
                verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(child, v);
                        if(Function.verifikasi(verifikasiController.usernameField.getText(), 
                                verifikasiController.passwordField.getText(), "Setor Uang Kas")){

                            SetoranCabang s = new SetoranCabang();
                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            s.setNoSetor(SetoranCabangDAO.getId(conPusat));
                                            s.setTglSetor(Function.getSystemDate());
                                            s.setKodeCabang(cabang.getKodeCabang());
                                            s.setTipeKasir(controller.tipeKasirCombo.getSelectionModel().getSelectedItem());
                                            s.setJumlahRp(Double.parseDouble(controller.jumlahRpField.getText().replaceAll(",", "")));
                                            s.setKodeUser(verifikasiController.usernameField.getText());
                                            s.setStatusTerima("false");
                                            s.setTglTerima("2000-01-01 00:00:00");
                                            s.setUserTerima("");
                                            s.setStatusBatal("false");
                                            s.setTglBatal("2000-01-01 00:00:00");
                                            s.setUserBatal("");
                                            return Service.saveSetorUangKas(conPusat, conCabang, s);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((ev) -> {
                                mainApp.closeLoading();
                                getKeuangan();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.closeDialog(stage, child);
                                    mainApp.showMessage(Modality.NONE, "Success", "Setor uang kas berhasil di simpan");
                                    try{
                                        List<SetoranCabang> listSetor = new ArrayList<>();
                                        listSetor.add(s);
                                        PrintOut po = new PrintOut();
                                        po.printBuktiSetor(listSetor);
                                    }catch(Exception e){
                                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                    }
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
            }
        });
    }
    private void terimaUangBank(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/TerimaUangBank.fxml");
        TerimaUangBankController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.newTerimaUangBank();
        controller.saveButton.setOnAction((event) -> {
            if(controller.noTambahField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "No transaksi masih kosong");
            else{
                controller.getTambahUangCabang();
                
                Stage v = new Stage();
                FXMLLoader verifikasiLoader = mainApp.showDialog(child, v, "View/Dialog/Verifikasi.fxml");
                VerifikasiController verifikasiController = verifikasiLoader.getController();
                verifikasiController.setMainApp(mainApp, child, v);
                verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(child, v);
                        if(Function.verifikasi(verifikasiController.usernameField.getText(), 
                                verifikasiController.passwordField.getText(), "Terima Uang Bank")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            controller.tambahUangCabang.setStatusTerima("true");
                                            controller.tambahUangCabang.setTglTerima(Function.getSystemDate());
                                            controller.tambahUangCabang.setUserTerima(verifikasiController.usernameField.getText());
                                            return Service.saveTerimaUangBank(conPusat, conCabang, controller.tambahUangCabang);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getKeuangan();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.closeDialog(stage, child);
                                    mainApp.showMessage(Modality.NONE, "Success", "Terima uang bank berhasil di simpan");
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
            }
        });
    }
    
    private void detailKeuangan(String kategori, Keuangan k){
        if(kategori.equals("Penjualan Umum") || kategori.equals("Poin") || 
                kategori.equals("Orang Kurang")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPenjualan.fxml");
            DetailKeuanganPenjualanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), k.getListKeuangan(), kategori);
        }else if(kategori.equals("Orang Bayar")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPenjualan.fxml");
            DetailKeuanganPenjualanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getOrangBayar(k.getListKeuangan(), kategori);
        }else if(kategori.equals("Terima Pemesanan") || kategori.equals("Ambil Pemesanan") || kategori.equals("Batal Pemesanan")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPemesanan.fxml");
            DetailKeuanganPemesananController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getPemesanan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kategori);
        }else if(kategori.matches("Penjualan Cabang - .*")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPenjualanCabang.fxml");
            DetailKeuanganPenjualanCabangController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kategori);
        }else if(kategori.matches("Pembelian Cabang - .*")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPenjualanCabang.fxml");
            DetailKeuanganPenjualanCabangController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getPembelian(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kategori);
        }else if(kategori.equals("Pembelian Umum")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganPembelian.fxml");
            DetailKeuanganPembelianController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getPembelian(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        }else if(kategori.equals("Servis")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganServis.fxml");
            DetailKeuanganServisController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getServis(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        }else if(kategori.equals("Terima Hutang") || kategori.equals("Hutang Lunas") || kategori.equals("Hutang Bunga")){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuanganHutang.fxml");
            DetailKeuanganHutangController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getHutang(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kategori);
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailKeuangan.fxml");
            DetailKeuanganController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setKeuangan(k);
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem refresh = new MenuItem("Refresh");
            refresh.setOnAction((ActionEvent event) -> {
                controller.keuanganTable.refresh();
            });
            rowMenu.getItems().addAll(refresh);
            controller.keuanganTable.setContextMenu(rowMenu);
            controller.keuanganTable.setRowFactory(table -> {
                TableRow<Keuangan> row = new TableRow<Keuangan>() {
                    @Override
                    public void updateItem(Keuangan item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setContextMenu(rowMenu);
                        }else{
                            final ContextMenu rowMenu = new ContextMenu();
                            MenuItem batal = new MenuItem("Batal Transaksi");
                            batal.setOnAction((ActionEvent e) -> {
                                batalTransaksi(item, child);
                            });
                            MenuItem revisi = new MenuItem("Revisi Transaksi");
                            revisi.setOnAction((ActionEvent e) -> {
                                revisiTransaksi(item, child);
                            });
                            MenuItem detailSetor = new MenuItem("Detail Setor Uang Kas");
                            detailSetor.setOnAction((ActionEvent e) -> {
                                detailSetor(item, child);
                            });
                            MenuItem print = new MenuItem("Print Bukti Setor Uang Kas");
                            print.setOnAction((ActionEvent e) -> {
                                printBuktiSetor(item);
                            });
                            MenuItem batalSetor = new MenuItem("Batal Setor Uang Kas");
                            batalSetor.setOnAction((ActionEvent e) -> {
                                batalSetor(item, child);
                            });
                            MenuItem detailTerimaUang = new MenuItem("Detail Terima Uang Bank");
                            detailTerimaUang.setOnAction((ActionEvent e) -> {
                                detailTerimaUang(item, child);
                            });
                            MenuItem batalTerimaUang = new MenuItem("Batal Terima Uang Bank");
                            batalTerimaUang.setOnAction((ActionEvent e) -> {
                                batalTerimaUang(item, child);
                            });
                            MenuItem refresh = new MenuItem("Refresh");
                            refresh.setOnAction((ActionEvent e) -> {
                                controller.keuanganTable.refresh();
                            });
                            boolean status = false;
                            for(KategoriTransaksi k : listKategoriTransaksi){
                                if(item.getKategori().equals(k.getKodeTransaksi()))
                                    status = true;
                            }
                            if(status){
                                rowMenu.getItems().add(batal);
                                boolean statusRevisi = false;
                                for(Otoritas o : user.getOtoritas()){
                                    if(o.getJenis().equals("Revisi Transaksi"))
                                        statusRevisi = o.isStatus();
                                }
                                if(statusRevisi)
                                    rowMenu.getItems().add(revisi);
                            }
                            if(item.getKategori().equals("Setor Uang Kas")){
                                rowMenu.getItems().add(detailSetor);
                                rowMenu.getItems().add(print);
                                rowMenu.getItems().add(batalSetor);
                            }
                            if(item.getKategori().equals("Terima Uang Bank")){
                                rowMenu.getItems().add(detailTerimaUang);
                                rowMenu.getItems().add(batalTerimaUang);
                            }
                            rowMenu.getItems().add(refresh);
                            setContextMenu(rowMenu);
                        }
                    }
                };
                return row;
            });
        }
    }
    private void batalTransaksi(Keuangan k, Stage c){
        try{
            if(!k.getNoKeuangan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Transkasi tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal transaksi "+k.getKeterangan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(c, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, c, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(c, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Transaksi")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            return Service.saveBatalTransaksiKas(conCabang, k);
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getKeuangan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.closeDialog(stage, c);
                                        mainApp.showMessage(Modality.NONE, "Success", "Transaksi berhasil dibatal");
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
    private void revisiTransaksi(Keuangan k, Stage c){
        try{
            LocalDate twoweekbefore = LocalDate.parse(sistem.getTglSystem(), DateTimeFormatter.ISO_DATE).minusWeeks(2);
            LocalDate tglTransaksi = LocalDate.parse(k.getNoKeuangan().substring(7,13), DateTimeFormatter.ofPattern("yyMMdd"));
            if(tglTransaksi.isBefore(twoweekbefore)){
                mainApp.showMessage(Modality.NONE, "Warning", "Transkasi tidak dapat dibatal, karena sudah melebihi 2 minggu");
            }else{
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(c ,child, "View/Dialog/TransaksiKeuangan.fxml");
                TransaksiKeuanganController controller = loader.getController();
                controller.setMainApp(mainApp, c, child);
                controller.setKategori(listKategoriTransaksi);
                controller.setRevisiTransaksi(k);
                controller.saveButton.setOnAction((event) -> {
                    if(controller.tipeKasirCombo.getSelectionModel().getSelectedItem()==null)
                        mainApp.showMessage(Modality.NONE, "Warning", "Tipe kasir belum dipilih");
                    else if(controller.kategoriCombo.getSelectionModel().getSelectedItem()==null)
                        mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
                    else if(controller.deskripsiField.getText().equals(""))
                        mainApp.showMessage(Modality.NONE, "Warning", "deskripsi masih kosong");
                    else if(controller.jumlahRpField.getText().equals("0") || controller.jumlahRpField.getText().equals(""))
                        mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
                    else if(controller.kodeSalesField.getText().equals(""))
                        mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
                    else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                        mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
                    else{
                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                    return Service.saveRevisiKas(conCabang, k, 
                                            controller.kategoriCombo.getSelectionModel().getSelectedItem(), controller.deskripsiField.getText());
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((e) -> {
                            mainApp.closeLoading();
                            getKeuangan();
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.closeDialog(c ,child);
                                mainApp.closeDialog(stage, c);
                                mainApp.showMessage(Modality.NONE, "Success", "Transaksi keuangan berhasil disimpan");
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void detailSetor(Keuangan k, Stage c){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(c ,child, "View/Dialog/SetorUangKas.fxml");
        SetorUangKasController controller = loader.getController();
        controller.setMainApp(mainApp, c, child);
        controller.detailSetoran(k);
    }
    private void batalSetor(Keuangan k, Stage c){
        try{
            if(!k.getNoKeuangan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Setor uang kas tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal transaksi "+k.getKeterangan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(c, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, c, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(c, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Setor Uang Kas")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                                return Service.saveBatalSetorUangKas(conPusat, conCabang, k, controller.usernameField.getText());
                                            }
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getKeuangan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.closeDialog(stage, c);
                                        mainApp.showMessage(Modality.NONE, "Success", "Setor uang kas berhasil dibatal");
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
    private void detailTerimaUang(Keuangan k, Stage c){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(c ,child, "View/Dialog/TerimaUangBank.fxml");
        TerimaUangBankController controller = loader.getController();
        controller.setMainApp(mainApp, c, child);
        controller.detailTerimaUangBank(k);
    }
    private void batalTerimaUang(Keuangan k, Stage c){
        try{
            if(!k.getNoKeuangan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Terima uang bank tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal transaksi "+k.getKeterangan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(c, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, c, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(c, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Terima Uang Bank")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                                return Service.saveBatalTerimaUangBank(conPusat, conCabang, k, controller.usernameField.getText());
                                            }
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getKeuangan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.closeDialog(stage, c);
                                        mainApp.showMessage(Modality.NONE, "Success", "Terima uang bank berhasil dibatal");
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
    private void printBuktiSetor(Keuangan k){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<SetoranCabang> listSetor = new ArrayList<>();
            SetoranCabang s = SetoranCabangDAO.get(conPusat, k.getKeterangan());
            listSetor.add(s);
            PrintOut po = new PrintOut();
            po.printBuktiSetor(listSetor);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
