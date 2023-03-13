
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAllSummaryDay {

    @SerializedName("Measures")
    @Expose
    private String measures;
    @SerializedName("TodaysSummary")
    @Expose
    private String todaysSummary;
    @SerializedName("MTDSummary")
    @Expose
    private String mTDSummary;
    @SerializedName("TableNo")
    @Expose
    private Integer tableNo;
    @SerializedName("ColorCode")
    @Expose
    private String colorCode;

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getTodaysSummary() {
        return todaysSummary;
    }

    public void setTodaysSummary(String todaysSummary) {
        this.todaysSummary = todaysSummary;
    }

    public String getMTDSummary() {
        return mTDSummary;
    }

    public void setMTDSummary(String mTDSummary) {
        this.mTDSummary = mTDSummary;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

}
