package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.allanasosfa.truetime.TimeUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import java.text.SimpleDateFormat;


public class StorelistActivity extends BaseActivity implements LocationInterface,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,InterfaceRetrofit {
    //spinner variables
    int modeOfVisit=0;
    AlertDialog alertCoverage;
    public int RouteIDSelectedInSpinner=0;
    public int CoverageIDSelectedInSpinner=0;
    ListView listCoverage;
    public Button onlineBtn;
    public Button offlineBtn;
    public int CoverageID = 0;
    public int RouteID = 0;
    TextView selectCoverageSpinner;
    ArrayAdapter<String> adapterCoverageList;
    ArrayAdapter<String> adapterRouteList;
    LinkedHashMap<String, String> hmapCoverage_details=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapRoute_details=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapCoverageRouteMap_details=new LinkedHashMap<String, String>();
    String slctdRouteName="All";
    View convertView;
    ListView listDominantBrand;
    ArrayAdapter<String> adapterDominantBrand;
    AlertDialog ad;
    public String[] DB_NAME;
    TextView   sp_Route;
    LinkedHashMap<String,String> hmapRoutlist=new LinkedHashMap();
    //spinner variables end
    String tagFromStore=null;
    public String Noti_text="Null";
    public int MsgServerID=0;
    LinkedHashMap<View, String> hmapStoreViewAndName=new LinkedHashMap<View,String>();
    public EditText ed_search;
    private ArrayAdapter<String> adapterAutoText;
    List<String> listStore;
    List<String> listOutletId;
    List<String> listOwener;
    List<String> listOutletNewStoreFlg;
    List<String> listOutletSstat;
    LinkedHashMap<String, String> hmapOutletList=new LinkedHashMap<String, String>();
    public HashMap<String, String> hmapOutletIDSstat=new HashMap<String, String>();
    public HashMap<String, String> hmapOutletIDNewStoreFlg=new HashMap<String, String>();


    public String[] OutletId;
    public	String[] OutletName;
    public String[] RetalierName;
    public String[] RetalierContactNo;

    String[] AllOutletCompleteInfo;



    ImageView logoutIcon,menu_icon;
    InputStream inputStream;
    LinearLayout parentOfAllDynamicData;
    LinkedHashMap<String, String> hmapStoresFromDataBase;
    Button EditBtn , AddStoreBtn,RefreshBtn;
    String tagOfselectedStore="0"+"^"+"0";
    public LocationManager locationManager;


    int serverResponseCode = 0;
    public int syncFLAG = 0;
    public String[] xmlForWeb = new String[1];

    public int chkFlgForErrorToCloseApp=0;
    public   PowerManager pm;
    public	 PowerManager.WakeLock wl;

   // public SimpleDateFormat sdf;
    public ProgressDialog pDialog2STANDBY;
    public Location location;
    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;


    public String AccuracyFromLauncher="NA";
    String AddressFromLauncher="NA";
    String CityFromLauncher="NA";
    String PincodeFromLauncher="NA";
    String StateFromLauncher="NA";
    AlertDialog.Builder alertDialog;
    public String ProviderFromLauncher="NA";
    public String GpsLatFromLauncher="NA";
    public String GpsLongFromLauncher="NA";
    public String GpsAccuracyFromLauncher="NA";
    public String NetworkLatFromLauncher="NA";
    public String NetworkLongFromLauncher="NA";
    public String NetworkAccuracyFromLauncher="NA";
    public String FusedLatFromLauncher="NA";
    public String FusedLongFromLauncher="NA";
    public String FusedAccuracyFromLauncher="NA";

    public String fnAddressFromLauncher="NA";
    public String AllProvidersLocationFromLauncher="NA";
    public String GpsAddressFromLauncher="NA";
    public String NetwAddressFromLauncher="NA";
    public String FusedAddressFromLauncher="NA";
    public String FusedLocationLatitudeWithFirstAttemptFromLauncher="NA";
    public String FusedLocationLongitudeWithFirstAttemptFromLauncher="NA";
    public String FusedLocationAccuracyWithFirstAttemptFromLauncher="NA";

    public boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;



    private final long startTime = 15000;
    private final long interval = 200;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    LocationRequest mLocationRequest;
    String imei;
    public String fDate;
    String[] RouteNames;
    String fusedData;
    Dialog dialog;

    ServiceWorker newservice = new ServiceWorker();
    LinkedHashMap<String, String> hmapOutletListForNear=new LinkedHashMap<String, String>();

    LinkedHashMap<String, String> hmapOutletListForNearUpdated=new LinkedHashMap<String, String>();

    SharedPreferences sharedPreferences;
    public int flgAddButtonCliked=0;
    public static String activityFrom="";
    String[] CoverageAreaNames;

