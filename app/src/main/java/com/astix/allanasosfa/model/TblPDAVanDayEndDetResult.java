
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblPDAVanDayEndDetResult {

    @SerializedName("tblPDAVanDayEndDetResult")
    @Expose
    private List<FlgDayEndRequestAccept> tblPDAVanDayEndDetResult = null;

    public List<FlgDayEndRequestAccept> getTblPDAVanDayEndDetResult() {
        return tblPDAVanDayEndDetResult;
    }

    public void setTblPDAVanDayEndDetResult(List<FlgDayEndRequestAccept> tblPDAVanDayEndDetResult) {
        this.tblPDAVanDayEndDetResult = tblPDAVanDayEndDetResult;
    }

}
