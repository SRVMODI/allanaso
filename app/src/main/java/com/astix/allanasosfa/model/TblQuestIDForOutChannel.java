
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblQuestIDForOutChannel {

    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("QuestID")
    @Expose
    private Integer questID;
    @SerializedName("OptionID")
    @Expose
    private String optionID;
    @SerializedName("SectionCount")
    @Expose
    private Integer sectionCount;

    public Integer getGrpQuestID() {
        return grpQuestID;
    }

    public void setGrpQuestID(Integer grpQuestID) {
        this.grpQuestID = grpQuestID;
    }

    public Integer getQuestID() {
        return questID;
    }

    public void setQuestID(Integer questID) {
        this.questID = questID;
    }

    public String getOptionID() {
        return optionID;
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }

    public Integer getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(Integer sectionCount) {
        this.sectionCount = sectionCount;
    }

}
