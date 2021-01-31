/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PermintaanBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class PermintaanBarangDAO {
    
    public static List<PermintaanBarang> getAllByCabangAndLimit(Connection con, String kodeCabang, int limit)throws Exception{
        String sql = "select * from (select * from tt_permintaan_barang ";
        if(!kodeCabang.equals("%"))
            sql = sql + " where kode_cabang = '"+kodeCabang+"' ";
        sql = sql +  " order by tanggal desc limit "+limit+") a order by tanggal asc ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PermintaanBarang> allPermintaan = new ArrayList<>();
        while(rs.next()){
            PermintaanBarang p = new PermintaanBarang();
            p.setTanggal(rs.getString(1));
            p.setKodeCabang(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setStatus(rs.getString(5));
            allPermintaan.add(p);
        }
        return allPermintaan;
    }
    public static List<PermintaanBarang> getAllByDateAndCabang(Connection con, 
            String tglMulai, String tglAkhir, String kodeCabang)throws Exception{
        String sql = "select * from tt_permintaan_barang where left(tanggal,10) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PermintaanBarang> allPermintaan = new ArrayList<>();
        while(rs.next()){
            PermintaanBarang p = new PermintaanBarang();
            p.setTanggal(rs.getString(1));
            p.setKodeCabang(rs.getString(2));
            p.setKodeSales(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setStatus(rs.getString(5));
            allPermintaan.add(p);
        }
        return allPermintaan;
    }
    public static void insert(Connection con,PermintaanBarang p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_permintaan_barang values(?,?,?,?,?)");
        ps.setString(1, p.getTanggal());
        ps.setString(2, p.getKodeCabang());
        ps.setString(3, p.getKodeSales());
        ps.setString(4, p.getKeterangan());
        ps.setString(5, p.getStatus());
        ps.executeUpdate();
    }
}
