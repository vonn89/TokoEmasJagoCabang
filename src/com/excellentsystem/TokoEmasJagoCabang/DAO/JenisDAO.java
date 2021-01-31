/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Jenis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class JenisDAO {
    public static List<Jenis> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_jenis");
        ResultSet rs = ps.executeQuery();
        List<Jenis> listJenis = new ArrayList<>();
        while(rs.next()){
            Jenis j = new Jenis();
            j.setKodeJenis(rs.getString(1));
            j.setNamaJenis(rs.getString(2));
            j.setKodeKategori(rs.getString(3));
            j.setVerifikasi(rs.getString(4));
            listJenis.add(j);
        }
        return listJenis;
    }
    public static Jenis get(Connection con,String kodeJenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_jenis where kode_jenis = ?");
        ps.setString(1, kodeJenis);
        ResultSet rs = ps.executeQuery();
        Jenis j = null;
        if(rs.next()){
            j = new Jenis();
            j.setKodeJenis(rs.getString(1));
            j.setNamaJenis(rs.getString(2));
            j.setKodeKategori(rs.getString(3));
            j.setVerifikasi(rs.getString(4));
        }
        return j;
    }
    public static void insert(Connection con,Jenis j) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_jenis values(?,?,?,?)");
        ps.setString(1, j.getKodeJenis());
        ps.setString(2, j.getNamaJenis());
        ps.setString(3, j.getKodeKategori());
        ps.setString(4, j.getVerifikasi());
        ps.executeUpdate();
    }
    public static void update(Connection con, Jenis j) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_jenis set "
                + " nama_jenis=?, kode_kategori=?, verifikasi=? "
                + " where kode_jenis=? ");
        ps.setString(1, j.getNamaJenis());
        ps.setString(2, j.getKodeKategori());
        ps.setString(3, j.getVerifikasi());
        ps.setString(4, j.getKodeJenis());
        ps.executeUpdate();
    }
}
