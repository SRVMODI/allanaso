
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductListLastVisitStockOrOrderMstr {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("PrdID")
    @Expose
    private Integer prdID;
    @SerializedName("OrderQty")
    @Expose
    private Integer orderQty;
    @SerializedName("StockQty")
    @Expose
    private Integer stockQty;

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

    public Integer getPrdID() {
        return prdID;
    }

    public void setPrdID(Integer prdID) {
        this.prdID = prdID;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

}
