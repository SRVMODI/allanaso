
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductSegementMap {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("BusinessSegmentId")
    @Expose
    private Integer businessSegmentId;
    @SerializedName("SegmentNodeId")
    @Expose
    private Integer segmentNodeId;
    @SerializedName("ProductMRP")
    @Expose
    private Double productMRP;
    @SerializedName("ProductRLP")
    @Expose
    private Double productRLP;
    @SerializedName("ProductTaxAmount")
    @Expose
    private Double productTaxAmount;
    @SerializedName("RetMarginPer")
    @Expose
    private Double retMarginPer;
    @SerializedName("Tax")
    @Expose
    private Double tax;
    @SerializedName("StandardRate")
    @Expose
    private Double standardRate;
    @SerializedName("StandardRateBeforeTax")
    @Expose
    private Double standardRateBeforeTax;
    @SerializedName("StandardTax")
    @Expose
    private Double standardTax;
    @SerializedName("flgPriceAva")
    @Expose
    private Integer flgPriceAva;
    @SerializedName("flgPrdBulkPriceapplicable")
    @Expose
    private Integer flgPrdBulkPriceapplicable;
    @SerializedName("Cutoffvalue")
    @Expose
    private Integer cutoffvalue;
    @SerializedName("StandardRateWholeSale")
    @Expose
    private Double standardRateWholeSale;
    @SerializedName("StandardRateBeforeTaxWholeSale")
    @Expose
    private Double standardRateBeforeTaxWholeSale;
    @SerializedName("StandardTaxWholeSale")
    @Expose
    private Double standardTaxWholeSale;
    @SerializedName("HSNCode")
    @Expose
    private String hSNCode;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBusinessSegmentId() {
        return businessSegmentId;
    }

    public void setBusinessSegmentId(Integer businessSegmentId) {
        this.businessSegmentId = businessSegmentId;
    }

    public Integer getSegmentNodeId() {
        return segmentNodeId;
    }

    public void setSegmentNodeId(Integer segmentNodeId) {
        this.segmentNodeId = segmentNodeId;
    }

    public Double getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(Double productMRP) {
        this.productMRP = productMRP;
    }

    public Double getProductRLP() {
        return productRLP;
    }

    public void setProductRLP(Double productRLP) {
        this.productRLP = productRLP;
    }

    public Double getProductTaxAmount() {
        return productTaxAmount;
    }

    public void setProductTaxAmount(Double productTaxAmount) {
        this.productTaxAmount = productTaxAmount;
    }

    public Double getRetMarginPer() {
        return retMarginPer;
    }

    public void setRetMarginPer(Double retMarginPer) {
        this.retMarginPer = retMarginPer;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(Double standardRate) {
        this.standardRate = standardRate;
    }

    public Double getStandardRateBeforeTax() {
        return standardRateBeforeTax;
    }

    public void setStandardRateBeforeTax(Double standardRateBeforeTax) {
        this.standardRateBeforeTax = standardRateBeforeTax;
    }

    public Double getStandardTax() {
        return standardTax;
    }

    public void setStandardTax(Double standardTax) {
        this.standardTax = standardTax;
    }

    public Integer getFlgPriceAva() {
        return flgPriceAva;
    }

    public void setFlgPriceAva(Integer flgPriceAva) {
        this.flgPriceAva = flgPriceAva;
    }

    public Integer getFlgPrdBulkPriceapplicable() {
        return flgPrdBulkPriceapplicable;
    }

    public void setFlgPrdBulkPriceapplicable(Integer flgPrdBulkPriceapplicable) {
        this.flgPrdBulkPriceapplicable = flgPrdBulkPriceapplicable;
    }

    public Integer getCutoffvalue() {
        return cutoffvalue;
    }

    public void setCutoffvalue(Integer cutoffvalue) {
        this.cutoffvalue = cutoffvalue;
    }

    public Double getStandardRateWholeSale() {
        return standardRateWholeSale;
    }

    public void setStandardRateWholeSale(Double standardRateWholeSale) {
        this.standardRateWholeSale = standardRateWholeSale;
    }

    public Double getStandardRateBeforeTaxWholeSale() {
        return standardRateBeforeTaxWholeSale;
    }

    public void setStandardRateBeforeTaxWholeSale(Double standardRateBeforeTaxWholeSale) {
        this.standardRateBeforeTaxWholeSale = standardRateBeforeTaxWholeSale;
    }

    public Double getStandardTaxWholeSale() {
        return standardTaxWholeSale;
    }

    public void setStandardTaxWholeSale(Double standardTaxWholeSale) {
        this.standardTaxWholeSale = standardTaxWholeSale;
    }

    public String getHSNCode() {
        return hSNCode;
    }

    public void setHSNCode(String hSNCode) {
        this.hSNCode = hSNCode;
    }

}
