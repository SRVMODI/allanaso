package com.astix.allanasosfa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allanasosfa.truetime.TimeUtils;
import com.astix.Common.CommonInfo;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class DayEndStoreCollectionsChequeReport extends BaseActivity {
    LinkedHashMap<String,String> hmapAllStoreWiseCollectionReport=new  LinkedHashMap<String,String>();
    LinearLayout ll_storeInfo;
    Button btn_updateCollection,btn_Next;
    ImageView imgVw_back;
    Integer flgCollReqATCycleEnd=1;
    Integer flgStockUnloadAtCycleEnd=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_end_store_collections_cheque_report);
        Intent intent=  getIntent();
        flgCollReqATCycleEnd=    intent.getIntExtra("flgCollReqATCycleEnd",0);
        flgStockUnloadAtCycleEnd= intent.getIntExtra("flgStockUnloadAtCycleEnd",0);
        hmapAllStoreWiseCollectionReport=mDataSource.fnGetAllStoreWiseCollectionReport();
        LinearLayout ll_storeInfo=(LinearLayout)findViewById(R.id.ll_storeInfo);
        imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        btn_updateCollection = (Button) findViewById(R.id.btn_updateCollection);
                btn_Next = (Button) findViewById(R.id.btn_Next);

        if(CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd")==0 && CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd")==0)
        {
            btn_Next.setVisibility(View.GONE);
        }
        if( CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd")==1 && flgStockUnloadAtCycleEnd==0)
        {
            btn_Next.setVisibility(View.GONE);
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        btn_updateCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date1 = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String fDate = sdf.format(date1).toString().trim();

                sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String fDateNew = TimeUtils.getNetworkDateTime(DayEndStoreCollectionsChequeReport.this,TimeUtils.DATE_FORMAT);
                String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                Intent storeIntent = new Intent(DayEndStoreCollectionsChequeReport.this, collectionReportStoreList.class);
                storeIntent.putExtra("imei", CommonInfo.imei);
                storeIntent.putExtra("userDate", fDate);
                storeIntent.putExtra("pickerDate", fDateNew);
                storeIntent.putExtra("rID", rID);
                storeIntent.putExtra("PageFrom", "2");
                storeIntent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                storeIntent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                startActivity(storeIntent);
                finish();
            }
        });
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DayEndStoreCollectionsChequeReport.this,StockUnloadEndClosure.class);
                intent.putExtra("IntentFrom",1);
                intent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                intent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                startActivity(intent);
                finish();
            }
        });
        imgVw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayEndStoreCollectionsChequeReport.this, DayCollectionReport.class);
                intent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                intent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                DayEndStoreCollectionsChequeReport.this.startActivity(intent);
                finish();
            }
        });
        if(hmapAllStoreWiseCollectionReport!=null && hmapAllStoreWiseCollectionReport.size()>0)
        {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapAllStoreWiseCollectionReport);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                String StoreName=me2.getKey().toString();
                String CheequeFulleDetails=(String)me2.getValue();

                View layoutStoreWise = inflater.inflate(R.layout.store_wise_collection_report, null);
                TextView txtStoreName= (TextView) layoutStoreWise.findViewById(R.id.txtStoreName);

                TextView txtInvoiceAmount= (TextView) layoutStoreWise.findViewById(R.id.txtInvoiceAmount);
                TextView txtCollection= (TextView) layoutStoreWise.findViewById(R.id.txtCollection);
                TextView txtChqAmt= (TextView) layoutStoreWise.findViewById(R.id.txtChqAmt);
                TextView txtBalance= (TextView) layoutStoreWise.findViewById(R.id.txtBalance);
                ImageView btnModify= (ImageView) layoutStoreWise.findViewById(R.id.btnModify);
               btnModify.setTag(StoreName);
                txtStoreName.setText(StoreName.split(Pattern.quote("^"))[1]);
                txtInvoiceAmount.setText(CheequeFulleDetails.split(Pattern.quote("^"))[0]);
                txtCollection.setText(CheequeFulleDetails.split(Pattern.quote("^"))[1]);
                txtChqAmt.setText(CheequeFulleDetails.split(Pattern.quote("^"))[2]);
                txtBalance.setText(CheequeFulleDetails.split(Pattern.quote("^"))[3]);
                /*if(Double.parseDouble(CheequeFulleDetails.split(Pattern.quote("^"))[3])>0.0)
                {
                    btnModify.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnModify.setVisibility(View.GONE);
                }*/
               // btnModify.setText("Modify");
                btnModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Date date1 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String fDate = sdf.format(date1).toString().trim();

                        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String fDateNew = TimeUtils.getNetworkDateTime(DayEndStoreCollectionsChequeReport.this,TimeUtils.DATE_FORMAT);
                        String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                        String StoreName=v.getTag().toString();
                      /*    Intent storeIntent = new Intent(DayEndStoreCollectionsChequeReport.this, collectionReportStoreList.class);
                        storeIntent.putExtra("imei", CommonInfo.imei);
                        storeIntent.putExtra("userDate", fDate);
                        storeIntent.putExtra("pickerDate", fDateNew);
                        storeIntent.putExtra("rID", rID);
                        startActivity(storeIntent);
                        finish();*/

                            Intent ready4GetLoc = new Intent(DayEndStoreCollectionsChequeReport.this,CollectionDetailsStoreWise.class);
                            ready4GetLoc.putExtra("storeID", StoreName.split(Pattern.quote("^"))[0]);
                            ready4GetLoc.putExtra("selStoreName", StoreName.split(Pattern.quote("^"))[1]);
                            ready4GetLoc.putExtra("imei", CommonInfo.imei);
                            ready4GetLoc.putExtra("userDate", fDate);
                            ready4GetLoc.putExtra("pickerDate", fDateNew);
                            ready4GetLoc.putExtra("bck", 0);
                            ready4GetLoc.putExtra("PageFrom", "2");
                        ready4GetLoc.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                        ready4GetLoc.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                            startActivity(ready4GetLoc);
                            finish();


                    }
                });


                ll_storeInfo.addView(layoutStoreWise);
            }
        }
    }

}
