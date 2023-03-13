
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VanDayEnd {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("DayEndTime")
    @Expose
    private String dayEndTime;
    @SerializedName("VisitDate")
    @Expose
    private String visitDate;
    @SerializedName("AppVersionId")
    @Expose
    private Integer appVersionId;
    @SerializedName("VanLoadUnLoadCycleId")
    @Expose
    private Integer vanLoadUnLoadCycleId;
    @SerializedName("FlgUnloading")
    @Expose
    private Integer flgUnloading;

    public Integer getDayEndButtonOrCycleEnd() {
        return dayEndButtonOrCycleEnd;
    }

    public void setDayEndButtonOrCycleEnd(Integer dayEndButtonOrCycleEnd) {
        this.dayEndButtonOrCycleEnd = dayEndButtonOrCycleEnd;

    }

    @SerializedName("DayEndButtonOrCycleEnd")
    @Expose
    private Integer dayEndButtonOrCycleEnd;

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public String getDayEndTime() {
        return dayEndTime;
    }

    public void setDayEndTime(String dayEndTime) {
        this.dayEndTime = dayEndTime;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public Integer getVanLoadUnLoadCycleId() {
        return vanLoadUnLoadCycleId;
    }

    public void setVanLoadUnLoadCycleId(Integer vanLoadUnLoadCycleId) {
        this.vanLoadUnLoadCycleId = vanLoadUnLoadCycleId;
    }

    public Integer getFlgUnloading() {
        return flgUnloading;
    }

    public void setFlgUnloading(Integer flgUnloading) {
        this.flgUnloading = flgUnloading;
    }

}
