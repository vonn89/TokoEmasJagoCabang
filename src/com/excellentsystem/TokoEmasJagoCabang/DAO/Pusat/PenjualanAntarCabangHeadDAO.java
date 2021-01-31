/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PenjualanAntarCabangHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PenjualanAntarCabangHeadDAO {
    public static List<PenjualanAntarCabangHead> getAllByTglTerimaAndCabangAsalAndCabangTujuan(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String cabangTujuan)throws Exception{
        String sql = "select * from tt_penjualan_antar_cabang_head "
                + " where left(tgl_terima,10) between ? and ? and status_terima like 'true' and status_batal like 'false'";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!cabangTujuan.equals("%"))
            sql = sql + " and cabang_tujuan = '"+cabangTujuan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PenjualanAntarCabangHead> listPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanAntarCabangHead p = new PenjualanAntarCabangHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setCabangTujuan(rs.getString(4));
            p.setKodeSales(rs.getString(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setTotalHarga(rs.getDouble(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setSalesTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            listPenjualan.add(p);
        }
        return listPenjualan;
    }
    public static List<PenjualanAntarCabangHead> getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String cabangTujuan,
            String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_penjualan_antar_cabang_head where mid(no_penjualan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!cabangTujuan.equals("%"))
            sql = sql + " and cabang_tujuan = '"+cabangTujuan+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<PenjualanAntarCabangHead> listPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanAntarCabangHead p = new PenjualanAntarCabangHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setCabangTujuan(rs.getString(4));
            p.setKodeSales(rs.getString(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setTotalHarga(rs.getDouble(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setSalesTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            listPenjualan.add(p);
        }
        return listPenjualan;
    }
    public static PenjualanAntarCabangHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_antar_cabang_head "
                + " where no_penjualan = ? ");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanAntarCabangHead p = null;
        while(rs.next()){
            p = new PenjualanAntarCabangHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setCabangTujuan(rs.getString(4));
            p.setKodeSales(rs.getString(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setTotalHarga(rs.getDouble(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setSalesTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,4)) from tt_penjualan_antar_cabang_head "
                + " where mid(no_penjualan,8,6)=? and kode_cabang like ?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ps.setString(2, cabang.getKodeCabang());
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-PC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-PC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void update(Connection con, PenjualanAntarCabangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_antar_cabang_head set "
                + " tgl_penjualan=?, kode_cabang=?, cabang_tujuan=?, "
                + " kode_sales=?, total_berat=?, total_harga=?, "
                + " status_terima=?, tgl_terima=?, sales_terima=?, "
                + " status_batal=?, tgl_batal=?, user_batal=? "
                + " where no_penjualan=?");
        ps.setString(1, p.getTglPenjualan());
        ps.setString(2, p.getKodeCabang());
        ps.setString(3, p.getCabangTujuan());
        ps.setString(4, p.getKodeSales());
        ps.setDouble(5, p.getTotalBerat());
        ps.setDouble(6, p.getTotalHarga());
        ps.setString(7, p.getStatusTerima());
        ps.setString(8, p.getTglTerima());
        ps.setString(9, p.getSalesTerima());
        ps.setString(10, p.getStatusBatal());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.setString(13, p.getNoPenjualan());
        ps.executeUpdate();
    }
    public static void insert(Connection con, PenjualanAntarCabangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_antar_cabang_head "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getKodeCabang());
        ps.setString(4, p.getCabangTujuan());
        ps.setString(5, p.getKodeSales());
        ps.setDouble(6, p.getTotalBerat());
        ps.setDouble(7, p.getTotalHarga());
        ps.setString(8, p.getStatusTerima());
        ps.setString(9, p.getTglTerima());
        ps.setString(10, p.getSalesTerima());
        ps.setString(11, p.getStatusBatal());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.executeUpdate();
    }
}
