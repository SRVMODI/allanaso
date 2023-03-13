
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUOMMapping {

    @SerializedName("NodeID")
    @Expose
    private Integer nodeID;
    @SerializedName("NodeType")
    @Expose
    private Integer nodeType;
    @SerializedName("BaseUOMID")
    @Expose
    private Integer baseUOMID;
    @SerializedName("PackUOMID")
    @Expose
    private Integer packUOMID;
    @SerializedName("RelConversionUnits")
    @Expose
    private Double relConversionUnits;
    @SerializedName("flgVanLoading")
    @Expose
    private Integer flgVanLoading;

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

    public Integer getBaseUOMID() {
        return baseUOMID;
    }

    public void setBaseUOMID(Integer baseUOMID) {
        this.baseUOMID = baseUOMID;
    }

    public Integer getPackUOMID() {
        return packUOMID;
    }

    public void setPackUOMID(Integer packUOMID) {
        this.packUOMID = packUOMID;
    }

    public Double getRelConversionUnits() {
        return relConversionUnits;
    }

    public void setRelConversionUnits(Double relConversionUnits) {
        this.relConversionUnits = relConversionUnits;
    }

    public Integer getFlgVanLoading() {
        return flgVanLoading;
    }

    public void setFlgVanLoading(Integer flgVanLoading) {
        this.flgVanLoading = flgVanLoading;
    }

}
