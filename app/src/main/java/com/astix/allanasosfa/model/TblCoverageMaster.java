package com.astix.allanasosfa.model;

import com.google.gson.annotations.SerializedName;

public class TblCoverageMaster {


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

    public String getWorkingType() {
        return WorkingType;
    }

    public void setWorkingType(String workingType) {
        WorkingType = workingType;
    }

    @SerializedName("WorkingType")
    private String WorkingType;





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


}
