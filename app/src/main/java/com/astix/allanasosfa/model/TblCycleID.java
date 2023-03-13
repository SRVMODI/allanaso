
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class TblCycleID {

    @SerializedName("CycleID")
    @Expose
    private Integer cycleID;
    @SerializedName("CycStartTime")
    @Expose
    private String cycStartTime;
    @SerializedName("CycleTime")
    @Expose
    private BigInteger cycleTime;

    public Integer getCycleID() {
        return cycleID;
    }

    public void setCycleID(Integer cycleID) {
        this.cycleID = cycleID;
    }

    public String getCycStartTime() {
        return cycStartTime;
    }

    public void setCycStartTime(String cycStartTime) {
        this.cycStartTime = cycStartTime;
    }

    public BigInteger getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(BigInteger cycleTime) {
        this.cycleTime = cycleTime;
    }

}
