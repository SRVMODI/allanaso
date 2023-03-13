
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblSupplierMstrList {

    @SerializedName("NodeID")
    @Expose
    private Integer nodeID;
    @SerializedName("NodeType")
    @Expose
    private Integer nodeType;
    @SerializedName("Descr")
    @Expose
    private String descr;
    @SerializedName("latCode")
    @Expose
    private Double latCode;
    @SerializedName("LongCode")
    @Expose
    private Double longCode;
    @SerializedName("flgMapped")
    @Expose
    private Integer flgMapped;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("TaxNumber")
    @Expose
    private String taxNumber;
    @SerializedName("flgStockManage")
    @Expose
    private Integer flgStockManage;
    @SerializedName("flgDefault")
    @Expose
    private Integer flgDefault;
    @SerializedName("EMailID")
    @Expose
    private String eMailID;

    public Integer getNodeID() {
        return nodeID;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Double getLatCode() {
        return latCode;
    }

    public void setLatCode(Double latCode) {
        this.latCode = latCode;
    }

    public Double getLongCode() {
        return longCode;
    }

    public void setLongCode(Double longCode) {
        this.longCode = longCode;
    }

    public Integer getFlgMapped() {
        return flgMapped;
    }

    public void setFlgMapped(Integer flgMapped) {
        this.flgMapped = flgMapped;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Integer getFlgStockManage() {
        return flgStockManage;
    }

    public void setFlgStockManage(Integer flgStockManage) {
        this.flgStockManage = flgStockManage;
    }

    public Integer getFlgDefault() {
        return flgDefault;
    }

    public void setFlgDefault(Integer flgDefault) {
        this.flgDefault = flgDefault;
    }

    public String getEMailID() {
        return eMailID;
    }

    public void setEMailID(String eMailID) {
        this.eMailID = eMailID;
    }

}
