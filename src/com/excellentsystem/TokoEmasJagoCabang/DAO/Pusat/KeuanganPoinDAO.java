/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.KeuanganPoin;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Xtreme
 */
public class KeuanganPoinDAO {
    public static void insert(Connection con,KeuanganPoin keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan_poin values(?,?,?,?,?,?,?)");
        ps.setString(1, keu.getNoKeuangan());
        ps.setString(2, keu.getTglKeuangan());
        ps.setString(3, keu.getKodeCabang());
        ps.setString(4, keu.getKategori());
        ps.setString(5, keu.getDeskripsi());
        ps.setDouble(6, keu.getJumlahRp());
        ps.setString(7, keu.getKodeUser());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan_poin where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
