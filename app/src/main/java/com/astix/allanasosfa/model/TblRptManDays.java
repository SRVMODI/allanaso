
package com.astix.allanasosfa.model;

import com.google.gson.annotations.SerializedName;

public class TblRptManDays {

    @SerializedName("Description")
    private String description;
    @SerializedName("In Field_MTD")
    private int inFieldMTD;
    @SerializedName("In Field_Yesterday")
    private int inFieldYesterday;
    @SerializedName("Planned")
    private int planned;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInFieldMTD() {
        return inFieldMTD;
    }

    public void setInFieldMTD(int inFieldMTD) {
        this.inFieldMTD = inFieldMTD;
    }

    public int getInFieldYesterday() {
        return inFieldYesterday;
    }

    public void setInFieldYesterday(int inFieldYesterday) {
        this.inFieldYesterday = inFieldYesterday;
    }

    public int getPlanned() {
        return planned;
    }

    public void setPlanned(int planned) {
        this.planned = planned;
    }

}
