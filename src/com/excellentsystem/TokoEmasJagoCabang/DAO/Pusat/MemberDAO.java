/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.Member;
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
public class MemberDAO {
    public static List<Member> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_member where kode_member!='' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Member> listMember = new ArrayList<>();
        while(rs.next()){
            Member m = new Member();
            m.setKodeMember(rs.getString(1));
            m.setNoRfid(rs.getString(2));
            m.setNoKartu(rs.getString(3));
            m.setNama(rs.getString(4));
            m.setAlamat(rs.getString(5));
            m.setKelurahan(rs.getString(6));
            m.setKecamatan(rs.getString(7));
            m.setNoTelp(rs.getString(8));
            m.setPekerjaan(rs.getString(9));
            m.setIdentitas(rs.getString(10));
            m.setNoIdentitas(rs.getString(11));
            m.setPoin(rs.getInt(12));
            m.setStatus(rs.getString(13));
            m.setTglDaftar(rs.getString(14));
            m.setSalesDaftar(rs.getString(15));
            listMember.add(m);
        }
        return listMember;
    }
    public static Member getByNoKartuOrNoRfid(Connection con, String noKartu)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_member where no_kartu = ? or no_rfid = ?");
        ps.setString(1, noKartu);
        ps.setString(2, noKartu);
        ResultSet rs = ps.executeQuery();
        Member m = null;
        while(rs.next()){
            m = new Member();
            m.setKodeMember(rs.getString(1));
            m.setNoRfid(rs.getString(2));
            m.setNoKartu(rs.getString(3));
            m.setNama(rs.getString(4));
            m.setAlamat(rs.getString(5));
            m.setKelurahan(rs.getString(6));
            m.setKecamatan(rs.getString(7));
            m.setNoTelp(rs.getString(8));
            m.setPekerjaan(rs.getString(9));
            m.setIdentitas(rs.getString(10));
            m.setNoIdentitas(rs.getString(11));
            m.setPoin(rs.getInt(12));
            m.setStatus(rs.getString(13));
            m.setTglDaftar(rs.getString(14));
            m.setSalesDaftar(rs.getString(15));
        }
        return m;
    }
    public static Member get(Connection con, String kodeMember)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_member where kode_member = ?");
        ps.setString(1, kodeMember);
        ResultSet rs = ps.executeQuery();
        Member m = null;
        while(rs.next()){
            m = new Member();
            m.setKodeMember(rs.getString(1));
            m.setNoRfid(rs.getString(2));
            m.setNoKartu(rs.getString(3));
            m.setNama(rs.getString(4));
            m.setAlamat(rs.getString(5));
            m.setKelurahan(rs.getString(6));
            m.setKecamatan(rs.getString(7));
            m.setNoTelp(rs.getString(8));
            m.setPekerjaan(rs.getString(9));
            m.setIdentitas(rs.getString(10));
            m.setNoIdentitas(rs.getString(11));
            m.setPoin(rs.getInt(12));
            m.setStatus(rs.getString(13));
            m.setTglDaftar(rs.getString(14));
            m.setSalesDaftar(rs.getString(15));
        }
        return m;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_member,6)) from tm_member");
        DecimalFormat df = new DecimalFormat("000000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-M-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-M-000001";
        return id;
    }
    public static void insert(Connection con, Member m)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_member values "
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, m.getKodeMember());
        ps.setString(2, m.getNoRfid());
        ps.setString(3, m.getNoKartu());
        ps.setString(4, m.getNama());
        ps.setString(5, m.getAlamat());
        ps.setString(6, m.getKelurahan());
        ps.setString(7, m.getKecamatan());
        ps.setString(8, m.getNoTelp());
        ps.setString(9, m.getPekerjaan());
        ps.setString(10, m.getIdentitas());
        ps.setString(11, m.getNoIdentitas());
        ps.setInt(12, m.getPoin());
        ps.setString(13, m.getStatus());
        ps.setString(14, m.getTglDaftar());
        ps.setString(15, m.getSalesDaftar());
        ps.executeUpdate();
    }
    public static void update(Connection con, Member m)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_member set "
                + " no_rfid=?, no_kartu=?, nama=?, alamat=?, kelurahan=?, kecamatan=?, no_telp=?, "
                + " pekerjaan=?, identitas=?, no_identitas=?, poin=?, status=?, tgl_daftar=?, sales_daftar=? "
                + " where kode_member=?");
        ps.setString(1, m.getNoRfid());
        ps.setString(2, m.getNoKartu());
        ps.setString(3, m.getNama());
        ps.setString(4, m.getAlamat());
        ps.setString(5, m.getKelurahan());
        ps.setString(6, m.getKecamatan());
        ps.setString(7, m.getNoTelp());
        ps.setString(8, m.getPekerjaan());
        ps.setString(9, m.getIdentitas());
        ps.setString(10, m.getNoIdentitas());
        ps.setInt(11, m.getPoin());
        ps.setString(12, m.getStatus());
        ps.setString(13, m.getTglDaftar());
        ps.setString(14, m.getSalesDaftar());
        ps.setString(15, m.getKodeMember());
        ps.executeUpdate();
    }
}
