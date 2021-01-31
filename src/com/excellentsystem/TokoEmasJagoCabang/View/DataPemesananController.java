/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananHeadDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.InputKodeSalesController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PembayaranController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PemesananController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PenjualanController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
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
public class DataPemesananController  {

    @FXML private TableView<PemesananDetail> pemesananHeadTable;
    @FXML private TableColumn<PemesananDetail, String> noPemesananColumn;
    @FXML private TableColumn<PemesananDetail, String> tglPemesananColumn;
    @FXML private TableColumn<PemesananDetail, String> namaColumn;
    @FXML private TableColumn<PemesananDetail, String> alamatColumn;
    @FXML private TableColumn<PemesananDetail, Number> titipUangColumn;
    
    @FXML private TableColumn<PemesananDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PemesananDetail, String> namaBarangColumn;
    @FXML private TableColumn<PemesananDetail, Number> beratColumn;
    @FXML private TableColumn<PemesananDetail, Number> ongkosColumn;
    @FXML private TableColumn<PemesananDetail, Number> hargaColumn;
    
    @FXML private TableColumn<PemesananDetail, String> keteranganColumn;
    @FXML private TableColumn<PemesananDetail, String> kodeSalesColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalOngkosLabel;
    @FXML private Label totalHargaLabel;
    @FXML private Label totalPemesananLabel;
    @FXML private Label totalTitipUangLabel;
    private Main mainApp;   
    private final ObservableList<PemesananDetail> allPemesanan = FXCollections.observableArrayList();
    private final ObservableList<PemesananDetail> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().noPemesananProperty());
        tglPemesananColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPemesananHead().getTglPemesanan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().alamatProperty());
        titipUangColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().titipUangProperty());
        titipUangColumn.setCellFactory(col -> getTableCell(rp));
        
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        ongkosColumn.setCellValueFactory(cellData -> cellData.getValue().ongkosProperty());
        ongkosColumn.setCellFactory(col -> getTableCell(rp));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().keteranganProperty());
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananHead().kodeSalesProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusYears(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Pemesanan");
        addNew.setOnAction((ActionEvent e) -> {
            newPemesanan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPemesanan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pemesananHeadTable.setContextMenu(rowMenu);
        pemesananHeadTable.setRowFactory(table -> {
            TableRow<PemesananDetail> row = new TableRow<PemesananDetail>() {
                @Override
                public void updateItem(PemesananDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pemesanan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPemesanan();
                        });
                        MenuItem detail = new MenuItem("Detail Pemesanan");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPemesanan(item.getPemesananHead());
                        });
                        MenuItem ambil = new MenuItem("Ambil Pemesanan");
                        ambil.setOnAction((ActionEvent e) -> {
                            ambilPemesanan(item.getPemesananHead());
                        });
                        MenuItem batal = new MenuItem("Batal Pemesanan");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPemesanan(item.getPemesananHead());
                        });
                        MenuItem print = new MenuItem("Print Surat Pemesanan");
                        print.setOnAction((ActionEvent e) -> {
                            printPemesanan(item.getPemesananHead());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPemesanan();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        if(item.getPemesananHead().getStatusAmbil().equals("false") && item.getPemesananHead().getStatusBatal().equals("false")){
                            rowMenu.getItems().add(ambil);
                            rowMenu.getItems().add(print);
                            rowMenu.getItems().add(batal);
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPemesanan(row.getItem().getPemesananHead());
                }
            });
            return row;
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananDetail> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPemesanan);
        pemesananHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.addAll("Belum Diambil","Sudah Diambil","Semua");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Belum Diambil");
        getPemesanan();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getPemesanan(){
        Task<List<PemesananDetail>> task = new Task<List<PemesananDetail>>() {
            @Override 
            public List<PemesananDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    String statusAmbil = "%";
                    String statusBatal = "false";
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Diambil")){
                        statusAmbil = "false";
                    }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Sudah Diambil")){
                        statusAmbil = "true";
                    }
                    List<PemesananHead> listPemesananHead = PemesananHeadDAO.getAllByDateAndStatusAmbilAndStatusBatal(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                        statusAmbil, statusBatal);
                    List<PemesananDetail> listPemesananDetail = PemesananDetailDAO.getAllByDateAndStatusAmbilAndStatusBatal(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                        statusAmbil, statusBatal);
                    for(PemesananDetail d : listPemesananDetail){
                        for(PemesananHead p : listPemesananHead){
                            if(d.getNoPemesanan().equals(p.getNoPemesanan()))
                                d.setPemesananHead(p);
                        }
                    }
                    return listPemesananDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
            pemesananHeadTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPemesanan() {
        try{
            filterData.clear();
            for (PemesananDetail d : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getPemesananHead().getNoPemesanan())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getPemesananHead().getTglPemesanan())))||
                        checkColumn(d.getPemesananHead().getNama())||
                        checkColumn(d.getPemesananHead().getAlamat())||
                        checkColumn(d.getPemesananHead().getKodeSales())||
                        checkColumn(d.getPemesananHead().getKeterangan())||
                        checkColumn(rp.format(d.getPemesananHead().getTitipUang()))||
                        checkColumn(d.getKodeJenis())||
                        checkColumn(d.getNamaBarang())||
                        checkColumn(gr.format(d.getBerat()))||
                        checkColumn(rp.format(d.getOngkos()))||
                        checkColumn(rp.format(d.getHarga())))
                        filterData.add(d);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalOngkos = 0;
        double totalHarga = 0;
        double totalPemesanan = 0;
        double totalPembayaran = 0;
        List<PemesananHead> listPemesananHead = new ArrayList<>();
        for(PemesananDetail d : filterData){
            totalBerat = totalBerat + d.getBerat();
            totalOngkos = totalOngkos + d.getOngkos();
            totalHarga = totalHarga + d.getHarga();
            totalPemesanan = totalPemesanan + d.getOngkos() + d.getHarga();
            
            if(!listPemesananHead.contains(d.getPemesananHead()))
                listPemesananHead.add(d.getPemesananHead());
        }
        for(PemesananHead p : listPemesananHead){
            totalPembayaran = totalPembayaran + p.getTitipUang();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalOngkosLabel.setText(rp.format(totalOngkos));
        totalHargaLabel.setText(rp.format(totalHarga));
        totalPemesananLabel.setText(rp.format(totalPemesanan));
        totalTitipUangLabel.setText(rp.format(totalPembayaran));
    }
    private void newPemesanan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Pemesanan.fxml");
        PemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPemesanan();
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.namaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama pelanggan masih kosong");
            else if(controller.alamatField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Alamat pelanggan masih kosong");
            else if(controller.listPemesananDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data pemesanan masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else if(Double.parseDouble(controller.totalPemesananField.getText().replaceAll(",", ""))<
                    Double.parseDouble(controller.titipUangField.getText().replaceAll(",", "")))
                mainApp.showMessage(Modality.NONE, "Warning", "Titipan uang lebih besar daripada total pemesanan");
            else{
                PemesananHead p = new PemesananHead();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            p.setNoPemesanan("");
                            p.setTglPemesanan(Function.getSystemDate());
                            if(controller.m==null){
                                p.setKodeMember("");
                                p.setNama(controller.namaField.getText());
                                p.setAlamat(controller.alamatField.getText());
                                p.setNoTelp(controller.noTelpField.getText());
                            }else{
                                p.setKodeMember(controller.m.getKodeMember());
                                p.setNama(controller.m.getNama());
                                p.setAlamat(controller.m.getAlamat());
                                p.setNoTelp(controller.m.getNoTelp());
                            }
                            p.setKeterangan(controller.keteranganField.getText());
                            int noUrut = 1;
                            for(PemesananDetail d : controller.listPemesananDetail){
                                d.setNoUrut(noUrut);
                                noUrut = noUrut + 1;
                            }
                            p.setListPemesananDetail(controller.listPemesananDetail);
                            p.setTotalPemesanan(Double.parseDouble(controller.totalPemesananField.getText().replaceAll(",", "")));
                            p.setTitipUang(Double.parseDouble(controller.titipUangField.getText().replaceAll(",", "")));
                            p.setSisaPembayaran(Double.parseDouble(controller.totalPemesananField.getText().replaceAll(",", "")) - 
                                    Double.parseDouble(controller.titipUangField.getText().replaceAll(",", "")));
                            p.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                            p.setStatusAmbil("false");
                            p.setTglAmbil("2000-01-01 00:00:00");
                            p.setSalesAmbil("");
                            p.setStatusBatal("false");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePemesanan(conCabang, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((ev) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pemesanan berhasil disimpan");
                        if(controller.printSuratPemesananCheck.isSelected()){
                            try{
                                for(PemesananDetail d : controller.listPemesananDetail){
                                    d.setPemesananHead(p);
                                }
                                PrintOut po = new PrintOut();
                                po.printSuratPemesanan(controller.listPemesananDetail);
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
    private void detailPemesanan(PemesananHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Pemesanan.fxml");
        PemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p);
    }
    private void batalPemesanan(PemesananHead p){
        try{
            if(p.getStatusAmbil().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah diambil");
            else if(p.getStatusBatal().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah dibatal");
            else{
                if(!p.getNoPemesanan().substring(8,14).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/InputKodeSales.fxml");
                    InputKodeSalesController controller = loader.getController();
                    controller.setMainApp(mainApp, mainApp.MainStage, child);
                    controller.saveButton.setOnAction((event) -> {
                        if(controller.kodeSalesField.getText().equals(""))
                            mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
                        else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                            mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
                        else
                            batal(p, controller.kodeSalesField.getText());
                    });
                }else{
                    MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                                "Batal pemesanan "+p.getNoPemesanan()+" ?");
                    x.OK.setOnAction((ActionEvent ex) -> {
                        mainApp.closeMessage();
                        batal(p, "");
                    });
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void batal(PemesananHead p, String kodeSales){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/Verifikasi.fxml");
        VerifikasiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                mainApp.closeDialog(mainApp.MainStage, child);
                if(Function.verifikasi(controller.usernameField.getText(), 
                        controller.passwordField.getText(), "Batal Pemesanan")){

                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                p.setStatusBatal("true");
                                p.setTglBatal(Function.getSystemDate());
                                p.setUserBatal(controller.usernameField.getText());
                                return Service.saveBatalPemesanan(conCabang, p, kodeSales);
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((e) -> {
                        mainApp.closeLoading();
                        getPemesanan();
                        String status = task.getValue();
                        if(status.equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Pemesanan berhasil dibatal");
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
    private boolean statusRewardPoin = false;
    private void ambilPemesanan(PemesananHead psn){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPemesanan(psn);
        controller.saveButton.setOnAction((event) -> {
            if(controller.memberRadio.isSelected() && controller.m==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
            else if(controller.listPenjualanDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data penjualan masih kosong");
            else if(controller.kodeSalesField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
            else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
            else if(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", ""))<psn.getTitipUang())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah penjualan kurang dari jumlah uang yang dititipkan");
            else{
                Stage child = new Stage();
                FXMLLoader loaderPembayaran = mainApp.showDialog(stage ,child, "View/Dialog/Pembayaran.fxml");
                PembayaranController controllerPembayaran = loaderPembayaran.getController();
                controllerPembayaran.setMainApp(mainApp, stage, child);
                controllerPembayaran.setTotalPenjualan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")),
                        controller.m);
                controllerPembayaran.setTitipanUang(psn.getTitipUang());
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
                                    statusRewardPoin = false;
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
                                            return Service.saveAmbilPemesananPoin(conPusat, conCabang, psn, p);
                                        }
                                    }else
                                        return Service.saveAmbilPemesanan(conCabang, psn, p);
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((evt) -> {
                            mainApp.closeLoading();
                            getPemesanan();
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.closeDialog(stage, child);
                                mainApp.closeDialog(mainApp.MainStage, stage);
                                mainApp.showMessage(Modality.NONE, "Success", "Ambil pemesanan berhasil disimpan");
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
    private void printPemesanan(PemesananHead p){
        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
            List<PemesananDetail> listPemesanan = PemesananDetailDAO.getAllByNoPemesanan(conCabang, p.getNoPemesanan());
            for(PemesananDetail d : listPemesanan){
                d.setPemesananHead(p);
            }
            PrintOut po = new PrintOut();
            po.printSuratPemesanan(listPemesanan);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
