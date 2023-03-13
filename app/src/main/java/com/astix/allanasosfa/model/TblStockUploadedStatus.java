
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class TblStockUploadedStatus {

    @SerializedName("flgStockTrans")
    @Expose
    private Integer flgStockTrans;
    @SerializedName("VanLoadUnLoadCycID")
    @Expose
    private Integer vanLoadUnLoadCycID;
    @SerializedName("CycleTime")
    @Expose
    private BigInteger cycleTime;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("flgDayEnd")
    @Expose
    private Integer flgDayEnd;

    public Integer getFlgStockTrans() {
        return flgStockTrans;
    }

    public void setFlgStockTrans(Integer flgStockTrans) {
        this.flgStockTrans = flgStockTrans;
    }

    public Integer getVanLoadUnLoadCycID() {
        return vanLoadUnLoadCycID;
    }

    public void setVanLoadUnLoadCycID(Integer vanLoadUnLoadCycID) {
        this.vanLoadUnLoadCycID = vanLoadUnLoadCycID;
    }

    public BigInteger getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(BigInteger cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public Integer getFlgDayEnd() {
        return flgDayEnd;
    }

    public void setFlgDayEnd(Integer flgDayEnd) {
        this.flgDayEnd = flgDayEnd;
    }

}
