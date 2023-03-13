
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblTotalEarning {

    @SerializedName("Total_Earning")
    @Expose
    private String total_Earning;

    public String getTotal_Earning() {
        return total_Earning;
    }

    public void setTotal_Earning(String total_Earning) {
        this.total_Earning = total_Earning;
    }

}
