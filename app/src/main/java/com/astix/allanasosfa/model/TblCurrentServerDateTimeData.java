
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblCurrentServerDateTimeData {

    @SerializedName("tblServerTime")
    @Expose
    private List<TblServerTime> tblServerTime = null;

    public List<TblServerTime> getTblServerTime() {
        return tblServerTime;
    }

    public void setTblServerTime(List<TblServerTime> tblServerTime) {
        this.tblServerTime = tblServerTime;
    }


}
