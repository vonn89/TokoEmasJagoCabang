/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoCabang.Model.Sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanAmbilBalenanController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanAmbilPemesananController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanAmbilServisController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanBarangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanBarangDibeliController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanBarangTerjualController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanHancurBarangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanKeuanganHarianSummaryController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPelunasanHutangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPembayaranController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPembayaranKekuranganController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPembelianCabangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPembelianController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPemesananController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPenjualanCabangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanPenjualanController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanStokBarangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanStokHutangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanStokHutangDetailController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanStokHutangPeriodeController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanTerimaBarangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanTerimaHutangController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanTerimaServisController;
import com.excellentsystem.TokoEmasJagoCabang.Report.LaporanTransaksiSalesController;
import com.excellentsystem.TokoEmasJagoCabang.View.DashboardController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataAmbilBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataKategoriBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataMemberController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPegawaiController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPelunasanHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPembelianCabangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPembelianController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPemesananController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPenjualanCabangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataPenjualanController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataServisController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataStokOpnameBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataTerimaBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataTerimaHutangController;
import com.excellentsystem.TokoEmasJagoCabang.View.DataUserController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DataCabangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DataKategoriTransaksiController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.KeuanganController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PengaturanLainLainController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.QuickButtonController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.TutupTokoController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.UbahPasswordController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.WarningController;
import com.excellentsystem.TokoEmasJagoCabang.View.LoginController;
import com.excellentsystem.TokoEmasJagoCabang.View.MainController;
import com.excellentsystem.TokoEmasJagoCabang.View.StokBarangController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author excellent
 */
public class Main extends Application{
    
    public Stage MainStage;
    public Stage message;
    public Stage loading;
    public static Stage quickButton;
    
    public Dimension screenSize;
    public BorderPane mainLayout;
    public MainController mainController;
    
    private double x = 0;
    private double y = 0;
    
    public static DecimalFormat gr = new DecimalFormat("###,##0.##");
    public static DecimalFormat rp = new DecimalFormat("###,##0");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSystem = new SimpleDateFormat("yyMMdd");
    public static DateFormat tglNormal = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    
    public static Sistem sistem;
    public static Cabang cabang;
    public static User user;
    public static List<User> allUser;
    public static List<Pegawai> allPegawai;
    
    public static long timeout = 0;
    public static final String version = "2.0.10";
    public static final String printerPenjualan = "EPSON LX-300";
    public static final String printerRR = "SLIP";
    public static final String printerBarcode = "SATO CG408";
    @Override
    public void start(Stage stage) {
        try{
            MainStage = stage;
            MainStage.setTitle("Toko Emas Jago Cabang");
            MainStage.setMaximized(true);
            MainStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            showLoginScene();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            System.exit(0);
        }
        
    }
    public void showLoginScene() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Login.fxml"));
            AnchorPane container = (AnchorPane) loader.load();
            
