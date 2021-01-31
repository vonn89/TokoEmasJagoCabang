/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AmbilServisController {

    @FXML private TextField noServisField;
    @FXML private TextField tglAmbilField;
    @FXML public TextField salesAmbilField;
    
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private TextField namaBarangField;
    @FXML private TextField beratField;
    @FXML private TextField kategoriServisField;
    
    @FXML private ToggleGroup jenisPembayaran = new ToggleGroup();
    @FXML private GridPane cashPane;
    @FXML public ToggleButton cashButton;
    @FXML public TextField jumlahPembayaranField;
    
    @FXML private GridPane rewardPoinPane;
    @FXML public ToggleButton rewardPoinButton;
    @FXML private TextField noKartuField;
    @FXML private TextField namaMemberField;
    @FXML private TextField alamatMemberField;
    @FXML private TextField jumlahPoinTersisaField;
    @FXML private Label nilaiPoinLabel;
    @FXML private Label nilaiPoin2Label;
    @FXML private TextField jumlahRpTersisaField;
    @FXML public TextField poinDigunakanField;
    @FXML public TextField jumlahRpDigunakanField;
    
    @FXML public Button saveButton;
    
    public Member member;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        salesAmbilField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                salesAmbilField.setText(Function.ceksales(salesAmbilField.getText()));
                if(cashButton.isSelected()){
                    jumlahPembayaranField.requestFocus();
                    jumlahPembayaranField.selectAll();
                }else if(rewardPoinButton.isSelected()){
                    noKartuField.requestFocus();
                    noKartuField.selectAll();
                }
            }
        });
        Function.setNumberField(jumlahPembayaranField, rp);
        poinDigunakanField.setOnKeyReleased((event) -> {
            try{
                String string = poinDigunakanField.getText();
                if(string.contains("-"))
                    poinDigunakanField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            poinDigunakanField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            poinDigunakanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        poinDigunakanField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                poinDigunakanField.end();
            }catch(Exception e){
                poinDigunakanField.undo();
            }
            jumlahRpDigunakanField.setText(rp.format(Double.parseDouble(
                    poinDigunakanField.getText().replaceAll(",", ""))*sistem.getNilaiPoin()));
        });
        jumlahPembayaranField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
        poinDigunakanField.setOnKeyPressed((KeyEvent keyEvent) -> {
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
        nilaiPoinLabel.setText("x "+rp.format(sistem.getNilaiPoin())+" =");
        nilaiPoin2Label.setText("x "+rp.format(sistem.getNilaiPoin()));
    }
    public void setServis(Servis s){
        noServisField.setText(s.getNoServis());
        tglAmbilField.setText(Function.getSystemDate());
        namaField.setText(s.getNama());
        alamatField.setText(s.getAlamat());
        noTelpField.setText(s.getNoTelp());
        namaBarangField.setText(s.getNamaBarang());
        beratField.setText(gr.format(s.getBerat()));
        kategoriServisField.setText(s.getKategoriServis());
        jenisPembayaran.selectToggle(cashButton);
        selectPembayaran();
    }
    @FXML
    private void selectPembayaran(){
        ToggleButton selected = (ToggleButton) jenisPembayaran.getSelectedToggle();
        if(selected==null){
            rewardPoinPane.setVisible(false);
            cashPane.setVisible(false);
            stage.setHeight(520);
            stage.setMaxHeight(520);
        }else if(selected.equals(cashButton)){
            cashPane.setVisible(true);
            rewardPoinPane.setVisible(false);
            stage.setHeight(570);
            stage.setMaxHeight(570);
            
            jumlahPembayaranField.setText("0");
            jumlahPembayaranField.requestFocus();
        }else if(selected.equals(rewardPoinButton)){
            rewardPoinPane.setVisible(true);
            cashPane.setVisible(false);
            stage.setHeight(720);
            stage.setMaxHeight(720);
            
            resetRewardPoin();
        }
    }
    private void resetRewardPoin(){
        member = null;
        noKartuField.setText("");
        namaMemberField.setText("");
        alamatMemberField.setText("");
        jumlahPoinTersisaField.setText("");
        jumlahRpTersisaField.setText("");
        poinDigunakanField.setText("0");
        jumlahRpDigunakanField.setText("0");
        noKartuField.requestFocus();
    }
    @FXML
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
            member = task.getValue();
            if(member==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Member tidak ditemukan");
                resetRewardPoin();
            }else if(member.getStatus().equals("false")){
                mainApp.showMessage(Modality.NONE, "Warning", "Member sudah dihapus/tidak aktif");
                resetRewardPoin();
            }else{
                int poin = member.getPoin();
                namaField.setText(member.getNama());
                alamatField.setText(member.getAlamat());
                noTelpField.setText(member.getNoTelp());
                jumlahPoinTersisaField.setText(rp.format(poin));
                jumlahRpTersisaField.setText(rp.format(poin*sistem.getNilaiPoin()));
                poinDigunakanField.setText("0");
                jumlahRpDigunakanField.setText("0");
                poinDigunakanField.requestFocus();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
