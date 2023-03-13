package com.astix.allanasosfa;


import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.astix.sancussosfa.database.AppDataSource;
import com.astix.sancussosfa.model.AllSummaryStoreWiseDay;
import com.astix.sancussosfa.model.ReportsInfo;
import com.astix.sancussosfa.model.TblStoreWiseDaySummary;
import com.astix.sancussosfa.rest.ApiClient;
import com.astix.sancussosfa.rest.ApiInterface;
import com.sancussosfa.truetime.TimeUtils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreWiseFragmentOneTab<Context> extends Fragment
{
	//implements InterfaceRetrofit
	public String imei;
	public String fDate;
	public SimpleDateFormat sdf;
	AppDataSource dbengine;
	private Activity mContext;
	
	LinearLayout ll_Scroll_product,ll_scheme_detail;

	int count=1;
	
	int pos=0;
	
	//public String[] AllDataContainer;
	public View rootView;

	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
       rootView = inflater.inflate(R.layout.store_summary, container, false);
        
        mContext = getActivity();
        dbengine = AppDataSource.getInstance(mContext);
        
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
		imei = tManager.getDeviceId();
		
		
		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei= CommonInfo.imei.trim();
		}
		
		
		Date date1=new Date();
		sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = TimeUtils.getNetworkDateTime(getActivity(),TimeUtils.DATE_FORMAT);
		//fDate="29-10-2015";
		
		//'868087024619932','29-10-2015'  

	/*	if(isOnline()) {
			try {
				GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay();
				task.execute();
			} catch (Exception e) {
				// TODO Autouuid-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(mContext, getResources().getString(R.string.NoDataConnectionFullMsg), Toast.LENGTH_SHORT).show();
		}*/

		try
		{
			// new GetRouteInfo().execute();

			getAllStoreWiseSummaryReport(imei, CommonInfo.RegistrationID,"Please wait generating report.");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
        return rootView;
    }

	public void getAllStoreWiseSummaryReport( final String imei, String RegistrationID, String msgToShow){
		final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
		mProgressDialog.setMessage(getActivity().getResources().getString(R.string.RetrivingDataMsg));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		final ArrayList blankTablearrayList=new ArrayList();
		Date date1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		final String fDate = TimeUtils.getNetworkDateTime(getActivity(),TimeUtils.DATE_FORMAT);
		ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

		apiService =
				ApiClient.getClient().create(ApiInterface.class);


		String PersonNodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

		int PersonNodeId=0;

		int PersonNodeType=0;
		if(!PersonNodeIdAndNodeType.equals("0^0")) {
			PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
			PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
		}

		String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
		String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
		String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
		int FlgAllRoutesData=1;
		String  serverDateForSPref=	dbengine.fnGetServerDate();

		ReportsInfo reportsInfo=new ReportsInfo();
		reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
		reportsInfo.setIMEINo(imei);
		reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
		reportsInfo.setForDate(fDate);
		reportsInfo.setSalesmanNodeId(PersonNodeId);
		reportsInfo.setSalesmanNodeType(PersonNodeType);
		reportsInfo.setFlgDataScope(0);

		Call<AllSummaryStoreWiseDay> call= apiService.Call_AllSummaryStoreWiseDay(reportsInfo);
		call.enqueue(new Callback<AllSummaryStoreWiseDay>() {
			@Override
			public void onResponse(Call<AllSummaryStoreWiseDay> call, Response<AllSummaryStoreWiseDay> response) {
				if(response.code()==200){
					AllSummaryStoreWiseDay allSummaryStoreWiseDayModel=  response.body();
					System.out.println("DATAENSERTEDSP");
					//table 1
					dbengine.truncateStoreWiseDataTable();
					List<TblStoreWiseDaySummary> tblStoreWiseDaySummary=  allSummaryStoreWiseDayModel.getTblStoreWiseDaySummary();
					if(tblStoreWiseDaySummary.size()>0){
						dbengine.savetblStoreWiseDaySummary(tblStoreWiseDaySummary);
					}
					else{
						blankTablearrayList.add("tblStoreWiseDaySummary");
					}
					mProgressDialog.dismiss();
					intializeFields();
				}
				else{
					mProgressDialog.dismiss();
					// showAlertForError("Error while retreiving data from server");
				}
			}

			@Override
			public void onFailure(Call<AllSummaryStoreWiseDay> call, Throwable t) {
				System.out.println();
				mProgressDialog.dismiss();
				//   showAlertForError("Error while retreiving data from server");
			}
		});



	}

	
	public void intializeFields()
	{
		String[]  AllDataContainer= dbengine.fetchAllDataFromtblStoreWiseDaySummary();
		if(AllDataContainer.length>0)
		{
		
		 StringTokenizer tokens = new StringTokenizer(String.valueOf(AllDataContainer[0]), "^");
         
         String a1 = tokens.nextToken().toString().trim();
         String a2 = tokens.nextToken().toString().trim();
         String a3 = tokens.nextToken().toString().trim();
         String a4 = tokens.nextToken().toString().trim();
         String a5 = tokens.nextToken().toString().trim();
         String a6 = tokens.nextToken().toString().trim();
         String a7 = tokens.nextToken().toString().trim();
         String a8 = tokens.nextToken().toString().trim();
         String a9 = tokens.nextToken().toString().trim();
        
     	/*initialValues.put("AutoId", AutoId);
			initialValues.put("Store", Store.trim());
			initialValues.put("LinesperBill", LinesperBill.trim());
			initialValues.put("StockValue", StockValue.trim());
			initialValues.put("DiscValue", DiscValue.trim());
			initialValues.put("ValBeforeTax", ValBeforeTax.trim());
			initialValues.put("TaxValue", TaxValue.trim());
			initialValues.put("ValAfterTax", ValAfterTax.trim());
			initialValues.put("Lvl", Lvl.trim());*/
        
        
		
		TextView total_LinesPerBill=(TextView)rootView.findViewById(R.id.total_LinesPerBill);
		total_LinesPerBill.setText(a3);
		
		TextView total_StockValue=(TextView)rootView.findViewById(R.id.total_StockValue);
		Double StockValue_val=Double.parseDouble(a4);
		StockValue_val= Double.parseDouble(new DecimalFormat("##.##").format(StockValue_val));
		total_StockValue.setText(""+StockValue_val);
		
		
		
		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
		Double disc_val=Double.parseDouble(a5);
		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
		total_discountValue.setText(""+disc_val.intValue());
		
		
		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
		Double ValBeforeTax=Double.parseDouble(a6);
		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
		
		
		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
		Double ValTax=Double.parseDouble(a7);
		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
		total_ValTax.setText(""+ValTax.intValue());
		
		
		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
		Double ValAfterTax=Double.parseDouble(a8);
		ValAfterTax=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		
		
		ll_Scroll_product=(LinearLayout) rootView.findViewById(R.id.ll_Scroll_product);
		ll_Scroll_product.removeAllViews();
		
		for(int i=1;i<AllDataContainer.length;i++)
		{
		
			 StringTokenizer tokens1 = new StringTokenizer(String.valueOf(AllDataContainer[i]), "^");
	         
	         String s1 = tokens1.nextToken().toString().trim();
	         String s2 = tokens1.nextToken().toString().trim();
	         String s3 = tokens1.nextToken().toString().trim();
	         String s4 = tokens1.nextToken().toString().trim();
	         String s5 = tokens1.nextToken().toString().trim();
	         String s6 = tokens1.nextToken().toString().trim();
	         String s7 = tokens1.nextToken().toString().trim();
	         String s8 = tokens1.nextToken().toString().trim();
	         String s9 = tokens1.nextToken().toString().trim();
	        /* String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14 = tokens1.nextToken().toString().trim();
	         String s15 = tokens1.nextToken().toString().trim();*/
	         
	         LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
				final View view=inflater.inflate(R.layout.list_store_wise,null);
				if(i%2==0)
				{
					view.setBackgroundResource(R.drawable.card_background_summary);
				}
				view.setTag(count);
			
				
				
				/*initialValues.put("AutoId", AutoId);
				initialValues.put("Store", Store.trim());
				initialValues.put("LinesperBill", LinesperBill.trim());
				initialValues.put("StockValue", StockValue.trim());
				initialValues.put("DiscValue", DiscValue.trim());
				initialValues.put("ValBeforeTax", ValBeforeTax.trim());
				initialValues.put("TaxValue", TaxValue.trim());
				initialValues.put("ValAfterTax", ValAfterTax.trim());
				initialValues.put("Lvl", Lvl.trim());*/
				
				
			
				TextView tv_product_name=(TextView) view.findViewById(R.id.txt_store);
				tv_product_name.setText(s2);
				
				TextView txt_lines_bill=(TextView) view.findViewById(R.id.txt_lines_bill);
				txt_lines_bill.setText(s3);
				
				TextView txt_stock=(TextView) view.findViewById(R.id.txt_stock);
				txt_stock.setText(s4);
				
				
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				Double disc_val1=Double.parseDouble(s5);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_discnt_val.setText(""+disc_val1.intValue());
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				Double ValBeforeTax1=Double.parseDouble(s6);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_gross_val.setText(""+ValBeforeTax1.intValue());
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				Double ValTax1=Double.parseDouble(s7);
				ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_tac_val.setText(""+ValTax1.intValue());
			
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				Double ValAfterTax1=Double.parseDouble(s8);
				ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_net_val.setText(""+ValAfterTax1.intValue());
				
				
				
				
			ll_Scroll_product.addView(view);
				
		}
		
	}



	}
	/*@Override
	public void success() {
		intializeFields();

	}

	@Override
	public void failure() {

	}*/
 
}
