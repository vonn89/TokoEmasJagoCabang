/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.StokHutang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokHutangDAO {
    public static List<StokHutang> getSumByPeriode(Connection con, String tglAwal, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select tanggal,tgl_hutang, " +
                " 0,0,0, " +
                " sum(stok_masuk),sum(berat_masuk),sum(jumlah_masuk), " +
                " sum(stok_keluar),sum(berat_keluar),sum(jumlah_keluar), " +
                " sum(stok_masuk)-sum(stok_keluar),sum(berat_masuk)-sum(berat_keluar),sum(jumlah_masuk)-sum(jumlah_keluar) " +
                " from tt_stok_hutang " + 
                " where tanggal between ? and ? group by tgl_hutang");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        List<StokHutang> allStokHutang = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            StokHutang s = new StokHutang();
            s.setTanggal(rs.getString(1));
            s.setTglHutang(rs.getString(2));
            s.setStokAwal(rs.getInt(3));
            s.setBeratAwal(rs.getDouble(4));
            s.setJumlahAwal(rs.getDouble(5));
            s.setStokMasuk(rs.getInt(6));
            s.setBeratMasuk(rs.getDouble(7));
            s.setJumlahMasuk(rs.getDouble(8));
            s.setStokKeluar(rs.getInt(9));
            s.setBeratKeluar(rs.getDouble(10));
            s.setJumlahKeluar(rs.getDouble(11));
            s.setStokAkhir(rs.getInt(12));
            s.setBeratAkhir(rs.getDouble(13));
            s.setJumlahAkhir(rs.getDouble(14));
            allStokHutang.add(s);
        }
        return allStokHutang;
    }
    public static List<StokHutang> getAllByTanggal(Connection con, String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_hutang "
                + "where tanggal = ? ");
        ps.setString(1, tanggal);
        List<StokHutang> allStokHutang = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            StokHutang s = new StokHutang();
            s.setTanggal(rs.getString(1));
            s.setTglHutang(rs.getString(2));
            s.setStokAwal(rs.getInt(3));
            s.setBeratAwal(rs.getDouble(4));
            s.setJumlahAwal(rs.getDouble(5));
            s.setStokMasuk(rs.getInt(6));
            s.setBeratMasuk(rs.getDouble(7));
            s.setJumlahMasuk(rs.getDouble(8));
            s.setStokKeluar(rs.getInt(9));
            s.setBeratKeluar(rs.getDouble(10));
            s.setJumlahKeluar(rs.getDouble(11));
            s.setStokAkhir(rs.getInt(12));
            s.setBeratAkhir(rs.getDouble(13));
            s.setJumlahAkhir(rs.getDouble(14));
            allStokHutang.add(s);
        }
        return allStokHutang;
    }
    public static StokHutang get(Connection con, String tanggal, String tglHutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_hutang "
                + "where tanggal = ? and tgl_hutang = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, tglHutang);
        ResultSet rs = ps.executeQuery();
        StokHutang s = null;
        while(rs.next()){
            s = new StokHutang();
            s.setTanggal(rs.getString(1));
            s.setTglHutang(rs.getString(2));
            s.setStokAwal(rs.getInt(3));
            s.setBeratAwal(rs.getDouble(4));
            s.setJumlahAwal(rs.getDouble(5));
            s.setStokMasuk(rs.getInt(6));
            s.setBeratMasuk(rs.getDouble(7));
            s.setJumlahMasuk(rs.getDouble(8));
            s.setStokKeluar(rs.getInt(9));
            s.setBeratKeluar(rs.getDouble(10));
            s.setJumlahKeluar(rs.getDouble(11));
            s.setStokAkhir(rs.getInt(12));
            s.setBeratAkhir(rs.getDouble(13));
            s.setJumlahAkhir(rs.getDouble(14));
        }
        return s;
    }
    public static void insertStokAwal(Connection con, String date, String nextDate)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_hutang "
            + " select '"+nextDate+"',tgl_hutang,"
            + " stok_akhir,berat_akhir,jumlah_akhir,"
            + " 0,0,0,"
            + " 0,0,0,"
            + " stok_akhir,berat_akhir,jumlah_akhir "
            + " from tt_stok_hutang where tanggal = ? and stok_akhir!=0 ");
        ps.setString(1, date);
        ps.executeUpdate();
    }
    public static void insert(Connection con,StokHutang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_hutang "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, s.getTanggal());
        ps.setString(2, s.getTglHutang());
        ps.setInt(3, s.getStokAwal());
        ps.setDouble(4, s.getBeratAwal());
        ps.setDouble(5, s.getJumlahAwal());
        ps.setInt(6, s.getStokMasuk());
        ps.setDouble(7, s.getBeratMasuk());
        ps.setDouble(8, s.getJumlahMasuk());
        ps.setInt(9, s.getStokKeluar());
        ps.setDouble(10, s.getBeratKeluar());
        ps.setDouble(11, s.getJumlahKeluar());
        ps.setInt(12, s.getStokAkhir());
        ps.setDouble(13, s.getBeratAkhir());
        ps.setDouble(14, s.getJumlahAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con,StokHutang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_hutang set "
            + "stok_awal=?, berat_awal=?, jumlah_awal=?, "
            + "stok_masuk=?, berat_masuk=?, jumlah_masuk=?, "
            + "stok_keluar=?, berat_keluar=?, jumlah_keluar=?, "
            + "stok_akhir=?, berat_akhir=?, jumlah_akhir=? "
            + "where tanggal=? and tgl_hutang=?");
        ps.setInt(1, s.getStokAwal());
        ps.setDouble(2, s.getBeratAwal());
        ps.setDouble(3, s.getJumlahAwal());
        ps.setInt(4, s.getStokMasuk());
        ps.setDouble(5, s.getBeratMasuk());
        ps.setDouble(6, s.getJumlahMasuk());
        ps.setInt(7, s.getStokKeluar());
        ps.setDouble(8, s.getBeratKeluar());
        ps.setDouble(9, s.getJumlahKeluar());
        ps.setInt(10, s.getStokAkhir());
        ps.setDouble(11, s.getBeratAkhir());
        ps.setDouble(12, s.getJumlahAkhir());
        ps.setString(13, s.getTanggal());
        ps.setString(14, s.getTglHutang());
        ps.executeUpdate();
    }
}
