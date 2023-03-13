
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblRptDistribution {

    @SerializedName("Description")
    private String description;
    @Expose
    private int flgCollapse;
    @SerializedName("MTD")
    private int mTD;
    @SerializedName("New_YesterDay")
    private int newYesterDay;
    @SerializedName("YTD_Tgt")
    private String yTDTgt;
    @SerializedName("YTD_TillDate")
    private int yTDTillDate;
    @SerializedName("flgLevel")
    private int flgLevel;

    public int getFlgLevel() {
        return flgLevel;
    }

    public void setFlgLevel(int flgLevel) {
        this.flgLevel = flgLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFlgCollapse() {
        return flgCollapse;
    }

    public void setFlgCollapse(int flgCollapse) {
        this.flgCollapse = flgCollapse;
    }

    public int getMTD() {
        return mTD;
    }

    public void setMTD(int mTD) {
        this.mTD = mTD;
    }

    public int getNewYesterDay() {
        return newYesterDay;
    }

    public void setNewYesterDay(int newYesterDay) {
        this.newYesterDay = newYesterDay;
    }

    public String getYTDTgt() {
        return yTDTgt;
    }

    public void setYTDTgt(String yTDTgt) {
        this.yTDTgt = yTDTgt;
    }

    public int getYTDTillDate() {
        return yTDTillDate;
    }

    public void setYTDTillDate(int yTDTillDate) {
        this.yTDTillDate = yTDTillDate;
    }

}
