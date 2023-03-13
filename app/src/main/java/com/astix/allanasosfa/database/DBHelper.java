package com.astix.allanasosfa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.astix.Common.CommonInfo;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public static final String flgStoreVisitFeedback = "flgStoreVisitFeedback";
    public static final String flgDSMOverAllFeedback = "flgDSMOverAllFeedback";
    public static final String flgDSMVisitFeedback = "flgDSMVisitFeedback";
    public static final String flgNewStore = "flgNewStore";


    public static final String DESCRIPTION = "Description";
    public static final String YTD_TGT    = "YTD_Tgt";
    public static final String YTD_TILLDATE = "YTD_TillDate";
    public static final String MTD = "MTD";
    public static final String New_YESTERDAY = "New_YesterDay";
    public static final String RR_REQUIRED = "RR_Required";
    public static final String FLGCOLLAPSE = "flgCollapse";
    public static final String flgLEVEL = "flgLevel";
    public static final String TABLE_REPORTDISTRIBUTION="tblRptDistribution";
    public static final String CREATE_TABLE_REPORTDISTRIBUTION ="CREATE TABLE "+
            TABLE_REPORTDISTRIBUTION+"("+
            DESCRIPTION+" TEXT ,"+
            YTD_TGT+" TEXT,"+
            YTD_TILLDATE+" TEXT,"+
            MTD+" TEXT,"+
            New_YESTERDAY+" TEXT,"+
            flgLEVEL+" INTEGER,"+
            FLGCOLLAPSE+" INTEGER);";


    public static final String MTD_TGT    = "MTD_Tgt";
    public static final String MTD_TILLDATE    = "MTD_TillDate";
    public static final String YESTERDAY    = "Yesterday";
    public static final String TABLE_REPORTSECTIONVOL="tblRptSecVol";
    public static final String CREATE_TABLE_REPORTSECTIONVOL ="CREATE TABLE "+
            TABLE_REPORTSECTIONVOL+"("+
            DESCRIPTION+" TEXT ,"+
            MTD_TGT+" TEXT,"+
            MTD_TILLDATE+" TEXT,"+
            RR_REQUIRED+" TEXT,"+
            YESTERDAY+" TEXT,"+
            flgLEVEL+" INTEGER,"+
            FLGCOLLAPSE+" INTEGER);";

    public static final String MTD_DELIVERED    = "MTD_Delivered";
    public static final String PENDING_DELIVERY    = "Pending_Delivery";

    public static final String TABLE_REPORTPRIMARYVOL="tblRptPrimaryVol";
    public static final String CREATE_TABLE_REPORTPRIMARYVOL ="CREATE TABLE "+
            TABLE_REPORTPRIMARYVOL+"("+
            DESCRIPTION+" TEXT ,"+
            MTD_TGT+" TEXT,"+
            MTD_DELIVERED+" TEXT,"+
            PENDING_DELIVERY+" TEXT,"+
            flgLEVEL+" INTEGER,"+
            YESTERDAY+" TEXT,"+
            FLGCOLLAPSE+" INTEGER);";

    public static final String PLANNED    = "Planned";
    public static final String IN_FIELD_MTD    = "In_Field_MTD";
    public static final String IN_FIELD_YESTERDAY    = "In_Field_Yesterday";

    public static final String TABLE_REPORTMANDAYS="tblRptManDays";
    public static final String CREATE_TABLE_REPORTMANDAYS="CREATE TABLE "+
            TABLE_REPORTMANDAYS+"("+
            DESCRIPTION+" TEXT ,"+
            PLANNED+" INTEGER,"+
            IN_FIELD_MTD+" INTEGER,"+
            IN_FIELD_YESTERDAY+" INTEGER);";

    public static final String MANAGERNODEID = "ManagerNodeId";
    public static final String MANAGERNODETYPE = "ManagerNodeType";
    public static final String ROUTETYPE = "RouteType";
    public static final String COVERAGETYPE = "CoverageAreaType";
    public static final String VISITDATE = "VisitDate";
    public static final String VISITSTARTTIME = "VisitStartTime";
    public static final String VISITENDTIME = "VisitEndtime";
    public static final String IMEINO = "IMEINo";
    public static final String TIMESTAMPIN = "TimeStampIn";
    public static final String ALLPROVIDERDATA = "AllProviderData";
    public static final String GPSLATITUDE = "GPSLatitude";
    public static final String GPSLONGITUDE = "GPSLongitude";
    public static final String NETWORKLATITUDE = "NetworkLatitude";
    public static final String NETWORKLONGITUDE = "NetworkLongitude";
    public static final String NETWORKACCURACY = "NetworkAccuracy";
    public static final String NETWORKADDRESS = "NetworkAddress";
    public static final String FUSEDLATITUDE = "FusedLatitude";
    public static final String FUSEDLONGITUDE = "FusedLongitude";
    public static final String FLGREBOOT = "flgReboot";
    public static final String ISCOMPLETE = "isComplete";
    public static final String JOINTVISITID = "JointVisitId";
    public static final String ROUTEID= "Routeid";
    public static final String COVERAGEAREANODEID= "CoverageAreaNodeID";
    public static final String	MAPADDRESS="MapAddress";
    public static final String	MAPCITY="MapCity";
    public static final String	MAPPINCODE="MapPinCode";
    public static final String	MAPSTATE="MapState";
    public static final String	FLGLOCATIONSERVICESONOFF="flgLocationServicesOnOff";
    public static final String	FLGGPSONOFF="flgGpsOnOff";
    public static final String	FLGNETWORKONOFF="flgNetworkOnOff";
    public static final String	FLGFUSEDONOFF="flgFusedOnOff";
    public static final String	FLGINTERNETONOFFWHILELOCATIONTRACKING="flgInternetOnOffWhileLocationTracking";
    public static final String ACTUALLATITUDE="ActualLatitude";
    public static final String ACTUALLONGITUDE="ActualLongitude";
    public static final String LOCPROVIDER="LocProvider";
    public static final String ACCURACY=	"Accuracy";
    public static final String GPSACCURACY=	"GpsAccuracy";
    public static final String GPSADDRESS=	"GpsAddress";
    public static final String FUSEDACCURACY=	"FusedAccuracy";
    public static final String FUSEDADDRESS=	"FusedAddress";
    public static final String ADDRESS=	"Address";
    public static final String SSTAT=	"Sstat";



    public static final String TABLE_JOINTVISITMASTER = "tblJointVisitMstr";
    public static final String CREATE_TABLE__JOINTVISITMASTER ="CREATE TABLE "+
            TABLE_JOINTVISITMASTER+"("+
            JOINTVISITID+" TEXT, "+
            MANAGERNODEID+" INTEGER, "+
            MANAGERNODETYPE+" INTEGER, "+
            IMEINO+" TEXT, "+
            ROUTEID+" INTEGER, "+
            ROUTETYPE+" TEXT, "+
            COVERAGEAREANODEID+" INTEGER, "+
            COVERAGETYPE+" TEXT,"+
            VISITDATE+" TEXT,"+
            VISITSTARTTIME+" TEXT,"+
            VISITENDTIME+" TEXT,"+
            MAPADDRESS+" TEXT,"+
            MAPPINCODE+" INTEGER,"+
            MAPCITY+" TEXT,"+
            MAPSTATE+" TEXT,"+
            ACTUALLATITUDE+" REAL,"+
            ACTUALLONGITUDE+" REAL,"+
            LOCPROVIDER+" TEXT,"+
            ACCURACY+" INTEGER,"+
            TIMESTAMPIN+" TEXT,"+
            ALLPROVIDERDATA+" TEXT,"+
            GPSLATITUDE+" REAL,"+
            GPSLONGITUDE+" REAL,"+
            GPSACCURACY+" INTEGER,"+
            GPSADDRESS+" TEXT,"+
            NETWORKLATITUDE+" REAL,"+
            NETWORKLONGITUDE+" REAL,"+
            NETWORKACCURACY+" INTEGER,"+
            NETWORKADDRESS+" TEXT,"+
            FUSEDLATITUDE+" REAL,"+
            FUSEDLONGITUDE+" REAL,"+
            FUSEDACCURACY+" INTEGER,"+
            FUSEDADDRESS+" INTEGER,"+
            FLGLOCATIONSERVICESONOFF+" INTEGER,"+
            FLGGPSONOFF+" INTEGER,"+
            FLGNETWORKONOFF+" INTEGER,"+
            FLGFUSEDONOFF+" INTEGER,"+
            FLGINTERNETONOFFWHILELOCATIONTRACKING+" INTEGER,"+
            FLGREBOOT+" INTEGER,"+
            ADDRESS+" TEXT,"+
            ISCOMPLETE+" INTEGER,"+
            SSTAT+" INTEGER);";

    public static final String FELLOWPERSONNODEID = "FellowPersonNodeId";
    public static final String FELLOWPERSONNODETYPE = "FellowPersonNodeType";
    public static final String FELLOWPERSONNAME = "FellowPersonName";
    public static final String TABLE_JOINTVISITDETAILS = "tblJointVisitDetails";
    public static final String CREATE_TABLE_JOINTVISITDETAILS ="CREATE TABLE "+
            TABLE_JOINTVISITDETAILS+"("+
            JOINTVISITID+" TEXT ,"+
            FELLOWPERSONNODEID+" INTEGER,"+
            FELLOWPERSONNAME+" TEXT,"+
            SSTAT+" INTEGER,"+
            FELLOWPERSONNODETYPE+" TEXT);";



    public static final String DATABASE_TABLE_tblQuestionsSurvey= "tblQuestionsSurvey";
    public static final String DATABASE_CREATE_TABLE_tblQuestionsSurvey = "create table tblQuestionsSurvey (QstnID text null,QstnText text null,flgActive text null,flgOrder text null);";
    public static final String DATABASE_TABLE_tblOptionSurvey= "tblOptionSurvey";
    public static final String DATABASE_CREATE_TABLE_tblOptionSurvey = "create table tblOptionSurvey (OptionID text null,OptionText text null,QstnID text null,flgaActive text null);";
    public static final String DATABASE_TABLE_tblSurveyData= "tblSurveyData";
    public static final String DATABASE_CREATE_TABLE_tblSurveyData = "create table tblSurveyData (StoreID text null,QstnID text null,OptionID text null,OptionText text null,DateTime text null,Latitude text null,Longitude text null,Accuracy text null,Sstat Integer null,flgNetorkTimeRecorded int null, device_time text null);";


    public static final String DATABASE_TABLE_tblCoverageDsr = "tblRoutelist";

    public static final String DATABASE_TABLE_tblCoverageMstr = "tblCoverage";

    public static final String CoverageAreaNodeID = "CoverageAreaNodeID";
    public static final String CoverageAreaNodeType = "CoverageAreaNodeType";
    public static final String CoverageArea = "CoverageArea";
    public static final String PersonNodeID = "PersonNodeID";
    public static final String PersonNodeType = "PersonNodeType";
    public static final String PersonName = "PersonName";
    public static final String RouteNodeID = "RouteNodeID";
    public static final String RouteNodeType = "RouteNodeType";
    public static final String Route = "Route";
    public static final String Active = "Active";
    public static final String flgTodayRoute = "flgTodayRoute";

    public static final String WorkingType = "WorkingType";


    public static final String DATABASE_CREATE_TABLE_tblCoverageMstr= "create table tblCoverage (CoverageAreaNodeID text null, CoverageAreaNodeType text null, CoverageArea text null," +
            "PersonNodeID text null, PersonNodeType text null, PersonName text null, WorkingType int null);";

    public static final String DATABASE_CREATE_TABLE_tblCoverageDsr= "create table tblRoutelist (CoverageAreaNodeID text null, CoverageAreaNodeType text null, CoverageArea text null," +
            "PersonNodeID text null, PersonNodeType text null, PersonName text null, RouteNodeID text null, RouteNodeType text null, Route text null, Active text null, flgTodayRoute int  null);";

    public static final String DATABASE_TABLE_tblStoreEdit = "tblStoreEdit";
    public static final String DATABASE_CREATE_TABLE_tblStoreEdit = "create table tblStoreEdit (StoreID text null, OwnerName text null, ContactNumber text null, StoreAddress text null, SalesPersonName text null, SalesPersonContact text null, Sstat integer not null, AddImageFlag integer not null, ReplaceImageFlag integer not null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_tblStoreEditImages = "tblStoreEditImages";
    public static final String DATABASE_CREATE_TABLE_tblStoreEditImages= "create table tblStoreEditImages (StoreID text null,  ImageName integer not null, ImagePath integer not null,Sstat integer not null,flgNetorkTimeRecorded int null, device_time text null);";


    public static final String DATABASE_TABLE_tblExecutionImages = "tblExecutionImages";
    public static final String DATABASE_CREATE_TABLE_tblExecutionImages= "create table tblExecutionImages (OrderID text null,StoreID text null, ImageName integer not null, ImagePath integer not null,Sstat integer not null,InvNumber text null,InvDate text null);";

    public static final String DATABASE_TABLE_REASON_ORDER_CANCEL = "tblReasonOrderCncl";
    public static final String DATABASE_CREATE_REASON_ORDER_CANCEL = "create table tblReasonOrderCncl (ReasonCodeID int null,ReasonDescr text null);";

    public static final String TABLE_tblAllServicesCalledSuccessfull = "tblAllServicesCalledSuccessfull";
    public static final String DATABASE_CREATE_tblAllServicesCalledSuccessfull = "create table tblAllServicesCalledSuccessfull(flgAllServicesCalledOrNot int null);";


    public static final String DATABASE_TABLE_tblDAGetAddedOutletSummaryReport = "tblDAGetAddedOutletSummaryReport";
    public static final String DATABASE_CREATE_TABLE_tblDAGetAddedOutletSummaryReport = "create table tblDAGetAddedOutletSummaryReport (Header text null,Child text null,TotalStores int null,Validated int null,Pending int null,FlgNormalOverall int null);";


    public static final String TABLE_tblAppMasterFlags = "tblAppMasterFlags";
    public static final String DATABASE_CREATE_TABLE_tblAppMasterFlags = "create table tblAppMasterFlags(flgDistributorCheckIn int not null,flgDBRStockInApp int not null,flgDBRStockEdit int not null,flgDBRStockCalculate int not null,flgDBRStockControl int not null,flgCollRequired int not null,flgCollReqOrdr int not null ,flgCollTab int not null,flgCollDefControl int not null,flgCashDiscount int not null,flgCollControlRule int not null,flgSchemeAvailable int not null,flgSchemeAllowEntry int not null,flgSchemeAllowEdit int not null ,flgQuotationIsAvailable int not null,flgExecutionIsAvailable int not null ,flgExecutionPhotoCompulsory int not null,flgTargetShowatStart int not null,flgIncentiveShowatStart int not null,flgInvoicePrint int not null ,flgShowPOSM int not null,flgVisitStartOutstandingDetails int not null,flgVisitStartSchemeDetails int not null,flgStoreDetailsEdit int not null,flgShowDeliveryAddressButtonOnOrder int not null,flgShowManagerOnStoreList int not null,flgRptTargetVsAchived int not null ,SalesNodeID int not null,SalesNodetype int not null,WorkingTypeID int not null,flgVanStockInApp int not null,flgVanStockEdit int not null,flgVanStockCalculate int not null,flgVanStockControl int not null,flgStockRefillReq int not null,flgDayEnd int not null,flgStockUnloadAtCycleEnd int not null,flgStockUnloadAtDayEnd int not null,flgCollReqATCycleEnd int not null,flgCollReqATDayEnd int not null,flgDayEndSummary int not null,flgStoreCheckInApplicable int not null,flgStoreCheckInPhotoCompulsory int not null);";


    public static final String TABLE_tblCollectionReportCashChange = "tblCollectionReportCashChange";
    public static final String DATABASE_CREATE_TABLE_tblCollectionReportCashChange = "create table tblCollectionReportCashChange(StoreID text null,PreviousCashCollectionAmt real null,ModifiedCashCollectionAmt real null,Sstat text null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_tblCollectionReportChequeChange="tblCollectionReportChequeChange";
    public static final String DATABASE_CREATE_TABLE_tblCollectionReportChequeChange="create table tblCollectionReportChequeChange (StoreID text not null, PaymentMode_Old text null,PaymentModeID_Old text null, Amount_Old text null, RefNoChequeNoTrnNo_Old text null, Date_Old text null, Bank_Old text null,PaymentMode_New text null,PaymentModeID_New text null, Amount_New text null, RefNoChequeNoTrnNo_New text null, Date_New text null, Bank_New text null,flgDeleteModifyNew text null,Sstat text null,flgNetorkTimeRecorded int null, device_time text null);";


    public static final String TABLE_tblStoreLastDeliveryNoteNumber = "tblStoreLastDeliveryNoteNumber";
    public static final String DATABASE_CREATE_TABLE_tblStoreLastDeliveryNoteNumber = "create table tblStoreLastDeliveryNoteNumber(LastDeliveryNoteNumber int null);";

    public static final String TABLE_tblWarehouseMstr = "tblWarehouseMstr";
    public static final String DATABASE_CREATE_TABLE_tblWarehouseMstr = "create table tblWarehouseMstr(NodeID int null,NodeType int null,Descr text null,latCode text null,LongCode text null,flgMapped int null,Address text null,State text null,City text null,PinCode text null,PhoneNo text null,TaxNumber text null);";

    public static final String TABLE_tblUOMMstr = "tblUOMMaster";
    public static final String DATABASE_CREATE_TABLE_tblUOMMstr = "create table tblUOMMaster(UOMId int not null,UOM text not null,flgBaseUnit int null);";

    public static final String TABLE_tblUOMMapping = "tblUOMMapping";
    public static final String DATABASE_CREATE_TABLE_tblUOMMapping = "create table tblUOMMapping(NodeId int not null,NodeType int not null,BaseUnitID int not null,PackUnitId int not null,BaseValue real not null,flgDefaultUOM int not null);";

    public static final String TABLE_tbl_StockRqst = "tblStockRqst";
    public static final String DATABASE_CREATE_TABLE_tblStockReq = "create table tblStockRqst(ProductID int null,ReqStock int null,dfltUOMId int null,SlctdUOMID int null);";

    public static final String DATABASE_TABLE_tblStoreImageList = "tblStoreImageList";
    public static final String DATABASE_CREATE_TABLE_tblStoreImageList = "create table tblStoreImageList (StoreID text null,StoreIDDB text null,StoreImagename text null,ImageType int null,flgManagerUploaded int null);";


    public static final String DATABASE_TABLE_tblUserName = "tblUserName";
    public static final String DATABASE_TABLE_tblStoreCountDetails = "tblStoreCountDetails";
    public static final String DATABASE_TABLE_tblPreAddedStores = "tblPreAddedStores";
    public static final String DATABASE_TABLE_tblPreAddedStoresDataDetails = "tblPreAddedStoresDataDetails";
    public static final String DATABASE_TABLE_tblStoreCheckInPic = "tblStoreCheckInPic";
    public static final String DATABASE_CREATE_TABLE_tblStoreCheckInPic = "create table tblStoreCheckInPic (StoreID text null,imageName text null,picClkdPath text null,clkdDateTime,Sstat integer null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_CREATE_TABLE_tblUserName = "create table tblUserName (UserName text null);";
    public static final String DATABASE_CREATE_TABLE_tblStoreCountDetails = "create table tblStoreCountDetails (TotStoreAdded int not null,TodayStoreAdded int not null);";
    public static final String DATABASE_CREATE_TABLE_tblPreAddedStores = "create table tblPreAddedStores(StoreID text null,StoreName text null,LatCode text null," +
            "LongCode text null,DateAdded text null,DistanceNear int null,flgOldNewStore int null,CoverageAreaID int null,CoverageAreaType int null," +
            "RouteNodeID int null,RouteNodeType int null,City text null,State text null,PinCode text null,StoreCategoryType text null,StoreSectionCount int null," +
            "flgApproveOrRejectOrNoActionOrReVisit int null,SOLatCode text null,SOLongCode text null,VisitStartTS text null,VisitEndTS text null," +
            "LocProvider text null,Accuracy text null,BateryLeftStatus text null,IsStoreDataCompleteSaved int null,PaymentStage text null,flgLocationTrackEnabled int null," +
            "flgStoreVisitMode int null,Sstat int null,StoreAddress text null,SOAccuracy text null,flgRemap int null,flgLocationServicesOnOff int null,flgGPSOnOff int null,flgNetworkOnOff int null,flgFusedOnOff int null,flgInternetOnOffWhileLocationTracking int null,flgRestart int null,flgStoreOrder int null,MapAddress text null,MapCity text null,MapPinCode text null,MapState text null,CityId text null,StateId text null,flgSelfStoreNode int null);";
    public static final String DATABASE_CREATE_TABLE_tblPreAddedStoresDataDetails = "create table tblPreAddedStoresDataDetails (StoreIDDB text not null,GrpQuestID text not null,QstId text not null,AnsControlTypeID text not null,AnsTextVal text null,flgPrvVal text null);";



    //actual visit stock
    public static final String DATABASE_TABLE_tblActualVisitStock = "tblActualVisitStock";
    public static final String DATABASE_CREATE_TABLE_tblActualVisitStock = "create table tblActualVisitStock (storeID text null,ProductID text null,Stock text null,Sstat integer null,flgNetorkTimeRecorded int null, device_time text null);";

    public static final String DATABASE_TABLE_tblProductListLastVisitStockOrOrderMstr= "tblProductListLastVisitStockOrOrderMstr";
    public static final String DATABASE_CREATE_TABLEtblProductListLastVisitStockOrOrderMstr = "create table tblProductListLastVisitStockOrOrderMstr (StoreID text not null,PrdID text not null);";

    public static final String TABLE_tblAttandanceDetails="tblAttandanceDetails";
    public static final String DATABASE_CREATE_TABLE_tblAttandanceDetails="create table tblAttandanceDetails(AttandanceTime text null," +
            "PersonNodeID text null, PersonNodeType text null," +
            "OptionID text null,OptionDesc text null,ReasonID text null,ReasonDesc text null,Comment text null, Address text null,PinCode text null, City text null, State text null," +
            "fnLati text null,fnLongi text null,fnAccuracy text null," +
            "flgLocNotFound text null,fnAccurateProvider text null,AllProvidersLocation text null,fnAddress text null," +
            "GpsLat text null, GpsLong text null, GpsAccuracy text null, GpsAddress text null, NetwLat text null, " +
            "NetwLong text null, NetwAccuracy text null, NetwAddress text null, FusedLat text null, FusedLong text null, " +
            "FusedAccuracy text null, FusedAddress text null,FusedLocationLatitudeWithFirstAttempt text null," +
            "FusedLocationLongitudeWithFirstAttempt text null,FusedLocationAccuracyWithFirstAttempt text null," +
            "Sstat int nutblDistributorSavedDatall,flgLocationServicesOnOff int null,flgGPSOnOff int null,flgNetworkOnOff int null," +
            "flgFusedOnOff int null,flgInternetOnOffWhileLocationTracking int null,flgRestart int null," +
            "MapAddress text null,MapCity text null,MapPinCode text null,MapState text null,CityId text null,StateId text null,DistributorId text null,DistributorNodeType text null,DistributorName text null,flgNetorkTimeRecorded int null, device_time text null);";

    public static final String DATABASE_TABLE_tblStoreOrderVisitDayActivity="tblStoreOrderVisitDayActivity";
    public static final String DATABASE_CREATE_tblStoreOrderVisitDayActivity="create table tblStoreOrderVisitDayActivity(IMEINumber text null,StoreVisitCode text null,StoreID text not null, TempStoreVisitCode text null, ForDate string not null, Sstat integer not null,VisitTimeStartAtStore text null,VisitTimeEndStore text null,flgNetorkTimeRecorded int null, device_time text null);";


    public static final String DATABASE_TABLE_INVOICE_DETAILS = "tblInvoiceDetails";//
    public static final String DATABASE_TABLE_MAIN32 = "tblTmpInvoiceHeader";//
    public static final String DATABASE_TABLE_MAIN210 = "tblTmpInvoiceDetails";//DATABASE_TABLE_TMPINVOICE_DETAILS


    public static final String  DATABASE_TABLE_INVOICE_HEADER = "tblInvoiceHeader";//DATABASE_TABLE_MAIN32,DATABASE_CREATE_TABLE_32
    public static final String DATABASE_CREATE_TABLE_INVOICE_HEADER = "create table tblInvoiceHeader (StoreVisitCode text not null,InvoiceNumber int not null,TmpInvoiceCodePDA text null, StoreID text not null, InvoiceDate string not null, TotalBeforeTaxDis real not null, TaxAmt real not null, TotalDis real not null, InvoiceVal real not null, FreeTotal integer not null, Sstat integer not null, InvAfterDis real not null, AddDis real not null,  NoCoupon int null, TotalCoupunAmount real null,TransDate string not null,FlgInvoiceType text not null,flgWholeSellApplicable int null,flgProcessedInvoice int  null,CycleID  int not null,flgDrctslsIndrctSls int null,RouteNodeID text null,RouteNodetype text null);";


    public static final String DATABASE_TABLE_STOREVISIT="tblStoreVisitMstr";
    public static final String DATABASE_CREATE_TABLE_STOREVISIT="create table tblStoreVisitMstr(IMEINumber text null,StoreVisitCode text null,StoreID text not null, " +
            "Sstat integer not null, ForDate string not null, ActualLatitude text null, ActualLongitude text null,VisitTimeOutSideStore text null," +
            "VisitTimeInSideStore text null,VisitTimeCheckStore text null, VisitEndTS text null, LocProvider text null, Accuracy text null,BateryLeftStatus text null," +
            "StoreClose integer null,StoreNextDay integer null,ISNewStore int null,IsNewStoreDataCompleteSaved int null,flgFromWhereSubmitStatus int null," +
            "flgSubmitFromQuotation int null,flgLocationServicesOnOff int null,flgGPSOnOff int null,flgNetworkOnOff int null,flgFusedOnOff int null," +
            "flgInternetOnOffWhileLocationTracking int null,flgStoreOrder int null,flgRetailerCreditBalnce integer null,VisitTypeStatus text null," +
            "flgVisitCollectionMarkedStatus int null,SelfCreditNote real null, flgNetorkTimeRecorded int null, device_time text null,JointVisitId text null," +
            "flgJointVisit int null,MapAddress text null,MapCity text null,MapPinCode text null,MapState text null,RouteNodeID text null,RouteNodeType text null);";

   /* public static final String DATABASE_CREATE_TABLE_STOREVISIT="create table tblStoreVisitMstr(IMEINumber text null,StoreVisitCode text null,StoreID text not null," +
            " Sstat integer not null, ForDate string not null, ActualLatitude text null, ActualLongitude text null,VisitTimeOutSideStore text null," +
            "VisitTimeInSideStore text null,VisitTimeCheckStore text null, VisitEndTS text null, LocProvider text null, Accuracy text null,BateryLeftStatus text null," +
            "StoreClose integer null,StoreNextDay integer null,ISNewStore int null," +
            "IsNewStoreDataCompleteSaved int null,flgFromWhereSubmitStatus int null,flgSubmitFromQuotation int null,flgLocationServicesOnOff int null,flgGPSOnOff int null," +
            "flgNetworkOnOff int null,flgFusedOnOff int null,flgInternetOnOffWhileLocationTracking int null,flgStoreOrder int null,flgRetailerCreditBalnce integer null," +
            "VisitTypeStatus text null,flgVisitCollectionMarkedStatus int null," +
            "SelfCreditNote real null,MapAddress text null,MapCity text null,MapPinCode text null,MapState text null,RouteNodeID text null,RouteNodeType text null);";*/

    public static final String  DATABASE_TABLE_DayCheckIn = "tblDayCheckIn";//DATABASE_TABLE_MAIN32,DATABASE_CREATE_TABLE_32
    public static final String DATABASE_CREATE_TABLE_DayCheckIn = "create table tblDayCheckIn (DateOfDayCheckIn text not null,flgDayDayCheckIn int not null);";
    public static final String DATABASE_TABLE_NewAddedStoreLocationDetails="tblNewAddedStoreLocationDetails";
    public static final String DATABASE_CREATE_TABLE_NewAddedStoreLocationDetails="create table tblNewAddedStoreLocationDetails(StoreID text not null, VisitEndTS text null, LocProvider text null, Accuracy text null, BateryLeftStatus text null,flgLocationServicesOnOff int null,flgGPSOnOff int null,flgNetworkOnOff int null,flgFusedOnOff int null,flgInternetOnOffWhileLocationTracking int null, Sstat integer not null,flgNetorkTimeRecorded int null, device_time text null );";
    //
    private static final String DATABASE_CREATE_TABLE_INVOICE_DETAILS = "create table tblInvoiceDetails (InvoiceNumber int not null,TmpInvoiceCodePDA text null,StoreID text not null,CatID text  null,ProdID text not null,ProductPrice real null, TaxRate real null,flgRuleTaxVal integer null,OrderQty integer not null,UOMId integer not null,LineValBfrTxAftrDscnt real not null,LineValAftrTxAftrDscnt real not null,FreeQty integer not null,DisVal real not null,Sstat integer not null,SampleQuantity int null,ProductShortName text null,TaxValue real null,OrderIDPDA text null,flgIsQuoteRateApplied int null,ServingDBRId text null,flgWholeSellApplicable int null,ProductExtraOrder int null,flgDrctslsIndrctSls int null);";
    private static final String DATABASE_CREATE_TABLE_210 = "create table tblTmpInvoiceDetails (TmpInvoiceCodePDA text null,StoreID text not null,CatID text  null,ProdID text not null,ProductPrice real null, TaxRate real null,flgRuleTaxVal integer null,OrderQty integer not null,UOMId integer not null,LineValBfrTxAftrDscnt real not null,LineValAftrTxAftrDscnt real not null,FreeQty integer not null,DisVal real not null,Sstat integer not null,SampleQuantity int null,ProductShortName text null,TaxValue real null,OrderIDPDA text null,flgIsQuoteRateApplied int null,ServingDBRId text null,flgWholeSellApplicable int null,ProductExtraOrder int null);";
    public static final String DATABASE_CREATE_TABLE_32 = "create table tblTmpInvoiceHeader (StoreVisitCode text not null,TmpInvoiceCodePDA text null, StoreID text not null, InvoiceDate string not null, TotalBeforeTaxDis real not null, TaxAmt real not null, TotalDis real not null, InvoiceVal real not null, FreeTotal integer not null, Sstat integer not null, InvAfterDis real not null, AddDis real not null,  NoCoupon int null, TotalCoupunAmount real null,TransDate string not null,FlgInvoiceType text not null,flgWholeSellApplicable int null,flgTransferStatus int not null,RouteNodeID text null,RouteNodetype text null);";
    public static final String DATABASE_TABLE_tblInvoiceCaption = "tblInvoiceCaption";
    public static final String DATABASE_CREATE_TABLE_tblInvoiceCaption = "create table tblInvoiceCaption(INVPrefix text null,VanIntialInvoiceIds int not null,INVSuffix text null);";
    public static final String DATABASE_TABLE_tblPriceApplyType = "tblPriceApplyType";
    public static final String DATABASE_CREATE_TABLE_tblPriceApplyType =  "create table tblPriceApplyType(DiscountLevelType int not null,cutoffvalue real not null);";
    public static final String DATABASE_TABLE_tblLastOutstanding = "tblLastOutstanding";
    public static final String DATABASE_CREATE_TABLE_tblLastOutstanding = "create table tblLastOutstanding(StoreID text not null,Outstanding real not null,AmtOverdue real not null);";
    public static final String DATABASE_TABLE_tblInvoiceLastVisitDetails = "tblInvoiceLastVisitDetails";
    public static final String DATABASE_CREATE_TABLE_tblInvoiceLastVisitDetails = "create table tblInvoiceLastVisitDetails(StoreID text not null,InvCode text null,InvDate text null,OutstandingAmt text not null,AmtOverdue text null);";
    //setock out flg by Sunil, we are using below table
    public static final String DATABASE_TABLE_tblStockUploadedStatus="tblStockUploadedStatus";
    public static final String DATABASE_CREATE_TABLE_tblStockUploadedStatus="create table tblStockUploadedStatus(flgStockTrans int null,VanLoadUnLoadCycID int null,CycleTime text null,StatusID int null,flgDayEnd int null);";
    public static final String DATABASE_TABLE_TMP_DISTRIBUTOR_STOCK = "tblTmpDistributorStock";
    public static final String DATABASE_CREATE_TABLE_TEMP_DISTRIBUTOR_STOCK="create table tblTMPDistributorStock(PrdctId text not null,StockQntty text not null,DistributorNodeIdNodeType text not null,SKUName text not null,OpeningStock text not null,TodaysAddedStock text not null,CycleAddedStock text not null,NetSalesQty text not null,TodaysUnloadStk text not null,CycleUnloadStk text not null,CategoryID text not null);";
    public static final String DATABASE_TABLE_tblStockConfirm="tblStockConfirm";
    public static final String DATABASE_CREATE_TABLE_tblStockConfirm="create table tblStockConfirm(UserId text null,confirmFlg text null);";
    //setock out flg not using below tavle
    public static final String DATABASE_TABLE_tblStockOut="tblDistributorStockOutFlg";
    public static final String DATABASE_CREATE_TABLE_tblStockOut="create table tblDistributorStockOutFlg(flgStockOutEntryDone int not null);";
    public static final String DATABASE_TABLE_tblBankMaster="tblBankMaster";
    public static final String DATABASE_TABLE_tblInstrumentMaster="tblInstrumentMaster";


    public static final String TABLE_tblStateCityMaster="tblStateCityMaster";
    public static final String DATABASE_CREATE_TABLE_tblStateCityMaster="create table tblStateCityMaster(StateID int not null, State text not null,CityID int not null,City text not null,CityDefault integer null);";


    //Amount Collction
    public static final String DATABASE_TABLE_tblAllCollectionData="tblAllCollectionData";
    //Amount Collection
    public static final String DATABASE_CREATE_TABLE_tblBankMaster="create table tblBankMaster (BankId int not null, BankName text not null);";
    public static final String DATABASE_CREATE_TABLE_tblInstrumentMaster="create table tblInstrumentMaster (InstrumentModeId text not null, InstrumentMode text not null, InstrumentType text not null);";
    public static final String DATABASE_CREATE_TABLE_tblAllCollectionData="create table tblAllCollectionData (StoreVisitCode text not null,StoreID text not null, PaymentMode text null,PaymentModeID text null, Amount text null, RefNoChequeNoTrnNo text null, Date text null, Bank text null,Sstat text null,TmpInvoiceCodePDA text null,CollectionCode text null,flgNetorkTimeRecorded int null, device_time text null);";
    //map distributor
    public static final String TABLE_tblSupplierMstrList = "tblSupplierMstrList";
    //public static final String DATABASE_CREATE_TABLE_tblAllCollectionData="create table tblAllCollectionData (StoreID text null, PaymentMode text null, Amount text null, RefNoChequeNoTrnNo text null, Date text null, Bank text null,Sstat text null,OrderPDAID text null);";
    public static final String DATABASE_CREATE_TABLE_tblSupplierMstrList =  "create table tblSupplierMstrList(NodeID int not null,NodeType int not null,Descr text not null,latCode real null,LongCode real null,flgMapped int not null,Address text null,State text null,City text null,PinCode text null,ContactNumber text null,TaxNumber text null,flgStockManage int not null,EmailID text null,flgDefault int not null);";
    public static final String TABLE_tblIsDBRStockSubmitted = "tblIsDBRStockSubmitted";
    public static final String DATABASE_CREATE_TABLE_tblIsDBRStockSubmitted = "create table tblIsDBRStockSubmitted (IsDBRStockSubmitted int null);";
    public static final String TABLE_tblSuplierMapping="tblSuplierMapping";
    public static final String DATABASE_CREATE_TABLE_tblSuplierMapping="create table tblSuplierMapping(" +
            "SuplierId text null,SuplierNodeType text null,flgGSTCapture text null," +
            "flgGSTCompliance text null,GSTNumber text null, Address text null,PinCode text null, City text null, State text null," +
            "fnLati text null,fnLongi text null,fnAccuracy text null," +
            "flgLocNotFound text null,fnAccurateProvider text null,AllProvidersLocation text null,fnAddress text null," +
            "GpsLat text null, GpsLong text null, GpsAccuracy text null, GpsAddress text null, NetwLat text null, " +
            "NetwLong text null, NetwAccuracy text null, NetwAddress text null, FusedLat text null, FusedLong text null, " +
            "FusedAccuracy text null, FusedAddress text null,FusedLocationLatitudeWithFirstAttempt text null," +
            "FusedLocationLongitudeWithFirstAttempt text null,FusedLocationAccuracyWithFirstAttempt text null,Sstat int null,flgLocationServicesOnOff int null,flgGPSOnOff int null,flgNetworkOnOff int null,flgFusedOnOff int null,flgInternetOnOffWhileLocationTracking int null,flgRestart int null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_DISTRIBUTOR_ORDERPDAID = "tblDistributorOrderPdaId";
    public static final String DATABASE_TABLE_DISTRIBUTOR_STOCK = "tblVanProductStock";
    public static final String DATABASE_TABLE_DISTRIBUTOR_VanProductAdjStock = "tblVanProductAdjStock";

    public static final String DATABASE_TABLE_DISTRIBUTOR_PRODUCT_STOCK = "tblDistributorProductStock";
    public static final String DATABASE_TABLE_DISTRIBUTOR_ORDERLEFT = "tblDistributorIDOrderIDLeft";

    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_PRODUCT_STOCK="create table tblDistributorProductStock(DistributorNodeIdNodeType text null,ProductNodeID int null,ProductNodeType int null,SKUName text null,StockDate text null,StockQty int null,OriginalStock int null);";
    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_ORDERLEFT="create table tblDistributorIDOrderIDLeft(DistributorNodeIdNodeType text null,OrderId text null,flgProcessedInvoice int null);";


    public static final String DATABASE_TABLE_DISTRIBUTOR_LEFTPRODUCT = "tblVanIDOrderIDLeft";
    public static final String DATABASE_TABLE_DayAndSummary = "tblAllSummaryDay";
    public static final String DATABASE_CREATE_TABLE_DayAndSummary = "create table tblAllSummaryDay (AutoId int not null,Measures text null," +
            "TodaysSummary text null,MTDSummary text null,TableNo int not null,ColorCode text not null);";
    public static final String DATABASE_TABLE_Main271 = "tblLatLongDetails";
    public static final String DATABASE_CREATE_TABLE_271 = "create table tblLatLongDetails (StoreID text null,fnLati text null,fnLongi text null,fnAccuracy text null,flgLocNotFound text null,fnAccurateProvider text null,AllProvidersLocation text null,fnAddress text null,GpsLat text null, GpsLong text null, GpsAccuracy text null, GpsAddress text null, NetwLat text null, NetwLong text null, NetwAccuracy text null, NetwAddress text null, FusedLat text null, FusedLong text null, FusedAccuracy text null, FusedAddress text null,FusedLocationLatitudeWithFirstAttempt text null,FusedLocationLongitudeWithFirstAttempt text null,FusedLocationAccuracyWithFirstAttempt text null,Sstat integer null);";
    public static final String DATABASE_TABLE_tblSameLocationForStoreRestartDone = "tblsameLocationForStoreRestartDone";
    public static final String DATABASE_CREATE_TABLE_tblSameLocationForStoreRestartDone="create table tblsameLocationForStoreRestartDone(UniqueID INTEGER PRIMARY KEY AUTOINCREMENT,prvsStoreID text null,CrntStoreID text null,isSavedOrSubmittedStore text null,isMsgToRestartPopUpShown text null,isRestartDoneByDSR text null ,Sstat integer null,ActionTime text null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_MAIN23 = "tblStoreWiseTarget";
    public static final String DATABASE_TABLE_MAIN235 = "tblTargetVsAchievedSummary";
    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_ORDERPDAID="create table tblDistributorOrderPdaId(DistributorNodeIdNodeType text null,OrderPDAID text null,ProductId text null,OrderQntty text null,Sstat integer not null);";
    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_STOCK="create table tblVanProductStock(PrdctId text not null,StockQntty text not null,DistributorNodeIdNodeType text not null,SKUName text not null,OpeningStock text not null,TodaysAddedStock text not null,CycleAddedStock text not null,NetSalesQty text not null,TodaysUnloadStk text not null,CycleUnloadStk text not null,CategoryID text not null);";
    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_tblVanProductAdjStock="create table tblVanProductAdjStock(PrdctId text not null,AdjStck text not null,DistributorNodeIdNodeType text not null,CycleID text not null,LastUpdatedTime text not null);";

    public static final String DATABASE_TABLE_CYCLEID="tblCycleID";
    public static final String DATABASE_CREATE_TABLE_CYCLEID="create table tblCycleID(CycleID int not null,Sstat int not null,CycStartTime text not null,CycleTime text not null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_CREATE_TABLE_DISTRIBUTOR_LEFTPRODUCT="create table tblVanIDOrderIDLeft(DistributorNodeIdNodeType text not null,OrderId text not null,flgProcessedInvoice int not null);";
    public static final String DATABASE_CREATE_TABLE_23 = "create table tblStoreWiseTarget (StoreID text not null,TargetValue text null);";
    public static final String DATABASE_CREATE_TABLE_235 = "create table tblTargetVsAchievedSummary (AutoId int not null,Descr text null," +
            "TodayTarget int null,TodayAchieved int null,TodayBal real null,Todayflg int null,MonthTarget real null,MonthAchieved real null,MonthBal real null,Monthflg int null,ValTgtOrPrdctFlg int not null);";
    public static final String DATABASE_TABLE_MAIN236 = "tblTargetVsAchievedNote";
    public static final String DATABASE_CREATE_TABLE_236 = "create table tblTargetVsAchievedNote (MsgToDisplay text null);";
    // Tables Data Coming at Splash Screen Starts
    public static final String TABLE_tblUserAuthenticationMstr_Define = "tblUserAuthenticationMstr";
    public static final String TABLE_tblUserAuthenticationMstr_Definition = "create table tblUserAuthenticationMstr (flgUserAuthenticated text not null,PersonName  text not null,PersonNodeID integer not null,PersonNodeType integer not null,FlgRegistered text not null,flgPersonTodaysAtt text null,ContactNo text null,DOB text null,SelfieName text null,SelfieNameURL text null,SalesAreaName text null,CoverageNodeId int not null,CoverageNodeType int not null,WorkingType int not null,flgToShowAllRoutesData int not null,flgDrctslsIndrctSls int null,flgNetorkTimeRecorded int null, device_time text null, emailID TEXT null);";

    // public static final String TABLE_tblUserAuthenticationMstr_Definition = "create table tblUserAuthenticationMstr (flgUserAuthenticated text null,PersonName text null,FlgRegistered text null,PersonNodeID text null,PersonNodeType text null,flgPersonTodaysAtt text null);";
    public static final String TABLE_tblBloodGroup_Define = "tblBloodGroup";
    public static final String DATABASE_CREATE_TABLE_tblBloodGroup = "create table tblBloodGroup (BloddGroups text not null);";
    public static final String TABLE_tblEducationQuali = "tblEducationQuali";
    public static final String DATABASE_CREATE_TABLE_tblEducationQuali = "create table tblEducationQuali (Qualification text not null);";
    public static final String TABLE_tblDsrRegDetails = "tblDsrRegDetails";
    public static final String DATABASE_CREATE_TABLE_tblDsrRegDetails = "create table tblDsrRegDetails (IMEI text null,ClickedDateTime text null,FirstName text null,LastName text null,ContactNo text null,DOB text null,Sex text null,MaritalStatus text null,MarriedDate text null,Qualification text null,SelfieName text null,SelfiePath text null,EmailID text null,BloodGroup text null,SignName text null,SignPath text null,Sstat integer null,PhotoName text null,PersonNodeId text null,PersonNodeType text null);";//,flgNetorkTimeRecorded int null,device_time text null
    public static final String TABLE_tblUserRegistarationStatus = "tblUserRegistarationStatus";
    public static final String DATABASE_CREATE_TABLE_tblUserRegistarationStatus = "create table tblUserRegistarationStatus (Flag text null,MsgToDisplay text null);";
    public static final String TABLE_tblAvailableVersionMstr_Define = "tblAvailableVersion";
    public static final String TABLE_tblAvailableVersionMstr_Definition = "create table tblAvailableVersion (VersionID text not null,VersionSerialNo text not null,VersionDownloadStatus text not null,ServerDate text not null);";

    public static final String TABLE_tblRouteListMaster_Define = "tblRouteListMaster";
    public static final String TABLE_tblRouteListMaster_Definition = "create table tblRouteListMaster(ID string not null,RouteType string not null, Descr string not null, Active integer null,flgTodayRoute integer null,RouteDate string null,CoverageAreaNodeID text null,CoverageAreaNodeType text null);";
    public static final String TABLE_tblPDANotificationMaster_Define = "tblPDANotificationMaster";
    public static final String TABLE_tblPDANotificationMaster_Definition = "create table tblPDANotificationMaster (SerialNo int not null,IMEI text not null, Noti_text text not null,Noti_DateTime text null,Noti_ReadStatus int null,Noti_NewOld int null,Noti_ReadDateTime text null,Sstat int null,MsgServerID int not null);";
    public static final String TABLE_tblDayStartAttendanceOptions_Define = "tblDayStartAttendanceOptions";
    public static final String TABLE_tblDayStartAttendanceOptions_Definition = "create table tblDayStartAttendanceOptions(AutoIdStore int not null,ReasonId text not null,ReasonDescr text not null,FlgToShowTextBox integer null,flgSOApplicable int null,flgDSRApplicable int null,flgNoVisitOption int null,SeqNo int null,flgDelayedReason int null,flgMarketVisit int not null);";

    // public static final String TABLE_tblDayStartAttendanceOptions_Definition = "create table tblDayStartAttendanceOptions(AutoIdStore integer null,ReasonId text null,ReasonDescr text null,FlgToShowTextBox integer null);";

    public static final String TABLE_tblNoVisitStoreDetails_Define = "tblNoVisitStoreDetails";
    public static final String TABLE_tblNoVisitStoreDetails_Definition = "create table tblNoVisitStoreDetails(IMEI text null,CurDate text null,ReasonId text null,ReasonDescr text null,flgHasVisit integer null,Sstat integer not null,flgNetorkTimeRecorded int null, device_time text null);";
    //new Store master data Start
    public static final String DATABASE_TABLE_NewStoreSalesQuotePaymentDetails="tblNewStoreSalesQuotePaymentDetails";
    public static final String DATABASE_NewStoreSalesQuotePaymentDetails = "create table tblNewStoreSalesQuotePaymentDetails (StoreId text null,PymtStageId text null,Sstat text null);";
    public static final String TABLE_QSTOUTCHANNEL = "tblQuestIDForOutChannel"; // show how many section we have to show
    public static final String DATABASE_CREATE_TABLE_QSTOUTCHANNEL = "create table tblQuestIDForOutChannel(GrpQstId int not null,QuestID int not null,OptID text not null,SectionCount int not null);";
    public static final String TABLE_QST_NAME = "tblQuestIDForName";
    public static final String DATABASE_CREATE_TABLE_QST_NAME = "create table tblQuestIDForName(ID int not null,GrpQstId int not null,QuestID int not null,QuestDesc text not null);";
    public static final String TABLE_QuestionMstr = "tblGetPDAQuestMstr";
    public static final String DATABASE_CREATE_TABLE_QUESTIONMstr = "create table tblGetPDAQuestMstr(QuestID int not null,QuestCode int null,QuestDesc text not null,QuestType int not null,AnsControlType int not null,AnsControlInputTypeID int not null,AnsControlInputTypeMinLength int null,AnsControlInputTypeMaxLength int null,AnsMustRequiredFlg int not null,QuestBundleFlg int null,ApplicationTypeID int not null,Sequence int not null,AnswerHint text null,flgQuestIDForOutChannel int  null,flgStoreVisitFeedback int null,flgDSMOverAllFeedback int null,flgDSMVisitFeedback int null,flgNewStore int null,flgStoreValidation int null);";
    public static final String TABLE_QuestGrpMappingMstr = "tblGetPDAQuestGrpMapping";
    public static final String DATABASE_CREATE_TABLE_QuestGrpMappingMstr = "create table tblGetPDAQuestGrpMapping(GrpQuestID int not null,QuestID int not null,GrpID int not null,GrpNodeID int not null,GrpDesc text not null,SectionNo int not null,GrpCopyID int null,QuestCopyID int null,Sequence int null,flgNewStore int null,flgStoreValidation int null);";
    public static final String TABLE_OptionMstr = "tblGetPDAQuestOptionMstr";
    public static final String DATABASE_CREATE_TABLE_OPTIONMstr = "create table tblGetPDAQuestOptionMstr(OptID text not null,QuestID int not null,OptionDescr text not null,Sequence int null );";
    public static final String TABLE_QuestOptionDependentMstr = "tblPDAQuestOptionDependentMstr";
    public static final String DATABASE_CREATE_TABLE_QUESTION_OPTION_DEPENDENTMstr = "create table tblPDAQuestOptionDependentMstr(QstID int not null,DepQstId int not null,GrpQuestID int not null,GrpDepQuestID int not null);";
    public static final String TABLE_QuestOptionValuesDependentMstr = "tblPDAQuestOptionValuesDependentMstr";
    public static final String DATABASE_CREATE_TABLE_QUESTION_OPTION_VAL_DEPENDENTMstr = "create table tblPDAQuestOptionValuesDependentMstr(DepQstId int not null,DepAnswValId text not null,QstId int not null,AnswValId text not null,OptDescr text not null,Sequence int null,GrpQuestID int not null,GrpDepQuestID int not null);";

   /* public static final String DATABASE_TABLE_tblLocationDetails = "tblLocationDetails";
    public static final String DATABASE_CREATE_TABLE_tblLocationDetails = "create table tblLocationDetails (Lattitude text null,Longitude text null,Accuracy text null,Address text null,City text null,Pincode text null,State text null,fnAccurateProvider  text null,GpsLat  text null,GpsLong  text null,GpsAccuracy  text null,NetwLat  text null,NetwLong  text null,NetwAccuracy  text null,FusedLat  text null,FusedLong  text null,FusedAccuracy  text null,AllProvidersLocation  text null,GpsAddress  text null,NetwAddress  text null,FusedAddress  text null,FusedLocationLatitudeWithFirstAttempt  text null,FusedLocationLongitudeWithFirstAttempt  text null,FusedLocationAccuracyWithFirstAttempt  text null);";
*/


    public static final String DATABASE_TABLE_tblLocationDetails = "tblLocationDetails";
    public static final String DATABASE_CREATE_TABLE_tblLocationDetails = "create table tblLocationDetails (StoreID text null,DSRId text null,DSRNodeType text null,PersonNodeID text null,PersonNodeType text null,DateTime text null,Lattitude text null,Longitude text null,Accuracy text null,Address text null,City text null,Pincode text null,State text null,fnAccurateProvider  text null,GpsLat  text null,GpsLong  text null,GpsAccuracy  text null,NetwLat  text null,NetwLong  text null,NetwAccuracy  text null,FusedLat  text null,FusedLong  text null,FusedAccuracy  text null,AllProvidersLocation  text null,GpsAddress  text null,NetwAddress  text null,FusedAddress  text null,FusedLocationLatitudeWithFirstAttempt  text null,FusedLocationLongitudeWithFirstAttempt  text null,FusedLocationAccuracyWithFirstAttempt  text null,flgActivityType int null,StoreVisitCode text null,JointVisitID,flgJointVisitLevel int null,flgLocationCaptureLevel int null,IsNewStore int null,Sstat int null,RouteNodeID int null,RouteNodeType int null);";


    public static final String TABLE_IMAGE = "tableImage";
    public static final String DATABASE_CREATE_TABLE_Image = "create table tableImage(StoreID text null,QstIdAnsCntrlTyp text null,imageName text null,imagePath text null,ImageClicktime text null,Sstat integer null,flgNetorkTimeRecorded int null, device_time text null);";


    public static final String TABLE_OutletQuestAnsMstr = "tblOutletQuestAnsMstr";
    public static final String DATABASE_CREATE_TABLE_tblOutletQuestAnsMstr = "create table tblOutletQuestAnsMstr (OutletID text not null,QuestID text not null,AnswerType text null, AnswerValue text null,QuestionGroupID integer null,sectionID integer null,Sstat integer not null);";




    // Tables Data Coming at Splash Screen Ends



    public static final String DATABASE_TABLE_StoreSalesOrderPaymentDetails="tblStoreSalesOrderPaymentDetails";
    public static final String DATABASE_TABLE_StoreAddressMapDetailsMstr="tblStoreAddressMapDetailsMstr";
    public static final String DATABASE_TABLE_StoreOrderBillAddressDetails="tblStoreOrderBillAddressDetails";
    public static final String TABLE_XMLFILES = "tbl_XMLfiles";


    // End Surbhi Change for Incentive
    public static final String DATABASE_CREATE_TABLE_XMLfiles = "create table tbl_XMLfiles(XmlFileName text null,Sstat text null,filetype text null);";
    public static final String TABLE_RETURN_REASON = "tblGetReturnsReasonForPDAMstr";
    public static final String TABLE_OutletChannelBusinessSegmentMaster = "tblOutletChannelBusinessSegmentMaster";
    public static final String TABLE_OutletMstr = "tblOutletMstr";
    public static final String TABLE_ViewOutletQuestAnsMstr = "tblViewOutletQuestAnsMstr";


    // static final String TABLE_XMLFILES = "tbl_XMLfiles";
    //public static final String DATABASE_CREATE_TABLE_XMLfiles = "create table tbl_XMLfiles(XmlFileName text null,Sstat text null);";
    public static final String TABLE_ViewOutletNameAndId = "tblViewOutletNameAndId";
    // Dynamic Store Mapping Start
    public static final String TABLE_StoreProductPhotoDetail = "tblOutletPhotoDetail";
    public static final String TABLE_QuestionDependentMstr = "tblGetPDAQuestionDependentMstr";
    public static final String TABLE_StoreSomeProdQuotePriceMstr = "tblStoreSomeProdQuotePriceMstr";
    public static final String DATABASE_CREATE_TABLE_OutletPhotoDetail = "create table tblOutletPhotoDetail(OutletID text null,ClickedDateTime text null,PhotoName text null,PhotoComment text null,PDAPhotoPath text null,Sstat integer null);";
    public static final String DATABASE_CREATE_TABLE_OutletChannelBusinessSegmentMasterr = "create table tblOutletChannelBusinessSegmentMaster(OutChannelID int null,ChannelName text null,BusinessSegmentID int null,BusinessSegment text null);";
    public static final String DATABASE_CREATE_TABLE_tblOutletMstr = "create table tblOutletMstr (OutletID text not null,VisitStartTS text not null,VisitEndTS text null,AppVersion int null,ActualLatitude text null, ActualLongitude text null, LocProvider text null, Accuracy text null, BateryLeftStatus text null,StoreName text null,imei text null, ISNewStore int null,Sstat integer not null);";
    public static final String DATABASE_CREATE_TABLE_tblViewOutletQuestAnsMstr = "create table tblViewOutletQuestAnsMstr (OutletID text not null,QuestID text not null,AnswerType text null, AnswerValue text null);";
    public static final String DATABASE_CREATE_TABLE_ViewOutletNameAndId = "create table tblViewOutletNameAndId(OutletID text not null,OutletName text not null);";
    public static final String DATABASE_CREATE_TABLE_QUESTION_DEPENDENTMstr = "create table tblGetPDAQuestionDependentMstr(QuestionID int not null,OptionID int not null,DependentQuestionID int not null,GrpID int not null,GrpDepQuestID int not null);";
    public static final String DATABASE_StoreSalesOrderPaymentDetails = "create table tblStoreSalesOrderPaymentDetails (StoreId text null,OrderID text null,PymtStageId text null,Sstat text null,TmpInvoiceCodePDA text null);";
    public static final String DATABASE_StoreAddressMapDetailsMstr = "create table tblStoreAddressMapDetailsMstr (StoreID text not null,OutAddTypeID int not null,Address text not null,AddressDet text null,OutAddID int not null);";
    public static final String DATABASE_StoreOrderBillAddressDetails = "create table tblStoreOrderBillAddressDetails (StoreID text null,OrderID text null,BillToAddress text null,ShipToAddress int null,Sstat text null);";
    public static final String DATABASE_StoreSomeProdQuotePriceMstr = "create table tblStoreSomeProdQuotePriceMstr (PrdId text not null,StoreID text not null,QPBT text not null,QPAT text not null,QPTaxAmount text not null,MinDlvrQty int null,UOMID text null,Sstat text null);";
    public static final String TABLE_tblSalesQuoteSponsorMstr = "tblSalesQuoteSponsorMstr";
    public static final String TABLE_tblManufacturerMstrMain = "tblManufacturerMstrMain";
    public static final String TABLE_tblRateDistribution = "tblRateDistribution";
    //nitika
    public static final String DATABASE_CREATE_TABLE_tblSalesQuoteSponsorMstr = "create table tblSalesQuoteSponsorMstr(SalesQuoteSponsorID text null,SponsorDescr text null,Ordr text null);";
    public static final String DATABASE_CREATE_TABLE_tblManufacturerMstrMain = "create table tblManufacturerMstrMain(ManufacturerID text null,ManufacturerName text null,NodeType text null);";
    public static final String DATABASE_CREATE_TABLE_tblRateDistribution = "create table tblRateDistribution(SalesQuoteId text null,StoreId text null,SalesQuoteSponsorID  text null,ManufacturerID  text null,Percentage  text null,SponsorDescr  text null,ManufacturerName  text null,Sstat text null);";
    public static final String DATABASE_TABLE_Main214 = "tblAlrtVal";
    public static final String DATABASE_TABLE_Main215 = "tblProductMappedWithSchemeSlabApplied";
    // Tables for Launcher Screen
    public static final String DATABASE_TABLE_MAIN11 = "tblPdaDate";
    public static final String DATABASE_TABLE_MAIN12 = "tblDayStartEndDetails";

    //  public static final String TABLE_StoreSomeProdQuotePriceMstr = "tblStoreSomeProdQuotePriceMstr";
    // Dynamic Store Mapping End
    public static final String IS_NETWORK_TIME_RECORDED = "flgNetorkTimeRecorded";
    public static final String DATE_TIME = "datetime";
    public static final String DATABASE_TABLE_MAIN13 = "tblStoreList";
    public static final String DATABASE_TABLE_MAIN14 = "tblProductListMaster";
    public static final String DATABASE_TABLE_MAINProductSegementMap = "tblProductSegementMap";
    public static final String DATABASE_TABLE_MAIN15 = "tblCategoryMaster";
    public static final String DATABASE_TABLE_MAIN19 = "tblPDAProductReturnMstr";
    public static final String DATABASE_TABLE_MAIN20 = "tblPDAProductReturnDetails";
    public static final String DATABASE_TABLE_MAIN21 = "tblNewStoreEntries";
    public static final String DATABASE_TABLE_MAIN22 = "tblTemp";
    public static final String DATABASE_TABLE_MAIN201 = "tblSchemeStoreMapping";
    public static final String DATABASE_TABLE_MAIN202 = "tblSchemeMstr";
    public static final String DATABASE_TABLE_MAIN203 = "tblSchemeSlabDetail";
    public static final String DATABASE_TABLE_MAIN204 = "tblSchemeSlabBucketDetails";
    public static final String DATABASE_TABLE_MAIN205 = "tblSchemeSlabBucketProductMapping";
    public static final String DATABASE_TABLE_MAIN206 = "tblSchemeSlabBenefitsBucketDetails";
    public static final String DATABASE_TABLE_MAIN207 = "tblSchemeSlabBenefitsProductMappingDetail";
    public static final String DATABASE_TABLE_MAIN208 = "tblSchemeSlabBenefitsValueDetail";
    public static final String DATABASE_TABLE_MAIN209 = "tblProductRelatedScheme";
    public static final String DATABASE_TABLE_MAINADDOnScheme = "tblProductADDONScheme";
    public static final String DATABASE_TABLE_ADDONSCHEME = "tblStoreProductAddOnSchemeApplied";
    public static final String DATABASE_TABLE_Main211 = "tblStoreProductAppliedSchemesBenifitsRecords";
    public static final String DATABASE_TABLE_MAIN221 = "tblStoreTypeMstr";
    public static final String DATABASE_TABLE_MAIN222 = "tblStoreProductClassificationTypeListMstr";
    public static final String DATABASE_TABLE_MAIN223 = "tblTradeChannelMstr";
    public static final String DATABASE_TABLE_MAIN231 = "tblSKUWiseDaySummary";
    public static final String DATABASE_TABLE_MAIN232 = "tblStoreWiseDaySummary";
    public static final String DATABASE_TABLE_MAIN233 = "tblStoreSKUWiseDaySummary";
    public static final String DATABASE_TABLE_MAIN234 = "tblAllSummary";
    public static final String DATABASE_TABLE_MAIN251 = "tblMessageTextFileContainer";
    public static final String DATABASE_TABLE_MAIN51 = "tblSchemeList";
    public static final String DATABASE_TABLE_MAIN52 = "tblSchemeDetails";
    public static final String DATABASE_TABLE_MAIN53 = "tblschemeStoreTypeMap";
    public static final String DATABASE_TABLE_MAIN54 = "tblschemeProductMap";
    public static final String DATABASE_TABLE_MAIN55 = "tblspForPDASchemeApplicableList";
    public static final String DATABASE_TABLE_MAIN56 = "tblPDAIsSchemeApplicable";
    public static final String DATABASE_TABLE_MAIN61 = "tblLastTransactionDetails";
    public static final String DATABASE_TABLE_MAIN62 = "tblPDALastTranDateForSecondPage";
    public static final String DATABASE_TABLE_MAIN63 = "tblStorTypeMstr";
    public static final String DATABASE_TABLE_MAIN9 = "tblVisibilityMstr";
    public static final String DATABASE_TABLE_MAIN10 = "tblVisibilityDetails";

    public static final String DATABASE_TABLE_MAIN16 = "tblNewStoreListEntries";
    public static final String DATABASE_TABLE_MAIN17 = "tblPDALastInvoiceDet";
    public static final String DATABASE_TABLE_MAIN18 = "tblPDATargetQtyForSecondPage";
    public static final String DATABASE_TABLE_MAIN31 = "tblTransac";
    public static final String DATABASE_TABLE_MAIN71 = "tblSyncSummuryDetails";
    public static final String DATABASE_TABLE_MAIN72 = "tblSyncSummuryForProductDetails";
    public static final String DATABASE_TABLE_MAIN81 = "tblStrachApplicableOnScheme";
    public static final String DATABASE_TABLE_MAIN82 = "tblStrachOnSchemeDetails";
    public static final String DATABASE_TABLE_MAIN91 = "tblOutLetInfoOnQuadVolumeCategoryBasis";
    public static final String DATABASE_TABLE_MAINStoreProductMap = "tblStoreProductMap";
    public static final String DATABASE_TABLE_MAIN92 = "tblSelectedStoreIDinChangeRouteCase";
    public static final String DATABASE_TABLE_MAIN94 = "tblSysVisitID";
    public static final String DATABASE_TABLE_MAIN101 = "tblFirstOrderDetailsOnLastVisitDetailsActivity";
    public static final String DATABASE_TABLE_MAIN102 = "tblSecondVisitDetailsOnLastVisitDetailsActivity";
    public static final String DATABASE_TABLE_MAIN103 = "tblPDAGetLODQty";
    public static final String DATABASE_TABLE_MAIN111 = "tblPendingInvoices";
    public static final String DATABASE_TABLE_MAIN112 = "tblInvoiceExecutionProductList";
    public static final String DATABASE_TABLE_MAIN113 = "tblProductWiseInvoice";
    public static final String DATABASE_TABLE_MAIN114 = "tblInvoiceButtonTransac";
    public static final String DATABASE_TABLE_MAIN141 = "tblPDAGetLastVisitDate";
    public static final String DATABASE_TABLE_MAIN142 = "tblPDAGetLastOrderDate";
    public static final String DATABASE_TABLE_MAIN143 = "tblPDAGetLastVisitDetails";
    public static final String DATABASE_TABLE_MAIN144 = "tblPDAGetLastOrderDetails";
    public static final String DATABASE_TABLE_MAIN145 = "tblPDAGetLastOrderDetailsTotalValues";
    public static final String DATABASE_TABLE_MAIN146 = "tblPDAGetExecutionSummary";
    public static final String DATABASE_TABLE_MAIN151 = "tblProductListForAvailableStock";
    public static final String DATABASE_TABLE_MAIN152 = "tblCatagoryMstrForDailyTarget";
    //surbhi
    public static final String DATABASE_TABLE_UOMMstr="tblUOMMstr";
    public static final String DATABASE_TABLE_SalesQuotePrcsMstr="tblSalesQuotePrcsMstr";
    public static final String DATABASE_TABLE_SalesQuotePersonMeetMstr="tblSalesQuotePersonMeetMstr";
    public static final String DATABASE_TABLE_SalesQuoteProductsMstr="tblSalesQuoteProductsMstr";
    public static final String DATABASE_TABLE_tblSalesQuotePaymentModeMstr="tblSalesQuotePaymentModeMstr";
    public static final String DATABASE_TABLE_tblSalesQuotePaymentStageMstr="tblSalesQuotePaymentStageMstr";
    public static final String DATABASE_TABLE_tblSalesQuoteTypeMstr="tblSalesQuoteTypeMstr";
    public static final String DATABASE_TABLE_tblSalesQuotePaymentStageModeMapMstr="tblSalesQuotePaymentStageModeMapMstr";
    //surbhi
    public static final String DATABASE_CREATE_TABLE_UOMMstr = "create table tblUOMMstr (UOMID text null,UOM text null);";
    public static final String DATABASE_CREATE_TABLE_SalesQuotePrcsMstr = "create table tblSalesQuotePrcsMstr (SalesQuotePrcsId text null, SalesQuotePrcs text null);";
    public static final String DATABASE_SalesQuotePersonMeetMstr = "create table tblSalesQuotePersonMeetMstr (SalesQuoteId text null,SalesQuoteCode text null,SalesQuotePrcsId text null,SalesQuotePrcs text null,StoreName text null,Remarks text null,StoreId text null,CreditLimit text null,CreditDays text null,ExpectedBusinessValue text null,SalesQuoteValidFrom text null,SalesQuoteValidTo text null,SalesQuoteDate text null,SalesQuoteType text null,ContactPerson text null,ContactPersonEmail text null,ContactPersonPhone text null,PaymentModeId text null,Sstat text null,PymtStageId text null,ManufacturerID text null,ManufacturerName text null);";
    public static final String DATABASE_CREATE_SalesQuoteProductsMstr = "create table tblSalesQuoteProductsMstr (SalesQuoteId text null,Row_No text null,PrdId text null,StandardRate text null,StandardRateBeforeTax text null,RateOffer text null,InclusiveTax text null,ValidFrom text null,ValidTo text null,MinDlvryQty text null,UOMID text null,Remarks text null,LastTranscRate text null,Sstat text null,TaxRate text null);";
    public static final String DATABASE_CREATE_TABLE_tblSalesQuotePaymentModeMstr="create table tblSalesQuotePaymentModeMstr (PymtModeId text null,PymtMode text null);";
    public static final String DATABASE_CREATE_TABLE_tblSalesQuotePaymentStageMstr="create table tblSalesQuotePaymentStageMstr (PymtStageId text null,PymtStage text null,PymtModeId text null);";
    public static final String DATABASE_CREATE_TABLE_tblSalesQuoteTypeMstr="create table tblSalesQuoteTypeMstr (SalesQuotetypeId	text null, SalesQuoteType text null);";
    public static final String DATABASE_CREATE_TABLE_tblSalesQuotePaymentStageModeMapMstr="create table tblSalesQuotePaymentStageModeMapMstr (PymtStageId text null, PymtModeId text null);";
    //public static final String DATABASE_TABLE_Summarytable = "tblDaySummary";
    public static final String DATABASE_TABLE_SummaryDayTableSummaryNew = "tblDaySummaryNew";
    public static final String DATABASE_TABLE_Main212 = "tblStoreProductPhotoDetail";
    public static final String DATABASE_TABLE_Main213 = "tblStoreReturnDetail";
    public static final String DATABASE_TABLE_MAIN161 = "tblPOSMaterialMstr";
    public static final String DATABASE_TABLE_MAIN162 = "tblStoreIDAndMaterialIDMap";
    public static final String DATABASE_TABLE_Main163 = "tblStoreMaterialDetail";
    public static final String DATABASE_TABLE_Main164 = "tblStoreMaterialPhotoDetail";
    public static final String DATABASE_TABLE_Main165 = "tblStorePOSLastVisitDateDetail";
    public static final String DATABASE_TABLE_Main166 = "tblStorePOSLastVisitALLMaterialDetails";
    public static final String DATABASE_CREATE_TABLE_165 = "create table tblStorePOSLastVisitDateDetail (StoreID text null,LastVisitDate text null);";
    public static final String DATABASE_CREATE_TABLE_166 = "create table tblStorePOSLastVisitALLMaterialDetails (StoreID text null,POSMaterialID text null,POSMaterialDescr text null,CurrentStockQty text null,NewOrderQty text null,ReturnQty text null,DamageQty text null);";
    public static final String DATABASE_CREATE_TABLE_161 = "create table tblPOSMaterialMstr (POSMaterialID text null,POSMaterialDescr text null);";
    public static final String DATABASE_CREATE_TABLE_162 = "create table tblStoreIDAndMaterialIDMap (StoreID text null,VisitID text null,MaterialID text null,CurrentStockQty text null);";
    public static final String DATABASE_CREATE_TABLE_163 = "create table tblStoreMaterialDetail (RouteID text null,StoreID text null,MaterialID text null,ExistStock integer null,ReturntoDistributor integer null,FreshOrder integer null,DiscardDamage integer null,Sstat integer null);";
    public static final String DATABASE_CREATE_TABLE_164 = "create table tblStoreMaterialPhotoDetail (RouteID text null,StoreID text null,MaterialID text null,ClickedDateTime text null,PhotoName text null,PhotoValidation text null,PDAPhotoPath text null,Sstat integer null);";
    public static final String DATABASE_CREATE_TABLE_214 = "create table tblAlrtVal (StoreId text null,ProductID text null,SpinnerVal text null,SpinnerPosition text null,Product text null,schSlabId text null,schmAlrtId text null,OrderIDPDA text null,TmpInvoiceCodePDA text null);";
    public static final String DATABASE_CREATE_TABLE_215 = "create table tblProductMappedWithSchemeSlabApplied (StoreId text null,ProductID text null,schSlabId text null,schmIdMapped text null,Sstat int null,OrderIDPDA text null,TmpInvoiceCodePDA text null);";
    public static final String DATABASE_CREATE_TABLE_213 = "create table tblStoreReturnDetail (RouteID text null,StoreID text null,ReturnProductID text null, ProdReturnQty text null, ProdReturnReason text null, ProdReturnReasonIndex text null,ReturnDate text null,Sstat integer null,OrderIDPDA text null,TmpInvoiceCodePDA text null);";
    public static final String DATABASE_CREATE_TABLE_212 = "create table tblStoreProductPhotoDetail (StoreID text null,ProductID text null,ClickedDateTime text null,PhotoName text null,ReasonForReturn text null,PhotoValidation text null,PDAPhotoPath text null,Sstat integer null,OrderIDPDA text null,TmpInvoiceCodePDA text null,flgNetorkTimeRecorded int null, datetime text null);";
    public static final String DATABASE_CREATE_TABLE_221 = "create table tblStoreTypeMstr(AutoIdStore integer null,StoreTypeID integer null,StoreTypeDescr text null);";


    // ReasonId,ReasonDescr,FlgToShowTextBox
    public static final String DATABASE_CREATE_TABLE_222 = "create table tblStoreProductClassificationTypeListMstr(AutoIdStore integer null," +
            "CategoryNodeID integer null,CategoryNodeType integer null, Category text null,ProductTypeNodeID integer null,ProductTypeNodeType integer null,ProductType text null,IsCategorySeleted int null,IsSubCategorySeleted int null,SubCategoryValue text null);";
    public static final String DATABASE_CREATE_TABLE_223 = "create table tblTradeChannelMstr(AutoIdStore integer null,TradeChannelID integer null,TradeChannelName text null);";
    public static final String DATABASE_CREATE_TABLE_22 = "create table tblTemp(RouteID text null,StoreID text not null," +
            "CategoryNodeID integer null,CategoryNodeType integer null,Category text null,ProductTypeNodeID integer null,ProductTypeNodeType integer null,ProductType text null,IsCategorySeleted int null,IsSubCategorySeleted int null,SubCategoryValue text null,Sstat integer null);";
    public static final String DATABASE_CREATE_TABLE_21 = "create table tblNewStoreEntries (RouteID text null,StoreID text not null,StoreName text null," +
            "RetailerName text  null,emailID text null,TinNo text null,RetailerContactNo text  null," +
            "StoreAddress text  null,StorePincode text  null,City text  null,KeyAccount text null,TradeChannelID integer null,StoreAttrHierID integer null,StoreProductClassificationID text null,ActualLatitude text null, ActualLongitude text null," +
            "LocProvider text null, Accuracy text null," +
            "VisitStartTS text null, VisitEndTS text null,Imei text null,BatteryStatus text null,Sstat integer null," +
            "CityId integer null,AppVersion text null,flgNetorkTimeRecorded int null, datetime text null);";
    public static final String DATABASE_CREATE_TABLE_251 = "create table tblMessageTextFileContainer (FileName text null,FileFlag integer null);";
    public static final String DATABASE_CREATE_TABLE_201 = "create table tblSchemeStoreMapping (StoreID text null,SchemeID text null);";
    public static final String DATABASE_CREATE_TABLE_202 = "create table tblSchemeMstr (SchemeID text null,SchemeName text null,SchemeApplicationID text null,SchemeAppliedRule text null);";
    public static final String DATABASE_CREATE_TABLE_203 = "create table tblSchemeSlabDetail (SchemeID text null,SchemeSlabID text null,SchemeSlabDesc text null,BenifitDescr text null);";
    public static final String DATABASE_CREATE_TABLE_204 = "create table tblSchemeSlabBucketDetails (RowID text null,SchemeID text null" +
            ",SchemeSlabID text null,BucketID text null,SubBucketID text null,SlabSubBucketType text null,SlabSubBucketValue text null,SubBucketValType text null);";
    public static final String DATABASE_CREATE_TABLE_205 = "create table tblSchemeSlabBucketProductMapping (RowID text null,ProductID text null);";
    public static final String DATABASE_CREATE_TABLE_206 = "create table tblSchemeSlabBenefitsBucketDetails (RowID text null," +
            "SchemeID text null,SchemeSlabID text null,BucketID text null,SubBucketID text null,BenSubBucketType text null," +
            "BenDiscApplied text null,CouponCode text null,BenSubBucketValue text null,Per real null,UOM real null,ProRata int null);";
    public static final String DATABASE_CREATE_TABLE_207 = "create table tblSchemeSlabBenefitsProductMappingDetail (RowID text null,ProductID text null);";
    public static final String DATABASE_CREATE_TABLE_208 = "create table tblSchemeSlabBenefitsValueDetail (RowID text null,BenValue text null,Remarks text null,Type text null);";
    public static final String DATABASE_CREATE_TABLE_209 = "create table tblProductRelatedScheme (ProductID text null,PrdString text null);";

    // Tables for New Schemes Structure
    public static final String DATABASE_CREATE_TABLE_211 = "create table tblStoreProductAppliedSchemesBenifitsRecords (StoreID text not null,ProductID int not null,schId int not null,schSlabId integer not null,schSlbBuckId integer not null,schSlabSubBucketValue real not null,schSubBucketValType integer not null,schSlabSubBucketType int not null,BenifitRowID integer not null,BenSubBucketType int null,FreeProductID int null,BenifitSubBucketValue real null,BenifitMaxValue real null,BenifitAssignedValue real null,BenifitAssignedValueType int null,BenifitDiscountApplied int null,BenifitCouponCode text null,Sstat integer not null,Per real null,UOM real null,WhatFinallyApplied int null,schSlbRowId int null,SchTypeId int null,DiscountPercentage real null,OrderIDPDA text null,TmpInvoiceCodePDA text null);";
    public static final String DATABASE_CREATE_TABLE_RETURNREASON = "create table tblGetReturnsReasonForPDAMstr(StockStatusId text not null,StockStatus text not null);";
    public static final String TABLE_tblDistributorDayReport="tblDistributorDayReport";
    public static final String TABLE_tblDistributorDayReportColumnsDesc="tblDistributorDayReportColumnsDesc";
    public static final String TABLE_tblDistributorSavedData="tblDistributorSavedData";
    public static final String TABLE_tblSuplierDetail="tblSuplierDetail";
    public static final String TABLE_tblDistributorOldStockData="tblDistributorOldStockData";

    public static final String CREATE_TABLE_tblSuplierDetail="create table tblSuplierDetail(DistributorNodeId text null,DistributorNodeType text null,Sstat int null,flgNetorkTimeRecorded int null, datetime text null);";

    public static final String CREATE_TABLE_tblDistributorDayReport="create table tblDistributorDayReport(ProductNodeID text null, ProductNodeType text null, SKUName text null, FlvShortName text null,StockDate text null,DistributorNodeID int null,DistributorNodeType int null);";
    public static final String CREATE_TABLE_tblDistributorDayReportColumnsDesc="create table tblDistributorDayReportColumnsDesc(DistDayReportCoumnName text null, DistDayReportColumnDisplayName text null,DistributorNodeID int null,DistributorNodeType int null);";
    public static final String CREATE_TABLE_tblDistributorSavedData="create table tblDistributorSavedData(ProductName text null,ShortName text null,ProductID text null,Date text null,EnteredValue text null,DistribtrId int null,DistributorNodeType int null,ProductNodeType int null,StockDate text null,EntryType int null,StockPcsCaseType int null,Sstat int null,flgNetorkTimeRecorded int null, datetime text null);";
    public static final String CREATE_TABLE_tblDistributorOldStockData="create table tblDistributorOldStockData (DistribtrId text null,DistributorNodeType text null,DistribtrTag text null,EnteredValue text null);";
    public static final String DATABASE_CREATE_TABLE_231 = "create table tblSKUWiseDaySummary (AutoId int not null,ProductId int null," +
            "Product text not null,MRP real null,Rate real null,NoofStores int null,OrderQty real null,FreeQty int null," +
            "DiscValue real null,ValBeforeTax real null,TaxValue real null,ValAfterTax real null,Lvl int null," +
            "Category text null,UOM text null);";
    public static final String DATABASE_CREATE_TABLE_232 ="create table tblStoreWiseDaySummary (AutoId int not null,Store text null," +
            "LinesperBill int null,StockValue real null,DiscValue real null,ValBeforeTax real null,TaxValue real null," +
            "ValAfterTax real null,Lvl int null);";
    public static final String DATABASE_CREATE_TABLE_233 =  "create table tblStoreSKUWiseDaySummary (AutoId int not null,ProductId int null," +
            "Product text null,MRP real null,Rate real null,OrderQty int null,FreeQty int null," +
            "DiscValue real null,ValBeforeTax real null,TaxValue real null,ValAfterTax real null,Lvl int null,StoreId int null,StockQty int null);";
    public static final String DATABASE_CREATE_TABLE_234 = "create table tblAllSummary (AutoId int not null,Measures text null," +
            "TodaysSummary text null,MTDSummary text null);";
    public static final String DATABASE_CREATE_TABLE_11 = "create table tblPdaDate (PdaDate text null);";
    public static final String DATABASE_CREATE_TABLE_12 = "create table tblDayStartEndDetails (IMEINo text null,SyncTime text null,RouteID text null,EndTime text null,DayEndFlag int null,ChangeRouteFlg int null,ForDate text null,AppVersionID string null,Sstat int null,CycleId text null,dayEndButtonOrCycleEnd int null, flgNetorkTimeRecorded int null, datetime text null);";//,AppVersionID int null//, VersionNo string null
    public static final String DATABASE_CREATE_TABLE_13 = "create table tblStoreList(IMEINumber text null,AutoIdStore INTEGER PRIMARY KEY AUTOINCREMENT not null,StoreID text not null, StoreName string not null,OwnerName text null,StoreContactNo text null,StoreAddress text null,StoreType string not null,chainID integer null,StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null,ISNewStore int null,StoreRouteID int null,RouteNodeType int null,StoreCatNodeId int null,PaymentStage text null,flgHasQuote int null,flgAllowQuotation int null,flgGSTCapture text null,flgGSTCompliance text null,GSTNumber text null,flgGSTRecordFromServer int null,DistanceNear int null,flgStoreOrder int null,StoreCity text null,StorePinCode text  null,StoreState text null,OutStanding float null,OverDue float null,DBR text null,flgRuleTaxVal integer null,flgTransType integer null,StoreCatType text null,IsNewStoreDataCompleteSaved int null,StoreClose int null,SalesPersonName text null,SalesPersonContact text null,IsComposite int null,StoreStateID int null,StoreCityID int null,flgOrderType int not null,flgCollDefControl int null,CollectionPer real null,flgNetorkTimeRecorded int null, device_time text null,P3MValue REAL Default 0.0, MTDValue REAL Default 0.0, NoOfBrands integer  Default 0, CoverageAreaNodeID integer null, CoverageAreaNodeType integer null);";//
    public static final String DATABASE_CREATE_TABLE_14 = "create table tblProductListMaster(CategoryID text not  null,ProductID text not  null, ProductShortName text not  null, DisplayUnit text not null, CalculateKilo real not  null,KGLiter string not null,CatOrdr int null,PrdOrdr int null,StoreCatNodeId int not null,SearchField text not null,ManufacturerID int null,RptUnitName text null,PerbaseUnit text null,HSNCode text null);";
    public static final String DATABASE_CREATE_TABLE_ProductSegementMap = "create table tblProductSegementMap(ProductID text not null,ProductMRP real not null, ProductRLP real not null, ProductTaxAmount real not null,RetMarginPer real not null,VatTax real not null,StandardRate real not null,StandardRateBeforeTax real not null,StandardTax real not null,BusinessSegmentId int not null,flgPriceAva int not null,flgWholeSellApplicable int  not null,PriceRangeWholeSellApplicable real null,StandardRateWholeSale real null,StandardRateBeforeTaxWholeSell real null,StandardTaxWholeSale real null,NHILTax real null,GetFundTax real null);";

    //AutoId int null
    public static final String DATABASE_CREATE_TABLE_15 = "create table tblCategoryMaster (CategoryID text not null,CategoryDescr text not null,CatOrdr int null);";


    public static final String DATABASE_CREATE_TABLE_9 = "create table tblVisibilityMstr (VisibilityID text null, VisibilityDescr text null);";

    public static final String DATABASE_CREATE_TABLE_10 = "create table tblVisibilityDetails (StoreID text not null,VisibilityID text null, VisibilityStock text null,Sstat  integer not null);";
    public static final String DATABASE_CREATE_TABLE_16 = "create table tblNewStoreListEntries (StoreID text null, StoreCity text null, StorePinCode text not null, StoreState text null, ActiveRouteID text null, Sstat integer not null);";
    public static final String DATABASE_CREATE_TABLE_17 = "create table tblPDALastInvoiceDet (StoreID text null, InvoiceID text null, LastTransDate text not null, BalanceAmount text null,CreditPreviousDue real null);";
    public static final String DATABASE_CREATE_TABLE_18 = "create table tblPDATargetQtyForSecondPage (StoreID text null, ProductID text null, SKUShortName text not null, TargetQty text null);";

    //public static final String DATABASE_CREATE_TABLE_13 = "create table tblStoreList (StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude text null, ActualLongitude text null, VisitStartTS text null, VisitEndTS text null, ISNewStore int null, AutoIdStore int null, LocProvider text null, Accuracy text null, BateryLeftStatus text null,StoreRouteID text null);";
    public static final String DATABASE_CREATE_TABLE_19 = "create table tblPDAProductReturnMstr (AutoReturnIdMstr int null,StoreID text null, ReturnDate text null, Comment text null, TotalReturnQty int null, TotalReturnValue real null, Sstat integer null,TotalAdjustQty integer null,TotalAdjustValue real null,FinalBalanceAmount real null,LastCreditAmount real null,OrderIDPDA text not null,TmpInvoiceCodePDA text null);";

    //public static final String DATABASE_CREATE_TABLE_2 = "create table tblProductListMaster (ProductID text not null, ProductShortName text not null, ProductMRP real not null, ProductRLP real not null, ProductTaxAmount real not null, KGLiter string nulll);";//,DisplayUnit string nul
    public static final String DATABASE_CREATE_TABLE_20 = "create table tblPDAProductReturnDetails (AutoReturnDetailsId int null,ReturnIdMstr int null, ProductId text null, ProdReturnQty text null, ProdRate real null, ProdReturnValue real null, ProdReturnReason text null, ProdReturnReasonDescr text null, ProdLastOrderDate text null, ProdLastOrderQyt text null, Sstat integer null,AdjustReturnQty integer null,AdjustReturnValue real null,TmpInvoiceCodePDA text null);";

    //public static final String DATABASE_CREATE_TABLE_ProductSegementMap = "create table tblProductSegementMap(ProductID text  null,ProductMRP real not null, ProductRLP real not null, ProductTaxAmount real not null,RetMarginPer real null,VatTax real null,StandardRate real null,StandardRateBeforeTax real null,StandardTax real null,BusinessSegmentId int null,flgPriceAva int null);";
    public static final String DATABASE_CREATE_TABLE_31 = "create table tblTransac (IMEIno text not null, TransDate string not null, StoreID text not null, ProdID text not null, Stock integer not null, OrderQty integer not null, OrderVal real not null, FreeQty integer not null, DisVal real not null, SchemeID text null, AppliedSlab text null, AppliedAbsVal text null, Sstat integer not null, SampleQuantity int null, ProductShortName text null, ProductPrice real null,RouteID int null,CatID text  null);";//, DisplayUnit text null
    public static final String DATABASE_CREATE_TABLE_StoreProductMap = "create table tblStoreProductMap (StoreID text  null,ProductID text  null, ProductMRP real  null, ProductRLP real  null, ProductTaxAmount real  null, DistributorPrice real null,CategoryID integer null);";

    //public static final String DATABASE_CREATE_TABLE_5 = "create table tblCategoryQuestionInformation (CategoryID text null,ProductID text null, Stock text null,Sstat integer not null,StoreID text not null);";

    //public static final String DATABASE_CREATE_TABLE_7 = "create table tblDistributionStoreProductWiseDetails (CatId integer not null,ProductID integer not null,StoreID text not null,Stock text null,ProductMfgDate text null,Sstat integer not null);";
    public static final String DATABASE_CREATE_TABLE_51 = "create table tblSchemeList (SchemeID text not null, SchemeName string not null, SchemeType string not null, StoreType string null, CombTypeID integer null,flgDiscountType integer null);";
    public static final String DATABASE_CREATE_TABLE_52 = "create table tblSchemeDetails (SchemeDetID text not null, SchemeID text not null, SlabFrom integer not null, SlabTo integer not null, FreeQuantity integer not null, Discount real not null, FreeProductID text null);";
    public static final String DATABASE_CREATE_TABLE_53 = "create table tblschemeStoreTypeMap (SchemeID text not null, StoreTypeID integer not null);";
    public static final String DATABASE_CREATE_TABLE_54 = "create table tblschemeProductMap (SchemeID text not null, ProductID text not null, SchemeType int null);";
    public static final String DATABASE_CREATE_TABLE_55 = "create table tblspForPDASchemeApplicableList(SchemeID string null, StoreType string null, SchemeDesc string null,flgSpecialScheme integer null);";
    public static final String DATABASE_CREATE_TABLE_56 = "create table tblPDAIsSchemeApplicable (IsSchemeApplicable int null);";
    public static final String DATABASE_CREATE_TABLE_61 = "create table tblLastTransactionDetails (StoreID text not null, ProductID text not null, LastTransDate string not null, Quantity integer not null, SampleQuantity int null,CategoryID integer null);";
    public static final String DATABASE_CREATE_TABLE_62 = "create table tblPDALastTranDateForSecondPage(StoreID text null, LastTransDate string null, RetailerName string null, SKUName string null, Stock string null, OrderQty string null, FreeQty string null);";

    //public static final String DATABASE_CREATE_TABLE_31 = "create table tblTransac (IMEIno text not null, TransDate string not null, StoreID text not null, ProdID text not null, OrderQty integer not null, OrderVal real not null, Sstat integer not null,ProductMRP real not null,TaxRate real not null);";
    //public static final String DATABASE_CREATE_TABLE_32 = "create table tblTmpInvoiceHeader (IMEIno text not null, StoreID text not null, InvoiceDate string not null,  TaxAmt real not null,  InvoiceVal real not null,GrossVal real not null,  Sstat integer not null, AmtColl real null, AmtOut real null);";



    //public static final String DATABASE_CREATE_TABLE_StoreProductMap = "create table tblSIMEInotoreProductMap (StoreID text  null,ProductID text  null, ProductMRP real  null, ProductRLP real  null, ProductTaxAmount real  null, DistributorPrice real null);";
    public static final String DATABASE_CREATE_TABLE_63 = "create table tblStorTypeMstr(ID string null, Descr string null);";
    public static final String DATABASE_CREATE_TABLE_71 = "create table tblSyncSummuryDetails (ActualCalls int null,ProductiveCalls int null, TotSalesValue text null, TotKGSales text null, TotFreeQtyKGSales text null, TotSampleKGSales text null, TotLTSales text null, TotFreeQtyLTSales text null, TotSampleLTSales text null, TotDiscountKGSales text null, TotDiscountLTales text null,Lines int null);";
    public static final String DATABASE_CREATE_TABLE_72 = "create table tblSyncSummuryForProductDetails (SkuName text null,OrderQty text null, FreeQty text null, SampleQty text null, TotalOrderKgs text null, TotalFreeKgs text null, TotalSampleKgs text null, TotalSales text null,Lines int null,OrderVal text null,DisVal text null,ProductPrice text null);";
    public static final String DATABASE_CREATE_TABLE_81 = "create table tblStrachApplicableOnScheme (SchID int null, flgIsStrachApplicable int null);";
    public static final String DATABASE_CREATE_TABLE_82 = "create table tblStrachOnSchemeDetails (SchID int null, cardStrachID int null, Slab int Null, Qty int null, ProductValueOrSlabBased int null);";
    public static final String DATABASE_CREATE_TABLE_91 = "create table tblOutLetInfoOnQuadVolumeCategoryBasis (OutID text null,OutletName text  null," +
            " OwnerName text  null,ContactNo text null,MarketAreaName text null,Latitude text null,Longitutde text null);";
    public static final String DATABASE_CREATE_TABLE_92 = "create table tblSelectedStoreIDinChangeRouteCase (StoreID text null);";
    public static final String DATABASE_CREATE_TABLE_94 = "create table tblSysVisitID (IMEINo text null,SysVisitID int null );";
    public static final String DATABASE_CREATE_tblDaySummaryNew = "create table tblDaySummaryNew (TargetCalls int null,ActualCallOnRoute int null,ActualCallOffRoute int null,ProdCallOnRoute int null,ProdCallOffRoute real null,TargetSalesForDay real null,TotalSalesForDay real null,CallsRemaining int null,TargetSalesMTD real null,AchievedSalesMTD real null,ProdStoresMTD int null,RunRate real null);";//, AutoIdOutlet int null
    public static final String DATABASE_CREATE_TABLE_101 = "create table tblFirstOrderDetailsOnLastVisitDetailsActivity(StoreID text null,Date text null,SKUID text null,OrderQty integer null,FreeQty integer null,Stock integer null,SKUName text null);";
    public static final String DATABASE_CREATE_TABLE_102 = "create table tblSecondVisitDetailsOnLastVisitDetailsActivity(StoreID text null,Date text null,SKUID text  null,OrderQty integer null,SKUName text null);";
    public static final String DATABASE_CREATE_TABLE_103 = "create table tblPDAGetLODQty(StoreID text not null,Date text null,SKUID text not null,Qty integer null,SKUName text not null);";
    public static final String DATABASE_CREATE_TABLE_111 = "create table tblPendingInvoices(StoreID integer null," +
            "StoreName text null,RouteId integer null,RouteNodeType integer null,RouteName text null,DistId integer null,DistName text null," +
            "InvoiceForDate text null,flgSubmit integer null,Sstat integer not null,IMEIno text not null,OrderID text  null,flgCancel int null,ServerOrdersFlg int null);";
    public static final String DATABASE_CREATE_TABLE_112 = "create table tblInvoiceExecutionProductList (ProductId integer null, ProductName text null);";
    public static final String DATABASE_CREATE_TABLE_113 = "create table tblProductWiseInvoice (StoreID integer not null," +
            " ProductID integer not null,OrderQty integer not null,ProductPrice real not null,InvoiceForDate text null,OrderID integer not null,CatID integer not null,Freeqty integer not null,TotLineDiscVal real null);";
    public static final String DATABASE_CREATE_TABLE_114 = "create table tblInvoiceButtonTransac (IMEIno text not null, " +
            "TransDate string not null, StoreID text not null, ProdID text not null, OrderQty integer not null, " +
            "DelQty integer not null,FreeQty integer not null,Sstat integer not null,ProductShortName text null, ProductPrice real null," +
            "RouteID int null,OrderID text  null,CatID text null,flgCancel int null,DiscountVal real null,additionalDiscount text null,CancelRemarks text null,CancelReasonId text null,InvNumber text null,InvDate text null,LineValue text null);";   public static final String DATABASE_CREATE_TABLE_141 = "create table tblPDAGetLastVisitDate(StoreID text not null,VisitDate text not null,flgOrder text null);";


    // public static final String DATABASE_CREATE_tblDaySummary = "create table tblDaySummary (StoreID text null,flgTarget int null,flgActualVisited int null,flgProductiveDay int null,flgProductiveMTD int null,DaySales real null,MTDSales real null);";//, AutoIdOutlet int null
    public static final String DATABASE_CREATE_TABLE_142 = "create table tblPDAGetLastOrderDate(StoreID text not null,OrderDate text not null,flgExecutionSummary text not null);";
    public static final String DATABASE_CREATE_TABLE_143 = "create table tblPDAGetLastVisitDetails(StoreID text not null,Date123 text null,Order123 text null,Stock text null,SKUName text not null,ExecutionQty text null,ProductID text not null);";
    //public static final String DATABASE_CREATE_TABLE_143 = "create table tblPDAGetLastVisitDetails(StoreID text null,Date123 text null,Order123 text null,Stock text null,SKUName text null,ExecutionQty text null);";

    public static final String DATABASE_CREATE_TABLE_144 = "create table tblPDAGetLastOrderDetails(StoreID text not null,OrderDate text not null,ProductID text not null,OrderQty text not null,FreeQty text null,PrdName text null,ExecutionQty text null);";
    public static final String DATABASE_CREATE_TABLE_145 = "create table tblPDAGetLastOrderDetailsTotalValues(StoreID text not null,OrderValue text not null,ExecutionValue text null);";
    public static final String DATABASE_CREATE_TABLE_146 = "create table tblPDAGetExecutionSummary(StoreID text not null,OrderDate text not null,ProductID text not null,OrderQty text not null,flgInvStatus text not null,ProductQty text not null,PrdName text not null);";
    public static final String DATABASE_CREATE_TABLE_147 = "create table tblStoreSchemeFreeProQtyOtherDetails(StoreID text null,StoreType text null,SchemeID text null,SchemeType string not null,CombTypeID integer null,flgDiscountType integer null,FreeProductID text null,FreeProductQty text null,Discount real not null);";
    public static final String DATABASE_CREATE_TABLE_151 = "create table tblProductListForAvailableStock (ProductID text  null, ProductName text  null);";
    public static final String DATABASE_TABLE_MAIN261 = "tblManagerMstr";

    //public static final String DATABASE_CREATE_TABLE_115 = "create table tblPdaDate (PdaDate text null);";
    public static final String DATABASE_TABLE_MAIN262 = "tblSelectedManagerDetails";
    public static final String DATABASE_CREATE_TABLE_261 = "create table tblManagerMstr(PersonID text not null,PersonType text not null,PersonName text not null,ManagerID text not null,ManagerType text not null,ManagerName text not null);";
    public static final String DATABASE_CREATE_TABLE_262 = "create table tblSelectedManagerDetails(IMEI text null,CurDate text null,PersonID text null,PersonType text null" +
            ",PersonName text null,ManagerID text null,ManagerType text null,ManagerName text null,OtherName text null,Sstat integer not null,flgNetorkTimeRecorded int null, device_time text null);";
    //store close info
    public static final String DATABASE_TABLE_tblStoreCloseLocationDetails = "tblStoreCloseLocationDetails";
    public static final String DATABASE_CREATE_TABLE_tblStoreCloseLocationDetails = "create table tblStoreCloseLocationDetails (StoreID text null,Lattitude text null,Longitude text null,Accuracy text null,Address text null,City text null,Pincode text null,State text null,fnAccurateProvider  text null,GpsLat  text null,GpsLong  text null,GpsAccuracy  text null,NetwLat  text null,NetwLong  text null,NetwAccuracy  text null,FusedLat  text null,FusedLong  text null,FusedAccuracy  text null,AllProvidersLocation  text null,GpsAddress  text null,NetwAddress  text null,FusedAddress  text null,FusedLocationLatitudeWithFirstAttempt  text null,FusedLocationLongitudeWithFirstAttempt  text null,FusedLocationAccuracyWithFirstAttempt  text null,Sstat int null,StoreVisitCode text null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_tblStoreClosedPhotoDetail = "tblStoreClosedPhotoDetail";
    public static final String DATABASE_CREATE_TABLE_tblStoreClosedPhotoDetail = "create table tblStoreClosedPhotoDetail (StoreID text null,ClickedDateTime text null,PhotoName text null,PDAPhotoPath text null,Sstat integer null,StoreVisitCode text null,flgNetorkTimeRecorded int null, device_time text null);";
    public static final String DATABASE_TABLE_tblStoreCloseReasonMaster = "tblStoreCloseReasonMaster";
    //public static final String DATABASE_CREATE_TABLE_152 = "create table tblCategoryMaster (CategoryID text not null,CategoryDescr text null);";
    public static final String DATABASE_CREATE_TABLE_tblStoreCloseReasonMaster = "create table tblStoreCloseReasonMaster (CloseReasonID int not null,CloseReasonDescr text not null);";
    public static final String DATABASE_TABLE_tblStoreCloseReasonSaving = "tblStoreCloseReasonSaving";
    public static final String DATABASE_CREATE_TABLE_tblStoreCloseReasonSaving = "create table tblStoreCloseReasonSaving (StoreID text null,ReasonID text null,ReasonDescr text null,Sstat integer null,StoreVisitCode text null,flgNetorkTimeRecorded int null, device_time text null);";

    public static final String DATABASE_CREATE_TABLE_ADDONSCHEME = "create table tblProductADDONScheme (ProductID text null,PrdString text null);";
    public static final String DATABASE_CREATE_ADDONSCHEME = "create table tblStoreProductAddOnSchemeApplied (StoreID text not null,ProductID int not null,schId int not null,schSlabId integer not null,schSlbBuckId integer not null,schSlabSubBucketValue real not null,schSubBucketValType integer not null,schSlabSubBucketType int not null,BenifitRowID integer not null,BenSubBucketType int null,FreeProductID int null,BenifitSubBucketValue real null,BenifitMaxValue real null,BenifitAssignedValue real null,BenifitAssignedValueType int null,BenifitDiscountApplied int null,BenifitCouponCode text null,Sstat integer not null,Per real null,UOM real null,WhatFinallyApplied int null,schSlbRowId int null,SchTypeId int null,DiscountPercentage real null,OrderIDPDA text null,flgAddOn int null,isDiscountOnTotalAmount int null);";


    //Incentive tables Starts Here

    public static final String TABLE_tblIncentiveMaster = "tblIncentiveMaster";
    public static final String CREATE_TABLE_tblIncentiveMaster = "create table tblIncentiveMaster(IncId int null, OutputType int null, IncentiveName text null,flgAcheived text null,Earning text null);";



    public static final String TABLE_tblIncentiveSecondaryMaster = "tblIncentiveSecondaryMaster";
    public static final String CREATE_TABLE_tblIncentiveSecondaryMaster = "create table tblIncentiveSecondaryMaster(IncSlabId int null,IncId int null,OutputType int null,IncSlabName text null,flgAcheived text null,Earning text null);";

    public static final String TABLE_tblIncentiveDetailsData = "tblIncentiveDetailsData";
    public static String DATABASE_CREATE_TABLE_tblIncentiveDetailsData="";


    public static final String TABLE_tblIncentiveDetailsColumnsDesc = "tblIncentiveDetailsColumnsDesc";
    public static final String CREATE_TABLE_tblIncentiveDetailsColumnsDesc = "create table tblIncentiveDetailsColumnsDesc(IncSlabId int null,ReportColumnName text null,DisplayColumnName text null);";


    public static final String TABLE_tblTotalEarning = "tblTotalEarning";
    public static final String CREATE_TABLE_tblTotalEarning = "create table tblTotalEarning(Total_Earning text null);";

    public static final String TABLE_tblIncentivePastDetailsData = "tblIncentivePastDetailsData";
    public static String DATABASE_CREATE_TABLE_tblIncentivePastDetailsData="";

    public static final String TABLE_tblIncentivePastDetailsColumnsDesc = "tblIncentivePastDetailsColumnsDesc";
    public static final String CREATE_TABLE_tblIncentivePastDetailsColumnsDesc = "create table tblIncentivePastDetailsColumnsDesc(IncSlabId int null,ReportColumnName text null,DisplayColumnName text null,Ordr text null);";


    public static final String TABLE_tblIncentiveMsgToDisplay_Define = "tblIncentiveMsgToDisplay";
    public static final String CREATE_tblIncentiveMsgToDisplay_Definition = "create table tblIncentiveMsgToDisplay(MsgToDisplay text null,flgBankDetailsToShow int null);";

    public static final String TABLE_tblIncentiveBankDetails = "tblIncentiveBankDetails";
    public static final String CREATE_tblIncentiveBankDetails = "create table tblIncentiveBankDetails(LvlName text null,Value text null);";

