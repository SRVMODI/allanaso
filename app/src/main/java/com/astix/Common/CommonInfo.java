package com.astix.Common;

import android.net.Uri;
import android.support.v4.BuildConfig;


import java.io.File;
import java.util.LinkedHashMap;

public class CommonInfo {
//firebaseID astix8  pass: astix1234

	//live
	/*public static final String LastTrackPreference="SancusLastTrackPrefrence";
	public static String ActiveRouteSM = "0";
	public static int AnyVisit = 0;
	public static String AppLatLngJsonFile = "SancusOrderSFALatLngJson";

	public static int Application_TypeID = 4;
	public static final String AttandancePreference = "SancusOrderAttandancePreference";
	public static int CoverageAreaNodeID = 0;
	public static int CoverageAreaNodeType = 0;
	public static String DATABASE_NAME = "AppDataSource";
	public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
	public static String AppVersionID = BuildConfig.VERSION_NAME;
	public static int flgDrctslsIndrctSls=2;
	public static int DayStartClick = 0;
	public static int DistanceRange = 3000;
	public static final String DistributorCheckInXMLFolder = "SancusOrderDistributorCheckInXML";
	public static final String DistributorMapXMLFolder = "SancusOrderDistributorMapXML";
	public static final String DistributorStockXMLFolder = "SancusOrderDistributorStockXML";
	//public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_SancusSFADistributionDevelopment/Default.aspx";
	public static String FinalLatLngJsonFile = "SancusOrderSFAFinalLatLngJsonTest";
	public static int FlgDSRSO = 0;
	public static String ImageSyncPath = "http://103.20.212.194/ReadXML_SancusImagesDevelopment/Default.aspx";
	public static String ImagesFolder = "SancusOrderSFAImages";
	public static String ImagesFolderServer = ".SancusOrderSFAImagesServer";
	public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_SancusInvoiceDevelopment/DefaultGT.aspx";
	public static String InvoiceXMLFolder = "SancusOrderInvoiceXml";
	public static String OrderSyncPath = "http://103.20.212.194/ReadXML_SancusDevelopment/DefaultGTSFA.aspx";
	public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_SancusDev/DefaultSODistributorMappingGT.aspx";
	public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForSancusSFADev/default.aspx";
	public static String OrderXMLFolder = "SancusOrderSFAXml";
	public static final String Preference = "SancusOrderPrefrence";
	public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
	public static String SalesPersonTodaysTargetMsg = "";
	public static String SalesQuoteId = "BLANK";
	public static int SalesmanNodeId = 0;
	public static int SalesmanNodeType = 0;
	public static String TextFileFolder = "SancusOrderTextFile";
	public static String VersionDownloadAPKName = "SancusSOOrderSales.apk";
	public static String VersionDownloadPath = "http://103.20.212.194/downloads/";
	public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidSancusLive/Service.asmx";
	public static String WebStockInUrl = "http://103.20.212.194/SancusNetworks/manageorder/frmstockin.aspx";
	public static String WebStockOutUrl = "http://103.20.212.194/SancusNetworks/manageorder/frmStockTransferToVanDetail_PDA.aspx";
	public static String clickedTagPhoto_savedInstance = null;
	public static String fileContent = "";
	public static int flgAllRoutesData = 1;
	public static int flgDataScope = 0;
	public static String globalValueOfPaymentStage = "0_0_0";
	public static File imageF_savedInstance = null;
	public static String imageName_savedInstance = null;
	public static String imei = "";
	public static int VanLoadedUnloaded = 0;
	public static String newQuottionID = "NULL";
	public static String prcID = "NULL";
	public static String quatationFlag = "";
	public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
	public static Uri uriSavedImage_savedInstance = null;
	public static String TextFileName="SancusAllDetails";
	public static String TextFileArrayName="AllDetails";
	public static final String COMMON_SYNC_PATH_URL = "http://103.20.212.194/SancusNetwork_PDAFileReceivingApp/Default.aspx?FileType=";
	public static String ClientFileNameOrderSync = "XML_Files";
	public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
	public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
	public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
	public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

	public static final String Invoice_Database_Assistant_DB_NAME= "XMLInvoiceFile";
	public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
	public static final String Database_Assistant_DB_NAME = "DBSancusSFA";
	//public static final String BASE_URL = "http://103.20.212.194/JBGroupoDSRDataAPI/";
	public static final String BASE_URL = "http://103.20.212.194/SancusNetwork_DataAPI/";
	public static String RegistrationID="NotGettingFromServer";
	public static String crntServerTimecrntAttndncTime="";
	public static Integer flgCollDefControl = 0;
	public static Double CollectionPer = 0.00;
	public static LinkedHashMap<String,Integer> hmapAppMasterFlags=new LinkedHashMap<String,Integer>();//Filled from All Button Activicty On onCreate Method
	public static int flgLangChangeReuired=0;
	public static String WebAttendanceReport="http://103.20.212.194/SancusNetworks/Mobile/frmTodayAttendence.aspx";
	public static String WebScndryUpdate="http://103.20.212.194/SancusNetworks/Mobile/frmDailySecondaryUpdate.aspx";

	public static int PersonNodeID=0;
	public static int PersonNodeType=0;

	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int flgNewStoreORStoreValidation=0;

	public static final String STORE_DETAILS_WEB_URL = "http://103.20.212.194/SancusNetworks/Mobile/frmOutletProfile.aspx?StoreId=";
	public static final String DSR_TRACKER_WEB_URL = "http://103.20.212.194/SancusNetworks/Reports/frmSMTracker.aspx?nid=";
	public static final String DAILY_TRACKER_WEB_URL = "http://103.20.212.194/SancusNetworks/Reports/frmDayTrackerReport.aspx?nid=";
	public static String WebPageUrlDataReport="http://103.20.212.194/SancusNetworks/Mobile/fnSalesmanWiseSummaryRpt.aspx";
	public static String WebPageUrl="http://103.20.212.194/SancusNetworks/Mobile/frmRouteTracking.aspx";
	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/SancusNetworks/Reports/frmPDAImgsdev.aspx";*/

