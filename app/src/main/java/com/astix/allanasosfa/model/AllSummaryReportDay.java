
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSummaryReportDay {


    public List<TblAllSummaryDay> getTblAllSummaryDay() {
        return tblAllSummaryDay;
    }

    public void setTblAllSummaryDay(List<TblAllSummaryDay> tblAllSummaryDay) {
        this.tblAllSummaryDay = tblAllSummaryDay;
    }

    @SerializedName("tblAllSummaryDay")
    @Expose
    private List<TblAllSummaryDay> tblAllSummaryDay = null;





}
