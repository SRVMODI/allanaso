package com.astix.allanasosfa;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.sync.SyncJobService;
import com.astix.allanasosfa.utils.AppUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.allanasosfa.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Pattern;


public class AddNewStore_DynamicSectionWise extends FragmentActivity implements LocationInterface,SearchListCommunicator,OnMapReadyCallback,CategoryCommunicatorCityState
{
    public static String distID = "0";
    public static int flgRuleTaxVal, flgTransType;
    public static String ProviderFromLauncher = "NA";
    public String fnlAddressName = "NA";
    public String fnlStoreType = "0", fnlOwnerName = "NA", fnlMobileNumber = "NA", fnSalesPersonName = "NA", fnSalesPersonContactNo = "NA", fnStoreCatType = "NA";
    public int flgOldNewStore=0;
    public int IsComposite = 0;
    public int flgSelfStore=0;
    public String strGlobalOrderID="0";
    public String flgGSTCapture="1";
    public String flgGSTCompliance="0";
    public String GSTNumber="NA";
    public int flgGSTRecordFromServer=0;


    public static String GpsLatFromLauncher = "NA";
    public static String GpsLongFromLauncher = "NA";
    public static String GpsAccuracyFromLauncher = "NA";
    public static String NetworkLatFromLauncher = "NA";
    public static String NetworkLongFromLauncher = "NA";
    public static String NetworkAccuracyFromLauncher = "NA";
    public static String FusedLatFromLauncher = "NA";
    public static String FusedLongFromLauncher = "NA";
    public static String FusedAccuracyFromLauncher = "NA";
    public static String fnAddressFromLauncher = "NA";
    public static String AllProvidersLocationFromLauncher = "NA";
    public static String GpsAddressFromLauncher = "NA";
    public static String NetwAddressFromLauncher = "NA";
    public static String FusedAddressFromLauncher = "NA";
    public static String FusedLocationLatitudeWithFirstAttemptFromLauncher = "NA";
    public static String FusedLocationLongitudeWithFirstAttemptFromLauncher = "NA";
    public static String FusedLocationAccuracyWithFirstAttemptFromLauncher = "NA";


    LinkedHashMap<String, String> hmapStoreQuestAnsNew = new LinkedHashMap<>();
    public static String activityFrom="";
    public static  int flgUpdateSomeNewStoreFlags=1;
    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static int flgRestart=0;
    public static int flgStoreOrder=0;

    private LinkedHashMap<String, String> hmapStoresBelowHundredMeter;
    private LinkedHashMap<String, String> hmapOutletListForNear= new LinkedHashMap<>();
    private LinkedHashMap<String, String> hmapCurrentOutletLatLong= new LinkedHashMap<>();

    private final LinkedHashMap<String, String> hmapOutletListForNearUpdated= new LinkedHashMap<>();
    private String newfullFileName;
    private String allValuesOfPaymentStageID="0";
    private String QuestIDForOutChannel="0";
    // public GetUpdateSchemeForNewStore  taskUpdateScheme=null;

   /* @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            imageF_savedInstance = new File(savedInstanceState.getString("imageClkdPath"));

            imageName_savedInstance = savedInstanceState.getString("imageName");
            clickedTagPhoto_savedInstance = savedInstanceState.getString("clickedTagPhoto");
            uriSavedImage_savedInstance = Uri.parse(savedInstanceState.getString("uriSavedImage"));
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    LinkedHashMap<String, String> hmapStoresDSRImageList= new LinkedHashMap<>();


    public int flgHasQuote=0;
    public static String address,pincode,city,state,latitudeToSave,longitudeToSave,accuracyToSave;
    public int flgAllowQuotation=1;
    public int flgSubmitFromQuotation=0;


    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private LocationRequest mLocationRequest;

    private String fnAccurateProvider="";
    private String fnLati="0";
    private String fnLongi="0";
    private Double fnAccuracy=0.0;

    public String OutletID="NA";
    private String StoreName="NA";

    public static String storeNameToShow="";
    public String StoreName_tag="NA";
    public static int StoreTypeTradeChannel=0;



    private String fusedData;

    public int checkSecondTaskStatus=0;
    public static int ServiceWorkerDataComingFlag=0;
    public static String ServiceWorkerStoreID="";
    public static String ServiceWorkerResultSet="";


    private String FusedLocationLatitude="0";
    private String FusedLocationLongitude="0";
    private String FusedLocationProvider="";
    private String FusedLocationAccuracy="0";

    private String GPSLocationLatitude="0";
    private String GPSLocationLongitude="0";
    private String GPSLocationProvider="";
    private String GPSLocationAccuracy="0";

    private String NetworkLocationLatitude="0";
    private String NetworkLocationLongitude="0";
    private String NetworkLocationProvider="";
    private String NetworkLocationAccuracy="0";

    private AppLocationService appLocationService;
    private TextView txtAddress;
    TextView txtAccuracy;
    TextView txtLong;
    TextView txtLat;
    TextView txt_internetProb;
    RelativeLayout rl_sectionMap,rl_sectionQuest;

    // public	GetAddingStoreInfo task=null;
    public	Timer timer;
    // public	MyTimerTask myTimerTask;

    public ProgressDialog pDialogGetStores;
    String VisitEndTS;
    public int chkFlgForErrorToCloseApp=0;
    String CustomStringForServiceWorker="";

    String CustomStoreID="NA";
    private LocationVo locVo;

    private LocationManager locationManager;
    public static int battLevel;

    public float Current_acc;
    private MapFragment mapFrag;
    GoogleMap googleMap;

    Button btnSubmit;
    private Location location;
    private ProgressDialog pDialog2STANDBY;

    public LocationListener locationListener;
    public double latitude; // latitude
    public double longitude; // longitude

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private boolean mIsServiceStarted = false;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 second

    private static final long MIN_TIME_BW_UPDATESNew = 100; // 1 second

    private boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    public float acc=0F;
    String locProvider="None";
    public String lastKnownLocLatitude="";
    public String lastKnownLocLongitude="";
    public String accuracy="0";
    public String locationProvider="Default";

    public ProgressDialog pDialogSync;



    LinearLayout ll_allData,ll_locmsg;
    private AddressResultReceiver mResultReceiver;
    private static String fetchAddress="";


    private NewStoreForm newStore_Fragment;
    private FragmentManager manager;
    private FragmentTransaction fragTrans;
    private ImageView img_next;
    private TextView txt_Next;
    private TextView txt_Activityname;
    ImageView img_reject;
    TextView txt_reject;

    ImageView img_revisit;
    TextView txt_revisit;


    public static LinkedHashMap<String, ArrayList<String>> hmapSection_key= new LinkedHashMap<>();
    public static LinkedHashMap<String, String> hmapDpndtQustGrpId= new LinkedHashMap<>();
    public static LinkedHashMap<String, String> hmapQuesIdandGetAnsCntrlType= new LinkedHashMap<>();
    public static LinkedHashMap<String, String> hmapQuesIdValues= new LinkedHashMap<>();
    public static LinkedHashMap<String, ArrayList<String>> hmapQuesGropKeySection= new LinkedHashMap<>();
    public static LinkedHashMap<String, String> hmapGroupId_Desc= new LinkedHashMap<>();
    public static LinkedHashMap<String, ArrayList<String>> hmapSctnId_GrpId= new LinkedHashMap<>();
    // public static LinkedHashMap<String, String> hmapOptionId_OptionValue=new LinkedHashMap<String, String>();
    private LinearLayout ll_next;
    private LinearLayout ll_back;
    private LinearLayout ll_save_Exit;
    private LinearLayout ll_map;
    private LinearLayout ll_refresh;
    private LinearLayout ll_reject;
    private LinearLayout ll_revisit;
    private int refreshCount=0;
    private RadioGroup rg_yes_no;
    private RadioButton rb_yes;
    private RadioButton rb_no;
    private Button btn_refresh;
    private TextView txt_rfrshCmnt;

    private ImageView img_exit;
    private AppDataSource helperDb;

    private String date_value="";
    private static String rID;
    private int sectionToShowHide=1;
    private static String pickerDate="";
    private static String imei;

    public static String VisitStartTS="NA";
    public static String selStoreID="0";


    private static int CoverageAreaID=0;
    private static int RouteNodeID=0;
    public static String StoreCategoryType="0-0-0";
    public static int StoreSectionCount=0;




    public static String FLAG_NEW_UPDATE="";
    private String LattitudeFromLauncher="NA";
    private String   LongitudeFromLauncher="NA";

    public String SOLattitudeFromLauncher="NA";
    public String   SOLongitudeFromLauncher="NA";


    public String SOStoreVisitStartTime="NA";
    public String   SOStoreVisitEndTime="NA";




    public String AccuracyFromLauncher="NA";
    public String   AddressFromLauncher="NA";
    public String CityFromLauncher="NA";
    public String   PincodeFromLauncher="NA";
    public String StateFromLauncher="NA";

    private MyReceiver myReceiver;

    private final DatabaseAssistant DA=new DatabaseAssistant(this);

    private static String  VisitStartTimeOfNewStore="NA";

    public static int IsStoreDataCompleteSaved=0;
    public static int flgApproveOrRejectOrNoActionOrReVisit=0;
    public static int flgStoreVisitMode=0;


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mIsServiceStarted) {
            mIsServiceStarted = false;
            stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
        }
        this.unregisterReceiver(this.mBatInfoReceiver);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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


    private void SetHundredMeterStoreNearCurrentStore(String selStoreID)
    {
        hmapOutletListForNear=helperDb.fnGetALLOutletMstr();
        hmapCurrentOutletLatLong=helperDb.fnGeStoreLatAndLongBasedOnStoreID(selStoreID);
        String CurrentLatitude=null;
        String CurrentLongitude=null;
        if(hmapCurrentOutletLatLong!=null)
        {
            String LatLong=hmapCurrentOutletLatLong.get(selStoreID);
            CurrentLatitude = LatLong.split(Pattern.quote("~"))[0];
            CurrentLongitude = LatLong.split(Pattern.quote("~"))[1];
        }
        else
        {
            CurrentLatitude=SOLattitudeFromLauncher;
            CurrentLongitude=SOLongitudeFromLauncher;

        }


        if(hmapOutletListForNear!=null)
        {

            for(Map.Entry<String, String> entry:hmapOutletListForNear.entrySet())
            {
                int DistanceBWPoint=1000;
                String outID= entry.getKey().trim();
                String PrevLatitude = entry.getValue().split(Pattern.quote("^"))[0];
                String PrevLongitude = entry.getValue().split(Pattern.quote("^"))[1];

                if (!PrevLatitude.equals("0"))
                {
                    try
                    {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(Double.parseDouble(CurrentLatitude));
                        locationA.setLongitude(Double.parseDouble(CurrentLongitude));
                        // locationA.setLatitude(Double.parseDouble("28.4473504000000000000000"));
                        // locationA.setLongitude(Double.parseDouble("77.0545223000000000000000"));

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

            }
        }

        if(hmapOutletListForNearUpdated!=null)
        {
            //helperDb.open();
            for(Map.Entry<String, String> entry:hmapOutletListForNearUpdated.entrySet())
            {
                String outID= entry.getKey().trim();
                String DistanceNear = entry.getValue().trim();
                if(!DistanceNear.equals(""))
                {
                    helperDb.UpdateStoreDistanceNear(outID,Integer.parseInt(DistanceNear));
                }
            }
            //helperDb.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstoredynamicsectionwise);
        flgStoreVisitMode= CommonInfo.flgLTFoodsSOOnlineOffLine;
        refreshCount=0;
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        helperDb=new AppDataSource(AddNewStore_DynamicSectionWise.this);
        locVo=new LocationVo();
        address="";
        pincode="";
        city="";
        state="";
        latitudeToSave="";
        longitudeToSave="";
        accuracyToSave="";
        FLAG_NEW_UPDATE="";
        boolean isGPSok = false;
        boolean isNWok=false;
        VisitStartTS="";
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        long  syncTIMESTAMP1 = System.currentTimeMillis();
        Date dateobj1 = new Date(syncTIMESTAMP1);

        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        VisitStartTS = df1.format(dateobj1);

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
        storeNameToShow="";
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Intent extras = getIntent();
        FLAG_NEW_UPDATE=  extras.getStringExtra("FLAG_NEW_UPDATE");
        activityFrom=extras.getStringExtra("activityFrom");
        txt_Activityname=(TextView) findViewById(R.id.txt_Activityname);
        if(FLAG_NEW_UPDATE.equals("UPDATE"))
        {

            selStoreID=  extras.getStringExtra("StoreID");
            StoreName=    extras.getStringExtra("StoreName");
           /* CoverageAreaID=Integer.parseInt(extras.getStringExtra("CoverageAreaID"));
            RouteNodeID=Integer.parseInt(extras.getStringExtra("RouteNodeID"));
            StoreCategoryType=    extras.getStringExtra("StoreCategoryType");
            StoreSectionCount=Integer.parseInt(extras.getStringExtra("StoreSectionCount"));*/



