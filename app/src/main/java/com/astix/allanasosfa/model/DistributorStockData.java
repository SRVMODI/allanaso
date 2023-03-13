
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistributorStockData {

    @SerializedName("tblDistributorDayReport")
    @Expose
    private List<TblDistributorDayReport> tblDistributorDayReport = null;
    @SerializedName("tblDistributorDayReportColumnsDesc")
    @Expose
    private List<TblDistributorDayReportColumnsDesc> tblDistributorDayReportColumnsDesc = null;
    @SerializedName("tblDistributorDayReportDisplayMessage")
    @Expose
    private List<TblDistributorDayReportDisplayMessage> tblDistributorDayReportDisplayMessage = null;

    public List<TblDistributorDayReport> getTblDistributorDayReport() {
        return tblDistributorDayReport;
    }

    public void setTblDistributorDayReport(List<TblDistributorDayReport> tblDistributorDayReport) {
        this.tblDistributorDayReport = tblDistributorDayReport;
    }

    public List<TblDistributorDayReportColumnsDesc> getTblDistributorDayReportColumnsDesc() {
        return tblDistributorDayReportColumnsDesc;
    }

    public void setTblDistributorDayReportColumnsDesc(List<TblDistributorDayReportColumnsDesc> tblDistributorDayReportColumnsDesc) {
        this.tblDistributorDayReportColumnsDesc = tblDistributorDayReportColumnsDesc;
    }

    public List<TblDistributorDayReportDisplayMessage> getTblDistributorDayReportDisplayMessage() {
        return tblDistributorDayReportDisplayMessage;
    }

    public void setTblDistributorDayReportDisplayMessage(List<TblDistributorDayReportDisplayMessage> tblDistributorDayReportDisplayMessage) {
        this.tblDistributorDayReportDisplayMessage = tblDistributorDayReportDisplayMessage;
    }

}
