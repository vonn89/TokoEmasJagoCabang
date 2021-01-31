/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.TpBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author excellent
 */
public class TpBarangDAO {
    public static String getKodeBarangFromTanggal(String tanggal){
        return tanggal.replaceAll("0", "X").replaceAll("1", "N").replaceAll("2", "I")
                .replaceAll("3", "C").replaceAll("4", "O").replaceAll("5", "D").replaceAll("6", "E")
                .replaceAll("7", "M").replaceAll("8", "U").replaceAll("9", "S");
    }
    public static String getKodeBarang(Connection con, String kodeJenis)throws Exception{
        String kode = getKodeBarangFromTanggal(tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        PreparedStatement ps = con.prepareStatement("select kode_barang from tp_barang where kode_jenis = ? and kode_barang like ? ");
        ps.setString(1, kodeJenis);
        ps.setString(2, kodeJenis+"-"+kode+"-%");
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int no = Integer.valueOf(rs.getString(1).split("-")[2])+1;
            return kodeJenis+"-"+kode+"-"+no;
        }else
            return kodeJenis+"-"+kode+"-1";
        
    }
    public static String getKodeBarcode(Connection con)throws Exception{
        DecimalFormat df = new DecimalFormat("00000000");
        PreparedStatement ps = con.prepareStatement("select max(kode_barcode) from tp_barang");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return df.format(rs.getInt(1)+1);
        else
            return df.format(1);
    }
    public static void update(Connection con, TpBarang t)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tp_barang set kode_barang=?, kode_barcode=?  where kode_jenis=?");
        ps.setString(1, t.getKodeBarang());
        ps.setString(2, t.getKodeBarcode());
        ps.setString(3, t.getKodeJenis());
        ps.executeUpdate();
    }
}
