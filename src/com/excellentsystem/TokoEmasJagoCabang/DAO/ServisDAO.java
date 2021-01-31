/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import static com.excellentsystem.TokoEmasJagoCabang.Main.cabang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.Servis;
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
public class ServisDAO {
    public static List<Servis> getAllByTglAmbil(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_servis where "
                + "left(tgl_ambil,10) between ? and ? and status_ambil = 'true' and status_batal = 'false'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Servis> listServis = new ArrayList<>();
        while(rs.next()){
            Servis s = new Servis();
            s.setNoServis(rs.getString(1));
            s.setTglServis(rs.getString(2));
            s.setKodeMember(rs.getString(3));
            s.setNama(rs.getString(4));
            s.setAlamat(rs.getString(5));
            s.setNoTelp(rs.getString(6));
            s.setNamaBarang(rs.getString(7));
            s.setBerat(rs.getDouble(8));
            s.setKategoriServis(rs.getString(9));
            s.setJumlahPembayaran(rs.getDouble(10));
            s.setJenisPembayaran(rs.getString(11));
            s.setKeteranganPembayaran(rs.getString(12));
            s.setKodeSales(rs.getString(13));
            s.setStatusAmbil(rs.getString(14));
            s.setTglAmbil(rs.getString(15));
            s.setSalesAmbil(rs.getString(16));
            s.setStatusBatal(rs.getString(17));
            s.setTglBatal(rs.getString(18));
            s.setUserBatal(rs.getString(19));
            listServis.add(s);
        }
        return listServis;
    }
    public static List<Servis> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String statusAmbil, String statusBatal)throws Exception{
        String sql = "select * from tt_servis where "
                + " mid(no_servis,8,6) between '"+tglSystem.format(tglBarang.parse(tglMulai))+"' and '"+tglSystem.format(tglBarang.parse(tglAkhir))+"' ";
        if(!statusAmbil.equals("%"))
            sql = sql + " and status_ambil = '"+statusAmbil+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Servis> listServis = new ArrayList<>();
        while(rs.next()){
            Servis s = new Servis();
            s.setNoServis(rs.getString(1));
            s.setTglServis(rs.getString(2));
            s.setKodeMember(rs.getString(3));
            s.setNama(rs.getString(4));
            s.setAlamat(rs.getString(5));
            s.setNoTelp(rs.getString(6));
            s.setNamaBarang(rs.getString(7));
            s.setBerat(rs.getDouble(8));
            s.setKategoriServis(rs.getString(9));
            s.setJumlahPembayaran(rs.getDouble(10));
            s.setJenisPembayaran(rs.getString(11));
            s.setKeteranganPembayaran(rs.getString(12));
            s.setKodeSales(rs.getString(13));
            s.setStatusAmbil(rs.getString(14));
            s.setTglAmbil(rs.getString(15));
            s.setSalesAmbil(rs.getString(16));
            s.setStatusBatal(rs.getString(17));
            s.setTglBatal(rs.getString(18));
            s.setUserBatal(rs.getString(19));
            listServis.add(s);
        }
        return listServis;
    }
    public static Servis get(Connection con, String noServis)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_servis where no_servis = ?");
        ps.setString(1, noServis);
        ResultSet rs = ps.executeQuery();
        Servis s = null;
        while(rs.next()){
            s = new Servis();
            s.setNoServis(rs.getString(1));
            s.setTglServis(rs.getString(2));
            s.setKodeMember(rs.getString(3));
            s.setNama(rs.getString(4));
            s.setAlamat(rs.getString(5));
            s.setNoTelp(rs.getString(6));
            s.setNamaBarang(rs.getString(7));
            s.setBerat(rs.getDouble(8));
            s.setKategoriServis(rs.getString(9));
            s.setJumlahPembayaran(rs.getDouble(10));
            s.setJenisPembayaran(rs.getString(11));
            s.setKeteranganPembayaran(rs.getString(12));
            s.setKodeSales(rs.getString(13));
            s.setStatusAmbil(rs.getString(14));
            s.setTglAmbil(rs.getString(15));
            s.setSalesAmbil(rs.getString(16));
            s.setStatusBatal(rs.getString(17));
            s.setTglBatal(rs.getString(18));
            s.setUserBatal(rs.getString(19));
        }
        return s;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_servis,4)) from tt_servis "
                + " where mid(no_servis,8,6)=?");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = ps.executeQuery();
        String id;
        if(rs.next())
            id = cabang.getKodeCabang()+"-SV-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            id = cabang.getKodeCabang()+"-SV-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-0001";
        return id;
    }
    public static void insert(Connection con, Servis servis)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_servis "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, servis.getNoServis());
        ps.setString(2, servis.getTglServis());
        ps.setString(3, servis.getKodeMember());
        ps.setString(4, servis.getNama());
        ps.setString(5, servis.getAlamat());
        ps.setString(6, servis.getNoTelp());
        ps.setString(7, servis.getNamaBarang());
        ps.setDouble(8, servis.getBerat());
        ps.setString(9, servis.getKategoriServis());
        ps.setDouble(10, servis.getJumlahPembayaran());
        ps.setString(11, servis.getJenisPembayaran());
        ps.setString(12, servis.getKeteranganPembayaran());
        ps.setString(13, servis.getKodeSales());
        ps.setString(14, servis.getStatusAmbil());
        ps.setString(15, servis.getTglAmbil());
        ps.setString(16, servis.getSalesAmbil());
        ps.setString(17, servis.getStatusBatal());
        ps.setString(18, servis.getTglBatal());
        ps.setString(19, servis.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, Servis servis)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_servis set "
                + "tgl_servis=?, kode_member=?, nama=?, alamat=?, no_telp=?, "
                + "nama_barang=?, berat=?, kategori_servis=?, jumlah_pembayaran=?, "
                + "jenis_pembayaran=?, keterangan_pembayaran=?, kode_sales=?, "
                + "status_ambil=?, tgl_ambil=?, sales_ambil=?, "
                + "status_batal=?, tgl_batal=?, user_batal=? where no_servis=? ");
        ps.setString(1, servis.getTglServis());
        ps.setString(2, servis.getKodeMember());
        ps.setString(3, servis.getNama());
        ps.setString(4, servis.getAlamat());
        ps.setString(5, servis.getNoTelp());
        ps.setString(6, servis.getNamaBarang());
        ps.setDouble(7, servis.getBerat());
        ps.setString(8, servis.getKategoriServis());
        ps.setDouble(9, servis.getJumlahPembayaran());
        ps.setString(10, servis.getJenisPembayaran());
        ps.setString(11, servis.getKeteranganPembayaran());
        ps.setString(12, servis.getKodeSales());
        ps.setString(13, servis.getStatusAmbil());
        ps.setString(14, servis.getTglAmbil());
        ps.setString(15, servis.getSalesAmbil());
        ps.setString(16, servis.getStatusBatal());
        ps.setString(17, servis.getTglBatal());
        ps.setString(18, servis.getUserBatal());
        ps.setString(19, servis.getNoServis());
        ps.executeUpdate();
    }
}
