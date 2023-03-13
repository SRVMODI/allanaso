
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPendingInvoices {

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("StoreName")
    @Expose
    private String storeName;

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;

    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;

    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDistID() {
        return distID;
    }

    public void setDistID(String distID) {
        this.distID = distID;
    }

    public String getInvoiceForDate() {
        return invoiceForDate;
    }

    public void setInvoiceForDate(String invoiceForDate) {
        this.invoiceForDate = invoiceForDate;
    }

    public Integer getFlgSubmit() {
        return flgSubmit;
    }

    public void setFlgSubmit(Integer flgSubmit) {
        this.flgSubmit = flgSubmit;
    }

    @SerializedName("RouteID")
    @Expose
    private Integer routeID;

    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;

    @SerializedName("RouteName")
    @Expose
    private String routeName;

    @SerializedName("DistID")
    @Expose
    private String distID;

    @SerializedName("InvoiceForDate")
    @Expose
    private String invoiceForDate;

    @SerializedName("flgSubmit")
    @Expose
    private Integer flgSubmit;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @SerializedName("OrderID")
    @Expose
    private String orderID;

    public Integer getStoreId() {
        return storeID;
    }

    public void setStoreId(Integer storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    @SerializedName("DistName")
    @Expose
    private String distName;
    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }
}
