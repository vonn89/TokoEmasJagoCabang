/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class WarningController  {

    @FXML private Label title;
    @FXML private Label content;
    private Main mainApp;
    public void setMainApp(Main mainApp, String title,String content){
        this.mainApp = mainApp;
        this.title.setText(title);
        this.content.setText(content);
    }
    @FXML private void closeWarning(){
        mainApp.closeWarning();
    }
}
