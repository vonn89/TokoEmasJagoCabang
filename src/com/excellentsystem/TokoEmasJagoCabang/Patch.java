/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import static com.excellentsystem.TokoEmasJagoCabang.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoCabang.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoCabang.Model.StokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author excellent
 */
public class Patch {
    public static String getNoKeuangan(Connection con, String cabang, String tanggal)throws Exception{
        DecimalFormat df = new DecimalFormat("0000");
        ResultSet rs = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan "
                + " where mid(no_keuangan,8,6)= '"+tglSystem.format(tglBarang.parse(tanggal))+"'").executeQuery();
        if(rs.next())
            return cabang+"-KK-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(rs.getInt(1)+1);
        else
            return cabang+"-KK-"+tglSystem.format(tglBarang.parse(tanggal))+"-0001";
    }
    private static String getTglSistem(Connection con, String kodeCabang)throws Exception{
        ResultSet rs = con.prepareStatement("select tanggal_system from tokomasjago_"+kodeCabang+".tm_system").executeQuery();
        if(rs.next())
            return rs.getString(1);
        else
            return "2000-01-01";
    }
    private static void importSistem(Connection con, String kodeCabang, String tglMulai)throws Exception{
        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tm_system a,tokomasjago_"+kodeCabang+".tm_system b "
            + " set version='2.0.0', a.tgl_system=b.tanggal_system, a.tgl_mulai_database='"+tglMulai+"', a.biaya_kartu_member=b.biaya_kartu_member, "
            + " a.get_poin_pembayaran_penjualan=b.get_poin_pembayaran_penjualan, a.nilai_poin=b.nilai_poin ").executeUpdate();
    }
    private static void importBungaHutang(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tm_bunga_hutang").executeUpdate();
        ResultSet rs = con.prepareStatement("select * from tokomasjago_"+kodeCabang+".tm_bunga_hutang").executeQuery();
        PreparedStatement ps = con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_bunga_hutang values(?,?,?,?)");
        int i = 1;
        final int batchSize = 1000;
        int count = 0;
        while(rs.next()){
            ps.setInt(1, i);
            ps.setDouble(2, rs.getDouble(1));
            ps.setDouble(3, rs.getDouble(2));
            ps.setDouble(4, rs.getDouble(3));
            ps.addBatch();
            i++;
            if(++count % batchSize == 0) 
                ps.executeBatch();
        }
        ps.executeBatch();
    }
    private static void importJenisBarang(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tm_jenis").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_jenis "
            + " select kode_jenis,nama_jenis,kode_kategori,status_verifikasi from tokomasjago_"+kodeCabang+".tm_jenis").executeUpdate();
    }
    private static void importKategoriBarang(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tm_kategori").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_kategori "
            + " select kode_kategori,nama_kategori,harga_persen,harga_beli,harga_jual from tokomasjago_"+kodeCabang+".tm_kategori").executeUpdate();
    }
    private static void importLogHarga(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_log_harga "
            + " select tanggal, a.kode_kategori, persentase_emas, a.harga_beli, a.harga_jual "
            + " from tokomasjago_"+kodeCabang+".tm_log_harga a, tokoemasjago_"+kodeCabang+".tm_kategori b "
            + " where a.kode_kategori=b.kode_kategori").executeUpdate();
    }
    private static void importKategoriTransaksi(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tm_kategori_transaksi").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_kategori_transaksi "
            + " select kode_kategori_transaksi,jenis_transaksi,status from tokomasjago_"+kodeCabang+".tm_kategori_transaksi").executeUpdate();
    }
    private static void importBarang(Connection con, String kodeCabang, String tglSystemFormatSystem)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_barang "
            + " select kode_barang,kode_barcode,nama_barang,a.kode_kategori,kode_jenis,kode_intern,kadar,berat,berat_asli, "
            + " round(b.persentase_emas*berat_asli/100,3),round(berat_asli*b.harga_beli),input_date,input_by,status_barang,'true'  "
            + " from tokomasjago_"+kodeCabang+".tm_barang_"+kodeCabang+" a, tokoemasjago_"+kodeCabang+".tm_kategori b where a.kode_kategori=b.kode_kategori").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_barang "
            + " select kode_barang,kode_barcode,nama_barang,a.kode_kategori,kode_jenis,kode_intern,kadar,berat,berat_asli, "
            + " round(b.persentase_emas*berat_asli/100,3),round(berat_asli*b.harga_beli),input_date,input_by,status_barang,'false'  "
            + " from tokomasjago_"+kodeCabang+".tm_barang_jual_"+kodeCabang+" a, tokoemasjago_"+kodeCabang+".tm_kategori b where a.kode_kategori=b.kode_kategori and "
            + " a.kode_barcode in (select kode_barcode from tokomasjago_"+kodeCabang+".tt_penjualan_detail_"+kodeCabang+" where mid(no_penjualan,8,6) ='"+tglSystemFormatSystem+"')").executeUpdate();
    }
    private static void importHutang(Connection con, String kodeCabang, String tglSystem)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_hutang_head "
            + " select no_hutang,tgl_hutang,'',nama,alamat,no_telp,total_berat,maks_pinjaman,total_hutang,lama_pinjam,bunga_persen,bunga_komp,bunga_rp,'',status_hilang, "
            + " kode_sales,status_lunas,tgl_lunas,sales_lunas,status_batal,tgl_batal,sales_batal "
            + " from tokomasjago_"+kodeCabang+".tm_hutang_head_"+kodeCabang+";").executeUpdate();