            storeNameToShow=StoreName;
            SetHundredMeterStoreNearCurrentStore(selStoreID);
        }
        else
        {
            txt_Activityname.setText("Add New Outlet");
            selStoreID=genTempID();
            /*StoreCategoryType=extras.getStringExtra("StoreCategoryType");*/

          /*  CoverageAreaID=Integer.parseInt(extras.getStringExtra("CoverageAreaID"));
            RouteNodeID=Integer.parseInt(extras.getStringExtra("RouteNodeID"));

            StoreSectionCount=Integer.parseInt(extras.getStringExtra("StoreSectionCount"));*/

        }


        if(extras !=null)
        {


            date_value="28-Jun-2017";
            pickerDate= "28-Jun-2017";
            //imei="123";
            rID="1";
        }


            imei=AppUtils.getIMEI(AddNewStore_DynamicSectionWise.this);

        checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWise.this);
        //helperDb.open();
        String allLoctionDetails=  helperDb.getLocationDetails();
        //helperDb.close();
        if(helperDb.fnCheckIfStoreIDExistsIn_tblStoreDeatils(selStoreID)==0)
        {
            flgOldNewStore=1;
            SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
            AccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[2];
            AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];

            SOStoreVisitStartTime="NA";
            SOStoreVisitEndTime="NA";
            LattitudeFromLauncher="NA";
            LongitudeFromLauncher="NA";
            allValuesOfPaymentStageID="0";


            LattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            LongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
            AccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[2];
            AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];

            ProviderFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[7];
            GpsLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[8];
            GpsLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[9];
            GpsAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[10];
            NetworkLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[11];
            NetworkLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[12];
            NetworkAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[13];
            FusedLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[14];
            FusedLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[15];
            FusedAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[16];

            AllProvidersLocationFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[17];
            GpsAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[18];
            NetwAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[19];
            FusedAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[20];
            FusedLocationLatitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[21];
            FusedLocationLongitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[22];
            FusedLocationAccuracyWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[23];
            if(SOStoreVisitStartTime.equals("NA"))
            {
                SOStoreVisitStartTime=fnGenerateDateTime();
            }
            myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationUpdateService.MY_ACTION);
            registerReceiver(myReceiver, intentFilter);
            startService(new Intent(this, LocationUpdateService.class));
            mIsServiceStarted = true;

        }
        else
        {


            ArrayList<String> arrBasisDetailsAgainstStore=    helperDb.fnGetDetails_tblStoreDeatilsSO(selStoreID,StoreName);


            allValuesOfPaymentStageID =  arrBasisDetailsAgainstStore.get(1);//bydefalt "0"
            AddressFromLauncher = arrBasisDetailsAgainstStore.get(2);////bydefalt "NA"
            CityFromLauncher =  arrBasisDetailsAgainstStore.get(3);
            PincodeFromLauncher =  arrBasisDetailsAgainstStore.get(4);
            StateFromLauncher =  arrBasisDetailsAgainstStore.get(5);

            LattitudeFromLauncher = arrBasisDetailsAgainstStore.get(6);
            LongitudeFromLauncher = arrBasisDetailsAgainstStore.get(7);
            AccuracyFromLauncher = arrBasisDetailsAgainstStore.get(8);
            SOLattitudeFromLauncher=arrBasisDetailsAgainstStore.get(9);
            SOLongitudeFromLauncher=arrBasisDetailsAgainstStore.get(10);
            IsStoreDataCompleteSaved=Integer.parseInt(arrBasisDetailsAgainstStore.get(11));

            SOStoreVisitStartTime=arrBasisDetailsAgainstStore.get(12);
            SOStoreVisitEndTime=arrBasisDetailsAgainstStore.get(13);

            flgOldNewStore=Integer.parseInt(arrBasisDetailsAgainstStore.get(14));
            flgSelfStore=Integer.parseInt(arrBasisDetailsAgainstStore.get(15));
            //  SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            //SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
           /* if(SOLattitudeFromLauncher.equals("NA"))
            {
                if(!allLoctionDetails.equals("0"))
                {
                    SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
                    SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
                }

            }*/
            if(SOStoreVisitStartTime.equals("NA"))
            {


                String Time = fnGenerateDateTime();


                helperDb.fnVisitStartOrEndTime(selStoreID,Time,0);


                VisitStartTimeOfNewStore=Time;
                SOStoreVisitStartTime=VisitStartTimeOfNewStore;


                if(CommonInfo.flgLTFoodsSOOnlineOffLine==1) {
                    if (IsStoreDataCompleteSaved == 0) {
                        myReceiver = new MyReceiver();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction(LocationUpdateService.MY_ACTION);
                        registerReceiver(myReceiver, intentFilter);
                        startService(new Intent(this, LocationUpdateService.class));
                        mIsServiceStarted = true;
                    }
                }

            }



            if(SOStoreVisitStartTime.equals("NA"))
            {


                String Time =fnGenerateDateTime();


                helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);
            }

        }

        address=AddressFromLauncher;
        pincode=PincodeFromLauncher;
        city=CityFromLauncher;
        state=StateFromLauncher;
        latitudeToSave=SOLattitudeFromLauncher;
        longitudeToSave=SOLongitudeFromLauncher;
        accuracyToSave=AccuracyFromLauncher;

		/*  PackageManager m = getPackageManager();
		    if (m.hasSystemFeature(PackageManager.FEATURE_LOCATION)) {

		    	m.setApplicationEnabledSetting(PackageManager.FEATURE_LOCATION,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
		    }*/
        mResultReceiver = new AddressResultReceiver(new Handler());
        //   ll_allData=(LinearLayout) findViewById(R.id.ll_allData);

        ll_back=(LinearLayout) findViewById(R.id.ll_back);
        ll_next=(LinearLayout) findViewById(R.id.ll_next);

        ll_save_Exit=(LinearLayout) findViewById(R.id.ll_save_Exit);


        ll_revisit=(LinearLayout) findViewById(R.id.ll_revisit);

        ll_reject=(LinearLayout) findViewById(R.id.ll_reject);


        img_next=(ImageView) findViewById(R.id.img_next);
        img_exit=(ImageView) findViewById(R.id.img_exit);



        txt_Next=(TextView) findViewById(R.id.txt_Next);
        ll_map=(LinearLayout) findViewById(R.id.ll_map);
        rg_yes_no= (RadioGroup) findViewById(R.id.rg_yes_no);
        TextView tv_MapLocationCorrectText=(TextView) findViewById(R.id.tv_MapLocationCorrectText);
        ll_refresh= (LinearLayout) findViewById(R.id.ll_refresh);

        btn_refresh= (Button) findViewById(R.id.btn_refresh);
        txt_rfrshCmnt= (TextView) findViewById(R.id.txt_rfrshCmnt);


        if(CommonInfo.flgLTFoodsSOOnlineOffLine==0)


        {
            rg_yes_no.setVisibility(View.VISIBLE);
            tv_MapLocationCorrectText.setVisibility(View.VISIBLE);
            ll_refresh.setVisibility(View.VISIBLE);
            btn_refresh.setVisibility(View.VISIBLE);
            txt_rfrshCmnt.setVisibility(View.VISIBLE);

        }

        else
        {
            rg_yes_no.setVisibility(View.GONE);
            tv_MapLocationCorrectText.setVisibility(View.GONE);
            ll_refresh.setVisibility(View.GONE);
            btn_refresh.setVisibility(View.GONE);
            txt_rfrshCmnt.setVisibility(View.GONE);

        }
        rb_yes= (RadioButton) findViewById(R.id.rb_yes);
        rb_no=(RadioButton)findViewById(R.id.rb_no);


        //  rl_sectionQuest=(RelativeLayout) findViewById(R.id.rl_sectionQuest);


        fillHmapData();
        addFragment();
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
        btn_refresh.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


              /*  if (mIsServiceStarted)
                {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
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

                    manager= getFragmentManager();
                    mapFrag = (MapFragment)manager.findFragmentById(
                            R.id.map);
                    // mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
                    mapFrag.getView().setVisibility(View.GONE);
                   /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.hide(mapFrag);*/
                    locationRetrievingAndDistanceCalculating();
                }


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
                rg_yes_no.clearCheck();
                ll_refresh.setVisibility(View.GONE);

            }
        });

        img_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
                getSectionNextOrBack(4, sectionToShowHide);

                String Time =fnGenerateDateTime();
                helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);

                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;

            }
        });

        ll_save_Exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                getSectionNextOrBack(2, sectionToShowHide);
                NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;

                String Time =fnGenerateDateTime();
                helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);


               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/

                IsStoreDataCompleteSaved=1;
                flgApproveOrRejectOrNoActionOrReVisit=0;



            }
        });
      /*  btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showSubmitConfirm();

            }
        });*/

        ll_reject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(recFragment!=null)
                {
                    StoreName=recFragment.currentStoreName;
                    if(recFragment.validate())// && recFragment.validatePaymentStageID()
                    {
                        IsStoreDataCompleteSaved=2;
                        flgApproveOrRejectOrNoActionOrReVisit=2;
                        showSubmitConfirm();
                    }
                }
            }
        });


        ll_revisit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(recFragment!=null)
                {
                    StoreName=recFragment.currentStoreName;
                    // if(recFragment.validate() && recFragment.validatePaymentStageID())
                    if(recFragment.validate())
                    {
                        IsStoreDataCompleteSaved=2;
                        flgApproveOrRejectOrNoActionOrReVisit=3;
                        showSubmitConfirm();
                    }
                }
            }
        });



        ll_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                hideSoftKeyboard(v);


               /* if(ll_reject.getVisibility()==View.VISIBLE)
                {
                    ll_reject.setVisibility(View.GONE);
                }

                if(ll_revisit.getVisibility()==View.VISIBLE)
                {
                    ll_revisit.setVisibility(View.GONE);
                }

                if(ll_map.getVisibility()==View.VISIBLE)
                {
                    ll_map.setVisibility(View.GONE);
                }
*/

                String Time =fnGenerateDateTime();
                helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);
                if(sectionToShowHide<hmapSctnId_GrpId.size()-1)
                {
                    boolean isNextMoved=getSectionNextOrBack(0,sectionToShowHide );
                    if(isNextMoved)
                    {
                        if(ll_reject.getVisibility()==View.VISIBLE)
                        {
                            ll_reject.setVisibility(View.GONE);
                        }

                        if(ll_revisit.getVisibility()==View.VISIBLE)
                        {
                            ll_revisit.setVisibility(View.GONE);
                        }

                        if(ll_map.getVisibility()==View.VISIBLE)
                        {
                            ll_map.setVisibility(View.GONE);
                        }

                        sectionToShowHide++;
                        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        StoreName=recFragment.currentStoreName;
                        if(sectionToShowHide!=1 )
                        {
                            if(ll_back.getVisibility()==View.INVISIBLE)
                            {
                                ll_back.setVisibility(View.VISIBLE);
                            }


                        }
                    }

                }
                else if(sectionToShowHide>hmapSctnId_GrpId.size()-1)
                {

                    //NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                    NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                    if(recFragment!=null)
                    {
                        StoreName=recFragment.currentStoreName;
                        if(recFragment.validate())// && recFragment.validatePaymentStageID()
                        {

                            IsStoreDataCompleteSaved=2;
                            if(flgSelfStore==1) {
                                flgApproveOrRejectOrNoActionOrReVisit=0;
                            }
                            else {
                                flgApproveOrRejectOrNoActionOrReVisit = 1;
                            }
                            showSubmitConfirm();
                        }
                    }

                 /*   if(recFragment.validate() && recFragment.validatePaymentStageID())
                    {

                       // getSectionNextOrBack(5,sectionToShowHide );


                    }*/




                }

                else
                {
                    boolean isNextMoved=getSectionNextOrBack(0,sectionToShowHide );
                    if(isNextMoved)
                    {
                        if(ll_map.getVisibility()==View.VISIBLE)
                        {
                            ll_map.setVisibility(View.GONE);
                        }
                        if(sectionToShowHide==hmapSctnId_GrpId.size()-1)
                        {
                            if(flgSelfStore==1) {

                            }
                            else if(flgSelfStore==0) {

                                if(flgOldNewStore==0) {
                                    if (ll_reject.getVisibility() == View.GONE) {
                                        ll_reject.setVisibility(View.VISIBLE);
                                    }

                                    if (ll_revisit.getVisibility() == View.GONE) {
                                        ll_revisit.setVisibility(View.VISIBLE);
                                    }
                                    img_next.setImageResource(R.drawable.done);
                                    txt_Next.setText("Approve");
                                    sectionToShowHide++;
                                }
                                else
                                {
                                    img_next.setImageResource(R.drawable.done);
                                    txt_Next.setText("Done");
                                    sectionToShowHide++;
                                }
                            }

                        }
                        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        StoreName=recFragment.currentStoreName;
                        if(ll_back.getVisibility()==View.INVISIBLE)
                        {

                            ll_back.setVisibility(View.VISIBLE);
                        }


                    }
                }


            }
        });

        ll_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sectionToShowHide--;
                getSectionNextOrBack(1,sectionToShowHide );
                NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName=recFragment.currentStoreName;

                if(ll_reject.getVisibility()==View.VISIBLE)
                {
                    ll_reject.setVisibility(View.GONE);
                }

                if(ll_revisit.getVisibility()==View.VISIBLE)
                {
                    ll_revisit.setVisibility(View.GONE);
                }


                if(sectionToShowHide==1)
                {
                    if(ll_back.getVisibility()==View.VISIBLE)
                    {
                        ll_back.setVisibility(View.INVISIBLE);
                        if(ll_map.getVisibility()==View.GONE)
                        {
                            ll_map.setVisibility(View.VISIBLE);
                        }
                    }

                }
                if(sectionToShowHide<hmapSctnId_GrpId.size())
                {
                    img_next.setImageResource(R.drawable.next);
                    txt_Next.setText(getResources().getString(R.string.txtNext));
                }
                String Time =fnGenerateDateTime();
                helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);
            }
        });
        ll_map.setVisibility(View.VISIBLE);
        manager= getFragmentManager();
        mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
        mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);

    }
    private void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };


    private boolean getSectionNextOrBack(int isNextPressed, int sectionToShowOrHide)
    {
        boolean isNextMoved=false;
        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            isNextMoved=recFragment.nextOrBackSection(isNextPressed,sectionToShowOrHide);
        }
        return isNextMoved;
    }
    private void addFragment() {
        newStore_Fragment=new NewStoreForm();
        manager=getFragmentManager();
        fragTrans=manager.beginTransaction();
        fragTrans.replace(R.id.fragmentForm, newStore_Fragment,"NewStoreFragment");

        fragTrans.commit();

    }
    private void fillHmapData() {
        //QuestID,QuestCode,QuestDesc,QuestType,AnsControlType,AnsControlInputTypeID,AnsControlInputTypeMaxLength,AnsMustRequiredFlg,QuestBundleFlg,ApplicationTypeID,Sequence,AnsControlInputTypeMinLength,AnsHint,QuestBundleGroupId
        //hmapQuesIdValues.put("1^2", "1^")

        hmapQuesIdValues=helperDb.fnGetQuestionMstr(StoreSectionCount);
        hmapQuesGropKeySection=helperDb.fnGetQuestionMstrKey();
        hmapGroupId_Desc=helperDb.getGroupDescription();
        hmapSctnId_GrpId=helperDb.fnGetGroupIdMpdWdSectionId(StoreSectionCount);
        hmapDpndtQustGrpId=helperDb.fnGetDependentQuestionMstr();
        hmapSection_key=helperDb.fnGetSection_Key();
        //   hmapOptionId_OptionValue=helperDb.fnGetOptionId_OptionValue();
        QuestIDForOutChannel=helperDb.fnGetQuestIDForOutChannelFromQuestionMstr();

    }

    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.genTermInformation));
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogNoConn.setTitle(getResources().getString(R.string.txtErrorNoDataConnection));
        alertDialogNoConn.setMessage(getResources().getString(R.string.txtErrorInternetConnection));
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /*if(isMyServiceRunning())
                  		{
                        stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
                  		}
                        finish();*/
                        //finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
        // alertDialogLowbatt.show();
    }

    private void showSubmitConfirm()
    {

        AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogSubmitConfirm.setTitle(getResources().getString(R.string.genTermInformation));
        alertDialogSubmitConfirm.setMessage(getResources().getString(R.string.SubmitStrInfo));
        alertDialogSubmitConfirm.setCancelable(false);

        alertDialogSubmitConfirm.setNeutralButton(getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                sectionToShowHide--;
							/*if(isOnline())
							{*/
                dialog.dismiss();
                //submitButtonFunctionality();

			        		/*}
							else
							{dialog.dismiss();
			        			showNoConnAlert();
			        		}*/

                SubmitData submitData = new SubmitData();
                AppUtils.executeAsyncTask(submitData);







              //  helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                 //       Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,hmapStoreAddress.get("0"),allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation,flgGSTCapture,flgGSTCompliance,GSTNumber,flgGSTRecordFromServer,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart,flgStoreOrder, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), hmapStoreAddress.get("4"), hmapStoreAddress.get("5"), hmapStoreAddress.get("6"), hmapStoreAddress.get("7"), hmapStoreAddress.get("8"), hmapStoreAddress.get("9"));
                //helperDb.close();
             /*   if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/

                // alertSubmit();

										  /*  Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
											ide.putExtra("userDate", pickerDate);
											ide.putExtra("pickerDate", pickerDate);
											ide.putExtra("imei", imei);
											ide.putExtra("rID", rID);
											AddNewStore_DynamicSectionWise.this.startActivity(ide);
											finish();*/
                // finish();

                /*  if (isOnline()) {
                 *//*getSectionNextOrBack(5,sectionToShowHide );*//*
                 *//*
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }

                    timer = new Timer();
                    myTimerTask = new MyTimerTask();
                    timer.schedule(myTimerTask, 45000);

                    try {

                      *//**//*  task = new GetAddingStoreInfo(AddNewStore_DynamicSectionWise.this);
                        task.execute();*//**//*
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*//*
                } else {
                    // getSectionNextOrBack(3,sectionToShowHide );


                }*/


            }
        });

        alertDialogSubmitConfirm.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
					/*//helperDb.open();
					//helperDb.deleteStoreFormtblStoreMain(StoreID);
					//helperDb.close();*/
            }
        });

        alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSubmitConfirm.create();

        alert.show();

    }
    class SubmitData extends AsyncTask<String, String, String> {

        KProgressHUD hud;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hud = KProgressHUD.create(AddNewStore_DynamicSectionWise.this)
                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();
            hud.setProgress(90);
        }

        @Override
        protected String doInBackground(String... strings) {

            if (ProviderFromLauncher.equals("Fused")) {
                fnAddressFromLauncher = FusedAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Gps")) {
                fnAddressFromLauncher = GpsAddressFromLauncher;
            } else if (ProviderFromLauncher.equals("Network")) {
                fnAddressFromLauncher = NetwAddressFromLauncher;
            }


            helperDb.saveLatLngToTxtFile(selStoreID,"NA",LattitudeFromLauncher, LongitudeFromLauncher, AccuracyFromLauncher, ProviderFromLauncher, GpsLatFromLauncher, GpsLongFromLauncher, GpsAccuracyFromLauncher, NetworkLatFromLauncher, NetworkLongFromLauncher, NetworkAccuracyFromLauncher, FusedLatFromLauncher, FusedLongFromLauncher, FusedAccuracyFromLauncher, 3, "0", fnAddressFromLauncher, AllProvidersLocationFromLauncher, GpsAddressFromLauncher, NetwAddressFromLauncher, FusedAddressFromLauncher, FusedLocationLatitudeWithFirstAttemptFromLauncher
                    , FusedLocationLongitudeWithFirstAttemptFromLauncher, FusedLocationAccuracyWithFirstAttemptFromLauncher);
            String lastprvsStoreId = helperDb.PrvsStoreMsgShownAndRestrtDone();
            if (!TextUtils.isEmpty(lastprvsStoreId.trim())) {
                helperDb.updateCurrentStoreId(selStoreID, lastprvsStoreId);
            }
            long syncTIMESTAMP = System.currentTimeMillis();
            Date datefromat = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String VisitEndFinalTS = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_TIME_FORMAT);

            helperDb.insertRestartStoreInfo(selStoreID, selStoreID, "2", "0", "0", 3, VisitEndFinalTS);


            try {

                addStoreOffline();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (hud != null && hud.isShowing())
                hud.dismiss();

            String presentRoute = helperDb.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

            String VisitDate = TimeUtils.getNetworkDateTime(AddNewStore_DynamicSectionWise.this, TimeUtils.DATE_FORMAT);

            Intent mMyServiceIntent = new Intent(AddNewStore_DynamicSectionWise.this, SyncJobService.class);  //is any of that needed?  idk.
            mMyServiceIntent.putExtra("whereTo", "Regular");//
            mMyServiceIntent.putExtra("storeID", selStoreID);
            mMyServiceIntent.putExtra("routeID", presentRoute);//

            SyncJobService.enqueueWork(AddNewStore_DynamicSectionWise.this, mMyServiceIntent);
alertSubmit();
            /*int flgOrderOrOnlyAdd=0;
            String StoreVisitCode="NA";
            if (activityFrom.equals("StoreSelection")) {
                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);

                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);

                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                } else {
                    Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    ide.putExtra("userDate", date_value);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("imei", imei);
                    ide.putExtra("rID", rID);
                    AddNewStore_DynamicSectionWise.this.startActivity(ide);
                    finish();
                }


            } else if (activityFrom.equals("AllButtonActivity")) {

               *//* Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                intent.putExtra("activityFrom", "AllButtonActivity");
                startActivity(intent);
                finish();*//*

                if (flgOrderOrOnlyAdd == 1) {
                    if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                        if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            startActivity(nxtP4);
                            finish();

                        } else {

                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else {
                        Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                        nxtP4.putExtra("storeID", selStoreID);
                        nxtP4.putExtra("SN", StoreName);
                        nxtP4.putExtra("imei", imei);
                        nxtP4.putExtra("userdate", VisitDate);
                        nxtP4.putExtra("pickerDate", VisitDate);
                        nxtP4.putExtra("flgOrderType", 1);
                        startActivity(nxtP4);
                        finish();
                    }
                } else if (flgOrderOrOnlyAdd == 2) {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                    intent.putExtra("activityFrom", "AllButtonActivity");
                    startActivity(intent);
                    finish();
                }

            } else {
                {
                    if (flgOrderOrOnlyAdd == 1) {
                        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ActualVisitStock.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                startActivity(nxtP4);
                                finish();

                            } else {

                                Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                                //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                                nxtP4.putExtra("storeID", selStoreID);
                                nxtP4.putExtra("SN", StoreName);
                                nxtP4.putExtra("imei", imei);
                                nxtP4.putExtra("userdate", VisitDate);
                                nxtP4.putExtra("pickerDate", VisitDate);
                                nxtP4.putExtra("flgOrderType", 1);
                                startActivity(nxtP4);
                                finish();
                            }
                        } else {
                            Intent nxtP4 = new Intent(AddNewStore_DynamicSectionWise.this, ProductEntryForm.class);
                            //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
                            nxtP4.putExtra("storeID", selStoreID);
                            nxtP4.putExtra("SN", StoreName);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", VisitDate);
                            nxtP4.putExtra("pickerDate", VisitDate);
                            nxtP4.putExtra("flgOrderType", 1);
                            startActivity(nxtP4);
                            finish();
                        }
                    } else if (flgOrderOrOnlyAdd == 2) {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    } else {
                        Intent ide = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        ide.putExtra("userDate", date_value);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("imei", imei);
                        ide.putExtra("rID", rID);
                        AddNewStore_DynamicSectionWise.this.startActivity(ide);
                        finish();
                    }

               *//* Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
                ide.putExtra("userDate", date_value);
                ide.putExtra("pickerDate", pickerDate);
                ide.putExtra("imei", imei);
                ide.putExtra("rID", rID);
                AddNewStore_DynamicSectionWise.this.startActivity(ide);
                finish();*//*

                }*/
           // }
         /*   if(isOnline())
            {
                Intent syncIntent = new Intent(AddNewStore_DynamicSectionWise.this, SyncMasterSO.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("activityFrom", activityFrom);
                syncIntent.putExtra("whereTo", "Regular");
                startActivity(syncIntent);
                finish();
            }
            else {*/

               /* if(activityFrom.equals("StoreSelection"))
                {
                    Date date1 = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String fDate = sdf.format(date1).trim();
                    Intent storeIntent = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDate);
                    startActivity(storeIntent);
                    finish();
                }
                else
                {
                    if(CommonInfo.flgNewStoreORStoreValidation==1)
                    {
                        Intent submitStoreIntent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                        startActivity(submitStoreIntent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                        intent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                        startActivity(intent);
                        finish();
                    }
                }
*/
           // }
        }
    }

    private String fnGenerateDateTime()
    {


        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);

        return df.format(datefromat);

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

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(FLAG_NEW_UPDATE.equals("NEW"))
        {
            if(!SOLattitudeFromLauncher.equals("NA") && !SOLattitudeFromLauncher.equals("0.0"))
            {
                googleMap.clear();
                try {
                    googleMap.setMyLocationEnabled(false);
                }
                catch (SecurityException e)
                {

                }


                MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                //markerSO.title("Your Point");
                Marker SOlocationMarker=googleMap.addMarker(markerSO);
                SOlocationMarker.showInfoWindow();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));


               /* MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                marker.title(StoreName +": By DSR");

                Marker locationMarker=googleMap.addMarker(marker);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                */
               /* if(CommonInfo.flgLTFoodsSOOnlineOffLine==1) {
                    if(!SOLattitudeFromLauncher.equals("NA"))
                    {
                      *//*  MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                        markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerSO.title("Your Point");
                        Marker SOlocationMarker=googleMap.addMarker(markerSO);
                        SOlocationMarker.showInfoWindow();

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));

*//*

                    }
                }
*/



            }

            else
            {
                if(refreshCount==2)
                {
                    txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                    btn_refresh.setVisibility(View.GONE);
                }
                try {
                    googleMap.setMyLocationEnabled(true);
                } catch (SecurityException e) {

                }
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker markerSO) {

                        markerSO.setTitle(StoreName);
                    }
                });

            }
        }
        else
        {
            if(!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0"))
            {
                googleMap.clear();
                try {
                    googleMap.setMyLocationEnabled(false);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                marker.title(StoreName +": By DSR");

                Marker locationMarker=googleMap.addMarker(marker);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));

                if(CommonInfo.flgLTFoodsSOOnlineOffLine==1) {
                    if(!SOLattitudeFromLauncher.equals("NA"))
                    {
                        MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                        markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerSO.title("Your Point");
                        Marker SOlocationMarker=googleMap.addMarker(markerSO);
                        SOlocationMarker.showInfoWindow();

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));



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
                try {
                    googleMap.setMyLocationEnabled(true);
                } catch (SecurityException e) {

                }
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        marker.setTitle(StoreName);
                    }
                });

            }
        }
        belowHundredMeterStoreOnMap(googleMap);

    }

    private void belowHundredMeterStoreOnMap(GoogleMap googleMap)
    {

        hmapStoresBelowHundredMeter=helperDb.fnGeStoreListAllBelowHundredMeter();
        if(hmapStoresBelowHundredMeter!=null) {

            for (Map.Entry<String, String> entry : hmapStoresBelowHundredMeter.entrySet())
            {
                String StoreID= entry.getKey().trim();
                //  String StoreName = entry.getValue().toString().trim();
                String StoreName = entry.getValue().split(Pattern.quote("~"))[0];
                String LatCode = entry.getValue().split(Pattern.quote("~"))[1];
                String LongCode = entry.getValue().split(Pattern.quote("~"))[2];
                MarkerOptions marker1 = new MarkerOptions().position(new LatLng(Double.parseDouble(LatCode), Double.parseDouble(LongCode)));
                marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                marker1.title(StoreName);
                googleMap.addMarker(marker1);

            }
        }
    }


    private boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void showAlertForEveryOne(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this);
        alertDialogNoConn.setTitle(getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // finish();
            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }


    /*private class GetAddingStoreInfo extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker getRouteservice = new ServiceWorker();


        public GetAddingStoreInfo(AddNewStore_DynamicSectionWise activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            VisitEndTS = df.format(dateobj);

            System.out.println("Im  Testing H2");


            pDialogGetStores.setTitle("Please Wait");
            pDialogGetStores.setMessage("");
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            try
            {
                for(int mm = 1; mm<2; mm++)
                {
                    if(mm==1)
                    {


                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date datefromat = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                        String VisitStartTS=df.format(datefromat);

                        int ApplicationID=CommonInfo.Application_TypeID;
										   *//*helperDb.inserttblOutletMstr(OutletID,VisitStartTS,String.valueOf(lastKnownLocLatitude),
												String.valueOf(lastKnownLocLongitude),"" + accuracy,locationProvider,battLevel + "%",StoreName,
												imei,0,3,ApplicationID);*//*

                        String NewStoreOutletPaymentDetails=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                        JSONArray ArrOutletPaymentDetails = new JSONArray();
                        JSONObject OutletPaymentDetails = new JSONObject();
                        OutletPaymentDetails.put("PymtStageId", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[0]);
                        OutletPaymentDetails.put("Percentage", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[1]);
                        OutletPaymentDetails.put("CreditDays", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[2]);
                        OutletPaymentDetails.put("CreditLimit", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[3]);
                        OutletPaymentDetails.put("PymtMode", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[4]);
                        ArrOutletPaymentDetails.put(OutletPaymentDetails);







                        JSONArray ArrOutletGeneralInfoTable = new JSONArray();
                        JSONObject OutletInfo = new JSONObject();

                        OutletInfo.put("OutletID", selStoreID.trim());
                        OutletInfo.put("VisitStartTS", VisitStartTS.trim());
                        OutletInfo.put("VisitEndTS", VisitStartTS.trim());
                        OutletInfo.put("AppVersion", ApplicationID);
                        OutletInfo.put("ActualLatitude", String.valueOf(fnLati));
                        OutletInfo.put("ActualLongitude", String.valueOf(fnLongi));


                        OutletInfo.put("LocProvider", fnAccurateProvider);
                        OutletInfo.put("Accuracy", fnAccuracy);
                        OutletInfo.put("BateryLeftStatus", battLevel);
                        OutletInfo.put("StoreName", StoreName.trim());
                        OutletInfo.put("imei", imei.trim());
                        OutletInfo.put("ISNewStore", 1);
                        OutletInfo.put("IsNewStoreDataCompleteSaved", 0);

                        OutletInfo.put("Sstat", 0);
                        OutletInfo.put("StoreMapAddress", fetchAddress);


                        ArrOutletGeneralInfoTable.put(OutletInfo);

                        JSONArray ArrAnsAndQuestionTable = new JSONArray();

                        LinkedHashMap<String, String> hmapStoreQuestAnsNew=new LinkedHashMap<String, String>();


                        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        if(null != recFragment)
                        {
                            hmapStoreQuestAnsNew=recFragment.hmapAnsValues;

                        }

                        try
                        {
                            if(hmapStoreQuestAnsNew.size()>0)
                            {
                                for(Entry<String, String> entry:hmapStoreQuestAnsNew.entrySet())
                                {
                                    JSONObject post_dict = new JSONObject();
                                    String keyhmap=entry.getKey();
                                    String valuehmap=entry.getValue();
                                    post_dict.put("OutletID" , selStoreID);

											        	 *//*if(Integer.parseInt(outletsDetailValue.get(1))==15)
											        	 {
											        		 StoreTypeTradeChannel= helperDb.fnGetAnsValFromOptionID(Integer.parseInt(outletsDetailValue.get(3)));//Integer.parseInt(outletsDetailValue.get(3));
											        	 }*//*
                                    post_dict.put("QuestionId" , keyhmap.split(Pattern.quote("^"))[0]);
                                    post_dict.put("AnsCtronlType", keyhmap.split(Pattern.quote("^"))[1]);
                                    post_dict.put("QuestGroupId", keyhmap.split(Pattern.quote("^"))[2]);
                                    post_dict.put("Value", valuehmap);

                                    *//*String optionValue="0";
                                    if(valuehmap.trim().indexOf("^")!=-1)
                                    {
                                        String[] diffrentOptionIdSelected=valuehmap.trim().split(Pattern.quote("^"));
                                        for(int j=0;j<diffrentOptionIdSelected.length;j++)
                                        {
                                            String curntOptionID=diffrentOptionIdSelected[j];
                                            String filterOptID="0";
                                            if(curntOptionID.trim().indexOf("~")!=-1)
                                            {
                                                filterOptID=curntOptionID.trim().split(Pattern.quote("~"))[0];
                                            }
                                            else
                                            {
                                                filterOptID=curntOptionID;
                                            }

                                            if(hmapOptionId_OptionValue.containsKey(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID))
                                            {
                                                if(j==0)
                                                {
                                                    optionValue=hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID);
                                                }
                                                else
                                                {
                                                    optionValue=optionValue+"^"+hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID);
                                                }
                                            }

                                        }
                                    }
                                    else
                                    {
                                        if(hmapOptionId_OptionValue.containsKey(keyhmap.split(Pattern.quote("^"))[0]+"_"+valuehmap.trim()))
                                        {
                                            optionValue=hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+valuehmap.trim());
                                        }
                                    }

                                    post_dict.put("optionValue", optionValue);*//*

                                    ArrAnsAndQuestionTable.put(post_dict);
                                }
												  *//*for(int i=0;i<hmapStoreQuestAnsNew.size();i++)
											         {
											        	 ArrayList<String> outletsDetailValue=hmapStoreQuestAnsNew.get(i);
											        	 JSONObject post_dict = new JSONObject();

													        	 if(i==0)
													        	 {
											        		     post_dict.put("OutletID" , outletsDetailValue.get(0));
											        	         }
													        	 //StoreType
													        	 if(Integer.parseInt(outletsDetailValue.get(1))==15)
													        	 {
													        		 StoreTypeTradeChannel= helperDb.fnGetAnsValFromOptionID(Integer.parseInt(outletsDetailValue.get(3)));//Integer.parseInt(outletsDetailValue.get(3));
													        	 }
											        		     post_dict.put("QuestionId" , outletsDetailValue.get(1));
													             post_dict.put("AnsCtronlType", outletsDetailValue.get(2));
													             post_dict.put("Value", outletsDetailValue.get(3));
													             ArrAnsAndQuestionTable.put(post_dict);


											         }*//*
                            }

                            // ArrAnsAndQuestionTable.put(ArrOutletGeneralInfoTable);
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                       // getRouteservice = getRouteservice.getNewStoreInfoDynamic(getApplicationContext(),ArrAnsAndQuestionTable,ArrOutletGeneralInfoTable,imei,ArrOutletPaymentDetails);

                        CustomStringForServiceWorker="";
                        if(!getRouteservice.director.toString().trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                System.out.println("Im  Testing H4");
                                chkFlgForErrorToCloseApp=1;

                            }

                        }
                    }


                }


            }
            catch (Exception e)
            {

            }
            finally
            {

            }

            return null;
        }

        @Override
        protected void onCancelled()
        {

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);


            System.out.println("Im  Testing H5 : "+chkFlgForErrorToCloseApp);

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(chkFlgForErrorToCloseApp==1)
            {
                chkFlgForErrorToCloseApp=0;


                // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                if (timer!=null)
                {
                    timer.cancel();
                    timer = null;
                }
                getSectionNextOrBack(3,sectionToShowHide );
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                String VisitStartTS=df.format(datefromat);
                int ApplicationID=CommonInfo.Application_TypeID;
							   *//*if(selStoreID.equals("0"))
							   {*//*

                helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                //helperDb.open();
               *//* helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                        Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                //helperDb.close();
                // }


								*//*Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
								ide.putExtra("userDate", pickerDate);
								ide.putExtra("pickerDate", pickerDate);
								ide.putExtra("imei", imei);
								ide.putExtra("rID", rID);
								AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();

            }
            else
            {
						*//* if(selStoreID=="0")
							 {*//*
                if(isOnline())

                {
                    try
                    {
                        checkSecondTaskStatus=1;
                        taskUpdateScheme = new GetUpdateSchemeForNewStore(AddNewStore_DynamicSectionWise.this);

                        taskUpdateScheme.execute();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {

                    //helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                    getSectionNextOrBack(3,sectionToShowHide );
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date datefromat = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                    String VisitStartTS=df.format(datefromat);

                    int ApplicationID=CommonInfo.Application_TypeID;
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    //helperDb.open();
                   *//* helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                    helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                            Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                    //helperDb.close();
								*//*
									 Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
								ide.putExtra("userDate", pickerDate);
								ide.putExtra("pickerDate", pickerDate);
								ide.putExtra("imei", imei);
								ide.putExtra("rID", rID);
								AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                    finish();

                }


							*//* }

						 else
						 {

							 Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
						ide.putExtra("userDate", pickerDate);
						ide.putExtra("pickerDate", pickerDate);
						ide.putExtra("imei", imei);
						ide.putExtra("rID", rID);
						AddNewStore_DynamicSectionWise.this.startActivity(ide);
						finish();

						 }*//*

            }




        }
    }*/


   /* private class GetUpdateSchemeForNewStore extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker newservice = new ServiceWorker();


        public GetUpdateSchemeForNewStore(AddNewStore_DynamicSectionWise activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle("Please Wait");
            pDialogGetStores.setMessage("");
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                for(int mm = 1; mm<2; mm++)
                {
                    if(mm==1)
                    {

                        Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();

                      //  newservice = newservice.getAllNewSchemeStructure(getApplicationContext(), currSysDateNew, imei, rID);
                        if(!newservice.director.toString().trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                chkFlgForErrorToCloseApp=1;

                            }

                        }
                    }
									*//*if(mm==2)
									{

										Date currDateNew = new Date();
										SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

										String currSysDateNew = currDateFormatNew.format(currDateNew).toString();

										newservice = newservice.fnGetStoreListWithPaymentAddressMR(getApplicationContext(), currSysDateNew, imei, rID);
										if(!newservice.director.toString().trim().equals("1"))
										{
											if(chkFlgForErrorToCloseApp==0)
											{
												chkFlgForErrorToCloseApp=1;

											}

										}
									}*//*
                }


            }
            catch (Exception e)
            {

            }
            finally
            {

            }

            return null;
        }

        @Override
        protected void onCancelled()
        {

        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (timer!=null)
            {
                timer.cancel();
                timer = null;
            }

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(chkFlgForErrorToCloseApp==1)
            {
                chkFlgForErrorToCloseApp=0;

                if(ServiceWorkerDataComingFlag==0)
                {
                    // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                    getSectionNextOrBack(3,sectionToShowHide );
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date datefromat = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                    String VisitStartTS=df.format(datefromat);
                    // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
                    int ApplicationID=CommonInfo.Application_TypeID;
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    //helperDb.open();
                *//* //   helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                  //  helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                            Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                    //helperDb.close();
                }
                else
                {
                    StringTokenizer tokens = new StringTokenizer(String.valueOf(ServiceWorkerResultSet), "^");

                    String StoreName= tokens.nextToken().toString().trim();
                    String StoreType= tokens.nextToken().toString().trim();
                    String StoreLatitude= tokens.nextToken().toString().trim();
                    String StoreLongitude= tokens.nextToken().toString().trim();
                    String LastVisitDate= tokens.nextToken().toString().trim();
                    String LastTransactionDate= tokens.nextToken().toString().trim();
                    String dateVAL= tokens.nextToken().toString().trim();
                    String AutoIdStore= tokens.nextToken().toString().trim();
                    String Sstat= tokens.nextToken().toString().trim();
                    String IsClose= tokens.nextToken().toString().trim();
                    String IsNextDat= tokens.nextToken().toString().trim();
                    String RouteID= tokens.nextToken().toString().trim();
                    int flgHasQuoteNew= Integer.parseInt(tokens.nextToken().toString().trim());
                    int flgAllowQuotationNew= Integer.parseInt(tokens.nextToken().toString().trim());
                    int flgSubmitFromQuotatioNew=Integer.parseInt(tokens.nextToken().toString().trim());
                    helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    //helperDb.open();
                   *//* helperDb.fndeleteNewStoreSalesQuotePaymentDetails(selStoreID);
                    helperDb.saveSOAPdataStoreList(ServiceWorkerStoreID,StoreName,StoreType,Double.parseDouble(StoreLatitude),Double.parseDouble(StoreLongitude),LastVisitDate,LastTransactionDate,
                            dateVAL.toString().trim(),Integer.parseInt(AutoIdStore), Integer.parseInt(Sstat),Integer.parseInt(IsClose),Integer.parseInt(IsNextDat),Integer.parseInt(RouteID),StoreTypeTradeChannel,fetchAddress,allValuesOfPaymentStageID,flgHasQuoteNew,flgAllowQuotationNew,flgSubmitFromQuotatioNew);
                   *//*

                    //helperDb.close();
                }




						   *//* Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
							ide.putExtra("userDate", pickerDate);
							ide.putExtra("pickerDate", pickerDate);
							ide.putExtra("imei", imei);
							ide.putExtra("rID", rID);
							AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();



            }
            else
            { if(ServiceWorkerDataComingFlag==0)
            {
                // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                getSectionNextOrBack(3,sectionToShowHide );
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                String VisitStartTS=df.format(datefromat);
                // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
                int ApplicationID=CommonInfo.Application_TypeID;
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                //helperDb.open();
              *//* // helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
               // helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                        Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                //helperDb.close();
            }
            else
            {
                StringTokenizer tokens = new StringTokenizer(String.valueOf(ServiceWorkerResultSet), "^");

                String StoreName= tokens.nextToken().toString().trim();
                String StoreType= tokens.nextToken().toString().trim();
                String StoreLatitude= tokens.nextToken().toString().trim();
                String StoreLongitude= tokens.nextToken().toString().trim();
                String LastVisitDate= tokens.nextToken().toString().trim();
                String LastTransactionDate= tokens.nextToken().toString().trim();
                String dateVAL= tokens.nextToken().toString().trim();
                String AutoIdStore= tokens.nextToken().toString().trim();
                String Sstat= tokens.nextToken().toString().trim();
                String IsClose= tokens.nextToken().toString().trim();
                String IsNextDat= tokens.nextToken().toString().trim();
                String RouteID= tokens.nextToken().toString().trim();
                int flgHasQuoteNew= Integer.parseInt(tokens.nextToken().toString().trim());
                int flgAllowQuotationNew= Integer.parseInt(tokens.nextToken().toString().trim());
                int flgSubmitFromQuotatioNew=Integer.parseInt(tokens.nextToken().toString().trim());
                helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                //helperDb.open();
               *//* helperDb.fndeleteNewStoreSalesQuotePaymentDetails(selStoreID);
                helperDb.saveSOAPdataStoreList(ServiceWorkerStoreID,StoreName,StoreType,Double.parseDouble(StoreLatitude),Double.parseDouble(StoreLongitude),LastVisitDate,LastTransactionDate,
                        dateVAL.toString().trim(),Integer.parseInt(AutoIdStore), Integer.parseInt(Sstat),Integer.parseInt(IsClose),Integer.parseInt(IsNextDat),Integer.parseInt(RouteID),StoreTypeTradeChannel,fetchAddress,allValuesOfPaymentStageID,flgHasQuoteNew,flgAllowQuotationNew,flgSubmitFromQuotatioNew);
               *//*

                //helperDb.close();
            }




				  *//*  Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
					ide.putExtra("userDate", pickerDate);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("imei", imei);
					ide.putExtra("rID", rID);
					AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();
            }
        }

    }*/

    /* private void initializaeMap(GoogleMap googleMap) {
 
 
         googleMap.setMyLocationEnabled(true);
         MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(StoreName);
         Marker locationMarker=googleMap.addMarker(marker);
         locationMarker.showInfoWindow();
         googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
 
         //}
                 *//*else
			    {
			     ft.hide(mapFrag);
			       txt_internetProb.setVisibility(View.VISIBLE);
			    }*//*
        //mapFrag.getView().setVisibility(View.GONE);

        ft.commit();
    }*/
    private String genTempID()
    {
        //store ID generation <x>

        String cxz;
        cxz = UUID.randomUUID().toString();
        /*cxz.split("^([^-]*,[^-]*,[^-]*,[^-]*),(.*)$");*/
        //System.out.println("cxz (BEFORE split): "+cxz);
        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        //System.out.println("cxz (AFTER split): "+cxz);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tManager.getDeviceId();
        String IMEIid =  imei.substring(9);

        cxz = IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;
        //-_
    }



    public void fetchAddress()
    {


        /*Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra("Reciever", mResultReceiver);
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        intent.putExtra("Location", location);
        startService(intent);*/
    }

    @Override
    public void selectedCityState(String selectedCategory, Dialog dialog, int flgCityState) {
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedCityState(selectedCategory,dialog,flgCityState);
        }
    }

    class AddressResultReceiver extends ResultReceiver
    {

        AddressResultReceiver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            // TODO Auto-generated method stub
            if (resultCode == 1)
            {
                final Address address = resultData.getParcelable("RESULT_ADDRESS");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        String address1 = resultData.getString("RESULT_DATA_KEY");
                        fetchAddress=address1;
                        txtAddress.setText(address1);
                    }
                });
            }
        }
    }

    @Override
    public void selectedOption(String optId, String optionVal,
                               EditText txtVw, ListView listViewOption, String tagVal,
                               Dialog dialog, TextView textView,ArrayList<String> listStoreIDOrigin) {

        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedOption( optId,  optionVal,
                    txtVw,  listViewOption,  tagVal,
                    dialog,  textView,listStoreIDOrigin);
        }

    }
    @Override
    public void selectedStoreMultiple(String optId, String optionVal,
                                      EditText txtVw, ListView listViewOption, String tagVal,
                                      Dialog dialog, TextView textView, LinearLayout ll_SlctdOpt,
                                      ArrayList<String> listSelectedOpt,
                                      ArrayList<String> listSelectedStoreID,ArrayList<String> listSelectedStoreOrigin) {

        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedStoreMultiple( optId,  optionVal,
                    txtVw,  listViewOption,  tagVal,
                    dialog,  textView,  ll_SlctdOpt,
                    listSelectedOpt,
                    listSelectedStoreID,listSelectedStoreOrigin);
        }

    }


    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

           /* intent.putExtra("Latitude", mCurrentLocation.getLatitude());
            intent.putExtra("Longitude", mCurrentLocation.getLatitude());
            intent.putExtra("Accuracy", mCurrentLocation.getAccuracy());*/
            double lat = arg1.getDoubleExtra("Latitude", 0.0);
            double lng=arg1.getDoubleExtra("Longitude", 0.0);
            float acc=arg1.getFloatExtra("Accuracy",0F);
            if(lat!=0.0)
            {

                SOLattitudeFromLauncher=String.valueOf(lat);
                SOLongitudeFromLauncher=String.valueOf(lng);
                AccuracyFromLauncher=String.valueOf(acc);
                latitudeToSave=SOLattitudeFromLauncher;
                longitudeToSave=SOLongitudeFromLauncher;
                accuracyToSave=AccuracyFromLauncher;
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(
                        R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);


            }



        }

    }


    public void alertSubmit()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(AddNewStore_DynamicSectionWise.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.DataSent));

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.DataSucc));

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                if(activityFrom.equals("StoreSelection"))
                {
                    Date date1 = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String fDate = sdf.format(date1).trim();
                    Intent storeIntent = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDate);
                    startActivity(storeIntent);
                    finish();
                }
                else
                {
                    if(CommonInfo.flgNewStoreORStoreValidation==1)
                    {
                        Intent submitStoreIntent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                        startActivity(submitStoreIntent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                        intent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                        startActivity(intent);
                        finish();
                    }
                }

               /* if(AddNewStore_DynamicSectionWise.activityFrom.equals("StoreSelection"))
                {
                    Date date1 = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String fDate = sdf.format(date1).trim();
                    Intent storeIntent = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDate);
                    startActivity(storeIntent);
                    finish();
                }
                else {
                    Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, AllButtonActivity.class);
                    intent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                    startActivity(intent);
                    finish();
                }*/

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private void locationRetrievingAndDistanceCalculating()
    {

       /* appLocationService = new AppLocationService();

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();


        pDialog2STANDBY = ProgressDialog.show(AddNewStore_DynamicSectionWise.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.rtrvng_loc), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(AddNewStore_DynamicSectionWise.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(AddNewStore_DynamicSectionWise.this)
                    .addOnConnectionFailedListener(AddNewStore_DynamicSectionWise.this)
                    .build();
            mGoogleApiClient.connect();
        }
        //startService(new Intent(DynamicActivity.this, AppLocationService.class));
        startService(new Intent(AddNewStore_DynamicSectionWise.this, AppLocationService.class));
        Location nwLocation = appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER, location);
        Location gpsLocation = appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER, location);
        long interval = 200;
        long startTime = 15000;
        countDownTimer = new CoundownClass(startTime, interval);
        countDownTimer.start();*/
        LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 20, 1);

    }
    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {


        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;


        LattitudeFromLauncher = fnLati;
        LongitudeFromLauncher = fnLongi;
        AccuracyFromLauncher = String.valueOf(fnAccuracy);
        latitudeToSave = fnLati;
        longitudeToSave = fnLongi;

        ProviderFromLauncher = fnAccurateProvider;
        GpsLatFromLauncher = GpsLat;
        GpsLongFromLauncher = GpsLong;
        GpsAccuracyFromLauncher = GpsAccuracy;
        NetworkLatFromLauncher = NetwLat;
        NetworkLongFromLauncher = NetwLong;
        NetworkAccuracyFromLauncher = NetwAccuracy;
        FusedLatFromLauncher = FusedLat;
        FusedLongFromLauncher = FusedLong;
        FusedAccuracyFromLauncher = FusedAccuracy;

        AllProvidersLocationFromLauncher = AllProvidersLocation;
        GpsAddressFromLauncher = GpsAddress;
        NetwAddressFromLauncher = NetwAddress;
        FusedAddressFromLauncher = FusedAddress;
        FusedLocationLatitudeWithFirstAttemptFromLauncher = FusedLocationLatitudeWithFirstAttempt;
        FusedLocationLongitudeWithFirstAttemptFromLauncher = FusedLocationLongitudeWithFirstAttempt;
        FusedLocationAccuracyWithFirstAttemptFromLauncher = FusedLocationAccuracyWithFirstAttempt;
        //LLLLL


        manager = getFragmentManager();
        mapFrag = (MapFragment) manager.findFragmentById(
                R.id.map);
        if (mapFrag != null && mapFrag.getView() != null) {
            mapFrag.getView().setVisibility(View.VISIBLE);
            mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.show(mapFrag);
        }

    }

    public String getAddressForDynamic(String latti,String longi){


        String areaToMerge="NA";
        Address addressTemp=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

           /* AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];*/
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0){
                if(addresses.get(0).getAddressLine(1)!=null){
                    addr=addresses.get(0).getAddressLine(1);
                    address=addr;
                }




                if(addresses.get(0).getLocality()!=null){
                    city=addresses.get(0).getLocality();
                    this.city=city;
                }

                if(addresses.get(0).getAdminArea()!=null){
                    state=addresses.get(0).getAdminArea();
                    this.state=state;
                }


                for(int i=0 ;i<addresses.size();i++){
                    addressTemp = addresses.get(i);
                    if(addressTemp.getPostalCode()!=null){
                        zipcode=addressTemp.getPostalCode();
                        this.pincode=zipcode;
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

                NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(null != recFragment)
                {
                    recFragment.setFreshAddress();
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
    public void addStoreOffline() {
        String selectedBeatName = "";
        String slctdBeatId = "0";
        String slctdBeatNodeType = "0";
        hmapStoreQuestAnsNew = new LinkedHashMap<>();
        LinkedHashMap<String, String> hmapStoreAddress = new LinkedHashMap<>();
        NewStoreForm recFragment = (NewStoreForm) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if (null != recFragment) {
            recFragment.saveDynamicQuesAns(true);
            hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
            hmapStoreAddress=recFragment.hmapAddress;
            selectedBeatName = recFragment.getSelectedBeatName();
            if (!selectedBeatName.equals("0") && (selectedBeatName.contains("-"))) {
                slctdBeatId = selectedBeatName.split(Pattern.quote("-"))[1];
                slctdBeatNodeType = selectedBeatName.split(Pattern.quote("-"))[2];
            }

            fnlStoreType = recFragment.currentStoreType;
            fnlAddressName = recFragment.currentAddressName;


            fnlOwnerName = recFragment.currentOwnerName;
            fnlMobileNumber = recFragment.currentMobileNumber;
            fnlAddressName = recFragment.currentAddressName;
            fnSalesPersonName = recFragment.currentSalesPersonName;
            fnSalesPersonContactNo = recFragment.currentSalesPersonContactNo;
            fnStoreCatType = recFragment.currentStoreCatType;
            if (!TextUtils.isEmpty(recFragment.distBId)) {
                distID = recFragment.distBId;
            }
        }
               /* int ansValForBSgmntId = helperDb.fnGetAnsValID(hmapStoreQuestAnsNew.get(QuestIDForOutChannel));
               int BusinessSegmentID = helperDb.fnGetBusinessSegmentIDAgainstStoreType(ansValForBSgmntId);*/

        int BusinessSegmentID=1;
        helperDb.fnsaveOutletQuestAnsMstrSectionWise(hmapStoreQuestAnsNew, 0, selStoreID,fnStoreCatType);

        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);


        String VisitEndFinalTS = df.format(datefromat);
        String VisitDate = dfDate.format(datefromat);
        // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
        int ApplicationID = CommonInfo.Application_TypeID;
        String allValuesOfPaymentStageID = helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
        //      helperDb.fnInsertOrUpdate_tblStoreDeatils(selStoreID, StoreName, SOLattitudeFromLauncher, SOLongitudeFromLauncher, VisitStartTimeOfNewStore, VisitEndFinalTS, "Fused", AccuracyFromLauncher, "" + battLevel, IsStoreDataCompleteSaved, allValuesOfPaymentStageID, 1, hmapStoreAddress.get("0"), hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), 3,flgApproveOrRejectOrNoActionOrReVisit,flgStoreVisitMode,StoreCategoryType,StoreSectionCount, hmapStoreAddress.get("4"), hmapStoreAddress.get("5"), hmapStoreAddress.get("6"), hmapStoreAddress.get("7"), hmapStoreAddress.get("8"), hmapStoreAddress.get("9"));

        helperDb.fnInsertOrUpdate_tblStoreDeatils(selStoreID, StoreName, SOLattitudeFromLauncher, SOLongitudeFromLauncher, VisitStartTimeOfNewStore, VisitEndFinalTS, "Fused", AccuracyFromLauncher, "" + battLevel, IsStoreDataCompleteSaved, allValuesOfPaymentStageID, 1, hmapStoreAddress.get("0"), hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), 3,flgApproveOrRejectOrNoActionOrReVisit,flgStoreVisitMode,StoreCategoryType,StoreSectionCount,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart,flgStoreOrder,flgUpdateSomeNewStoreFlags, hmapStoreAddress.get("4"), hmapStoreAddress.get("5"), hmapStoreAddress.get("6"), hmapStoreAddress.get("7"), hmapStoreAddress.get("8"), hmapStoreAddress.get("9"),slctdBeatId,slctdBeatNodeType,CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

        if (FLAG_NEW_UPDATE.equals("UPDATE")) {
            int CurrentCoverageIDOfStore=helperDb.fetch_GetCoverageAreaIDAgsinstStoreID(selStoreID);
            //helperDb.fnInsertOrUpdate_tblDSRSummaryDetials(CurrentCoverageIDOfStore,1);
            //helperDb.open();
            helperDb.UpdateStoreNamePreAddedTable(selStoreID, StoreName);
            //helperDb.close();
        } else {
            // helperDb.fnInsertOrUpdate_tblDSRSummaryDetials(0,0);
            String storeCountDeatails=helperDb.getTodatAndTotalStores();
            int  totaltarget = Integer.parseInt(storeCountDeatails.split(Pattern.quote("^"))[0]);
            int todayTarget = Integer.parseInt(storeCountDeatails.split(Pattern.quote("^"))[1]);

            //helperDb.open();
            helperDb.deletetblStoreCountDetails();
            totaltarget=totaltarget+1;
            todayTarget=todayTarget+1;
            helperDb.saveTblStoreCountDetails(String.valueOf(totaltarget),String.valueOf(todayTarget));
            //helperDb.saveTblPreAddedStores(selStoreID, StoreName, SOLattitudeFromLauncher, SOLongitudeFromLauncher, VisitDate, 1, 3);
            //helperDb.close();
        }
        fnGettingGSTOFflineVal();
        //helperDb.open();

        helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                   /* helperDb.savetblStoreMain("NA", selStoreID, StoreName, "NA", "NA", "NA", "NA", "NA", "NA", "NA", "0", StoreTypeTradeChannel,
                            BusinessSegmentID, 0, 0, 0, "NA", VisitStartTS, imei, "" + battLevel, 1, 1, String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy, "" + fnAccurateProvider, 0, fetchAddress, allValuesOfPaymentStageID, flgHasQuote, flgAllowQuotation, flgSubmitFromQuotation);
*/
        if ((!fnlStoreType.equals("0")) && fnlStoreType.contains("-")) {
            fnlStoreType = fnlStoreType.split(Pattern.quote("-"))[1];
        }
        if (FLAG_NEW_UPDATE.equals("UPDATE")) {

        }
        else {
            helperDb.savetblStoreMain(slctdBeatId, selStoreID, StoreName, "NA", "NA", "NA", "NA", "NA", "NA", "NA", "0", StoreTypeTradeChannel,
                    Integer.parseInt(fnlStoreType), 0, 0, 0, "NA", VisitStartTS, imei,
                    "" + battLevel, 3, 1, String.valueOf(LattitudeFromLauncher), String.valueOf(LongitudeFromLauncher),
                    "" + AccuracyFromLauncher, "" + ProviderFromLauncher, 0, fnlAddressName,
                    allValuesOfPaymentStageID, flgHasQuote, flgAllowQuotation, flgSubmitFromQuotation, flgGSTCapture, flgGSTCompliance, GSTNumber,
                    flgGSTRecordFromServer, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff, flgFusedOnOff, flgInternetOnOffWhileLocationTracking, flgRestart,
                    flgStoreOrder, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), distID, fnlOwnerName, fnlMobileNumber,
                    fnStoreCatType, flgRuleTaxVal, flgTransType, fnlMobileNumber, fnSalesPersonName, fnSalesPersonContactNo, IsComposite,
                    Integer.parseInt(hmapStoreAddress.get("5")), Integer.parseInt(hmapStoreAddress.get("4")), Integer.parseInt(slctdBeatNodeType));
        }
    }
    private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
    {


        final ProgressDialog pDialogGetStores;
        FullSyncDataNow(AddNewStore_DynamicSectionWise activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(getResources().getString(R.string.SubmittingTarget));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Void doInBackground(Void... params)
        {

           /* int Outstat=3;

            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            String StampEndsTime = df.format(dateobj);


            String Time=fnGenerateDateTime();
            helperDb.fnVisitStartOrEndTime(selStoreID,Time,1);

            //helperDb.open();
            // dbengine.UpdateStoreEndVisit(selStoreID, StampEndsTime);
            helperDb.UpdateStoreFlag(selStoreID, 3);
            helperDb.UpdateStoreImageTableFlag(selStoreID, 3);
            //helperDb.close();


            SimpleDateFormat df1 = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);

            newfullFileName=imei+"."+ df1.format(dateobj);



            try {


                File LTFoodxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                if (!LTFoodxmlFolder.exists())
                {
                    LTFoodxmlFolder.mkdirs();

                }

                SubmitData submitData = new SubmitData();
                AppUtils.executeAsyncTask(submitData);
                DA.open();
                DA.export(CommonInfo.DATABASE_NAME, newfullFileName);
                DA.close();



                helperDb.savetbl_XMLfiles(newfullFileName, "3","4");
                //helperDb.open();
                //  dbengine.UpdateStoreFlag(selStoreID.trim(), 5);
                helperDb.UpdateStoreFlagSO(selStoreID, 4);
                helperDb.UpdateStoreImageTableFlag(selStoreID.trim(), 5);

                //helperDb.close();
                helperDb.fnDeleteUnNeededRecordsFromOtheTables(selStoreID);
                //dbengine.fndeleteSbumittedStoreList(4);
            } catch (Exception e)
            {

                e.printStackTrace();
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }
            }*/
            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            try
            {
/*
                if(isOnline())
                {
                    Intent syncIntent = new Intent(AddNewStore_DynamicSectionWise.this, SyncMasterSO.class);
                    syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                    syncIntent.putExtra("OrigZipFileName", newfullFileName);
                    syncIntent.putExtra("activityFrom", activityFrom);
                    syncIntent.putExtra("whereTo", "Regular");
                    startActivity(syncIntent);
                    finish();
                }
                else {

                    if(activityFrom.equals("StoreSelection"))
                    {
                        Date date1 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String fDate = sdf.format(date1).trim();
                        Intent storeIntent = new Intent(AddNewStore_DynamicSectionWise.this, StoreSelection.class);
                        storeIntent.putExtra("imei", imei);
                        storeIntent.putExtra("userDate", fDate);
                        storeIntent.putExtra("pickerDate", fDate);
                        startActivity(storeIntent);
                        finish();
                    }
                    else
                    {
                        if(CommonInfo.flgNewStoreORStoreValidation==1)
                        {
                            Intent submitStoreIntent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                            startActivity(submitStoreIntent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(AddNewStore_DynamicSectionWise.this, StorelistActivity.class);
                            intent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                            startActivity(intent);
                            finish();
                        }
                    }

                }*/


            } catch (Exception e) {

                e.printStackTrace();
            }


        }
    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOff=0;
        flgGPSOnOff=0;
        flgNetworkOnOff=0;
        flgFusedOnOff=0;
        flgInternetOnOffWhileLocationTracking=0;

        if(isGooglePlayServicesAvailable())
        {
            flgFusedOnOff=1;
        }
        if(isOnline())
        {
            flgInternetOnOffWhileLocationTracking=1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=0;
                    flgNetworkOnOff=1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;

                flgLocationServicesOnOff = 0;
                flgGPSOnOff = 0;
                flgNetworkOnOff = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOff = 1;
                flgGPSOnOff = 1;
                flgNetworkOnOff = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

    }


    public  void fnGettingGSTOFflineVal()
    {
        // Start for getting GST Offline
/*
        String OutletID="0",QuestID = "0",AnswerType,AnswerValue = "";
        int sectionID = 0;
        int QuestionGroupID=0;


        for(Map.Entry<String, String> entry:hmapStoreQuestAnsNew.entrySet())
        {
            String questId=entry.getKey().split(Pattern.quote("^"))[0].toString();
            AnswerType=entry.getKey().split(Pattern.quote("^"))[1].toString();
            QuestionGroupID=Integer.valueOf(entry.getKey().split(Pattern.quote("^"))[2].toString());
            AnswerValue=entry.getValue();

            String optionValue="0";

            if(questId.equals("10"))
            {
                try
                {
                    //flgGSTCompliance=helperDb.fnGetGstOptionIDComplianceWhileAddingNewStore(""+AnswerValue);
                    String OptionDescr=helperDb.fnGetOptionDescrFromtblOptionMstr(questId,""+AnswerValue);
                    if(OptionDescr.equals("Yes"))
                    {
                        flgGSTCompliance="1";
                    }
                    else if(OptionDescr.equals("Not Required"))
                    {
                        flgGSTCompliance="0";
                    }
                    else if(OptionDescr.equals("Pending"))
                    {
                        flgGSTCompliance="2";
                    }

                }
                catch(Exception e)
                {

                }
            }
            if(questId.equals("11"))
            {
                if(!AnswerValue.equals(""))
                {
                    GSTNumber=AnswerValue;
                }
            }



        }*/
        String OutletID = "0", QuestID = "0", AnswerType, AnswerValue = "";
        int sectionID = 0;
        int QuestionGroupID = 0;


        for (Entry<String, String> entry : hmapStoreQuestAnsNew.entrySet()) {
            String questId = entry.getKey().split(Pattern.quote("^"))[0].toString();
            AnswerType = entry.getKey().split(Pattern.quote("^"))[1].toString();
            QuestionGroupID = Integer.valueOf(entry.getKey().split(Pattern.quote("^"))[2].toString());
            AnswerValue = entry.getValue();

            String optionValue = "0";

            if (questId.equals("49")) {
                try {
                    //flgGSTCompliance=mDataSource.fnGetGstOptionIDComplianceWhileAddingNewStore(""+AnswerValue);
                    String OptionDescr = helperDb.fnGetOptionDescrFromtblGetPDAQuestOptionMstr(questId, "" + AnswerValue);
                    if (OptionDescr.equals("Yes")) {
                        flgGSTCompliance = "1";
                    } else if (OptionDescr.equals("Not Required")) {
                        flgGSTCompliance = "0";
                    } else if (OptionDescr.equals("Pending")) {
                        flgGSTCompliance = "2";
                    }

                } catch (Exception e) {

                }
            }
            if (questId.equals("50")) {
                if (!AnswerValue.equals("")) {
                    GSTNumber = AnswerValue;
                }
            }


        }
        // End for gst getting
    }



}
