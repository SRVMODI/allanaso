
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblManagerMstr {

    @SerializedName("PersonID")
    @Expose
    private Integer personID;
    @SerializedName("PersonType")
    @Expose
    private Integer personType;
    @SerializedName("PersonName")
    @Expose
    private String personName;
    @SerializedName("ManagerID")
    @Expose
    private Integer managerID;
    @SerializedName("ManagerType")
    @Expose
    private Integer managerType;
    @SerializedName("ManagerName")
    @Expose
    private String managerName;

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

}
