
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlgDayEndRequestAccept {

    @SerializedName("flgDayEndRequestAccept")
    @Expose
    private Integer flgDayEndRequestAccept;

    public Integer getFlgDayEndRequestAccept() {
        return flgDayEndRequestAccept;
    }

    public void setFlgDayEndRequestAccept(Integer flgDayEndRequestAccept) {
        this.flgDayEndRequestAccept = flgDayEndRequestAccept;
    }

}
