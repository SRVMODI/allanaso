
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLODQty {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("SKUID")
    @Expose
    private Integer sKUID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("SKUName")
    @Expose
    private String sKUName;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Integer getSKUID() {
        return sKUID;
    }

    public void setSKUID(Integer sKUID) {
        this.sKUID = sKUID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSKUName() {
        return sKUName;
    }

    public void setSKUName(String sKUName) {
        this.sKUName = sKUName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

}