    public void showUnreadNotificationToUser()
    {
        try
        {

            //mDataSource.open();
            String Noti_textWithMsgServerID= mDataSource.fetchNoti_textFromtblPDANotificationMaster();
            //mDataSource.close();
            System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
            if(!Noti_textWithMsgServerID.equals("Null"))
            {
                StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

                MsgServerID= Integer.parseInt(token.nextToken().trim());
                Noti_text= token.nextToken().trim();


                if(Noti_text.equals("") || Noti_text.equals("Null"))
                {

                }
                else
                {



                    final AlertDialog builder = new AlertDialog.Builder(StorelistActivity.this).create();


                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View openDialog = inflater.inflate(R.layout.custom_dialog, null);
                    openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

                    builder.setCancelable(false);
                    TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
                    final TextView msg=(TextView)openDialog. findViewById(R.id.msg);

                    final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
                    final Button cancel=(Button)openDialog. findViewById(R.id.but_no);

                    cancel.setVisibility(View.GONE);
                    header_text.setText(getResources().getString(R.string.AlertDialogHeaderMsg));
                    msg.setText(Noti_text);

                    ok_but.setText(getResources().getString(R.string.AlertDialogOkButton));

                    builder.setView(openDialog,0,0,0,0);

                    ok_but.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            // TODO Auto-generated method stub

                            long syncTIMESTAMP = System.currentTimeMillis();
                            Date dateobj = new Date(syncTIMESTAMP);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                            String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(StorelistActivity.this, TimeUtils.DATE_TIME_FORMAT);;

                            //mDataSource.open();
                            mDataSource.updatetblPDANotificationMaster(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
                            //mDataSource.close();

                            try
                            {
                                //mDataSource.open();
                                int checkleftNoti= mDataSource.countNumberOFNotificationtblPDANotificationMaster();
                                if(checkleftNoti>0)
                                {
                                    String Noti_textWithMsgServerID= mDataSource.fetchNoti_textFromtblPDANotificationMaster();
                                    System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
                                    if(!Noti_textWithMsgServerID.equals("Null"))
                                    {
                                        StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");

                                        MsgServerID= Integer.parseInt(token.nextToken().trim());
                                        Noti_text= token.nextToken().trim();

                                        //mDataSource.close();
                                        if(Noti_text.equals("") || Noti_text.equals("Null"))
                                        {

                                        }
                                        else
                                        {
                                            msg.setText(Noti_text);
                                        }
                                    }

                                }
                                else
                                {
                                    builder.dismiss();
                                }

                            }
                            catch(Exception e)
                            {

                            }
                            finally
                            {
                                //mDataSource.close();

                            }


                        }
                    });




                    builder.show();






                }
            }
        }
        catch(Exception e)
        {

        }
    }



        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.storelist_activity);

            sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE);
            Intent extras = getIntent();
            if(extras !=null)
            {
                activityFrom=extras.getStringExtra("activityFrom");
            }

           // intent.putExtra("activityFrom", activityFrom);

            initializeAllView();
            locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
            /*Date date1=new Date();
            sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            fDate = sdf.format(date1).toString().trim();*/
            getCoverageAndRouteListDetail();
            adapterCoverageList = new ArrayAdapter<String>(StorelistActivity.this, R.layout.list_item_dark, R.id.fso_name, CoverageAreaNames);
            selectCoverageSpinner= (TextView) findViewById(R.id.selectCoverageSpinner);

            String StoreCategoryType=mDataSource.getChannelGroupIdOptIdForAddingStore();
            if(StoreCategoryType.equals("0-3-80"))
            {
                selectCoverageSpinner.setText("All Merchandiser/Coverage Area");
            }


            fDate=getDateInMonthTextFormat();
            sp_Route=(TextView) findViewById(R.id.selectRouteSpinner);
            routeListSpinner();


            adapterRouteList = new ArrayAdapter<String>(StorelistActivity.this, R.layout.list_item_dark, R.id.fso_name, RouteNames);

            offlineBtn=(Button) findViewById(R.id.offlineBtn);

            onlineBtn=(Button) findViewById(R.id.onlineBtn);

            setBtnBackgroundOfLineOnline();
            onlineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flgAddButtonCliked=0;
                    hmapStoresFromDataBase.clear();
                    parentOfAllDynamicData.removeAllViews();
                    modeOfVisit=1;

                    CommonInfo.flgLTFoodsSOOnlineOffLine=1;
                    setBtnBackgroundOfLineOnline();
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
                        CommonInfo.flgLTFoodsSOOnlineOffLine=1;
                        locationRetrievingAndDistanceCalculating();
                    }
                }
            });
            offlineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flgAddButtonCliked=0;
                    hmapStoresFromDataBase.clear();
                    parentOfAllDynamicData.removeAllViews();
                    modeOfVisit=0;
                    CommonInfo.flgLTFoodsSOOnlineOffLine=0;
                    setBtnBackgroundOfLineOnline();
                    getDataFromDatabaseToHashmap();
                    addViewIntoTable();
                }
            });

            selectCoverageSpinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  uomtext = (TextView) arg0;
                    alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = (View) inflater.inflate(R.layout.list_activity, null);
                    EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                    inputSearch.setVisibility(View.GONE);
                    listCoverage = (ListView)convertView. findViewById(R.id.list_view);

                    listCoverage.setAdapter(adapterCoverageList);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Coverage Area");
                    listCoverage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String coverageNameInSpinner=listCoverage.getItemAtPosition(position).toString().trim();
                            CoverageIDSelectedInSpinner=Integer.parseInt(hmapCoverage_details.get(coverageNameInSpinner));
                            selectCoverageSpinner.setText(coverageNameInSpinner);
                            RouteIDSelectedInSpinner=0;
                            //fsoIDSelectedInSpinner

                            hmapCoverageRouteMap_details.clear();
                            RouteNames = new String[0];
                            if(CoverageIDSelectedInSpinner==0)
                            {

                                hmapCoverageRouteMap_details=mDataSource.fetch_CoverageRouteMap_List(0,0);

                            }
                            else
                            {
                                hmapCoverageRouteMap_details=mDataSource.fetch_CoverageRouteMap_List(0,CoverageIDSelectedInSpinner);
                            }

                            int  index=0;
                            if(hmapRoute_details!=null)
                            {
                                RouteNames=new String[hmapCoverageRouteMap_details.size()];
                                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapCoverageRouteMap_details);
                                Set set2 = map.entrySet();
                                Iterator iterator = set2.iterator();
                                while(iterator.hasNext())
                                {
                                    Map.Entry me2 = (Map.Entry)iterator.next();
                                    RouteNames[index]=me2.getKey().toString();
                                    index=index+1;
                                }
                            }
                            adapterRouteList = new ArrayAdapter<String>(StorelistActivity.this, R.layout.list_item_dark, R.id.fso_name, RouteNames);

                            sp_Route.setText("Select Beat");

                            alertCoverage.dismiss();
                            filterStoreList(CoverageIDSelectedInSpinner,RouteIDSelectedInSpinner);

                     /* String coverageNameInSpinner=listCoverage.getItemAtPosition(position).toString().trim();
                      CoverageIDSelectedInSpinner=Integer.parseInt(hmapCoverage_details.get(coverageNameInSpinner));
                      selectCoverageSpinner.setText(coverageNameInSpinner);

                      //fsoIDSelectedInSpinner

                      alertCoverage.dismiss();
                      filterStoreList(CoverageIDSelectedInSpinner,RouteIDSelectedInSpinner);*/
                        }
                    });
                    alertCoverage=alertDialog.show();
                }
            });
    }
    public void filterStoreList(int CoverageIDOfSpinner,int RouteIDOfSpinner)
    {
        if (parentOfAllDynamicData != null && parentOfAllDynamicData.getChildCount() > 0) {
            // int rowCunt=parentOfAllDynamicData.getChildCount();
            for(Map.Entry<String, String> entry:hmapStoresFromDataBase.entrySet()) {
                //StoreID,StoreName,DateAdded,CoverageAreaID,RouteNodeID,StoreCategoryType,StoreSectionCount,flgApproveOrRejectOrNoActionOrReVisit

                //   ////StoreName,DateAdded,CoverageAreaID,RouteNodeID,StoreCategoryType,StoreSectionCount,flgApproveOrRejectOrNoActionOrReVisit
                String storeID=entry.getKey().toString().trim();
                String StoreDetails=entry.getValue().toString().trim();
                int CoverageAreaIDIdOfStore = Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[2]);
                int RouteNodeIDIdOfStore = Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[3]);
                String StoreCategoryTypeOfStore=StoreDetails.split(Pattern.quote("^"))[4];
                //storeID+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType

                //dynamic_container.setTag(storeID+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType);

                View dynamic_container=(View) parentOfAllDynamicData.findViewWithTag(storeID +"^"+CoverageAreaIDIdOfStore +"^"+RouteNodeIDIdOfStore +"^"+StoreCategoryTypeOfStore);
                // String asdasdad=" dynamic_container tag got is :-" + dynamic_container.getTag();
                if(CoverageIDOfSpinner==0 && RouteIDOfSpinner==0)
                {
                    dynamic_container.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(CoverageIDOfSpinner!=0 && RouteIDOfSpinner!=0)
                    {
                        if(CoverageAreaIDIdOfStore==CoverageIDOfSpinner && RouteNodeIDIdOfStore==RouteIDOfSpinner)
                        {
                            dynamic_container.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            dynamic_container.setVisibility(View.GONE);
                        }
                    }
                    if(CoverageIDOfSpinner!=0 && RouteIDOfSpinner==0)
                    {
                        if(CoverageAreaIDIdOfStore==CoverageIDOfSpinner)
                        {
                            dynamic_container.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            dynamic_container.setVisibility(View.GONE);
                        }
                    }
                    if(CoverageIDOfSpinner==0 && RouteIDOfSpinner!=0)
                    {
                        if(RouteNodeIDIdOfStore==RouteIDOfSpinner)
                        {
                            dynamic_container.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            dynamic_container.setVisibility(View.GONE);
                        }
                    }
                }
            }

        }

    }
    public void setBtnBackgroundOfLineOnline()
    {
        if(CommonInfo.flgLTFoodsSOOnlineOffLine==0)
        {
            offlineBtn.setBackground(getResources().getDrawable(R.drawable.btn_background));
            offlineBtn.setTextColor(Color.parseColor("#FFFFFF"));
            onlineBtn.setBackground(getResources().getDrawable(R.drawable.logo_background));
            onlineBtn.setTextColor(Color.parseColor("#FFFF4424"));

        }
        else
        {
            onlineBtn.setBackground(getResources().getDrawable(R.drawable.btn_background));
            onlineBtn.setTextColor(Color.parseColor("#FFFFFF"));
            offlineBtn.setBackground(getResources().getDrawable(R.drawable.logo_background));
            offlineBtn.setTextColor(Color.parseColor("#FFFF4424"));
        }
    }

    public void getCoverageAndRouteListDetail()
    {

        hmapCoverage_details=mDataSource.fetch_CoverageArea_List(0);//0=Calling this function from StoreListActivity,1=Calling this function from Report Activity
        hmapRoute_details=mDataSource.fetch_Route_LisStoreListActivityt(0);//0=Calling this function from StoreListActivity,1=Calling this function from Report Activity
        hmapCoverageRouteMap_details=mDataSource.fetch_CoverageRouteMap_List(0,0);
        int index=0;
        if(hmapCoverage_details!=null)
        {
            CoverageAreaNames=new String[hmapCoverage_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapCoverage_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                CoverageAreaNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }

        index=0;
        if(hmapRoute_details!=null)
        {
            RouteNames=new String[hmapRoute_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapRoute_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext())
            {
                Map.Entry me2 = (Map.Entry)iterator.next();
                RouteNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }
    @Override
    protected void onResume() {
        super.onResume();

        boolean isGPSok = false;
        boolean isNWok=false;
        boolean locNotEnabled=false;

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
            locNotEnabled=true;
        }

        SharedPreferences.Editor ed;
        //mDataSource.open();
        String getServerDate = mDataSource.fnGetServerDate();
        //mDataSource.close();

        if(locNotEnabled)
        {
            if(!sharedPreferences.contains("FirstRunNearByStore"))
            {
                ed = sharedPreferences.edit();
                ed.putBoolean("FirstRunNearByStore",true);
                ed.putString("LastRunNearByStoreDate",getServerDate);
                ed.commit();

                fetchLocationOnResume();
            }
            else
            {
                if(!sharedPreferences.getString("LastRunNearByStoreDate", "").equals(getServerDate))
                {
                    ed = sharedPreferences.edit();
                    ed.putString("LastRunNearByStoreDate",getServerDate);
                    ed.commit();
                    fetchLocationOnResume();
                }
                else
                {
                    getDataFromDatabaseToHashmap();
                    if(parentOfAllDynamicData.getChildCount()>0){
                        parentOfAllDynamicData.removeAllViews();
                        addViewIntoTable();
                    }
                    else
                    {
                        addViewIntoTable();
                    }
                }
            }
        }




    }





    private void fetchLocationOnResume()
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
                    if(checkRefreshDataUnSuccess())
                    {//show alert that last refresh throws exception
                        alertRefreshOrFinish();
                    }
                    else
                    {
                        locationRetrievingAndDistanceCalculating();
                    }
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

                if(checkRefreshDataUnSuccess())
                {
                    // show alert that last refresh throws exception
                    alertRefreshOrFinish();
                }
                else
                {
                    locationRetrievingAndDistanceCalculating();
                }

            }

        }
    }

    public void  initializeAllView()
    {
        AddStoreBtn= (Button) findViewById(R.id.AddStoreBtn);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        ed_search=(EditText) findViewById(R.id.ed_search);

        ed_search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().trim().length()==0)
                {
                    if (hmapStoreViewAndName != null)
                    {
                        if(hmapStoreViewAndName.size()>0)
                        {
                            for (Map.Entry<View, String> entry : hmapStoreViewAndName.entrySet())
                            {
                                View storeRow = entry.getKey();
                                storeRow.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                else
                {
                    if (hmapStoreViewAndName != null)
                    {
                        if(hmapStoreViewAndName.size()>0)
                        {
                            for (Map.Entry<View, String> entry : hmapStoreViewAndName.entrySet())
                            {
                                View storeRow = entry.getKey();
                                if (entry.getValue().toString().trim().toLowerCase().contains(s.toString().trim().toLowerCase()))
                                {
                                    storeRow.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    storeRow.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       // setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);//false for removing back icon
       // getSupportActionBar().setDisplayShowHomeEnabled(true);//false disable button
       // getSupportActionBar() .setDisplayShowTitleEnabled(false);//false for removing title
        //back button code uncommented
   /*   toolbar.setNavigationIcon(R.drawable.ic_back);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //What to do on back clicked

              AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);
              alertDialog.setTitle("Information");

              alertDialog.setCancelable(false);
              alertDialog.setMessage("Do you really want to go back ");
              alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();


                      Intent intent = new Intent(StorelistActivity.this, LauncherActivity.class);
                      intent.putExtra("FROM", "StorelistActivity");
                      StorelistActivity.this.startActivity(intent);
                      finish();

                  }
              });
              alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();

                  }
              });

              // Showing Alert Message
              alertDialog.show();

          }
      });*/

        menu_icon=(ImageView) findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                //open_pop_up();
                if(activityFrom.equals("StoreSelection"))
                {
                    Intent intent =new Intent(StorelistActivity.this,StoreSelection.class);
                    intent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent =new Intent(StorelistActivity.this,AllButtonActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        logoutIcon= (ImageView) findViewById(R.id.logoutIcon);
        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                alertDialog.setTitle("Information");

                alertDialog.setCancelable(false);
                alertDialog.setMessage("Do you really want to close app ");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

//--------------------------------------------------------------------------------------------------------------
                        finishAffinity();

                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });


        imei=getIMEI();
        parentOfAllDynamicData=(LinearLayout) findViewById(R.id.parentOfAllDynamicData);
        EditBtn= (Button) findViewById(R.id.EditBtn);
        //EditBtn is now not working for this project
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flgAddButtonCliked=0;
                boolean selectFlag=   checkStoreSelectededOrNot();
                if(selectFlag){
                    //Toast.makeText(getApplicationContext(),"selected",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(StorelistActivity.this,AddNewStore_DynamicSectionWise.class);

                    String storeID = tagOfselectedStore.split(Pattern.quote("^"))[0];
                    String   storeName = tagOfselectedStore.split(Pattern.quote("^"))[1];
                    intent.putExtra("FLAG_NEW_UPDATE","UPDATE");
                    intent.putExtra("StoreID",storeID);
                    intent.putExtra("StoreName",storeName);
                    intent.putExtra("CurrntRouteName",slctdRouteName);
                    intent.putExtra("activityFrom", activityFrom);

                    StorelistActivity.this.startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select store ",Toast.LENGTH_LONG).show();

                }
            }
        });
        RefreshBtn=(Button) findViewById(R.id.RefreshBtn);
        RefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flgAddButtonCliked=0;
                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                AddStoreBtn.setEnabled(false);
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
        });


        AddStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(ed_search.getText().toString().trim()))
                {
                   // Toast.makeText(StorelistActivity.this, "Please Add or Select Store To Proceed", Toast.LENGTH_SHORT).show();
                    showAlertSingleButtonInfo(getResources().getString(R.string.AddOrSelectStoreMsg));
                    return;
                }
                else
                {
                    int existOutID= mDataSource.fnGetExistingOutletIDFromOutletMstr( ed_search.getText().toString().trim());
                    if(existOutID>0)
                    {
                        AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(StorelistActivity.this);
                        alertDialogSubmitConfirm.setTitle("Information");
                        alertDialogSubmitConfirm.setMessage(getResources().getString(R.string.SameStoreMessage));
                        alertDialogSubmitConfirm.setCancelable(false);

                        alertDialogSubmitConfirm.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                dialog.dismiss();

                                flgAddButtonCliked=1;
                                fetchLocationOnResume();

                            }
                        });

                        alertDialogSubmitConfirm.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

                        alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

                        AlertDialog alert = alertDialogSubmitConfirm.create();

                        alert.show();
                    }
                    else
                    {
                        flgAddButtonCliked=1;
                        fetchLocationOnResume();
                    }
                }


            }
        });
    }

    public boolean checkStoreSelectededOrNot()
    {
        boolean selectedFlag=false;
        tagOfselectedStore="0"+"^"+"0";
        if(parentOfAllDynamicData!=null && parentOfAllDynamicData.getChildCount()>0){
            for(int i=0;parentOfAllDynamicData.getChildCount()>i;i++)
            {
                LinearLayout parentOfRadiobtn= (LinearLayout) parentOfAllDynamicData.getChildAt(i);
                RadioButton childRadiobtn= (RadioButton) parentOfRadiobtn.getChildAt(0);
                if(childRadiobtn.isChecked()){
                    selectedFlag=true;
                    tagOfselectedStore= childRadiobtn.getTag().toString().trim();
                    break;
                }


            }

        }
        else
        {
            selectedFlag=false;
        }
        return selectedFlag;
    }

    public void  getDataFromDatabaseToHashmap()
    {
       // hmapStoresFromDataBase = mDataSource.fnGeStoreListSM(CommonInfo.DistanceRange);
        hmapStoresFromDataBase = mDataSource.fnGeStoreListAllForSO(CoverageID,RouteID);
     /* hmapStoresFromDataBase.put("1","Store Croma Gurgaon");
      hmapStoresFromDataBase.put("2", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("3", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("4", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("5", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("6", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("7","Store Croma Gurgaon");
      hmapStoresFromDataBase.put("8", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("9", "Store Croma Gurgaon");
      hmapStoresFromDataBase.put("10","Store Croma Gurgaon");*/
    }
  /*  public void addViewIntoTable()
    {
        hmapStoreViewAndName.clear();

        for(Map.Entry<String, String> entry:hmapStoresFromDataBase.entrySet())
        {
            String storeID=entry.getKey().toString().trim();

            String StoreDetails=entry.getValue().toString().trim();
            String StoreName = StoreDetails.split(Pattern.quote("^"))[0];
            String LatCode = StoreDetails.split(Pattern.quote("^"))[1];
            String LongCode = StoreDetails.split(Pattern.quote("^"))[2];
            String DateAdded = StoreDetails.split(Pattern.quote("^"))[3];
            int flgReMap=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[4]);
            View dynamic_container=getLayoutInflater().inflate(R.layout.store_single_row,null);
            TextView storeTextview= (TextView) dynamic_container.findViewById(R.id.storeTextview);
            storeTextview.setTag(storeID+"^"+StoreName);
            storeTextview.setText(StoreName);

            dynamic_container.setTag(storeID+"^"+StoreName);
            hmapStoreViewAndName.put(dynamic_container,StoreName);
            dynamic_container.setVisibility(View.VISIBLE);

            storeTextview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View view)
                {
                    final TextView tvStores= (TextView) view;
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                    alertDialog.setTitle("Information");
                    AddStoreBtn.setVisibility(View.INVISIBLE);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getResources().getString(R.string.editStoreMessage));
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            tagFromStore=   tvStores.getTag().toString().trim();
                            flgAddButtonCliked=2;
                            fetchLocationOnResume();


//--------------------------------------------------------------------------------------------------------------
                            //finishAffinity();

                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AddStoreBtn.setVisibility(View.VISIBLE);

                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });
            if(flgReMap==0)
            {
                storeTextview.setTextColor(Color.parseColor("#1A237E"));
                storeTextview.setOnClickListener(null);
            }
            else if(flgReMap==1)
            {
                storeTextview.setTextColor(Color.parseColor("#2E7D32"));
                storeTextview.setOnClickListener(null);
            }
            else if(flgReMap==2)
            {
                storeTextview.setTextColor(Color.parseColor("#F44336"));
                storeTextview.setOnClickListener(null);
            }
            else if(flgReMap==3)
            {
                storeTextview.setTextColor(Color.parseColor("#EF6C00"));
            }
            else
            {
                storeTextview.setTextColor(Color.parseColor("#000000"));
            }
            RadioButton radioButton= (RadioButton) dynamic_container.findViewById(R.id.radiobtn);
            radioButton.setTag(storeID+"^"+StoreName);
            radioButton.setText(StoreName);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unCheckRadioButton();
                    RadioButton rb= (RadioButton) view;
                    rb.setChecked(true);
                }
            });
            parentOfAllDynamicData.addView(dynamic_container);
        }
    }*/

    public void addViewIntoTable()
    {
        hmapStoreViewAndName.clear();
        for(Map.Entry<String, String> entry:hmapStoresFromDataBase.entrySet())
        {
            String storeID=entry.getKey().toString().trim();
            //StoreID,StoreName,DateAdded,CoverageAreaID,RouteNodeID,StoreCategoryType,StoreSectionCount,flgApproveOrRejectOrNoActionOrReVisit
            ////StoreName,DateAdded,CoverageAreaID,RouteNodeID,StoreCategoryType,StoreSectionCount,flgApproveOrRejectOrNoActionOrReVisit
            String StoreDetails=entry.getValue().toString().trim();
            String StoreName = StoreDetails.split(Pattern.quote("^"))[0];
            String DateAdded = StoreDetails.split(Pattern.quote("^"))[1];
            int CoverageAreaID=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[2]);
            int RouteNodeID=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[3]);
            String StoreCategoryType=StoreDetails.split(Pattern.quote("^"))[4];
            int StoreSectionCount=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[5]);
            int flgApproveOrRejectOrNoActionOrReVisit=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[6]);
            int sStat=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[7]);
            int flgOldNewStore=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[8]);
            int flgReMap=Integer.parseInt(StoreDetails.split(Pattern.quote("^"))[9]);
            /*String LatCode = StoreDetails.split(Pattern.quote("^"))[1];
            String LongCode = StoreDetails.split(Pattern.quote("^"))[2];
            */

            View dynamic_container=getLayoutInflater().inflate(R.layout.store_single_row,null);
            TextView storeTextview= (TextView) dynamic_container.findViewById(R.id.storeTextview);


            storeTextview.setTag(storeID+"^"+StoreName+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType+"^"+StoreSectionCount+"^"+flgOldNewStore);
            storeTextview.setText(StoreName);
            dynamic_container.setTag(storeID+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType);
            dynamic_container.setVisibility(View.VISIBLE);

            hmapStoreViewAndName.put(dynamic_container,StoreName);

            if(flgApproveOrRejectOrNoActionOrReVisit==1)
            {
                storeTextview.setTextColor(getResources().getColor(R.color.green));
            }
            if(flgApproveOrRejectOrNoActionOrReVisit==2)
            {
                storeTextview.setTextColor(getResources().getColor(R.color.red));
            }
            if(flgApproveOrRejectOrNoActionOrReVisit==3)
            {
                storeTextview.setTextColor(getResources().getColor(R.color.blue));
            }
            if(flgReMap==3)
            {
                storeTextview.setTextColor(Color.parseColor("#EF6C00"));
            }
            if(sStat==1)
            {
                if(flgOldNewStore==1)
                {
                    storeTextview.setText(StoreName +"  :(Newly Added)");
                }

                storeTextview.setTextColor(getResources().getColor(R.color.mdtp_accent_color_dark));
            }


            if(flgReMap==3)
            {
                storeTextview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view)
                    {
                        final TextView tvStores= (TextView) view;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                        alertDialog.setTitle(getText(R.string.genTermInformation));

                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getText(R.string.editStoreAlert));
                        alertDialog.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(view.getTag().toString().split(Pattern.quote("^"))[6].equals("1"))
                                {
                                    CommonInfo.flgNewStoreORStoreValidation=1;
                                }
                                else
                                {
                                    CommonInfo.flgNewStoreORStoreValidation=2;
                                }
