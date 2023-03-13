
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPreAddedStores {

    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("StoreIDDB")
    @Expose
    private Integer storeIDDB;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("LatCode")
    @Expose
    private String latCode;
    @SerializedName("LongCode")
    @Expose
    private String longCode;
    @SerializedName("DateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("flgRemap")
    @Expose
    private Integer flgRemap;
    @SerializedName("RouteNodeID")
    @Expose
    private Integer routeNodeID;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
//---------------------------------------------------
    @SerializedName("CoverageAreaID")
    @Expose
    private Integer CoverageAreaID;

    public Integer getCoverageAreaID() {
        return CoverageAreaID;
    }

    public void setCoverageAreaID(Integer coverageAreaID) {
        CoverageAreaID = coverageAreaID;
    }

    public Integer getCoverageAreaType() {
        return CoverageAreaType;
    }

    public void setCoverageAreaType(Integer coverageAreaType) {
        CoverageAreaType = coverageAreaType;
    }

    public Integer getFlgStoreValidated() {
        return flgStoreValidated;
    }

    public void setFlgStoreValidated(Integer flgStoreValidated) {
        this.flgStoreValidated = flgStoreValidated;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getOptionID() {
        return OptionID;
    }

    public void setOptionID(String optionID) {
        OptionID = optionID;
    }

    public Integer getSectionCount() {
        return SectionCount;
    }

    public void setSectionCount(Integer sectionCount) {
        SectionCount = sectionCount;
    }

    public Integer getFlgSelfStore() {
        return flgSelfStore;
    }

    public void setFlgSelfStore(Integer flgSelfStore) {
        this.flgSelfStore = flgSelfStore;
    }

    public String getSOLatCode() {
        return SOLatCode;
    }

    public void setSOLatCode(String SOLatCode) {
        this.SOLatCode = SOLatCode;
    }

    public String getSOLongCode() {
        return SOLongCode;
    }

    public void setSOLongCode(String SOLongCode) {
        this.SOLongCode = SOLongCode;
    }

    public String getStoreCategoryType() {
        return StoreCategoryType;
    }

    public void setStoreCategoryType(String storeCategoryType) {
        StoreCategoryType = storeCategoryType;
    }

    public Integer getFlgOldNewStore() {
        return flgOldNewStore;
    }

    public void setFlgOldNewStore(Integer flgOldNewStore) {
        this.flgOldNewStore = flgOldNewStore;
    }

    public Integer getFlgStoreVisitMode() {
        return flgStoreVisitMode;
    }

    public void setFlgStoreVisitMode(Integer flgStoreVisitMode) {
        this.flgStoreVisitMode = flgStoreVisitMode;
    }

    public String getVisitStartTS() {
        return VisitStartTS;
    }

    public void setVisitStartTS(String visitStartTS) {
        VisitStartTS = visitStartTS;
    }

    public String getVisitEndTS() {
        return VisitEndTS;
    }

    public void setVisitEndTS(String visitEndTS) {
        VisitEndTS = visitEndTS;
    }

    public String getLocProvider() {
        return LocProvider;
    }

    public void setLocProvider(String locProvider) {
        LocProvider = locProvider;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getBateryLeftStatus() {
        return BateryLeftStatus;
    }

    public void setBateryLeftStatus(String bateryLeftStatus) {
        BateryLeftStatus = bateryLeftStatus;
    }

    public Integer getIsStoreDataCompleteSaved() {
        return IsStoreDataCompleteSaved;
    }

    public void setIsStoreDataCompleteSaved(Integer isStoreDataCompleteSaved) {
        IsStoreDataCompleteSaved = isStoreDataCompleteSaved;
    }

    public String getPaymentStage() {
        return PaymentStage;
    }

    public void setPaymentStage(String paymentStage) {
        PaymentStage = paymentStage;
    }

    public Integer getFlgLocationTrackEnabled() {
        return flgLocationTrackEnabled;
    }

    public void setFlgLocationTrackEnabled(Integer flgLocationTrackEnabled) {
        this.flgLocationTrackEnabled = flgLocationTrackEnabled;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }

    public String getSOAccuracy() {
        return SOAccuracy;
    }

    public void setSOAccuracy(String SOAccuracy) {
        this.SOAccuracy = SOAccuracy;
    }

    @SerializedName("CoverageAreaType")
    @Expose
    private Integer CoverageAreaType;

    @SerializedName("flgStoreValidated")
    @Expose
    private Integer flgStoreValidated;


    @SerializedName("City")
    @Expose
    private String City;

    @SerializedName("State")
    @Expose
    private String State;
    @SerializedName("PinCode")
    @Expose
    private String PinCode;

    @SerializedName("OptionID")
    @Expose
    private String OptionID;

    @SerializedName("SectionCount")
    @Expose
    private Integer SectionCount;

    @SerializedName("flgSelfStore")
    @Expose
    private Integer flgSelfStore;

    @SerializedName("SOLatCode")
    @Expose
    private String SOLatCode;


    @SerializedName("SOLongCode")
    @Expose
    private String SOLongCode;

    @SerializedName("StoreCategoryType")
    @Expose
    private String StoreCategoryType;

    @SerializedName("flgOldNewStore")
    @Expose
    private Integer flgOldNewStore;

    @SerializedName("flgStoreVisitMode")
    @Expose
    private Integer flgStoreVisitMode;

    @SerializedName("VisitStartTS")
    @Expose
    private String VisitStartTS;

    @SerializedName("VisitEndTS")
    @Expose
    private String VisitEndTS;

    @SerializedName("LocProvider")
    @Expose
    private String LocProvider;


    @SerializedName("Accuracy")
    @Expose
    private String Accuracy;


    @SerializedName("BateryLeftStatus")
    @Expose
    private String BateryLeftStatus;

    @SerializedName("IsStoreDataCompleteSaved")
    @Expose
    private Integer IsStoreDataCompleteSaved;

    @SerializedName("PaymentStage")
    @Expose
    private String PaymentStage;

    @SerializedName("flgLocationTrackEnabled")
    @Expose
    private Integer flgLocationTrackEnabled;


    @SerializedName("StoreAddress")
    @Expose
    private String StoreAddress;

    @SerializedName("SOAccuracy")
    @Expose
    private String SOAccuracy;
//-----------------------------------------



    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public Integer getStoreIDDB() {
        return storeIDDB;
    }

    public void setStoreIDDB(Integer storeIDDB) {
        this.storeIDDB = storeIDDB;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLatCode() {
        return latCode;
    }

    public void setLatCode(String latCode) {
        this.latCode = latCode;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getFlgRemap() {
        return flgRemap;
    }

    public void setFlgRemap(Integer flgRemap) {
        this.flgRemap = flgRemap;
    }

    public Integer getRouteNodeID() {
        return routeNodeID;
    }

    public void setRouteNodeID(Integer routeNodeID) {
        this.routeNodeID = routeNodeID;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

}
