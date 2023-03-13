
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreCountDetails {

    @SerializedName("TotStoreAdded")
    @Expose
    private Integer totStoreAdded;
    @SerializedName("TodayStoreAdded")
    @Expose
    private Integer todayStoreAdded;

    public Integer getTotStoreAdded() {
        return totStoreAdded;
    }

    public void setTotStoreAdded(Integer totStoreAdded) {
        this.totStoreAdded = totStoreAdded;
    }

    public Integer getTodayStoreAdded() {
        return todayStoreAdded;
    }

    public void setTodayStoreAdded(Integer todayStoreAdded) {
        this.todayStoreAdded = todayStoreAdded;
    }

}
