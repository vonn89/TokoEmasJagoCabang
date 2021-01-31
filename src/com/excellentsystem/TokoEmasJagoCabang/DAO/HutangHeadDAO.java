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
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangHead;
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
public class HutangHeadDAO {
    public static List<HutangHead> getAllByTglLunas(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang_head "
            + " where left(tgl_lunas,10) between '"+tglMulai+"' and '"+tglAkhir+"' and status_lunas = 'true' and status_batal = 'false' ");
        ResultSet rs = ps.executeQuery();
        List<HutangHead> listHutang = new ArrayList<>();
        while(rs.next()){
            HutangHead h = new HutangHead();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKodeMember(rs.getString(3));
            h.setNama(rs.getString(4));
            h.setAlamat(rs.getString(5));
            h.setNoTelp(rs.getString(6));
            h.setTotalBerat(rs.getDouble(7));
            h.setMaksPinjaman(rs.getDouble(8));
            h.setTotalHutang(rs.getDouble(9));
            h.setLamaPinjam(rs.getInt(10));
            h.setBungaPersen(rs.getDouble(11));
            h.setBungaKomp(rs.getDouble(12));
            h.setBungaRp(rs.getDouble(13));
            h.setKeterangan(rs.getString(14));
            h.setStatusHilang(rs.getString(15));
            h.setKodeSales(rs.getString(16));
            h.setStatusLunas(rs.getString(17));
            h.setTglLunas(rs.getString(18));
            h.setSalesLunas(rs.getString(19));
            h.setStatusBatal(rs.getString(20));
            h.setTglBatal(rs.getString(21));
            h.setUserBatal(rs.getString(22));
            listHutang.add(h);
        }
        return listHutang;
    }
    public static List<HutangHead> getAllByDateAndStatusLunasAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String statusLunas, String statusBatal)throws Exception{
        String sql = "select * from tm_hutang_head "
                + " where mid(no_hutang,8,6) between "+tglSystem.format(tglBarang.parse(tglMulai))+" and "+tglSystem.format(tglBarang.parse(tglAkhir))+" ";
        if(!statusLunas.equals("%"))
            sql = sql + " and status_lunas = '"+statusLunas+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<HutangHead> listHutang = new ArrayList<>();
        while(rs.next()){
            HutangHead h = new HutangHead();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKodeMember(rs.getString(3));
            h.setNama(rs.getString(4));
            h.setAlamat(rs.getString(5));
            h.setNoTelp(rs.getString(6));
            h.setTotalBerat(rs.getDouble(7));
            h.setMaksPinjaman(rs.getDouble(8));
            h.setTotalHutang(rs.getDouble(9));
            h.setLamaPinjam(rs.getInt(10));
            h.setBungaPersen(rs.getDouble(11));
            h.setBungaKomp(rs.getDouble(12));
            h.setBungaRp(rs.getDouble(13));
            h.setKeterangan(rs.getString(14));
            h.setStatusHilang(rs.getString(15));
            h.setKodeSales(rs.getString(16));
            h.setStatusLunas(rs.getString(17));
            h.setTglLunas(rs.getString(18));
            h.setSalesLunas(rs.getString(19));
            h.setStatusBatal(rs.getString(20));
            h.setTglBatal(rs.getString(21));
            h.setUserBatal(rs.getString(22));
            listHutang.add(h);
        }
        return listHutang;
    }
    public static HutangHead get(Connection con, String noHutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang_head where no_hutang = ? ");
        ps.setString(1, noHutang);
        ResultSet rs = ps.executeQuery();
        HutangHead h = null;
        while(rs.next()){
            h = new HutangHead();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKodeMember(rs.getString(3));
            h.setNama(rs.getString(4));
            h.setAlamat(rs.getString(5));
            h.setNoTelp(rs.getString(6));
            h.setTotalBerat(rs.getDouble(7));
            h.setMaksPinjaman(rs.getDouble(8));
            h.setTotalHutang(rs.getDouble(9));
            h.setLamaPinjam(rs.getInt(10));
            h.setBungaPersen(rs.getDouble(11));
            h.setBungaKomp(rs.getDouble(12));
            h.setBungaRp(rs.getDouble(13));
            h.setKeterangan(rs.getString(14));
            h.setStatusHilang(rs.getString(15));
            h.setKodeSales(rs.getString(16));
            h.setStatusLunas(rs.getString(17));
            h.setTglLunas(rs.getString(18));
            h.setSalesLunas(rs.getString(19));
            h.setStatusBatal(rs.getString(20));
            h.setTglBatal(rs.getString(21));
            h.setUserBatal(rs.getString(22));
        }
        return h;
    }
    public static void hitungBungaKomp(Connection con,String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_hutang_head "
            + " set lama_pinjam=datediff('"+tanggal+"',concat('20',SUBSTRING(no_hutang,8,2),'-',SUBSTRING(no_hutang,10,2),'-',substring(no_hutang,12,2))),"
            + " bunga_komp=ceil(total_hutang*bunga_persen/100/30*lama_pinjam/500)*500 , "
            + " bunga_rp=ceil(total_hutang*bunga_persen/100/30*lama_pinjam/500)*500 "
            + " where status_lunas='false' and status_batal='false'");
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_hutang,4)) from tm_hutang_head "
                + " where mid(no_hutang,8,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        DecimalFormat df = new DecimalFormat("0000");
        String id = cabang.getKodeCabang()+"-RR-"+
                tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        if(rs.next())
            id = cabang.getKodeCabang()+"-RR-"+
                tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        return id;
    }
    public static void insert(Connection con,HutangHead h)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_hutang_head "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, h.getNoHutang());
        ps.setString(2, h.getTglHutang());
        ps.setString(3, h.getKodeMember());
        ps.setString(4, h.getNama());
        ps.setString(5, h.getAlamat());
        ps.setString(6, h.getNoTelp());
        ps.setDouble(7, h.getTotalBerat());
        ps.setDouble(8, h.getMaksPinjaman());
        ps.setDouble(9, h.getTotalHutang());
        ps.setInt(10, h.getLamaPinjam());
        ps.setDouble(11, h.getBungaPersen());
        ps.setDouble(12, h.getBungaKomp());
        ps.setDouble(13, h.getBungaRp());
        ps.setString(14, h.getKeterangan());
        ps.setString(15, h.getStatusHilang());
        ps.setString(16, h.getKodeSales());
        ps.setString(17, h.getStatusLunas());
        ps.setString(18, h.getTglLunas());
        ps.setString(19, h.getSalesLunas());
        ps.setString(20, h.getStatusBatal());
        ps.setString(21, h.getTglBatal());
        ps.setString(22, h.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,HutangHead h)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_hutang_head set "
            + " tgl_hutang=?, kode_member=?, nama=?, alamat=?, no_telp=?, "
            + " total_berat=?, maks_pinjaman=?, total_hutang=?, lama_pinjam=?, bunga_persen=?, "
            + " bunga_komp=?, bunga_rp=?, keterangan=?, status_hilang=?, kode_sales=?, "
            + " status_lunas=?, tgl_lunas=?, sales_lunas=?, "
            + " status_batal=?, tgl_batal=?, user_batal=? where no_hutang=?");
        ps.setString(1, h.getTglHutang());
        ps.setString(2, h.getKodeMember());
        ps.setString(3, h.getNama());
        ps.setString(4, h.getAlamat());
        ps.setString(5, h.getNoTelp());
        ps.setDouble(6, h.getTotalBerat());
        ps.setDouble(7, h.getMaksPinjaman());
        ps.setDouble(8, h.getTotalHutang());
        ps.setInt(9, h.getLamaPinjam());
        ps.setDouble(10, h.getBungaPersen());
        ps.setDouble(11, h.getBungaKomp());
        ps.setDouble(12, h.getBungaRp());
        ps.setString(13, h.getKeterangan());
        ps.setString(14, h.getStatusHilang());
        ps.setString(15, h.getKodeSales());
        ps.setString(16, h.getStatusLunas());
        ps.setString(17, h.getTglLunas());
        ps.setString(18, h.getSalesLunas());
        ps.setString(19, h.getStatusBatal());
        ps.setString(20, h.getTglBatal());
        ps.setString(21, h.getUserBatal());
        ps.setString(22, h.getNoHutang());
        ps.executeUpdate();
    }
}
