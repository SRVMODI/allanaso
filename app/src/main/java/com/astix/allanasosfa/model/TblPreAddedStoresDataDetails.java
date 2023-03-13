
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPreAddedStoresDataDetails {

    @SerializedName("StoreIDDB")
    @Expose
    private Integer storeIDDB;
    @SerializedName("GrpQuestID")
    @Expose
    private Integer grpQuestID;
    @SerializedName("QstId")
    @Expose
    private Integer qstId;
    @SerializedName("AnsControlTypeID")
    @Expose
    private Integer ansControlTypeID;
    @SerializedName("Ans")
    @Expose
    private String ans;

    public Integer getStoreIDDB() {
        return storeIDDB;
    }

    public void setStoreIDDB(Integer storeIDDB) {
        this.storeIDDB = storeIDDB;
    }

    public Integer getGrpQuestID() {
        return grpQuestID;
    }

    public void setGrpQuestID(Integer grpQuestID) {
        this.grpQuestID = grpQuestID;
    }

    public Integer getQstId() {
        return qstId;
    }

    public void setQstId(Integer qstId) {
        this.qstId = qstId;
    }

    public Integer getAnsControlTypeID() {
        return ansControlTypeID;
    }

    public void setAnsControlTypeID(Integer ansControlTypeID) {
        this.ansControlTypeID = ansControlTypeID;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

}
