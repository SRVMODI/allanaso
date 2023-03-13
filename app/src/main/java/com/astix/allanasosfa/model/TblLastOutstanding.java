
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblLastOutstanding {

    @SerializedName("Storeid")
    @Expose
    private Integer storeid;
    @SerializedName("OutStanding")
    @Expose
    private Double outStanding;

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public Double getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(Double outStanding) {
        this.outStanding = outStanding;
    }

}
