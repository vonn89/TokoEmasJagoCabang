/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class KategoriDAO {
    public static List<Kategori> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori");
        ResultSet rs = ps.executeQuery();
        List<Kategori> listKategori = new ArrayList<>();
        while(rs.next()){
            Kategori k = new Kategori();
            k.setKodeKategori(rs.getString(1));
            k.setNamaKategori(rs.getString(2));
            k.setPersentaseEmas(rs.getDouble(3));
            k.setHargaBeli(rs.getDouble(4));
            k.setHargaJual(rs.getDouble(5));
            listKategori.add(k);
        }
        return listKategori;
    }
    public static Kategori get(Connection con,String kodeKategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori where kode_kategori = ?");
        ps.setString(1, kodeKategori);
        ResultSet rs = ps.executeQuery();
        Kategori k = null;
        if(rs.next()){
            k = new Kategori();
            k.setKodeKategori(rs.getString(1));
            k.setNamaKategori(rs.getString(2));
            k.setPersentaseEmas(rs.getDouble(3));
            k.setHargaBeli(rs.getDouble(4));
            k.setHargaJual(rs.getDouble(5));
        }
        return k;
    }
    public static void insert(Connection con,Kategori k) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori values(?,?,?,?,?)");
        ps.setString(1, k.getKodeKategori());
        ps.setString(2, k.getNamaKategori());
        ps.setDouble(3, k.getPersentaseEmas());
        ps.setDouble(4, k.getHargaBeli());
        ps.setDouble(5, k.getHargaJual());
        ps.executeUpdate();
    }
    public static void update(Connection con, Kategori k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori set "
                + " nama_kategori=?, persentase_emas=?, harga_beli=?, harga_jual=? "
                + " where kode_kategori=? ");
        ps.setString(1, k.getNamaKategori());
        ps.setDouble(2, k.getPersentaseEmas());
        ps.setDouble(3, k.getHargaBeli());
        ps.setDouble(4, k.getHargaJual());
        ps.setString(5, k.getKodeKategori());
        ps.executeUpdate();
    }
}
