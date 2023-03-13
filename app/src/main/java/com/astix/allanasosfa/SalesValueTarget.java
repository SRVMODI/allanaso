package com.astix.allanasosfa;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.sancussosfa.truetime.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SalesValueTarget extends Activity {
    public String fDate;
    TextView txt_stv;
    ImageView imgVw_next,imgVw_back;
    String imei,pickerDate,userDate;
    public SimpleDateFormat sdf;
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {
            // finish();
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_MENU)
        {
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_SEARCH)
        {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_value_target);
        txt_stv=(TextView) findViewById(R.id.txt_stv);
        imgVw_next=(ImageView) findViewById(R.id.imgVw_next);
        imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        Intent intent=getIntent();
        int intentFrom= intent.getIntExtra("IntentFrom", 0);
        if(intentFrom==1)
        {
            imei = intent.getStringExtra("imei").trim();
            pickerDate = intent.getStringExtra("pickerDate").trim();
            userDate = intent.getStringExtra("userDate");
        }
        //Html.fromHtml(CommonInfo.SalesPersonTodaysTargetMsg)
        if(!TextUtils.isEmpty(CommonInfo.SalesPersonTodaysTargetMsg))
        {
            txt_stv.setText(Html.fromHtml(CommonInfo.SalesPersonTodaysTargetMsg));
        }


        if(intentFrom==0)
        {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    /*Intent i=new Intent(SalesValueTarget.this,IncentiveActivity.class);
                    startActivity(i);
                    finish();*/
                    Date date1=new Date();
                    sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    fDate = TimeUtils.getNetworkDateTime(SalesValueTarget.this, TimeUtils.DATE_FORMAT);
                 /*   if ((CommonInfo.hmapAppMasterFlags.get("flgIncentiveShowtStart") == 1)) {
                        Intent i=new Intent(SalesValueTarget.this,IncentiveActivity.class);
                        i.putExtra("IntentFrom", 0);
                        startActivity(i);
                        finish();
                    }
                    else*/ if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1)) {
                        Intent intent = new Intent(SalesValueTarget.this, DistributorCheckInSecondActivity.class);
                        intent.putExtra("imei", CommonInfo.imei);
                        intent.putExtra("fDate", fDate);
                        intent.putExtra("From", "DayStart");
                        startActivity(intent);
                        finish();
                    }
                    else if ((CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
                        Intent intent = new Intent(SalesValueTarget.this, WarehouseCheckInSecondActivity.class);
                        intent.putExtra("imei", CommonInfo.imei);
                        intent.putExtra("fDate", fDate);
                        intent.putExtra("From", "DayStart");
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SalesValueTarget.this, AllButtonActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }
        else
        {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(SalesValueTarget.this,StoreSelection.class);
                    i.putExtra("imei", imei);
                    i.putExtra("userDate", userDate);
                    i.putExtra("pickerDate", pickerDate);
                    i.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                    startActivity(i);
                    finish();

                }
            });
        }



    }


}
