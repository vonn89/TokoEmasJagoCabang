/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.Service;

import com.excellentsystem.TokoEmasJagoCabang.DAO.BarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.BatalLunasDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.BungaHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.HutangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KategoriTransaksiDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.LogHargaDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PegawaiDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembayaranPenjualanDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PemesananHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.AmbilBarangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.KeuanganPoinDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.LogMemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.MemberDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PindahDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.SistemPusatDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat.TpBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.ServisDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokHutangDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokOpnameDetailDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.StokOpnameHeadDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.VerifikasiDAO;
import com.excellentsystem.TokoEmasJagoCabang.Function;
import static com.excellentsystem.TokoEmasJagoCabang.Function.pembulatan;
import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import com.excellentsystem.TokoEmasJagoCabang.Model.BatalLunas;
import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import com.excellentsystem.TokoEmasJagoCabang.Model.KategoriTransaksi;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.LogHarga;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.KeuanganPoin;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.LogMember;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SetoranCabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SistemPusat;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.TambahUangCabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.TpBarang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
import com.excellentsystem.TokoEmasJagoCabang.Model.Sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokHutang;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameDetail;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameHead;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class Service {
    
    public static String updateStokBarangBarcodeMasuk(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status) throws Exception{
        StokBarang s = StokBarangDAO.getBarcode(con, kodeBarcode);
        if(s==null){
            StokBarang sb = new StokBarang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang(kodeBarang);
            sb.setKodeBarcode(kodeBarcode);
            sb.setKodeCabang(cabang.getKodeCabang());
            sb.setKodeGudang(cabang.getKodeCabang());
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeBatalMasuk(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getBarcode(con, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeKeluar(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getBarcode(con, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeBatalKeluar(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getBarcode(con, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeMasuk(Connection con, String kodeGudang,
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status) throws Exception{
        StokBarang s = StokBarangDAO.getNonBarcode(con, kodeGudang, kodeJenis);
        if(s==null){
            StokBarang sb = new StokBarang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang("");
            sb.setKodeBarcode("");
            sb.setKodeCabang(cabang.getKodeCabang());
            sb.setKodeGudang(kodeGudang);
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeBatalMasuk(Connection con, String kodeGudang, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getNonBarcode(con, kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeKeluar(Connection con, String kodeGudang, 
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getNonBarcode(con, kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat){
            System.out.println(s.getBeratAkhir() + " - "+ berat);
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        }
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeBatalKeluar(Connection con, String kodeGudang,
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.getNonBarcode(con, kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    
    public static String updateStokBarangBarcodePusatMasuk(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null){
            StokBarangCabang sb = new StokBarangCabang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang(kodeBarang);
            sb.setKodeBarcode(kodeBarcode);
            sb.setKodeCabang(cabang.getKodeCabang());
            sb.setKodeGudang(kodeGudang);
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangCabangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodePusatBatalMasuk(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodePusatKeluar(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodePusatBatalKeluar(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodePusatMasuk(Connection con, String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null){
            StokBarangCabang sb = new StokBarangCabang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang("");
            sb.setKodeBarcode("");
            sb.setKodeCabang(cabang.getKodeCabang());
            sb.setKodeGudang(kodeGudang);
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangCabangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodePusatBatalMasuk(Connection con, String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodePusatKeluar(Connection con, String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodePusatBatalKeluar(Connection con, String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    
    public static void insertKeuangan(Connection con, String noKeuangan, int noUrut, String tipeKasir,
            String tipeKeuangan, String kategori, String keterangan, double jumlahRp, String kodeSales)throws Exception{
        Keuangan k = new Keuangan();
        k.setNoKeuangan(noKeuangan);
        k.setNoUrut(noUrut);
        k.setTglKeuangan(Function.getSystemDate());
        k.setTipeKasir(tipeKasir);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setKeterangan(keterangan);
        k.setJumlahRp(jumlahRp);
        k.setKodeSales(kodeSales);
        KeuanganDAO.insert(con, k);
    }
    public static void insertKeuanganPoin(Connection conPusat, String noKeuangan, String kodeCabang, 
            String kategori, String deskripsi, double jumlahRp, String kodeUser)throws Exception{
        KeuanganPoin k = new KeuanganPoin();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(Function.getSystemDate());
        k.setKodeCabang(kodeCabang);
        k.setKategori(kategori);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        KeuanganPoinDAO.insert(conPusat, k);
    }
    public static void insertKeuanganPusat(Connection conPusat, String noKeuangan, String kodeCabang, String tipeKasir,
            String tipeKeuangan, String kategori, String deskripsi, double jumlahRp, String kodeUser)throws Exception{
        KeuanganCabang k = new KeuanganCabang();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(Function.getSystemDate());
        k.setKodeCabang(kodeCabang);
        k.setTipeKasir(tipeKasir);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        KeuanganCabangDAO.insert(conPusat, k);
    }
    
    public static String savePengaturanLainLain(Connection con, Sistem s, List<BungaHutang> listBungaHutang){
        try{
            con.setAutoCommit(false);
            
            SistemDAO.update(con, s);
            BungaHutangDAO.deleteAll(con);
            listBungaHutang.sort(Comparator.comparing(BungaHutang::getMinJumlahRp));
            int i =1;
            for(BungaHutang b : listBungaHutang){
                b.setNoUrut(i);
                BungaHutangDAO.insert(con, b);
                i++;
            }
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }
    public static String saveUpdateSistem(Connection con, Sistem s){
        try{
            con.setAutoCommit(false);
            
            SistemDAO.update(con, s);
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }
    public static String saveUpdateKategoriBarang(Connection con, Kategori k){
        try{
            con.setAutoCommit(false);
            
            KategoriDAO.update(con, k);
            
            LogHarga l = new LogHarga();
            l.setTanggal(sistem.getTglSystem());
            l.setKodeKategori(k.getKodeKategori());
            l.setHargaBeli(k.getHargaBeli());
            l.setHargaJual(k.getHargaJual());
            LogHargaDAO.update(con, l);
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }
    public static String saveUpdateJenisBarang(Connection con, Jenis j){
        try{
            con.setAutoCommit(false);
            
            JenisDAO.update(con, j);
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveUpdatePassword(Connection con, User u){
        try{
            con.setAutoCommit(false);
            
            UserDAO.update(con, u);
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (Exception ex) {
                return ex.toString();
            }
        }
    }
    public static String saveNewUser(Connection con, User u){
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(User user : UserDAO.getAll(con)){
                if(user.getKodeUser().equals(u.getKodeUser()))
                    status = "Username sudah pernah terdaftar";
            }
            UserDAO.insert(con, u);
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            for(Verifikasi v : u.getVerifikasi()){
                VerifikasiDAO.insert(con, v);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveUpdateUser(Connection con, User u){
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.update(con, u);
            OtoritasDAO.deleteByKodeUser(con, u.getKodeUser());
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            VerifikasiDAO.deleteByKodeUser(con, u.getKodeUser());
            for(Verifikasi v : u.getVerifikasi()){
                VerifikasiDAO.insert(con, v);
            }

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteUser(Connection con, User u){
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.delete(con, u);
            OtoritasDAO.deleteByKodeUser(con, u.getKodeUser());
            VerifikasiDAO.deleteByKodeUser(con, u.getKodeUser());

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    } 
    
    public static String importCabang(Connection conPusat, Connection conCabang){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);

            CabangDAO.deleteAll(conCabang);
            
            for(Cabang c : CabangDAO.getAll(conPusat)){
                CabangDAO.insert(conCabang, c);
            }

            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String importSales(Connection conPusat, Connection conCabang){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);

            PegawaiDAO.deleteAll(conCabang);
            
            for(Pegawai p : PegawaiDAO.getAll(conPusat)){
                PegawaiDAO.insert(conCabang, p);
            }

            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveNewKategoriTransaksi(Connection con, KategoriTransaksi k){
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(KategoriTransaksi temp : KategoriTransaksiDAO.getAllByStatus(con, "%")){
                if(temp.getKodeTransaksi().equals(k.getKodeTransaksi()))
                    status = "Kode kategori transaksi sudah pernah terdaftar";
            }
            KategoriTransaksiDAO.insert(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveUpdateKategoriTransaksi(Connection con, KategoriTransaksi k){
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.update(con, k);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePendaftaranMember(Connection conPusat, Connection conCabang, Member m){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);

            m.setKodeMember(MemberDAO.getId(conPusat));
            MemberDAO.insert(conPusat, m);
            
            LogMember l = new LogMember();
            l.setTanggal(Function.getSystemDate());
            l.setKodeCabang(cabang.getKodeCabang());
            l.setKodeMember(m.getKodeMember());
            l.setKategori("Pendaftaran Member");
            l.setKeterangan(m.getNoRfid()+" - "+m.getNoKartu());
            l.setJumlahRp(sistem.getBiayaKartuMember());
            l.setJumlahPoin(0);
            l.setKodeSales(m.getSalesDaftar());
            l.setStatus("true");
            l.setTglBatal("2000-01-01 00:00:00");
            l.setUserBatal("");
            LogMemberDAO.insert(conPusat, l);
            
            String nokeuangan = KeuanganDAO.getId(conCabang);
            insertKeuangan(conCabang, nokeuangan, 1, "Kasir", "Kas", "Pendaftaran Member", 
                m.getKodeMember(), sistem.getBiayaKartuMember(), m.getSalesDaftar());
            insertKeuangan(conCabang, nokeuangan, 2, "Kasir", "Pendapatan Kartu Member", "Pendaftaran Member", 
                m.getKodeMember(), sistem.getBiayaKartuMember(), m.getSalesDaftar());
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                conCabang.rollback();
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPendaftaranMember(Connection conPusat, Connection con, Member m, String user){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);

            if(MemberDAO.get(conPusat, m.getKodeMember()).getStatus().equals("false")){
                status = "member sudah pernah dihapus";
            }else{
                m.setNoRfid("Batal Daftar Member - "+m.getKodeMember());
                m.setNoKartu("Batal Daftar Member - "+m.getKodeMember());
                m.setStatus("false");
                MemberDAO.update(conPusat, m);

                LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), m.getKodeMember(), "Pendaftaran Member", m.getNoRfid()+" - "+m.getNoKartu());
                l.setStatus("false");
                l.setTglBatal(Function.getSystemDate());
                l.setUserBatal(user);
                LogMemberDAO.update(conPusat, l);

                Keuangan k = KeuanganDAO.get(con, "Kasir", "Pendapatan Kartu Member", "Pendaftaran Member", m.getKodeMember());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            }
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveUpdateMember(Connection conPusat, Member m){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);

            MemberDAO.update(conPusat, m);
            
            if(status.equals("true"))
                conPusat.commit();
            else
                conPusat.rollback();
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveGantiKartuMember(Connection conPusat, Connection conCabang, Member m, String noRfidBaru, String noKartuBaru, String kodeSales){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);

            LogMember l = new LogMember();
            l.setTanggal(Function.getSystemDate());
            l.setKodeCabang(cabang.getKodeCabang());
            l.setKodeMember(m.getKodeMember());
            l.setKategori("Ganti Kartu Member");
            l.setKeterangan(m.getNoRfid()+" - "+m.getNoKartu()+" -> "+noRfidBaru+" - "+noKartuBaru);
            l.setJumlahRp(sistem.getBiayaKartuMember());
            l.setJumlahPoin(0);
            l.setKodeSales(kodeSales);
            l.setStatus("true");
            l.setTglBatal("2000-01-01 00:00:00");
            l.setUserBatal("");
            LogMemberDAO.insert(conPusat, l);

            String nokeuangan = KeuanganDAO.getId(conCabang);
            insertKeuangan(conCabang, nokeuangan, 1, "Kasir", "Kas", "Ganti Kartu Member", 
                m.getKodeMember(), sistem.getBiayaKartuMember(), kodeSales);
            insertKeuangan(conCabang, nokeuangan, 2, "Kasir", "Pendapatan Kartu Member", "Ganti Kartu Member", 
                m.getKodeMember(), sistem.getBiayaKartuMember(), kodeSales);
            
            m.setNoRfid(noRfidBaru);
            m.setNoKartu(noKartuBaru);
            MemberDAO.update(conPusat, m);
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conCabang.rollback();
                conCabang.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalGantiKartuMember(Connection conPusat, Connection con, Member m, String noRfidLama, String noKartuLama, String user){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String noKartuBaru = m.getNoKartu();
            String noRfidBaru = m.getNoRfid();
            m.setNoKartu(noKartuLama);
            m.setNoRfid(noRfidLama);
            MemberDAO.update(con, m);
            
            LogMember l = LogMemberDAO.get(con, cabang.getKodeCabang(), m.getKodeMember(), "Ganti Kartu Member", noRfidLama+"-"+noKartuLama+" -> "+noRfidBaru+"-"+noKartuBaru);
            l.setStatus("false");
            l.setTglBatal(Function.getSystemDate());
            l.setUserBatal(user);
            LogMemberDAO.update(con, l);
            
            Keuangan k = KeuanganDAO.get(con, "Kasir", "Pendapatan Kartu Member", "Ganti Kartu Member", 
                    m.getKodeMember());
            KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveDeleteMember(Connection conPusat, Member m, String user){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            if(MemberDAO.get(conPusat, m.getKodeMember()).getStatus().equals("false")){
                status = "member sudah pernah dihapus";
            }else{
                m.setNoRfid("Delete Member - "+m.getKodeMember());
                m.setNoKartu("Delete Member - "+m.getKodeMember());
                m.setStatus("false");
                MemberDAO.update(conPusat, m);

                LogMember l = new LogMember();
                l.setTanggal(Function.getSystemDate());
                l.setKodeCabang(cabang.getKodeCabang());
                l.setKodeMember(m.getKodeMember());
                l.setKategori("Hapus Member");
                l.setKeterangan("");
                l.setJumlahRp(0);
                l.setJumlahPoin(0);
                l.setKodeSales(user);
                l.setStatus("true");
                l.setTglBatal("2000-01-01 00:00:00");
                l.setUserBatal("");
                LogMemberDAO.insert(conPusat, l);
            }
            
            if(status.equals("true"))
                conPusat.commit();
            else
                conPusat.rollback();
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaBarang(Connection conPusat, Connection conCabang, PindahHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                double totalPindah = 0;
                PindahHeadDAO.update(conPusat, p);
                for(PindahDetail d : p.getListPindahDetail()){
                    Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                    if(b==null){
                        b = new Barang();
                        b.setKodeBarang(d.getKodeBarang());
                        b.setKodeBarcode(d.getKodeBarcode());
                        b.setNamaBarang(d.getNamaBarang());
                        b.setKodeKategori(d.getKodeKategori());
                        b.setKodeJenis(d.getKodeJenis());
                        b.setKodeIntern(d.getKodeIntern());
                        b.setKadar(d.getKadar());
                        b.setBerat(d.getBerat());
                        b.setBeratAsli(d.getBeratAsli());
                        b.setBeratPersen(d.getBeratPersen());
                        b.setNilaiPokok(d.getNilaiPokok());
                        b.setInputDate(d.getInputDate());
                        b.setInputBy(d.getInputBy());
                        b.setAsalBarang(d.getAsalBarang());
                        b.setStatus(d.getStatus());
                        BarangDAO.insert(conCabang, b);
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(conCabang, b);
                    }

                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodePusatKeluar(conPusat, b.getKodeBarang(), b.getKodeBarcode(), 
                            cabang.getKodeCabang()+"-Pindah", b.getKodeKategori(), b.getKodeJenis(), 
                            qty, berat, beratPersen, nilai, status);
                    status = updateStokBarangBarcodeMasuk(conCabang, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);

                    totalPindah = totalPindah + nilai;
                }
                String nokeuangan = KeuanganDAO.getId(conCabang);
                insertKeuangan(conCabang, nokeuangan, 1, "Kasir", cabang.getKodeCabang(), "Terima Barang", 
                    p.getNoPindah(), totalPindah, p.getUserTerima());
                insertKeuanganPusat(conPusat, nokeuangan, cabang.getKodeCabang(), "Kasir", 
                    cabang.getKodeCabang()+"-Pindah", "Terima Barang", 
                    p.getNoPindah(), -totalPindah, p.getUserTerima());
            }
            
            if(status.equals("true")){
                conCabang.commit();
                conPusat.commit();
            }else{
                conCabang.rollback();
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String saveBatalTerimaBarang(Connection conPusat, Connection conCabang, PindahHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                if(PindahHeadDAO.get(conPusat, p.getNoPindah()).getStatusTerima().equals("false")){
                    status = "Terima barang sudah pernah dibatal";
                }else{
                    PindahHeadDAO.update(conPusat, p);
                    p.setListPindahDetail(PindahDetailDAO.getAllByNoPindah(conPusat, p.getNoPindah()));
                    for(PindahDetail d : p.getListPindahDetail()){
                        Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                        if(b==null){
                            status = "Barang dengan kode barcode "+ d.getKodeBarcode()+" tidak ditemukan";
                        }else if(b.getStatus().equals("false")){
                            status = "Barang dengan kode barcode "+ b.getKodeBarcode()+" tidak tersedia";
                        }else{
                            b.setStatus("false");
                            BarangDAO.update(conCabang, b);

                            int qty = 1;
                            double berat = b.getBerat();
                            double beratPersen = b.getBeratPersen();
                            double nilai = b.getNilaiPokok();

                            status = updateStokBarangBarcodePusatBatalKeluar(conPusat, b.getKodeBarang(), b.getKodeBarcode(), 
                                cabang.getKodeCabang()+"-Pindah", b.getKodeKategori(), b.getKodeJenis(), 
                                qty, berat, beratPersen, nilai, status);
                            status = updateStokBarangBarcodeBatalMasuk(conCabang, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                        }
                    }
                    Keuangan k = KeuanganDAO.get(conCabang, "Kasir", cabang.getKodeCabang(), "Terima Barang", p.getNoPindah());
                    KeuanganDAO.deleteAll(conCabang, k.getNoKeuangan());
                    KeuanganCabangDAO.deleteAll(conPusat, k.getNoKeuangan());
                }
            }
            
            if(status.equals("true")){
                conCabang.commit();
                conPusat.commit();
            }else{
                conCabang.rollback();
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveAmbilBarang(Connection conPusat, Connection conCabang, AmbilBarangHead a, List<PembelianDetail> listPembelianDetail){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                String noAmbil = AmbilBarangHeadDAO.getId(conPusat);
                a.setNoAmbilBarang(noAmbil);
                AmbilBarangHeadDAO.insert(conPusat, a);
                
                List<String> listJenis = new ArrayList<>();
                for(AmbilBarangDetail d : a.getDetail()){
                    d.setNoAmbilBarang(noAmbil);
                    AmbilBarangDetailDAO.insert(conPusat, d);
                    
                    if(!listJenis.contains(d.getKodeJenis()))
                        listJenis.add(d.getKodeJenis());
                }
                double totalAmbil = 0;
                for(String kodeJenis : listJenis){
                    String kodeKategori = "";
                    int qty = 0;
                    double beratKotor = 0;
                    double beratPersen = 0;
                    double hargaBeli = 0;
                    for(AmbilBarangDetail d : a.getDetail()){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            kodeKategori = d.getKodeKategori();
                            qty = qty + d.getQty();
                            beratKotor = beratKotor+ d.getBeratKotor();
                            beratPersen = beratPersen + d.getBeratPersen();
                            hargaBeli = hargaBeli + d.getHargaBeli();
                        }
                    }
                    if(qty!=0){
                        status = updateStokBarangNonBarcodeKeluar(conCabang, cabang.getKodeCabang()+"-BLN", kodeKategori, kodeJenis,  
                                qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                        
                        status = updateStokBarangNonBarcodePusatMasuk(conPusat, cabang.getKodeCabang()+"-Ambil", kodeKategori, kodeJenis,  
                                qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                        
                        totalAmbil = totalAmbil + hargaBeli;
                    }
                }
                for(PembelianDetail d : listPembelianDetail){
                    if(PembelianDetailDAO.get(conCabang, d.getNoPembelian(), d.getNoUrut()).getStatusAmbil().equals("true")){
                        status = d.getKodeJenis()+" - "+d.getNamaBarang()+" barang sudah pernah di ambil";
                    }else{
                        d.setStatusAmbil("true");
                        d.setNoAmbilBarang(noAmbil);
                        PembelianDetailDAO.update(conCabang, d);
                    }
                }
                String nokeuangan = KeuanganDAO.getId(conCabang);
                insertKeuangan(conCabang, nokeuangan, 1, "Kasir", cabang.getKodeCabang()+"-BLN", "Ambil Barang", 
                    a.getNoAmbilBarang(), -totalAmbil, a.getKodeUser());
                insertKeuanganPusat(conPusat, nokeuangan, cabang.getKodeCabang(), "Kasir", 
                    cabang.getKodeCabang()+"-Ambil", "Ambil Barang", 
                    a.getNoAmbilBarang(), totalAmbil, a.getKodeUser());
            }
            if(status.equals("true")){
                conCabang.commit();
                conPusat.commit();
            }else{
                conCabang.rollback();
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String saveBatalAmbilBarang(Connection conPusat, Connection conCabang, AmbilBarangHead a){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                AmbilBarangHead checkAmbilBarang = AmbilBarangHeadDAO.get(conPusat, a.getNoAmbilBarang());
                if (checkAmbilBarang.getStatusBatal().equals("true")){
                    status = "Ambil barang tidak dapat dibatal, karena sudah dibatal";
                }else if (checkAmbilBarang.getStatusTerima().equals("true")){
                    status = "Ambil barang tidak dapat dibatal, karena sudah diterima";
                }else{
                    AmbilBarangHeadDAO.update(conPusat, a);
                    List<String> listJenis = new ArrayList<>();
                    a.setDetail(AmbilBarangDetailDAO.getAllByNoAmbilBarang(conPusat, a.getNoAmbilBarang()));
                    for(AmbilBarangDetail d : a.getDetail()){
                        if(!listJenis.contains(d.getKodeJenis()))
                            listJenis.add(d.getKodeJenis());
                    }
                    for(String kodeJenis : listJenis){
                        String kodeKategori = "";
                        int qty = 0;
                        double beratKotor = 0;
                        double beratPersen = 0;
                        double hargaBeli = 0;
                        for(AmbilBarangDetail d : a.getDetail()){
                            if(kodeJenis.equals(d.getKodeJenis())){
                                kodeKategori = d.getKodeKategori();
                                qty = qty + d.getQty();
                                beratKotor = beratKotor+ d.getBeratKotor();
                                beratPersen = beratPersen + d.getBeratPersen();
                                hargaBeli = hargaBeli + d.getHargaBeli();
                            }
                        }
                        if(qty!=0){
                            status = updateStokBarangNonBarcodeBatalKeluar(conCabang, cabang.getKodeCabang()+"-BLN", kodeKategori, kodeJenis,  
                                    qty, beratKotor, beratPersen, hargaBeli, status);

                            status = updateStokBarangNonBarcodePusatBatalMasuk(conPusat, cabang.getKodeCabang()+"-Ambil", kodeKategori, kodeJenis,  
                                    qty, beratKotor, beratPersen, hargaBeli, status);
                        }
                    }
                    List<PembelianDetail> listPembelianDetail = PembelianDetailDAO.getAllByNoAmbil(conCabang, a.getNoAmbilBarang());
                    for(PembelianDetail d : listPembelianDetail){
                        d.setStatusAmbil("false");
                        d.setNoAmbilBarang("");
                        PembelianDetailDAO.update(conCabang, d);
                    }
                    Keuangan k = KeuanganDAO.get(conCabang, "Kasir", cabang.getKodeCabang()+"-BLN", "Ambil Barang", a.getNoAmbilBarang());
                    KeuanganDAO.deleteAll(conCabang, k.getNoKeuangan());
                    KeuanganCabangDAO.deleteAll(conPusat, k.getNoKeuangan());
                }
            }
            
            if(status.equals("true")){
                conCabang.commit();
                conPusat.commit();
            }else{
                conCabang.rollback();
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveStokOpnameHead(Connection con, StokOpnameHead s){
        try{
            String status = "true";
            con.setAutoCommit(false);

            StokOpnameHeadDAO.insert(con, s);
            for(StokOpnameDetail d : s.getListStokOpnameDetail())
                StokOpnameDetailDAO.insert(con, d);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveStokOpnameDetail(Connection con, StokOpnameDetail d){
        try{
            String status = "true";
            con.setAutoCommit(false);

            StokOpnameDetailDAO.update(con, d);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePenjualan(Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            double totalNilai = 0;
            
            p.setNoPenjualan(PenjualanHeadDAO.getId(con));
            PenjualanHeadDAO.insert(con, p);
            
            for(PenjualanDetail d : p.getListPenjualanDetail()){
                d.setNoPenjualan(p.getNoPenjualan());
                PenjualanDetailDAO.insert(con, d);
                
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if(b==null){
                    status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                }else if(b.getStatus().equals("false")){
                    status = "Kode barcode "+d.getKodeBarcode()+" sudah dijual/dihancur";
                }else{
                    b.setStatus("false");
                    BarangDAO.update(con, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    totalNilai = totalNilai + nilai;
                }
            }
            
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
            }
            
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 3, "Kasir", "HPP Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            insertKeuangan(con, nokeuangan, 4, "Kasir", cabang.getKodeCabang(), "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            
            if(p.getSisaPembayaran()!=0){
                insertKeuangan(con, nokeuangan, 5, "Kasir", "Kas", "Orang Kurang", 
                    p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                insertKeuangan(con, nokeuangan, 6, "Kasir", "Piutang Penjualan", "Orang Kurang", 
                    p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String savePenjualanPoin(Connection conPusat, Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);
            conPusat.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            double totalNilai = 0;
            p.setNoPenjualan(PenjualanHeadDAO.getId(con));
            PenjualanHeadDAO.insert(con, p);
            for(PenjualanDetail d : p.getListPenjualanDetail()){
                d.setNoPenjualan(p.getNoPenjualan());
                PenjualanDetailDAO.insert(con, d);
                
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if(b==null){
                    status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                }else if(b.getStatus().equals("false")){
                    status = "Kode barcode "+d.getKodeBarcode()+" sudah dijual/dihancur";
                }else{
                    b.setStatus("false");
                    BarangDAO.update(con, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    totalNilai = totalNilai + nilai;
                }
            }
            double pembayaranCash = 0;
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
                
                if(pp.getJenisPembayaran().equals("Cash"))
                    pembayaranCash = pembayaranCash + pp.getJumlahPembayaran();
                
                if(pp.getJenisPembayaran().equals("Reward Poin")){
                    if(p.getKodeMember().equals(pp.getKeterangan())){
                        p.getMember().setPoin(p.getMember().getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                        Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                        m.setPoin(m.getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        MemberDAO.update(conPusat, m);
                    }

                    LogMember l = new LogMember();
                    l.setTanggal(Function.getSystemDate());
                    l.setKodeCabang(cabang.getKodeCabang());
                    l.setKodeMember(pp.getKeterangan());
                    l.setKategori("Pembayaran Poin");
                    l.setKeterangan(p.getNoPenjualan());
                    l.setJumlahRp(-pp.getJumlahPembayaran());
                    l.setJumlahPoin((int)(-pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    l.setKodeSales(p.getKodeSales());
                    l.setStatus("true");
                    l.setTglBatal("2000-01-01 00:00:00");
                    l.setUserBatal("");
                    LogMemberDAO.insert(conPusat, l);

                    insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Pembayaran Poin", 
                        p.getNoPenjualan(), -pp.getJumlahPembayaran(), p.getKodeSales());
                }
            }
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 3, "Kasir", "HPP Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            insertKeuangan(con, nokeuangan, 4, "Kasir", cabang.getKodeCabang(), "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            
            if(p.getSisaPembayaran()!=0){
                insertKeuangan(con, nokeuangan, 5, "Kasir", "Kas", "Orang Kurang", 
                    p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                insertKeuangan(con, nokeuangan, 6, "Kasir", "Piutang Penjualan", "Orang Kurang", 
                    p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
            }
            if(p.getMember()!=null){
                int getPoin = (int)(pembayaranCash/sistem.getGetPoinPembayaranPenjualan());
                p.getMember().setPoin(p.getMember().getPoin()+getPoin);
                MemberDAO.update(conPusat, p.getMember());
                
                LogMember l = new LogMember();
                l.setTanggal(Function.getSystemDate());
                l.setKodeCabang(cabang.getKodeCabang());
                l.setKodeMember(p.getMember().getKodeMember());
                l.setKategori("Get Poin Pembelian");
                l.setKeterangan(p.getNoPenjualan());
                l.setJumlahRp(pembayaranCash);
                l.setJumlahPoin(getPoin);
                l.setStatus("true");
                l.setKodeSales(p.getKodeSales());
                l.setTglBatal("2000-01-01 00:00:00");
                l.setUserBatal("");
                LogMemberDAO.insert(conPusat, l);

                insertKeuangan(con, nokeuangan, 7, "Kasir", "Kas", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                insertKeuangan(con, nokeuangan, 8, "Kasir", "Beban Poin", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Get Poin", 
                    p.getNoPenjualan(), getPoin*sistem.getNilaiPoin(), p.getKodeSales());
            }
            
            if(status.equals("true")){
                con.commit();
                conPusat.commit();
            }else{
                con.rollback();
                conPusat.rollback();
            }
            con.setAutoCommit(true);
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPenjualan(Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(PenjualanHeadDAO.get(con, p.getNoPenjualan()).getStatus().equals("false")){
                status = "Penjualan sudah pernah dibatalkan";
            }else{
                Keuangan k = KeuanganDAO.get(con, "Kasir", "Penjualan Umum", "Penjualan Umum", p.getNoPenjualan());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());

                Keuangan kp = KeuanganDAO.get(con, "Kasir", "Piutang Penjualan", "Orang Bayar", p.getNoPenjualan());
                if(kp!=null)
                    KeuanganDAO.deleteAll(con, kp.getNoKeuangan());

                PenjualanHeadDAO.update(con, p);
                p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
                for(PenjualanDetail d : p.getListPenjualanDetail()){
                    Barang b = BarangDAO.get(con, d.getKodeBarcode());
                    if(b==null){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                    }else if(b.getStatus().equals("true")){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak terjual";
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(con, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();
                        status = updateStokBarangBarcodeBatalKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    }
                }
                for(PembayaranPenjualan pp : p.getListPembayaran()){
                    pp.setStatus("false");
                    pp.setTglBatal(p.getTglBatal());
                    pp.setUserBatal(p.getUserBatal());
                    PembayaranPenjualanDAO.update(con, pp);
                }
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPenjualanPoin(Connection conPusat, Connection con, PenjualanHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);

            if(PenjualanHeadDAO.get(con, p.getNoPenjualan()).getStatus().equals("false")){
                status = "Penjualan sudah pernah dibatalkan";
            }else{
                Keuangan k = KeuanganDAO.get(con, "Kasir", "Penjualan Umum", "Penjualan Umum", p.getNoPenjualan());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                KeuanganPoinDAO.deleteAll(conPusat, k.getNoKeuangan());

                Keuangan kp = KeuanganDAO.get(con, "Kasir", "Piutang Penjualan", "Orang Bayar", p.getNoPenjualan());
                if(kp!=null)
                    KeuanganDAO.deleteAll(con, kp.getNoKeuangan());

                PenjualanHeadDAO.update(con, p);
                p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
                for(PenjualanDetail d : p.getListPenjualanDetail()){
                    Barang b = BarangDAO.get(con, d.getKodeBarcode());
                    if(b==null){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                    }else if(b.getStatus().equals("true")){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak terjual";
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(con, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();
                        status = updateStokBarangBarcodeBatalKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    }
                }
                if(!"".equals(p.getKodeMember()))
                    p.setMember(MemberDAO.get(conPusat, p.getKodeMember()));

                double pembayaranCash = 0;
                p.setListPembayaran(PembayaranPenjualanDAO.getAllByNoPenjualanAndStatus(con, p.getNoPenjualan(), "true"));
                for(PembayaranPenjualan pp : p.getListPembayaran()){
                    pp.setStatus("false");
                    pp.setTglBatal(p.getTglBatal());
                    pp.setUserBatal(p.getUserBatal());
                    PembayaranPenjualanDAO.update(con, pp);

                    if(pp.getJenisPembayaran().equals("Cash"))
                        pembayaranCash = pembayaranCash + pp.getJumlahPembayaran();

                    if(pp.getJenisPembayaran().equals("Reward Poin")){
                        if(p.getKodeMember().equals(pp.getKeterangan())){
                            p.getMember().setPoin(p.getMember().getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                            Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                            m.setPoin(m.getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                            MemberDAO.update(conPusat, m);
                        }

                        LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), pp.getKeterangan(), "Pembayaran Poin", p.getNoPenjualan());
                        l.setStatus("false");
                        l.setTglBatal(p.getTglBatal());
                        l.setUserBatal(p.getUserBatal());
                        LogMemberDAO.update(conPusat, l);
                    }
                }
                if(p.getMember()!=null){
                    p.getMember().setPoin(p.getMember().getPoin()-(int)(pembayaranCash/sistem.getGetPoinPembayaranPenjualan()));
                    MemberDAO.update(conPusat, p.getMember());

                    LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), p.getKodeMember(), "Get Poin Pembelian", p.getNoPenjualan());
                    l.setStatus("false");
                    l.setTglBatal(p.getTglBatal());
                    l.setUserBatal(p.getUserBatal());
                    LogMemberDAO.update(conPusat, l);
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String savePembayaranKekurangan(Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            PenjualanHeadDAO.update(con, p);
            double pembayaran = 0;
            String sales = "";
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
                
                pembayaran = pembayaran + pp.getJumlahPembayaran();
                sales = pp.getKodeSales();
            }
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Orang Bayar", 
                p.getNoPenjualan(), pembayaran, sales);
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Piutang Penjualan", "Orang Bayar", 
                p.getNoPenjualan(), -(pembayaran), sales);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String savePembayaranKekuranganPoin(Connection conPusat, Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            PenjualanHeadDAO.update(con, p);
            double pembayaranCash = 0;
            double pembayaranPoin = 0;
            String sales = "";
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
                
                if(pp.getJenisPembayaran().equals("Cash"))
                    pembayaranCash = pembayaranCash + pp.getJumlahPembayaran();
                
                if(pp.getJenisPembayaran().equals("Reward Poin")){
                    if(p.getKodeMember().equals(pp.getKeterangan())){
                        p.getMember().setPoin(p.getMember().getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                        Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                        m.setPoin(m.getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        MemberDAO.update(conPusat, m);
                    }

                    LogMember l = new LogMember();
                    l.setTanggal(Function.getSystemDate());
                    l.setKodeCabang(cabang.getKodeCabang());
                    l.setKodeMember(pp.getKeterangan());
                    l.setKategori("Pembayaran Poin");
                    l.setKeterangan(p.getNoPenjualan());
                    l.setJumlahRp(-pp.getJumlahPembayaran());
                    l.setJumlahPoin((int)(-pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    l.setStatus("true");
                    l.setTglBatal("2000-01-01 00:00:00");
                    l.setUserBatal("");
                    LogMemberDAO.insert(conPusat, l);

                    insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Pembayaran Poin", 
                        p.getNoPenjualan(), -pp.getJumlahPembayaran(), p.getKodeSales());
                    
                    pembayaranPoin = pembayaranPoin + pp.getJumlahPembayaran();
                }
                sales = pp.getKodeSales();
            }
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Orang Bayar", 
                p.getNoPenjualan(), pembayaranPoin+pembayaranCash, sales);
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Piutang Penjualan", "Orang Bayar", 
                p.getNoPenjualan(), -(pembayaranPoin+pembayaranCash), sales);
            
            if(p.getMember()!=null){
                int getPoin = (int)(pembayaranCash/sistem.getGetPoinPembayaranPenjualan());
                p.getMember().setPoin(p.getMember().getPoin()+getPoin);
                MemberDAO.update(conPusat, p.getMember());
                
                LogMember l = new LogMember();
                l.setTanggal(Function.getSystemDate());
                l.setKodeCabang(cabang.getKodeCabang());
                l.setKodeMember(p.getMember().getKodeMember());
                l.setKategori("Get Poin Pembelian");
                l.setKeterangan(p.getNoPenjualan());
                l.setJumlahRp(pembayaranCash);
                l.setJumlahPoin(getPoin);
                l.setStatus("true");
                l.setTglBatal("2000-01-01 00:00:00");
                l.setUserBatal("");
                LogMemberDAO.insert(conPusat, l);

                insertKeuangan(con, nokeuangan, 3, "Kasir", "Kas", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), sales);
                insertKeuangan(con, nokeuangan, 4, "Kasir", "Beban Poin", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), sales);
                insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Get Poin", 
                    p.getNoPenjualan(), getPoin*sistem.getNilaiPoin(), sales);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembayaranKekurangan(Connection con, PembayaranPenjualan pp){
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(PembayaranPenjualanDAO.get(con, pp.getNoPembayaran()).getStatus().equals("false")){
                status = "Pembayaran sudah pernah dibatal";
            }else{
                PembayaranPenjualanDAO.update(con, pp);

                PenjualanHead p = PenjualanHeadDAO.get(con, pp.getNoPenjualan());
                p.setPembayaran(p.getPembayaran()-pp.getJumlahPembayaran());
                p.setSisaPembayaran(p.getSisaPembayaran()+pp.getJumlahPembayaran());
                PenjualanHeadDAO.update(con, p);

                Keuangan k = KeuanganDAO.get(con, "Kasir", "Piutang Penjualan", "Orang Bayar", p.getNoPenjualan());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembayaranKekuranganPoin(Connection conPusat, Connection con, PenjualanHead p, PembayaranPenjualan pp){
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(PembayaranPenjualanDAO.get(con, pp.getNoPembayaran()).getStatus().equals("false")){
                status = "Pembayaran sudah pernah dibatal";
            }else{
                PembayaranPenjualanDAO.update(con, pp);

                p.setPembayaran(p.getPembayaran()-pp.getJumlahPembayaran());
                p.setSisaPembayaran(p.getSisaPembayaran()+pp.getJumlahPembayaran());
                PenjualanHeadDAO.update(con, p);

                Keuangan k = KeuanganDAO.get(con, "Kasir", "Piutang Penjualan", "Orang Bayar", p.getNoPenjualan());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                KeuanganPoinDAO.deleteAll(conPusat, k.getNoKeuangan());


                if(pp.getJenisPembayaran().equals("Reward Poin")){
                    if(p.getKodeMember().equals(pp.getKeterangan())){
                        p.getMember().setPoin(p.getMember().getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                        Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                        m.setPoin(m.getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        MemberDAO.update(conPusat, m);
                    }

                    LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), pp.getKeterangan(), "Pembayaran Poin", p.getNoPenjualan());
                    l.setStatus("false");
                    l.setTglBatal(p.getTglBatal());
                    l.setUserBatal(p.getUserBatal());
                    LogMemberDAO.update(conPusat, l);
                }

                if(p.getMember()!=null && pp.getJenisPembayaran().equals("Cash")){
                    p.getMember().setPoin(p.getMember().getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getGetPoinPembayaranPenjualan()));
                    MemberDAO.update(conPusat, p.getMember());

                    LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), p.getKodeMember(), "Get Poin Pembelian", p.getNoPenjualan());
                    l.setStatus("false");
                    l.setTglBatal(p.getTglBatal());
                    l.setUserBatal(p.getUserBatal());
                    LogMemberDAO.update(conPusat, l);
                }
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveBatalPenjualanBedaTanggal(Connection con, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(PenjualanHeadDAO.get(con, p.getNoPenjualan()).getStatus().equals("false")){
                status = "Penjualan sudah pernah dibatal";
            }else{
                PenjualanHeadDAO.update(con, p);
                p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
                double totalNilai = 0;
                for(PenjualanDetail d : p.getListPenjualanDetail()){
                    Barang b = BarangDAO.get(con, d.getKodeBarcode());
                    if(b==null){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                    }else if(b.getStatus().equals("true")){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak terjual";
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(con, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();
                        status = updateStokBarangBarcodeMasuk(con, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);

                        totalNilai = totalNilai + nilai;
                    }
                }
                for(PembayaranPenjualan pp : p.getListPembayaran()){
                    pp.setStatus("false");
                    pp.setTglBatal(p.getTglBatal());
                    pp.setUserBatal(p.getUserBatal());
                    PembayaranPenjualanDAO.update(con, pp);
                }

                String nokeuangan = KeuanganDAO.getId(con);

                insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), -p.getGrandtotal(), p.getKodeSales());
                insertKeuangan(con, nokeuangan, 2, "Kasir", "Penjualan Umum", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), -p.getGrandtotal(), p.getKodeSales());

                insertKeuangan(con, nokeuangan, 3, "Kasir", "HPP Penjualan Umum", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), totalNilai, p.getKodeSales());
                insertKeuangan(con, nokeuangan, 4, "Kasir", cabang.getKodeCabang(), "Batal Penjualan Umum", 
                    p.getNoPenjualan(), totalNilai, p.getKodeSales());

                if(p.getSisaPembayaran()!=0){
                    insertKeuangan(con, nokeuangan, 5, "Kasir", "Kas", "Batal Orang Kurang", 
                        p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
                    insertKeuangan(con, nokeuangan, 6, "Kasir", "Piutang Penjualan", "Batal Orang Kurang", 
                        p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                }
            }
            
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPenjualanPoinBedaTanggal(Connection conPusat, Connection con, PenjualanHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            if(PenjualanHeadDAO.get(con, p.getNoPenjualan()).getStatus().equals("false")){
                status = "Penjualan sudah pernah dibatalkan";
            }else{
                String nokeuangan = KeuanganDAO.getId(con);

                PenjualanHeadDAO.update(con, p);
                p.setListPenjualanDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
                double totalNilai = 0;
                for(PenjualanDetail d : p.getListPenjualanDetail()){
                    Barang b = BarangDAO.get(con, d.getKodeBarcode());
                    if(b==null){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                    }else if(b.getStatus().equals("true")){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak terjual";
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(con, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();
                        status = updateStokBarangBarcodeMasuk(con, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);

                        totalNilai = totalNilai + nilai;
                    }
                }
                if(!"".equals(p.getKodeMember()))
                    p.setMember(MemberDAO.get(conPusat, p.getKodeMember()));

                double pembayaranCash = 0;
                p.setListPembayaran(PembayaranPenjualanDAO.getAllByNoPenjualanAndStatus(con, p.getNoPenjualan(), "true"));
                for(PembayaranPenjualan pp : p.getListPembayaran()){
                    pp.setStatus("false");
                    pp.setTglBatal(p.getTglBatal());
                    pp.setUserBatal(p.getUserBatal());
                    PembayaranPenjualanDAO.update(con, pp);

                    if(pp.getJenisPembayaran().equals("Cash"))
                        pembayaranCash = pembayaranCash + pp.getJumlahPembayaran();

                    if(pp.getJenisPembayaran().equals("Reward Poin")){
                        if(p.getKodeMember().equals(pp.getKeterangan())){
                            p.getMember().setPoin(p.getMember().getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                            Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                            m.setPoin(m.getPoin()+(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                            MemberDAO.update(conPusat, m);
                        }

                        LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), pp.getKeterangan(), "Pembayaran Poin", p.getNoPenjualan());
                        l.setStatus("false");
                        l.setTglBatal(p.getTglBatal());
                        l.setUserBatal(p.getUserBatal());
                        LogMemberDAO.update(conPusat, l);

                        insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Batal Pembayaran Poin", 
                            p.getNoPenjualan(), pp.getJumlahPembayaran(), p.getKodeSales());
                    }
                }
                if(p.getMember()!=null){
                    int getPoin = (int)(pembayaranCash/sistem.getGetPoinPembayaranPenjualan());
                    p.getMember().setPoin(p.getMember().getPoin()-getPoin);
                    MemberDAO.update(conPusat, p.getMember());

                    LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), p.getKodeMember(), "Get Poin Pembelian", p.getNoPenjualan());
                    l.setStatus("false");
                    l.setTglBatal(p.getTglBatal());
                    l.setUserBatal(p.getUserBatal());
                    LogMemberDAO.update(conPusat, l);

                    insertKeuangan(con, nokeuangan, 7, "Kasir", "Kas", "Batal Poin", 
                        p.getNoPenjualan(), getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                    insertKeuangan(con, nokeuangan, 8, "Kasir", "Beban Poin", "Batal Poin", 
                        p.getNoPenjualan(), getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                    insertKeuanganPoin(conPusat, nokeuangan, cabang.getKodeCabang(), "Batal Get Poin", 
                        p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                }


                insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), -p.getGrandtotal(), p.getKodeSales());
                insertKeuangan(con, nokeuangan, 2, "Kasir", "Penjualan Umum", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), -p.getGrandtotal(), p.getKodeSales());

                insertKeuangan(con, nokeuangan, 3, "Kasir", "HPP Penjualan Umum", "Batal Penjualan Umum", 
                    p.getNoPenjualan(), totalNilai, p.getKodeSales());
                insertKeuangan(con, nokeuangan, 4, "Kasir", cabang.getKodeCabang(), "Batal Penjualan Umum", 
                    p.getNoPenjualan(), totalNilai, p.getKodeSales());

                if(p.getSisaPembayaran()!=0){
                    insertKeuangan(con, nokeuangan, 5, "Kasir", "Kas", "Batal Orang Kurang", 
                        p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
                    insertKeuangan(con, nokeuangan, 6, "Kasir", "Piutang Penjualan", "Batal Orang Kurang", 
                        p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePenjualanCabang(Connection conPusat, Connection conCabang, PenjualanAntarCabangHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            String noKeuangan = KeuanganDAO.getId(conCabang);
            String noPenjualan = PenjualanAntarCabangHeadDAO.getId(conPusat);
            p.setNoPenjualan(noPenjualan);
            PenjualanAntarCabangHeadDAO.insert(conPusat, p);
            int noUrut = 1;
            double totalNilai = 0;
            for(PenjualanAntarCabangDetail d : p.getListPenjualanAntarCabangDetail()){
                d.setNoPenjualan(noPenjualan);
                d.setNoUrut(noUrut);
                PenjualanAntarCabangDetailDAO.insert(conPusat, d);
                
                Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                if(b==null){
                    status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                }else if(b.getStatus().equals("false")){
                    status = "Kode barcode "+d.getKodeBarcode()+" sudah dijual/dihancur";
                }else{
                    b.setStatus("false");
                    BarangDAO.update(conCabang, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeKeluar(conCabang, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    totalNilai = totalNilai + nilai;
                }
                noUrut = noUrut + 1;
            }
            insertKeuangan(conCabang, noKeuangan, 1, "Kasir", "Kas", "Penjualan Cabang - "+p.getCabangTujuan(), 
                    p.getNoPenjualan(), p.getTotalHarga(), p.getKodeSales());
            insertKeuangan(conCabang, noKeuangan, 2, "Kasir", cabang.getKodeCabang(), "Penjualan Cabang - "+p.getCabangTujuan(), 
                    p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            insertKeuangan(conCabang, noKeuangan, 3, "Kasir", "Penjualan Antar Cabang", "Penjualan Cabang - "+p.getCabangTujuan(), 
                    p.getNoPenjualan(), p.getTotalHarga(), p.getKodeSales());
            insertKeuangan(conCabang, noKeuangan, 4, "Kasir", "HPP Penjualan Antar Cabang", "Penjualan Cabang - "+p.getCabangTujuan(), 
                    p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPenjualanCabang(Connection conPusat, Connection conCabang, PenjualanAntarCabangHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            PenjualanAntarCabangHead check = PenjualanAntarCabangHeadDAO.get(conPusat, p.getNoPenjualan());
            if(check.getStatusTerima().equals("true")){
                status = "Penjualan tidak dapat dibatal, karena sudah diterima cabang";
            }else if(check.getStatusBatal().equals("true")){
                status = "Penjualan tidak dapat dibatal, karena sudah dibatalkan";
            }else{
                Keuangan k = KeuanganDAO.get(conCabang, "Kasir", "Penjualan Antar Cabang", 
                        "Penjualan Cabang - "+p.getCabangTujuan(), p.getNoPenjualan());
                KeuanganDAO.deleteAll(conCabang, k.getNoKeuangan());

                PenjualanAntarCabangHeadDAO.update(conPusat, p);
                p.setListPenjualanAntarCabangDetail(PenjualanAntarCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualan()));
                for(PenjualanAntarCabangDetail d : p.getListPenjualanAntarCabangDetail()){
                    Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                    if(b==null){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                    }else if(b.getStatus().equals("true")){
                        status = "Kode barcode "+d.getKodeBarcode()+" tidak terjual";
                    }else{
                        b.setStatus("true");
                        BarangDAO.update(conCabang, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();
                        status = updateStokBarangBarcodeBatalKeluar(conCabang, b.getKodeBarang(), b.getKodeBarcode(), 
                                b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    }
                }
            }
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }

    public static String savePemesanan(Connection con, PemesananHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            String nokeuangan = KeuanganDAO.getId(con);
            String noPemesanan = PemesananHeadDAO.getId(con);
            p.setNoPemesanan(noPemesanan);
            PemesananHeadDAO.insert(con, p);
            for(PemesananDetail d : p.getListPemesananDetail()){
                d.setNoPemesanan(noPemesanan);
                PemesananDetailDAO.insert(con, d);
            }
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Terima Pemesanan", 
                noPemesanan, p.getTitipUang(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Hutang Terima Down Payment", "Terima Pemesanan", 
                noPemesanan, p.getTitipUang(), p.getKodeSales());
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPemesanan(Connection con, PemesananHead p, String kodeSales){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            PemesananHead check = PemesananHeadDAO.get(con, p.getNoPemesanan());
            if(check.getStatusBatal().equals("true")){
                status = "Tidak dapat dibatalkan, karena pemesanan sudah pernah dibatal";
            }else if(check.getStatusAmbil().equals("true")){
                status = "Tidak dapat dibatalkan, karena pemesanan sudah diambil";
            }else{
                PemesananHeadDAO.update(con, p);
                if(p.getNoPemesanan().substring(8,14).equals(tglSystem.format(tglBarang.parse(sistem.getTglSystem())))){
                    Keuangan k = KeuanganDAO.get(con, "Kasir", "Hutang Terima Down Payment", "Terima Pemesanan", p.getNoPemesanan());
                    KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                }else{
                    String nokeuangan = KeuanganDAO.getId(con);
                    insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Batal Pemesanan", 
                        p.getNoPemesanan(), -p.getTitipUang(), kodeSales);
                    insertKeuangan(con, nokeuangan, 2, "Kasir", "Hutang Terima Down Payment", "Batal Pemesanan", 
                        p.getNoPemesanan(), -p.getTitipUang(), kodeSales);
                }
            }
            
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveAmbilPemesanan(Connection con, PemesananHead psn, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            String noPenjualan = PenjualanHeadDAO.getId(con);
            
            psn.setStatusAmbil("true");
            psn.setTglAmbil(Function.getSystemDate());
            psn.setSalesAmbil(p.getKodeSales());
            PemesananHeadDAO.update(con, psn);
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Ambil Pemesanan", 
                psn.getNoPemesanan(), -psn.getTitipUang(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Hutang Terima Down Payment", "Ambil Pemesanan", 
                psn.getNoPemesanan(), -psn.getTitipUang(), p.getKodeSales());
            
            double totalNilai = 0;
            p.setNoPenjualan(noPenjualan);
            PenjualanHeadDAO.insert(con, p);
            for(PenjualanDetail d : p.getListPenjualanDetail()){
                d.setNoPenjualan(p.getNoPenjualan());
                PenjualanDetailDAO.insert(con, d);
                
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if(b==null){
                    status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                }else if(b.getStatus().equals("false")){
                    status = "Kode barcode "+d.getKodeBarcode()+" sudah dijual/dihancur";
                }else{
                    b.setStatus("false");
                    BarangDAO.update(con, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    totalNilai = totalNilai + nilai;
                }
            }
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
            }
            String noKeuangan2 = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan2, 1, "Kasir", "Kas", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, noKeuangan2, 2, "Kasir", "Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, noKeuangan2, 3, "Kasir", "HPP Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            insertKeuangan(con, noKeuangan2, 4, "Kasir", cabang.getKodeCabang(), "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            
            if(p.getSisaPembayaran()!=0){
                insertKeuangan(con, noKeuangan2, 5, "Kasir", "Kas", "Orang Kurang", 
                    p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                insertKeuangan(con, noKeuangan2, 6, "Kasir", "Piutang Penjualan", "Orang Kurang", 
                    p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveAmbilPemesananPoin(Connection conPusat, Connection con, PemesananHead psn, PenjualanHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);
            conPusat.setAutoCommit(false);

            String nokeuangan = KeuanganDAO.getId(con);
            String noPenjualan = PenjualanHeadDAO.getId(con);
            
            psn.setStatusAmbil("true");
            psn.setTglAmbil(Function.getSystemDate());
            psn.setSalesAmbil(p.getKodeSales());
            PemesananHeadDAO.update(con, psn);
            insertKeuangan(con, nokeuangan, 1, "Kasir", "Kas", "Ambil Pemesanan", 
                psn.getNoPemesanan(), -psn.getTitipUang(), p.getKodeSales());
            insertKeuangan(con, nokeuangan, 2, "Kasir", "Hutang Terima Down Payment", "Ambil Pemesanan", 
                psn.getNoPemesanan(), -psn.getTitipUang(), p.getKodeSales());
            
            double totalNilai = 0;
            p.setNoPenjualan(noPenjualan);
            PenjualanHeadDAO.insert(con, p);
            for(PenjualanDetail d : p.getListPenjualanDetail()){
                d.setNoPenjualan(p.getNoPenjualan());
                PenjualanDetailDAO.insert(con, d);
                
                Barang b = BarangDAO.get(con, d.getKodeBarcode());
                if(b==null){
                    status = "Kode barcode "+d.getKodeBarcode()+" tidak ditemukan";
                }else if(b.getStatus().equals("false")){
                    status = "Kode barcode "+d.getKodeBarcode()+" sudah dijual/dihancur";
                }else{
                    b.setStatus("false");
                    BarangDAO.update(con, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeKeluar(con, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    totalNilai = totalNilai + nilai;
                }
            }
            String nokeuangan2 = KeuanganDAO.getId(con);
            double pembayaranCash = 0;
            for(PembayaranPenjualan pp : p.getListPembayaran()){
                pp.setNoPenjualan(p.getNoPenjualan());
                pp.setNoPembayaran(PembayaranPenjualanDAO.getId(con));
                PembayaranPenjualanDAO.insert(con, pp);
                
                if(pp.getJenisPembayaran().equals("Cash"))
                    pembayaranCash = pembayaranCash + pp.getJumlahPembayaran();
                
                if(pp.getJenisPembayaran().equals("Reward Poin")){
                    if(p.getKodeMember().equals(pp.getKeterangan())){
                        p.getMember().setPoin(p.getMember().getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    }else if(!p.getKodeMember().equals(pp.getKeterangan())){
                        Member m = MemberDAO.get(conPusat, pp.getKeterangan());
                        m.setPoin(m.getPoin()-(int)(pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                        MemberDAO.update(conPusat, m);
                    }

                    LogMember l = new LogMember();
                    l.setTanggal(Function.getSystemDate());
                    l.setKodeCabang(cabang.getKodeCabang());
                    l.setKodeMember(pp.getKeterangan());
                    l.setKategori("Pembayaran Poin");
                    l.setKeterangan(p.getNoPenjualan());
                    l.setJumlahRp(-pp.getJumlahPembayaran());
                    l.setJumlahPoin((int)(-pp.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    l.setStatus("true");
                    l.setTglBatal("2000-01-01 00:00:00");
                    l.setUserBatal("");
                    LogMemberDAO.insert(conPusat, l);

                    insertKeuanganPoin(conPusat, nokeuangan2, cabang.getKodeCabang(), "Pembayaran Poin", 
                        p.getNoPenjualan(), -pp.getJumlahPembayaran(), p.getKodeSales());
                }
            }
            insertKeuangan(con, nokeuangan2, 1, "Kasir", "Kas", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan2, 2, "Kasir", "Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), p.getGrandtotal(), p.getKodeSales());
            insertKeuangan(con, nokeuangan2, 3, "Kasir", "HPP Penjualan Umum", "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            insertKeuangan(con, nokeuangan2, 4, "Kasir", cabang.getKodeCabang(), "Penjualan Umum", 
                p.getNoPenjualan(), -totalNilai, p.getKodeSales());
            
            if(p.getSisaPembayaran()!=0){
                insertKeuangan(con, nokeuangan2, 5, "Kasir", "Kas", "Orang Kurang", 
                    p.getNoPenjualan(), -p.getSisaPembayaran(), p.getKodeSales());
                insertKeuangan(con, nokeuangan2, 6, "Kasir", "Piutang Penjualan", "Orang Kurang", 
                    p.getNoPenjualan(), p.getSisaPembayaran(), p.getKodeSales());
            }
            if(p.getMember()!=null){
                int getPoin = (int)(pembayaranCash/sistem.getGetPoinPembayaranPenjualan());
                p.getMember().setPoin(p.getMember().getPoin()+getPoin);
                MemberDAO.update(conPusat, p.getMember());
                
                LogMember l = new LogMember();
                l.setTanggal(Function.getSystemDate());
                l.setKodeCabang(cabang.getKodeCabang());
                l.setKodeMember(p.getMember().getKodeMember());
                l.setKategori("Get Poin Pembelian");
                l.setKeterangan(p.getNoPenjualan());
                l.setJumlahRp(pembayaranCash);
                l.setJumlahPoin(getPoin);
                l.setStatus("true");
                l.setTglBatal("2000-01-01 00:00:00");
                l.setUserBatal("");
                LogMemberDAO.insert(conPusat, l);

                insertKeuangan(con, nokeuangan2, 7, "Kasir", "Kas", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                insertKeuangan(con, nokeuangan2, 8, "Kasir", "Beban Poin", "Poin", 
                    p.getNoPenjualan(), -getPoin*sistem.getNilaiPoin(), p.getKodeSales());
                insertKeuanganPoin(conPusat, nokeuangan2, cabang.getKodeCabang(), "Get Poin", 
                    p.getNoPenjualan(), getPoin*sistem.getNilaiPoin(), p.getKodeSales());
            }
            
            if(status.equals("true")){
                con.commit();
                conPusat.commit();
            }else{
                con.rollback();
                conPusat.rollback();
            }
            con.setAutoCommit(true);
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePembelian(Connection con, PembelianHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);

            String noKeuangan = KeuanganDAO.getId(con);
            String noPembelian = PembelianHeadDAO.getId(con);
            
            p.setNoPembelian(noPembelian);
            PembelianHeadDAO.insert(con, p);
            
            List<String> groupBy = new ArrayList<>();
            for(PembelianDetail d : p.getListPembelianDetail()){
                d.setNoPembelian(noPembelian);
                PembelianDetailDAO.insert(con, d);
                
                if(!groupBy.contains(d.getKodeJenis()))
                    groupBy.add(d.getKodeJenis());
            }
            
            for(String j : groupBy){
                int qty = 0;
                double beratKotor = 0;
                double beratPersen = 0;
                double hargaBeli = 0;
                String kodeKategori = "";
                for(PembelianDetail d : p.getListPembelianDetail()){
                    if(j.equals(d.getKodeJenis())){
                        qty = qty + d.getQty();
                        beratKotor = beratKotor + d.getBeratKotor();
                        beratPersen = beratPersen + d.getBeratPersen();
                        hargaBeli = hargaBeli + d.getHargaBeli();
                        kodeKategori = d.getKodeKategori();
                    }
                }
                status = updateStokBarangNonBarcodeMasuk(con, cabang.getKodeCabang()+"-BLN", kodeKategori, j,  
                        qty, beratKotor, beratPersen, hargaBeli, status);
            }
            insertKeuangan(con, noKeuangan, 1, "Kasir", "Kas", "Pembelian Umum", p.getNoPembelian(), 
                    -p.getTotalPembelian(), p.getKodeSales());
            insertKeuangan(con, noKeuangan, 2, "Kasir", cabang.getKodeCabang()+"-BLN", "Pembelian Umum", p.getNoPembelian(), 
                    p.getTotalPembelian(), p.getKodeSales());
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembelian(Connection con, PembelianHead p){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(PembelianHeadDAO.get(con, p.getNoPembelian()).getStatus().equals("false")){
                status = "Pembelian sudah pernah dibatal";
            }else{
                PembelianHeadDAO.update(con, p);
                List<String> groupBy = new ArrayList<>();
                p.setListPembelianDetail(PembelianDetailDAO.getAllByNoPembelian(con, p.getNoPembelian()));
                for(PembelianDetail d : p.getListPembelianDetail()){
                    if(d.getStatusAmbil().equals("true"))
                        status = "Barang pembelian sudah diambil barang";
                    if(!groupBy.contains(d.getKodeJenis()))
                        groupBy.add(d.getKodeJenis());
                }
                for(String j : groupBy){
                    int qty = 0;
                    double beratKotor = 0;
                    double beratPersen = 0;
                    double hargaBeli = 0;
                    String kodeKategori = "";
                    for(PembelianDetail d : p.getListPembelianDetail()){
                        if(j.equals(d.getKodeJenis())){
                            qty = qty + d.getQty();
                            beratKotor = beratKotor + d.getBeratKotor();
                            beratPersen = beratPersen + d.getBeratPersen();
                            hargaBeli = hargaBeli + d.getHargaBeli();
                            kodeKategori = d.getKodeKategori();
                        }
                    }
                    status = updateStokBarangNonBarcodeBatalMasuk(con, cabang.getKodeCabang()+"-BLN", kodeKategori, j,  
                            qty, beratKotor, beratPersen, hargaBeli, status);
                }
                Keuangan k = KeuanganDAO.get(con, "Kasir", cabang.getKodeCabang()+"-BLN", "Pembelian Umum", 
                        p.getNoPembelian());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            }
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePembelianCabang(Connection conPusat, Connection conCabang, PenjualanAntarCabangHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            String noKeuangan = KeuanganDAO.getId(conCabang);
            insertKeuangan(conCabang, noKeuangan, 1, "Kasir", "Kas", "Pembelian Cabang - "+p.getKodeCabang(), 
                    p.getNoPenjualan(), -p.getTotalHarga(), p.getSalesTerima());
            insertKeuangan(conCabang, noKeuangan, 2, "Kasir", cabang.getKodeCabang(), "Pembelian Cabang - "+p.getKodeCabang(), 
                    p.getNoPenjualan(), p.getTotalHarga(), p.getSalesTerima());
            
            PenjualanAntarCabangHeadDAO.update(conPusat, p);
            for(PenjualanAntarCabangDetail d : p.getListPenjualanAntarCabangDetail()){
                Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                if(b==null){
                    b = new Barang();
                    b.setKodeBarang(d.getKodeBarang());
                    b.setKodeBarcode(d.getKodeBarcode());
                    b.setNamaBarang(d.getNamaBarang());
                    b.setKodeKategori(d.getKodeKategori());
                    b.setKodeJenis(d.getKodeJenis());
                    b.setKodeIntern(d.getKodeIntern());
                    b.setKadar(d.getKadar());
                    b.setBerat(d.getBerat());
                    b.setBeratAsli(d.getBeratAsli());
                    b.setBeratPersen(d.getBeratPersen());
                    b.setNilaiPokok(d.getHarga());
                    b.setInputDate(d.getInputDate());
                    b.setInputBy(d.getInputBy());
                    b.setAsalBarang(p.getKodeCabang());
                    b.setStatus(d.getStatus());
                    BarangDAO.insert(conCabang, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeMasuk(conCabang, b.getKodeBarang(), b.getKodeBarcode(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                }else{
                    String kodeBarcodeBaru = TpBarangDAO.getKodeBarcode(conPusat);
                    String kodeBarangBaru = TpBarangDAO.getKodeBarang(conPusat, d.getKodeJenis());
                    
                    TpBarang tpBarang = new TpBarang();
                    tpBarang.setKodeJenis(d.getKodeJenis());
                    tpBarang.setKodeBarang(kodeBarangBaru);
                    tpBarang.setKodeBarcode(kodeBarcodeBaru);
                    TpBarangDAO.update(conPusat, tpBarang);
                    
                    b = new Barang();
                    b.setKodeBarang(kodeBarangBaru);
                    b.setKodeBarcode(kodeBarcodeBaru);
                    b.setNamaBarang(d.getNamaBarang());
                    b.setKodeKategori(d.getKodeKategori());
                    b.setKodeJenis(d.getKodeJenis());
                    b.setKodeIntern(d.getKodeIntern());
                    b.setKadar(d.getKadar());
                    b.setBerat(d.getBerat());
                    b.setBeratAsli(d.getBeratAsli());
                    b.setBeratPersen(d.getBeratPersen());
                    b.setNilaiPokok(d.getHarga());
                    b.setInputDate(d.getInputDate());
                    b.setInputBy(d.getInputBy());
                    b.setAsalBarang(p.getKodeCabang());
                    b.setStatus(d.getStatus());
                    BarangDAO.insert(conCabang, b);
                    
                    int qty = 1;
                    double berat = b.getBerat();
                    double beratPersen = b.getBeratPersen();
                    double nilai = b.getNilaiPokok();
                    status = updateStokBarangBarcodeMasuk(conCabang, kodeBarangBaru, kodeBarcodeBaru, 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);
                    
                    d.setKodeBarcodeBaru(kodeBarcodeBaru);
                    PenjualanAntarCabangDetailDAO.update(conPusat, d);
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembelianCabang(Connection conPusat, Connection conCabang, PenjualanAntarCabangHead p){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            if(PenjualanAntarCabangHeadDAO.get(conPusat, p.getNoPenjualan()).getStatusTerima().equals("false")){
                status = "Pembelian cabang sudah pernah di batal";
            }else{
                Keuangan k = KeuanganDAO.get(conCabang, "Kasir", cabang.getKodeCabang(), 
                        "Pembelian Cabang - "+p.getKodeCabang(), p.getNoPenjualan());
                KeuanganDAO.deleteAll(conCabang, k.getNoKeuangan());

                PenjualanAntarCabangHeadDAO.update(conPusat, p);
                p.setListPenjualanAntarCabangDetail(PenjualanAntarCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualan()));
                for(PenjualanAntarCabangDetail d : p.getListPenjualanAntarCabangDetail()){
                    Barang b = BarangDAO.get(conCabang, d.getKodeBarcodeBaru());
                    if(b==null){
                        status = "Barang dengan kode barcode "+ d.getKodeBarcodeBaru()+" tidak ditemukan";
                    }else if(b.getStatus().equals("false")){
                        status = "Barang dengan kode barcode "+ d.getKodeBarcodeBaru()+" tidak tersedia";
                    }else{
                        b.setStatus("false");
                        BarangDAO.update(conCabang, b);

                        int qty = 1;
                        double berat = b.getBerat();
                        double beratPersen = b.getBeratPersen();
                        double nilai = b.getNilaiPokok();

                        status = updateStokBarangBarcodeBatalMasuk(conCabang, b.getKodeBarang(), d.getKodeBarcodeBaru(), 
                            b.getKodeKategori(), b.getKodeJenis(), qty, berat, beratPersen, nilai, status);

                        d.setKodeBarcodeBaru(d.getKodeBarcode());
                        PenjualanAntarCabangDetailDAO.update(conPusat, d);
                    }
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaHutang(Connection con, HutangHead h){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            String noKeuangan = KeuanganDAO.getId(con);
            String noHutang = HutangHeadDAO.getId(con);

            h.setNoHutang(noHutang);
            HutangHeadDAO.insert(con, h);
            for(HutangDetail d : h.getListHutangDetail()){
                d.setNoHutang(noHutang);
                HutangDetailDAO.insert(con, d);
            }
            StokHutang s = StokHutangDAO.get(con, sistem.getTglSystem(), sistem.getTglSystem());
            if(s==null){
                s = new StokHutang();
                s.setTanggal(sistem.getTglSystem());
                s.setTglHutang(sistem.getTglSystem());
                s.setStokAwal(0);
                s.setBeratAwal(0);
                s.setJumlahAwal(0);
                s.setStokMasuk(1);
                s.setBeratMasuk(h.getTotalBerat());
                s.setJumlahMasuk(h.getTotalHutang());
                s.setStokKeluar(0);
                s.setBeratKeluar(0);
                s.setJumlahKeluar(0);
                s.setStokAkhir(1);
                s.setBeratAkhir(h.getTotalBerat());
                s.setJumlahAkhir(h.getTotalHutang());
                StokHutangDAO.insert(con, s);
            }else{
                s.setStokMasuk(s.getStokMasuk()+1);
                s.setBeratMasuk(s.getBeratMasuk()+h.getTotalBerat());
                s.setJumlahMasuk(s.getJumlahMasuk()+h.getTotalHutang());
                s.setStokAkhir(s.getStokAkhir()+1);
                s.setBeratAkhir(s.getBeratAkhir()+h.getTotalBerat());
                s.setJumlahAkhir(s.getJumlahAkhir()+h.getTotalHutang());
                StokHutangDAO.update(con, s);
            }
            insertKeuangan(con, noKeuangan, 1, "RR", "Kas", "Terima Hutang", noHutang, 
                    -h.getTotalHutang(), h.getKodeSales());
            insertKeuangan(con, noKeuangan, 2, "RR", "Piutang", "Terima Hutang", noHutang, 
                    h.getTotalHutang(), h.getKodeSales());

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalTerimaHutang(Connection con, HutangHead h){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(HutangHeadDAO.get(con, h.getNoHutang()).getStatusBatal().equals("true")){
                status = "Terima hutang sudah pernah dibatal";
            }else{
                HutangHeadDAO.update(con, h);

                StokHutang stokHutang = StokHutangDAO.get(con, sistem.getTglSystem(), sistem.getTglSystem());
                stokHutang.setStokMasuk(stokHutang.getStokMasuk()-1);
                stokHutang.setBeratMasuk(stokHutang.getBeratMasuk()-h.getTotalBerat());
                stokHutang.setJumlahMasuk(stokHutang.getJumlahMasuk()-h.getTotalHutang());
                stokHutang.setStokAkhir(stokHutang.getStokAkhir()-1);
                stokHutang.setBeratAkhir(stokHutang.getBeratAkhir()-h.getTotalBerat());
                stokHutang.setJumlahAkhir(stokHutang.getJumlahAkhir()-h.getTotalHutang());
                StokHutangDAO.update(con, stokHutang);

                Keuangan k = KeuanganDAO.get(con, "RR", "Piutang", "Terima Hutang", h.getNoHutang());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            }
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String savePelunasanHutang(Connection con, HutangHead hutangLunas, HutangHead hutangBaru){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(HutangHeadDAO.get(con, hutangLunas.getNoHutang()).getStatusLunas().equals("true")){
                status = "Hutang sudah pernah dilunasi";
            }else{
                if(hutangBaru==null){//pelunasan hutang
                    HutangHeadDAO.update(con, hutangLunas);

                    String tglHutang = tglBarang.format(tglSystem.parse(hutangLunas.getNoHutang().substring(7,13)));
                    StokHutang stokHutangLama = StokHutangDAO.get(con, sistem.getTglSystem(), tglHutang);
                    stokHutangLama.setStokKeluar(stokHutangLama.getStokKeluar()+1);
                    stokHutangLama.setBeratKeluar(stokHutangLama.getBeratKeluar()+hutangLunas.getTotalBerat());
                    stokHutangLama.setJumlahKeluar(stokHutangLama.getJumlahKeluar()+hutangLunas.getTotalHutang());
                    stokHutangLama.setStokAkhir(stokHutangLama.getStokAkhir()-1);
                    stokHutangLama.setBeratAkhir(stokHutangLama.getBeratAkhir()-hutangLunas.getTotalBerat());
                    stokHutangLama.setJumlahAkhir(stokHutangLama.getJumlahAkhir()-hutangLunas.getTotalHutang());
                    StokHutangDAO.update(con, stokHutangLama);

                    String noKeuangan = KeuanganDAO.getId(con);
                    insertKeuangan(con, noKeuangan, 1, "RR", "Kas", "Hutang Lunas", hutangLunas.getNoHutang(), 
                            hutangLunas.getTotalHutang(), hutangLunas.getSalesLunas());
                    insertKeuangan(con, noKeuangan, 2, "RR", "Piutang", "Hutang Lunas", hutangLunas.getNoHutang(), 
                            -hutangLunas.getTotalHutang(), hutangLunas.getSalesLunas());

                    insertKeuangan(con, noKeuangan, 3, "RR", "Kas", "Hutang Bunga", hutangLunas.getNoHutang(), 
                            hutangLunas.getBungaRp(), hutangLunas.getSalesLunas());
                    insertKeuangan(con, noKeuangan, 4, "RR", "Pendapatan Bunga", "Hutang Bunga", hutangLunas.getNoHutang(), 
                            hutangLunas.getBungaRp(), hutangLunas.getSalesLunas());
                }else{//cicil hutang/bayar bunga
                    HutangHeadDAO.update(con, hutangLunas);

                    String noHutangBaru = HutangHeadDAO.getId(con);
                    hutangBaru.setNoHutang(noHutangBaru);
                    HutangHeadDAO.insert(con, hutangBaru);
                    for(HutangDetail d : hutangBaru.getListHutangDetail()){
                        d.setNoHutang(noHutangBaru);
                        HutangDetailDAO.insert(con, d);
                    }
                    String tglHutang = tglBarang.format(tglSystem.parse(hutangLunas.getNoHutang().substring(7,13)));
                    if(!sistem.getTglSystem().equals(tglHutang)){
                        StokHutang stokHutangLama = StokHutangDAO.get(con, sistem.getTglSystem(), tglHutang);
                        stokHutangLama.setStokKeluar(stokHutangLama.getStokKeluar()+1);
                        stokHutangLama.setBeratKeluar(stokHutangLama.getBeratKeluar()+hutangLunas.getTotalBerat());
                        stokHutangLama.setJumlahKeluar(stokHutangLama.getJumlahKeluar()+hutangLunas.getTotalHutang());
                        stokHutangLama.setStokAkhir(stokHutangLama.getStokAkhir()-1);
                        stokHutangLama.setBeratAkhir(stokHutangLama.getBeratAkhir()-hutangLunas.getTotalBerat());
                        stokHutangLama.setJumlahAkhir(stokHutangLama.getJumlahAkhir()-hutangLunas.getTotalHutang());
                        StokHutangDAO.update(con, stokHutangLama);

                        StokHutang stokHutangBaru = StokHutangDAO.get(con, sistem.getTglSystem(), sistem.getTglSystem());
                        if(stokHutangBaru==null){
                            stokHutangBaru = new StokHutang();
                            stokHutangBaru.setTanggal(sistem.getTglSystem());
                            stokHutangBaru.setTglHutang(sistem.getTglSystem());
                            stokHutangBaru.setStokAwal(0);
                            stokHutangBaru.setBeratAwal(0);
                            stokHutangBaru.setJumlahAwal(0);
                            stokHutangBaru.setStokMasuk(1);
                            stokHutangBaru.setBeratMasuk(hutangBaru.getTotalBerat());
                            stokHutangBaru.setJumlahMasuk(hutangBaru.getTotalHutang());
                            stokHutangBaru.setStokKeluar(0);
                            stokHutangBaru.setBeratKeluar(0);
                            stokHutangBaru.setJumlahKeluar(0);
                            stokHutangBaru.setStokAkhir(1);
                            stokHutangBaru.setBeratAkhir(hutangBaru.getTotalBerat());
                            stokHutangBaru.setJumlahAkhir(hutangBaru.getTotalHutang());
                            StokHutangDAO.insert(con, stokHutangBaru);
                        }else{
                            stokHutangBaru.setStokMasuk(stokHutangBaru.getStokMasuk()+1);
                            stokHutangBaru.setBeratMasuk(stokHutangBaru.getBeratMasuk()+hutangBaru.getTotalBerat());
                            stokHutangBaru.setJumlahMasuk(stokHutangBaru.getJumlahMasuk()+hutangBaru.getTotalHutang());
                            stokHutangBaru.setStokAkhir(stokHutangBaru.getStokAkhir()+1);
                            stokHutangBaru.setBeratAkhir(stokHutangBaru.getBeratAkhir()+hutangBaru.getTotalBerat());
                            stokHutangBaru.setJumlahAkhir(stokHutangBaru.getJumlahAkhir()+hutangBaru.getTotalHutang());
                            StokHutangDAO.update(con, stokHutangBaru);
                        }
                    }else{
                        StokHutang s = StokHutangDAO.get(con, sistem.getTglSystem(), sistem.getTglSystem());
                        s.setStokMasuk(s.getStokMasuk()+1);
                        s.setBeratMasuk(s.getBeratMasuk()+hutangBaru.getTotalBerat());
                        s.setJumlahMasuk(s.getJumlahMasuk()+hutangBaru.getTotalHutang());
                        s.setStokKeluar(s.getStokKeluar()+1);
                        s.setBeratKeluar(s.getBeratKeluar()+hutangLunas.getTotalBerat());
                        s.setJumlahKeluar(s.getJumlahKeluar()+hutangLunas.getTotalHutang());
                        s.setStokAkhir(s.getStokAkhir()-1+1);
                        s.setBeratAkhir(s.getBeratAkhir()-hutangLunas.getTotalBerat()+hutangBaru.getTotalBerat());
                        s.setJumlahAkhir(s.getJumlahAkhir()-hutangLunas.getTotalHutang()+hutangBaru.getTotalHutang());
                        StokHutangDAO.update(con, s);
                    }
                    String noKeuangan1 = KeuanganDAO.getId(con);
                    insertKeuangan(con, noKeuangan1, 1, "RR", "Kas", "Hutang Lunas", hutangLunas.getNoHutang(), 
                            hutangLunas.getTotalHutang(), hutangLunas.getSalesLunas());
                    insertKeuangan(con, noKeuangan1, 2, "RR", "Piutang", "Hutang Lunas", hutangLunas.getNoHutang(), 
                            -hutangLunas.getTotalHutang(), hutangLunas.getSalesLunas());

                    insertKeuangan(con, noKeuangan1, 3, "RR", "Kas", "Hutang Bunga", hutangLunas.getNoHutang(), 
                            hutangLunas.getBungaRp(), hutangLunas.getSalesLunas());
                    insertKeuangan(con, noKeuangan1, 4, "RR", "Pendapatan Bunga", "Hutang Bunga", hutangLunas.getNoHutang(), 
                            hutangLunas.getBungaRp(), hutangLunas.getSalesLunas());

                    String noKeuangan2 = KeuanganDAO.getId(con);
                    insertKeuangan(con, noKeuangan2, 1, "RR", "Kas", "Terima Hutang", noHutangBaru, 
                            -hutangBaru.getTotalHutang(), hutangBaru.getKodeSales());
                    insertKeuangan(con, noKeuangan2, 2, "RR", "Piutang", "Terima Hutang", noHutangBaru, 
                            hutangBaru.getTotalHutang(), hutangBaru.getKodeSales());

                }
            }
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(Exception ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPelunasanHutang(Connection con, HutangHead h, String user){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(HutangHeadDAO.get(con, h.getNoHutang()).getStatusLunas().equals("false")){
                status = "Pelunasan hutang sudah pernah dibatal";
            }else{
                BatalLunas b = new BatalLunas();
                b.setNoBatalLunas(BatalLunasDAO.getId(con));
                b.setTglBatalLunas(Function.getSystemDate());
                b.setNoHutang(h.getNoHutang());
                b.setTotalBerat(h.getTotalBerat());
                b.setTotalPinjaman(h.getTotalHutang());
                b.setUserBatal(user);
                BatalLunasDAO.insert(con, b);

                h.setBungaRp(h.getBungaKomp());
                h.setStatusLunas("false");
                h.setTglLunas("2000-01-01");
                h.setSalesLunas("");
                HutangHeadDAO.update(con, h);

                String tglHutang = tglBarang.format(tglSystem.parse(h.getNoHutang().substring(7,13)));
                StokHutang s = StokHutangDAO.get(con, sistem.getTglSystem(), tglHutang);
                s.setStokKeluar(s.getStokKeluar()-1);
                s.setBeratKeluar(s.getBeratKeluar()-h.getTotalBerat());
                s.setJumlahKeluar(s.getJumlahKeluar()-h.getTotalHutang());
                s.setStokAkhir(s.getStokAkhir()+1);
                s.setBeratAkhir(s.getBeratAkhir()+h.getTotalBerat());
                s.setJumlahAkhir(s.getJumlahAkhir()+h.getTotalHutang());
                StokHutangDAO.update(con, s);

                Keuangan k = KeuanganDAO.get(con, "RR", "Piutang", "Hutang Lunas", h.getNoHutang());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaServis(Connection con, Servis s){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            s.setNoServis(ServisDAO.getId(con));
            ServisDAO.insert(con, s);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalTerimaServis(Connection con, Servis s){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(ServisDAO.get(con, s.getNoServis()).getStatusBatal().equals("true")){
                status = "Terima servis sudah pernah dibatal";
            }else{
                ServisDAO.update(con, s);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveAmbilServis(Connection con, Servis s){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            ServisDAO.update(con, s);
            
            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, 1, "Kasir", "Kas", "Servis", s.getNoServis(), 
                    s.getJumlahPembayaran(), s.getSalesAmbil());
            insertKeuangan(con, noKeuangan, 2, "Kasir", "Pendapatan Servis", "Servis", s.getNoServis(), 
                    s.getJumlahPembayaran(), s.getSalesAmbil());
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveAmbilServisPoin(Connection conPusat, Connection con, Servis s){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            ServisDAO.update(con, s);
            
            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, 1, "Kasir", "Kas", "Servis", s.getNoServis(), 
                    s.getJumlahPembayaran(), s.getSalesAmbil());
            insertKeuangan(con, noKeuangan, 2, "Kasir", "Pendapatan Servis", "Servis", s.getNoServis(), 
                    s.getJumlahPembayaran(), s.getSalesAmbil());
            
            if(s.getJenisPembayaran().equals("Reward Poin")){
                Member m = MemberDAO.get(conPusat, s.getKeteranganPembayaran());
                m.setPoin(m.getPoin()-(int)(s.getJumlahPembayaran()/sistem.getNilaiPoin()));
                if(m.getPoin()<0)
                    status = "Poin yang dimiliki member "+m.getNama()+" tidak mencukupi";
                MemberDAO.update(conPusat, m);

                LogMember l = new LogMember();
                l.setTanggal(Function.getSystemDate());
                l.setKodeCabang(cabang.getKodeCabang());
                l.setKodeMember(s.getKeteranganPembayaran());
                l.setKategori("Pembayaran Poin");
                l.setKeterangan(s.getNoServis());
                l.setJumlahRp(-s.getJumlahPembayaran());
                l.setJumlahPoin((int)(-s.getJumlahPembayaran()/sistem.getNilaiPoin()));
                l.setKodeSales(s.getSalesAmbil());
                l.setStatus("true");
                l.setTglBatal("2000-01-01 00:00:00");
                l.setUserBatal("");
                LogMemberDAO.insert(conPusat, l);

                insertKeuanganPoin(conPusat, noKeuangan, cabang.getKodeCabang(), "Pembayaran Poin", 
                    s.getNoServis(), -s.getJumlahPembayaran(), s.getSalesAmbil());
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalAmbilServis(Connection con, Servis s){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            Servis check = ServisDAO.get(con, s.getNoServis());
            if(check.getStatusAmbil().equals("false")){
                status = "Ambil servis sudah pernah dibatal";
            }else if(check.getStatusBatal().equals("true")){
                status = "Ambil servis tidak dapat dibatal, karena servis sudah pernah dibatalkan";
            }else{
                Keuangan k = KeuanganDAO.get(con, "Kasir", "Pendapatan Servis", "Servis", s.getNoServis());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());

                s.setJumlahPembayaran(0);
                s.setJenisPembayaran("");
                s.setKeteranganPembayaran("");
                s.setStatusAmbil("false");
                s.setTglAmbil("2000-01-01 00:00:00");
                s.setSalesAmbil("");
                ServisDAO.update(con, s);
            }
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalAmbilServisPoin(Connection conPusat, Connection con, Servis s, String user){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            Servis check = ServisDAO.get(con, s.getNoServis());
            if(check.getStatusAmbil().equals("false")){
                status = "Ambil servis sudah pernah dibatal";
            }else if(check.getStatusBatal().equals("true")){
                status = "Ambil servis tidak dapat dibatal, karena servis sudah pernah dibatalkan";
            }else{
                if(s.getJenisPembayaran().equals("Reward Poin")){
                    Member m = MemberDAO.get(conPusat, s.getKeteranganPembayaran());
                    m.setPoin(m.getPoin()+(int)(s.getJumlahPembayaran()/sistem.getNilaiPoin()));
                    MemberDAO.update(conPusat, m);

                    LogMember l = LogMemberDAO.get(conPusat, cabang.getKodeCabang(), s.getKeteranganPembayaran(), "Pembayaran Poin", s.getNoServis());
                    l.setStatus("false");
                    l.setTglBatal(Function.getSystemDate());
                    l.setUserBatal(user);
                    LogMemberDAO.update(conPusat, l);
                }

                Keuangan k = KeuanganDAO.get(con, "Kasir", "Pendapatan Servis", "Servis", s.getNoServis());
                KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                KeuanganPoinDAO.deleteAll(conPusat, k.getNoKeuangan());

                s.setJumlahPembayaran(0);
                s.setJenisPembayaran("");
                s.setKeteranganPembayaran("");
                s.setStatusAmbil("false");
                s.setTglAmbil("2000-01-01 00:00:00");
                s.setSalesAmbil("");
                ServisDAO.update(con, s);
            }
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTransaksiKas(Connection con, String tipeKasir, String kategori, String keterangan, double jumlahRp, String kodeSales){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            KategoriTransaksi k = KategoriTransaksiDAO.get(con, kategori);
            if(k.getKategoriTransaksi().matches(".*Beban.*"))
                jumlahRp = jumlahRp * -1;
            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, 1, tipeKasir, "Kas", kategori, keterangan, 
                    jumlahRp, kodeSales);
            insertKeuangan(con, noKeuangan, 2, tipeKasir, k.getKategoriTransaksi(), kategori, keterangan, 
                    jumlahRp, kodeSales);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveRevisiKas(Connection con, Keuangan kd, String kategori, String keterangan){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            KeuanganDAO.deleteAll(con, kd.getNoKeuangan());
            
            double jumlahRp = Math.abs(kd.getJumlahRp());
            KategoriTransaksi kt = KategoriTransaksiDAO.get(con, kategori);
            if(kt.getKategoriTransaksi().matches(".*Beban.*"))
                jumlahRp = jumlahRp * -1;
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, kd.getTglKeuangan().substring(0,10));
            Keuangan k = new Keuangan();
            k.setNoKeuangan(noKeuangan);
            k.setNoUrut(1);
            k.setTglKeuangan(Function.getSystemDate());
            k.setTipeKasir(kd.getTipeKasir());
            k.setTipeKeuangan("Kas");
            k.setKategori(kategori);
            k.setKeterangan(keterangan);
            k.setJumlahRp(jumlahRp);
            k.setKodeSales(kd.getKodeSales());
            KeuanganDAO.insert(con, k);
        
            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(noKeuangan);
            k2.setNoUrut(2);
            k2.setTglKeuangan(Function.getSystemDate());
            k2.setTipeKasir(kd.getTipeKasir());
            k2.setTipeKeuangan(kt.getKategoriTransaksi());
            k2.setKategori(kategori);
            k2.setKeterangan(keterangan);
            k2.setJumlahRp(jumlahRp);
            k2.setKodeSales(kd.getKodeSales());
            KeuanganDAO.insert(con, k2);
        
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalTransaksiKas(Connection con, Keuangan k){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            KeuanganDAO.deleteAll(con, k.getNoKeuangan());
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveSetorUangKas(Connection conPusat, Connection con, SetoranCabang s){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                SetoranCabangDAO.insert(conPusat, s);
                String noKeuangan = KeuanganDAO.getId(con);
                insertKeuangan(con, noKeuangan, 1, s.getTipeKasir(), "Kas", 
                        "Setor Uang Kas", s.getNoSetor(), -s.getJumlahRp(), s.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuangan, cabang.getKodeCabang(), s.getTipeKasir(), "Bank-"+cabang.getKodeCabang(), 
                        "Setor Uang Kas", s.getNoSetor(), s.getJumlahRp(), s.getKodeUser());
            }
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalSetorUangKas(Connection conPusat, Connection con, Keuangan k, String user){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                SetoranCabang s = SetoranCabangDAO.get(conPusat, k.getKeterangan());
                if(s==null)
                    status = "Setor uang kas tidak ditemukan";
                else if(s.getStatusBatal().equals("true"))
                    status = "Tidak dapat dibatal, karena setor uang kas sudah dibatal";
                else if(s.getStatusTerima().equals("true"))
                    status = "Tidak dapat dibatal, karena setor uang kas sudah diterima";
                else{
                    s.setStatusBatal("true");
                    s.setTglBatal(Function.getSystemDate());
                    s.setUserBatal(user);
                    SetoranCabangDAO.update(conPusat, s);

                    KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                    KeuanganCabangDAO.deleteAll(conPusat, k.getNoKeuangan());
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaUangBank(Connection conPusat, Connection con, TambahUangCabang tu){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                if(TambahUangCabangDAO.get(conPusat, tu.getNoTambahUang()).getStatusTerima().equals("true")){
                    status = "Tambah uang cabang sudah pernah diterima";
                }else{
                    TambahUangCabangDAO.update(conPusat, tu);
                    String noKeuangan = KeuanganDAO.getId(con);
                    insertKeuangan(con, noKeuangan, 1, tu.getTipeKasir(), "Kas", 
                            "Terima Uang Bank", tu.getNoTambahUang(), tu.getJumlahRp(), tu.getUserTerima());
                    insertKeuanganPusat(conPusat, noKeuangan, cabang.getKodeCabang(), tu.getTipeKasir(), "Bank-"+cabang.getKodeCabang(), 
                            "Terima Uang Bank", tu.getNoTambahUang(), -tu.getJumlahRp(), tu.getUserTerima());
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalTerimaUangBank(Connection conPusat, Connection con, Keuangan k, String user){
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            con.setAutoCommit(false);
            
            SistemPusat sistemPusat = SistemPusatDAO.get(conPusat);
            if(!sistemPusat.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem berbeda dengan tanggal sistem pusat";
            else{
                TambahUangCabang tu = TambahUangCabangDAO.get(conPusat, k.getKeterangan());
                if(tu==null)
                    status = "Terima uang bank tidak ditemukan";
                else if(tu.getStatusBatal().equals("true"))
                    status = "Tidak dapat dibatal, karena terima uang bank sudah dibatal";
                else if(tu.getStatusTerima().equals("false"))
                    status = "Tidak dapat dibatal, karena terima uang bank belum diterima";
                else{
                    tu.setStatusTerima("false");
                    tu.setTglBatal(Function.getSystemDate());
                    tu.setUserBatal(user);
                    TambahUangCabangDAO.update(conPusat, tu);

                    KeuanganDAO.deleteAll(con, k.getNoKeuangan());
                    KeuanganCabangDAO.deleteAll(conPusat, k.getNoKeuangan());
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
                con.commit();
            }else{
                conPusat.rollback();
                con.rollback();
            }
            conPusat.setAutoCommit(true);
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
}
