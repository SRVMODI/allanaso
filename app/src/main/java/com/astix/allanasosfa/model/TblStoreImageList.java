
package com.astix.allanasosfa.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreImageList {



    @SerializedName("StoreIDDB")
    @Expose
    private String StoreIDDB;

    public String getStoreIDDB() {
        return StoreIDDB;
    }

    public void setStoreIDDB(String storeIDDB) {
        StoreIDDB = storeIDDB;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getStoreImagename() {
        return StoreImagename;
    }

    public void setStoreImagename(String storeImagename) {
        StoreImagename = storeImagename;
    }

    public Integer getImageType() {
        return ImageType;
    }

    public void setImageType(Integer imageType) {
        ImageType = imageType;
    }

    public Integer getFlgManagerUploaded() {
        return flgManagerUploaded;
    }

    public void setFlgManagerUploaded(Integer flgManagerUploaded) {
        this.flgManagerUploaded = flgManagerUploaded;
    }

    @SerializedName("StoreID")
    @Expose
    private String StoreID;

    @SerializedName("StoreImagename")
    @Expose
    private String StoreImagename;


    @SerializedName("ImageType")
    @Expose
    private Integer ImageType;

    @SerializedName("flgManagerUploaded")
    @Expose
    private Integer flgManagerUploaded;

}
