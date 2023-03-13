
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblQuestionsSurvey {

    @SerializedName("QstnID")
    @Expose
    private Integer qstnID;
    @SerializedName("QstnText")
    @Expose
    private String qstnText;

    public String getFlgActive() {
        return FlgActive;
    }

    public void setFlgActive(String flgActive) {
        FlgActive = flgActive;

    }

    public String getFlgOrder() {
        return FlgOrder;
    }

    public void setFlgOrder(String flgOrder) {
        FlgOrder = flgOrder;

    }

    @SerializedName("flgActive")
    @Expose
    private String FlgActive;

    @SerializedName("flgOrder")
    @Expose
    private String FlgOrder;

    public Integer getQstnID() {
        return qstnID;
    }

    public void setQstnID(Integer qstnID) {
        this.qstnID = qstnID;
    }

    public String getQstnText() {
        return qstnText;
    }

    public void setQstnText(String qstnText) {
        this.qstnText = qstnText;
    }


}
