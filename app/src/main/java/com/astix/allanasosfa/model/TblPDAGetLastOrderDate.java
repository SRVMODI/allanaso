
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLastOrderDate {

    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("flgExecutionSummary")
    @Expose
    private Integer flgExecutionSummary;

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getFlgExecutionSummary() {
        return flgExecutionSummary;
    }

    public void setFlgExecutionSummary(Integer flgExecutionSummary) {
        this.flgExecutionSummary = flgExecutionSummary;
    }

}
