
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblQuestIDForName {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("QuestID")
    @Expose
    private Integer questID;
    @SerializedName("QuestDesc")
    @Expose
    private String questDesc;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

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

    public String getQuestDesc() {
        return questDesc;
    }

    public void setQuestDesc(String questDesc) {
        this.questDesc = questDesc;
    }

}
