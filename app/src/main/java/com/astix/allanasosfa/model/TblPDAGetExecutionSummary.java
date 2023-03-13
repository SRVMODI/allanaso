
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAGetExecutionSummary {

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
    @SerializedName("flgInvStatus")
    @Expose
    private Integer flgInvStatus;
    @SerializedName("ProductQty")
    @Expose
    private Integer productQty;
    @SerializedName("PrdName")
    @Expose
    private String prdName;
    @SerializedName("OrderDate1")
    @Expose
    private String orderDate1;
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

    public Integer getFlgInvStatus() {
        return flgInvStatus;
    }

    public void setFlgInvStatus(Integer flgInvStatus) {
        this.flgInvStatus = flgInvStatus;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getOrderDate1() {
        return orderDate1;
    }

    public void setOrderDate1(String orderDate1) {
        this.orderDate1 = orderDate1;
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