//    public static final String IS_NETWORK_TIME_RECORDED = "IsNetworkTimeRecorded";
    public static final String DEVICE_TIME = "device_time";

    public static Context context;
    public static String DATABASE_NAME = CommonInfo.DATABASE_NAME;
    public static int DATABASE_VERSION = CommonInfo.DATABASE_VERSIONID;
    public static String AppVersionID = CommonInfo.AppVersionID.trim();
    public static int Application_TypeID = CommonInfo.Application_TypeID; //1=Parag Store Mapping,2=Parag SFA Indirect,3=Parag SFA Direct


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

        for (String table : tables) {
            if (!table.equalsIgnoreCase("sqlite_sequence")) {
                String dropQuery = "DROP TABLE IF EXISTS " + table;
                System.out.println("0000 Yogesh " + dropQuery);
                db.execSQL(dropQuery);
            }
        }
        onCreate(db);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreImageList);
            db.execSQL(CREATE_TABLE__JOINTVISITMASTER);
            db.execSQL(CREATE_TABLE_JOINTVISITDETAILS);
            db.execSQL(CREATE_TABLE_REPORTDISTRIBUTION);
            db.execSQL(CREATE_TABLE_REPORTSECTIONVOL);
            db.execSQL(CREATE_TABLE_REPORTPRIMARYVOL);
            db.execSQL(CREATE_TABLE_REPORTMANDAYS);


            db.execSQL(DATABASE_CREATE_TABLE_tblCoverageDsr);
        db.execSQL(DATABASE_CREATE_TABLE_tblCoverageMstr);

            db.execSQL(DATABASE_CREATE_TABLE_ADDONSCHEME);
            db.execSQL(DATABASE_CREATE_ADDONSCHEME);

            db.execSQL(DATABASE_CREATE_TABLE_tblSurveyData);
            db.execSQL(DATABASE_CREATE_TABLE_tblOptionSurvey);
            db.execSQL(DATABASE_CREATE_TABLE_tblQuestionsSurvey);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreEdit);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreEditImages);
            db.execSQL(CREATE_tblIncentiveMsgToDisplay_Definition);
            db.execSQL(CREATE_TABLE_tblIncentiveMaster);
            db.execSQL(CREATE_TABLE_tblIncentiveDetailsColumnsDesc);
            db.execSQL(CREATE_TABLE_tblTotalEarning);
            db.execSQL(CREATE_TABLE_tblIncentivePastDetailsColumnsDesc);
            db.execSQL(CREATE_TABLE_tblIncentiveSecondaryMaster);
            db.execSQL(CREATE_tblIncentiveBankDetails);
            db.execSQL(DATABASE_CREATE_TABLE_tblExecutionImages);
            db.execSQL(DATABASE_CREATE_REASON_ORDER_CANCEL);
            db.execSQL(CREATE_TABLE_tblSuplierDetail);

            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_PRODUCT_STOCK);
            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_ORDERLEFT);

            db.execSQL(DATABASE_CREATE_tblAllServicesCalledSuccessfull);
            db.execSQL(DATABASE_CREATE_TABLE_tblDAGetAddedOutletSummaryReport);
            db.execSQL(DATABASE_CREATE_TABLE_tblCollectionReportChequeChange);
            db.execSQL(DATABASE_CREATE_TABLE_tblCollectionReportCashChange);
            db.execSQL(DATABASE_CREATE_TABLE_tblUOMMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblUOMMapping);
            db.execSQL(DATABASE_CREATE_TABLE_tblStockReq);

            db.execSQL(DATABASE_CREATE_TABLE_tblStoreLastDeliveryNoteNumber);
            db.execSQL(DATABASE_CREATE_TABLE_tblAppMasterFlags);
            db.execSQL(DATABASE_CREATE_TABLE_tblWarehouseMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreCheckInPic);
            db.execSQL(DATABASE_CREATE_TABLE_tblUserName);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreCountDetails);
            db.execSQL(DATABASE_CREATE_TABLE_tblPreAddedStores);
            db.execSQL(DATABASE_CREATE_TABLE_tblPreAddedStoresDataDetails);

            db.execSQL(DATABASE_CREATE_TABLE_tblActualVisitStock);
            db.execSQL(DATABASE_CREATE_TABLEtblProductListLastVisitStockOrOrderMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblAttandanceDetails);
            db.execSQL(DATABASE_CREATE_TABLE_tblStateCityMaster);
            db.execSQL(DATABASE_CREATE_TABLE_DayCheckIn);
            db.execSQL(DATABASE_CREATE_TABLE_TEMP_DISTRIBUTOR_STOCK);
            db.execSQL(DATABASE_CREATE_TABLE_CYCLEID);

            db.execSQL(DATABASE_CREATE_TABLE_INVOICE_HEADER);
            db.execSQL(DATABASE_CREATE_TABLE_INVOICE_DETAILS);


            db.execSQL(DATABASE_CREATE_TABLE_STOREVISIT);
            db.execSQL(DATABASE_CREATE_tblStoreOrderVisitDayActivity);
            db.execSQL(DATABASE_CREATE_TABLE_tblPriceApplyType);

            db.execSQL(DATABASE_CREATE_TABLE_tblInvoiceCaption);
            db.execSQL(DATABASE_CREATE_TABLE_tblLastOutstanding);
            db.execSQL(DATABASE_CREATE_TABLE_tblInvoiceLastVisitDetails);

            db.execSQL(DATABASE_CREATE_TABLE_tblStockConfirm);
            db.execSQL(DATABASE_CREATE_TABLE_tblStockUploadedStatus);
            //stock out flg
            db.execSQL(DATABASE_CREATE_TABLE_tblStockOut);


            //Amount Collection
            db.execSQL(DATABASE_CREATE_TABLE_tblBankMaster);
            db.execSQL(DATABASE_CREATE_TABLE_tblInstrumentMaster);
            db.execSQL(DATABASE_CREATE_TABLE_tblAllCollectionData);

            //store close info



            db.execSQL(DATABASE_CREATE_TABLE_tblIsDBRStockSubmitted);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreCloseLocationDetails);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreClosedPhotoDetail);
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreCloseReasonMaster );
            db.execSQL(DATABASE_CREATE_TABLE_tblStoreCloseReasonSaving );

            // Splash Screen Tables
            db.execSQL(DATABASE_CREATE_TABLE_tblSupplierMstrList);

            db.execSQL(DATABASE_CREATE_TABLE_tblSuplierMapping);

            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_ORDERPDAID);
            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_LEFTPRODUCT);
            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_STOCK);

            db.execSQL(DATABASE_CREATE_TABLE_tblSameLocationForStoreRestartDone);
            db.execSQL(DATABASE_CREATE_TABLE_23);

            db.execSQL(DATABASE_CREATE_TABLE_DayAndSummary);

            db.execSQL(DATABASE_CREATE_TABLE_235);
            db.execSQL(DATABASE_CREATE_TABLE_236);


            db.execSQL(DATABASE_CREATE_TABLE_271);

            db.execSQL(CREATE_TABLE_tblDistributorDayReport);
            db.execSQL(CREATE_TABLE_tblDistributorDayReportColumnsDesc);
            db.execSQL(CREATE_TABLE_tblDistributorSavedData);
            db.execSQL(CREATE_TABLE_tblDistributorOldStockData);




            db.execSQL(TABLE_tblUserAuthenticationMstr_Definition);
            db.execSQL(DATABASE_CREATE_TABLE_tblBloodGroup);
            db.execSQL(DATABASE_CREATE_TABLE_tblEducationQuali);
            db.execSQL(DATABASE_CREATE_TABLE_tblDsrRegDetails);
            db.execSQL(DATABASE_CREATE_TABLE_tblUserRegistarationStatus);


            db.execSQL(TABLE_tblAvailableVersionMstr_Definition);
            db.execSQL(TABLE_tblRouteListMaster_Definition);
            db.execSQL(TABLE_tblPDANotificationMaster_Definition);
            db.execSQL(TABLE_tblDayStartAttendanceOptions_Definition);
            db.execSQL(TABLE_tblNoVisitStoreDetails_Definition);

            db.execSQL(DATABASE_CREATE_TABLE_QSTOUTCHANNEL);
            db.execSQL(DATABASE_CREATE_TABLE_QST_NAME);
            db.execSQL(DATABASE_CREATE_TABLE_QUESTIONMstr);
            db.execSQL(DATABASE_CREATE_TABLE_QuestGrpMappingMstr);
            db.execSQL(DATABASE_CREATE_TABLE_QUESTION_OPTION_DEPENDENTMstr);
            db.execSQL(DATABASE_CREATE_TABLE_QUESTION_OPTION_VAL_DEPENDENTMstr);

            db.execSQL(DATABASE_CREATE_TABLE_Image);
            db.execSQL(DATABASE_CREATE_TABLE_tblLocationDetails);




            db.execSQL(DATABASE_NewStoreSalesQuotePaymentDetails);
            db.execSQL(DATABASE_StoreSalesOrderPaymentDetails);
            db.execSQL(DATABASE_StoreAddressMapDetailsMstr);
            db.execSQL(DATABASE_StoreOrderBillAddressDetails);

            db.execSQL(DATABASE_StoreSomeProdQuotePriceMstr);

            db.execSQL(DATABASE_CREATE_TABLE_RETURNREASON);

            //db.execSQL(DATABASE_CREATE_TABLE_QuestGrpMappingMstr);
            db.execSQL(DATABASE_CREATE_TABLE_XMLfiles);
            db.execSQL(DATABASE_CREATE_TABLE_13);

            db.execSQL(DATABASE_CREATE_TABLE_NewAddedStoreLocationDetails);


            db.execSQL(DATABASE_CREATE_TABLE_OutletPhotoDetail);
            // db.execSQL(DATABASE_CREATE_TABLE_QUESTIONMstr);
            db.execSQL(DATABASE_CREATE_TABLE_OutletChannelBusinessSegmentMasterr);
            db.execSQL(DATABASE_CREATE_TABLE_OPTIONMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblOutletMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblOutletQuestAnsMstr);
            db.execSQL( DATABASE_CREATE_TABLE_tblViewOutletQuestAnsMstr);

            db.execSQL(  DATABASE_CREATE_TABLE_ViewOutletNameAndId);


            db.execSQL(DATABASE_CREATE_TABLE_QUESTION_DEPENDENTMstr);




            db.execSQL(DATABASE_CREATE_TABLE_11);
            db.execSQL(DATABASE_CREATE_TABLE_12);

            db.execSQL(DATABASE_CREATE_TABLE_14);
            db.execSQL(DATABASE_CREATE_TABLE_ProductSegementMap);
            db.execSQL(DATABASE_CREATE_TABLE_15);

            db.execSQL(DATABASE_CREATE_TABLE_UOMMstr);
            db.execSQL(DATABASE_CREATE_TABLE_SalesQuotePrcsMstr);
            db.execSQL(DATABASE_SalesQuotePersonMeetMstr);
            db.execSQL(DATABASE_CREATE_SalesQuoteProductsMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblSalesQuotePaymentModeMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblSalesQuotePaymentStageMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblSalesQuoteTypeMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblSalesQuotePaymentStageModeMapMstr);
            //db.execSQL(DATABASE_CREATE_TABLE_5);
            //db.execSQL(DATABASE_CREATE_TABLE_7);
            db.execSQL(DATABASE_CREATE_TABLE_9);
            db.execSQL(DATABASE_CREATE_TABLE_10);
            db.execSQL(DATABASE_CREATE_TABLE_16);
            db.execSQL(DATABASE_CREATE_TABLE_17);
            db.execSQL(DATABASE_CREATE_TABLE_18);
            db.execSQL(DATABASE_CREATE_TABLE_19);
            db.execSQL(DATABASE_CREATE_TABLE_20);
            db.execSQL(DATABASE_CREATE_TABLE_31);
            db.execSQL(DATABASE_CREATE_TABLE_32);

            db.execSQL(DATABASE_CREATE_TABLE_StoreProductMap);
            db.execSQL(DATABASE_CREATE_TABLE_51);
            db.execSQL(DATABASE_CREATE_TABLE_52);
            db.execSQL(DATABASE_CREATE_TABLE_53);
            db.execSQL(DATABASE_CREATE_TABLE_54);
            db.execSQL(DATABASE_CREATE_TABLE_55);
            db.execSQL(DATABASE_CREATE_TABLE_56);
            db.execSQL(DATABASE_CREATE_TABLE_61);
            db.execSQL(DATABASE_CREATE_TABLE_62);
            db.execSQL(DATABASE_CREATE_TABLE_63);
            db.execSQL(DATABASE_CREATE_TABLE_71);
            db.execSQL(DATABASE_CREATE_TABLE_72);
            db.execSQL(DATABASE_CREATE_TABLE_81);
            db.execSQL(DATABASE_CREATE_TABLE_82);
            db.execSQL(DATABASE_CREATE_TABLE_91);
            db.execSQL(DATABASE_CREATE_TABLE_92);

            db.execSQL(DATABASE_CREATE_TABLE_94);

            db.execSQL(DATABASE_CREATE_tblDaySummaryNew);


            db.execSQL(DATABASE_CREATE_TABLE_101);
            db.execSQL(DATABASE_CREATE_TABLE_102);
            db.execSQL(DATABASE_CREATE_TABLE_103);


            db.execSQL(DATABASE_CREATE_TABLE_111);
            db.execSQL(DATABASE_CREATE_TABLE_112);
            db.execSQL(DATABASE_CREATE_TABLE_113);
            db.execSQL(DATABASE_CREATE_TABLE_114);


            db.execSQL(DATABASE_CREATE_TABLE_141);
            db.execSQL(DATABASE_CREATE_TABLE_142);
            db.execSQL(DATABASE_CREATE_TABLE_143);
            db.execSQL(DATABASE_CREATE_TABLE_144);
            db.execSQL(DATABASE_CREATE_TABLE_145);
            db.execSQL(DATABASE_CREATE_TABLE_146);
            db.execSQL(DATABASE_CREATE_TABLE_147);

            db.execSQL(DATABASE_CREATE_TABLE_151);

            db.execSQL(DATABASE_CREATE_TABLE_161);
            db.execSQL(DATABASE_CREATE_TABLE_162);
            db.execSQL(DATABASE_CREATE_TABLE_163);
            db.execSQL(DATABASE_CREATE_TABLE_164);

            db.execSQL(DATABASE_CREATE_TABLE_165);
            db.execSQL(DATABASE_CREATE_TABLE_166);


            db.execSQL(DATABASE_CREATE_TABLE_201);
            db.execSQL(DATABASE_CREATE_TABLE_202);
            db.execSQL(DATABASE_CREATE_TABLE_203);
            db.execSQL(DATABASE_CREATE_TABLE_204);
            db.execSQL(DATABASE_CREATE_TABLE_205);
            db.execSQL(DATABASE_CREATE_TABLE_206);
            db.execSQL(DATABASE_CREATE_TABLE_207);
            db.execSQL(DATABASE_CREATE_TABLE_208);
            db.execSQL(DATABASE_CREATE_TABLE_209);
            db.execSQL(DATABASE_CREATE_TABLE_210);
            db.execSQL(DATABASE_CREATE_TABLE_211);

            db.execSQL(DATABASE_CREATE_TABLE_221);
            db.execSQL(DATABASE_CREATE_TABLE_222);
            db.execSQL(DATABASE_CREATE_TABLE_223);

            db.execSQL(DATABASE_CREATE_TABLE_231);
            db.execSQL(DATABASE_CREATE_TABLE_232);
            db.execSQL(DATABASE_CREATE_TABLE_233);
            db.execSQL(DATABASE_CREATE_TABLE_234);

            db.execSQL(DATABASE_CREATE_TABLE_251);




            db.execSQL(DATABASE_CREATE_TABLE_21);
            db.execSQL(DATABASE_CREATE_TABLE_22);

            db.execSQL(DATABASE_CREATE_TABLE_212);

            db.execSQL(DATABASE_CREATE_TABLE_213);

            db.execSQL(DATABASE_CREATE_TABLE_214);
            db.execSQL(DATABASE_CREATE_TABLE_215);

            db.execSQL(DATABASE_CREATE_TABLE_tblSalesQuoteSponsorMstr);
            db.execSQL(DATABASE_CREATE_TABLE_tblManufacturerMstrMain);
            db.execSQL(DATABASE_CREATE_TABLE_tblRateDistribution);


            db.execSQL(DATABASE_CREATE_TABLE_261);
            db.execSQL(DATABASE_CREATE_TABLE_262);
            db.execSQL(DATABASE_CREATE_TABLE_DISTRIBUTOR_tblVanProductAdjStock);


    }
}
