
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblVanProductStock {

    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("ProductNodeID")
    @Expose
    private Integer productNodeID;
    @SerializedName("ProductNodeType")
    @Expose
    private Integer productNodeType;
    @SerializedName("SKUName")
    @Expose
    private String sKUName;
    @SerializedName("StockDate")
    @Expose
    private String stockDate;
    @SerializedName("DayOpeningStock")
    @Expose
    private Integer dayOpeningStock;
    @SerializedName("TodaysAddedStock")
    @Expose
    private Integer todaysAddedStock;
    @SerializedName("CycleAddedStock")
    @Expose
    private Integer cycleAddedStock;
    @SerializedName("TodaysUnloadStock")
    @Expose
    private Integer todaysUnloadStock;
    @SerializedName("CycleUnloadStock")
    @Expose
    private Integer cycleUnloadStock;
    @SerializedName("NetSalesQty")
    @Expose
    private Integer netSalesQty;
    @SerializedName("FinalStockQty")
    @Expose
    private Integer finalStockQty;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getProductNodeID() {
        return productNodeID;
    }

    public void setProductNodeID(Integer productNodeID) {
        this.productNodeID = productNodeID;
    }

    public Integer getProductNodeType() {
        return productNodeType;
    }

    public void setProductNodeType(Integer productNodeType) {
        this.productNodeType = productNodeType;
    }

    public String getSKUName() {
        return sKUName;
    }

    public void setSKUName(String sKUName) {
        this.sKUName = sKUName;
    }

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public Integer getDayOpeningStock() {
        return dayOpeningStock;
    }

    public void setDayOpeningStock(Integer dayOpeningStock) {
        this.dayOpeningStock = dayOpeningStock;
    }

    public Integer getTodaysAddedStock() {
        return todaysAddedStock;
    }

    public void setTodaysAddedStock(Integer todaysAddedStock) {
        this.todaysAddedStock = todaysAddedStock;
    }

    public Integer getCycleAddedStock() {
        return cycleAddedStock;
    }

    public void setCycleAddedStock(Integer cycleAddedStock) {
        this.cycleAddedStock = cycleAddedStock;
    }

    public Integer getTodaysUnloadStock() {
        return todaysUnloadStock;
    }

    public void setTodaysUnloadStock(Integer todaysUnloadStock) {
        this.todaysUnloadStock = todaysUnloadStock;
    }

    public Integer getCycleUnloadStock() {
        return cycleUnloadStock;
    }

    public void setCycleUnloadStock(Integer cycleUnloadStock) {
        this.cycleUnloadStock = cycleUnloadStock;
    }

    public Integer getNetSalesQty() {
        return netSalesQty;
    }

    public void setNetSalesQty(Integer netSalesQty) {
        this.netSalesQty = netSalesQty;
    }

    public Integer getFinalStockQty() {
        return finalStockQty;
    }

    public void setFinalStockQty(Integer finalStockQty) {
        this.finalStockQty = finalStockQty;
    }

}
