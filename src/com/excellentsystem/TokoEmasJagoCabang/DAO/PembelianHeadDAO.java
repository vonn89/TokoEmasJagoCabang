/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianHead;
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
public class PembelianHeadDAO {
    public static List<PembelianHead> getAllDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_head where "
                + "mid(no_pembelian,5,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PembelianHead> listPembelian = new ArrayList<>();
        while(rs.next()){
            PembelianHead p = new PembelianHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setTotalBeratKotor(rs.getDouble(7));
            p.setTotalBeratBersih(rs.getDouble(8));
            p.setTotalBeratPersen(rs.getDouble(9));
            p.setTotalPembelian(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatus(rs.getString(12));
            p.setTglBatal(rs.getString(13));
            p.setUserBatal(rs.getString(14));
            listPembelian.add(p);
        }
        return listPembelian;
    }
    public static PembelianHead get(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head where no_pembelian = ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        PembelianHead p = null;
        while(rs.next()){
            p = new PembelianHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeMember(rs.getString(3));
            p.setNama(rs.getString(4));
            p.setAlamat(rs.getString(5));
            p.setNoTelp(rs.getString(6));
            p.setTotalBeratKotor(rs.getDouble(7));
            p.setTotalBeratBersih(rs.getDouble(8));
            p.setTotalBeratPersen(rs.getDouble(9));
            p.setTotalPembelian(rs.getDouble(10));
            p.setKodeSales(rs.getString(11));
            p.setStatus(rs.getString(12));
            p.setTglBatal(rs.getString(13));
            p.setUserBatal(rs.getString(14));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembelian,4)) from tt_pembelian_head "
                + " where mid(no_pembelian,5,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = "BLN-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = "BLN-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void update(Connection con, PembelianHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_head set "
                + " tgl_pembelian=?, kode_member=?, nama=?, alamat=?, no_telp=?, "
                + " total_berat_kotor=?, total_berat_bersih=?, total_berat_persen=?, total_pembelian=?, "
                + " kode_sales=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_pembelian=?");
        ps.setString(1, p.getTglPembelian());
        ps.setString(2, p.getKodeMember());
        ps.setString(3, p.getNama());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getNoTelp());
        ps.setDouble(6, p.getTotalBeratKotor());
        ps.setDouble(7, p.getTotalBeratBersih());
        ps.setDouble(8, p.getTotalBeratPersen());
        ps.setDouble(9, p.getTotalPembelian());
        ps.setString(10, p.getKodeSales());
        ps.setString(11, p.getStatus());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.setString(14, p.getNoPembelian());
        ps.executeUpdate();
    }
    public static void insert(Connection con, PembelianHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_head "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembelian());
        ps.setString(2, p.getTglPembelian());
        ps.setString(3, p.getKodeMember());
        ps.setString(4, p.getNama());
        ps.setString(5, p.getAlamat());
        ps.setString(6, p.getNoTelp());
        ps.setDouble(7, p.getTotalBeratKotor());
        ps.setDouble(8, p.getTotalBeratBersih());
        ps.setDouble(9, p.getTotalBeratPersen());
        ps.setDouble(10, p.getTotalPembelian());
        ps.setString(11, p.getKodeSales());
        ps.setString(12, p.getStatus());
        ps.setString(13, p.getTglBatal());
        ps.setString(14, p.getUserBatal());
        ps.executeUpdate();
    }
}
