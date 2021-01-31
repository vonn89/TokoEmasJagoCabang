/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import java.sql.Connection;
import java.text.ParseException;
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
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailKeuanganHutangController {

    @FXML private Label title;
    @FXML private TableView<HutangHead> terimaHutangTable;
    @FXML private TableColumn<HutangHead, String> noHutangColumn;
    @FXML private TableColumn<HutangHead, String> tglHutangColumn;
    @FXML private TableColumn<HutangHead, String> kodeSalesColumn;
    @FXML private TableColumn<HutangHead, String> tglLunasColumn;
    @FXML private TableColumn<HutangHead, String> salesLunasColumn;
    @FXML private TableColumn<HutangHead, String> namaColumn;
    @FXML private TableColumn<HutangHead, String> alamatColumn;
    @FXML private TableColumn<HutangHead, Number> totalBeratColumn;
    @FXML private TableColumn<HutangHead, Number> totalHutangColumn;
    @FXML private TableColumn<HutangHead, Number> bungaPersenColumn;
    @FXML private TableColumn<HutangHead, Number> lamaPinjamColumn;
    @FXML private TableColumn<HutangHead, Number> bungaRpColumn;
    
    @FXML private HBox hBox;
    @FXML private TextField searchField;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHutangLabel;
    @FXML private Label totalBungaStringLabel;
    @FXML private Label totalBungaLabel;
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
        bungaRpColumn.setCellValueFactory(cellData -> cellData.getValue().bungaRpProperty());
        bungaRpColumn.setCellFactory(col -> getTableCell(rp));
        lamaPinjamColumn.setCellValueFactory(cellData -> cellData.getValue().lamaPinjamProperty());
        lamaPinjamColumn.setCellFactory(col -> getTableCell(rp));
        tglLunasColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglLunas())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglLunasColumn.setComparator(Function.sortDate(tglLengkap));
        salesLunasColumn.setCellValueFactory(cellData -> cellData.getValue().salesLunasProperty());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            terimaHutangTable.refresh();
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
                        MenuItem detailPelunasan = new MenuItem("Detail Pelunasan Hutang");
                        detailPelunasan.setOnAction((ActionEvent e) -> {
                            detailPelunasanHutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            terimaHutangTable.refresh();
                        });
                        rowMenu.getItems().add(detailTerima);
                        if(item.getStatusLunas().equals("true"))
                            rowMenu.getItems().add(detailPelunasan);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailHutang(row.getItem());
                }
            });
            return row;
        });
        allHutang.addListener((ListChangeListener.Change<? extends HutangHead> change) -> {
            searchHutang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        filterData.addAll(allHutang);
        terimaHutangTable.setItems(filterData);
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
        for(Node n : hBox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
    }
    public void getHutang(String tglAwal, String tglAkhir, String kategori){
        Task<List<HutangHead>> task = new Task<List<HutangHead>>() {
            @Override 
            public List<HutangHead> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    if(kategori.equals("Terima Hutang")){
                        return HutangHeadDAO.getAllByDateAndStatusLunasAndStatusBatal(
                            conCabang, tglAwal, tglAkhir, "%","false");
                    }else if(kategori.equals("Hutang Lunas")){
                        return HutangHeadDAO.getAllByTglLunas(conCabang, tglAwal, tglAkhir);
                    }else if(kategori.equals("Hutang Bunga")){
                        return HutangHeadDAO.getAllByTglLunas(conCabang, tglAwal, tglAkhir);
                    }else
                        return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            if(kategori.equals("Terima Hutang")){
                tglLunasColumn.setVisible(true);
                kodeSalesColumn.setVisible(true);
                tglLunasColumn.setVisible(false);
                salesLunasColumn.setVisible(false);
                lamaPinjamColumn.setVisible(false);
                bungaRpColumn.setVisible(false);
                totalBungaStringLabel.setVisible(false);
                totalBungaLabel.setVisible(false);
            }else if(kategori.equals("Hutang Lunas")){
                tglLunasColumn.setVisible(false);
                kodeSalesColumn.setVisible(false);
                tglLunasColumn.setVisible(true);
                salesLunasColumn.setVisible(true);
                lamaPinjamColumn.setVisible(true);
                bungaRpColumn.setVisible(true);
                totalBungaStringLabel.setVisible(true);
                totalBungaLabel.setVisible(true);
            }else if(kategori.equals("Hutang Bunga")){
                tglLunasColumn.setVisible(false);
                kodeSalesColumn.setVisible(false);
                tglLunasColumn.setVisible(true);
                salesLunasColumn.setVisible(true);
                lamaPinjamColumn.setVisible(true);
                bungaRpColumn.setVisible(true);
                totalBungaStringLabel.setVisible(true);
                totalBungaLabel.setVisible(true);
            }
            title.setText(kategori);
            allHutang.clear();
            allHutang.addAll(task.getValue());
            terimaHutangTable.refresh();
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
    private void searchHutang() {
        try{
            filterData.clear();
            for (HutangHead d : allHutang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getNoHutang())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglHutang())))||
                        checkColumn(d.getKodeSales())||
                        checkColumn(d.getNama())||
                        checkColumn(d.getAlamat())||
                        checkColumn(gr.format(d.getTotalBerat()))||
                        checkColumn(rp.format(d.getTotalHutang()))||
                        checkColumn(gr.format(d.getBungaPersen()))||
                        checkColumn(rp.format(d.getBungaRp()))||
                        checkColumn(rp.format(d.getLamaPinjam()))||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getTglLunas())))||
                        checkColumn(d.getStatusHilang())||
                        checkColumn(d.getSalesLunas()))
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
        double totalBunga = 0;
        double totalHutang = 0;
        for(HutangHead d : filterData){
            totalBerat = totalBerat + d.getTotalBerat();
            totalBunga = totalBunga + d.getBungaRp();
            totalHutang = totalHutang + d.getTotalHutang();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHutangLabel.setText(rp.format(totalHutang));
        totalBungaLabel.setText(rp.format(totalBunga));
    }
    private void detailHutang(HutangHead h){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/TerimaHutang.fxml");
        TerimaHutangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailHutang(h);
    }
    private void detailPelunasanHutang(HutangHead h){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PelunasanHutang.fxml");
        PelunasanHutangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailHutang(h);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
