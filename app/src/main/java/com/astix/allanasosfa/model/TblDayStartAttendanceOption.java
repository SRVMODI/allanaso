
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDayStartAttendanceOption {

    @SerializedName("ReasonId")
    @Expose
    private Integer reasonId;
    @SerializedName("ReasonDescr")
    @Expose
    private String reasonDescr;
    @SerializedName("FlgToShowTextBox")
    @Expose
    private Integer flgToShowTextBox;
    @SerializedName("flgSOApplicable")
    @Expose
    private Integer flgSOApplicable;
    @SerializedName("flgDSRApplicable")
    @Expose
    private Integer flgDSRApplicable;
    @SerializedName("flgNoVisitOption")
    @Expose
    private Integer flgNoVisitOption;
    @SerializedName("SeqNo")
    @Expose
    private Integer seqNo;
    @SerializedName("flgDelayedReason")
    @Expose
    private Integer flgDelayedReason;


    @SerializedName("flgMarketVisit")
    @Expose
    private Integer flgMarketVisit;

    public Integer getFlgMarketVisit() {
        return flgMarketVisit;
    }

    public void setFlgMarketVisit(Integer flgMarketVisit) {
        this.flgMarketVisit = flgMarketVisit;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonDescr() {
        return reasonDescr;
    }

    public void setReasonDescr(String reasonDescr) {
        this.reasonDescr = reasonDescr;
    }

    public Integer getFlgToShowTextBox() {
        return flgToShowTextBox;
    }

    public void setFlgToShowTextBox(Integer flgToShowTextBox) {
        this.flgToShowTextBox = flgToShowTextBox;
    }

    public Integer getFlgSOApplicable() {
        return flgSOApplicable;
    }

    public void setFlgSOApplicable(Integer flgSOApplicable) {
        this.flgSOApplicable = flgSOApplicable;
    }

    public Integer getFlgDSRApplicable() {
        return flgDSRApplicable;
    }

    public void setFlgDSRApplicable(Integer flgDSRApplicable) {
        this.flgDSRApplicable = flgDSRApplicable;
    }

    public Integer getFlgNoVisitOption() {
        return flgNoVisitOption;
    }

    public void setFlgNoVisitOption(Integer flgNoVisitOption) {
        this.flgNoVisitOption = flgNoVisitOption;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getFlgDelayedReason() {
        return flgDelayedReason;
    }

    public void setFlgDelayedReason(Integer flgDelayedReason) {
        this.flgDelayedReason = flgDelayedReason;
    }

}
