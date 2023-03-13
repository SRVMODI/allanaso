
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IMEIVersionParentModel {

    @SerializedName("tblUserAuthentication")
    @Expose
    private List<TblUserAuthentication> tblUserAuthentication = null;
    @SerializedName("tblAvailableVersion")
    @Expose
    private List<TblAvailableVersion> tblAvailableVersion = null;

    public List<TblUserAuthentication> getTblUserAuthentication() {
        return tblUserAuthentication;
    }

    public void setTblUserAuthentication(List<TblUserAuthentication> tblUserAuthentication) {
        this.tblUserAuthentication = tblUserAuthentication;
    }

    public List<TblAvailableVersion> getTblAvailableVersion() {
        return tblAvailableVersion;
    }

    public void setTblAvailableVersion(List<TblAvailableVersion> tblAvailableVersion) {
        this.tblAvailableVersion = tblAvailableVersion;
    }

}
