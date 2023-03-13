
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonInfo {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("DOB")
    @Expose
    private String dOB;

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

}
