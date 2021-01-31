/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanDetailDAO {
    public static List<PenjualanDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_detail where no_penjualan in ("
                + " select no_penjualan from tt_penjualan_head "
                + " where mid(no_penjualan,8,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + ") ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PenjualanDetail> listDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanDetail d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarcode(rs.getString(3));
            d.setKodeBarang(rs.getString(4));
            d.setKodeKategori(rs.getString(5));
            d.setKodeJenis(rs.getString(6));
            d.setNamaBarang(rs.getString(7));
            d.setBerat(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            d.setHargaJual(rs.getDouble(10));
            d.setHargaKategori(rs.getDouble(11));
            d.setOngkos(rs.getDouble(12));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static List<PenjualanDetail> getAllByNoPenjualan(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        List<PenjualanDetail> listDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanDetail d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarcode(rs.getString(3));
            d.setKodeBarang(rs.getString(4));
            d.setKodeKategori(rs.getString(5));
            d.setKodeJenis(rs.getString(6));
            d.setNamaBarang(rs.getString(7));
            d.setBerat(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            d.setHargaJual(rs.getDouble(10));
            d.setHargaKategori(rs.getDouble(11));
            d.setOngkos(rs.getDouble(12));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static PenjualanDetail get(Connection con, String noPenjualan, String noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail "
                + "where no_penjualan = ? and no_urut = ?");
        ps.setString(1, noPenjualan);
        ps.setString(2, noUrut);
        ResultSet rs = ps.executeQuery();
        PenjualanDetail d = null;
        while(rs.next()){
            d = new PenjualanDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarcode(rs.getString(3));
            d.setKodeBarang(rs.getString(4));
            d.setKodeKategori(rs.getString(5));
            d.setKodeJenis(rs.getString(6));
            d.setNamaBarang(rs.getString(7));
            d.setBerat(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            d.setHargaJual(rs.getDouble(10));
            d.setHargaKategori(rs.getDouble(11));
            d.setOngkos(rs.getDouble(12));
        }
        return d;
    }
    public static void insert(Connection con, PenjualanDetail detail) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_detail "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPenjualan());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeBarcode());
        ps.setString(4, detail.getKodeBarang());
        ps.setString(5, detail.getKodeKategori());
        ps.setString(6, detail.getKodeJenis());
        ps.setString(7, detail.getNamaBarang());
        ps.setDouble(8, detail.getBerat());
        ps.setDouble(9, detail.getHargaBeli());
        ps.setDouble(10, detail.getHargaJual());
        ps.setDouble(11, detail.getHargaKategori());
        ps.setDouble(12, detail.getOngkos());
        ps.executeUpdate();
    }
}
