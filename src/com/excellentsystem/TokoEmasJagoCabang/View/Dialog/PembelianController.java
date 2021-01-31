/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoCabang.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PembelianController {

    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PembelianDetail, Number> persentaseEmasColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratPersenColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaColumn;
    
    @FXML private Label noPembelianLabel;
    @FXML private Label tglPembelianLabel;
    @FXML public TextField kodeSalesField;
    
    @FXML private VBox memberVbox;
    @FXML private RadioButton pelangganUmumRadio;
    @FXML public RadioButton memberRadio;
    @FXML private HBox noKartuHbox;
    @FXML private TextField noKartuField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    
    @FXML private VBox addBarangVbox;
    @FXML private ComboBox<String> kodeJenisCombo;
    @FXML private TextField namaBarangField;
    @FXML private TextField qtyField;
    @FXML private TextField beratKotorField;
    @FXML private TextField beratBersihField;
    @FXML private TextField persentaseEmasField;
    @FXML private TextField hargaField;
    
    @FXML public CheckBox printSuratPembelianCheck;
    @FXML public TextField totalPembelianField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public Member m;
    private List<Jenis> allJenis;
    private ObservableList<String> listJenis = FXCollections.observableArrayList();
    public ObservableList<PembelianDetail> listPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
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
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pembelianDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(rowMenu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<PembelianDetail> row = new TableRow<PembelianDetail>(){
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            removeBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            pembelianDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rowMenu.getItems().addAll(hapus);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        Function.setNumberField(qtyField, rp);
        Function.setNumberField(beratKotorField, gr);
        Function.setNumberField(hargaField, rp);
        persentaseEmasField.setOnKeyReleased((event) -> {
            try{
                String string = persentaseEmasField.getText();
                if(string.contains("-"))
                    persentaseEmasField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            persentaseEmasField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                persentaseEmasField.end();
            }catch(Exception e){
                persentaseEmasField.undo();
            }
            hitungHarga();
        });
        beratBersihField.setOnKeyReleased((event) -> {
            try{
                String string = beratBersihField.getText();
                if(string.contains("-"))
                    beratBersihField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            beratBersihField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            beratBersihField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        beratBersihField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratBersihField.end();
            }catch(Exception e){
                beratBersihField.undo();
            }
            hitungHarga();
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals("")){
                    if(noKartuHbox.isVisible()){
                        noKartuField.requestFocus();
                        noKartuField.selectAll();
                    }else{
                        namaField.requestFocus();
                        namaField.selectAll();
                    }
                }
            }
        });
        noKartuField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                if(!noKartuField.getText().equals(""))
                    getMember();
                else{
                    namaField.requestFocus();
                    namaField.selectAll();
                }
            }
        });
        namaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                alamatField.requestFocus();
                alamatField.selectAll();
            }
        });
        alamatField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                noTelpField.requestFocus();
                noTelpField.selectAll();
            }
        });
        noTelpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeJenisCombo.getEditor().requestFocus();
                kodeJenisCombo.getEditor().selectAll();
            }
        });
        kodeJenisCombo.setOnAction((event) -> {
                getBarang();
        });
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                getBarang();
            }
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                qtyField.requestFocus();
                qtyField.selectAll();
            }
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) { 
                beratKotorField.requestFocus();
                beratKotorField.selectAll();
            }
        });
        beratKotorField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                beratBersihField.requestFocus();
                beratBersihField.selectAll();
            }
        });
        beratBersihField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hitungHarga();
                if(persentaseEmasField.isDisable()){
                    hargaField.requestFocus();
                    hargaField.selectAll();
                }else{
                    persentaseEmasField.requestFocus();
                    persentaseEmasField.selectAll();
                }
            }
        });
        persentaseEmasField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hitungHarga();
                hargaField.requestFocus();
                hargaField.selectAll();
            }
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
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
        pembelianDetailTable.setItems(listPembelianDetail);
        for(Node n : memberVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        kodeJenisCombo.setItems(listJenis);
        selectPelanggan();
    }
    public void setPembelian(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    listJenis.clear();
                    allJenis = JenisDAO.getAll(conCabang);
                    List<Kategori> allKategori = KategoriDAO.getAll(conCabang);
                    for(Jenis j : allJenis){
                        for(Kategori k : allKategori){
                            if(j.getKodeKategori().equals(k.getKodeKategori()))
                                j.setKategori(k);
                        }
                        listJenis.add(j.getKodeJenis());
                    }
                    listJenis.add("RSK");
                    return PembelianHeadDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noPembelianLabel.setText(task.getValue());
                tglPembelianLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy").format(tglBarang.parse(sistem.getTglSystem()))+" "+
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
                kodeSalesField.requestFocus();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML private GridPane gridPane;
    public void setDetailPembelian(PembelianHead p){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
            @Override 
            public List<PembelianDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return PembelianDetailDAO.getAllByNoPembelian(conCabang, p.getNoPembelian());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                p.setListPembelianDetail(task.getValue());

                kodeSalesField.setDisable(true);
                pelangganUmumRadio.setVisible(false);
                memberRadio.setVisible(false);
                noKartuHbox.setVisible(false);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                noTelpField.setDisable(true);
                addBarangVbox.setVisible(false);
                printSuratPembelianCheck.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);

                noPembelianLabel.setText(p.getNoPembelian());
                tglPembelianLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy HH:mm:ss").format(tglSql.parse(p.getTglPembelian())));
                kodeSalesField.setText(p.getKodeSales());
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                listPembelianDetail.addAll(p.getListPembelianDetail());
                totalPembelianField.setText(rp.format(p.getTotalPembelian()));
                gridPane.getRowConstraints().get(4).setMinHeight(0);
                gridPane.getRowConstraints().get(4).setPrefHeight(0);
                gridPane.getRowConstraints().get(4).setMaxHeight(0);
                gridPane.getRowConstraints().get(7).setMinHeight(0);
                gridPane.getRowConstraints().get(7).setPrefHeight(0);
                gridPane.getRowConstraints().get(7).setMaxHeight(0);
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void selectPelanggan(){
        if(pelangganUmumRadio.isSelected()){
            noKartuHbox.setVisible(false);
            namaField.setDisable(false);
            alamatField.setDisable(false);
            noTelpField.setDisable(false);
            
            m = null;
            noKartuField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
        }else if(memberRadio.isSelected()){
            noKartuHbox.setVisible(true);
            namaField.setDisable(true);
            alamatField.setDisable(true);
            noTelpField.setDisable(true);
            
            m = null;
            noKartuField.setText("");
            namaField.setText("");
            alamatField.setText("");
            noTelpField.setText("");
        }
    }
    private void getMember(){
        Task<Member> task = new Task<Member>() {
            @Override 
            public Member call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return MemberDAO.getByNoKartuOrNoRfid(conPusat, noKartuField.getText());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            m = task.getValue();
            if(m==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Member tidak ditemukan");
                noKartuField.selectAll();
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
            }else if(m.getStatus().equals("false")){
                mainApp.showMessage(Modality.NONE, "Warning", "Member sudah dihapus/tidak aktif");
                noKartuField.selectAll();
                m = null;
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
            }else{
                namaField.setText(m.getNama());
                alamatField.setText(m.getAlamat());
                noTelpField.setText(m.getNoTelp());
                kodeJenisCombo.getEditor().requestFocus();
                kodeJenisCombo.getEditor().selectAll();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        jenis = null;
        namaBarangField.setText("");
        qtyField.setText("0");
        beratKotorField.setText("0");
        beratBersihField.setText("0");
        persentaseEmasField.setText("0");
        hargaField.setText("0");
        namaBarangField.setDisable(true);
        qtyField.setDisable(true);
        beratKotorField.setDisable(true);
        beratBersihField.setDisable(true);
        persentaseEmasField.setDisable(true);
        hargaField.setDisable(true);
        if(kodeJenisCombo.getEditor().getText().toUpperCase().equals("RSK")){
            kodeJenisCombo.getSelectionModel().select("RSK");
            namaBarangField.setText("ROSOK");
            namaBarangField.setDisable(false);
            qtyField.setDisable(false);
            beratKotorField.setDisable(false);
            beratBersihField.setDisable(false);
            persentaseEmasField.setDisable(false);
            hargaField.setDisable(false);
            hitungHarga();
            namaBarangField.requestFocus();
            namaBarangField.selectAll();
        }else{
            for(Jenis j : allJenis){
                if(j.getKodeJenis().toUpperCase().equals(kodeJenisCombo.getEditor().getText().toUpperCase())){
                    kodeJenisCombo.getSelectionModel().select(j.getKodeJenis());
                    jenis = j;
                    namaBarangField.setText(jenis.getNamaJenis());
                    persentaseEmasField.setText(gr.format(jenis.getKategori().getPersentaseEmas()));
                    namaBarangField.setDisable(false);
                    qtyField.setDisable(false);
                    beratKotorField.setDisable(false);
                    beratBersihField.setDisable(false);
                    persentaseEmasField.setDisable(true);
                    hargaField.setDisable(false);
                    hitungHarga();
                    namaBarangField.requestFocus();
                    namaBarangField.selectAll();
                }
            }
        }
    }
    private void hitungHarga(){
        double berat = Double.parseDouble(beratBersihField.getText().replaceAll(",", ""));
        if(jenis!=null){
            hargaField.setText(rp.format(ceil(jenis.getKategori().getHargaBeli()*berat/500)*500));
        }else if(kodeJenisCombo.getSelectionModel().getSelectedItem().equals("RSK")){
            double persentaseEmas = Double.parseDouble(persentaseEmasField.getText().replaceAll(",", ""));
            hargaField.setText(rp.format(ceil(sistem.getHargaRosok()*persentaseEmas/100*berat/500)*500));
        }
    }
    private Jenis jenis;
    @FXML
    private void setBarang(){
        if(kodeJenisCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            kodeJenisCombo.requestFocus();
            kodeJenisCombo.getEditor().selectAll();
        }else if(namaBarangField.getText()==null||namaBarangField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            namaBarangField.requestFocus();
            namaBarangField.selectAll();
        }else if(Double.parseDouble(qtyField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            qtyField.requestFocus();
            qtyField.selectAll();
        }else if(Double.parseDouble(beratKotorField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat kotor masih kosong");
            beratKotorField.requestFocus();
            beratKotorField.selectAll();
        }else if(Double.parseDouble(beratBersihField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih masih kosong");
            beratBersihField.requestFocus();
            beratBersihField.selectAll();
        }else if(Double.parseDouble(persentaseEmasField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            persentaseEmasField.requestFocus();
            persentaseEmasField.selectAll();
        }else if(Double.parseDouble(hargaField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Harga masih kosong");
            hargaField.requestFocus();
            hargaField.selectAll();
        }else if(Double.parseDouble(beratKotorField.getText().replaceAll(",", ""))<
                Double.parseDouble(beratBersihField.getText().replaceAll(",", ""))){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih lebih berat daripada berat kotor");
            beratKotorField.requestFocus();
            beratKotorField.selectAll();
        }else{
            for(Jenis j : allJenis){
                if(j.getKodeJenis().equals(kodeJenisCombo.getSelectionModel().getSelectedItem()))
                    jenis = j;
            }
            int qty = Integer.parseInt(qtyField.getText().replaceAll(",", ""));
            double persentaseEmas = Double.parseDouble(persentaseEmasField.getText().replaceAll(",", ""));
            double beratKotor = Double.parseDouble(beratKotorField.getText().replaceAll(",", ""));
            double beratBersih = Double.parseDouble(beratBersihField.getText().replaceAll(",", ""));
            double hargaBeli = Double.parseDouble(hargaField.getText().replaceAll(",", ""));
            String kodeKategori = "";
            String kodeJenis = kodeJenisCombo.getSelectionModel().getSelectedItem();
            double hargaKomp = 0;
            if(jenis!=null){
                kodeKategori = jenis.getKodeKategori();
                hargaKomp = ceil(jenis.getKategori().getHargaBeli()*beratBersih/500)*500;
            }else if(kodeJenisCombo.getSelectionModel().getSelectedItem().equals("RSK")){
                kodeKategori = "RSK";
                hargaKomp = ceil(sistem.getHargaRosok()*persentaseEmas/100*beratBersih/500)*500;
            }
            if(hargaKomp!=0){
                PembelianDetail d = new PembelianDetail();
                d.setKodeKategori(kodeKategori);
                d.setKodeJenis(kodeJenis);
                d.setNamaBarang(namaBarangField.getText());
                d.setQty(qty);
                d.setBeratKotor(beratKotor);
                d.setBeratBersih(beratBersih);
                d.setPersentaseEmas(persentaseEmas);
                d.setBeratPersen(pembulatan(beratKotor*persentaseEmas/100));
                d.setSuratPembelian("");
                d.setHargaKomp(hargaKomp);
                d.setHargaBeli(hargaBeli);
                d.setStatusAmbil("false");
                d.setNoAmbilBarang("");
                
                if(hargaKomp<hargaBeli || (jenis!=null&&"true".equals(jenis.getVerifikasi()))){
                    Stage child = new Stage();
                    FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Verifikasi.fxml");
                    VerifikasiController controller = loader.getController();
                    controller.setMainApp(mainApp, stage, child);
                    controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                        if (keyEvent.getCode() == KeyCode.ENTER)  {
                            mainApp.closeDialog(stage, child);
                            if(Function.verifikasi(controller.usernameField.getText(), 
                                    controller.passwordField.getText(), "Pembelian")){
                                
                                listPembelianDetail.add(d);
                                pembelianDetailTable.refresh();
                                hitungTotal();

                                kodeJenisCombo.getSelectionModel().clearSelection();
                                jenis=null;
                                namaBarangField.setText("");
                                qtyField.setText("0");
                                beratKotorField.setText("0");
                                beratBersihField.setText("0");
                                persentaseEmasField.setText("0");
                                hargaField.setText("0");
                                kodeJenisCombo.requestFocus();
                                kodeJenisCombo.getEditor().selectAll();
                                
                            }else{
                                mainApp.showMessage(Modality.NONE, "Warning", "username & password masih salah \n"
                                        + "atau otoritas tidak diijinkan");
                            }
                        }
                    });

                }else{
                    listPembelianDetail.add(d);
                    pembelianDetailTable.refresh();
                    hitungTotal();

                    kodeJenisCombo.getSelectionModel().clearSelection();
                    jenis=null;
                    namaBarangField.setText("");
                    qtyField.setText("0");
                    beratKotorField.setText("0");
                    beratBersihField.setText("0");
                    persentaseEmasField.setText("0");
                    hargaField.setText("0");
                    kodeJenisCombo.requestFocus();
                    kodeJenisCombo.getEditor().selectAll();
                }
            }
        }
    }
    private void removeBarang(PembelianDetail d){
        listPembelianDetail.remove(d);
        pembelianDetailTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double total = 0;
        for(PembelianDetail d : listPembelianDetail){
            total = total + d.getHargaBeli();
        }
        totalPembelianField.setText(rp.format(total));
    }
    @FXML
    private void searchMember(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/CariMember.fxml");
        CariMemberController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.memberTable.setRowFactory(table -> {
            TableRow<Member> row = new TableRow<Member>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            m = row.getItem();
                            noKartuField.setText(m.getNoKartu());
                            namaField.setText(m.getNama());
                            alamatField.setText(m.getAlamat());
                            noTelpField.setText(m.getNoTelp());
                            kodeSalesField.requestFocus();
                            kodeSalesField.selectAll();
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
