
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTargetVsAchieved {


    public List<TblActualVsTargetReport> getTblActualVsTargetReport() {
        return tblActualVsTargetReport;
    }

    public void setTblActualVsTargetReport(List<TblActualVsTargetReport> tblActualVsTargetReport) {
        this.tblActualVsTargetReport = tblActualVsTargetReport;
    }

    public List<TblValueVolumeTarget> getTblValueVolumeTarget() {
        return tblValueVolumeTarget;
    }

    public void setTblValueVolumeTarget(List<TblValueVolumeTarget> tblValueVolumeTarget) {
        this.tblValueVolumeTarget = tblValueVolumeTarget;
    }

    @SerializedName("tblActualVsTargetReport")
    @Expose
    private List<TblActualVsTargetReport> tblActualVsTargetReport = null;


    @SerializedName("tblValueVolumeTarget")
    @Expose
    private List<TblValueVolumeTarget> tblValueVolumeTarget = null;

    public List<TblActualVsTargetNote> getTblActualVsTargetNote() {
        return tblActualVsTargetNote;
    }

    public void setTblActualVsTargetNote(List<TblActualVsTargetNote> tblActualVsTargetNote) {
        this.tblActualVsTargetNote = tblActualVsTargetNote;
    }

    @SerializedName("tblActualVsTargetNote")
    @Expose
    private List<TblActualVsTargetNote> tblActualVsTargetNote = null;


}
