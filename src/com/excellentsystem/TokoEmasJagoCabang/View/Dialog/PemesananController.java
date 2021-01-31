/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
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
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import static java.lang.Math.ceil;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.TextArea;
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
public class PemesananController {

    @FXML private TableView<PemesananDetail> pemesananDetailTable;
    @FXML private TableColumn<PemesananDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PemesananDetail, String> namaBarangColumn;
    @FXML private TableColumn<PemesananDetail, Number> beratColumn;
    @FXML private TableColumn<PemesananDetail, Number> hargaColumn;
    @FXML private TableColumn<PemesananDetail, Number> ongkosColumn;
    @FXML private TableColumn<PemesananDetail, Number> totalColumn;
    
    @FXML private Label noPemesananLabel;
    @FXML private Label tglPemesananLabel;
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
    @FXML private TextField beratField;
    @FXML private TextField hargaField;
    @FXML private TextField ongkosField;
    
    @FXML public CheckBox printSuratPemesananCheck;
    @FXML public TextArea keteranganField;
    @FXML public TextField totalPemesananField;
    @FXML public TextField titipUangField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public Member m;
    private List<Jenis> allJenis;
    private ObservableList<String> listJenis = FXCollections.observableArrayList();
    public ObservableList<PemesananDetail> listPemesananDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        ongkosColumn.setCellValueFactory(cellData -> cellData.getValue().ongkosProperty());
        ongkosColumn.setCellFactory(col -> getTableCell(rp));
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHarga()+cellData.getValue().getOngkos());
        });
        totalColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pemesananDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        pemesananDetailTable.setContextMenu(rowMenu);
        pemesananDetailTable.setRowFactory(table -> {
            TableRow<PemesananDetail> row = new TableRow<PemesananDetail>(){
                @Override
                public void updateItem(PemesananDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            listPemesananDetail.remove(item);
                            pemesananDetailTable.refresh();
                            hitungTotal();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            pemesananDetailTable.refresh();
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
            hitungHarga();
        });
        Function.setNumberField(hargaField, rp);
        Function.setNumberField(ongkosField, rp);
        Function.setNumberField(titipUangField, rp);
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
            if (keyEvent.getCode() == KeyCode.ENTER) { 
                getBarang();
            }
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                beratField.requestFocus();
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hitungHarga();
                hargaField.requestFocus();
                hargaField.selectAll();
            }
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                ongkosField.requestFocus();
                ongkosField.selectAll();
            }
        });
        ongkosField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
        });
        titipUangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
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
        pemesananDetailTable.setItems(listPemesananDetail);
        for(Node n : memberVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        kodeJenisCombo.setItems(listJenis);
        selectPelanggan();
    }
    public void setPemesanan(){
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
                    return PemesananHeadDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noPemesananLabel.setText(task.getValue());
                tglPemesananLabel.setText(
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
    public void setDetailPemesanan(PemesananHead p){
        Task<List<PemesananDetail>> task = new Task<List<PemesananDetail>>() {
            @Override 
            public List<PemesananDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return PemesananDetailDAO.getAllByNoPemesanan(conCabang, p.getNoPemesanan());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                p.setListPemesananDetail(task.getValue());

                kodeSalesField.setDisable(true);
                pelangganUmumRadio.setVisible(false);
                memberRadio.setVisible(false);
                noKartuHbox.setVisible(false);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                noTelpField.setDisable(true);
                addBarangVbox.setVisible(false);
                keteranganField.setDisable(true);
                titipUangField.setDisable(true);
                printSuratPemesananCheck.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);

                noPemesananLabel.setText(p.getNoPemesanan());
                tglPemesananLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy HH:mm:ss").format(tglSql.parse(p.getTglPemesanan())));
                kodeSalesField.setText(p.getKodeSales());
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                listPemesananDetail.addAll(p.getListPemesananDetail());
                keteranganField.setText(p.getKeterangan());
                totalPemesananField.setText(rp.format(p.getTotalPemesanan()));
                titipUangField.setText(rp.format(p.getTitipUang()));
                gridPane.getRowConstraints().get(4).setMinHeight(0);
                gridPane.getRowConstraints().get(4).setPrefHeight(0);
                gridPane.getRowConstraints().get(4).setMaxHeight(0);
                gridPane.getRowConstraints().get(8).setMinHeight(0);
                gridPane.getRowConstraints().get(8).setPrefHeight(0);
                gridPane.getRowConstraints().get(8).setMaxHeight(0);
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
                kodeJenisCombo.requestFocus();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Jenis jenis;
    private void getBarang(){
        jenis = null;
        namaBarangField.setText("");
        beratField.setText("0");
        hargaField.setText("0");
        ongkosField.setText("0");
        namaBarangField.setDisable(true);
        beratField.setDisable(true);
        hargaField.setDisable(true);
        ongkosField.setDisable(true);
        for(Jenis j : allJenis){
            if(j.getKodeJenis().toUpperCase().equals(kodeJenisCombo.getEditor().getText().toUpperCase())){
                kodeJenisCombo.getSelectionModel().select(j.getKodeJenis());
                jenis = j;
                namaBarangField.setText(jenis.getNamaJenis());
                namaBarangField.setDisable(false);
                beratField.setDisable(false);
                hargaField.setDisable(false);
                ongkosField.setDisable(false);
                hitungHarga();
                namaBarangField.requestFocus();
                namaBarangField.selectAll();
            }
        }
    }
    private void hitungHarga(){
        double berat = Double.parseDouble(beratField.getText().replaceAll(",", ""));
        if(jenis!=null){
            hargaField.setText(rp.format(ceil(jenis.getKategori().getHargaJual()*berat/500)*500));
        }
    }
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
        }else if(Double.parseDouble(beratField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            beratField.requestFocus();
            beratField.selectAll();
        }else if(Double.parseDouble(hargaField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Harga masih kosong");
            hargaField.requestFocus();
            hargaField.selectAll();
        }else{
            PemesananDetail d = new PemesananDetail();
            d.setKodeJenis(kodeJenisCombo.getSelectionModel().getSelectedItem());
            d.setNamaBarang(namaBarangField.getText());
            d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
            d.setHarga(Double.parseDouble(hargaField.getText().replaceAll(",", "")));;
            d.setOngkos(Double.parseDouble(ongkosField.getText().replaceAll(",", "")));
            listPemesananDetail.add(d);
            pemesananDetailTable.refresh();
            hitungTotal();

            kodeJenisCombo.getSelectionModel().clearSelection();
            namaBarangField.setText("");
            beratField.setText("0");
            hargaField.setText("0");
            ongkosField.setText("0");
            kodeJenisCombo.requestFocus();
            kodeJenisCombo.getEditor().selectAll();
        }
    }
    private void hitungTotal(){
        double total = 0;
        for(PemesananDetail d : listPemesananDetail){
            total = total + d.getHarga()+d.getOngkos();
        }
        totalPemesananField.setText(rp.format(total));
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
