
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblIncentiveMsgToDisplay {

    @SerializedName("MsgToDisplay")
    @Expose
    private String msgToDisplay;

    public String getMsgToDisplay() {
        return msgToDisplay;
    }

    public void setMsgToDisplay(String incId) {
        this.msgToDisplay = incId;
    }


    @SerializedName("flgBankDetailsToShow")
    @Expose
    private Integer flgBankDetailsToShow;

    public Integer getFlgBankDetailsToShow() {
        return flgBankDetailsToShow;
    }

    public void setFlgBankDetailsToShow(Integer flgBankDetailsToShow) {
        this.flgBankDetailsToShow = flgBankDetailsToShow;
    }




}
