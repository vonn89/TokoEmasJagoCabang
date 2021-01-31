/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
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
public class CariHutangController  {

    @FXML private TableView<HutangHead> terimaHutangTable;
    @FXML private TableColumn<HutangHead, String> noHutangColumn;
    @FXML private TableColumn<HutangHead, String> tglHutangColumn;
    @FXML private TableColumn<HutangHead, String> kodeSalesColumn;
    @FXML private TableColumn<HutangHead, String> namaColumn;
    @FXML private TableColumn<HutangHead, String> alamatColumn;
    @FXML private TableColumn<HutangHead, Number> totalBeratColumn;
    @FXML private TableColumn<HutangHead, Number> totalHutangColumn;
    @FXML private TableColumn<HutangHead, Number> bungaPersenColumn;
    
    @FXML private TextField noHutangField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> totalHutangCombo;
    @FXML private TextField totalHutangField;
    @FXML private TextField bungaPersenField;
    @FXML private ComboBox<String> kodeKategoriCombo;
    @FXML private TextField namaPelangganField;
    @FXML private TextField alamatPelangganField;
    @FXML private TextField noTelpField;
    @FXML private TextField kodeSalesField;
    @FXML private TextField namaBarangField;
    
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<HutangHead> allHutang = FXCollections.observableArrayList();
    private final ObservableList<HutangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglHutangColumn.setComparator(Function.sortDate(tglLengkap));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHutangColumn.setCellValueFactory(cellData -> cellData.getValue().totalHutangProperty());
        totalHutangColumn.setCellFactory(col -> getTableCell(rp));
        bungaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().bungaPersenProperty());
        bungaPersenColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse("2000-01-01"));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getHutang();
        });
        rowMenu.getItems().addAll(refresh);
        terimaHutangTable.setContextMenu(rowMenu);
        terimaHutangTable.setRowFactory(table -> {
            TableRow<HutangHead> row = new TableRow<HutangHead>() {
                @Override
                public void updateItem(HutangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detailTerima = new MenuItem("Detail Terima Hutang");
                        detailTerima.setOnAction((ActionEvent e) -> {
                            detailHutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pelunasan Hutang");
                        bayar.setOnAction((ActionEvent e) -> {
                            pelunasanHutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getHutang();
                        });
                        rowMenu.getItems().add(detailTerima);
                        rowMenu.getItems().add(bayar);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        pelunasanHutang(row.getItem());
                }
            });
            return row;
        });
        totalHutangField.setOnKeyReleased((event) -> {
            try{
                String string = totalHutangField.getText();
                if(string.contains("-"))
                    totalHutangField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            totalHutangField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            totalHutangField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else{
                        if(!string.equals(""))
                            totalHutangField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                }
                totalHutangField.end();
            }catch(Exception e){
                totalHutangField.undo();
            }
        });
        allHutang.addListener((ListChangeListener.Change<? extends HutangHead> change) -> {
            searchHutang();
        });
        noHutangField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        bungaPersenField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        namaPelangganField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        alamatPelangganField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        noTelpField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        kodeSalesField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        totalHutangField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        namaBarangField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        filterData.addAll(allHutang);
        terimaHutangTable.setItems(filterData);
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
        ObservableList<String> allTotalHutang = FXCollections.observableArrayList();
        allTotalHutang.add("Sama Dengan");
        allTotalHutang.add("Kurang Dari");
        allTotalHutang.add("Lebih Dari");
        totalHutangCombo.setItems(allTotalHutang);
        totalHutangCombo.getSelectionModel().select("Sama Dengan");
        kodeKategoriCombo.setItems(allKategori);
        getKategori();
        getHutang();
    }
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private void getKategori(){
        Task<List<Kategori>> task = new Task<List<Kategori>>() {
            @Override 
            public List<Kategori> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KategoriDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKategori.clear();
            allKategori.add("Semua");
            for(Kategori k : task.getValue()){
                allKategori.add(k.getKodeKategori());
            }
            kodeKategoriCombo.getSelectionModel().select("Semua");
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void getHutang(){
        Task<List<HutangHead>> task = new Task<List<HutangHead>>() {
            @Override 
            public List<HutangHead> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<HutangHead> listHutangHead = HutangHeadDAO.getAllByDateAndStatusLunasAndStatusBatal(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "false", "false");
                    for(HutangHead h : listHutangHead){
                        h.setListHutangDetail(HutangDetailDAO.getAllByNoHutang(conCabang, h.getNoHutang()));
                    }
                    return listHutangHead;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allHutang.clear();
            allHutang.addAll(task.getValue());
            terimaHutangTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void searchHutang() {
        try{
            filterData.clear();
            for (HutangHead d : allHutang) {
                boolean status = false;
                boolean statusKategori = false;
                for(HutangDetail dt : d.getListHutangDetail()){
                    if(dt.getNamaBarang()!=null && dt.getNamaBarang().toLowerCase().contains(namaBarangField.getText().toLowerCase()))
                        status = true;
                    if(kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("Semua") ||
                            (dt.getKodeKategori()!=null && dt.getKodeKategori().equals(kodeKategoriCombo.getSelectionModel().getSelectedItem())))
                        statusKategori = true;
                }
                if (noHutangField.getText() == null || noHutangField.getText().equals("") || 
                        (d.getNoHutang()!=null && d.getNoHutang().toLowerCase().contains(noHutangField.getText().toLowerCase()))){
                    if (bungaPersenField.getText() == null || bungaPersenField.getText().equals("") || 
                            (gr.format(d.getBungaPersen())!=null && gr.format(d.getBungaPersen()).toLowerCase().contains(bungaPersenField.getText().toLowerCase()))){
                        if (namaPelangganField.getText() == null || namaPelangganField.getText().equals("") || 
                                (d.getNama()!=null && d.getNama().toLowerCase().contains(namaPelangganField.getText().toLowerCase()))){
                            if (alamatPelangganField.getText() == null || alamatPelangganField.getText().equals("") || 
                                    (d.getAlamat()!=null && d.getAlamat().toLowerCase().contains(alamatPelangganField.getText().toLowerCase()))){
                                if (noTelpField.getText() == null || noTelpField.getText().equals("") || 
                                        (d.getNoTelp()!=null && d.getNoTelp().toLowerCase().contains(noTelpField.getText().toLowerCase()))){
                                    if (kodeSalesField.getText() == null || kodeSalesField.getText().equals("") || 
                                            (d.getKodeSales()!=null && d.getKodeSales().toLowerCase().contains(kodeSalesField.getText().toLowerCase()))){
                                        if (namaBarangField.getText() == null || namaBarangField.getText().equals("") || status){
                                            if (statusKategori){
                                                if (totalHutangField.getText() == null || totalHutangField.getText().equals("")){
                                                    filterData.add(d);
                                                }else{
                                                    if(totalHutangCombo.getSelectionModel().getSelectedItem().equals("Sama Dengan") && 
                                                            d.getTotalHutang()==Double.parseDouble(totalHutangField.getText().replaceAll(",", "")))
                                                        filterData.add(d);
                                                    else if(totalHutangCombo.getSelectionModel().getSelectedItem().equals("Lebih Dari") && 
                                                            d.getTotalHutang()>=Double.parseDouble(totalHutangField.getText().replaceAll(",", "")))
                                                        filterData.add(d);
                                                    else if(totalHutangCombo.getSelectionModel().getSelectedItem().equals("Kurang Dari") && 
                                                            d.getTotalHutang()<=Double.parseDouble(totalHutangField.getText().replaceAll(",", "")))
                                                        filterData.add(d);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void detailHutang(HutangHead h){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/TerimaHutang.fxml");
        TerimaHutangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailHutang(h);
    }
    private void pelunasanHutang(HutangHead h){
        if(h.getStatusLunas().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Hutang sudah lunas");
        }else{
            Stage child = new Stage();
            FXMLLoader loaderPembayaran = mainApp.showDialog(stage ,child, "View/Dialog/PelunasanHutang.fxml");
            PelunasanHutangController controller = loaderPembayaran.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.noHutangField.setText(h.getNoHutang());
            controller.getHutang();
            controller.saveButton.setOnAction((ev) -> {
                if(controller.kodeSalesField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
                else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
                else if(controller.pembayaranPinjamanField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman masih kosong");
                else if(controller.pembayaranBungaField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran bunga masih kosong");
                else if(controller.bungaPersenBaruField.getText().equals("") || controller.bungaPersenBaruField.getText().equals("0"))
                    mainApp.showMessage(Modality.NONE, "Warning", "Bunga persen masih kosong");
                else if(Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", ""))>
                        Double.parseDouble(controller.totalPinjamanField.getText().replaceAll(",", "")))
                    mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran pinjaman melebihi total pinjaman");
                else{
                    double bungaKomp = Double.parseDouble(controller.bungaRpField.getText().replaceAll(",", ""));
                    double bayarBunga = Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", ""));
                    double bayarPinjaman = Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", ""));
                    double sisaPinjaman = Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", ""));
                    double bungaBaru = Double.parseDouble(controller.bungaPersenBaruField.getText().replaceAll(",", ""));
                    double bunga = 0;
                    for(BungaHutang b : controller.listBungaHutang){
                        if(b.getMinJumlahRp()<=sisaPinjaman && sisaPinjaman<=b.getMaxJumlahRp())
                            bunga = b.getBungaPersen();
                    }
                    if(bayarBunga<bungaKomp || bunga!=bungaBaru){
                        Stage verifikasi = new Stage();
                        FXMLLoader loader = mainApp.showDialog(child, verifikasi, "View/Dialog/Verifikasi.fxml");
                        VerifikasiController verifikasiController = loader.getController();
                        verifikasiController.setMainApp(mainApp, child, verifikasi);
                        verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                            if (keyEvent.getCode() == KeyCode.ENTER)  {
                                mainApp.closeDialog(child, verifikasi);
                                if(Function.verifikasi(verifikasiController.usernameField.getText(), verifikasiController.passwordField.getText(),"Pelunasan Hutang")){
                                    savePelunasanHutang(controller, child, h);
                                }else{
                                    mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi salah");
                                }
                            }
                        });
                    }else{
                        savePelunasanHutang(controller, child, h);
                    }
                }
            });
        }
    }
    private HutangHead hutangBaru;
    private void savePelunasanHutang(PelunasanHutangController controller, Stage child, HutangHead h){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    h.setTglLunas(Function.getSystemDate());
                    h.setSalesLunas(Function.ceksales(controller.kodeSalesField.getText()));
                    h.setBungaRp(Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", "")));
                    h.setStatusLunas("true");
                    h.setListHutangDetail(controller.allDetail);
                    
                    hutangBaru = null;
                    if(Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", ""))>0){
                        hutangBaru = new HutangHead();
                        hutangBaru.setTglHutang(h.getTglLunas());
                        hutangBaru.setKodeMember(h.getKodeMember());
                        hutangBaru.setNama(h.getNama());
                        hutangBaru.setAlamat(h.getAlamat());
                        hutangBaru.setNoTelp(h.getNoTelp());
                        int noUrut = 1;
                        double totalBerat = 0;
                        double totalNilaiJual = 0;
                        hutangBaru.setListHutangDetail(controller.allDetail);
                        for(HutangDetail d : hutangBaru.getListHutangDetail()){
                            d.setNoUrut(noUrut);
                            d.setNilaiJual(ceil(d.getKategori().getHargaJual()*d.getBerat()/500)*500);
                            
                            noUrut = noUrut + 1;
                            totalBerat = totalBerat + d.getBerat();
                            totalNilaiJual = totalNilaiJual + d.getNilaiJual();
                        }
                        hutangBaru.setTotalBerat(totalBerat);
                        hutangBaru.setMaksPinjaman(totalNilaiJual*70/100);
                        hutangBaru.setTotalHutang(Double.parseDouble(controller.sisaPinjamanField.getText().replaceAll(",", "")));
                        hutangBaru.setLamaPinjam(0);
                        hutangBaru.setBungaPersen(Double.parseDouble(controller.bungaPersenBaruField.getText().replaceAll(",", "")));
                        hutangBaru.setBungaKomp(Math.ceil(Double.parseDouble(controller.bungaRpBaruField.getText().replaceAll(",", ""))/30/500)*500);
                        hutangBaru.setBungaRp(Math.ceil(Double.parseDouble(controller.bungaRpBaruField.getText().replaceAll(",", ""))/30/500)*500);
                        hutangBaru.setKeterangan("No Hutang Lama : "+h.getNoHutang()+"\n"
                                + "Pinjaman : "+rp.format(h.getTotalHutang())+"\n"
                                + "Cicil Pinjaman : "+rp.format(Double.parseDouble(controller.pembayaranPinjamanField.getText().replaceAll(",", "")))+"\n"
                                + "Bunga Dibayar : "+rp.format(Double.parseDouble(controller.pembayaranBungaField.getText().replaceAll(",", ""))));
                        hutangBaru.setStatusHilang("false");
                        hutangBaru.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                        hutangBaru.setStatusLunas("false");
                        hutangBaru.setTglLunas("2000-01-01 00:00:00");
                        hutangBaru.setSalesLunas("");
                        hutangBaru.setStatusBatal("false");
                        hutangBaru.setTglBatal("2000-01-01 00:00:00");
                        hutangBaru.setUserBatal("");
                    }
                    return Service.savePelunasanHutang(conCabang, h, hutangBaru);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            getHutang();
            String status = task.getValue();
            if(status.equals("true")){
                if(hutangBaru!=null){
                    if(controller.printSuratHutangCheckBox.isSelected()){
                        try{
                            for(HutangDetail d : controller.allDetail){
                                d.setHutangHead(hutangBaru);
                            }
                            PrintOut po = new PrintOut();
                            po.printSuratHutang(controller.allDetail);
                            po.printSuratHutangInternal(controller.allDetail);
                        }catch(Exception ex){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                    mainApp.closeDialog(stage, child);
                    mainApp.showMessage(Modality.NONE, "Success", "Bayar bunga/cicil hutang berhasil disimpan");
                }else{
                    mainApp.closeDialog(stage, child);
                    mainApp.showMessage(Modality.NONE, "Success", "Pelunasan hutang berhasil disimpan");
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
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
