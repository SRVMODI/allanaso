
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreSKUWiseDaySummary {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("MRP")
    @Expose
    private Float mRP;
    @SerializedName("Rate")
    @Expose
    private Float rate;
    @SerializedName("OrderQty")
    @Expose
    private Integer orderQty;
    @SerializedName("FreeQty")
    @Expose
    private Integer freeQty;
    @SerializedName("DiscValue")
    @Expose
    private Float discValue;
    @SerializedName("ValBeforeTax")
    @Expose
    private Float valBeforeTax;
    @SerializedName("TaxValue")
    @Expose
    private Float taxValue;
    @SerializedName("ValAfterTax")
    @Expose
    private Float valAfterTax;
    @SerializedName("Lvl")
    @Expose
    private Integer lvl;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("StockQty")
    @Expose
    private Integer stockQty;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Float getMRP() {
        return mRP;
    }

    public void setMRP(Float mRP) {
        this.mRP = mRP;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
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

    public Float getDiscValue() {
        return discValue;
    }

    public void setDiscValue(Float discValue) {
        this.discValue = discValue;
    }

    public Float getValBeforeTax() {
        return valBeforeTax;
    }

    public void setValBeforeTax(Float valBeforeTax) {
        this.valBeforeTax = valBeforeTax;
    }

    public Float getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Float taxValue) {
        this.taxValue = taxValue;
    }

    public Float getValAfterTax() {
        return valAfterTax;
    }

    public void setValAfterTax(Float valAfterTax) {
        this.valAfterTax = valAfterTax;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }


}
