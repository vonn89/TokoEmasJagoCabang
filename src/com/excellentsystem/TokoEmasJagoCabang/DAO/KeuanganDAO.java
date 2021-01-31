/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KeuanganDAO {
    
    public static double getSaldoBefore(Connection con, String tipeKasir, String tipeKeuangan, String tanggal)throws Exception{
        double saldoAkhir = 0;
        String sql = "select sum(jumlah_rp) from tt_keuangan "
            + "where mid(no_keuangan,8,6) < "+tglSystem.format(tglBarang.parse(tanggal))+" ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldoAkhir = rs.getDouble(1);
        return saldoAkhir;
    }
    public static double getSaldoAfter(Connection con, String tipeKasir, String tipeKeuangan, String tanggal)throws Exception{
        double saldoAkhir = 0;
        String sql = "select sum(jumlah_rp) from tt_keuangan "
            + "where mid(no_keuangan,8,6) <= "+tglSystem.format(tglBarang.parse(tanggal))+" ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldoAkhir = rs.getDouble(1);
        return saldoAkhir;
    }
    public static List<Keuangan> getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(Connection con, String tglMulai, String tglAkhir, 
            String tipeKasir, String tipeKeuangan, String kategori, String kodeSales)throws Exception{
        SimpleDateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");//number format exception
        SimpleDateFormat tglSystem = new SimpleDateFormat("yyMMdd");
        String sql = " select * from tt_keuangan "
                + " where mid(no_keuangan,8,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!kodeSales.equals("%"))
            sql = sql + " and kode_sales = '"+kodeSales+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<Keuangan> allKeuangan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setNoUrut(rs.getInt(2));
            k.setTglKeuangan(rs.getString(3));
            k.setTipeKasir(rs.getString(4));
            k.setTipeKeuangan(rs.getString(5));
            k.setKategori(rs.getString(6));
            k.setKeterangan(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeSales(rs.getString(9));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static List<Keuangan> getAll(Connection con, String tipeKasir, String tipeKeuangan, String kategori, String keterangan)throws Exception{
        String sql = " select * from tt_keuangan where no_keuangan!='' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!keterangan.equals("%"))
            sql = sql + " and keterangan = '"+keterangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<Keuangan> allKeuangan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setNoUrut(rs.getInt(2));
            k.setTglKeuangan(rs.getString(3));
            k.setTipeKasir(rs.getString(4));
            k.setTipeKeuangan(rs.getString(5));
            k.setKategori(rs.getString(6));
            k.setKeterangan(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeSales(rs.getString(9));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static Keuangan get(Connection con, String tipeKasir, String tipeKeuangan, String kategori, String keterangan)throws Exception{
        String sql = " select * from tt_keuangan where no_keuangan!='' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!keterangan.equals("%"))
            sql = sql + " and keterangan = '"+keterangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        Keuangan k = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setNoUrut(rs.getInt(2));
            k.setTglKeuangan(rs.getString(3));
            k.setTipeKasir(rs.getString(4));
            k.setTipeKeuangan(rs.getString(5));
            k.setKategori(rs.getString(6));
            k.setKeterangan(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeSales(rs.getString(9));
        }
        return k;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan "
                + " where mid(no_keuangan,8,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-KK-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-KK-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static String getIdByDate(Connection con, String tgl)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan "
                + " where mid(no_keuangan,8,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(tgl)));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-KK-"+tglSystem.format(tglBarang.parse(tgl))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-KK-"+tglSystem.format(tglBarang.parse(tgl))+"-0001";
        return id;
    }
    public static void insert(Connection con,Keuangan k) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, k.getNoKeuangan());
        ps.setInt(2, k.getNoUrut());
        ps.setString(3, k.getTglKeuangan());
        ps.setString(4, k.getTipeKasir());
        ps.setString(5, k.getTipeKeuangan());
        ps.setString(6, k.getKategori());
        ps.setString(7, k.getKeterangan());
        ps.setDouble(8, k.getJumlahRp());
        ps.setString(9, k.getKodeSales());
        ps.executeUpdate();
    }
    public static void updateNoUrut(Connection con, Keuangan k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan set "
                + " no_urut=?, tgl_keuangan=?, tipe_kasir=?, keterangan=?, jumlah_rp=?, kode_sales=? "
                + " where no_keuangan=? and tipe_keuangan=? and kategori=?");
        ps.setInt(1, k.getNoUrut());
        ps.setString(2, k.getTglKeuangan());
        ps.setString(3, k.getTipeKasir());
        ps.setString(4, k.getKeterangan());
        ps.setDouble(5, k.getJumlahRp());
        ps.setString(6, k.getKodeSales());
        ps.setString(7, k.getNoKeuangan());
        ps.setString(8, k.getTipeKeuangan());
        ps.setString(9, k.getKategori());
        ps.executeUpdate();
    }
    public static void update(Connection con, Keuangan k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan set "
                + " tgl_keuangan=?, tipe_kasir=?, tipe_keuangan=? and kategori=?, keterangan=?, jumlah_rp=?, kode_sales=? "
                + " where no_keuangan=? and no_urut=?");
        ps.setString(1, k.getTglKeuangan());
        ps.setString(2, k.getTipeKasir());
        ps.setString(3, k.getTipeKeuangan());
        ps.setString(4, k.getKategori());
        ps.setString(5, k.getKeterangan());
        ps.setDouble(6, k.getJumlahRp());
        ps.setString(7, k.getKodeSales());
        ps.setString(8, k.getNoKeuangan());
        ps.setInt(9, k.getNoUrut());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
