package com.astix.allanasosfa.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblRouteListMaster {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("RouteType")
    @Expose
    private Integer routeType;
    @SerializedName("Descr")
    @Expose
    private String descr;
    @SerializedName("Active")
    @Expose
    private Integer active;
    @SerializedName("CoverageAreaNodeID")
    @Expose
    private Integer CoverageAreaNodeID;
    @SerializedName("CoverageAreaNodeType")
    @Expose
    private Integer CoverageAreaNodeType;

    public Integer getCoverageAreaNodeType() {
        return CoverageAreaNodeType;
    }

    public void setCoverageAreaNodeType(Integer coverageAreaNodeType) {
        CoverageAreaNodeType = coverageAreaNodeType;
    }

    public Integer getCoverageAreaNodeID() {
        return CoverageAreaNodeID;
    }

    public void setCoverageAreaNodeID(Integer coverageAreaNodeID) {
        CoverageAreaNodeID = coverageAreaNodeID;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getRouteType() {
        return routeType;
    }

    public void setRouteType(Integer routeType) {
        this.routeType = routeType;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
