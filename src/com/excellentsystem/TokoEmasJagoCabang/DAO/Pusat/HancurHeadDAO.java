/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.HancurHead;
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
public class HancurHeadDAO {
    public static List<HancurHead> getAllByDateAndCabangAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String kodeCabang, String status)throws Exception{
        String sql = "select * from tt_hancur_head where mid(no_hancur,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<HancurHead> listHancurHead = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            HancurHead h = new HancurHead();
            h.setNoHancur(rs.getString(1));
            h.setTglHancur(rs.getString(2));
            h.setKodeCabang(rs.getString(3));
            h.setTotalQty(rs.getInt(4));
            h.setTotalBerat(rs.getDouble(5));
            h.setTotalBeratAsli(rs.getDouble(6));
            h.setTotalBeratPersen(rs.getDouble(7));
            h.setKodeUser(rs.getString(8));
            h.setStatus(rs.getString(9));
            h.setTglBatal(rs.getString(10));
            h.setUserBatal(rs.getString(11));
            listHancurHead.add(h);
        }
        return listHancurHead;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_hancur,4)) from tt_hancur_head "
                + "where mid(no_hancur,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "HB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "HB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void update(Connection con, HancurHead h)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_hancur_head set"
                + " tgl_hancur=?, kode_cabang=?, total_qty=?, total_berat=?,"
                + " total_berat_asli=?, total_berat_persen=?, kode_user=?, "
                + " status=?, tgl_batal=?, user_batal=? where no_hancur=?");
        ps.setString(1, h.getTglHancur());
        ps.setString(2, h.getKodeCabang());
        ps.setInt(3, h.getTotalQty());
        ps.setDouble(4, h.getTotalBerat());
        ps.setDouble(5, h.getTotalBeratAsli());
        ps.setDouble(6, h.getTotalBeratPersen());
        ps.setString(7, h.getKodeUser());
        ps.setString(8, h.getKodeUser());
        ps.setString(9, h.getStatus());
        ps.setString(10, h.getTglBatal());
        ps.setString(11, h.getUserBatal());
        ps.executeUpdate();
    }
    public static void insert(Connection con,HancurHead h)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_hancur_head values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, h.getNoHancur());
        ps.setString(2, h.getTglHancur());
        ps.setString(3, h.getKodeCabang());
        ps.setInt(4, h.getTotalQty());
        ps.setDouble(5, h.getTotalBerat());
        ps.setDouble(6, h.getTotalBeratAsli());
        ps.setDouble(7, h.getTotalBeratPersen());
        ps.setString(8, h.getKodeUser());
        ps.setString(9, h.getStatus());
        ps.setString(10, h.getTglBatal());
        ps.setString(11, h.getUserBatal());
        ps.executeUpdate();
    }
}
