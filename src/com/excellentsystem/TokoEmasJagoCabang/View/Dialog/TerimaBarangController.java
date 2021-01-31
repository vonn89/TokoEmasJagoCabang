/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PindahDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
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
public class TerimaBarangController {

    @FXML private TreeTableView<PindahDetail> pindahDetailTable;
    @FXML private TreeTableColumn<PindahDetail, String> kodeBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> namaBarangColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kodeKategoriColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kodeInternColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kadarColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> qtyColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> beratColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> beratAsliColumn;
    @FXML private TreeTableColumn<PindahDetail, String> tglBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> userBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> asalBarangColumn;
    
    @FXML private TextField noPindahField;
    @FXML public TextField kodeSalesField;
    @FXML public Label totalQtyLabel;
    @FXML public Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    @FXML public Button saveButton;
    
    private final TreeItem<PindahDetail> root = new TreeItem<>();
    public ObservableList<PindahDetail> listPindahDetail = FXCollections.observableArrayList();
    public PindahHead pindahHead;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeKategoriProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kadarProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTreeTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTreeTableCell(gr));
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getInputDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().inputByProperty());
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().asalBarangProperty());
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPindahDetail();
        });
        menu.getItems().addAll(refresh);
        pindahDetailTable.setContextMenu(menu);
        pindahDetailTable.setRowFactory(table -> {
            TreeTableRow<PindahDetail> row = new TreeTableRow<PindahDetail>(){
                @Override
                public void updateItem(PindahDetail item, boolean empty) {
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
                            getPindahDetail();
                        });
                        if(item.getNamaBarang()!=null)
                            rowMenu.getItems().add(detail);
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
        noPindahField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                getPindahDetail();
                kodeSalesField.requestFocus();
                kodeSalesField.selectAll();
            }
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals(""))
                    saveButton.fire();
            }
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
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
    private void getPindahDetail(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listPindahDetail.clear();
                    pindahHead = PindahHeadDAO.get(conPusat, noPindahField.getText());
                    if(pindahHead==null)
                        return "No pindah yang dimasukan masih kosong atau salah";
                    else if(!pindahHead.getKodeCabang().equals(cabang.getKodeCabang()))
                        return "Tidak dapat diterima, karena no pindah untuk cabang "+pindahHead.getKodeCabang();
                    else if(pindahHead.getStatusTerima().equals("true"))
                        return "Tidak dapat diterima, karena no pindah sudah pernah diterima";
                    else if(pindahHead.getStatusBatal().equals("true"))
                        return "Tidak dapat diterima, karena no pindah sudah dibatalkan";
                    else if(pindahHead.getStatusTerima().equals("false")&&
                            pindahHead.getStatusBatal().equals("false")){
                        List<PindahDetail> temp = PindahDetailDAO.getAllByNoPindah(conPusat, noPindahField.getText());
                        for(PindahDetail d : temp){
                            d.setQty(1);
                        }
                        listPindahDetail.addAll(temp);
                        return "true";
                    }else 
                        return "Tidak dapat diterima, karena status pindah salah";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            pindahDetailTable.refresh();
            setTable();
            hitungTotal();
            if(!task.getValue().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void setTable(){
        if(pindahDetailTable.getRoot()!=null)
            pindahDetailTable.getRoot().getChildren().clear();
        List<String> jenis = new ArrayList<>();
        for(PindahDetail p: listPindahDetail){
            if(!jenis.contains(p.getKodeJenis()))
                jenis.add(p.getKodeJenis());
        }
        for(String s : jenis){
            PindahDetail group = new PindahDetail();
            int qty = 0;
            double berat = 0;
            double beratAsli = 0;
            TreeItem<PindahDetail> parent = new TreeItem<>(group);
            for(PindahDetail p : listPindahDetail){
                if(s.equals(p.getKodeJenis())){
                    qty = qty + 1;
                    berat = berat + p.getBerat();
                    beratAsli = beratAsli + p.getBeratAsli();
                    
                    TreeItem<PindahDetail> child = new TreeItem<>(p);
                    parent.getChildren().addAll(child);
                }
            }
            parent.getValue().setKodeBarcode(s);
            parent.getValue().setQty(qty);
            parent.getValue().setBerat(berat);
            parent.getValue().setBeratAsli(beratAsli);
            root.getChildren().add(parent);
        }
        pindahDetailTable.setRoot(root);
    } 
    private void detailBarang(PindahDetail d){
        Barang b = new Barang();
        b.setKodeBarang(d.getKodeBarang());
        b.setKodeBarcode(d.getKodeBarcode());
        b.setNamaBarang(d.getNamaBarang());
        b.setKodeKategori(d.getKodeKategori());
        b.setKodeJenis(d.getKodeJenis());
        b.setKodeIntern(d.getKodeIntern());
        b.setKadar(d.getKadar());
        b.setBerat(d.getBerat());
        b.setBeratAsli(d.getBeratAsli());
        b.setBeratPersen(d.getBeratPersen());
        b.setNilaiPokok(d.getNilaiPokok());
        b.setInputDate(d.getInputDate());
        b.setInputBy(d.getInputBy());
        b.setAsalBarang(d.getAsalBarang());
        b.setStatus(d.getStatus());
        
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(b);
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double beratAsli = 0;
        for(PindahDetail d : listPindahDetail){
            qty = qty + 1;
            berat = berat + d.getBerat();
            beratAsli = beratAsli + d.getBeratAsli();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalBeratAsliLabel.setText(gr.format(beratAsli));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
