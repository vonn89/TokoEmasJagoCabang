/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokOpnameHeadDAO;
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
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameHead;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailStokOpnameController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.NewStokOpnameController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.StokOpnameController;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
public class DataStokOpnameBarangController {

    @FXML private TableView<StokOpnameHead> stokOpnameTable;
    @FXML private TableColumn<StokOpnameHead, String> noStokOpnameColumn;
    @FXML private TableColumn<StokOpnameHead, String> tglStokOpnameColumn;
    @FXML private TableColumn<StokOpnameHead, String> kodeJenisColumn;
    @FXML private TableColumn<StokOpnameHead, Number> totalQtyColumn;
    @FXML private TableColumn<StokOpnameHead, Number> totalBeratColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private final ObservableList<StokOpnameHead> allStokOpname = FXCollections.observableArrayList();
    private final ObservableList<StokOpnameHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noStokOpnameColumn.setCellValueFactory(cellData -> cellData.getValue().noStokOpnameProperty());
        tglStokOpnameColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglStokOpname())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglStokOpnameColumn.setComparator(Function.sortDate(tglLengkap));
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Stok Opname");
        addNew.setOnAction((ActionEvent e) -> {
            newStokOpname();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getStokOpname();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        stokOpnameTable.setContextMenu(rowMenu);
        stokOpnameTable.setRowFactory(table -> {
            TableRow<StokOpnameHead> row = new TableRow<StokOpnameHead>() {
                @Override
                public void updateItem(StokOpnameHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Stok Opname");
                        addNew.setOnAction((ActionEvent e) -> {
                            newStokOpname();
                        });
                        MenuItem lanjut = new MenuItem("Lanjutkan Stok Opname");
                        lanjut.setOnAction((ActionEvent e) -> {
                            lanjutStokOpname(item);
                        });
                        MenuItem detail = new MenuItem("Detail Stok Opname");
                        detail.setOnAction((ActionEvent e) -> {
                            detailStokOpname(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getStokOpname();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(lanjut);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailStokOpname(row.getItem());
                }
            });
            return row;
        });
        allStokOpname.addListener((ListChangeListener.Change<? extends StokOpnameHead> change) -> {
            searchStokOpname();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchStokOpname();
        });
        filterData.addAll(allStokOpname);
        stokOpnameTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getStokOpname();
    } 
    @FXML
    private void getStokOpname(){
        Task<List<StokOpnameHead>> task = new Task<List<StokOpnameHead>>() {
            @Override 
            public List<StokOpnameHead> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<StokOpnameHead> listStokOpname = StokOpnameHeadDAO.getAllByDate( 
                        conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    return listStokOpname;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allStokOpname.clear();
            allStokOpname.addAll(task.getValue());
            stokOpnameTable.refresh();
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
    private void searchStokOpname() {
        try{
            filterData.clear();
            for (StokOpnameHead t : allStokOpname) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoStokOpname())||
                        checkColumn(t.getKodeJenis())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglStokOpname())))||
                        checkColumn(rp.format(t.getTotalQty()))||
                        checkColumn(gr.format(t.getTotalBerat())))
                        filterData.add(t);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private String noStokOpname;
    private void newStokOpname(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/NewStokOpname.fxml");
        NewStokOpnameController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        String kodeJenis = "";
                        List<Barang> listBarang = new ArrayList<>();
                        for(Jenis j : controller.allJenis){
                            if(j.getVerifikasi().equals("true")){
                                listBarang.addAll(BarangDAO.getAllByKategoriAndJenisAndStatus(
                                        conCabang, "%", j.getKodeJenis(), "true"));
                                if(kodeJenis.equals(""))
                                    kodeJenis = kodeJenis + j.getKodeJenis();
                                else
                                    kodeJenis = kodeJenis + ", "+ j.getKodeJenis();
                            }
                        }
                        if(kodeJenis.equals(""))
                            return "Jenis barang yang akan di stok opname belum dipilih";
                        else{
                            noStokOpname = StokOpnameHeadDAO.getId(conCabang);

                            int totalQty = 0;
                            double totalBerat = 0;
                            List<StokOpnameDetail> listDetail = new ArrayList<>();
                            for(Barang b : listBarang){
                                StokOpnameDetail d = new StokOpnameDetail();
                                d.setNoStokOpname(noStokOpname);
                                d.setStokBarang(0);
                                d.setKodeBarang(b.getKodeBarang());
                                d.setKodeBarcode(b.getKodeBarcode());
                                d.setNamaBarang(b.getNamaBarang());
                                d.setKodeIntern(b.getKodeIntern());
                                d.setKadar(b.getKadar());
                                d.setBerat(b.getBerat());
                                d.setBeratAsli(b.getBeratAsli());
                                listDetail.add(d);
                                
                                totalQty = totalQty + 1;
                                totalBerat = totalBerat + b.getBerat();
                            }
                            
                            StokOpnameHead s = new StokOpnameHead();
                            s.setNoStokOpname(noStokOpname);
                            s.setTglStokOpname(Function.getSystemDate());
                            s.setKodeJenis(kodeJenis);
                            s.setTotalQty(totalQty);
                            s.setTotalBerat(totalBerat);
                            s.setListStokOpnameDetail(listDetail);
                            
                            return Service.saveStokOpnameHead(conCabang, s);
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getStokOpname();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    
                    Stage child = new Stage();
                    FXMLLoader loader2 = mainApp.showDialog(mainApp.MainStage ,child, "View/Dialog/StokOpname.fxml");
                    StokOpnameController controller2 = loader2.getController();
                    controller2.setMainApp(mainApp, mainApp.MainStage, child);
                    controller2.setStokOpname(noStokOpname);
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void detailStokOpname(StokOpnameHead s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailStokOpname.fxml");
        DetailStokOpnameController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setStokOpnameHead(s);
    }
    private void lanjutStokOpname(StokOpnameHead s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/StokOpname.fxml");
        StokOpnameController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setStokOpname(s.getNoStokOpname());
    }
}
