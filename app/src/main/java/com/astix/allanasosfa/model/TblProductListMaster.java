
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductListMaster {

    @SerializedName("CatID")
    @Expose
    private Integer catID;
    @SerializedName("ProductID")
    @Expose
    private Integer productID;
    @SerializedName("ProductShortName")
    @Expose
    private String productShortName;
    @SerializedName("DisplayUnit")
    @Expose
    private String displayUnit;
    @SerializedName("CalculateKilo")
    @Expose
    private Integer calculateKilo;
    @SerializedName("KGLiter")
    @Expose
    private Integer kGLiter;
    @SerializedName("ProductType")
    @Expose
    private String productType;
    @SerializedName("SearchField")
    @Expose
    private String searchField;
    @SerializedName("ManufacturerID")
    @Expose
    private Integer manufacturerID;
    @SerializedName("PrdOrdr")
    @Expose
    private Integer prdOrdr;
    @SerializedName("RptUnitName")
    @Expose
    private String rptUnitName;
    @SerializedName("PerbaseUnit")
    @Expose
    private Integer perbaseUnit;
    @SerializedName("HSNCode")
    @Expose
    private String hSNCode;

    public Integer getCatID() {
        return catID;
    }

    public void setCatID(Integer catID) {
        this.catID = catID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public Integer getCalculateKilo() {
        return calculateKilo;
    }

    public void setCalculateKilo(Integer calculateKilo) {
        this.calculateKilo = calculateKilo;
    }

    public Integer getKGLiter() {
        return kGLiter;
    }

    public void setKGLiter(Integer kGLiter) {
        this.kGLiter = kGLiter;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public Integer getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(Integer manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public Integer getPrdOrdr() {
        return prdOrdr;
    }

    public void setPrdOrdr(Integer prdOrdr) {
        this.prdOrdr = prdOrdr;
    }

    public String getRptUnitName() {
        return rptUnitName;
    }

    public void setRptUnitName(String rptUnitName) {
        this.rptUnitName = rptUnitName;
    }

    public Integer getPerbaseUnit() {
        return perbaseUnit;
    }

    public void setPerbaseUnit(Integer perbaseUnit) {
        this.perbaseUnit = perbaseUnit;
    }

    public String getHSNCode() {
        return hSNCode;
    }

    public void setHSNCode(String hSNCode) {
        this.hSNCode = hSNCode;
    }

}