//        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_hutang_head "
//            + " select no_hutang,tgl_hutang,'',nama,alamat,no_telp,total_berat,maks_pinjaman,total_hutang,lama_pinjam,bunga_persen,bunga_komp,bunga_rp,'',status_hilang, "
//            + " kode_sales,status_lunas,tgl_lunas,sales_lunas,status_batal,tgl_batal,sales_batal "
//            + " from tokomasjago_"+kodeCabang+".tm_hutang_lunas_"+kodeCabang+" where tgl_lunas = '"+tglSystem+"';").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tm_hutang_detail "
            + " select no_hutang,no_urut,kode_kategori,nama_barang,berat,round(pinjaman*100/70) "
            + " from tokomasjago_"+kodeCabang+".tm_hutang_detail_"+kodeCabang+" "
            + " where no_hutang in (select no_hutang from tokoemasjago_"+kodeCabang+".tm_hutang_head)").executeUpdate();
    }
    private static void importPembelian(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pembelian_head "
            + " select no_pembelian,tgl_pembelian,'',nama,alamat,no_telp,total_berat_kotor,total_berat_bersih, "
            + " 0,total_harga,input_by,if(status_batal='true','false','true'),'2000-01-01 00:00:00','' "
            + " from tokomasjago_"+kodeCabang+".tt_pembelian_head_"+kodeCabang+" where status_batal='false'").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pembelian_head "
            + " select a.no_pembelian,tgl_pembelian,'',nama,alamat,no_telp,total_berat_kotor,total_berat_bersih, "
            + " 0,total_harga,input_by,if(status_batal='true','false','true'),tgl_batal_beli,b.kode_sales  "
            + " from tokomasjago_"+kodeCabang+".tt_pembelian_head_"+kodeCabang+" a, tokomasjago_"+kodeCabang+".tt_batal_beli_"+kodeCabang+" b "
            + " where a.no_pembelian=b.no_pembelian and a.status_batal='true'").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pembelian_detail "
            + " select no_pembelian,no_urut,kode_kategori,kode_jenis,nama_barang,qty,berat_kotor,berat_bersih,berat_persen,round(berat_persen*berat_bersih/100,3), "
            + " surat_pembelian,harga_beli,harga_beli,status_ambil,no_ambil_barang "
            + " from tokomasjago_"+kodeCabang+".tt_pembelian_detail_"+kodeCabang+"").executeUpdate();
        final int batchSize = 1000;
        int count = 0;
        ResultSet rs = con.prepareStatement("select no_pembelian,round(sum(berat_persen*berat_bersih/100),3) from tokoemasjago_"+kodeCabang+".tt_pembelian_detail "
                + " group by no_pembelian").executeQuery();
        PreparedStatement ps = con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_pembelian_head set total_berat_persen = ? where no_pembelian = ?");
        while(rs.next()){
            ps.setDouble(1, rs.getDouble(2));
            ps.setString(2, rs.getString(1));
            ps.addBatch();
            if(++count % batchSize == 0) 
                ps.executeBatch();
        }
        ps.executeBatch();
    }
    private static void importPemesanan(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pemesanan_head "
            + " select no_pemesanan,tgl_pemesanan,'',nama,alamat,no_telp,keterangan,total_pembayaran,titip_uang,sisa_pembayaran, "
            + " kode_sales,status_ambil,tgl_ambil,sales_ambil,'false','2000-01-01 00:00:00','' "
            + " from tokomasjago_"+kodeCabang+".tt_pemesanan_head_"+kodeCabang+" where status_ambil='false'").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pemesanan_detail  "
            + " select * from tokomasjago_"+kodeCabang+".tt_pemesanan_detail_"+kodeCabang+" "
            + " where no_pemesanan in (select no_pemesanan from tokoemasjago_"+kodeCabang+".tt_pemesanan_head)").executeUpdate();
    }
    private static void importPenjualan(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_penjualan_head "
            + " select a.no_penjualan,tgl_penjualan,'',nama,alamat,no_telp,total_berat,total_harga,total_ongkos,a.grandtotal,pembayaran,kekurangan,'',a.kode_sales,'true','2000-01-01 00:00:00','' "
            + " from tokomasjago_"+kodeCabang+".tt_penjualan_head_"+kodeCabang+" a, tokomasjago_"+kodeCabang+".tt_kurang_bayar_"+kodeCabang+" b "
            + " where a.no_penjualan=b.no_penjualan and status_kurang = 'true' and status_batal = 'false' and b.status = 'open';").executeUpdate();
        
        ResultSet rs = con.prepareStatement("select no_penjualan,1,kode_barcode,kode_barang,kode_kategori,kode_jenis,nama_barang,berat,"
            + " 0,harga_jual,harga_kategori,ongkos  "
            + " from tokomasjago_"+kodeCabang+".tt_penjualan_detail_"+kodeCabang+" "
            + " where no_penjualan in (select no_penjualan from tokoemasjago_"+kodeCabang+".tt_penjualan_head)").executeQuery();
        PreparedStatement ps = con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_penjualan_detail values (?,?,?,?,?,?,?,?,?,?,?,?)");
        final int batchSize = 1000;
        int count = 0;
        int noUrut = 1;
        String noPenjualan = "";
        while(rs.next()){
            if(noPenjualan.equals(rs.getString(1))){
                noUrut++;
            }else{
                noPenjualan = rs.getString(1);
                noUrut = 1;
            }
            ps.setString(1, rs.getString(1));
            ps.setInt(2, noUrut);
            ps.setString(3, rs.getString(3));
            ps.setString(4, rs.getString(4));
            ps.setString(5, rs.getString(5));
            ps.setString(6, rs.getString(6));
            ps.setString(7, rs.getString(7));
            ps.setDouble(8, rs.getDouble(8));
            ps.setDouble(9, rs.getDouble(9));
            ps.setDouble(10, rs.getDouble(10));
            ps.setDouble(11, rs.getDouble(11));
            ps.setDouble(12, rs.getDouble(12));
            ps.addBatch();
            if(++count % batchSize == 0) 
                ps.executeBatch();
        }
        ps.executeBatch();
    }
    private static void importPembayaran(Connection con, String kodeCabang)throws Exception{
        ResultSet rs = con.prepareStatement("select '',a.tgl_penjualan as tanggal,a.no_penjualan,'Cash','',b.pembayaran,a.kode_sales,'true','2000-01-01 00:00:00','' "
                + " from tokomasjago_"+kodeCabang+".tt_penjualan_head_"+kodeCabang+" a, tokomasjago_"+kodeCabang+".tt_kurang_bayar_"+kodeCabang+" b "
                + " where a.no_penjualan = b.no_penjualan and a.status_kurang='true' and b.status='open' order by tanggal").executeQuery();
        PreparedStatement ps = con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_pembayaran_penjualan values(?,?,?,?,?,?,?,?,?,?)");
        final int batchSize = 1000;
        int count = 0;
        DecimalFormat df = new DecimalFormat("0000");
        String tanggal = "2000-01-01";
        String noPembayaran = "PB-"+tglSystem.format(tglBarang.parse(tanggal))+"-0001";
        while(rs.next()){
            if(tanggal.equals(rs.getString(2).substring(0,10))){
                int noUrut = Integer.parseInt(noPembayaran.substring(10));
                noPembayaran = "PB-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(noUrut+1);
            }else{
                tanggal = rs.getString(2).substring(0,10);
                noPembayaran = "PB-"+tglSystem.format(tglBarang.parse(tanggal))+"-0001";
            }
            ps.setString(1, noPembayaran);
            ps.setString(2, rs.getString(2));
            ps.setString(3, rs.getString(3));
            ps.setString(4, rs.getString(4));
            ps.setString(5, rs.getString(5));
            ps.setDouble(6, rs.getDouble(6));
            ps.setString(7, rs.getString(7));
            ps.setString(8, rs.getString(8));
            ps.setString(9, rs.getString(9));
            ps.setString(10, rs.getString(10));
            ps.addBatch();
            if(++count % batchSize == 0) 
                ps.executeBatch();
        }
        ps.executeBatch();
    }
    private static void importServis(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_servis "
            + " select no_servis,tgl_servis,'',nama,alamat,no_telp,nama_barang,berat,kategori_servis,biaya_servis,jenis_pembayaran,keterangan_pembayaran,kode_sales, "
            + " status_ambil,tgl_ambil,sales_ambil,status_batal,tgl_batal,sales_batal from tokomasjago_"+kodeCabang+".tt_servis_"+kodeCabang+" "
            + " where status_ambil='false' and status_batal='false' ;").executeUpdate();
    }
    private static void importStokHutang(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_stok_hutang "
            + " select * from tokomasjago_"+kodeCabang+".tt_stok_hutang_"+kodeCabang+" "
            + " where stok_awal!=0 or stok_masuk!=0 or stok_keluar!=0 or stok_akhir!=0 ").executeUpdate();
    }
    private static void importStokBarang(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_stok_barang "
            + " select tanggal,a.kode_barang,a.kode_barcode,a.kode_gudang,a.kode_gudang,a.kode_kategori,a.kode_jenis, "
            + " stock_awal,berat_awal, b.berat_persen*stock_awal, b.nilai_pokok*stock_awal, "
            + " stock_masuk,berat_masuk, b.berat_persen*stock_masuk, b.nilai_pokok*stock_masuk,  "
            + " stock_keluar,berat_keluar, b.berat_persen*stock_keluar, b.nilai_pokok*stock_keluar,  "
            + " stock_akhir,berat_akhir, b.berat_persen*stock_akhir, b.nilai_pokok*stock_akhir "
            + " from tokomasjago_"+kodeCabang+".tt_barang_saldo_"+kodeCabang+" a, tokoemasjago_"+kodeCabang+".tm_barang b "
            + " where a.kode_barcode = b.kode_barcode").executeUpdate();
    }
    private static void importStokBarangBalenan(Connection con, String kodeCabang, String tglMulai, String tglSistem)throws Exception{
        //recreate stok barang gudang balenan
        LocalDate date = LocalDate.parse(tglMulai);
        while(date.isBefore(LocalDate.parse(tglSistem).plusDays(1))){
            List<StokBarang> listStok = new ArrayList<>();
            ResultSet rs = con.prepareStatement("select substring(a.no_pembelian,5,6),kode_kategori,kode_jenis,sum(qty),round(sum(berat_kotor),3),round(sum(berat_persen),3),sum(harga_beli) "
                + " from tokoemasjago_"+kodeCabang+".tt_pembelian_head a, tokoemasjago_"+kodeCabang+".tt_pembelian_detail b "
                + " where a.no_pembelian = b.no_pembelian and a.status = 'true' "
                + " and substring(a.no_pembelian,5,6) = '"+date.format(DateTimeFormatter.ofPattern("yyMMdd"))+"' "
                + " group by substring(a.no_pembelian,5,6),kode_jenis").executeQuery();
            while(rs.next()){
                StokBarang s = new StokBarang();
                s.setTanggal(date.toString());
                s.setKodeKategori(rs.getString(2));
                s.setKodeJenis(rs.getString(3));
                s.setStokMasuk(rs.getInt(4));
                s.setBeratMasuk(rs.getDouble(5));
                s.setBeratPersenMasuk(rs.getDouble(6));
                s.setNilaiMasuk(rs.getDouble(7));
                s.setStokKeluar(0);
                s.setBeratKeluar(0);
                s.setBeratPersenKeluar(0);
                s.setNilaiKeluar(0);
                listStok.add(s);
            }
            
            rs = con.prepareStatement("select substring(no_ambil_barang,8,6),kode_kategori,kode_jenis,sum(qty),round(sum(berat_kotor),3),round(sum(berat_persen),3),sum(harga_beli)  "
                + " from tokoemasjago_"+kodeCabang+".tt_pembelian_detail "
                + " where status_ambil='true' "
                + " and substring(no_ambil_barang,8,6) = '"+date.format(DateTimeFormatter.ofPattern("yyMMdd"))+"' "
                + " group by substring(no_ambil_barang,8,6),kode_jenis").executeQuery();
            while(rs.next()){
                boolean statusStok = true;
                for(StokBarang s : listStok){
                    if(s.getKodeJenis().equals(rs.getString(3))){
                        s.setStokKeluar(rs.getInt(4));
                        s.setBeratKeluar(rs.getDouble(5));
                        s.setBeratPersenKeluar(rs.getDouble(6));
                        s.setNilaiKeluar(rs.getDouble(7));
                        statusStok = false;
                    }
                }
                if(statusStok){
                    StokBarang s = new StokBarang();
                    s.setTanggal(date.toString());
                    s.setKodeKategori(rs.getString(2));
                    s.setKodeJenis(rs.getString(3));
                    s.setStokKeluar(rs.getInt(4));
                    s.setBeratKeluar(rs.getDouble(5));
                    s.setBeratPersenKeluar(rs.getDouble(6));
                    s.setNilaiKeluar(rs.getDouble(7));
                    listStok.add(s);
                }
            }

            PreparedStatement update = con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_stok_barang set "
                    + " stok_masuk=stok_masuk+?, berat_masuk=round(berat_masuk+?,3), berat_persen_masuk=round(berat_persen_masuk+?,3), nilai_masuk=nilai_masuk+?, "
                    + " stok_keluar=stok_keluar+?, berat_keluar=round(berat_keluar+?,3), berat_persen_keluar=round(berat_persen_keluar+?,3), nilai_keluar=nilai_keluar+?, "
                    + " stok_akhir=stok_awal+stok_masuk-stok_keluar, "
                    + " berat_akhir=round(berat_awal+berat_masuk-berat_keluar,3), "
                    + " berat_persen_akhir=round(berat_persen_awal+berat_persen_masuk-berat_persen_keluar,3), "
                    + " nilai_akhir=nilai_awal+nilai_masuk-nilai_keluar "
                    + " where tanggal=? and kode_cabang=? and kode_gudang=? and kode_kategori=? and kode_jenis=?");
            PreparedStatement insert = con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_stok_barang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(StokBarang s : listStok){
                rs = con.prepareStatement("select * from tokoemasjago_"+kodeCabang+".tt_stok_barang "
                    + " where tanggal = '"+date.toString()+"' and kode_gudang = '"+kodeCabang+"-BLN' and kode_jenis = '"+s.getKodeJenis()+"'").executeQuery();
                if(rs.next()){
                    //update
                    update.setInt(1, s.getStokMasuk());
                    update.setDouble(2, s.getBeratMasuk());
                    update.setDouble(3, s.getBeratPersenMasuk());
                    update.setDouble(4, s.getNilaiMasuk());
                    update.setInt(5, s.getStokKeluar());
                    update.setDouble(6, s.getBeratKeluar());
                    update.setDouble(7, s.getBeratPersenKeluar());
                    update.setDouble(8, s.getNilaiKeluar());
                    update.setString(9, s.getTanggal());
                    update.setString(10, kodeCabang);
                    update.setString(11, kodeCabang+"-BLN");
                    update.setString(12, s.getKodeKategori());
                    update.setString(13, s.getKodeJenis());
                    update.executeUpdate();
                }else{
                    //insert
                    insert.setString(1, s.getTanggal());
                    insert.setString(2, "");
                    insert.setString(3, "");
                    insert.setString(4, kodeCabang);
                    insert.setString(5, kodeCabang+"-BLN");
                    insert.setString(6, s.getKodeKategori());
                    insert.setString(7, s.getKodeJenis());
                    insert.setInt(8, 0);
                    insert.setDouble(9, 0);
                    insert.setDouble(10, 0);
                    insert.setDouble(11, 0);
                    insert.setInt(12, s.getStokMasuk());
                    insert.setDouble(13, s.getBeratMasuk());
                    insert.setDouble(14, s.getBeratPersenMasuk());
                    insert.setDouble(15, s.getNilaiMasuk());
                    insert.setInt(16, s.getStokKeluar());
                    insert.setDouble(17, s.getBeratKeluar());
                    insert.setDouble(18, s.getBeratPersenKeluar());
                    insert.setDouble(19, s.getNilaiKeluar());
                    insert.setInt(20, s.getStokMasuk()-s.getStokKeluar());
                    insert.setDouble(21, s.getBeratMasuk()-s.getBeratKeluar());
                    insert.setDouble(22, s.getBeratPersenMasuk()-s.getBeratPersenKeluar());
                    insert.setDouble(23, s.getNilaiMasuk()-s.getNilaiKeluar());
                    insert.executeUpdate();
                }
            }
            con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_stok_barang "
                    + " select '"+date.plusDays(1).toString()+"',kode_barang,kode_barcode,kode_cabang,kode_gudang,kode_kategori,kode_jenis,"
                    + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir,"
                    + " 0,0,0,0,"
                    + " 0,0,0,0,"
                    + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir from tokoemasjago_"+kodeCabang+".tt_stok_barang "
                    + " where tanggal = '"+date.toString()+"' and kode_gudang = '"+kodeCabang+"-BLN' and stok_akhir!=0 ").executeUpdate();
            date = date.plusDays(1);
        }
    }
    private static void importKeuangan(Connection con, String kodeCabang)throws Exception{
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_keuangan "
                + " select concat('"+kodeCabang+"-',no_keuangan),tanggal,tipe_kasir,'Kas','Saldo Awal Kasir','Saldo Awal',sum(if(tipe_transaksi='D',jumlah_rp,jumlah_rp*-1)),'System' "
                + " from tokomasjago_"+kodeCabang+".tt_keuangan_"+kodeCabang+" where tipe_kasir = 'Kasir' ").executeUpdate();
        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_keuangan "
                + " select concat('"+kodeCabang+"-',no_keuangan),tanggal,tipe_kasir,'Kas','Saldo Awal RR','Saldo Awal',sum(if(tipe_transaksi='D',jumlah_rp,jumlah_rp*-1)),'System' "
                + " from tokomasjago_"+kodeCabang+".tt_keuangan_"+kodeCabang+" where tipe_kasir = 'RR' ").executeUpdate();
