package com.excellentsystem.TokoEmasJagoCabang;

import static com.excellentsystem.TokoEmasJagoCabang.Main.sistem;
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Xtreme
 */
public class KoneksiPusat {
    public static Connection getConnection()throws Exception{
        String DbName = "jdbc:mysql://"+sistem.getIpServerPusat()+":3306/tokoemasjagopusat?"
                + "connectTimeout=1000&socketTimeout=1000";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
}
