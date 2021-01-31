/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PermintaanBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoCabang.Main.user;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PermintaanBarang;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailKategoriBarangController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.HargaRosokController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DashboardController {

    @FXML private StackPane penjualanLoading;
    @FXML private StackPane pembelianLoading;
    @FXML private StackPane terimaHutangLoading;
    @FXML private StackPane pelunasanHutangLoading;
    @FXML private StackPane storePerformanceLoading;
    @FXML private StackPane kategoriBarangLoading;
    @FXML private StackPane permintaanBarangLoading;
    @FXML private StackPane bestsellingItemsLoading;
    @FXML public ComboBox<String> periodeCombo;
    private ObservableList<String> periode = FXCollections.observableArrayList();
    private String tglAwal = sistem.getTglSystem();
    private String tglAkhir = sistem.getTglSystem();
    
    private Main mainApp;  
    public void initialize() {
        //Label Header
        penjualanVbox.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2)
                mainApp.showDataPenjualan();
        });
        pembelianVbox.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2)
                mainApp.showDataPembelian();
        });
        terimaHutangVbox.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2)
                mainApp.showDataTerimaHutang();
        });
        hutangLunasVbox.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2)
                mainApp.showDataPelunasanHutang();
        });
        
        //Kalkulator Emas
        kategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                getBarang();
        });
        beratField.setOnKeyReleased((event) -> {
            try{
                String string = beratField.getText();
                if(string.contains("-"))
                    beratField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            beratField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratField.end();
            }catch(Exception e){
                beratField.undo();
            }
            getNilaiJual();
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                getNilaiJual();
            }
        });
        
        //Data Kategori Barang
        kategoriTable.setItems(allKategori);
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTableCell(rp));
        ContextMenu kategoriMenu = new ContextMenu();
        MenuItem rosok = new MenuItem("Ubah Harga Rosok");
        rosok.setOnAction((ActionEvent) -> {
            ubahHargaRosok();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            setDataKategoriBarang();
        });
        kategoriMenu.getItems().addAll(rosok, refresh);
        kategoriTable.setContextMenu(kategoriMenu);
        kategoriTable.setRowFactory(t -> {
            TableRow<Kategori> row = new TableRow<Kategori>(){
                @Override
                public void updateItem(Kategori item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(kategoriMenu);
                    }else{
                        MenuItem editKategori = new MenuItem("Ubah Kategori Barang");
                        editKategori.setOnAction((ActionEvent) -> {
                            editKategori(item);
                        });
                        MenuItem rosok = new MenuItem("Ubah Harga Rosok");
                        rosok.setOnAction((ActionEvent) -> {
                            ubahHargaRosok();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            setDataKategoriBarang();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(editKategori, rosok, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        editKategori(row.getItem());
                }
            });
            return row;
        });
        
        //Permintaan Barang
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals("")){
                    keteranganField.requestFocus();
                    keteranganField.selectAll();
                }
            }
        });
        ContextMenu rowMenu = new ContextMenu();
        MenuItem refreshPermintaanBarang = new MenuItem("Refresh");
        refreshPermintaanBarang.setOnAction((ActionEvent) -> {
            setPermintaanBarang();
        });
        rowMenu.getItems().add(refreshPermintaanBarang);
        vbox.setOnContextMenuRequested((e) -> {
            rowMenu.show(vbox, e.getScreenX(), e.getScreenY());
        });
        warningLabel.setOnContextMenuRequested((e) -> {
            rowMenu.show(vbox, e.getScreenX(), e.getScreenY());
        });
        
        Timeline t = new Timeline(
            new KeyFrame(Duration.seconds(5), (ActionEvent actionEvent) -> {
                if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day") && 
                        mainApp.mainController.title.getText().equals("Dashboard") && mainApp.MainStage.isFocused()){
                    System.out.println(new Date()+" setDashboard");
                    setData();
                }
            })
        );
        t.setCycleCount(Animation.INDEFINITE);
        t.playFromStart();
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        periode.clear();
        periode.add("This Day");
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Dashboard - This Month")&& o.isStatus())
                periode.add("This Month");
        }
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Dashboard - Last 6 Months")&& o.isStatus())
                periode.add("Last 6 Months");
        }
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Dashboard - Last 12 Months")&& o.isStatus())
                periode.add("Last 12 Months");
        }
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Dashboard - This Year")&& o.isStatus())
                periode.add("This Year");
        }
        periodeCombo.setItems(periode);
        periodeCombo.getSelectionModel().selectFirst();
        setKategoriCombo();
        
        setData();
    }
    private void setKategoriCombo(){
        Task<List<Kategori>> task = new Task<List<Kategori>>() {
            @Override 
            public List<Kategori> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KategoriDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Kategori> kategori = task.getValue();
            ObservableList<String> listKategori = FXCollections.observableArrayList();
            for(Kategori k : kategori){
                listKategori.add(k.getKodeKategori());
            }
            kategoriCombo.setItems(listKategori);
            
            ObservableList<String> listKategoriBestSellingItems = FXCollections.observableArrayList();
            listKategoriBestSellingItems.add("Semua Kategori");
            for(Kategori k : kategori){
                listKategoriBestSellingItems.add(k.getKodeKategori());
            }
            kategoriBestSellingItemsCombo.setItems(listKategoriBestSellingItems);
            kategoriBestSellingItemsCombo.getSelectionModel().selectFirst();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void changePeriode(){
        LocalDate localDate = LocalDate.parse(sistem.getTglSystem(), DateTimeFormatter.ISO_DATE);
        tglAwal = localDate.toString();
        tglAkhir = localDate.toString();
        DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");
        if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
            tglAwal = tglAkhir;
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
            tglAwal = localDate.format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 6 Months")){
            tglAwal = localDate.minusMonths(5).format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 12 Months")){
            tglAwal = localDate.minusMonths(11).format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Year")){
            tglAwal = localDate.format(yyyy)+"-01-01";
        }
        setData();
    }
    @FXML
    public void setData(){
        setLabelHeader();
        setStorePerformance();
        setBestSellingItems();
        setDataKategoriBarang();
        setPermintaanBarang();
    }
    //Label Header
    @FXML private VBox penjualanVbox;
    @FXML private Label totalPenjualanLabel;
    @FXML private Label countPenjualanLabel;
    @FXML private VBox pembelianVbox;
    @FXML private Label totalPembelianLabel;
    @FXML private Label countPembelianLabel;
    @FXML private VBox terimaHutangVbox;
    @FXML private Label totalTerimaHutangLabel;
    @FXML private Label countTerimaHutangLabel;
    @FXML private VBox hutangLunasVbox;
    @FXML private Label totalHutangLunasLabel;
    @FXML private Label totalBungaHutangLabel;
    @FXML private Label countHutangLunasLabel;
    private void setLabelHeader(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(
                            conCabang, tglAwal, tglAkhir, "%", "Kas", "%", "%");
                }
            }
        };
        task.setOnRunning((event) -> {
            penjualanLoading.setVisible(true);
            pembelianLoading.setVisible(true);
            terimaHutangLoading.setVisible(true);
            pelunasanHutangLoading.setVisible(true);
        });
        task.setOnSucceeded((ev) -> {
            List<Keuangan> allKeuangan = task.getValue();
            double totalPenjualan = 0;
            double totalPembelian = 0;
            double totalTerimaHutang = 0;
            double totalHutangLunas = 0;
            double totalHutangBunga = 0;
            int countPenjualan = 0;
            int countPembelian = 0;
            int countTerimaHutang = 0;
            int countHutangLunas = 0;
            for(Keuangan k : allKeuangan){
                if(k.getKategori().equals("Penjualan Umum")){
                    countPenjualan = countPenjualan + 1;
                    totalPenjualan = totalPenjualan + k.getJumlahRp();
                }
                if(k.getKategori().equals("Pembelian Umum")){
                    countPembelian = countPembelian + 1;
                    totalPembelian = totalPembelian + abs(k.getJumlahRp());
                }
                if(k.getKategori().equals("Terima Hutang")){
                    countTerimaHutang = countTerimaHutang + 1;
                    totalTerimaHutang = totalTerimaHutang + abs(k.getJumlahRp());
                }
                if(k.getKategori().equals("Hutang Lunas")){
                    countHutangLunas = countHutangLunas + 1;
                    totalHutangLunas = totalHutangLunas + k.getJumlahRp();
                }
                if(k.getKategori().equals("Hutang Bunga"))
                    totalHutangBunga = totalHutangBunga + k.getJumlahRp();
            }
            DecimalFormat df = new DecimalFormat("###,##0.##");
            String a = new DecimalFormat("###,##0").format(totalPenjualan);
            String b = new DecimalFormat("###,##0").format(totalPembelian);
            String c = new DecimalFormat("###,##0").format(totalTerimaHutang);
            String d = new DecimalFormat("###,##0").format(totalHutangLunas);
            String e = new DecimalFormat("###,##0").format(totalHutangBunga);
            if(totalPenjualan/1000000000>=1 || totalPenjualan/1000000000<=-1)
                a = df.format(totalPenjualan/1000000000)+" M";
            if(totalPembelian/1000000000>=1 || totalPembelian/1000000000<=-1)
                b = df.format(totalPembelian/1000000000)+" M";
            if(totalTerimaHutang/1000000000>=1 || totalTerimaHutang/1000000000<=-1)
                c = df.format(totalTerimaHutang/1000000000)+" M";
            if(totalHutangLunas/1000000000>=1 || totalHutangLunas/1000000000<=-1)
                d = df.format(totalHutangLunas/1000000000)+" M";
            if(totalHutangBunga/1000000000>=1 || totalHutangBunga/1000000000<=-1)
                e = df.format(totalHutangBunga/1000000000)+" M";

            totalPenjualanLabel.setText(a);
            totalPembelianLabel.setText(b);
            totalTerimaHutangLabel.setText(c);
            totalHutangLunasLabel.setText(d);
            totalBungaHutangLabel.setText(e);

            countPenjualanLabel.setText("( "+rp.format(countPenjualan)+" )");
            countPembelianLabel.setText("( "+rp.format(countPembelian)+" )");
            countTerimaHutangLabel.setText("( "+rp.format(countTerimaHutang)+" )");
            countHutangLunasLabel.setText("( "+rp.format(countHutangLunas)+" )");
            
            penjualanLoading.setVisible(false);
            pembelianLoading.setVisible(false);
            terimaHutangLoading.setVisible(false);
            pelunasanHutangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            totalPenjualanLabel.setText("-");
            totalPembelianLabel.setText("-");
            totalTerimaHutangLabel.setText("-");
            totalHutangLunasLabel.setText("-");
            totalBungaHutangLabel.setText("-");

            countPenjualanLabel.setText("( - )");
            countPembelianLabel.setText("( - )");
            countTerimaHutangLabel.setText("( - )");
            countHutangLunasLabel.setText("( - )");
            
            penjualanLoading.setVisible(false);
            pembelianLoading.setVisible(false);
            terimaHutangLoading.setVisible(false);
            pelunasanHutangLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    
    //Store Performance
    @FXML private RadioButton countStorePerformanceRadio;
    @FXML private RadioButton totalStorePerformanceRadio;
    @FXML private LineChart<String, Double> storePerformanceChart;
    @FXML private CategoryAxis periodeStorePerformanceAxis;
    private XYChart.Series getXYChartSeriesStore(CategoryAxis categoryAxis, List<Keuangan> listKeuangan, String kategori)throws Exception{
        XYChart.Series series = new XYChart.Series<>();
        series.setName(kategori);  
        for(String s : categoryAxis.getCategories()){
            double x = 0;
            for(Keuangan k : listKeuangan){
                if(k.getKategori().equals(kategori)){
                    boolean status = false;
                    if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
                        if(s.equals(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                        if(s.equals(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }else{
                        if(s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }
                    if(status){
                        if(totalStorePerformanceRadio.isSelected())
                            x = x + abs(k.getJumlahRp());
                        else if(countStorePerformanceRadio.isSelected()){
                            if(!kategori.equals("Hutang Bunga"))
                                x = x + 1;
                            else
                                x = x + 0;
                        }
                    }
                }
            }
            series.getData().add(new XYChart.Data<>(s, x));
        }
        return series;
    }
    @FXML
    private void setStorePerformance(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(
                            conCabang, tglAwal, tglAkhir, "%", "Kas", "%", "%");
                }
            }
        };
        task.setOnRunning((event) -> {
            storePerformanceLoading.setVisible(true);
        });
        task.setOnSucceeded((ev) -> {
            try{
                List<Keuangan> listKeuangan = task.getValue();
                periodeStorePerformanceAxis.getCategories().clear();
                for(Keuangan k : listKeuangan){
                    if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
                        if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                        if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                    }else{
                        if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                    }
                }

                ObservableList<Series<String, Double>> dataStorePerformance = FXCollections.observableArrayList();
                XYChart.Series series1 = getXYChartSeriesStore(periodeStorePerformanceAxis, listKeuangan, "Penjualan Umum");
                if(!series1.getData().isEmpty())
                    dataStorePerformance.add(series1);

                XYChart.Series series2 = getXYChartSeriesStore(periodeStorePerformanceAxis, listKeuangan, "Pembelian Umum");
                if(!series2.getData().isEmpty())
                    dataStorePerformance.add(series2);

                XYChart.Series series3 = getXYChartSeriesStore(periodeStorePerformanceAxis, listKeuangan, "Terima Hutang");
                if(!series3.getData().isEmpty())
                    dataStorePerformance.add(series3);

                XYChart.Series series4 = getXYChartSeriesStore(periodeStorePerformanceAxis, listKeuangan, "Hutang Lunas");
                if(!series4.getData().isEmpty())
                    dataStorePerformance.add(series4);

                XYChart.Series series5 = getXYChartSeriesStore(periodeStorePerformanceAxis, listKeuangan, "Hutang Bunga");
                if(!series5.getData().isEmpty())
                    dataStorePerformance.add(series5);

                storePerformanceChart.setData(dataStorePerformance);
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            storePerformanceLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            storePerformanceChart.setData(FXCollections.observableArrayList());
            storePerformanceLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    
    //Best Selling Items
    @FXML private ComboBox<String> kategoriBestSellingItemsCombo;
    @FXML private RadioButton qtyBestSellingItemRadio;
    @FXML private RadioButton beratBestSellingItemRadio;
    @FXML private PieChart bestSellingItemChart;
    @FXML
    private void setBestSellingItems(){
        Task<List<PenjualanDetail>> task = new Task<List<PenjualanDetail>>() {
            @Override 
            public List<PenjualanDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return PenjualanDetailDAO.getAllByDateAndStatus(conCabang, tglAwal, tglAkhir, "true");
                }
            }
        };
        task.setOnRunning((event) -> {
            bestsellingItemsLoading.setVisible(true);
        });
        task.setOnSucceeded((ev) -> {
            List<PenjualanDetail> allPenjualanDetail = task.getValue();
            String kategori = kategoriBestSellingItemsCombo.getSelectionModel().getSelectedItem();
            ObservableList<Data> dataBestSellingItems = FXCollections.observableArrayList();
            HashMap hm = new HashMap();
            if(qtyBestSellingItemRadio.isSelected()){
                for(PenjualanDetail d : allPenjualanDetail){
                    if(kategori==null || kategori.equals("Semua Kategori")){
                        if(hm.get(d.getKodeKategori())==null){
                            hm.put(d.getKodeKategori(), 1);
                        }else{
                            hm.put(d.getKodeKategori(), (int)hm.get(d.getKodeKategori())+1);
                        }
                    }else{
                        if(d.getKodeKategori().equals(kategori)){
                            if(hm.get(d.getKodeJenis())==null){
                                hm.put(d.getKodeJenis(), 1);
                            }else{
                                hm.put(d.getKodeJenis(), (int)hm.get(d.getKodeJenis())+1);
                            }
                        }
                    }
                }
            }else if(beratBestSellingItemRadio.isSelected()){
                for(PenjualanDetail d : allPenjualanDetail){
                    if(kategori==null || kategori.equals("Semua Kategori")){
                        if(hm.get(d.getKodeKategori())==null){
                            hm.put(d.getKodeKategori(), d.getBerat());
                        }else{
                            hm.put(d.getKodeKategori(), (double)hm.get(d.getKodeKategori())+d.getBerat());
                        }
                    }else{
                        if(d.getKodeKategori().equals(kategori)){
                            if(hm.get(d.getKodeJenis())==null){
                                hm.put(d.getKodeJenis(), d.getBerat());
                            }else{
                                hm.put(d.getKodeJenis(), (double)hm.get(d.getKodeJenis())+d.getBerat());
                            }
                        }
                    }
                }
            }
            Iterator i = hm.entrySet().iterator();
            while(i.hasNext()){
                Map.Entry me = (Map.Entry)i.next();
                double value = Double.parseDouble(new DecimalFormat("##0.##").format(me.getValue()));
                dataBestSellingItems.add(new PieChart.Data(me.getKey().toString(), value));
            }
            dataBestSellingItems.forEach(data -> data.nameProperty().bind(
                Bindings.concat(data.getName(), " ( ", gr.format(data.getPieValue()), " )")
            ));
            bestSellingItemChart.setData(dataBestSellingItems);
            bestsellingItemsLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            bestSellingItemChart.setData(FXCollections.observableArrayList());
            bestsellingItemsLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    
    //Permintaan Barang
    @FXML private Label warningLabel;
    @FXML private TextField kodeSalesField;
    @FXML private TextArea keteranganField;
    @FXML private VBox vbox;
    private void setPermintaanBarang(){
        Task<List<PermintaanBarang>> task = new Task<List<PermintaanBarang>>() {
            @Override 
            public List<PermintaanBarang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PermintaanBarangDAO.getAllByCabangAndLimit(conPusat, cabang.getKodeCabang(), 10);
                }
            }
        };
        task.setOnRunning((event) -> {
            permintaanBarangLoading.setVisible(true);
        });
        task.setOnSucceeded((ev) -> {
            try{
                List<PermintaanBarang> listPermintaan = task.getValue();
                vbox.getChildren().clear();
                for(PermintaanBarang p : listPermintaan){
                    ImageView img = null;
                    if(p.getStatus().equals("true"))
                        img = new ImageView(new Image(Main.class.getResourceAsStream("Resource/check.png"), 15, 15, true, true));
                    Label tanggalAndSales = new Label(tglLengkap.format(tglSql.parse(p.getTanggal()))+" - "+p.getKodeSales(), img);
                    tanggalAndSales.setContentDisplay(ContentDisplay.RIGHT);
                    Label keterangan = new Label(p.getKeterangan());
                    tanggalAndSales.setStyle("-fx-font-size : 12; "
                            + " -fx-font-weight : bold; "
                            + " -fx-text-fill: seccolor1;");
                    keterangan.setStyle("-fx-font-size : 13;");
                    keterangan.setWrapText(true);
                    vbox.getChildren().add(tanggalAndSales);
                    vbox.getChildren().add(keterangan);
                    vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
                }
                warningLabel.setVisible(false);
            }catch(ParseException e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            permintaanBarangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            warningLabel.setVisible(true);
            permintaanBarangLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    @FXML
    private void sendButton(){
        kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
        if(kodeSalesField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode sales masih kosong / salah");
        else if(keteranganField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Keterangan masih kosong");
        else{
            Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        PermintaanBarang p = new PermintaanBarang();
                        p.setTanggal(Function.getSystemDate());
                        p.setKodeCabang(cabang.getKodeCabang());
                        p.setKodeSales(kodeSalesField.getText());
                        p.setKeterangan(keteranganField.getText());
                        p.setStatus("false");
                        PermintaanBarangDAO.insert(conPusat, p);
                    }
                    return null;
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                kodeSalesField.setText("");
                keteranganField.setText("");
                setPermintaanBarang();
                mainApp.showMessage(Modality.NONE, "Success", "Permintaan barang berhasil dikirim");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void cancelButton(){
        kodeSalesField.setText("");
        keteranganField.setText("");
        setPermintaanBarang();
    }
    
    //Data Kategori Barang
    @FXML private TableView<Kategori> kategoriTable;
    @FXML private TableColumn<Kategori, String> kodeKategoriColumn;
    @FXML private TableColumn<Kategori, Number> hargaBeliColumn;
    @FXML private TableColumn<Kategori, Number> hargaJualColumn;
    private ObservableList<Kategori> allKategori = FXCollections.observableArrayList();
    private void setDataKategoriBarang(){
        Task<List<Kategori>> task = new Task<List<Kategori>>() {
            @Override 
            public List<Kategori> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return KategoriDAO.getAll(conCabang);
                }
            }
        };
        task.setOnRunning((event) -> {
            kategoriBarangLoading.setVisible(true);
        });
        task.setOnSucceeded((e) -> {
            allKategori.clear();
            allKategori.addAll(task.getValue());
            kategoriTable.refresh();
            kategoriBarangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            allKategori.clear();
            kategoriTable.refresh();
            kategoriBarangLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    private void ubahHargaRosok(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/HargaRosok.fxml");
        HargaRosokController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.getHargaRosok();
        controller.saveButton.setOnAction((event) -> {
            Stage child = new Stage();
            FXMLLoader verifikasiLoader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
            VerifikasiController verifikasiController = verifikasiLoader.getController();
            verifikasiController.setMainApp(mainApp, stage, child);
            verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    mainApp.closeDialog(stage, child);
                    if(Function.verifikasi(verifikasiController.usernameField.getText(), 
                            verifikasiController.passwordField.getText(), "Ubah Harga Rosok")){

                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                    sistem.setHargaRosok(Double.parseDouble(controller.hargaRosokField.getText().replaceAll(",", "")));
                                    return Service.saveUpdateSistem(conCabang, sistem);
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((e) -> {
                            mainApp.closeLoading();
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.closeDialog(mainApp.MainStage, stage);
                                mainApp.showMessage(Modality.NONE, "Success", "Harga Rosok berhasil disimpan");
                            }else
                                mainApp.showMessage(Modality.NONE, "Failed", status);
                        });
                        task.setOnFailed((e) -> {
                            mainApp.closeLoading();
                            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                        });
                        new Thread(task).start();

                    }else{
                        mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                + "atau otoritas tidak diijinkan");
                    }
                }
            });
        });
    }
    private void editKategori(Kategori k){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailKategoriBarang.fxml");
        DetailKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategoriBarang(k);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
            else if(controller.namaKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama kategori masih kosong");
            else if(controller.hargaBeliField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
            else if(controller.hargaJualField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual masih kosong");
            else{
                Stage child = new Stage();
                FXMLLoader verifikasiLoader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                VerifikasiController verifikasiController = verifikasiLoader.getController();
                verifikasiController.setMainApp(mainApp, stage, child);
                verifikasiController.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER)  {
                        mainApp.closeDialog(stage, child);
                        if(Function.verifikasi(verifikasiController.usernameField.getText(), 
                                verifikasiController.passwordField.getText(), "Ubah Kategori Barang")){
                            
                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                        k.setNamaKategori(controller.namaKategoriField.getText());
                                        k.setHargaBeli(Double.parseDouble(controller.hargaBeliField.getText().replaceAll(",", "")));
                                        k.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                                        return Service.saveUpdateKategoriBarang(conCabang, k);
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                setDataKategoriBarang();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.closeDialog(mainApp.MainStage, stage);
                                    mainApp.showMessage(Modality.NONE, "Success", "Kategori Barang berhasil disimpan");
                                }else
                                    mainApp.showMessage(Modality.NONE, "Failed", status);
                            });
                            task.setOnFailed((e) -> {
                                mainApp.closeLoading();
                                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                            });
                            new Thread(task).start();
                            
                        }else{
                            mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                    + "atau otoritas tidak diijinkan");
                        }
                    }
                });
            }
        });
    }
    
    //Kalkulator Emas
    @FXML private ComboBox<String> kategoriCombo;
    @FXML private TextField beratField;
    @FXML private TextField hargaBeliField;
    @FXML private TextField hargaJualField;
    @FXML
    private void getBarang(){
        if(kategoriCombo.getSelectionModel().getSelectedItem()!=null){
            getNilaiJual();
            beratField.selectAll();
            beratField.requestFocus();
        }
    }
    private void getNilaiJual(){
        if(kategoriCombo.getSelectionModel().getSelectedItem()!=null&&
                Double.parseDouble(beratField.getText().replaceAll(",", ""))>=0){
            double hargaBeli = 0;
            double hargaJual = 0;
            Boolean status = false;
            for(Kategori k : allKategori){
                if(k.getKodeKategori().toUpperCase().equals(
                        kategoriCombo.getSelectionModel().getSelectedItem().toUpperCase())){
                    hargaBeli = Double.parseDouble(beratField.getText().replaceAll(",", ""))*k.getHargaBeli();
                    hargaJual = Double.parseDouble(beratField.getText().replaceAll(",", ""))*k.getHargaJual();
                    kategoriCombo.getSelectionModel().select(
                            kategoriCombo.getSelectionModel().getSelectedItem().toUpperCase());
                    status = true;
                }
            }
            if(!status)
                kategoriCombo.getSelectionModel().select(null);
            hargaBeliField.setText(rp.format(hargaBeli));
            hargaJualField.setText(rp.format(hargaJual));
        }
    }
}
