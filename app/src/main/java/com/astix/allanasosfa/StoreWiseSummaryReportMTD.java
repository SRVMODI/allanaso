package com.astix.allanasosfa;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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

public class StoreWiseSummaryReportMTD extends BaseActivity implements InterfaceRetrofit
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
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.store_summary_mtd);
     
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
		fDate = TimeUtils.getNetworkDateTime(StoreWiseSummaryReportMTD.this,TimeUtils.DATE_FORMAT);
		if(isOnline())
		{
		/* try
		    {
		      GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay(StoreWiseSummaryReportMTD.this);
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

				CommonFunction.getAllStoreWiseMTDSummaryReport(StoreWiseSummaryReportMTD.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

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
					Intent ide=new Intent(StoreWiseSummaryReportMTD.this,DetailReportSummaryActivity.class);
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
		AllDataContainer= mDataSource.fetchAllDataFromtblStoreWiseDaySummary();
		
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
        
        
		
		TextView total_LinesPerBill=(TextView)findViewById(R.id.total_LinesPerBill);
		total_LinesPerBill.setText(a3);
		TextView total_StockValue=(TextView)findViewById(R.id.total_StockValue);
		total_StockValue.setText(a4);
		
		
		
		TextView total_discountValue=(TextView)findViewById(R.id.total_discountValue);
		Double disc_val=Double.parseDouble(a5);
		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
		total_discountValue.setText(""+disc_val.intValue());
		
		TextView total_ValBeforeTax=(TextView)findViewById(R.id.total_ValBeforeTax);
		Double ValBeforeTax=Double.parseDouble(a6);
		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
		
		TextView total_ValTax=(TextView)findViewById(R.id.total_ValTax);
		Double ValTax=Double.parseDouble(a7);
		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
		total_ValTax.setText(""+ValTax.intValue());
		
		TextView total_ValAfterTax=(TextView)findViewById(R.id.total_ValAfterTax);
		Double ValAfterTax=Double.parseDouble(a8);
		ValAfterTax=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		
		
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
	        /* String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14 = tokens1.nextToken().toString().trim();
	         String s15 = tokens1.nextToken().toString().trim();*/
	         
	         LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View view=inflater.inflate(R.layout.list_store_wise_mt,null);
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
			    txt_gross_val.setVisibility(View.VISIBLE);
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
	@Override
	public void success() {
		intializeFields();
	}

	@Override
	public void failure() {

	}
}

