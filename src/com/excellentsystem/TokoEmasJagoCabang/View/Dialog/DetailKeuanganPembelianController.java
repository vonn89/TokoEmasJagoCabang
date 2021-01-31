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
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
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
public class DetailKeuanganPembelianController  {

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
    @FXML private Label totalPembelianLabel;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
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
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pembelianHeadTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
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
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPembelian(item.getPembelianHead());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            pembelianHeadTable.refresh();
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
    public void getPembelian(String tglAwal, String tglAkhir){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
            @Override 
            public List<PembelianDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<PembelianDetail> listPembelianDetail = PembelianDetailDAO.getAllDateAndStatusAndStatusAmbil(
                        conCabang, tglAwal, tglAkhir, "true", "%");
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
    private void detailPembelian(PembelianHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPembelian(p);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
