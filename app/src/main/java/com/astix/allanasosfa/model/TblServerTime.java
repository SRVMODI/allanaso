
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblServerTime {

    @SerializedName("ServerTime")
    @Expose
    private String serverTime;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String cycleID) {
        this.serverTime = serverTime;
    }


}
