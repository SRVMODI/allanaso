
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUOMMaster {

    @SerializedName("BUOMID")
    @Expose
    private Integer bUOMID;
    @SerializedName("BUOMName")
    @Expose
    private String bUOMName;
    @SerializedName("flgRetailUnit")
    @Expose
    private Integer flgRetailUnit;

    public Integer getBUOMID() {
        return bUOMID;
    }

    public void setBUOMID(Integer bUOMID) {
        this.bUOMID = bUOMID;
    }

    public String getBUOMName() {
        return bUOMName;
    }

    public void setBUOMName(String bUOMName) {
        this.bUOMName = bUOMName;
    }

    public Integer getFlgRetailUnit() {
        return flgRetailUnit;
    }

    public void setFlgRetailUnit(Integer flgRetailUnit) {
        this.flgRetailUnit = flgRetailUnit;
    }

}
