package com.astix.allanasosfa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.allanasosfa.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

//import com.astix.sfatju.R;

public class SurveyStoreList extends BaseActivity implements com.google.android.gms.location.LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
{
    ImageView backIcon;
     EditText inputSearch;
       ListView   listDistributor;
     ArrayAdapter<String>     adapterDistributor;
    LinkedHashMap<String, String> hmapdsrIdAndDescr_details=new LinkedHashMap<String, String>();
    String[] drsNames;
    SharedPreferences sharedPref;
    int slctdCoverageAreaNodeID=0,slctdCoverageAreaNodeType=0,slctdDSrSalesmanNodeId=0,slctdDSrSalesmanNodeType=0;
    public String	SelectedDSRValue="";
    public String currSysDate;
    public int chkFlgForErrorToCloseApp=0;
    Spinner spinner_manager;
    Spinner spinner_RouteList;

    String[] Manager_names=null;
    String[] Route_names=null;
    //String[] Manager_names= { "Select Market Location", "sec-20", "sec-24", "other"};
    static String selected_manager="NA";
    static String seleted_routeIDType="0";
    RelativeLayout rl_for_other;
    EditText ed_Street;
    static int Selected_manager_Id=0;



    HashMap<String, String> hmapManagerNameManagerIdDetails=new HashMap<String, String>();
    LinkedHashMap<String, String> hmapStoreNameID=new LinkedHashMap<String, String>();
    HashMap<String, String> hmapRouteIdNameDetails=new HashMap<String, String>();



    boolean serviceException=false;

    public static final String DATASUBDIRECTORYForText = CommonInfo.TextFileFolder;

    public String passDate;
    public SimpleDateFormat sdf;
    public String fDate;
    public String userDate;

    public String pickerDate;
    public String imei;
    public String[] storeList;
    public String[] storeRouteIdType;
    Dialog dialog;

    CheckBox check1, check2;
    public TableLayout tl2;
    RelativeLayout relativeLayout1;
    int battLevel=0;

    public static HashMap<String, String> hmapStoreIdSstat=new HashMap<String, String>();
    public static HashMap<String, String> hmapStoreIdForDate=new HashMap<String, String>();
    public static HashMap<String, String> hmapStoreIdflgOrderType=new HashMap<String, String>();
    public int flgDayEndOrChangeRoutenew=0;
    LinkedHashMap<String, String> hmapOutletListForNear=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNearUpdated=new LinkedHashMap<String, String>();

    LinkedHashMap<String, String> hmapTestSubmitOrNot=new LinkedHashMap<String, String>();

    static int flgChangeRouteOrDayEnd = 0;

    ProgressDialog pDialogGetStores;
    ProgressDialog mProgressDialog;
    public String Noti_text="Null";
    public int MsgServerID=0;

    public boolean[] checks;
    ServiceWorker newservice = new ServiceWorker();
    static ScheduledExecutorService scheduler;
    public static ScheduledExecutorService schPHSTATS;

    public int noLOCflag = 0;
    boolean bool = true;
    public ProgressDialog pDialogSync;

    ImageView img_side_popUp;
    int closeList = 0;
    int whatTask = 0;
    String whereTo = "11";

    ArrayList mSelectedItems = new ArrayList();

    int prevSel = 0;
    int prevID;
    public long syncTIMESTAMP;
    public String fullFileName1;

    public String[] storeCode;
    public String[] storeName;
    ArrayList<String> stIDs;
    ArrayList<String> stNames;

    public String[] storeStatus;

    public String[] StoreflgSubmitFromQuotation;


    public String[] storeCloseStatus;

    public String[] storeNextDayStatus;
    public ListView listView;
    public ProgressDialog pDialog2STANDBY;


    public TableRow tr;
    public String selStoreID = "";
    public String selStoreName = "";
    public String prevSelStoreID;

    public Double myCurrentLon; // removed "static"
    public Double myCurrentLat;
    public Double finalLatNow;
    public Double finalLonNow;

    public int gotLoc = 0;
    public int locStat = 0;
    ProgressDialog pDialog2;
    String FWDCLname;

    String BCKCLname;
    public Location firstLoc;
    public float acc;


    public Location location2;
    public String[] StoreList2Procs;
    public Location finalLocation;
    standBYtask task_STANDBY = new standBYtask();


    private final Context mContext = this;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second

    // Declaring a Location Manager
    protected LocationManager locationManager;
    public int valDayEndOrChangeRoute=0; // 1=Clicked on Day End Button, 2=Clicked on Change Route Button


    public String[] route_name;
    public String[] route_name_id;
    public String selected_route_id="0";

    private int selected = 0;
    public String temp_select_routename="NA";
    public String temp_select_routeid="NA";
    public String rID;
    public   PowerManager pm;
    public	 PowerManager.WakeLock wl;
    public Location location;
    public String AllProvidersLocation="";
    public String FusedLocationLatitudeWithFirstAttempt="0";
    public String FusedLocationLongitudeWithFirstAttempt="0";
    public String FusedLocationAccuracyWithFirstAttempt="0";
    public String FusedLocationLatitude="0";
    public String FusedLocationLongitude="0";
    public String FusedLocationProvider="";
    public String FusedLocationAccuracy="0";

    public String GPSLocationLatitude="0";
    public String GPSLocationLongitude="0";
    public String GPSLocationProvider="";
    public String GPSLocationAccuracy="0";

