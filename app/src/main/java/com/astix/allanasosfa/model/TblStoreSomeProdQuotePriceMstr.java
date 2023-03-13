
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreSomeProdQuotePriceMstr {

    @SerializedName("PrdId")
    @Expose
    private Integer prdId;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("QPBT")
    @Expose
    private Double qPBT;
    @SerializedName("QPAT")
    @Expose
    private Double qPAT;
    @SerializedName("QPTaxAmt")
    @Expose
    private Double qPTaxAmt;
    @SerializedName("MinDlvryQty")
    @Expose
    private Integer minDlvryQty;
    @SerializedName("UOMID")
    @Expose
    private Integer uOMID;

    public Integer getPrdId() {
        return prdId;
    }

    public void setPrdId(Integer prdId) {
        this.prdId = prdId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Double getQPBT() {
        return qPBT;
    }

    public void setQPBT(Double qPBT) {
        this.qPBT = qPBT;
    }

    public Double getQPAT() {
        return qPAT;
    }

    public void setQPAT(Double qPAT) {
        this.qPAT = qPAT;
    }

    public Double getQPTaxAmt() {
        return qPTaxAmt;
    }

    public void setQPTaxAmt(Double qPTaxAmt) {
        this.qPTaxAmt = qPTaxAmt;
    }

    public Integer getMinDlvryQty() {
        return minDlvryQty;
    }

    public void setMinDlvryQty(Integer minDlvryQty) {
        this.minDlvryQty = minDlvryQty;
    }

    public Integer getUOMID() {
        return uOMID;
    }

    public void setUOMID(Integer uOMID) {
        this.uOMID = uOMID;
    }

}
