/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.PindahHead;
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
public class PindahHeadDAO {
    public static List<PindahHead> getAllByDateAndCabangAndStatusTerimaAndStatusBatal(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_pindah_head where mid(no_pindah,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PindahHead> allPindah = new ArrayList<>();
        while(rs.next()){
            PindahHead p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            allPindah.add(p);
        }
        return allPindah;
    }
    public static List<PindahHead> getAllByTglTerimaAndCabang(Connection con, String tglMulai, String tglAkhir, String kodeCabang)throws Exception{
        String sql = "select * from tt_pindah_head where left(tgl_terima, 10) between ? and ? "
                + " and status_terima like 'true' and status_batal like 'false'";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PindahHead> allPindah = new ArrayList<>();
        while(rs.next()){
            PindahHead p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            allPindah.add(p);
        }
        return allPindah;
    }
    public static PindahHead get(Connection con, String noPindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_head where no_pindah = ?");
        ps.setString(1, noPindah);
        ResultSet rs = ps.executeQuery();
        PindahHead p = null;
        while(rs.next()){
            p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
        }
        return p;
    }
    public static void insert(Connection con,PindahHead pindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_head values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pindah.getNoPindah());
        ps.setString(2, pindah.getTglPindah());
        ps.setString(3, pindah.getKodeCabang());
        ps.setString(4, pindah.getKodeGudang());
        ps.setDouble(5, pindah.getTotalQty());
        ps.setDouble(6, pindah.getTotalBerat());
        ps.setString(7, pindah.getKodeUser());
        ps.setString(8, pindah.getStatusTerima());
        ps.setString(9, pindah.getTglTerima());
        ps.setString(10, pindah.getUserTerima());
        ps.setString(11, pindah.getStatusBatal());
        ps.setString(12, pindah.getTglBatal());
        ps.setString(13, pindah.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,PindahHead pindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pindah_head set "
                + "tgl_pindah=?, kode_cabang=?, kode_gudang=?, total_qty=?, total_berat=?, kode_user=?, "
                + "status_terima=?, tgl_terima=?, user_terima=?, status_batal=?, tgl_batal=?, user_batal=? "
                + "where no_pindah=?");
        ps.setString(1, pindah.getTglPindah());
        ps.setString(2, pindah.getKodeCabang());
        ps.setString(3, pindah.getKodeGudang());
        ps.setDouble(4, pindah.getTotalQty());
        ps.setDouble(5, pindah.getTotalBerat());
        ps.setString(6, pindah.getKodeUser());
        ps.setString(7, pindah.getStatusTerima());
        ps.setString(8, pindah.getTglTerima());
        ps.setString(9, pindah.getUserTerima());
        ps.setString(10, pindah.getStatusBatal());
        ps.setString(11, pindah.getTglBatal());
        ps.setString(12, pindah.getUserBatal());
        ps.setString(13, pindah.getNoPindah());
        ps.executeUpdate();
    }
}