	//Test

	public static final String LastTrackPreference="SancusLastTrackPrefrence";
	public static String ActiveRouteSM = "0";
	public static int AnyVisit = 0;
	public static String AppLatLngJsonFile = "SancusOrderSFALatLngJson";

	public static int Application_TypeID = 4;
	public static final String AttandancePreference = "SancusOrderAttandancePreference";
	public static int CoverageAreaNodeID = 0;
	public static int CoverageAreaNodeType = 0;
	public static String DATABASE_NAME = "AppDataSource";
	public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
	public static String AppVersionID = BuildConfig.VERSION_NAME;
	public static int flgDrctslsIndrctSls=2;
	public static int DayStartClick = 0;
	public static int DistanceRange = 3000;
	public static final String DistributorCheckInXMLFolder = "SancusOrderDistributorCheckInXMLDev";
	public static final String DistributorMapXMLFolder = "SancusOrderDistributorMapXMLTest";
	public static final String DistributorStockXMLFolder = "SancusOrderDistributorStockXMLTest";
	//public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_SancusSFADistributionDevelopment/Default.aspx";
	public static String FinalLatLngJsonFile = "SancusOrderSFAFinalLatLngJsonTest";
	public static int FlgDSRSO = 0;
	public static String ImageSyncPath = "http://103.20.212.194/ReadXML_SancusImagesDevelopment/Default.aspx";
	public static String ImagesFolder = "SancusOrderSFAImagesTest";
	public static String ImagesFolderServer = ".SancusOrderSFAImagesServerTest";
	public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_SancusInvoiceDevelopment/DefaultGT.aspx";
	public static String InvoiceXMLFolder = "SancusOrderInvoiceXmlTest";
	public static String OrderSyncPath = "http://103.20.212.194/ReadXML_SancusDevelopment/DefaultGTSFA.aspx";
	public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_SancusDev/DefaultSODistributorMappingGT.aspx";
	public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForSancusSFADev/default.aspx";
	public static String OrderXMLFolder = "SancusOrderSFAXmlTest";
	public static final String Preference = "SancusOrderPrefrence";
	public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
	public static String SalesPersonTodaysTargetMsg = "";
	public static String SalesQuoteId = "BLANK";
	public static int SalesmanNodeId = 0;
	public static int SalesmanNodeType = 0;
	public static String TextFileFolder = "SancusOrderTextFile";
	public static String VersionDownloadAPKName = "SancusSOOrderSales_Test.apk";
	public static String VersionDownloadPath = "http://103.20.212.194/downloads/";
	public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidSancusDevelopment/Service.asmx";
	public static String WebStockInUrl = "http://103.20.212.194/Sancusdev/manageorder/frmstockin.aspx";
	public static String WebStockOutUrl = "http://103.20.212.194/Sancusdev/manageorder/frmStockTransferToVanDetail_PDA.aspx";
	public static String clickedTagPhoto_savedInstance = null;
	public static String fileContent = "";
	public static int flgAllRoutesData = 1;
	public static int flgDataScope = 0;
	public static String globalValueOfPaymentStage = "0_0_0";
	public static File imageF_savedInstance = null;
	public static String imageName_savedInstance = null;
	public static String imei = "";
	public static int VanLoadedUnloaded = 0;
	public static String newQuottionID = "NULL";
	public static String prcID = "NULL";
	public static String quatationFlag = "";
	public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
	public static Uri uriSavedImage_savedInstance = null;
	public static String TextFileName="SancusAllDetails";
	public static String TextFileArrayName="AllDetails";
	public static final String COMMON_SYNC_PATH_URL = "http://103.20.212.194/SancusNetwork_PDAFileReceivingApp_test/Default.aspx?FileType=";
	public static String ClientFileNameOrderSync = "XML_Files";
	public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
	public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
	public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
	public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

