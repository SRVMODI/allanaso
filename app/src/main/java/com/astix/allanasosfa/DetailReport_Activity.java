package com.astix.allanasosfa;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allanasosfa.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class DetailReport_Activity extends BaseActivity  implements  InterfaceRetrofit
{

	String date_value="";
	String imei="";
	String rID;
	String pickerDate="";
	TextView actualCalls;
	
	public String back="0";
	
	
	TextView totalOrderKg;
	TextView totalFreeKg;
	TextView totalSample;
	
	
	// by Sunil
	TextView TargetCallsValue;
	TextView TargetCallsValueMTD;
	TextView actualCallValue;
	TextView actualCallValueMTD;
	TextView actualCallOffRouteValue;
	TextView actualCallOffRouteValueMTD;
	
	TextView productiveCalls;
	TextView productiveCallsMTD;
	TextView productiveCallOffValue;
	TextView productiveCallOffValueMTD;
	TextView txtCallsRemainingValue;
	TextView txtCallsRemainingValueMTD;
	
	TextView txtAverageLinePerBill;
	TextView txtAverageLinePerBillMTD;
	
	TextView totalSalesValue;
	TextView totalSalesValueMTD;
	TextView txtTgtSalesForDayValue;
	TextView txtTgtSalesForDayValueMTD;
	TextView txtTotalDiscountValue;
	TextView txtTotalDiscountValueMTD;
	
	
	TextView txtPlacementOutletsValue;
	TextView txtPlacementOutletsValueMTD;
	TextView txtNoofStoresaddedValue;
	TextView txtNoofStoresaddedValueMTD;
	
	
	
	
	
	
	
	
	
	
	
	
	TextView productiveCallValue;
	

	TextView totalsalesValue;
	
	TextView txtTargetSalesOverallMTD;
	//TextView txtAchivedSalesTillDateValue;
	//TextView txtRunRateValue;
	//TextView ProdStoreMTDValue;
	
	TextView totalOrderLt;
	TextView totalFreeLt;
	TextView totalSampleLt;
	TextView totalDiscount;
	public TableLayout tl2; 
	public int bck = 0;
	
	public String Noti_text="Null";
	public int MsgServerID=0;
	
	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	public String fDate;
	public String[] AllDataContainer;
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK){
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME){
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_MENU){
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_SEARCH){
			  return true;
		  }

		  return super.onKeyDown(keyCode, event);
	}
	
	
	
@Override
	protected void onResume() 
      {
		// TODO Auto-generated method stub
		super.onResume();
		//mDataSource.open();
		String Noti_textWithMsgServerID= mDataSource.fetchNoti_textFromtblPDANotificationMaster();
		//mDataSource.close();
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
			AlertDialog.Builder alertDialogSaveOK = new AlertDialog.Builder(DetailReport_Activity.this);
			  alertDialogSaveOK.setTitle("Notification");
			  
			  alertDialogSaveOK.setMessage(Noti_text);
			  alertDialogSaveOK.setNeutralButton("OK",
			    new DialogInterface.OnClickListener() {
			     public void onClick(DialogInterface dialog, int which) {

			    	 long syncTIMESTAMP = System.currentTimeMillis();
						Date dateobj = new Date(syncTIMESTAMP);
						SimpleDateFormat df = new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
						String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(DetailReport_Activity.this,TimeUtils.DATE_TIME_FORMAT);
			    	 //dbengine.open();
						
						mDataSource.updatetblPDANotificationMaster(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
						//mDataSource.close();
			      dialog.dismiss();
			     
			     }
			    });
			  alertDialogSaveOK.setIcon(R.drawable.info_ico);
			  //alertDialogSaveOK.setIcon(R.drawable.error_info_ico);
			  
			  AlertDialog alert = alertDialogSaveOK.create();
			  alert.show();
			 
		}
		}
		
	}

