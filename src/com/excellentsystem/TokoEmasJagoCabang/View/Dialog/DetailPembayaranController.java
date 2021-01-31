/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PembayaranPenjualanDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import java.sql.Connection;
import java.text.ParseException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailPembayaranController  {

    @FXML public TableView<PembayaranPenjualan> pembayaranTable;
    @FXML private TableColumn<PembayaranPenjualan, String> noPembayaranColumn;
    @FXML private TableColumn<PembayaranPenjualan, String> tglPembayaranColumn;
    @FXML private TableColumn<PembayaranPenjualan, String> jenisPembayaranColumn;
    @FXML private TableColumn<PembayaranPenjualan, String> keteranganColumn;
    @FXML private TableColumn<PembayaranPenjualan, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<PembayaranPenjualan, String> kodeSalesColumn;
    
    @FXML private Label totalPembayaranLabel;
    private ObservableList<PembayaranPenjualan> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noPembayaranProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembayaran())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembayaranColumn.setComparator(Function.sortDate(tglLengkap));
        jenisPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jenisPembayaranProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        pembayaranTable.setItems(allPembayaran);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void getPembayaran(String noPenjualan){
        Task<List<PembayaranPenjualan>> task = new Task<List<PembayaranPenjualan>>() {
            @Override 
            public List<PembayaranPenjualan> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<PembayaranPenjualan> listPembayaran = PembayaranPenjualanDAO.getAllByNoPenjualanAndStatus(
                        conCabang, noPenjualan, "true");
                    return listPembayaran;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPembayaran.clear();
            allPembayaran.addAll(task.getValue());
            pembayaranTable.refresh();
            hitungTotal();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void hitungTotal(){
        double total = 0;
        for(PembayaranPenjualan p : allPembayaran){
            total = total + p.getJumlahPembayaran();
        }
        totalPembayaranLabel.setText(rp.format(total));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
