/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.TambahUangCabang;
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
public class TambahUangCabangDAO {
    public static List<TambahUangCabang> getAllByDateAndCabangAndTipeKasirAndStatus(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String tipeKasir, String status)throws Exception{
        String sql = "select * from tt_tambah_uang_cabang where mid(no_tambah_uang,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<TambahUangCabang> allTambahUang = new ArrayList<>();
        while(rs.next()){
            TambahUangCabang mub = new TambahUangCabang();
            mub.setNoTambahUang(rs.getString(1));
            mub.setTglTambah(rs.getString(2));
            mub.setKodeCabang(rs.getString(3));
            mub.setTipeKasir(rs.getString(4));
            mub.setJumlahRp(rs.getDouble(5));
            mub.setKodeUser(rs.getString(6));
            mub.setStatusTerima(rs.getString(7));
            mub.setTglTerima(rs.getString(8));
            mub.setUserTerima(rs.getString(9));
            mub.setStatusBatal(rs.getString(10));
            mub.setTglBatal(rs.getString(11));
            mub.setUserBatal(rs.getString(12));
            allTambahUang.add(mub);
        }
        return allTambahUang;
    }
    public static TambahUangCabang get(Connection con, String noTambah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_tambah_uang_cabang where no_tambah_uang = ? ");
        ps.setString(1, noTambah);
        ResultSet rs = ps.executeQuery();
        TambahUangCabang mub = null;
        while(rs.next()){
            mub = new TambahUangCabang();
            mub.setNoTambahUang(rs.getString(1));
            mub.setTglTambah(rs.getString(2));
            mub.setKodeCabang(rs.getString(3));
            mub.setTipeKasir(rs.getString(4));
            mub.setJumlahRp(rs.getDouble(5));
            mub.setKodeUser(rs.getString(6));
            mub.setStatusTerima(rs.getString(7));
            mub.setTglTerima(rs.getString(8));
            mub.setUserTerima(rs.getString(9));
            mub.setStatusBatal(rs.getString(10));
            mub.setTglBatal(rs.getString(11));
            mub.setUserBatal(rs.getString(12));
        }
        return mub;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_tambah_uang,4)) from tt_tambah_uang_cabang "
                + "where mid(no_tambah_uang,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "TU-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "TU-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, TambahUangCabang mub)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_tambah_uang_cabang values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, mub.getNoTambahUang());
        ps.setString(2, mub.getTglTambah());
        ps.setString(3, mub.getKodeCabang());
        ps.setString(4, mub.getTipeKasir());
        ps.setDouble(5, mub.getJumlahRp());
        ps.setString(6, mub.getKodeUser());
        ps.setString(7, mub.getStatusTerima());
        ps.setString(8, mub.getTglTerima());
        ps.setString(9, mub.getUserTerima());
        ps.setString(10, mub.getStatusBatal());
        ps.setString(11, mub.getTglBatal());
        ps.setString(12, mub.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, TambahUangCabang MUB)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_tambah_uang_cabang set "
                + " tgl_tambah=?, kode_cabang=?, tipe_kasir=?, jumlah_rp=?, kode_user=?, "
                + " status_terima=?, tgl_terima=?, user_terima=?, "
                + " status_batal=?, tgl_batal=?, user_batal=?"
                + " where no_tambah_uang=? ");
        ps.setString(1, MUB.getTglTambah());
        ps.setString(2, MUB.getKodeCabang());
        ps.setString(3, MUB.getTipeKasir());
        ps.setDouble(4, MUB.getJumlahRp());
        ps.setString(5, MUB.getKodeUser());
        ps.setString(6, MUB.getStatusTerima());
        ps.setString(7, MUB.getTglTerima());
        ps.setString(8, MUB.getUserTerima());
        ps.setString(9, MUB.getStatusBatal());
        ps.setString(10, MUB.getTglBatal());
        ps.setString(11, MUB.getUserBatal());
        ps.setString(12, MUB.getNoTambahUang());
        ps.executeUpdate();
    }
}
