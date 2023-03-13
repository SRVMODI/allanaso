
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreListWithPaymentAddress {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("OutAddTypeID")
    @Expose
    private Integer outAddTypeID;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("AddressDet")
    @Expose
    private String addressDet;
    @SerializedName("OutAddID")
    @Expose
    private Integer outAddID;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Integer getOutAddTypeID() {
        return outAddTypeID;
    }

    public void setOutAddTypeID(Integer outAddTypeID) {
        this.outAddTypeID = outAddTypeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDet() {
        return addressDet;
    }

    public void setAddressDet(String addressDet) {
        this.addressDet = addressDet;
    }

    public Integer getOutAddID() {
        return outAddID;
    }

    public void setOutAddID(Integer outAddID) {
        this.outAddID = outAddID;
    }

}
