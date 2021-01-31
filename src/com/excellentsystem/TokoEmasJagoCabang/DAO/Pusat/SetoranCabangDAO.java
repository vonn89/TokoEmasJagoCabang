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
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.SetoranCabang;
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
public class SetoranCabangDAO {
    public static List<SetoranCabang> getAllByDateAndCabangAndTipeKasirAndStatusTerimaAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String tipeKasir, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_setoran_cabang where mid(no_setor,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<SetoranCabang> listSetor = new ArrayList<>();
        if(rs.next()){
            SetoranCabang s = new SetoranCabang();
            s.setNoSetor(rs.getString(1));
            s.setTglSetor(rs.getString(2));
            s.setKodeCabang(rs.getString(3));
            s.setTipeKasir(rs.getString(4));
            s.setJumlahRp(rs.getDouble(5));
            s.setKodeUser(rs.getString(6));
            s.setStatusTerima(rs.getString(7));
            s.setTglTerima(rs.getString(8));
            s.setUserTerima(rs.getString(9));
            s.setStatusBatal(rs.getString(10));
            s.setTglBatal(rs.getString(11));
            s.setUserBatal(rs.getString(12));
            listSetor.add(s);
        }
        return listSetor;
    }
    public static SetoranCabang get(Connection con, String noSetor)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_setoran_cabang where no_setor = ? ");
        ps.setString(1, noSetor);
        ResultSet rs = ps.executeQuery();
        SetoranCabang s = null;
        if(rs.next()){
            s = new SetoranCabang();
            s.setNoSetor(rs.getString(1));
            s.setTglSetor(rs.getString(2));
            s.setKodeCabang(rs.getString(3));
            s.setTipeKasir(rs.getString(4));
            s.setJumlahRp(rs.getDouble(5));
            s.setKodeUser(rs.getString(6));
            s.setStatusTerima(rs.getString(7));
            s.setTglTerima(rs.getString(8));
            s.setUserTerima(rs.getString(9));
            s.setStatusBatal(rs.getString(10));
            s.setTglBatal(rs.getString(11));
            s.setUserBatal(rs.getString(12));
        }
        return s;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_setor,4)) from tt_setoran_cabang "
                + "where mid(no_setor,8,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return cabang.getKodeCabang()+"-ST-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            return cabang.getKodeCabang()+"-ST-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
    }
    public static void insert(Connection con,SetoranCabang setor)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_setoran_cabang "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, setor.getNoSetor());
        ps.setString(2, setor.getTglSetor());
        ps.setString(3, setor.getKodeCabang());
        ps.setString(4, setor.getTipeKasir());
        ps.setDouble(5, setor.getJumlahRp());
        ps.setString(6, setor.getKodeUser());
        ps.setString(7, setor.getStatusTerima());
        ps.setString(8, setor.getTglTerima());
        ps.setString(9, setor.getUserTerima());
        ps.setString(10, setor.getStatusBatal());
        ps.setString(11, setor.getTglBatal());
        ps.setString(12, setor.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SetoranCabang setor)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_setoran_cabang set "
                + "tgl_setor=?, kode_cabang=?, tipe_kasir=?, jumlah_rp=?, kode_user=?, "
                + "status_terima=?, tgl_terima=?, user_terima=?, status_batal=?, tgl_batal=?, user_batal=? "
                + "where no_setor=? ");
        ps.setString(1, setor.getTglSetor());
        ps.setString(2, setor.getKodeCabang());
        ps.setString(3, setor.getTipeKasir());
        ps.setDouble(4, setor.getJumlahRp());
        ps.setString(5, setor.getKodeUser());
        ps.setString(6, setor.getStatusTerima());
        ps.setString(7, setor.getTglTerima());
        ps.setString(8, setor.getUserTerima());
        ps.setString(9, setor.getStatusBatal());
        ps.setString(10, setor.getTglBatal());
        ps.setString(11, setor.getUserBatal());
        ps.setString(12, setor.getNoSetor());
        ps.executeUpdate();
    }
}
