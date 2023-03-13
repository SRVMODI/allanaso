
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationValidation {

    @SerializedName("tblMessageDisplaySetting")
    @Expose
    private List<TblMessageDisplaySetting> tblMessageDisplaySetting = null;
    @SerializedName("tblPersonDetailsForRegistration")
    @Expose
    private List<TblPersonDetailsForRegistration> tblPersonDetailsForRegistration = null;

    public List<TblMessageDisplaySetting> getTblMessageDisplaySetting() {
        return tblMessageDisplaySetting;
    }

    public void setTblMessageDisplaySetting(List<TblMessageDisplaySetting> tblMessageDisplaySetting) {
        this.tblMessageDisplaySetting = tblMessageDisplaySetting;
    }

    public List<TblPersonDetailsForRegistration> getTblPersonDetailsForRegistration() {
        return tblPersonDetailsForRegistration;
    }

    public void setTblPersonDetailsForRegistration(List<TblPersonDetailsForRegistration> tblPersonDetailsForRegistration) {
        this.tblPersonDetailsForRegistration = tblPersonDetailsForRegistration;
    }

}
