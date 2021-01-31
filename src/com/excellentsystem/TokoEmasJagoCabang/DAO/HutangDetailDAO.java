/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.HutangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class HutangDetailDAO {
    public static List<HutangDetail> getAllByDateAndStatusLunasAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String statusLunas, String statusBatal)throws Exception{
        String sql = "select * from tm_hutang_detail where no_hutang in ("
            + " select no_hutang from tm_hutang_head "
            + " where mid(no_hutang,8,6) between "+tglSystem.format(tglBarang.parse(tglMulai))+" and "+tglSystem.format(tglBarang.parse(tglAkhir))+" ";
        if(!statusLunas.equals("%"))
            sql = sql + " and status_lunas = '"+statusLunas+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<HutangDetail> listDetail = new ArrayList<>();
        while(rs.next()){
            HutangDetail d = new HutangDetail();
            d.setNoHutang(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static List<HutangDetail> getAllByNoHutang(Connection con, String noHutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang_detail  "
            + " where no_hutang = ? ");
        ps.setString(1, noHutang);
        ResultSet rs = ps.executeQuery();
        List<HutangDetail> listDetail = new ArrayList<>();
        while(rs.next()){
            HutangDetail d = new HutangDetail();
            d.setNoHutang(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static HutangDetail get(Connection con, String noHutang, String noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang_detail  "
            + " where no_hutang = ? and no_urut = ?");
        ps.setString(1, noHutang);
        ps.setString(2, noUrut);
        ResultSet rs = ps.executeQuery();
        HutangDetail d = null;
        while(rs.next()){
            d = new HutangDetail();
            d.setNoHutang(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setNilaiJual(rs.getDouble(6));
        }
        return d;
    }
    public static void insert(Connection con, HutangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_hutang_detail values(?,?,?,?,?,?)");
        ps.setString(1, d.getNoHutang());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeKategori());
        ps.setString(4, d.getNamaBarang());
        ps.setDouble(5, d.getBerat());
        ps.setDouble(6, d.getNilaiJual());
        ps.executeUpdate();
    }
}
