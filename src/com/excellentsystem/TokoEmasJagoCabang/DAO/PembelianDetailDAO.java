/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.PembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianDetailDAO {
    public static List<PembelianDetail> getAllDateAndStatusAndStatusAmbil(
            Connection con, String tglMulai, String tglAkhir, String status, String statusAmbil)throws Exception{
        String sql = "select * from tt_pembelian_detail where no_pembelian in ("
                + " select no_pembelian from tt_pembelian_head where "
                + " mid(no_pembelian,5,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " ) ";
        if(!statusAmbil.equals("%"))
            sql = sql + " and status_ambil = '"+statusAmbil+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> detail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setQty(rs.getInt(6));
            d.setBeratKotor(rs.getDouble(7));
            d.setBeratBersih(rs.getDouble(8));
            d.setPersentaseEmas(rs.getDouble(9));
            d.setBeratPersen(rs.getDouble(10));
            d.setSuratPembelian(rs.getString(11));
            d.setHargaKomp(rs.getDouble(12));
            d.setHargaBeli(rs.getDouble(13));
            d.setStatusAmbil(rs.getString(14));
            d.setNoAmbilBarang(rs.getString(15));
            detail.add(d);
        }
        return detail;
    }
    public static List<PembelianDetail> getAllByNoAmbil(Connection con, String noAmbil)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail "
                + " where no_ambil_barang = ?");
        ps.setString(1, noAmbil);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> detail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setQty(rs.getInt(6));
            d.setBeratKotor(rs.getDouble(7));
            d.setBeratBersih(rs.getDouble(8));
            d.setPersentaseEmas(rs.getDouble(9));
            d.setBeratPersen(rs.getDouble(10));
            d.setSuratPembelian(rs.getString(11));
            d.setHargaKomp(rs.getDouble(12));
            d.setHargaBeli(rs.getDouble(13));
            d.setStatusAmbil(rs.getString(14));
            d.setNoAmbilBarang(rs.getString(15));
            detail.add(d);
        }
        return detail;
    }
    public static List<PembelianDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail "
                + " where no_pembelian = ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> detail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setQty(rs.getInt(6));
            d.setBeratKotor(rs.getDouble(7));
            d.setBeratBersih(rs.getDouble(8));
            d.setPersentaseEmas(rs.getDouble(9));
            d.setBeratPersen(rs.getDouble(10));
            d.setSuratPembelian(rs.getString(11));
            d.setHargaKomp(rs.getDouble(12));
            d.setHargaBeli(rs.getDouble(13));
            d.setStatusAmbil(rs.getString(14));
            d.setNoAmbilBarang(rs.getString(15));
            detail.add(d);
        }
        return detail;
    }
    public static PembelianDetail get(Connection con, String noPembelian, int noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail "
                + " where no_pembelian = ? and no_urut = ? ");
        ps.setString(1, noPembelian);
        ps.setInt(2, noUrut);
        ResultSet rs = ps.executeQuery();
        PembelianDetail d = null;
        while(rs.next()){
            d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setQty(rs.getInt(6));
            d.setBeratKotor(rs.getDouble(7));
            d.setBeratBersih(rs.getDouble(8));
            d.setPersentaseEmas(rs.getDouble(9));
            d.setBeratPersen(rs.getDouble(10));
            d.setSuratPembelian(rs.getString(11));
            d.setHargaKomp(rs.getDouble(12));
            d.setHargaBeli(rs.getDouble(13));
            d.setStatusAmbil(rs.getString(14));
            d.setNoAmbilBarang(rs.getString(15));
        }
        return d;
    }
    public static void insert(Connection con,PembelianDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPembelian());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeKategori());
        ps.setString(4, d.getKodeJenis());
        ps.setString(5, d.getNamaBarang());
        ps.setInt(6, d.getQty());
        ps.setDouble(7, d.getBeratKotor());
        ps.setDouble(8, d.getBeratBersih());
        ps.setDouble(9, d.getPersentaseEmas());
        ps.setDouble(10, d.getBeratPersen());
        ps.setString(11, d.getSuratPembelian());
        ps.setDouble(12, d.getHargaKomp());
        ps.setDouble(13, d.getHargaBeli());
        ps.setString(14, d.getStatusAmbil());
        ps.setString(15, d.getNoAmbilBarang());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_detail set "
                + "kode_kategori=?, kode_jenis=?, nama_barang=?, qty=?, "
                + "berat_kotor=?, berat_bersih=?, persentase_emas=?, berat_persen=?, surat_pembelian=?, "
                + "harga_komp=?, harga_beli=?, status_ambil=?, no_ambil_barang=? "
                + "where no_pembelian=? and no_urut=?");
        ps.setString(1, d.getKodeKategori());
        ps.setString(2, d.getKodeJenis());
        ps.setString(3, d.getNamaBarang());
        ps.setInt(4, d.getQty());
        ps.setDouble(5, d.getBeratKotor());
        ps.setDouble(6, d.getBeratBersih());
        ps.setDouble(7, d.getPersentaseEmas());
        ps.setDouble(8, d.getBeratPersen());
        ps.setString(9, d.getSuratPembelian());
        ps.setDouble(10, d.getHargaKomp());
        ps.setDouble(11, d.getHargaBeli());
        ps.setString(12, d.getStatusAmbil());
        ps.setString(13, d.getNoAmbilBarang());
        ps.setString(14, d.getNoPembelian());
        ps.setInt(15, d.getNoUrut());
        ps.executeUpdate();
    }
}