    public String NetworkLocationLatitude="0";
    public String NetworkLocationLongitude="0";
    public String NetworkLocationProvider="";
    public String NetworkLocationAccuracy="0";
    public CoundownClass countDownTimer;
    private final long startTime = 15000;
    private final long interval = 200;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    String fusedData;
    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;
    ShivAdapter adapterCusto;
    ArrayList<String> listItem;
    ArrayList<String> listStoreId;
    LocationRequest mLocationRequest;
    LinkedHashMap<String,String> hmapStoreLatLongDistanceFlgRemap=new LinkedHashMap<String,String>();
    SharedPreferences sharedPrefForSurvey;
    private void getDSRDetail() throws IOException
    {



        hmapdsrIdAndDescr_details= mDataSource.fetch_DSRCoverage_List();

        int index=0;
        if(hmapdsrIdAndDescr_details!=null)
        {
            drsNames=new String[hmapdsrIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapdsrIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                drsNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }





    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };
    public void locationRetrievingAndDistanceCalculating()
    {

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();


        pDialog2STANDBY = ProgressDialog.show(SurveyStoreList.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.searchingnearbystores), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(SurveyStoreList.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(SurveyStoreList.this)
                    .addOnConnectionFailedListener(SurveyStoreList.this)
                    .build();
            mGoogleApiClient.connect();
        }
        //startService(new Intent(DynamicActivity.this, AppLocationService.class));


       countDownTimer = new CoundownClass(startTime, interval);
        countDownTimer.start();

    }
    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
    protected void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }
    protected void startLocationUpdates()
    {
        try
        {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
        catch (SecurityException e)
        {

        }

    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    private void updateUI() {
        Location loc =mCurrentLocation;
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());

            FusedLocationLatitude=lat;
            FusedLocationLongitude=lng;
            FusedLocationProvider=mCurrentLocation.getProvider();
            FusedLocationAccuracy=""+mCurrentLocation.getAccuracy();
            fusedData="At Time: " + mLastUpdateTime  +
                    "Latitude: " + lat  +
                    "Longitude: " + lng  +
                    "Accuracy: " + mCurrentLocation.getAccuracy() +
                    "Provider: " + mCurrentLocation.getProvider();

        } else {

        }
    }

    protected void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);




    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location args0) {
        mCurrentLocation = args0;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    public class standBYtask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

    }



    // *****SYNC******




    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);

        alertDialog.setPositiveButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }


    public void onDestroy()
    {
        super.onDestroy();
        // unregister receiver
        this.unregisterReceiver(this.mBatInfoReceiver);

        //this.unregisterReceiver(this.KillME);
    }


