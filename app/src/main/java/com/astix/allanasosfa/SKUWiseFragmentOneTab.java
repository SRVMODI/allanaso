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
import android.widget.Toast;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.model.AllSummarySKUWiseDay;
import com.astix.allanasosfa.model.ReportsInfo;
import com.astix.allanasosfa.model.TblSKUWiseDaySummary;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;
import com.allanasosfa.truetime.TimeUtils;


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

public class SKUWiseFragmentOneTab<Context> extends Fragment
{

	public String imei;
	public String fDate;
	public SimpleDateFormat sdf;
	AppDataSource dbengine;
	private Activity mContext;
	
	LinearLayout ll_Scroll_product,ll_scheme_detail;

	int count=1;
	
	int pos=0;
	
	public String[] AllDataContainer;
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
       rootView = inflater.inflate(R.layout.sku_summary, container, false);
        
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
		fDate = TimeUtils.getNetworkDateTime(getActivity(), TimeUtils.DATE_FORMAT);;
		//fDate="29-10-2015";
		
		//'868087024619932','29-10-2015'  

			if(isOnline())
		{

			/* try
			    {
			      GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay();
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

				getAllSKUWiseSummaryReport(imei, CommonInfo.RegistrationID,"Please wait generating report.");

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(mContext, getResources().getString(R.string.NoDataConnectionFullMsg), Toast.LENGTH_SHORT).show();
		}
		 
		
		
        return rootView;
    }

	public void getAllSKUWiseSummaryReport(final String imei, String RegistrationID, String msgToShow){
		final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
		mProgressDialog.setMessage(getActivity().getResources().getString(R.string.RetrivingDataMsg));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		final ArrayList blankTablearrayList=new ArrayList();
		Date date1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		final String fDate = TimeUtils.getNetworkDateTime(getActivity(), TimeUtils.DATE_FORMAT);;
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

		Call<AllSummarySKUWiseDay> call= apiService.Call_AllSummarySKUWiseDay(reportsInfo);
		call.enqueue(new Callback<AllSummarySKUWiseDay>() {
			@Override
			public void onResponse(Call<AllSummarySKUWiseDay> call, Response<AllSummarySKUWiseDay> response) {
				if(response.code()==200){
					AllSummarySKUWiseDay allSummarySKUWiseDayModel=  response.body();
					System.out.println("DATAENSERTEDSP");
					//table 1
					dbengine.truncateSKUDataTable();
					List<TblSKUWiseDaySummary> tblSKUWiseDaySummary=  allSummarySKUWiseDayModel.getTblSKUWiseDaySummary();
					if(tblSKUWiseDaySummary.size()>0){
						dbengine.savetblSKUWiseDaySummary(tblSKUWiseDaySummary);
					}
					else{
						blankTablearrayList.add("tblSKUWiseDaySummary");
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
			public void onFailure(Call<AllSummarySKUWiseDay> call, Throwable t) {
				System.out.println();
				mProgressDialog.dismiss();

				//   showAlertForError("Error while retreiving data from server");
			}
		});



	}


	
	public void intializeFields()
	{
		AllDataContainer= dbengine.fetchAllDataFromtblSKUWiseDaySummary();
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
         String a10 = tokens.nextToken().toString().trim();
         String a11 = tokens.nextToken().toString().trim();
         String a12 = tokens.nextToken().toString().trim();
         String a13 = tokens.nextToken().toString().trim();
         String a14 = tokens.nextToken().toString().trim();
         String a15 = tokens.nextToken().toString().trim();
         
         
        
        
		
		TextView total_stores=(TextView)rootView.findViewById(R.id.total_stores);
		total_stores.setText(a6);
		TextView total_orderQty=(TextView)rootView.findViewById(R.id.total_orderQty);
		TextView total_freeQty=(TextView)rootView.findViewById(R.id.total_freeQty);
		
		
		
		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
 		Double DisValue=Double.parseDouble(a9);
 		DisValue= Double.parseDouble(new DecimalFormat("##.##").format(DisValue));
 		total_discountValue.setText(""+DisValue.intValue());
 		
 		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
 		Double ValBeforeTax=Double.parseDouble(a10);
 		ValBeforeTax= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
 		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
 		
 		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
 		Double ValTax=Double.parseDouble(a11);
 		ValTax= Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
 		total_ValTax.setText(""+ValTax.intValue());
 		
 		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
 		Double ValAfterTax=Double.parseDouble(a12);
 		ValAfterTax= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
 		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		}
		
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
	         String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14 = tokens1.nextToken().toString().trim();
	         String s15 = tokens1.nextToken().toString().trim();
	         
	         System.out.println("Value of level :"+s13);
	         
	         
	        LayoutInflater inflaterParent=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
			final View viewParent=inflaterParent.inflate(R.layout.list_sku_wise_header,null);
				
            LinearLayout ll_store_sku=(LinearLayout) viewParent.findViewById(R.id.ll_store_sku);
	         
	         if(Integer.parseInt(s13)==1)
	         {
	        	TextView txt_catgry_sku=(TextView) viewParent.findViewById(R.id.txt_catgry_sku);
				TextView txt_sku_stores=(TextView) viewParent.findViewById(R.id.txt_sku_stores);
				TextView txt_sku_order_qty=(TextView) viewParent.findViewById(R.id.txt_sku_order_qty);
				TextView txt_sku_free_qty=(TextView) viewParent.findViewById(R.id.txt_sku_free_qty);
				TextView txt_sku_disc_val=(TextView) viewParent.findViewById(R.id.txt_sku_disc_val);
				TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				
				txt_catgry_sku.setText(s3);
				txt_sku_stores.setText(s6);
				txt_sku_order_qty.setText(s7+" "+s15);
				txt_sku_free_qty.setText(s8);
				
				Double disc_val=Double.parseDouble(s9);
				disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
				txt_sku_disc_val.setText(""+disc_val.intValue());
				
				Double ValBeforeTax1=Double.parseDouble(s10);
				//ValBeforeTax1=Math.round(ValBeforeTax1 * 100.0)/100.0;
		 		ValBeforeTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
		 		txt_store_sku_gross_val.setText(""+ValBeforeTax1.intValue());
		 		
		 		
		 		Double ValTax1=Double.parseDouble(s11);
		 		//ValTax1=Math.round(ValTax1 * 100.0)/100.0;
		 		ValTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
		 		txt_store_sku_tac_val.setText(""+ValTax1.intValue());
		 		
		 		
		 		Double ValAfterTax1=Double.parseDouble(s12);
		 		//ValAfterTax1=Math.round(ValAfterTax1 * 100.0)/100.0;
		 		ValAfterTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
		 		txt_store_sku_net_val.setText(""+ValAfterTax1.intValue());
				
				/*txt_sku_disc_val.setText(s9);
				txt_store_sku_gross_val.setText(s10);
				txt_store_sku_tac_val.setText(s11);
				txt_store_sku_net_val.setText(s12);*/
				
	         }
	         else if(Integer.parseInt(s13)==2)
	         {
	        	   TextView txt_catgry_sku=(TextView) viewParent.findViewById(R.id.txt_catgry_sku);
					TextView txt_sku_stores=(TextView) viewParent.findViewById(R.id.txt_sku_stores);
					TextView txt_sku_order_qty=(TextView) viewParent.findViewById(R.id.txt_sku_order_qty);
					TextView txt_sku_free_qty=(TextView) viewParent.findViewById(R.id.txt_sku_free_qty);
					TextView txt_sku_disc_val=(TextView) viewParent.findViewById(R.id.txt_sku_disc_val);
					TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
					TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
					TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
					
					txt_catgry_sku.setVisibility(View.GONE);
					txt_sku_stores.setVisibility(View.GONE);
					txt_sku_order_qty.setVisibility(View.GONE);
					txt_sku_free_qty.setVisibility(View.GONE);
					txt_sku_disc_val.setVisibility(View.GONE);
					txt_store_sku_gross_val.setVisibility(View.GONE);
					txt_store_sku_tac_val.setVisibility(View.GONE);
					txt_store_sku_net_val.setVisibility(View.GONE);
	        	 
	        	 LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
	 			 final View view=inflater.inflate(R.layout.list_item_card_skuwise,null);
	 			
	 			
	 			TextView tv_product_name=(TextView) view.findViewById(R.id.txt_prdct);
				tv_product_name.setText(s3);
				
				TextView txt_mrp=(TextView) view.findViewById(R.id.txt_mrp);
				txt_mrp.setText(s4);
				
				TextView txt_rate=(TextView) view.findViewById(R.id.txt_rate);
                txt_rate.setText(s5);
                
                TextView txt_stores=(TextView) view.findViewById(R.id.txt_stores);
				txt_stores.setText(s6);
				
				TextView txt_order=(TextView) view.findViewById(R.id.txt_order);
				txt_order.setText(s7);
				
				TextView txt_free=(TextView) view.findViewById(R.id.txt_free);
				txt_free.setText(s8);	
                
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				//txt_discnt_val.setText(s9);
				
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				//txt_gross_val.setText(s10);
				
				
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				//txt_tac_val.setText(s11);
				
				
				
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				//txt_net_val.setText(s12);
				
				
				
				Double disc_val=Double.parseDouble(s9);
				disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
				txt_discnt_val.setText(""+disc_val.intValue());
				
				Double ValBeforeTax1=Double.parseDouble(s10);
				//ValBeforeTax1=Math.round(ValBeforeTax1 * 100.0)/100.0;
				//double roundOff = Math.round(a * 100.0) / 100.0;
		 		ValBeforeTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
		 		txt_gross_val.setText(""+ValBeforeTax1.intValue());
		 		
		 		
		 		Double ValTax1=Double.parseDouble(s11);
		 		//ValTax1=Math.round(ValTax1 * 100.0)/100.0;
		 		ValTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
		 		txt_tac_val.setText(""+ValTax1.intValue());
		 		
		 		
		 		Double ValAfterTax1=Double.parseDouble(s12);
		 		//ValAfterTax1=Math.round(ValAfterTax1 * 100.0)/100.0;
		 		ValAfterTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
		 		txt_net_val.setText(""+ValAfterTax1.intValue());
				
				ll_store_sku.addView(view);
	         }
	         ll_Scroll_product.addView(viewParent);
	         
		}
		
		
		
			
			
	}


}
