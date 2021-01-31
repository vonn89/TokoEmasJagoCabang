/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianHeadDAO;
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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.PembelianController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
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
public class DataPembelianController {

    @FXML private TableView<PembelianDetail> pembelianHeadTable;
    @FXML private TableColumn<PembelianDetail, String> noPembelianColumn;
    @FXML private TableColumn<PembelianDetail, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PembelianDetail, Number> persentaseEmasColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaBeliColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeSalesColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalPembelianLabel;
    private Main mainApp;   
    private final ObservableList<PembelianDetail> allPembelian = FXCollections.observableArrayList();
    private final ObservableList<PembelianDetail> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPembelianHead().getTglPembelian())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(gr));
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> getTableCell(gr));
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> getTableCell(gr));
        persentaseEmasColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseEmasProperty());
        persentaseEmasColumn.setCellFactory(col -> getTableCell(gr));
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getPembelianHead().kodeSalesProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Pembelian");
        addNew.setOnAction((ActionEvent e) -> {
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pembelianHeadTable.setContextMenu(rowMenu);
        pembelianHeadTable.setRowFactory(table -> {
            TableRow<PembelianDetail> row = new TableRow<PembelianDetail>() {
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pembelian");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPembelian(item.getPembelianHead());
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembelian(item.getPembelianHead());
                        });
                        MenuItem print = new MenuItem("Print Surat Pembelian");
                        print.setOnAction((ActionEvent e) -> {
                            printPembelian(item.getPembelianHead());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(print);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPembelian(row.getItem().getPembelianHead());
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianDetail> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
        pembelianHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getPembelian();
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Ganti Tanggal")){
                tglAwalPicker.setDisable(!o.isStatus());
                tglAkhirPicker.setDisable(!o.isStatus());
            }
        }
    } 
    @FXML
    private void getPembelian(){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
            @Override 
            public List<PembelianDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<PembelianDetail> listPembelianDetail = PembelianDetailDAO.getAllDateAndStatusAndStatusAmbil(
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true", "%");
                    for(PembelianDetail d : listPembelianDetail){
                        d.setPembelianHead(PembelianHeadDAO.get(conCabang, d.getNoPembelian()));
                    }
                    return listPembelianDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
            pembelianHeadTable.refresh();
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
    private void searchPembelian() {
        try{
            filterData.clear();
            for (PembelianDetail p : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(p);
                else{
                    if(checkColumn(p.getPembelianHead().getNoPembelian())||
                        checkColumn(tglLengkap.format(tglSql.parse(p.getPembelianHead().getTglPembelian())))||
                        checkColumn(p.getKodeKategori())||
                        checkColumn(p.getKodeJenis())||
                        checkColumn(p.getNamaBarang())||
                        checkColumn(p.getPembelianHead().getKodeSales())||
                        checkColumn(gr.format(p.getQty()))||
                        checkColumn(gr.format(p.getBeratKotor()))||
                        checkColumn(gr.format(p.getBeratBersih()))||
                        checkColumn(gr.format(p.getPersentaseEmas()))||
                        checkColumn(rp.format(p.getHargaBeli())))
                        filterData.add(p);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalQty = 0;
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPembelian = 0;
        for(PembelianDetail p : filterData){
            totalQty = totalQty + p.getQty();
            totalBeratKotor = totalBeratKotor + p.getBeratKotor();
            totalBeratBersih = totalBeratBersih + p.getBeratBersih();
            totalPembelian = totalPembelian + p.getHargaBeli();
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratKotorLabel.setText(gr.format(totalBeratKotor));
        totalBeratBersihLabel.setText(gr.format(totalBeratBersih));
        totalPembelianLabel.setText(rp.format(totalPembelian));
    }
    private void newPembelian(){
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
                    getPembelian();
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
    private void detailPembelian(PembelianHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPembelian(p);
    }
    private void batalPembelian(PembelianHead p){
        try{
            if(!p.getNoPembelian().substring(4,10).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                mainApp.showMessage(Modality.NONE, "Warning", "Pembelian tidak dapat dibatal, karena sudah berbeda tanggal");
            }else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Batal pembelian "+p.getNoPembelian()+" ?");
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
                                    controller.passwordField.getText(), "Batal Pembelian")){

                                Task<String> task = new Task<String>() {
                                    @Override 
                                    public String call() throws Exception{
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            p.setStatus("false");
                                            p.setTglBatal(Function.getSystemDate());
                                            p.setUserBatal(controller.usernameField.getText());
                                            return Service.saveBatalPembelian(conCabang, p);
                                        }
                                    }
                                };
                                task.setOnRunning((e) -> {
                                    mainApp.showLoadingScreen();
                                });
                                task.setOnSucceeded((e) -> {
                                    mainApp.closeLoading();
                                    getPembelian();
                                    String status = task.getValue();
                                    if(status.equals("true")){
                                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian berhasil dibatal");
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
    private void printPembelian(PembelianHead p){
        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
            List<PembelianDetail> listPembelian = PembelianDetailDAO.getAllByNoPembelian(conCabang, p.getNoPembelian());
            for(PembelianDetail d : listPembelian){
                d.setPembelianHead(p);
            }
            PrintOut po = new PrintOut();
            po.printBuktiPembelian(listPembelian);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
