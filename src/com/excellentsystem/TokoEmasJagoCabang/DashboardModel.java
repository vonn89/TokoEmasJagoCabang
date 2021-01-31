/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoCabang;

import com.excellentsystem.TokoEmasJagoCabang.Model.Keuangan;
import com.excellentsystem.TokoEmasJagoCabang.Model.PenjualanDetail;
import java.util.List;

/**
 *
 * @author excellent
 */
public class DashboardModel {
    private List<Keuangan> listKeuangan;
    private List<PenjualanDetail> listPenjualanDetail;

    public List<Keuangan> getListKeuangan() {
        return listKeuangan;
    }

    public void setListKeuangan(List<Keuangan> listKeuangan) {
        this.listKeuangan = listKeuangan;
    }

    public List<PenjualanDetail> getListPenjualanDetail() {
        return listPenjualanDetail;
    }

    public void setListPenjualanDetail(List<PenjualanDetail> listPenjualanDetail) {
        this.listPenjualanDetail = listPenjualanDetail;
    }
    
}
