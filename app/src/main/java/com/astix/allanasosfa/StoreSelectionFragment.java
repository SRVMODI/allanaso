package com.astix.allanasosfa;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.astix.sancussosfa.database.AppDataSource;
import com.astix.sancussosfa.model.TblCoverageDsr;
import com.astix.sancussosfa.model.TblStoreListMaster;
import com.astix.sancussosfa.sync.DatabaseAssistant;
import com.astix.sancussosfa.utils.AppUtils;
import com.astix.sancussosfa.utils.PreferenceManager;
import com.example.gcm.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


//import com.astix.sfatju.R;

public class StoreSelectionFragment extends BaseActivity implements InterfaceRetrofit {
    //public static HashMap<String, String> hmapStoreIdSstat=new HashMap<String, String>();

    public static final String DATASUBDIRECTORYForText = CommonInfo.TextFileFolder;
    public static final int PAGE_START = 1;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    static String selected_manager = "NA";
    static String seleted_routeIDType = "0";
    static int Selected_manager_Id = 0;
    static int flgChangeRouteOrDayEnd = 0;
    private final long startTime = 15000;
    private final long interval = 200;
    public boolean addStoreBtnClick = false;
    public String[] xmlForWeb = new String[1];
    public int syncFLAG = 0;
    public String currSysDate;
    public int chkFlgForErrorToCloseApp = 0;
    public String passDate;
    public SimpleDateFormat sdf;
    public String fDate;
    public String userDate;
    public String pickerDate;
    public String imei;
    public String[] storeList;
    public String[] storeRouteIdType;
    public int flgDayEndOrChangeRoutenew = 0;
    public String Noti_text = "Null";
    public int MsgServerID = 0;
    public boolean[] checks;
    public int noLOCflag = 0;
    public ProgressDialog pDialogSync;
    public long syncTIMESTAMP;
    public String fullFileName1;
    public String[] storeCode;
    public String[] storeName;
    public String[] storeStatus;
    public String[] storeLastVisitDate;
    public String[] storeFlgProductive;
    public String[] storeNoOfBrands;
    public String[] storeP3MValue;
    public String[] storeMTDValue;
    public String[] storeLastOrderDate;
    public String[] StoreflgSubmitFromQuotation;
    public String[] storeCloseStatus;
    public String[] storeNextDayStatus;
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
    public Location firstLoc;
    public float acc;
    public Location location2;
    public String[] StoreList2Procs;
    public Location finalLocation;
    public int valDayEndOrChangeRoute = 0; // 1=Clicked on Day End Button, 2=Clicked on Change Route Button
    public String[] route_name;
    public String[] route_name_id;
    public String selected_route_id = "0";
    public String temp_select_routename = "NA";
    public String temp_select_routeid = "NA";
    public String rID;
    public PowerManager pm;
    public PowerManager.WakeLock wl;
    public Location location;
    public String AllProvidersLocation = "";
    public String FusedLocationLatitudeWithFirstAttempt = "0";
    public String FusedLocationLongitudeWithFirstAttempt = "0";
    public String FusedLocationAccuracyWithFirstAttempt = "0";
    public String FusedLocationLatitude = "0";
    public String FusedLocationLongitude = "0";
    public String FusedLocationProvider = "";
    public String FusedLocationAccuracy = "0";
    public String GPSLocationLatitude = "0";
    public String GPSLocationLongitude = "0";
    public String GPSLocationProvider = "";
    public String GPSLocationAccuracy = "0";
    public String NetworkLocationLatitude = "0";
    public String NetworkLocationLongitude = "0";
    public String NetworkLocationProvider = "";
    public String NetworkLocationAccuracy = "0";
    public AppLocationService appLocationService;
    public String fnAccurateProvider = "";
    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;
    public String JointVisitId;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    int serverResponseCode = 0;
    InputStream inputStream;
    String[] Manager_names = null;
    String[] Route_names = null;
    HashMap<String, String> hmapManagerNameManagerIdDetails = new HashMap<String, String>();
    HashMap<String, String> hmapRouteIdNameDetails = new HashMap<String, String>();
    boolean serviceException = false;
    Dialog dialog;
    CheckBox check1, check2;
    int battLevel = 0;
    LinkedHashMap<String, String> hmapOutletListForNear = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNearUpdated = new LinkedHashMap<String, String>();
    ProgressDialog pDialogGetStores;
    ProgressDialog mProgressDialog;
    boolean bool = true;
    DatabaseAssistant DA;
    int closeList = 0;
    int whatTask = 0;
    String whereTo = "11";
    ArrayList mSelectedItems = new ArrayList();
    int prevSel = 0;
    int prevID;
    ArrayList<String> stIDs;
    ArrayList<String> stNames;
    AppDataSource dbengine;
    ProgressDialog pDialog2;
    String FWDCLname;
    String BCKCLname;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    double latitude;
    double longitude;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    String fusedData;
    LocationRequest mLocationRequest;
    LinkedHashMap<String, String> hmapStoreLatLongDistanceFlgRemap = new LinkedHashMap<String, String>();
    List<TblStoreListMaster> tblStoreListMasterList = new ArrayList<TblStoreListMaster>();
    List<TblStoreListMaster> tblStoreListMasterList_all = new ArrayList<TblStoreListMaster>();
    List<TblCoverageDsr> tblCoverageDsrs = new ArrayList<>();
    List<TblCoverageDsr> tblRouteNames = new ArrayList<>();
    ArrayList<String> coverageArea = new ArrayList<String>();
    ArrayList<String> coverageIDS = new ArrayList<String>();
    List<TblStoreListMaster> allStoresList = new ArrayList<>();


