
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockUploadedStatus {

    @SerializedName("tblStockUploadedStatus")
    @Expose
    private List<TblStockUploadedStatus> tblStockUploadedStatus = null;

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }


}
