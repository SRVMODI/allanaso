
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblInstrumentMaster {

    @SerializedName("InstrumentModeId")
    @Expose
    private Integer instrumentModeId;
    @SerializedName("InstrumentMode")
    @Expose
    private String instrumentMode;
    @SerializedName("InstrumentType")
    @Expose
    private Integer instrumentType;

    public Integer getInstrumentModeId() {
        return instrumentModeId;
    }

    public void setInstrumentModeId(Integer instrumentModeId) {
        this.instrumentModeId = instrumentModeId;
    }

    public String getInstrumentMode() {
        return instrumentMode;
    }

    public void setInstrumentMode(String instrumentMode) {
        this.instrumentMode = instrumentMode;
    }

    public Integer getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(Integer instrumentType) {
        this.instrumentType = instrumentType;
    }

}