public void setUpVariable()
{


	Button btn_Target_Achieved_Report = (Button) findViewById(R.id.btn_Target_Achieved_Report);
	if(CommonInfo.hmapAppMasterFlags.containsKey("hmapAppMasterFlags")) {
		if (CommonInfo.hmapAppMasterFlags.get("hmapAppMasterFlags") == 1) {
			btn_Target_Achieved_Report.setVisibility(View.VISIBLE);
		}
	}
	btn_Target_Achieved_Report.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(DetailReport_Activity.this, TargetVsAchievedActivity.class);
			intent.putExtra("imei", imei);
			intent.putExtra("userDate", date_value);
			intent.putExtra("pickerDate", pickerDate);
			intent.putExtra("rID", rID);
			intent.putExtra("Pagefrom", "2");
			DetailReport_Activity.this.startActivity(intent);
			finish();

		}
	});


	ImageView but_back=(ImageView)findViewById(R.id.backbutton);
	but_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent ide=new Intent(DetailReport_Activity.this,StoreSelection.class);
			//Intent ide=new Intent(DetailReport_Activity.this,StoreSelection.class);
			ide.putExtra("userDate", date_value);
			ide.putExtra("pickerDate", pickerDate);
			ide.putExtra("imei", imei);
			ide.putExtra("rID", rID);
			ide.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
			//startActivity(ide);
			startActivity(ide);
			finish();
			
			
		}
	});
	
	Button btn_sku_wise = (Button) findViewById(R.id.btn_sku_wise);
	btn_sku_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			 Intent intent = new Intent(DetailReport_Activity.this, SKUWiseSummaryReport_ByTab.class);
			intent.putExtra("imei", imei);
			intent.putExtra("userDate", date_value);
			intent.putExtra("pickerDate", pickerDate);
			intent.putExtra("rID", rID);
			DetailReport_Activity.this.startActivity(intent);
			finish();
			
		}
	});
	
	Button btn_store_wise = (Button) findViewById(R.id.btn_store_wise);
	btn_store_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			 Intent intent = new Intent(DetailReport_Activity.this, StoreWiseSummaryReport_ByTab.class);
			intent.putExtra("imei", imei);
			intent.putExtra("userDate", date_value);
			intent.putExtra("pickerDate", pickerDate);
			intent.putExtra("rID", rID);
			DetailReport_Activity.this.startActivity(intent);
			finish();
			
		}
	});
	
	Button btn_str_sku_wise = (Button) findViewById(R.id.btn_str_sku_wise);
	btn_str_sku_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			 Intent intent = new Intent(DetailReport_Activity.this, StoreAndSKUWiseSummaryReport_ByTab.class);
			intent.putExtra("imei", imei);
			intent.putExtra("userDate", date_value);
			intent.putExtra("pickerDate", pickerDate);
			intent.putExtra("rID", rID);
			DetailReport_Activity.this.startActivity(intent);
			finish();
			
		}
	});
	
	Button btn_mtd_sku_wise = (Button) findViewById(R.id.btn_mtd_sku_wise);
	btn_mtd_sku_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			 Intent intent = new Intent(DetailReport_Activity.this, SKUWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReport_Activity.this.startActivity(intent);
				finish();
			
		}
	});
	
	Button btn_mtd_store_wise = (Button) findViewById(R.id.btn_mtd_store_wise);
	btn_mtd_store_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			    Intent intent = new Intent(DetailReport_Activity.this, StoreWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReport_Activity.this.startActivity(intent);
				finish();
			
		}
	});
	
	Button btn_mtd_str_sku_wise = (Button) findViewById(R.id.btn_mtd_str_sku_wise);
	btn_mtd_str_sku_wise.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			 Intent intent = new Intent(DetailReport_Activity.this, StoreAndSKUWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DetailReport_Activity.this.startActivity(intent);
				finish();
			
		}
	});
	
}



