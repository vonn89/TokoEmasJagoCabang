/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokOpnameHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokOpnameHeadDAO {
    public static List<StokOpnameHead> getAllByDate(
            Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_head "
                + "where mid(no_stok_opname,4,6) between ? and ? ");
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<StokOpnameHead> listStok = new ArrayList<>();
        while(rs.next()){
            StokOpnameHead s = new StokOpnameHead();
            s.setNoStokOpname(rs.getString(1));
            s.setTglStokOpname(rs.getString(2));
            s.setKodeJenis(rs.getString(3));
            s.setTotalQty(rs.getInt(4));
            s.setTotalBerat(rs.getDouble(5));
            listStok.add(s);
        }
        return listStok;
    }
    public static StokOpnameHead get(Connection con, String noStokOpname)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_opname_head "
                + " where no_stok_opname=? ");
        ps.setString(1, noStokOpname);
        ResultSet rs = ps.executeQuery();
        StokOpnameHead s = null;
        while(rs.next()){
            s = new StokOpnameHead();
            s.setNoStokOpname(rs.getString(1));
            s.setTglStokOpname(rs.getString(2));
            s.setKodeJenis(rs.getString(3));
            s.setTotalQty(rs.getInt(4));
            s.setTotalBerat(rs.getDouble(5));
        }
        return s;
    }
    public static void insert(Connection con,StokOpnameHead s)throws Exception{
        PreparedStatement ps= con.prepareStatement("insert into tt_stok_opname_head values(?,?,?,?,?)");
        ps.setString(1, s.getNoStokOpname());
        ps.setString(2, s.getTglStokOpname());
        ps.setString(3, s.getKodeJenis());
        ps.setInt(4, s.getTotalQty());
        ps.setDouble(5, s.getTotalBerat());
        ps.executeUpdate();
    }
    public static void update(Connection con, StokOpnameHead s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_opname_head set "
                + "tgl_stok_opname=?, kode_jenis=?, total_qty=?, total_berat=? where no_stok_opname=?");
        ps.setString(1, s.getTglStokOpname());
        ps.setString(2, s.getKodeJenis());
        ps.setInt(3, s.getTotalQty());
        ps.setDouble(4, s.getTotalBerat());
        ps.setString(5, s.getNoStokOpname());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_stok_opname,4)) from tt_stok_opname_head "
                + " where mid(no_stok_opname,4,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = "SO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = "SO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
}
