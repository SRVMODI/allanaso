
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDANotificationMaster {

    @SerializedName("MsgServerID")
    @Expose
    private Integer msgServerID;
    @SerializedName("NotificationMessage")
    @Expose
    private String notificationMessage;
    @SerializedName("MsgSendingTime")
    @Expose
    private String msgSendingTime;

    public Integer getMsgServerID() {
        return msgServerID;
    }

    public void setMsgServerID(Integer msgServerID) {
        this.msgServerID = msgServerID;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getMsgSendingTime() {
        return msgSendingTime;
    }

    public void setMsgSendingTime(String msgSendingTime) {
        this.msgSendingTime = msgSendingTime;
    }

}
