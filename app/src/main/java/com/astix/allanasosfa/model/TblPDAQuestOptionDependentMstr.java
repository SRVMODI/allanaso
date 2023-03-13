package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 24-Sep-18.
 */

public class TblPDAQuestOptionDependentMstr {

    @SerializedName("QstId")
    @Expose
    private Integer qstId;
    @SerializedName("DepQstId")
    @Expose
    private Integer depQstId;
    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("GrpDepQuestID")
    @Expose
    private Integer grpDepQuestID;

    public Integer getQstId() {
        return qstId;
    }

    public void setQstId(Integer qstId) {
        this.qstId = qstId;
    }

    public Integer getDepQstId() {
        return depQstId;
    }

    public void setDepQstId(Integer depQstId) {
        this.depQstId = depQstId;
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
