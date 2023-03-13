package com.astix.allanasosfa;

import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.astix.sancussosfa.location.LocationInterface;
import com.astix.sancussosfa.location.LocationRetreivingGlobal;
import com.astix.sancussosfa.sync.SyncJobService;
import com.astix.sancussosfa.utils.AppUtils;
import com.astix.sancussosfa.utils.PreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sancussosfa.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class DayStartActivity extends BaseActivity implements LocationInterface,OnMapReadyCallback,CompoundButton.OnCheckedChangeListener,InterfaceRetrofit
{
    LinearLayout ll_OverAllDistributionCheckin;
    TextView txt_DBR_Name;
    String reasonId="0";
    String otherReasonForLate="NA";
    EditText et_DBR_Name,rsnLatetext;
    ArrayList<String> listTxtBxToShow;
    ArrayAdapter<String> adapterDBR;
    String[] listLocationWrkng={"Select Working Location","At Warehouse Location","Other Location"};
    LinearLayout ll_DBR_Name;
    boolean isLateApplicable=false;
    LinkedHashMap<String,String> hmapReasonIdAndDescr_details;
    String crntServerTime;
    String crntAttndncTime="9:30 AM";
    String[] reasonLate;
    ArrayAdapter<String> adapterLocation;
    String FnlAddress="NA",finalPinCode="NA",finalCity="NA",finalState="NA",fnAccurateProvider="NA",AllProvidersLocation="NA",FusedAddress="NA",GpsLat="NA",GpsLong="NA"
            ,GpsAccuracy="NA",GpsAddress="NA",NetwLat="NA",NetwLong="NA",NetwAccuracy="NA" ,NetwAddress="NA",FusedLat="NA",FusedLong="NA",FusedAccuracy="NA"
            ,FusedLocationLatitudeWithFirstAttempt="NA" ,FusedLocationLongitudeWithFirstAttempt="NA",FusedLocationAccuracyWithFirstAttempt="NA";
    int flgLocationServicesOnOff=0,flgGPSOnOff=0,flgNetworkOnOff=0,flgFusedOnOff=0,flgInternetOnOffWhileLocationTracking=0,flgRestart=0;
    String imei;
    TextView txt_rfrshCmnt;
    LinkedHashMap<String,String> hmapDistLatLng;
    static int flgDaySartWorking = 0;
    public long syncTIMESTAMP;
    ProgressDialog pDialog2;
    String PersonNodeID="NA";
    String PersonNodeType="NA";
    String PersonName="NA";
    String OptionID="NA";
    String OptionDesc="NA";

    double distanceCalc=0.0;


    String cityID="NA";
    String StateID="NA";
    String MapAddress="NA";
    String MapPincode="NA";
    String MapCity="NA";
    String MapState="NA";

    int refreshCount=0;
    int countSubmitClicked=0;
    public LocationManager locationManager;
    int intentFrom=0;
    LinearLayout ll_map,ll_comment;

    EditText et_otherPleaseSpecify;
    public String ReasonId="0";;
    public String ReasonText="NA";
    public int chkFlgForErrorToCloseApp=0;
    String[] reasonNames;


    public String fDate;
    public SimpleDateFormat sdf;
    SharedPreferences sPrefAttandance;

    FragmentManager manager;
    FragmentTransaction fragTrans;
    MapFragment mapFrag;
    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    public String AccuracyFromLauncher="NA";
    String AddressFromLauncher="NA";
    LinearLayout ll_start,ll_startAfterDayEndFirst,ll_startAfterDayEndSecond,ll_Working,ll_NoWorking;
    LinearLayout ll_Working_parent,ll_NoWorking_parent,ll_reason;
    Button but_Next;

    TextView txt_DayStarttime;

    LinkedHashMap<String,String>  hmapSelectedCheckBoxData=new LinkedHashMap<String,String>();

    LinkedHashMap<Integer, String> hmapReasonIdAndDescrForWorking_details=new LinkedHashMap<Integer, String>();
    LinkedHashMap<Integer, String> hmapReasonIdAndDescrForNotWorking_details=new LinkedHashMap<Integer, String>();
    String reasonIdForMrktVisit;
    public CheckBox[] cb;
    public RadioButton[] rb;


    String[] Distribtr_list;
    String DbrNodeId,DbrNodeType,DbrName;
    ArrayList<String> DbrArray=new ArrayList<String>();


    RadioButton rb_workingYes,rb_workingNo;//=null;
    Spinner spinner_for_filter,spnr_late,spinner_location;


    String DistributorName_Global="Select Distributor";
    String DistributorId_Global="0";
    String DistributorNodeType_Global="0";
    private Button btn_refresh;
    private LinearLayout ll_refresh;
    private RadioGroup rg_yes_no;
    TextView tv_MapLocationCorrectText;

    private RadioButton rb_yes;
    private RadioButton rb_no;

    Context ctx;
    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }
    public void customHeader()
    {


        TextView tv_heading=(TextView) findViewById(R.id.tv_heading);
        tv_heading.setText("Day Start");

        ImageView imgVw_next=(ImageView) findViewById(R.id.imgVw_next);

        ImageView imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        imgVw_next.setVisibility(View.GONE);
        imgVw_back.setVisibility(View.GONE);
        if(intentFrom==0)
        {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(DayStartActivity.this,AllButtonActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }
        else
        {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(DayStartActivity.this,StoreSelection.class);

                    startActivity(i);
                    finish();

                }
            });
        }
    }
    private void getDataFromDatabase() throws IOException
    {
        reasonIdForMrktVisit= mDataSource.getReasonIdForMarketVisit();
        hmapReasonIdAndDescrForWorking_details= mDataSource.fetch_Reason_List_for_option();
        hmapReasonIdAndDescrForNotWorking_details= mDataSource.fetch_NoWorking_Reason_List();
        listTxtBxToShow= mDataSource.fetch_Text_To_Show();
    }


    private void createCheckBoxForWorking()
    {
        cb = new CheckBox[hmapReasonIdAndDescrForWorking_details.size()];

        int i = 0;
        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForWorking_details.entrySet())
        {
            EditText editText = null;
            cb[i] = new CheckBox(this);
            cb[i].setText(entry.getValue());
            cb[i].setTag(entry.getKey().toString().trim());
            cb[i].setOnCheckedChangeListener(this);

            if(listTxtBxToShow!=null && listTxtBxToShow.contains(entry.getKey().toString().trim()))
            {
                editText= getEditText(entry.getKey().toString().trim()+"_ed");
            }

            ll_Working.addView(cb[i]);
            if(editText!=null)
            {
                ll_Working.addView(editText);
                editText.setVisibility(View.GONE);
            }
            cb[i].setOnCheckedChangeListener(this);
            i = i + 1;
        }

    }

    public void onCheckedChanged(CompoundButton cb, boolean isChecked){
        String checkedText = cb.getText()+"";
        String checkedID = cb.getTag()+"";

        if(Integer.parseInt(checkedID.trim())==6)
        {
            et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
            et_otherPleaseSpecify.setVisibility(View.VISIBLE);
        }
        else
        {
            et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
            et_otherPleaseSpecify.setVisibility(View.GONE);
        }

        if(isChecked)
        {

            // for unchecked all the Radio Button in NoT Working
            int i=0;
            for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet())
            {
                RadioButton rb = (RadioButton)ll_NoWorking.getChildAt(i);
                rb.setChecked(false);
                i = i + 1;
            }

            if(Integer.parseInt(checkedID.trim())==6)
            {
                et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.VISIBLE);
            }
            else
            {
                et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.GONE);
            }
            if(Integer.parseInt(checkedID.trim())==6)
            {
                if(!TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim()))
                {
                    hmapSelectedCheckBoxData.put(checkedID.trim(),et_otherPleaseSpecify.getText().toString().trim());
                }
                else
                {
                    hmapSelectedCheckBoxData.put(checkedID.trim(),"NA");
                }

            }
            else
            {
                hmapSelectedCheckBoxData.put(checkedID.trim(),checkedText.trim());
            }

            CommonInfo.DayStartClick=1;

        }
        else
        {
            if(Integer.parseInt(checkedID.trim())==6)
            {
                et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
                et_otherPleaseSpecify.setVisibility(View.GONE);
                et_otherPleaseSpecify.setText("");
            }

            hmapSelectedCheckBoxData.remove(checkedID.trim());
            if(hmapSelectedCheckBoxData.size()==0)
            {
                CommonInfo.DayStartClick=0;
            }

        }



    }

    private void createRadioButtonForNotWorking()
    {
        rb = new RadioButton[hmapReasonIdAndDescrForNotWorking_details.size()];
        int i = 0;
        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet())
        {
            rb[i] = new RadioButton(this);
            rb[i].setText(entry.getValue());
            rb[i].setTag(entry.getKey().toString().trim());
            rb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked)
                {
                    String checkedText = cb.getText()+"";
                    String checkedID = cb.getTag()+"";
                    if(isChecked)
                    {
                        ReasonText = hmapReasonIdAndDescrForNotWorking_details.get(Integer.parseInt(checkedID.trim()));
                        ReasonId = "" + checkedID;


                        // for checked Selected the Radio Button in NoT Working
                        int i=0;
                        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForNotWorking_details.entrySet())
                        {
                            RadioButton rb = (RadioButton)ll_NoWorking.getChildAt(i);
                            if(rb.getTag().toString().trim().equals(checkedID.trim()))
                            {
                                rb.setChecked(true);
                            }
                            else
                            {
                                rb.setChecked(false);
                            }

                            i = i + 1;
                        }

                        // for unchecked all the CheckBox in Today Working
                        i=0;
                        for (Map.Entry<Integer, String> entry : hmapReasonIdAndDescrForWorking_details.entrySet())
                        {
                            cb = (CheckBox) ll_Working.getChildAt(i);
                            cb.setChecked(false);
                            i = i + 1;
                        }
                        hmapSelectedCheckBoxData.clear();
                        hmapSelectedCheckBoxData.clear();

                        CommonInfo.DayStartClick=2;
                    }
                    else
                    {

                    }
                }
            });
            ll_NoWorking.addView(rb[i]);
            i = i + 1;
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if(!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0"))
        {
            googleMap.clear();
            try {
                googleMap.setMyLocationEnabled(false);
            }
            catch(SecurityException e)
            {

            }
            if(AccuracyFromLauncher.equals("0.0") || AccuracyFromLauncher.equals("NA")|| AccuracyFromLauncher.equals("")|| AccuracyFromLauncher==null){

            }
            else{

                if(Double.parseDouble(AccuracyFromLauncher)>100){
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Marker locationMarker=googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                  /*  if(!fetchedFromDb.equals("0") && fetchedFromDb!=null){
                        distanceText.setVisibility(View.VISIBLE);
                    }*/

                }
                else{
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker locationMarker=googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                    // distanceText.setVisibility(View.GONE);
                }

            }




        }
        else
        {

            if(refreshCount==2)
            {
                txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                btn_refresh.setVisibility(View.GONE);
            }
            try
            {
                googleMap.setMyLocationEnabled(false);
            }
            catch(SecurityException e)
            {

            }
            // googleMap.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    //  marker.setTitle(StoreName);
                }
            });

        }

    }

    public void fnGetDistributorList()
    {

        //mDataSource.open();
        Distribtr_list= mDataSource.getSuplierData();
        hmapDistLatLng= mDataSource.getDistLatLng();
        //String distDefault=mDataSource.getDfltDstbtr();
        //mDataSource.close();
        for(int i=0;i<Distribtr_list.length;i++)
        {
            //System.out.println("DISTRIBUTOR........"+Distribtr_list[i]);
            String value=Distribtr_list[i];
            DbrNodeId=value.split(Pattern.quote("^"))[0];
            DbrNodeType=value.split(Pattern.quote("^"))[1];
            DbrName=value.split(Pattern.quote("^"))[2];
            DbrArray.add(DbrName);
        }

        adapterDBR= new ArrayAdapter<String>(DayStartActivity.this,R.layout.initial_spinner_text,DbrArray);
        adapterDBR.setDropDownViewResource(R.layout.spina);

        spinner_for_filter.setAdapter(adapterDBR);





        spinner_for_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id)
            {

                TextView tv =(TextView) view;
                String text=tv.getText().toString();

                if(text.equals("Select Distributor"))
                {
                    DistributorName_Global="Select Distributor";
                    DistributorId_Global="0";
                    DistributorNodeType_Global="0";
                    distanceCalc=0.0;
                }
                else
                {

                    DistributorName_Global=tv.getText().toString();
                    String   Distribtor_Detail= mDataSource.fetchSuplierIdByName(text);
                    DistributorId_Global=Distribtor_Detail.split(Pattern.quote("^"))[0];
                    DistributorNodeType_Global=Distribtor_Detail.split(Pattern.quote("^"))[1];
                    String distLatLng=hmapDistLatLng.get(Distribtor_Detail);
                    Location mylocation = new Location("");
                    Location dest_location = new Location("");
                    String lat =distLatLng.split(Pattern.quote("^"))[0];
                    String lon =distLatLng.split(Pattern.quote("^"))[1];
                    dest_location.setLatitude(Double.parseDouble(lat));
                    dest_location.setLongitude(Double.parseDouble(lon));

                    if((!LattitudeFromLauncher.equals("NA")) && (!LongitudeFromLauncher.equals("NA")))
                    {
                        mylocation.setLatitude(Double.parseDouble(LattitudeFromLauncher));
                        mylocation.setLongitude(Double.parseDouble(LongitudeFromLauncher));
                        float distance =mylocation.distanceTo(dest_location);//in meters
                        distanceCalc=distance;
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daystart);
        ctx=this;

            imei= AppUtils.getIMEI(ctx);

        Intent intent=getIntent();
        intentFrom= intent.getIntExtra("IntentFrom", 0);
        sPrefAttandance=getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);
        customHeader();
        btn_refresh= (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setVisibility(View.GONE);
        ll_refresh= (LinearLayout) findViewById(R.id.ll_refresh);
        ll_refresh.setVisibility(View.GONE);
        txt_rfrshCmnt= (TextView) findViewById(R.id.txt_rfrshCmnt);
        rsnLatetext= (EditText) findViewById(R.id.rsnLatetext);
        rsnLatetext.setVisibility(View.GONE);
        rb_yes= (RadioButton) findViewById(R.id.rb_yes);
        rb_no=(RadioButton)findViewById(R.id.rb_no);

        rg_yes_no= (RadioGroup) findViewById(R.id.rg_yes_no);
        rg_yes_no.setVisibility(View.GONE);

        rg_yes_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i!=-1)
                {

                    RadioButton    radioButtonVal = (RadioButton) radioGroup.findViewById(i);
                    if(radioButtonVal.getId()==R.id.rb_yes)
                    {
                        ll_refresh.setVisibility(View.GONE);

                    }
                    else if(radioButtonVal.getId()==R.id.rb_no)
                    {
                        ll_refresh.setVisibility(View.VISIBLE);


                    }
                }

            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(!isGPSok)
                {
//                    isGPSok = false;
                    showSettingsAlert();                }
                if(!isNWok)
                {
                    isNWok = false;
                }
                if(!isGPSok && !isNWok)
                {
                    try
                    {
                        showSettingsAlert();
                    }
                    catch(Exception e)
                    {

                    }

                    isGPSok = false;
                    isNWok=false;
                }
                else
                {
                    rg_yes_no.clearCheck();
                    ll_refresh.setVisibility(View.GONE);
                    refreshCount++;
                    if(refreshCount==1)
                    {
                        txt_rfrshCmnt.setText(getString(R.string.second_msg_for_map));
                    }
                    else if(refreshCount==2)
                    {
                        txt_rfrshCmnt.setText(getString(R.string.third_msg_for_map));
                        btn_refresh.setVisibility(View.GONE);
                    }

                    LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
                    llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this,false, true, 20, 0);

                }


            }
        });



        try
        {
            getDataFromDatabase();
        }
        catch(Exception e)
        {

        }
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        Date date1=new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(DayStartActivity.this,TimeUtils.DATE_FORMAT);
        txt_DBR_Name=(TextView) findViewById(R.id.txt_DBR_Name);
        ll_start=(LinearLayout)findViewById(R.id.ll_start);
        ll_start.setVisibility(View.VISIBLE);
        ll_startAfterDayEndFirst=(LinearLayout)findViewById(R.id.ll_startAfterDayEndFirst);
        ll_startAfterDayEndFirst.setVisibility(View.GONE);
        ll_startAfterDayEndSecond=(LinearLayout)findViewById(R.id.ll_startAfterDayEndSecond);
        ll_startAfterDayEndSecond.setVisibility(View.GONE);
        ll_map=(LinearLayout)findViewById(R.id.ll_map);
        ll_map.setVisibility(View.GONE);


        ll_DBR_Name= (LinearLayout) findViewById(R.id.ll_DBR_Name);
        ll_DBR_Name.setVisibility(View.GONE);
        et_DBR_Name= (EditText) findViewById(R.id.et_DBR_Name);
        ll_Working=(LinearLayout)findViewById(R.id.ll_Working);
        ll_Working.setVisibility(View.GONE);
        ll_NoWorking=(LinearLayout)findViewById(R.id.ll_NoWorking);
        ll_NoWorking.setVisibility(View.GONE);



        ll_Working_parent=(LinearLayout)findViewById(R.id.ll_Working_parent);
        ll_Working_parent.setVisibility(View.GONE);

        ll_NoWorking_parent=(LinearLayout)findViewById(R.id.ll_NoWorking_parent);
        ll_NoWorking_parent.setVisibility(View.GONE);

        tv_MapLocationCorrectText=(TextView) findViewById(R.id.tv_MapLocationCorrectText);
        tv_MapLocationCorrectText.setVisibility(View.GONE);



        rb_workingYes=(RadioButton)findViewById(R.id.rb_workingYes);
        rb_workingYes.setVisibility(View.GONE);
        rb_workingNo=(RadioButton)findViewById(R.id.rb_workingNo);
        rb_workingNo.setVisibility(View.GONE);
        ll_OverAllDistributionCheckin=(LinearLayout) findViewById(R.id.ll_OverAllDistributionCheckin);
        spinner_location=(Spinner)findViewById(R.id.spinner_location);
        adapterLocation = new ArrayAdapter<String>(DayStartActivity.this, android.R.layout.simple_spinner_item,listLocationWrkng);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(adapterLocation);
        // spinner_location.setVisibility(View.GONE);
        spinner_for_filter=(Spinner)findViewById(R.id.spinner_for_filter);

        spnr_late=(Spinner)findViewById(R.id.spnr_late);
        if( CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn")==0) {
            ll_OverAllDistributionCheckin.setVisibility(View.GONE);

        }
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spinner_location.getSelectedItem().toString().equals("Select Working Location"))
                {

                    ll_map.setVisibility(View.VISIBLE);
                    ll_Working_parent.setVisibility(View.VISIBLE);
                    ll_NoWorking_parent.setVisibility(View.GONE);
                    ll_map.setVisibility(View.VISIBLE);
                    ll_comment.setVisibility(View.VISIBLE);
                    if(isLateApplicable)
                    {
                        ll_reason.setVisibility(View.VISIBLE);
                    }

                    but_Next.setVisibility(View.VISIBLE);

                    rg_yes_no.setVisibility(View.VISIBLE);

                    btn_refresh.setVisibility(View.VISIBLE);
                    tv_MapLocationCorrectText.setVisibility(View.VISIBLE);

                    if(spinner_location.getSelectedItem().toString().equals("At Warehouse Location"))
                    {
                        spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                        spinner_for_filter.setVisibility(View.VISIBLE);
                        ll_DBR_Name.setVisibility(View.VISIBLE);
                        txt_DBR_Name.setText(getString(R.string.DistbtrName));
                        et_DBR_Name.setText( DbrArray.get(position));
                        CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag("7");
                        if(checkBox!=null)
                        {
                            checkBox.setChecked(true);
                        }

                    }
                    else  if(spinner_location.getSelectedItem().toString().equals("Other Location"))
                    {
                        spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                        spinner_for_filter.setVisibility(View.GONE);
                        ll_DBR_Name.setVisibility(View.VISIBLE);
                        txt_DBR_Name.setText(getString(R.string.other_loc));
                        CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag("7");
                        if(checkBox!=null)
                        {
                            checkBox.setChecked(false);
                        }
                    }
                    else
                    {
                        //DbrArray
                        if(DbrArray.size()>1)
                        {
                            spinner_for_filter.setSelection(adapterDBR.getPosition(DbrArray.get(1)));
                            DistributorName_Global=DbrArray.get(1);
                            String   Distribtor_Detail= mDataSource.fetchSuplierIdByName(DistributorName_Global);
                            DistributorId_Global=Distribtor_Detail.split(Pattern.quote("^"))[0];
                            DistributorNodeType_Global=Distribtor_Detail.split(Pattern.quote("^"))[1];
                        }

                        spinner_for_filter.setVisibility(View.VISIBLE);
                        ll_DBR_Name.setVisibility(View.GONE);
                        CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag("7");
                        if(checkBox!=null)
                        {
                            checkBox.setChecked(false);
                        }
                    }
                }
                else
                {
                    spinner_for_filter.setSelection(adapterDBR.getPosition("Select Distributor"));
                    spinner_for_filter.setVisibility(View.GONE);
                    ll_map.setVisibility(View.GONE);
                    ll_Working_parent.setVisibility(View.GONE);

                    ll_map.setVisibility(View.GONE);
                    ll_reason.setVisibility(View.GONE);
                    btn_refresh.setVisibility(View.GONE);
                    ll_refresh.setVisibility(View.GONE);
                    rg_yes_no.setVisibility(View.GONE);
                    tv_MapLocationCorrectText.setVisibility(View.GONE);

                    ll_DBR_Name.setVisibility(View.GONE);

                    CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag("7");
                    if(checkBox!=null)
                    {
                        checkBox.setChecked(false);
                    }

                    but_Next.setVisibility(View.GONE);

                    DistributorName_Global="Select Distributor";
                    DistributorId_Global="0";
                    DistributorNodeType_Global="0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnr_late.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if((!spnr_late.getSelectedItem().toString().equals("Select Reason")) && (!spnr_late.getSelectedItem().toString().equals("No Reason")))
                {
                    if(listTxtBxToShow.contains(hmapReasonIdAndDescr_details.get(spnr_late.getSelectedItem().toString())))
                    {
                        rsnLatetext.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        rsnLatetext.setVisibility(View.GONE);
                    }
                    otherReasonForLate=spnr_late.getSelectedItem().toString();
                    reasonId=hmapReasonIdAndDescr_details.get(spnr_late.getSelectedItem().toString());
                }
                else
                {
                    reasonId="0";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_reason=(LinearLayout) findViewById(R.id.ll_reason);
        ll_reason.setVisibility(View.GONE);


        ll_comment=(LinearLayout) findViewById(R.id.ll_comment);
        ll_comment.setVisibility(View.GONE);

        but_Next=(Button) findViewById(R.id.but_Next);
        but_Next.setVisibility(View.GONE);

        if(CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn")==1)
        {
            fnGetDistributorList();
        }

        getReasonDetail();


        Button but_DayStart=(Button) findViewById(R.id.but_DayStart);
        but_DayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(!isGPSok)
                {
                    isGPSok = false;
                }
                if(!isNWok)
                {
                    isNWok = false;
                }
                if(!isGPSok && !isNWok)
                {
                    try
                    {
                        showSettingsAlert();
                    }
                    catch(Exception e)
                    {

                    }

                    isGPSok = false;
                    isNWok=false;
                }
                else
                {

                    ll_Working.setVisibility(View.VISIBLE);
                    ll_NoWorking.setVisibility(View.VISIBLE);
                    createCheckBoxForWorking();
                    createRadioButtonForNotWorking();
                    txt_DayStarttime=(TextView)findViewById(R.id.txt_DayStarttime);
                    txt_DayStarttime.setText(getDateAndTimeInSecond());

                    rb_workingYes.setVisibility(View.VISIBLE);
                    rb_workingNo.setVisibility(View.VISIBLE);

                    manager= getFragmentManager();
                    mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                    mapFrag.getView().setVisibility(View.VISIBLE);
                    // mapFrag.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
                    // mapFrag.set

                    mapFrag.getMapAsync(DayStartActivity.this);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.show(mapFrag);

                    ll_start=(LinearLayout)findViewById(R.id.ll_start);
                    ll_start.setVisibility(View.GONE);
                    ll_startAfterDayEndFirst=(LinearLayout)findViewById(R.id.ll_startAfterDayEndFirst);
                    ll_startAfterDayEndFirst.setVisibility(View.VISIBLE);
                    ll_startAfterDayEndSecond=(LinearLayout)findViewById(R.id.ll_startAfterDayEndSecond);
                    ll_startAfterDayEndSecond.setVisibility(View.VISIBLE);



                    LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
                    llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this,true,true,20,1);

                }



            }
        });
        Button but_Exit=(Button) findViewById(R.id.but_Exit);
        but_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finishAffinity();
            }
        });


        but_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                otherReasonForLate="NA";
                int flg100MeterValidation=0;
                if(CommonInfo.hmapAppMasterFlags.containsKey("flg100MeterValidation"))
                {
                    if(CommonInfo.hmapAppMasterFlags.get("flg100MeterValidation")==1)
                    {
                        flg100MeterValidation=1;
                    }
                }

                int flgCheckForLateReason=0;
                if(CommonInfo.hmapAppMasterFlags.containsKey("flgCheckForLateReason"))
                {
                    if(CommonInfo.hmapAppMasterFlags.get("flgCheckForLateReason")==1)
                    {
                        flgCheckForLateReason=1;
                    }
                }

                if(CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn")==1)
                {
                    if(rb_workingYes.isChecked()) {
                        if (parseDateTime(crntServerTime).after(crntDateTime(crntAttndncTime)) && flgCheckForLateReason==1) {
                            if (reasonId.equals("0")) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                alertDialog.setMessage(getResources().getString(R.string.selectReasonForLate));
                                alertDialog.setIcon(R.drawable.error);
                                alertDialog.setCancelable(false);

                                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                                return;

                            } else if (spnr_late.getSelectedItem().toString().equals("Others.")) {

                                if (TextUtils.isEmpty(rsnLatetext.getText().toString())) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                    alertDialog.setMessage(getResources().getString(R.string.selectOtherReasonForLate));
                                    alertDialog.setIcon(R.drawable.error);
                                    alertDialog.setCancelable(false);

                                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                    return;

                                } else {
                                    otherReasonForLate = rsnLatetext.getText().toString();
                                }
                            } else {
                                otherReasonForLate = spnr_late.getSelectedItem().toString();
                            }
                        }
                    }
                    if((!spinner_location.getSelectedItem().toString().equals("At Warehouse Location")) && (!spinner_location.getSelectedItem().toString().equals("Other Location")) )

                    {
                        if(rb_workingYes.isChecked() && DistributorName_Global.equals("Select Distributor"))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage(getResources().getString(R.string.selectDistributorProceeds));
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            return;
                        }
                        else if((distanceCalc>100.00) && (refreshCount<2) && (hmapSelectedCheckBoxData.containsKey(reasonIdForMrktVisit)) && flg100MeterValidation==1)
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                            alertDialog.setMessage(getResources().getString(R.string.txtLocation));
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            return;
                        }

                        //  else if((refreshCount==2) || (rg_yes_no.getCheckedRadioButtonId()==R.id.rb_yes))
                        if((rb_workingYes!=null && rb_workingYes.isChecked()==true) || (rb_workingNo!=null && rb_workingNo.isChecked()==true))
                        {
                            if((refreshCount==2) || (rg_yes_no.getCheckedRadioButtonId()==R.id.rb_yes) && ((rb_workingYes!=null && rb_workingYes.isChecked()==true)))
                            {

                                if (CommonInfo.DayStartClick==0)
                                {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                    alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOption));
                                    alertDialog.setIcon(R.drawable.error);
                                    alertDialog.setCancelable(false);

                                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog,int which)
                                        {
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                    return;
                                }
                                else if(hmapSelectedCheckBoxData.containsKey("6") && TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim()))
                                {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                    alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                    alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOptionwithedittext));
                                    alertDialog.setIcon(R.drawable.error);
                                    alertDialog.setCancelable(false);

                                    alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog,int which)
                                        {
                                            dialog.dismiss();


                                        }
                                    });
                                    alertDialog.show();
                                    return;
                                }
                                else
                                {
                                    if(hmapSelectedCheckBoxData.containsKey("6"))
                                    {
                                        hmapSelectedCheckBoxData.remove("6");

                                        hmapSelectedCheckBoxData.put("6",et_otherPleaseSpecify.getText().toString().trim());
                                    }

                                    if(hmapSelectedCheckBoxData.size()>0)
                                    {
                                        ReasonId="";
                                        ReasonText="";
                                        for(Map.Entry<String, String> entry:hmapSelectedCheckBoxData.entrySet())
                                        {
                                            String key = entry.getKey().toString().trim();
                                            String value = entry.getValue().toString().trim();

                                            if(ReasonId.equals(""))
                                            {
                                                ReasonId= key;
                                            }
                                            else
                                            {
                                                ReasonId=ReasonId+"$"+key;
                                            }

                                            if(ReasonText.equals(""))
                                            {
                                                ReasonText= value;
                                            }
                                            else
                                            {
                                                ReasonText=ReasonText+"$"+value;
                                            }
                                        }



                                    }


                                    String commentValue="NA";
                                    EditText commenttext=(EditText)findViewById(R.id.commenttext);
                                    if(!TextUtils.isEmpty(commenttext.getText().toString().trim()))
                                    {
                                        commentValue=commenttext.getText().toString().trim();
                                    }
                                    else
                                    {

                                    }


                                    if(DistributorName_Global.equals("Select Distributor"))
                                    {
                                        DistributorName_Global="NA";
                                        DistributorId_Global="0";
                                        DistributorNodeType_Global="0";
                                    }

                                    String flgGSTCapture="NA",flgGSTCompliance="NA",GSTNumber="NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                                    mDataSource.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText,commentValue,DistributorId_Global,DistributorNodeType_Global,DistributorName_Global);
                                    syncStartAfterSavindData();
                                }
                            }
                            else if((rg_yes_no.getCheckedRadioButtonId()==R.id.rb_no) && ((rb_workingYes!=null && rb_workingYes.isChecked()==true)))
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                                alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                                alertDialog.setCancelable(false);

                                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int which)
                                    {
                                        dialog.dismiss();


                                    }
                                });
                                alertDialog.show();

                            }
                            else if( ((rb_workingNo!=null && rb_workingNo.isChecked()==true)))
                            {
                                if(hmapSelectedCheckBoxData.containsKey("6"))
                                {
                                    hmapSelectedCheckBoxData.remove("6");

                                    hmapSelectedCheckBoxData.put("6",et_otherPleaseSpecify.getText().toString().trim());
                                }

                                if(hmapSelectedCheckBoxData.size()>0)
                                {
                                    ReasonId="";
                                    ReasonText="";
                                    for(Map.Entry<String, String> entry:hmapSelectedCheckBoxData.entrySet())
                                    {
                                        String key = entry.getKey().toString().trim();
                                        String value = entry.getValue().toString().trim();

                                        if(ReasonId.equals(""))
                                        {
                                            ReasonId= key;
                                        }
                                        else
                                        {
                                            ReasonId=ReasonId+"$"+key;
                                        }

                                        if(ReasonText.equals(""))
                                        {
                                            ReasonText= value;
                                        }
                                        else
                                        {
                                            ReasonText=ReasonText+"$"+value;
                                        }
                                    }



                                }


                                String commentValue="NA";
                                EditText commenttext=(EditText)findViewById(R.id.commenttext);
                                if(!TextUtils.isEmpty(commenttext.getText().toString().trim()))
                                {
                                    commentValue=commenttext.getText().toString().trim();
                                }
                                else
                                {

                                }


                                if(DistributorName_Global.equals("Select Distributor"))
                                {
                                    DistributorName_Global="NA";
                                    DistributorId_Global="0";
                                    DistributorNodeType_Global="0";
                                }

                                String flgGSTCapture="NA",flgGSTCompliance="NA",GSTNumber="NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                                mDataSource.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText,commentValue,DistributorId_Global,DistributorNodeType_Global,DistributorName_Global);
                                syncStartAfterSavindData();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                            alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();


                                }
                            });
                            alertDialog.show();
                        }

                    }


                }
                else
                {
                    if((rb_workingYes!=null && rb_workingYes.isChecked()==true) || (rb_workingNo!=null && rb_workingNo.isChecked()==true))
                    {
                        if((refreshCount==2) || (rg_yes_no.getCheckedRadioButtonId()==R.id.rb_yes) && ((rb_workingYes!=null && rb_workingYes.isChecked()==true)))
                        {

                            if (CommonInfo.DayStartClick==0)
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOption));
                                alertDialog.setIcon(R.drawable.error);
                                alertDialog.setCancelable(false);

                                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int which)
                                    {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                                return;
                            }
                            else if(hmapSelectedCheckBoxData.containsKey("6") && TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim()))
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg));
                                alertDialog.setMessage(getResources().getString(R.string.selectAtleastOneOptionwithedittext));
                                alertDialog.setIcon(R.drawable.error);
                                alertDialog.setCancelable(false);

                                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int which)
                                    {
                                        dialog.dismiss();


                                    }
                                });
                                alertDialog.show();
                                return;
                            }
                            else
                            {
                                if(hmapSelectedCheckBoxData.containsKey("6"))
                                {
                                    hmapSelectedCheckBoxData.remove("6");

                                    hmapSelectedCheckBoxData.put("6",et_otherPleaseSpecify.getText().toString().trim());
                                }

                                if(hmapSelectedCheckBoxData.size()>0)
                                {
                                    ReasonId="";
                                    ReasonText="";
                                    for(Map.Entry<String, String> entry:hmapSelectedCheckBoxData.entrySet())
                                    {
                                        String key = entry.getKey().toString().trim();
                                        String value = entry.getValue().toString().trim();

                                        if(ReasonId.equals(""))
                                        {
                                            ReasonId= key;
                                        }
                                        else
                                        {
                                            ReasonId=ReasonId+"$"+key;
                                        }

                                        if(ReasonText.equals(""))
                                        {
                                            ReasonText= value;
                                        }
                                        else
                                        {
                                            ReasonText=ReasonText+"$"+value;
                                        }
                                    }



                                }


                                String commentValue="NA";
                                EditText commenttext=(EditText)findViewById(R.id.commenttext);
                                if(!TextUtils.isEmpty(commenttext.getText().toString().trim()))
                                {
                                    commentValue=commenttext.getText().toString().trim();
                                }
                                else
                                {

                                }


                                if(DistributorName_Global.equals("Select Distributor"))
                                {
                                    DistributorName_Global="NA";
                                    DistributorId_Global="0";
                                    DistributorNodeType_Global="0";
                                }

                                String flgGSTCapture="NA",flgGSTCompliance="NA",GSTNumber="NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                                mDataSource.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText,commentValue,DistributorId_Global,DistributorNodeType_Global,DistributorName_Global);
                                syncStartAfterSavindData();
                            }
                        }
                        else if((rg_yes_no.getCheckedRadioButtonId()==R.id.rb_no) && ((rb_workingYes!=null && rb_workingYes.isChecked()==true)))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                            alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();


                                }
                            });
                            alertDialog.show();

                        }
                        else if( ((rb_workingNo!=null && rb_workingNo.isChecked()==true)))
                        {
                            if(hmapSelectedCheckBoxData.containsKey("6"))
                            {
                                hmapSelectedCheckBoxData.remove("6");

                                hmapSelectedCheckBoxData.put("6",et_otherPleaseSpecify.getText().toString().trim());
                            }

                            if(hmapSelectedCheckBoxData.size()>0)
                            {
                                ReasonId="";
                                ReasonText="";
                                for(Map.Entry<String, String> entry:hmapSelectedCheckBoxData.entrySet())
                                {
                                    String key = entry.getKey().toString().trim();
                                    String value = entry.getValue().toString().trim();

                                    if(ReasonId.equals(""))
                                    {
                                        ReasonId= key;
                                    }
                                    else
                                    {
                                        ReasonId=ReasonId+"$"+key;
                                    }

                                    if(ReasonText.equals(""))
                                    {
                                        ReasonText= value;
                                    }
                                    else
                                    {
                                        ReasonText=ReasonText+"$"+value;
                                    }
                                }



                            }


                            String commentValue="NA";
                            EditText commenttext=(EditText)findViewById(R.id.commenttext);
                            if(!TextUtils.isEmpty(commenttext.getText().toString().trim()))
                            {
                                commentValue=commenttext.getText().toString().trim();
                            }
                            else
                            {

                            }


                            if(DistributorName_Global.equals("Select Distributor"))
                            {
                                DistributorName_Global="NA";
                                DistributorId_Global="0";
                                DistributorNodeType_Global="0";
                            }

                            String flgGSTCapture="NA",flgGSTCompliance="NA",GSTNumber="NA";
                           /* mDataSource.savetblSuplierMappingData(DistributorId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                                    GSTNumber,FusedAddress,finalPinCode,finalCity,finalState,LattitudeFromLauncher,LongitudeFromLauncher,
                                    AccuracyFromLauncher,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);
*/

                            mDataSource.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText,commentValue,DistributorId_Global,DistributorNodeType_Global,DistributorName_Global);
                            syncStartAfterSavindData();
                        }
                    }
                    else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                        alertDialog.setMessage(getResources().getString(R.string.verifyLocation));

                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int which)
                            {
                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();
                    }

                }



            }
        });

        rb_workingYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                int flg100MeterValidation=0;

                if(CommonInfo.hmapAppMasterFlags.containsKey("flg100MeterValidation"))
                {
                    if(CommonInfo.hmapAppMasterFlags.get("flg100MeterValidation")==1)
                    {
                        flg100MeterValidation=1;
                    }
                }

                int flgCheckForLateReason=0;
                if(CommonInfo.hmapAppMasterFlags.containsKey("flgCheckForLateReason"))
                {
                    if(CommonInfo.hmapAppMasterFlags.get("flgCheckForLateReason")==1)
                    {
                        flgCheckForLateReason=1;
                    }
                }
                if(rb_workingYes.isChecked())
                {

                    rb_workingNo.setChecked(false);
                    if( CommonInfo.hmapAppMasterFlags.get("flgDistributorCheckIn")==0)
                    {
                        ll_OverAllDistributionCheckin.setVisibility(View.GONE);
                    }
                    else {
                        ll_OverAllDistributionCheckin.setVisibility(View.VISIBLE);
                    }
                    if(isLateApplicable && flgCheckForLateReason==1)
                    {
                        ll_reason.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        ll_reason.setVisibility(View.GONE);
                    }

                    ll_map.setVisibility(View.VISIBLE);
                    ll_Working_parent.setVisibility(View.VISIBLE);
                    ll_NoWorking_parent.setVisibility(View.GONE);
                    ll_map.setVisibility(View.VISIBLE);
                    ll_comment.setVisibility(View.VISIBLE);
                    but_Next.setVisibility(View.VISIBLE);

                    rg_yes_no.setVisibility(View.VISIBLE);

                    btn_refresh.setVisibility(View.VISIBLE);
                    tv_MapLocationCorrectText.setVisibility(View.VISIBLE);
                   /* String distDefault=mDataSource.getDfltDstbtr();
                    if(DbrArray.size()>1 && DbrArray.size()<3) {
                        spinner_for_filter.setSelection(1);
                        spinner_for_filter.setEnabled(false);
                    }
                    else
                    {
                        if(!TextUtils.isEmpty(distDefault))
                        {
                            spinner_for_filter.setSelection(adapterDBR.getPosition(distDefault));
                        }
                    }*/
                }
            }
        });

        rb_workingNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_workingNo.isChecked())
                {
                    for(int i=0;i<ll_Working.getChildCount();i++)
                    {
                        View view=ll_Working.getChildAt(i);
                        if(view instanceof CheckBox)
                        {
                            CheckBox chckBox= (CheckBox) view;
                            if(chckBox.isChecked())
                            {
                                chckBox.setChecked(false);
                            }
                        }
                        else if(view instanceof EditText)
                        {
                            EditText editText= (EditText) view;
                            if(editText!=null)
                            {
                                editText.setText("");
                            }
                        }
                    }
                    rb_workingYes.setChecked(false);
                    ll_OverAllDistributionCheckin.setVisibility(View.GONE);
                    ll_map.setVisibility(View.GONE);
                    ll_Working_parent.setVisibility(View.GONE);
                    ll_NoWorking_parent.setVisibility(View.VISIBLE);
                    ll_map.setVisibility(View.GONE);
                    ll_reason.setVisibility(View.GONE);
                    btn_refresh.setVisibility(View.GONE);
                    ll_refresh.setVisibility(View.GONE);
                    rg_yes_no.setVisibility(View.GONE);

                    tv_MapLocationCorrectText.setVisibility(View.GONE);
                    spinner_location.setSelection(adapterLocation.getPosition("Select Working Location"));
                    // spinner_location.setVisibility(View.GONE);

                    ll_comment.setVisibility(View.VISIBLE);

                    but_Next.setText(getText(R.string.txtSubmit));
                    but_Next.setVisibility(View.VISIBLE);

                    DistributorName_Global="Select Distributor";
                    DistributorId_Global="0";
                    DistributorNodeType_Global="0";


                }
            }
        });


    }

    public void syncStartAfterSavindData()
    {
        SharedPreferences.Editor editor=sPrefAttandance.edit();
        editor.clear();
        editor.commit();
        sPrefAttandance.edit().putString("AttandancePref", fDate).commit();
        flgDaySartWorking=1;
        try {

            new bgTasker().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //System.out.println(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        boolean isGPSok = false;
        boolean isNWok=false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSok)
        {
            isGPSok = false;
        }
        if(!isNWok)
        {
            isNWok = false;
        }
        if(!isGPSok && !isNWok)
        {
            try
            {
                showSettingsAlert();
            }
            catch(Exception e)
            {

            }

            isGPSok = false;
            isNWok=false;
        }
        else
        {
            if (isOnline())
            {
                //   new CurrentDateTime().execute();
                CommonFunction.getGetServerTime(DayStartActivity.this, CommonInfo.imei);
            }
            else
            {
                showNoConnAlert(false);
            }
            // LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
            // llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this);
        }
    }


    public boolean checkLastFinalLoctionIsRepeated(String currentLat,String currentLong,String currentAccuracy){
        boolean repeatedLoction=false;

        try {

            String chekLastGPSLat="0";
            String chekLastGPSLong="0";
            String chekLastGpsAccuracy="0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer=new StringBuffer();
                String myjson_stampiGPSLastLocation="";
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;

                try {
                    br = new BufferedReader(new FileReader(file));

                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myjson_stampiGPSLastLocation=sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat=jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong=jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy=jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if(currentLat!=null )
                {
                    if(currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy))
                    {
                        repeatedLoction=true;
                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }
    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
    {

        try {

            JSONArray jArray=new JSONArray();
            JSONObject jsonObjMain=new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put( "chekLastGPSLat",chekLastGPSLat);
            jOnew.put( "chekLastGPSLong",chekLastGPSLong);
            jOnew.put( "chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("FinalGPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{

        }
    }
    public void showSettingsAlert()
    {
        AppUtils.doCheckGPSEnable(DayStartActivity.this);
    }



    public void showNoConnAlert(final boolean isSyncdCalled)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DayStartActivity.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        if(!isSyncdCalled)
                        {
                            finish();
                        }

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    public String displayAlertDialog()
    {
        String str="cb_NoWorking Selected;";
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_nostore, null);
        final EditText et_Reason = (EditText) alertLayout.findViewById(R.id.et_Reason);
        et_Reason.setVisibility(View.INVISIBLE);

        final Spinner spinner_reason=(Spinner) alertLayout.findViewById(R.id.spinner_reason);

        ArrayAdapter adapterCategory=new ArrayAdapter(DayStartActivity.this, android.R.layout.simple_spinner_item,reasonNames);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reason.setAdapter(adapterCategory);

        spinner_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                // TODO Auto-generated method stub
                String	spinnerReasonSelected = spinner_reason.getSelectedItem().toString();
                ReasonText=spinnerReasonSelected;
                int check= mDataSource.fetchFlgToShowTextBox(spinnerReasonSelected);
                ReasonId= mDataSource.fetchReasonIdBasedOnReasonDescr(spinnerReasonSelected);
                if(check==0)
                {
                    et_Reason.setVisibility(View.INVISIBLE);
                }
                else
                {
                    et_Reason.setVisibility(View.VISIBLE);
                }


                //ReasonId,ReasonText
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.AlertDialogHeaderMsg);
        alert.setView(alertLayout);
        //alert.setIcon(R.drawable.info_ico);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                if (ReasonText.equals("")||ReasonText.equals("Select Reason"))
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please select the reason first.");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,int which)
                        {
                            dialog.dismiss();
                            displayAlertDialog();

                        }
                    });
                    alertDialog.show();
                }
                else
                {


                   /* Date pdaDate=new Date();
                    SimpleDateFormat sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String CurDate = sdfPDaDate.format(pdaDate).toString().trim();

                    if(et_Reason.isShown())
                    {

                        if(TextUtils.isEmpty(et_Reason.getText().toString().trim()))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please enter the reason.");
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();
                                    displayAlertDialog();

                                }
                            });
                            alertDialog.show();
                        }
                        else
                        {
                            ReasonText = et_Reason.getText().toString();
                            if(isOnline())
                            {
                               GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                                task.execute();
                            }
                            else
                            {
                                mDataSource.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                                mDataSource.updateCurDatetblNoVisitStoreDetails(fDate);
                                mDataSource.updateSstattblNoVisitStoreDetails(3);


                                String aab=mDataSource.fetchReasonDescr();

                                showNoConnAlert();

                            }



                        }
                    }
                    else
                    {
                        if(isOnline())
                        {
                           GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                            task.execute();
                        }
                        else
                        {
                            mDataSource.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                            mDataSource.updateCurDatetblNoVisitStoreDetails(fDate);
                            mDataSource.updateSstattblNoVisitStoreDetails(3);
                            showNoConnAlert();

                        }


                    }*/


                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        return str;
    }

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        //System.out.println("SHIVA"+fnLati+","+fnLongi+","+finalAccuracy+","+fnAccurateProvider+","+GpsLat+","+GpsLong+","+GpsAccuracy+","+NetwLat+","+NetwLong+","+NetwAccuracy+","+FusedLat+","+FusedLong+","+FusedAccuracy+","+AllProvidersLocation+","+GpsAddress+","+NetwAddress+","+FusedAddress+","+FusedLocationLatitudeWithFirstAttempt+","+FusedLocationLongitudeWithFirstAttempt+","+FusedLocationAccuracyWithFirstAttempt+","+fnLongi+","+flgLocationServicesOnOff+","+flgGPSOnOff+","+flgNetworkOnOff+","+flgFusedOnOff+","+flgInternetOnOffWhileLocationTracking+","+address+","+pincode+","+city+","+state);

        if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy)))
        {
            LattitudeFromLauncher=String.valueOf(fnLati);
            LongitudeFromLauncher=String.valueOf(fnLongi);
            AccuracyFromLauncher=String.valueOf(finalAccuracy);

            this.FnlAddress=FusedAddress;
            this.finalPinCode=pincode;
            this.finalCity=city;
            this.finalState=state;
            this.fnAccurateProvider=fnAccurateProvider;
            this.AllProvidersLocation=AllProvidersLocation;
            this.FusedAddress=FusedAddress;
            this.GpsLat=GpsLat;
            this.GpsLong=GpsLong;
            this.GpsAccuracy=GpsAccuracy;
            this.GpsAddress=GpsAddress;
            this.NetwLat=NetwLat;
            this.NetwLong=NetwLong;
            this.NetwAccuracy=NetwAccuracy ;
            this.NetwAddress=NetwAddress;
            this.FusedLat=FusedLat;
            this.FusedLong=FusedLong;
            this.FusedAccuracy=FusedAccuracy;
            this.FusedLocationLatitudeWithFirstAttempt=FusedLocationLatitudeWithFirstAttempt ;
            this.FusedLocationLongitudeWithFirstAttempt=FusedLocationLongitudeWithFirstAttempt;
            this.FusedLocationAccuracyWithFirstAttempt=FusedLocationAccuracyWithFirstAttempt;
            this.flgLocationServicesOnOff=flgLocationServicesOnOff ;
            this.flgGPSOnOff=flgGPSOnOff;
            this.flgNetworkOnOff=flgNetworkOnOff;
            this.flgFusedOnOff=flgFusedOnOff;
            this.flgInternetOnOffWhileLocationTracking=flgInternetOnOffWhileLocationTracking;


            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy));
            manager= getFragmentManager();
            mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
            mapFrag.getView().setVisibility(View.VISIBLE);
            mapFrag.getMapAsync(DayStartActivity.this);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.show(mapFrag);
            //mDataSource.open();
            mDataSource.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(),PersonNodeID,PersonNodeType,PersonName,OptionID,OptionDesc,
                    ReasonId,ReasonText,FusedAddress,finalPinCode,finalCity,finalState,fnLati,fnLongi,
                    finalAccuracy,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,
                    flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState);

            //mDataSource.close();






            mDataSource.saveTblLocationDetails(fnLati, fnLongi, finalAccuracy, address, city, pincode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);



        }
        else
        {
            countSubmitClicked++;
            if(countSubmitClicked==1)
            {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DayStartActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage(getText(R.string.AlertSameLoc));

                // On pressing Settings button
                alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                if(!isFinishing() && !isDestroyed())
                    alertDialog.show();



            }
            else
            {

                LattitudeFromLauncher=String.valueOf(fnLati);
                LongitudeFromLauncher=String.valueOf(fnLongi);
                AccuracyFromLauncher=String.valueOf(finalAccuracy);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DayStartActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);

                //mDataSource.open();
                mDataSource.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(),PersonNodeID,PersonNodeType,PersonName,OptionID,OptionDesc,
                        ReasonId,ReasonText,FusedAddress,finalPinCode,finalCity,finalState,fnLati,fnLongi,
                        finalAccuracy,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                        GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                        NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                        FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                        FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                        FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,
                        flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState);




                //mDataSource.close();
                mDataSource.deleteLocationTable();
                mDataSource.saveTblLocationDetails(fnLati, fnLongi, finalAccuracy, address, city, pincode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);

                //mDataSource.close();




            }


        }
    }


    private class bgTasker extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {

            try {

                SyncNow();



            } catch (Exception e) {
            }
            finally {}

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(DayStartActivity.this,getText(R.string.PleaseWaitMsg),getText(R.string.genTermProcessingRequest), true);
            pDialog2.setIndeterminate(true);
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected void onCancelled() {
            Log.i("bgTasker", "bgTasker Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("bgTasker", "bgTasker Execution cycle completed");
            pDialog2.dismiss();


            if ((CommonInfo.hmapAppMasterFlags.get("flgTargetShowatStart") == 1)) {
                Intent i = new Intent(DayStartActivity.this, SalesValueTarget.class);
                i.putExtra("IntentFrom", 0);
                startActivity(i);
                finish();
            }
               /* else if ((CommonInfo.hmapAppMasterFlags.get("flgIncentiveShowtStart") == 1)) {
                    Intent i=new Intent(DayStartActivity.this,IncentiveActivity.class);
                    i.putExtra("IntentFrom", 0);
                    startActivity(i);
                    finish();
                }*/
                       /* else if ((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1)) {
                                Intent intent = new Intent(DayStartActivity.this, DistributorCheckInSecondActivity.class);
                                intent.putExtra("imei", imei);
                                intent.putExtra("fDate", fDate);
                                intent.putExtra("From", "DayStart");
                                startActivity(intent);
                                finish();
                        }
                        else if ((CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1)) {
                                Intent intent = new Intent(DayStartActivity.this, WarehouseCheckInSecondActivity.class);
                                intent.putExtra("imei", imei);
                                intent.putExtra("fDate", fDate);
                                intent.putExtra("From", "DayStart");
                                startActivity(intent);
                                finish();
                        } else {*/
            if(rb_workingNo.isChecked())
            {
                AlertDialog .Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                alertDialog.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg));
                alertDialog.setMessage("Today not working marked successfully.");

                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int which)
                    {
                        SharedPreferences.Editor editor=sPrefAttandance.edit();
                        editor.clear();
                        editor.commit();

                        finishAffinity();
                        dialog.dismiss();


                    }
                });
                alertDialog.show();
            }

            else {
                Intent intent = new Intent(DayStartActivity.this, AllButtonActivity.class);
                startActivity(intent);
                finish();
            }
            // }



               /* Intent intent=new Intent(DayStartActivity.this,DSR_Registration.class);
                intent.putExtra("IntentFrom", "SPLASH");
                startActivity(intent);
                finish();*/

                /*Intent syncIntent = new Intent(DayStartActivity.this, SyncMaster.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", "DayStart");
                startActivity(syncIntent);
                finish();*/
            // whatTask = 0;

        }
    }

    public void SyncNow()
    {




        try
        {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists())
            {
                OrderXMLFolder.mkdirs();
            }




            if(isOnline()) {

                Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
                mMyServiceIntent.putExtra("whereTo", "DayStart");//
                mMyServiceIntent.putExtra("routeID", "0");//
                SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);


//                Intent mMyServiceIntent = new Intent(getCtx(), MyService.class);
//                mMyServiceIntent.putExtra("whereTo", "DayStart");//
//                mMyServiceIntent.putExtra("routeID", "0");//
//
//                if (!isMyServiceRunning(MyService.class)) {
//                    startService(mMyServiceIntent);
//                }

            }
            else
            {
                showNoConnAlert(true);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    private void getReasonDetail()
    {



        hmapReasonIdAndDescr_details= mDataSource.fetch_Reason_Late();

        int index=0;
        if(hmapReasonIdAndDescr_details!=null)
        {
            reasonLate=new String[hmapReasonIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<>(hmapReasonIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                reasonLate[index]=me2.getKey().toString();
                index=index+1;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DayStartActivity.this, android.R.layout.simple_spinner_item,reasonLate);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnr_late.setAdapter(adapter);
        }


    }
    @Override
    public void success() {
//CommonInfo.crntServerTimecrntAttndncTime
        if((!TextUtils.isEmpty(CommonInfo.crntServerTimecrntAttndncTime)) && (CommonInfo.crntServerTimecrntAttndncTime!=null))
        {

            if(CommonInfo.crntServerTimecrntAttndncTime.contains("^"))
            {
                crntServerTime= CommonInfo.crntServerTimecrntAttndncTime.split(Pattern.quote("^"))[0];
                crntAttndncTime= CommonInfo.crntServerTimecrntAttndncTime.split(Pattern.quote("^"))[1];
            }
            else
            {
                crntServerTime= CommonInfo.crntServerTimecrntAttndncTime;
            }

            if(parseDateTime(crntServerTime).after(crntDateTime(crntAttndncTime)) )
            {
                isLateApplicable=false;
                /*if(rb_workingYes.isChecked())
                {
                    ll_reason.setVisibility(View.VISIBLE);
                }*/

                //Toast.makeText(DayStartActivity.this,"Time between 9:30 and 10:30",Toast.LENGTH_SHORT).show();
            }

            else
            {
                isLateApplicable=false;
                //Toast.makeText(DayStartActivity.this,"Time before 9:30",Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void failure() {
        showAlertSingleButtonError("Error while fetching data.");
    }

    public Date parseDateTime(String time) {
        String inputPattern = "dd-MMM-yyyy hh:mm:ss aa";
        String outputPattern = "hh:mm aa";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

        Date date,dateFnl = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
            dateFnl=outputFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Date fnldate = formatter.parse(dateInString);;
        return dateFnl;
    }

    public Date crntDateTime(String time) {
        String inputPattern = "hh:mm aa";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);


        Date date = null;


        try {
            date = inputFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Date fnldate = formatter.parse(dateInString);;
        return date;
    }

    public String getDateAndTimeAMPM()
    {
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH);
        String curTime = TimeUtils.getNetworkDateTime(DayStartActivity.this,TimeUtils.DATE_TIME_FORMAT);
        return curTime;
    }
    public EditText getEditText(String tagVal)
    {
        EditText editText=new EditText(DayStartActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);

        params.leftMargin = 50;
        params.rightMargin = 50;
        editText.setBackground(getResources().getDrawable(R.drawable.et_boundary_retrun));
        editText.setLayoutParams(params);
        editText.setTag(tagVal);
        editText.setEms(10);
        return editText;
    }
    public boolean validateEditText()
    {
        boolean isEdiTextFilled=false;
        if(spinner_location.getSelectedItem().toString().equals("At Warehouse Location"))
        {
            if(TextUtils.isEmpty(et_DBR_Name.getText().toString().trim()))
            {
                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg),getString(R.string.specifyDBRName));
                isEdiTextFilled=true;
                return isEdiTextFilled;
            }
            else
            {
                CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag("7");
                if(checkBox!=null)
                {

                    hmapSelectedCheckBoxData.put("7",et_DBR_Name.getText().toString().trim());
                }

            }

        }
        else  if(spinner_location.getSelectedItem().toString().equals("Other Location"))
        {
            if(TextUtils.isEmpty(et_DBR_Name.getText().toString().trim()))
            {
                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg),getString(R.string.specifyLocatione));
                isEdiTextFilled=true;
                return isEdiTextFilled;
            }
            else
            {
                hmapSelectedCheckBoxData.put("15","Other Location"+"^"+et_DBR_Name.getText().toString().trim());
            }
        }
        if(listTxtBxToShow!=null)
        {
            for(int i=0;i<listTxtBxToShow.size();i++)
            {
                CheckBox checkBox= (CheckBox) ll_Working.findViewWithTag(listTxtBxToShow.get(i));
                if(checkBox!=null)
                {
                    if(checkBox.isChecked())
                    {
                        EditText editText= (EditText) ll_Working.findViewWithTag(listTxtBxToShow.get(i)+"_ed");
                        if(editText!=null)
                        {
                            if(TextUtils.isEmpty(editText.getText().toString().trim()))
                            {
                                isEdiTextFilled=true;
                                alertForValidation(getString(R.string.AlertDialogHeaderErrorMsg),getString(R.string.selectAtleastOneOptionwithedittext));
                                break;
                            }
                            else
                            {
                                hmapSelectedCheckBoxData.put(listTxtBxToShow.get(i),editText.getText().toString().trim());
                            }
                        }

                    }

                }

            }
        }
        return isEdiTextFilled;
    }

    public void alertForValidation(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.dismiss();


            }
        });
        alertDialog.show();
    }
}
