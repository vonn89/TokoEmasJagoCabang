/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokOpnameDetailDAO {
    public static List<StokOpnameDetail> getAllByNoStok(Connection con, String noStok)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_detail "
                + " where no_stok_opname = ?");
        ps.setString(1, noStok);
        ResultSet rs = ps.executeQuery();
        List<StokOpnameDetail> listStok = new ArrayList<>();
        while(rs.next()){
            StokOpnameDetail d = new StokOpnameDetail();
            d.setNoStokOpname(rs.getString(1));
            d.setStokBarang(rs.getInt(2));
            d.setKodeBarcode(rs.getString(3));
            d.setKodeBarang(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setKodeIntern(rs.getString(6));
            d.setKadar(rs.getString(7));
            d.setBerat(rs.getDouble(8));
            d.setBeratAsli(rs.getDouble(9));
            listStok.add(d);
        }
        return listStok;
    }
    public static StokOpnameDetail get(Connection con, String noStok, String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_detail "
                + " where no_stok_opname = ? and kode_barcode = ?");
        ps.setString(1, noStok);
        ps.setString(2, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        StokOpnameDetail d = null;
        while(rs.next()){
            d = new StokOpnameDetail();
            d.setNoStokOpname(rs.getString(1));
            d.setStokBarang(rs.getInt(2));
            d.setKodeBarcode(rs.getString(3));
            d.setKodeBarang(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setKodeIntern(rs.getString(6));
            d.setKadar(rs.getString(7));
            d.setBerat(rs.getDouble(8));
            d.setBeratAsli(rs.getDouble(9));
        }
        return d;
    }
    public static void insert(Connection con,StokOpnameDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_opname_detail values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoStokOpname());
        ps.setInt(2, d.getStokBarang());
        ps.setString(3, d.getKodeBarcode());
        ps.setString(4, d.getKodeBarang());
        ps.setString(5, d.getNamaBarang());
        ps.setString(6, d.getKodeIntern());
        ps.setString(7, d.getKadar());
        ps.setDouble(8, d.getBerat());
        ps.setDouble(9, d.getBeratAsli());
        ps.executeUpdate();
    }
    public static void update(Connection con,StokOpnameDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_opname_detail set "
                + " stok_barang=?, kode_barang=?, nama_barang=?, "
                + " kode_intern=?, kadar=?, berat=?, berat_asli=? "
                + " where no_stok_opname=? and kode_barcode=? ");
        ps.setInt(1, d.getStokBarang());
        ps.setString(2, d.getKodeBarang());
        ps.setString(3, d.getNamaBarang());
        ps.setString(4, d.getKodeIntern());
        ps.setString(5, d.getKadar());
        ps.setDouble(6, d.getBerat());
        ps.setDouble(7, d.getBeratAsli());
        ps.setString(8, d.getNoStokOpname());
        ps.setString(9, d.getKodeBarcode());
        ps.executeUpdate();
    }
}
