/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembayaranPenjualanDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailPembayaranController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PembayaranController;
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
public class DataPenjualanController  {

    @FXML private TableView<PenjualanDetail> penjualanHeadTable;
    @FXML private TableColumn<PenjualanDetail, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanDetail, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaColumn;
    @FXML private TableColumn<PenjualanDetail, String> alamatColumn;
    @FXML private TableColumn<PenjualanDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML private TableColumn<PenjualanDetail, Number> ongkosColumn;
    @FXML private TableColumn<PenjualanDetail, String> kodeSalesColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaLabel;
    @FXML private Label totalOngkosLabel;
    @FXML private Label totalPenjualanLabel;
    private Main mainApp;   
    private final ObservableList<PenjualanDetail> allPenjualan = FXCollections.observableArrayList();
    private final ObservableList<PenjualanDetail> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPenjualanHead().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().alamatProperty());
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(gr));
        ongkosColumn.setCellValueFactory(cellData -> cellData.getValue().ongkosProperty());
        ongkosColumn.setCellFactory(col -> getTableCell(gr));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().kodeSalesProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Penjualan");
        addNew.setOnAction((ActionEvent e) -> {
            newPenjualan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        penjualanHeadTable.setContextMenu(rowMenu);
        penjualanHeadTable.setRowFactory(table -> {
            TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>() {
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Penjualan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenjualan();
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualan(item.getPenjualanHead());
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Kekurangan");
                        bayar.setOnAction((ActionEvent e) -> {
                            pembayaranKekurangan(item.getPenjualanHead());
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            detailPembayaran(item.getPenjualanHead());
                        });
                        MenuItem batal = new MenuItem("Batal Penjualan");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPenjualan(item.getPenjualanHead());
                        });
                        MenuItem print = new MenuItem("Print Surat Penjualan");
                        print.setOnAction((ActionEvent e) -> {
                            printPenjualan(item.getPenjualanHead());
                        });
                        MenuItem printKurang = new MenuItem("Print Surat Kurang Bayar");
                        printKurang.setOnAction((ActionEvent e) -> {
                            printKurangBayar(item.getPenjualanHead());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(detailBayar);
                        rowMenu.getItems().add(print);
                        if(item.getPenjualanHead().getSisaPembayaran()!=0){
                            rowMenu.getItems().add(bayar);
                            rowMenu.getItems().add(printKurang);
                        }
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPenjualan(row.getItem().getPenjualanHead());
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanDetail> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.addAll("Semua","Lunas","Belum Lunas");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Semua");
        getPenjualan();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanDetail>> task = new Task<List<PenjualanDetail>>() {
            @Override 
            public List<PenjualanDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByDateAndStatus(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<PenjualanDetail> listPenjualan = new ArrayList<>();
                    for(PenjualanDetail d : listPenjualanDetail){
                        d.setPenjualanHead(PenjualanHeadDAO.get(conCabang, d.getNoPenjualan()));
                        if(statusCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                            listPenjualan.add(d);
                        }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Lunas")){
                            if(d.getPenjualanHead().getSisaPembayaran()==0)
                                listPenjualan.add(d);
                        }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas")){
                            if(d.getPenjualanHead().getSisaPembayaran()!=0)
                                listPenjualan.add(d);
                        }
                    }
                    return listPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanDetail d : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getPenjualanHead().getTglPenjualan())))||
                        checkColumn(d.getKodeBarcode())||
                        checkColumn(d.getNamaBarang())||
                        checkColumn(gr.format(d.getBerat()))||
                        checkColumn(rp.format(d.getHargaJual()))||
                        checkColumn(rp.format(d.getOngkos()))||
                        checkColumn(d.getPenjualanHead().getNama())||
                        checkColumn(d.getPenjualanHead().getAlamat())||
                        checkColumn(d.getPenjualanHead().getNoTelp())||
                        checkColumn(d.getPenjualanHead().getKodeSales()))
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
        double totalHarga = 0;
        double totalOngkos = 0;
        double totalPenjualan = 0;
        for(PenjualanDetail d : filterData){
            totalBerat = totalBerat + d.getBerat();
            totalHarga = totalHarga + d.getHargaJual();
            totalOngkos = totalOngkos + d.getOngkos();
            totalPenjualan = totalPenjualan + d.getHargaJual() + d.getOngkos();
        }
        totalBeratLabel.setText(rp.format(totalBerat));
        totalHargaLabel.setText(rp.format(totalHarga));
        totalOngkosLabel.setText(rp.format(totalOngkos));
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    private boolean statusRewardPoin = false;
    private void newPenjualan(){
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
                                    statusRewardPoin = false;
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
                        task.setOnSucceeded((evt) -> {
                            mainApp.closeLoading();
                            getPenjualan();
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
    private void detailPenjualan(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }
    private void batalPenjualan(PenjualanHead p){
        try{
            if(!p.getNoPenjualan().substring(7,13).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal penjualan "+p.getNoPenjualan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, mainApp.MainStage, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(mainApp.MainStage, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Penjualan Beda Tanggal")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            p.setStatus("false");
                                            p.setTglBatal(Function.getSystemDate());
                                            p.setUserBatal(controller.usernameField.getText());
                                            boolean statusRewardPoin = false;
                                            p.setListPembayaran(PembayaranPenjualanDAO.getAllByNoPenjualanAndStatus(conCabang, p.getNoPenjualan(), "true"));
                                            for(PembayaranPenjualan pp : p.getListPembayaran()){
                                                if(pp.getJenisPembayaran().equals("Reward Poin"))
                                                    statusRewardPoin = true;
                                            }
                                            if(p.getMember()!=null || statusRewardPoin){
                                                try(Connection conPusat = KoneksiPusat.getConnection()){
                                                    return Service.saveBatalPenjualanPoinBedaTanggal(conPusat, conCabang, p);
                                                }
                                            }else
                                                return Service.saveBatalPenjualanBedaTanggal(conCabang, p);
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPenjualan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan berhasil dibatal");
                                    }else
                                        mainApp.showMessage(Modality.NONE, "Failed", status);
                                });
                                task.setOnFailed((e) -> {
                                    task.getException().printStackTrace();
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
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal penjualan "+p.getNoPenjualan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, mainApp.MainStage, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(mainApp.MainStage, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Penjualan")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            p.setStatus("false");
                                            p.setTglBatal(Function.getSystemDate());
                                            p.setUserBatal(controller.usernameField.getText());
                                            boolean statusRewardPoin = false;
                                            p.setListPembayaran(PembayaranPenjualanDAO.getAllByNoPenjualanAndStatus(conCabang, p.getNoPenjualan(), "true"));
                                            for(PembayaranPenjualan pp : p.getListPembayaran()){
                                                if(pp.getJenisPembayaran().equals("Reward Poin"))
                                                    statusRewardPoin = true;
                                            }
                                            if(p.getMember()!=null || statusRewardPoin){
                                                try(Connection conPusat = KoneksiPusat.getConnection()){
                                                    return Service.saveBatalPenjualanPoin(conPusat, conCabang, p);
                                                }
                                            }else
                                                return Service.saveBatalPenjualan(conCabang, p);
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPenjualan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan berhasil dibatal");
                                    }else
                                        mainApp.showMessage(Modality.NONE, "Failed", status);
                                });
                                task.setOnFailed((e) -> {
                                    task.getException().printStackTrace();
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void pembayaranKekurangan(PenjualanHead p){
        if(p.getSisaPembayaran()==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan tidak ada kekurangan pembayaran");
        }else{
            Stage child = new Stage();
            FXMLLoader loaderPembayaran = mainApp.showDialog(mainApp.MainStage ,child, "View/Dialog/Pembayaran.fxml");
            PembayaranController controller = loaderPembayaran.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, child);
            controller.setPembayaranKekurangan(p.getSisaPembayaran(), p.getKodeMember());
            controller.saveButton.setOnAction((ev) -> {
                if(controller.kodeSalesField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong");
                else if(Function.ceksales(controller.kodeSalesField.getText()).equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih salah");
                else if(controller.sisaPembayaranLabel.getText().equals("Sisa Pembayaran"))
                    mainApp.showMessage(Modality.NONE, "Warning", "Masih ada sisa pembayaran");
                else{
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                double pembayaran = 0;
                                boolean statusRewardPoin = false;
                                for(PembayaranPenjualan pp : controller.listPembayaran){
                                    p.setMember(controller.getMember);
                                    pp.setTglPembayaran(Function.getSystemDate());
                                    pp.setKodeSales(Function.ceksales(controller.kodeSalesField.getText()));
                                    pp.setStatus("true");
                                    pp.setTglBatal("2000-01-01 00:00:00");
                                    pp.setUserBatal("");

                                    pembayaran = pembayaran + pp.getJumlahPembayaran();

                                    if(pp.getJenisPembayaran().equals("Reward Poin"))
                                        statusRewardPoin = true;
                                }
                                p.setListPembayaran(controller.listPembayaran);
                                p.setPembayaran(p.getPembayaran()+pembayaran);
                                p.setSisaPembayaran(p.getSisaPembayaran()-pembayaran);

                                if(p.getMember()!=null || statusRewardPoin){
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        return Service.savePembayaranKekuranganPoin(conPusat, conCabang, p);
                                    }
                                }else
                                    return Service.savePembayaranKekurangan(conCabang, p);
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((e) -> {
                        mainApp.closeLoading();
                        getPenjualan();
                        String status = task.getValue();
                        if(status.equals("true")){
                            mainApp.closeDialog(mainApp.MainStage, child);
                            mainApp.showMessage(Modality.NONE, "Success", "Pembayaran kekurangan berhasil disimpan");
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
    private void detailPembayaran(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPembayaran.fxml");
        DetailPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.getPembayaran(p.getNoPenjualan());
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
                        MenuItem batal = new MenuItem("Batal Pembayaran");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembayaran(item);
                        });
                        MenuItem print = new MenuItem("Print Slip Pembayaran Poin");
                        print.setOnAction((ActionEvent e) -> {
                            printSlipPembayaranPoin(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            controller.pembayaranTable.refresh();
                        });
                        if(!item.getNoPembayaran().substring(4,10).equals(p.getNoPenjualan().substring(7,13)))
                            rowMenu.getItems().add(batal);
                        if(item.getJenisPembayaran().equals("Reward Poin"))
                            rowMenu.getItems().add(print);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }
    private void batalPembayaran(PembayaranPenjualan pp){
        try{
            if(!pp.getNoPembayaran().substring(4,10).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal pembayaran "+pp.getNoPenjualan()+" ?");
                x.OK.setOnAction((ActionEvent ex) -> {
                    mainApp.closeMessage();

                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, mainApp.MainStage, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(mainApp.MainStage, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Batal Pembayaran")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            PenjualanHead p = PenjualanHeadDAO.get(conCabang, pp.getNoPenjualan());
                                            pp.setStatus("false");
                                            pp.setTglBatal(Function.getSystemDate());
                                            pp.setUserBatal(controller.usernameField.getText());
                                            if(!p.getKodeMember().equals("") || pp.getJenisPembayaran().equals("Reward Poin")){
                                                try(Connection conPusat = KoneksiPusat.getConnection()){
                                                    return Service.saveBatalPembayaranKekuranganPoin(conPusat, conCabang, p, pp);
                                                }
                                            }else
                                                return Service.saveBatalPembayaranKekurangan(conCabang, pp);
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPenjualan();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran berhasil dibatal");
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void printPenjualan(PenjualanHead p){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
            for(PenjualanDetail d : p.getListPenjualanDetail()){
                d.setBarang(BarangDAO.get(con, d.getKodeBarcode()));
            }
            PrintOut po = new PrintOut();
            po.printSuratPenjualan(p);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void printKurangBayar(PenjualanHead p){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan());
            for(PenjualanDetail d : listPenjualanDetail){
                d.setPenjualanHead(p);
            }
            PrintOut po = new PrintOut();
            po.printSuratKurangBayar(listPenjualanDetail);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void printSlipPembayaranPoin(PembayaranPenjualan p){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<PembayaranPenjualan> listPembayaran = new ArrayList<>();
            p.setMember(MemberDAO.get(conPusat, p.getKeterangan()));
            listPembayaran.add(p);
            PrintOut po = new PrintOut();
            po.printSlipPembayaranPoin(listPembayaran);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
