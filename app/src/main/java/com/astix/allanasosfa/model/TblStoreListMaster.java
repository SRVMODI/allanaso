package com.astix.allanasosfa.model;

import com.google.gson.annotations.SerializedName;

public class TblStoreListMaster {

    @SerializedName("CityID")
    private String cityID;
    @SerializedName("CollectionPer")
    private int collectionPer;
    @SerializedName("DBR")
    private String dBR;
    @SerializedName("FlgAllowQuotation")
    private int flgAllowQuotation;
    @SerializedName("FlgCollDefControl")
    private int flgCollDefControl;
    @SerializedName("FlgHasQuote")
    private int flgHasQuote;
    @SerializedName("FlgLastVisitOrdered")
    private int flgLastVisitOrdered;
    @SerializedName("FlgPTRPTCMarked")
    private int flgPTRPTCMarked;
    @SerializedName("FlgRuleTaxVal")
    private int flgRuleTaxVal;
    @SerializedName("FlgSubmitFromQuotation")
    private int flgSubmitFromQuotation;
    @SerializedName("FlgTransType")
    private int flgTransType;
    @SerializedName("ID")
    private int iD;
    @SerializedName("IsClose")
    private int isClose;
    @SerializedName("IsComposite")
    private int isComposite;
    @SerializedName("IsNextDat")
    private int isNextDat;
    @SerializedName("LastExecutionDate")
    private String lastExecutionDate;
    @SerializedName("LastTransactionDate")
    private String lastTransactionDate;
    @SerializedName("LastVisitDate")
    private String lastVisitDate;
    @SerializedName("OutStanding")
    private int outStanding;
    @SerializedName("OverDue")
    private int overDue;
    @SerializedName("OwnerName")
    private String ownerName;
    @SerializedName("PaymentStage")
    private String paymentStage;
    @SerializedName("RouteID")
    private int routeID;
    @SerializedName("RouteNodeType")
    private int routeNodeType;
    @SerializedName("SalesPersonContact")
    private String salesPersonContact;
    @SerializedName("SalesPersonName")
    private String salesPersonName;
    @SerializedName("Sstat")
    private String sstat;
    @SerializedName("StateID")
    private int stateID;
    @SerializedName("StoreAddress")
    private String storeAddress;
    @SerializedName("StoreCatType")
    private String storeCatType;
    @SerializedName("StoreCity")
    private String storeCity;
    @SerializedName("StoreContactNo")
    private String storeContactNo;
    @SerializedName("StoreID")
    private String storeID;
    @SerializedName("StoreIDPDA")
    private String storeIDPDA;
    @SerializedName("StoreLatitude")
    private Double storeLatitude;
    @SerializedName("StoreLongitude")
    private Double storeLongitude;
    @SerializedName("Storeintitude")
    private Double storeintitude;
    @SerializedName("StoreName")
    private String storeName;
    @SerializedName("StorePinCode")
    private String storePinCode;
    @SerializedName("StoreState")
    private String storeState;
    @SerializedName("StoreType")
    private int storeType;
    @SerializedName("TaxNumber")
    private String taxNumber;
    @SerializedName("TotalLastInvoiceValue")
    private float totalLastInvoiceValue;
    @SerializedName("TotalLastOrderValue")
    private Double totalLastOrderValue;

    private int IsNewStoreDataCompleteSaved;
    private int StoreClose;

    public int getFlgRadioButtonSelected() {
        return flgRadioButtonSelected;
    }

    public void setFlgRadioButtonSelected(int flgRadioButtonSelected) {
        this.flgRadioButtonSelected = flgRadioButtonSelected;
    }

    private int flgRadioButtonSelected;


    private int flgOrderType;
    @SerializedName("flgProductive")
    private int flgProductive;
    @SerializedName("NoOfBrands")
    private int NoOfBrands;
    @SerializedName("P3MValue")
    private Double P3MValue;
    @SerializedName("MTDValue")
    private Double MTDValue;
    @SerializedName("flgBoughtLast3Months")
    private int flgBoughtLast3Months;

    @SerializedName("CoverageAreaNodeID")
    private int CoverageAreaNodeID;

    @SerializedName("CoverageAreaNodeType")
    private int CoverageAreaNodeType;

    public int getISNewStore() {
        return ISNewStore;
    }

    public void setISNewStore(int ISNewStore) {
        this.ISNewStore = ISNewStore;
    }

    private int ISNewStore;

    public int getStoreStatusVisitedOfNewStore() {
        return StoreStatusVisitedOfNewStore;
    }

    public void setStoreStatusVisitedOfNewStore(int storeStatusVisitedOfNewStore) {
        StoreStatusVisitedOfNewStore = storeStatusVisitedOfNewStore;
    }

    private int StoreStatusVisitedOfNewStore;

    public int getFlgProductive() {
        return flgProductive;
    }

    public void setFlgProductive(int flgProductive) {
        this.flgProductive = flgProductive;
    }

    public int getNoOfBrands() {
        return NoOfBrands;
    }

    public void setNoOfBrands(int noOfBrands) {
        NoOfBrands = noOfBrands;
    }

    public Double getP3MValue() {
        return P3MValue;
    }

    public void setP3MValue(Double p3MValue) {
        P3MValue = p3MValue;
    }

    public Double getMTDValue() {
        return MTDValue;
    }

    public void setMTDValue(Double MTDValue) {
        this.MTDValue = MTDValue;
    }

    public int getFlgBoughtLast3Months() {
        return flgBoughtLast3Months;
    }

    public void setFlgBoughtLast3Months(int flgBoughtLast3Months) {
        this.flgBoughtLast3Months = flgBoughtLast3Months;
    }


