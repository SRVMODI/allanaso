
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;
    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("AppVersionNo")
    @Expose
    private String AppVersionNo;

    public String getAppVersionNo() {
        return AppVersionNo;
    }

    public void setAppVersionNo(String appVersionNo) {
        AppVersionNo = appVersionNo;
    }

    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;
    @SerializedName("ForDate")
    @Expose
    private String forDate;
    @SerializedName("RegistrationId")
    @Expose
    private String registrationId;
    @SerializedName("RouteNodeId")
    @Expose
    private Integer routeNodeId;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
    @SerializedName("InvoiceList")
    @Expose
    private  List<InvoiceList> invoiceList;
    @SerializedName("FlgAllRouteData")
    @Expose
    private Integer flgAllRouteData;
    @SerializedName("CoverageAreaNodeId")
    @Expose
    private Integer coverageAreaNodeId;
    @SerializedName("CoverageAreaNodeType")
    @Expose
    private Integer coverageAreaNodeType;

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Integer applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getRouteNodeId() {
        return routeNodeId;
    }

    public void setRouteNodeId(Integer routeNodeId) {
        this.routeNodeId = routeNodeId;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    public List<InvoiceList> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceList> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public Integer getFlgAllRouteData() {
        return flgAllRouteData;
    }

    public void setFlgAllRouteData(Integer flgAllRouteData) {
        this.flgAllRouteData = flgAllRouteData;
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

}
