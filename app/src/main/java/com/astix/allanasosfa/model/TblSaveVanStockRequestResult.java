
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblSaveVanStockRequestResult {

    @SerializedName("VanLoadUnLoadCycID")
    @Expose
    private Integer vanLoadUnLoadCycID;
    @SerializedName("flgRequestAccept")
    @Expose
    private Integer flgRequestAccept;

    public Integer getVanLoadUnLoadCycID() {
        return vanLoadUnLoadCycID;
    }

    public void setVanLoadUnLoadCycID(Integer vanLoadUnLoadCycID) {
        this.vanLoadUnLoadCycID = vanLoadUnLoadCycID;
    }

    public Integer getFlgRequestAccept() {
        return flgRequestAccept;
    }

    public void setFlgRequestAccept(Integer flgRequestAccept) {
        this.flgRequestAccept = flgRequestAccept;
    }

}
