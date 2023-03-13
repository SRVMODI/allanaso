
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAvailableVersion {

    @SerializedName("VersionID")
    @Expose
    private Integer versionID;
    @SerializedName("VersionSerialNo")
    @Expose
    private String versionSerialNo;
    @SerializedName("VersionDownloadStatus")
    @Expose
    private Integer versionDownloadStatus;
    @SerializedName("ServerDate")
    @Expose
    private String serverDate;

    public Integer getVersionID() {
        return versionID;
    }

    public void setVersionID(Integer versionID) {
        this.versionID = versionID;
    }

    public String getVersionSerialNo() {
        return versionSerialNo;
    }

    public void setVersionSerialNo(String versionSerialNo) {
        this.versionSerialNo = versionSerialNo;
    }

    public Integer getVersionDownloadStatus() {
        return versionDownloadStatus;
    }

    public void setVersionDownloadStatus(Integer versionDownloadStatus) {
        this.versionDownloadStatus = versionDownloadStatus;
    }

    public String getServerDate() {
        return serverDate;
    }

    public void setServerDate(String serverDate) {
        this.serverDate = serverDate;
    }

}
