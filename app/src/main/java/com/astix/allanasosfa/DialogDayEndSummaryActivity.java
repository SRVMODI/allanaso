package com.astix.allanasosfa;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allanasosfa.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.R;
import com.allanasosfa.truetime.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class DialogDayEndSummaryActivity extends BaseActivity  implements  InterfaceRetrofit
{
	TableLayout tbl_inflate;

	LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();

	String date_value="";
	String imei="";
	String rID;
	String pickerDate="";
	String fromPage="1";

	public String back="0";

	public TableLayout tl2;
	public int bck = 0;

	public String Noti_text="Null";
	public int MsgServerID=0;

	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";

	public String fDate;
	public String[] AllDataContainer;

	Button btn_done;
	ImageView backbutton;
	TableLayout tbl_targetAchieved;

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		//mDataSource.open();
		String Noti_textWithMsgServerID= mDataSource.fetchNoti_textFromtblPDANotificationMaster();
		//mDataSource.close();

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
				AlertDialog.Builder alertDialogSaveOK = new AlertDialog.Builder(DialogDayEndSummaryActivity.this);
				alertDialogSaveOK.setTitle(getResources().getString(R.string.txtNotification));

				alertDialogSaveOK.setMessage(Noti_text);
				alertDialogSaveOK.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								long syncTIMESTAMP = System.currentTimeMillis();
								Date dateobj = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat(
										"dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
								String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(DialogDayEndSummaryActivity.this,TimeUtils.DATE_TIME_FORMAT);
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_summary);
		tbl_inflate= (TableLayout) findViewById(R.id.tbl_inflate);
		Intent extras = getIntent();

		bck = extras.getIntExtra("bck", 0);


		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
			back=extras.getStringExtra("back");
			//fromPage=extras.getStringExtra("fromPage");

		}


		imei = AppUtils.getIMEI(this);

		Date date1=new Date();
		SimpleDateFormat	sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = sdf.format(date1).toString().trim();

		btn_done= (Button) findViewById(R.id.btn_done);
		btn_done.setVisibility(View.VISIBLE);

		backbutton= (ImageView) findViewById(R.id.backbutton);
		backbutton.setVisibility(View.GONE);

		/*tbl_targetAchieved= (TableLayout) findViewById(R.id.tbl_targetAchieved);
		tbl_targetAchieved.setVisibility(View.GONE);*/

		TextView txtSalessumuryDate=(TextView)findViewById(R.id.txtSalessumuryDate);
		txtSalessumuryDate.setText(getResources().getString(R.string.txtSummaryAsOn)+fDate);


		setUpVariable();

		if(isOnline())
		{

			/*try
			{
				GetSummaryForDay task = new GetSummaryForDay(DialogDayEndSummaryActivity.this);
				task.execute();
			}
			catch (Exception e)
			{
				// TODO Autouuid-generated catch block
				e.printStackTrace();
			}*/
			try
			{
				// new GetRouteInfo().execute();
				CommonFunction.getAllSummaryReportData(DialogDayEndSummaryActivity.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(DialogDayEndSummaryActivity.this,getResources().getString(R.string.NoDataConnectionFullMsg) , Toast.LENGTH_SHORT).show();
		}


		btn_done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				AllButtonActivity.isDayEndClicked=true;
				finish();
			}
		});

	}

	public void getDataFromDatabase()
	{
		//String[] tblRowCount=mDataSource.fetchTblRowSummary();

		hmapSummaryDataNew= mDataSource.fetchTblRowSummary();

		//System.out.println("CountNew " +tblRowCount.length);
		//LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();

        int a=1;
		for (Map.Entry<String, LinkedHashMap<String, String>> entry : hmapSummaryDataNew.entrySet())
			{
				String key = entry.getKey();
				LinkedHashMap<String, String> ab = entry.getValue();

				if(a==0)
				{
					LinearLayout addSpace = new LinearLayout(DialogDayEndSummaryActivity.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 20);
					addSpace.setLayoutParams(lp);
					tbl_inflate.addView(addSpace);
				}
				a=0;

				for (Map.Entry<String, String> entry1 : ab.entrySet())
				{

					String key1 = entry1.getKey();

					String value = entry1.getValue();


					String TodaysSummary=value.split(Pattern.quote("^"))[0];
					String MTDSummary=value.split(Pattern.quote("^"))[1];
					String ColorCode=value.split(Pattern.quote("^"))[2];

					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view = inflater.inflate(R.layout.summary_row_inflate, null);



					TextView measure_val=(TextView) view.findViewById(R.id.measure_val);

					TextView txtValueAchievedToday=(TextView) view.findViewById(R.id.txtValueAchievedToday);
					TextView txtValueAchievedMTD=(TextView) view.findViewById(R.id.txtValueAchievedMTD);

					measure_val.setText(key1);
					txtValueAchievedToday.setText(TodaysSummary);
					txtValueAchievedMTD.setText(MTDSummary);

					view.setBackgroundColor(Color.parseColor(ColorCode));


						tbl_inflate.addView(view);


				}



			}


	}


	public void setUpVariable()
	{


		Button btn_Target_Achieved_Report = (Button) findViewById(R.id.btn_Target_Achieved_Report);
		btn_Target_Achieved_Report.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, TargetVsAchievedActivity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("Pagefrom", "2");
				DialogDayEndSummaryActivity.this.startActivity(intent);
				finish();

			}
		});


		ImageView but_back=(ImageView)findViewById(R.id.backbutton);
		but_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				SharedPreferences prefs = getSharedPreferences("Report",MODE_PRIVATE);
				fromPage = prefs.getString("fromPage","1");

				if(fromPage.equals("1"))
				{
					Intent ide=new Intent(DialogDayEndSummaryActivity.this,AllButtonActivity.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("imei", imei);
					ide.putExtra("rID", rID);
					startActivity(ide);
					finish();

				}
				else if(fromPage.equals("2"))
				{
					Intent ide=new Intent(DialogDayEndSummaryActivity.this,StoreSelection.class);
					ide.putExtra("userDate", date_value);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("imei", imei);
					ide.putExtra("rID", rID);
					ide.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
					startActivity(ide);
					finish();

				}


			}
		});

		Button btn_sku_wise = (Button) findViewById(R.id.btn_sku_wise);
		btn_sku_wise.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, SKUWiseSummaryReport_ByTab.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
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
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, StoreWiseSummaryReport_ByTab.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
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
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, StoreAndSKUWiseSummaryReport_ByTab.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
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
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, SKUWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
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
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, StoreWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
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
				Intent intent = new Intent(DialogDayEndSummaryActivity.this, StoreAndSKUWiseSummaryReportMTD.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", date_value);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				DialogDayEndSummaryActivity.this.startActivity(intent);
				finish();

			}
		});

	}

	@Override
	public void success() {
		getDataFromDatabase();
	}

	@Override
	public void failure() {

	}
}