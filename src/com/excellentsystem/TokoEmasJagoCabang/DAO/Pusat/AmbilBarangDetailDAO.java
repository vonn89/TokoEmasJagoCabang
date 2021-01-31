/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang.DAO.Pusat;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Pusat.AmbilBarangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class AmbilBarangDetailDAO {
    public static List<AmbilBarangDetail> getAllByDateAndCabangAndStatusTerimaAndStatusBatal(Connection con, 
            String tglMulai, String tglAkhir, String kodeCabang, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_ambil_barang_detail where no_ambil_barang in "
            + " (select no_ambil_barang from tt_ambil_barang_head  "
            + " where mid(no_ambil_barang,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<AmbilBarangDetail> listAmbilBarangDetail = new ArrayList<>();
        while(rs.next()){
            AmbilBarangDetail a = new AmbilBarangDetail();
            a.setNoAmbilBarang(rs.getString(1));
            a.setNoPembelian(rs.getString(2));
            a.setNoUrut(rs.getInt(3));
            a.setTglPembelian(rs.getString(4));
            a.setKodeSales(rs.getString(5));
            a.setKodeKategori(rs.getString(6));
            a.setKodeJenis(rs.getString(7));
            a.setNamaBarang(rs.getString(8));
            a.setQty(rs.getInt(9));
            a.setBeratKotor(rs.getDouble(10));
            a.setBeratBersih(rs.getDouble(11));
            a.setPersentaseEmas(rs.getDouble(12));
            a.setBeratPersen(rs.getDouble(13));
            a.setSuratPembelian(rs.getString(14));
            a.setHargaKomp(rs.getDouble(15));
            a.setHargaBeli(rs.getDouble(16));
            listAmbilBarangDetail.add(a);
        }
        return listAmbilBarangDetail;
    }
    public static List<AmbilBarangDetail> getAllByNoAmbilBarang(Connection con, String noAmbil)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_ambil_barang_detail  "
            + " where no_ambil_barang = ? ");
        ps.setString(1, noAmbil);
        ResultSet rs = ps.executeQuery();
        List<AmbilBarangDetail> listAmbilBarangDetail = new ArrayList<>();
        while(rs.next()){
            AmbilBarangDetail a = new AmbilBarangDetail();
            a.setNoAmbilBarang(rs.getString(1));
            a.setNoPembelian(rs.getString(2));
            a.setNoUrut(rs.getInt(3));
            a.setTglPembelian(rs.getString(4));
            a.setKodeSales(rs.getString(5));
            a.setKodeKategori(rs.getString(6));
            a.setKodeJenis(rs.getString(7));
            a.setNamaBarang(rs.getString(8));
            a.setQty(rs.getInt(9));
            a.setBeratKotor(rs.getDouble(10));
            a.setBeratBersih(rs.getDouble(11));
            a.setPersentaseEmas(rs.getDouble(12));
            a.setBeratPersen(rs.getDouble(13));
            a.setSuratPembelian(rs.getString(14));
            a.setHargaKomp(rs.getDouble(15));
            a.setHargaBeli(rs.getDouble(16));
            listAmbilBarangDetail.add(a);
        }
        return listAmbilBarangDetail;
    }
    public static void insert(Connection con, AmbilBarangDetail a)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_ambil_barang_detail "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, a.getNoAmbilBarang());
        ps.setString(2, a.getNoPembelian());
        ps.setInt(3, a.getNoUrut());
        ps.setString(4, a.getTglPembelian());
        ps.setString(5, a.getKodeSales());
        ps.setString(6, a.getKodeKategori());
        ps.setString(7, a.getKodeJenis());
        ps.setString(8, a.getNamaBarang());
        ps.setInt(9, a.getQty());
        ps.setDouble(10, a.getBeratKotor());
        ps.setDouble(11, a.getBeratBersih());
        ps.setDouble(12, a.getPersentaseEmas());
        ps.setDouble(13, a.getBeratPersen());
        ps.setString(14, a.getSuratPembelian());
        ps.setDouble(15, a.getHargaKomp());
        ps.setDouble(16, a.getHargaBeli());
        ps.executeUpdate();
    }
}
