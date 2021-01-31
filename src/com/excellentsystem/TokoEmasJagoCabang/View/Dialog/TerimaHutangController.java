/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BungaHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
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
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
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
public class TerimaHutangController  {

    @FXML private GridPane gridPane;
    
    @FXML private TableView<HutangDetail> hutangDetailTable;
    @FXML private TableColumn<HutangDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<HutangDetail, String> namaBarangColumn;
    @FXML private TableColumn<HutangDetail, Number> beratColumn;
    @FXML private TableColumn<HutangDetail, Number> nilaiJualColumn;
    
    @FXML private Label noHutangLabel;
    @FXML private Label tglHutangLabel;
    @FXML public TextField kodeSalesField;
    
    @FXML private VBox memberVbox;
    @FXML public RadioButton pelangganUmumRadio;
    @FXML public RadioButton memberRadio;
    @FXML private HBox noKartuHbox;
    @FXML private TextField noKartuField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    
    @FXML private VBox addBarangVbox;
    @FXML private ComboBox<String> kodeKategoriCombo;
    @FXML private TextField namaBarangField;
    @FXML private TextField beratField;
    @FXML private TextField nilaiJualField;
    
    @FXML public Label totalBeratLabel;
    @FXML public Label totalNilaiJualLabel;
    @FXML public Label totalMaxPinjamanLabel;
    @FXML public CheckBox printSuratHutangCheck;
    @FXML public TextField totalPinjamanField;
    @FXML public TextField bungaPersenField;
    @FXML public TextField bungaRpField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public Member m;
    private List<Kategori> allKategori;
    public List<BungaHutang> listBungaHutang;
    private ObservableList<String> listKategori = FXCollections.observableArrayList();
    public ObservableList<HutangDetail> listHutangDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        nilaiJualColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiJualProperty());
        nilaiJualColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            hutangDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        hutangDetailTable.setContextMenu(rowMenu);
        hutangDetailTable.setRowFactory(table -> {
            TableRow<HutangDetail> row = new TableRow<HutangDetail>(){
                @Override
                public void updateItem(HutangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            listHutangDetail.remove(item);
                            hitungTotal();
                            getBungaPersen();
                            hutangDetailTable.refresh();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            hutangDetailTable.refresh();
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
            getNilaiJual();
        });
        totalPinjamanField.setOnKeyReleased((event) -> {
            try{
                String string = totalPinjamanField.getText();
                if(string.contains("-"))
                    totalPinjamanField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            totalPinjamanField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            totalPinjamanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        totalPinjamanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                totalPinjamanField.end();
            }catch(Exception e){
                totalPinjamanField.undo();
            }
            getBungaPersen();
        });
        bungaPersenField.setOnKeyReleased((event) -> {
            try{
                String string = bungaPersenField.getText();
                if(string.contains("-"))
                    bungaPersenField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            bungaPersenField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            bungaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        bungaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                bungaPersenField.end();
            }catch(Exception e){
                bungaPersenField.undo();
            }
            getBungaRp();
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
                kodeKategoriCombo.getEditor().requestFocus();
                kodeKategoriCombo.getEditor().selectAll();
            }
        });
        kodeKategoriCombo.setOnAction((event) -> {
            getBarang();
        });
        kodeKategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
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
                getNilaiJual();
                setBarang();
            }
        });
        totalPinjamanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                bungaPersenField.selectAll();
                bungaPersenField.requestFocus();
            }
        });
        bungaPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
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
        hutangDetailTable.setItems(listHutangDetail);
        for(Node n : memberVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        kodeKategoriCombo.setItems(listKategori);
        selectPelanggan();
    }
    public void setNewHutang(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    listBungaHutang = BungaHutangDAO.getAll(conCabang);
                    listKategori.clear();
                    allKategori = KategoriDAO.getAll(conCabang);
                    for(Kategori k : allKategori){
                        listKategori.add(k.getKodeKategori());
                    }
                    return HutangHeadDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noHutangLabel.setText(task.getValue());
                tglHutangLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy").format(tglBarang.parse(sistem.getTglSystem()))+" "+
                    new SimpleDateFormat("HH:mm:ss").format(new Date()));
                kodeSalesField.requestFocus();
            }catch(Exception ex){
                ex.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setDetailHutang(HutangHead h){
        Task<List<HutangDetail>> task = new Task<List<HutangDetail>>() {
            @Override 
            public List<HutangDetail> call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return HutangDetailDAO.getAllByNoHutang(conCabang, h.getNoHutang());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                h.setListHutangDetail(task.getValue());

                kodeSalesField.setDisable(true);
                pelangganUmumRadio.setVisible(false);
                memberRadio.setVisible(false);
                noKartuHbox.setVisible(false);
                namaField.setDisable(true);
                alamatField.setDisable(true);
                noTelpField.setDisable(true);
                addBarangVbox.setVisible(false);
                totalPinjamanField.setDisable(true);
                bungaPersenField.setDisable(true);
                printSuratHutangCheck.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);

                noHutangLabel.setText(h.getNoHutang());
                tglHutangLabel.setText(
                    new SimpleDateFormat("EEEEEE, dd MMMMM yyyy HH:mm:ss").format(tglSql.parse(h.getTglHutang())));
                kodeSalesField.setText(h.getKodeSales());
                namaField.setText(h.getNama());
                alamatField.setText(h.getAlamat());
                noTelpField.setText(h.getNoTelp());
                listHutangDetail.addAll(h.getListHutangDetail());
                totalPinjamanField.setText(rp.format(h.getTotalHutang()));
                bungaPersenField.setText(gr.format(h.getBungaPersen()));
                bungaRpField.setText(rp.format(h.getBungaKomp()));
                hitungTotal();
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
    private void getBungaPersen(){
        double pinjaman = Double.parseDouble(totalPinjamanField.getText().replaceAll(",", ""));
        double bunga = 0;
        for(BungaHutang b : listBungaHutang){
            if(b.getMinJumlahRp()<=pinjaman&&pinjaman<=b.getMaxJumlahRp())
                bunga = b.getBungaPersen();
        }
        bungaPersenField.setText(gr.format(bunga));
        getBungaRp();
    }
    private void getBungaRp(){
        double pinjaman = Double.parseDouble(totalPinjamanField.getText().replaceAll(",", ""));
        double bunga = Double.parseDouble(bungaPersenField.getText().replaceAll(",", ""));
        double bungaRp = pinjaman*bunga/100;
        bungaRpField.setText(rp.format(Math.ceil(bungaRp/500)*500));
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalNilaiJual = 0;
        for(HutangDetail d : listHutangDetail){
            totalBerat = totalBerat + d.getBerat();
            totalNilaiJual = totalNilaiJual + d.getNilaiJual();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalNilaiJualLabel.setText(rp.format(totalNilaiJual));
        totalMaxPinjamanLabel.setText(rp.format(totalNilaiJual*70/100));
    }
    
    @FXML 
    private void getBarang(){
        if(kodeKategoriCombo.getSelectionModel().getSelectedItem()!=null){
            getNilaiJual();
            namaBarangField.selectAll();
            namaBarangField.requestFocus();
        }
    }
    private void getNilaiJual(){
        if(kodeKategoriCombo.getSelectionModel().getSelectedItem()!=null&&
                Double.parseDouble(beratField.getText().replaceAll(",", ""))>=0){
            double hargaJual = 0;
            Boolean status = false;
            for(Kategori k : allKategori){
                if(k.getKodeKategori().toUpperCase().equals(
                        kodeKategoriCombo.getSelectionModel().getSelectedItem().toUpperCase())){
                    hargaJual = Double.parseDouble(beratField.getText().replaceAll(",", ""))*k.getHargaJual();
                    kodeKategoriCombo.getSelectionModel().select(
                            kodeKategoriCombo.getSelectionModel().getSelectedItem().toUpperCase());
                    status = true;
                }
            }
            if(status){
                nilaiJualField.setText(rp.format(hargaJual));
            }else{
                kodeKategoriCombo.getSelectionModel().select(null);
                nilaiJualField.setText(rp.format(hargaJual));
            }
        }
    }
    @FXML private void setBarang(){
        if(kodeKategoriCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            kodeKategoriCombo.requestFocus();
            kodeKategoriCombo.getEditor().selectAll();
        }else if(namaBarangField.getText()==null||namaBarangField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            namaBarangField.requestFocus();
            namaBarangField.selectAll();
        }else if(Double.parseDouble(beratField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            beratField.requestFocus();
            beratField.selectAll();
        }else{
            HutangDetail d  = new HutangDetail();
            d.setKodeKategori(kodeKategoriCombo.getSelectionModel().getSelectedItem());
            d.setNamaBarang(namaBarangField.getText());
            d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
            d.setNilaiJual(Double.parseDouble(nilaiJualField.getText().replaceAll(",", "")));
            listHutangDetail.add(d);
            hutangDetailTable.refresh();
            hitungTotal();
            getBungaPersen();

            kodeKategoriCombo.getSelectionModel().select(null);
            namaBarangField.setText("");
            beratField.setText("0");
            nilaiJualField.setText("0");
            kodeKategoriCombo.requestFocus();
        }
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
                kodeKategoriCombo.requestFocus();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
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
