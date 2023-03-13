
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDistributorDayReportColumnsDesc {

    @SerializedName("DistDayReportCoumnName")
    @Expose
    private String distDayReportCoumnName;
    @SerializedName("DistDayReportColumnDisplayName")
    @Expose
    private String distDayReportColumnDisplayName;

    public String getDistDayReportCoumnName() {
        return distDayReportCoumnName;
    }

    public void setDistDayReportCoumnName(String distDayReportCoumnName) {
        this.distDayReportCoumnName = distDayReportCoumnName;
    }

    public String getDistDayReportColumnDisplayName() {
        return distDayReportColumnDisplayName;
    }

    public void setDistDayReportColumnDisplayName(String distDayReportColumnDisplayName) {
        this.distDayReportColumnDisplayName = distDayReportColumnDisplayName;
    }

}
