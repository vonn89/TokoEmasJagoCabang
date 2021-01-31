/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.LogMemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.LogMember;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanLogMemberController;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class LogMemberController {
    
    @FXML private TableView<LogMember> memberTable;
    @FXML private TableColumn<LogMember, String> tanggalColumn;
    @FXML private TableColumn<LogMember, String> kodeCabangColumn;
    @FXML private TableColumn<LogMember, String> kategoriColumn;
    @FXML private TableColumn<LogMember, String> keteranganColumn;
    @FXML private TableColumn<LogMember, Number> jumlahRpColumn;
    @FXML private TableColumn<LogMember, Number> jumlahPoinColumn;
    @FXML private TableColumn<LogMember, String> kodeSalesColumn;
    @FXML private Label kodeMemberLabel;
    @FXML private Label namaMemberLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalPembelianLabel;
    @FXML private Label totalPoinAkhirLabel;
    
    private Member member;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<LogMember> allMember = FXCollections.observableArrayList();
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTableCell(rp));
        jumlahPoinColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPoinProperty());
        jumlahPoinColumn.setCellFactory(col -> Function.getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print History Member");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getLogMember();
        });
        rm.getItems().add(print);
        rm.getItems().addAll(refresh);
        memberTable.setContextMenu(rm);
        memberTable.setRowFactory(ttv -> {
            TableRow<LogMember> row = new TableRow<LogMember>() {
                @Override
                public void updateItem(LogMember item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        ContextMenu rm = new ContextMenu();
                        MenuItem batalPendaftaran = new MenuItem("Batal Pendaftaran");
                        batalPendaftaran.setOnAction((ActionEvent e)->{
                            batalPendaftaran(item);
                        });
                        MenuItem batalGantiKartu = new MenuItem("Batal Ganti Kartu Member");
                        batalGantiKartu.setOnAction((ActionEvent e)->{
                            batalGantiKartu(item);
                        });
                        MenuItem detailPenjualan = new MenuItem("Detail Pembelian");
                        detailPenjualan.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            detailPembayaran(item);
                        });
                        MenuItem detailServis = new MenuItem("Detail Servis");
                        detailServis.setOnAction((ActionEvent e)->{
                            lihatDetailServis(item);
                        });
                        MenuItem print = new MenuItem("Print History Member");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getLogMember();
                        });
                        if(item.getKategori().equals("Pendaftaran Member"))
                            rm.getItems().add(batalPendaftaran);
                        if(item.getKategori().equals("Ganti Kartu Member"))
                            rm.getItems().add(batalGantiKartu);
                        if(item.getKategori().equals("Get Poin Pembelian")){
                            if(item.getKeterangan().startsWith(cabang.getKodeCabang()+"-PJ"))
                                rm.getItems().addAll(detailPenjualan, detailBayar);
                        }
                        if(item.getKategori().equals("Pembayaran Poin")){
                            if(item.getKeterangan().startsWith(cabang.getKodeCabang()+"-PJ"))
                                rm.getItems().addAll(detailPenjualan, detailBayar);
                            if(item.getKeterangan().startsWith(cabang.getKodeCabang()+"-SV"))
                                rm.getItems().add(detailServis);
                        }
                        rm.getItems().add(print);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        memberTable.setItems(allMember);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    } 
    public void setMember(Member m){
        member = m;
        kodeMemberLabel.setText(m.getKodeMember());
        namaMemberLabel.setText(m.getNama());
        getLogMember();
    } 
    @FXML
    private void getLogMember(){
        Task<List<LogMember>> task = new Task<List<LogMember>>() {
            @Override 
            public List<LogMember> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<LogMember> allLogMember = LogMemberDAO.getAllByDateAndCabangAndMemberAndKategoriAndSalesAndStatus(conPusat, 
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", kodeMemberLabel.getText(),"%","%","true");
                    return allLogMember;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allMember.clear();
            allMember.addAll(task.getValue());
            hitungTotal();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void hitungTotal(){
        double totalPembelian = 0;
        int totalPoinAkhir = 0;
        for(LogMember l : allMember){
            totalPoinAkhir = totalPoinAkhir + l.getJumlahPoin();
            if(l.getKategori().equals("Get Poin Pembelian"))
                totalPembelian = totalPembelian + l.getJumlahRp();
        }
        totalPembelianLabel.setText(rp.format(totalPembelian));
        totalPoinAkhirLabel.setText(rp.format(totalPoinAkhir));
    }
    private void batalPendaftaran(LogMember log){
        if(!log.getTanggal().substring(0, 10).equals(sistem.getTglSystem()))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, sudah berbeda tanggal");
        else if(log.getStatus().equals("false"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah dibatal");
        else if(member.getPoin()>0)
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena member masih memiliki poin");
        else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal pendaftaran "+log.getKodeMember()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();

                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(stage, child);
                        if(Function.verifikasi(controller.usernameField.getText(), 
                                controller.passwordField.getText(), "Batal Pendaftaran")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            return Service.saveBatalPendaftaranMember(conPusat, conCabang, member, controller.usernameField.getText());
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                close();
                                mainApp.showDataMember();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Pendaftaran member berhasil dibatal");
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
    private void batalGantiKartu(LogMember log){
        if(!log.getTanggal().substring(0, 10).equals(sistem.getTglSystem()))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, sudah berbeda tanggal");
        else if(log.getStatus().equals("false"))
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah dibatal");
        else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal Ganti Kartu "+log.getKodeMember()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();

                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(stage, child);
                        if(Function.verifikasi(controller.usernameField.getText(), 
                                controller.passwordField.getText(), "Batal Ganti Kartu")){

                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            String noRfidLama = log.getKeterangan().split(" -> ")[0].split(" - ")[0];
                                            String noKartuLama = log.getKeterangan().split(" -> ")[0].split(" - ")[1];
                                            return Service.saveBatalGantiKartuMember(conPusat, conCabang, member, noRfidLama, noKartuLama, controller.usernameField.getText());
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                close();
                                mainApp.showDataMember();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Pendaftaran member berhasil dibatal");
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
    private void lihatDetailPenjualan(LogMember log){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPenjualan(log.getKeterangan());
    }
    private void detailPembayaran(LogMember log){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailPembayaran.fxml");
        DetailPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.getPembayaran(log.getKeterangan());
        // batal pembayaran kekurangan
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            controller.pembayaranTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        controller.pembayaranTable.setContextMenu(rowMenu);
        controller.pembayaranTable.setRowFactory(table -> {
            TableRow<PembayaranPenjualan> row = new TableRow<PembayaranPenjualan>() {
                @Override
                public void updateItem(PembayaranPenjualan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            controller.pembayaranTable.refresh();
                        });
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }
    private void lihatDetailServis(LogMember l){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailServis.fxml");
        DetailServisController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailServis(l.getKeterangan());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void print(){
        try{
            Stage child = new Stage();
            child.setMaximized(true);
            child.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Report/LogMember.fxml"));
            BorderPane page = (BorderPane) loader.load();
            
            Scene scene = new Scene(page);
            child.setScene(scene);
            child.setTitle("Laporan History Member");
            child.show();
            
            LaporanLogMemberController controller = loader.getController();
            controller.setMember(member.getKodeMember());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
