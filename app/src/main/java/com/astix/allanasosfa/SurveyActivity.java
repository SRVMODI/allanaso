package com.astix.allanasosfa;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.astix.allanasosfa.R;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.sync.SyncJobService;
import com.allanasosfa.truetime.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by User on 13-Jul-18.
 */

public class SurveyActivity extends BaseActivity implements LocationInterface {
    public String newfullFileName;
    TextView tv_Quest1,tv_quest2,tv_quest3,tv_quest4;
    String StoreID;
    ImageView backIcon;
    Button btn_submit;
    public String userDate;
    public String pickerDate;
    public String imei;
    EditText et_Comment;
    RadioButton rb_quest1_yes,rb_quest1_no,rb_quest2_yes,rb_quest2_no;
    LinkedHashMap<String,String> hmapQuestion=new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> hmapOption=new LinkedHashMap<String,String>();
    LinearLayout ll_parentofAll,ll_parentOfCheckBox;
    public LocationManager locationManager;
    Context ctx;
    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_activity);
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        getDataFromDataBase();
        getIntentData();
        buttonInitialization();
        textviewInitialization();
        radiobuttonInitialization();
        edittextInitialization();
        linearLayoutInitialization();
        setDataToLayoutFromDatabase();
    }
    public void setDataToLayoutFromDatabase(){
        if(hmapQuestion!=null && (!hmapQuestion.isEmpty())){
            int i=0;
        for(Map.Entry<String, String> entry:hmapQuestion.entrySet())
        {
            String questID= entry.getKey().trim();
            String questData= entry.getValue().trim();
            String questText=   questData.split(Pattern.quote("^"))[0];
            String flgActive=   questData.split(Pattern.quote("^"))[1];
            if(i==0){
                tv_Quest1.setText(questText);
                tv_Quest1.setTag(questID);
            }
            if(i==1){
                tv_quest2.setText(questText);
                tv_quest2.setTag(questID);
            }
            if(i==2){
                tv_quest3.setText(questText);
                tv_quest3.setTag(questID);
            }
            if(i==3){
                tv_quest4.setText(questText);
                tv_quest4.setTag(questID);
            }

            hmapOption= mDataSource.fngettblOptionSurvey(questID);
            int j=0;
            for(Map.Entry<String, String> entry2:hmapOption.entrySet())
            {
                String optionID= entry2.getKey().trim();
                String optionData= entry2.getValue().trim();
                String optionText=   optionData.split(Pattern.quote("^"))[0];
                String flgActiveOption=   optionData.split(Pattern.quote("^"))[1];

                if(i==0){
                    if(j==0){
                        rb_quest1_yes.setText(optionText);
                        rb_quest1_yes.setTag(optionID);
                    }
                    if(j==1){
                        rb_quest1_no.setText(optionText);
                        rb_quest1_no.setTag(optionID);
                    }

                }
                if(i==1){
                    if(j==0){
                        rb_quest2_yes.setText(optionText);
                        rb_quest2_yes.setTag(optionID);
                    }
                    if(j==1){
                        rb_quest2_no.setText(optionText);
                        rb_quest2_no.setTag(optionID);
                    }
                }
                if(i==2){
                    View dynamic_container=getLayoutInflater().inflate(R.layout.checkbox_row,null);
                    final CheckBox checkBox= (CheckBox) dynamic_container.findViewById(R.id.cb_options);
                    checkBox.setTag(optionID);
                    checkBox.setText(optionText);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkBox.isChecked()){
                                if(checkBox.getTag().toString().equals("11")){
                                    for(int i=0;i<ll_parentOfCheckBox.getChildCount();i++){
                                        LinearLayout linearLayout=(LinearLayout)    ll_parentOfCheckBox.getChildAt(i);
                                        CheckBox checkBoxChild=(CheckBox)    linearLayout.getChildAt(0);
                                        if(checkBoxChild.isChecked()){

                                            checkBoxChild.setChecked(false);

                                        }
                                    }
                                }
                                else {
                                    try {
                                        CheckBox checkBoxChild= (CheckBox)  ll_parentOfCheckBox.findViewWithTag("11");
                                        if(checkBoxChild!=null){
                                            checkBoxChild.setChecked(false);
                                        }
                                    }
                                    catch (Exception e){}

                                }
                                checkBox.setChecked(true);
                            }


                            else{
                                checkBox.setChecked(false);
                            }
                        }
                    });
                    ll_parentOfCheckBox.addView(dynamic_container);
                }
                if(i==3){

                }
                j++;
            }
            i++;

        }

        }
        else{
            ll_parentofAll.setVisibility(View.GONE);

        }

    }

    public void linearLayoutInitialization(){
        ll_parentofAll=(LinearLayout) findViewById(R.id.ll_parentofAll);
        ll_parentOfCheckBox=(LinearLayout) findViewById(R.id.ll_parentOfCheckBox);
    }
    public  void edittextInitialization(){
        et_Comment=(EditText) findViewById(R.id.et_Comment);
    }
    public void radiobuttonInitialization(){
        rb_quest1_yes=(RadioButton) findViewById(R.id.rb_quest1_yes);
        rb_quest1_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_quest1_yes.isChecked()){
                    rb_quest1_no.setChecked(false);
                }
            }
        });
        rb_quest1_no=(RadioButton) findViewById(R.id.rb_quest1_no);
        rb_quest1_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_quest1_no.isChecked()){
                    rb_quest1_yes.setChecked(false);
                }
            }
        });
        rb_quest2_yes=(RadioButton) findViewById(R.id.rb_quest2_yes);
        rb_quest2_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_quest2_yes.isChecked()){
                    rb_quest2_no.setChecked(false);
                }
            }
        });
        rb_quest2_no=(RadioButton) findViewById(R.id.rb_quest2_no);
        rb_quest2_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_quest2_no.isChecked()){
                    rb_quest2_yes.setChecked(false);
                }
            }
        });

    }
    public void textviewInitialization(){
        tv_Quest1=(TextView) findViewById(R.id.tv_quest1);
        tv_quest2=(TextView) findViewById(R.id.tv_quest2);
        tv_quest3=(TextView) findViewById(R.id.tv_quest3);
        tv_quest4=(TextView) findViewById(R.id.tv_quest4);
    }
    public void getIntentData(){
        Intent intent=getIntent();
         StoreID= intent.getStringExtra("StoreID");
        String StoreName=   intent.getStringExtra("StoreName");
        imei = intent.getStringExtra("imei").trim();
        userDate = intent.getStringExtra("userDate");
        pickerDate = intent.getStringExtra("pickerDate").trim();

    }
    public void getDataFromDataBase(){
        hmapQuestion= mDataSource.fngettblQuestionsSurvey();
        //hmapOption= mDataSource.fngettblOptionSurvey();
    }
    public void buttonInitialization(){

        btn_submit=(Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    if(isOnline()){
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
                        else {
                            LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
                            llaaa.locationRetrievingAndDistanceCalculating(SurveyActivity.this,false,false,20,1);


                        }
                        }
                    else{
                        showNoConnAlert();
                    }

                }

            }
        });
        backIcon=(ImageView) findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent     intent=new Intent(SurveyActivity.this,SurveyStoreList.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", pickerDate);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean validate(){
        if((!rb_quest1_yes.isChecked()) && (!rb_quest1_no.isChecked())){
            showAlertSingleButtonError("Please select option for question: "+ tv_Quest1.getText());
            return false;
        }
       else if((!rb_quest2_yes.isChecked()) && (!rb_quest2_no.isChecked())){
            showAlertSingleButtonError("Please select option for question: "+ tv_quest2.getText());
            return false;
        }
     else   if(!checkCheckboxSelected()){
            showAlertSingleButtonError("Please select an option for question: "+ tv_quest3.getText());
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkCheckboxSelected(){
        boolean flag=false;
        for(int i=0;i<ll_parentOfCheckBox.getChildCount();i++){
        LinearLayout linearLayout=(LinearLayout)    ll_parentOfCheckBox.getChildAt(i);
            CheckBox checkBox=(CheckBox)    linearLayout.getChildAt(0);
            if(checkBox.isChecked()){
                flag=true;
                break;


            }
        }
        return  flag;
    }
    public void showAlertSingleButtonError(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }
    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SurveyActivity.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        // finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

   
    public void submitAllFunctionality(String fnLati, String fnLongi, String finalAccuracy){


            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
           // SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            String currentDateTime = TimeUtils.getNetworkDateTime(SurveyActivity.this,TimeUtils.DATE_TIME_FORMAT);
            mDataSource.deletetblSurveyData(StoreID);
            String QstnID= tv_Quest1.getTag().toString();
            String OptionID="0";
            String OptionText="0";
            if(rb_quest1_yes.isChecked()){
                OptionID=rb_quest1_yes.getTag().toString();
                OptionText=rb_quest1_yes.getText().toString();
            }
            else {
                OptionID=rb_quest1_no.getTag().toString();
                OptionText=rb_quest1_no.getText().toString();
            }
            mDataSource.fnsavetblSurveyData(StoreID,QstnID,OptionID,OptionText,currentDateTime,3,fnLati,fnLongi,finalAccuracy);

            QstnID= tv_quest2.getTag().toString();
            if(rb_quest2_yes.isChecked()){
                OptionID=rb_quest2_yes.getTag().toString();
                OptionText=rb_quest2_yes.getText().toString();
            }
            else {
                OptionID=rb_quest2_no.getTag().toString();
                OptionText=rb_quest2_no.getText().toString();
            }
            mDataSource.fnsavetblSurveyData(StoreID,QstnID,OptionID,OptionText,currentDateTime,3,fnLati,fnLongi,finalAccuracy);

            QstnID= tv_quest3.getTag().toString();
            OptionID="0";
            OptionText="0";
            for(int i=0;i<ll_parentOfCheckBox.getChildCount();i++){

                LinearLayout linearLayout=(LinearLayout)    ll_parentOfCheckBox.getChildAt(i);
                CheckBox checkBox=(CheckBox)    linearLayout.getChildAt(0);
                if(checkBox.isChecked()){
                    if(i==0){
                        OptionID=checkBox.getTag().toString();
                        OptionText=checkBox.getText().toString();
                    }
                    else{
                        if(OptionID.equals("0") && OptionText.equals("0")){
                            OptionID=checkBox.getTag().toString();
                            OptionText=checkBox.getText().toString();
                        }
                        else{
                            OptionID=OptionID+"^"+checkBox.getTag().toString();
                            OptionText=OptionText+"^"+checkBox.getText().toString();
                        }


                    }



                }
            }
            mDataSource.fnsavetblSurveyData(StoreID,QstnID,OptionID,OptionText,currentDateTime,3,fnLati,fnLongi,finalAccuracy);
            QstnID= tv_quest4.getTag().toString();
            OptionID="0";
            OptionText="";
            if(!et_Comment.getText().toString().trim().equals("")){
                OptionText= et_Comment.getText().toString().trim();
            }
            mDataSource.fnsavetblSurveyData(StoreID,QstnID,OptionID,OptionText,currentDateTime,3,fnLati,fnLongi,finalAccuracy);


//            if(isOnline()){
               /* Intent syncIntent = new Intent(SurveyActivity.this, SyncMaster.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", "SurveyActivity");
                syncIntent.putExtra("imei", imei);
                syncIntent.putExtra("userDate", userDate);
                syncIntent.putExtra("pickerDate", pickerDate);
                startActivity(syncIntent);
                finish();*/
        Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);
        mMyServiceIntent.putExtra("whereTo", "DayStart");//
        mMyServiceIntent.putExtra("routeID", "0");//
        SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);

                Intent storeIntent =  new Intent(SurveyActivity.this, SurveyStoreList.class);
                storeIntent.putExtra("PageFrom", "0");
                storeIntent.putExtra("FROM", "SURVEY");
                storeIntent.putExtra("imei", imei);
                startActivity(storeIntent);
//            }
//            else{
//                Intent storeIntent =  new Intent(SurveyActivity.this, SurveyStoreList.class);
//                storeIntent.putExtra("PageFrom", "0");
//                storeIntent.putExtra("FROM", "SURVEY");
//                storeIntent.putExtra("imei", imei);
//                startActivity(storeIntent);
//            }

        }

    public void showSettingsAlert()
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(getText(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
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

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        submitAllFunctionality(fnLati,fnLongi,finalAccuracy);
    }
}
