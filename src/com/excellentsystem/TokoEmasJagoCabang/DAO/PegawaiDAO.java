/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pegawai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PegawaiDAO {
    public static List<Pegawai> getAll(Connection con)throws Exception{
        List<Pegawai> allPegawai = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_pegawai");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){  
            Pegawai p = new Pegawai();
            p.setKodePegawai(rs.getString(1));
            p.setNama(rs.getString(2));
            p.setJenisKelamin(rs.getString(3));
            p.setAlamat(rs.getString(4));
            p.setNoTelp(rs.getString(5));
            p.setJabatan(rs.getString(6));
            p.setTglMasuk(rs.getString(7));
            p.setTglKeluar(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPegawai.add(p);
        }
        return allPegawai;
    }
    public static void insert(Connection con, Pegawai p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_pegawai values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodePegawai());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getJenisKelamin());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getNoTelp());
        ps.setString(6, p.getJabatan());
        ps.setString(7, p.getTglMasuk());
        ps.setString(8, p.getTglKeluar());
        ps.setString(9, p.getStatus());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_pegawai");
        ps.executeUpdate();
    }
}
