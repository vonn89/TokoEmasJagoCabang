/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class KartuStokBarangController  {

    @FXML private TableView<StokBarang> stokBarangTable;
    @FXML private TableColumn<StokBarang, String> tanggalColumn;
    @FXML private TableColumn<StokBarang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarang, Number> beratAkhirColumn;
    
    @FXML private Label kodeBarangLabel;
    @FXML private Label kodeBarangValueLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglNormal));
        
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> getTableCell(rp));
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> getTableCell(rp));
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> getTableCell(rp));
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> getTableCell(rp));
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokBarang> row = new TableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Stok Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        if(kodeBarangLabel.getText().equals("Kode Jenis"))
                            rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        stokBarangTable.setItems(allBarang);
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
    }
    public void setBarcode(String kodeBarcode){
        kodeBarangLabel.setText("Kode Barcode");
        kodeBarangValueLabel.setText(kodeBarcode);
        getBarang();
    }
    public void setJenis(String kodeJenis){
        kodeBarangLabel.setText("Kode Jenis");
        kodeBarangValueLabel.setText(kodeJenis);
        getBarang();
    }
    @FXML
    private void getBarang(){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    String kodeBarcode = "%";
                    String kodeJenis = "%";
                    if(kodeBarangLabel.getText().equals("Kode Barcode")){
                        kodeBarcode = kodeBarangValueLabel.getText();
                        List<StokBarang> listStokBarang = StokBarangDAO.getAllByDateAndGudangAndJenisAndBarcode(conCabang,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabang.getKodeCabang(), kodeJenis, kodeBarcode);
                        listStokBarang.sort(Comparator.comparing(StokBarang::getTanggal));
                        return listStokBarang;
                    }else if(kodeBarangLabel.getText().equals("Kode Jenis")){
                        kodeJenis = kodeBarangValueLabel.getText();
                        List<StokBarang> listStokBarang = StokBarangDAO.getAllByDateAndGudangAndJenisAndBarcode(conCabang,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabang.getKodeCabang(), kodeJenis, kodeBarcode);
                    
                        List<StokBarang> listBarang = new ArrayList<>();
                        for(StokBarang s : listStokBarang){
                            boolean status = true;
                            for(StokBarang temp : listBarang){
                                if(s.getKodeJenis().equals(temp.getKodeJenis())&&
                                        s.getTanggal().equals(temp.getTanggal())){
                                    status = false;
                                    temp.setStokAwal(temp.getStokAwal()+s.getStokAwal());
                                    temp.setBeratAwal(temp.getBeratAwal()+s.getBeratAwal());
                                    temp.setStokMasuk(temp.getStokMasuk()+s.getStokMasuk());
                                    temp.setBeratMasuk(temp.getBeratMasuk()+s.getBeratMasuk());
                                    temp.setStokKeluar(temp.getStokKeluar()+s.getStokKeluar());
                                    temp.setBeratKeluar(temp.getBeratKeluar()+s.getBeratKeluar());
                                    temp.setStokAkhir(temp.getStokAkhir()+s.getStokAkhir());
                                    temp.setBeratAkhir(temp.getBeratAkhir()+s.getBeratAkhir());
                                }
                            }
                            if(status){
                                StokBarang stokJenis = new StokBarang();
                                stokJenis.setTanggal(s.getTanggal());
                                stokJenis.setKodeKategori(s.getKodeKategori());
                                stokJenis.setKodeJenis(s.getKodeJenis());
                                stokJenis.setStokAwal(s.getStokAwal());
                                stokJenis.setBeratAwal(s.getBeratAwal());
                                stokJenis.setStokMasuk(s.getStokMasuk());
                                stokJenis.setBeratMasuk(s.getBeratMasuk());
                                stokJenis.setStokKeluar(s.getStokKeluar());
                                stokJenis.setBeratKeluar(s.getBeratKeluar());
                                stokJenis.setStokAkhir(s.getStokAkhir());
                                stokJenis.setBeratAkhir(s.getBeratAkhir());
                                listBarang.add(stokJenis);
                            }
                        }
                        listBarang.sort(Comparator.comparing(StokBarang::getTanggal));
                        return listBarang;
                    }else
                        return null;
                    
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allBarang.clear();
                allBarang.addAll(task.getValue());
                stokBarangTable.refresh();
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
    private void detailBarang(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailStokBarang.fxml");
        DetailStokBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s.getTanggal(), s.getKodeJenis());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
