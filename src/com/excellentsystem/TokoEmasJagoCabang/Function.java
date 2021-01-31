/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import static com.excellentsystem.TokoEmasJagoCabang.Main.allPegawai;
import static com.excellentsystem.TokoEmasJagoCabang.Main.allUser;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglLengkap;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Annotation;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author yunaz
 */
public class Function {
    public static double pembulatan(double angka){
        return (double) Math.round(angka*100)/100;
    }
    public static Date getInternetDate()throws Exception{
        tglLengkap.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        NTPUDPClient timeClient = new NTPUDPClient();
        TimeInfo timeInfo = timeClient.getTime(InetAddress.getByName("time-a.nist.gov"));
        Date serverDate = new Date(timeInfo.getReturnTime());
        System.out.println(serverDate);
        System.out.println(tglLengkap.format(serverDate));
        return serverDate;
    }
    public static String getSystemDate(){
        return sistem.getTglSystem()+" "+new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    public static Comparator<String> sortDate(DateFormat df){
        return (String t, String t1) -> {
            try{
                return Long.compare(df.parse(t).getTime(),df.parse(t1).getTime());
            }catch(ParseException e){
                return -1;
            }
        };
    }
    public static String toRGBCode( Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    public static TableCell getTableCell(DecimalFormat df){ 
        TableCell cell = new TableCell<Annotation, Number>(){
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty)
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
        return cell;
    }
    public static TreeTableCell getTreeTableCell(DecimalFormat df){
        return new TreeTableCell<Annotation, Number>() {
            @Override 
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) 
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
    }
    public static DateCell getDateCellDisableBefore(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if (item.isBefore(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellDisableAfter(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if (item.isAfter(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellMulai(DatePicker tglAkhir, LocalDate tglSystem){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if(item.isAfter(tglSystem))
                    this.setDisable(true);
                if(item.isAfter(tglAkhir.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellAkhir(DatePicker tglMulai, LocalDate tglSystem){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 150%);");
                if(item.isAfter(tglSystem))
                    this.setDisable(true);
                if(item.isBefore(tglMulai.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static StringConverter getTglConverter(){
        StringConverter<LocalDate> date = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            @Override 
            public String toString(LocalDate date) {
                if (date != null) 
                    return dateFormatter.format(date);
                else 
                    return "";
            }
            @Override 
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) 
                    return LocalDate.parse(string, dateFormatter);
                else 
                    return null;
            }
        };
        return date;
    }
    public static void setNumberField(TextField field, DecimalFormat df){
        field.setOnKeyReleased((event) -> {
            try{
                String string = field.getText();
                if(string.contains("-"))
                    field.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            field.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            field.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        field.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                field.end();
            }catch(Exception e){
                field.undo();
            }
        });
    }
    public static boolean verifikasi(String username, String password, String jenis){
        Boolean verifikasi = false;
        for(User u : allUser){
            if(u.getKodeUser().equalsIgnoreCase(username)&&u.getPassword().equalsIgnoreCase(password)){
                for(Verifikasi v : u.getVerifikasi()){
                    if(v.getJenis().equalsIgnoreCase(jenis))
                        verifikasi = v.isStatus(); 
                }
            }
        }
        return verifikasi;
    }
    public static String ceksales(String kodeSales){
        boolean status = false;
        for(Pegawai p : allPegawai){
            if(p.getKodePegawai().toUpperCase().equals(kodeSales.toUpperCase()))
                status = true;
        }
        if(status){
            return kodeSales.toUpperCase();
        }else{
            return "";
        }
    }
    public static void shutdown() throws RuntimeException, IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");
        
        if("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
            shutdownCommand = "shutdown -h now";
        }else if("Windows".equals(operatingSystem)) {
            shutdownCommand = "shutdown.exe -s -t 0";
        }else if(operatingSystem.matches(".*Windows.*")) {
            shutdownCommand = "shutdown.exe -s -t 0";
        }else{
            throw new RuntimeException("Unsupported operating system.");
        }
        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }
    public static String downloadUpdate(String ftpServer, String user, String password, String filename)throws Exception{
        FTPClient client = new FTPClient();
        String status;
        client.connect(ftpServer, 21);
        boolean login = client.login(user, password);
        if (login) {
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE); 
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            
            System.out.println("create backup");
            Path sourceFile = Paths.get(filename);
            Path targetFile = Paths.get(filename+" backup");
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("start download");
            FileOutputStream fos = new FileOutputStream(filename);
            boolean file = client.retrieveFile("/" + filename, fos);
            if(file){
                status = "Update Success - please restart application";
            }else{
                System.out.println("rollback file");
                File fileasli = new File(filename);
                File filebackup = new File(filename+" backup");
                if (!fileasli.exists()) {
                    fileasli.createNewFile();
                }
                FileChannel sourceChannel = new FileInputStream(filebackup).getChannel();
                FileChannel destChannel = new FileOutputStream(fileasli).getChannel();
                sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
                if (sourceChannel != null) 
                    sourceChannel.close();
                if (destChannel != null) 
                    destChannel.close();
                    
                status = "Update Failed - please try again";
            }
            
            System.out.println("delete backup");
            Files.deleteIfExists(Paths.get(filename+" backup")); 
            
            client.logout();
            if(fos!= null) 
                fos.close();
        }else{
            status = "Update Failed - couldn't login to FTP server";
        }
        client.disconnect();
        return status;
    }
    public static String downloadUpdateGoogleStorage(String filename)throws Exception{
        String status = "";
        System.out.println("create backup");
        Path sourceFile = Paths.get(filename);
        Path targetFile = Paths.get(filename+" backup");
        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("start download");
        try{
            StorageOptions storageOptions = StorageOptions.newBuilder().
                    setProjectId("excellentSystem").
                    setCredentials(GoogleCredentials.fromStream(Main.class.getResourceAsStream("Resource/credentials.json"))).build();
            Storage storage = storageOptions.getService();
            
            
            Blob blob = storage.get(BlobId.of("jago", filename));
            blob.downloadTo(Paths.get(filename));
            status = "Update Success - please restart application";
        }catch(Exception e){
            System.out.println("rollback file");
            File fileasli = new File(filename);
            File filebackup = new File(filename+" backup");
            if (!fileasli.exists()) {
                fileasli.createNewFile();
            }
            FileChannel sourceChannel = new FileInputStream(filebackup).getChannel();
            FileChannel destChannel = new FileOutputStream(fileasli).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
            if (sourceChannel != null) 
                sourceChannel.close();
            if (destChannel != null) 
                destChannel.close();

            status = "Update Failed - please try again";
        }
        System.out.println("delete backup");
        Files.deleteIfExists(Paths.get(filename+" backup")); 
//            
        return status;
    }
    public static void createRow(Workbook workbook, Sheet sheet,int r,int col, String style){
        Font f = workbook.createFont();
        f.setBold(true);
        Font f2 = workbook.createFont();
        f2.setBold(true);
        f2.setColor(HSSFColor.WHITE.index);
        
        CellStyle boldFont = workbook.createCellStyle();
        boldFont.setFont(f);

        CellStyle subHeader = workbook.createCellStyle();
        subHeader.setFont(f);
        subHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        subHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);

        CellStyle header = workbook.createCellStyle();
        header.setFont(f2);
        header.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        header.setFillPattern(CellStyle.SOLID_FOREGROUND);

        sheet.createRow(r);
        for(int i=0 ; i<col ; i++){ 
            sheet.getRow(r).createCell(i);
            if(style.equals("Bold"))
                sheet.getRow(r).getCell(i).setCellStyle(boldFont);
            else if(style.equals("SubHeader"))
                sheet.getRow(r).getCell(i).setCellStyle(subHeader);
            else if(style.equals("Header"))
                sheet.getRow(r).getCell(i).setCellStyle(header);
            else if(style.equals("Detail"))
                sheet.getRow(r).getCell(i).setCellStyle(null);
        }
    }
}
