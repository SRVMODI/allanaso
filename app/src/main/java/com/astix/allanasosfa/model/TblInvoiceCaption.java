
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblInvoiceCaption {

    @SerializedName("InvPrefix")
    @Expose
    private String invPrefix;
    @SerializedName("VanIntialInvoiceIds")
    @Expose
    private Integer vanIntialInvoiceIds;
    @SerializedName("InvSuffix")
    @Expose
    private String invSuffix;

    public String getInvPrefix() {
        return invPrefix;
    }

    public void setInvPrefix(String invPrefix) {
        this.invPrefix = invPrefix;
    }

    public Integer getVanIntialInvoiceIds() {
        return vanIntialInvoiceIds;
    }

    public void setVanIntialInvoiceIds(Integer vanIntialInvoiceIds) {
        this.vanIntialInvoiceIds = vanIntialInvoiceIds;
    }

    public String getInvSuffix() {
        return invSuffix;
    }

    public void setInvSuffix(String invSuffix) {
        this.invSuffix = invSuffix;
    }

}
