/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanHeadDAO;
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
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import java.sql.Connection;
import java.text.ParseException;
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
public class DetailKeuanganPenjualanController  {

    @FXML private Label title;
    @FXML private TableView<Keuangan> penjualanHeadTable;
    @FXML private TableColumn<Keuangan, String> tglPenjualanColumn;
    @FXML private TableColumn<Keuangan, String> noPenjualanColumn;
    @FXML private TableColumn<Keuangan, String> namaColumn;
    @FXML private TableColumn<Keuangan, String> alamatColumn;
    @FXML private TableColumn<Keuangan, String> kodeSalesColumn;
    @FXML private TableColumn<Keuangan, Number> totalPenjualanColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahPoinColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahNilaiPoinColumn;
    @FXML private TableColumn<Keuangan, Number> kurangBayarColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahBayarColumn;
    
    @FXML private HBox hBox;
    @FXML private TextField searchField;
    @FXML private Label totalPoinStringLabel;
    @FXML private Label totalNilaiPoinStringLabel;
    @FXML private Label totalKurangBayarStringLabel;
    @FXML private Label totalJumlahBayarStringLabel;
    @FXML private Label totalPenjualanLabel;
    @FXML private Label totalPoinLabel;
    @FXML private Label totalNilaiPoinLabel;
    @FXML private Label totalKurangBayarLabel;
    @FXML private Label totalJumlahBayarLabel;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private final ObservableList<Keuangan> allPenjualan = FXCollections.observableArrayList();
    private final ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPenjualanHead().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().alamatProperty());
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().grandtotalProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(rp));
        jumlahPoinColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getJumlahRp()/sistem.getNilaiPoin()*-1);
        });
        jumlahPoinColumn.setCellFactory(col -> getTableCell(rp));
        jumlahNilaiPoinColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getJumlahRp()*-1);
        });
        jumlahNilaiPoinColumn.setCellFactory(col -> getTableCell(rp));
        kurangBayarColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getJumlahRp()*-1);
        });
        kurangBayarColumn.setCellFactory(col -> getTableCell(rp));
        jumlahBayarColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahBayarColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanHeadTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        penjualanHeadTable.setContextMenu(rowMenu);
        penjualanHeadTable.setRowFactory(table -> {
            TableRow<Keuangan> row = new TableRow<Keuangan>() {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detailMember = new MenuItem("Detail Member");
                        detailMember.setOnAction((ActionEvent e) -> {
                            detailMember(item.getPenjualanHead().getKodeMember());
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualan(item.getPenjualanHead());
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            detailPembayaran(item.getPenjualanHead());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            penjualanHeadTable.refresh();
                        });
                        if(title.getText().equals("Poin"))
                            rowMenu.getItems().add(detailMember);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(detailBayar);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPenjualan(row.getItem().getPenjualanHead());
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanHeadTable.setItems(filterData);
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
    public void getPenjualan(String tglAwal, String tglAkhir, List<Keuangan> listKeuangan, String kategori){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    for(Keuangan k : listKeuangan){
                        k.setPenjualanHead(PenjualanHeadDAO.get(conCabang, k.getKeterangan()));
                    }
                    return listKeuangan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            title.setText(kategori);
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
            hitungTotal();
            if(kategori.equals("Penjualan Umum")){
                jumlahPoinColumn.setVisible(false);
                jumlahNilaiPoinColumn.setVisible(false);
                kurangBayarColumn.setVisible(false);
                jumlahBayarColumn.setVisible(false);
                
                totalPoinStringLabel.setVisible(false);
                totalPoinLabel.setVisible(false);
                
                totalNilaiPoinStringLabel.setVisible(false);
                totalNilaiPoinLabel.setVisible(false);
                
                totalKurangBayarStringLabel.setVisible(false);
                totalKurangBayarLabel.setVisible(false);
                
                totalJumlahBayarStringLabel.setVisible(false);
                totalJumlahBayarLabel.setVisible(false);
            }else if(kategori.equals("Poin")){
                jumlahPoinColumn.setVisible(true);
                jumlahNilaiPoinColumn.setVisible(true);
                kurangBayarColumn.setVisible(false);
                jumlahBayarColumn.setVisible(false);
                
                totalPoinStringLabel.setVisible(true);
                totalPoinLabel.setVisible(true);
                
                totalNilaiPoinStringLabel.setVisible(true);
                totalNilaiPoinLabel.setVisible(true);
                
                totalKurangBayarStringLabel.setVisible(false);
                totalKurangBayarLabel.setVisible(false);
                
                totalJumlahBayarStringLabel.setVisible(false);
                totalJumlahBayarLabel.setVisible(false);
            }else if(kategori.equals("Orang Kurang")){
                jumlahPoinColumn.setVisible(false);
                jumlahNilaiPoinColumn.setVisible(false);
                kurangBayarColumn.setVisible(true);
                jumlahBayarColumn.setVisible(false);
                
                totalPoinStringLabel.setVisible(false);
                totalPoinLabel.setVisible(false);
                
                totalNilaiPoinStringLabel.setVisible(false);
                totalNilaiPoinLabel.setVisible(false);
                
                totalKurangBayarStringLabel.setVisible(true);
                totalKurangBayarLabel.setVisible(true);
                
                totalJumlahBayarStringLabel.setVisible(false);
                totalJumlahBayarLabel.setVisible(false);
            }else if(kategori.equals("Orang Bayar")){
                jumlahPoinColumn.setVisible(false);
                jumlahNilaiPoinColumn.setVisible(false);
                kurangBayarColumn.setVisible(false);
                jumlahBayarColumn.setVisible(true);
                
                totalPoinStringLabel.setVisible(false);
                totalPoinLabel.setVisible(false);
                
                totalNilaiPoinStringLabel.setVisible(false);
                totalNilaiPoinLabel.setVisible(false);
                
                totalKurangBayarStringLabel.setVisible(false);
                totalKurangBayarLabel.setVisible(false);
                
                totalJumlahBayarStringLabel.setVisible(true);
                totalJumlahBayarLabel.setVisible(true);
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void getOrangBayar(List<Keuangan> listKeuangan, String kategori){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    for(Keuangan k : listKeuangan){
                        k.setPenjualanHead(PenjualanHeadDAO.get(conCabang, k.getKeterangan()));
                    }
                    return listKeuangan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            title.setText(kategori);
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            penjualanHeadTable.refresh();
            hitungTotal();
            
            jumlahPoinColumn.setVisible(false);
            jumlahNilaiPoinColumn.setVisible(false);
            kurangBayarColumn.setVisible(false);
            jumlahBayarColumn.setVisible(true);

            totalPoinStringLabel.setVisible(false);
            totalPoinLabel.setVisible(false);

            totalNilaiPoinStringLabel.setVisible(false);
            totalNilaiPoinLabel.setVisible(false);

            totalKurangBayarStringLabel.setVisible(false);
            totalKurangBayarLabel.setVisible(false);

            totalJumlahBayarStringLabel.setVisible(true);
            totalJumlahBayarLabel.setVisible(true);
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (Keuangan d : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(d);
                else{
                    if(checkColumn(d.getKeterangan())||
                        checkColumn(tglLengkap.format(tglSql.parse(d.getPenjualanHead().getTglPenjualan())))||
                        checkColumn(gr.format(d.getPenjualanHead().getTotalBerat()))||
                        checkColumn(rp.format(d.getPenjualanHead().getTotalHarga()))||
                        checkColumn(rp.format(d.getPenjualanHead().getTotalOngkos()))||
                        checkColumn(rp.format(d.getPenjualanHead().getGrandtotal()))||
                        checkColumn(rp.format(d.getJumlahRp()))||
                        checkColumn(rp.format(d.getJumlahRp()/sistem.getNilaiPoin()))||
                        checkColumn(d.getPenjualanHead().getNama())||
                        checkColumn(d.getPenjualanHead().getAlamat())||
                        checkColumn(d.getPenjualanHead().getNoTelp())||
                        checkColumn(d.getPenjualanHead().getKodeSales()))
                        filterData.add(d);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalPenjualan = 0;
        double totalPoin = 0;
        double totalNilaiPoin = 0;
        double totalKurangBayar = 0;
        double totalBayar = 0;
        for(Keuangan d : filterData){
            totalPoin = totalPoin + d.getJumlahRp()/sistem.getNilaiPoin()*-1;
            totalNilaiPoin = totalNilaiPoin + d.getJumlahRp()*-1;
            totalPenjualan = totalPenjualan + d.getPenjualanHead().getGrandtotal();
            totalKurangBayar = totalKurangBayar + d.getJumlahRp()*-1;
            totalBayar = totalBayar + d.getJumlahRp();
        }
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
        totalPoinLabel.setText(rp.format(totalPoin));
        totalNilaiPoinLabel.setText(rp.format(totalNilaiPoin));
        totalKurangBayarLabel.setText(rp.format(totalKurangBayar));
        totalJumlahBayarLabel.setText(rp.format(totalBayar));
    }
    private void detailPenjualan(PenjualanHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }
    private void detailMember(String kodeMember){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailMember.fxml");
        DetailMemberController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.lihatMember(kodeMember);
    }
    private void detailPembayaran(PenjualanHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailPembayaran.fxml");
        DetailPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.getPembayaran(p.getNoPenjualan());
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            controller.pembayaranTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        controller.pembayaranTable.setContextMenu(rowMenu);
        controller.pembayaranTable.setRowFactory(table -> {
            TableRow<PembayaranPenjualan> row = new TableRow<PembayaranPenjualan>() {
                @Override
                public void updateItem(PembayaranPenjualan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            controller.pembayaranTable.refresh();
                        });
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
