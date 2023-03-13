
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStateCityMaster {

    @SerializedName("StateID")
    @Expose
    private Integer stateID;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("CityID")
    @Expose
    private Integer cityID;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CityDefault")
    @Expose
    private Integer cityDefault;

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCityDefault() {
        return cityDefault;
    }

    public void setCityDefault(Integer cityDefault) {
        this.cityDefault = cityDefault;
    }

}
