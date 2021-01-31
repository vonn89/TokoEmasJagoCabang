/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.LogHargaDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.shutdown;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.LogHarga;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class TutupTokoController {

    @FXML private Label tglSystemLabel;
    @FXML private CheckBox autoBackupCheckbox;
    @FXML private CheckBox autoShutDownCheckbox;
    @FXML private ProgressBar progressBar;
    @FXML private Button tutupTokoButton;
    @FXML private Button closeButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        progressBar.setProgress(0);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            tglSystemLabel.setText(tglNormal.format(tglBarang.parse(sistem.getTglSystem())));
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private File backupfile;
    @FXML
    private void tutupToko(){
        if(Main.sistem.getTglSystem().equals(tglBarang.format(new Date())))
            mainApp.showMessage(Modality.NONE, "Warning", "tanggal sistem sudah sama dengan tanggal komputer");
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child , "View/Dialog/Verifikasi.fxml");
        VerifikasiController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child );
        controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                if(Function.verifikasi(controller.usernameField.getText(), 
                        controller.passwordField.getText(), "Tutup Toko")){
                    mainApp.closeDialog(stage, child);
                    
                    progressBar.setProgress(0);
                    autoBackupCheckbox.setDisable(true);
                    autoShutDownCheckbox.setDisable(true);
                    tutupTokoButton.setDisable(true);
                    closeButton.setDisable(true);
                    
                    if(autoBackupCheckbox.isSelected()){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Select location to backup");
                        fileChooser.setInitialFileName("Backup database "+cabang.getKodeCabang()+" - "+sistem.getTglSystem());
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("zip file", "*.zip"));
                        backupfile = fileChooser.showSaveDialog(stage);
                    }
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = KoneksiCabang.getConnection(cabang)){
                                try{
                                    String status = "true";
                                    con.setAutoCommit(false);

                                    progressBar.setProgress(0.1);
                                    LocalDate besok = LocalDate.parse(sistem.getTglSystem(), 
                                            DateTimeFormatter.ISO_DATE).plusDays(1);

                                    progressBar.setProgress(0.2);
                                    StokBarangDAO.insertStokAwal(con, sistem.getTglSystem(), besok.toString());

                                    progressBar.setProgress(0.4);
                                    StokHutangDAO.insertStokAwal(con, sistem.getTglSystem(), besok.toString());

                                    progressBar.setProgress(0.5);
                                    HutangHeadDAO.hitungBungaKomp(con, besok.toString());

                                    progressBar.setProgress(0.6);
                                    List<Kategori> listKategori = KategoriDAO.getAll(con);
                                    for(Kategori k : listKategori){
                                        LogHarga l = new LogHarga();
                                        l.setTanggal(besok.toString());
                                        l.setKodeKategori(k.getKodeKategori());
                                        l.setPersentaseEmas(k.getPersentaseEmas());
                                        l.setHargaBeli(k.getHargaBeli());
                                        l.setHargaJual(k.getHargaJual());
                                        LogHargaDAO.insert(con, l);
                                    }

                                    progressBar.setProgress(0.7);
                                    sistem.setTglSystem(besok.toString());
                                    SistemDAO.update(con, sistem);

                                    progressBar.setProgress(0.8);

                                    if(status.equals("true"))
                                        con.commit();
                                    else
                                        con.rollback();
                                    con.setAutoCommit(true);
                                    return status;
                                }catch(Exception e){
                                    e.printStackTrace();
                                    try{
                                        con.rollback();
                                        con.setAutoCommit(true);
                                        return e.toString();
                                    }catch(SQLException ex){
                                        return ex.toString();
                                    }
                                }
                            }
                        }
                    };
                    task.setOnSucceeded((e) -> {
                        try{
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.showMessage(Modality.NONE, "Success", "Tutup toko berhasil");
                                progressBar.setProgress(0.9);
                                if(autoBackupCheckbox.isSelected()){
                                    File file = new File("temp.sql");
                                    String executeCmd = "mysqldump --host "+cabang.getIpServer()+" -P 3306 "
                                            + " -u jago_admin -pjagopusat tokoemasjago_"+cabang.getKodeCabang()+" -r \"" + file.getPath()+"\"";
                                    System.out.println(executeCmd);
                                    Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                                    int processComplete = runtimeProcess.waitFor();
                                    if (processComplete == 0) {
                                        FileOutputStream fos = new FileOutputStream(backupfile.getPath());
                                        ZipOutputStream zipOut = new ZipOutputStream(fos);
                                        FileInputStream fis = new FileInputStream(file);
                                        ZipEntry zipEntry = new ZipEntry(file.getName());
                                        zipOut.putNextEntry(zipEntry);
                                        final byte[] bytes = new byte[1024];
                                        int length;
                                        while((length = fis.read(bytes)) >= 0) {
                                            zipOut.write(bytes, 0, length);
                                        }
                                        zipOut.close();
                                        fis.close();
                                        fos.close();
                                        file.delete();
                                        mainApp.showMessage(Modality.NONE, "Success", "Backup database berhasil");
                                    } else {
                                        mainApp.showMessage(Modality.NONE, "Success", "Backup database gagal");
                                    }
                                }
                                progressBar.setProgress(1);
                                if(autoShutDownCheckbox.isSelected()){
                                    shutdown();
                                }
                                mainApp.closeDialog(owner, stage);
                                mainApp.checkTglSystem();
                            }else{
                                progressBar.progressProperty().unbind();
                                progressBar.setProgress(0);
                                autoBackupCheckbox.setDisable(false);
                                autoShutDownCheckbox.setDisable(false);
                                tutupTokoButton.setDisable(false);
                                closeButton.setDisable(false);
                                mainApp.showMessage(Modality.NONE, "Failed", status);
                            }
                        }catch(Exception ex){
                            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                        }
                    });
                    task.setOnFailed((e) -> {
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
    
}
