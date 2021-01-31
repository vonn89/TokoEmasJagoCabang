/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PenjualanController  {

    @FXML private TableView<PenjualanDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanDetail, Number> hargaColumn;
    @FXML private TableColumn<PenjualanDetail, Number> ongkosColumn;
    @FXML private TableColumn<PenjualanDetail, Number> totalColumn;
    
    @FXML private Label noPenjualanLabel;
    @FXML private Label tglPenjualanLabel;
    @FXML public TextField kodeSalesField;
    
    @FXML private VBox memberVbox;
    @FXML private RadioButton pelangganUmumRadio;
    @FXML public RadioButton memberRadio;
    @FXML private HBox noKartuHbox;
    @FXML private TextField noKartuField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    
    @FXML private VBox addBarangVbox;
    @FXML private HBox labelHbox;
    @FXML private HBox fieldHbox;
    @FXML private TextField kodeBarcodeField;
    @FXML private TextField namaBarangField;
    @FXML private TextField beratField;
    @FXML private TextField hargaField;
    @FXML private Label ongkosLabel;
    @FXML private TextField ongkosField;
    
    @FXML private VBox vbox;
    @FXML public CheckBox printSuratPenjualanCheck;
    @FXML public TextArea keteranganField;
    @FXML public TextField grandtotalField;
    @FXML public Button saveButton;
    
    private Barang b;
    public Member m;
    public ObservableList<PenjualanDetail> listPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        ongkosColumn.setCellValueFactory(cellData -> cellData.getValue().ongkosProperty());
        ongkosColumn.setCellFactory(col -> getTableCell(rp));
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHargaJual()+cellData.getValue().getOngkos());
        });
        totalColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cari = new MenuItem("Cari Barang");
        cari.setOnAction((ActionEvent event) -> {
            searchBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanDetailTable.refresh();
        });
        rowMenu.getItems().addAll(cari);
        rowMenu.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(rowMenu);
        penjualanDetailTable.setRowFactory(table -> {
            TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>(){
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem cari = new MenuItem("Cari Barang");
                        cari.setOnAction((ActionEvent event) -> {
                            searchBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            listPenjualanDetail.remove(item);
                            penjualanDetailTable.refresh();
                            hitungTotal();
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
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        if(row.getItem().getNamaBarang()!=null)
                            detailBarang(row.getItem());
                    
                }
            });
            return row;
        });
        Function.setNumberField(hargaField, rp);
        Function.setNumberField(ongkosField, rp);
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals("")){
                    if(noKartuHbox.isVisible()){
                        noKartuField.requestFocus();
                        noKartuField.selectAll();
                    }else{
                        namaField.requestFocus();
                        namaField.selectAll();
                    }
                }
            }
        });
        noKartuField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                if(!noKartuField.getText().equals(""))
                    getMember();
                else{
                    namaField.requestFocus();
                    namaField.selectAll();
                }
            }
        });
        namaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                alamatField.requestFocus();
                alamatField.selectAll();
            }
        });
        alamatField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                noTelpField.requestFocus();
                noTelpField.selectAll();
            }
        });
        noTelpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeBarcodeField.requestFocus();
                kodeBarcodeField.selectAll();
            }
        });
        kodeBarcodeField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                getBarang();
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                if(ongkosField.isDisable())
                    setBarang();
                else{
                    ongkosField.requestFocus();
                    ongkosField.selectAll();
                }
            }
        });
        ongkosField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
        });
        saveButton.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
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
        penjualanDetailTable.setItems(listPenjualanDetail);
        for(Node n : labelHbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : fieldHbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : memberVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : vbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        selectPelanggan();
    }
    public void setPenjualan(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return PenjualanHeadDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                ongkosColumn.setVisible(false);
                totalColumn.setVisible(false);
                ongkosField.setVisible(false);
                ongkosLabel.setVisible(false);
                
                noPenjualanLabel.setText(task.getValue());
                tglPenjualanLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy").format(tglBarang.parse(sistem.getTglSystem()))+" "+
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
                kodeSalesField.requestFocus();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPemesanan(PemesananHead psn){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    if(!psn.getKodeMember().equals("")){
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            memberRadio.setSelected(true);
                            selectPelanggan();
                            m = MemberDAO.get(conPusat, psn.getKodeMember());
                            namaField.setText(m.getNama());
                            alamatField.setText(m.getAlamat());
                            noTelpField.setText(m.getNoTelp());
                        }
                    }else{
                        pelangganUmumRadio.setSelected(true);
                        selectPelanggan();
                        namaField.setText(psn.getNama());
                        alamatField.setText(psn.getAlamat());
                        noTelpField.setText(psn.getNoTelp());
                    }
                    return PenjualanHeadDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noPenjualanLabel.setText(task.getValue());
                tglPenjualanLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy").format(tglBarang.parse(sistem.getTglSystem()))+" "+
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
                ongkosField.setDisable(false);
                kodeSalesField.requestFocus();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML private GridPane gridPane;
    public void setDetailPenjualan(String noPenjualan){
        Task<PenjualanHead> task = new Task<PenjualanHead>() {
            @Override 
            public PenjualanHead call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    PenjualanHead p = PenjualanHeadDAO.get(conCabang, noPenjualan);
                    p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(conCabang, noPenjualan));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                PenjualanHead p = task.getValue();

                kodeSalesField.setDisable(true);
                pelangganUmumRadio.setVisible(false);
                memberRadio.setVisible(false);
                noKartuHbox.setVisible(false);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                noTelpField.setDisable(true);
                addBarangVbox.setVisible(false);
                labelHbox.setVisible(false);
                fieldHbox.setVisible(false);
                keteranganField.setDisable(true);
                printSuratPenjualanCheck.setVisible(false);
                saveButton.setVisible(false);
                for(MenuItem m : penjualanDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Cari Barang"))
                        penjualanDetailTable.getContextMenu().getItems().remove(m);
                }

                noPenjualanLabel.setText(p.getNoPenjualan());
                tglPenjualanLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy HH:mm:ss").format(tglSql.parse(p.getTglPenjualan())));
                kodeSalesField.setText(p.getKodeSales());
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                listPenjualanDetail.addAll(p.getListPenjualanDetail());
                keteranganField.setText(p.getKeterangan());
                grandtotalField.setText(rp.format(p.getGrandtotal()));
                gridPane.getRowConstraints().get(4).setMinHeight(0);
                gridPane.getRowConstraints().get(4).setPrefHeight(0);
                gridPane.getRowConstraints().get(4).setMaxHeight(0);
                gridPane.getRowConstraints().get(7).setMinHeight(10);
                gridPane.getRowConstraints().get(7).setPrefHeight(10);
                gridPane.getRowConstraints().get(7).setMaxHeight(10);
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void selectPelanggan(){
        if(pelangganUmumRadio.isSelected()){
            noKartuHbox.setVisible(false);
            namaField.setDisable(false);
            alamatField.setDisable(false);
            noTelpField.setDisable(false);
            
            m = null;
            noKartuField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
        }else if(memberRadio.isSelected()){
            noKartuHbox.setVisible(true);
            namaField.setDisable(true);
            alamatField.setDisable(true);
            noTelpField.setDisable(true);
            
            m = null;
            noKartuField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
        }
    }
    private void getMember(){
        Task<Member> task = new Task<Member>() {
            @Override 
            public Member call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return MemberDAO.getByNoKartuOrNoRfid(conPusat, noKartuField.getText());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            m = task.getValue();
            if(m==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Member tidak ditemukan");
                noKartuField.selectAll();
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
            }else if(m.getStatus().equals("false")){
                mainApp.showMessage(Modality.NONE, "Warning", "Member sudah dihapus/tidak aktif");
                noKartuField.selectAll();
                m = null;
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
            }else{
                namaField.setText(m.getNama());
                alamatField.setText(m.getAlamat());
                noTelpField.setText(m.getNoTelp());
                kodeBarcodeField.requestFocus();
                kodeBarcodeField.selectAll();
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
        for(PenjualanDetail d : listPenjualanDetail){
            if(d.getKodeBarcode().equals(kodeBarcodeField.getText()))
                status = true;
        }
        if(status){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode sudah diinput");
            b = null;
            namaBarangField.setText("");
            beratField.setText("0");
            hargaField.setText("0");
            ongkosField.setText("0");
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
                    ongkosField.setText("0");
                    kodeBarcodeField.selectAll();
                }else if(b.getStatus().equals("false")){
                    mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah terjual/hancur");
                    b = null;
                    namaBarangField.setText("");
                    beratField.setText("0");
                    hargaField.setText("0");
                    ongkosField.setText("0");
                    kodeBarcodeField.selectAll();
                }else{
                    namaBarangField.setText(b.getNamaBarang());
                    beratField.setText(gr.format(b.getBerat()));
                    hargaField.setText(rp.format(ceil(b.getKategori().getHargaJual()*b.getBerat()/500)*500));
                    ongkosField.setText("0");
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
        }else if(listPenjualanDetail.size()>=4){
            mainApp.showMessage(Modality.NONE, "Warning", "Surat penjualan tidak boleh dari 4 item");
            saveButton.requestFocus();
        }else{
            namaBarangField.setText(b.getNamaBarang());
            beratField.setText(gr.format(b.getBerat()));
            double hargaKomp = ceil(b.getKategori().getHargaJual()*b.getBerat()/500)*500;
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
                                controller.passwordField.getText(), "Penjualan")){
                            PenjualanDetail d = new PenjualanDetail();
                            d.setKodeBarcode(b.getKodeBarcode());
                            d.setKodeBarang(b.getKodeBarang());
                            d.setKodeKategori(b.getKodeKategori());
                            d.setKodeJenis(b.getKodeJenis());
                            d.setNamaBarang(b.getNamaBarang());
                            d.setBerat(b.getBerat());
                            d.setHargaBeli(b.getNilaiPokok());
                            d.setHargaJual(hargaInput);
                            d.setHargaKategori(b.getKategori().getHargaJual());
                            d.setOngkos(Double.parseDouble(ongkosField.getText().replaceAll(",", "")));
                            d.setBarang(b);
                            listPenjualanDetail.add(d);
                            penjualanDetailTable.refresh();
                            hitungTotal();

                            b = null;
                            kodeBarcodeField.setText("");
                            namaBarangField.setText("");
                            beratField.setText("0");
                            hargaField.setText("0");
                            ongkosField.setText("0");
                            saveButton.requestFocus();
                        }else{
                            mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                    + "atau otoritas tidak diijinkan");
                        }
                    }
                });
            }else{
                PenjualanDetail d = new PenjualanDetail();
                d.setKodeBarcode(b.getKodeBarcode());
                d.setKodeBarang(b.getKodeBarang());
                d.setKodeKategori(b.getKodeKategori());
                d.setKodeJenis(b.getKodeJenis());
                d.setNamaBarang(b.getNamaBarang());
                d.setBerat(b.getBerat());
                d.setHargaBeli(b.getNilaiPokok());
                d.setHargaJual(hargaInput);
                d.setHargaKategori(b.getKategori().getHargaJual());
                d.setOngkos(Double.parseDouble(ongkosField.getText().replaceAll(",", "")));
                d.setBarang(b);
                listPenjualanDetail.add(d);
                penjualanDetailTable.refresh();
                hitungTotal();

                b = null;
                kodeBarcodeField.setText("");
                namaBarangField.setText("");
                beratField.setText("0");
                hargaField.setText("0");
                ongkosField.setText("0");
                saveButton.requestFocus();
            }
        }
    }
    private void detailBarang(PenjualanDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarangByKodeBarcode(d.getKodeBarcode());
    }
    private void hitungTotal(){
        double total = 0;
        for(PenjualanDetail d : listPenjualanDetail){
            total = total + d.getHargaJual()+d.getOngkos();
        }
        grandtotalField.setText(rp.format(total));
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
    private void searchMember(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/CariMember.fxml");
        CariMemberController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.memberTable.setRowFactory(table -> {
            TableRow<Member> row = new TableRow<Member>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            m = row.getItem();
                            noKartuField.setText(m.getNoKartu());
                            namaField.setText(m.getNama());
                            alamatField.setText(m.getAlamat());
                            noTelpField.setText(m.getNoTelp());
                            kodeSalesField.requestFocus();
                            kodeSalesField.selectAll();
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
