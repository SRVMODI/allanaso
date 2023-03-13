
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCategoryMaster {

    @SerializedName("NODEID")
    @Expose
    private Integer nODEID;
    @SerializedName("CATEGORY")
    @Expose
    private String cATEGORY;

    public Integer getNODEID() {
        return nODEID;
    }

    public void setNODEID(Integer nODEID) {
        this.nODEID = nODEID;
    }

    public String getCATEGORY() {
        return cATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        this.cATEGORY = cATEGORY;
    }

}
