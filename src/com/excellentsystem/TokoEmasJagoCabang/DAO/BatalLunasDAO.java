/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.BatalLunas;
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
public class BatalLunasDAO {
    public static List<BatalLunas> getAllByDate(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_batal_lunas  "
            + " where mid(no_batal_lunas,4,6) between ? and ? ");
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<BatalLunas> listBatalLunas = new ArrayList<>();
        while(rs.next()){
            BatalLunas b = new BatalLunas();
            b.setNoBatalLunas(rs.getString(1));
            b.setNoHutang(rs.getString(2));
            b.setTglBatalLunas(rs.getString(3));
            b.setTotalPinjaman(rs.getInt(4));
            b.setTotalBerat(rs.getDouble(5));
            b.setUserBatal(rs.getString(6));
            listBatalLunas.add(b);
        }
        return listBatalLunas;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_batal_lunas,4)) from tt_batal_lunas "
                + " where mid(no_batal_lunas,4,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = "BS-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = "BS-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void insert(Connection con,BatalLunas b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_batal_lunas values(?,?,?,?,?,?)");
        ps.setString(1, b.getNoBatalLunas());
        ps.setString(2, b.getNoHutang());
        ps.setString(3, b.getTglBatalLunas());
        ps.setDouble(4, b.getTotalPinjaman());
        ps.setDouble(5, b.getTotalBerat());
        ps.setString(6, b.getUserBatal());
        ps.executeUpdate();
    }
}
