
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreCloseReasonMaster {

    public Integer getCloseReasonID() {
        return closeReasonID;
    }

    public void setCloseReasonID(Integer closeReasonID) {
        this.closeReasonID = closeReasonID;
    }

    public String getCloseReasonDescr() {
        return closeReasonDescr;
    }

    public void setCloseReasonDescr(String closeReasonDescr) {
        this.closeReasonDescr = closeReasonDescr;
    }

    @SerializedName("CloseReasonID")
    @Expose
    private Integer closeReasonID;
    @SerializedName("CloseReasonDescr")
    @Expose
    private String closeReasonDescr;


}
