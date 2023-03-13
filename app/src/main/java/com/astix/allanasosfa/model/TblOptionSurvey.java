
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblOptionSurvey {

    @SerializedName("OptionID")
    @Expose
    private Integer optionID;
    @SerializedName("OptionText")
    @Expose
    private String optionText;


    public String getQstnID() {
        return qstnID;
    }

    public void setQstnID(String qstnID) {
        this.qstnID = qstnID;

    }

    public String getFlgaActive() {
        return FlgaActive;
    }

    public void setFlgaActive(String flgaActive) {
        FlgaActive = flgaActive;

    }

    @SerializedName("QstnID")
    @Expose
    private String qstnID;

    @SerializedName("flgaActive")
    @Expose
    private String FlgaActive;

    public Integer getOptionID() {
        return optionID;
    }

    public void setOptionID(Integer optionID) {
        this.optionID = optionID;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }


}
