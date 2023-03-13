
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIncentiveDetailsColumnsDesc {

    @SerializedName("IncSlabId")
    @Expose
    private Integer incSlabId;

    public Integer getIncSlabId() {
        return incSlabId;
    }

    public void setIncSlabId(Integer incId) {
        this.incSlabId = incId;
    }


    @SerializedName("ReportColumnName")
    @Expose
    private String reportColumnName;

    public String getReportColumnName() {
        return reportColumnName;
    }

    public void setReportColumnName(String reportColumnName) {
        this.reportColumnName = reportColumnName;
    }


    @SerializedName("DisplayColumnName")
    @Expose
    private String displayColumnName;

    public String getDisplayColumnName() {
        return displayColumnName;
    }

    public void setDisplayColumnName(String displayColumnName) {
        this.displayColumnName = displayColumnName;
    }


}
