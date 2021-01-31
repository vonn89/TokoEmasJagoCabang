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
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanHead;
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
public class PenjualanHeadDAO {
    
    public static List<PenjualanHead> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_head "
                + " where mid(no_penjualan,8,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PenjualanHead> listPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanHead p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setTotalBerat(rs.getDouble(7));
            p.setTotalHarga(rs.getDouble(8));
            p.setTotalOngkos(rs.getDouble(9));
            p.setGrandtotal(rs.getDouble(10));
            p.setPembayaran(rs.getDouble(11));
            p.setSisaPembayaran(rs.getDouble(12));
            p.setKeterangan(rs.getString(13));
            p.setKodeSales(rs.getString(14));
            p.setStatus(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
            listPenjualan.add(p);
        }
        return listPenjualan;
    }
    public static PenjualanHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanHead p = null;
        while(rs.next()){
            p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setTotalBerat(rs.getDouble(7));
            p.setTotalHarga(rs.getDouble(8));
            p.setTotalOngkos(rs.getDouble(9));
            p.setGrandtotal(rs.getDouble(10));
            p.setPembayaran(rs.getDouble(11));
            p.setSisaPembayaran(rs.getDouble(12));
            p.setKeterangan(rs.getString(13));
            p.setKodeSales(rs.getString(14));
            p.setStatus(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,4)) from tt_penjualan_head "
                + " where mid(no_penjualan,8,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-PJ-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-PJ-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void insert(Connection con,PenjualanHead p) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_head "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getKodeMember());
        ps.setString(4, p.getNama());
        ps.setString(5, p.getAlamat());
        ps.setString(6, p.getNoTelp());
        ps.setDouble(7, p.getTotalBerat());
        ps.setDouble(8, p.getTotalHarga());
        ps.setDouble(9, p.getTotalOngkos());
        ps.setDouble(10, p.getGrandtotal());
        ps.setDouble(11, p.getPembayaran());
        ps.setDouble(12, p.getSisaPembayaran());
        ps.setString(13, p.getKeterangan());
        ps.setString(14, p.getKodeSales());
        ps.setString(15, p.getStatus());
        ps.setString(16, p.getTglBatal());
        ps.setString(17, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_head set "
                + " tgl_penjualan=?, kode_member=?, nama=?, alamat=?, no_telp=?, "
                + " total_berat=?, total_harga=?, total_ongkos=?, grandtotal=?, "
                + " pembayaran=?, sisa_pembayaran=?, keterangan=?, "
                + " kode_sales=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_penjualan=? ");
        ps.setString(1, p.getTglPenjualan());
        ps.setString(2, p.getKodeMember());
        ps.setString(3, p.getNama());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getNoTelp());
        ps.setDouble(6, p.getTotalBerat());
        ps.setDouble(7, p.getTotalHarga());
        ps.setDouble(8, p.getTotalOngkos());
        ps.setDouble(9, p.getGrandtotal());
        ps.setDouble(10, p.getPembayaran());
        ps.setDouble(11, p.getSisaPembayaran());
        ps.setString(12, p.getKeterangan());
        ps.setString(13, p.getKodeSales());
        ps.setString(14, p.getStatus());
        ps.setString(15, p.getTglBatal());
        ps.setString(16, p.getUserBatal());
        ps.setString(17, p.getNoPenjualan());
        ps.executeUpdate();
    }
}
