
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLastOrderDetails {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;
    @SerializedName("OrderQty")
    @Expose
    private Integer orderQty;
    @SerializedName("FreeQty")
    @Expose
    private Integer freeQty;
    @SerializedName("Stock")
    @Expose
    private Integer stock;
    @SerializedName("PrdName")
    @Expose
    private String prdName;
    @SerializedName("ExecutionQty")
    @Expose
    private Integer executionQty;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(Integer freeQty) {
        this.freeQty = freeQty;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public Integer getExecutionQty() {
        return executionQty;
    }

    public void setExecutionQty(Integer executionQty) {
        this.executionQty = executionQty;
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
