/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangHead;
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
public class AmbilBarangHeadDAO {
    public static List<AmbilBarangHead> getAllByDateAndCabangAndStatusTerimaAndStatusBatal(Connection con, 
            String tglMulai, String tglAkhir, String kodeCabang, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_ambil_barang_head  "
            + " where mid(no_ambil_barang,8,6) between ? and ? ";
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
        List<AmbilBarangHead> listAmbilBarang = new ArrayList<>();
        while(rs.next()){
            AmbilBarangHead a = new AmbilBarangHead();
            a.setNoAmbilBarang(rs.getString(1));
            a.setTglAmbilBarang(rs.getString(2));
            a.setTglPembelianMulai(rs.getString(3));
            a.setTglPembelianAkhir(rs.getString(4));
            a.setKodeCabang(rs.getString(5));
            a.setTotalQty(rs.getInt(6));
            a.setTotalBeratKotor(rs.getDouble(7));
            a.setTotalBeratBersih(rs.getDouble(8));
            a.setTotalBeratPersen(rs.getDouble(9));
            a.setTotalPembelian(rs.getDouble(10));
            a.setKodeUser(rs.getString(11));
            a.setStatusTerima(rs.getString(12));
            a.setTglTerima(rs.getString(13));
            a.setUserTerima(rs.getString(14));
            a.setStatusBatal(rs.getString(15));
            a.setTglBatal(rs.getString(16));
            a.setUserBatal(rs.getString(17));
            listAmbilBarang.add(a);
        }
        return listAmbilBarang;
    }
    public static AmbilBarangHead get(Connection con, String noAmbil)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_ambil_barang_head  "
            + " where no_ambil_barang = ? ");
        ps.setString(1, noAmbil);
        ResultSet rs = ps.executeQuery();
        AmbilBarangHead a = null;
        while(rs.next()){
            a = new AmbilBarangHead();
            a.setNoAmbilBarang(rs.getString(1));
            a.setTglAmbilBarang(rs.getString(2));
            a.setTglPembelianMulai(rs.getString(3));
            a.setTglPembelianAkhir(rs.getString(4));
            a.setKodeCabang(rs.getString(5));
            a.setTotalQty(rs.getInt(6));
            a.setTotalBeratKotor(rs.getDouble(7));
            a.setTotalBeratBersih(rs.getDouble(8));
            a.setTotalBeratPersen(rs.getDouble(9));
            a.setTotalPembelian(rs.getDouble(10));
            a.setKodeUser(rs.getString(11));
            a.setStatusTerima(rs.getString(12));
            a.setTglTerima(rs.getString(13));
            a.setUserTerima(rs.getString(14));
            a.setStatusBatal(rs.getString(15));
            a.setTglBatal(rs.getString(16));
            a.setUserBatal(rs.getString(17));
        }
        return a;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_ambil_barang,4)) from tt_ambil_barang_head "
                + " where mid(no_ambil_barang,8,6)=? and kode_cabang like ?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ps.setString(2, cabang.getKodeCabang());
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-AB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-AB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void update(Connection con,AmbilBarangHead a)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_ambil_barang_head set "
                + " tgl_ambil_barang=?, tgl_pembelian_mulai=?, tgl_pembelian_akhir=?, "
                + " kode_cabang=?, total_qty=?, total_berat_kotor=?, total_berat_bersih=?, "
                + " total_berat_persen=?, total_pembelian=?, kode_user=?, "
                + " status_terima=?, tgl_terima=?, user_terima=?, "
                + " status_batal=?, tgl_batal=?, user_batal=? "
                + " where no_ambil_barang=? ");
        ps.setString(1, a.getTglAmbilBarang());
        ps.setString(2, a.getTglPembelianMulai());
        ps.setString(3, a.getTglPembelianAkhir());
        ps.setString(4, a.getKodeCabang());
        ps.setInt(5, a.getTotalQty());
        ps.setDouble(6, a.getTotalBeratKotor());
        ps.setDouble(7, a.getTotalBeratBersih());
        ps.setDouble(8, a.getTotalBeratPersen());
        ps.setDouble(9, a.getTotalPembelian());
        ps.setString(10, a.getKodeUser());
        ps.setString(11, a.getStatusTerima());
        ps.setString(12, a.getTglTerima());
        ps.setString(13, a.getUserTerima());
        ps.setString(14, a.getStatusBatal());
        ps.setString(15, a.getTglBatal());
        ps.setString(16, a.getUserBatal());
        ps.setString(17, a.getNoAmbilBarang());
        ps.executeUpdate();
    }
    public static void insert(Connection con, AmbilBarangHead a)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_ambil_barang_head "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, a.getNoAmbilBarang());
        ps.setString(2, a.getTglAmbilBarang());
        ps.setString(3, a.getTglPembelianMulai());
        ps.setString(4, a.getTglPembelianAkhir());
        ps.setString(5, a.getKodeCabang());
        ps.setInt(6, a.getTotalQty());
        ps.setDouble(7, a.getTotalBeratKotor());
        ps.setDouble(8, a.getTotalBeratBersih());
        ps.setDouble(9, a.getTotalBeratPersen());
        ps.setDouble(10, a.getTotalPembelian());
        ps.setString(11, a.getKodeUser());
        ps.setString(12, a.getStatusTerima());
        ps.setString(13, a.getTglTerima());
        ps.setString(14, a.getUserTerima());
        ps.setString(15, a.getStatusBatal());
        ps.setString(16, a.getTglBatal());
        ps.setString(17, a.getUserBatal());
        ps.executeUpdate();
    }
}
