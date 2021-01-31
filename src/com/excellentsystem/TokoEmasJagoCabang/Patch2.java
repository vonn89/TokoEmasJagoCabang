/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import com.excellentsystem.TokoEmasJagoCabang.DAO.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoCabang.DAO.VerifikasiDAO;
import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import com.excellentsystem.TokoEmasJagoCabang.Model.Verifikasi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class Patch2 {
    
    public static String patchNoKeuangan(Connection con){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            con.prepareStatement("ALTER TABLE tt_keuangan "
                    + " ADD COLUMN `no_urut` INT NOT NULL AFTER `no_keuangan`").executeUpdate();
            List<Keuangan> listKeuangan = KeuanganDAO.getAll(con, "%", "%", "%", "%");
            String noKeuangan = "";
            int noUrut = 0;
            for(Keuangan k : listKeuangan){
                if(k.getNoKeuangan().equals(noKeuangan)){
                    noUrut = noUrut + 1;
                    k.setNoUrut(noUrut);
                    KeuanganDAO.updateNoUrut(con, k);
                }else{
                    noUrut = 1;
                    k.setNoUrut(noUrut);
                    KeuanganDAO.updateNoUrut(con, k);
                }
                noKeuangan = k.getNoKeuangan();
            }
            con.prepareStatement("ALTER TABLE tt_keuangan DROP PRIMARY KEY, ADD PRIMARY KEY (`no_keuangan`, `no_urut`)").executeUpdate();
            List<User> listUser = UserDAO.getAll(con);
            for(User u : listUser){
                Verifikasi v = new Verifikasi();
                v.setKodeUser(u.getKodeUser());
                v.setJenis("Batal Penjualan Beda Tanggal");
                v.setStatus(false);
                VerifikasiDAO.insert(con, v);
                
                Otoritas o = new Otoritas();
                o.setKodeUser(u.getKodeUser());
                o.setJenis("Ganti Cabang");
                o.setStatus(false);
                OtoritasDAO.insert(con, o);
                
                Otoritas o2 = new Otoritas();
                o2.setKodeUser(u.getKodeUser());
                o2.setJenis("Revisi Transaksi");
                o2.setStatus(false);
                OtoritasDAO.insert(con, o2);
            }
            
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
