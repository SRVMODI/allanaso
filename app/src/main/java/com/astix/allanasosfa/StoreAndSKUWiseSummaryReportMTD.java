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
import com.astix.allanasosfa.utils.AppUtils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class StoreAndSKUWiseSummaryReportMTD extends BaseActivity   implements InterfaceRetrofit
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
		
		
		setContentView(R.layout.store_and_sku_wise_mtd);
     
		Intent extras = getIntent();
		bck = extras.getIntExtra("bck", 0);
		
		
		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
	    }


		imei = AppUtils.getIMEI(this);
		
		
		Date date1=new Date();
		sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = TimeUtils.getNetworkDateTime(StoreAndSKUWiseSummaryReportMTD.this,TimeUtils.DATE_FORMAT);

		if(isOnline())
		{

		/* try
		    {
		      GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay(StoreAndSKUWiseSummaryReportMTD.this);
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
				CommonFunction.getAllStoreSKUWiseMTDSummaryReport(StoreAndSKUWiseSummaryReportMTD.this,imei, CommonInfo.RegistrationID,"Please wait generating report.");

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
					Intent ide=new Intent(StoreAndSKUWiseSummaryReportMTD.this,DetailReportSummaryActivity.class);
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
		AllDataContainer= mDataSource.fetchAllDataFromtblStoreSKUWiseDaySummary();
		TextView tvValBeforeTax=(TextView) findViewById(R.id.tvValBeforeTax);
		tvValBeforeTax.setVisibility(View.GONE);
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
        
         
       /* initialValues.put("AutoId", AutoId);
 		initialValues.put("ProductId", ProductId.trim());
 		initialValues.put("Product", Product.trim());
 		initialValues.put("MRP", MRP.trim());
 		initialValues.put("Rate", Rate.trim());
 		initialValues.put("OrderQty", OrderQty.trim());
 		initialValues.put("FreeQty", FreeQty.trim());
 		initialValues.put("DiscValue", DiscValue.trim());
 		initialValues.put("ValBeforeTax", ValBeforeTax.trim());
 		initialValues.put("TaxValue", TaxValue.trim());
 		initialValues.put("ValAfterTax", ValAfterTax.trim());
 		initialValues.put("Lvl", Lvl.trim());
 		initialValues.put("StoreId", StoreId.trim());*/
     	
		/*TextView total_LinesPerBill=(TextView)rootView.findViewById(R.id.total_LinesPerBill);
		total_LinesPerBill.setText(a3);
		TextView total_StockValue=(TextView)rootView.findViewById(R.id.total_StockValue);
		total_StockValue.setText(a4);*/
		TextView total_discountValue=(TextView)findViewById(R.id.tvDisCount);
		Double disc_val=Double.parseDouble(a8);
		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
		total_discountValue.setText(""+disc_val.intValue());
		
		
		TextView total_ValBeforeTax=(TextView)findViewById(R.id.total_ValBeforeTax);
		Double ValBeforeTax=Double.parseDouble(a9);
		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
			total_ValBeforeTax.setVisibility(View.GONE);
		TextView total_ValTax=(TextView)findViewById(R.id.total_ValTax);
		Double ValTax=Double.parseDouble(a10);
		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
		total_ValTax.setText(""+ValTax.intValue());
		
		
		TextView total_ValAfterTax=(TextView)findViewById(R.id.total_ValAfterTax);
		Double ValAfterTax=Double.parseDouble(a11);
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
	         String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14="";
	         try
	         {
	         s14 = tokens1.nextToken().toString().trim();
	         }
	         catch(Exception e)
	         {
	        	 
	         }
	        
	         
	         System.out.println("Value of level :"+s13);
	         
	         
	        LayoutInflater inflaterParent=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View viewParent=inflaterParent.inflate(R.layout.list_store_sku_header_wise,null);
				
           LinearLayout ll_store_sku=(LinearLayout) viewParent.findViewById(R.id.ll_store_sku);
	         
	         if(Integer.parseInt(s12)==1)
	         {
	        	
				
				 TextView txt_store_sku=(TextView) viewParent.findViewById(R.id.txt_store_sku);
				 TextView txt_store_sku_discnt_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_discnt_val);
				 TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				 TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				 TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				
				
				txt_store_sku.setText(s3);
				/*//mDataSource.open();
				String StoreName=mDataSource.FetchStoreName(s13.trim());
				//mDataSource.close();
				txt_store_sku.setText(StoreName);*/
				
				Double disc_val1=Double.parseDouble(s8);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_store_sku_discnt_val.setText(""+disc_val1.intValue());

				
				
				Double ValBeforeTax1=Double.parseDouble(s9);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_store_sku_gross_val.setText(""+ValBeforeTax1.intValue());
				 txt_store_sku_gross_val.setVisibility(View.GONE);
				/* if(txt_store_sku_gross_val.getVisibility()==View.GONE) {
					 txt_store_sku_gross_val.setVisibility(View.VISIBLE);
				 }*/
				
				Double ValTax1=Double.parseDouble(s10);
		 		ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_store_sku_tac_val.setText(""+ValTax1.intValue());
				
				
				Double ValAfterTax1=Double.parseDouble(s11);
		 		ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_store_sku_net_val.setText(""+ValAfterTax1.intValue());
				
				
	         }
	         else if(Integer.parseInt(s12)==2)
	         {
	        	 TextView txt_store_sku=(TextView) viewParent.findViewById(R.id.txt_store_sku);
				 TextView txt_store_sku_discnt_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_discnt_val);
				 TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				 TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				 TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				 txt_store_sku.setVisibility(View.GONE);
				 txt_store_sku_discnt_val.setVisibility(View.GONE);
				/* if(txt_store_sku_gross_val.getVisibility()==View.GONE) {
					 txt_store_sku_gross_val.setVisibility(View.VISIBLE);
				 }*/
				 txt_store_sku_tac_val.setVisibility(View.GONE);
				 txt_store_sku_net_val.setVisibility(View.GONE);
					
	        	 LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 			 final View view=inflater.inflate(R.layout.list_store_sku_wise,null);
	 			
	 			 /* initialValues.put("AutoId", AutoId);
		      		initialValues.put("ProductId", ProductId.trim());
		      		initialValues.put("Product", Product.trim());
		      		initialValues.put("MRP", MRP.trim());
		      		initialValues.put("Rate", Rate.trim());
		      		initialValues.put("OrderQty", OrderQty.trim());
		      		initialValues.put("FreeQty", FreeQty.trim());
		      		initialValues.put("DiscValue", DiscValue.trim());
		      		initialValues.put("ValBeforeTax", ValBeforeTax.trim());
		      		initialValues.put("TaxValue", TaxValue.trim());
		      		initialValues.put("ValAfterTax", ValAfterTax.trim());
		      		initialValues.put("Lvl", Lvl.trim());
		      		initialValues.put("StoreId", StoreId.trim());*/
				
	 			TextView tv_product_name=(TextView) view.findViewById(R.id.txt_prdct);
				tv_product_name.setText(s3);
				
				TextView txt_mrp=(TextView) view.findViewById(R.id.txt_mrp);
				txt_mrp.setText(s4);
				
				TextView txt_rate=(TextView) view.findViewById(R.id.txt_rate);
				txt_rate.setText(s5);
				
				TextView txt_stock=(TextView) view.findViewById(R.id.txt_stock);
				if(s14.equals("") || s14.equals(null))
				{
					txt_stock.setText("");
				}
				else
				{
					Double Valstock=Double.parseDouble(s14);
					Valstock=Double.parseDouble(new DecimalFormat("##.##").format(Valstock));
					txt_stock.setText(""+Valstock.intValue());
				}
				
				
				TextView txt_order=(TextView) view.findViewById(R.id.txt_order);
				txt_order.setText(s6);
				
				TextView txt_free=(TextView) view.findViewById(R.id.txt_free);
				txt_free.setText(s7);
				
				
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				Double disc_val1=Double.parseDouble(s8);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_discnt_val.setText(""+disc_val1.intValue());
				
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				Double ValBeforeTax1=Double.parseDouble(s9);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_gross_val.setText(""+ValBeforeTax1.intValue());

				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				Double ValTax1=Double.parseDouble(s10);
				ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_tac_val.setText(""+ValTax1.intValue());
				
				
				
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				Double ValAfterTax1=Double.parseDouble(s11);
				ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_net_val.setText(""+ValAfterTax1.intValue());
				
				
				ll_store_sku.addView(view);
	         }
	         ll_Scroll_product.addView(viewParent);
	         
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

