
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSummarySKUWiseDay {


    public List<TblSKUWiseDaySummary> getTblSKUWiseDaySummary() {
        return tblSKUWiseDaySummary;
    }

    public void seTblSKUWiseDaySummary(List<TblSKUWiseDaySummary> tblSKUWiseDaySummary) {
        this.tblSKUWiseDaySummary = tblSKUWiseDaySummary;
    }

    @SerializedName("tblSKUWiseDaySummary")
    @Expose
    private List<TblSKUWiseDaySummary> tblSKUWiseDaySummary = null;





}
