
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblInvoiceLastVisitDetails {

    @SerializedName("Storeid")
    @Expose
    private Integer storeid;
    @SerializedName("InvCode")
    @Expose
    private String invCode;
    @SerializedName("InvDate")
    @Expose
    private String invDate;
    @SerializedName("OutStandingAmt")
    @Expose
    private Double outStandingAmt;

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public Double getOutStandingAmt() {
        return outStandingAmt;
    }

    public void setOutStandingAmt(Double outStandingAmt) {
        this.outStandingAmt = outStandingAmt;
    }

}
