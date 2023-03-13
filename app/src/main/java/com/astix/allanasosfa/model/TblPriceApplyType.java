
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPriceApplyType {

    @SerializedName("DiscountLevel")
    @Expose
    private Integer discountLevel;
    @SerializedName("cutoffvalue")
    @Expose
    private Integer cutoffvalue;

    public Integer getDiscountLevel() {
        return discountLevel;
    }

    public void setDiscountLevel(Integer discountLevel) {
        this.discountLevel = discountLevel;
    }

    public Integer getCutoffvalue() {
        return cutoffvalue;
    }

    public void setCutoffvalue(Integer cutoffvalue) {
        this.cutoffvalue = cutoffvalue;
    }

}
