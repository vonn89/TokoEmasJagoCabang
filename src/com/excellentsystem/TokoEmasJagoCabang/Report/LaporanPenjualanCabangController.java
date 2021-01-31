/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.Report;

import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoCabang.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSql;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class LaporanPenjualanCabangController {

    @SuppressWarnings("rawtypes")
    private JasperPrint jasperPrint;
    @FXML private ImageView imageView;
    @FXML private TextField pageField;
    @FXML private Label totalPageLabel;
    @FXML private Slider zoomLevel;
    private double zoomFactor;
    private double imageHeight;
    private double imageWidth;

    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> cabangCombo;
    @FXML private TextField searchField;
    public void initialize(){
        try(Connection con = KoneksiCabang.getConnection(cabang)){
            Function.setNumberField(pageField, rp);
            tglAwalPicker.setConverter(Function.getTglConverter());
            tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
            tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                    Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
            tglAkhirPicker.setConverter(Function.getTglConverter());
            tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
            tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                    Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));

            ObservableList<String> listCabang = FXCollections.observableArrayList();
            listCabang.add("Semua");
            List<Cabang> allCabang = CabangDAO.getAll(con);
            for(Cabang c : allCabang){
                listCabang.add(c.getKodeCabang());
            }
            cabangCombo.setItems(listCabang);
            cabangCombo.getSelectionModel().select("Semua");
            getBarang();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
        
    }
    @FXML
    private void getBarang(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            String cabangTujuan = "%";
            if(!cabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                cabangTujuan = cabangCombo.getSelectionModel().getSelectedItem();
            List<PenjualanAntarCabangDetail> listPenjualanDetail = PenjualanAntarCabangDetailDAO.getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabang.getKodeCabang(), cabangTujuan, 
                    "%", "false");
            List<PenjualanAntarCabangDetail> listPenjualan = new ArrayList<>();
            for(PenjualanAntarCabangDetail d : listPenjualanDetail){
                d.setPenjualanAntarCabangHead(PenjualanAntarCabangHeadDAO.get(conPusat, d.getNoPenjualan()));
                listPenjualan.add(d);
            }
            List<PenjualanAntarCabangDetail> filterData = filterData(listPenjualan);
            filterData.sort(Comparator.comparing(PenjualanAntarCabangDetail::getNoPenjualan));
            Collections.sort(filterData, (o1, o2) -> {
                int sComp = ((PenjualanAntarCabangDetail) o1).getPenjualanAntarCabangHead().getCabangTujuan().compareTo(
                        ((PenjualanAntarCabangDetail) o2).getPenjualanAntarCabangHead().getCabangTujuan());
                if (sComp != 0) 
                    return sComp;
                return ((PenjualanAntarCabangDetail) o1).getNoPenjualan().compareTo(((PenjualanAntarCabangDetail) o2).getNoPenjualan());
            });
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCabang.jrxml"));
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(filterData);
            Map hash = new HashMap();
            hash.put("kodeCabang", cabang.getKodeCabang());
            hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+" - "+
                    tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
            hash.put("cabangTujuan", cabangCombo.getSelectionModel().getSelectedItem());
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
            zoomFactor = 1.5d;
            zoomLevel.setValue(100d);
            imageView.setX(0);
            imageView.setY(0);
            imageHeight = jasperPrint.getPageHeight();
            imageWidth = jasperPrint.getPageWidth();
            zoomLevel.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                zoomFactor = newValue.doubleValue() / 100 *1.5;
                imageView.setFitHeight(imageHeight * zoomFactor);
                imageView.setFitWidth(imageWidth * zoomFactor);
            });
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
                totalPageLabel.setText(String.valueOf(jasperPrint.getPages().size()));
            }else{
                imageView.setImage(null);
                pageField.setText("0");
                totalPageLabel.setText("0");
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private List<PenjualanAntarCabangDetail> filterData(List<PenjualanAntarCabangDetail> listData) throws Exception{
        List<PenjualanAntarCabangDetail> filterData = new ArrayList<>();
        for (PenjualanAntarCabangDetail p : listData) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(p);
            else{
                if(checkColumn(p.getNoPenjualan())||
                    checkColumn(tglLengkap.format(tglSql.parse(p.getPenjualanAntarCabangHead().getTglPenjualan())))||
                    checkColumn(tglLengkap.format(tglSql.parse(p.getPenjualanAntarCabangHead().getTglTerima())))||
                    checkColumn(p.getPenjualanAntarCabangHead().getCabangTujuan())||
                    checkColumn(p.getPenjualanAntarCabangHead().getSalesTerima())||
                    checkColumn(p.getPenjualanAntarCabangHead().getKodeSales())||
                    checkColumn(rp.format(p.getPenjualanAntarCabangHead().getTotalHarga()))||
                    checkColumn(gr.format(p.getPenjualanAntarCabangHead().getTotalBerat()))||
                    checkColumn(p.getKodeBarcode())||
                    checkColumn(p.getKodeBarcodeBaru())||
                    checkColumn(p.getKodeBarang())||
                    checkColumn(p.getNamaBarang())||
                    checkColumn(p.getKodeJenis())||
                    checkColumn(p.getKodeKategori())||
                    checkColumn(gr.format(p.getBerat()))||
                    checkColumn(gr.format(p.getHarga())))
                    filterData.add(p);
            }
        }
        return filterData;
    }
    @FXML
    private void changePage(){
        try {
            int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""));
            if(1<=pageNumber && pageNumber<=jasperPrint.getPages().size()){
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }else{
                pageField.setText("0");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void prevPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))>1){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))-1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void nextPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))<jasperPrint.getPages().size()){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))+1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void firstPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void lastPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(jasperPrint.getPages().size());
                pageField.setText(rp.format(jasperPrint.getPages().size()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    private void showImage(int pageNumber)throws Exception{
        imageView.setFitHeight(imageHeight * zoomFactor);
        imageView.setFitWidth(imageWidth * zoomFactor);
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber-1, 2);
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        imageView.setImage(SwingFXUtils.toFXImage(image, fxImage));
    }
    @FXML
    private void print() {
        try {
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    
    
}
