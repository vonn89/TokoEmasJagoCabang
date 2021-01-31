/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoCabang.DAO;

import com.excellentsystem.TokoEmasJagoCabang.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yunaz
 */
public class UserDAO {
    public static User get(Connection con, String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user where kode_user=?");
        ps.setString(1, kodeUser);
        ResultSet rs = ps.executeQuery();
        User u = null;
        if(rs.next()){
            u = new User();
            u.setKodeUser(rs.getString(1));
            u.setPassword(rs.getString(2));
        }
        return u;
    }
    public static List<User> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user");
        ResultSet rs = ps.executeQuery();
        List<User> allUser = new ArrayList<>();
        while(rs.next()){
            User u = new User();
            u.setKodeUser(rs.getString(1));
            u.setPassword(rs.getString(2));
            allUser.add(u);
        }
        return allUser;
    }
    public static void insert(Connection con, User u)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_user values(?,?)");
        ps.setString(1, u.getKodeUser());
        ps.setString(2, u.getPassword());
        ps.executeUpdate();
    }
    public static void update(Connection con, User u)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_user set password=? where kode_user=?");
        ps.setString(1, u.getPassword());
        ps.setString(2, u.getKodeUser());
        ps.executeUpdate();
    }
    public static void delete(Connection con,User u)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_user where kode_user=? ");
        ps.setString(1, u.getKodeUser());
        ps.executeUpdate();
    }
}
