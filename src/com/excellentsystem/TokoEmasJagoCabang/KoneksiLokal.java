/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Xtreme
 */
public class KoneksiLokal {
    public static Connection getConnection(String ipServer, String kodeCabang)throws Exception{
        String DbName = "jdbc:mysql://"+ipServer+":3306/tokoemasjago_"+kodeCabang+"?"
                    + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
}
