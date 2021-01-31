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
import com.excellentsystem.TokoEmasJagoCabang.Model.PemesananHead;
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
public class PemesananHeadDAO {
    public static List<PemesananHead> getAllByTglBatal(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_head "
                + " where left(tgl_batal,10) between ? and ? and status_ambil = 'false' and status_batal = 'true'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PemesananHead> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananHead p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setTotalPemesanan(rs.getDouble(8));
            p.setTitipUang(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatusAmbil(rs.getString(12));
            p.setTglAmbil(rs.getString(13));
            p.setSalesAmbil(rs.getString(14));
            p.setStatusBatal(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
            listPemesanan.add(p);
        }
        return listPemesanan;
    }
    public static List<PemesananHead> getAllByTglAmbil(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_head "
                + " where left(tgl_ambil,10) between ? and ? and status_ambil = 'true' and status_batal = 'false'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PemesananHead> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananHead p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setTotalPemesanan(rs.getDouble(8));
            p.setTitipUang(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatusAmbil(rs.getString(12));
            p.setTglAmbil(rs.getString(13));
            p.setSalesAmbil(rs.getString(14));
            p.setStatusBatal(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
            listPemesanan.add(p);
        }
        return listPemesanan;
    }
    public static List<PemesananHead> getAllByDateAndStatusAmbilAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String statusAmbil, String statusBatal)throws Exception{
        String sql = "select * from tt_pemesanan_head "
                + " where mid(no_pemesanan,9,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!statusAmbil.equals("%"))
            sql = sql + " and status_ambil = '"+statusAmbil+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PemesananHead> listPemesanan = new ArrayList<>();
        while(rs.next()){
            PemesananHead p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setTotalPemesanan(rs.getDouble(8));
            p.setTitipUang(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatusAmbil(rs.getString(12));
            p.setTglAmbil(rs.getString(13));
            p.setSalesAmbil(rs.getString(14));
            p.setStatusBatal(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
            listPemesanan.add(p);
        }
        return listPemesanan;
    }
    public static PemesananHead get(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_head where no_pemesanan = ?");
        ps.setString(1, noPemesanan);
        ResultSet rs = ps.executeQuery();
        PemesananHead p = null;
        while(rs.next()){
            p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setTotalPemesanan(rs.getDouble(8));
            p.setTitipUang(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatusAmbil(rs.getString(12));
            p.setTglAmbil(rs.getString(13));
            p.setSalesAmbil(rs.getString(14));
            p.setStatusBatal(rs.getString(15));
            p.setTglBatal(rs.getString(16));
            p.setUserBatal(rs.getString(17));
        }
        return p;
    }
    public static void insert(Connection con, PemesananHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_head "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        ps.setString(1, p.getNoPemesanan());
        ps.setString(2, p.getTglPemesanan());
        ps.setString(3, p.getKodeMember());
        ps.setString(4, p.getNama());
        ps.setString(5, p.getAlamat());
        ps.setString(6, p.getNoTelp());
        ps.setString(7, p.getKeterangan());
        ps.setDouble(8, p.getTotalPemesanan());
        ps.setDouble(9, p.getTitipUang());
        ps.setDouble(10, p.getSisaPembayaran());
        ps.setString(11, p.getKodeSales());
        ps.setString(12, p.getStatusAmbil());
        ps.setString(13, p.getTglAmbil());
        ps.setString(14, p.getSalesAmbil());
        ps.setString(15, p.getStatusBatal());
        ps.setString(16, p.getTglBatal());
        ps.setString(17, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PemesananHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pemesanan_head set "
            + " tgl_pemesanan=?, kode_member=?, nama=?, alamat=?, no_telp=?, "
            + " keterangan=?, total_pemesanan=?, titip_uang=?, sisa_pembayaran=?, kode_sales=?, "
            + " status_ambil=?, tgl_ambil=?, sales_ambil=?, status_batal=?, tgl_batal=?, user_batal=? "
            + " where no_pemesanan=?");
        ps.setString(1, p.getTglPemesanan());
        ps.setString(2, p.getKodeMember());
        ps.setString(3, p.getNama());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getNoTelp());
        ps.setString(6, p.getKeterangan());
        ps.setDouble(7, p.getTotalPemesanan());
        ps.setDouble(8, p.getTitipUang());
        ps.setDouble(9, p.getSisaPembayaran());
        ps.setString(10, p.getKodeSales());
        ps.setString(11, p.getStatusAmbil());
        ps.setString(12, p.getTglAmbil());
        ps.setString(13, p.getSalesAmbil());
        ps.setString(14, p.getStatusBatal());
        ps.setString(15, p.getTglBatal());
        ps.setString(16, p.getUserBatal());
        ps.setString(17, p.getNoPemesanan());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pemesanan,4)) from tt_pemesanan_head "
                + " where mid(no_pemesanan,9,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-PSN-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-PSN-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
}
