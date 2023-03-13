
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PDAConfirmVanStockModel {

    @SerializedName("tblPDAConfirmVanStockResult")
    @Expose
    private List<TblPDAConfirmVanStockResult> tblPDAConfirmVanStockResult = null;

    public List<TblPDAConfirmVanStockResult> getTblPDAConfirmVanStockResult() {
        return tblPDAConfirmVanStockResult;
    }

    public void setTblPDAConfirmVanStockResult(List<TblPDAConfirmVanStockResult> tblPDAConfirmVanStockResult) {
        this.tblPDAConfirmVanStockResult = tblPDAConfirmVanStockResult;
    }

}
