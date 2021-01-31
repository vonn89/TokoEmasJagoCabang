/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.StokOpnameDetailDAO;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
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
public class DetailStokOpnameController {

    @FXML private TableView<StokOpnameDetail> stokOpnameDetailTable;
    @FXML private TableColumn<StokOpnameDetail, Number> stokBarangColumn;
    @FXML private TableColumn<StokOpnameDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<StokOpnameDetail, String> kodeBarangColumn;
    @FXML private TableColumn<StokOpnameDetail, String> namaBarangColumn;
    @FXML private TableColumn<StokOpnameDetail, String> kodeInternColumn;
    @FXML private TableColumn<StokOpnameDetail, String> kadarColumn;
    @FXML private TableColumn<StokOpnameDetail, Number> beratColumn;
    @FXML private TableColumn<StokOpnameDetail, Number> beratAsliColumn;
    
    @FXML private TextField noStokOpnameField;
    @FXML private TextField tglStokOpnameField;
    @FXML private TextField kodeJenisField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalQtyDistokLabel;
    @FXML private Label totalBeratDistokLabel;
    
    private ObservableList<StokOpnameDetail> listStokOpnameDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        stokBarangColumn.setCellValueFactory(cellData -> cellData.getValue().stokBarangProperty());
        stokBarangColumn.setCellFactory(col -> getTableCell(rp));
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getStokOpnameDetail();
        });
        menu.getItems().addAll(refresh);
        stokOpnameDetailTable.setContextMenu(menu);
        stokOpnameDetailTable.setRowFactory(table -> {
            TableRow<StokOpnameDetail> row = new TableRow<StokOpnameDetail>(){
                @Override
                public void updateItem(StokOpnameDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getStokOpnameDetail();
                        });
                        rowMenu.getItems().addAll(detail);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        if(row.getItem().getNamaBarang()!=null)
                            detailBarang(row.getItem());
                    
                }
            });
            return row;
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stokOpnameDetailTable.setItems(listStokOpnameDetail);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Semua");
        status.add("Barang Tidak Ditemukan");
        status.add("Barang Lebih Dari Satu");
        statusCombo.setItems(status);
        statusCombo.getSelectionModel().select("Semua");
    }
    public void setStokOpnameHead(StokOpnameHead s){
        try{
            noStokOpnameField.setText(s.getNoStokOpname());
            tglStokOpnameField.setText(tglLengkap.format(tglSql.parse(s.getTglStokOpname())));
            kodeJenisField.setText(s.getKodeJenis());
            getStokOpnameDetail();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getStokOpnameDetail(){
        Task<List<StokOpnameDetail>> task = new Task<List<StokOpnameDetail>>() {
            @Override 
            public List<StokOpnameDetail> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<StokOpnameDetail> listStokOpname = new ArrayList<>();
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        listStokOpname = StokOpnameDetailDAO.getAllByNoStok(conCabang, noStokOpnameField.getText());
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Barang Tidak Ditemukan")){
                        for(StokOpnameDetail d : StokOpnameDetailDAO.getAllByNoStok(conCabang, noStokOpnameField.getText())){
                            if(d.getStokBarang()<1)
                                listStokOpname.add(d);
                        }
                    }else if(statusCombo.getSelectionModel().getSelectedItem().equals("Barang Lebih Dari Satu")){
                        for(StokOpnameDetail d : StokOpnameDetailDAO.getAllByNoStok(conCabang, noStokOpnameField.getText())){
                            if(d.getStokBarang()>1)
                                listStokOpname.add(d);
                        }
                    }
                    return listStokOpname;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            listStokOpnameDetail.clear();
            listStokOpnameDetail.addAll(task.getValue());
            stokOpnameDetailTable.refresh();
            hitungTotal();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double qtyDistok = 0;
        double beratDistok = 0;
        for(StokOpnameDetail d : listStokOpnameDetail){
            qty = qty + 1;
            berat = berat + d.getBerat();
            qtyDistok = qtyDistok + d.getStokBarang();
            beratDistok = beratDistok + (d.getStokBarang()*d.getBerat());
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalQtyDistokLabel.setText(rp.format(qtyDistok));
        totalBeratDistokLabel.setText(gr.format(beratDistok));
    }
    private void detailBarang(StokOpnameDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarangByKodeBarcode(d.getKodeBarcode());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
