/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangHead;
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
public class DetailAmbilBarangController  {

    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> tanggalColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratPersenColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalBeliColumn;
    
    @FXML private TextField noAmbilBarangField;
    @FXML private TextField tglAmbilBarangField;
    @FXML private TextField tglPembelianField;
    @FXML private TextField userAmbilField;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalPembelianLabel;
    
    private AmbilBarangHead ambilBarang;
    private ObservableList<PembelianDetail> listPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglSystem.parse(
                        cellData.getValue().getNoPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglNormal));
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> getTableCell(gr));
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        totalBeliColumn.setCellFactory(col -> getTableCell(rp));
        
        pembelianDetailTable.setItems(listPembelianDetail);
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getAmbilBarangDetail();
        });
        menu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(menu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<PembelianDetail> row = new TableRow<PembelianDetail>(){
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
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
                            getAmbilBarangDetail();
                        });
                        rowMenu.getItems().addAll(detail, refresh);
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
    public void setAmbilBarang(AmbilBarangHead a){
        try{
            ambilBarang = a;
            noAmbilBarangField.setText(a.getNoAmbilBarang());
            tglAmbilBarangField.setText(tglLengkap.format(tglSql.parse(a.getTglAmbilBarang())));
            tglPembelianField.setText(tglNormal.format(tglBarang.parse(a.getTglPembelianMulai()))+" - "+tglNormal.format(tglBarang.parse(a.getTglPembelianAkhir())));
            userAmbilField.setText(a.getKodeUser());
            getAmbilBarangDetail();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void getAmbilBarangDetail(){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
            @Override 
            public List<PembelianDetail> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<PembelianDetail> allPembelianDetail = PembelianDetailDAO.getAllByNoAmbil(conCabang, noAmbilBarangField.getText());
                    List<String> tanggal = new ArrayList<>();
                    for(PembelianDetail d : allPembelianDetail){
                        d.setPembelianHead(PembelianHeadDAO.get(conCabang, d.getNoPembelian()));
                        if(!tanggal.contains(d.getNoPembelian().substring(4,10)))
                            tanggal.add(d.getNoPembelian().substring(4,10));
                    }
                    List<PembelianDetail> listAmbil = new ArrayList<>();
                    for(String s : tanggal){
                        PembelianDetail group = new PembelianDetail();
                        group.setNoPembelian(s);
                        group.setListPembelianDetail(new ArrayList<>());
                        int qty = 0;
                        double beratKotor = 0;
                        double beratBersih = 0;
                        double beratPersen = 0;
                        double totalBeli = 0;
                        for(PembelianDetail d : allPembelianDetail){
                            if(s.equals(d.getNoPembelian().substring(4,10))){
                                qty = qty + 1;
                                beratKotor = beratKotor + d.getBeratKotor();
                                beratBersih = beratBersih + d.getBeratBersih();
                                beratPersen = beratPersen + d.getBeratPersen();
                                totalBeli = totalBeli + d.getHargaBeli();

                                group.getListPembelianDetail().add(d);
                            }
                        }
                        group.setQty(qty);
                        group.setBeratKotor(beratKotor);
                        group.setBeratBersih(beratBersih);
                        group.setBeratPersen(beratPersen);
                        group.setHargaBeli(totalBeli);
                        
                        listAmbil.add(group);
                    }
                    return listAmbil;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            listPembelianDetail.clear();
            listPembelianDetail.addAll(task.getValue());
            pembelianDetailTable.refresh();
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
        double beratKotor = 0;
        double beratBersih = 0;
        double beratPersen = 0;
        double harga = 0;
        for(PembelianDetail d : listPembelianDetail){
            qty = qty + d.getQty();
            beratKotor = beratKotor + d.getBeratKotor();
            beratBersih = beratBersih + d.getBeratBersih();
            beratPersen = beratPersen + d.getBeratPersen();
            harga = harga + d.getHargaBeli();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratKotorLabel.setText(gr.format(beratKotor));
        totalBeratBersihLabel.setText(gr.format(beratBersih));
        totalBeratPersenLabel.setText(gr.format(beratPersen));
        totalPembelianLabel.setText(gr.format(harga));
    }
    private void detailBarang(PembelianDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarangPembelian.fxml");
        DetailBarangPembelianController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setAmbilBarang(d.getListPembelianDetail(), d.getNoPembelian());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
