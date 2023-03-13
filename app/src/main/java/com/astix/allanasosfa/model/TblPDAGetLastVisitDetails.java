
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetLastVisitDetails {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Order")
    @Expose
    private Integer order;
    @SerializedName("Stock")
    @Expose
    private Integer stock;
    @SerializedName("SKUName")
    @Expose
    private String sKUName;
    @SerializedName("ExecutionQty")
    @Expose
    private Integer executionQty;
    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSKUName() {
        return sKUName;
    }

    public void setSKUName(String sKUName) {
        this.sKUName = sKUName;
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

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

}