//        con.prepareStatement("insert into tokoemasjago_"+kodeCabang+".tt_keuangan "
//                + " select concat('"+kodeCabang+"-',no_keuangan),tanggal,tipe_kasir,'Kas',kategori,deskripsi,if(tipe_transaksi='D',jumlah_rp,jumlah_rp*-1),kode_sales "
//                + " from tokomasjago_"+kodeCabang+".tt_keuangan_"+kodeCabang+" where kategori = 'Saldo Awal RR' order by no_keuangan limit 1").executeUpdate();
//        con.prepareStatement(" insert into tokoemasjago_"+kodeCabang+".tt_keuangan "
//                + " select concat('"+kodeCabang+"-',no_keuangan),tanggal,tipe_kasir,'Kas',kategori,deskripsi,sum(if(tipe_transaksi='D',jumlah_rp,jumlah_rp*-1)),kode_sales "
//                + " from tokomasjago_"+kodeCabang+".tt_keuangan_"+kodeCabang+"  "
//                + " where kategori not in ('Saldo Awal Kasir','Saldo Awal RR','Hutang Lunas','Batal Hutang Lunas','Hutang Bunga','Batal Hutang Bunga')").executeUpdate();
//        System.out.println(new Date()+" insert tt_keuangan");
        
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan set kategori = 'Terima Pemesanan' where kategori = 'Pemesanan'").executeUpdate();
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan set kategori = 'Ambil Pemesanan' where kategori = 'Pesanan Diambil'").executeUpdate();
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan set kategori = 'Batal Pemesanan' where kategori = 'Pemesanan Batal'").executeUpdate();
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan set kategori = replace(kategori, 'Penjualan Cabang', 'Penjualan Cabang - ') "
//                + " where kategori like 'Penjualan Cabang%'").executeUpdate();
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan set kategori = replace(kategori, 'Pembelian Cabang', 'Pembelian Cabang - ') "
//                + " where kategori like 'Pembelian Cabang%'").executeUpdate();
//        con.prepareStatement("update tokoemasjago_"+kodeCabang+".tt_keuangan a, tokomasjago_"+kodeCabang+".tt_kurang_bayar_"+kodeCabang+" b "
//                + " set a.keterangan=b.no_penjualan "
//                + " where a.keterangan=b.no_kurang_bayar and a.kategori in ('Orang Kurang','Orang Bayar')").executeUpdate();
//        System.out.println(new Date()+" update tt_keuangan");
//        
//        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tt_keuangan "
//                + " where keterangan in ( select no_penjualan from tokomasjago_"+kodeCabang+".tt_batal_jual_"+kodeCabang+" )").executeUpdate();
//        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tt_keuangan where kategori = 'Penjualan Batal'").executeUpdate();
//        System.out.println(new Date()+" delete from tt_keuangan penjualan batal");
//        
//        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tt_keuangan "
//                + " where keterangan in ( select no_pembelian from tokomasjago_"+kodeCabang+".tt_batal_beli_"+kodeCabang+" )").executeUpdate();
//        con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tt_keuangan where kategori = 'Pembelian Batal'").executeUpdate();
//        System.out.println(new Date()+" delete from tt_keuangan pembelian batal");
//        
//        PreparedStatement insert = con.prepareStatement("insert into tt_keuangan values(?,?,?,?,?,?,?,?)");
//        PreparedStatement delete = con.prepareStatement("delete from tokoemasjago_"+kodeCabang+".tt_keuangan where keterangan = ?");
//        
//        final int batchSize = 5000;
//        int count = 0;
//        ResultSet rs = con.prepareStatement("select keterangan from tokoemasjago_"+kodeCabang+".tt_keuangan where kategori like 'Batal Penjualan Cabang%'").executeQuery();
//        while(rs.next()){
//            delete.setString(1, rs.getString(1));
//            delete.addBatch();
//            if(++count % batchSize == 0) 
//                delete.executeBatch();
//        }
//        delete.executeBatch();
//        System.out.println(new Date()+" delete from tt_keuangan batal penjualan cabang");
//        
//        count = 0;
//        rs = con.prepareStatement("select keterangan from tokoemasjago_"+kodeCabang+".tt_keuangan where kategori = 'Hutang Batal'").executeQuery();
//        while(rs.next()){
//            delete.setString(1, rs.getString(1));
//            delete.addBatch();
//            if(++count % batchSize == 0) 
//                delete.executeBatch();
//        }
//        delete.executeBatch();
//        System.out.println(new Date()+" delete from tt_keuangan hutang batal");
//
//        rs = con.prepareStatement("select tgl_lunas,no_hutang,total_hutang,bunga_rp,sales_lunas from tm_hutang_head where status_lunas = 'true' ").executeQuery();
//        while(rs.next()){
//            insert.setString(1, getNoKeuangan(con, kodeCabang, rs.getString(1).substring(0, 10)));
//            insert.setString(2, rs.getString(1));
//            insert.setString(3, "RR");
//            insert.setString(4, "Kas");
//            insert.setString(5, "Hutang Lunas");
//            insert.setString(6, rs.getString(2));
//            insert.setDouble(7, rs.getDouble(3));
//            insert.setString(8, rs.getString(5));
//            insert.executeUpdate();
//            
//            insert.setString(1, getNoKeuangan(con, kodeCabang, rs.getString(1).substring(0, 10)));
//            insert.setString(2, rs.getString(1));
//            insert.setString(3, "RR");
//            insert.setString(4, "Kas");
//            insert.setString(5, "Hutang Bunga");
//            insert.setString(6, rs.getString(2));
//            insert.setDouble(7, rs.getDouble(4));
//            insert.setString(8, rs.getString(5));
//            insert.executeUpdate();
//        }
//        System.out.println(new Date()+" insert tt_keuangan hutang lunas");
    }
    public static String moveDatabase(Connection con, String kodeCabang){
        try{
            String status = "true";
            con.setAutoCommit(false);

            System.out.println(new Date()+" startImporting");
            String tglMulai = "2016-05-09";
            String tglSistem = tglBarang.format(tglSystem.parse(getTglSistem(con, kodeCabang)));
            importSistem(con, kodeCabang, tglMulai);
            System.out.println(new Date()+" importSistem");
            
            importBungaHutang(con, kodeCabang);
            System.out.println(new Date()+" importBungaHutang");
            
            importJenisBarang(con, kodeCabang);
            System.out.println(new Date()+" importJenisBarang");
            
            //setting berat ciok kategori & harga beli/nilai pokok
            importKategoriBarang(con, kodeCabang);
            System.out.println(new Date()+" importKategoriBarang");
            
//            importLogHarga(con, kodeCabang);
//            System.out.println(new Date()+" importLogHarga");
            
            importKategoriTransaksi(con, kodeCabang);
            System.out.println(new Date()+" importKategoriTransaksi");
            
            //data cabang & data pegawai import dari program
            //data user buat baru dari program
            
            //persentase emas & nilai pokok diambil dari table tm_kategori database lama
            importBarang(con, kodeCabang, tglSystem.format(tglBarang.parse(tglSistem)));
            System.out.println(new Date()+" importBarang");
            
            importHutang(con, kodeCabang, tglSistem);
            System.out.println(new Date()+" importHutang");
            
            //import pembelian yang belum di ambil balenan
//            importPembelian(con, kodeCabang);
//            System.out.println(new Date()+" importPembelian");
            
            importPemesanan(con, kodeCabang);
            System.out.println(new Date()+" importPemesanan");
            
            importPenjualan(con, kodeCabang);
            System.out.println(new Date()+" importPenjualan");
            
            importPembayaran(con, kodeCabang);
            System.out.println(new Date()+" importPembayaran");
            
            importServis(con, kodeCabang);
            System.out.println(new Date()+" importServis");
            
            importStokHutang(con, kodeCabang);
            System.out.println(new Date()+" importStokHutang");
            
            importStokBarang(con, kodeCabang);
            System.out.println(new Date()+" importStokBarang");
            
//            importStokBarangBalenan(con, kodeCabang, tglMulai, tglSistem);
//            System.out.println(new Date()+" importStokBarangBalenan");
             
            importKeuangan(con, kodeCabang);
            System.out.println(new Date()+" importKeuangan");
            

            if(status.equals("true")){
                con.commit();
            }else{
                con.rollback();
            }
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    
}
