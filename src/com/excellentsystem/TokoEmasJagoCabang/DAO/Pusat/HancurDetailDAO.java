/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.HancurDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class HancurDetailDAO {
    public static List<HancurDetail> getAllByDateAndCabangAndStatus(Connection con, String tglMulai, String tglAkhir, 
            String kodeCabang, String status)throws Exception{
        String sql = "select * from tt_hancur_detail where no_hancur in ("
            + " select no_hancur from tt_hancur_head "
            + " where mid(no_hancur,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<HancurDetail> listHancurDetail = new ArrayList<>();
        while(rs.next()){
            HancurDetail h = new HancurDetail();
            h.setNoHancur(rs.getString(1));
            h.setKodeBarang(rs.getString(2));
            h.setKodeBarcode(rs.getString(3));
            h.setNamaBarang(rs.getString(4));
            h.setKodeKategori(rs.getString(5));
            h.setKodeJenis(rs.getString(6));
            h.setKodeIntern(rs.getString(7));
            h.setKadar(rs.getString(8));
            h.setBerat(rs.getDouble(9));
            h.setBeratAsli(rs.getDouble(10));
            h.setBeratPersen(rs.getDouble(11));
            h.setNilaiPokok(rs.getDouble(12));
            h.setInputDate(rs.getString(13));
            h.setInputBy(rs.getString(14));
            h.setAsalBarang(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setPersentaseRosok(rs.getDouble(17));
            h.setBeratPersenRosok(rs.getDouble(18));
            listHancurDetail.add(h);
        }
        return listHancurDetail;
    }
    public static List<HancurDetail> getAllByNoHancur(Connection con, String noHancur)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_hancur_detail where no_hancur = ?");
        ResultSet rs = ps.executeQuery();
        List<HancurDetail> listHancurDetail = new ArrayList<>();
        while(rs.next()){
            HancurDetail h = new HancurDetail();
            h.setNoHancur(rs.getString(1));
            h.setKodeBarang(rs.getString(2));
            h.setKodeBarcode(rs.getString(3));
            h.setNamaBarang(rs.getString(4));
            h.setKodeKategori(rs.getString(5));
            h.setKodeJenis(rs.getString(6));
            h.setKodeIntern(rs.getString(7));
            h.setKadar(rs.getString(8));
            h.setBerat(rs.getDouble(9));
            h.setBeratAsli(rs.getDouble(10));
            h.setBeratPersen(rs.getDouble(11));
            h.setNilaiPokok(rs.getDouble(12));
            h.setInputDate(rs.getString(13));
            h.setInputBy(rs.getString(14));
            h.setAsalBarang(rs.getString(15));
            h.setStatus(rs.getString(16));
            h.setPersentaseRosok(rs.getDouble(17));
            h.setBeratPersenRosok(rs.getDouble(18));
            listHancurDetail.add(h);
        }
        return listHancurDetail;
    }
    public static void insert(Connection con,HancurDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_hancur_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoHancur());
        ps.setString(2, d.getKodeBarang());
        ps.setString(3, d.getKodeBarcode());
        ps.setString(4, d.getNamaBarang());
        ps.setString(5, d.getKodeKategori());
        ps.setString(6, d.getKodeJenis());
        ps.setString(7, d.getKodeIntern());
        ps.setString(8, d.getKadar());
        ps.setDouble(9, d.getBerat());
        ps.setDouble(10, d.getBeratAsli());
        ps.setDouble(11, d.getBeratPersen());
        ps.setDouble(12, d.getNilaiPokok());
        ps.setString(13, d.getInputDate());
        ps.setString(14, d.getInputBy());
        ps.setString(15, d.getAsalBarang());
        ps.setString(16, d.getStatus());
        ps.setDouble(17, d.getPersentaseRosok());
        ps.setDouble(18, d.getBeratPersenRosok());
        ps.executeUpdate();
                            
    }
}
