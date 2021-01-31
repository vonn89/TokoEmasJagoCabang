/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BungaHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PelunasanHutangController {

    @FXML private TableView<HutangDetail> detailTable;
    @FXML private TableColumn<HutangDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<HutangDetail, String> namaBarangColumn;
    @FXML private TableColumn<HutangDetail, Number> beratColumn;
    @FXML private TableColumn<HutangDetail, Number> nilaiJualColumn;
    @FXML private TableColumn<HutangDetail, Number> nilaiJualSekarangColumn;
    
    @FXML public TextField noHutangField;
    @FXML private TextField tglHutangField;
    @FXML private TextField tglLunasField;
    
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    
    @FXML public TextField totalPinjamanField;
    @FXML private TextField lamaPinjamField;
    @FXML private TextField bungaPersenField;
    @FXML public TextField bungaRpField;
    @FXML public TextField kodeSalesField;
    @FXML public CheckBox printSuratHutangCheckBox;
    
    @FXML public TextField pembayaranPinjamanField;
    @FXML public TextField pembayaranBungaField;
    
    @FXML private Label sisaPinjamanLabel;
    @FXML public TextField sisaPinjamanField;
    @FXML private Label bungaPersenLabel;
    @FXML private HBox bungaPersenHbox;
    @FXML public TextField bungaPersenBaruField;
    @FXML public TextField bungaRpBaruField;
    
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private HutangHead h =null;
    public List<BungaHutang> listBungaHutang;
    public ObservableList<HutangDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        nilaiJualColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiJualProperty());
        nilaiJualColumn.setCellFactory(col -> getTableCell(rp));
        nilaiJualSekarangColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getKategori()==null)
                return new SimpleDoubleProperty(0);
            else
                return new SimpleDoubleProperty(ceil(cellData.getValue().getKategori().getHargaJual()*cellData.getValue().getBerat()/500)*500);
        });
        nilaiJualSekarangColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            detailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        detailTable.setContextMenu(rowMenu);
        detailTable.setRowFactory(table -> {
            TableRow<HutangDetail> row = new TableRow<HutangDetail>(){
                @Override
                public void updateItem(HutangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            detailTable.refresh();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        
        pembayaranPinjamanField.setOnKeyReleased((event) -> {
            try{
                String string = pembayaranPinjamanField.getText();
                if(string.contains("-"))
                    pembayaranPinjamanField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            pembayaranPinjamanField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            pembayaranPinjamanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        pembayaranPinjamanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                pembayaranPinjamanField.end();
            }catch(Exception e){
                pembayaranPinjamanField.undo();
            }
            hitung();
        });
        pembayaranBungaField.setOnKeyReleased((event) -> {
            try{
                String string = pembayaranBungaField.getText();
                if(string.contains("-"))
                    pembayaranBungaField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            pembayaranBungaField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            pembayaranBungaField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        pembayaranBungaField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                pembayaranBungaField.end();
            }catch(Exception e){
                pembayaranBungaField.undo();
            }
            hitung();
        });
        bungaPersenBaruField.setOnKeyReleased((event) -> {
            try{
                String string = bungaPersenBaruField.getText();
                if(string.contains("-"))
                    bungaPersenBaruField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            bungaPersenBaruField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            bungaPersenBaruField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        bungaPersenBaruField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                bungaPersenBaruField.end();
            }catch(Exception e){
                bungaPersenBaruField.undo();
            }
            getBungaRp();
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals("")){
                    pembayaranPinjamanField.requestFocus();
                    pembayaranPinjamanField.selectAll();
                }
            }
        });
        pembayaranPinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                pembayaranBungaField.selectAll();
                pembayaranBungaField.requestFocus();
            }
        });
        pembayaranBungaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                bungaPersenBaruField.requestFocus();
                bungaPersenBaruField.selectAll();
            }
        });
        bungaPersenBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveButton.requestFocus();
            }
        });
        saveButton.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveButton.fire();
            }
        });
        noHutangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                getHutang();
            }
        });
        
        detailTable.setItems(allDetail);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        Task<List<BungaHutang>> task = new Task<List<BungaHutang>>() {
            @Override 
            public List<BungaHutang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return BungaHutangDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                listBungaHutang = task.getValue();
                tglLunasField.setText(
                    tglNormal.format(tglBarang.parse(sistem.getTglSystem()))+" "+
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }catch(Exception ex){
                ex.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void getHutang(){
        Task<List<HutangDetail>> task = new Task<List<HutangDetail>>() {
            @Override 
            public List<HutangDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    h = HutangHeadDAO.get(conCabang, noHutangField.getText());
                    List<HutangDetail> listHutangDetail = HutangDetailDAO.getAllByNoHutang(conCabang, noHutangField.getText());
                    for(HutangDetail d : listHutangDetail){
                        d.setKategori(KategoriDAO.get(conCabang, d.getKodeKategori()));
                    }
                    return listHutangDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allDetail.clear();
                tglHutangField.setText("");
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
                totalPinjamanField.setText("0");
                lamaPinjamField.setText("0");
                bungaPersenField.setText("0");
                bungaRpField.setText("0");
                pembayaranPinjamanField.setText("0");
                pembayaranBungaField.setText("0");
                sisaPinjamanField.setText("0");
                bungaPersenBaruField.setText("0");
                bungaRpBaruField.setText("0");
                if(h==null){
                    mainApp.showMessage(Modality.NONE, "Warning", "No Hutang tidak ditemukan");
                }else if(h.getStatusLunas().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Warning", "Hutang sudah pernah dilunasi");
                }else if(h.getStatusBatal().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Warning", "Hutang sudah pernah dibatalkan");
                }else{
                    h.setListHutangDetail(task.getValue());
                    allDetail.addAll(h.getListHutangDetail());
                    noHutangField.setText(h.getNoHutang());
                    tglHutangField.setText(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                    tglLunasField.setText(
                        tglNormal.format(tglBarang.parse(sistem.getTglSystem()))+" "+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    namaField.setText(h.getNama());
                    alamatField.setText(h.getAlamat());
                    noTelpField.setText(h.getNoTelp());
                    
                    totalPinjamanField.setText(rp.format(h.getTotalHutang()));
                    lamaPinjamField.setText(rp.format(h.getLamaPinjam()));
                    bungaPersenField.setText(gr.format(h.getBungaPersen()));
                    bungaRpField.setText(rp.format(h.getBungaKomp()));
                    
                    pembayaranPinjamanField.setText(rp.format(h.getTotalHutang()));
                    pembayaranBungaField.setText(rp.format(h.getBungaKomp()));
                    
                    kodeSalesField.requestFocus();
                    kodeSalesField.selectAll();
                }
            }catch(Exception ex){
                ex.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void getBungaRp(){
        double pinjaman = Double.parseDouble(sisaPinjamanField.getText().replaceAll(",", ""));
        double bunga = Double.parseDouble(bungaPersenBaruField.getText().replaceAll(",", ""));
        double bungaRp = pinjaman*bunga/100;
        bungaRpBaruField.setText(rp.format(ceil(bungaRp/500)*500));
    }
    private void hitung(){
        double sisaPinjaman = Double.parseDouble(totalPinjamanField.getText().replaceAll(",", ""))-
                Double.parseDouble(pembayaranPinjamanField.getText().replaceAll(",", ""));
        
        double bunga = 0;
        for(BungaHutang b : listBungaHutang){
            if(b.getMinJumlahRp()<=sisaPinjaman&&sisaPinjaman<=b.getMaxJumlahRp())
                bunga = b.getBungaPersen();
        }
        sisaPinjamanField.setText(rp.format(sisaPinjaman));
        bungaPersenBaruField.setText(gr.format(bunga));
        getBungaRp();
    }
    @FXML private GridPane gridPane;
    public void setDetailHutang(HutangHead h){
        Task<List<HutangDetail>> task = new Task<List<HutangDetail>>() {
            @Override 
            public List<HutangDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<HutangDetail> listHutangDetail = HutangDetailDAO.getAllByNoHutang(conCabang, h.getNoHutang());
                    for(HutangDetail d : listHutangDetail){
                        d.setKategori(KategoriDAO.get(conCabang, d.getKodeKategori()));
                    }
                    return listHutangDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                h.setListHutangDetail(task.getValue());

                noHutangField.setDisable(true);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                noTelpField.setDisable(true);
                
                pembayaranPinjamanField.setDisable(true);
                pembayaranBungaField.setDisable(true);
                kodeSalesField.setDisable(true);
                
                sisaPinjamanLabel.setVisible(false);
                sisaPinjamanField.setVisible(false);
                bungaPersenLabel.setVisible(false);
                bungaPersenHbox.setVisible(false);
                
                printSuratHutangCheckBox.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);

                noHutangField.setText(h.getNoHutang());
                tglHutangField.setText(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                tglLunasField.setText(tglLengkap.format(tglSql.parse(h.getTglLunas())));
                namaField.setText(h.getNama());
                alamatField.setText(h.getAlamat());
                noTelpField.setText(h.getNoTelp());
                totalPinjamanField.setText(rp.format(h.getTotalHutang()));
                lamaPinjamField.setText(rp.format(h.getLamaPinjam()));
                bungaPersenField.setText(gr.format(h.getBungaPersen()));
                bungaRpField.setText(rp.format(h.getBungaKomp()));
                kodeSalesField.setText(h.getKodeSales());
                
                pembayaranPinjamanField.setText(rp.format(h.getTotalHutang()));
                pembayaranBungaField.setText(rp.format(h.getBungaRp()));
                bungaPersenField.setText(gr.format(h.getBungaPersen()));
                bungaRpField.setText(rp.format(h.getBungaKomp()));
                
                allDetail.addAll(h.getListHutangDetail());
                gridPane.getRowConstraints().get(10).setMinHeight(0);
                gridPane.getRowConstraints().get(10).setPrefHeight(0);
                gridPane.getRowConstraints().get(10).setMaxHeight(0);
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
    
}
