
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VanStockRequest {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("CoverageAreaNodeId")
    @Expose
    private Integer coverageAreaNodeId;
    @SerializedName("CoverageAreaNodeType")
    @Expose
    private Integer coverageAreaNodeType;
    @SerializedName("TrnDate")
    @Expose
    private String trnDate;
    @SerializedName("ProductString")
    @Expose
    private String productString;
    @SerializedName("StatusId")
    @Expose
    private Integer statusId;

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getCoverageAreaNodeId() {
        return coverageAreaNodeId;
    }

    public void setCoverageAreaNodeId(Integer coverageAreaNodeId) {
        this.coverageAreaNodeId = coverageAreaNodeId;
    }

    public Integer getCoverageAreaNodeType() {
        return coverageAreaNodeType;
    }

    public void setCoverageAreaNodeType(Integer coverageAreaNodeType) {
        this.coverageAreaNodeType = coverageAreaNodeType;
    }

    public String getTrnDate() {
        return trnDate;
    }

    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getProductString() {
        return productString;
    }

    public void setProductString(String productString) {
        this.productString = productString;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

}
