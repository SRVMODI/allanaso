
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreWiseDaySummary {

    @SerializedName("Store")
    @Expose
    private String store;
    @SerializedName("LinesperBill")
    @Expose
    private Integer linesperBill;
    @SerializedName("StockValue")
    @Expose
    private Float stockValue;
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

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getLinesperBill() {
        return linesperBill;
    }

    public void setLinesperBill(Integer linesperBill) {
        this.linesperBill = linesperBill;
    }

    public Float getStockValue() {
        return stockValue;
    }

    public void setStockValue(Float stockValue) {
        this.stockValue = stockValue;
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

}
