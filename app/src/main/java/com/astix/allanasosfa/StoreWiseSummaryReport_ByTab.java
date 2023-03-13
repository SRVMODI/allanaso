package com.astix.allanasosfa;


import android.graphics.Typeface;
import android.support.v7.app.ActionBar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TextView;

import com.astix.sancussosfa.R;

public class StoreWiseSummaryReport_ByTab extends BaseActivity
{

	String date_value="";
	String imei="";
	String pickerDate="";
	String rID;


	public TableLayout
			tl2;
	public int bck = 0;
	public String Noti_text="Null";
	public int MsgServerID=0;



	// Declaring our tabs and the corresponding fragments.
	ActionBar.Tab OneTab, TwoTab, ThreeTab;

	Fragment fragmentOneTab = new StoreWiseFragmentOneTab();
	Fragment fragmentTwoTab = new StoreWiseFragmentTwoTab();
	Fragment fragmentThreeTab = new StoreWiseFragmentThreeTab();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.mysummary_bytab);

		Intent extras = getIntent();
		bck = extras.getIntExtra("bck", 0);


		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
		}


		try
		{

			// Asking for the default ActionBar element that our platform supports.
			ActionBar actionBar = getSupportActionBar();

			// Screen handling while hiding ActionBar icon.
			actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.ic_back);

			// Screen handling while hiding Actionbar title.

			actionBar.setTitle(getResources().getString(R.string.txtStoreWiseSummary));
//        actionBar.setTitle("  SKU Wise Summary");
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			// Creating ActionBar tabs.
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


			// Setting custom tab icons.



			OneTab = actionBar.newTab();
			OneTab.setCustomView(createTab(R.string.submit_sent_server));

			TwoTab = actionBar.newTab().setText(R.string.saved_not_submit);
			TwoTab.setCustomView(createTab(R.string.saved_not_submit));

			ThreeTab = actionBar.newTab().setText(R.string.submit_not_sent_server);
			ThreeTab.setCustomView(createTab(R.string.submit_not_sent_server));


       /* TwoTab = actionBar.newTab().setText(R.string.submit_not_sent_server);
        ThreeTab = actionBar.newTab().setText(R.string.saved_not_submit);*/


			// Setting tab listeners.





			OneTab.setTabListener(new TabListener(fragmentOneTab)).setTag("fragmentOneTab");
			// TwoTab.setTabListener(new TabListener(fragmentTwoTab));
			ThreeTab.setTabListener(new TabListener(fragmentThreeTab));

			// Adding tabs to the ActionBar.





			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e71d35")));

			// set background for action bar tab
			actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

			actionBar.addTab(OneTab);
			// actionBar.addTab(TwoTab);
			actionBar.addTab(ThreeTab);

		}
		catch(Exception e)
		{

		}


	}

	private TextView createTab(int titleText)
	{
		TextView tv = new TextView(this);
		//set caption and caption appearance
		tv.setText(titleText);
		//set appearance of tab
		//	tv.setBackgroundResource(tabBackgroundDrawableId);
		ViewGroup.LayoutParams layoutParams = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(layoutParams);
		tv.setTypeface(null, Typeface.BOLD);


		//Make sure all tabs have the same height
		tv.setMaxLines(2);
		tv.setMinLines(2);
		tv.setTextColor(getResources().getColor(android.R.color.black));
		return tv;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				Intent ide=new Intent(StoreWiseSummaryReport_ByTab.this,DetailReportSummaryActivity.class);
				ide.putExtra("userDate", date_value);
				ide.putExtra("pickerDate", pickerDate);
				ide.putExtra("imei", imei);
				ide.putExtra("rID", rID);
				ide.putExtra("back", "1");
				startActivity(ide);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}



}

