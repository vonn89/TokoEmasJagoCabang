/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PemesananDetailDAO {
    public static List<PemesananDetail> getAllByTglAmbil(Connection con, String tglMulai, String tglAkhir)throws Exception{
        String sql = "select * from tt_pemesanan_detail where no_pemesanan in ("
                + " select no_pemesanan from tt_pemesanan_head "
                + " where left(tgl_ambil,10) between '"+tglMulai+"' and '"+tglAkhir+"' and status_ambil = 'true' and status_batal = 'false' )";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PemesananDetail> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananDetail d = new PemesananDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeJenis(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setOngkos(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
            listPemesanan.add(d);
        }
        return listPemesanan;
    }
    public static List<PemesananDetail> getAllByDateAndStatusAmbilAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String statusAmbil, String statusBatal)throws Exception{
        String sql = "select * from tt_pemesanan_detail where no_pemesanan in ("
                + " select no_pemesanan from tt_pemesanan_head "
                + " where mid(no_pemesanan,9,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!statusAmbil.equals("%"))
            sql = sql + " and status_ambil = '"+statusAmbil+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PemesananDetail> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananDetail d = new PemesananDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeJenis(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setOngkos(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
            listPemesanan.add(d);
        }
        return listPemesanan;
    }
    public static List<PemesananDetail> getAllByNoPemesanan(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_detail where no_pemesanan = ?");
        ps.setString(1, noPemesanan);
        ResultSet rs = ps.executeQuery();
        List<PemesananDetail> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananDetail d = new PemesananDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeJenis(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setOngkos(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
            listPemesanan.add(d);
        }
        return listPemesanan;
    }
    public static PemesananDetail get(Connection con, String noPemesanan, String noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_detail "
                + " where no_pemesanan = ? and no_urut = ? ");
        ps.setString(1, noPemesanan);
        ps.setString(2, noUrut);
        ResultSet rs = ps.executeQuery();
        PemesananDetail d = null;
        while(rs.next()){
            d = new PemesananDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeJenis(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setOngkos(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
        }
        return d;
    }
    public static void insert(Connection con,PemesananDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_detail values(?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPemesanan());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeJenis());
        ps.setString(4, detail.getNamaBarang());
        ps.setDouble(5, detail.getBerat());
        ps.setDouble(6, detail.getOngkos());
        ps.setDouble(7, detail.getHarga());
        ps.executeUpdate();
    }
}
