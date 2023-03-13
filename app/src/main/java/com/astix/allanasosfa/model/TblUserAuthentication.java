
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUserAuthentication {

    @SerializedName("flgUserAuthenticated")
    @Expose
    private Integer flgUserAuthenticated;

    @SerializedName("PersonName")
    @Expose
    private String personName;
    @SerializedName("FlgRegistered")
    @Expose
    private Integer flgRegistered;
    @SerializedName("flgToShowAllRoutesData")
    @Expose
    private Integer flgToShowAllRoutesData;
    @SerializedName("PersonNodeID")
    @Expose
    private Integer personNodeID;
    @SerializedName("PersonNodeType")
    @Expose
    private Integer personNodeType;
    @SerializedName("CoverageAreaNodeID")
    @Expose
    private Integer coverageAreaNodeID;
    @SerializedName("CoverageAreaNodeType")
    @Expose
    private Integer coverageAreaNodeType;
    @SerializedName("flgPersonTodaysAtt")
    @Expose
    private Integer flgPersonTodaysAtt;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("SelfieName")
    @Expose
    private String selfieName;
    @SerializedName("SelfieNameURL")
    @Expose
    private String selfieNameURL;
    @SerializedName("SalesAreaName")
    @Expose
    private String salesAreaName;
    @SerializedName("WorkingType")
    @Expose
    private Integer workingType;

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }
    @SerializedName("EmailID")
    private String EmailID;

    public Integer getFlgUserAuthenticated() {
        return flgUserAuthenticated;
    }

    public void setFlgUserAuthenticated(Integer flgUserAuthenticated) {
        this.flgUserAuthenticated = flgUserAuthenticated;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getFlgRegistered() {
        return flgRegistered;
    }

    public void setFlgRegistered(Integer flgRegistered) {
        this.flgRegistered = flgRegistered;
    }

    public Integer getFlgToShowAllRoutesData() {
        return flgToShowAllRoutesData;
    }

    public void setFlgToShowAllRoutesData(Integer flgToShowAllRoutesData) {
        this.flgToShowAllRoutesData = flgToShowAllRoutesData;
    }

    public Integer getPersonNodeID() {
        return personNodeID;
    }

    public void setPersonNodeID(Integer personNodeID) {
        this.personNodeID = personNodeID;
    }

    public Integer getPersonNodeType() {
        return personNodeType;
    }

    public void setPersonNodeType(Integer personNodeType) {
        this.personNodeType = personNodeType;
    }

    public Integer getCoverageAreaNodeID() {
        return coverageAreaNodeID;
    }

    public void setCoverageAreaNodeID(Integer coverageAreaNodeID) {
        this.coverageAreaNodeID = coverageAreaNodeID;
    }

    public Integer getCoverageAreaNodeType() {
        return coverageAreaNodeType;
    }

    public void setCoverageAreaNodeType(Integer coverageAreaNodeType) {
        this.coverageAreaNodeType = coverageAreaNodeType;
    }

    public Integer getFlgPersonTodaysAtt() {
        return flgPersonTodaysAtt;
    }

    public void setFlgPersonTodaysAtt(Integer flgPersonTodaysAtt) {
        this.flgPersonTodaysAtt = flgPersonTodaysAtt;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getSelfieName() {
        return selfieName;
    }

    public void setSelfieName(String selfieName) {
        this.selfieName = selfieName;
    }

    public String getSelfieNameURL() {
        return selfieNameURL;
    }

    public void setSelfieNameURL(String selfieNameURL) {
        this.selfieNameURL = selfieNameURL;
    }

    public String getSalesAreaName() {
        return salesAreaName;
    }

    public void setSalesAreaName(String salesAreaName) {
        this.salesAreaName = salesAreaName;
    }

    public Integer getWorkingType() {
        return workingType;
    }

    public void setWorkingType(Integer workingType) {
        this.workingType = workingType;
    }

}
