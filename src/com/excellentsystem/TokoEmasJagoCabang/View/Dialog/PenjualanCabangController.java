/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PenjualanCabangController  {

    @FXML private TableView<PenjualanAntarCabangDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanAntarCabangDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, Number> hargaColumn;
    
    @FXML private GridPane gridPane;
    @FXML private TextField noPenjualanField;
    @FXML private TextField tglPenjualanField;
    @FXML public TextField kodeSalesField;
    @FXML public ComboBox<String> kodeCabangCombo;
    
    @FXML private VBox addBarangVbox;
    @FXML private TextField kodeBarcodeField;
    @FXML private TextField namaBarangField;
    @FXML private TextField beratField;
    @FXML private TextField hargaField;
    
    @FXML public CheckBox printSuratPenjualanCabangCheck;
    @FXML private TextField totalPenjualanField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Barang b;
    private ObservableList<String> listCabang = FXCollections.observableArrayList();
    public ObservableList<PenjualanAntarCabangDetail> listPenjualanAntarCabangDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cari = new MenuItem("Cari Barang");
        cari.setOnAction((ActionEvent event) -> {
            searchBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanDetailTable.refresh();
        });
        rowMenu.getItems().addAll(cari, refresh);
        penjualanDetailTable.setContextMenu(rowMenu);
        penjualanDetailTable.setRowFactory(table -> {
            TableRow<PenjualanAntarCabangDetail> row = new TableRow<PenjualanAntarCabangDetail>(){
                @Override
                public void updateItem(PenjualanAntarCabangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cari = new MenuItem("Cari Barang");
                        cari.setOnAction((ActionEvent event) -> {
                            searchBarang();
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            removeBarang(item);
                        });
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            penjualanDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rowMenu.getItems().addAll(cari, hapus);
                        rowMenu.getItems().addAll(detail, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        Function.setNumberField(hargaField, rp);
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals("")){
                    kodeBarcodeField.requestFocus();
                    kodeBarcodeField.selectAll();
                }
            }
        });
        kodeBarcodeField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                getBarang();
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        penjualanDetailTable.setItems(listPenjualanAntarCabangDetail);
        kodeCabangCombo.setItems(listCabang);
    }
    public void setPenjualan(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        listCabang.clear();
                        for(Cabang c : CabangDAO.getAll(conCabang)){
                            if(!c.getKodeCabang().equals(cabang.getKodeCabang()))
                                listCabang.addAll(c.getKodeCabang());
                        }
                    }
                    return PenjualanAntarCabangHeadDAO.getId(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noPenjualanField.setText(task.getValue());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
                kodeCabangCombo.getSelectionModel().clearSelection();
                kodeSalesField.requestFocus();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setDetailPenjualan(PenjualanAntarCabangHead p){
        Task<List<PenjualanAntarCabangDetail>> task = new Task<List<PenjualanAntarCabangDetail>>() {
            @Override 
            public List<PenjualanAntarCabangDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PenjualanAntarCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualan());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                p.setListPenjualanAntarCabangDetail(task.getValue());
                
                kodeSalesField.setDisable(true);
                kodeCabangCombo.setDisable(true);
                addBarangVbox.setVisible(false);
                printSuratPenjualanCabangCheck.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                for(MenuItem m : penjualanDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Cari Barang"))
                        penjualanDetailTable.getContextMenu().getItems().remove(m);
                }

                noPenjualanField.setText(p.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                kodeCabangCombo.getSelectionModel().select(p.getCabangTujuan());
                kodeSalesField.setText(p.getKodeSales());
                listPenjualanAntarCabangDetail.addAll(p.getListPenjualanAntarCabangDetail());
                totalPenjualanField.setText(rp.format(p.getTotalHarga()));
                gridPane.getRowConstraints().get(6).setMinHeight(0);
                gridPane.getRowConstraints().get(6).setPrefHeight(0);
                gridPane.getRowConstraints().get(6).setMaxHeight(0);
                gridPane.getRowConstraints().get(9).setMinHeight(0);
                gridPane.getRowConstraints().get(9).setPrefHeight(0);
                gridPane.getRowConstraints().get(9).setMaxHeight(0);
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        boolean status = false;
        for(PenjualanAntarCabangDetail d : listPenjualanAntarCabangDetail){
            if(d.getKodeBarcode().equals(kodeBarcodeField.getText()))
                status = true;
        }
        if(status){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode sudah diinput");
            b = null;
            namaBarangField.setText("");
            beratField.setText("0");
            hargaField.setText("0");
            kodeBarcodeField.selectAll();
        }else{
            Task<Barang> task = new Task<Barang>() {
                @Override 
                public Barang call() throws Exception{
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        Barang b = BarangDAO.get(conCabang, kodeBarcodeField.getText());
                        if(b!=null){
                            b.setKategori(KategoriDAO.get(conCabang, b.getKodeKategori()));
                            b.setJenis(JenisDAO.get(conCabang, b.getKodeJenis()));
                        }
                        return b;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                b = task.getValue();
                if(b==null){
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode tidak ditemukan");
                    namaBarangField.setText("");
                    beratField.setText("0");
                    hargaField.setText("0");
                    kodeBarcodeField.selectAll();
                }else if(b.getStatus().equals("false")){
                    mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah terjual/hancur");
                    b = null;
                    namaBarangField.setText("");
                    beratField.setText("0");
                    hargaField.setText("0");
                    kodeBarcodeField.selectAll();
                }else{
                    namaBarangField.setText(b.getNamaBarang());
                    beratField.setText(gr.format(b.getBerat()));
                    hargaField.setText(rp.format(ceil(b.getKategori().getHargaJual()*b.getBerat()*95/100/500)*500));
                    hargaField.requestFocus();
                    hargaField.selectAll();
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void setBarang(){
        if(b==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode belum di scan");
            kodeBarcodeField.selectAll();
        }else if(b.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah terjual/hancur");
            kodeBarcodeField.selectAll();
        }else{
            namaBarangField.setText(b.getNamaBarang());
            beratField.setText(gr.format(b.getBerat()));
            double hargaKomp = ceil(b.getKategori().getHargaJual()*b.getBerat()*95/100/500)*500;
            double hargaInput = Double.parseDouble(hargaField.getText().replaceAll(",", ""));
            if(hargaKomp>hargaInput || "true".equals(b.getJenis().getVerifikasi())){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(stage, child);
                        if(Function.verifikasi(controller.usernameField.getText(), 
                                controller.passwordField.getText(), "Penjualan Cabang")){
                            PenjualanAntarCabangDetail d = new PenjualanAntarCabangDetail();
                            d.setKodeBarang(b.getKodeBarang());
                            d.setKodeBarcode(b.getKodeBarcode());
                            d.setNamaBarang(b.getNamaBarang());
                            d.setKodeKategori(b.getKodeKategori());
                            d.setKodeJenis(b.getKodeJenis());
                            d.setKodeIntern(b.getKodeIntern());
                            d.setKadar(b.getKadar());
                            d.setBerat(b.getBerat());
                            d.setBeratAsli(b.getBeratAsli());
                            d.setBeratPersen(b.getBeratPersen());
                            d.setNilaiPokok(b.getNilaiPokok());
                            d.setInputDate(b.getInputDate());
                            d.setInputBy(b.getInputBy());
                            d.setAsalBarang(b.getAsalBarang());
                            d.setStatus(b.getStatus());
                            d.setHarga(hargaInput);
                            d.setKodeBarcodeBaru(b.getKodeBarcode());
                            listPenjualanAntarCabangDetail.add(d);
                            penjualanDetailTable.refresh();
                            hitungTotal();

                            b = null;
                            kodeBarcodeField.setText("");
                            namaBarangField.setText("");
                            beratField.setText("0");
                            hargaField.setText("0");
                            kodeBarcodeField.requestFocus();
                        }else{
                            mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                    + "atau otoritas tidak diijinkan");
                        }
                    }
                });
            }else{
                PenjualanAntarCabangDetail d = new PenjualanAntarCabangDetail();
                d.setKodeBarang(b.getKodeBarang());
                d.setKodeBarcode(b.getKodeBarcode());
                d.setNamaBarang(b.getNamaBarang());
                d.setKodeKategori(b.getKodeKategori());
                d.setKodeJenis(b.getKodeJenis());
                d.setKodeIntern(b.getKodeIntern());
                d.setKadar(b.getKadar());
                d.setBerat(b.getBerat());
                d.setBeratAsli(b.getBeratAsli());
                d.setBeratPersen(b.getBeratPersen());
                d.setNilaiPokok(b.getNilaiPokok());
                d.setInputDate(b.getInputDate());
                d.setInputBy(b.getInputBy());
                d.setAsalBarang(b.getAsalBarang());
                d.setStatus(b.getStatus());
                d.setHarga(hargaInput);
                d.setKodeBarcodeBaru(b.getKodeBarcode());
                listPenjualanAntarCabangDetail.add(d);
                penjualanDetailTable.refresh();
                hitungTotal();

                b = null;
                kodeBarcodeField.setText("");
                namaBarangField.setText("");
                beratField.setText("0");
                hargaField.setText("0");
                kodeBarcodeField.requestFocus();
            }
        }
    }
    private void removeBarang(PenjualanAntarCabangDetail d){
        listPenjualanAntarCabangDetail.remove(d);
        penjualanDetailTable.refresh();
        hitungTotal();
    }
    private void detailBarang(PenjualanAntarCabangDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarangByKodeBarcode(d.getKodeBarcode());
    }
    private void hitungTotal(){
        double total = 0;
        for(PenjualanAntarCabangDetail d : listPenjualanAntarCabangDetail){
            total = total + d.getHarga();
        }
        totalPenjualanField.setText(rp.format(total));
    }
    private void searchBarang(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/CariBarangBarcode.fxml");
        CariBarangBarcodeController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<Barang>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            b = row.getItem();
                            kodeBarcodeField.setText(b.getKodeBarcode());
                            getBarang();
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