/*
                                int chkFlgValue=mDataSource.fnCheckTableFlagValue("tblAllServicesCalledSuccessfull","flgAllServicesCalledOrNot");
                                if(chkFlgValue==1)
                                {
                                    dialog.dismiss();
                                    fnshowalertHalfDataRefreshed();
                                }
                                else
                                {*/
                                    String tagFromStore=   tvStores.getTag().toString().trim();
                                    Intent intent=new Intent(StorelistActivity.this,AddNewStore_DynamicSectionWise.class);

                                    //  storeTextview.setTag(storeID+"^"+StoreName+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType+"^"+StoreSectionCount);
                                    String storeID = tagFromStore.split(Pattern.quote("^"))[0];
                                    String   storeName = tagFromStore.split(Pattern.quote("^"))[1];

                                    int CoverageAreaID=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[2]);
                                    int RouteNodeID=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[3]);
                                    String StoreCategoryType=tagFromStore.split(Pattern.quote("^"))[4];
                                    int StoreSectionCount=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[5]);

                                    intent.putExtra("FLAG_NEW_UPDATE","UPDATE");


                                    intent.putExtra("StoreID",storeID);
                                    intent.putExtra("StoreName", storeName);
                                    intent.putExtra("CoverageAreaID", ""+CoverageAreaID);
                                    intent.putExtra("RouteNodeID", ""+RouteNodeID);
                                    intent.putExtra("StoreCategoryType", StoreCategoryType);
                                    intent.putExtra("StoreSectionCount", ""+StoreSectionCount);
                                    intent.putExtra("activityFrom", "StoreActivity");

                                    dialog.dismiss();
                                    StorelistActivity.this.startActivity(intent);

                                    finish();

                               // }


