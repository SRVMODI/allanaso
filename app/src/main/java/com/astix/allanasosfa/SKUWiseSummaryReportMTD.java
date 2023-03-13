package com.astix.allanasosfa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.allanasosfa.truetime.TimeUtils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class SKUWiseSummaryReportMTD extends BaseActivity   implements InterfaceRetrofit
{

	public String imei;
	public String fDate;
	public SimpleDateFormat sdf;

	
	LinearLayout ll_Scroll_product,ll_scheme_detail;

	int count=1;
	
	int pos=0;
	
	public String[] AllDataContainer;

	
	public int bck = 0;
	String date_value="";
	String pickerDate="";
	String rID;


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			// finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.sku_summary_mtd);
		
		
		Intent extras = getIntent();
		bck = extras.getIntExtra("bck", 0);
		
		
		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
	    }
     
       
        
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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
		fDate = TimeUtils.getNetworkDateTime(SKUWiseSummaryReportMTD.this,TimeUtils.DATE_FORMAT);
		//fDate="29-10-2015";
		
		//'868087024619932','29-10-2015'  
		if(isOnline())
		{

			 /*try
			    {
			      GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay(SKUWiseSummaryReportMTD.this);
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
				CommonFunction.getAllSKUWiseMTDSummaryReport(SKUWiseSummaryReportMTD.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
		}
		 
		
		ImageView btnBack=(ImageView)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ide=new Intent(SKUWiseSummaryReportMTD.this,DetailReportSummaryActivity.class);
				ide.putExtra("userDate", date_value);
				ide.putExtra("pickerDate", pickerDate);
				ide.putExtra("imei", imei);
				ide.putExtra("rID", rID);
				ide.putExtra("back", "1");
				startActivity(ide);
				finish();
			}
		});
       
    }
    

	
	private void intializeFields() 
	{
		AllDataContainer= mDataSource.fetchAllDataFromtblSKUWiseDaySummary();
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
         
         
        
        
		
		TextView total_stores=(TextView)findViewById(R.id.total_stores);
		total_stores.setText(a6);
		TextView total_orderQty=(TextView)findViewById(R.id.total_orderQty);
		TextView total_freeQty=(TextView)findViewById(R.id.total_freeQty);
		
		
		
		TextView total_discountValue=(TextView)findViewById(R.id.total_discountValue);
 		Double DisValue=Double.parseDouble(a9);
 		DisValue= Double.parseDouble(new DecimalFormat("##.##").format(DisValue));
 		total_discountValue.setText(""+DisValue.intValue());
 		
 		TextView total_ValBeforeTax=(TextView)findViewById(R.id.total_ValBeforeTax);
 		Double ValBeforeTax=Double.parseDouble(a10);
 		ValBeforeTax= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
 		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
 		
 		TextView total_ValTax=(TextView)findViewById(R.id.total_ValTax);
 		Double ValTax=Double.parseDouble(a11);
 		ValTax= Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
 		total_ValTax.setText(""+ValTax.intValue());
 		
 		TextView total_ValAfterTax=(TextView)findViewById(R.id.total_ValAfterTax);
 		Double ValAfterTax=Double.parseDouble(a12);
 		ValAfterTax= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
 		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		}
		
		ll_Scroll_product=(LinearLayout) findViewById(R.id.ll_Scroll_product);
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
	         
	         
	        LayoutInflater inflaterParent=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				 txt_store_sku_gross_val.setVisibility(View.VISIBLE);
		 		
		 		Double ValTax1=Double.parseDouble(s11);
		 		//ValTax1=Math.round(ValTax1 * 100.0)/100.0;
		 		ValTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
		 		txt_store_sku_tac_val.setText(""+ValTax1.intValue());
		 		
		 		
		 		Double ValAfterTax1=Double.parseDouble(s12);
		 		//ValAfterTax1=Math.round(ValAfterTax1 * 100.0)/100.0;
		 		ValAfterTax1= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
		 		txt_store_sku_net_val.setText(""+ValAfterTax1.intValue());
			//	 txt_store_sku_net_val.setVisibility(View.VISIBLE);
				
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
	        	 
	        	 LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				 txt_gross_val.setVisibility(View.VISIBLE);
				
				
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
				 txt_net_val.setVisibility(View.VISIBLE);
				ll_store_sku.addView(view);
	         }
	         ll_Scroll_product.addView(viewParent);
	         
		}
		
		
		
			
			
	}
	@Override
	public void success() {
		intializeFields();
	}

	@Override
	public void failure() {

	}
}

