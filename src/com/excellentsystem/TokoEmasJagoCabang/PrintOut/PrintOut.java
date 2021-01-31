/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.PrintOut;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.gr;
import static com.excellentsystem.TokoEmasJagoCabang.Main.printerBarcode;
import static com.excellentsystem.TokoEmasJagoCabang.Main.printerRR;
import static com.excellentsystem.TokoEmasJagoCabang.Main.rp;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SetoranCabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import simple.escp.SimpleEscp;
import simple.escp.Template;
import simple.escp.data.DataSources;
import simple.escp.json.JsonTemplate;

/**
 *
 * @author excellent
 */
public class PrintOut {
    public String angka(double satuan){ 
          String[] huruf ={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"}; 
          String hasil=""; 
          if(satuan<12) 
           hasil=hasil+huruf[(int)satuan]; 
          else if(satuan<20) 
           hasil=hasil+angka(satuan-10)+" Belas"; 
          else if(satuan<100) 
           hasil=hasil+angka(satuan/10)+" Puluh "+angka(satuan%10); 
          else if(satuan<200) 
           hasil=hasil+"Seratus "+angka(satuan-100); 
          else if(satuan<1000) 
           hasil=hasil+angka(satuan/100)+" Ratus "+angka(satuan%100); 
          else if(satuan<2000) 
           hasil=hasil+"Seribu "+angka(satuan-1000); 
          else if(satuan<1000000) 
           hasil=hasil+angka(satuan/1000)+" Ribu "+angka(satuan%1000); 
          else if(satuan<1000000000) 
           hasil=hasil+angka(satuan/1000000)+" Juta "+angka(satuan%1000000); 
          else if(satuan<1000000000000.0) 
           hasil=hasil+angka(satuan/1000000000)+" Milyar "+angka(satuan%1000000000); 
          else if(satuan>=100000000000.0) 
           hasil="Angka terlalu besar, harus kurang dari 1 Triliun!"; 
          return hasil; 
    }
    public void printBarcode(List<Barang> listBarang)throws Exception{
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerBarcode))
                selectedService = i;
        }
        DecimalFormat gr = new DecimalFormat("###,##0.00");
        for(int i=0; i<listBarang.size();i++){
            Barang b = listBarang.get(i);
            if(i==0||i%2==0){
                String commands = "<ESC>A<ESC>A3H0130V0010";
                commands=commands+"<ESC>H0000<ESC>V0007<ESC>M"+b.getKodeBarcode()+
                    "<ESC>H0000<ESC>V0030<ESC>BG01050"+b.getKodeBarcode()+
                    "<ESC>H0000<ESC>V0105<ESC>S"+b.getNamaBarang()+
                    "<ESC>H0020<ESC>V0130<ESC>L0102<ESC>M"+gr.format(b.getBerat())+"<ESC>L0102<ESC>S gr" +
                    "<ESC>H0000<ESC>V0170<ESC>L0101<ESC>S"+b.getKodeJenis()+"<ESC>H0110<ESC>V0170<ESC>S"+b.getKodeIntern();
                if(listBarang.size()==i+1){}else{
                    Barang b2 = listBarang.get(i+1);
                    commands=commands+"<ESC>H0455<ESC>V0007<ESC>M"+b2.getKodeBarcode()+
                        "<ESC>H0455<ESC>V0030<ESC>BG01050"+b2.getKodeBarcode()+
                        "<ESC>H0455<ESC>V0105<ESC>S" +b2.getNamaBarang()+
                        "<ESC>H0475<ESC>V0130<ESC>L0102<ESC>M"+gr.format(b2.getBerat())+"<ESC>L0102<ESC>S gr" +
                        "<ESC>H0455<ESC>V0170<ESC>L0101<ESC>S"+b2.getKodeJenis()+"<ESC>H0565<ESC>V0170<ESC>S"+b2.getKodeIntern();
                }
                commands=commands+"<ESC>Q1<ESC>Z";
                char ch = 27;
                commands = commands.replace("<ESC>", String.valueOf(ch));
                PrintService pservice = services[selectedService];
                DocPrintJob job = pservice.createPrintJob();  
                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
                job.print(doc, null);
            }
        } 
    }
    public void printSuratPenjualan(PenjualanHead p)throws Exception{
        Faktur faktur = new Faktur(p.getNoPenjualan(), 
            tglNormal.format(tglBarang.parse(p.getTglPenjualan().substring(0,10))), 
            p.getNama(),p.getAlamat(),p.getNoTelp(),p.getKodeSales(),
            p.getTglPenjualan().substring(10, 16),rp.format(p.getGrandtotal()),
            angka(p.getGrandtotal())+" Rupiah");
        int i = 0;
        for(PenjualanDetail d : p.getListPenjualanDetail()){
            faktur.tambahItemFaktur(new ItemFaktur(
                d.getNamaBarang()+"("+d.getKodeBarcode()+")", gr.format(d.getBerat()), d.getBarang().getKadar()+" %", 
                d.getBarang().getKodeIntern(), rp.format(d.getHargaJual())));
            if(d.getOngkos()!=0){
                faktur.tambahItemFaktur(new ItemFaktur("     Ongkos", "", "", "", rp.format(d.getOngkos())));
                i++;
            }
            i++;
            if(i==4)
                break;
        }
        for(int j = i ; j<4;j++){
            faktur.tambahItemFaktur(new ItemFaktur("","","","",""));
        }
        faktur.tambahItemFaktur(new ItemFaktur("    "+p.getKeterangan(),"","","",""));
        SimpleEscp simpleEscp = new SimpleEscp();           
        Template template = new JsonTemplate(getClass().getResourceAsStream("template.json"));
        simpleEscp.print(template, DataSources.from(faktur));
    }
    public void printSuratKurangBayar(List<PenjualanDetail> listPenjualan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratKurangBayar.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPenjualan);
        Map hash = new HashMap();
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSlipPembayaranPoin(List<PembayaranPenjualan> pembayaranPoin)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SlipPembayaranPoin.jrxml"));
        HashMap hash = new HashMap();
        hash.put("nilaiPoin", sistem.getNilaiPoin());
        hash.put("kodeCabang", cabang.getKodeCabang());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pembayaranPoin);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSuratPenjualanCabang(List<PenjualanAntarCabangDetail> listPenjualan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiPenjualanCabang.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPenjualan);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(2));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSuratPemesanan(List<PemesananDetail> listPemesanan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratPemesanan.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPemesanan);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(2));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printBuktiPembelian(List<PembelianDetail> listPembelian)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiPembelianUmum.jrxml"));

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPembelian);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR)){
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSuratHutang(List<HutangDetail> listHutang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratHutang.jrxml"));
        
        Calendar c = Calendar.getInstance();
        c.setTime(tglBarang.parse(sistem.getTglSystem()));
        c.add(Calendar.MONTH , 3);
        
        HashMap hash = new HashMap();
        hash.put("jatuhTempo", tglBarang.format(c.getTime()));

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listHutang);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR)){
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(40,40,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSuratHutangInternal(List<HutangDetail> listHutang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratHutangIntern.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listHutang);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR)){
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(40,40,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printBuktiPelunasanGadai(List<HutangDetail> listHutang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiPelunasanGadai.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listHutang);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printSuratServis(List<Servis> listServis)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratServis.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listServis);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR)){
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(2));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printBuktiSetor(List<SetoranCabang> listSetor)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SetorUangKas.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listSetor);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR)){
                selectedService = i;
            }
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void printBuktiAmbilBarang(List<AmbilBarangDetail> listAmbil)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BuktiAmbilBarang.jrxml"));
        
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAmbil);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerRR))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();

    }
}
