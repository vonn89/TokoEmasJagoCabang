/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
import java.sql.Connection;
import java.text.ParseException;
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
public class DetailKeuanganPemesananController  {

    @FXML private Label title;
    @FXML private TableView<PemesananHead> pemesananTable;
    @FXML private TableColumn<PemesananHead, String> noPemesananColumn;
    @FXML private TableColumn<PemesananHead, String> tglPemesananColumn;
    @FXML private TableColumn<PemesananHead, String> tglAmbilColumn;
    @FXML private TableColumn<PemesananHead, String> tglBatalColumn;
    
    @FXML private TableColumn<PemesananHead, String> salesTerimaColumn;
    @FXML private TableColumn<PemesananHead, String> salesAmbilColumn;
    @FXML private TableColumn<PemesananHead, String> salesBatalColumn;
    
    @FXML private TableColumn<PemesananHead, String> namaColumn;
    @FXML private TableColumn<PemesananHead, String> alamatColumn;
    @FXML private TableColumn<PemesananHead, String> keteranganColumn;
    @FXML private TableColumn<PemesananHead, Number> totalPemesananColumn;
    @FXML private TableColumn<PemesananHead, Number> titipUangColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPemesananLabel;
    @FXML private Label totalTitipUangLabel;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<PemesananHead> allPemesanan = FXCollections.observableArrayList();
    private final ObservableList<PemesananHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        tglPemesananColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));
        tglAmbilColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglAmbil())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglAmbilColumn.setComparator(Function.sortDate(tglLengkap));
        tglBatalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglBatal())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglBatalColumn.setComparator(Function.sortDate(tglLengkap));
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        totalPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().totalPemesananProperty());
        totalPemesananColumn.setCellFactory(col -> getTableCell(rp));
        titipUangColumn.setCellValueFactory(cellData -> cellData.getValue().titipUangProperty());
        titipUangColumn.setCellFactory(col -> getTableCell(rp));
        salesTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        salesAmbilColumn.setCellValueFactory(cellData -> cellData.getValue().salesAmbilProperty());
        salesBatalColumn.setCellValueFactory(cellData -> cellData.getValue().userBatalProperty());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pemesananTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        pemesananTable.setContextMenu(rowMenu);
        pemesananTable.setRowFactory(table -> {
            TableRow<PemesananHead> row = new TableRow<PemesananHead>() {
                @Override
                public void updateItem(PemesananHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pemesanan");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPemesanan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            pemesananTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPemesanan(row.getItem());
                }
            });
            return row;
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPemesanan);
        pemesananTable.setItems(filterData);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
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
    }
    public void getPemesanan(String tglAwal, String tglAkhir, String kategori){
        Task<List<PemesananHead>> task = new Task<List<PemesananHead>>() {
            @Override 
            public List<PemesananHead> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    if(kategori.equals("Terima Pemesanan")){
                        return PemesananHeadDAO.getAllByDateAndStatusAmbilAndStatusBatal(
                            conCabang, tglAwal, tglAkhir, "%", "%");
                    }else if(kategori.equals("Ambil Pemesanan")){
                        return PemesananHeadDAO.getAllByTglAmbil(conCabang, tglAwal, tglAkhir);
                    }else if(kategori.equals("Batal Pemesanan")){
                        List<PemesananHead> listPemesananHead = PemesananHeadDAO.getAllByTglBatal(conCabang, tglAwal, tglAkhir);
                        List<PemesananHead> listBatalPesan = new ArrayList<>();
                        for(PemesananHead p : listPemesananHead){
                            if(!p.getTglPemesanan().substring(0, 10).equals(p.getTglBatal().substring(0, 10)))
                                listBatalPesan.add(p);
                        }
                        return listBatalPesan;
                    }else{
                         return null;   
                    }
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            if(kategori.equals("Terima Pemesanan")){
                tglAmbilColumn.setVisible(false);
                salesAmbilColumn.setVisible(false);
                tglBatalColumn.setVisible(false);
                salesBatalColumn.setVisible(false);
            }else if(kategori.equals("Ambil Pemesanan")){
                tglAmbilColumn.setVisible(true);
                salesAmbilColumn.setVisible(true);
                tglBatalColumn.setVisible(false);
                salesBatalColumn.setVisible(false);
            }else if(kategori.equals("Batal Pemesanan")){
                tglAmbilColumn.setVisible(false);
                salesAmbilColumn.setVisible(false);
                tglBatalColumn.setVisible(true);
                salesBatalColumn.setVisible(true);
            }
            title.setText(kategori);
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
            pemesananTable.refresh();
            hitungTotal();
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
            for (PemesananHead d : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getNoPemesanan())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglPemesanan())))||
                        checkColumn(gr.format(d.getTotalPemesanan()))||
                        checkColumn(rp.format(d.getTitipUang()))||
                        checkColumn(d.getNama())||
                        checkColumn(d.getAlamat())||
                        checkColumn(d.getKeterangan())||
                        checkColumn(d.getKodeSales()))
                        filterData.add(d);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalPemesanan = 0;
        double totalTitipUang = 0;
        for(PemesananHead d : filterData){
            totalPemesanan = totalPemesanan + d.getTotalPemesanan();
            totalTitipUang = totalTitipUang + d.getTitipUang();
        }
        totalPemesananLabel.setText(rp.format(totalPemesanan));
        totalTitipUangLabel.setText(rp.format(totalTitipUang));
    }
    private void detailPemesanan(PemesananHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/Pemesanan.fxml");
        PemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPemesanan(p);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
