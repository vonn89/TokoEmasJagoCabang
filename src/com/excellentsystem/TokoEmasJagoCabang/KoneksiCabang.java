/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import com.excellentsystem.TokoEmasJagoCabang.Model.Cabang;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author excellent
 */
public class KoneksiCabang {
    public static Connection getConnection(Cabang c)throws Exception{
        String DbName = "jdbc:mysql://"+c.getIpServer()+":3306/tokoemasjago_"+c.getKodeCabang()+"?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.setLoginTimeout(10);
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
}
