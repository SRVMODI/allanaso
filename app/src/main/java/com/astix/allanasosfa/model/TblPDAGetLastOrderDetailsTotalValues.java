
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLastOrderDetailsTotalValues {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("OrderValue")
    @Expose
    private Double orderValue;
    @SerializedName("ExecutionValue")
    @Expose
    private Double executionValue;
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

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    public Double getExecutionValue() {
        return executionValue;
    }

    public void setExecutionValue(Double executionValue) {
        this.executionValue = executionValue;
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