	public static final String Invoice_Database_Assistant_DB_NAME= "XMLInvoiceFile";
	public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
	public static final String Database_Assistant_DB_NAME = "DBSancusSFA";
	//public static final String BASE_URL = "http://103.20.212.194/JBGroupoDSRDataAPI/";
	public static final String BASE_URL = "http://103.20.212.194/SancusNetwork_DataAPI_test/";
	public static String RegistrationID="NotGettingFromServer";
	public static String crntServerTimecrntAttndncTime="";
	public static Integer flgCollDefControl = 0;
	public static Double CollectionPer = 0.00;
	public static LinkedHashMap<String,Integer> hmapAppMasterFlags=new LinkedHashMap<String,Integer>();//Filled from All Button Activicty On onCreate Method
	public static int flgLangChangeReuired=0;
	public static String WebAttendanceReport="http://103.20.212.194/SancusNetworks_test/Mobile/frmTodayAttendence.aspx";
	public static String WebScndryUpdate="http://103.20.212.194/SancusNetworks_test/Mobile/frmDailySecondaryUpdate.aspx";

	public static int PersonNodeID=0;
	public static int PersonNodeType=0;

	public static int flgLTFoodsSOOnlineOffLine=0;
	public static int flgNewStoreORStoreValidation=0;

	public static final String STORE_DETAILS_WEB_URL = "http://103.20.212.194/SuperMaxSFANigeria_test/Mobile/frmOutletProfile.aspx?StoreId=";
	public static final String DSR_TRACKER_WEB_URL = "http://103.20.212.194/SuperMaxSFANigeria_test/Reports/frmSMTracker.aspx?nid=";
	public static final String DAILY_TRACKER_WEB_URL = "http://103.20.212.194/SuperMaxSFANigeria_test/Reports/frmDayTrackerReport.aspx?nid=";
	public static String WebPageUrlDataReport="http://103.20.212.194/SancusNetworks_test/Mobile/fnSalesmanWiseSummaryRpt.aspx";
	public static String WebPageUrl="http://103.20.212.194/SancusNetworks_test/Mobile/frmRouteTracking.aspx";
	public static String URLImageLinkToViewStoreOverWebProtal="http://103.20.212.194/SancusNetworks_test/Reports/frmPDAImgsdev.aspx";


					/*hmapAppMasterFlags.put("flgDistributorCheckIn", cursor.getInt(0));
                    hmapAppMasterFlags.put("flgDBRStockInApp", cursor.getInt(1));
                    hmapAppMasterFlags.put("flgDBRStockEdit", cursor.getInt(2));
                    hmapAppMasterFlags.put("flgDBRStockCalculate", cursor.getInt(3));
                    hmapAppMasterFlags.put("flgDBRStockControl", cursor.getInt(4));
                    hmapAppMasterFlags.put("flgCollRequired", cursor.getInt(5));   //0=Not To be mapped Again,1=Can Map Distributor
                    hmapAppMasterFlags.put("flgCollReqOrdr", cursor.getInt(6));
                    hmapAppMasterFlags.put("flgCollTab", cursor.getInt(7));
                    hmapAppMasterFlags.put("flgCollDefControl", cursor.getInt(8));
                    hmapAppMasterFlags.put("flgCollControlRule", cursor.getInt(9));
                    hmapAppMasterFlags.put("flgSchemeAvailable", cursor.getInt(10));
                    hmapAppMasterFlags.put("flgSchemeAllowEntry", cursor.getInt(11));
                    hmapAppMasterFlags.put("flgSchemeAllowEdit", cursor.getInt(12));
                    hmapAppMasterFlags.put("flgQuotationIsAvailable", cursor.getInt(13));
                    hmapAppMasterFlags.put("flgExecutionIsAvailable", cursor.getInt(14));
                    hmapAppMasterFlags.put("flgExecutionPhotoCompulsory", cursor.getInt(15));
                    hmapAppMasterFlags.put("flgTargetShowatStart", cursor.getInt(16));
                    hmapAppMasterFlags.put("flgIncentiveShowtStart", cursor.getInt(17));
                    hmapAppMasterFlags.put("flgInvoicePrint", cursor.getInt(18));
                    hmapAppMasterFlags.put("flgShowPOSM", cursor.getInt(19));
                    hmapAppMasterFlags.put("flgVisitStartOutstandingDetails", cursor.getInt(20));
                    hmapAppMasterFlags.put("flgVisitStartSchemeDetails", cursor.getInt(21));
                    hmapAppMasterFlags.put("flgStoreDetailsEdit", cursor.getInt(22));
                    hmapAppMasterFlags.put("flgShowDeliveryAddressButtonOnOrder", cursor.getInt(23));
                    hmapAppMasterFlags.put("flgShowManagerOnStoreList", cursor.getInt(24));
                    hmapAppMasterFlags.put("flgRptTargetVsAchived", cursor.getInt(25));
                    hmapAppMasterFlags.put("flgVanSockManage", cursor.getInt(26));
                    hmapAppMasterFlags.put("flgVanSockManage", cursor.getInt(27));*/
}