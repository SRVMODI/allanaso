
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIsSchemeApplicable {

    @SerializedName("IsSchemeApplicable")
    @Expose
    private Integer isSchemeApplicable;

    public Integer getIsSchemeApplicable() {
        return isSchemeApplicable;
    }

    public void setIsSchemeApplicable(Integer isSchemeApplicable) {
        this.isSchemeApplicable = isSchemeApplicable;
    }

}
