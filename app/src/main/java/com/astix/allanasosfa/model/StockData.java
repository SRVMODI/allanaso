
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockData {

    @SerializedName("tblStockUploadedStatus")
    @Expose
    private List<TblStockUploadedStatus> tblStockUploadedStatus = null;
    @SerializedName("tblVanProductStock")
    @Expose
    private List<TblVanProductStock> tblVanProductStock = null;
    @SerializedName("tblCycleID")
    @Expose
    private List<TblCycleID> tblCycleID = null;
    @SerializedName("tblVanStockOutFlg")
    @Expose
    private List<TblVanStockOutFlg> tblVanStockOutFlg = null;
    @SerializedName("tblVanIDOrderIDLeft")
    @Expose
    private List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = null;

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblVanProductStock> getTblVanProductStock() {
        return tblVanProductStock;
    }

    public void setTblVanProductStock(List<TblVanProductStock> tblVanProductStock) {
        this.tblVanProductStock = tblVanProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblVanStockOutFlg> getTblVanStockOutFlg() {
        return tblVanStockOutFlg;
    }

    public void setTblVanStockOutFlg(List<TblVanStockOutFlg> tblVanStockOutFlg) {
        this.tblVanStockOutFlg = tblVanStockOutFlg;
    }

    public List<TblVanIDOrderIDLeft> getTblVanIDOrderIDLeft() {
        return tblVanIDOrderIDLeft;
    }

    public void setTblVanIDOrderIDLeft(List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft) {
        this.tblVanIDOrderIDLeft = tblVanIDOrderIDLeft;
    }

}
