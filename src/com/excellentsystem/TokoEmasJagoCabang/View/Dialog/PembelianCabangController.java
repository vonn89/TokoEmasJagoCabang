/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PembelianCabangController  {

    @FXML private TableView<PenjualanAntarCabangDetail> pembelianDetailTable;
    @FXML private TableColumn<PenjualanAntarCabangDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, String> kodeBarcodeBaruColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanAntarCabangDetail, Number> hargaColumn;
    
    @FXML private GridPane gridPane;
    @FXML private TextField noPenjualanField;
    @FXML private TextField tglPenjualanField;
    @FXML private TextField cabangAsalField;
    @FXML private TextField salesJualField;
    @FXML private TextField tglPembelianField;
    @FXML public TextField kodeSalesField;
    
    @FXML private TextField totalPembelianField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public PenjualanAntarCabangHead penjualanAntarCabangHead;
    public ObservableList<PenjualanAntarCabangDetail> listPembelianAntarCabangDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        kodeBarcodeBaruColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeBaruProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Cetak Barcode");
        cetak.setOnAction((ActionEvent e)->{
            cetakBarcode();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pembelianDetailTable.refresh();
        });
        if(!saveButton.isVisible())
            rowMenu.getItems().addAll(cetak);
        rowMenu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(rowMenu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<PenjualanAntarCabangDetail> row = new TableRow<PenjualanAntarCabangDetail>(){
                @Override
                public void updateItem(PenjualanAntarCabangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e)->{
                            cetakBarcode();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            pembelianDetailTable.refresh();
                        });
                        if(!saveButton.isVisible())
                            rowMenu.getItems().addAll(cetak);
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
        noPenjualanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                getPembelian();
            }
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals(""))
                    saveButton.fire();
            }
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
        pembelianDetailTable.setItems(listPembelianAntarCabangDetail);
    }
    public void setNewPembelian(){
        try{
            noPenjualanField.setText("");
            tglPenjualanField.setText("");
            cabangAsalField.setText("");
            salesJualField.setText("");
            tglPembelianField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
            totalPembelianField.setText("0");
            kodeSalesField.setText("");
            kodeBarcodeBaruColumn.setVisible(false);
            noPenjualanField.requestFocus();
            noPenjualanField.selectAll();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void getPembelian(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listPembelianAntarCabangDetail.clear();
                    penjualanAntarCabangHead = PenjualanAntarCabangHeadDAO.get(conPusat, noPenjualanField.getText());
                    if(penjualanAntarCabangHead==null)
                        return "No penjualan yang dimasukan masih kosong atau salah";
                    else if(!penjualanAntarCabangHead.getCabangTujuan().equals(cabang.getKodeCabang()))
                        return "Tidak dapat diterima, karena no penjualan untuk cabang "+penjualanAntarCabangHead.getKodeCabang();
                    else if(penjualanAntarCabangHead.getStatusTerima().equals("true"))
                        return "Tidak dapat diterima, karena no penjualan sudah pernah diterima";
                    else if(penjualanAntarCabangHead.getStatusBatal().equals("true"))
                        return "Tidak dapat diterima, karena no penjualan sudah dibatalkan";
                    else if(penjualanAntarCabangHead.getStatusTerima().equals("false")&&
                            penjualanAntarCabangHead.getStatusBatal().equals("false")){
                        penjualanAntarCabangHead.setListPenjualanAntarCabangDetail(PenjualanAntarCabangDetailDAO.getAllByNoPenjualan(conPusat, noPenjualanField.getText()));
                        listPembelianAntarCabangDetail.addAll(penjualanAntarCabangHead.getListPenjualanAntarCabangDetail());
                        return "true";
                    }else 
                        return "Tidak dapat diterima, karena status pindah salah";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                for(MenuItem m : pembelianDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Cetak Barcode"))
                        pembelianDetailTable.getContextMenu().getItems().remove(m);
                }
                if(task.getValue().equals("true")){
                    noPenjualanField.setText(penjualanAntarCabangHead.getNoPenjualan());
                    tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualanAntarCabangHead.getTglPenjualan())));
                    cabangAsalField.setText(penjualanAntarCabangHead.getKodeCabang());
                    salesJualField.setText(penjualanAntarCabangHead.getKodeSales());
                    tglPembelianField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
                    totalPembelianField.setText(rp.format(penjualanAntarCabangHead.getTotalHarga()));
                    kodeSalesField.setText("");
                    kodeSalesField.requestFocus();
                }else{
                    noPenjualanField.setText(penjualanAntarCabangHead.getNoPenjualan());
                    tglPenjualanField.setText("");
                    cabangAsalField.setText("");
                    salesJualField.setText("");
                    tglPembelianField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
                    totalPembelianField.setText("0");
                    kodeSalesField.setText("");
                    noPenjualanField.requestFocus();
                    noPenjualanField.selectAll();
                    mainApp.showMessage(Modality.NONE, "Warning", task.getValue());
                }
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
    public void setDetailPembelian(PenjualanAntarCabangHead p){
        Task<List<PenjualanAntarCabangDetail>> task = new Task<List<PenjualanAntarCabangDetail>>() {
            @Override 
            public List<PenjualanAntarCabangDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    penjualanAntarCabangHead = p;
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
                
                noPenjualanField.setDisable(true);
                kodeSalesField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);

                noPenjualanField.setText(penjualanAntarCabangHead.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualanAntarCabangHead.getTglPenjualan())));
                cabangAsalField.setText(penjualanAntarCabangHead.getKodeCabang());
                salesJualField.setText(penjualanAntarCabangHead.getKodeSales());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(penjualanAntarCabangHead.getTglTerima())));
                totalPembelianField.setText(rp.format(penjualanAntarCabangHead.getTotalHarga()));
                kodeSalesField.setText(penjualanAntarCabangHead.getSalesTerima());
                listPembelianAntarCabangDetail.addAll(penjualanAntarCabangHead.getListPenjualanAntarCabangDetail());
                
                gridPane.getRowConstraints().get(11).setMinHeight(0);
                gridPane.getRowConstraints().get(11).setPrefHeight(0);
                gridPane.getRowConstraints().get(11).setMaxHeight(0);
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
    private void detailBarang(PenjualanAntarCabangDetail d){
        Barang b = new Barang();
        b.setKodeBarang(d.getKodeBarang());
        b.setKodeBarcode(d.getKodeBarcodeBaru());
        b.setNamaBarang(d.getNamaBarang());
        b.setKodeKategori(d.getKodeKategori());
        b.setKodeJenis(d.getKodeJenis());
        b.setKodeIntern(d.getKodeIntern());
        b.setKadar(d.getKadar());
        b.setBerat(d.getBerat());
        b.setBeratAsli(d.getBeratAsli());
        b.setBeratPersen(d.getBeratPersen());
        b.setNilaiPokok(d.getNilaiPokok());
        b.setInputDate(d.getInputDate());
        b.setInputBy(d.getInputBy());
        b.setAsalBarang(d.getAsalBarang());
        b.setStatus(d.getStatus());
        
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(b);
    }
    private void cetakBarcode(){
        List<Barang> barang = new ArrayList<>();
        for(PenjualanAntarCabangDetail d : listPembelianAntarCabangDetail){
//            if(b.isChecked())
            Barang b = new Barang();
            b.setKodeBarang(d.getKodeBarang());
            b.setKodeBarcode(d.getKodeBarcodeBaru());
            b.setNamaBarang(d.getNamaBarang());
            b.setKodeKategori(d.getKodeKategori());
            b.setKodeJenis(d.getKodeJenis());
            b.setKodeIntern(d.getKodeIntern());
            b.setKadar(d.getKadar());
            b.setBerat(d.getBerat());
            b.setBeratAsli(d.getBeratAsli());
            b.setBeratPersen(d.getBeratPersen());
            b.setNilaiPokok(d.getNilaiPokok());
            b.setInputDate(d.getInputDate());
            b.setInputBy(d.getInputBy());
            b.setAsalBarang(d.getAsalBarang());
            b.setStatus(d.getStatus());
            barang.add(b);
        }
        if(barang.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        else{        
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Cetak barcode ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                try{ 
                    mainApp.closeMessage();
                    PrintOut po = new PrintOut();
                    po.printBarcode(barang);
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
