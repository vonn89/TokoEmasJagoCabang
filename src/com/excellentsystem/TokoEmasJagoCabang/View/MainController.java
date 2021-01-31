package com.excellentsystem.TokoEmasJagoCabang.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.quickButton;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import java.net.InetAddress;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class MainController  {

    @FXML public Label title;
    @FXML private Label tglSystemLabel;
    
    @FXML public VBox menuPane;
    
    @FXML private Accordion accordion;
    
    @FXML private VBox userVbox;
    @FXML private TitledPane loginButton;
    @FXML private MenuButton logoutButton;
    @FXML private MenuButton ubahPasswordButton;
    
    @FXML private TitledPane dashboardPane;
    
    @FXML private TitledPane memberPane;
    
    @FXML private TitledPane barangPane;
    @FXML private VBox barangVbox;
    @FXML private MenuButton menuDataBarang;
    @FXML private MenuButton menuStokBarang;
    @FXML private MenuButton menuDataTerimaBarang;
    @FXML private MenuButton menuDataAmbilBarang;
    @FXML private MenuButton menuStokOpnameBarang;
    
    @FXML private TitledPane penjualanPane;
    @FXML private VBox penjualanVbox;
    @FXML private MenuButton menuDataPenjualan;
    @FXML private MenuButton menuDataPenjualanCabang;
    @FXML private MenuButton menuDataPemesanan;
    
    @FXML private TitledPane pembelianPane;
    @FXML private VBox pembelianVbox;
    @FXML private MenuButton menuDataPembelian;
    @FXML private MenuButton menuDataPembelianCabang;
    
    @FXML private TitledPane hutangPane;
    @FXML private VBox hutangVbox;
    @FXML private MenuButton menuDataTerimaHutang;
    @FXML private MenuButton menuDataPelunasanHutang;
    
    @FXML private TitledPane servisPane;
    @FXML private TitledPane keuanganPane;
    
    @FXML private TitledPane laporanPane;
    @FXML private VBox laporanVbox;
    @FXML private MenuButton menuLaporanMember;
    @FXML private MenuButton menuLaporanBarang;
    @FXML private MenuButton menuLaporanPenjualan;
    @FXML private MenuButton menuLaporanPembelian;
    @FXML private MenuButton menuLaporanHutang;
    @FXML private MenuButton menuLaporanServis;
    @FXML private MenuButton menuLaporanKeuangan;
    
    @FXML private TitledPane pengaturanPane;
    @FXML private VBox pengaturanVbox;
    @FXML private MenuButton menuPengaturanLainLain;
    @FXML private MenuButton menuDataKategoriBarang;
    @FXML private MenuButton menuDataKategoriTransaksi;
    @FXML private MenuButton menuDataSales;
    @FXML private MenuButton menuDataUser;
    @FXML private MenuButton menuDataCabang;
    @FXML private MenuButton menuTutupToko;
    private Timeline checkTglSystemTimeline;
    private Timeline checkConnectionTimeline;
    public Timeline refreshDashboardTimeline;
    
    @FXML private ComboBox<String> cabangCombo;
    private ObservableList<String> listKodeCabang = FXCollections.observableArrayList();
    private List<Cabang> listCabang = new ArrayList<>();
    @FXML private ImageView connectionStrengthImg;
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        try{
            this.mainApp = mainApp;
            menuPane.setPrefWidth(0);
            for(Node n : barangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : penjualanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pembelianVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : hutangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : laporanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pengaturanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : userVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            cabangCombo.setItems(listKodeCabang);
            getCabang();
            setUser();
            
//            showDashboard();
            mainApp.showQuickMenu();
            
            checkTglSystem();
            checkTglSystemTimeline = new Timeline(new KeyFrame(Duration.seconds(10), (ActionEvent actionEvent) -> {
                System.out.println(new Date()+" checkTglSystem");
                checkTglSystem();
            }));
            checkTglSystemTimeline.setCycleCount(Animation.INDEFINITE);
            checkTglSystemTimeline.play();
            
            checkConnection();
            checkConnectionTimeline = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent actionEvent) -> {
                System.out.println(new Date()+" checkConnection");
                checkConnection();
            }));
            checkConnectionTimeline.setCycleCount(Animation.INDEFINITE);
            checkConnectionTimeline.play();
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }    
    private void getCabang(){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            listKodeCabang.clear();
            listCabang = CabangDAO.getAll(con);
            for(Cabang c : listCabang){
                listKodeCabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().select(cabang.getKodeCabang());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void setCabang(){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            cabang = CabangDAO.get(con, cabangCombo.getSelectionModel().getSelectedItem());
            showDashboard();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void setUser() {
        if(user!=null){
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText(user.getKodeUser());
            for(Otoritas o : user.getOtoritas()){
                if(o.getJenis().equals("Dashboard") && o.isStatus()==false){
                    accordion.getPanes().remove(dashboardPane);
                    
                }else if(o.getJenis().equals("Member") && o.isStatus()==false){
                    accordion.getPanes().remove(memberPane);
                    
                }else if(o.getJenis().equals("Barang") && o.isStatus()==false){
                    accordion.getPanes().remove(barangPane);
                }else if(o.getJenis().equals("Data Barang")){
                    menuDataBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok Barang")){
                    menuStokBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Barang")){
                    menuDataTerimaBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Ambil Barang")){
                    menuDataAmbilBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok Opname Barang")){
                    menuStokOpnameBarang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Penjualan") && o.isStatus()==false){
                    accordion.getPanes().remove(penjualanPane);
                }else if(o.getJenis().equals("Penjualan Umum")){
                    menuDataPenjualan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan Cabang")){
                    menuDataPenjualanCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pemesanan")){
                    menuDataPemesanan.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Pembelian") && o.isStatus()==false){
                    accordion.getPanes().remove(pembelianPane);
                }else if(o.getJenis().equals("Pembelian Umum")){
                    menuDataPembelian.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian Cabang")){
                    menuDataPembelianCabang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Hutang") && o.isStatus()==false){
                    accordion.getPanes().remove(hutangPane);
                }else if(o.getJenis().equals("Terima Hutang")){
                    menuDataTerimaHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pelunasan Hutang")){
                    menuDataPelunasanHutang.setVisible(o.isStatus());
                                        
                }else if(o.getJenis().equals("Servis") && o.isStatus()==false){
                    accordion.getPanes().remove(servisPane);
                    
                }else if(o.getJenis().equals("Keuangan") && o.isStatus()==false){
                    accordion.getPanes().remove(keuanganPane);
                    
                }else if(o.getJenis().equals("Laporan") && o.isStatus()==false){
                    accordion.getPanes().remove(laporanPane);
                    
                }else if(o.getJenis().equals("Pengaturan") && o.isStatus()==false){
                    accordion.getPanes().remove(pengaturanPane);
                }else if(o.getJenis().equals("Ganti Cabang")){
                    if(o.isStatus())
                        cabangCombo.setDisable(false);
                    else
                        cabangCombo.setDisable(true);
                }else if(o.getJenis().equals("Data Kategori Barang")){
                    menuDataKategoriBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Kategori Transaksi")){
                    menuDataKategoriTransaksi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data User")){
                    menuDataUser.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Sales")){
                    menuDataSales.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Cabang")){
                    menuDataCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pengaturan Lain-lain")){
                    menuPengaturanLainLain.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Tutup Toko")){
                    menuTutupToko.setVisible(o.isStatus());
                }
            }
        }
    }
    @FXML
    private void startOrPauseCheckTanggal(){
        if(checkTglSystemTimeline.getStatus().equals(Animation.Status.RUNNING)){
            checkTglSystemTimeline.pause();
            tglSystemLabel.setTextFill(Paint.valueOf("RED"));
        }else if(checkTglSystemTimeline.getStatus().equals(Animation.Status.PAUSED)){
            checkTglSystemTimeline.play();
            tglSystemLabel.setTextFill(Paint.valueOf("WHITE"));
        }
    }
    public void checkTglSystem(){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            sistem = SistemDAO.get(con);
            tglSystemLabel.setText(tglNormal.format(tglBarang.parse(sistem.getTglSystem())));
            if(!sistem.getTglSystem().equals(tglBarang.format(new Date()))){
                if(mainApp.warning==null)
                    mainApp.showWarning("Warning!!", "Tanggal sistem dan tanggal komputer berbeda");
            }
        }catch(Exception e){
            mainApp.showWarning("Error", e.toString());
        }
    }
    private void checkConnection() {
        new Thread(() -> {
            try{
                InetAddress address = InetAddress.getByName(sistem.getIpServerPusat());
                if(address.isReachable(10)){
                    connectionStrengthImg.setImage(new Image(Main.class.getResourceAsStream("Resource/highConnection.png")));
                }else if(address.isReachable(50)){
                    connectionStrengthImg.setImage(new Image(Main.class.getResourceAsStream("Resource/mediumConnection.png")));
                }else if(address.isReachable(100)){
                    connectionStrengthImg.setImage(new Image(Main.class.getResourceAsStream("Resource/lowConnection.png")));
                }
            } catch (Exception e){
                connectionStrengthImg.setImage(new Image(Main.class.getResourceAsStream("Resource/noConnection.png")));
            }
        }).start();
    }
    @FXML
    public void showHideMenu(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curWidth = 220 * (1.0 - frac) ;
                menuPane.setPrefWidth(curWidth);
                memberPane.setExpanded(false);
                barangPane.setExpanded(false);
                penjualanPane.setExpanded(false);
                pembelianPane.setExpanded(false);
                hutangPane.setExpanded(false);
                servisPane.setExpanded(false);
                keuanganPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
                loginButton.setExpanded(false);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 220 * frac ;
              menuPane.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (menuPane.getPrefWidth()!=0) 
                hideSidebar.play();
            else 
                showSidebar.play();
        }
    }
    @FXML
    private void showQuickButton(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curPos = mainApp.screenSize.getWidth()-(230*(1-frac));
                quickButton.setX(curPos);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curPos = mainApp.screenSize.getWidth()-(230* frac) ;
                quickButton.setX(curPos);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (quickButton.getX()==mainApp.screenSize.getWidth()-230) 
                hideSidebar.play();
            else 
                showSidebar.play();
        }
    }
    public void setTitle(String x){
        title.setText(x);
    }
    @FXML
    private void logout(){
        mainApp.showLoginScene();
    }
    @FXML
    private void exit(){
        System.exit(0);
    }
    @FXML
    private void showUbahPassword(){
        mainApp.showUbahPassword();
    }     
    @FXML
    private void showPengaturanLainLain(){
        mainApp.showPengaturanLainLain();
    }
    @FXML
    private void showDataKategoriBarang(){
        mainApp.showDataKategoriBarang();
    }
    @FXML
    private void showDataKategoriTransaksi(){
        mainApp.showDataKategoriTransaksi();
    }
    @FXML
    private void showDataPegawai(){
        mainApp.showDataPegawai();
    }
    @FXML
    private void showDataUser(){
        mainApp.showDataUser();
    }
    @FXML
    private void showDataCabang(){
        mainApp.showDataCabang();
    }
    @FXML
    private void showTutupToko(){
        mainApp.showTutupToko();
    }
    @FXML
    private void showDashboard(){
        mainApp.showDashboard();
    }
    @FXML
    private void showDataMember(){
        mainApp.showDataMember();
    }
    @FXML
    private void showDataBarang(){
        mainApp.showDataBarang();
    }
    @FXML
    private void showDataTerimaBarang(){
        mainApp.showDataTerimaBarang();
    }
    @FXML
    private void showStokBarang(){
        mainApp.showStokBarang();
    }
    @FXML
    private void showDataAmbilBarang(){
        mainApp.showDataAmbilBarang();
    }
    @FXML 
    private void showStokOpnameBarang(){
        mainApp.showStokOpnameBarang();
    }
    @FXML
    private void showDataPenjualan(){
        mainApp.showDataPenjualan();
    }
    @FXML
    private void showDataPenjualanCabang(){
        mainApp.showDataPenjualanCabang();
    }
    @FXML
    private void showDataPemesanan(){
        mainApp.showDataPemesanan();
    }
    @FXML
    private void showDataPembelian(){
        mainApp.showDataPembelian();
    }
    @FXML
    private void showDataPembelianCabang(){
        mainApp.showDataPembelianCabang();
    }
    @FXML
    private void showDataTerimaHutang(){
        mainApp.showDataTerimaHutang();
    }
    @FXML
    private void showDataPelunasanHutang(){
        mainApp.showDataPelunasanHutang();
    }
    @FXML
    private void showDataServis(){
        mainApp.showDataServis();
    }
    @FXML
    private void showDataKeuangan(){
        mainApp.showDataKeuangan();
    }
    @FXML
    private void showLaporanBarang(){
        mainApp.showLaporanBarang();
    }
    @FXML
    private void showLaporanStokBarang(){
        mainApp.showLaporanStokBarang();
    }
    @FXML
    private void showLaporanTerimaBarang(){
        mainApp.showLaporanTerimaBarang();
    }
    @FXML
    private void showLaporanAmbilBalenan(){
        mainApp.showLaporanAmbilBalenan();
    }
    @FXML
    private void showLaporanHancurBarang(){
        mainApp.showLaporanHancurBarang();
    }
    @FXML
    private void showLaporanPenjualan(){
        mainApp.showLaporanPenjualan();
    }
    @FXML
    private void showLaporanBarangTerjual(){
        mainApp.showLaporanBarangTerjual();
    }
    @FXML
    private void showLaporanPembayaran(){
        mainApp.showLaporanPembayaran();
    }
    @FXML
    private void showLaporanPembayaranKekurangan(){
        mainApp.showLaporanPembayaranKekurangan();
    }
    @FXML
    private void showLaporanPenjualanCabang(){
        mainApp.showLaporanPenjualanCabang();
    }
    @FXML
    private void showLaporanPemesanan(){
        mainApp.showLaporanPemesanan();
    }
    @FXML
    private void showLaporanAmbilPemesanan(){
        mainApp.showLaporanAmbilPemesanan();
    }
    @FXML
    private void showLaporanPembelian(){
        mainApp.showLaporanPembelian();
    }
    @FXML
    private void showLaporanBarangDibeli(){
        mainApp.showLaporanBarangDibeli();
    }
    @FXML
    private void showLaporanPembelianCabang(){
        mainApp.showLaporanPembelianCabang();
    }
    @FXML
    private void showLaporanTerimaHutang(){
        mainApp.showLaporanTerimaHutang();
    }
    @FXML
    private void showLaporanPelunasanHutang(){
        mainApp.showLaporanPelunasanHutang();
    }
    @FXML
    private void showLaporanStokHutang(){
        mainApp.showLaporanStokHutang();
    }
    @FXML
    private void showLaporanStokHutangPeriode(){
        mainApp.showLaporanStokHutangPeriode();
    }
    @FXML
    private void showLaporanStokHutangDetail(){
        mainApp.showLaporanStokHutangDetail();
    }
    @FXML
    private void showLaporanTerimaServis(){
        mainApp.showLaporanTerimaServis();
    }
    @FXML
    private void showLaporanAmbilServis(){
        mainApp.showLaporanAmbilServis();
    }
    @FXML
    private void showLaporanKeuanganHarianSummary(){
        mainApp.showLaporanKeuanganHarianSummary();
    }
    @FXML
    private void showLaporanTransaksiSales(){
        mainApp.showLaporanTransaksiSales();
    }
}
