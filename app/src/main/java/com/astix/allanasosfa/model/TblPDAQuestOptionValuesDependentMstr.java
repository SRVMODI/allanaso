
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAQuestOptionValuesDependentMstr {

    @SerializedName("DepQstId")
    @Expose
    private Integer depQstId;
    @SerializedName("DepOptID")
    @Expose
    private String depOptID;
    @SerializedName("QuestId")
    @Expose
    private Integer questId;
    @SerializedName("OptID")
    @Expose
    private String optID;
    @SerializedName("OptDescr")
    @Expose
    private String optDescr;
    @SerializedName("Sequence")
    @Expose
    private Integer sequence;
    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("GrpDepQuestID")
    @Expose
    private Integer grpDepQuestID;
    @SerializedName("DepNodeID")
    @Expose
    private Integer depNodeID;
    @SerializedName("DepNodeType")
    @Expose
    private Integer depNodeType;
    @SerializedName("NodeID")
    @Expose
    private Integer nodeID;
    @SerializedName("NodeType")
    @Expose
    private Integer nodeType;

    public Integer getDepQstId() {
        return depQstId;
    }

    public void setDepQstId(Integer depQstId) {
        this.depQstId = depQstId;
    }

    public String getDepOptID() {
        return depOptID;
    }

    public void setDepOptID(String depOptID) {
        this.depOptID = depOptID;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public String getOptID() {
        return optID;
    }

    public void setOptID(String optID) {
        this.optID = optID;
    }

    public String getOptDescr() {
        return optDescr;
    }

    public void setOptDescr(String optDescr) {
        this.optDescr = optDescr;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Integer getDepNodeID() {
        return depNodeID;
    }

    public void setDepNodeID(Integer depNodeID) {
        this.depNodeID = depNodeID;
    }

    public Integer getDepNodeType() {
        return depNodeType;
    }

    public void setDepNodeType(Integer depNodeType) {
        this.depNodeType = depNodeType;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

}
