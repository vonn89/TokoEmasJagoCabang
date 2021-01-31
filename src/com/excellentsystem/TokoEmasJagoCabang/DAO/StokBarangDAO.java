/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokBarangDAO {
    public static List<StokBarang> getAllByDateAndGudangAndJenisAndBarcode(
            Connection con, String tglMulai, String tglAkhir, String gudang, String jenis, String barcode)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal between '"+tglMulai+"' and '"+tglAkhir+"' ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        if(!jenis.equals("%"))
            sql = sql + " and kode_jenis = '"+jenis+"' ";
        if(!barcode.equals("%"))
            sql = sql + " and kode_barcode = '"+barcode+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<StokBarang> allBarang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));
            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));
            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
            allBarang.add(s);
        }
        return allBarang;
    }
    public static List<StokBarang> getAllByTanggalAndGudangAndKategoriAndJenis(
            Connection con, String tanggal, String gudang, String kategori, String jenis)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal = '"+tanggal+"'  ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kategori+"' ";
        if(!jenis.equals("%"))
            sql = sql + " and kode_jenis = '"+jenis+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<StokBarang> allBarang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));
            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));
            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
            allBarang.add(s);
        }
        return allBarang;
    }
    public static StokBarang getBarcode(Connection con, String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal = ? and kode_barcode = ? ");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        StokBarang s = null;
        if(rs.next()){
            s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));
            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));
            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
        }
        return s;
    }
    public static StokBarang getNonBarcode(Connection con, String kodeGudang, String kodeJenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal = ? and kode_gudang = ? and kode_jenis = ?");
        ps.setString(1, sistem.getTglSystem());
        ps.setString(2, kodeGudang);
        ps.setString(3, kodeJenis);
        ResultSet rs = ps.executeQuery();
        StokBarang s = null;
        if(rs.next()){
            s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));
            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));
            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
        }
        return s;
    }
    public static void insertStokAwal(Connection con, String date, String nextDate)throws Exception{
        con.prepareStatement("insert into tt_stok_barang "
            + " select '"+nextDate+"',kode_barang,kode_barcode,kode_cabang,kode_gudang,kode_kategori,kode_jenis,"
            + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir,"
            + " 0,0,0,0,"
            + " 0,0,0,0,"
            + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir "
            + " from tt_stok_barang where tanggal = '"+date+"' and stok_akhir!=0 ").executeUpdate();
    }
    public static void insert(Connection con,StokBarang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_barang values("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, stok.getTanggal());
        ps.setString(2, stok.getKodeBarang());
        ps.setString(3, stok.getKodeBarcode());
        ps.setString(4, stok.getKodeCabang());
        ps.setString(5, stok.getKodeGudang());
        ps.setString(6, stok.getKodeKategori());
        ps.setString(7, stok.getKodeJenis());
        ps.setInt(8, stok.getStokAwal());
        ps.setDouble(9, stok.getBeratAwal());
        ps.setDouble(10, stok.getBeratPersenAwal());
        ps.setDouble(11, stok.getNilaiAwal());
        ps.setInt(12, stok.getStokMasuk());
        ps.setDouble(13, stok.getBeratMasuk());
        ps.setDouble(14, stok.getBeratPersenMasuk());
        ps.setDouble(15, stok.getNilaiMasuk());
        ps.setInt(16, stok.getStokKeluar());
        ps.setDouble(17, stok.getBeratKeluar());
        ps.setDouble(18, stok.getBeratPersenKeluar());
        ps.setDouble(19, stok.getNilaiKeluar());
        ps.setInt(20, stok.getStokAkhir());
        ps.setDouble(21, stok.getBeratAkhir());
        ps.setDouble(22, stok.getBeratPersenAkhir());
        ps.setDouble(23, stok.getNilaiAkhir());
        ps.executeUpdate();      
    }
    public static void update(Connection con,StokBarang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang set "
            + "stok_awal=?, berat_awal=?, berat_persen_awal=?, nilai_awal=?, "
            + "stok_masuk=?, berat_masuk=?, berat_persen_masuk=?, nilai_masuk=?, "
            + "stok_keluar=?, berat_keluar=?, berat_persen_keluar=?, nilai_keluar=?, "
            + "stok_akhir=?, berat_akhir=?, berat_persen_akhir=?, nilai_akhir=? "
            + "where tanggal=? and kode_barang=? and kode_barcode=? and "
            + "kode_cabang=? and kode_gudang=? and kode_kategori=? and kode_jenis=? ");
        ps.setInt(1, stok.getStokAwal());
        ps.setDouble(2, stok.getBeratAwal());
        ps.setDouble(3, stok.getBeratPersenAwal());
        ps.setDouble(4, stok.getNilaiAwal());
        ps.setInt(5, stok.getStokMasuk());
        ps.setDouble(6, stok.getBeratMasuk());
        ps.setDouble(7, stok.getBeratPersenMasuk());
        ps.setDouble(8, stok.getNilaiMasuk());
        ps.setInt(9, stok.getStokKeluar());
        ps.setDouble(10, stok.getBeratKeluar());
        ps.setDouble(11, stok.getBeratPersenKeluar());
        ps.setDouble(12, stok.getNilaiKeluar());
        ps.setInt(13, stok.getStokAkhir());
        ps.setDouble(14, stok.getBeratAkhir());
        ps.setDouble(15, stok.getBeratPersenAkhir());
        ps.setDouble(16, stok.getNilaiAkhir());
        ps.setString(17, stok.getTanggal());
        ps.setString(18, stok.getKodeBarang());
        ps.setString(19, stok.getKodeBarcode());
        ps.setString(20, stok.getKodeCabang());
        ps.setString(21, stok.getKodeGudang());
        ps.setString(22, stok.getKodeKategori());
        ps.setString(23, stok.getKodeJenis());
        ps.executeUpdate();
    }
}
