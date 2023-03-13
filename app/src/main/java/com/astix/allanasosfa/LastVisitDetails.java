package com.astix.allanasosfa;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.sancussosfa.truetime.TimeUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;


public class LastVisitDetails extends BaseActivity
{


	public String address="NA";
	public String city="NA";
	public String pincode="0";
	public String state="NA";
	TextView tv_overdueVal;
	public int flgOrderType=0;
	public static String TempStoreVisitCode="NA";
	public int flgVisitCollectionMarkedStatus=0;
	public static int battLevel=0;
	public  LinearLayout ll_gstDetails,ll_gstDependent;
	public  RadioButton rb_gst_yes,rb_gst_no,rb_pending;
	public  EditText edit_gstYes;
	public Button gst_Details_but,btn_CloseStore;
	public String StoreVisitCode="NA";
	public  ArrayList<String> GstComplianceData=new  ArrayList<String>();

	public  String flagforGstLayout="",flagforGstRadio="";
	public  String GstYesValue="";

	public String flgGSTCapture="";
	public String flgGSTCompliance="";
	public  String GSTNumberGlobal="";

	public int chkIfStoreFasQuote=0;
	public int chkIfStoreAllowedQuote=1;
	public String storeID;
	public String imei;
	public String date;
	public String pickerDate;
	public Double currLon;
	public Double currLat;
	public String selStoreName;
	public String startTS;
	public int bck = 0;
	public int checkdataForVisit=0;
	public LocationManager locationManager;
	public Location location;
	public float acc;
	public Double myCurrentLon;
	public Double myCurrentLat;
	public TableLayout tbl4_dyntable_dynprodtableQuatation;
	public TableRow tr2PG4;
	public TableLayout tbl1_dyntable_For_OrderDetails;
	public TableRow tr1PG2;
	public TableLayout tbl3_dyntable_SchemeApplicable;
	public TableRow tr2PG2;
	public TableLayout tbl3_dyntable_SpecialSchemeApplicable;
	public TableRow tr2PG2_SpecialScheme;
	public String Noti_text="Null";
	public int MsgServerID=0;
	public TableLayout tbl2_dyntable_For_LastVisitDate;
	public TableRow tr3PG2;
	public TableLayout tbl1_dyntable_For_ExecutionDetails;
	public TableRow ExecutionRow;
	public String lastVisitDate="";
	public String lastOrderDate="";
	LinkedHashMap<String, String> hmapStoreBasicDetails=new LinkedHashMap<String, String>();
	String[] strInvoiceData;
	LinearLayout ll_InvoiceLastVisit,ll_inflateInvoiceData;
	LinkedHashMap<String, String> hmapDistinctSalesQuotePersonMeetMstr=new LinkedHashMap<String, String>();
	Float locACC;
	LinkedHashMap<String, String> hmapAllValuesOfPaymentMode;
	CheckBox chBoxView,AdvanceBeforeDeliveryCheckBoxNew,OnDeliveryCheckBoxNew,CreditCheckBoxNew;
	LinearLayout ll_data,parentOfAdvanceBeforeDeliveryPayMentMode,parentOfOnDeliveryPayMentMode,parentOfCreditPayMentMode,parentOfCheckBox;
	TextView PaymentStageTextView,paymentModeTextviewNew,creditdaysTextboxNew,CreditlimitTextboxNew,percentageTextviewNew,paymentstagetextviewNew, CreditDaysTextbox, PaymentModeTextView, Date,SalesQuoteTypeSpinner,ValFrom,ValTo,SalesQuoteType,ValidityFrom,PaymentTerms,headerstring;
	EditText percentageOfAdvanceBeforeDelivery,percentageOfOnDelivery,creditDaysOfOnDelivery,PercentageOfCredit,creditDaysOfCredit,creditLimitOfCredit;
	LinkedHashMap<String,String> hmapZoneDisplayMstr;
	StoreSelection aa;

	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	TextView tv_outstandingvalue;

	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {

			battLevel =intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try
		{

			////mDataSource.open();
			String Noti_textWithMsgServerID=mDataSource.fetchNoti_textFromtblPDANotificationMaster();
			//mDataSource.close();
			System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
			if(!Noti_textWithMsgServerID.equals("Null"))
			{
				StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

				MsgServerID= Integer.parseInt(token.nextToken().trim());
				Noti_text= token.nextToken().trim();


				if(Noti_text.equals("") || Noti_text.equals("Null"))
				{

				}
				else
				{
					final AlertDialog builder = new AlertDialog.Builder(LastVisitDetails.this).create();

					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View openDialog = inflater.inflate(R.layout.custom_dialog, null);
					openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

					builder.setCancelable(false);
					TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
					final TextView msg=(TextView)openDialog. findViewById(R.id.msg);

					final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
					final Button cancel=(Button)openDialog. findViewById(R.id.but_no);

					cancel.setVisibility(View.GONE);
					header_text.setText(getText(R.string.AlertDialogHeaderMsg));
					msg.setText(Noti_text);

					ok_but.setText(getText(R.string.AlertDialogOkButton));

					builder.setView(openDialog,0,0,0,0);

					ok_but.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View arg0)
						{
							// TODO Auto-generated method stub

							long syncTIMESTAMP = System.currentTimeMillis();
							Date dateobj = new Date(syncTIMESTAMP);
							SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
							String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;

							////mDataSource.open();
							mDataSource.updatetblPDANotificationMaster(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
							//mDataSource.close();

							try
							{
								////mDataSource.open();
								int checkleftNoti=mDataSource.countNumberOFNotificationtblPDANotificationMaster();
								if(checkleftNoti>0)
								{
									String Noti_textWithMsgServerID=mDataSource.fetchNoti_textFromtblPDANotificationMaster();
									System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
									if(!Noti_textWithMsgServerID.equals("Null"))
									{
										StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

										MsgServerID= Integer.parseInt(token.nextToken().trim());
										Noti_text= token.nextToken().trim();

										//mDataSource.close();
										if(Noti_text.equals("") || Noti_text.equals("Null"))
										{

										}
										else
										{
											msg.setText(Noti_text);
										}
									}

								}
								else
								{
									builder.dismiss();
								}

							}
							catch(Exception e)
							{

							}
							finally
							{
								//mDataSource.close();

							}


						}
					});




					builder.show();






				}
			}
		}
		catch(Exception e)
		{

		}
	}

	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle(R.string.genTermInformation);
		alertDialog.setIcon(R.drawable.error_info_ico);

		// Setting Dialog Message
		alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);

		// On pressing Settings button
		alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public void StoreNameAndSalesPersonInfo()
	{
		hmapStoreBasicDetails=mDataSource.fngetStoreBasicDetails(storeID);
		TextView storeName = (TextView)findViewById(R.id.txt_storeSummary);
		TextView txt_SalesPersonName_Value = (TextView)findViewById(R.id.txt_SalesPersonName_Value);
		TextView txt_SalesPersonContact_Value = (TextView)findViewById(R.id.txt_SalesPersonContact_Value);

		TextView txt_StoreOwner_Value = (TextView)findViewById(R.id.txt_StoreOwner_Value);
		TextView txt_StoreOwnerContact_Value = (TextView)findViewById(R.id.txt_StoreOwnerContact_Value);

		TextView txt_StoreCatType_Value = (TextView)findViewById(R.id.txt_StoreCatType_Value);
		TextView txt_SalesPersonAddress_Value = (TextView)findViewById(R.id.txt_SalesPersonAddress_Value);
		storeName.setText(hmapStoreBasicDetails.get("StoreName")+" "+getText(R.string.Summary));
		txt_StoreOwner_Value.setText(hmapStoreBasicDetails.get("OwnerName"));
		txt_StoreOwnerContact_Value.setText(hmapStoreBasicDetails.get("StoreContactNo"));
		txt_StoreCatType_Value.setText(hmapStoreBasicDetails.get("StoreCatType"));
		txt_SalesPersonName_Value.setText(hmapStoreBasicDetails.get("SalesPersonName"));
		txt_SalesPersonContact_Value.setText(hmapStoreBasicDetails.get("SalesPersonContact"));
		txt_SalesPersonAddress_Value.setText(hmapStoreBasicDetails.get("StoreAddress"));
	}

	public String  getStoreVisitCode()
	{
		String mrouteID=mDataSource.fnGetRouteIDAgainstStoreID(storeID);
		String mrouteType=mDataSource.FetchRouteType(mrouteID);
		/*int flgDrctslsIndrctSls=1;
		int ISNewStore =mDataSource.fncheckStoreIsNewOrOld(storeID);

		if(CommonInfo.FlgDSRSO==1)
		{
			String SOCoverageAreaIDAndType=mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();
			flgDrctslsIndrctSls=mDataSource.fnGetflgDrctslsIndrctSls(Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[0]),Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[1]));
		}
		else
		{
			flgDrctslsIndrctSls=mDataSource.fnGetflgDrctslsIndrctSls(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
		}*/

		if(CommonInfo.flgDrctslsIndrctSls==2)
		{
			int flgCheckIfVisitExists=mDataSource.fnGetCountStoreVisitCode(storeID);
			if(flgCheckIfVisitExists==0)
			{
				String passdLevel = battLevel + "%";
				StoreVisitCode=genStoreVisitCode();

				mDataSource.fnInsertOrUpdate_tblStoreVisitMstr(StoreVisitCode,storeID,1,getDateInMonthTextFormat(),"0","0",getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),"","0",passdLevel,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						flgVisitCollectionMarkedStatus,address,city,pincode,state,mrouteID,mrouteType);
				mDataSource.fnInsertOrUpdate_tblStoreOrderVisitDayActivity(StoreVisitCode,storeID,StoreVisitCode,getDateInMonthTextFormat(),1,getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond());
				TempStoreVisitCode=StoreVisitCode;
			}
			else
			{
				StoreVisitCode=mDataSource.fnGetStoreVisitCodeInCaseOfIndrectSales(storeID);
				if(mDataSource.fnGetStoreTempStoreVisitCodeFromtblStoreOrderVisitDayActivity(storeID).equals("NA"))
				{
					TempStoreVisitCode=genStoreTempVisitCode();
					mDataSource.fnInsertOrUpdate_tblStoreOrderVisitDayActivity(StoreVisitCode,storeID,TempStoreVisitCode,getDateInMonthTextFormat(),1,getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond());

				}
				else
				{
					TempStoreVisitCode=mDataSource.fnGetStoreTempStoreVisitCodeFromtblStoreOrderVisitDayActivity(storeID);
				}
			}
		}
		else
		{
			int StoreCurrentOutsStat=mDataSource.fnGetStoreCurrentOutsStat(storeID);
			if(StoreCurrentOutsStat!=1)
			{
				String passdLevel = battLevel + "%";
				StoreVisitCode=genStoreVisitCode();
				mDataSource.fnInsertOrUpdate_tblStoreVisitMstr(StoreVisitCode,storeID,1,getDateInMonthTextFormat(),"0","0",getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond(),"","0",passdLevel,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
						flgVisitCollectionMarkedStatus,address,city,pincode,state,mrouteID,mrouteType);
				mDataSource.fnInsertOrUpdate_tblStoreOrderVisitDayActivity(StoreVisitCode,storeID,StoreVisitCode,getDateInMonthTextFormat(),1,getDateAndTimeInMilliSecond(),getDateAndTimeInMilliSecond());
				//mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);
			}
			else
			{
				StoreVisitCode=mDataSource.fnGetStoreVisitCode(storeID);
			}
		}
		return StoreVisitCode;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last_visit_details);

		CommonInfo.ActiveRouteSM="0";


		registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);

		decimalFormat.applyPattern(pattern);
		Intent passedvals = getIntent();
		bck = passedvals.getIntExtra("bck", 0);
		// aa=(StoreSelection)getIntent().getSerializableExtra("MyClass");

		if(bck == 1)
		{
			selStoreName = passedvals.getStringExtra("SN");
			storeID = passedvals.getStringExtra("storeID");
			imei = passedvals.getStringExtra("imei");
			date = passedvals.getStringExtra("userdate");
			pickerDate= passedvals.getStringExtra("pickerDate");
		}
		else
		{
			storeID = passedvals.getStringExtra("storeID");
			imei = passedvals.getStringExtra("imei");
			date = passedvals.getStringExtra("userDate");
			pickerDate= passedvals.getStringExtra("pickerDate");
			locACC = passedvals.getFloatExtra("acc", 0);
			currLon = passedvals.getDoubleExtra("currUsrLon", 0);
			currLat = passedvals.getDoubleExtra("currUsrLat", 0);
			selStoreName = passedvals.getStringExtra("selStoreName");
			startTS = passedvals.getStringExtra("startTS");
		}


		/*IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);*/
		StoreNameAndSalesPersonInfo();
		long syncTIMESTAMP;
		String fullTS;

		syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fullTS = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_FORMAT);;

		ll_InvoiceLastVisit=(LinearLayout) findViewById(R.id.ll_InvoiceLastVisit);
		ll_inflateInvoiceData=(LinearLayout) findViewById(R.id.ll_inflateInvoiceData);

		    /*LinkedHashMap<String, String> hmapListQuoteISOfUnmappedWithProducts= mDataSource.fnGetListQuoteISOfUnmappedWithProducts(storeID);
		    if(hmapListQuoteISOfUnmappedWithProducts.size()>0)
		    {
		    	for(Map.Entry<String, String> entry:hmapListQuoteISOfUnmappedWithProducts.entrySet())
				{
		    	mDataSource.fndeleteQuoteISOfUnmappedWithProducts(entry.getKey());
				}
		    }
		    */
		/*int StoreCurrentOutsStat=mDataSource.fnGetStoreCurrentOutsStat(storeID);

			if(StoreCurrentOutsStat!=1)
			{
				StoreVisitCode=genStoreVisitCode();
			}
			else {
				StoreVisitCode=mDataSource.fnGetStoreVisitCode(storeID);
			}*/


		Button quotationBtn=	(Button) findViewById(R.id.quotationBtn);
		quotationBtn.setVisibility(View.GONE);
			/*chkIfStoreFasQuote=mDataSource.fnchkIfStoreFasQuote(storeID);
			if(chkIfStoreFasQuote==1)
			{
				mDataSource.fnDeletefromtblSchemeStoreMappingAgainstStore(storeID);
			}

			chkIfStoreAllowedQuote=mDataSource.fnchkIfStoreAllowQuotation(storeID);
			if(chkIfStoreAllowedQuote==0)
			{
				quotationBtn.setVisibility(View.GONE);
			}*/

		/*TextView tvPreCreditAmt = (TextView)findViewById(R.id.pre_credit_amount_value);
		TextView tvBalAmt = (TextView)findViewById(R.id.pre_balance_amount_value);


		////mDataSource.open();
		Double PreCreditAmt =mDataSource.fnGetLastCreditAmountFromLastInvoiceTable(storeID);
		//mDataSource.close();

			Double.parseDouble(decimalFormat.format(PreCreditAmt));//Double.parseDouble(new DecimalFormat("##.##").format(PreCreditAmt));


			tvPreCreditAmt.setText(""+PreCreditAmt);
			////mDataSource.open();
		String BalAmt =mDataSource.fnGetPDALastInvoiceDetDueAmt(storeID);
		//mDataSource.close();
		Double BalAmtNew=0.00;
		if(BalAmt.equals(""))
		{
			BalAmt="0.00";
		}
		BalAmtNew=Double.parseDouble(BalAmt);
		if(BalAmtNew>0.0)
		{
		BalAmtNew = Double.parseDouble(new DecimalFormat("##.##").format(BalAmtNew));
		}
		Double.parseDouble(decimalFormat.format(BalAmtNew));
		tvBalAmt.setText(""+ BalAmtNew);*/

		/*////mDataSource.open();
		String lastVisitDate=mDataSource.fnGettblFirstOrderDetailsOnLastVisitDetailsActivity(storeID);
		//mDataSource.close();*/

		////mDataSource.open();
		checkdataForVisit=mDataSource.counttblForPDAGetLastVisitDate(storeID);
		//mDataSource.close();

		TextView txt_visitDate_Value = (TextView)findViewById(R.id.txt_visitDate_Value);

		if(checkdataForVisit==1)
		{
			////mDataSource.open();
			String lastVisitDateAndFlgOrder=mDataSource.fnGetVisitDateAndflgOrderFromtblForPDAGetLastVisitDate(storeID);
			StringTokenizer tokens = new StringTokenizer(String.valueOf(lastVisitDateAndFlgOrder), "^");

			lastVisitDate=tokens.nextToken().trim();
			int flgOrder=Integer.parseInt(tokens.nextToken().trim());

			if(flgOrder==1)
			{
				txt_visitDate_Value.setBackgroundColor(Color.YELLOW);
			}
			else
			{
				txt_visitDate_Value.setBackgroundColor(Color.RED);
			}

			//mDataSource.close();
		}

		if(lastVisitDate.equals(""))
		{
			txt_visitDate_Value.setText("NA");

		}
		else if(lastVisitDate.equals("0"))
		{
			txt_visitDate_Value.setText("NA");
		}
		else if(lastVisitDate.equals("NA"))
		{
			txt_visitDate_Value.setText("NA");
		}
		else
		{
			txt_visitDate_Value.setText(lastVisitDate);

		}

		final Button btn_Cancel=(Button) findViewById(R.id.btn_Cancel);

		int valSstatValueAgainstStore=0;
		try
		{
			////mDataSource.open();
			valSstatValueAgainstStore=mDataSource.fnGetStatValueagainstStore(storeID);
			//mDataSource.close();
			if(valSstatValueAgainstStore==0)
			{
				btn_Cancel.setVisibility(View.VISIBLE);
			}
			else
			{
				btn_Cancel.setVisibility(View.GONE);

			}

		}catch(Exception e)
		{

		}finally
		{

		}

		btn_Cancel.setOnClickListener(new OnClickListener() {
			//  wer
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub





				AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(LastVisitDetails.this);
				alertDialogSyncError.setTitle(getText(R.string.genTermInformation));
				alertDialogSyncError.setCancelable(false);  // try submitting the details from outside the door
				alertDialogSyncError.setMessage(getText(R.string.CancelVisitDealer));
				alertDialogSyncError.setPositiveButton(getText(R.string.AlertDialogOkButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{

								int flag=0;
								//getStoreVisitCode();
								String passdLevel = battLevel + "%";
								mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);


								Intent storeSaveIntent = new Intent(LastVisitDetails.this, LauncherActivity.class);
								startActivity(storeSaveIntent);
								finish();
							}

						});
				alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogCancelButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {


								dialog.dismiss();

							}
						});
				alertDialogSyncError.setIcon(R.drawable.info_ico);

				AlertDialog alert = alertDialogSyncError.create();
				alert.show();



			}
		});

		Button visitDate_butn=(Button)findViewById(R.id.txt_visitDate_Details);
		visitDate_butn.setOnClickListener(new OnClickListener(
		) {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				//getStoreVisitCode();
				////mDataSource.open();
				int checkDataForVisitDetails=mDataSource.counttblForPDAGetLastVisitDetails(storeID);
				int checkDataForOrderDetails=mDataSource.counttblForPDAGetLastOrderDetails(storeID);
				//mDataSource.close();
				String passdLevel = battLevel + "%";
				mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);
				if(checkDataForVisitDetails!=0 || checkDataForOrderDetails!=0)
				{
					Intent nxtP4 = new Intent(LastVisitDetails.this,LastVisitDetailsSecondPart.class);
					nxtP4.putExtra("storeID", storeID);
					nxtP4.putExtra("SN", selStoreName);
					nxtP4.putExtra("imei", imei);
					nxtP4.putExtra("userdate", date);
					nxtP4.putExtra("pickerDate", pickerDate);
					startActivity(nxtP4);
					finish();
				}
				else
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LastVisitDetails.this);
					alertDialogBuilder.setTitle(getText(R.string.genTermInformation));
					alertDialogBuilder.setMessage(getText(R.string.NoDataAvlblForOrder));

					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {}
							});


					alertDialogBuilder.setIcon(R.drawable.info_ico);
					// create an alert dialog
					AlertDialog alert = alertDialogBuilder.create();
					alert.show();
				}

			}
		});

		//LastVisitDateValue



		TextView orderDate_Value = (TextView)findViewById(R.id.txt_orderDate_Value);

		////mDataSource.open();
		int checkdataForOrder=mDataSource.counttblForPDAGetLastOrderDate(storeID);
		//mDataSource.close();

		if(checkdataForOrder==1)
		{
			////mDataSource.open();
			String lastOrderDateAndflgExecutionSummary=mDataSource.fnGettblForPDAGetLastOrderDate(storeID);
			//mDataSource.close();


			StringTokenizer tokens = new StringTokenizer(String.valueOf(lastOrderDateAndflgExecutionSummary), "^");

			lastOrderDate=tokens.nextToken().trim();
			int flgExecutionSummary=Integer.parseInt(tokens.nextToken().trim());

			if(flgExecutionSummary==1)
			{
				orderDate_Value.setBackgroundColor(Color.YELLOW);
			}
			else if(flgExecutionSummary==2)
			{
				orderDate_Value.setBackgroundColor(Color.RED);
			}
			else
			{
				orderDate_Value.setBackgroundColor(Color.MAGENTA);
			}



		}










		if(lastOrderDate.equals(""))
		{
			orderDate_Value.setText("NA");

		}
		else if(lastOrderDate.equals("0"))
		{
			orderDate_Value.setText("NA");
		}
		else if(lastOrderDate.equals("NA"))
		{
			orderDate_Value.setText("NA");
		}
		else
		{
			orderDate_Value.setText(lastOrderDate);

		}





		Button executionDetails_butn=(Button)findViewById(R.id.txt_execution_Details);

		if(CommonInfo.hmapAppMasterFlags.containsKey("flgExecutionIsAvailable"))
		{
			if(CommonInfo.hmapAppMasterFlags.get("flgExecutionIsAvailable")==1)
			{
				executionDetails_butn.setVisibility(View.VISIBLE);
				TextView txt_execution_Value=(TextView)findViewById(R.id.txt_execution_Value);
				txt_execution_Value.setVisibility(View.INVISIBLE);
				TextView txt_execution=(TextView)findViewById(R.id.txt_execution);
				txt_execution.setVisibility(View.VISIBLE);
			}
		}

		executionDetails_butn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				LayoutInflater layoutInflater = LayoutInflater.from(LastVisitDetails.this);
				View promptView = layoutInflater.inflate(R.layout.lastsummary_execution, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LastVisitDetails.this);


				alertDialogBuilder.setTitle(getText(R.string.genTermInformation));



				////mDataSource.open();

				String DateResult[]=mDataSource.fetchOrderDateFromtblForPDAGetExecutionSummary(storeID);
				String LastexecutionDetail[]=mDataSource.fetchAllDataFromtbltblForPDAGetExecutionSummary(storeID);

				String PrdNameDetail[]=mDataSource.fetchPrdNameFromtblForPDAGetExecutionSummary(storeID);

				String ProductIDDetail[]=mDataSource.fetchProductIDFromtblForPDAGetExecutionSummary(storeID);

				System.out.println("Ashish and Anuj LastexecutionDetail : "+LastexecutionDetail.length);
				//mDataSource.close();

				if(DateResult.length>0)
				{
					TextView FirstDate = (TextView)promptView.findViewById(R.id.FirstDate);
					TextView SecondDate = (TextView)promptView.findViewById(R.id.SecondDate);
					TextView ThirdDate = (TextView)promptView.findViewById(R.id.ThirdDate);

					TextView lastExecution = (TextView)promptView.findViewById(R.id.lastExecution);
					lastExecution.setText(LastVisitDetails.this.getResources().getString(R.string.lastvisitdetails_last)+
							DateResult.length+LastVisitDetails.this.getResources().getString(R.string.ExecSummary));

					if(DateResult.length==1)
					{
						FirstDate.setText(""+DateResult[0]);
						SecondDate.setVisibility(View.GONE);
						ThirdDate.setVisibility(View.GONE);
					}
					else if(DateResult.length==2)
					{
						FirstDate.setText(""+DateResult[0]);
						SecondDate.setText(""+DateResult[1]);
						ThirdDate.setVisibility(View.GONE);
					}
					else if(DateResult.length==3)
					{
						FirstDate.setText(""+DateResult[0]);
						SecondDate.setText(""+DateResult[1]);
						ThirdDate.setText(""+DateResult[2]);
					}
				}

				LayoutInflater inflater = getLayoutInflater();

				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				double x = Math.pow(dm.widthPixels/dm.xdpi,2);
				double y = Math.pow(dm.heightPixels/dm.ydpi,2);
				double screenInches = Math.sqrt(x+y);
				if(LastexecutionDetail.length>0)
				{
					alertDialogBuilder.setView(promptView);

					tbl1_dyntable_For_ExecutionDetails = (TableLayout) promptView.findViewById(R.id.dyntable_For_ExecutionDetails);
					TableRow row1 = (TableRow)inflater.inflate(R.layout.table_execution_head, tbl1_dyntable_For_OrderDetails, false);

					TextView firstDateOrder = (TextView)row1.findViewById(R.id.firstDateOrder);
					TextView firstDateInvoice = (TextView)row1.findViewById(R.id.firstDateInvoice);
					TextView secondDateOrder = (TextView)row1.findViewById(R.id.secondDateOrder);
					TextView secondDateInvoice = (TextView)row1.findViewById(R.id.secondDateInvoice);
					TextView thirdDateOrder = (TextView)row1.findViewById(R.id.thirdDateOrder);
					TextView thirdDateInvoice = (TextView)row1.findViewById(R.id.thirdDateInvoice);
					if(DateResult.length>0)
					{
						if(DateResult.length==1)
						{

							secondDateOrder.setVisibility(View.GONE);
							secondDateInvoice.setVisibility(View.GONE);
							thirdDateOrder.setVisibility(View.GONE);
							thirdDateInvoice.setVisibility(View.GONE);
						}
						else if(DateResult.length==2)
						{
							thirdDateOrder.setVisibility(View.GONE);
							thirdDateInvoice.setVisibility(View.GONE);
						}
					}

					tbl1_dyntable_For_ExecutionDetails.addView(row1);


					for (int current = 0; current <= (PrdNameDetail.length - 1); current++)
					{


						final TableRow row = (TableRow)inflater.inflate(R.layout.table_execution_row, tbl1_dyntable_For_OrderDetails, false);

						TextView tv1 = (TextView)row.findViewById(R.id.skuName);
						TextView tv2 = (TextView)row.findViewById(R.id.firstDateOrder);
						TextView tv3 = (TextView)row.findViewById(R.id.firstDateInvoice);
						TextView tv4 = (TextView)row.findViewById(R.id.secondDateOrder);
						TextView tv5 = (TextView)row.findViewById(R.id.secondDateInvoice);
						TextView tv6 = (TextView)row.findViewById(R.id.thirdDateOrder);
						TextView tv7 = (TextView)row.findViewById(R.id.thirdDateInvoice);

						tv1.setText(PrdNameDetail[current]);

						if(DateResult.length>0)
						{
							if(DateResult.length==1)
							{
								tv4.setVisibility(View.GONE);
								tv5.setVisibility(View.GONE);
								tv6.setVisibility(View.GONE);
								tv7.setVisibility(View.GONE);
								////mDataSource.open();
								String abc[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number "+abc.length);
								//System.out.println("Check Value Number12 "+DateResult[0]);
								if(abc.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
									tv2.setText(tokens.nextToken().trim());
									tv3.setText(tokens.nextToken().trim());
								}
								else
								{
									tv2.setText("0");
									tv3.setText("0");
								}
							}
							else if(DateResult.length==2)
							{
								tv6.setVisibility(View.GONE);
								tv7.setVisibility(View.GONE);

								////mDataSource.open();
								String abc[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number "+abc.length);
								//System.out.println("Check Value Number12 "+DateResult[0]);
								if(abc.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
									tv2.setText(tokens.nextToken().trim());
									tv3.setText(tokens.nextToken().trim());
								}
								else
								{
									tv2.setText("0");
									tv3.setText("0");
								}

								////mDataSource.open();
								String abc1[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[1],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number NEw "+abc1.length);
								//System.out.println("Check Value Number12 NEw "+DateResult[1]);
								if(abc1.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
									tv4.setText(tokens.nextToken().trim());
									tv5.setText(tokens.nextToken().trim());
								}
								else
								{
									tv4.setText("0");
									tv5.setText("0");
								}





							}
							else if(DateResult.length==3)
							{
								////mDataSource.open();
								String abc[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number "+abc.length);
								//System.out.println("Check Value Number12 "+DateResult[0]);
								if(abc.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
									tv2.setText(tokens.nextToken().trim());
									tv3.setText(tokens.nextToken().trim());
								}
								else
								{
									tv2.setText("0");
									tv3.setText("0");
								}

								////mDataSource.open();
								String abc1[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[1],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number NEw "+abc1.length);
								//System.out.println("Check Value Number12 NEw "+DateResult[1]);
								if(abc1.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
									tv4.setText(tokens.nextToken().trim());
									tv5.setText(tokens.nextToken().trim());
								}
								else
								{
									tv4.setText("0");
									tv5.setText("0");
								}

								////mDataSource.open();
								String abc2[]=mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[2],ProductIDDetail[current]);
								//mDataSource.close();

								//System.out.println("Check Value Number NEw "+abc2.length);
								//System.out.println("Check Value Number12 NEw "+DateResult[2]);
								if(abc2.length>0)
								{
									StringTokenizer tokens = new StringTokenizer(String.valueOf(abc2[0]), "_");
									tv6.setText(tokens.nextToken().trim());
									tv7.setText(tokens.nextToken().trim());
								}
								else
								{
									tv6.setText("0");
									tv7.setText("0");
								}





							}
							else
							{

							}
						}

					/*if(screenInches>6.5)
					{
						tv1.setTextSize(14);
						tv2.setTextSize(14);
						tv3.setTextSize(14);
						tv4.setTextSize(14);
						tv5.setTextSize(14);
						tv6.setTextSize(14);
						tv7.setTextSize(14);
					}
					else
					{

					}*/

						//System.out.println("Abhinav Raj LTDdet[current]:"+LTDdet[current]);
					/*StringTokenizer tokens = new StringTokenizer(String.valueOf(LastexecutionDetail[current]), "_");

					tv1.setText(tokens.nextToken().trim());
					tv2.setText(tokens.nextToken().trim());
					tokens.nextToken().trim();
					tv3.setText(tokens.nextToken().trim());*/
					/*tv4.setText(tokens.nextToken().trim());
					tv5.setText(tokens.nextToken().trim());*/
						tbl1_dyntable_For_ExecutionDetails.addView(row);

					}

				}
				else
				{
					alertDialogBuilder.setMessage(getText(R.string.AlertExecNoSum));
				}
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {}
						});


				alertDialogBuilder.setIcon(R.drawable.info_ico);
				// create an alert dialog
				AlertDialog alert = alertDialogBuilder.create();
				alert.show();



			}
		});




		/*
		////mDataSource.open();
		int checkdata=mDataSource.counttblSecondVisitDetailsOnLastVisitDetailsActivity(storeID);
		//mDataSource.close();

		if(checkdata==1)
		{
		////mDataSource.open();
		String lastVisitDate=mDataSource.fnGettblSecondVisitDetailsOnLastVisitDetailsActivity(storeID);
		LastVisitDateValue.setText(lastVisitDate);
		//mDataSource.close();

		tbl2_dyntable_For_LastVisitDate = (TableLayout) findViewById(R.id.dyntable_For_LastVisitDate);

		////mDataSource.open();
		String LastVisitDetails[] = mDataSource.fetchtblSecondVisitDetailsOnLastVisitDetailsActivity(storeID);
		//mDataSource.close();

		LayoutInflater inflater3 = getLayoutInflater();
		for (int current2 = 0; current2 <= (LastVisitDetails.length - 1); current2++)
		{

			final TableRow row2 = (TableRow)inflater3.inflate(R.layout.trans_row3_second_pg, tbl2_dyntable_For_LastVisitDate, false);


			StringTokenizer tokens = new StringTokenizer(String.valueOf(LastVisitDetails[current2]), "_");

			TextView tv1 = (TextView)row2.findViewById(R.id.tv32ndPageProduct);
			TextView tv2 = (TextView)row2.findViewById(R.id.tv32ndPageTarget);
			if(screenInches>6.5)
			{
				tv1.setTextSize(14);
				tv2.setTextSize(14);

			}
			else
			{

			}

			tv1.setText(tokens.nextToken().trim());
			tv2.setText(tokens.nextToken().trim());



			tbl2_dyntable_For_LastVisitDate.addView(row2);
		}
	}
	else
	{
		TextView txtLastVisitDate=(TextView)findViewById(R.id.txtLastVisitDate);
		TextView LastVisitDateValue=(TextView)findViewById(R.id.LastVisitDateValue);
		RelativeLayout RelativeLayoutLastVisitDate=(RelativeLayout)findViewById(R.id.relativeLayoutLastVisitDate);
		txtLastVisitDate.setVisibility(View.GONE);
		LastVisitDateValue.setVisibility(View.GONE);
		RelativeLayoutLastVisitDate.setVisibility(View.GONE);
		TextView txtLastOrderDate=(TextView)findViewById(R.id.textView1_TSHeader);
		txtLastOrderDate.setText("Last Order/Visit Date : ");
	}*/


		tbl3_dyntable_SchemeApplicable = (TableLayout) findViewById(R.id.dyntable_SchemeApplicable);

		////mDataSource.open();
		String LTschApp[] = mDataSource.PrevPDASchemeApplicableSecondPage(storeID);
		//mDataSource.close();

		if(LTschApp.length>0)
		{
			TextView txt_GeneralScheme_Value = (TextView)findViewById(R.id.txt_GeneralScheme_Value);
			txt_GeneralScheme_Value.setVisibility(View.GONE);
			RelativeLayout relLayout_for_generalScheme = (RelativeLayout)findViewById(R.id.relLayout_for_generalScheme);
			relLayout_for_generalScheme.setVisibility(View.VISIBLE);

		}
		else
		{
			TextView txt_GeneralScheme_Value = (TextView)findViewById(R.id.txt_GeneralScheme_Value);

			RelativeLayout relLayout_for_generalScheme = (RelativeLayout)findViewById(R.id.relLayout_for_generalScheme);
			relLayout_for_generalScheme.setVisibility(View.GONE);
			// set Visibility gone for Godrej Project Acc. to Avinash Sir
			//txt_GeneralScheme_Value.setVisibility(View.VISIBLE);
			txt_GeneralScheme_Value.setVisibility(View.GONE);
		}

		if(CommonInfo.hmapAppMasterFlags.containsKey("flgSchemeAvailable") && CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartSchemeDetails"))
		{
			if(CommonInfo.hmapAppMasterFlags.get("flgSchemeAvailable")==1 && CommonInfo.hmapAppMasterFlags.get("flgVisitStartSchemeDetails")==1)
			{
				TextView txt_generalScheme = (TextView)findViewById(R.id.txt_generalScheme);
				txt_generalScheme.setVisibility(View.VISIBLE);
				TextView txt_GeneralScheme_Value = (TextView)findViewById(R.id.txt_GeneralScheme_Value);
				txt_GeneralScheme_Value.setVisibility(View.VISIBLE);
				RelativeLayout relLayout_for_generalScheme = (RelativeLayout)findViewById(R.id.relLayout_for_generalScheme);
				relLayout_for_generalScheme.setVisibility(View.VISIBLE);

			/*	TextView txt_specialScheme = (TextView)findViewById(R.id.txt_specialScheme);
				txt_specialScheme.setVisibility(View.VISIBLE);
				TextView txt_specialScheme_Value = (TextView)findViewById(R.id.txt_specialScheme_Value);
				txt_GeneralScheme_Value.setVisibility(View.VISIBLE);
				RelativeLayout relLayout_for_specialScheme = (RelativeLayout)findViewById(R.id.relLayout_for_specialScheme);
				relLayout_for_specialScheme.setVisibility(View.VISIBLE);*/
			}
		}

		if(CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartOutstandingDetails")) {
			if (CommonInfo.hmapAppMasterFlags.get("flgVisitStartOutstandingDetails") == 0) {
				TextView txt_OutStandingDetailsDesc = (TextView)findViewById(R.id.txt_OutStandingDetailsDesc);
				txt_OutStandingDetailsDesc.setVisibility(View.GONE);
				LinearLayout ll_outstanding = (LinearLayout)findViewById(R.id.ll_outstanding);
				ll_outstanding.setVisibility(View.GONE);
			}
		}


		LayoutInflater inflater2 = getLayoutInflater();
		for (int current2 = 0; current2 <= (LTschApp.length - 1); current2++)
		{

			final TableRow row2 = (TableRow)inflater2.inflate(R.layout.trans_row2_second_pg, tbl3_dyntable_SchemeApplicable, false);


			StringTokenizer token = new StringTokenizer(String.valueOf(LTschApp[current2]), "_");

			String SchemeId=token.nextToken().toString().trim();
			String SchemeDesc=token.nextToken().toString().trim();

			TextView tv1 = (TextView)row2.findViewById(R.id.tv2PG2SCHDES);

			tv1.setTag(SchemeId+"_"+SchemeDesc);

			//tv1.setText(SchemeDesc);

			tv1.setTextColor(Color.parseColor("#303F9F"));
			tv1.setTypeface(null, Typeface.BOLD);
			String mystring=SchemeDesc;
			SpannableString content = new SpannableString(mystring);
			content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
			tv1.setText(content);


			tv1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//v.getTag();

					CustomAlertBoxForSchemeDetails(v.getTag().toString());



				}
			});



			tbl3_dyntable_SchemeApplicable.addView(row2);
		}

		tbl3_dyntable_SpecialSchemeApplicable = (TableLayout) findViewById(R.id.dyntable_SpecialSchemeApplicable);

		////mDataSource.open();
		String SpecialScheme[] = mDataSource.PrevPDASchemeApplicableSecondPageSpecialScheme(storeID);
		//mDataSource.close();

		if(SpecialScheme.length>0)
		{
			RelativeLayout relLayout_for_specialScheme = (RelativeLayout)findViewById(R.id.relLayout_for_specialScheme);
			relLayout_for_specialScheme.setVisibility(View.VISIBLE);
			TextView txt_specialScheme_Value = (TextView)findViewById(R.id.txt_specialScheme_Value);
			txt_specialScheme_Value.setVisibility(View.GONE);


		}
		else
		{
			RelativeLayout relLayout_for_specialScheme = (RelativeLayout)findViewById(R.id.relLayout_for_specialScheme);
			relLayout_for_specialScheme.setVisibility(View.GONE);
			TextView txt_specialScheme_Value = (TextView)findViewById(R.id.txt_specialScheme_Value);

			// set Visibility gone for Godrej Project Acc. to Avinash Sir
			//txt_specialScheme_Value.setVisibility(View.VISIBLE);
			txt_specialScheme_Value.setVisibility(View.GONE);

		}

		LayoutInflater inflater2_SpecialScheme = getLayoutInflater();
		for (int current2 = 0; current2 <= (SpecialScheme.length - 1); current2++)
		{

			final TableRow row2 = (TableRow)inflater2_SpecialScheme.inflate(R.layout.trans_row2_second_pg, tbl3_dyntable_SpecialSchemeApplicable, false);

			TextView tv1 = (TextView)row2.findViewById(R.id.tv2PG2SCHDES);

			tv1.setText(SpecialScheme[current2].toString().trim());
			/*if(screenInches>6.5)
			{
				tv1.setTextSize(14);

			}
			else
			{

			}*/

			tbl3_dyntable_SpecialSchemeApplicable.addView(row2);
		}



		Button nxtP4 = (Button)findViewById(R.id.nxtP4);
		Button prevP2 = (Button)findViewById(R.id.prevP2);


		prevP2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				//	getStoreVisitCode();
				String passdLevel = battLevel + "%";
				mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);
				////mDataSource.open();
				String rID=mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
				//mDataSource.close();

				Intent prevP2 = new Intent(LastVisitDetails.this, StoreSelection.class);

				//Location_Getting_Service.closeFlag = 0;
				prevP2.putExtra("imei", imei);
				prevP2.putExtra("userDate", date);
				prevP2.putExtra("pickerDate", pickerDate);
				prevP2.putExtra("rID", rID);
				startActivity(prevP2);
				finish();



			}
		});

		nxtP4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				String[] checkClosrOrNext = mDataSource.checkStoreCloseOrNextMethod(storeID);
				if (checkClosrOrNext.length!=0) {
					if (Integer.parseInt(checkClosrOrNext[0]) == 1)
						showAlertForClosedStore(getResources().getString(R.string.genTermPleaseSelectDiffrentStore));
					else
						startActualVisit();
				}else
					startActualVisit();

			}
		});



		quotationBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				Intent intents=new Intent(LastVisitDetails.this,QuotationActivity.class);
				intents.putExtra("quatationFlag", "NEW");  //Update
				intents.putExtra("SalesQuoteId", "Null");
				intents.putExtra("storeID", storeID);
				intents.putExtra("SN", selStoreName);
				intents.putExtra("imei", imei);
				intents.putExtra("userdate", date);
				intents.putExtra("pickerDate", pickerDate);
				intents.putExtra("prcID", "NULL");
				CommonInfo.SalesQuoteId="BLANK";
				CommonInfo.prcID="NULL";
				CommonInfo.newQuottionID="NULL";
				CommonInfo.globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";


				startActivity(intents);
				finish();

			}
		});

		// setQuotationList();
		paymentSectionInitialize();



		ll_gstDetails=(LinearLayout) findViewById(R.id.ll_gstDetails);
		ll_gstDependent=(LinearLayout) findViewById(R.id.ll_gstDependent);
		edit_gstYes=(EditText) findViewById(R.id.edit_gstYes);
		gst_Details_but=(Button) findViewById(R.id.gst_Details_but);

		rb_gst_yes=(RadioButton) findViewById(R.id.rb_gst_yes);
		rb_gst_no=(RadioButton) findViewById(R.id.rb_gst_no);
		rb_pending=(RadioButton) findViewById(R.id.rb_pending);
		RadioBtnGSTFunctionality();
		btn_CloseStore= (Button) findViewById(R.id.btn_CloseStore);

		/*int checkVisitType=mDataSource.checkVisitTypeStatus(storeID,StoreVisitCode);
		if(checkVisitType==0)
		{
			btn_CloseStore.setVisibility(View.VISIBLE);
		}
		else
		{
			btn_CloseStore.setVisibility(View.GONE);
		}
*/

		try
		{
			////mDataSource.open();
			valSstatValueAgainstStore=mDataSource.fnGetStatValueagainstStore(storeID);
			//mDataSource.close();
			if(valSstatValueAgainstStore==0)
			{
				btn_CloseStore.setVisibility(View.VISIBLE);
			}
			else
			{
				btn_CloseStore.setVisibility(View.GONE);

			}

		}catch(Exception e)
		{
			e.printStackTrace();

		}finally
		{

		}


		btn_CloseStore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				getStoreVisitCode();
				String passdLevel = battLevel + "%";
				mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);
				Intent nxtP4=new Intent(LastVisitDetails.this,StoreClosedActivity.class);
				nxtP4.putExtra("storeID", storeID);
				nxtP4.putExtra("SN", selStoreName);
				nxtP4.putExtra("imei", imei);
				nxtP4.putExtra("userdate", date);
				nxtP4.putExtra("pickerDate", pickerDate);
				startActivity(nxtP4);
				finish();
			}
		});
		tv_outstandingvalue=(TextView) findViewById(R.id.tv_outstandingvalue);
	/*	Double outstandingvalue=mDataSource.fnGetStoretblLastOutstanding(storeID);
		tv_outstandingvalue.setText(""+outstandingvalue);*/

		Double cntAllOustandings=mDataSource.fetch_Store_AllOustandings(storeID);
		cntAllOustandings=Double.parseDouble(new DecimalFormat("##.##").format(cntAllOustandings));


		Double cntTotCollectionAmtAgainstStoreIrespectiveOfVisit=mDataSource.fnTotCollectionAmtAgainstStoreIrespectiveOfVisit(storeID);
		cntTotCollectionAmtAgainstStoreIrespectiveOfVisit=Double.parseDouble(new DecimalFormat("##.##").format(cntTotCollectionAmtAgainstStoreIrespectiveOfVisit));




		Double cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit=mDataSource.fnTotInvoicesAmtAgainstStoreIrespectiveOfVisit(storeID);
		cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit=Double.parseDouble(new DecimalFormat("##.##").format(cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit));



		Double totOutstandingValue=cntAllOustandings+cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit-cntTotCollectionAmtAgainstStoreIrespectiveOfVisit;
		totOutstandingValue=Double.parseDouble(new DecimalFormat("##.##").format(totOutstandingValue));
		tv_outstandingvalue.setText(" : "+String.format("%.2f", totOutstandingValue));


		tv_overdueVal=(TextView) findViewById(R.id.tv_overdueVal);
		Double overdueBal=mDataSource.fnGetStoretblLastOverDue(storeID);
		tv_overdueVal.setText(""+overdueBal);

		setInvoiceData();

		int flgOrderType=mDataSource.fnGetflgOrderTypeAgainstStore(storeID);
		if(flgOrderType==0)
		{
			RedirectPageToPRoductEntryDirectly();
		}
		//getStoreVisitCode();

	}

	public void showAlertForClosedStore(String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
				.setMessage(msg)
				.setCancelable(false)
				.setIcon(R.drawable.info_ico)
				.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
						mDataSource.UpdateStoreStoreClose(storeID,0);
						startActualVisit();
					}
				}).setNegativeButton(getResources().getString(R.string.txtCancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}

	private void startActualVisit(){
		if (validate())
		{
			getStoreVisitCode();
			String flgGSTCompliance="NA";
			// String flgGSTCapture="NA";
			String GSTNumber="NA";


			String passdLevel = battLevel + "%";
			mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);

			if(ll_gstDetails.getVisibility()==View.VISIBLE)
			{
				flgGSTCapture="1";
			}
			else if(ll_gstDetails.getVisibility()==View.GONE)
			{
				flgGSTCapture="0";
			}

			if(rb_gst_yes.isChecked())
			{
				flgGSTCompliance="1";
				if(!edit_gstYes.getText().toString().trim().equals(null) ||!edit_gstYes.getText().toString().trim().equals(""))
				{
					GSTNumber=edit_gstYes.getText().toString().trim();
				}
			}
			else if(rb_gst_no.isChecked())
			{
				flgGSTCompliance="0";
			}
			else if(rb_pending.isChecked())
			{
				flgGSTCompliance="2";
			}
			mDataSource.UpdateStoreInfoGST(storeID,flgGSTCapture,flgGSTCompliance,GSTNumber);

			long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobjNew = new Date(syncTIMESTAMP);
			SimpleDateFormat dfnew = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
			String startTS = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;


			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;


			CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Next Button Click on last Visit Details"+StartClickTimeFinal;



			////mDataSource.open();
			mDataSource.UpdateStoreEndVisit(storeID,startTS);
			//mDataSource.close();
			if(CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable"))
			{
				if(CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable")==1)
				{
					Intent nxtP4 = new Intent(LastVisitDetails.this,ActualVisitStock.class);
					nxtP4.putExtra("storeID", storeID);
					nxtP4.putExtra("SN", selStoreName);
					nxtP4.putExtra("imei", imei);
					nxtP4.putExtra("userdate", date);
					nxtP4.putExtra("pickerDate", pickerDate);
					nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
					startActivity(nxtP4);
					finish();
				}
				else {
					Intent nxtP4 = new Intent(LastVisitDetails.this,ProductEntryForm.class);
					//Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
					nxtP4.putExtra("storeID", storeID);
					nxtP4.putExtra("SN", selStoreName);
					nxtP4.putExtra("imei", imei);
					nxtP4.putExtra("userdate", date);
					nxtP4.putExtra("pickerDate", pickerDate);
					nxtP4.putExtra("flgOrderType", 1);
					startActivity(nxtP4);
					finish();
				}
			}
			else
			{
				Intent nxtP4 = new Intent(LastVisitDetails.this,ProductEntryForm.class);
				//Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
				nxtP4.putExtra("storeID", storeID);
				nxtP4.putExtra("SN", selStoreName);
				nxtP4.putExtra("imei", imei);
				nxtP4.putExtra("userdate", date);
				nxtP4.putExtra("pickerDate", pickerDate);
				nxtP4.putExtra("flgOrderType", 1);
				startActivity(nxtP4);
				finish();
			}


					/*Intent nxtP4 = new Intent(LastVisitDetails.this,ProductEntryForm.class);
					//Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
					nxtP4.putExtra("storeID", storeID);
					nxtP4.putExtra("SN", selStoreName);
					nxtP4.putExtra("imei", imei);
					nxtP4.putExtra("userdate", date);
					nxtP4.putExtra("pickerDate", pickerDate);
					nxtP4.putExtra("flgOrderType", 1);
					startActivity(nxtP4);
					finish();*/
		}

	}
	public  void RedirectPageToPRoductEntryDirectly()
	{
		if (validate())
		{
			getStoreVisitCode();
			String flgGSTCompliance="NA";
			// String flgGSTCapture="NA";
			String GSTNumber="NA";


			String passdLevel = battLevel + "%";
			mDataSource.UpdateStoreVisitBattVisitWise(storeID,passdLevel,StoreVisitCode);

			if(ll_gstDetails.getVisibility()==View.VISIBLE)
			{
				flgGSTCapture="1";
			}
			else if(ll_gstDetails.getVisibility()==View.GONE)
			{
				flgGSTCapture="0";
			}

			if(rb_gst_yes.isChecked())
			{
				flgGSTCompliance="1";
				if(!edit_gstYes.getText().toString().trim().equals(null) ||!edit_gstYes.getText().toString().trim().equals(""))
				{
					GSTNumber=edit_gstYes.getText().toString().trim();
				}
			}
			else if(rb_gst_no.isChecked())
			{
				flgGSTCompliance="0";
			}
			else if(rb_pending.isChecked())
			{
				flgGSTCompliance="2";
			}
			mDataSource.UpdateStoreInfoGST(storeID,flgGSTCapture,flgGSTCompliance,GSTNumber);

			long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobjNew = new Date(syncTIMESTAMP);
			SimpleDateFormat dfnew = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
			String startTS = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;


			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;


			CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Next Button Click on last Visit Details"+StartClickTimeFinal;



			////mDataSource.open();
			mDataSource.UpdateStoreEndVisit(storeID,startTS);
			//mDataSource.close();
					/*	Intent nxtP4 = new Intent(LastVisitDetails.this,ActualVisitStock.class);
						//Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
						nxtP4.putExtra("storeID", storeID);
						nxtP4.putExtra("SN", selStoreName);
						nxtP4.putExtra("imei", imei);
						nxtP4.putExtra("userdate", date);
						nxtP4.putExtra("pickerDate", pickerDate);
						startActivity(nxtP4);
						finish();*/

			Intent nxtP4 = new Intent(LastVisitDetails.this,ProductEntryForm.class);
			//Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
			nxtP4.putExtra("storeID", storeID);
			nxtP4.putExtra("SN", selStoreName);
			nxtP4.putExtra("imei", imei);
			nxtP4.putExtra("userdate", date);
			nxtP4.putExtra("pickerDate", pickerDate);
			nxtP4.putExtra("flgOrderType", 0);
			startActivity(nxtP4);
			finish();
		}
	}

	private boolean validate()
	{
		if(flgGSTCapture.equals("1") && rb_gst_no.isChecked()== false && rb_gst_yes.isChecked()== false && rb_pending.isChecked()== false)
		{
			showAlertSingleButtonError(LastVisitDetails.this.getResources().getString(R.string.SelectGst));
			return false;
		}
		else if(rb_gst_yes.isChecked()== true && edit_gstYes.getText().toString().trim().equals(null))
		{
			showAlertSingleButtonError(LastVisitDetails.this.getResources().getString(R.string.FillGst));
			return false;
		}
		else if(rb_gst_yes.isChecked()== true && edit_gstYes.getText().toString().trim().equals("NA"))
		{
			showAlertSingleButtonError(LastVisitDetails.this.getResources().getString(R.string.FillGst));
			return false;
		}
		else if(rb_gst_yes.isChecked()== true && edit_gstYes.getText().toString().trim().equals("0"))
		{
			showAlertSingleButtonError(LastVisitDetails.this.getResources().getString(R.string.FillGst));
			return false;
		}
		else if(rb_gst_yes.isChecked()== true && edit_gstYes.getText().toString().trim().equals(""))
		{
			showAlertSingleButtonError(LastVisitDetails.this.getResources().getString(R.string.FillGst));
			return false;
		}

		else
		{


			return true;
		}


	}

	public void RadioBtnGSTFunctionality()
	{

		gst_Details_but.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub


				//flgGSTCompliance="1";
				if(flgGSTCompliance.equals("1"))
				{
					final AlertDialog builder = new AlertDialog.Builder(LastVisitDetails.this).create();


					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View openDialog = inflater.inflate(R.layout.custom_dialog_with_edittext, null);
					openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

					builder.setCancelable(false);
					TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
					final TextView msg=(TextView)openDialog. findViewById(R.id.msg);

					final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
					final Button cancel=(Button)openDialog. findViewById(R.id.but_no);

					String GSTNUmber="";
					if(TextUtils.isEmpty(edit_gstYes.getText()))
					{
						GSTNUmber="";
					}
					else
					{
						GSTNUmber=edit_gstYes.getText().toString().trim();
					}

					final EditText editTextmobile=(EditText)openDialog. findViewById(R.id.editTextmobile);
					editTextmobile.setText(GSTNUmber);
					editTextmobile.setEnabled(true);

					if(!TextUtils.isEmpty(edit_gstYes.getText()))
					{
						int pos = edit_gstYes.getText().length();
						editTextmobile.setSelection(pos);
					}


					//cancel.setVisibility(View.GONE);
					header_text.setText(getText(R.string.AlertDialogHeaderMsg));
					msg.setText(getText(R.string.EnterGst));

					ok_but.setText(getText(R.string.Confirm));
					cancel.setText(getText(R.string.AlertDialogCancelButton));

					builder.setView(openDialog,0,0,0,0);

					ok_but.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View arg0)
						{

							if(ok_but.getText().toString().trim().equals("Confirm"))
							{
								if(TextUtils.isEmpty(editTextmobile.getText()))
								{
									msg.setText(getText(R.string.PleaseEnterGstToProc));
									cancel.setVisibility(View.GONE);
									editTextmobile.setVisibility(View.GONE);
									ok_but.setText(getText(R.string.AlertDialogOkButton));

								}

								else
								{
									// TODO Auto-generated method stub
									if(!TextUtils.isEmpty(editTextmobile.getText()))
									{
										edit_gstYes.setText(editTextmobile.getText());
									}
									builder.dismiss();
								}
							}
							else
							{
								msg.setText(getText(R.string.EnterGst));
								cancel.setVisibility(View.VISIBLE);
								editTextmobile.setVisibility(View.VISIBLE);
								editTextmobile.setEnabled(true);
								ok_but.setText(getText(R.string.Confirm));
							}
						}
					});
					cancel.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View arg0)
						{
							// TODO Auto-generated method stub
							//builder.dismiss();

							builder.dismiss();
							// editTextmobile.setEnabled(true);
							//editTextmobile.setFocusable(true);
							if(!TextUtils.isEmpty(editTextmobile.getText()))
							{
								edit_gstYes.setText(editTextmobile.getText());
							}



									/* if(!TextUtils.isEmpty(editTextmobile.getText()))
									 {
										 int pos = editTextmobile.getText().length();
										 editTextmobile.setSelection(pos);
									 }*/


						}
					});

					builder.setIcon(R.drawable.info_icon);


					builder.show();
				}

			}
		});

		GstComplianceData=mDataSource.getGstDataByStore(storeID);

		flgGSTCapture=GstComplianceData.get(0);
		flgGSTCompliance=GstComplianceData.get(1);
		String GSTNumber=GstComplianceData.get(2);
		GSTNumberGlobal=GSTNumber;

		int flgServerRecord=mDataSource.fnGetflgServerRecordFromOutletMstr(storeID);


		if(flgGSTCapture.equals("1"))
		{
			//set Visibility gone for Godrej acc. to Avinash Sir
			//ll_gstDetails.setVisibility(View.VISIBLE);
			ll_gstDetails.setVisibility(View.GONE);

			if(flgGSTCompliance.equals("0"))
			{
				rb_gst_no.setEnabled(true);
				rb_gst_yes.setEnabled(true);
				rb_pending.setEnabled(true);
				rb_gst_no.setChecked(true);
				ll_gstDependent.setVisibility(View.GONE);
				edit_gstYes.setText("");
			}
			else if(flgGSTCompliance.equals("1"))
			{
				rb_gst_yes.setChecked(true);
				ll_gstDependent.setVisibility(View.VISIBLE);
				if(GSTNumber.equals("NA") || GSTNumber.equals("0") || GSTNumber.trim().equals(""))
				{
					edit_gstYes.setText("");
				}
				else
				{
					edit_gstYes.setText(GSTNumber);
				}
				// edit_gstYes.setEnabled(true);
				if(flgServerRecord==0)
				{
					rb_gst_no.setEnabled(true);
					rb_gst_yes.setEnabled(true);
					rb_pending.setEnabled(true);
					edit_gstYes.setEnabled(true);
					gst_Details_but.setVisibility(View.GONE);
				}
				else
				{
					rb_gst_no.setEnabled(false);
					rb_gst_yes.setEnabled(false);
					rb_pending.setEnabled(false);
					edit_gstYes.setEnabled(false);
					gst_Details_but.setVisibility(View.VISIBLE);
					if(TextUtils.isEmpty(edit_gstYes.getText()))
					{
						gst_Details_but.setVisibility(View.GONE);
						edit_gstYes.setEnabled(true);
						TextView gst_compliance_txt=(TextView)findViewById(R.id.gst_compliance_txt);
						gst_compliance_txt.setVisibility(View.VISIBLE);
						rb_gst_yes.setVisibility(View.VISIBLE);
						rb_gst_no.setVisibility(View.VISIBLE);
						rb_pending.setVisibility(View.VISIBLE);
					}
					else
					{
						TextView gst_compliance_txt=(TextView)findViewById(R.id.gst_compliance_txt);
						gst_compliance_txt.setVisibility(View.GONE);
						rb_gst_yes.setVisibility(View.GONE);
						rb_gst_no.setVisibility(View.GONE);
						rb_pending.setVisibility(View.GONE);
					}
				}


			}
			else if(flgGSTCompliance.equals("2"))
			{
				rb_gst_no.setEnabled(true);
				rb_gst_yes.setEnabled(true);
				rb_pending.setEnabled(true);
				rb_pending.setChecked(true);
				ll_gstDependent.setVisibility(View.GONE);
				edit_gstYes.setText("");
			}
		}
		else if(flgGSTCapture.equals("0"))
		{
			ll_gstDetails.setVisibility(View.GONE);
			edit_gstYes.setText("");
			rb_gst_no.setChecked(false);
			rb_gst_yes.setChecked(false);
			rb_pending.setChecked(false);

		}


		rb_gst_no.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(rb_gst_no.isChecked())
				{
					flgGSTCompliance="0";
					rb_gst_no.setChecked(true);
					rb_gst_yes.setChecked(false);
					rb_pending.setChecked(false);
					ll_gstDependent.setVisibility(View.GONE);
					edit_gstYes.setText("");
					GSTNumberGlobal="0";

				}
			}
		});

		rb_pending.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(rb_pending.isChecked())
				{
					flgGSTCompliance="2";
					rb_gst_no.setChecked(false);
					rb_gst_yes.setChecked(false);
					rb_pending.setChecked(true);
					ll_gstDependent.setVisibility(View.GONE);
					edit_gstYes.setText("");
					GSTNumberGlobal="0";

				}
			}
		});

		rb_gst_yes.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(rb_gst_yes.isChecked())
				{
					flgGSTCompliance="1";
					rb_gst_yes.setChecked(true);
					rb_gst_no.setChecked(false);
					rb_pending.setChecked(false);
					ll_gstDependent.setVisibility(View.VISIBLE);


					if(!TextUtils.isEmpty(GSTNumberGlobal))
					{
						/*if(!GSTNumberGlobal.equals("NA") || !GSTNumberGlobal.equals("0"))
						{
							edit_gstYes.setText(GSTNumberGlobal);
						}*/

						if(GSTNumberGlobal.equals("NA") || GSTNumberGlobal.equals("0"))
						{
							edit_gstYes.setText("");
						}
						else
						{
							edit_gstYes.setText(GSTNumberGlobal);
						}
					}

					if(TextUtils.isEmpty(edit_gstYes.getText()))
					{
						gst_Details_but.setVisibility(View.GONE);
						edit_gstYes.setEnabled(true);
					}


				}
			}
		});
	}


	public void paymentSectionInitialize() {}
	public void disableAllCheckBoxOfPaymentMode(LinearLayout parentofPaymentModeCheckboxes) {
		// TODO Auto-generated method stub
		int count = parentofPaymentModeCheckboxes.getChildCount();

		for (int ui=0;ui<count;ui++)
		{
			View ch = parentofPaymentModeCheckboxes.getChildAt(ui);
			if (ch instanceof CheckBox)
			{
				ch.setEnabled(false);

			}
		}

	}
	public void fillValuesInPaymentSection(String allValuesOfPaymentStageID) {}

	public void checkBoxCreationwhenPageLoading(String paymentStageID) {}

	public void CustomAlertBoxForSchemeDetails(String TagValue)
	{
		// ////mDataSource.open();
		String SchemeId=(TagValue.split(Pattern.quote("_")))[0];
		String SchemeDesc=(TagValue.split(Pattern.quote("_")))[1];

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		ScrollView scroll = new ScrollView(this);

		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));


		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(parms);
		layout.setGravity(Gravity.CLIP_VERTICAL);
		layout.setPadding(2,2,2,0);
		layout.setBackgroundColor(Color.WHITE);



		TextView tv = new TextView(this);
		tv.setText(getText(R.string.genTermInformation));
		tv.setPadding(40, 10, 40, 10);
		tv.setBackgroundColor(Color.parseColor("#486FA8"));
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);
		tv.setTextColor(Color.parseColor("#ffffff"));


		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		tv1Params.bottomMargin = 5;





		for(int i=0;i<1;i++)
		{

			LinearLayout ChildViewDynamic = new LinearLayout(LastVisitDetails.this);
			ChildViewDynamic.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,25f));
			ChildViewDynamic.setOrientation(LinearLayout.VERTICAL);
			ChildViewDynamic.setWeightSum(25f);

			TextView tv1 = new TextView(this);
			tv1.setTextColor(Color.BLACK);
			tv1.setBackgroundColor(Color.parseColor("#FFFEFC"));
			SchemeDesc=SchemeDesc;
			//tv1.setText("Scheme Name :"+SchemeDesc);
			tv1.setTextColor(Color.parseColor("#303F9F"));
			tv1.setTypeface(null, Typeface.BOLD);
			String mystring="Scheme Name :"+SchemeDesc;
			SpannableString content = new SpannableString(mystring);
			content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
			tv1.setText(content);

			ChildViewDynamic.addView(tv1,tv1Params);



			String AllSchemeSlabID[]=mDataSource.fnGetAllSchSlabbasedOnSchemeID(SchemeId);

			// below two line for Testing,  please comment below two line for live
			// hmapSchemeSlabIdSlabDes.put("62", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");
			// hmapSchemeSlabIdSlabDes.put("63", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");


			// hmapSchmeSlabIdSlabDes

			// hmapSchemeSlabIdBenifitDes.put("62", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");
			// hmapSchemeSlabIdBenifitDes.put("63", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");

			int k=0;
			for(int j=0;j<AllSchemeSlabID.length;j++)   // change 3 into SchmSlabId.length which i got hmapSchmSlabIdSchmID (Length of SchmSlabId)
			{

				k=j+1;

				// System.out.println("List of all SchemeSlabID :"+AllSchemeSlabID[j]);

				TextView tv2 = new TextView(this);
				tv2.setTextColor(Color.BLACK);
				tv2.setBackgroundColor(Color.parseColor("#FFFEFC"));
				String aa[]=mDataSource.fnGetAllSchSlabDescbasedOnSchemeSlabID(AllSchemeSlabID[j]);
				tv2.setText("Slab "+k+"  :"+aa[0]); // It is for Live
				//  tv2.setText("Slab "+k+"  :"+hmapSchemeSlabIdSlabDes.get("62"));  // It is for Testing
				tv2.setTextColor(Color.parseColor("#E65100"));

				ChildViewDynamic.addView(tv2,tv1Params);



				TextView tv3 = new TextView(this);
				tv3.setTextColor(Color.BLACK);
				tv3.setBackgroundColor(Color.parseColor("#FFFEFC"));
				String bb[]=mDataSource.fnGetAllBenifitDescrbasedOnSchemeSlabID(AllSchemeSlabID[j]);
				tv3.setText("Benifit :"+bb[0]);  // It is for Live
				// tv3.setText("Benifit :"+hmapSchemeSlabIdBenifitDes.get("62"));   // It is for Testing
				tv3.setTextColor(Color.parseColor("#3BA1B3"));


				ChildViewDynamic.addView(tv3,tv1Params);



			}




			layout.addView(ChildViewDynamic,tv1Params);
		}

		scroll.addView(layout);





		alertDialogBuilder.setView(scroll);
		alertDialogBuilder.setCustomTitle(tv);
		alertDialogBuilder.setIcon(R.drawable.info_ico);
		alertDialogBuilder.setCancelable(false);



		// Setting Positive "Yes" Button
		alertDialogBuilder.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				// //mDataSource.close();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();


		try {
			alertDialog.show();
		} catch (Exception e) {
			// WindowManager$BadTokenException will be caught and the app would
			// not display the 'Force Close' message
			e.printStackTrace();
		}




	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();



		this.unregisterReceiver(this.mBatInfoReceiver);
	}




	public void setInvoiceData(){

		strInvoiceData=mDataSource.fetch_Store_tblInvoiceLastVisitDetails(storeID);

		String val[]=new String[strInvoiceData.length];
		if(CommonInfo.flgDrctslsIndrctSls==1) {
			if (strInvoiceData.length > 0) {
				for (int i = 0; i < strInvoiceData.length; i++) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view = inflater.inflate(R.layout.inflate_row_invoice_lastvisit, null);

					TextView InvCode = (TextView) view.findViewById(R.id.InvCode);
					TextView InvDate = (TextView) view.findViewById(R.id.InvDate);
					TextView OutStandingAmnt = (TextView) view.findViewById(R.id.OutStandingAmnt);
					TextView AmntOverDue = (TextView) view.findViewById(R.id.AmntOverDue);


					StringTokenizer tokens = new StringTokenizer(String.valueOf(strInvoiceData[i]), "^");


					String strInvCode = tokens.nextToken().toString().trim();
					String strInvDate = tokens.nextToken().toString().trim();
					String strOutstandingAmt = tokens.nextToken().toString().trim();
					String strAmtOverdue = tokens.nextToken().toString().trim();
					Double AmtOverdue = Double.parseDouble(strAmtOverdue);

					InvCode.setText(strInvCode);
					InvDate.setText(strInvDate);
					OutStandingAmnt.setText(String.format("%.2f", Double.parseDouble(strOutstandingAmt)));
					AmntOverDue.setText(String.format("%.2f", AmtOverdue));


					ll_inflateInvoiceData.addView(view);

				}

			} else {
				ll_InvoiceLastVisit.setVisibility(View.GONE);
			}
		}
		else
		{
			ll_InvoiceLastVisit.setVisibility(View.GONE);
		}
	}

	public String genStoreVisitCode()
	{
		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String VisitStartTS = TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;
		String cxz;
		cxz = UUID.randomUUID().toString();


		StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

		String val1 = tokens.nextToken().trim();
		String val2 = tokens.nextToken().trim();
		String val3 = tokens.nextToken().trim();
		String val4 = tokens.nextToken().trim();
		cxz = tokens.nextToken().trim();

		String IMEIid =  CommonInfo.imei.substring(9);
		cxz = "StoreVisitCode" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


		return cxz;

	}


	public String genStoreTempVisitCode()
	{
		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String VisitStartTS =TimeUtils.getNetworkDateTime(LastVisitDetails.this, TimeUtils.DATE_TIME_FORMAT);;
		String cxz;
		cxz = UUID.randomUUID().toString();


		StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

		String val1 = tokens.nextToken().trim();
		String val2 = tokens.nextToken().trim();
		String val3 = tokens.nextToken().trim();
		String val4 = tokens.nextToken().trim();
		cxz = tokens.nextToken().trim();

		String IMEIid =  CommonInfo.imei.substring(9);
		cxz = "StoreVisitCode" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


		return cxz;

	}
}
