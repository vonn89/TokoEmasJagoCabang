/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class VerifikasiDAO {
    public static List<Verifikasi> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_verifikasi");
        List<Verifikasi> listVerifikasi = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Verifikasi v = new Verifikasi();
            v.setKodeUser(rs.getString(1));
            v.setJenis(rs.getString(2));
            v.setStatus(Boolean.parseBoolean(rs.getString(3)));
            listVerifikasi.add(v);
        }
        return listVerifikasi;
    }
    public static List<Verifikasi> getAllByUser(Connection con, String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_verifikasi where kode_user = ?");
        ps.setString(1, kodeUser);
        List<Verifikasi> listVerifikasi = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Verifikasi v = new Verifikasi();
            v.setKodeUser(rs.getString(1));
            v.setJenis(rs.getString(2));
            v.setStatus(Boolean.parseBoolean(rs.getString(3)));
            listVerifikasi.add(v);
        }
        return listVerifikasi;
    }
    public static void insert(Connection con,Verifikasi v)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_verifikasi values(?,?,?)");
        ps.setString(1, v.getKodeUser());
        ps.setString(2, v.getJenis());
        ps.setString(3, String.valueOf(v.isStatus()));
        ps.executeUpdate();
    }
    public static void update(Connection con,Verifikasi v) throws Exception{
        PreparedStatement ps =con.prepareStatement("update tm_verifikasi set status=? where kode_user=? and jenis=?");
        ps.setString(1, String.valueOf(v.isStatus()));
        ps.setString(2, v.getKodeUser());
        ps.setString(3, v.getJenis());
        ps.executeUpdate();
                        
    }
    public static void deleteByKodeUser(Connection con,String kodeUser)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_verifikasi where kode_user = ? ");
        ps.setString(1, kodeUser);
        ps.executeUpdate();
    }
    
}
