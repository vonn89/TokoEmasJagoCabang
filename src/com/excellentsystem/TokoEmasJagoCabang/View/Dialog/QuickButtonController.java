/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class QuickButtonController {

    @FXML private Button penjualanButton; 
    @FXML private Button pembelianButton;
    @FXML private Button terimaHutangButton;
    @FXML private Button pelunasanHutangButton;
    @FXML private Button terimaServisButton;
    private Main mainApp;
    public void initialize(){
        penjualanButton.setOnMouseEntered((event) -> {
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                  final double curWidth = 140 * frac ;
                  penjualanButton.setPrefWidth(50+curWidth);
                }
            };
            showSidebar.play();
            penjualanButton.setText("Penjualan Umum");
        });
        penjualanButton.setOnMouseExited((event) -> {
            final Animation hideSidebar = new Transition() { 
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curWidth = 140 * (1.0 - frac) ;
                    penjualanButton.setPrefWidth(50+curWidth);
                }
            };
            hideSidebar.play();
            penjualanButton.setText("");
        });
        pembelianButton.setOnMouseEntered((event) -> {
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                  final double curWidth = 140 * frac ;
                  pembelianButton.setPrefWidth(50+curWidth);
                }
            };
            showSidebar.play();
            pembelianButton.setText("Pembelian Umum");
        });
        pembelianButton.setOnMouseExited((event) -> {
            final Animation hideSidebar = new Transition() { 
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curWidth = 140 * (1.0 - frac) ;
                    pembelianButton.setPrefWidth(50+curWidth);
                }
            };
            hideSidebar.play();
            pembelianButton.setText("");
        });
        terimaHutangButton.setOnMouseEntered((event) -> {
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                  final double curWidth = 140 * frac ;
                  terimaHutangButton.setPrefWidth(50+curWidth);
                }
            };
            showSidebar.play();
            terimaHutangButton.setText("Terima Hutang");
        });
        terimaHutangButton.setOnMouseExited((event) -> {
            final Animation hideSidebar = new Transition() { 
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curWidth = 140 * (1.0 - frac) ;
                    terimaHutangButton.setPrefWidth(50+curWidth);
                }
            };
            hideSidebar.play();
            terimaHutangButton.setText("");
        });
        pelunasanHutangButton.setOnMouseEntered((event) -> {
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                  final double curWidth = 140 * frac ;
                  pelunasanHutangButton.setPrefWidth(50+curWidth);
                }
            };
            showSidebar.play();
            pelunasanHutangButton.setText("Pelunasan Hutang");
        });
        pelunasanHutangButton.setOnMouseExited((event) -> {
            final Animation hideSidebar = new Transition() { 
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curWidth = 140 * (1.0 - frac) ;
                    pelunasanHutangButton.setPrefWidth(50+curWidth);
                }
            };
            hideSidebar.play();
            pelunasanHutangButton.setText("");
        });
        terimaServisButton.setOnMouseEntered((event) -> {
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                  final double curWidth = 140 * frac ;
                  terimaServisButton.setPrefWidth(50+curWidth);
                }
            };
            showSidebar.play();
            terimaServisButton.setText("Terima Servis");
        });
        terimaServisButton.setOnMouseExited((event) -> {
            final Animation hideSidebar = new Transition() { 
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curWidth = 140 * (1.0 - frac) ;
                    terimaServisButton.setPrefWidth(50+curWidth);
                }
            };
            hideSidebar.play();
            terimaServisButton.setText("");
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }
    @FXML 
    private void close(){
//        mainApp.closeQuickButton();
    }
    private boolean statusRewardPoin = false;
    @FXML
    public void newPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPenjualan();
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.listPenjualanDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data penjualan masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                Stage child = new Stage();
                FXMLLoader loaderPembayaran = mainApp.showDialog(stage ,child, "View/Dialog/Pembayaran.fxml");
                PembayaranController controllerPembayaran = loaderPembayaran.getController();
                controllerPembayaran.setMainApp(mainApp, stage, child);
                controllerPembayaran.setTotalPenjualan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")),
                        controller.m);
                controllerPembayaran.saveButton.setOnAction((ev) -> {
                    if(controllerPembayaran.sisaPembayaranLabel.getText().equals("Sisa Pembayaran")){
                        mainApp.showMessage(Modality.NONE, "Warning", "Masih ada sisa pembayaran");
                    }else{
                        PenjualanHead p = new PenjualanHead();
                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                    p.setNoPenjualan("");
                                    p.setTglPenjualan(Function.getSystemDate());
                                    if(controller.m==null){
                                        p.setKodeMember("");
                                        p.setNama(controller.namaField.getText());
                                        p.setAlamat(controller.alamatField.getText());
                                        p.setNoTelp(controller.noTelpField.getText());
                                    }else{
                                        p.setMember(controller.m);
                                        p.setKodeMember(controller.m.getKodeMember());
                                        p.setNama(controller.m.getNama());
                                        p.setAlamat(controller.m.getAlamat());
                                        p.setNoTelp(controller.m.getNoTelp());
                                    }
                                    double totalBerat = 0;
                                    double totalHarga = 0;
                                    double totalOngkos = 0;
                                    int noUrut = 1;
                                    for(PenjualanDetail d : controller.listPenjualanDetail){
                                        d.setNoUrut(noUrut);
                                        noUrut = noUrut + 1;
                                        totalBerat = totalBerat + d.getBerat();
                                        totalHarga = totalHarga + d.getHargaJual();
                                        totalOngkos = totalOngkos + d.getOngkos();
                                    }
                                    p.setListPenjualanDetail(controller.listPenjualanDetail);
                                    p.setTotalBerat(totalBerat);
                                    p.setTotalHarga(totalHarga);
                                    p.setTotalOngkos(totalOngkos);
                                    p.setGrandtotal(totalHarga+totalOngkos);
                                    double pembayaran = 0;
                                    for(PembayaranPenjualan pp : controllerPembayaran.listPembayaran){
                                        pp.setTglPembayaran(p.getTglPenjualan());
                                        pp.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                                        pp.setStatus("true");
                                        pp.setTglBatal("2000-01-01 00:00:00");
                                        pp.setUserBatal("");
                                        
                                        pembayaran = pembayaran + pp.getJumlahPembayaran();
                                        
                                        if(pp.getJenisPembayaran().equals("Reward Poin"))
                                            statusRewardPoin = true;
                                    }
                                    p.setListPembayaran(controllerPembayaran.listPembayaran);
                                    p.setPembayaran(pembayaran);
                                    p.setSisaPembayaran(totalHarga+totalOngkos-pembayaran);
                                    p.setKeterangan(controller.keteranganField.getText());
                                    p.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                                    p.setStatus("true");
                                    p.setTglBatal("2000-01-01 00:00:00");
                                    p.setUserBatal("");
                                    if(p.getMember()!=null || statusRewardPoin){
                                        try(Connection conPusat = KoneksiPusat.getConnection()){
                                            return Service.savePenjualanPoin(conPusat, conCabang, p);
                                        }
                                    }else
                                        return Service.savePenjualan(conCabang, p);
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((eve) -> {
                            mainApp.closeLoading();
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.closeDialog(stage, child);
                                mainApp.closeDialog(mainApp.MainStage, stage);
                                mainApp.showMessage(Modality.NONE, "Success", "Penjualan berhasil disimpan");
                                if(controller.printSuratPenjualanCheck.isSelected()){
                                    try{
                                        PrintOut po = new PrintOut();
                                        //print surat penjualan
                                        po.printSuratPenjualan(p);
                                        //print surat kurang bayar
                                        if(p.getSisaPembayaran()!=0){
                                            for(PenjualanDetail d : p.getListPenjualanDetail()){
                                                d.setPenjualanHead(p);
                                            }
                                            po.printSuratKurangBayar(p.getListPenjualanDetail());
                                        }
                                        //print surat reward poin
                                        if(statusRewardPoin){
                                            List<PembayaranPenjualan> pembayaranPoin = new ArrayList<>();
                                            for(PembayaranPenjualan pp : controllerPembayaran.listPembayaran){
                                                if(pp.getJenisPembayaran().equals("Reward Poin")){
                                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                                        pp.setMember(MemberDAO.get(conPusat, pp.getKeterangan()));
                                                        pembayaranPoin.add(pp);
                                                    }
                                                }
                                            }
                                            po.printSlipPembayaranPoin(pembayaranPoin);
                                        }
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
        });
    }
    @FXML
    public void newPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembelian();
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.listPembelianDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data pembelian masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else{
                PembelianHead p = new PembelianHead();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            p.setNoPembelian("");
                            p.setTglPembelian(Function.getSystemDate());
                            if(controller.m==null){
                                p.setKodeMember("");
                                p.setNama(controller.namaField.getText());
                                p.setAlamat(controller.alamatField.getText());
                                p.setNoTelp(controller.noTelpField.getText());
                            }else{
                                p.setMember(controller.m);
                                p.setKodeMember(controller.m.getKodeMember());
                                p.setNama(controller.m.getNama());
                                p.setAlamat(controller.m.getAlamat());
                                p.setNoTelp(controller.m.getNoTelp());
                            }
                            double totalBeratKotor = 0;
                            double totalBeratBersih = 0;
                            double totalBeratPersen = 0;
                            double totalHarga = 0;
                            int noUrut = 1;
                            for(PembelianDetail d : controller.listPembelianDetail){
                                d.setNoUrut(noUrut);
                                noUrut = noUrut + 1;
                                totalBeratKotor = totalBeratKotor + d.getBeratKotor();
                                totalBeratBersih = totalBeratBersih + d.getBeratBersih();
                                totalBeratPersen = totalBeratPersen + d.getBeratPersen();
                                totalHarga = totalHarga + d.getHargaBeli();
                            }
                            p.setListPembelianDetail(controller.listPembelianDetail);
                            p.setTotalBeratKotor(totalBeratKotor);
                            p.setTotalBeratBersih(totalBeratBersih);
                            p.setTotalBeratPersen(totalBeratPersen);
                            p.setTotalPembelian(totalHarga);
                            p.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePembelian(conCabang, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((ev) -> {
                    mainApp.closeLoading();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian berhasil disimpan");
                        if(controller.printSuratPembelianCheck.isSelected()){
                            try{
                                for(PembelianDetail d : controller.listPembelianDetail){
                                    d.setPembelianHead(p);
                                }
                                PrintOut po = new PrintOut();
                                po.printBuktiPembelian(controller.listPembelianDetail);
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
    @FXML
    public void newHutang(){
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
    public void saveTerimaHutang(TerimaHutangController controller, Stage stage){
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
    @FXML
    public void cariHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/CariHutang.fxml");
        CariHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }
    @FXML
    public void newServis(){
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
                            return Service.saveTerimaServis(conCabang, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((ev) -> {
                    mainApp.closeLoading();
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
}
