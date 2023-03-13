package com.astix.allanasosfa.sync;


import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.database.AppDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;


public class DatabaseAssistant 
{
	private static final String DATASUBDIRECTORY = CommonInfo.OrderXMLFolder;
	private XmlBuilder xmlBuilder;
	static String DATABASE_NAME_2SYNC = CommonInfo.DATABASE_NAME;
	 static  int DATABASE_VERSION = CommonInfo.DATABASE_VERSIONID;
	private final Context context;

	private AppDataSource mDataSource;

	public DatabaseAssistant(Context ctx)
	{
		this.context = ctx;
		mDataSource=AppDataSource.getInstance(ctx);
	}


	public void export(final String dbName, final String exportFileNamePrefix,String ActiveRouteID)
			throws IOException
			{

		xmlBuilder = new XmlBuilder();
				xmlBuilder.start(CommonInfo.Database_Assistant_DB_NAME);

			exportTableStoreList("tblStoreList");

			exportTable("tblNewAddedStoreLocationDetails");
			exportTableStoreVisit("tblStoreVisitMstr");
			exportTabletblTransac("tblInvoiceDetails");
				exportTableStoreCheckIn("tblActualVisitStock");

			exportTabletblInvoice("tblInvoiceHeader");


			exportCollection("tblAllCollectionData");
			exportTablStoreProductPhotoDetail("tblStoreProductPhotoDetail");
			

		

			exportTableStoreMain("tblNewStoreEntries");

			exportTable("tblOutletQuestAnsMstr");

			exportTable("tblNoVisitStoreDetails");
			

			exportTable("tblSelectedManagerDetails");

			exportTable("tableImage");
			exportTable("tblLatLongDetails");
			exportTable("tblsameLocationForStoreRestartDone");

			exportTable("tblDsrRegDetails");
			exportTable("tblStoreCloseLocationDetails");
			exportTable("tblStoreClosedPhotoDetail");
			exportTable("tblStoreCloseReasonSaving");
				exportTableAttandance("tblAttandanceDetails");
				exportTable("tblStoreCheckInPic");

				exportTable("tblCollectionReportCashChange");
				exportTable("tblCollectionReportChequeChange");
				exportTable("tblStoreOrderVisitDayActivity");


				exportTable("tblDistributorSavedData");
				exportTable("tblSuplierDetail");

				exportTable("tblSuplierMapping");
				exportTable("tblCycleID");
				exportTable("tblStoreEdit");
				exportTable("tblStoreEditImages");
				exportTable("tblSurveyData");

				exportTable("tblJointVisitMstr");
				exportTable("tblJointVisitDetails");
				exportTable("tblLocationDetails");
				exportTable("tblPreAddedStores");
				exportTableDayStartEndDetails("tblDayStartEndDetails");
			//String routeID=GetActiveRouteIDSunil();
			UpdateNewAddedStorephotoFlag();

		String xmlString = xmlBuilder.end();
		writeToFile(xmlString, exportFileNamePrefix + ".xml");

	}
	private void exportTableAttandance(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	private void exportTableNotification(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	
	private void exportTableForRouteType(final String tableName,String ActiveRouteID) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select ID,RouteType from " + tableName + " WHERE ID='"+ ActiveRouteID +"' Limit 1";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	
	private void exportTableStoreMaterialDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3 and  (ExistStock<>0 OR ReturntoDistributor<>0 OR FreshOrder<>0 OR DiscardDamage<>0)";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTablStoreProductPhotoDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select StoreID,ProductID,ClickedDateTime,PhotoName,PhotoValidation,PDAPhotoPath,TmpInvoiceCodePDA from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	
	private void exportTableStoreMain(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTableTemp(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	

	private void exportTableStoreCheckIn(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select storeID,ProductID,Stock from " + tableName + " where Sstat = 3";//CollectionCode		// chk for flag - DB adapter
		//String	sql = db.rawQuery("SELECT ifnull(INVPrefix,''),ifnull(INVSuffix,'') FROM tblActualVisitStock", null); //order by AutoIdOutlet Desc
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}

	private void exportTabletblInvoice(final String tableName) throws IOException {
		LinkedHashMap<String,String> hmapInvoiceCaptionPrefixAndSuffix=fetch_InvoiceCaptionPrefixAndSuffix();
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		//String sql = "select StoreID,StoreVisitCode,'"+hmapInvoiceCaptionPrefixAndSuffix.get("INVPrefix")+"-'||InvoiceNumber||'/"+hmapInvoiceCaptionPrefixAndSuffix.get("INVSuffix")+"' AS InvoiceNumber,InvoiceDate,TotalBeforeTaxDis,TaxAmt,TotalDis,InvoiceVal,FreeTotal,InvAfterDis,AddDis,NoCoupon,TotalCoupunAmount,TransDate,FlgInvoiceType,flgWholeSellApplicable,CycleID,flgDrctslsIndrctSls from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		String sql = "select StoreID,StoreVisitCode,TmpInvoiceCodePDA AS InvoiceNumber,InvoiceDate,TotalBeforeTaxDis,TaxAmt,TotalDis,InvoiceVal,FreeTotal,InvAfterDis,AddDis,NoCoupon,TotalCoupunAmount,TransDate,FlgInvoiceType,flgWholeSellApplicable,CycleID,flgDrctslsIndrctSls from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	public LinkedHashMap<String,String> fetch_InvoiceCaptionPrefixAndSuffix()
	{

		// open();
		LinkedHashMap<String,String>hmapInvoiceCaptionPrefixAndSuffix=new LinkedHashMap<String,String>();



		Cursor	cursor = mDataSource.db.rawQuery("SELECT ifnull(INVPrefix,''),ifnull(INVSuffix,'') FROM tblInvoiceCaption", null); //order by AutoIdOutlet Desc

		try
		{
			if(cursor.getCount()>0)
			{
				if (cursor.moveToFirst())
				{
					hmapInvoiceCaptionPrefixAndSuffix.put("INVPrefix",cursor.getString(0));
					hmapInvoiceCaptionPrefixAndSuffix.put("INVSuffix",cursor.getString(1));

				}
			}
			else
			{
				hmapInvoiceCaptionPrefixAndSuffix.put("INVPrefix","");
				hmapInvoiceCaptionPrefixAndSuffix.put("INVSuffix","");
			}

			return hmapInvoiceCaptionPrefixAndSuffix;
		}
		finally
		{
			if(cursor!=null) {
				cursor.close();
			}
			//   close();
		}
	}
	//
	private void exportCollection(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select StoreID,StoreVisitCode,PaymentMode,PaymentModeID,Amount,RefNoChequeNoTrnNo,Date,Bank,TmpInvoiceCodePDA,CollectionCode from " + tableName + " where Sstat = 3";//CollectionCode		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	/*private static final String DATABASE_CREATE_TABLE_31 = "create table tblTransac (IMEIno text not null, " +
			"TransDate string not null, StoreID text not null, ProdID text not null, Stock integer not null," +
			" OrderQty integer not null, OrderVal real not null, FreeQty integer not null, DisVal real not null, " +
			"SchemeID text null, AppliedSlab text null, AppliedAbsVal text null, Sstat integer not null, " +
			"SampleQuantity int null, ProductShortName text null, ProductPrice real null,RouteID int null,CatID text  null);";*///, DisplayUnit text null

	
	
	/*<col name="IMEIno"/>
	<col name="RouteID">7</col>
	<col name="StoreID">1</col>
	<col name="CatID">3</col>
	<col name="ProdID">36</col>
	<col name="TransDate"/>
	<col name="Stock">0</col>
	<col name="OrderQty">5</col>
	<col name="OrderVal">429.8</col>
	<col name="FreeQty">0</col>
	<col name="DisVal">0</col>
	<col name="Sstat">3</col>
	<col name="SampleQuantity">0</col>
	<col name="ProductShortName">Go_Spread_Btl_200gm</col>
	<col name="ProductPrice">85.96</col>*/


	private void exportTabletblTransac(final String tableName) throws IOException {
		LinkedHashMap<String,String> hmapInvoiceCaptionPrefixAndSuffix=fetch_InvoiceCaptionPrefixAndSuffix();
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select TmpInvoiceCodePDA AS InvoiceNumber,CatID,ProdID,ProductPrice,TaxRate,flgRuleTaxVal,OrderQty,UOMId,LineValBfrTxAftrDscnt,LineValAftrTxAftrDscnt,FreeQty,DisVal,SampleQuantity,ProductShortName,TaxValue,flgIsQuoteRateApplied,ServingDBRId,flgWholeSellApplicable,ProductExtraOrder from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}

	
	private void exportTableDayStartEndDetails(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		String sql = "select * from " + tableName + " where (DayEndFlag=1 OR ChangeRouteFlg=1)";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	private void exportSalesQuotePersonMeetTable(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select SalesQuoteId,SalesQuoteCode,SalesQuotePrcsId,SalesQuotePrcs,StoreName,Remarks,StoreId,SalesQuoteValidFrom,SalesQuoteValidTo,SalesQuoteDate,SalesQuoteType,ContactPerson,ContactPersonEmail,ContactPersonPhone,PymtStageId,Sstat,ManufacturerID,ManufacturerName from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	//
	private void exportTabletblStoreSalesOrderPaymentDetails(final String tableName) throws IOException {

		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;
		String sql = "select StoreId,PymtStageId,TmpInvoiceCodePDA,Sstat from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	private void exportTable(final String tableName) throws IOException {
	
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTableStoreReturnDetail(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	private void exportTableStoreProductAppliedSchemesBenifitsRecords(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;			
		String sql = "select * from " + tableName + " where Sstat = 3 and WhatFinallyApplied=1";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	//
	private void exportTableStoreList(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);

		String sql = "select IMEINumber,StoreID,StoreName,OwnerName,StoreContactNo,StoreAddress,StoreType, StoreLatitude,StoreLongitude,LastVisitDate,LastTransactionDate,ISNewStore,PaymentStage,DBR,StoreCity,StorePinCode,StoreState,0 AS flgRestart,"+ CommonInfo.DATABASE_VERSIONID+" AS AppVersion,StoreStateID,StoreCityID,flgOrderType from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter  AND ISNewStore=1
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();

		}

	public void UpdateNewAddedStorephotoFlag()
	{
		try
		{
			String sql = "Update tableImage SET Sstat=5 WHERE EXISTS(SELECT StoreID FROM tblStoreList WHERE(tableImage.StoreID=tblStoreList.StoreID AND tblStoreList.ISNewStore=1))  AND tableImage.Sstat=3";		// chk for flag - DB adapter
			mDataSource.db.execSQL(sql);

			String sql1 = "Update tblLatLongDetails SET Sstat=4 WHERE EXISTS(SELECT StoreID FROM tblStoreList WHERE(tblLatLongDetails.StoreID=tblStoreList.StoreID)) AND tblLatLongDetails.Sstat=3";		// chk for flag - DB adapter
			mDataSource.db.execSQL(sql1);

		}
		catch (Exception ex)
		{
			System.out.println("shivam query = "+ex.toString());
		}
	}
	private void exportTableStoreVisit(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);

		String sql = "select IMEINumber,StoreID,StoreVisitCode,ForDate,ActualLatitude,ActualLongitude,VisitTimeOutSideStore, VisitTimeInSideStore,VisitTimeCheckStore,VisitEndTS,LocProvider,Accuracy,BateryLeftStatus,StoreClose,StoreNextDay,ISNewStore,IsNewStoreDataCompleteSaved,flgFromWhereSubmitStatus,flgSubmitFromQuotation,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgStoreOrder,flgRetailerCreditBalnce,VisitTypeStatus,flgVisitCollectionMarkedStatus,SelfCreditNote from " + tableName + " where Sstat = 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}

	private void exportTableProductMappedWithSchemeSlabApplied(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;	
		// text null,  text null, 
		//StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude real null, ActualLongitude real null, VisitStartTS text null, VisitEndTS text null);";
		String sql = "select * from " + tableName + " where Sstat= 3";		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();				
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}
	
	private void exportTableNewStoreListEntries(final String tableName) throws IOException {
		xmlBuilder.openTable(tableName);
		//String sql = "select * from " + tableName;	
		//StoreID text not null, StoreType string not null, StoreName string not null, StoreLatitude real not null, StoreLongitude real not null, LastVisitDate string not null, LastTransactionDate string not null, Sstat integer not null, ForDate string not null, ActualLatitude real null, ActualLongitude real null, VisitStartTS text null, VisitEndTS text null);";
		String sql = "select * from " + tableName + " where Sstat = 3"; ;		// chk for flag - DB adapter
		Cursor c = mDataSource.db.rawQuery(sql, new String[0]);
		if (c.moveToFirst()) {
			int cols = c.getColumnCount();
			do {
				xmlBuilder.openRow();
				for (int i = 0; i < cols; i++) {
					xmlBuilder.addColumn(c.getColumnName(i), c.getString(i));
				}
				xmlBuilder.closeRow();
			} while (c.moveToNext());
		}
		c.close();
		xmlBuilder.closeTable();
	}

	private void writeToFile(final String xmlString, final String exportFileName)
			throws IOException {
		File dir = new File(Environment.getExternalStorageDirectory(),
				DatabaseAssistant.DATASUBDIRECTORY);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, exportFileName);
		file.createNewFile();

		ByteBuffer buff = ByteBuffer.wrap(xmlString.getBytes());
		FileChannel channel = new FileOutputStream(file).getChannel();
		try {
			channel.write(buff);
		} finally {
			if (channel != null) {
				channel.close();
			}
		}
	}

	/**
	 * XmlBuilder is used to write XML tags (open and close, and a few
	 * attributes) to a StringBuilder. Here we have nothing to do with IO or
	 * SQL, just a StringBuilder.
	 * 
	 * 
	 * 
	 */
	static class XmlBuilder {
		private static final String OPEN_XML_STANZA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		private static final String CLOSE_WITH_TICK = "'>";
		private static final String DB_OPEN = "<database name='";
		private static final String DB_CLOSE = "</database>";
		private static final String TABLE_OPEN = "<table name='";
		private static final String TABLE_CLOSE = "</table>";
		private static final String ROW_OPEN = "<row>";
		private static final String ROW_CLOSE = "</row>";
		private static final String COL_OPEN = "<col name='";
		private static final String COL_CLOSE = "</col>";

		private final StringBuilder sb;

		public XmlBuilder() throws IOException {
			sb = new StringBuilder();
		}

		void start(final String dbName) {
			//sb.append(XmlBuilder.OPEN_XML_STANZA);
			sb.append(XmlBuilder.DB_OPEN + dbName + XmlBuilder.CLOSE_WITH_TICK);
		}

		String end() throws IOException {
			sb.append(XmlBuilder.DB_CLOSE);
			return sb.toString();
		}

		void openTable(final String tableName) {
			sb.append(XmlBuilder.TABLE_OPEN + tableName
					+ XmlBuilder.CLOSE_WITH_TICK);
		}

		void closeTable() {
			sb.append(XmlBuilder.TABLE_CLOSE);
		}

		void openRow() {
			sb.append(XmlBuilder.ROW_OPEN);
		}

		void closeRow() {
			sb.append(XmlBuilder.ROW_CLOSE);
		}

		void addColumn(final String name, final String val) throws IOException {
			sb.append(XmlBuilder.COL_OPEN + name + XmlBuilder.CLOSE_WITH_TICK
					+ val + XmlBuilder.COL_CLOSE);
		}
	}



}