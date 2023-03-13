package com.astix.allanasosfa;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddedOutletSummaryReportActivity extends BaseActivity  implements InterfaceRetrofit
{
    private ImageView img_back;
    private RadioGroup radioGrp;
    private RadioButton rb_byRoute;
    private RadioButton rb_byCat;
    private Button btn_refresh;
    private LinearLayout ll_inflateParent,header,ll_overallDetails;
    private TextView txt_lstVisitTime,textView_ChildName,textView_OverAllTotal,textView_OverAllValidate,textView_OverAllPending;
    private ScrollView scroll;



    private LinkedHashMap<String,ArrayList<String>> hmapSummaryDataByHeader;
    String overAllSummaryDetail="NA";
    private String imei,pickerDate,userDate;
    private int flgToCall=0;

    public boolean onKeyDown(int keyCode, KeyEvent event)    // Control the PDA Native Button Handling
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            return true;
            // finish();
        }
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {
            // finish();

        }
        if(keyCode==KeyEvent.KEYCODE_MENU)
        {
            return true;
        }
        if(keyCode== KeyEvent.KEYCODE_SEARCH)
        {
            return true;
        }

        return super.onKeyDown(keyCode, event);}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_outlet_summary_report);




        if (CommonInfo.imei.equals(""))
        {
            CommonInfo.imei = imei;
        }
        else
        {
            imei=CommonInfo.imei;
        }

        initialiseViews();

        if(!isOnline())
        {
            showNoConnAlert();
        }
        else
        {
            byDefaultChecked();
        }
    }



    private void initialiseViews()
    {
        textView_ChildName= (TextView) findViewById(R.id.textView_ChildName);
        header= (LinearLayout) findViewById(R.id.header);
        ll_overallDetails= (LinearLayout) findViewById(R.id.ll_overallDetails);
        textView_OverAllTotal= (TextView) findViewById(R.id.textView_OverAllTotal);
        textView_OverAllValidate= (TextView) findViewById(R.id.textView_OverAllValidate);
        textView_OverAllPending= (TextView) findViewById(R.id.textView_OverAllPending);

        scroll= (ScrollView) findViewById(R.id.scroll);

        img_back= (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* Intent intent=new Intent(AddedOutletSummaryReportActivity.this,StoreSelection.class);
                    intent.putExtra("imei", imei);
                    intent.putExtra("userDate", userDate);
                    intent.putExtra("pickerDate", pickerDate);
                    startActivity(intent);*/
                    finish();
            }
        });

        radioGrp= (RadioGroup) findViewById(R.id.radioGrp);

        rb_byRoute= (RadioButton) findViewById(R.id.rb_byRoute);
        rb_byRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline())
                {
                    showNoConnAlert();
                }
                else
                {
                    if(rb_byRoute.isChecked())
                    {
                        flgToCall=1; //1 for route
                    }

                    try
                    {
                        header.setVisibility(View.GONE);
                        ll_overallDetails.setVisibility(View.GONE);
                        CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
       /* rb_byRoute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!isOnline())
                {
                    showNoConnAlert();
                }
                else
                {
                    if(rb_byRoute.isChecked())
                {
                    flgToCall=1; //1 for route
                }

                    try
                    {
                        header.setVisibility(View.GONE);
                        ll_overallDetails.setVisibility(View.GONE);
                        CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        rb_byCat= (RadioButton) findViewById(R.id.rb_byCat);
        rb_byCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline())
                {
                    showNoConnAlert();
                }
                else
                {
                    if(rb_byCat.isChecked())
                    {
                        flgToCall=2; //2 for catgory
                    }

                    try
                    {
                        header.setVisibility(View.GONE);
                        ll_overallDetails.setVisibility(View.GONE);
                        CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
      /*  rb_byCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isOnline())
                {
                    showNoConnAlert();
                }
                else
                {
                    if(rb_byCat.isChecked())
                    {
                        flgToCall=2; //2 for catgory
                    }

                    try
                    {
                        header.setVisibility(View.GONE);
                        ll_overallDetails.setVisibility(View.GONE);
                        CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
*/
        btn_refresh= (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!isOnline())
                {
                    showNoConnAlert();
                }
                else
                {
                    if(rb_byRoute.isChecked())
                    {
                        flgToCall=1;
                    }
                    else if(rb_byCat.isChecked())
                    {
                        flgToCall=2;
                    }
                    try
                    {
                        header.setVisibility(View.GONE);
                        ll_overallDetails.setVisibility(View.GONE);
                        CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        ll_inflateParent= (LinearLayout) findViewById(R.id.ll_inflateParent);
        txt_lstVisitTime= (TextView) findViewById(R.id.txt_lstVisitTime);

    }


    private void inflateRows()
    {
        header.setVisibility(View.VISIBLE);
        ll_overallDetails.setVisibility(View.VISIBLE);

        ll_inflateParent.removeAllViews();

        if(!overAllSummaryDetail.equals("NA"))
        {
            textView_OverAllTotal.setText(overAllSummaryDetail.split(Pattern.quote("^"))[0]);
            textView_OverAllValidate.setText(overAllSummaryDetail.split(Pattern.quote("^"))[1]);
            textView_OverAllPending.setText(overAllSummaryDetail.split(Pattern.quote("^"))[2]);
        }

        if(hmapSummaryDataByHeader != null && hmapSummaryDataByHeader.size()>0)
        {
            //ll_inflateParent.setBackgroundResource(R.drawable.shadow_deep_orng);

            if(flgToCall==1)
            {
                textView_ChildName.setText("Route/Category");
            }
            else
            {
                textView_ChildName.setText("Category/Route");
            }

           for(Map.Entry<String,ArrayList<String>> entry:hmapSummaryDataByHeader.entrySet())
           {
               String headerName=entry.getKey();
               ArrayList<String> list_headerData=entry.getValue();

               View dynamic_container=getLayoutInflater().inflate(R.layout.account_inflate_row_header,null);

               final LinearLayout llayout_for_header= (LinearLayout) dynamic_container.findViewById(R.id.llayout_for_header);
               llayout_for_header.setBackgroundColor(Color.parseColor("#66BB6A"));
               llayout_for_header.setTag("False");

               /*TextView textView_Header= (TextView) dynamic_container.findViewById(R.id.textView_Header);
               textView_Header.setText(headerName);*/

               final TextView textView_Child_Header= (TextView) dynamic_container.findViewById(R.id.textView_Child);
               SpannableString mySpannableString = new SpannableString(headerName);
               mySpannableString.setSpan(new UnderlineSpan(), 0, mySpannableString.length(), 0);
               textView_Child_Header.setText(mySpannableString);

               final TextView textView_TotalStore_Header= (TextView) dynamic_container.findViewById(R.id.textView_TotalStore);
               textView_TotalStore_Header.setText(list_headerData.get(0).split(Pattern.quote("^"))[1]);

               final TextView textView_Validated_Header= (TextView) dynamic_container.findViewById(R.id.textView_Validated);
               textView_Validated_Header.setText(list_headerData.get(0).split(Pattern.quote("^"))[2]);

               final TextView textView_Pending_Header= (TextView) dynamic_container.findViewById(R.id.textView_Pending);
               textView_Pending_Header.setText(list_headerData.get(0).split(Pattern.quote("^"))[3]);

              /* final ImageView img_expand= (ImageView) dynamic_container.findViewById(R.id.img_expand);*/
               final LinearLayout llayout_for_row= (LinearLayout) dynamic_container.findViewById(R.id.llayout_for_row);

               llayout_for_header.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                        if(v.getTag().toString().equals("False"))
                        {
                            //img_expand.setBackgroundResource(R.drawable.collapse_arrow);
                            llayout_for_header.setTag("True");
                            llayout_for_header.setBackgroundColor(Color.RED);
                            textView_Child_Header.setTextColor(Color.WHITE);
                            textView_TotalStore_Header.setTextColor(Color.WHITE);
                            textView_Validated_Header.setTextColor(Color.WHITE);
                            textView_Pending_Header.setTextColor(Color.WHITE);
                            llayout_for_row.setVisibility(View.VISIBLE);
                        }
                        else if(v.getTag().toString().equals("True"))
                        {
                            //img_expand.setBackgroundResource(R.drawable.expand_arrow);
                            llayout_for_header.setTag("False");
                            llayout_for_header.setBackgroundColor(Color.parseColor("#66BB6A"));
                            textView_Child_Header.setTextColor(Color.BLACK);
                            textView_TotalStore_Header.setTextColor(Color.BLACK);
                            textView_Validated_Header.setTextColor(Color.BLACK);
                            textView_Pending_Header.setTextColor(Color.BLACK);
                            llayout_for_row.setVisibility(View.GONE);
                        }
                   }
               });

               for (int i=0;i<list_headerData.size();i++)
               {
                   if(i != 0)
                   {
                       View dynamic_container_row=getLayoutInflater().inflate(R.layout.account_inflate_row,null);

                       LinearLayout ll_parentRow= (LinearLayout) dynamic_container_row.findViewById(R.id.ll_parentRow);
                       ll_parentRow.setBackgroundResource(R.drawable.row_round_corner);

                       TextView textView_Child= (TextView) dynamic_container_row.findViewById(R.id.textView_Child);
                       textView_Child.setText(list_headerData.get(i).split(Pattern.quote("^"))[0]);

                       TextView textView_TotalStore= (TextView) dynamic_container_row.findViewById(R.id.textView_TotalStore);
                       textView_TotalStore.setText(list_headerData.get(i).split(Pattern.quote("^"))[1]);

                       TextView textView_Validated= (TextView) dynamic_container_row.findViewById(R.id.textView_Validated);
                       textView_Validated.setText(list_headerData.get(i).split(Pattern.quote("^"))[2]);

                       TextView textView_Pending= (TextView) dynamic_container_row.findViewById(R.id.textView_Pending);
                       textView_Pending.setText(list_headerData.get(i).split(Pattern.quote("^"))[3]);

                       llayout_for_row.addView(dynamic_container_row);
                   }
               }

               ll_inflateParent.addView(dynamic_container);

               /*View spaceView=new View(AddedOutletSummaryReportActivity.this);
               LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,5);
               spaceView.setLayoutParams(params);
               ll_inflateParent.addView(spaceView);*/
           }
       }
    }

    private void byDefaultChecked()
    {
        rb_byRoute.setChecked(true);
        btn_refresh.setVisibility(View.VISIBLE);
        flgToCall=1; //1 for route

        try
        {
            header.setVisibility(View.GONE);
            ll_overallDetails.setVisibility(View.GONE);
            CommonFunction.getAllAddedOutletSummaryReportModel(AddedOutletSummaryReportActivity.this,imei,CommonInfo.RegistrationID,"Please wait generating report.",flgToCall);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void success() {
        hmapSummaryDataByHeader=mDataSource.fetchtblDAGetAddedOutletSummaryReport();
        overAllSummaryDetail=mDataSource.fetchtblDAGetAddedOutletOverAllData();
        inflateRows();
    }

    @Override
    public void failure() {

    }

}
