
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAQuestionDependentMstr {

    @SerializedName("QuestID")
    @Expose
    private Integer questID;
    @SerializedName("DependentQuestID")
    @Expose
    private Integer dependentQuestID;
    @SerializedName("OptID")
    @Expose
    private String optID;
    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("GrpDepQuestID")
    @Expose
    private Integer grpDepQuestID;

    public Integer getQuestID() {
        return questID;
    }

    public void setQuestID(Integer questID) {
        this.questID = questID;
    }

    public Integer getDependentQuestID() {
        return dependentQuestID;
    }

    public void setDependentQuestID(Integer dependentQuestID) {
        this.dependentQuestID = dependentQuestID;
    }

    public String getOptID() {
        return optID;
    }

    public void setOptID(String optID) {
        this.optID = optID;
    }

    public Integer getGrpQuestID() {
        return grpQuestID;
    }

    public void setGrpQuestID(Integer grpQuestID) {
        this.grpQuestID = grpQuestID;
    }

    public Integer getGrpDepQuestID() {
        return grpDepQuestID;
    }

    public void setGrpDepQuestID(Integer grpDepQuestID) {
        this.grpDepQuestID = grpDepQuestID;
    }

}
