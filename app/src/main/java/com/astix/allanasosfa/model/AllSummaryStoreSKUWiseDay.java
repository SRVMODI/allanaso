
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSummaryStoreSKUWiseDay {


    public List<TblStoreSKUWiseDaySummary> getTblStoreSKUWiseDaySummary() {
        return tblStoreSKUWiseDaySummary;
    }

    public void setTblStoreSKUWiseDaySummary(List<TblStoreSKUWiseDaySummary> tblStoreSKUWiseDaySummary) {
        this.tblStoreSKUWiseDaySummary = tblStoreSKUWiseDaySummary;
    }

    @SerializedName("tblStoreSKUWiseDaySummary")
    @Expose
    private List<TblStoreSKUWiseDaySummary> tblStoreSKUWiseDaySummary = null;





}
