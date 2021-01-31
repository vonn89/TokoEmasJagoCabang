/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PindahDetailDAO {
    public static List<PindahDetail> getAllByTglTerimaAndCabang(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang)throws Exception{
        String sql = "select * from tt_pindah_detail where no_pindah in (select no_pindah from tt_pindah_head where left(tgl_terima, 10) between ? and ? "
                + " and status_terima like 'true' and status_batal like 'false'";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PindahDetail> listPindahDetail = new ArrayList<>();
        while(rs.next()){
            PindahDetail p = new PindahDetail();
            p.setNoPindah(rs.getString(1));
            p.setKodeBarang(rs.getString(2));
            p.setKodeBarcode(rs.getString(3));
            p.setNamaBarang(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setKodeJenis(rs.getString(6));
            p.setKodeIntern(rs.getString(7));
            p.setKadar(rs.getString(8));
            p.setBerat(rs.getDouble(9));
            p.setBeratAsli(rs.getDouble(10));
            p.setBeratPersen(rs.getDouble(11));
            p.setNilaiPokok(rs.getDouble(12));
            p.setInputDate(rs.getString(13));
            p.setInputBy(rs.getString(14));
            p.setAsalBarang(rs.getString(15));
            p.setStatus(rs.getString(16));
            listPindahDetail.add(p);
        }
        return listPindahDetail;
    }
    public static List<PindahDetail> getAllByDateAndCabangAndStatusTerimaAndStatusBatal(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_pindah_detail where no_pindah in (select no_pindah from tt_pindah_head where "
                + " mid(no_pindah,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PindahDetail> listPindahDetail = new ArrayList<>();
        while(rs.next()){
            PindahDetail p = new PindahDetail();
            p.setNoPindah(rs.getString(1));
            p.setKodeBarang(rs.getString(2));
            p.setKodeBarcode(rs.getString(3));
            p.setNamaBarang(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setKodeJenis(rs.getString(6));
            p.setKodeIntern(rs.getString(7));
            p.setKadar(rs.getString(8));
            p.setBerat(rs.getDouble(9));
            p.setBeratAsli(rs.getDouble(10));
            p.setBeratPersen(rs.getDouble(11));
            p.setNilaiPokok(rs.getDouble(12));
            p.setInputDate(rs.getString(13));
            p.setInputBy(rs.getString(14));
            p.setAsalBarang(rs.getString(15));
            p.setStatus(rs.getString(16));
            listPindahDetail.add(p);
        }
        return listPindahDetail;
    }
    public static List<PindahDetail> getAllByNoPindah(Connection con, String noPindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_detail where no_pindah =?");
        ps.setString(1, noPindah);
        ResultSet rs = ps.executeQuery();
        List<PindahDetail> listPindahDetail = new ArrayList<>();
        while(rs.next()){
            PindahDetail p = new PindahDetail();
            p.setNoPindah(rs.getString(1));
            p.setKodeBarang(rs.getString(2));
            p.setKodeBarcode(rs.getString(3));
            p.setNamaBarang(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setKodeJenis(rs.getString(6));
            p.setKodeIntern(rs.getString(7));
            p.setKadar(rs.getString(8));
            p.setBerat(rs.getDouble(9));
            p.setBeratAsli(rs.getDouble(10));
            p.setBeratPersen(rs.getDouble(11));
            p.setNilaiPokok(rs.getDouble(12));
            p.setInputDate(rs.getString(13));
            p.setInputBy(rs.getString(14));
            p.setAsalBarang(rs.getString(15));
            p.setStatus(rs.getString(16));
            listPindahDetail.add(p);
        }
        return listPindahDetail;
    }
    public static void insert(Connection con,PindahDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPindah());
        ps.setString(2, detail.getKodeBarang());
        ps.setString(3, detail.getKodeBarcode());
        ps.setString(4, detail.getNamaBarang());
        ps.setString(5, detail.getKodeKategori());
        ps.setString(6, detail.getKodeJenis());
        ps.setString(7, detail.getKodeIntern());
        ps.setString(8, detail.getKadar());
        ps.setDouble(9, detail.getBerat());
        ps.setDouble(10, detail.getBeratAsli());
        ps.setDouble(11, detail.getBeratPersen());
        ps.setDouble(12, detail.getNilaiPokok());
        ps.setString(13, detail.getInputDate());
        ps.setString(14, detail.getInputBy());
        ps.setString(15, detail.getAsalBarang());
        ps.setString(16, detail.getStatus());
        ps.executeUpdate();
    }
}