public void backButtonFuntion(){

    backIcon=(ImageView) findViewById(R.id.backIcon);
    backIcon.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent     intent=null;
            if(sharedPrefForSurvey.contains("FROM")){
                if( sharedPrefForSurvey.getString("FROM", "").equals("DASHBOARD")){
                         intent=new Intent(SurveyStoreList.this,AllButtonActivity.class);
                }
                else if( sharedPrefForSurvey.getString("FROM", "").equals("STORESELECTION")){
                        intent=new Intent(SurveyStoreList.this,StoreSelection.class);
                    intent.putExtra("imei", imei);
                    intent.putExtra("userDate", userDate);
                    intent.putExtra("pickerDate", pickerDate);
                    intent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                }
                else {
                    intent=new Intent(SurveyStoreList.this,AllButtonActivity.class);
                }

            }
            else{
                intent=new Intent(SurveyStoreList.this,AllButtonActivity.class);
            }

            startActivity(intent);
            finish();
        }
    });
}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_storelist);
        hmapTestSubmitOrNot= mDataSource.fngetSubmittedSurvey();
        sharedPrefForSurvey=getSharedPreferences("SurveyPref", MODE_PRIVATE);

        inputSearch           =	 (EditText) findViewById(R.id.inputSearch);
        listDistributor      = (ListView) findViewById(R.id.list_view);
        backButtonFuntion();


        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        if(sharedPref.contains("CoverageAreaNodeID"))
        {
            if(sharedPref.getInt("CoverageAreaNodeID",0)!=0)
            {
                CommonInfo.CoverageAreaNodeID=sharedPref.getInt("CoverageAreaNodeID",0);
                CommonInfo.CoverageAreaNodeType=sharedPref.getInt("CoverageAreaNodeType",0);
            }
        }
        if(sharedPref.contains("SalesmanNodeId"))
        {
            if(sharedPref.getInt("SalesmanNodeId",0)!=0)
            {
                CommonInfo.SalesmanNodeId=sharedPref.getInt("SalesmanNodeId",0);
                CommonInfo.SalesmanNodeType=sharedPref.getInt("SalesmanNodeType",0);
            }
        }
        if(sharedPref.contains("flgDataScope"))
        {
            if(sharedPref.getInt("flgDataScope",0)!=0)
            {
                CommonInfo.flgDataScope=sharedPref.getInt("flgDataScope",0);

            }
        }
        if(sharedPref.contains("flgDSRSO"))
        {
            if(sharedPref.getInt("flgDSRSO",0)!=0)
            {
                CommonInfo.FlgDSRSO=sharedPref.getInt("flgDSRSO",0);

            }
        }

        tl2 = (TableLayout) findViewById(R.id.dynprodtable);
        Intent getStorei = getIntent();
        if(getStorei !=null)
        {
            imei = getStorei.getStringExtra("imei").trim();
            userDate = getStorei.getStringExtra("userDate");
            pickerDate = getStorei.getStringExtra("pickerDate").trim();

        }

        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        relativeLayout1=(RelativeLayout) findViewById(R.id.relativeLayout1);

        

        rID=  mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
        if(rID.equals("0"))
        {
            rID= mDataSource.GetNotActiveRouteID();
        }
        mDataSource.updateActiveRoute(rID, 1);
        
        mProgressDialog = new ProgressDialog(SurveyStoreList.this);
        mProgressDialog.setTitle(getResources().getString(R.string.genTermPleaseWaitNew));
        mProgressDialog.setMessage(getResources().getString(R.string.txtRefreshingData));

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);


        try {
            getDSRDetail();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Date date1=new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        passDate = TimeUtils.getNetworkDateTime(SurveyStoreList.this,TimeUtils.DATE_FORMAT);



        fDate = passDate.trim().toString();


        getManagersDetail();
        getRouteDetail();

        spinner_manager = (Spinner)findViewById(R.id.spinner_manager);
        ArrayAdapter adapterCategory=new ArrayAdapter(this, android.R.layout.simple_spinner_item,Manager_names);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_manager.setAdapter(adapterCategory);

        spinner_RouteList = (Spinner)findViewById(R.id.spinner_RouteList);
        ArrayAdapter adapterRouteList=new ArrayAdapter(this, android.R.layout.simple_spinner_item,Route_names);
        adapterRouteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_RouteList.setAdapter(adapterRouteList);

        spinner_RouteList.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                seleted_routeIDType=hmapRouteIdNameDetails.get(arg0.getItemAtPosition(arg2).toString());
                
                mDataSource.fnSetAllRouteActiveStatus();
                mDataSource.updateActiveRoute(seleted_routeIDType.split(Pattern.quote("_"))[0], 1);
                
                rID=seleted_routeIDType.split(Pattern.quote("_"))[0];

                try {
                    //fnCreateStoreListOnLoad();

                    tl2.removeAllViews();
                    setStoresList();
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rl_for_other = (RelativeLayout) findViewById(R.id.rl_for_other);
        rl_for_other.setVisibility(RelativeLayout.GONE);

        ed_Street=(EditText)findViewById(R.id.streetid);

        spinner_manager.setOnItemSelectedListener(new OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {

                selected_manager=arg0.getItemAtPosition(arg2).toString();

                String ManagerID=hmapManagerNameManagerIdDetails.get(selected_manager);


                if(ManagerID.equals("0"))
                {
                    rl_for_other.setVisibility(RelativeLayout.GONE);
                    ed_Street.setText("");
                    Selected_manager_Id=0;

                }
                else if(ManagerID.equals("-99"))
                {
                    Selected_manager_Id=-99;
                    rl_for_other.setVisibility(RelativeLayout.VISIBLE);
                }
                else
                {
                    Selected_manager_Id=Integer.parseInt(ManagerID);

                    ed_Street.setText("");

                    rl_for_other.setVisibility(RelativeLayout.GONE);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

                //selected_location=arg0.getItemAtPosition(0).toString();
                //System.out.println("selected_location in resume1111111111 "+selected_location);
            }
        });


        
        int chk= mDataSource.counttblSelectedManagerDetails();
        
        if(chk==1)
        {
            String abcd= mDataSource.Fetch_tblSelectedManagerDetails();

            StringTokenizer tokens = new StringTokenizer(String.valueOf(abcd), "_");

            String as=tokens.nextToken().toString().trim();


            String ManagerName = tokens.nextToken().toString().trim();

            int ManagerID =  Integer.parseInt(as);

            int selected_choice_index=0;

            if(ManagerID==0)
            {
                spinner_manager.setSelection(0);

            }
            else if(ManagerID!=-99)
            {
                for(int i1=0;i1<Manager_names.length;i1++)
                {
                    if(Manager_names[i1].equals(ManagerName))
                    {
                        selected_choice_index=i1;
                    }
                }
                spinner_manager.setSelection(selected_choice_index);

            }

            else
            {
                for(int i1=0;i1<Manager_names.length;i1++)
                {
                    if(Manager_names[i1].equals(ManagerName))
                    {
                        selected_choice_index=i1;
                    }
                }
                spinner_manager.setSelection(selected_choice_index);

                
                String OtherName= mDataSource.fetchOtherNameBasicOfManagerID(ManagerID);
                
                rl_for_other.setVisibility(RelativeLayout.VISIBLE);
                ed_Street.setText(OtherName);



            }
        }




       // setUpVariable();


        //
      //  String routeNametobeSelectedInSpinner=mDataSource.GetActiveRouteDescrBasedCoverageIDandNodeTyep();
        String routeNametobeSelectedInSpinner= mDataSource.GetActiveRouteDescr();
        int index=0;
        if(hmapRouteIdNameDetails!=null)
        {

            Set set2 = hmapRouteIdNameDetails.entrySet();
            Iterator iterator = set2.iterator();
            Boolean isrouteSelected=false;

            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                if(routeNametobeSelectedInSpinner.trim().equals(me2.getKey().toString().trim()))
                {
                    isrouteSelected=true;
                    break;
                }
                index=index+1;
            }
            if(isrouteSelected)
            {
                spinner_RouteList.setSelection(index);
            }
            else
            {
                spinner_RouteList.setSelection(0);
            }

        }

        setStoresList();



    }

    public void firstTimeLocationTrack()
    {
        if(pDialog2STANDBY!=null)
        {
            if(pDialog2STANDBY.isShowing())
            {


            }
            else
            {
                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
                    locationRetrievingAndDistanceCalculating();
                }
            }
        }
        else
        {
            boolean isGPSok = false;
            boolean isNWok=false;
            isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
                locationRetrievingAndDistanceCalculating();
            }

        }
    }


    public void setStoresList()
    {

        



        storeList = mDataSource.FetchStoreListAllRoute();
     /*   storeRouteIdType=mDataSource.FetchStoreRouteIdType(rID);
        storeStatus = mDataSource.FetchStoreStatus(rID);

        storeCloseStatus = mDataSource.FetchStoreStoreCloseStatus(rID);

        storeNextDayStatus = mDataSource.FetchStoreStoreNextDayStatus();
        StoreflgSubmitFromQuotation= mDataSource.FetchStoreStatusflgSubmitFromQuotation();
        hmapStoreLatLongDistanceFlgRemap=mDataSource.fnGeStoreList(CommonInfo.DistanceRange);*/
        

        storeCode = new String[storeList.length];
        storeName = new String[storeList.length];
        hmapStoreNameID.clear();
        for (int splitval = 0; splitval <= (storeList.length - 1); splitval++)
        {
            StringTokenizer tokens = new StringTokenizer(String.valueOf(storeList[splitval]), "_");

            storeCode[splitval] = tokens.nextToken().trim();

            storeName[splitval] = tokens.nextToken().trim();
            hmapStoreNameID.put(storeName[splitval],storeCode[splitval]);


        }
        listStoreId=new ArrayList<String>(Arrays.asList(storeCode));
        listItem=new ArrayList<String>(Arrays.asList(storeName));
        adapterCusto=new ShivAdapter(getApplicationContext(), listItem);

           // adapterDistributor = new ArrayAdapter<String>(SurveyStoreList.this, R.layout.survey_list_row, R.id.product_name, storeName);
        //listDistributor.setAdapter(adapterDistributor);
        listDistributor.setAdapter(adapterCusto);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
               // adapterDistributor.getFilter().filter(arg0.toString().trim());

                String text = arg0.toString().toLowerCase(Locale.ENGLISH);
                adapterCusto.filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        listDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                String abc=listDistributor.getItemAtPosition(arg2).toString().trim();
                inputSearch.setText("");
                if(hmapStoreNameID.containsKey(abc)){
                 String storeID=   hmapStoreNameID.get(abc);
                    //uncomment if you want old survey
                    Intent intent=new Intent(SurveyStoreList.this,SurveyActivity.class);
                    //new surwey added
                    //Intent intent=new Intent(SurveyStoreList.this,SurveyActivityTwo.class);
                    intent.putExtra("imei", imei);
                    intent.putExtra("userDate", userDate);
                    intent.putExtra("pickerDate", pickerDate);
                    intent.putExtra("StoreID",storeID);
                    intent.putExtra("StoreName",abc);
                    startActivity(intent);
                }



            }
        });
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


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


        
        String allLoctionDetails=  mDataSource.getLocationDetails();
        
        if(allLoctionDetails.equals("0"))
        {
            firstTimeLocationTrack();
        }



    }


    public void showAlertForEveryOne(String msg)
    {
        //AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(SurveyStoreList.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                //finish();
            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        android.support.v7.app.AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
	/*	if(storeList.length>0)
		{

		}

		else
		{
			new GetStoresForDay().execute();
		}*/
    }

    public class CoundownClass extends CountDownTimer {

        public CoundownClass(long startTime, long interval) {
            super(startTime, interval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish()
        {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            String GpsLat="0";
            String GpsLong="0";
            String GpsAccuracy="0";
            String GpsAddress="0";
            if(isGPSEnabled)
            {

                Location nwLocation=null;

                if(nwLocation!=null){
                    double lattitude=nwLocation.getLatitude();
                    double longitude=nwLocation.getLongitude();
                    double accuracy= nwLocation.getAccuracy();
                    GpsLat=""+lattitude;
                    GpsLong=""+longitude;
                    GpsAccuracy=""+accuracy;
                    if(isOnline())
                    {
                        GpsAddress=getAddressOfProviders(GpsLat, GpsLong);
                    }
                    else
                    {
                        GpsAddress="NA";
                    }
                    GPSLocationLatitude=""+lattitude;
                    GPSLocationLongitude=""+longitude;
                    GPSLocationProvider="GPS";
                    GPSLocationAccuracy=""+accuracy;
                    AllProvidersLocation="GPS=Lat:"+lattitude+"Long:"+longitude+"Acc:"+accuracy;

                }
            }

            Location gpsLocation=null;
            String NetwLat="0";
            String NetwLong="0";
            String NetwAccuracy="0";
            String NetwAddress="0";
            if(gpsLocation!=null){
                double lattitude1=gpsLocation.getLatitude();
                double longitude1=gpsLocation.getLongitude();
                double accuracy1= gpsLocation.getAccuracy();

                NetwLat=""+lattitude1;
                NetwLong=""+longitude1;
                NetwAccuracy=""+accuracy1;
                if(isOnline())
                {
                    NetwAddress=getAddressOfProviders(NetwLat, NetwLong);
                }
                else
                {
                    NetwAddress="NA";
                }


                NetworkLocationLatitude=""+lattitude1;
                NetworkLocationLongitude=""+longitude1;
                NetworkLocationProvider="Network";
                NetworkLocationAccuracy=""+accuracy1;
                if(!AllProvidersLocation.equals(""))
                {
                    AllProvidersLocation=AllProvidersLocation+"$Network=Lat:"+lattitude1+"Long:"+longitude1+"Acc:"+accuracy1;
                }
                else
                {
                    AllProvidersLocation="Network=Lat:"+lattitude1+"Long:"+longitude1+"Acc:"+accuracy1;
                }
                System.out.println("LOCATION(N/W)  LATTITUDE: " +lattitude1 + "LONGITUDE:" + longitude1+ "accuracy:" + accuracy1);

            }
									 /* TextView accurcy=(TextView) findViewById(R.id.Acuracy);
									  accurcy.setText("GPS:"+GPSLocationAccuracy+"\n"+"NETWORK"+NetworkLocationAccuracy+"\n"+"FUSED"+fusedData);*/

            System.out.println("LOCATION Fused"+fusedData);

            String FusedLat="0";
            String FusedLong="0";
            String FusedAccuracy="0";
            String FusedAddress="0";

            if(!FusedLocationProvider.equals(""))
            {
                fnAccurateProvider="Fused";
                fnLati=FusedLocationLatitude;
                fnLongi=FusedLocationLongitude;
                fnAccuracy= Double.parseDouble(FusedLocationAccuracy);

                FusedLat=FusedLocationLatitude;
                FusedLong=FusedLocationLongitude;
                FusedAccuracy=FusedLocationAccuracy;
                FusedLocationLatitudeWithFirstAttempt=FusedLocationLatitude;
                FusedLocationLongitudeWithFirstAttempt=FusedLocationLongitude;
                FusedLocationAccuracyWithFirstAttempt=FusedLocationAccuracy;
                if(isOnline())
                {
                    FusedAddress=getAddressOfProviders(FusedLat, FusedLong);
                }
                else
                {
                    FusedAddress="NA";
                }

                if(!AllProvidersLocation.equals(""))
                {
                    AllProvidersLocation=AllProvidersLocation+"$Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
                }
                else
                {
                    AllProvidersLocation="Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
                }
            }



            try {
                if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
                {
                    stopLocationUpdates();
                    mGoogleApiClient.disconnect();
                }
            }
            catch (Exception e){

            }
            //




            fnAccurateProvider="";
            fnLati="0";
            fnLongi="0";
            fnAccuracy=0.0;

            if(!FusedLocationProvider.equals(""))
            {
                fnAccurateProvider="Fused";
                fnLati=FusedLocationLatitude;
                fnLongi=FusedLocationLongitude;
                fnAccuracy= Double.parseDouble(FusedLocationAccuracy);
            }

            if(!fnAccurateProvider.equals(""))
            {
                if(!GPSLocationProvider.equals(""))
                {
                    if(Double.parseDouble(GPSLocationAccuracy)<fnAccuracy)
                    {
                        fnAccurateProvider="Gps";
                        fnLati=GPSLocationLatitude;
                        fnLongi=GPSLocationLongitude;
                        fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
                    }
                }
            }
            else
            {
                if(!GPSLocationProvider.equals(""))
                {
                    fnAccurateProvider="Gps";
                    fnLati=GPSLocationLatitude;
                    fnLongi=GPSLocationLongitude;
                    fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
                }
            }

            if(!fnAccurateProvider.equals(""))
            {
                if(!NetworkLocationProvider.equals(""))
                {
                    if(Double.parseDouble(NetworkLocationAccuracy)<fnAccuracy)
                    {
                        fnAccurateProvider="Network";
                        fnLati=NetworkLocationLatitude;
                        fnLongi=NetworkLocationLongitude;
                        fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
                    }
                }
            }
            else
            {
                if(!NetworkLocationProvider.equals(""))
                {
                    fnAccurateProvider="Network";
                    fnLati=NetworkLocationLatitude;
                    fnLongi=NetworkLocationLongitude;
                    fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
                }
            }
            // fnAccurateProvider="";
            if(fnAccurateProvider.equals(""))
            {
                //because no location found so updating table with NA
                
                mDataSource.deleteLocationTable();
                //mDataSource.saveTblLocationDetails("NA", "NA", "NA","NA","NA","NA","NA","NA", "NA", "NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA");
                
                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }



                int flagtoShowStorelistOrAddnewStore= mDataSource.fncheckCountNearByStoreExistsOrNot(CommonInfo.DistanceRange);


                if(flagtoShowStorelistOrAddnewStore==1)
                {
                    //getDataFromDatabaseToHashmap();
                    //tl2.removeAllViews();

                    if(tl2.getChildCount()>0){
                        tl2.removeAllViews();
                        // dynamcDtaContnrScrollview.removeAllViews();
                        //addViewIntoTable();
                        setStoresList();
                    }
                    else
                    {
                        //addViewIntoTable();
                        setStoresList();
                    }
                    if(pDialog2STANDBY!=null)
                    {
                        if (pDialog2STANDBY.isShowing())
                        {
                            pDialog2STANDBY.dismiss();
                        }
                    }

                       /* Intent intent =new Intent(LauncherActivity.this,StorelistActivity.class);
                        LauncherActivity.this.startActivity(intent);
                        finish();*/

                }
                else
                {
                    if(pDialog2STANDBY!=null) {
                        if (pDialog2STANDBY.isShowing()) {
                            pDialog2STANDBY.dismiss();
                        }
                    }
                }
                //send direct to dynamic page-------------------------
               /* Intent intent=new Intent(StorelistActivity.this,AddNewStore_DynamicSectionWise.class);
                intent.putExtra("FLAG_NEW_UPDATE","NEW");
                StorelistActivity.this.startActivity(intent);
                finish();*/


                //commenting below error message
                // showAlertForEveryOne("Please try again, No Fused,GPS or Network found.");
            }
            else
            {
                String FullAddress="0";
                if(isOnline())
                {
                    FullAddress=   getAddressForDynamic(fnLati, fnLongi);
                }
                else
                {
                    FullAddress="NA";
                }

                if(!GpsLat.equals("0") )
                {
                    fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
                }
                //now Passing intent to other activity
                String addr="NA";
                String zipcode="NA";
                String city="NA";
                String state="NA";


                if(!FullAddress.equals("NA"))
                {
                    addr = FullAddress.split(Pattern.quote("^"))[0];
                    zipcode = FullAddress.split(Pattern.quote("^"))[1];
                    city = FullAddress.split(Pattern.quote("^"))[2];
                    state = FullAddress.split(Pattern.quote("^"))[3];
                }

                if(fnAccuracy>10000)
                {
                    
                    mDataSource.deleteLocationTable();
                   // mDataSource.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), addr, city, zipcode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);
                    
                    if(pDialog2STANDBY.isShowing())
                    {
                        pDialog2STANDBY.dismiss();
                    }

                    //send to addstore Dynamic page direct-----------------------------
                   /* Intent intent=new Intent(LauncherActivity.this,AddNewStore_DynamicSectionWise.class);
                    intent.putExtra("FLAG_NEW_UPDATE","NEW");
                    LauncherActivity.this.startActivity(intent);
                    finish();*/


                    //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget


                }
                else
                {
                    
                    mDataSource.deleteLocationTable();
                   // mDataSource.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), addr, city, zipcode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);
                    


                    hmapOutletListForNear= mDataSource.fnGetALLOutletMstr();
                    System.out.println("SHIVAM"+hmapOutletListForNear);
                    if(hmapOutletListForNear!=null)
                    {

                        for(Map.Entry<String, String> entry:hmapOutletListForNear.entrySet())
                        {
                            int DistanceBWPoint=1000;
                            String outID=entry.getKey().toString().trim();
                            //  String PrevAccuracy = entry.getValue().split(Pattern.quote("^"))[0];
                            String PrevLatitude = entry.getValue().split(Pattern.quote("^"))[0];
                            String PrevLongitude = entry.getValue().split(Pattern.quote("^"))[1];

                            // if (!PrevAccuracy.equals("0"))
                            // {
                            if (!PrevLatitude.equals("0"))
                            {
                                try
                                {
                                    Location locationA = new Location("point A");
                                    locationA.setLatitude(Double.parseDouble(fnLati));
                                    locationA.setLongitude(Double.parseDouble(fnLongi));

                                    Location locationB = new Location("point B");
                                    locationB.setLatitude(Double.parseDouble(PrevLatitude));
                                    locationB.setLongitude(Double.parseDouble(PrevLongitude));

                                    float distance = locationA.distanceTo(locationB) ;
                                    DistanceBWPoint=(int)distance;

                                    hmapOutletListForNearUpdated.put(outID, ""+DistanceBWPoint);
                                }
                                catch(Exception e)
                                {

                                }
                            }
                            // }
                        }
                    }

                    if(hmapOutletListForNearUpdated!=null)
                    {
                        
                        for(Map.Entry<String, String> entry:hmapOutletListForNearUpdated.entrySet())
                        {
                            String outID=entry.getKey().toString().trim();
                            String DistanceNear = entry.getValue().trim();
                            if(outID.equals("853399-a1445e87daf4-NA"))
                            {
                                System.out.println("Shvam Distance = "+DistanceNear);
                            }
                            if(!DistanceNear.equals(""))
                            {
                                //853399-81752acdc662-NA
                                if(outID.equals("853399-a1445e87daf4-NA"))
                                {
                                    System.out.println("Shvam Distance = "+DistanceNear);
                                }
                                mDataSource.UpdateStoreDistanceNear(outID,Integer.parseInt(DistanceNear));
                            }
                        }
                        
                    }
                    //send to storeListpage page
                    //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget
                    int flagtoShowStorelistOrAddnewStore=      mDataSource.fncheckCountNearByStoreExistsOrNot(CommonInfo.DistanceRange);


                    if(flagtoShowStorelistOrAddnewStore==1)
                    {
                        //getDataFromDatabaseToHashmap();
                        if(tl2.getChildCount()>0){
                            tl2.removeAllViews();
                            // dynamcDtaContnrScrollview.removeAllViews();
                            //addViewIntoTable();
                            setStoresList();
                        }
                        else
                        {
                            //addViewIntoTable();
                            setStoresList();
                        }

                       /* Intent intent =new Intent(LauncherActivity.this,StorelistActivity.class);
                        LauncherActivity.this.startActivity(intent);
                        finish();*/

                    }
                    else
                    {
                        //send to AddnewStore directly
                       /* Intent intent=new Intent(LauncherActivity.this,AddNewStore_DynamicSectionWise.class);
                        intent.putExtra("FLAG_NEW_UPDATE","NEW");
                        LauncherActivity.this.startActivity(intent);
                        finish();*/


                        if(tl2.getChildCount()>0){
                            tl2.removeAllViews();
                            // dynamcDtaContnrScrollview.removeAllViews();
                            //addViewIntoTable();
                            setStoresList();
                        }
                        else
                        {
                            //addViewIntoTable();
                            setStoresList();
                        }

                    }
                    if(pDialog2STANDBY.isShowing())
                    {
                        pDialog2STANDBY.dismiss();
                    }

                }
               /* Intent intent =new Intent(LauncherActivity.this,StorelistActivity.class);
               *//* intent.putExtra("FROM","SPLASH");
                intent.putExtra("errorMessageFlag",errorMessageFlag); // 0 if no error, if error, then error message passes
                intent.putExtra("username",username);//if error then it will 0
                intent.putExtra("totaltarget",totaltarget);////if error then it will 0
                intent.putExtra("todayTarget",todayTarget);//if error then it will 0*//*
                LauncherActivity.this.startActivity(intent);
                finish();
*/
                GpsLat="0";
                GpsLong="0";
                GpsAccuracy="0";
                GpsAddress="0";
                NetwLat="0";
                NetwLong="0";
                NetwAccuracy="0";
                NetwAddress="0";
                FusedLat="0";
                FusedLong="0";
                FusedAccuracy="0";
                FusedAddress="0";

                //code here
            }


            //AddStoreBtn.setEnabled(true);

        }

        @Override
        public void onTick(long arg0) {
            // TODO Auto-generated method stub

        }}




    public String getAddressForDynamic(String latti,String longi){


        String areaToMerge="NA";
        Address address=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0){
                if(addresses.get(0).getAddressLine(1)!=null){
                    addr=addresses.get(0).getAddressLine(1);
                }

                if(addresses.get(0).getLocality()!=null){
                    city=addresses.get(0).getLocality();
                }

                if(addresses.get(0).getAdminArea()!=null){
                    state=addresses.get(0).getAdminArea();
                }


                for(int i=0 ;i<addresses.size();i++){
                    address = addresses.get(i);
                    if(address.getPostalCode()!=null){
                        zipcode=address.getPostalCode();
                        break;
                    }




                }

                if(addresses.get(0).getAddressLine(0)!=null && addr.equals("NA")){
                    String countryname="NA";
                    if(addresses.get(0).getCountryName()!=null){
                        countryname=addresses.get(0).getCountryName();
                    }

                    addr=  getAddressNewWay(addresses.get(0).getAddressLine(0),city,state,zipcode,countryname);
                }

            }
            else{FULLADDRESS3.append("NA");}
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            return fullAddress=addr+"^"+zipcode+"^"+city+"^"+state;
        }
    }
    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
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

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.AppLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="GPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.AppLatLngJsonFile+"/"+txtFileNamenew;


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

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{

        }
    }
    private void getManagersDetail()
    {

        hmapManagerNameManagerIdDetails= mDataSource.fetch_Manager_List();

        int index=0;
        if(hmapManagerNameManagerIdDetails!=null)
        {
            Manager_names=new String[hmapManagerNameManagerIdDetails.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapManagerNameManagerIdDetails);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                Manager_names[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }

	/*public String getAddressOfProviders(String latti, String longi){

		StringBuilder FULLADDRESS2 =new StringBuilder();
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());



		try {
			addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

			if (addresses == null || addresses.size()  == 0)
			{
				FULLADDRESS2=  FULLADDRESS2.append("NA");
			}
			else
			{
				for(Address address : addresses) {
					//  String outputAddress = "";
					for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
						if(i==1)
						{
							FULLADDRESS2.append(address.getAddressLine(i));
						}
						else if(i==2)
						{
							FULLADDRESS2.append(",").append(address.getAddressLine(i));
						}
					}
				}
		      *//* //String address = addresses.get(0).getAddressLine(0);
		       String address = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
		       String city = addresses.get(0).getLocality();
		       String state = addresses.get(0).getAdminArea();
		       String country = addresses.get(0).getCountryName();
		       String postalCode = addresses.get(0).getPostalCode();
		       String knownName = addresses.get(0).getFeatureName();
		       FULLADDRESS=address+","+city+","+state+","+country+","+postalCode;
		      Toast.makeText(contextcopy, "ADDRESS"+address+"city:"+city+"state:"+state+"country:"+country+"postalCode:"+postalCode, Toast.LENGTH_LONG).show();*//*

			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Here 1 represent max location result to returned, by documents it recommended 1 to 5


		return FULLADDRESS2.toString();

	}*/

    public String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(SurveyStoreList.this, Locale.ENGLISH);



        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

            if (addresses == null || addresses.size()  == 0 || addresses.get(0).getAddressLine(0)==null)
            {
                FULLADDRESS2=  FULLADDRESS2.append("NA");
            }
            else
            {
                FULLADDRESS2 =FULLADDRESS2.append(addresses.get(0).getAddressLine(0));
            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        return FULLADDRESS2.toString();

    }
    public void saveLocale(String lang)
    {


        SharedPreferences prefs = getSharedPreferences("LanguagePref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", lang);
        editor.commit();
    }

    void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
        saveLocale(language);
        // updateTexts();
        //you can refresh or you can settext
        Intent refresh = new Intent(SurveyStoreList.this, AllButtonActivity.class);
        startActivity(refresh);
        finish();

    }
    public String getAddressNewWay(String ZeroIndexAddress,String city,String State,String pincode,String country){
        String editedAddress=ZeroIndexAddress;
        if(editedAddress.contains(city)){
            editedAddress= editedAddress.replace(city,"");

        }
        if(editedAddress.contains(State)){
            editedAddress=editedAddress.replace(State,"");

        }
        if(editedAddress.contains(pincode)){
            editedAddress= editedAddress.replace(pincode,"");

        }
        if(editedAddress.contains(country)){
            editedAddress=editedAddress.replace(country,"");

        }
        if(editedAddress.contains(",")){
            editedAddress=editedAddress.replace(","," ");

        }

        return editedAddress;
    }

    private void getRouteDetail()
    {

        hmapRouteIdNameDetails= mDataSource.fetch_Route_List();

        int index=0;
        if(hmapRouteIdNameDetails!=null)
        {
            Route_names=new String[hmapRouteIdNameDetails.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapRouteIdNameDetails);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                Route_names[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }
    public void fnCreateStoreListOnLoad() throws Exception
    {



        for(int i = 0; i < tl2.getChildCount(); i++)
        {
            TableRow row = (TableRow) tl2.getChildAt(i);
            if(seleted_routeIDType.equals(row.getTag()))
            {
                row.setVisibility(View.VISIBLE);
            }
            else
            {
                row.setVisibility(View.GONE);
            }
        }

    }





    class ShivAdapter extends ArrayAdapter<String> implements Filterable
    {
        Context context;
        ArrayList<String> ttt;
        ArrayList<String> arraylst;
        ShivAdapter(Context c, ArrayList<String> ttt )
        {

            super(c,R.layout.survey_list_row,R.id.product_name,ttt);
            this.arraylst=new ArrayList<String>();
            this.arraylst.addAll(ttt);
            this.context=c;
            this.ttt=ttt;
            //this.titleArray=titles;
            //this.descriptionArray=description;
        }

        class MyViewHolder
        {
            ImageView myImage;
            TextView myTitle;
            CheckBox myDescription;
            MyViewHolder(View v)
            {

                //  myImage=(ImageView) v.findViewById(R.id.imageView1);
                myTitle=(TextView) v.findViewById(R.id.product_name);
                // myDescription=(CheckBox) v.findViewById(R.id.checkbox);
            }


        }

        public View getView(int position, View convertView, ViewGroup parent){
            View row=convertView;
            final MyViewHolder holder;
            //if you want to put some data in row from outside hashmap and you getting problem in scrolling then comment uncomment row=null;
            row=null;
            if(row==null)
            {

                LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(R.layout.survey_list_row, parent,false);
                holder=new MyViewHolder(row);
                row.setTag(holder);

            }
            else{
                holder=(MyViewHolder) row.getTag();
            }

            //holder.myImage.setImageResource(images[position]);
            //holder.myTitle.setText(titleArray[position]);

            holder.myTitle.setText(ttt.get(position).toString().trim());
            holder.myTitle.setTag(listStoreId.get(position).toString().trim());

            if(hmapTestSubmitOrNot!=null){
                if((!hmapTestSubmitOrNot.isEmpty()) && hmapTestSubmitOrNot.containsKey(holder.myTitle.getTag().toString()) ){
                    holder.myTitle.setTextColor(getResources().getColor(R.color.green));
                    holder.myTitle.setEnabled(false);
                }
            }





            // holder.myDescription.setTag(ttt.get(position).toString().trim());
            // String valuess=	hmapBooleanValues.get(ttt.get(position).toString().trim());
            //  holder.myDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_button_image));
            /*if(valuess.equals("0")){
                holder.myDescription.setChecked(false);
            }

            if(valuess.equals("1")){
                holder.myDescription.setChecked(true);
            }*/
		/*if(position==2){
			holder.myTitle.setTextColor(Color.GREEN);

		}*/
            //holder.myDescription.setChecked(descriptionArray.get(position));
            holder.myTitle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    //String mm=		holder.myDescription.getTag().toString();
                    //System.out.println("fff"+mm);

                  //  int mmm=(Integer) holder.myTitle.getTag();
                    String  abc=(String) holder.myTitle.getText();

                    inputSearch.setText("");
                    if(hmapStoreNameID.containsKey(abc)){
                        String storeID=   hmapStoreNameID.get(abc);
                        //Intent intent=new Intent(SurveyStoreList.this,SurveyActivityTwo.class);
                        Intent intent=new Intent(SurveyStoreList.this,SurveyActivity.class);
                        intent.putExtra("imei", imei);
                        intent.putExtra("userDate", userDate);
                        intent.putExtra("pickerDate", pickerDate);
                        intent.putExtra("StoreID",storeID);
                        intent.putExtra("StoreName",abc);
                        startActivity(intent);
                    }
                /*    if(holder.myDescription.isChecked()){
                        holder.myDescription.getTag().toString().trim();
                        hmapBooleanValues.put(holder.myDescription.getTag().toString().trim(), "1");
                        holder.myDescription.setChecked(true);
                        adapterCusto.notifyDataSetChanged();
                        if(holder.myDescription.getTag().toString().trim().equals("Others")){
                            othersEditText.setVisibility(View.VISIBLE);
                        }
                        cal25[mmm]=true;
                        listItemBoolean.set(mmm, true);
                        adapterCusto.notifyDataSetChanged();
                        holder.myDescription.setChecked(true);
                        String abc=lv.getItemAtPosition(mmm).toString().trim();
                        hmapCheckedValues.put(abc+"^"+mmm, abc);
                        //System.out.println("Printt"+ii);
                    }
                    else{
                        holder.myDescription.getTag().toString().trim();
                        hmapBooleanValues.put(holder.myDescription.getTag().toString().trim(), "0");
                        holder.myDescription.setChecked(false);
                        adapterCusto.notifyDataSetChanged();
                        if(holder.myDescription.getTag().toString().trim().equals("Others")){
                            othersEditText.setVisibility(View.GONE);


                        }

                        cal25[mmm]=false;
                        listItemBoolean.set(mmm, false);
                        adapterCusto.notifyDataSetChanged();
                        holder.myDescription.setChecked(false);
                        String abc=lv.getItemAtPosition(mmm).toString().trim();
                        hmapCheckedValues.put(abc+"^"+mmm, "");
                    }*/

                }
            });
            return row;
        }
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            ttt.clear();
            if (charText.length() == 0) {
                ttt.addAll(arraylst);
            }
            else
            {
                for (int i=0;i<arraylst.size();i++)
                {
                    if (arraylst.get(i).toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        ttt.add(arraylst.get(i));
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
