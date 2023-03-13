
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportsAddedOutletSummary {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;
    @SerializedName("ForDate")
    @Expose
    private String forDate;


    public Integer getFlgDrillLevel() {
        return FlgDrillLevel;
    }

    public void setFlgDrillLevel(Integer flgDrillLevel) {
        FlgDrillLevel = flgDrillLevel;
    }

    @SerializedName("flgDrillLevel")
    @Expose
    private Integer FlgDrillLevel;




    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Integer applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }





}
