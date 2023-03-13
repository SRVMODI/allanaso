package com.astix.allanasosfa;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.allanasosfa.R;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class RemainingStockStatusReport extends BaseActivity
{

	ArrayAdapter<String> dataAdapter = null;
	 String[] storeNames;
	 
	 LinkedHashMap<String, String> hmapStore_details=new LinkedHashMap<String, String>();
	 
	View viewProduct;
	 String date_value="";
		String imei="";
		String rID;
		String pickerDate="";
public LinearLayout ll_product_stock;
		public String back="0";
		public int bck = 0;
	public LinearLayout listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.activity_remaining_stock_status_data);
		 
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
		
		 getAllStoreListDetail();
		 
		 initialization();
	}
	

	private void getAllStoreListDetail() 
	{
			
		hmapStore_details= mDataSource.fetch_Store_RemaningStockStatus();

		}
	
	public void initialization()
	{
		
		ImageView but_back=(ImageView)findViewById(R.id.backbutton);
		but_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ide=new Intent(RemainingStockStatusReport.this,StoreSelection.class);
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
				if(!hmapStore_details.containsKey("No Stock")) {
					createProductDetail();
				}
	}

	public void createProductDetail() {
		LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 listView = (LinearLayout) findViewById(R.id.listView1);


if(hmapStore_details!=null && hmapStore_details.size()>0)
{
	int index=0;
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapStore_details);
	Set set2 = map.entrySet();
	Iterator iterator = set2.iterator();

	while(iterator.hasNext())
	{
		Map.Entry me2 = (Map.Entry)iterator.next();
		viewProduct=inflater.inflate(R.layout.list_remaning_stock_report,null);

		TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);
		tv_product_name.setText(me2.getKey().toString());

		TextView tvOpnStk=(TextView) viewProduct.findViewById(R.id.tvOpnStk);
		tvOpnStk.setText(me2.getValue().toString().split(Pattern.quote("^"))[1]);

		TextView tvSales=(TextView) viewProduct.findViewById(R.id.tvSales);
		tvSales.setText(me2.getValue().toString().split(Pattern.quote("^"))[2]);

		TextView tv_Remaningstock=(TextView) viewProduct.findViewById(R.id.tvRemaingStock);
		tv_Remaningstock.setText(me2.getValue().toString().split(Pattern.quote("^"))[0]);

		listView.addView(viewProduct);
		index=index+1;
	}



}



	}


}
