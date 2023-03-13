
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIsDBRStockSubmitted {


    @SerializedName("IsDBRStockSubmitted")
    @Expose
    private Integer isDBRStockSubmitted;


    public Integer getIsDBRStockSubmitted() {
        return isDBRStockSubmitted;
    }

    public void setIsDBRStockSubmitted(Integer isDBRStockSubmitted) {
        this.isDBRStockSubmitted = isDBRStockSubmitted;
    }


}
