package com.excellentsystem.TokoEmasJagoCabang.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PegawaiDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.VerifikasiDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiLokal;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import static com.excellentsystem.TokoEmasJagoCabang.Main.version;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import com.excellentsystem.TokoEmasJagoCabang.Patch;
import com.excellentsystem.TokoEmasJagoCabang.Patch2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController {

    @FXML private GridPane gridPane;
    @FXML private Label updateLabel;
    @FXML private ProgressBar progressBar;
    
    @FXML private Label warningLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label versionLabel;
    
    
    private String ipServer;
    private String kodeCabang;
    private Cabang cabang;
    
    private Main mainApp;
    private int attempt = 0;
    public void initialize() {
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
        passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                handleLoginButton();
        });
    }    
    public void setMainApp(Main mainApp){
        try{
            this.mainApp = mainApp;
            BufferedReader in = new BufferedReader(new FileReader("koneksi"));
            ipServer = in.readLine();
            kodeCabang = in.readLine();
            try(Connection conLokal = KoneksiLokal.getConnection(ipServer, kodeCabang)){
                versionLabel.setText("Ver. "+version);
                warningLabel.setText("");
                cabang = CabangDAO.get(conLokal, kodeCabang);
                usernameField.requestFocus();
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void handleLoginButton(){
        if("".equals(usernameField.getText())){
            warningLabel.setText("Username masih kosong");
        }else if(passwordField.getText().equals("")){
            warningLabel.setText("Password masih kosong");
        }else if(attempt>=3){
            System.exit(0);
        }else{
            if(usernameField.getText().equals("patch")&&passwordField.getText().equals("jagopusat")){
//                patchNoUrutKeuangan();
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        String warning = "Error";
//                        Cabang c = null;
//                        for(Cabang temp : listCabang){
//                            if(cabangCombo.getSelectionModel().getSelectedItem().equals(temp.getKodeCabang()))
//                                c = temp;
//                        }
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            User user = UserDAO.get(conCabang, usernameField.getText());
                            if(user==null){
                                warning = "Username tidak ditemukan";
                                attempt = attempt +1;
                            }else if(!user.getPassword().equals(passwordField.getText())){
                                warning = "Password masih salah";
                                attempt = attempt +1;
                            }else{
                                warning = "true";
                                sistem = SistemDAO.get(conCabang);
                                Main.cabang = cabang;
                                user.setOtoritas(OtoritasDAO.getAllByUser(conCabang, user.getKodeUser()));
                                Main.user = user;
                                Main.allUser = UserDAO.getAll(conCabang);
                                List<Otoritas> listOtoritas = OtoritasDAO.getAll(conCabang);
                                List<Verifikasi> listVerifikasi = VerifikasiDAO.getAll(conCabang);
                                for(User u : Main.allUser){
                                    List<Otoritas> otoritas = new ArrayList<>();
                                    for(Otoritas o : listOtoritas){
                                        if(o.getKodeUser().equals(u.getKodeUser()))
                                            otoritas.add(o);
                                    }
                                    u.setOtoritas(otoritas);
                                    List<Verifikasi> verifikasi = new ArrayList<>();
                                    for(Verifikasi v : listVerifikasi){
                                        if(v.getKodeUser().equals(u.getKodeUser()))
                                            verifikasi.add(v);
                                    }
                                    u.setVerifikasi(verifikasi);
                                }
                                Main.allPegawai = PegawaiDAO.getAll(conCabang);
                            }
                        }
                        return warning;
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    if(task.getValue().equals("true")){
                        if(!version.equals(sistem.getVersion()))
                            checkUpdate();
                        else
                            mainApp.showMainScene();
                    }else{
                        warningLabel.setText(task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    task.getException().printStackTrace();;
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        }
    }
    private void checkUpdate(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(1000);
                return Function.downloadUpdateGoogleStorage("Toko Emas Jago Cabang.exe");
            }
        };
        task.setOnRunning((e) -> {
            gridPane.setVisible(true);
                mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
            gridPane.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(task.getValue());
            alert.showAndWait();
            System.exit(0);
        });
        task.setOnFailed((e) -> {
                mainApp.closeLoading();
            gridPane.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Update gagal silakan coba kembali \n"
                        + task.getException().toString());
            alert.showAndWait();
            System.exit(0);
        });
        new Thread(task).start();
    }
}
