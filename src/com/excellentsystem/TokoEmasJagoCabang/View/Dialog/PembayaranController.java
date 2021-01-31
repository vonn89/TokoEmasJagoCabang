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
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PembayaranController {

    @FXML private Label totalPenjualanLabel;
    @FXML private TextField totalPenjualanField;
    @FXML private TextField pembayaranField;
    @FXML public Label sisaPembayaranLabel;
    @FXML private TextField sisaPembayaranField;
    
    @FXML private Label getPoinLabel;
    @FXML private CheckBox kurangBayarCheckBox;
    
    @FXML private ToggleGroup jenisPembayaran = new ToggleGroup();
    @FXML private GridPane cashPane;
    @FXML private ToggleButton cashButton;
    @FXML private HBox hbox;
    @FXML private Button uangPasButton;
    @FXML private Button lebihSepuluhButton;
    @FXML private Button lebihLimaPuluhButton;
    @FXML private Button lebihSeratusButton;
    @FXML private TextField jumlahPembayaranField;
    
    @FXML private GridPane rewardPoinPane;
    @FXML private ToggleButton rewardPoinButton;
    @FXML private TextField noKartuField;
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private TextField jumlahPoinTersisaField;
    @FXML private Label nilaiPoinLabel;
    @FXML private Label nilaiPoin2Label;
    @FXML private TextField jumlahRpTersisaField;
    @FXML private TextField poinDigunakanField;
    @FXML private TextField jumlahRpDigunakanField;
    
    @FXML private HBox salesHbox;
    @FXML public TextField kodeSalesField;
    @FXML public Button saveButton;
    
    public Member getMember;
    private Member memberBayar;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public List<PembayaranPenjualan> listPembayaran = new ArrayList<>();
    public void initialize() {
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
                addPembayaranCash();
        });
        poinDigunakanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                addPembayaranRewardPoin();
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
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
        reset();
        nilaiPoinLabel.setText("x "+rp.format(sistem.getNilaiPoin())+" =");
        nilaiPoin2Label.setText("x "+rp.format(sistem.getNilaiPoin()));
        for(Node n : hbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
    }
    @FXML
    private void reset(){
        listPembayaran = new ArrayList<>();
        pembayaranField.setText("0");
        if(titipanUang!=0){
            PembayaranPenjualan pp = new PembayaranPenjualan();
            pp.setJenisPembayaran("Cash");
            pp.setKeterangan("");
            pp.setJumlahPembayaran(titipanUang);
            listPembayaran.add(pp);
            
            double totalPembayaran = Double.parseDouble(pembayaranField.getText().replaceAll(",", ""));
            pembayaranField.setText(rp.format(totalPembayaran+titipanUang));
        }
        jenisPembayaran.selectToggle(null);
        selectPembayaran();
        hitungTotal();
    }
    public void setTotalPenjualan(double totalPenjualan, Member member){
        totalPenjualanField.setText(rp.format(totalPenjualan));
        getMember = member;
        reset();
    }
    double titipanUang = 0;
    public void setTitipanUang(double jumlahBayar){
        titipanUang = jumlahBayar;
        reset();
    }
    public void setPembayaranKekurangan(double sisaPembayaran, String kodeMember){
        if(!kodeMember.equals("")){
            Task<Member> task = new Task<Member>() {
                @Override 
                public Member call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return MemberDAO.get(conPusat, kodeMember);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getMember = task.getValue();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
        totalPenjualanLabel.setText("Kekurangan");
        kurangBayarCheckBox.setVisible(false);
        salesHbox.setVisible(true);
        totalPenjualanField.setText(rp.format(sisaPembayaran));
        reset();
    }
    @FXML
    private void selectPembayaran(){
        ToggleButton selected = (ToggleButton) jenisPembayaran.getSelectedToggle();
        if(selected==null){
            rewardPoinPane.setVisible(false);
            cashPane.setVisible(false);
            stage.setHeight(380);
            stage.setMaxHeight(380);
        }else if(selected.equals(cashButton)){
            cashPane.setVisible(true);
            rewardPoinPane.setVisible(false);
            stage.setHeight(540);
            stage.setMaxHeight(540);
            
            jumlahPembayaranField.setText("0");
            jumlahPembayaranField.requestFocus();
        }else if(selected.equals(rewardPoinButton)){
            rewardPoinPane.setVisible(true);
            cashPane.setVisible(false);
            stage.setHeight(640);
            stage.setMaxHeight(640);
            
            resetRewardPoin();
        }
    }
    private void resetRewardPoin(){
        memberBayar = null;
        noKartuField.setText("");
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        jumlahPoinTersisaField.setText("");
        jumlahRpTersisaField.setText("");
        poinDigunakanField.setText("0");
        jumlahRpDigunakanField.setText("0");
        noKartuField.requestFocus();
    }
    @FXML
    private void addPembayaranCash(){
        double jumlahBayar = Double.parseDouble(jumlahPembayaranField.getText().replaceAll(",", ""));
        if(jumlahBayar>Double.parseDouble(sisaPembayaranField.getText().replaceAll(",", "")))
            jumlahBayar = Double.parseDouble(sisaPembayaranField.getText().replaceAll(",", ""));
        
        boolean status = true;
        for(PembayaranPenjualan temp : listPembayaran){
            if(temp.getJenisPembayaran().equals("Cash")){
                status = false;
                temp.setJumlahPembayaran(temp.getJumlahPembayaran()+jumlahBayar);
            }
        }
        if(status){
            PembayaranPenjualan pp = new PembayaranPenjualan();
            pp.setJenisPembayaran("Cash");
            pp.setKeterangan("");
            pp.setJumlahPembayaran(jumlahBayar);
            listPembayaran.add(pp);
        }
        cashButton.setSelected(false);
        selectPembayaran();
        double totalPembayaran = Double.parseDouble(pembayaranField.getText().replaceAll(",", ""));
        double jumlahUang = Double.parseDouble(jumlahPembayaranField.getText().replaceAll(",", ""));
        pembayaranField.setText(rp.format(totalPembayaran+jumlahUang));
        hitungTotal();
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
            memberBayar = task.getValue();
            if(memberBayar==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Member tidak ditemukan");
                resetRewardPoin();
            }else if(memberBayar.getStatus().equals("false")){
                mainApp.showMessage(Modality.NONE, "Warning", "Member sudah dihapus/tidak aktif");
                resetRewardPoin();
            }else{
                int poin = memberBayar.getPoin();
                for(PembayaranPenjualan temp : listPembayaran){
                    if(temp.getJenisPembayaran().equals("Reward Poin")&&
                            temp.getKeterangan().equals(memberBayar.getKodeMember())){
                        poin = poin - (int)(temp.getJumlahPembayaran()/sistem.getNilaiPoin());
                    }
                }
                namaField.setText(memberBayar.getNama());
                alamatField.setText(memberBayar.getAlamat());
                noTelpField.setText(memberBayar.getNoTelp());
                jumlahPoinTersisaField.setText(rp.format(poin));
                jumlahRpTersisaField.setText(rp.format(poin*sistem.getNilaiPoin()));
                if(Double.parseDouble(sisaPembayaranField.getText().replaceAll(",", ""))<
                        Double.parseDouble(jumlahRpTersisaField.getText().replaceAll(",", ""))){
                    poinDigunakanField.setText(rp.format(Double.parseDouble(
                            sisaPembayaranField.getText().replaceAll(",", ""))/sistem.getNilaiPoin()));
                }else{
                    poinDigunakanField.setText(rp.format(Double.parseDouble(
                            jumlahRpTersisaField.getText().replaceAll(",", ""))/sistem.getNilaiPoin()));
                }
                jumlahRpDigunakanField.setText(rp.format(Double.parseDouble(
                        poinDigunakanField.getText().replaceAll(",", ""))*sistem.getNilaiPoin()));
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
    private void addPembayaranRewardPoin(){
        if(memberBayar==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Member belum dipilih");
        }else{
            int poin = memberBayar.getPoin();
            for(PembayaranPenjualan temp : listPembayaran){
                if(temp.getJenisPembayaran().equals("Reward Poin")&&
                        temp.getKeterangan().equals(memberBayar.getKodeMember())){
                    poin = poin - (int)(temp.getJumlahPembayaran()/sistem.getNilaiPoin());
                }
            }
            double jumlahBayar = Double.parseDouble(jumlahRpDigunakanField.getText().replaceAll(",", ""));
            if(poin<Integer.parseInt(poinDigunakanField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Poin yang akan digunakan melebih dari poin yang tersisa");
            }else if(jumlahBayar>Double.parseDouble(sisaPembayaranField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah pembayaran melebihi sisa pembayaran");
            }else{
                boolean status = true;
                for(PembayaranPenjualan temp : listPembayaran){
                    if(temp.getJenisPembayaran().equals("Reward Poin")&&
                            temp.getKeterangan().equals(memberBayar.getKodeMember())){
                        status = false;
                        temp.setJumlahPembayaran(temp.getJumlahPembayaran()+jumlahBayar);
                    }
                }
                if(status){
                    PembayaranPenjualan pp = new PembayaranPenjualan();
                    pp.setJenisPembayaran("Reward Poin");
                    pp.setKeterangan(memberBayar.getKodeMember());
                    pp.setJumlahPembayaran(jumlahBayar);
                    listPembayaran.add(pp);
                }
                double totalPembayaran = Double.parseDouble(pembayaranField.getText().replaceAll(",", ""));
                pembayaranField.setText(rp.format(totalPembayaran+jumlahBayar));
                hitungTotal();
                rewardPoinButton.setSelected(false);
                resetRewardPoin();
                selectPembayaran();
            }
        }
    }
    @FXML
    private void hitungTotal(){
        if(getMember!=null){
            double jumlahCash = 0;
            for(PembayaranPenjualan pp : listPembayaran){
                if(pp.getJenisPembayaran().equals("Cash"))
                    jumlahCash = jumlahCash + pp.getJumlahPembayaran(); 
            }
            getPoinLabel.setText("Member "+getMember.getNama()+" mendapatkan "
                +rp.format(floor(jumlahCash/sistem.getGetPoinPembayaranPenjualan()))+" Poin");
            getPoinLabel.setVisible(true);
        }else{
            getPoinLabel.setVisible(false);
        }
        
        double totalPenjualan = Double.parseDouble(totalPenjualanField.getText().replaceAll(",", ""));
        double pembayaran = Double.parseDouble(pembayaranField.getText().replaceAll(",", ""));
        double sisaPembayaran = totalPenjualan-pembayaran;
        if(sisaPembayaran==0){
            sisaPembayaranLabel.setText("Kembalian");
            sisaPembayaranField.setText(rp.format(sisaPembayaran));
            
            kurangBayarCheckBox.setSelected(false);
            kurangBayarCheckBox.setDisable(true);
            cashButton.setDisable(true);
            rewardPoinButton.setDisable(true);
        }else if(sisaPembayaran<0){
            sisaPembayaranLabel.setText("Kembalian");
            sisaPembayaranField.setText(rp.format(sisaPembayaran*-1));
            
            kurangBayarCheckBox.setSelected(false);
            kurangBayarCheckBox.setDisable(true);
            cashButton.setDisable(true);
            rewardPoinButton.setDisable(true);
        }else{
            if(kurangBayarCheckBox.isSelected())
                sisaPembayaranLabel.setText("Kurang Bayar");
            else
                sisaPembayaranLabel.setText("Sisa Pembayaran");
            sisaPembayaranField.setText(rp.format(sisaPembayaran));
            
            kurangBayarCheckBox.setDisable(false);
            cashButton.setDisable(false);
            rewardPoinButton.setDisable(false);
            lebihSepuluhButton.setVisible(true);
            lebihLimaPuluhButton.setVisible(true);
            lebihSeratusButton.setVisible(true);
            
            uangPasButton.setText("Rp "+rp.format(sisaPembayaran));
            lebihSepuluhButton.setText("Rp "+rp.format(ceil(sisaPembayaran/10000)*10000));
            lebihLimaPuluhButton.setText("Rp "+rp.format(ceil(sisaPembayaran/50000)*50000));
            lebihSeratusButton.setText("Rp "+rp.format(ceil(sisaPembayaran/100000)*100000));
            
            if(uangPasButton.getText().equals(lebihSepuluhButton.getText()))
                lebihSepuluhButton.setVisible(false);
            if(uangPasButton.getText().equals(lebihLimaPuluhButton.getText())||
                    lebihSepuluhButton.getText().equals(lebihLimaPuluhButton.getText()))
                lebihLimaPuluhButton.setVisible(false);
            if(uangPasButton.getText().equals(lebihSeratusButton.getText())||
                    lebihSepuluhButton.getText().equals(lebihSeratusButton.getText())||
                    lebihLimaPuluhButton.getText().equals(lebihSeratusButton.getText()))
                lebihSeratusButton.setVisible(false);
        }
    }
    @FXML
    private void uangPas(){
        jumlahPembayaranField.setText(uangPasButton.getText().replaceAll("Rp ", ""));
    }
    @FXML
    private void lebihSepuluh(){
        jumlahPembayaranField.setText(lebihSepuluhButton.getText().replaceAll("Rp ", ""));
    }
    @FXML
    private void lebihLimaPuluh(){
        jumlahPembayaranField.setText(lebihLimaPuluhButton.getText().replaceAll("Rp ", ""));
    }
    @FXML
    private void lebihSeratus(){
        jumlahPembayaranField.setText(lebihSeratusButton.getText().replaceAll("Rp ", ""));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
