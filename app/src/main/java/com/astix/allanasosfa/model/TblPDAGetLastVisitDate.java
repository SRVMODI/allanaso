
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLastVisitDate {

    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("VIsitDate")
    @Expose
    private String vIsitDate;
    @SerializedName("flgOrder")
    @Expose
    private Integer flgOrder;

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

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public String getVIsitDate() {
        return vIsitDate;
    }

    public void setVIsitDate(String vIsitDate) {
        this.vIsitDate = vIsitDate;
    }

    public Integer getFlgOrder() {
        return flgOrder;
    }

    public void setFlgOrder(Integer flgOrder) {
        this.flgOrder = flgOrder;
    }

}