//--------------------------------------------------------------------------------------------------------------
                                //finishAffinity();

                            }
                        });
                        alertDialog.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }
                });

                RadioButton radioButton= (RadioButton) dynamic_container.findViewById(R.id.radiobtn);
                radioButton.setTag(storeID+"^"+StoreName);
                radioButton.setText(StoreName);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        unCheckRadioButton();
                        RadioButton rb= (RadioButton) view;
                        rb.setChecked(true);
                    }
                });
            }
            else
            {
                if(flgReMap==0 && sStat==0)
                {
                    storeTextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view)
                        {
                            final TextView tvStores= (TextView) view;
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                            alertDialog.setTitle(getText(R.string.genTermInformation));

                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(getText(R.string.editStoreAlert));
                            alertDialog.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if(view.getTag().toString().split(Pattern.quote("^"))[6].equals("1"))
                                    {
                                        CommonInfo.flgNewStoreORStoreValidation=1;
                                    }
                                    else
                                    {
                                        CommonInfo.flgNewStoreORStoreValidation=2;
                                    }

                                  /*  int chkFlgValue=mDataSource.fnCheckTableFlagValue("tblAllServicesCalledSuccessfull","flgAllServicesCalledOrNot");
                                    if(chkFlgValue==1)
                                    {
                                        dialog.dismiss();
                                        fnshowalertHalfDataRefreshed();
                                    }
                                    else
                                    {*/
                                        String tagFromStore=   tvStores.getTag().toString().trim();
                                        Intent intent=new Intent(StorelistActivity.this,AddNewStore_DynamicSectionWise.class);

                                        //  storeTextview.setTag(storeID+"^"+StoreName+"^"+CoverageAreaID+"^"+RouteNodeID+"^"+StoreCategoryType+"^"+StoreSectionCount);
                                        String storeID = tagFromStore.split(Pattern.quote("^"))[0];
                                        String   storeName = tagFromStore.split(Pattern.quote("^"))[1];

                                        int CoverageAreaID=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[2]);
                                        int RouteNodeID=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[3]);
                                        String StoreCategoryType=tagFromStore.split(Pattern.quote("^"))[4];
                                        int StoreSectionCount=Integer.parseInt(tagFromStore.split(Pattern.quote("^"))[5]);

                                        intent.putExtra("FLAG_NEW_UPDATE","UPDATE");


                                        intent.putExtra("StoreID",storeID);
                                        intent.putExtra("StoreName", storeName);
                                        intent.putExtra("CoverageAreaID", ""+CoverageAreaID);
                                        intent.putExtra("RouteNodeID", ""+RouteNodeID);
                                        intent.putExtra("StoreCategoryType", StoreCategoryType);
                                        intent.putExtra("StoreSectionCount", ""+StoreSectionCount);
                                        intent.putExtra("activityFrom", "StoreActivity");

                                        dialog.dismiss();
                                        StorelistActivity.this.startActivity(intent);

                                        finish();

                                   // }


