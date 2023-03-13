package com.astix.allanasosfa.model;

public class JointVisitMemberDetail {

    private String jointVisitId;
    private int fellowPersonNodeId;
    private int fellowPersonNodeType;
    private String fellowPersonName;

    public String getStoreVisitCoded() {
        return storeVisitCoded;
    }

    public void setStoreVisitCoded(String storeVisitCoded) {
        this.storeVisitCoded = storeVisitCoded;
    }

    private String storeVisitCoded;

    public String getFellowPersonName() {
        return fellowPersonName;
    }

    public void setFellowPersonName(String fellowPersonName) {
        this.fellowPersonName = fellowPersonName;
    }

    public String getJointVisitId() {
        return jointVisitId;
    }

    public void setJointVisitId(String jointVisitId) {
        this.jointVisitId = jointVisitId;
    }

    public int getFellowPersonNodeId() {
        return fellowPersonNodeId;
    }

    public void setFellowPersonNodeId(int fellowPersonNodeId) {
        this.fellowPersonNodeId = fellowPersonNodeId;
    }

    public int getFellowPersonNodeType() {
        return fellowPersonNodeType;
    }

    public void setFellowPersonNodeType(int fellowPersonNodeType) {
        this.fellowPersonNodeType = fellowPersonNodeType;
    }
}
