/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View.Dialog;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class CariMemberController  {

    @FXML public TableView<Member> memberTable;
    @FXML private TableColumn<Member, String> kodeMemberColumn;
    @FXML private TableColumn<Member, String> noRfidColumn;
    @FXML private TableColumn<Member, String> noKartuColumn;
    @FXML private TableColumn<Member, String> namaColumn;
    @FXML private TableColumn<Member, String> alamatColumn;
    @FXML private TableColumn<Member, String> kelurahanColumn;
    @FXML private TableColumn<Member, String> kecamatanColumn;
    @FXML private TableColumn<Member, String> noTelpColumn;
    @FXML private TableColumn<Member, String> pekerjaanColumn;
    @FXML private TableColumn<Member, String> identitasColumn;
    @FXML private TableColumn<Member, String> noIdentitasColumn;
    @FXML private TableColumn<Member, Number> poinColumn;
    
    @FXML private TextField searchField;
    private ObservableList<Member> allMember = FXCollections.observableArrayList();
    private ObservableList<Member> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeMemberColumn.setCellValueFactory(cellData -> cellData.getValue().kodeMemberProperty());
        noRfidColumn.setCellValueFactory(cellData -> cellData.getValue().noRfidProperty());
        noKartuColumn.setCellValueFactory(cellData -> cellData.getValue().noKartuProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        kelurahanColumn.setCellValueFactory(cellData -> cellData.getValue().kelurahanProperty());
        kecamatanColumn.setCellValueFactory(cellData -> cellData.getValue().kecamatanProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        pekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().pekerjaanProperty());
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        poinColumn.setCellValueFactory(cellData -> cellData.getValue().poinProperty());
        poinColumn.setCellFactory(col -> getTableCell(rp));
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getMember();
        });
        menu.getItems().addAll(refresh);
        memberTable.setContextMenu(menu);
        memberTable.setRowFactory(table -> {
            TableRow<Member> row = new TableRow<Member>(){
                @Override
                public void updateItem(Member item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getMember();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allMember.addListener((ListChangeListener.Change<? extends Member> change) -> {
            searchMember();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchMember();
        });
        filterData.addAll(allMember);
        memberTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        getMember();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    private void getMember(){
        Task<List<Member>> task = new Task<List<Member>>() {
            @Override 
            public List<Member> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return MemberDAO.getAllByStatus(conPusat, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allMember.clear();
            allMember.addAll(task.getValue());
            memberTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchMember() {
        try{
            filterData.clear();
            for (Member temp : allMember) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodeMember())||
                        checkColumn(temp.getNoRfid())||
                        checkColumn(temp.getNoKartu())||
                        checkColumn(temp.getNama())||
                        checkColumn(temp.getAlamat())||
                        checkColumn(temp.getKelurahan())||
                        checkColumn(temp.getKecamatan())||
                        checkColumn(temp.getNoTelp())||
                        checkColumn(temp.getPekerjaan())||
                        checkColumn(temp.getIdentitas())||
                        checkColumn(temp.getNoIdentitas())||
                        checkColumn(rp.format(temp.getPoin())))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
