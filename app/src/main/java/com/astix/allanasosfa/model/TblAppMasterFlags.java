
package com.astix.allanasosfa.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAppMasterFlags {

    @SerializedName("flgDistributorCheckIn")
    @Expose
    private Integer flgDistributorCheckIn;
    @SerializedName("flgDBRStockInApp")
    @Expose
    private Integer flgDBRStockInApp;
    @SerializedName("flgDBRStockEdit")
    @Expose
    private Integer flgDBRStockEdit;
    @SerializedName("flgDBRStockCalculate")
    @Expose
    private Integer flgDBRStockCalculate;
    @SerializedName("flgDBRStockControl")
    @Expose
    private Integer flgDBRStockControl;
    @SerializedName("flgCollRequired")
    @Expose
    private Integer flgCollRequired;
    @SerializedName("flgCollReqOrdr")
    @Expose
    private Integer flgCollReqOrdr;
    @SerializedName("flgCollTab")
    @Expose
    private Integer flgCollTab;
    @SerializedName("flgCollDefControl")
    @Expose
    private Integer flgCollDefControl;
    @SerializedName("flgCashDiscount")
    @Expose
    private Integer flgCashDiscount;
    @SerializedName("flgCollControlRule")
    @Expose
    private Integer flgCollControlRule;
    @SerializedName("flgSchemeAvailable")
    @Expose
    private Integer flgSchemeAvailable;
    @SerializedName("flgSchemeAllowEntry")
    @Expose
    private Integer flgSchemeAllowEntry;
    @SerializedName("flgSchemeAllowEdit")
    @Expose
    private Integer flgSchemeAllowEdit;
    @SerializedName("flgQuotationIsAvailable")
    @Expose
    private Integer flgQuotationIsAvailable;
    @SerializedName("flgExecutionIsAvailable")
    @Expose
    private Integer flgExecutionIsAvailable;
    @SerializedName("flgExecutionPhotoCompulsory")
    @Expose
    private Integer flgExecutionPhotoCompulsory;
    @SerializedName("flgTargetShowatStart")
    @Expose
    private Integer flgTargetShowatStart;
    @SerializedName("flgIncentiveShowatStart")
    @Expose
    private Integer flgIncentiveShowatStart;
    @SerializedName("flgInvoicePrint")
    @Expose
    private Integer flgInvoicePrint;
    @SerializedName("flgShowPOSM")
    @Expose
    private Integer flgShowPOSM;
    @SerializedName("flgVisitStartOutstandingDetails")
    @Expose
    private Integer flgVisitStartOutstandingDetails;
    @SerializedName("flgVisitStartSchemeDetails")
    @Expose
    private Integer flgVisitStartSchemeDetails;
    @SerializedName("flgStoreDetailsEdit")
    @Expose
    private Integer flgStoreDetailsEdit;
    @SerializedName("flgShowDeliveryAddressButtonOnOrder")
    @Expose
    private Integer flgShowDeliveryAddressButtonOnOrder;
    @SerializedName("flgShowManagerOnStoreList")
    @Expose
    private Integer flgShowManagerOnStoreList;
    @SerializedName("flgRptTargetVsAchived")
    @Expose
    private Integer flgRptTargetVsAchived;

    @SerializedName("SalesNodeID")
    @Expose
    private Integer salesNodeID;
    @SerializedName("SalesNodetype")
    @Expose
    private Integer salesNodetype;
    @SerializedName("WorkingTypeID")
    @Expose
    private Integer workingTypeID;

    @SerializedName("flgVanStockInApp")
    @Expose
    private Integer flgVanStockInApp;

    @SerializedName("flgVanStockEdit")
    @Expose
    private Integer flgVanStockEdit;

    @SerializedName("flgVanStockCalculate")
    @Expose
    private Integer flgVanStockCalculate;

    @SerializedName("flgVanStockControl")
    @Expose
    private Integer flgVanStockControl;

    @SerializedName("flgStockRefillReq")
    @Expose
    private Integer flgStockRefillReq;

    @SerializedName("flgDayEnd")
    @Expose
    private Integer flgDayEnd;

    @SerializedName("flgStockUnloadAtCycleEnd")
    @Expose
    private Integer flgStockUnloadAtCycleEnd;

    @SerializedName("flgStockUnloadAtDayEnd")
    @Expose
    private Integer flgStockUnloadAtDayEnd;

    @SerializedName("flgCollReqATCycleEnd")
    @Expose
    private Integer flgCollReqATCycleEnd;

    @SerializedName("flgCollReqATDayEnd")
    @Expose
    private Integer flgCollReqATDayEnd;

    @SerializedName("flgDayEndSummary")
    @Expose
    private Integer flgDayEndSummary;

    @SerializedName("flgStoreCheckInApplicable")
    @Expose
    private Integer flgStoreCheckInApplicable;

    public Integer getFlgVanStockInApp() {
        return flgVanStockInApp;
    }

    public void setFlgVanStockInApp(Integer flgVanStockInApp) {
        this.flgVanStockInApp = flgVanStockInApp;
    }

    public Integer getFlgVanStockEdit() {
        return flgVanStockEdit;
    }

    public void setFlgVanStockEdit(Integer flgVanStockEdit) {
        this.flgVanStockEdit = flgVanStockEdit;
    }

    public Integer getFlgVanStockCalculate() {
        return flgVanStockCalculate;
    }

    public void setFlgVanStockCalculate(Integer flgVanStockCalculate) {
        this.flgVanStockCalculate = flgVanStockCalculate;
    }

    public Integer getFlgVanStockControl() {
        return flgVanStockControl;
    }

    public void setFlgVanStockControl(Integer flgVanStockControl) {
        this.flgVanStockControl = flgVanStockControl;
    }

    public Integer getFlgStockRefillReq() {
        return flgStockRefillReq;
    }

    public void setFlgStockRefillReq(Integer flgStockRefillReq) {
        this.flgStockRefillReq = flgStockRefillReq;
    }

    public Integer getFlgDayEnd() {
        return flgDayEnd;
    }

    public void setFlgDayEnd(Integer flgDayEnd) {
        this.flgDayEnd = flgDayEnd;
    }

    public Integer getFlgStockUnloadAtCycleEnd() {
        return flgStockUnloadAtCycleEnd;
    }

    public void setFlgStockUnloadAtCycleEnd(Integer flgStockUnloadAtCycleEnd) {
        this.flgStockUnloadAtCycleEnd = flgStockUnloadAtCycleEnd;
    }

    public Integer getFlgStockUnloadAtDayEnd() {
        return flgStockUnloadAtDayEnd;
    }

    public void setFlgStockUnloadAtDayEnd(Integer flgStockUnloadAtDayEnd) {
        this.flgStockUnloadAtDayEnd = flgStockUnloadAtDayEnd;
    }

    public Integer getFlgCollReqATCycleEnd() {
        return flgCollReqATCycleEnd;
    }

    public void setFlgCollReqATCycleEnd(Integer flgCollReqATCycleEnd) {
        this.flgCollReqATCycleEnd = flgCollReqATCycleEnd;
    }

    public Integer getFlgCollReqATDayEnd() {
        return flgCollReqATDayEnd;
    }

    public void setFlgCollReqATDayEnd(Integer flgCollReqATDayEnd) {
        this.flgCollReqATDayEnd = flgCollReqATDayEnd;
    }

    public Integer getFlgDayEndSummary() {
        return flgDayEndSummary;
    }

    public void setFlgDayEndSummary(Integer flgDayEndSummary) {
        this.flgDayEndSummary = flgDayEndSummary;
    }

    public Integer getFlgStoreCheckInApplicable() {
        return flgStoreCheckInApplicable;
    }

    public void setFlgStoreCheckInApplicable(Integer flgStoreCheckInApplicable) {
        this.flgStoreCheckInApplicable = flgStoreCheckInApplicable;
    }

    public Integer getFlgStoreCheckInPhotoCompulsory() {
        return flgStoreCheckInPhotoCompulsory;
    }

    public void setFlgStoreCheckInPhotoCompulsory(Integer flgStoreCheckInPhotoCompulsory) {
        this.flgStoreCheckInPhotoCompulsory = flgStoreCheckInPhotoCompulsory;
    }

    @SerializedName("flgStoreCheckInPhotoCompulsory")
    @Expose
    private Integer flgStoreCheckInPhotoCompulsory;



    public Integer getFlgDistributorCheckIn() {
        return flgDistributorCheckIn;
    }

    public void setFlgDistributorCheckIn(Integer flgDistributorCheckIn) {
        this.flgDistributorCheckIn = flgDistributorCheckIn;
    }

    public Integer getFlgDBRStockInApp() {
        return flgDBRStockInApp;
    }

    public void setFlgDBRStockInApp(Integer flgDBRStockInApp) {
        this.flgDBRStockInApp = flgDBRStockInApp;
    }

    public Integer getFlgDBRStockEdit() {
        return flgDBRStockEdit;
    }

    public void setFlgDBRStockEdit(Integer flgDBRStockEdit) {
        this.flgDBRStockEdit = flgDBRStockEdit;
    }

    public Integer getFlgDBRStockCalculate() {
        return flgDBRStockCalculate;
    }

    public void setFlgDBRStockCalculate(Integer flgDBRStockCalculate) {
        this.flgDBRStockCalculate = flgDBRStockCalculate;
    }

    public Integer getFlgDBRStockControl() {
        return flgDBRStockControl;
    }

    public void setFlgDBRStockControl(Integer flgDBRStockControl) {
        this.flgDBRStockControl = flgDBRStockControl;
    }

    public Integer getFlgCollRequired() {
        return flgCollRequired;
    }

    public void setFlgCollRequired(Integer flgCollRequired) {
        this.flgCollRequired = flgCollRequired;
    }

    public Integer getFlgCollReqOrdr() {
        return flgCollReqOrdr;
    }

    public void setFlgCollReqOrdr(Integer flgCollReqOrdr) {
        this.flgCollReqOrdr = flgCollReqOrdr;
    }

    public Integer getFlgCollTab() {
        return flgCollTab;
    }

    public void setFlgCollTab(Integer flgCollTab) {
        this.flgCollTab = flgCollTab;
    }

    public Integer getFlgCollDefControl() {
        return flgCollDefControl;
    }

    public void setFlgCollDefControl(Integer flgCollDefControl) {
        this.flgCollDefControl = flgCollDefControl;
    }

    public Integer getFlgCashDiscount() {
        return flgCashDiscount;
    }

    public void setFlgCashDiscount(Integer flgCashDiscount) {
        this.flgCashDiscount = flgCashDiscount;
    }

    public Integer getFlgCollControlRule() {
        return flgCollControlRule;
    }

    public void setFlgCollControlRule(Integer flgCollControlRule) {
        this.flgCollControlRule = flgCollControlRule;
    }

    public Integer getFlgSchemeAvailable() {
        return flgSchemeAvailable;
    }

    public void setFlgSchemeAvailable(Integer flgSchemeAvailable) {
        this.flgSchemeAvailable = flgSchemeAvailable;
    }

    public Integer getFlgSchemeAllowEntry() {
        return flgSchemeAllowEntry;
    }

    public void setFlgSchemeAllowEntry(Integer flgSchemeAllowEntry) {
        this.flgSchemeAllowEntry = flgSchemeAllowEntry;
    }

    public Integer getFlgSchemeAllowEdit() {
        return flgSchemeAllowEdit;
    }

    public void setFlgSchemeAllowEdit(Integer flgSchemeAllowEdit) {
        this.flgSchemeAllowEdit = flgSchemeAllowEdit;
    }

    public Integer getFlgQuotationIsAvailable() {
        return flgQuotationIsAvailable;
    }

    public void setFlgQuotationIsAvailable(Integer flgQuotationIsAvailable) {
        this.flgQuotationIsAvailable = flgQuotationIsAvailable;
    }

    public Integer getFlgExecutionIsAvailable() {
        return flgExecutionIsAvailable;
    }

    public void setFlgExecutionIsAvailable(Integer flgExecutionIsAvailable) {
        this.flgExecutionIsAvailable = flgExecutionIsAvailable;
    }

    public Integer getFlgExecutionPhotoCompulsory() {
        return flgExecutionPhotoCompulsory;
    }

    public void setFlgExecutionPhotoCompulsory(Integer flgExecutionPhotoCompulsory) {
        this.flgExecutionPhotoCompulsory = flgExecutionPhotoCompulsory;
    }

    public Integer getFlgTargetShowatStart() {
        return flgTargetShowatStart;
    }

    public void setFlgTargetShowatStart(Integer flgTargetShowatStart) {
        this.flgTargetShowatStart = flgTargetShowatStart;
    }

    public Integer getFlgIncentiveShowatStart() {
        return flgIncentiveShowatStart;
    }

    public void setFlgIncentiveShowatStart(Integer flgIncentiveShowatStart) {
        this.flgIncentiveShowatStart = flgIncentiveShowatStart;
    }

    public Integer getFlgInvoicePrint() {
        return flgInvoicePrint;
    }

    public void setFlgInvoicePrint(Integer flgInvoicePrint) {
        this.flgInvoicePrint = flgInvoicePrint;
    }

    public Integer getFlgShowPOSM() {
        return flgShowPOSM;
    }

    public void setFlgShowPOSM(Integer flgShowPOSM) {
        this.flgShowPOSM = flgShowPOSM;
    }

    public Integer getFlgVisitStartOutstandingDetails() {
        return flgVisitStartOutstandingDetails;
    }

    public void setFlgVisitStartOutstandingDetails(Integer flgVisitStartOutstandingDetails) {
        this.flgVisitStartOutstandingDetails = flgVisitStartOutstandingDetails;
    }

    public Integer getFlgVisitStartSchemeDetails() {
        return flgVisitStartSchemeDetails;
    }

    public void setFlgVisitStartSchemeDetails(Integer flgVisitStartSchemeDetails) {
        this.flgVisitStartSchemeDetails = flgVisitStartSchemeDetails;
    }

    public Integer getFlgStoreDetailsEdit() {
        return flgStoreDetailsEdit;
    }

    public void setFlgStoreDetailsEdit(Integer flgStoreDetailsEdit) {
        this.flgStoreDetailsEdit = flgStoreDetailsEdit;
    }

    public Integer getFlgShowDeliveryAddressButtonOnOrder() {
        return flgShowDeliveryAddressButtonOnOrder;
    }

    public void setFlgShowDeliveryAddressButtonOnOrder(Integer flgShowDeliveryAddressButtonOnOrder) {
        this.flgShowDeliveryAddressButtonOnOrder = flgShowDeliveryAddressButtonOnOrder;
    }

    public Integer getFlgShowManagerOnStoreList() {
        return flgShowManagerOnStoreList;
    }

    public void setFlgShowManagerOnStoreList(Integer flgShowManagerOnStoreList) {
        this.flgShowManagerOnStoreList = flgShowManagerOnStoreList;
    }

    public Integer getFlgRptTargetVsAchived() {
        return flgRptTargetVsAchived;
    }

    public void setFlgRptTargetVsAchived(Integer flgRptTargetVsAchived) {
        this.flgRptTargetVsAchived = flgRptTargetVsAchived;
    }



    public Integer getSalesNodeID() {
        return salesNodeID;
    }

    public void setSalesNodeID(Integer salesNodeID) {
        this.salesNodeID = salesNodeID;
    }

    public Integer getSalesNodetype() {
        return salesNodetype;
    }

    public void setSalesNodetype(Integer salesNodetype) {
        this.salesNodetype = salesNodetype;
    }

    public Integer getWorkingTypeID() {
        return workingTypeID;
    }

    public void setWorkingTypeID(Integer workingTypeID) {
        this.workingTypeID = workingTypeID;
    }

}

