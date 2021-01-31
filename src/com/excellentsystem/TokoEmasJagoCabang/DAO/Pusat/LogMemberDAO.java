/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.LogMember;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class LogMemberDAO {
    public static double getPoinBefore(Connection con, String kodeMember, String tanggal)throws Exception{
        double saldoAkhir = 0;
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_poin) from tm_log_member "
                + " where left(tanggal, 10) < "+tanggal+" "
                + " and kode_member = '"+kodeMember+"' and status = 'true'");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldoAkhir = rs.getDouble(1);
        return saldoAkhir;
    }
    public static double getPoinAfter(Connection con, String kodeMember, String tanggal)throws Exception{
        double saldoAkhir = 0;
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_poin) from tm_log_member "
                + " where left(tanggal, 10) <= "+tanggal+" "
                + " and kode_member = '"+kodeMember+"' and status = 'true'");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldoAkhir = rs.getDouble(1);
        return saldoAkhir;
    }
    public static List<LogMember> getAllByDateAndCabangAndMemberAndKategoriAndSalesAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String cabang, String kodeMember, String kategori, String kodeSales, String status)throws Exception{
        String sql = "select * from tm_log_member where left(tanggal, 10) between ? and ? ";
        if(!cabang.equals("%"))
            sql = sql + " and kode_cabang = '"+cabang+"' ";
        if(!kodeMember.equals("%"))
            sql = sql + " and kode_member = '"+kodeMember+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!kodeSales.equals("%"))
            sql = sql + " and kode_sales = '"+kodeSales+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<LogMember> listLogMember = new ArrayList<>();
        while(rs.next()){
            LogMember l = new LogMember();
            l.setTanggal(rs.getString(1));
            l.setKodeCabang(rs.getString(2));
            l.setKodeMember(rs.getString(3));
            l.setKategori(rs.getString(4));
            l.setKeterangan(rs.getString(5));
            l.setJumlahRp(rs.getDouble(6));
            l.setJumlahPoin(rs.getInt(7));
            l.setKodeSales(rs.getString(8));
            l.setStatus(rs.getString(9));
            l.setTglBatal(rs.getString(10));
            l.setUserBatal(rs.getString(11));
            listLogMember.add(l);
        }
        return listLogMember;
    }
    public static LogMember get(Connection con, String kodeCabang, String kodeMember, String kategori, String keterangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_log_member "
                + " where kode_cabang = ? and kode_member = ? and kategori = ? and keterangan = ?");
        ps.setString(1, kodeCabang);
        ps.setString(2, kodeMember);
        ps.setString(3, kategori);
        ps.setString(4, keterangan);
        ResultSet rs = ps.executeQuery();
        LogMember l = null;
        while(rs.next()){
            l = new LogMember();
            l.setTanggal(rs.getString(1));
            l.setKodeCabang(rs.getString(2));
            l.setKodeMember(rs.getString(3));
            l.setKategori(rs.getString(4));
            l.setKeterangan(rs.getString(5));
            l.setJumlahRp(rs.getDouble(6));
            l.setJumlahPoin(rs.getInt(7));
            l.setKodeSales(rs.getString(8));
            l.setStatus(rs.getString(9));
            l.setTglBatal(rs.getString(10));
            l.setUserBatal(rs.getString(11));
        }
        return l;
    }
    public static void insert(Connection con, LogMember l)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_log_member values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, l.getTanggal());
        ps.setString(2, l.getKodeCabang());
        ps.setString(3, l.getKodeMember());
        ps.setString(4, l.getKategori());
        ps.setString(5, l.getKeterangan());
        ps.setDouble(6, l.getJumlahRp());
        ps.setInt(7, l.getJumlahPoin());
        ps.setString(8, l.getKodeSales());
        ps.setString(9, l.getStatus());
        ps.setString(10, l.getTglBatal());
        ps.setString(11, l.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, LogMember l)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_log_member set "
                + " jumlah_rp=?, jumlah_poin=?, kode_sales=?, status=?, tgl_batal=?, user_batal=? "
                + " where tanggal=? and kode_cabang=? and kode_member=? and kategori=? and keterangan=?");
        ps.setDouble(1, l.getJumlahRp());
        ps.setInt(2, l.getJumlahPoin());
        ps.setString(3, l.getKodeSales());
        ps.setString(4, l.getStatus());
        ps.setString(5, l.getTglBatal());
        ps.setString(6, l.getUserBatal());
        ps.setString(7, l.getTanggal());
        ps.setString(8, l.getKodeCabang());
        ps.setString(9, l.getKodeMember());
        ps.setString(10, l.getKategori());
        ps.setString(11, l.getKeterangan());
        ps.executeUpdate();
    }
}
