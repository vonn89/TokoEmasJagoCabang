/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.View;

import com.excellentsystem.TokoEmasJagoCabang.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.VerifikasiDAO;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.Main;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.timeout;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import com.excellentsystem.TokoEmasJagoCabang.Service.Service;
import com.excellentsystem.TokoEmasJagoCabang.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataUserController  {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> usernameColumn;
    
    @FXML private TreeTableView<Otoritas> otoritasTable;
    @FXML private TreeTableColumn<Otoritas, String> jenisOtoritasColumn;
    @FXML private TreeTableColumn<Otoritas, Boolean> statusOtoritasColumn;
    
    @FXML private TableView<Verifikasi> verifikasiTable;
    @FXML private TableColumn<Verifikasi, String> jenisVerifikasiColumn;
    @FXML private TableColumn<Verifikasi, Boolean> statusVerifikasiColumn;
    
    @FXML private CheckBox checkOtoritas;
    @FXML private CheckBox checkVerifikasi;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private String status = "";
    private final TreeItem<Otoritas> root = new TreeItem<>();
    private final ObservableList<User> allUser = FXCollections.observableArrayList();
    private List<Otoritas> otoritas = new ArrayList<>();
    private final ObservableList<Verifikasi> verifikasi = FXCollections.observableArrayList();
    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
                
        jenisOtoritasColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jenisProperty());
        statusOtoritasColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Otoritas, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().getValue().setStatus(newValue);
                for(TreeItem<Otoritas> child : param.getValue().getChildren()){
                    child.getValue().setStatus(newValue);
                }
                otoritasTable.refresh();
            });
            return booleanProp;
        });
        statusOtoritasColumn.setCellFactory((TreeTableColumn<Otoritas,Boolean> p) -> {
            CheckBoxTreeTableCell<Otoritas,Boolean> cell = new CheckBoxTreeTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        
        jenisVerifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().jenisProperty());
        statusVerifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusVerifikasiColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer param) -> verifikasiTable.getItems().get(param).statusProperty()));
        
        userTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectUser(newValue));
        final ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("New User");
        addNew.setOnAction((ActionEvent event) -> {
            newUser();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getUser();
        });
        menu.getItems().addAll(addNew, refresh);
        userTable.setContextMenu(menu);
        userTable.setRowFactory(table -> {
            TableRow<User> row = new TableRow<User>(){
                @Override
                public void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New User");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Hapus User");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteUser(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getUser();
                        });
                        rowMenu.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        verifikasiTable.setRowFactory(table -> {
            TableRow<Verifikasi> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().isStatus())
                            row.getItem().setStatus(false);
                        else
                            row.getItem().setStatus(true);
                    }
                }
            });
            return row;
        });
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        userTable.setItems(allUser);
        verifikasiTable.setItems(verifikasi);
        getUser();
    }    
    @FXML
    private void checkOtoritas(){
        for(TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()){
            head.getValue().setStatus(checkOtoritas.isSelected());
            for(TreeItem<Otoritas> child : head.getChildren()){
                child.getValue().setStatus(checkOtoritas.isSelected());
            }
        }
        otoritasTable.refresh();
    }
    @FXML
    private void checkVerifikasi(){
        for(Verifikasi v : verifikasi){
            v.setStatus(checkVerifikasi.isSelected());
        }
        verifikasiTable.refresh();
    }
    private void getUser(){
        Task<List<User>> task = new Task<List<User>>() {
            @Override 
            public List<User> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                    List<User> allUser = UserDAO.getAll(conCabang);
                    List<Otoritas> allOtoritas = OtoritasDAO.getAll(conCabang);
                    List<Verifikasi> allVerifikasi = VerifikasiDAO.getAll(conCabang);
                    for(User u : allUser){
                        List<Otoritas> otoritas = new ArrayList<>();
                        for(Otoritas o : allOtoritas){
                            if(u.getKodeUser().equals(o.getKodeUser()))
                                otoritas.add(o);
                        }
                        u.setOtoritas(otoritas);
                        List<Verifikasi> verifikasi = new ArrayList<>();
                        for(Verifikasi v : allVerifikasi){
                            if(u.getKodeUser().equals(v.getKodeUser()))
                                verifikasi.add(v);
                        }
                        u.setVerifikasi(verifikasi);
                    }
                    return allUser;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allUser.clear();
            allUser.addAll(task.getValue());
            reset();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private TreeItem<Otoritas> createTreeItem(String head, List<String> child){
        Otoritas temp = new Otoritas();
        temp.setJenis(head);
        temp.setStatus(false);
        for(Otoritas o : otoritas){
            if(o.getJenis().equals(temp.getJenis()))
                temp.setStatus(o.isStatus());
        }
        TreeItem<Otoritas> parent = new TreeItem<>(temp);
        for(String s : child){
            Otoritas temp2 = new Otoritas();
            temp2.setJenis(s);
            temp2.setStatus(false);
            for(Otoritas o : otoritas){
                if(o.getJenis().equals(temp2.getJenis()))
                    temp2.setStatus(o.isStatus());
            }
            parent.getChildren().addAll(new TreeItem<>(temp2));
        }
        return parent;
    }
    private void setTable(){
        try{
            if(otoritasTable.getRoot()!=null)
                otoritasTable.getRoot().getChildren().clear();
            
            root.getChildren().add(createTreeItem("Dashboard",
                Arrays.asList(
                    "Dashboard - This Month", 
                    "Dashboard - Last 6 Months", 
                    "Dashboard - Last 12 Months",
                    "Dashboard - This Year"
                )
            ));
            root.getChildren().add(createTreeItem("Member", 
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Barang",
                Arrays.asList(               
                    "Data Barang",
                    "Stok Barang",     
                    "Terima Barang",        
                    "Ambil Barang",
                    "Stok Opname Barang"
                )
            ));
            root.getChildren().add(createTreeItem("Penjualan",
                Arrays.asList(               
                    "Penjualan Umum",
                    "Penjualan Cabang",     
                    "Pemesanan"
                )
            ));
            root.getChildren().add(createTreeItem("Pembelian",
                Arrays.asList(               
                    "Pembelian Umum",
                    "Pembelian Cabang"
                )
            ));
            root.getChildren().add(createTreeItem("Hutang",
                Arrays.asList(               
                    "Terima Hutang",
                    "Pelunasan Hutang"
                )
            ));
            root.getChildren().add(createTreeItem("Servis",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Keuangan",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pengaturan",
                Arrays.asList(               
                    "Ganti Cabang",    
                    "Ganti Tanggal",
                    "Data Kategori Barang",
                    "Data Kategori Transaksi",     
                    "Data User",        
                    "Data Sales",
                    "Data Cabang",
                    "Pengaturan Lain-lain",
                    "Tutup Toko"
                )
            ));
            
            otoritasTable.setRoot(root);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML 
    private void reset(){
        otoritas.clear();
        verifikasi.clear();
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status="";
        setTable();
    }
    private void selectUser(User u){
        if(u!=null){
            status="update";
            otoritas.clear();
            otoritas.addAll(u.getOtoritas());
            verifikasi.clear();
            verifikasi.addAll(u.getVerifikasi());
            usernameField.setText(u.getKodeUser());
            passwordField.setText(u.getPassword());
            usernameField.setDisable(true);
            passwordField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            setTable();
        }
    }    
    private void newUser(){
        status = "new";
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        setTable();
        
        List<String> allVerifikasi = new ArrayList<>();
        allVerifikasi.add("Ubah Kategori Barang");//ubah kategori barang dari dashboard
        allVerifikasi.add("Ubah Harga Rosok");//ubah harga rosok dari dashboard
        
        allVerifikasi.add("Batal Pendaftaran");
        allVerifikasi.add("Batal Ganti Kartu");
        allVerifikasi.add("Hapus Member");

        allVerifikasi.add("Batal Terima Barang");
        allVerifikasi.add("Ambil Barang");
        allVerifikasi.add("Batal Ambil Barang");
        
        allVerifikasi.add("Penjualan");//input barang barcode dibawah limit harga / jenis barang yang membutuhkan verifikasi
        allVerifikasi.add("Batal Penjualan");
        allVerifikasi.add("Batal Penjualan Beda Tanggal");
        allVerifikasi.add("Penjualan Cabang");//input barang barcode dibawah limit harga / jenis barang yang membutuhkan verifikasi
        allVerifikasi.add("Batal Penjualan Cabang");
        allVerifikasi.add("Batal Pemesanan");
        
        allVerifikasi.add("Pembelian");
        allVerifikasi.add("Batal Pembelian");
        allVerifikasi.add("Batal Pembelian Cabang");
        
        allVerifikasi.add("Terima Hutang");
        allVerifikasi.add("Pelunasan Hutang");
        allVerifikasi.add("Batal Pelunasan Hutang");
        allVerifikasi.add("Batal Terima Hutang");

        allVerifikasi.add("Batal Terima Servis");
        allVerifikasi.add("Batal Ambil Servis");
        
        allVerifikasi.add("Batal Transaksi");
        allVerifikasi.add("Terima Uang Bank");
        allVerifikasi.add("Batal Terima Uang Bank");
        allVerifikasi.add("Setor Uang Kas");
        allVerifikasi.add("Batal Setor Uang Kas");
        
        allVerifikasi.add("Tutup Toko");
        
        
        List<Verifikasi> tempVerifikasi = new ArrayList<>();
        for(String jns : allVerifikasi){
            Verifikasi temp = new Verifikasi();
            temp.setJenis(jns);
            temp.setStatus(checkVerifikasi.isSelected());
            tempVerifikasi.add(temp);
        }
        verifikasi.clear();
        verifikasi.addAll(tempVerifikasi);
    }    
    @FXML
    private void saveUser() {
        if(usernameField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "User belum dipilih");
        }else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        User user = new User();
                        user.setKodeUser(usernameField.getText());
                        user.setPassword(passwordField.getText());
                        List<Otoritas> listOtoritas = new ArrayList<>();
                        for(TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()){
                            Otoritas o = head.getValue();
                            o.setKodeUser(usernameField.getText());
                            listOtoritas.add(o);
                            for(TreeItem<Otoritas> child : head.getChildren()){
                                Otoritas o2 = child.getValue();
                                o2.setKodeUser(usernameField.getText());
                                listOtoritas.add(o2);
                            }
                        }
                        user.setOtoritas(listOtoritas);
                        for(Verifikasi v : verifikasi){
                            v.setKodeUser(user.getKodeUser());
                        }
                        user.setVerifikasi(verifikasi);
                        if(status.equals("update"))
                            return Service.saveUpdateUser(conCabang, user);
                        else if(status.equals("new"))
                            return Service.saveNewUser(conCabang, user);
                        else
                            return "false";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getUser();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", message);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }    
    private void deleteUser(User user){
        MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                "Hapus user "+user.getKodeUser()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        return Service.deleteUser(conCabang, user);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getUser();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", message);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    
}
