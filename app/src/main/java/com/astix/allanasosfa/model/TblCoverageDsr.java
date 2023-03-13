package com.astix.allanasosfa.model;

import com.google.gson.annotations.SerializedName;

public class TblCoverageDsr {


    @SerializedName("CoverageAreaNodeID")
    private String CoverageID;

    @SerializedName("CoverageAreaNodeType")
    private String CoverageType;

    @SerializedName("CoverageArea")
    private String CoverageArea;

    @SerializedName("PersonNodeID")
    private String PersonNodeID;

    @SerializedName("PersonNodeType")
    private String PersonNodeType;

    @SerializedName("PersonName")
    private String PersonName;

    @SerializedName("RouteNodeID")
    private String RouteNodeID;

    @SerializedName("RouteNodeType")
    private String RouteNodeType;

    @SerializedName("Route")
    private String Route;

    @SerializedName("Active")
    private String Active;


    public int getFlgTodayRoute() {
        return flgTodayRoute;
    }

    public void setFlgTodayRoute(int flgTodayRoute) {
        this.flgTodayRoute = flgTodayRoute;
    }

    private int flgTodayRoute;

    public String getCoverageID() {
        return CoverageID;
    }

    public void setCoverageID(String coverageID) {
        CoverageID = coverageID;
    }

    public String getCoverageType() {
        return CoverageType;
    }

    public void setCoverageType(String coverageType) {
        CoverageType = coverageType;
    }

    public String getCoverageArea() {
        return CoverageArea;
    }

    public void setCoverageArea(String coverageArea) {
        CoverageArea = coverageArea;
    }

    public String getPersonNodeID() {
        return PersonNodeID;
    }

    public void setPersonNodeID(String personNodeID) {
        PersonNodeID = personNodeID;
    }

    public String getPersonNodeType() {
        return PersonNodeType;
    }

    public void setPersonNodeType(String personNodeType) {
        PersonNodeType = personNodeType;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getRouteNodeID() {
        return RouteNodeID;
    }

    public void setRouteNodeID(String routeNodeID) {
        RouteNodeID = routeNodeID;
    }

    public String getRouteNodeType() {
        return RouteNodeType;
    }

    public void setRouteNodeType(String routeNodeType) {
        RouteNodeType = routeNodeType;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }
}
