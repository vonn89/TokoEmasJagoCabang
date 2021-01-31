/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.ServisDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import java.sql.Connection;
import java.time.LocalDate;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class TerimaServisController {

    @FXML private TextField noServisField;
    @FXML private TextField tglServisField;
    @FXML public TextField salesTerimaField;
    
    @FXML private VBox memberVbox;
    @FXML private RadioButton pelangganUmumRadio;
    @FXML public RadioButton memberRadio;
    @FXML private HBox noKartuHbox;
    @FXML private TextField noKartuField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    
    @FXML public TextField namaBarangField;
    @FXML public TextField beratField;
    @FXML public TextField kategoriServisField;
    @FXML public DatePicker tglAmbilPicker;
    
    @FXML public CheckBox printSuratServisCheck;
    @FXML public Button saveButton;
    
    public Member m;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tglAmbilPicker.setConverter(Function.getTglConverter());
        tglAmbilPicker.setValue(LocalDate.parse(sistem.getTglSystem()).plusWeeks(1));
        tglAmbilPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellDisableBefore(LocalDate.parse(sistem.getTglSystem())));
        
        Function.setNumberField(beratField, gr);
        salesTerimaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                salesTerimaField.setText(Function.ceksales(salesTerimaField.getText()));
                if(!salesTerimaField.getText().equals("")){
                    namaField.requestFocus();
                    namaField.selectAll();
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
                namaBarangField.requestFocus();
                namaBarangField.selectAll();
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
                kategoriServisField.requestFocus();
                kategoriServisField.selectAll();
            }
        });
        kategoriServisField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                tglAmbilPicker.requestFocus();
            }
        });
        tglAmbilPicker.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
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
        for(Node n : memberVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        selectPelanggan();
    }
    public void setServis(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    return ServisDAO.getId(conCabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                noServisField.setText(task.getValue());
                tglServisField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
                salesTerimaField.requestFocus();
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
                namaBarangField.requestFocus();
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
                            namaBarangField.requestFocus();
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
