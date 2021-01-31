/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailKategoriBarangController {

    @FXML public TextField kodeKategoriField;
    @FXML public TextField namaKategoriField;
    @FXML public TextField hargaBeliField;
    @FXML public TextField hargaJualField;
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(hargaBeliField, rp);
        hargaJualField.setOnKeyReleased((event) -> {
            try{
                String string = hargaJualField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        hargaJualField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        hargaJualField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    hargaJualField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                hargaJualField.end();
            }catch(Exception e){
                hargaJualField.undo();
            }
            hitungHargaBeli();
        });
        namaKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hargaBeliField.requestFocus();
                hargaBeliField.selectAll();
            }
        });
        hargaBeliField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hargaJualField.requestFocus();
                hargaJualField.selectAll();
            }
        });
        hargaJualField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveButton.fire();
            }
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setKategoriBarang(Kategori k ){
        kodeKategoriField.setText(k.getKodeKategori());
        namaKategoriField.setText(k.getNamaKategori());
        hargaBeliField.setText(rp.format(k.getHargaBeli()));
        hargaJualField.setText(rp.format(k.getHargaJual()));
    }
    private void hitungHargaBeli(){
        double hargaJual = Double.parseDouble(hargaJualField.getText().replaceAll(",", ""));
        hargaBeliField.setText(rp.format(hargaJual*95/100));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
