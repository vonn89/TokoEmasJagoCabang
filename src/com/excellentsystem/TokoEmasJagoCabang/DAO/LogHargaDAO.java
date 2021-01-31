/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.LogHarga;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class LogHargaDAO {
    public static List<LogHarga> getAll(Connection con, String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_log_harga where tanggal = ?");
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        List<LogHarga> listLogHarga = new ArrayList<>();
        while(rs.next()){
            LogHarga l = new LogHarga();
            l.setTanggal(rs.getString(1));
            l.setKodeKategori(rs.getString(2));
            l.setPersentaseEmas(rs.getDouble(3));
            l.setHargaBeli(rs.getDouble(4));
            l.setHargaJual(rs.getDouble(5));
            listLogHarga.add(l);
        }
        return listLogHarga;
    }
    public static void insert(Connection con, LogHarga l)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_log_harga values(?,?,?,?,?)");
        ps.setString(1, l.getTanggal());
        ps.setString(2, l.getKodeKategori());
        ps.setDouble(3, l.getPersentaseEmas());
        ps.setDouble(4, l.getHargaBeli());
        ps.setDouble(5, l.getHargaJual());
        ps.executeUpdate();
    }
    public static void update(Connection con, LogHarga l)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_log_harga set "
                + " persentase_emas=?, harga_beli=?, harga_jual=? "
                + " where tanggal=? and kode_kategori=?");
        ps.setDouble(1, l.getPersentaseEmas());
        ps.setDouble(2, l.getHargaBeli());
        ps.setDouble(3, l.getHargaJual());
        ps.setString(4, l.getTanggal());
        ps.setString(5, l.getKodeKategori());
        ps.executeUpdate();
    }
}