//--------------------------------------------------------------------------------------------------------------
                                    //finishAffinity();

                                }
                            });
                            alertDialog.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();
                        }
                    });

                    RadioButton radioButton= (RadioButton) dynamic_container.findViewById(R.id.radiobtn);
                    radioButton.setTag(storeID+"^"+StoreName);
                    radioButton.setText(StoreName);
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            unCheckRadioButton();
                            RadioButton rb= (RadioButton) view;
                            rb.setChecked(true);
                        }
                    });
                }
            }
            parentOfAllDynamicData.addView(dynamic_container);
        }
    }


    public void unCheckRadioButton()
    {
        for(int i=0;parentOfAllDynamicData.getChildCount()>i;i++)
        {
            LinearLayout dd= (LinearLayout) parentOfAllDynamicData.getChildAt(i);
            RadioButton ff= (RadioButton) dd.getChildAt(0);
            ff.setChecked(false);
            int ss=parentOfAllDynamicData.getChildCount();

        }

    }
    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Information");
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. \nPlease select all settings on the next page!");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();

    }
    protected void startLocationUpdates() {
            try
            {
//                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            catch (SecurityException e)
            {

            }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private void updateUI() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    protected void stopLocationUpdates() {

//        LocationServices.FusedLocationApi.removeLocationUpdates(
//                mGoogleApiClient, this);




    }
    public void locationRetrievingAndDistanceCalculating()
    {
        LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(StorelistActivity.this,false, true, 20, 0);


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

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {

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




        if (fnAccuracy > 10000) {
            //mDataSource.open();
            mDataSource.deleteLocationTable();
           // mDataSource.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);
            //mDataSource.close();

            if (pDialog2STANDBY.isShowing()) {
                pDialog2STANDBY.dismiss();
            }


        } else {
            //mDataSource.open();
            mDataSource.deleteLocationTable();
          //  mDataSource.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);
            //mDataSource.close();


            if (flgAddButtonCliked == 1) {
                if (pDialog2STANDBY != null) {
                    if (pDialog2STANDBY.isShowing()) {
                        pDialog2STANDBY.dismiss();
                    }
                }

                RefreshBtn.setEnabled(false);
                String StoreName = "NA";
                if (!TextUtils.isEmpty(ed_search.getText().toString().trim())) {
                    StoreName = ed_search.getText().toString().trim();
                }


                Intent intent = new Intent(StorelistActivity.this, AddNewStore_DynamicSectionWise.class);
                intent.putExtra("FLAG_NEW_UPDATE", "NEW");
                intent.putExtra("StoreID", "0");

                intent.putExtra("StoreName", StoreName);
                intent.putExtra("CurrntRouteName", slctdRouteName);
                intent.putExtra("activityFrom", activityFrom);
                StorelistActivity.this.startActivity(intent);
                finish();
            } else if (flgAddButtonCliked == 2) {
                if (pDialog2STANDBY != null) {
                    if (pDialog2STANDBY.isShowing()) {
                        pDialog2STANDBY.dismiss();
                    }
                }


                AddStoreBtn.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(StorelistActivity.this, AddNewStore_DynamicSectionWise.class);
                String storeID = tagFromStore.split(Pattern.quote("^"))[0];
                String storeName = tagFromStore.split(Pattern.quote("^"))[1];
                intent.putExtra("FLAG_NEW_UPDATE", "UPDATE");
                intent.putExtra("StoreID", storeID);
                intent.putExtra("StoreName", storeName);
                intent.putExtra("CurrntRouteName", slctdRouteName);
                intent.putExtra("activityFrom", activityFrom);
                StorelistActivity.this.startActivity(intent);
                finish();
            }
            if (flgAddButtonCliked == 0) {
                hmapOutletListForNear = mDataSource.fnGetALLOutletMstrSM();
                System.out.println("SHIVAM" + hmapOutletListForNear);
                if (hmapOutletListForNear != null) {
                    for (Map.Entry<String, String> entry : hmapOutletListForNear.entrySet()) {
                        int DistanceBWPoint = 1000;
                        String outID = entry.getKey().toString().trim();
                        //  String PrevAccuracy = entry.getValue().split(Pattern.quote("^"))[0];
                        String PrevLatitude = entry.getValue().split(Pattern.quote("^"))[0];
                        String PrevLongitude = entry.getValue().split(Pattern.quote("^"))[1];

                        // if (!PrevAccuracy.equals("0"))
                        // {
                        if (!PrevLatitude.equals("0")) {
                            try {
                                Location locationA = new Location("point A");
                                locationA.setLatitude(Double.parseDouble(fnLati));
                                locationA.setLongitude(Double.parseDouble(fnLongi));

                                Location locationB = new Location("point B");
                                locationB.setLatitude(Double.parseDouble(PrevLatitude));
                                locationB.setLongitude(Double.parseDouble(PrevLongitude));

                                float distance = locationA.distanceTo(locationB);
                                DistanceBWPoint = (int) distance;

                                hmapOutletListForNearUpdated.put(outID, "" + DistanceBWPoint);
                            } catch (Exception e) {
                            }
                        }
                        // }
                    }
                }

                if (hmapOutletListForNearUpdated != null) {
                    //mDataSource.open();
                    for (Map.Entry<String, String> entry : hmapOutletListForNearUpdated.entrySet()) {
                        String outID = entry.getKey().toString().trim();
                        String DistanceNear = entry.getValue().trim();
                        if (outID.equals("853399-a1445e87daf4-NA")) {
                            System.out.println("Shivam Distance = " + DistanceNear);
                        }
                        if (!DistanceNear.equals("")) {
                            //853399-81752acdc662-NA
                            if (outID.equals("853399-a1445e87daf4-NA")) {
                                System.out.println("Shvam Distance = " + DistanceNear);
                            }
                            mDataSource.UpdateStoreDistanceNearSM(outID, Integer.parseInt(DistanceNear));
                        }
                    }
                    //mDataSource.close();
                }
                //send to storeListpage page
                //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget
                int flagtoShowStorelistOrAddnewStore = mDataSource.fncheckCountNearByStoreExistsOrNotSM(CommonInfo.DistanceRange);

                if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
                    pDialog2STANDBY.dismiss();
                }
                if (flagtoShowStorelistOrAddnewStore == 1) {
                    getDataFromDatabaseToHashmap();
                    if (parentOfAllDynamicData.getChildCount() > 0) {
                        parentOfAllDynamicData.removeAllViews();
                        // dynamcDtaContnrScrollview.removeAllViews();
                        addViewIntoTable();
                    } else {
                        addViewIntoTable();
                    }

                       /* Intent intent =new Intent(LauncherActivity.this,StorelistActivity.class);
                        LauncherActivity.this.startActivity(intent);
                        finish();*/

                } else {
                    //send to AddnewStore directly
                           /* Intent intent=new Intent(LauncherActivity.this,AddNewStore_DynamicSectionWise.class);
                            intent.putExtra("FLAG_NEW_UPDATE","NEW");
                            LauncherActivity.this.startActivity(intent);
                            finish();*/
                }
            }

        }
    }


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









    private class FullSyncDataNow extends AsyncTask<Void, Void, Integer>
    {


        ProgressDialog pDialogGetStores;
        public FullSyncDataNow(StorelistActivity activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            File LTFoodXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!LTFoodXMLFolder.exists())
            {
                LTFoodXMLFolder.mkdirs();
            }


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage("Submitting Pending Data...");
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Integer doInBackground(Void... params)
        {

            int responseCode=0;
            try
            {



                File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                // check number of files in folder
                String [] AllFilesName= checkNumberOfFiles(del);


                if(AllFilesName!=null && AllFilesName.length>0)
                {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


                    for(int vdo=0;vdo<AllFilesName.length;vdo++)
                    {
                        String fileUri=  AllFilesName[vdo];


                        //System.out.println("Sunil Again each file Name :" +fileUri);

                        if(fileUri.contains(".zip"))
                        {
                            File file = new File( Environment.getExternalStorageDirectory() + "/"+ CommonInfo.OrderXMLFolder+"/" +fileUri);
                            file.delete();
                        }
                        else
                        {
                            String f1=Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.OrderXMLFolder + "/" +fileUri;
                            // System.out.println("Sunil Again each file full path"+f1);
                            try
                            {
                                responseCode =upLoad2Server(f1,fileUri);
                            }
                            catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        if(responseCode!=200)
                        {
                            break;
                        }
                    }

                }
                else
                {
                    responseCode=200;
                }







            } catch (Exception e)
            {
                responseCode=0;
                e.printStackTrace();
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }
            }
            return responseCode;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(result!=200)
            {


                showErrorAlert("Error while uploading data");


            }
            else
            {
                mDataSource.deleteXmlTable("4");
                showErrorAlert("Data has been successfully submitted");
            }


        }
    }

    public void delXML(String delPath)
    {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
    }

    public static String[] checkNumberOfFiles(File dir)
    {
        int NoOfFiles=0;
        String [] Totalfiles = null;

        if (dir.isDirectory())
        {
            String[] children = dir.list();
            NoOfFiles=children.length;
            Totalfiles=new String[children.length];

            for (int i=0; i<children.length; i++)
            {
                Totalfiles[i]=children[i];
            }
        }
        return Totalfiles;
    }

    public static void zip(String[] files, String zipFile) throws IOException
    {
        BufferedInputStream origin = null;
        final int BUFFER_SIZE = 2048;

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                }
                finally {
                    origin.close();
                }
            }
        }

        finally {
            out.close();
        }
    }



    public  int upLoad2Server(String sourceFileUri,String fileUri)
    {

        fileUri=fileUri.replace(".xml", "");

        String fileName = fileUri;
        String zipFileName=fileUri;

        String newzipfile = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.OrderXMLFolder+"/" + fileName + ".zip";

        sourceFileUri=newzipfile;

        xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/"+ CommonInfo.OrderXMLFolder+"/" + fileName + ".xml";


        try
        {
            zip(xmlForWeb,newzipfile);
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            //java.io.FileNotFoundException: /359648069495987.2.21.04.2016.12.44.02: open failed: EROFS (Read-only file system)
        }


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;


        File file2send = new File(newzipfile);

        String urlString="";
        if(zipFileName.contains(".xml"))
        {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName;

        }
        else
        {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName+".xml";

        }

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(file2send);
            URL url = new URL(urlString);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("zipFileName", zipFileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + zipFileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            //Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

            if(serverResponseCode == 200)
            {
                syncFLAG = 1;


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                // editor.remove(xmlForWeb[0]);
                editor.putString(fileUri, ""+4);
                editor.commit();

                String FileSyncFlag=pref.getString(fileUri, ""+1);
                mDataSource.upDateTblXmlFile(fileName);
                delXML(xmlForWeb[0].toString());


            }
            else
            {
                syncFLAG = 0;
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex)
        {
            ex.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }




        return serverResponseCode;

    }


    public void showErrorAlert(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(StorelistActivity.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setNeutralButton(R.string.txtOk,
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
    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(StorelistActivity.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(R.string.genTermNoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.txtOk,
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

    public void alertSubmitPendingData()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(StorelistActivity.this);
        alertDialogNoConn.setTitle(R.string.pending_data);
        alertDialogNoConn.setMessage(R.string.submit_pending_data);
        alertDialogNoConn.setNeutralButton(R.string.txtOk,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                        // finish();
                    }
                });

        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }


   /* public void funForCheckSaveExitData()
    {
        try
        {
            int checkSaveExitData = mDataSource.CheckIfSavedDataExist();
            if(checkSaveExitData==1)
            {
                long  syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String StampEndsTime = df.format(dateobj);


                //mDataSource.open();
                mDataSource.UpdateStoreFlagForSaveExit("1");
                //mDataSource.close();


                SimpleDateFormat df1 = new SimpleDateFormat(imei+"."+ "dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

                String newfullFileName=df1.format(dateobj);




                try {


                    File LTFoodxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.LTFoodOrderXMLFolder);

                    if (!LTFoodxmlFolder.exists())
                    {
                        LTFoodxmlFolder.mkdirs();

                    }


                    DA.open();
                    DA.export(CommonInfo.DATABASE_NAME, newfullFileName);
                    DA.close();



                    mDataSource.savetbl_XMLfiles(newfullFileName, "3");
                    //mDataSource.open();

                    mDataSource.UpdateStoreFlagForFinalSubmit("3");
                    mDataSource.UpdateStoreFlagForALLSubmit("5");

                    //mDataSource.close();



                } catch (Exception e)
                {

                    e.printStackTrace();

                }
            }
        }
        catch(Exception e)
        {

        }
    }*/


    private class ImageSync extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if(pDialog2STANDBY == null)
            {
                pDialog2STANDBY=new ProgressDialog(StorelistActivity.this);
            }
            pDialog2STANDBY.setTitle("Please Wait");
            pDialog2STANDBY.setMessage("Uploading Image...");
            pDialog2STANDBY.setIndeterminate(false);
            pDialog2STANDBY.setCancelable(false);
            pDialog2STANDBY.setCanceledOnTouchOutside(false);
            /*pDialog2STANDBY.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    imgSyncTask.cancel(true);
                    dialog.dismiss();
                }
            });*/
            pDialog2STANDBY.show();
        }
        @Override
        protected Boolean doInBackground(Void... args)
        {
            boolean isErrorExist=false;


            try
            {
                //mDataSource.upDateCancelTask("0");
                ArrayList<String> listImageDetails=new ArrayList<String>();

                listImageDetails= mDataSource.getImageDetails(5);

                if(listImageDetails!=null && listImageDetails.size()>0)
                {
                    for(String imageDetail:listImageDetails)
                    {
                        String tempIdImage=imageDetail.split(Pattern.quote("^"))[0].toString();
                        String imagePath=imageDetail.split(Pattern.quote("^"))[1].toString();
                        String imageName=imageDetail.split(Pattern.quote("^"))[2].toString();
                        String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+imageName;
                        File fImage = new File(file_dj_path);
                        if (fImage.exists())
                        {
                            uploadImage(imagePath, imageName, tempIdImage);
                        }



                    }
                }


            }
            catch (Exception e)
            {
                isErrorExist=true;
            }

            finally
            {
                Log.i("SvcMgr", "Service Execution Completed...");
            }

            return isErrorExist;
        }

        @Override
        protected void onPostExecute(Boolean resultError)
        {
            super.onPostExecute(resultError);

            if(pDialog2STANDBY.isShowing())
            {
                pDialog2STANDBY.dismiss();

            }


            if(resultError)   // if Webservice showing exception or not excute complete properly
            {
                showErrorAlert(getString(R.string.uploading_error));
            }
            else
            {
                new FullSyncDataNow(StorelistActivity.this).execute();

            }

        }
    }



    public void uploadImage(String sourceFileUri,String fileName,String tempIdImage) throws IOException
    {
        BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
        IMGoptions01.inDither=false;
        IMGoptions01.inPurgeable=true;
        IMGoptions01.inInputShareable=true;
        IMGoptions01.inTempStorage = new byte[16*1024];

        //finalBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fnameIMG,IMGoptions01), 640, 480, false);

        Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(sourceFileUri).getPath(),IMGoptions01);

//			/Uri.parse(sourceFileUri).getPath()
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream); //compress to which format you want.

        //b is the Bitmap
        //int bytes = bitmap.getWidth()*bitmap.getHeight()*4; //calculate how many bytes our image consists of. Use a different value than 4 if you don't use 32bit images.

        //ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        //bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
        //byte [] byte_arr = buffer.array();


        //     byte [] byte_arr = stream.toByteArray();
        String image_str = BitMapToString(bitmap);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

        ////System.out.println("image_str: "+image_str);

        stream.flush();
        stream.close();
        //buffer.clear();
        //buffer = null;
        bitmap.recycle();
        nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
       // nameValuePairs.add(new BasicNameValuePair("TempID", tempIdImage));
        nameValuePairs.add(new BasicNameValuePair("comment","NA"));
        nameValuePairs.add(new BasicNameValuePair("storeID","0"));
        nameValuePairs.add(new BasicNameValuePair("date","0"));
        nameValuePairs.add(new BasicNameValuePair("routeID","0"));
        try
        {

            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);

            //HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);

            HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout+2000);


            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppost = new HttpPost(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath );




            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

            String the_string_response = convertResponseToString(response);
            if(the_string_response.equals("SUCCESS"))
            {
                mDataSource.updateSSttImage(fileName, 4);

                String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+fileName;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }
            }

        }catch(Exception e)
        {

            System.out.println(e);
            //	IMGsyOK = 1;

        }
    }
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(StorelistActivity.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            StorelistActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0)
        {
        }
        else
        {
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }


   /* public String BitMapToString(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();
        h1=h1/8;
        w1=w1/8;
        bitmap=Bitmap.createScaledBitmap(bitmap, w1, h1, true);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }*/
   public String BitMapToString(Bitmap bitmap)
   {
       int h1=bitmap.getHeight();
       int w1=bitmap.getWidth();

       if(w1 > 768 || h1 > 1024){
           bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);

       }


       else {

           bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
       }

       ByteArrayOutputStream baos=new  ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
       byte [] arr=baos.toByteArray();
       String result=Base64.encodeToString(arr, Base64.DEFAULT);
       return result;
   }
    public boolean checkRefreshDataUnSuccess()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed;
        boolean flagForError=false;
        /*if(sharedPreferences.contains("ServerDate"))
        {
            if(sharedPreferences.getString("ServerDate", "").equals("0"))
            {
                flagForError=true;
            }
        }
        else
        {
            flagForError=true;

        }*/
        return flagForError;

    }


    public void alertRefreshOrFinish()
    {

        AlertDialog.Builder alertDialogBuilderNEw11 = new AlertDialog.Builder(StorelistActivity.this);

        // set title
        alertDialogBuilderNEw11.setTitle("Information");

        // set dialog message
        alertDialogBuilderNEw11.setMessage("Are you sure to refresh complete Data? As all saved data will be lost.");
        alertDialogBuilderNEw11.setCancelable(false);
        alertDialogBuilderNEw11.setPositiveButton("Refresh Again",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogintrfc,int id) {
                // if this button is clicked, close
                // current activity
                dialogintrfc.cancel();
                if(CommonInfo.VanLoadedUnloaded==1)
                {
                    showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

                }
                else {
                    if (isOnline()) {
                        fnStartProcedureOfRefreshData();

                    } else {
                        showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                        return;

                    }
                }

                //onCreate(new Bundle());
            }
        });

        alertDialogBuilderNEw11.setNegativeButton("Close App",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogintrfc, int which) {
                        // //System.out.println("value ofwhatTask after no button pressed by sunil"+whatTask);

                        dialogintrfc.dismiss();
                        finishAffinity();
                    }
                });

        alertDialogBuilderNEw11.setIcon(R.drawable.info_ico);
        AlertDialog alert121 = alertDialogBuilderNEw11.create();
        alert121.show();
    }
  /*  public String getAddressOfProviders(String latti,String longi){

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
        geocoder = new Geocoder(StorelistActivity.this, Locale.ENGLISH);



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
    public  void routeListSpinner(){

        hmapRoutlist= mDataSource.fnCompleteRouteList();
        DB_NAME=changeHmapToArrayKey(hmapRoutlist);

        sp_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder	 alertDialog = new AlertDialog.Builder(StorelistActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                convertView = (View) inflater.inflate(R.layout.activity_list, null);
                EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                inputSearch.setVisibility(View.GONE);
                listDominantBrand = (ListView)convertView. findViewById(R.id.list_view);
                adapterDominantBrand = new ArrayAdapter<String>(StorelistActivity.this, R.layout.list_item, R.id.product_name, DB_NAME);
                listDominantBrand.setAdapter(adapterDominantBrand);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select Route");

                listDominantBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                        String abc=listDominantBrand.getItemAtPosition(arg2).toString().trim();

                        if(!(sp_Route.getText().equals(abc))){
                            sp_Route.setText(abc);
                            if(hmapRoutlist.containsKey(abc))
                            {
                                slctdRouteName=abc;
                               //load list here
                                if(hmapStoresFromDataBase!=null){
                                    hmapStoresFromDataBase.clear();
                                }
                                //get storelist against routeID
                                if(abc.equals("All"))
                                {
                                    //hmapStoresFromDataBase=   mDataSource.fnGeStoreListSM(CommonInfo.DistanceRange);
                                    hmapStoresFromDataBase = mDataSource.fnGeStoreListAllForSO(CoverageID,RouteID);

                                }
                                else{

                                   // hmapStoresFromDataBase=   mDataSource.fnGeStoreListAgainstRoute(Integer.parseInt(hmapRoutlist.get(abc)));
                                    hmapStoresFromDataBase = mDataSource.fnGeStoreListAllForSO(CoverageID,Integer.parseInt(hmapRoutlist.get(abc)));
                                }

                                if(parentOfAllDynamicData.getChildCount()>0){
                                    parentOfAllDynamicData.removeAllViews();
                                    addViewIntoTable();
                                }
                                else
                                {
                                    addViewIntoTable();
                                }

                            }
                        }

                        ad.dismiss();

                    }
                });
                ad=alertDialog.show();
            }
        });

    }

    public void showAlertSingleWareHouseStockconfirButtonInfo(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                        Intent intent=new Intent(StorelistActivity.this,AllButtonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }


    public void fnStartProcedureOfRefreshData()
    {
        AlertDialog.Builder alertDialogBuilderNEw11 = new AlertDialog.Builder(StorelistActivity.this);

        // set title
        alertDialogBuilderNEw11.setTitle(getResources().getString(R.string.genTermNoDataConnection));

        // set dialog message
        alertDialogBuilderNEw11.setMessage(getResources().getString(R.string.RefreshDataMsg));
        alertDialogBuilderNEw11.setCancelable(false);
        alertDialogBuilderNEw11.setPositiveButton(getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogintrfc, int id) {
                // if this button is clicked, close
                // current activity
                dialogintrfc.cancel();
                try {
                    try
                    {
                        // new GetRouteInfo().execute();
                        CommonFunction.getAllMasterTableModelData(StorelistActivity.this,imei, CommonInfo.RegistrationID,"Please wait Refreshing data.",1);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                } catch (Exception e) {

                }

                //onCreate(new Bundle());
            }
        });

        alertDialogBuilderNEw11.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogintrfc, int which) {
                        // //System.out.println("value ofwhatTask after no button pressed by sunil"+whatTask);

                        dialogintrfc.dismiss();
                    }
                });

        alertDialogBuilderNEw11.setIcon(R.drawable.info_ico);
        AlertDialog alert121 = alertDialogBuilderNEw11.create();
        alert121.show();
    }

    @Override
    public void success() {
        Intent intent = new Intent(StorelistActivity.this, StorelistActivity.class);
        intent.putExtra("activityFrom", "AllButtonActivity");
        startActivity(intent);
        finish();
    }

    @Override
    public void failure() {
        showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrRetrieving));
    }

    public void showAlertException(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StorelistActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                dialog.dismiss();
                try
                {
                    // new GetRouteInfo().execute();
                    CommonFunction.getAllMasterTableModelData(StorelistActivity.this,imei, CommonInfo.RegistrationID,"Please wait Refreshing data.",1);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
