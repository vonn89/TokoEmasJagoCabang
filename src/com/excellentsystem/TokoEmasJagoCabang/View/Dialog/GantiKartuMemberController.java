/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
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
public class GantiKartuMemberController {

    @FXML private TextField kodeMemberField;
    @FXML private TextField namaField;
    @FXML public TextField noKartuBaruField;
    @FXML public TextField ulangiNoKartuBaruField;
    @FXML public TextField noRfidBaruField;
    @FXML public TextField ulangiNoRfidBaruField;
    @FXML public TextField kodeSalesField;
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noRfidBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                ulangiNoRfidBaruField.requestFocus();
                ulangiNoRfidBaruField.selectAll();
            }
        });
        ulangiNoRfidBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                noKartuBaruField.requestFocus();
                noKartuBaruField.selectAll();
            }
        });
        noKartuBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                ulangiNoKartuBaruField.requestFocus();
                ulangiNoKartuBaruField.selectAll();
            }
        });
        ulangiNoKartuBaruField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.requestFocus();
                kodeSalesField.selectAll();
            }
        });
        kodeSalesField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeSalesField.setText(Function.ceksales(kodeSalesField.getText()));
                if(!kodeSalesField.getText().equals(""))
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
    }
    public void setMember(Member m){
        kodeMemberField.setText(m.getKodeMember());
        namaField.setText(m.getNama());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}

