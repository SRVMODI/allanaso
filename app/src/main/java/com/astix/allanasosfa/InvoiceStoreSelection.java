package com.astix.allanasosfa;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.allanasosfa.truetime.TimeUtils;
import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.utils.AppUtils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;


public class InvoiceStoreSelection extends Activity implements OnItemSelectedListener, ListAdapter.customButtonListener, InterfaceRetrofit
{
	public static String activityFrom="AllButtonActivity";
	LinkedHashMap<String,String> hmapRsnForCncl=new LinkedHashMap<String,String>();
	ArrayList<String> listRsnForCncl=new ArrayList<String>();
	String idSelectedRsn="0";
	public EditText et_Reason;
	Spinner spnrCnclRsn;
	View alertLayout;
	public String imei;
	public String currSysDate;
	public String pickerDate;
	public String[] distributor_name;
	public  String[] distributor_name_id;
	public  String selected_distributor_name_id="0";
	public String[] route_name;
	public  String[] route_name_id;
	public  String selected_route_name_id="0";
	public String[] StoreID;
	public String[] StoreIDOrderID;
	public String[] StoreSstat;
	public String[] OrderID;
	public String[] StorenameList;

	ProgressDialog mProgressDialog;
	public boolean[] checksCancelInvoices;

	public boolean[] checksConformInvoices;

	int spinnerRouteValue;
	int spinnerDistributrValue;
	ArrayAdapter<String> adapterRoute;
	ArrayAdapter<String> adapterDisrt;

	Spinner Spinner_Distributor;
	Spinner Spinner_Route;
	public HashMap<String, String> hmap=new HashMap<String, String>();
	public HashMap<String, String> hmapStoreIdName=new HashMap<String, String>();
	public HashMap<String, String> hmapStoreIdAndInvoiceOtherDetails=new HashMap<String, String>();
	public HashMap<String, String> hmapStoreIdAndInvoiceReturnRemaks=new HashMap<String, String>();
	public HashMap<String, String> hmapConfirmCancel=new HashMap<String, String>();
	public long syncTIMESTAMP;
	public String fullFileName1;

	String[] StorenameOther;
	int closeList = 0;
	int whatTask = 0;
	String whereTo = "11";

	public String[] StoreList2Procs;

	public String[] StoreConformInvoiceOrder;


	ArrayList<String> stIDs;
	ArrayList<String> stNames;
	ArrayList mSelectedItems = new ArrayList();

	ArrayList mSelectedItemsConfornInvoiceOrders = new ArrayList();

	String strReason="";
	String[] branch_name;
	public EditText editText;
	public int inTest=0;
	ListAdapter adapter;
	InvoiceDatabaseAssistant DA = new InvoiceDatabaseAssistant(this);

	AppDataSource dbengine;// = new AppDataSource(this);
	String[] StoreName;
	ArrayList<String> dataItems = new ArrayList<String>();
	public String Noti_text="Null";
	public int MsgServerID=0;

