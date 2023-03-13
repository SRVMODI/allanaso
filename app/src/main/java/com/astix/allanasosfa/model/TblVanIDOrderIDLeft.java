
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblVanIDOrderIDLeft {

    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("PDAOrderId")
    @Expose
    private String pDAOrderId;
    @SerializedName("flgInvExists")
    @Expose
    private Integer flgInvExists;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPDAOrderId() {
        return pDAOrderId;
    }

    public void setPDAOrderId(String pDAOrderId) {
        this.pDAOrderId = pDAOrderId;
    }

    public Integer getFlgInvExists() {
        return flgInvExists;
    }

    public void setFlgInvExists(Integer flgInvExists) {
        this.flgInvExists = flgInvExists;
    }

}
