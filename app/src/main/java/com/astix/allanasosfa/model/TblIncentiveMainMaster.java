
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIncentiveMainMaster {

    @SerializedName("IncId")
    @Expose
    private Integer incId;

    public Integer getIncId() {
        return incId;
    }

    public void setIncId(Integer incId) {
        this.incId = incId;
    }


    @SerializedName("OutputType")
    @Expose
    private Integer outputType;

    public Integer getOutputType() {
        return outputType;
    }

    public void setOutputType(Integer outputType) {
        this.outputType = outputType;
    }


    @SerializedName("IncentiveName")
    @Expose
    private String incentiveName;

    public String getIncentiveName() {
        return incentiveName;
    }

    public void setIncentiveName(String incentiveName) {
        this.incentiveName = incentiveName;
    }

    @SerializedName("flgAcheived")
    @Expose
    private Integer flgAcheived;

    public Integer getFlgAcheived() {
        return flgAcheived;
    }

    public void setFlgAcheived(Integer flgAcheived) {
        this.flgAcheived = flgAcheived;
    }


    @SerializedName("Earning")
    @Expose
    private Double earning;

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }
}