@Override
protected void onCreate(Bundle savedInstanceState) 
        {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.today_sales_summary);
				
				//setContentView(R.layout.summary_testing);
				decimalFormat.applyPattern(pattern);
				Intent extras = getIntent();
				bck = extras.getIntExtra("bck", 0);
				
				
				if(extras !=null)
				{
				date_value=extras.getStringExtra("userDate");
				pickerDate= extras.getStringExtra("pickerDate");
				imei=extras.getStringExtra("imei");
				rID=extras.getStringExtra("rID");
				back=extras.getStringExtra("back");	
				
			    }
				
				
				 TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				 imei = tManager.getDeviceId();
				 
				 
				 imei= CommonInfo.imei.trim();
				 
					//imei="357196059648969";
					
					Date date1=new Date();
					SimpleDateFormat	sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					fDate = TimeUtils.getNetworkDateTime(DetailReport_Activity.this,TimeUtils.DATE_FORMAT);
					
					
					TextView txtSalessumuryDate=(TextView)findViewById(R.id.txtSalessumuryDate);
					txtSalessumuryDate.setText("Summary as on :"+fDate);
					
					
					//fDate="21-Nov-2015";
				//'17-Oct-2015', '867626026312250'
					
				
					
					if(Integer.parseInt(back)==1)
					{
						//mDataSource.open();
				        AllDataContainer= mDataSource.fetchAllDataFromtblAllSummary();
				        //mDataSource.close();
				        intializeFields();
					}
					else
					{
							if(isOnline())
							{
			

								try
								{
									// new GetRouteInfo().execute();
									CommonFunction.getAllSummaryReportData(DetailReport_Activity.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
							else
							{
								Toast.makeText(DetailReport_Activity.this, "Your device has no Data Connection. \n Please ensure Internet is accessible to Continue.", Toast.LENGTH_SHORT).show();
							}
                   }
				 
				
				setUpVariable();
				
				
				
          }



public void intializeFields()
{



	AllDataContainer= mDataSource.fetchAllDataFromtblAllSummary();
	
	TargetCallsValue=(TextView)findViewById(R.id.txtTargetCallsValue);
	TargetCallsValueMTD=(TextView)findViewById(R.id.txtTargetCallsValueMTD);
	
	actualCallValue=(TextView)findViewById(R.id.actualCallValue);
	actualCallValueMTD=(TextView)findViewById(R.id.actualCallValueMTD);
	
	actualCallOffRouteValue=(TextView)findViewById(R.id.actualCallOffRouteValue);
	actualCallOffRouteValueMTD=(TextView)findViewById(R.id.actualCallOffRouteValueMTD);
	
	productiveCalls=(TextView)findViewById(R.id.productiveCallValue);
	productiveCallsMTD=(TextView)findViewById(R.id.productiveCallValueMTD);
	
	productiveCallOffValue=(TextView)findViewById(R.id.productiveCallOffValue);
	productiveCallOffValueMTD=(TextView)findViewById(R.id.productiveCallOffValueMTD);
	
	txtCallsRemainingValue=(TextView)findViewById(R.id.txtCallsRemainingValue);
	txtCallsRemainingValueMTD=(TextView)findViewById(R.id.txtCallsRemainingValueMTD);
	
	txtAverageLinePerBill=(TextView)findViewById(R.id.txtAverageLinePerBill);
	txtAverageLinePerBillMTD=(TextView)findViewById(R.id.txtAverageLinePerBillMTD);
	
	totalSalesValue=(TextView)findViewById(R.id.totalsalesValue);
	totalSalesValueMTD=(TextView)findViewById(R.id.totalsalesValueMTD);
	
	txtTgtSalesForDayValue=(TextView)findViewById(R.id.txtTgtSalesForDayValue);
	txtTgtSalesForDayValueMTD=(TextView)findViewById(R.id.txtTgtSalesForDayValueMTD);
	
	txtTotalDiscountValue=(TextView)findViewById(R.id.txtTotalDiscountValue);
	txtTotalDiscountValueMTD=(TextView)findViewById(R.id.txtTotalDiscountValueMTD);
	
	
	
	txtPlacementOutletsValue=(TextView)findViewById(R.id.txtPlacementOutletsValue);
	txtPlacementOutletsValueMTD=(TextView)findViewById(R.id.txtPlacementOutletsValueMTD);
	
	
	txtNoofStoresaddedValue=(TextView)findViewById(R.id.txtNoofStoresaddedValue);
	txtNoofStoresaddedValueMTD=(TextView)findViewById(R.id.txtNoofStoresaddedValueMTD);
	
	
	
	if(AllDataContainer.length>0)
	{
		for(int i=0;i<AllDataContainer.length;i++)
		{
		
			 StringTokenizer tokens1 = new StringTokenizer(String.valueOf(AllDataContainer[i]), "^");
	         
	         String s1 = tokens1.nextToken().toString().trim();
	         String s2 = tokens1.nextToken().toString().trim();
	         String s3 = tokens1.nextToken().toString().trim();
	         String s4 = tokens1.nextToken().toString().trim();
	         
	         
	         
	       /*  initialValues.put("AutoId", AutoId);
	 		initialValues.put("Measures", Measures.trim());
	 		initialValues.put("TodaysSummary", TodaysSummary.trim());
	 		initialValues.put("MTDSummary", MTDSummary.trim());*/
	         if(i==0)
	         {
	         TargetCallsValue.setText(s3);
	         TargetCallsValueMTD.setText(s4);
	         }
	         else if(i==1)
	         {
	        	 actualCallValue.setText(s3);
	        	 actualCallValueMTD.setText(s4);
	         }
	         else if(i==2)
	         {
	        	 actualCallOffRouteValue.setText(s3);
	        	 actualCallOffRouteValueMTD.setText(s4);
	         }
	         else if(i==3)
	         {
	        	 productiveCalls.setText(s3);
	        	 productiveCallsMTD.setText(s4);
	         }
	         else if(i==4)
	         {
	        	 productiveCallOffValue.setText(s3);
	        	 productiveCallOffValueMTD.setText(s4);
	         }
	         else if(i==5)
	         {
	        	 txtCallsRemainingValue.setText(s3);
	        	 txtCallsRemainingValueMTD.setText(s4);
	         }
	         else if(i==6)
	         {
	        	 txtAverageLinePerBill.setText(s3);
	        	 txtAverageLinePerBillMTD.setText(s4);
	         }
	         else if(i==7)
	         {
	        	 totalSalesValue.setText(s3);
	        	 totalSalesValueMTD.setText(s4);
	         }
	         else if(i==8)
	         {
	        	 txtTgtSalesForDayValue.setText(s3);
	        	 txtTgtSalesForDayValueMTD.setText(s4);
	         }
	         else if(i==9)
	         {
	        	 txtTotalDiscountValue.setText(s3);
	        	 txtTotalDiscountValueMTD.setText(s4);
	         }
	        /* else if(i==10)
	         {
	        	 txtPlacementOutletsValue.setText(s3);
	        	 txtPlacementOutletsValueMTD.setText(s4);
	         }*/
	         else if(i==10)
	         {
	        	 txtNoofStoresaddedValue.setText(s3);
	        	 txtNoofStoresaddedValueMTD.setText(s4);
	         }
	         
	         
	         
	       
	     	
	 		
	}
	
	
	}
	
	
	
}

	@Override
	public void success() {

		//mDataSource.close();
		intializeFields();
	}

	@Override
	public void failure() {

	}
}