    String coverageID="0";
    String routeID = "0";

    @BindView(R.id.image_Notification)
    ImageView image_Notification;
    /* @BindView(R.id.btn_endvisit)
     Button btn_endvisit;*/
    @BindView(R.id.txtview_selectstoretext)
    TextView txtview_selectstoretext;
    @BindView(R.id.btn_nearStores)
    Button btn_nearStores;
    /*    @BindView(R.id.ll_ForStoreList)
        LinearLayout ll_ForStoreList;*/
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.ll_manager)
    RelativeLayout ll_manager;

    @BindView(R.id.rl_for_other)
    RelativeLayout rl_for_other;
    @BindView(R.id.streetid)
    EditText ed_Street;
    @BindView(R.id.spinner_manager)
    Spinner spinner_manager;
    @BindView(R.id.img_side_popUp)
    ImageView img_side_popUp;
    @BindView(R.id.txt_Route)
    TextView txt_Route;


    SearchView searchView;
    @BindView(R.id.store_list)
    RecyclerView store_list;
    StoreListAdapter storeListAdapter = null;
    int itemCount = 0;
    LinearLayoutManager mlayoutmanager;// = new LinearLayoutManager(this);
    private Activity mContext;
    private int selected = 0;
    private PreferenceManager mPreferenceManager;
    private int mFeedbackType = 0;
    private int flgJointVisit;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    Spinner route_list,dsr_list;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };


    // *****SYNC******
    private View view;
    private ArrayList<String[]> arrStorOtherDetails;

    @Override
    public void success() {


//		getStoreListAndType();
//		setStoresList();

//		Intent i=new Intent(mContext,LauncherActivity.class);
//		i.putExtra("imei", imei);
//		startActivity(i);
//		mContext.finish();
    }

    @Override
    public void failure() {
        showAlertException(getResources().getString(R.string.txtError), getResources().getString(R.string.txtErrRetrieving));
    }

    public void DayEnd() {


        AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.titlebar, null);
        alertDialogSubmitConfirm.setCustomTitle(view);
        TextView title_txt = view.findViewById(R.id.title_txt);
        title_txt.setText(getText(R.string.PleaseConformMsg));


        View view1 = inflater.inflate(R.layout.custom_alert_dialog, null);
        view1.setBackgroundColor(Color.parseColor("#1D2E3C"));
        TextView msg_txt = view1.findViewById(R.id.msg_txt);
        msg_txt.setText(getText(R.string.genTermDayEndAlert));
        alertDialogSubmitConfirm.setView(view1);
        alertDialogSubmitConfirm.setInverseBackgroundForced(true);


        alertDialogSubmitConfirm.setNeutralButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //dbengine.open();

                if (dbengine.GetLeftStoresChk() == true) {

                    //dbengine.close();

                    whatTask = 3;

                    /*try {

//                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }
*/
                } else {

                   /* try {
                        //dbengine.close();
                        whatTask = 1;
                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }*/


                }

            }
        });

        alertDialogSubmitConfirm.setNegativeButton(R.string.AlertDialogNoButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogSubmitConfirm.create();
        alert.show();

    }

    public void DayEndWithoutalert() {


//        SyncNow();

    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_store_selection);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Store List");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {           // OnClick Handling on Tool Navigation Button
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                    getSupportFragmentManager().popBackStackImmediate();
                else
                    finish();
            }
        });
        mContext = this;
        mlayoutmanager = new LinearLayoutManager(mContext);
        //  new AppUtils().callParentMethod(mContext);
        dbengine = new AppDataSource(mContext);
        imei = AppUtils.getIMEI(mContext);
        DA = new DatabaseAssistant(mContext);
       // mPreferenceManager = PreferenceManager.getInstance(mContext);
        mProgressDialog = new ProgressDialog(this);

        store_list.setLayoutManager(mlayoutmanager);

        tblCoverageDsrs = dbengine.fngetTblCoverageDsr();
        coverageArea.add(0,"Select DSR");
        coverageIDS.add(0,"0");


        for (TblCoverageDsr tblCoverageDsr : tblCoverageDsrs) {
            if (tblCoverageDsr.getCoverageArea() != null || !tblCoverageDsr.getCoverageArea().equals("")) {
                coverageArea.add(tblCoverageDsr.getCoverageArea());
                coverageIDS.add(tblCoverageDsr.getCoverageID());
            }

        }

        route_list=(Spinner) findViewById(R.id.route_list);
        dsr_list=(Spinner) findViewById(R.id.dsr_list);
      //  try {

            ArrayAdapter adapterDsrList = new ArrayAdapter(StoreSelectionFragment.this, android.R.layout.simple_spinner_item, coverageArea);
            adapterDsrList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dsr_list.setAdapter(adapterDsrList);


      /*  }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
*/


        dsr_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String coverage_Area = dsr_list.getSelectedItem().toString();

                 coverageID = dbengine.getCoverageID(coverage_Area);


                //if (!coverageID.equals("0")) {

                //}
                    if(!coverageID.equals(""))
                        setRouteNames(coverageID);
                    else {
                        coverageID = "0";
                        setRouteNames(coverageID);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



      /*  store_list.addOnScrollListener(new PaginationScrollListener(mlayoutmanager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                preparedListItem();


            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
*/
        searchView = (SearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                storeListAdapter.filter(text);
                return false;
            }
        });

        // setStoresList();
       // getStoreList();

    }

    private void getStoreNewList(String route_ID) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog.setMessage("Loading data..");
                mProgressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                allStoresList.clear();
                tblStoreListMasterList = dbengine.fngetStoreProfileByRouteAlsoAgainstCoverageArea(Integer.parseInt(coverageID),route_ID);
                tblStoreListMasterList_all = dbengine.fngetStoreProfileWithCoverageArea(Integer.parseInt(coverageID),route_ID);

                if(tblStoreListMasterList.size()==0)
                {
                    mProgressDialog.dismiss();
                }
                else
                {
                    allStoresList.addAll(tblStoreListMasterList);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mProgressDialog.dismiss();
                setStoresList();



            }
        }.execute();


    }

    private void getStoreList() {
       /* if (ll_ForStoreList != null) {
            ll_ForStoreList.removeAllViews();
        }*/

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //mProgressDialog = ProgressDialog.show(StoreSelectionFragment.this,"","Loading Data...");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                storeList = dbengine.FetchStoreList(rID);
                storeStatus = dbengine.FetchStoreStatus(rID);
                arrStorOtherDetails = dbengine.FetchStoreOtherDetails();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                setStoresList();
            }
        }.execute();
    }

    public void setStoresList() {


        mProgressDialog.dismiss();


       if(allStoresList.size()==0)
       {
           /*showAlertSingleButtonInfo("Please select a DS");*/
           searchView.setVisibility(View.GONE);

       }
       else
       {
           searchView.setVisibility(View.VISIBLE);
       }
        storeListAdapter = new StoreListAdapter(this, allStoresList);
        store_list.setAdapter(storeListAdapter);
        storeListAdapter.notifyDataSetChanged();
        ((SimpleItemAnimator) store_list.getItemAnimator()).setSupportsChangeAnimations(false);

    }


    protected void open_pop_up() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.selection_header_custom);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.side_dialog_animation;
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();
        parms.gravity = Gravity.TOP | Gravity.LEFT;
        parms.height = ViewGroup.LayoutParams.MATCH_PARENT;
        parms.dimAmount = (float) 0.5;
        final Button butn_Census_report = dialog.findViewById(R.id.butn_Census_report);
        if (CommonInfo.flgDrctslsIndrctSls == 1) {
            butn_Census_report.setVisibility(View.GONE);
        }
        butn_Census_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddedOutletSummaryReportActivity.class);
                startActivity(intent);
                // finish();
            }
        });
        butn_Census_report.setVisibility(View.GONE);

        final Button btn_uploadPending_data = dialog.findViewById(R.id.btn_uploadPending_data);


        final Button butn_refresh_data = dialog.findViewById(R.id.butn_refresh_data);
        final Button but_day_end = dialog.findViewById(R.id.mainImg1);
        final Button changeRoute = dialog.findViewById(R.id.changeRoute);
        changeRoute.setVisibility(View.GONE);
        final Button btnewAddedStore = dialog.findViewById(R.id.btnewAddedStore);
        btnewAddedStore.setVisibility(View.GONE);


        final Button btnRemainingStockStatus = dialog.findViewById(R.id.btnRemainingStockStatus);
        btnRemainingStockStatus.setVisibility(View.GONE);


        final Button butHome = dialog.findViewById(R.id.butHome);
        butHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                mContext.finish();
            }
        });

        final Button btnTargetVsAchieved = dialog.findViewById(R.id.btnTargetVsAchieved);
        btnTargetVsAchieved.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
