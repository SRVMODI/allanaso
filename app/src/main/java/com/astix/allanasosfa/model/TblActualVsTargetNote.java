
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblActualVsTargetNote {


    public String getMsgToDisplay() {
        return msgToDisplay;
    }

    public void setMsgToDisplay(String msgToDisplay) {
        this.msgToDisplay = msgToDisplay;
    }

    @SerializedName("MsgToDisplay")
    @Expose
    private String msgToDisplay;

   
}
