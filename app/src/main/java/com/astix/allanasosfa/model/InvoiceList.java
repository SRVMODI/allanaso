
package com.astix.allanasosfa.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceList {

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getpDAOrderId() {
        return pDAOrderId;
    }

    public void setpDAOrderId(String pDAOrderId) {
        this.pDAOrderId = pDAOrderId;
    }

    @SerializedName("Customer")
    @Expose
    private String customer;

    @SerializedName("PDAOrderId")
    @Expose
    private String pDAOrderId;



}
