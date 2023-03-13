package com.astix.allanasosfa.reports;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.BaseActivity;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.utils.IntentConstants;
import com.astix.allanasosfa.utils.PreferenceManager;
import com.astix.sancussosfa.BaseActivity;
import com.astix.sancussosfa.R;
import com.astix.sancussosfa.database.AppDataSource;
import com.astix.sancussosfa.utils.AppUtils;
import com.astix.sancussosfa.utils.IntentConstants;
import com.astix.sancussosfa.utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportsActivity extends BaseActivity {


    @BindView(R.id.content_layout)
    FrameLayout mFragmentContainer;
    Toolbar toolbar;

    public static final String YESTERDAY_REPORT="yesterdayreports";
    public static final String DAY_PLAN_REPORT="dayplanreport";

    public LocationManager locationManager;

    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    public String AccuracyFromLauncher="NA";
    String FnlAddress="NA",finalPinCode="NA",finalCity="NA",finalState="NA",fnAccurateProvider="NA",AllProvidersLocation="NA",FusedAddress="NA",GpsLat="NA",GpsLong="NA"
            ,GpsAccuracy="NA",GpsAddress="NA",NetwLat="NA",NetwLong="NA",NetwAccuracy="NA" ,NetwAddress="NA",FusedLat="NA",FusedLong="NA",FusedAccuracy="NA"
            ,FusedLocationLatitudeWithFirstAttempt="NA" ,FusedLocationLongitudeWithFirstAttempt="NA",FusedLocationAccuracyWithFirstAttempt="NA";
    int flgLocationServicesOnOff=0,flgGPSOnOff=0,flgNetworkOnOff=0,flgFusedOnOff=0,flgInternetOnOffWhileLocationTracking=0,flgRestart=0;

    String cityID="NA";
    String StateID="NA";
    String MapAddress="NA";
    String MapPincode="NA";
    String MapCity="NA";
    String MapState="NA";

    String PersonNodeID="NA";
    String PersonNodeType="NA";
    String PersonName="NA";
    String OptionID="NA";
    String OptionDesc="NA";

    private AppDataSource mDataSource;
    int countSubmitClicked=0;
    private PreferenceManager mPreferenceManager;
    private int flgShowNextButton=0;
   @Override
   public void onBackPressed()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("MTD Summary");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {           // OnClick Handling on Tool Navigation Button
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().getBackStackEntryCount()>1)
                    getSupportFragmentManager().popBackStackImmediate();
                else
                    finish();
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

                }
            }
        });

        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        mDataSource=new AppDataSource(this);
        mPreferenceManager=PreferenceManager.getInstance(this);

        PersonNodeID=""+mPreferenceManager.getIntValue(AppUtils.PERSON_NODE_ID,0);
        PersonNodeType=""+mPreferenceManager.getIntValue(AppUtils.PERSON_NODE_TYPE,0);
        PersonName=mPreferenceManager.getStringValue(AppUtils.PERSON_NAME,"");
        Bundle args=new Bundle();
        args.putInt(IntentConstants.flgShowNextButton,flgShowNextButton);
        loadFragment(args,YESTERDAY_REPORT);
    }

    public void loadFragment(Bundle args, String tag) {
        Fragment fragment = null;
        if (tag == null)
            return;
        switch (tag) {
            case YESTERDAY_REPORT:
                fragment= YesterdayReportFragment.newInstance(args);
                break;
        }

        if (fragment != null)
            addFragment(fragment, tag);
    }



    private void addFragment(Fragment fragment, String tag) {
        mFragmentContainer.setVisibility(View.VISIBLE);

        if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_layout, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_layout, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
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

}
