/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.Sistem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Yunaz
 */
public class SistemDAO {
    public static Sistem get(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_system");
        ResultSet rs = ps.executeQuery();
        Sistem s = null;
        while(rs.next()){
            s = new Sistem();
            s.setVersion(rs.getString(1));
            s.setTglSystem(rs.getString(2));
            s.setTglMulaiDatabase(rs.getString(3));
            s.setIpServerPusat(rs.getString(4));
            s.setBiayaKartuMember(rs.getDouble(5));
            s.setGetPoinPembayaranPenjualan(rs.getDouble(6));
            s.setNilaiPoin(rs.getDouble(7));
            s.setHargaRosok(rs.getDouble(8));
        }
        return s;
    }
    public static void update(Connection con, Sistem s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_system set "
                + "version=?, tgl_system=?, tgl_mulai_database=?, ip_server_pusat=?, "
                + "biaya_kartu_member=?, get_poin_pembayaran_penjualan=?, nilai_poin=?, harga_rosok=?");
        ps.setString(1, s.getVersion());
        ps.setString(2, s.getTglSystem());
        ps.setString(3, s.getTglMulaiDatabase());
        ps.setString(4, s.getIpServerPusat());
        ps.setDouble(5, s.getBiayaKartuMember());
        ps.setDouble(6, s.getGetPoinPembayaranPenjualan());
        ps.setDouble(7, s.getNilaiPoin());
        ps.setDouble(8, s.getHargaRosok());
        ps.executeUpdate();
    }
}
