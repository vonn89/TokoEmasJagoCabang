/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class BarangDAO {
    public static List<Barang> getAllByKategoriAndJenisAndStatus(
            Connection con, String kategori, String jenis, String status)throws Exception{
        String sql = "select * from tm_barang where kode_barcode !='' ";
        if(!kategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kategori+"' ";
        if(!jenis.equals("%"))
            sql = sql + " and kode_jenis = '"+jenis+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Barang> allBarang = new ArrayList<>();
        while(rs.next()){
            Barang barang = new Barang();
            barang.setKodeBarang(rs.getString(1));
            barang.setKodeBarcode(rs.getString(2));
            barang.setNamaBarang(rs.getString(3));
            barang.setKodeKategori(rs.getString(4));
            barang.setKodeJenis(rs.getString(5));
            barang.setKodeIntern(rs.getString(6));
            barang.setKadar(rs.getString(7));
            barang.setBerat(rs.getDouble(8));
            barang.setBeratAsli(rs.getDouble(9));
            barang.setBeratPersen(rs.getDouble(10));
            barang.setNilaiPokok(rs.getDouble(11));
            barang.setInputDate(rs.getString(12));
            barang.setInputBy(rs.getString(13));
            barang.setAsalBarang(rs.getString(14));
            barang.setStatus(rs.getString(15));
            allBarang.add(barang);
        }
        return allBarang;
    }
    public static Barang get(Connection con,String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_barang where kode_barcode = ?");
        ps.setString(1, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        Barang barang = null;
        if(rs.next()){
            barang = new Barang();
            barang.setKodeBarang(rs.getString(1));
            barang.setKodeBarcode(rs.getString(2));
            barang.setNamaBarang(rs.getString(3));
            barang.setKodeKategori(rs.getString(4));
            barang.setKodeJenis(rs.getString(5));
            barang.setKodeIntern(rs.getString(6));
            barang.setKadar(rs.getString(7));
            barang.setBerat(rs.getDouble(8));
            barang.setBeratAsli(rs.getDouble(9));
            barang.setBeratPersen(rs.getDouble(10));
            barang.setNilaiPokok(rs.getDouble(11));
            barang.setInputDate(rs.getString(12));
            barang.setInputBy(rs.getString(13));
            barang.setAsalBarang(rs.getString(14));
            barang.setStatus(rs.getString(15));
        }
        return barang;
    }
    public static void insert(Connection con,Barang barang) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_barang values("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, barang.getKodeBarang());
        ps.setString(2, barang.getKodeBarcode());
        ps.setString(3, barang.getNamaBarang());
        ps.setString(4, barang.getKodeKategori());
        ps.setString(5, barang.getKodeJenis());
        ps.setString(6, barang.getKodeIntern());
        ps.setString(7, barang.getKadar());
        ps.setDouble(8, barang.getBerat());
        ps.setDouble(9, barang.getBeratAsli());
        ps.setDouble(10, barang.getBeratPersen());
        ps.setDouble(11, barang.getNilaiPokok());
        ps.setString(12, barang.getInputDate());
        ps.setString(13, barang.getInputBy());
        ps.setString(14, barang.getAsalBarang());
        ps.setString(15, barang.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con,Barang barang) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_barang set "
                + " nama_barang=?, kode_kategori=?, kode_jenis=?,"
                + " kode_intern=?, kadar=?, berat=?, berat_asli=?, berat_persen=?, nilai_pokok=?,"
                + " input_date=?, input_by=?, asal_barang=?, status=? "
                + " where kode_barang=? and kode_barcode=? ");
        ps.setString(1, barang.getNamaBarang());
        ps.setString(2, barang.getKodeKategori());
        ps.setString(3, barang.getKodeJenis());
        ps.setString(4, barang.getKodeIntern());
        ps.setString(5, barang.getKadar());
        ps.setDouble(6, barang.getBerat());
        ps.setDouble(7, barang.getBeratAsli());
        ps.setDouble(8, barang.getBeratPersen());
        ps.setDouble(9, barang.getNilaiPokok());
        ps.setString(10, barang.getInputDate());
        ps.setString(11, barang.getInputBy());
        ps.setString(12, barang.getAsalBarang());
        ps.setString(13, barang.getStatus());
        ps.setString(14, barang.getKodeBarang());
        ps.setString(15, barang.getKodeBarcode());
        ps.executeUpdate();
    }
}
