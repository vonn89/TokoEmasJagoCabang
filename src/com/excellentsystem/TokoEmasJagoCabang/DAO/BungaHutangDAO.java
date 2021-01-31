/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.BungaHutang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class BungaHutangDAO {
    public static List<BungaHutang> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_bunga_hutang");
        ResultSet rs = ps.executeQuery();
        List<BungaHutang> listBunga = new ArrayList<>();
        while(rs.next()){
            BungaHutang b = new BungaHutang();
            b.setNoUrut(rs.getInt(1));
            b.setMinJumlahRp(rs.getDouble(2));
            b.setMaxJumlahRp(rs.getDouble(3));
            b.setBungaPersen(rs.getDouble(4));
            listBunga.add(b);
        }
        return listBunga;
    }
    public static void insert(Connection con, BungaHutang b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_bunga_hutang values (?,?,?,?)");
        ps.setInt(1, b.getNoUrut());
        ps.setDouble(2, b.getMinJumlahRp());
        ps.setDouble(3, b.getMaxJumlahRp());
        ps.setDouble(4, b.getBungaPersen());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_bunga_hutang");
        ps.executeUpdate();
    }
}
