/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataBarangController {

    @FXML private TableView<Barang> barangTable;
    @FXML private TableColumn<Barang,Boolean> checkColumn;
    @FXML private TableColumn<Barang, String> kodeBarangColumn;
    @FXML private TableColumn<Barang, String> kodeBarcodeColumn;
    @FXML private TableColumn<Barang, String> namaBarangColumn;
    @FXML private TableColumn<Barang, String> kodeKategoriColumn;
    @FXML private TableColumn<Barang, String> kodeJenisColumn;
    @FXML private TableColumn<Barang, String> kodeInternColumn;
    @FXML private TableColumn<Barang, String> kadarColumn;
    @FXML private TableColumn<Barang, Number> beratColumn;
    @FXML private TableColumn<Barang, Number> beratAsliColumn;
    @FXML private TableColumn<Barang, Number> hargaJualColumn;
    @FXML private TableColumn<Barang, String> tglBarcodeColumn;
    @FXML private TableColumn<Barang, String> userBarcodeColumn;
    @FXML private TableColumn<Barang, String> asalBarangColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> kategoriCombo;
    @FXML private ComboBox<String> jenisCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    
    private List<Jenis> listJenis;
    private List<Kategori> listKategori;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allJenis = FXCollections.observableArrayList();
    private ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private ObservableList<Barang> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        hargaJualColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getBerat()*cellData.getValue().getKategori().getHargaJual());
        });
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().asalBarangProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getInputDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().inputByProperty());
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer i) -> {
            return barangTable.getItems().get(i).checkedProperty();
        }));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Cetak Barcode");
        cetak.setOnAction((ActionEvent e)->{
            cetakBarcode();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().add(cetak);
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<Barang>() {
                @Override
                public void updateItem(Barang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e)->{
                            cetakBarcode();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(cetak);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().isChecked())
                            row.getItem().setChecked(false);
                        else
                            row.getItem().setChecked(true);
                        hitungTotal();
                    }
                }
            });
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
        barangTable.setItems(filterData);
        kategoriCombo.setItems(allKategori);
        jenisCombo.setItems(allJenis);
    }      
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getKategoriJenis();
    } 
    private void getKategoriJenis(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    listKategori = KategoriDAO.getAll(conCabang);
                    listJenis = JenisDAO.getAll(conCabang);
                    return null;
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
            for(Kategori k : listKategori){
                allKategori.add(k.getKodeKategori());
            }
            kategoriCombo.getSelectionModel().select("Semua");
            barangTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setJenis(){
        allJenis.clear();
        allJenis.add("Semua");
        for(Jenis j : listJenis){
            if(kategoriCombo.getSelectionModel().getSelectedItem().equals("Semua")||
                    kategoriCombo.getSelectionModel().getSelectedItem().equals(j.getKodeKategori()))
                allJenis.add(j.getKodeJenis());
        }
        jenisCombo.getSelectionModel().select("Semua");
    }
    @FXML
    private void getBarang(){
        Task<List<Barang>> task = new Task<List<Barang>>() {
            @Override 
            public List<Barang> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    String kategori = "%";
                    String jenis = "%";
                    if(!kategoriCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        kategori = kategoriCombo.getSelectionModel().getSelectedItem();
                    if(!jenisCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        jenis = jenisCombo.getSelectionModel().getSelectedItem();
                    List<Barang> allBarang = BarangDAO.getAllByKategoriAndJenisAndStatus(
                            conCabang, kategori, jenis, "true");
                    for(Barang b : allBarang){
                        for(Kategori k : listKategori){
                            if(k.getKodeKategori().equals(b.getKodeKategori()))
                                b.setKategori(k);
                        }
                        b.setChecked(checkAll.isSelected());
                    }
                    return allBarang;
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
            barangTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAllHandle(){
        for(Barang b: allBarang){
            b.setChecked(checkAll.isSelected());
        }
        barangTable.refresh();
        hitungTotal();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchBarang() {
        try{
            filterData.clear();
            for (Barang b : allBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(b);
                else{
                    if(checkColumn(b.getKodeKategori())||
                        checkColumn(b.getKodeBarcode())||
                        checkColumn(b.getKodeBarang())||
                        checkColumn(b.getNamaBarang())||
                        checkColumn(b.getKodeJenis())||
                        checkColumn(b.getKodeIntern())||
                        checkColumn(b.getKadar())||
                        checkColumn(b.getAsalBarang())||
                        checkColumn(b.getInputBy())||
                        checkColumn(tglLengkap.format(tglSql.parse(b.getInputDate())))||
                        checkColumn(gr.format(b.getBerat()))||
                        checkColumn(gr.format(b.getBeratAsli()))||
                        checkColumn(rp.format(b.getBerat()*b.getKategori().getHargaJual())))
                        filterData.add(b);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        double totalBeratAsli = 0;
        for(Barang b : filterData){
            if(b.isChecked()){
                totalQty = totalQty + 1;
                totalBerat = totalBerat + b.getBerat();
                totalBeratAsli = totalBeratAsli + b.getBeratAsli();
            }
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratAsliLabel.setText(gr.format(totalBeratAsli));
    }
    private void detailBarang(Barang b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(b);
    }
    private void cetakBarcode(){
        List<Barang> barang = new ArrayList<>();
        for(Barang b : filterData){
            if(b.isChecked())
                barang.add(b);
        }
        if(barang.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        else{        
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Cetak "+totalQtyLabel.getText()+" barcode ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                try{ 
                    PrintOut po = new PrintOut();
                    po.printBarcode(barang);
                    mainApp.closeMessage();
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }
}
