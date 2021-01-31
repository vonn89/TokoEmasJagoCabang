/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembayaranPenjualan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembayaranPenjualanDAO {
    
    public static List<PembayaranPenjualan> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembayaran_penjualan "
                + " where mid(no_pembayaran,4,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PembayaranPenjualan> listPenjualan = new ArrayList<>();
        while(rs.next()){
            PembayaranPenjualan p = new PembayaranPenjualan();
            p.setNoPembayaran(rs.getString(1));
            p.setTglPembayaran(rs.getString(2));
            p.setNoPenjualan(rs.getString(3));
            p.setJenisPembayaran(rs.getString(4));
            p.setKeterangan(rs.getString(5));
            p.setJumlahPembayaran(rs.getDouble(6));
            p.setKodeSales(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
            listPenjualan.add(p);
        }
        return listPenjualan;
    }
    public static List<PembayaranPenjualan> getAllByNoPenjualanAndStatus(
            Connection con, String noPenjualan, String status)throws Exception{
        String sql = "select * from tt_pembayaran_penjualan where no_penjualan = '"+noPenjualan+"'";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PembayaranPenjualan> listPenjualan = new ArrayList<>();
        while(rs.next()){
            PembayaranPenjualan p = new PembayaranPenjualan();
            p.setNoPembayaran(rs.getString(1));
            p.setTglPembayaran(rs.getString(2));
            p.setNoPenjualan(rs.getString(3));
            p.setJenisPembayaran(rs.getString(4));
            p.setKeterangan(rs.getString(5));
            p.setJumlahPembayaran(rs.getDouble(6));
            p.setKodeSales(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
            listPenjualan.add(p);
        }
        return listPenjualan;
    }
    public static PembayaranPenjualan get(Connection con, String noPembayaran)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembayaran_penjualan where no_pembayaran = ?");
        ps.setString(1, noPembayaran);
        ResultSet rs = ps.executeQuery();
        PembayaranPenjualan p = null;
        while(rs.next()){
            p = new PembayaranPenjualan();
            p.setNoPembayaran(rs.getString(1));
            p.setTglPembayaran(rs.getString(2));
            p.setNoPenjualan(rs.getString(3));
            p.setJenisPembayaran(rs.getString(4));
            p.setKeterangan(rs.getString(5));
            p.setJumlahPembayaran(rs.getDouble(6));
            p.setKodeSales(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembayaran,4)) from tt_pembayaran_penjualan "
                + " where mid(no_pembayaran,4,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void insert(Connection con,PembayaranPenjualan p) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembayaran_penjualan "
                + " values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembayaran());
        ps.setString(2, p.getTglPembayaran());
        ps.setString(3, p.getNoPenjualan());
        ps.setString(4, p.getJenisPembayaran());
        ps.setString(5, p.getKeterangan());
        ps.setDouble(6, p.getJumlahPembayaran());
        ps.setString(7, p.getKodeSales());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getTglBatal());
        ps.setString(10, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembayaranPenjualan p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembayaran_penjualan set "
                + "tgl_pembayaran=?, no_penjualan=?, jenis_pembayaran=?, keterangan=?, "
                + "jumlah_pembayaran=?, kode_sales=?, status=?, tgl_batal=?, user_batal=?"
                + "where no_pembayaran=?");
        ps.setString(1, p.getTglPembayaran());
        ps.setString(2, p.getNoPenjualan());
        ps.setString(3, p.getJenisPembayaran());
        ps.setString(4, p.getKeterangan());
        ps.setDouble(5, p.getJumlahPembayaran());
        ps.setString(6, p.getKodeSales());
        ps.setString(7, p.getStatus());
        ps.setString(8, p.getTglBatal());
        ps.setString(9, p.getUserBatal());
        ps.setString(10, p.getNoPembayaran());
        ps.executeUpdate();
    }
}
