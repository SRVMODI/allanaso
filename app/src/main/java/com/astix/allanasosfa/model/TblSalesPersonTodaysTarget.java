
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblSalesPersonTodaysTarget {


    @SerializedName("ValueTarget")
    @Expose
    private String valueTarget;


    public String getValueTarget() {
        return valueTarget;
    }

    public void setValueTarget(String valueTarget) {
        this.valueTarget = valueTarget;
    }


}
