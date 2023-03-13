
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllAddedOutletSummaryReportModel {


    public List<TblDAGetAddedOutletSummaryReport> getTblDAGetAddedOutletSummaryReport() {
        return tblDAGetAddedOutletSummaryReport;
    }

    public void seTblDAGetAddedOutletSummaryReport(List<TblDAGetAddedOutletSummaryReport> tblDAGetAddedOutletSummaryReport) {
        this.tblDAGetAddedOutletSummaryReport = tblDAGetAddedOutletSummaryReport;
    }

    @SerializedName("tblDAGetAddedOutletSummaryReport")
    @Expose
    private List<TblDAGetAddedOutletSummaryReport> tblDAGetAddedOutletSummaryReport = null;


    public List<TblDAGetAddedOutletSummaryOverallReport> getTblDAGetAddedOutletSummaryOverallReport() {
        return tblDAGetAddedOutletSummaryOverallReport;
    }

    public void seTblDAGetAddedOutletSummaryOverallReport(List<TblDAGetAddedOutletSummaryOverallReport> tblDAGetAddedOutletSummaryOverallReport) {
        this.tblDAGetAddedOutletSummaryOverallReport = tblDAGetAddedOutletSummaryOverallReport;
    }

    @SerializedName("tblDAGetAddedOutletSummaryOverallReport")
    @Expose
    private List<TblDAGetAddedOutletSummaryOverallReport> tblDAGetAddedOutletSummaryOverallReport = null;


}
