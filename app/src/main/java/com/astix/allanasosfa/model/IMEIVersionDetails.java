
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMEIVersionDetails {

    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getApplicationType() {
        return applicationTypeId;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationTypeId = applicationType;
    }

}
