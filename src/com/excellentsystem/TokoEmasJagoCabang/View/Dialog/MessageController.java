/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.Main;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class MessageController {

    @FXML private Label title;
    @FXML private Label content;
    @FXML public Button OK;
    @FXML public Button cancel;
    private Main mainApp;
    public void setMainApp(Main mainApp,String title,String content){
        this.mainApp = mainApp;
        this.title.setText(title);
        this.content.setText(content);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> mainApp.closeMessage() );
        if(title.equals("Error"))
            this.title.setTextFill(Color.RED);
        else if(title.equals("Confirmation")){
            OK.setVisible(true);
            cancel.setVisible(true);
        }else{
            delay.play();
        }
    }
    public void closeConfirmation(){
        mainApp.closeMessage();
    }
    public void closeMessage(){
        if(!title.getText().equals("Confirmation"))
            mainApp.closeMessage();
    }
}