//				 Intent intent = new Intent(StoreSelection.this, TargetVsAchievedActivity.class);
//				 intent.putExtra("imei", imei);
//				 intent.putExtra("userDate", userDate);
//				 intent.putExtra("pickerDate", pickerDate);
//				 intent.putExtra("rID", rID);
//				 intent.putExtra("Pagefrom", "1");
//				 //intent.putExtra("back", "0");
//				 startActivity(intent);
//				 finish();


            }
        });
        btnTargetVsAchieved.setVisibility(View.GONE);

        final Button btnVersion = dialog.findViewById(R.id.btnVersion);
        btnVersion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                btnVersion.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
            }
        });

        String ApplicationVersion = CommonInfo.AppVersionID;
        btnVersion.setText(getResources().getString(R.string.VersionNo) + ApplicationVersion);


        final Button btnChangeLanguage = dialog.findViewById(R.id.btnChangeLanguage);
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final Dialog dialogLanguage = new Dialog(mContext);
                dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialogLanguage.setCancelable(false);
                dialogLanguage.setContentView(R.layout.language_popup);

                TextView textviewEnglish = dialogLanguage.findViewById(R.id.textviewEnglish);
                TextView textviewHindi = dialogLanguage.findViewById(R.id.textviewHindi);
                TextView textviewGujrati = dialogLanguage.findViewById(R.id.textviewGujrati);

                textviewEnglish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("en");
                    }
                });
                textviewHindi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("hi");
                    }
                });
                textviewGujrati.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("gu");
                    }
                });
                dialogLanguage.show();


            }
        });


        but_day_end.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //checking that dsr fill registration form or not for flag 0

                but_day_end.setBackgroundColor(Color.GREEN);
                closeList = 0;
                valDayEndOrChangeRoute = 1;


                dialog.dismiss();
                DayEndWithoutalert();


            }
        });


        butn_refresh_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                butn_refresh_data.setBackgroundColor(Color.GREEN);
                if (CommonInfo.VanLoadedUnloaded == 1) {
                    showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

                } else {
                    if (AppUtils.isOnline(mContext)) {
                        fnStartProcedureOfRefreshData();

                    } else {
                        AppUtils.showAlertSingleButtonError(mContext, getResources().getString(R.string.NoDataConnectionFullMsg));
                        return;

                    }
                }

                dialog.dismiss();

            }

        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void fnStartProcedureOfRefreshData() {
        AlertDialog.Builder alertDialogBuilderNEw11 = new AlertDialog.Builder(mContext);

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
                    try {
                        // new GetRouteInfo().execute();
//                        CommonFunction.getAllMasterTableModelData(mContext, CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

                    } catch (Exception e) {
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


    public void showAlertException(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                try {
                    // new GetRouteInfo().execute();
//                    CommonFunction.getAllMasterTableModelData(mContext, CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

                } catch (Exception e) {
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

    public void showAlertSingleWareHouseStockconfirButtonInfo(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        mContext.finish();
                    }
                }).create().show();
    }


    public void saveLocale(String lang) {


        mPreferenceManager.setValue("Language", lang);
    }

    private void setLanguage(String language) {
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
        Intent refresh = new Intent(mContext, LauncherActivity.class);
        startActivity(refresh);
        mContext.finish();

    }

    private void setRouteNames(String coverageIDFromddl) {
        if(!coverageIDFromddl.equals("")) {
            tblRouteNames = dbengine.fngetRouteNames(coverageIDFromddl);
            ArrayList<String> routeNames = new ArrayList<String>();
            ArrayList<String> routeIDs = new ArrayList<String>();
            routeNames.add(0, "Select Route");
            routeIDs.add(0, "0");
            for (TblCoverageDsr tblCoverageDsr : tblRouteNames) {

                routeNames.add(tblCoverageDsr.getRoute());
                routeIDs.add(tblCoverageDsr.getRouteNodeID());


            }
            ArrayAdapter adapterrouteList = new ArrayAdapter(StoreSelectionFragment.this, android.R.layout.simple_spinner_item, routeNames);
            adapterrouteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            route_list.setAdapter(adapterrouteList);


            route_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (routeIDs.size() != 0) {
                        routeID = routeIDs.get(i);
                    }

                 /*   if (!routeID.equals("0")) {
                        allStoresList.clear();
                        getStoreNewList(routeID);

                    }*/
                    //  setStoresList();
                    allStoresList.clear();
                    getStoreNewList(routeID);

                   /*  if(routeID.equals("0"))
                    {
                        for (String j : routeIDs) {

                            getStoreNewList(j);
                        }
                        setStoresList();

                    }*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }




     /*   for (String i : routeIDs) {
            getStoreNewList(i);
        }*/

       // storeListAdapter.notifyDataSetChanged();

        }
    }



