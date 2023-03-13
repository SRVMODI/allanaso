
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllMasterTablesModel {


    public List<TblStoreImageList> getTblStoreImageList() {
        return tblStoreImageList;
    }

    public void setTblStoreImageList(List<TblStoreImageList> tblStoreImageList) {
        this.tblStoreImageList = tblStoreImageList;
    }

    @SerializedName("tblStoreImageList")
    @Expose
    private List<TblStoreImageList> tblStoreImageList = null;


    @SerializedName("tblStoreListMaster")
    @Expose
    private List<TblStoreListMaster> tblStoreListMaster = null;

    @SerializedName("tblRptDistributionDate")
    @Expose
    private List<TblRptDistributionDate> tblRptDistributionDate = null;

    @SerializedName("tblRptManDays")
    @Expose
    private List<TblRptManDays> tblRptManDays = null;

    @SerializedName("tblRptPrimaryVol")
    @Expose
    private List<TblRptPrimaryVol> tblRptPrimaryVol = null;

    @SerializedName("tblRptSecVol")
    @Expose
    private List<TblRptSecVol> tblRptSecVol = null;

    @SerializedName("tblRptDistribution")
    @Expose
    private List<TblRptDistribution> tblRptDistribution = null;
    @SerializedName("tblBloodGroup")
    @Expose
    private List<TblBloodGroup> tblBloodGroup = null;

    @SerializedName("tblEducationQuali")
    @Expose
    private List<TblEducationQuali> tblEducationQuali = null;


    public List<TblStoreListMaster> getTblStoreListMaster() {
        return tblStoreListMaster;
    }

    public void setTblStoreListMaster(List<TblStoreListMaster> tblStoreListMaster) {
        this.tblStoreListMaster = tblStoreListMaster;
    }

    public List<TblRptDistributionDate> getTblRptDistributionDate() {
        return tblRptDistributionDate;
    }

    public void setTblRptDistributionDate(List<TblRptDistributionDate> tblRptDistributionDate) {
        this.tblRptDistributionDate = tblRptDistributionDate;
    }

    public List<TblRptManDays> getTblRptManDays() {
        return tblRptManDays;
    }

    public void setTblRptManDays(List<TblRptManDays> tblRptManDays) {
        this.tblRptManDays = tblRptManDays;
    }

    public List<TblRptPrimaryVol> getTblRptPrimaryVol() {
        return tblRptPrimaryVol;
    }

    public void setTblRptPrimaryVol(List<TblRptPrimaryVol> tblRptPrimaryVol) {
        this.tblRptPrimaryVol = tblRptPrimaryVol;
    }

    public List<TblRptSecVol> getTblRptSecVol() {
        return tblRptSecVol;
    }

    public void setTblRptSecVol(List<TblRptSecVol> tblRptSecVol) {
        this.tblRptSecVol = tblRptSecVol;
    }

    public List<TblRptDistribution> getTblRptDistribution() {
        return tblRptDistribution;
    }

    public void setTblRptDistribution(List<TblRptDistribution> tblRptDistribution) {
        this.tblRptDistribution = tblRptDistribution;
    }
    public List<TblQuestionsSurvey> getTblQuestionsSurvey() {
        return tblQuestionsSurvey;
    }

    public void setTblQuestionsSurvey(List<TblQuestionsSurvey> tblQuestionsSurvey) {
        this.tblQuestionsSurvey = tblQuestionsSurvey;

    }

    public List<TblOptionSurvey> getTblOptionSurvey() {
        return tblOptionSurvey;
    }

    public void setTblOptionSurvey(List<TblOptionSurvey> tblOptionSurvey) {
        this.tblOptionSurvey = tblOptionSurvey;

    }

    @SerializedName("tblQuestionsSurvey")
    @Expose
    private List<TblQuestionsSurvey> tblQuestionsSurvey = null;

    @SerializedName("tblOptionSurvey")
    @Expose
    private List<TblOptionSurvey> tblOptionSurvey = null;


    @SerializedName("tblStoreCloseReasonMaster")
    @Expose
    private List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster = null;

    public List<TblSalesPersonTodaysTarget> getTblSalesPersonTodaysTarget() {
        return tblSalesPersonTodaysTarget;
    }

    public void setTblSalesPersonTodaysTarget(List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget) {
        this.tblSalesPersonTodaysTarget = tblSalesPersonTodaysTarget;
    }

    @SerializedName("tblSalesPersonTodaysTarget")
    @Expose
    private List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget = null;


    public List<TblIsDBRStockSubmitted> getTblIsDBRStockSubmitted() {
        return tblIsDBRStockSubmitted;
    }


   /* public List<TblIncentiveMainMaster> getTblIncentiveMainMaster() {
        return tblIncentiveMainMaster;
    }

    public void setTblIncentiveMainMaster(List<TblIncentiveMainMaster> tblIncentiveMainMaster) {
        this.tblIncentiveMainMaster = tblIncentiveMainMaster;
    }

    @SerializedName("tblIncentiveMainMaster")
    @Expose
    private List<TblIncentiveMainMaster> tblIncentiveMainMaster = null;

    public List<TblIncentiveSecondaryMaster> getTblIncentiveSecondaryMaster() {
        return tblIncentiveSecondaryMaster;
    }

    public void setTblIncentiveSecondaryMaster(List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster) {
        this.tblIncentiveSecondaryMaster = tblIncentiveSecondaryMaster;

    }

    @SerializedName("tblIncentiveSecondaryMaster")
    @Expose
    private List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster = null;


    public Object getTblIncentiveDetailsData() {
        return tblIncentiveDetailsData;
    }

    public void setTblIncentiveDetailsData(Object tblIncentiveDetailsData) {
        this.tblIncentiveDetailsData = tblIncentiveDetailsData;

    }

    @SerializedName("tblIncentiveDetailsData")
    @Expose
    private Object  tblIncentiveDetailsData = null;

    public List<TblIncentiveDetailsColumnsDesc> getTblIncentiveDetailsColumnsDesc() {
        return tblIncentiveDetailsColumnsDesc;
    }

    public void setTblIncentiveDetailsColumnsDesc(List<TblIncentiveDetailsColumnsDesc> tblIncentiveDetailsColumnsDesc) {
        this.tblIncentiveDetailsColumnsDesc = tblIncentiveDetailsColumnsDesc;
    }

    @SerializedName("tblIncentiveDetailsColumnsDesc")
    @Expose
    private List<TblIncentiveDetailsColumnsDesc>  tblIncentiveDetailsColumnsDesc = null;


    public List<TblTotalEarning> getTblTotalEarning() {
        return tblTotalEarning;
    }

    public void setTblTotalEarning(List<TblTotalEarning> tblTotalEarning) {
        this.tblTotalEarning = tblTotalEarning;
    }

    @SerializedName("tblTotalEarning")
    @Expose
    private List<TblTotalEarning>  tblTotalEarning = null;

    public Object getTblIncentivePastDetailsData() {
        return tblIncentivePastDetailsData;
    }

    public void setTblIncentivePastDetailsData(Object tblIncentivePastDetailsData) {
        this.tblIncentivePastDetailsData = tblIncentivePastDetailsData;
    }

    @SerializedName("tblIncentivePastDetailsData")
    @Expose
    private Object  tblIncentivePastDetailsData = null;


    public List<TblIncentivePastDetailsColumnsDesc> getTblIncentivePastDetailsColumnsDesc() {
        return tblIncentivePastDetailsColumnsDesc;
    }

    public void setTblIncentivePastDetailsColumnsDesc(List<TblIncentivePastDetailsColumnsDesc> tblIncentivePastDetailsColumnsDesc) {
        this.tblIncentivePastDetailsColumnsDesc = tblIncentivePastDetailsColumnsDesc;
    }

    @SerializedName("tblIncentivePastDetailsColumnsDesc")
    @Expose
    private List<TblIncentivePastDetailsColumnsDesc>  tblIncentivePastDetailsColumnsDesc = null;

    public List<TblIncentiveMsgToDisplay> getTblIncentiveMsgToDisplay() {
        return tblIncentiveMsgToDisplay;
    }

    public void setTblIncentiveMsgToDisplay(List<TblIncentiveMsgToDisplay> tblIncentiveMsgToDisplay) {
        this.tblIncentiveMsgToDisplay = tblIncentiveMsgToDisplay;
    }

    @SerializedName("tblIncentiveMsgToDisplay")
    @Expose
    private List<TblIncentiveMsgToDisplay>  tblIncentiveMsgToDisplay = null;

    public List<TblIncentiveBankDetails> getTblIncentiveBankDetails() {
        return tblIncentiveBankDetails;
    }

    public void setTblIncentiveBankDetails(List<TblIncentiveBankDetails> tblIncentiveBankDetails) {
        this.tblIncentiveBankDetails = tblIncentiveBankDetails;
    }

    @SerializedName("tblIncentiveBankDetails")
    @Expose
    private List<TblIncentiveBankDetails>  tblIncentiveBankDetails = null;
*/
   /* tblIncentiveMainMaster
            tblIncentiveSecondaryMaster
    tblIncentiveDetailsData
            tblIncentiveDetailsColumnsDesc
    tblTotalEarning
            tblIncentivePastDetailsData
    tblIncentivePastDetailsColumnsDesc
            tblIncentiveMsgToDisplay
    tblIncentiveBankDetails*/

    public void setTblIsDBRStockSubmitted(List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted) {
        this.tblIsDBRStockSubmitted = tblIsDBRStockSubmitted;
    }

    @SerializedName("tblIsDBRStockSubmitted")
    @Expose
    private List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted = null;

    @SerializedName("tblStockUploadedStatus")
    @Expose
    private List<TblStockUploadedStatus> tblStockUploadedStatus = null;

    @SerializedName("tblVanProductStock")
    @Expose
    private List<TblVanProductStock> tblVanProductStock = null;

    @SerializedName("tblCycleID")
    @Expose
    private List<TblCycleID> tblCycleID = null;

    @SerializedName("tblVanStockOutFlg")
    @Expose
    private List<TblVanStockOutFlg> tblVanStockOutFlg = null;

    @SerializedName("tblVanIDOrderIDLeft")
    @Expose
    private List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = null;

    @SerializedName("tblInvoiceCaption")
    @Expose
    private List<TblInvoiceCaption> tblInvoiceCaption = null;

    @SerializedName("tblGetReturnsReasonForPDAMstr")
    @Expose
    private List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr = null;

    @SerializedName("tblIsSchemeApplicable")
    @Expose
    private List<TblIsSchemeApplicable> tblIsSchemeApplicable = null;


    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblVanProductStock> getTblVanProductStock() {
        return tblVanProductStock;
    }

    public void setTblVanProductStock(List<TblVanProductStock> tblVanProductStock) {
        this.tblVanProductStock = tblVanProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblVanStockOutFlg> getTblVanStockOutFlg() {
        return tblVanStockOutFlg;
    }

    public void setTblVanStockOutFlg(List<TblVanStockOutFlg> tblVanStockOutFlg) {
        this.tblVanStockOutFlg = tblVanStockOutFlg;
    }

    public List<TblVanIDOrderIDLeft> getTblVanIDOrderIDLeft() {
        return tblVanIDOrderIDLeft;
    }

    public void setTblVanIDOrderIDLeft(List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft) {
        this.tblVanIDOrderIDLeft = tblVanIDOrderIDLeft;
    }

    public List<TblInvoiceCaption> getTblInvoiceCaption() {
        return tblInvoiceCaption;
    }

    public void setTblInvoiceCaption(List<TblInvoiceCaption> tblInvoiceCaption) {
        this.tblInvoiceCaption = tblInvoiceCaption;
    }

    public List<TblGetReturnsReasonForPDAMstr> getTblGetReturnsReasonForPDAMstr() {
        return tblGetReturnsReasonForPDAMstr;
    }

    public void setTblGetReturnsReasonForPDAMstr(List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr) {
        this.tblGetReturnsReasonForPDAMstr = tblGetReturnsReasonForPDAMstr;
    }

    public List<TblIsSchemeApplicable> getTblIsSchemeApplicable() {
        return tblIsSchemeApplicable;
    }

    public void setTblIsSchemeApplicable(List<TblIsSchemeApplicable> tblIsSchemeApplicable) {
        this.tblIsSchemeApplicable = tblIsSchemeApplicable;
    }


    public List<TblStoreListWithPaymentAddress> getTblStoreListWithPaymentAddress() {
        return tblStoreListWithPaymentAddress;
    }

    public void setTblStoreListWithPaymentAddress(List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress) {
        this.tblStoreListWithPaymentAddress = tblStoreListWithPaymentAddress;
    }

    public List<TblStoreSomeProdQuotePriceMstr> getTblStoreSomeProdQuotePriceMstr() {
        return tblStoreSomeProdQuotePriceMstr;
    }

    public void setTblStoreSomeProdQuotePriceMstr(List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr) {
        this.tblStoreSomeProdQuotePriceMstr = tblStoreSomeProdQuotePriceMstr;
    }

    public List<TblStoreLastDeliveryNoteNumber> getTblStoreLastDeliveryNoteNumber() {
        return tblStoreLastDeliveryNoteNumber;
    }

    public void setTblStoreLastDeliveryNoteNumber(List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber) {
        this.tblStoreLastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumber;
    }

    @SerializedName("tblStoreListWithPaymentAddress")
    @Expose
    private List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress = null;

    @SerializedName("tblStoreSomeProdQuotePriceMstr")
    @Expose
    private List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr = null;

    @SerializedName("tblStoreLastDeliveryNoteNumber")
    @Expose
    private List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber = null;











    public List<TblDayStartAttendanceOption> getTblDayStartAttendanceOptions() {
        return tblDayStartAttendanceOptions;
    }

    public void setTblDayStartAttendanceOptions(List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions) {
        this.tblDayStartAttendanceOptions = tblDayStartAttendanceOptions;
    }

    @SerializedName("tblDayStartAttendanceOptions")
    @Expose
    private List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions = null;

    @SerializedName("tblProductListLastVisitStockOrOrderMstr")
    @Expose
    private List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr = null;

    @SerializedName("tblPDAGetLODQty")
    @Expose
    private List<TblPDAGetLODQty> tblPDAGetLODQty = null;

    @SerializedName("tblPDAGetLastVisitDate")
    @Expose
    private List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate = null;

    @SerializedName("tblPDAGetLastOrderDate")
    @Expose
    private List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate = null;

    @SerializedName("tblPDAGetLastVisitDetails")
    @Expose
    private List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails = null;

    public List<TblProductListLastVisitStockOrOrderMstr> getTblProductListLastVisitStockOrOrderMstr() {
        return tblProductListLastVisitStockOrOrderMstr;
    }

    public void setTblProductListLastVisitStockOrOrderMstr(List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr) {
        this.tblProductListLastVisitStockOrOrderMstr = tblProductListLastVisitStockOrOrderMstr;
    }

    public List<TblPDAGetLODQty> getTblPDAGetLODQty() {
        return tblPDAGetLODQty;
    }

    public void setTblPDAGetLODQty(List<TblPDAGetLODQty> tblPDAGetLODQty) {
        this.tblPDAGetLODQty = tblPDAGetLODQty;
    }

    public List<TblPDAGetLastVisitDate> getTblPDAGetLastVisitDate() {
        return tblPDAGetLastVisitDate;
    }

    public void setTblPDAGetLastVisitDate(List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate) {
        this.tblPDAGetLastVisitDate = tblPDAGetLastVisitDate;
    }

    public List<TblPDAGetLastOrderDate> getTblPDAGetLastOrderDate() {
        return tblPDAGetLastOrderDate;
    }

    public void setTblPDAGetLastOrderDate(List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate) {
        this.tblPDAGetLastOrderDate = tblPDAGetLastOrderDate;
    }

    public List<TblPDAGetLastVisitDetails> getTblPDAGetLastVisitDetails() {
        return tblPDAGetLastVisitDetails;
    }

    public void setTblPDAGetLastVisitDetails(List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails) {
        this.tblPDAGetLastVisitDetails = tblPDAGetLastVisitDetails;
    }

    public List<TblPDAGetLastOrderDetails> getTblPDAGetLastOrderDetails() {
        return tblPDAGetLastOrderDetails;
    }

    public void setTblPDAGetLastOrderDetails(List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails) {
        this.tblPDAGetLastOrderDetails = tblPDAGetLastOrderDetails;
    }

    public List<TblPDAGetLastOrderDetailsTotalValues> getTblPDAGetLastOrderDetailsTotalValues() {
        return tblPDAGetLastOrderDetailsTotalValues;
    }

    public void setTblPDAGetLastOrderDetailsTotalValues(List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues) {
        this.tblPDAGetLastOrderDetailsTotalValues = tblPDAGetLastOrderDetailsTotalValues;
    }

    public List<TblPDAGetExecutionSummary> getTblPDAGetExecutionSummary() {
        return tblPDAGetExecutionSummary;
    }

    public void setTblPDAGetExecutionSummary(List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary) {
        this.tblPDAGetExecutionSummary = tblPDAGetExecutionSummary;
    }

    public List<TblLastOutstanding> getTblLastOutstanding() {
        return tblLastOutstanding;
    }

    public void setTblLastOutstanding(List<TblLastOutstanding> tblLastOutstanding) {
        this.tblLastOutstanding = tblLastOutstanding;
    }

    public List<TblInvoiceLastVisitDetails> getTblInvoiceLastVisitDetails() {
        return tblInvoiceLastVisitDetails;
    }

    public void setTblInvoiceLastVisitDetails(List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails) {
        this.tblInvoiceLastVisitDetails = tblInvoiceLastVisitDetails;
    }

    @SerializedName("tblPDAGetLastOrderDetails")
    @Expose
    private List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails = null;

    @SerializedName("tblPDAGetLastOrderDetailsTotalValues")
    @Expose
    private List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues = null;

    @SerializedName("tblPDAGetExecutionSummary")
    @Expose
    private List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary = null;

    @SerializedName("tblLastOutstanding")
    @Expose
    private List<TblLastOutstanding> tblLastOutstanding = null;

    @SerializedName("tblInvoiceLastVisitDetails")
    @Expose
    private List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails = null;



    @SerializedName("tblUOMMaster")
    @Expose
    private List<TblUOMMaster> tblUOMMaster = null;

    public List<TblUOMMaster> getTblUOMMaster() {
        return tblUOMMaster;
    }

    public void setTblUOMMaster(List<TblUOMMaster> tblUOMMaster) {
        this.tblUOMMaster = tblUOMMaster;
    }

    public List<TblUOMMapping> getTblUOMMapping() {
        return tblUOMMapping;
    }

    public void setTblUOMMapping(List<TblUOMMapping> tblUOMMapping) {
        this.tblUOMMapping = tblUOMMapping;
    }

    public List<TblManagerMstr> getTblManagerMstr() {
        return tblManagerMstr;
    }

    public void setTblManagerMstr(List<TblManagerMstr> tblManagerMstr) {
        this.tblManagerMstr = tblManagerMstr;
    }

    public List<TblCategoryMaster> getTblCategoryMaster() {
        return tblCategoryMaster;
    }

    public void setTblCategoryMaster(List<TblCategoryMaster> tblCategoryMaster) {
        this.tblCategoryMaster = tblCategoryMaster;
    }

    public List<TblBankMaster> getTblBankMaster() {
        return tblBankMaster;
    }

    public void setTblBankMaster(List<TblBankMaster> tblBankMaster) {
        this.tblBankMaster = tblBankMaster;
    }

    public List<TblInstrumentMaster> getTblInstrumentMaster() {
        return tblInstrumentMaster;
    }

    public void setTblInstrumentMaster(List<TblInstrumentMaster> tblInstrumentMaster) {
        this.tblInstrumentMaster = tblInstrumentMaster;
    }

    @SerializedName("tblUOMMapping")
    @Expose
    private List<TblUOMMapping> tblUOMMapping = null;

    @SerializedName("tblManagerMstr")
    @Expose
    private List<TblManagerMstr> tblManagerMstr = null;

    @SerializedName("tblCategoryMaster")
    @Expose
    private List<TblCategoryMaster> tblCategoryMaster = null;

    @SerializedName("tblBankMaster")
    @Expose
    private List<TblBankMaster> tblBankMaster = null;

    @SerializedName("tblInstrumentMaster")
    @Expose
    private List<TblInstrumentMaster> tblInstrumentMaster = null;

    public List<TblPreAddedStoresDataDetails> getTblPreAddedStoresDataDetails() {
        return tblPreAddedStoresDataDetails;
    }

    public void setTblPreAddedStoresDataDetails(List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails) {
        this.tblPreAddedStoresDataDetails = tblPreAddedStoresDataDetails;
    }

    public List<TblStateCityMaster> getTblStateCityMaster() {
        return tblStateCityMaster;
    }

    public void setTblStateCityMaster(List<TblStateCityMaster> tblStateCityMaster) {
        this.tblStateCityMaster = tblStateCityMaster;
    }

    public List<TblProductListMaster> getTblProductListMaster() {
        return tblProductListMaster;
    }

    public void setTblProductListMaster(List<TblProductListMaster> tblProductListMaster) {
        this.tblProductListMaster = tblProductListMaster;
    }

    public List<TblProductSegementMap> getTblProductSegementMap() {
        return tblProductSegementMap;
    }

    public void setTblProductSegementMap(List<TblProductSegementMap> tblProductSegementMap) {
        this.tblProductSegementMap = tblProductSegementMap;
    }

    public List<TblPriceApplyType> getTblPriceApplyType() {
        return tblPriceApplyType;
    }

    public void setTblPriceApplyType(List<TblPriceApplyType> tblPriceApplyType) {
        this.tblPriceApplyType = tblPriceApplyType;
    }

    @SerializedName("tblPreAddedStoresDataDetails")
    @Expose
    private List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails = null;

    @SerializedName("tblStateCityMaster")
    @Expose
    private List<TblStateCityMaster> tblStateCityMaster = null;

    @SerializedName("tblProductListMaster")
    @Expose
    private List<TblProductListMaster> tblProductListMaster = null;

    @SerializedName("tblProductSegementMap")
    @Expose
    private List<TblProductSegementMap> tblProductSegementMap = null;

    @SerializedName("tblPriceApplyType")
    @Expose
    private List<TblPriceApplyType> tblPriceApplyType = null;

    public List<TblPreAddedStores> getTblPreAddedStores() {
        return tblPreAddedStores;
    }

    public void setTblPreAddedStores(List<TblPreAddedStores> tblPreAddedStores) {
        this.tblPreAddedStores = tblPreAddedStores;
    }


    public List<TblStoreCountDetails> getTblStoreCountDetails() {
        return tblStoreCountDetails;
    }

    public void setTblStoreCountDetails(List<TblStoreCountDetails> tblStoreCountDetails) {
        this.tblStoreCountDetails = tblStoreCountDetails;
    }

    @SerializedName("tblStoreCountDetails")
    @Expose
    private List<TblStoreCountDetails> tblStoreCountDetails = null;

    @SerializedName("tblPreAddedStores")
    @Expose
    private List<TblPreAddedStores> tblPreAddedStores = null;

    public List<TblPDANotificationMaster> getTblPDANotificationMaster() {
        return tblPDANotificationMaster;
    }

    public void setTblPDANotificationMaster(List<TblPDANotificationMaster> tblPDANotificationMaster) {
        this.tblPDANotificationMaster = tblPDANotificationMaster;
    }

    @SerializedName("tblPDANotificationMaster")
    @Expose
    private List<TblPDANotificationMaster> tblPDANotificationMaster = null;

    public List<TblPDAQuestOptionValuesDependentMstr> getTblPDAQuestOptionValuesDependentMstr() {
        return tblPDAQuestOptionValuesDependentMstr;
    }

    public void setTblPDAQuestOptionValuesDependentMstr(List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr) {
        this.tblPDAQuestOptionValuesDependentMstr = tblPDAQuestOptionValuesDependentMstr;
    }

    @SerializedName("tblPDAQuestOptionValuesDependentMstr")
    @Expose
    private List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr = null;

    @SerializedName("tblWarehouseList")
    @Expose
    private List<TblSupplierMstrList> tblSupplierMstrList = null;

    @SerializedName("tblRouteListMaster")
    @Expose
    private List<TblRouteListMaster> tblRouteListMaster = null;

    @SerializedName("tblAppMasterFlags")
    @Expose
    private List<TblAppMasterFlags> tblAppMasterFlags = null;



    @SerializedName("tblGetPDAQuestMstr")
    @Expose
    private List<TblGetPDAQuestMstr> tblGetPDAQuestMstr = null;

    @SerializedName("tblQuestIDForOutChannel")
    @Expose
    private List<TblQuestIDForOutChannel> tblQuestIDForOutChannel = null;

    @SerializedName("tblQuestIDForName")
    @Expose
    private List<TblQuestIDForName> tblQuestIDForName = null;

    public List<TblSupplierMstrList> getTblSupplierMstrList() {
        return tblSupplierMstrList;
    }

    public void setTblSupplierMstrList(List<TblSupplierMstrList> tblSupplierMstrList) {
        this.tblSupplierMstrList = tblSupplierMstrList;
    }

    public List<TblRouteListMaster> getTblRouteListMaster() {
        return tblRouteListMaster;
    }

    public void setTblRouteListMaster(List<TblRouteListMaster> tblRouteListMaster) {
        this.tblRouteListMaster = tblRouteListMaster;
    }

    public List<TblBloodGroup> getTblBloodGroup() {
        return tblBloodGroup;
    }

    public void setTblBloodGroup(List<TblBloodGroup> tblBloodGroup) {
        this.tblBloodGroup = tblBloodGroup;
    }

    public List<TblEducationQuali> getTblEducationQuali() {
        return tblEducationQuali;
    }

    public void setTblEducationQuali(List<TblEducationQuali> tblEducationQuali) {
        this.tblEducationQuali = tblEducationQuali;
    }

    public List<TblAppMasterFlags> getTblAppMasterFlags() {
        return tblAppMasterFlags;
    }

    public void setTblAppMasterFlags(List<TblAppMasterFlags> tblAppMasterFlags) {
        this.tblAppMasterFlags = tblAppMasterFlags;
    }

    public List<TblGetPDAQuestMstr> getTblGetPDAQuestMstr() {
        return tblGetPDAQuestMstr;
    }

    public void setTblGetPDAQuestMstr(List<TblGetPDAQuestMstr> tblGetPDAQuestMstr) {
        this.tblGetPDAQuestMstr = tblGetPDAQuestMstr;
    }

    public List<TblQuestIDForOutChannel> getTblQuestIDForOutChannel() {
        return tblQuestIDForOutChannel;
    }

    public void setTblQuestIDForOutChannel(List<TblQuestIDForOutChannel> tblQuestIDForOutChannel) {
        this.tblQuestIDForOutChannel = tblQuestIDForOutChannel;
    }

    public List<TblQuestIDForName> getTblQuestIDForName() {
        return tblQuestIDForName;
    }

    public void setTblQuestIDForName(List<TblQuestIDForName> tblQuestIDForName) {
        this.tblQuestIDForName = tblQuestIDForName;
    }

    public List<TblGetPDAQuestGrpMapping> getTblGetPDAQuestGrpMapping() {
        return tblGetPDAQuestGrpMapping;
    }

    public void setTblGetPDAQuestGrpMapping(List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping) {
        this.tblGetPDAQuestGrpMapping = tblGetPDAQuestGrpMapping;
    }


    public List<TblStoreCloseReasonMaster> getTblStoreCloseReasonMaster() {
        return tblStoreCloseReasonMaster;
    }

    public void setTblStoreCloseReasonMaster(List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster) {
        this.tblStoreCloseReasonMaster = tblStoreCloseReasonMaster;

    }


    public List<TblGetPDAQuestionDependentMstr> getTblGetPDAQuestionDependentMstr() {
        return tblGetPDAQuestionDependentMstr;
    }

    public void setTblGetPDAQuestionDependentMstr(List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr) {
        this.tblGetPDAQuestionDependentMstr = tblGetPDAQuestionDependentMstr;
    }

    public List<TblPDAQuestOptionDependentMstr> getTblPDAQuestOptionDependentMstr() {
        return tblPDAQuestOptionDependentMstr;
    }

    public void setTblPDAQuestOptionDependentMstr(List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr) {
        this.tblPDAQuestOptionDependentMstr = tblPDAQuestOptionDependentMstr;
    }

    @SerializedName("tblGetPDAQuestGrpMapping")
    @Expose
    private List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping = null;

    @SerializedName("tblGetPDAQuestionDependentMstr")
    @Expose
    private List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr = null;

    @SerializedName("tblPDAQuestOptionDependentMstr")
    @Expose
    private List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr = null;


    public List<TblUserName> getTblUserName() {
        return tblUserName;
    }

    public void setTblUserName(List<TblUserName> tblUserName) {
        this.tblUserName = tblUserName;
    }

    @SerializedName("tblUserName")
    @Expose
    private List<TblUserName> tblUserName = null;

    public List<TblGetPDAQuestOptionMstr> getTblGetPDAQuestOptionMstr() {
        return tblGetPDAQuestOptionMstr;
    }

    public void setTblGetPDAQuestOptionMstr(List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr) {
        this.tblGetPDAQuestOptionMstr = tblGetPDAQuestOptionMstr;
    }

    @SerializedName("tblGetPDAQuestOptionMstr")
    @Expose
    private List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr = null;


    public List<TblPendingInvoices> getTblPendingInvoices() {
        return tblPendingInvoices;
    }

    public void setTblPendingInvoices(List<TblPendingInvoices> tblPendingInvoices) {
        this.tblPendingInvoices = tblPendingInvoices;

    }

    public List<TblInvoiceExecutionProductList> getTblInvoiceExecutionProductList() {
        return tblInvoiceExecutionProductList;
    }

    public void setTblInvoiceExecutionProductList(List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList) {
        this.tblInvoiceExecutionProductList = tblInvoiceExecutionProductList;

    }

    public List<TblProductWiseInvoice> getTblProductWiseInvoice() {
        return tblProductWiseInvoice;
    }

    public void setTblProductWiseInvoice(List<TblProductWiseInvoice> tblProductWiseInvoice) {
        this.tblProductWiseInvoice = tblProductWiseInvoice;

    }

    @SerializedName("tblPendingInvoices")
    @Expose
    private List<TblPendingInvoices> tblPendingInvoices = null;


    @SerializedName("tblRoutelist")
    @Expose
    private List<TblCoverageDsr> tblCoverageDsr = null;

    public List<TblCoverageMaster> getTblCoverage() {
        return tblCoverage;
    }

    public void setTblCoverage(List<TblCoverageMaster> tblCoverage) {
        this.tblCoverage = tblCoverage;
    }

    @SerializedName("tblCoverage")
    @Expose
    private List<TblCoverageMaster> tblCoverage = null;


    public List<TblCoverageDsr> getTblCoverageDsr() {
        return tblCoverageDsr;
    }

    public void setTblCoverageDsr(List<TblCoverageDsr> tblCoverageDsr) {
        this.tblCoverageDsr = tblCoverageDsr;
    }



    @SerializedName("tblInvoiceExecutionProductList")
    @Expose
    private List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = null;


    @SerializedName("tblProductWiseInvoice")
    @Expose
    private List<TblProductWiseInvoice> tblProductWiseInvoice = null;



    @SerializedName("tblDistributorProductStock")
    @Expose
    private List<TblDistributorProductStock> tblDistributorProductStock = null;
    @SerializedName("tblDistributorIDOrderIDLeft")
    @Expose
    private List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft = null;

    public List<TblDistributorProductStock> getTblDistributorProductStock() {
        return tblDistributorProductStock;
    }

    public void setTblDistributorProductStock(List<TblDistributorProductStock> tblDistributorProductStock) {
        this.tblDistributorProductStock = tblDistributorProductStock;
    }

    public List<TblDistributorIDOrderIDLeft> getTblDistributorIDOrderIDLeft() {
        return tblDistributorIDOrderIDLeft;
    }

    public void setTblDistributorIDOrderIDLeft(List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft) {
        this.tblDistributorIDOrderIDLeft = tblDistributorIDOrderIDLeft;
    }


    public List<TblReasonOrderCncl> getTblReasonOrderCncl() {
        return tblReasonOrderCncl;
    }

    public void setTblReasonOrderCncl(List<TblReasonOrderCncl> tblReasonOrderCncl) {
        this.tblReasonOrderCncl = tblReasonOrderCncl;
    }

    @SerializedName("tblReasonOrderCncl")
    @Expose
    private List<TblReasonOrderCncl> tblReasonOrderCncl = null;

  /*


    public List<TblDayStartAttendanceOption> getTblDayStartAttendanceOption() {
        return tblDayStartAttendanceOptions;
    }
    public void setTblDayStartAttendanceOption(List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions) {
        this.tblDayStartAttendanceOptions = tblDayStartAttendanceOptions;
    }



    public List<TblSupplierMstrList> getTblSupplierMstrList() {
        return tblSupplierMstrList;
    }

    public void setTblSupplierMstrList(List<TblSupplierMstrList> tblSupplierMstrList) {
        this.tblSupplierMstrList = tblSupplierMstrList;
    }

    public List<TblRouteListMaster> getTblRouteListMaster() {
        return tblRouteListMaster;
    }

    public void setTblRouteListMaster(List<TblRouteListMaster> tblRouteListMaster) {
        this.tblRouteListMaster = tblRouteListMaster;
    }

    public List<TblBloodGroup> getTblBloodGroup() {
        return tblBloodGroup;
    }

    public void setTblBloodGroup(List<TblBloodGroup> tblBloodGroup) {
        this.tblBloodGroup = tblBloodGroup;
    }

    public List<TblEducationQuali> getTblEducationQuali() {
        return tblEducationQuali;
    }

    public void setTblEducationQuali(List<TblEducationQuali> tblEducationQuali) {
        this.tblEducationQuali = tblEducationQuali;
    }

    public List<TblAppMasterFlags> getTblAppMasterFlags() {
        return tblAppMasterFlags;
    }

    public void setTblAppMasterFlags(List<TblAppMasterFlags> tblAppMasterFlags) {
        this.tblAppMasterFlags = tblAppMasterFlags;
    }

   *//* public List<TblPDAIMEIGCMRegistration> getTblPDAIMEIGCMRegistration() {
        return tblPDAIMEIGCMRegistration;
    }

    public void setTblPDAIMEIGCMRegistration(List<TblPDAIMEIGCMRegistration> tblPDAIMEIGCMRegistration) {
        this.tblPDAIMEIGCMRegistration = tblPDAIMEIGCMRegistration;
    }*//*

    public List<TblGetPDAQuestMstr> getTblGetPDAQuestMstr() {
        return tblGetPDAQuestMstr;
    }

    public void setTblGetPDAQuestMstr(List<TblGetPDAQuestMstr> tblGetPDAQuestMstr) {
        this.tblGetPDAQuestMstr = tblGetPDAQuestMstr;
    }

    public List<TblQuestIDForOutChannel> getTblQuestIDForOutChannel() {
        return tblQuestIDForOutChannel;
    }

    public void setTblQuestIDForOutChannel(List<TblQuestIDForOutChannel> tblQuestIDForOutChannel) {
        this.tblQuestIDForOutChannel = tblQuestIDForOutChannel;
    }

    public List<TblQuestIDForName> getTblQuestIDForName() {
        return tblQuestIDForName;
    }

    public void setTblQuestIDForName(List<TblQuestIDForName> tblQuestIDForName) {
        this.tblQuestIDForName = tblQuestIDForName;
    }

    public List<TblGetPDAQuestGrpMapping> getTblGetPDAQuestGrpMapping() {
        return tblGetPDAQuestGrpMapping;
    }

    public void setTblGetPDAQuestGrpMapping(List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping) {
        this.tblGetPDAQuestGrpMapping = tblGetPDAQuestGrpMapping;
    }

    public List<TblGetPDAQuestionDependentMstr> getTblGetPDAQuestionDependentMstr() {
        return tblGetPDAQuestionDependentMstr;
    }

    public void setTblGetPDAQuestionDependentMstr(List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr) {
        this.tblGetPDAQuestionDependentMstr = tblGetPDAQuestionDependentMstr;
    }

    public List<TblPDAQuestOptionDependentMstr> getTblPDAQuestOptionDependentMstr() {
        return tblPDAQuestOptionDependentMstr;
    }

    public void setTblPDAQuestOptionDependentMstr(List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr) {
        this.tblPDAQuestOptionDependentMstr = tblPDAQuestOptionDependentMstr;
    }

    public List<TblPDAQuestOptionValuesDependentMstr> getTblPDAQuestOptionValuesDependentMstr() {
        return tblPDAQuestOptionValuesDependentMstr;
    }

    public void setTblPDAQuestOptionValuesDependentMstr(List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr) {
        this.tblPDAQuestOptionValuesDependentMstr = tblPDAQuestOptionValuesDependentMstr;
    }

    public List<TblPDANotificationMaster> getTblPDANotificationMaster() {
        return tblPDANotificationMaster;
    }

    public void setTblPDANotificationMaster(List<TblPDANotificationMaster> tblPDANotificationMaster) {
        this.tblPDANotificationMaster = tblPDANotificationMaster;
    }

    public List<TblUserName> getTblUserName() {
        return tblUserName;
    }

    public void setTblUserName(List<TblUserName> tblUserName) {
        this.tblUserName = tblUserName;
    }

    public List<TblStoreCountDetails> getTblStoreCountDetails() {
        return tblStoreCountDetails;
    }

    public void setTblStoreCountDetails(List<TblStoreCountDetails> tblStoreCountDetails) {
        this.tblStoreCountDetails = tblStoreCountDetails;
    }

    public List<TblPreAddedStores> getTblPreAddedStores() {
        return tblPreAddedStores;
    }

    public void setTblPreAddedStores(List<TblPreAddedStores> tblPreAddedStores) {
        this.tblPreAddedStores = tblPreAddedStores;
    }

    public List<TblPreAddedStoresDataDetails> getTblPreAddedStoresDataDetails() {
        return tblPreAddedStoresDataDetails;
    }

    public void setTblPreAddedStoresDataDetails(List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails) {
        this.tblPreAddedStoresDataDetails = tblPreAddedStoresDataDetails;
    }

    public List<TblStateCityMaster> getTblStateCityMaster() {
        return tblStateCityMaster;
    }

    public void setTblStateCityMaster(List<TblStateCityMaster> tblStateCityMaster) {
        this.tblStateCityMaster = tblStateCityMaster;
    }

    public List<TblProductListMaster> getTblProductListMaster() {
        return tblProductListMaster;
    }

    public void setTblProductListMaster(List<TblProductListMaster> tblProductListMaster) {
        this.tblProductListMaster = tblProductListMaster;
    }

    public List<TblProductSegementMap> getTblProductSegementMap() {
        return tblProductSegementMap;
    }

    public void setTblProductSegementMap(List<TblProductSegementMap> tblProductSegementMap) {
        this.tblProductSegementMap = tblProductSegementMap;
    }

    public List<TblPriceApplyType> getTblPriceApplyType() {
        return tblPriceApplyType;
    }

    public void setTblPriceApplyType(List<TblPriceApplyType> tblPriceApplyType) {
        this.tblPriceApplyType = tblPriceApplyType;
    }

    public List<TblUOMMaster> getTblUOMMaster() {
        return tblUOMMaster;
    }

    public void setTblUOMMaster(List<TblUOMMaster> tblUOMMaster) {
        this.tblUOMMaster = tblUOMMaster;
    }

    public List<TblUOMMapping> getTblUOMMapping() {
        return tblUOMMapping;
    }

    public void setTblUOMMapping(List<TblUOMMapping> tblUOMMapping) {
        this.tblUOMMapping = tblUOMMapping;
    }

    public List<TblManagerMstr> getTblManagerMstr() {
        return tblManagerMstr;
    }

    public void setTblManagerMstr(List<TblManagerMstr> tblManagerMstr) {
        this.tblManagerMstr = tblManagerMstr;
    }

    public List<TblCategoryMaster> getTblCategoryMaster() {
        return tblCategoryMaster;
    }

    public void setTblCategoryMaster(List<TblCategoryMaster> tblCategoryMaster) {
        this.tblCategoryMaster = tblCategoryMaster;
    }

    public List<TblBankMaster> getTblBankMaster() {
        return tblBankMaster;
    }

    public void setTblBankMaster(List<TblBankMaster> tblBankMaster) {
        this.tblBankMaster = tblBankMaster;
    }

    public List<TblInstrumentMaster> getTblInstrumentMaster() {
        return tblInstrumentMaster;
    }

    public void setTblInstrumentMaster(List<TblInstrumentMaster> tblInstrumentMaster) {
        this.tblInstrumentMaster = tblInstrumentMaster;
    }

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblDistributorProductStock> getTblDistributorProductStock() {
        return tblDistributorProductStock;
    }

    public void setTblDistributorProductStock(List<TblDistributorProductStock> tblDistributorProductStock) {
        this.tblDistributorProductStock = tblDistributorProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblDistributorStockOutFlg> getTblDistributorStockOutFlg() {
        return tblDistributorStockOutFlg;
    }

    public void setTblDistributorStockOutFlg(List<TblDistributorStockOutFlg> tblDistributorStockOutFlg) {
        this.tblDistributorStockOutFlg = tblDistributorStockOutFlg;
    }

    public List<TblDistributorIDOrderIDLeft> getTblDistributorIDOrderIDLeft() {
        return tblDistributorIDOrderIDLeft;
    }

    public void setTblDistributorIDOrderIDLeft(List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft) {
        this.tblDistributorIDOrderIDLeft = tblDistributorIDOrderIDLeft;
    }

    public List<TblInvoiceCaption> getTblInvoiceCaption() {
        return tblInvoiceCaption;
    }

    public void setTblInvoiceCaption(List<TblInvoiceCaption> tblInvoiceCaption) {
        this.tblInvoiceCaption = tblInvoiceCaption;
    }

    public List<TblGetReturnsReasonForPDAMstr> getTblGetReturnsReasonForPDAMstr() {
        return tblGetReturnsReasonForPDAMstr;
    }

    public void setTblGetReturnsReasonForPDAMstr(List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr) {
        this.tblGetReturnsReasonForPDAMstr = tblGetReturnsReasonForPDAMstr;
    }

    public List<TblIsSchemeApplicable> getTblIsSchemeApplicable() {
        return tblIsSchemeApplicable;
    }

    public void setTblIsSchemeApplicable(List<TblIsSchemeApplicable> tblIsSchemeApplicable) {
        this.tblIsSchemeApplicable = tblIsSchemeApplicable;
    }

    public List<TblStoreListMaster> getTblStoreListMaster() {
        return tblStoreListMaster;
    }

    public void setTblStoreListMaster(List<TblStoreListMaster> tblStoreListMaster) {
        this.tblStoreListMaster = tblStoreListMaster;
    }

    public List<TblStoreListWithPaymentAddressMR> getTblStoreListWithPaymentAddressMR() {
        return tblStoreListWithPaymentAddressMR;
    }

    public void setTblStoreListWithPaymentAddressMR(List<TblStoreListWithPaymentAddressMR> tblStoreListWithPaymentAddressMR) {
        this.tblStoreListWithPaymentAddressMR = tblStoreListWithPaymentAddressMR;
    }

    public List<TblStoreSomeProdQuotePriceMstr> getTblStoreSomeProdQuotePriceMstr() {
        return tblStoreSomeProdQuotePriceMstr;
    }

    public void setTblStoreSomeProdQuotePriceMstr(List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr) {
        this.tblStoreSomeProdQuotePriceMstr = tblStoreSomeProdQuotePriceMstr;
    }

    public List<TblStoreLastDeliveryNoteNumber> getTblStoreLastDeliveryNoteNumber() {
        return tblStoreLastDeliveryNoteNumber;
    }

    public void setTblStoreLastDeliveryNoteNumber(List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber) {
        this.tblStoreLastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumber;
    }

    public List<TblProductListLastVisitStockOrOrderMstr> getTblProductListLastVisitStockOrOrderMstr() {
        return tblProductListLastVisitStockOrOrderMstr;
    }

    public void setTblProductListLastVisitStockOrOrderMstr(List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr) {
        this.tblProductListLastVisitStockOrOrderMstr = tblProductListLastVisitStockOrOrderMstr;
    }

    public List<TblPDAGetLODQty> getTblPDAGetLODQty() {
        return tblPDAGetLODQty;
    }

    public void setTblPDAGetLODQty(List<TblPDAGetLODQty> tblPDAGetLODQty) {
        this.tblPDAGetLODQty = tblPDAGetLODQty;
    }

    public List<TblPDAGetLastVisitDate> getTblPDAGetLastVisitDate() {
        return tblPDAGetLastVisitDate;
    }

    public void setTblPDAGetLastVisitDate(List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate) {
        this.tblPDAGetLastVisitDate = tblPDAGetLastVisitDate;
    }

    public List<TblPDAGetLastOrderDate> getTblPDAGetLastOrderDate() {
        return tblPDAGetLastOrderDate;
    }

    public void setTblPDAGetLastOrderDate(List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate) {
        this.tblPDAGetLastOrderDate = tblPDAGetLastOrderDate;
    }

    public List<TblPDAGetLastVisitDetails> getTblPDAGetLastVisitDetails() {
        return tblPDAGetLastVisitDetails;
    }

    public void setTblPDAGetLastVisitDetails(List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails) {
        this.tblPDAGetLastVisitDetails = tblPDAGetLastVisitDetails;
    }

    public List<TblPDAGetLastOrderDetails> getTblPDAGetLastOrderDetails() {
        return tblPDAGetLastOrderDetails;
    }

    public void setTblPDAGetLastOrderDetails(List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails) {
        this.tblPDAGetLastOrderDetails = tblPDAGetLastOrderDetails;
    }

    public List<TblPDAGetLastOrderDetailsTotalValues> getTblPDAGetLastOrderDetailsTotalValues() {
        return tblPDAGetLastOrderDetailsTotalValues;
    }

    public void setTblPDAGetLastOrderDetailsTotalValues(List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues) {
        this.tblPDAGetLastOrderDetailsTotalValues = tblPDAGetLastOrderDetailsTotalValues;
    }

    public List<TblPDAGetExecutionSummary> getTblPDAGetExecutionSummary() {
        return tblPDAGetExecutionSummary;
    }

    public void setTblPDAGetExecutionSummary(List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary) {
        this.tblPDAGetExecutionSummary = tblPDAGetExecutionSummary;
    }

    public List<TblLastOutstanding> getTblLastOutstanding() {
        return tblLastOutstanding;
    }

    public void setTblLastOutstanding(List<TblLastOutstanding> tblLastOutstanding) {
        this.tblLastOutstanding = tblLastOutstanding;
    }

    public List<TblInvoiceLastVisitDetails> getTblInvoiceLastVisitDetails() {
        return tblInvoiceLastVisitDetails;
    }

    public void setTblInvoiceLastVisitDetails(List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails) {
        this.tblInvoiceLastVisitDetails = tblInvoiceLastVisitDetails;
    }

*/





}
