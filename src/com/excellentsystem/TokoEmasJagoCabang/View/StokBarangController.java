/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailStokBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.KartuStokBarangController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class StokBarangController {

    @FXML private TableView<StokBarang> stokBarangTable;
    @FXML private TableColumn<StokBarang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarang, Number> beratAkhirColumn;
    
    @FXML private TableView<Kategori> kategoriTable;
    @FXML private TableColumn<Kategori, String> kategoriColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private DatePicker tglPicker;
    private Main mainApp;   
    private final ObservableList<Kategori> allKategori = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
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
        
        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(
                LocalDate.parse(sistem.getTglSystem())));
        
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
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(kartu);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailBarang(row.getItem());
                }
            });
            return row;
        });
        final ContextMenu rowMenuKategori = new ContextMenu();
        MenuItem refreshKategori = new MenuItem("Refresh");
        refreshKategori.setOnAction((ActionEvent event) -> {
            getKategori();
        });
        rowMenuKategori.getItems().addAll(refreshKategori);
        kategoriTable.setContextMenu(rowMenuKategori);
        kategoriTable.setRowFactory(table -> {
            TableRow<Kategori> row = new TableRow<Kategori>() {
                @Override
                public void updateItem(Kategori item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenuKategori);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refreshKategori = new MenuItem("Refresh");
                        refreshKategori.setOnAction((ActionEvent e) -> {
                            getKategori();
                        });
                        rowMenu.getItems().add(refreshKategori);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> getBarang());
        
        allBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
        kategoriTable.setItems(allKategori);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getKategori();
        getBarang();
    } 
    @FXML
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
            Kategori k = new Kategori();
            k.setKodeKategori("Semua");
            allKategori.add(k);
            allKategori.addAll(task.getValue());
            kategoriTable.getSelectionModel().select(k);
            getBarang();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void getBarang(){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    String kategori = "%";
                    if(kategoriTable.getSelectionModel().getSelectedItem()!=null && 
                            !kategoriTable.getSelectionModel().getSelectedItem().getKodeKategori().equals("Semua"))
                        kategori = kategoriTable.getSelectionModel().getSelectedItem().getKodeKategori();
                    List<StokBarang> allBarang = StokBarangDAO.getAllByTanggalAndGudangAndKategoriAndJenis(
                            conCabang, tglPicker.getValue().toString(), cabang.getKodeCabang(), kategori, "%");
                    List<StokBarang> listBarang = new ArrayList<>();
                    for(StokBarang s : allBarang){
                        boolean status = true;
                        for(StokBarang temp : listBarang){
                            if(s.getKodeJenis().equals(temp.getKodeJenis())){
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
                    listBarang.sort(Comparator.comparing(StokBarang::getKodeJenis));
                    return listBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBarang.clear();
            allBarang.addAll(task.getValue());
            stokBarangTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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
    private void searchBarang() {
        filterData.clear();
        for (StokBarang b : allBarang) {
            if(searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(b);
            else if(checkColumn(b.getKodeKategori())|| checkColumn(b.getKodeJenis()))
                filterData.add(b);
        }
        hitungTotal();
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        for(StokBarang b : filterData){
            totalQty = totalQty + b.getStokAkhir();
            totalBerat = totalBerat + b.getBeratAkhir();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }
    private void detailBarang(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/DetailStokBarang.fxml");
        DetailStokBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setBarang(tglPicker.getValue().toString(), s.getKodeJenis());
    }
    private void kartuStok(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/KartuStokBarang.fxml");
        KartuStokBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setJenis(s.getKodeJenis());
    }
}
