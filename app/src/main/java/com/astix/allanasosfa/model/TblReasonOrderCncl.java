
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblReasonOrderCncl {

    public Integer getReasonCodeID() {
        return reasonCodeID;
    }

    public void setReasonCodeID(Integer reasonCodeID) {
        this.reasonCodeID = reasonCodeID;
    }

    public String getReasonDescr() {
        return reasonDescr;
    }

    public void setReasonDescr(String reasonDescr) {
        this.reasonDescr = reasonDescr;
    }

    @SerializedName("ReasonCodeID")
    @Expose
    private Integer reasonCodeID;
    @SerializedName("ReasonDescr")
    @Expose
    private String reasonDescr;




}
