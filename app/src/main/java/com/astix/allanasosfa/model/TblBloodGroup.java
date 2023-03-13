
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblBloodGroup {

    @SerializedName("BloddGroups")
    @Expose
    private String bloddGroups;

    public String getBloddGroups() {
        return bloddGroups;
    }

    public void setBloddGroups(String bloddGroups) {
        this.bloddGroups = bloddGroups;
    }

}