    public int getCoverageAreaNodeID() {
        return CoverageAreaNodeID;
    }

    public void setCoverageAreaNodeID(int coverageAreaNodeID) {
        CoverageAreaNodeID = coverageAreaNodeID;
    }

    public int getCoverageAreaNodeType() {
        return CoverageAreaNodeType;
    }

    public void setCoverageAreaNodeType(int coverageAreaNodeType) {
        CoverageAreaNodeType = coverageAreaNodeType;
    }

    public int getIsNewStoreDataCompleteSaved() {
        return IsNewStoreDataCompleteSaved;
    }

    public void setIsNewStoreDataCompleteSaved(int isNewStoreDataCompleteSaved) {
        IsNewStoreDataCompleteSaved = isNewStoreDataCompleteSaved;
    }

    public int getStoreClose() {
        return StoreClose;
    }

    public void setStoreClose(int storeClose) {
        StoreClose = storeClose;
    }

    public int getFlgOrderType() {
        return flgOrderType;
    }

    public void setFlgOrderType(int flgOrderType) {
        this.flgOrderType = flgOrderType;
    }

    public Double getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(Double storeLongitude) {
        this.storeLongitude = storeLongitude;
    }


    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public int getCollectionPer() {
        return collectionPer;
    }

    public void setCollectionPer(int collectionPer) {
        this.collectionPer = collectionPer;
    }

    public String getDBR() {
        return dBR;
    }

    public void setDBR(String dBR) {
        this.dBR = dBR;
    }

    public int getFlgAllowQuotation() {
        return flgAllowQuotation;
    }

    public void setFlgAllowQuotation(int flgAllowQuotation) {
        this.flgAllowQuotation = flgAllowQuotation;
    }

    public int getFlgCollDefControl() {
        return flgCollDefControl;
    }

    public void setFlgCollDefControl(int flgCollDefControl) {
        this.flgCollDefControl = flgCollDefControl;
    }

    public int getFlgHasQuote() {
        return flgHasQuote;
    }

    public void setFlgHasQuote(int flgHasQuote) {
        this.flgHasQuote = flgHasQuote;
    }

    public int getFlgLastVisitOrdered() {
        return flgLastVisitOrdered;
    }

    public void setFlgLastVisitOrdered(int flgLastVisitOrdered) {
        this.flgLastVisitOrdered = flgLastVisitOrdered;
    }

    public int getFlgPTRPTCMarked() {
        return flgPTRPTCMarked;
    }

    public void setFlgPTRPTCMarked(int flgPTRPTCMarked) {
        this.flgPTRPTCMarked = flgPTRPTCMarked;
    }

    public int getFlgRuleTaxVal() {
        return flgRuleTaxVal;
    }

    public void setFlgRuleTaxVal(int flgRuleTaxVal) {
        this.flgRuleTaxVal = flgRuleTaxVal;
    }

    public int getFlgSubmitFromQuotation() {
        return flgSubmitFromQuotation;
    }

    public void setFlgSubmitFromQuotation(int flgSubmitFromQuotation) {
        this.flgSubmitFromQuotation = flgSubmitFromQuotation;
    }

    public int getFlgTransType() {
        return flgTransType;
    }

    public void setFlgTransType(int flgTransType) {
        this.flgTransType = flgTransType;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public int getIsComposite() {
        return isComposite;
    }

    public void setIsComposite(int isComposite) {
        this.isComposite = isComposite;
    }

    public int getIsNextDat() {
        return isNextDat;
    }

    public void setIsNextDat(int isNextDat) {
        this.isNextDat = isNextDat;
    }

    public String getLastExecutionDate() {
        return lastExecutionDate;
    }

    public void setLastExecutionDate(String lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
    }

    public String getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(String lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public int getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(int outStanding) {
        this.outStanding = outStanding;
    }

    public int getOverDue() {
        return overDue;
    }

    public void setOverDue(int overDue) {
        this.overDue = overDue;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPaymentStage() {
        return paymentStage;
    }

    public void setPaymentStage(String paymentStage) {
        this.paymentStage = paymentStage;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(int routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    public String getSalesPersonContact() {
        return salesPersonContact;
    }

    public void setSalesPersonContact(String salesPersonContact) {
        this.salesPersonContact = salesPersonContact;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getSstat() {
        return sstat;
    }

    public void setSstat(String sstat) {
        this.sstat = sstat;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCatType() {
        return storeCatType;
    }

    public void setStoreCatType(String storeCatType) {
        this.storeCatType = storeCatType;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreContactNo() {
        return storeContactNo;
    }

    public void setStoreContactNo(String storeContactNo) {
        this.storeContactNo = storeContactNo;
    }


    public String getStoreIDPDA() {
        return storeIDPDA;
    }

    public void setStoreIDPDA(String storeIDPDA) {
        this.storeIDPDA = storeIDPDA;
    }

    public Double getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(Double storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public Double getStoreintitude() {
        return storeintitude;
    }

    public void setStoreintitude(Double storeintitude) {
        this.storeintitude = storeintitude;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePinCode() {
        return storePinCode;
    }

    public void setStorePinCode(String storePinCode) {
        this.storePinCode = storePinCode;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public float getTotalLastInvoiceValue() {
        return totalLastInvoiceValue;
    }

    public void setTotalLastInvoiceValue(float totalLastInvoiceValue) {
        this.totalLastInvoiceValue = totalLastInvoiceValue;
    }

    public Double getTotalLastOrderValue() {
        return totalLastOrderValue;
    }

    public void setTotalLastOrderValue(Double totalLastOrderValue) {
        this.totalLastOrderValue = totalLastOrderValue;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
}
