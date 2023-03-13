
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIncentiveSecondaryMaster {


    public Integer getIncSlabId() {
        return incSlabId;
    }

    public void setIncSlabId(Integer incSlabId) {
        this.incSlabId = incSlabId;
    }

    @SerializedName("IncSlabId")
    @Expose
    private Integer incSlabId;




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


    @SerializedName("IncSlabName")
    @Expose
    private String incSlabName;

    public String getIncSlabName() {
        return incSlabName;
    }

    public void setIncSlabName(String incSlabName) {
        this.incSlabName = incSlabName;
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
