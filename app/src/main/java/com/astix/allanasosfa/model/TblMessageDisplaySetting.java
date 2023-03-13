
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblMessageDisplaySetting {

    @SerializedName("Flag")
    @Expose
    private Integer flag;
    @SerializedName("MsgToDisplay")
    @Expose
    private String msgToDisplay;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMsgToDisplay() {
        return msgToDisplay;
    }

    public void setMsgToDisplay(String msgToDisplay) {
        this.msgToDisplay = msgToDisplay;
    }

}
