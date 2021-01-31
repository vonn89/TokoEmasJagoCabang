/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.KategoriTransaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class KategoriTransaksiDAO {
    public static List<KategoriTransaksi> getAllByStatus(Connection con,String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_transaksi where status = ?");
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<KategoriTransaksi> listKategori = new ArrayList<>();
        while(rs.next()){
            KategoriTransaksi k = new KategoriTransaksi();
            k.setKodeTransaksi(rs.getString(1));
            k.setKategoriTransaksi(rs.getString(2));
            k.setStatus(rs.getString(3));
            listKategori.add(k);
        }
        return listKategori;
    }
    public static KategoriTransaksi get(Connection con,String kodeKategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_transaksi where kode_transaksi = ?");
        ps.setString(1, kodeKategori);
        ResultSet rs = ps.executeQuery();
        KategoriTransaksi k = null;
        if(rs.next()){
            k = new KategoriTransaksi();
            k.setKodeTransaksi(rs.getString(1));
            k.setKategoriTransaksi(rs.getString(2));
            k.setStatus(rs.getString(3));
        }
        return k;
    }
    public static void insert(Connection con, KategoriTransaksi k) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_transaksi values(?,?,?)");
        ps.setString(1, k.getKodeTransaksi());
        ps.setString(2, k.getKategoriTransaksi());
        ps.setString(3, k.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, KategoriTransaksi k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori_transaksi set "
                + " kategori_transaksi=?, status=? where kode_transaksi=?");
        ps.setString(1, k.getKategoriTransaksi());
        ps.setString(2, k.getStatus());
        ps.setString(3, k.getKodeTransaksi());
        ps.executeUpdate();
    }
}
