
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDistributorIDOrderIDLeft {

    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("PDAOrderId")
    @Expose
    private String pDAOrderId;


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



}
