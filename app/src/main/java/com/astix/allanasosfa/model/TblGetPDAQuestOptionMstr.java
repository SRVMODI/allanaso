
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAQuestOptionMstr {

    @SerializedName("QuestID")
    @Expose
    private Integer questID;
    @SerializedName("OptID")
    @Expose
    private String optID;
    @SerializedName("OptionDescr")
    @Expose
    private String optionDescr;
    @SerializedName("Sequence")
    @Expose
    private Integer sequence;

    public Integer getQuestID() {
        return questID;
    }

    public void setQuestID(Integer questID) {
        this.questID = questID;
    }

    public String getOptID() {
        return optID;
    }

    public void setOptID(String optID) {
        this.optID = optID;
    }

    public String getOptionDescr() {
        return optionDescr;
    }

    public void setOptionDescr(String optionDescr) {
        this.optionDescr = optionDescr;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

}