            Scene scene = new Scene(container);
            final Animation animationshow = new Transition() {
                { setCycleDuration(Duration.millis(1000)); }
                @Override
                protected void interpolate(double frac) {
                    MainStage.setOpacity(1-frac);
                }
            };
            animationshow.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
                final Animation animation = new Transition() {
                    { setCycleDuration(Duration.millis(1000)); }
                    @Override
                    protected void interpolate(double frac) {
                        MainStage.setOpacity(frac);
                    }
                };
                animation.play();
                MainStage.hide();
                MainStage.setScene(scene);
                MainStage.show();
            });
            animationshow.play();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    public void showMainScene(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Main.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            
            MainStage.hide();
            MainStage.setScene(scene);
            MainStage.show();
            
            
            mainController = loader.getController();
            mainController.setMainApp(this);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    public DataUserController showDataUser(){
        FXMLLoader loader = changeStage("View/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data User");
        return controller;
    }
    public DataPegawaiController showDataPegawai(){
        FXMLLoader loader = changeStage("View/DataPegawai.fxml");
        DataPegawaiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Sales");
        return controller;
    }
    public DataKategoriBarangController showDataKategoriBarang(){
        FXMLLoader loader = changeStage("View/DataKategoriBarang.fxml");
        DataKategoriBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Kategori Barang");
        return controller;
    }
    public PengaturanLainLainController showPengaturanLainLain(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/PengaturanLainLain.fxml");
        PengaturanLainLainController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public DataKategoriTransaksiController showDataKategoriTransaksi(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/DataKategoriTransaksi.fxml");
        DataKategoriTransaksiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public DataCabangController showDataCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/DataCabang.fxml");
        DataCabangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public TutupTokoController showTutupToko(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/TutupToko.fxml");
        TutupTokoController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public DashboardController showDashboard(){
        FXMLLoader loader = changeStage("View/Dashboard.fxml");
        DashboardController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Dashboard");
        return controller;
    }
    public DataMemberController showDataMember(){
        try{
            InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
            if(address.isReachable(1000)){
                FXMLLoader loader = changeStage("View/DataMember.fxml");
                DataMemberController controller = loader.getController();
                controller.setMainApp(this);
                setTitle("Member");
                return controller;
            }else{
                changeStage("View/Dialog/NoConnection.fxml");
                setTitle("Member");
                return null;
            }
        } catch (Exception e){
            changeStage("View/Dialog/NoConnection.fxml");
            setTitle("Member");
            return null;
        }
    }
    public DataBarangController showDataBarang(){
        FXMLLoader loader = changeStage("View/DataBarang.fxml");
        DataBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Barang");
        return controller;
    }
    public DataTerimaBarangController showDataTerimaBarang(){
        try{
            InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
            if(address.isReachable(1000)){
                FXMLLoader loader = changeStage("View/DataTerimaBarang.fxml");
                DataTerimaBarangController controller = loader.getController();
                controller.setMainApp(this);
                setTitle("Terima Barang");
                return controller;
            }else{
                changeStage("View/Dialog/NoConnection.fxml");
                setTitle("Terima Barang");
                return null;
            }
        } catch (Exception e){
            changeStage("View/Dialog/NoConnection.fxml");
            setTitle("Terima Barang");
            return null;
        }
    }
    public StokBarangController showStokBarang(){
        FXMLLoader loader = changeStage("View/StokBarang.fxml");
        StokBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Barang");
        return controller;
    }
    public DataAmbilBarangController showDataAmbilBarang(){
        try{
            InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
            if(address.isReachable(1000)){
                FXMLLoader loader = changeStage("View/DataAmbilBarang.fxml");
                DataAmbilBarangController controller = loader.getController();
                controller.setMainApp(this);
                setTitle("Ambil Barang");
                return controller;
            }else{
                changeStage("View/Dialog/NoConnection.fxml");
                setTitle("Ambil Barang");
                return null;
            }
        } catch (Exception e){
            changeStage("View/Dialog/NoConnection.fxml");
            setTitle("Ambil Barang");
            return null;
        }
    }
    public DataStokOpnameBarangController showStokOpnameBarang(){
        FXMLLoader loader = changeStage("View/DataStokOpnameBarang.fxml");
        DataStokOpnameBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Opname Barang");
        return controller;
    }
    public DataPenjualanController showDataPenjualan(){
        FXMLLoader loader = changeStage("View/DataPenjualan.fxml");
        DataPenjualanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Penjualan Umum");
        return controller;
    }
    public DataPenjualanCabangController showDataPenjualanCabang(){
        try{
            InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
            if(address.isReachable(1000)){
                FXMLLoader loader = changeStage("View/DataPenjualanCabang.fxml");
                DataPenjualanCabangController controller = loader.getController();
                controller.setMainApp(this);
                setTitle("Penjualan Cabang");
                return controller;
            }else{
                changeStage("View/Dialog/NoConnection.fxml");
                setTitle("Penjualan Cabang");
                return null;
            }
        } catch (Exception e){
            changeStage("View/Dialog/NoConnection.fxml");
            setTitle("Penjualan Cabang");
            return null;
        }
    }
    public DataPemesananController showDataPemesanan(){
        FXMLLoader loader = changeStage("View/DataPemesanan.fxml");
        DataPemesananController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pemesanan");
        return controller;
    }
    public DataPembelianController showDataPembelian(){
        FXMLLoader loader = changeStage("View/DataPembelian.fxml");
        DataPembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembelian Umum");
        return controller;
    }
    public DataPembelianCabangController showDataPembelianCabang(){
        try{
            InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
            if(address.isReachable(1000)){
                FXMLLoader loader = changeStage("View/DataPembelianCabang.fxml");
                DataPembelianCabangController controller = loader.getController();
                controller.setMainApp(this);
                setTitle("Pembelian Cabang");
                return controller;
            }else{
                changeStage("View/Dialog/NoConnection.fxml");
                setTitle("Pembelian Cabang");
                return null;
            }
        } catch (Exception e){
            changeStage("View/Dialog/NoConnection.fxml");
            setTitle("Pembelian Cabang");
            return null;
        }
    }
    public DataTerimaHutangController showDataTerimaHutang(){
        FXMLLoader loader = changeStage("View/DataTerimaHutang.fxml");
        DataTerimaHutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Terima Hutang");
        return controller;
    }
    public DataPelunasanHutangController showDataPelunasanHutang(){
        FXMLLoader loader = changeStage("View/DataPelunasanHutang.fxml");
        DataPelunasanHutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pelunasan Hutang");
        return controller;
    }
    public DataServisController showDataServis(){
        FXMLLoader loader = changeStage("View/DataServis.fxml");
        DataServisController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Servis");
        return controller;
    }
    public KeuanganController showDataKeuangan(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/Keuangan.fxml");
        KeuanganController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public UbahPasswordController showUbahPassword(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/UbahPassword.fxml");
        UbahPasswordController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public void showLaporanBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanBarang.fxml", "Laporan Barang Detail");
        LaporanBarangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanStokBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanStokBarang.fxml", "Laporan Stok Barang");
        LaporanStokBarangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanTerimaBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanTerimaBarang.fxml", "Laporan Terima Barang");
        LaporanTerimaBarangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanAmbilBalenan(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanAmbilBalenan.fxml", "Laporan Ambil Balenan");
        LaporanAmbilBalenanController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanHancurBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanHancurBarang.fxml", "Laporan Hancur Barang");
        LaporanHancurBarangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPenjualan.fxml", "Laporan Penjualan Umum");
        LaporanPenjualanController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanBarangTerjual(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanBarangTerjual.fxml", "Laporan Barang Terjual");
        LaporanBarangTerjualController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPembayaran(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPembayaran.fxml", "Laporan Pembayaran");
        LaporanPembayaranController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPembayaranKekurangan(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPembayaranKekurangan.fxml", "Laporan Pembayaran Kekurangan");
        LaporanPembayaranKekuranganController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPenjualanCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPenjualanCabang.fxml", "Laporan Penjualan Cabang");
        LaporanPenjualanCabangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPemesanan(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPemesanan.fxml", "Laporan Pemesanan");
        LaporanPemesananController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPembelian.fxml", "Laporan Pembelian Umum");
        LaporanPembelianController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanBarangDibeli(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanBarangDibeli.fxml", "Laporan Barang Dibeli");
        LaporanBarangDibeliController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPembelianCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPembelianCabang.fxml", "Laporan Pembelian Cabang");
        LaporanPembelianCabangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanTerimaHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanTerimaHutang.fxml", "Laporan Terima Hutang");
        LaporanTerimaHutangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPelunasanHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPelunasanHutang.fxml", "Laporan Pelunasan Hutang");
        LaporanPelunasanHutangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanStokHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanStokHutang.fxml", "Laporan Stok Hutang");
        LaporanStokHutangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanStokHutangPeriode(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanStokHutangPeriode.fxml", "Laporan Stok Hutang Periode");
        LaporanStokHutangPeriodeController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanStokHutangDetail(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanStokHutangDetail.fxml", "Laporan Stok Hutang Detail");
        LaporanStokHutangDetailController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanTerimaServis(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanTerimaServis.fxml", "Laporan Terima Servis");
        LaporanTerimaServisController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanAmbilServis(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanAmbilServis.fxml", "Laporan Ambil Servis");
        LaporanAmbilServisController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanKeuanganHarianSummary(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanKeuanganHarianSummary.fxml", "Laporan Keuangan Harian Summary");
        LaporanKeuanganHarianSummaryController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanTransaksiSales(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanTransaksiSales.fxml", "Laporan Transaksi Sales");
        LaporanTransaksiSalesController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanAmbilPemesanan(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanAmbilPemesanan.fxml", "Laporan Ambil Pemesanan");
        LaporanAmbilPemesananController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    private FXMLLoader showLaporan(Stage stage, String URL, String title){
        try{
            stage.setMaximized(true);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            BorderPane page = (BorderPane) loader.load();
            
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
            
            return loader;
        }catch(Exception e){
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }
    
    private void setTitle(String title){
        mainController.setTitle(title);
        if (mainController.menuPane.getPrefWidth()!=0) 
            mainController.showHideMenu();
    }
    public void checkTglSystem(){
        mainController.checkTglSystem();
    }
    public FXMLLoader changeStage(String URL){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();
            BorderPane border = (BorderPane) mainLayout.getCenter();
            border.setCenter(container);
            return loader;
        }catch(Exception e){
            e.printStackTrace();
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }
    public void showLoadingScreen(){
        try{
            if(loading!=null)
                loading.close();
            loading = new Stage();
            loading.initModality(Modality.APPLICATION_MODAL);
            loading.initOwner(MainStage);
            loading.initStyle(StageStyle.TRANSPARENT);
            loading.setOnCloseRequest((event) -> {
                event.consume();
            });
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/LoadingScreen.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            loading.setOpacity(0.7);
            loading.hide();
            loading.setScene(scene);
            loading.show();
            
            loading.setHeight(screenSize.getHeight());
            loading.setWidth(screenSize.getWidth());
            loading.setX((screenSize.getWidth() - loading.getWidth()) / 2);
            loading.setY((screenSize.getHeight() - loading.getHeight()) / 2);
        }catch(Exception e){
            showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void closeLoading(){
        loading.close();
    }
    public FXMLLoader showDialog(Stage owner, Stage dialog, String URL){
        try{
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            dialog.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = dialog.getX() - mouseEvent.getScreenX();
                y = dialog.getY() - mouseEvent.getScreenY();
            });
            scene.setOnMouseDragged((MouseEvent mouseEvent) -> {
                dialog.setX(mouseEvent.getScreenX() + x);
                dialog.setY(mouseEvent.getScreenY() + y);
            });
            owner.getScene().getRoot().setEffect(new ColorAdjust(0, 0, -0.5, -0.5));
            dialog.hide();
            dialog.setScene(scene);
            dialog.show();
            //set dialog on center parent
            dialog.setX((screenSize.getWidth() - dialog.getWidth()) / 2);
            dialog.setY((screenSize.getHeight() - dialog.getHeight()) / 2);
            return loader;
        }catch(IOException e){
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }
    public void closeDialog(Stage owner,Stage dialog){
        dialog.close();
        owner.getScene().getRoot().setEffect(new ColorAdjust(0,0,0,0));
        MainStage.requestFocus();
    }
    public Stage warning;
    public void showWarning(String title, String text){
        try{
            if(warning!=null)
                warning.close();
            warning = new Stage();
            warning.initModality(Modality.APPLICATION_MODAL);
            warning.initOwner(MainStage);
            warning.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/Warning.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            warning.hide();
            warning.setScene(scene);
            warning.show();
            //set dialog on center parent
            warning.setX((screenSize.getWidth() - warning.getWidth()) / 2);
            warning.setY((screenSize.getHeight() - warning.getHeight()) / 2);
            warning.setOnCloseRequest((event) -> {
                closeWarning();
            });
            WarningController controller = loader.getController();
            controller.setMainApp(this, title, text);
        }catch(IOException e){
            showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void closeWarning(){
        warning.close();
        warning=null;
        MainStage.requestFocus();
    }
    public MessageController showMessage(Modality modal, String type, String content){
        try{
            if(message!=null)
                message.close();
            message = new Stage();
            message.initModality(modal);
            message.initOwner(MainStage);
            message.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/Message.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            message.setX(screenSize.getWidth()-410);
            message.setY(screenSize.getHeight()-160);
            
            final Animation animation = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curPos = (screenSize.getHeight()-160) * (1-frac);
                    container.setTranslateY(curPos);
                }
            };
            animation.play();
            message.hide();
            message.setScene(scene);
            message.show();
            MessageController controller = loader.getController();
            controller.setMainApp(this,type,content);
            return controller;
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            return null;
        }
    }
    public void closeMessage(){
        message.close();
        MainStage.requestFocus();
    }
    public void showQuickMenu(){
        try{
            quickButton = new Stage();
            quickButton.initModality(Modality.NONE);
            quickButton.initOwner(MainStage);
            quickButton.initStyle(StageStyle.TRANSPARENT);

            quickButton.setOnCloseRequest((event) -> {
                event.consume();
            });
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/QuickButton.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);

            quickButton.setX(screenSize.getWidth()-230);
            quickButton.setY((screenSize.getHeight() / 2)-270);

            quickButton.hide();
            quickButton.setScene(scene);
            quickButton.show();
            QuickButtonController controller = loader.getController();
            controller.setMainApp(this);
            MainStage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
                if (event.getCode() == KeyCode.F1) 
                    controller.newPenjualan();
                if (event.getCode() == KeyCode.F2) 
                    controller.newPembelian();
                if (event.getCode() == KeyCode.F3) 
                    controller.newHutang();
                if (event.getCode() == KeyCode.F4) 
                    controller.cariHutang();
                if (event.getCode() == KeyCode.F5) 
                    controller.newServis();
            });
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