	TextView refresh_tv;


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try
		{

			/*dbengine.open();
			String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
			dbengine.close();
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



					final AlertDialog builder = new AlertDialog.Builder(InvoiceStoreSelection.this).create();


					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View openDialog = inflater.inflate(R.layout.custom_dialog, null);
					openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

					builder.setCancelable(false);
					TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
					final TextView msg=(TextView)openDialog. findViewById(R.id.msg);

					final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
					final Button cancel=(Button)openDialog. findViewById(R.id.but_no);

					cancel.setVisibility(View.GONE);
					header_text.setText("Alert");
					msg.setText(Noti_text);

					ok_but.setText("OK");

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
							String Noti_ReadDateTime = df.format(dateobj);

							dbengine.open();
							dbengine.updatetblNotificationMstr(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
							dbengine.close();

							try
							{
								dbengine.open();
								int checkleftNoti=dbengine.countNumberOFNotificationtblNotificationMstr();
								if(checkleftNoti>0)
								{
									String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
									System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
									if(!Noti_textWithMsgServerID.equals("Null"))
									{
										StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

										MsgServerID= Integer.parseInt(token.nextToken().trim());
										Noti_text= token.nextToken().trim();

										dbengine.close();
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
								dbengine.close();

							}


						}
					});




					builder.show();






				}
			}*/
		}
		catch(Exception e)
		{

		}

	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_storeselection);
		Intent passedvals = getIntent();
		imei = AppUtils.getIMEI(this);
		currSysDate = passedvals.getStringExtra("currSysDate");
		pickerDate = passedvals.getStringExtra("pickerDate");
		activityFrom = passedvals.getStringExtra("activityFrom");
		Bundle extras = passedvals.getExtras();

		dbengine = new AppDataSource(InvoiceStoreSelection.this);

		if (extras != null) {

			if (extras.containsKey("spnrDistSlctd")) {
				spinnerDistributrValue= extras.getInt("spnrDistSlctd", 0);

			}

			else
			{
				spinnerDistributrValue=0;
			}
			if (extras.containsKey("spinnerSlctd")) {
				spinnerRouteValue= extras.getInt("spinnerSlctd", 0);

			}

			else
			{
				spinnerRouteValue=0;
			}
		}
		//dbengine.open();
		distributor_name = dbengine.fnGetDistinctDistributorsName();
		distributor_name_id = dbengine.fnGetDistinctDistributorsID();
		//dbengine.close();
		hmapRsnForCncl=dbengine.getReasonToCancel();
		for(Map.Entry<String,String> entry:hmapRsnForCncl.entrySet())
		{
			listRsnForCncl.add(entry.getKey());
		}
		setUpVariable();
		showInputDialog();
	}


	protected void showInputDialog() {
		System.out.println("inTest :"+ inTest);
		inTest=inTest+1;
		//dbengine.open();
		StoreName = new String[0];
		StorenameOther = new String[0];
		StoreID = new String[0];
		StoreSstat = new String[0];
		StorenameList= new String[0];
		StoreName = dbengine.fnGetStoreListForInvoice(selected_distributor_name_id,selected_route_name_id,"");
		//dbengine.close();
		StorenameList=new String[StoreName.length];
		StorenameOther=new String[StoreName.length];
		StoreID=new String[StoreName.length];
		StoreSstat=new String[StoreName.length];
		OrderID=new String[StoreName.length];
		StoreIDOrderID=new String[StoreName.length];
		hmap.clear();
		int listRouteValue;
		int listDistValue;
		if(Spinner_Distributor.getSelectedItem()!=null)
		{

			listDistValue= adapterDisrt.getPosition(Spinner_Distributor.getSelectedItem().toString());

		}
		else
		{
			listDistValue=0;

		}

		if(Spinner_Route.getSelectedItem()!=null)
		{

			listRouteValue= adapterRoute.getPosition(Spinner_Route.getSelectedItem().toString());

		}
		else
		{
			listRouteValue=0;

		}
		for(int i=0;i<StoreName.length;i++)
		{
			String[] parts = StoreName[i].split("\\^");
			StorenameList[i] = parts[0];
			StorenameOther[i]= parts[1];
			int OdrStat= Integer.parseInt(parts[2]);
			//hmap.put(StorenameList[i], StorenameOther[i]);
			StoreSstat[i]= parts[2];
			StringTokenizer abc = new StringTokenizer(String.valueOf(StorenameOther[i]), "_");
			//String getvalue=abc.nextToken().trim();

			String currentStoreId="";
			String currentStoreOrderId="";
			currentStoreId=abc.nextToken().trim();
			currentStoreOrderId=abc.nextToken().trim();
			StoreID[i]= currentStoreId;
			OrderID[i]=currentStoreOrderId;
			hmap.put(StorenameList[i], StorenameOther[i]);
			hmapStoreIdName.put(currentStoreOrderId, StorenameList[i]);
			StoreIDOrderID[i]=currentStoreOrderId;
			hmapStoreIdAndInvoiceOtherDetails.put(currentStoreOrderId, StorenameOther[i]);
			if(OdrStat==10)
			{
				hmapConfirmCancel.put(OrderID[i], "10");
				//hmapStoreIdAndInvoiceReturnRemaks.put(currentStoreOrderId,  parts[2]);

			}
		}



		/*try
		{
		*/

		editText = (EditText)findViewById(R.id.inputSearch);
		dataItems.clear();
		List<String> dataTemp = Arrays.asList(StoreIDOrderID);
		dataItems.addAll(dataTemp);
		final ListView areaLV = (ListView)findViewById(R.id.list_view);
		adapter = new ListAdapter(InvoiceStoreSelection.this, dataItems,StoreIDOrderID,StoreSstat,StorenameOther,pickerDate,hmap,imei,currSysDate,StorenameList,listRouteValue,listDistValue,hmapStoreIdName,hmapStoreIdAndInvoiceOtherDetails,activityFrom);
		adapter.setCustomButtonListner(InvoiceStoreSelection.this);
		areaLV.setAdapter(adapter);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				areaLV.clearFocus();
				adapter.getFilter().filter(cs.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
										  int arg3) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		/*}
		catch(Exception e)
		{

		}*/


	}
	public void setUpVariable()
	{


		adapterDisrt = new ArrayAdapter<String>(InvoiceStoreSelection.this,
				android.R.layout.simple_spinner_item, distributor_name);
		adapterDisrt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_Distributor = (Spinner) findViewById(R.id.Spin_Distributor);
		Spinner_Distributor.setAdapter(adapterDisrt);

		Spinner_Distributor.setOnItemSelectedListener(this);
		Spinner_Route = (Spinner) findViewById(R.id.Spin_Route);
		Spinner_Route.setEnabled(false);

		if(distributor_name.length==2)
		{
			Spinner_Distributor.setSelection(distributor_name.length-1);
			Spinner_Route.setEnabled(true);
		}

		else
		{
			Spinner_Distributor.setSelection(spinnerDistributrValue);

		}


		Button But_Submit =(Button) findViewById(R.id.But_Submit);
		But_Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				//dbengine.open();
				int check=dbengine.checkRouteIDExistInStoreListTable();
				//dbengine.close();
				if(check==0)
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(InvoiceStoreSelection.this);
					alertDialog.setTitle("Information");
					alertDialog.setMessage("There is no Store for Submission");
					alertDialog.setIcon(R.drawable.error);
					alertDialog.setCancelable(false);
					// Setting Positive "Yes" Button
					alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog,int which)
						{

							dialog.dismiss();
						}
					});

					alertDialog.show();
				}
				else
				{
					new GetData().execute();
				}

			}
		});

		refresh_tv = (TextView)findViewById(R.id.refresh_text);

		refresh_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				CommonFunction.getAllExeutionData(InvoiceStoreSelection.this,imei,"","Refreshing data..",0);


			}
		});

		Button But_Conform_Select = (Button) findViewById(R.id.But_Conform_Select_Invoice);//Change Id to Conform Selected Button
		But_Conform_Select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strReason="";
				if(isOnline())
				{

				}
				else
				{
					showNoConnAlert();
					return;

				}
				whereTo = "11";
				//.open();
				StoreConformInvoiceOrder = dbengine.ProcessConformStoreReq();
				//dbengine.close();
				if (hmapConfirmCancel.size() != 0)
				{
					midPartConformInvoices();
					showPendingStorelistConformInvoices();
				}
				else
				{
					AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(InvoiceStoreSelection.this);
					alertDialogNoConn.setTitle("Information");
					alertDialogNoConn.setMessage("No Store Order is selected for confirmation.");
					alertDialogNoConn.setPositiveButton(R.string.txtOk,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which)
								{
									dialog.dismiss();

								}
							});

					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();
					return;
				}

			}

		});

		Button But_Cancel_Select = (Button) findViewById(R.id.But_Cancel_Select);
		But_Cancel_Select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(isOnline())
				{}
				else
				{
					showNoConnAlert();
					return;

				}

				whereTo = "11";
				//dbengine.open();
				StoreList2Procs = dbengine.ProcessCancelStoreReq();
				//dbengine.close();
				if (hmapConfirmCancel.size()!=0)
				{
					midPartCancelInvoices();
					showPendingStorelistCancelInvoices();
				}

				else {
					AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(InvoiceStoreSelection.this);
					alertDialogNoConn.setTitle("Information");
					alertDialogNoConn.setMessage("No Store order is selected for cancellation.");
					alertDialogNoConn.setPositiveButton(R.string.txtOk,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which)
								{
									dialog.dismiss();

								}
							});

					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();
					return;
				}

			}
		});
		ImageView ButBack = (ImageView) findViewById(R.id.btn_bck);
		ButBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				//.open();
				int check = dbengine.CheckNonSubmitDataIntblPendingInvoices();// check = dbengine.CheckNonSubmitDataIntblInvoiceButtonStoreMstr();
				//dbengine.close();

				System.out.println("Check Data in NonSubmitDataIntblInvoiceButtonStoreMstr"+check);
				if(check==1)
				{
					AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(
							InvoiceStoreSelection.this);
					alertDialogSubmitConfirm.setTitle("Information");
					alertDialogSubmitConfirm.setMessage("You have marked orders for execution / deletion ,but not submited, do you wish to continue?");
					alertDialogSubmitConfirm.setCancelable(false);

					alertDialogSubmitConfirm.setNeutralButton(R.string.txtYes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();

									dbengine.fnAllServerOrdersFlgWith0();
									/*Intent intent = new Intent(InvoiceStoreSelection.this, LauncherActivity.class);
									intent.putExtra("imei", imei);
									startActivity(intent);
									finish();*/
									Intent intent = new Intent(InvoiceStoreSelection.this, AllButtonActivity.class);
									intent.putExtra("imei", imei);
									startActivity(intent);
									finish();
								}
							});

					alertDialogSubmitConfirm.setNegativeButton(R.string.txtNo,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});

					alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogSubmitConfirm.create();
					alert.show();
				}
				else
				{


					Intent intent = new Intent(InvoiceStoreSelection.this, AllButtonActivity.class);
					intent.putExtra("imei", imei);
					startActivity(intent);
					finish();

				}
			}

		});




	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
							   long id) {
		// TODO Auto-generated method stub
		switch(parent.getId())
		{
			case R.id.Spin_Distributor:
				int index=Spinner_Distributor.getSelectedItemPosition();
				selected_distributor_name_id=distributor_name_id[index];


				System.out.println("Dangi new testing selected_distributor_name_id:"+selected_distributor_name_id);
				// selected_route_name_id="0";
				if(index==0)
				{
					Spinner_Route.setEnabled(false);
					Spinner_Route.setSelection(0);
					selected_distributor_name_id="0";
					selected_route_name_id="0";
				}
				else
				{
					Spinner_Route.setEnabled(true);
					//dbengine.open();
					route_name = dbengine.fnGetDistinctRouteName(selected_distributor_name_id);
					route_name_id = dbengine.fnGetDistinctRouteId(selected_distributor_name_id);
					//dbengine.close();
					adapterRoute = new ArrayAdapter<String>(InvoiceStoreSelection.this,
							android.R.layout.simple_spinner_item, route_name);
					adapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					Spinner_Route.setAdapter(adapterRoute);
					Spinner_Route.setOnItemSelectedListener(this);
					Spinner_Route.setSelection(spinnerRouteValue);

				}

				showInputDialog();
				break;

        	                      /* int index=Spinner_Distributor.getSelectedItemPosition();
        	                       selected_distributor_name_id=distributor_name_id[index];
        	                       Spinner_Distributor.setEnabled(false);

	        	                    	   Spinner_Route.setEnabled(true);
	        	                    	   dbengine.open();
	        	                    	   route_name = dbengine.fnGetDistinctRouteName(selected_distributor_name_id);
	        	                    	   route_name_id = dbengine.fnGetDistinctRouteId(selected_distributor_name_id);
	        	                   		   dbengine.close();
	        	                    	   ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(InvoiceStoreSelection.this,
	        	                   	            android.R.layout.simple_spinner_item, route_name);
	        	                   		   adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        	                    	   Spinner_Route.setAdapter(adapter2);
	        	                    	   Spinner_Route.setOnItemSelectedListener(this);
        	                       showInputDialog();
        	                       break;*/
			case R.id.Spin_Route:
				int index1=Spinner_Route.getSelectedItemPosition();
				selected_route_name_id=route_name_id[index1];
				if(index1==0)
				{
					selected_route_name_id="0";
				}

				showInputDialog();
				break;
			default:
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	@Override
	public void onButtonClickListner(int position, final String value,final TextView txtvw,final Button btninv,final CheckBox checkConformExecutionStore,final int clkdView,String OrderIdOnClickedChekBoxNew)
	{
		if(clkdView==1)
		{/*
			 dbengine.open();
            final StringTokenizer ad = new StringTokenizer(String.valueOf(hmap.get(value)), "_");
    		String TagStoreID= ad.nextToken().trim();
    		String TagOrderID= ad.nextToken().trim();
    		String TagRouteID= ad.nextToken().trim();
    		String TagDistID= ad.nextToken().trim();
    		String TagDate= ad.nextToken().trim();
    		String TagSstat= ad.nextToken().trim();
    		if(Integer.parseInt(TagSstat)==0)
    		{
    			hmap.remove(value);
        		hmap.put(value, TagStoreID+"_"+TagOrderID+"_"+TagRouteID+"_"+TagDistID+"_"+TagDate+"_"+7);

                dbengine.UpdateInvoiceButtonStoreFlag(TagStoreID.trim(), 7,1,TagOrderID.trim());
                String abc1= dbengine.FetchInvoiceButtonSstat1(TagStoreID,TagOrderID);

               String abc= dbengine.FetchInvoiceButtonSstat(TagStoreID,TagOrderID);
               if(Integer.parseInt(abc)==0)
               {
              	 dbengine.saveInvoiceButtonStoreTransac("NA",pickerDate,TagStoreID,"NA","NA",0.0, 0,
              			 0, 0,TagOrderID,"","7",1);
              	 dbengine.saveInvoiceButtonStoreTransac("NA",TagDate,TagStoreID,"NA","NA",0.0, 0,
                           0, 0,TagOrderID,"","7",1,0.0,TagRouteID,"0");
               }
               else
               {
              	 dbengine.UpdatetblInvoiceButtonTransac(TagStoreID, 7,1,TagOrderID);
               }

                dbengine.close();
                txtvw.setTextColor(Color.RED);
                btninv.setTextColor(Color.RED);
                btnCan.setTextColor(Color.RED);
                txtvw.setEnabled(false);
                btninv.setEnabled(false);
                btnCan.setEnabled(true);
                btnCan.setBackground(getResources().getDrawable(R.drawable.delete_button_selected_red));
                btninv.setVisibility(View.VISIBLE);
                btnCan.setVisibility(View.VISIBLE);
                checkConformExecutionStore.setEnabled(false);
                checkConformExecutionStore.setButtonDrawable(getResources().getDrawable(R.drawable.checknew_red1));

    		}
    		else
    		{
    			hmap.remove(value);
          		hmap.put(value, TagStoreID+"_"+TagOrderID+"_"+TagRouteID+"_"+TagDistID+"_"+TagDate+"_"+0);

                 dbengine.UpdateInvoiceButtonStoreFlag(TagStoreID.trim(), 0,0,TagOrderID.trim());
               //  dbengine.deleteInvoiceRelatedTableEtrySavedData(TagStoreID,TagOrderID);

                 dbengine.UpdatetblInvoiceButtonTransac(TagStoreID, 0,0,TagOrderID);

                  dbengine.close();
                  txtvw.setTextColor(Color.BLACK);
                  btninv.setTextColor(Color.BLACK);
                  btnCan.setTextColor(Color.BLACK);
                  txtvw.setEnabled(true);
                  btninv.setEnabled(true);
                  btnCan.setEnabled(true);
                 btnCan.setBackground(getResources().getDrawable(R.drawable.delete_button_unselected));
                  btninv.setVisibility(View.VISIBLE);
                  btnCan.setVisibility(View.VISIBLE);
                  checkConformExecutionStore.setVisibility(View.VISIBLE);
                  checkConformExecutionStore.setEnabled(true);
                  checkConformExecutionStore.setChecked(false);
                  checkConformExecutionStore.setButtonDrawable(getResources().getDrawable(R.drawable.unchecknew));
    		}




			//return;
		*/}
		if(clkdView==2)
		{
			if(checkConformExecutionStore.isChecked())
			{

				//dbengine.open();
				final StringTokenizer ad = new StringTokenizer(String.valueOf(hmapStoreIdAndInvoiceOtherDetails.get(OrderIdOnClickedChekBoxNew)), "_");
				String TagStoreID= ad.nextToken().trim();
				String TagOrderID= ad.nextToken().trim();
				String TagRouteID= ad.nextToken().trim();
				String TagDistID= ad.nextToken().trim();
				String TagDate= ad.nextToken().trim();
				String TagSstat= ad.nextToken().trim();
				String isCancelConfirm=ad.nextToken().trim();
				hmapStoreIdAndInvoiceOtherDetails.remove(OrderIdOnClickedChekBoxNew);
				hmapStoreIdAndInvoiceOtherDetails.put(OrderIdOnClickedChekBoxNew, TagStoreID+"_"+TagOrderID+"_"+TagRouteID+"_"+TagDistID+"_"+TagDate+"_"+10+"_"+isCancelConfirm);
				hmapConfirmCancel.put(TagOrderID, "10");
				dbengine.UpdateInvoiceButtonStoreFlag(TagStoreID.trim(), 10,0,TagOrderID.trim());
				String abc1= dbengine.FetchInvoiceButtonSstat1(TagStoreID,TagOrderID);

				String abc= dbengine.FetchInvoiceButtonSstat(TagStoreID,TagOrderID);
				if(Integer.parseInt(abc)==0)
				{
              	/* dbengine.saveInvoiceButtonStoreTransac("NA",pickerDate,TagStoreID,"NA","NA",0.0, 0,
              			 0, 0,TagOrderID,"","7",1);*/
					/*if(!strReason.equals(""))
					{
						dbengine.saveInvoiceButtonStoreTransac("NA",TagDate,TagStoreID,"0","0",0.0, 0,
								0, 0,TagOrderID,"","10",0,0.0,TagRouteID,"0",strReason,idSelectedRsn);
					}
					else
					{
						dbengine.saveInvoiceButtonStoreTransac("NA",TagDate,TagStoreID,"0","0",0.0, 0,
								0, 0,TagOrderID,"","10",0,0.0,TagRouteID,"0","",idSelectedRsn);
					}*/

				}
				else
				{
					dbengine.UpdatetblInvoiceButtonTransac(TagStoreID, 10,0,TagOrderID,strReason,idSelectedRsn);
				}

				//	dbengine.close();
				txtvw.setTextColor(Color.parseColor("#9C27B0"));
				btninv.setTextColor(Color.BLUE);
				//  btnCan.setTextColor(Color.BLUE);
				txtvw.setEnabled(false);
				btninv.setEnabled(false);
				//  btnCan.setEnabled(false);

				//getResources().getDrawable(R.drawable.ready)
				btninv.setVisibility(View.GONE);
				// btnCan.setVisibility(View.GONE);
				checkConformExecutionStore.setChecked(true);
				checkConformExecutionStore.setButtonDrawable(getResources().getDrawable(R.drawable.checked));



				//return;

			}
			else
			{


				// dialog.dismiss();
				//dbengine.open();
				final StringTokenizer ad = new StringTokenizer(String.valueOf(hmapStoreIdAndInvoiceOtherDetails.get(OrderIdOnClickedChekBoxNew)), "_");
				String TagStoreID= ad.nextToken().trim();
				String TagOrderID= ad.nextToken().trim();
				String TagRouteID= ad.nextToken().trim();
				String TagDistID= ad.nextToken().trim();
				String TagDate= ad.nextToken().trim();
				String TagSstat= ad.nextToken().trim();
				String isCancelConfirm=ad.nextToken().trim();
				hmapStoreIdAndInvoiceOtherDetails.remove(OrderIdOnClickedChekBoxNew);
				hmapStoreIdAndInvoiceOtherDetails.put(OrderIdOnClickedChekBoxNew, TagStoreID+"_"+TagOrderID+"_"+TagRouteID+"_"+TagDistID+"_"+TagDate+"_"+0+"_"+isCancelConfirm);
				hmapConfirmCancel.remove(TagOrderID);
				dbengine.UpdateInvoiceButtonStoreFlag(TagStoreID.trim(), 0,0,TagOrderID.trim());
				// dbengine.deleteInvoiceRelatedTableEtrySavedData(TagStoreID,TagOrderID);

				dbengine.UpdatetblInvoiceButtonTransac(TagStoreID, 0,0,TagOrderID,strReason,idSelectedRsn);

				//dbengine.close();
				txtvw.setTextColor(Color.BLACK);
				btninv.setTextColor(Color.BLACK);
				// btnCan.setTextColor(Color.BLACK);
				txtvw.setEnabled(true);
				btninv.setEnabled(true);
				//  btnCan.setEnabled(true);

				btninv.setVisibility(View.VISIBLE);
				//   btnCan.setVisibility(View.VISIBLE);
				checkConformExecutionStore.setChecked(false);
				checkConformExecutionStore.setEnabled(true);
				checkConformExecutionStore.setButtonDrawable(getResources().getDrawable(R.drawable.unchecked));

				/*AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(InvoiceStoreSelection.this);
				alertDialogNoConn.setTitle("Information");
				alertDialogNoConn.setMessage("Are you sure you want to unmark the store Order from the conform store conform order :"+value);

				alertDialogNoConn.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{}
						});
				alertDialogNoConn.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
	                      dialog.dismiss();

							}
						});
				alertDialogNoConn.setIcon(R.drawable.info_ico);
				AlertDialog alert = alertDialogNoConn.create();
				alert.show();*/
				//return;


			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	public void showNoConnAlert()
	{
		AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(InvoiceStoreSelection.this);
		alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
		alertDialogNoConn.setMessage(R.string.genTermNoDataConnectionFullMsg);
		alertDialogNoConn.setNeutralButton(R.string.txtOk,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
		alertDialogNoConn.setIcon(R.drawable.error_ico);
		AlertDialog alert = alertDialogNoConn.create();
		alert.show();
	}

	public void midPartConformInvoices() {
		String tempSID;
		String tempSNAME;
//StoreConformInvoiceOrder
		stIDs = new ArrayList<String>(StoreConformInvoiceOrder.length);
		stNames = new ArrayList<String>(StoreConformInvoiceOrder.length);
		for (int x = 0; x < (StoreConformInvoiceOrder.length); x++)
		{
			StringTokenizer tokens = new StringTokenizer(String.valueOf(StoreConformInvoiceOrder[x]), "%");
			tempSID = tokens.nextToken().trim();
			tempSNAME = tokens.nextToken().trim();
			stIDs.add(x, tempSID);
			stNames.add(x, tempSNAME);
		}
	}


	public void showPendingStorelistCancelInvoices()
	{

		LayoutInflater inflater = getLayoutInflater();
		alertLayout = inflater.inflate(R.layout.layout_custom_dialog_cancelationremarks, null);
		et_Reason = (EditText) alertLayout.findViewById(R.id.et_CancelationReason);
		spnrCnclRsn = (Spinner) alertLayout.findViewById(R.id.spnrCnclRsn);
		ArrayAdapter adapterRsnCncl = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listRsnForCncl);
		adapterRsnCncl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Setting the ArrayAdapter data on the Spinner
		spnrCnclRsn.setAdapter(adapterRsnCncl);
		et_Reason.setVisibility(View.INVISIBLE);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InvoiceStoreSelection.this);


		TextView tv = new TextView(InvoiceStoreSelection.this);
		tv.setText("Please provide cancelation reason:");
		tv.setPadding(40, 10, 40, 10);
		tv.setBackgroundColor(Color.parseColor("#486FA8"));
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);
		tv.setTextColor(Color.parseColor("#ffffff"));
		alertDialogBuilder.setView(alertLayout);
		alertDialogBuilder.setCustomTitle(tv);
		alertDialogBuilder.setIcon(R.drawable.info_ico);
		alertDialogBuilder.setCancelable(false);
		et_Reason.setVisibility(View.VISIBLE);
		spnrCnclRsn.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedReason=spnrCnclRsn.getSelectedItem().toString();
				idSelectedRsn=hmapRsnForCncl.get(selectedReason);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


		alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(et_Reason.getText().toString().trim() !="" && !TextUtils.isEmpty(et_Reason.getText().toString()))
				{
					dialog.dismiss();
					strReason=	et_Reason.getText().toString().trim();
					fnInvoiceWithCancelReason();
				}




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
	public  void fnInvoiceWithCancelReason()
	{
		ContextThemeWrapper cw = new ContextThemeWrapper(this,R.style.AlertDialogTheme);

		System.out.println("Nitish showPendingStorelistCancelInvoices");
		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		builder.setTitle(R.string.genTermSelectStoresPendingToCancel);
		mSelectedItems.clear();

		final String[] stNames4List = new String[stNames.size()];
		checksCancelInvoices=new boolean[stNames.size()];
		stNames.toArray(stNames4List);
		for(int cntPendingList=0;cntPendingList<stNames4List.length;cntPendingList++)
		{
			mSelectedItems.add(stNames4List[cntPendingList]);
			checksCancelInvoices[cntPendingList]=true;
		}

		builder.setPositiveButton(R.string.genTermSubmitCancelInvoiceList,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (mSelectedItems.size() == 0) {

							showPendingStorelistCancelInvoices();
						}

						else {
							whatTask = 2;

							try {


								new bgTasker().execute().get();
							} catch (InterruptedException e) {
								e.printStackTrace();
								////System.out.println(e);
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							// --

						}

					}
				});
			/*builder.setNeutralButton(R.string.genTermDirectlyChangeRouteInvoice,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Intent intent = new Intent(InvoiceStoreSelection.this, LauncherActivity.class);
					    	intent.putExtra("imei", imei);
					         startActivity(intent);
					         finish();

						}
					});*/
		builder.setNegativeButton(R.string.txtCancle,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						closeList = 1;
						arg0.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		//AlertDialog alert = builder.create();
		alert.show();
			/*if (closeList == 1) {
				closeList = 0;
				alert.dismiss();


			} else {
				alert.show();
				alert.setCancelable(false);
			}*/

	}

	public void midPartCancelInvoices() {
		String tempSID;
		String tempSNAME;

		stIDs = new ArrayList<String>(StoreList2Procs.length);
		stNames = new ArrayList<String>(StoreList2Procs.length);
		for (int x = 0; x < (StoreList2Procs.length); x++)
		{

			StringTokenizer tokens = new StringTokenizer(String.valueOf(StoreList2Procs[x]), "%");
			tempSID = tokens.nextToken().trim();
			tempSNAME = tokens.nextToken().trim();
			stIDs.add(x, tempSID);
			stNames.add(x, tempSNAME);
		}

		//System.out.println("Nitish midPartCancelInvoices :"+stIDs+"="+stNames);

	}

	public void showPendingStorelistConformInvoices() {
		ContextThemeWrapper cw = new ContextThemeWrapper(this,R.style.AlertDialogTheme);
		System.out.println("Nitish showPendingStorelistConformInvoices");
		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		//StoreConformInvoiceOrder
		builder.setTitle(R.string.genTermSelectStoresPendingToComplete);
		mSelectedItemsConfornInvoiceOrders.clear();

		final String[] StoreConformInvoiceOrderList = new String[stNames.size()];
		checksConformInvoices=new boolean[stNames.size()];
		stNames.toArray(StoreConformInvoiceOrderList);
		for(int cntPendingList=0;cntPendingList<StoreConformInvoiceOrderList.length;cntPendingList++)
		{
			mSelectedItemsConfornInvoiceOrders.add(StoreConformInvoiceOrderList[cntPendingList]);
			checksConformInvoices[cntPendingList]=true;
		}
			/*builder.setMultiChoiceItems(StoreConformInvoiceOrderList, checksConformInvoices,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {

							if (isChecked) {
								mSelectedItemsConfornInvoiceOrders.add(StoreConformInvoiceOrderList[which]);

							} else if (mSelectedItemsConfornInvoiceOrders.contains(StoreConformInvoiceOrderList[which])) {
								//////System.out.println("Abhinav store Selection  Step 5");
								mSelectedItemsConfornInvoiceOrders.remove(StoreConformInvoiceOrderList[which]);

							}
						}
					});
*/

		builder.setPositiveButton(R.string.genTermSubmitSelectedInvoice,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (mSelectedItemsConfornInvoiceOrders.size() == 0) {
								/*Toast.makeText(
										getApplicationContext(),
										R.string.genTermNoStroeSelectedOnSubmit,

										Toast.LENGTH_SHORT).show();*/
							showPendingStorelistConformInvoices();
						}

						else {
							whatTask = 2;
							// -- Route Info Exec()
							try {

								new bgTaskerConformInvoices().execute().get();
							} catch (InterruptedException e) {
								e.printStackTrace();
								////System.out.println(e);
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							// --

						}

					}
				});
			/*builder.setNeutralButton(R.string.genTermDirectlyChangeRouteInvoice,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Intent intent = new Intent(InvoiceStoreSelection.this, LauncherActivity.class);
					    	intent.putExtra("imei", imei);
					         startActivity(intent);
					         finish();

						}
					});*/
		builder.setNegativeButton(R.string.txtCancle,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

						arg0.cancel();

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		//return;
	}

	@Override
	public void success() {
		showAlertRefreshSucessFully("Information", "Data Refreshed Sucessfully");

	}

	@Override
	public void failure() {
		showAlertException(getResources().getString(R.string.txtError), getResources().getString(R.string.txtErrRetrieving));

	}


	private class bgTaskerConformInvoices extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			try {
				//dbengine.open();
				String rID=dbengine.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
				//dbengine.close();
				if (whatTask == 2)
				{
					whatTask = 0;
					//	dbengine.open();
					for (int nosSelected = 0; nosSelected <= mSelectedItemsConfornInvoiceOrders.size() - 1; nosSelected++)
					{
						String valSN = (String) mSelectedItemsConfornInvoiceOrders.get(nosSelected);
						int valID = stNames.indexOf(valSN);
						String stIDneeded = stIDs.get(valID);
					}
					//		dbengine.close();
					dbengine.UpdateCancelStoreFlag(hmapConfirmCancel,0,strReason,idSelectedRsn);

				}else if (whatTask == 3)
				{
					whatTask = 0;
				}
				else if (whatTask == 1)
				{
					// clear all
					whatTask = 0;

				}

			} catch (Exception e) {
				Log.i("bgTasker", "bgTasker Execution Failed!", e);

			}

			finally {

				Log.i("bgTasker", "bgTasker Execution Completed...");

			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			Log.i("bgTasker", "bgTasker Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Log.i("bgTasker", "bgTasker Execution cycle completed");
			//pDialog2.dismiss();
			hmapConfirmCancel.clear();
			showInputDialog();
			whatTask = 0;

		}
	}



	private class bgTasker extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			try {
				//.open();
				String rID=dbengine.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

				//dbengine.close();
				if (whatTask == 2)
				{
					whatTask = 0;
					//	dbengine.open();
					for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++)
					{
						String valSN = (String) mSelectedItems.get(nosSelected);
						int valID = stNames.indexOf(valSN);
						String stIDneeded = stIDs.get(valID);
					}
					//	dbengine.close();

					//9,1=cancel
					dbengine.UpdateCancelStoreFlag(hmapConfirmCancel,1,strReason,idSelectedRsn);


				}else if (whatTask == 3)
				{
					whatTask = 0;

				}else if (whatTask == 1)
				{
					// clear all
					whatTask = 0;

				}

			} catch (Exception e) {
				Log.i("bgTasker", "bgTasker Execution Failed!", e);

			}

			finally {

				Log.i("bgTasker", "bgTasker Execution Completed...");

			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			Log.i("bgTasker", " Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			hmapConfirmCancel.clear();
			showInputDialog();
			Log.i("bgTasker", "bgTasker Execution cycle completed");
			//pDialog2.dismiss();
			whatTask = 0;

		}
	}







	public void submitDataToServer()
	{


		syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat(".dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);
		fullFileName1 = imei +df.format(dateobj).toString().trim();
		try {

			File InvoiceXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.InvoiceXMLFolder);

			if (!InvoiceXMLFolder.exists())
			{
				InvoiceXMLFolder.mkdirs();

			}
			String[] OrderListing=dbengine.fngetDistictOrderIdsForSubmission();
			DA.open();
			DA.export(CommonInfo.DATABASE_NAME, fullFileName1,9,OrderListing);
			DA.close();
			dbengine.UpdatetblExecutionImages(5);

		} catch (IOException e) {

			e.printStackTrace();
		}


	}

	public class GetData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(InvoiceStoreSelection.this);
			mProgressDialog.setTitle("Plase Wait");
			mProgressDialog.setMessage("While we submit your Data...");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{

			submitDataToServer();
			return null;
		}
		@Override
		protected void onPostExecute(Void args) {
			mProgressDialog.dismiss();

			Intent syncIntent = new Intent(InvoiceStoreSelection.this, InvoiceSyncMaster.class);
			//syncIntent.putExtra("xmlPathForSync",Environment.getExternalStorageDirectory() + "/TJUKSFAInvoicexml/" + fullFileName1 + ".xml");
			syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.InvoiceXMLFolder + "/" + fullFileName1 + ".xml");

			syncIntent.putExtra("OrigZipFileName", fullFileName1);
			syncIntent.putExtra("whereTo", "9");
			syncIntent.putStringArrayListExtra("mSelectedItems", mSelectedItemsConfornInvoiceOrders);
			syncIntent.putExtra("imei", imei);
			syncIntent.putExtra("activityFrom", activityFrom);

			syncIntent.putExtra("currSysDate", currSysDate);
			syncIntent.putExtra("pickerDate", pickerDate);

			startActivity(syncIntent);
			finish();
		}

	}

	public void showAlertRefreshSucessFully(String title, String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(InvoiceStoreSelection.this);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(msg);
		alertDialog.setIcon(R.drawable.info_icon);
		alertDialog.setCancelable(false);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {


				dialog.dismiss();
				Intent storeIntent = new Intent(InvoiceStoreSelection.this, InvoiceStoreSelection.class);
				storeIntent.putExtra("imei", imei);
				storeIntent.putExtra("userDate", currSysDate);
				storeIntent.putExtra("pickerDate", pickerDate);
				storeIntent.putExtra("activityFrom", "AllButtonActivity");
				startActivity(storeIntent);
				finish();
			}
		});


		// Showing Alert Message
		alertDialog.show();
	}

	public void showAlertException(String title, String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(InvoiceStoreSelection.this);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(msg);
		alertDialog.setIcon(R.drawable.error);
		alertDialog.setCancelable(false);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {


				dialog.dismiss();
				try {
					// new GetRouteInfo().execute();
					CommonFunction.getAllExeutionData(InvoiceStoreSelection.this, imei, "", "Please wait Refreshing data.", 0);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to invoke NO event
				dialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}