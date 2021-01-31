/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.KeuanganCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author Xtreme
 */
public class KeuanganCabangDAO {
    public static String getId(Connection con, String kodeCabang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan_cabang "
                + "where mid(no_keuangan,8,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,KeuanganCabang keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan_cabang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, keu.getNoKeuangan());
        ps.setString(2, keu.getTglKeuangan());
        ps.setString(3, keu.getKodeCabang());
        ps.setString(4, keu.getTipeKasir());
        ps.setString(5, keu.getTipeKeuangan());
        ps.setString(6, keu.getKategori());
        ps.setString(7, keu.getDeskripsi());
        ps.setDouble(8, keu.getJumlahRp());
        ps.setString(9, keu.getKodeUser());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan_cabang where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
