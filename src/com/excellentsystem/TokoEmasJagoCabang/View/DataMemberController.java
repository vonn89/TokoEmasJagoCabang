/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.DetailMemberController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.GantiKartuMemberController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.LogMemberController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiController;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.VerifikasiNoKartuController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataMemberController {

    @FXML private TableView<Member> memberTable;
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
    @FXML private Label totalMemberField;
    private ObservableList<Member> allMember = FXCollections.observableArrayList();
    private ObservableList<Member> filterData = FXCollections.observableArrayList();
    private Main mainApp;
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
        MenuItem daftar = new MenuItem("Pendaftaran Member");
        daftar.setOnAction((ActionEvent event) -> {
            pendaftaranMember();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getMember();
        });
        menu.getItems().add(daftar);
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
                        MenuItem daftar = new MenuItem("Pendaftaran Member");
                        daftar.setOnAction((ActionEvent event) -> {
                            pendaftaranMember();
                        });
                        MenuItem edit = new MenuItem("Edit Member");
                        edit.setOnAction((ActionEvent event) -> {
                            editMember(item);
                        });
                        MenuItem ganti = new MenuItem("Ganti Kartu Member");
                        ganti.setOnAction((ActionEvent event) -> {
                            gantiKartuMember(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Member");
                        hapus.setOnAction((ActionEvent event) -> {
                            hapusMember(item);
                        });
                        MenuItem history = new MenuItem("Lihat History Member");
                        history.setOnAction((ActionEvent event) -> {
                            historyMember(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getMember();
                        });
                        rowMenu.getItems().add(daftar);
                        rowMenu.getItems().add(edit);
                        rowMenu.getItems().add(ganti);
                        rowMenu.getItems().add(hapus);
                        rowMenu.getItems().add(history);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        historyMember(row.getItem());
                }
            });
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
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getMember();
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
                        checkColumn(temp.getNoKartu())||
                        checkColumn(temp.getNoRfid())||
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
            totalMemberField.setText("Total Member : "+rp.format(filterData.size()));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void pendaftaranMember(){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailMember.fxml");
            DetailMemberController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.tglDaftarField.setText(tglLengkap.format(tglSql.parse(Function.getSystemDate())));
            controller.saveButton.setOnAction((event) -> {
                if(controller.noRfidField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "No RFID masih kosong");
                else if(controller.noKartuField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "No Kartu masih kosong");
                else if(controller.namaField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Nama masih kosong");
                else if(controller.salesDaftarField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode Sales masih kosong");
                else if(Function.ceksales(controller.salesDaftarField.getText()).equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode Sales masih salah");
                else{
                    Stage child = new Stage();
                    FXMLLoader verifikasiNoKartuLoader = mainApp.showDialog(stage ,child, "View/Dialog/VerifikasiNoKartu.fxml");
                    VerifikasiNoKartuController verifikasiNoKartuController = verifikasiNoKartuLoader.getController();
                    verifikasiNoKartuController.setMainApp(mainApp, stage, child);
                    verifikasiNoKartuController.biayaPendaftaranMemberField.setText(rp.format(sistem.getBiayaKartuMember()));
                    verifikasiNoKartuController.saveButton.setOnAction((ev) -> {
                        if(verifikasiNoKartuController.verifikasiNoRfidField.getText().equals(""))
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi no RFID masih kosong");
                        else if(verifikasiNoKartuController.verifikasiNoKartuField.getText().equals(""))
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi no kartu masih kosong");
                        else if(!controller.noRfidField.getText().equals(verifikasiNoKartuController.verifikasiNoRfidField.getText()))
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi no RFID masih salah");
                        else if(!controller.noKartuField.getText().equals(verifikasiNoKartuController.verifikasiNoKartuField.getText()))
                            mainApp.showMessage(Modality.NONE, "Warning", "Verifikasi no kartu masih salah");
                        else{
                            Task<String> task = new Task<String>() {
                                @Override 
                                public String call() throws Exception{
                                    try(Connection conPusat = KoneksiPusat.getConnection()){
                                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                            Member m = new Member();
                                            m.setNoRfid(controller.noRfidField.getText());
                                            m.setNoKartu(controller.noKartuField.getText());
                                            m.setNama(controller.namaField.getText());
                                            m.setAlamat(controller.alamatField.getText());
                                            m.setKelurahan(controller.kelurahanField.getText());
                                            m.setKecamatan(controller.kecamatanField.getText());
                                            m.setNoTelp(controller.noTelpField.getText());
                                            m.setPekerjaan(controller.pekerjaanField.getText());
                                            m.setIdentitas(controller.identitasCombo.getSelectionModel().getSelectedItem());
                                            m.setNoIdentitas(controller.noIdentitasField.getText());
                                            m.setPoin(0);
                                            m.setStatus("true");
                                            m.setTglDaftar(Function.getSystemDate());
                                            m.setSalesDaftar(controller.salesDaftarField.getText());
                                            return Service.savePendaftaranMember(conPusat, conCabang, m);
                                        }
                                    }
                                }
                            };
                            task.setOnRunning((e) -> {
                                mainApp.showLoadingScreen();
                            });
                            task.setOnSucceeded((e) -> {
                                mainApp.closeLoading();
                                getMember();
                                String status = task.getValue();
                                if(status.equals("true")){
                                    mainApp.closeDialog(mainApp.MainStage, stage);
                                    mainApp.showMessage(Modality.NONE, "Success", "Member berhasil disimpan");
                                }else
                                    mainApp.showMessage(Modality.NONE, "Failed", status);
                            });
                            task.setOnFailed((e) -> {
                                mainApp.closeLoading();
                                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                            });
                            new Thread(task).start();
                        }
                    });
                } 
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void editMember(Member m){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailMember.fxml");
        DetailMemberController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setMember(m);
        controller.saveButton.setOnAction((event) -> {
            if(controller.namaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            m.setNama(controller.namaField.getText());
                            m.setAlamat(controller.alamatField.getText());
                            m.setKelurahan(controller.kelurahanField.getText());
                            m.setKecamatan(controller.kecamatanField.getText());
                            m.setNoTelp(controller.noTelpField.getText());
                            m.setPekerjaan(controller.pekerjaanField.getText());
                            m.setIdentitas(controller.identitasCombo.getSelectionModel().getSelectedItem());
                            m.setNoIdentitas(controller.noIdentitasField.getText());
                            return Service.saveUpdateMember(conPusat, m);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getMember();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Member berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            } 
        });
    }
    private void gantiKartuMember(Member m){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/GantiKartuMember.fxml");
        GantiKartuMemberController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setMember(m);
        controller.saveButton.setOnAction((event) -> {
            if(controller.noRfidBaruField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "No RFID baru masih kosong");
            else if(controller.ulangiNoRfidBaruField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Ulangi no RFID baru masih kosong");
            else if(controller.noKartuBaruField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "No kartu baru masih kosong");
            else if(controller.ulangiNoKartuBaruField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Ulangi no kartu baru masih kosong");
            else if(!controller.noRfidBaruField.getText().equals(controller.ulangiNoRfidBaruField.getText()))
                mainApp.showMessage(Modality.NONE, "Warning", "No RFID baru tidak sama");
            else if(!controller.noKartuBaruField.getText().equals(controller.ulangiNoKartuBaruField.getText()))
                mainApp.showMessage(Modality.NONE, "Warning", "No kartu baru tidak sama");
            else{
                MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Penggantian kartu member "+m.getNama()+" akan dikenakan biaya "
                                + "sebesar Rp "+rp.format(sistem.getBiayaKartuMember()));
                x.OK.setOnAction((ActionEvent ex) -> {
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            try(Connection conPusat = KoneksiPusat.getConnection()){
                                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                    return Service.saveGantiKartuMember(conPusat, conCabang, m, 
                                            controller.noRfidBaruField.getText(), controller.noKartuBaruField.getText(), controller.kodeSalesField.getText());
                                }
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((e) -> {
                        mainApp.closeLoading();
                        getMember();
                        String status = task.getValue();
                        if(status.equals("true")){
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Member berhasil disimpan");
                        }else
                            mainApp.showMessage(Modality.NONE, "Failed", status);
                    });
                    task.setOnFailed((e) -> {
                        mainApp.closeLoading();
                        mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    });
                    new Thread(task).start();
                });
            }
        });
    }
    private void hapusMember(Member m){
        if(m.getPoin()>0){
            mainApp.showMessage(Modality.NONE, "Warning", "Member tidak dapat dihapus karena masih memiliki poin");
        }else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/Verifikasi.fxml");
            VerifikasiController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    if(Function.verifikasi(controller.usernameField.getText(), 
                            controller.passwordField.getText(), "Hapus Member")){
                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try(Connection conPusat = KoneksiPusat.getConnection()){
                                    return Service.saveDeleteMember(conPusat, m, controller.usernameField.getText());
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((e) -> {
                            mainApp.closeLoading();
                            getMember();
                            String status = task.getValue();
                            if(status.equals("true")){
                                mainApp.showMessage(Modality.NONE, "Success", "Member berhasil dihapus");
                            }else
                                mainApp.showMessage(Modality.NONE, "Failed", status);
                        });
                        task.setOnFailed((e) -> {
                            mainApp.closeLoading();
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
    private void historyMember(Member m){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/LogMember.fxml");
        LogMemberController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setMember(m);
    }
}
