/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SistemPusat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author yunaz
 */
public class SistemPusatDAO {
    public static void update(Connection con, SistemPusat b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_system set "
                + " version=?, tgl_system=?, tgl_mulai_database=?, harga_emas=? ");
        ps.setString(1, b.getVersion());
        ps.setString(2, b.getTglSystem());
        ps.setString(3, b.getTglMulaiDatabase());
        ps.setDouble(4, b.getHargaEmas());
        ps.executeUpdate();
    }
    public static SistemPusat get(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_system");
        ResultSet rs = ps.executeQuery();
        SistemPusat s = new SistemPusat();
        while(rs.next()){
            s.setVersion(rs.getString(1));
            s.setTglSystem(rs.getString(2));
            s.setTglMulaiDatabase(rs.getString(3));
            s.setHargaEmas(rs.getDouble(4));
        }
        return s;
    }
}
