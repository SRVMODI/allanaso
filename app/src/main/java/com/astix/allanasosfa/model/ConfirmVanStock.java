
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmVanStock {

    @SerializedName("VanLoadUnLoadCycleId")
    @Expose
    private Integer vanLoadUnLoadCycleId;
    @SerializedName("PDACycleTime")
    @Expose
    private String pDACycleTime;

    public Integer getVanLoadUnLoadCycleId() {
        return vanLoadUnLoadCycleId;
    }

    public void setVanLoadUnLoadCycleId(Integer vanLoadUnLoadCycleId) {
        this.vanLoadUnLoadCycleId = vanLoadUnLoadCycleId;
    }

    public String getPDACycleTime() {
        return pDACycleTime;
    }

    public void setPDACycleTime(String pDACycleTime) {
        this.pDACycleTime = pDACycleTime;
    }

}
