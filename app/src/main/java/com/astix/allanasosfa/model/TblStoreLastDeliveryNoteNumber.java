
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreLastDeliveryNoteNumber {

    @SerializedName("LastDeliveryNoteNumber")
    @Expose
    private Integer lastDeliveryNoteNumber;

    public Integer getLastDeliveryNoteNumber() {
        return lastDeliveryNoteNumber;
    }

    public void setLastDeliveryNoteNumber(Integer lastDeliveryNoteNumber) {
        this.lastDeliveryNoteNumber = lastDeliveryNoteNumber;
    }

}
