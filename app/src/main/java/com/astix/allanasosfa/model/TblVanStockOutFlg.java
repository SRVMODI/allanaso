
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblVanStockOutFlg {

    @SerializedName("flgStockOutEntryDone")
    @Expose
    private Integer flgStockOutEntryDone;

    public Integer getFlgStockOutEntryDone() {
        return flgStockOutEntryDone;
    }

    public void setFlgStockOutEntryDone(Integer flgStockOutEntryDone) {
        this.flgStockOutEntryDone = flgStockOutEntryDone;
    }

}
