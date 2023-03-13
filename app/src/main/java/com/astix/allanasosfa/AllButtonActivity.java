package com.astix.allanasosfa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.model.JointVisitDetail;
import com.astix.allanasosfa.model.JointVisitMemberDetail;
import com.astix.allanasosfa.reports.ReportsActivity;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.sync.SyncJobService;
import com.astix.allanasosfa.utils.AppUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class AllButtonActivity extends BaseActivity implements LocationInterface, MultipleInterfaceRetrofit, InterfaceRetrofit {
    public static int flgVisitTypeForFlow=0;
    private static final String TAG = "LocationActivity";
    public LocationManager locationManager;
    private FloatingActionButton fab;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    public static boolean isDayEndClicked = false;
    public static int RowId = 0;
    public static int flgLocationServicesOnOff = 0;
    public static int flgGPSOnOff = 0;
    public static int flgNetworkOnOff = 0;
    public static int flgFusedOnOff = 0;
    public static int flgInternetOnOffWhileLocationTracking = 0;
    public static int flgRestart = 0;
    public static int flgStoreOrder = 0;
    public static String address, pincode, city, state, latitudeToSave, longitudeToSave, accuracyToSave;
    static int flgChangeRouteOrDayEnd = 0;
    private final long startTime = 15000;
    private final long interval = 200;
    public String SelectedDistributorValue = "";
    public String userDate;
    public int dayEndButtonOrCycleEndForTable = 0;
    public boolean serviceException = false;
    public String serviceExceptionCode = "";
    public String passDate;
    public String[] storeList;
    public String[] storeCloseStatus;
    public String[] storeNextDayStatus;
    public String[] StoreflgSubmitFromQuotation;
    public String selStoreID = "";
    public String selStoreName = "";
    public String fullFileName1;
    public int valDayEndOrChangeRoute = 1; // 1=Clicked on Day End Button, 2=Clicked on Change Route Button
    public String[] StoreList2Procs;
    public String[] storeCode;
    public String[] storeName;
    public boolean[] checks;
    public long syncTIMESTAMP;
    public TableLayout tl2;
    public String[] storeStatus;
    public int powerCheck = 0;
    public PowerManager.WakeLock wl;
    public String rID = "0";    // Abhinav Sir tell Sunil for set its value zero at 10 October 2017
    public String SelectedDSRValue = "";
    public int chkFlgForErrorToCloseApp = 0;
    public String fDate;
    public SimpleDateFormat sdf;
    public String ReasonId;
    public String ReasonText = "NA";
    public int click_but_distribtrStock = 0;
    public TextView txtview_Dashboard;
    public String newfullFileName;
    //  SyncXMLfileData task2;
    public String[] xmlForWeb = new String[1];
    public int syncFLAG = 0;
    // public ProgressDialog pDialogGetStores;
    public TextView noVisit_tv;
    public Date currDate;
    public SimpleDateFormat currDateFormat;
    public String currSysDate;
    public String SelectedDistrbtrName = "";
    public PowerManager pm;
    public ProgressDialog pDialog2STANDBY;
    public Location location;
    //    public AllButtonActivity.CoundownClass countDownTimer;
    public boolean isGPSEnabled = false;
    public boolean isNetworkEnabled = false;
    public String fnAccurateProvider = "";
    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;
    public String FusedLocationLatitudeWithFirstAttempt = "0";
    public String FusedLocationLongitudeWithFirstAttempt = "0";
    public String FusedLocationAccuracyWithFirstAttempt = "0";
    public String AllProvidersLocation = "";
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
    int slctdCoverageAreaNodeID = 0, slctdCoverageAreaNodeType = 0, slctdDSrSalesmanNodeId = 0, slctdDSrSalesmanNodeType = 0;
    int dayEndButtonOrCycleEnd = 1;//1 =dayEnd, 2=CycleEnd
    Integer flgCollReqATCycleEnd = 1;
    Integer flgStockUnloadAtCycleEnd = 1;
    InputStream inputStream;
    int flgClkdBtn = 0;
    SharedPreferences sharedPref, sharedPrefReport, sharePrefCycleOrDayEnd;
    String whereTo = "11";
    ArrayList<String> stIDs;
    ArrayList<String> stNames;
    int whatTask = 0;
    ProgressDialog pDialog2;
    ArrayList mSelectedItems = new ArrayList();
    int isFinalSubmit = 0;
    LinearLayout ll_webreport,ll_marketVisit, ll_reports, ll_storeVal, ll_distrbtrCheckIn, ll_execution,
            ll_stockCheckOut, ll_warehose, ll_distributor, ll_registration, ll_Survey,ll_dailyTracker,ll_salesmanLocation,ll_storeDetails,ll_MTDsummary;
    String[] drsNames;
    ImageView imageView551;
    TextView tv_Warehouse, tv_DistributorStock;
    LinkedHashMap<String, String> hmapdsrIdAndDescr_details = new LinkedHashMap<String, String>();
    String imei;
    int CstmrNodeId = 0, CstomrNodeType = 0;
    int battLevel = 0;
    DatabaseAssistantDistributorEntry DA = new DatabaseAssistantDistributorEntry(this);
    DatabaseAssistant DASFA;
    int serverResponseCode = 0;
    String[] reasonNames;
    LinkedHashMap<String, String> hmapReasonIdAndDescr_details = new LinkedHashMap<String, String>();
    LinearLayout ll_dsrTracker, ll_changelagugae, ll_DayEnd, ll_StkRqst, ll_CycleEnd;
    ImageView imgVw_logout;
    //report alert
    String[] Distribtr_list;
    String DbrNodeId, DbrNodeType, DbrName;
    ArrayList<String> DbrArray = new ArrayList<String>();
    LinkedHashMap<String, String> hmapDistrbtrList = new LinkedHashMap<>();
    LinkedHashMap<String, String> hmapOutletListForNear = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapOutletListForNearUpdated = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapStoreLatLongDistanceFlgRemap = new LinkedHashMap<String, String>();
    //market visit loc alert
    android.app.AlertDialog GPSONOFFAlert = null;
    String mLastUpdateTime;
    int countSubmitClicked = 0;
    String fusedData;

    String StockDate = "";
    String attendanceDate = "";
    int isPerformDayEndFirst = 0;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };

    public static boolean deleteNon_EmptyDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteNon_EmptyDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static String[] checkNumberOfFiles(File dir) {
        int NoOfFiles = 0;
        String[] Totalfiles = null;

        if (dir.isDirectory()) {
            String[] children = dir.list();
            NoOfFiles = children.length;
            Totalfiles = new String[children.length];

            for (int i = 0; i < children.length; i++) {
                Totalfiles[i] = children[i];
            }
        }
        return Totalfiles;
    }

    public static void zip(String[] files, String zipFile) throws IOException {
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
                } finally {
                    origin.close();
                }
            }
        } finally {
            out.close();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.mBatInfoReceiver);

        wl.release();
    }

    private void getReasonDetail() throws IOException {

        int check = mDataSource.countDataIntblDayStartAttendanceOptions();

        hmapReasonIdAndDescr_details = mDataSource.fetch_Reason_List();

        int index = 0;
        if (hmapReasonIdAndDescr_details != null) {
            reasonNames = new String[hmapReasonIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapReasonIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                reasonNames[index] = me2.getKey().toString();
                index = index + 1;
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        StockDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

        int alreadyLocFind = mDataSource.fetchtblIsDBRStockSubmitted();
        if (alreadyLocFind == 0) {
            int checkData = mDataSource.checkDSRCheckIntblSuplierMapping();
            TextView DistributorCheckTextView = (TextView) findViewById(R.id.DistributorCheckTextView);
          /*  if (checkData == 1) {
                ll_marketVisit.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_distrbtrCheckIn.setBackgroundColor(Color.parseColor("#ffffff"));
                DistributorCheckTextView.setTextColor(Color.parseColor("#000000"));
            } else {
                ll_marketVisit.setBackgroundColor(Color.parseColor("#EEEEEE"));
                ll_distrbtrCheckIn.setBackgroundColor(Color.parseColor("#E0E0E0"));
                DistributorCheckTextView.setTextColor(Color.parseColor("#BF360C"));
            }*/
        } else {

        }
      /*  if (CommonInfo.hmapAppMasterFlags.get("flgDayEndSummary") == 0) {
            isDayEndClicked = true;
        }*/
     /* if(isFinalSubmit==1 || isFinalSubmit==2 || isFinalSubmit==3)
      {
          isDayEndClicked=true;
      }*/

        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && (CommonInfo.hmapAppMasterFlags.get("flgDayEnd") != 0)) {
            if (CommonInfo.hmapAppMasterFlags.get("flgDayEndSummary") == 1 && isFinalSubmit == 0) {//isDayEndClicked==false){
                ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlrtDiffrentDayEndProcess(1);
                    }
                });
            } else {

                if (sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd")) {
                    dayEndButtonOrCycleEndForTable = sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd", 0);
                }
                int flgStockRqst = mDataSource.fetchtblStockUploadedStatusForRqstStatus();

                if ((isFinalSubmit == 3) || (mDataSource.fetchtblDayEndStatus() == 2)) {
                    mDataSource.reCreateDB();

                    finishAffinity();
                } else if ((isFinalSubmit == 2)) {
                    ll_marketVisit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndDone));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndDone));
                            }
                        }
                    });
                    ll_distributor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndDone));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndDone));
                            }
                        }
                    });
                    ll_warehose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndDone));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndDone));
                            }
                        }
                    });
                    ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndDone));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndDone));
                            }

                        }
                    });
                    ll_CycleEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndDone));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndDone));
                            }

                        }
                    });
                } else if ((isFinalSubmit == 1) || ((mDataSource.fetchtblDayEndStatus() == 1)))//; && (mDataSource.CheckTotalStoreCount()>0)))
                {
                    ll_marketVisit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
                            }


                        }
                    });
                    ll_distributor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
                            }
                        }
                    });
                    ll_warehose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
                            }
                        }
                    });
                    ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
                            }
                        }
                    });
                    ll_CycleEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
                            }

                        }
                    });
                } else if (flgStockRqst == 4) {
                    ll_marketVisit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertCycleEndCnfrmForRqstStk));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertDayEndCnfrmForRqstStk));
                            }

                        }
                    });
                    ll_distributor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertCycleEndCnfrmForRqstStk));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertDayEndCnfrmForRqstStk));
                            }
                        }
                    });
                    ll_warehose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertCycleEndCnfrmForRqstStk));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertDayEndCnfrmForRqstStk));
                            }
                        }
                    });
                    ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertCycleEndCnfrmForRqstStk));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertDayEndCnfrmForRqstStk));
                            }
                        }
                    });
                    ll_CycleEnd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (dayEndButtonOrCycleEndForTable == 2) {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertCycleEndCnfrmForRqstStk));
                            } else {
                                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.AlertDayEndCnfrmForRqstStk));
                            }

                        }
                    });
                }
            }
            if (isDayEndClicked && isFinalSubmit == 0) {
                DayEndCodeAfterSummary();
            }

        } else {
            if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDayEndSummary") == 1 && isDayEndClicked == false) {
                ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlrtDiffrentDayEndProcess(2);
                    }
                });
            }
            if (isDayEndClicked) {
                DayEndCodeAfterSummary();
            }
        }
       /* else {

            if (isDayEndClicked) {

            }
            if (CommonInfo.hmapAppMasterFlags.get("flgDayEndSummary") == 1 && isDayEndClicked == false) {
                ll_DayEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(AllButtonActivity.this, DialogDayEndSummaryActivity.class);
                        startActivity(in);
                    }
                });
            }



        }*/


    }
    public void showAlrtDiffrentDayEndProcess(int flg121) {
        if(flg121==1) {
            AlertDialog.Builder alertDialogOrderSubmission = new AlertDialog.Builder(this);
            alertDialogOrderSubmission.setTitle(R.string.genTermInformation);
            alertDialogOrderSubmission.setMessage(R.string.DayEndDataMsg);
            alertDialogOrderSubmission.setPositiveButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogOkButton),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            dayEndButtonOrCycleEnd = 1; // day end button clicked
                            SharedPreferences.Editor editorCycleOrDayEnd = sharePrefCycleOrDayEnd.edit();
                            editorCycleOrDayEnd.clear();
                            editorCycleOrDayEnd.putInt("dayEndButtonOrCycleEnd", dayEndButtonOrCycleEnd);
                            editorCycleOrDayEnd.commit();
                            Intent in = new Intent(AllButtonActivity.this, DialogDayEndSummaryActivity.class);
                            startActivity(in);

                        }
                    });
            alertDialogOrderSubmission.setNegativeButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogCancelButton),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
            AlertDialog alert = alertDialogOrderSubmission.create();
            alert.show();
        }

        if(flg121==2) {
            AlertDialog.Builder alertDialogOrderSubmission = new AlertDialog.Builder(this);
            alertDialogOrderSubmission.setTitle(R.string.genTermInformation);
            alertDialogOrderSubmission.setMessage(R.string.DayEndDataMsg);
            alertDialogOrderSubmission.setPositiveButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogOkButton),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            dayEndButtonOrCycleEnd = 1; // day end button clicked
                            SharedPreferences.Editor editorCycleOrDayEnd = sharePrefCycleOrDayEnd.edit();
                            editorCycleOrDayEnd.clear();
                            editorCycleOrDayEnd.putInt("dayEndButtonOrCycleEnd", dayEndButtonOrCycleEnd);
                            editorCycleOrDayEnd.commit();
                            Intent in = new Intent(AllButtonActivity.this, DialogDayEndSummaryActivity.class);
                            startActivity(in);

                        }
                    });
            alertDialogOrderSubmission.setNegativeButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogCancelButton),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
            AlertDialog alert = alertDialogOrderSubmission.create();
            alert.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_button);

        StoreSelection.flgChangeRouteOrDayEnd = 1;

        sharedPrefReport = getSharedPreferences("Report", MODE_PRIVATE);

        DASFA = new DatabaseAssistant(this);

        sharePrefCycleOrDayEnd = getSharedPreferences(CommonInfo.CycleOrDayEndPreference, MODE_PRIVATE);

        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        flgVisitTypeForFlow=0;
        if (sharedPref.contains("FinalSubmit")) {
            isFinalSubmit = sharedPref.getInt("FinalSubmit", 0);
        }
        if (sharedPref.contains("CoverageAreaNodeID")) {
            if (sharedPref.getInt("CoverageAreaNodeID", 0) != 0) {
                CommonInfo.CoverageAreaNodeID = sharedPref.getInt("CoverageAreaNodeID", 0);
                CommonInfo.CoverageAreaNodeType = sharedPref.getInt("CoverageAreaNodeType", 0);
            }
        }
        if (sharedPref.contains("SalesmanNodeId")) {
            if (sharedPref.getInt("SalesmanNodeId", 0) != 0) {
                CommonInfo.SalesmanNodeId = sharedPref.getInt("SalesmanNodeId", 0);
                CommonInfo.SalesmanNodeType = sharedPref.getInt("SalesmanNodeType", 0);
            }
        }
        if (sharedPref.contains("flgDataScope")) {
            if (sharedPref.getInt("flgDataScope", 0) != 0) {
                CommonInfo.flgDataScope = sharedPref.getInt("flgDataScope", 0);

            }
        }
        if (sharedPref.contains("flgDSRSO")) {
            if (sharedPref.getInt("flgDSRSO", 0) != 0) {
                CommonInfo.FlgDSRSO = sharedPref.getInt("flgDSRSO", 0);

            }
        }
      /*  TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // imei = tManager.getDeviceId();

        if (CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals("")) {
            imei = tManager.getDeviceId();
            CommonInfo.imei = imei;
        } else {
            imei = CommonInfo.imei.trim();
        }*/
        imei=AppUtils.getIMEI(AllButtonActivity.this);
        Date date1 = new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        passDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

        //System.out.println("Selctd Date: "+ passDate);

        fDate = passDate.trim().toString();

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        /*if (CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals("")) {
            imei = tManager.getDeviceId();
            CommonInfo.imei = imei;
        } else {
            imei = CommonInfo.imei.trim();
        }*/

      /*  //  SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CoverageAreaNodeID", coverageAreaNodeID);
        editor.putInt("CoverageAreaNodeType", coverageAreaNodeType);


        editor.commit();*/
        shardPrefForSalesman(0, 0);

        flgDataScopeSharedPref(0);
        Date date2 = new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

        currDate = new Date();
        currDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        currSysDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

        String prsnCvrgId_NdTyp = mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
        int CoverageNodeId = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0]);
        int CoverageNodeType = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]);
        CommonInfo.flgDrctslsIndrctSls = mDataSource.fnGetflgDrctslsIndrctSlsForDSR(CoverageNodeId, CoverageNodeType);
        CommonInfo.hmapAppMasterFlags = mDataSource.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);

        initialiseViews();
        if (powerCheck == 0) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            wl.acquire();
        }

        //hide or show cycleENd Or DayEnd
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 &&CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 0) {
            ll_DayEnd.setVisibility(View.GONE);
            ll_CycleEnd.setVisibility(View.GONE);

        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 1) {
            ll_DayEnd.setVisibility(View.GONE);
            //ll_CycleEnd.setVisibility(View.GONE);
        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 2) {
            //ll_DayEnd.setVisibility(View.GONE);
            ll_CycleEnd.setVisibility(View.GONE);
        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 3) {

        }
        checkAttendanceDateChange();
    }
    private void checkAttendanceDateChange() {
        String attendanceDateTime = mDataSource.getAttendanceDateTime();
        SharedPreferences sPrefAttandance = getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);
        if (TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime = sPrefAttandance.getString("AttandancePref", "");
        if (TextUtils.isEmpty(attendanceDateTime) || attendanceDateTime.equalsIgnoreCase("NA"))
            attendanceDateTime = TimeUtils.getNetworkDateTime(this, TimeUtils.DATE_TIME_FORMAT);
        attendanceDate = TimeUtils.getDateFromDateTime(attendanceDateTime);
        String networkDate = TimeUtils.getNetworkDate(this, TimeUtils.DATE_FORMAT);
        int numberOfDays = TimeUtils.calculateDaysDifferenceBetweenTwoDates(attendanceDate, networkDate);
        if (numberOfDays > 0)
            isPerformDayEndFirst = 1;

    }

    private boolean fnCheckPreviousDayDayEndDone() {
        if (isPerformDayEndFirst == 1) {
            AppUtils.showAlertDialog(this, "Your day end is still pending for " + attendanceDate + " , Please perform day end first.");
            return false;
        }
        return true;
    }
    void initialiseViews() {

        ll_Survey = (LinearLayout) findViewById(R.id.ll_Survey);
        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStaticSurveyAllStores")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStaticSurveyAllStores") == 1) {
                ll_Survey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent storeIntent = new Intent(AllButtonActivity.this, SurveyStoreList.class);
                        storeIntent.putExtra("PageFrom", "0");
                        storeIntent.putExtra("FROM", "SURVEY");
                        storeIntent.putExtra("imei", imei);
                        startActivity(storeIntent);
                    }
                });
            } else {
                ll_Survey.setVisibility(View.GONE);
            }
        } else {
            ll_Survey.setVisibility(View.GONE);
        }

        ll_registration = (LinearLayout) findViewById(R.id.ll_registration);
      /*  if (CommonInfo.flgDrctslsIndrctSls == 2) {
            ll_registration.setVisibility(View.GONE);
        }*/

        ll_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fnCheckPreviousDayDayEndDone()) {
                    Intent intent = new Intent(AllButtonActivity.this, DSR_Registration.class);
                    intent.putExtra("IntentFrom", "AllButtonActivity");
                    intent.putExtra("imei", imei);
                    intent.putExtra("userDate", userDate);
                    intent.putExtra("pickerDate", userDate);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ll_webreport = findViewById(R.id.ll_webreport);
        ll_webreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllButtonActivity.this,PurchaseOrderWebActivity.class));
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunction.getAllMasterTableModelData(AllButtonActivity.this, AppUtils.getDeviceId(AllButtonActivity.this), ""+0, "Please wait loading data.", 0);
            }
        });

        ll_marketVisit = (LinearLayout) findViewById(R.id.ll_marketVisit);
        ll_reports = (LinearLayout) findViewById(R.id.ll_reports);
        ll_storeVal = (LinearLayout) findViewById(R.id.ll_storeVal);
        ll_distrbtrCheckIn = (LinearLayout) findViewById(R.id.ll_distrbtrCheckIn);
        ll_warehose = (LinearLayout) findViewById(R.id.ll_warehose);

        ll_execution = (LinearLayout) findViewById(R.id.ll_execution);
        ll_stockCheckOut = (LinearLayout) findViewById(R.id.ll_stockCheckOut);
        ll_changelagugae = (LinearLayout) findViewById(R.id.ll_changelagugae);
        ll_dsrTracker = (LinearLayout) findViewById(R.id.ll_dsrTracker);
        ll_DayEnd = (LinearLayout) findViewById(R.id.ll_DayEnd);
        ll_CycleEnd = (LinearLayout) findViewById(R.id.ll_CycleEnd);
        ll_distributor = (LinearLayout) findViewById(R.id.ll_distributor);
        ll_dailyTracker = (LinearLayout) findViewById(R.id.ll_dailyTracker);
        ll_dailyTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyTracker();
            }
        });

        ll_dsrTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDSRTrackerAlert();
            }
        });

        ll_salesmanLocation = (LinearLayout) findViewById(R.id.ll_salesmanLocation);

        ll_salesmanLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salesLocation();
            }
        });
        ll_storeDetails = (LinearLayout) findViewById(R.id.ll_storeDetails);

        ll_storeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fnCheckPreviousDayDayEndDone()) {
                    CommonInfo.FlgDSRSO = 0;
                    storeDetails();
                }
            }
        });
        ll_MTDsummary = (LinearLayout) findViewById(R.id.ll_MTDsummary);
        ll_MTDsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtdSummary();
            }
        });

        LinearLayout llDayStartDistributorStock = (LinearLayout) findViewById(R.id.llDayStartDistributorStock);
        //llDayStartDistributorStock.setVisibility(View.VISIBLE);
        if (CommonInfo.flgLangChangeReuired == 1) {
            ll_changelagugae.setVisibility(View.VISIBLE);
        } else {
            ll_changelagugae.setVisibility(View.GONE);
        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 0) {
            ll_distributor.setVisibility(View.GONE);
            llDayStartDistributorStock.setVisibility(View.GONE);
        }


        ll_StkRqst = (LinearLayout) findViewById(R.id.ll_StkRqst);
        imageView551 = (ImageView) findViewById(R.id.imageView551);


        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") != 0) {
            if (mDataSource.fetchtblIsDBRStockSubmitted() == 0) {
                imageView551.setBackgroundResource(R.drawable.distributorstock_not_confirmed);
            } else {
                imageView551.setBackgroundResource(R.drawable.backgrnd_distributionstock);
            }
        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0  && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {
            if (mDataSource.flgConfirmedWareHouse() == 0) {
                imageView551.setBackgroundResource(R.drawable.distributorstock_not_confirmed);
            } else {
                imageView551.setBackgroundResource(R.drawable.backgrnd_distributionstock);
            }
        }
        int flgShowInvoice = 0;
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgExecutionIsAvailable")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgExecutionIsAvailable") == 1) {
                flgShowInvoice = 1;
            }
        }

        if (flgShowInvoice == 1) {
            View viewExecutionLine = (View) findViewById(R.id.viewExecutionLine);
            viewExecutionLine.setVisibility(View.VISIBLE);
            ll_execution.setVisibility(View.VISIBLE);
            executionWorking();
        }

        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.containsKey("flgStockRefillReq")) {
            int flgStockRequest = CommonInfo.hmapAppMasterFlags.get("flgStockRefillReq");

            if (flgStockRequest == 1) {
                View stockReqestView = (View) findViewById(R.id.stockReqestView);
                stockReqestView.setVisibility(View.VISIBLE);
                ll_StkRqst.setVisibility(View.VISIBLE);
            }
        }
        tv_DistributorStock = (TextView) findViewById(R.id.tv_DistributorStock);
        int flgCkechDayStart = mDataSource.fnCkechDayStart();
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0  && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) {
            tv_DistributorStock.setText(R.string.txtDistributorStock);


        }


        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) {
            View viewDayStartDistributorStock = (View) findViewById(R.id.viewDayStartDistributorStock);
            viewDayStartDistributorStock.setVisibility(View.VISIBLE);
        }
        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
            View viewDayStartWarehouseStock = (View) findViewById(R.id.viewDayStartWarehouseStock);
            View llDayStartWarehouseStock = (View) findViewById(R.id.llDayStartWarehouseStock);
            View stockReqestView = (View) findViewById(R.id.stockReqestView);

            llDayStartWarehouseStock.setVisibility(View.VISIBLE);
            llDayStartWarehouseStock.setVisibility(View.VISIBLE);
            viewDayStartWarehouseStock.setVisibility(View.VISIBLE);
        }

        txtview_Dashboard = (TextView) findViewById(R.id.txtview_Dashboard);


        String PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
        String personName = "";


        if (!PersonNameAndFlgRegistered.equals("0")) {
            personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
            txtview_Dashboard.setText(personName);

        }
        try {
            getReasonDetail();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            getDSRDetail();
            //report alert
          //  getDistribtrList();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        imgVw_logout = (ImageView) findViewById(R.id.imgVw_logout);

        marketVisitWorking();
        reportsWorking();
        storeValidationWorking();
        stockOutWorking();
        //wareHouseWorking();

        ll_StkRqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunction.getStockData(AllButtonActivity.this, imei, CommonInfo.RegistrationID, "", 1);

            }
        });
        // distributorCheckInWorking();
        DistributorWorking();
        warehouseCheckInWorking();
        //distributorStockWorking();
        //  executionWorking();
        // noVisitWorking();
        // distributorMapWorking();
        changelaguage();
        dayEndWorking();
        CycleEndWorking();

        imgVw_logout = (ImageView) findViewById(R.id.imgVw_logout);
        imgVw_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);
                if (!OrderXMLFolder.exists()) {
                    OrderXMLFolder.mkdirs();
                }

                imgVw_logout.setImageResource(R.drawable.logout_hover);
/*
                File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                // check number of files in folder
                final String [] AllFilesNameNotSync= checkNumberOfFiles(del);

                String xmlfileNames = mDataSource.fnGetXMLFile("3");
              //  String xmlfileNamesStrMap=mDataSoureSo.fnGetXMLFile("3");

                //mDataSource.open();
                String[] SaveStoreList = mDataSource.ProcessStoreReq();
                //mDataSource.close();

                if (SaveStoreList.length != 0)
                {
                    showAlertSingleButtonInfo(getResources().getString(R.string.DayEndBeforeLogout));

                }
                else if(xmlfileNames.length()>0)
                {
                    showAlertSingleButtonInfo(getResources().getString(R.string.DayEndBeforeLogout));
                }
                  else
                {*/

                dialogLogout();

                // }

              /*  Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                startActivity(refresh);
                finish();*/
            }
        });
    }
    void getDistribtrList() {


        Distribtr_list = mDataSource.getDistributorDataMstr();

        for (int i = 0; i < Distribtr_list.length; i++) {
            String value = Distribtr_list[i];
            DbrNodeId = value.split(Pattern.quote("^"))[0];
            DbrNodeType = value.split(Pattern.quote("^"))[1];
            DbrName = value.split(Pattern.quote("^"))[2];
            //flgReMap=Integer.parseInt(value.split(Pattern.quote("^"))[3]);

            hmapDistrbtrList.put(DbrName, DbrNodeId + "^" + DbrNodeType);
            DbrArray.add(DbrName);
        }

    }
    private void getDSRDetail() throws IOException {



        hmapdsrIdAndDescr_details = mDataSource.fetch_DSRCoverage_List();

        int index = 0;
        if (hmapdsrIdAndDescr_details != null) {
            drsNames = new String[hmapdsrIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapdsrIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                drsNames[index] = me2.getKey().toString();
                index = index + 1;
            }
        }


    }

    public void dailyTracker() {
        Intent intent = new Intent(this,PurchaseOrderWebActivity.class);
        intent.putExtra(PurchaseOrderWebActivity.REPORT_TYPE_KEY,3);
        startActivity(intent);
    }
    public void storeDetails() {
        startActivity(new Intent(this, StoreSelectionFragment.class));
    }
    public void salesLocation() {
        Intent intent = new Intent(this,PurchaseOrderWebActivity.class);
        intent.putExtra(PurchaseOrderWebActivity.REPORT_TYPE_KEY,2);
        startActivity(intent);
    }
    public void mtdSummary() {
        startActivity(new Intent(this, ReportsActivity.class));
    }

    public void stockOutWorking() {

        ll_stockCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(AllButtonActivity.this, WebViewActivity.class);
                i.putExtra("comeFrom", "StockOut");
                startActivity(i);


            }
        });
    }

    private void openDSRTrackerAlert()
    {
        final AlertDialog.Builder alert=new AlertDialog.Builder(AllButtonActivity.this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dsr_tracker_alert, null);
        alert.setView(view);

        alert.setCancelable(false);

        final RadioButton rb_dataReport= (RadioButton) view.findViewById(R.id.rb_dataReport);
        final RadioButton rb_onMap= (RadioButton) view.findViewById(R.id.rb_onMap);


        Button btn_proceed= (Button) view.findViewById(R.id.btn_proceed);
        Button btn_cancel= (Button) view.findViewById(R.id.btn_cancel);

        final AlertDialog dialog=alert.create();
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if(rb_dataReport.isChecked())
                {
                    Intent i=new Intent(AllButtonActivity.this,WebViewDSRDataReportActivity.class);
                    startActivity(i);

                }
                else if(rb_onMap.isChecked())
                {
                    Intent i = new Intent(AllButtonActivity.this, WebViewDSRTrackerActivity.class);
                    startActivity(i);

                }

                else
                {
                    showAlertSingleButtonInfo(getResources().getString(R.string.selectOptionProceeds));

                }
            }
        });

        rb_dataReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_dataReport.isChecked())
                {
                    rb_onMap.setChecked(false);

                }
            }
        });

        rb_onMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_onMap.isChecked())
                {
                    rb_dataReport.setChecked(false);

                }
            }
        });



        dialog.show();
    }

    void CycleEndWorking() {


        ll_CycleEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayEndButtonOrCycleEnd = 2;//1 =dayEnd, 2=CycleEnd //cycle end button clicked

                SharedPreferences.Editor editorCycleOrDayEnd = sharePrefCycleOrDayEnd.edit();
                editorCycleOrDayEnd.clear();
                editorCycleOrDayEnd.putInt("dayEndButtonOrCycleEnd", dayEndButtonOrCycleEnd);
                editorCycleOrDayEnd.commit();


                String PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
                String personName = "";
                String FlgRegistered = "";
                int DsrRegTableCount = 0;
                DsrRegTableCount = mDataSource.fngetcounttblDsrRegDetails();
                if (!PersonNameAndFlgRegistered.equals("0")) {
                    personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
                    FlgRegistered = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[1];
                }
                int flagForShowDsrRegistration = 0;
                if (FlgRegistered.equals("0") && DsrRegTableCount == 0 && flagForShowDsrRegistration == 1) {
                    android.app.AlertDialog.Builder alertDialogNoConn = new android.app.AlertDialog.Builder(AllButtonActivity.this);
                    alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));
                    alertDialogNoConn.setMessage(getResources().getString(R.string.Dsrmessage));
                    alertDialogNoConn.setCancelable(false);
                    alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(AllButtonActivity.this, DSR_Registration.class);
                            intent.putExtra("IntentFrom", "AllButtonActivity");
                            intent.putExtra("imei", imei);
                            intent.putExtra("userDate", userDate);
                            intent.putExtra("pickerDate", userDate);

                            startActivity(intent);
                            finish();

                        }
                    });
                    alertDialogNoConn.setIcon(R.drawable.info_ico);
                    android.app.AlertDialog alert = alertDialogNoConn.create();
                    alert.show();

                } else {


                    //CommonInfo.hmapAppMasterFlags.put("flgStockUnloadAtCycleEnd",1);
                    // CommonInfo.hmapAppMasterFlags.put("flgCollReqATCycleEnd",1);
                    if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && (CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd") == 1) || (CommonInfo.hmapAppMasterFlags.get("flgCollReqATCycleEnd") == 1)) {
                        showDialogForFlag();
                    } else {
                        flgCollReqATCycleEnd = 0;
                        flgStockUnloadAtCycleEnd = 0;
                        cycleEndCode();
                    }


                    //


                }

            }
        });

    }
    public void showConfirmationDayAlert()
    {
        AlertDialog.Builder alertDialogOrderSubmission = new AlertDialog.Builder(this);
        alertDialogOrderSubmission.setTitle(R.string.genTermInformation);
        alertDialogOrderSubmission.setMessage(R.string.DayEndDataMsg);
        alertDialogOrderSubmission.setPositiveButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        dayEndButtonOrCycleEnd = 1; // day end button clicked
                        SharedPreferences.Editor editorCycleOrDayEnd = sharePrefCycleOrDayEnd.edit();
                        editorCycleOrDayEnd.clear();
                        editorCycleOrDayEnd.putInt("dayEndButtonOrCycleEnd", dayEndButtonOrCycleEnd);
                        editorCycleOrDayEnd.commit();

                        String PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
                        String personName = "";
                        String FlgRegistered = "";
                        int DsrRegTableCount = 0;
                        DsrRegTableCount = mDataSource.fngetcounttblDsrRegDetails();
                        if (!PersonNameAndFlgRegistered.equals("0")) {
                            personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
                            FlgRegistered = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[1];
                        }

                        if (FlgRegistered.equals("0") && DsrRegTableCount == 0)
                        //   if(!FlgRegistered.equals("0")&& DsrRegTableCount==0)
                        {
                            android.app.AlertDialog.Builder alertDialogNoConn = new android.app.AlertDialog.Builder(AllButtonActivity.this);
                            alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));
                            alertDialogNoConn.setMessage(getResources().getString(R.string.Dsrmessage));
                            alertDialogNoConn.setCancelable(false);
                            alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(AllButtonActivity.this, DSR_Registration.class);
                                    intent.putExtra("IntentFrom", "AllButtonActivity");
                                    intent.putExtra("imei", imei);
                                    intent.putExtra("userDate", userDate);
                                    intent.putExtra("pickerDate", userDate);

                                    startActivity(intent);
                                    finish();

                                }
                            });
                            alertDialogNoConn.setIcon(R.drawable.info_ico);
                            android.app.AlertDialog alert = alertDialogNoConn.create();
                            alert.show();

                        } else {


                            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

// check number of files in folder
                            final String[] AllFilesNameNotSync = checkNumberOfFiles(del);

                            String xmlfileNames = mDataSource.fnGetXMLFile("3");
                            // String xmlfileNamesStrMap=mDataSoureSo.fnGetXMLFile("3");

                            //mDataSource.open();
                            String[] SaveStoreList = mDataSource.SaveStoreList();
                            //mDataSource.close();
                            if (xmlfileNames.length() > 0 || SaveStoreList.length != 0) {
                                if (isOnline()) {


                                    whereTo = "11";

                                    //mDataSource.open();

                                    StoreList2Procs = mDataSource.ProcessStoreReq();
                                    if (StoreList2Procs.length != 0) {

                                        midPart();
                                        dayEndCustomAlert(1);
                                        //mDataSource.close();

                                    } else if (mDataSource.GetLeftStoresChk() == true) {
                                        DayEnd();

                                    } else {
                                        DayEndWithoutalert();
                                    }
                                } else {
                                    showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));


                                }
                            } else {
                                //showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));
                                if (isOnline()) {
                                    whereTo = "11";
                                    DayEndWithoutalert();
                                } else {
                                    showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                                }

                            }


                        }

                    }
                });
        alertDialogOrderSubmission.setNegativeButton(AllButtonActivity.this.getResources().getString(R.string.AlertDialogCancelButton),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
        alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
        AlertDialog alert = alertDialogOrderSubmission.create();
        alert.show();

    }
    void dayEndWorking() {

        ll_DayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDayAlert();


            }
        });

    }

    public void dayEndCustomAlert(int flagWhichButtonClicked) {

        final Dialog dialog = new Dialog(AllButtonActivity.this, R.style.AlertDialogDayEndTheme);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.day_end_custom_alert);
        if (flagWhichButtonClicked == 1) {
            dialog.setTitle(R.string.genStoreListWhoseDataIsNotYetSubmitted);

        } else if (flagWhichButtonClicked == 2) {
            dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteChangeRoute);
        }


        LinearLayout ll_product_not_submitted = (LinearLayout) dialog.findViewById(R.id.ll_product_not_submitted);
        mSelectedItems.clear();
        final String[] stNames4List = new String[stNames.size()];
        checks = new boolean[stNames.size()];
        stNames.toArray(stNames4List);

        for (int cntPendingList = 0; cntPendingList < stNames4List.length; cntPendingList++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View viewAlertProduct = inflater.inflate(R.layout.day_end_alrt, null);
            final TextView txtVw_product_name = (TextView) viewAlertProduct.findViewById(R.id.txtVw_product_name);
            txtVw_product_name.setText(stNames4List[cntPendingList]);
            txtVw_product_name.setTextColor(this.getResources().getColor(R.color.green_submitted));
            final ImageView img_to_be_submit = (ImageView) viewAlertProduct.findViewById(R.id.img_to_be_submit);
            img_to_be_submit.setTag(cntPendingList);
            img_to_be_submit.setVisibility(View.INVISIBLE);
            final ImageView img_to_be_cancel = (ImageView) viewAlertProduct.findViewById(R.id.img_to_be_cancel);
            img_to_be_cancel.setTag(cntPendingList);

            img_to_be_cancel.setVisibility(View.INVISIBLE);
            img_to_be_submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    if (!checks[Integer.valueOf(img_to_be_submit.getTag().toString())]) {
                        img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_hover));
                        img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_normal));
                        txtVw_product_name.setTextColor(getResources().getColor(R.color.green_submitted));
                        mSelectedItems.add(stNames4List[Integer.valueOf(img_to_be_submit.getTag().toString())]);
                        checks[Integer.valueOf(img_to_be_submit.getTag().toString())] = true;
                    }


                }
            });

            img_to_be_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mSelectedItems.contains(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())])) {
                        if (checks[Integer.valueOf(img_to_be_cancel.getTag().toString())]) {

                            img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_normal));
                            img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_hover));
                            txtVw_product_name.setTextColor(Color.RED);
                            mSelectedItems.remove(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())]);
                            checks[Integer.valueOf(img_to_be_cancel.getTag().toString())] = false;
                        }

                    }

                }
            });
            mSelectedItems.add(stNames4List[cntPendingList]);
            checks[cntPendingList] = false;
            ll_product_not_submitted.addView(viewAlertProduct);
        }


        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        Button btn_cancel_Back = (Button) dialog.findViewById(R.id.btn_cancel_Back);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                if (mSelectedItems.size() == 0) {
                    DayEnd();
                } else {

                    int countOfOrderNonSelected = 0;
                    for (int countForFalse = 0; countForFalse < checks.length; countForFalse++) {
                        if (checks[countForFalse] == false) {
                            countOfOrderNonSelected++;
                        }

                    }
                    /*if(countOfOrderNonSelected>0)
                    {
                       // confirmationForSubmission(String.valueOf(countOfOrderNonSelected));
                        DayEnd();
                    }

                    else
                    {*/


                    whatTask = 2;
                    try {
                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }

                    //}

                }

            }
        });

        btn_cancel_Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                valDayEndOrChangeRoute = 0;
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);


        dialog.show();


    }

    public void SyncNow() {

        syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);


        //mDataSource.open();
        String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
        //mDataSource.close();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss", Locale.ENGLISH);

        String newfullFileName = imei + "." + presentRoute + "." + df.format(dateobj);

        LinkedHashMap<String, String> hmapStoreListToProcessWithoutAlret = mDataSource.fnGetStoreListToProcessWithoutAlret();

        if (hmapStoreListToProcessWithoutAlret != null) {

            Set set2 = hmapStoreListToProcessWithoutAlret.entrySet();
            Iterator iterator = set2.iterator();
            //mDataSource.open();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                mDataSource.UpdateStoreFlagAtDayEndOrChangeRouteWithOnlyVistOrCollection(StoreIDToProcessWithoutAlret, 3);

            }
            //mDataSource.close();;

            Set set3 = hmapStoreListToProcessWithoutAlret.entrySet();
            Iterator iterator1 = set3.iterator();

            while (iterator1.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator1.next();
                String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                String StoreVisitCode = mDataSource.fnGetStoreVisitCode(StoreIDToProcessWithoutAlret);
                String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret, StoreVisitCode);

                String strGetJointVisitId = mDataSource.fnGetJointVisitId(StoreIDToProcessWithoutAlret);

                mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 5, StoreVisitCode, TmpInvoiceCodePDA,strGetJointVisitId);
                mDataSource.updateflgFromWhereSubmitStatusAgainstStore(StoreIDToProcessWithoutAlret, 1, StoreVisitCode);
            }
        }

        try {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists()) {
                OrderXMLFolder.mkdirs();
            }

            String routeID = mDataSource.GetActiveRouteIDSunil();

            if (CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 0 || CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 2) {
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobjForDayEnd = new Date(syncTIMESTAMP);
                SimpleDateFormat dfForDayEnd = new SimpleDateFormat("dd-MMM-yyyy HH.mm.ss", Locale.ENGLISH);
                String startTS = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_TIME_FORMAT);

                int DayEndFlg =1;
                int ChangeRouteFlg = 0;

                String AppVersionID = mDataSource.dbHelper.AppVersionID;
                if (sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd")) {
                    dayEndButtonOrCycleEndForTable = sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd", 0);
                }

                mDataSource.insertTblDayStartEndDetails(imei, startTS, rID, DayEndFlg, ChangeRouteFlg, fDate, AppVersionID, dayEndButtonOrCycleEndForTable);//DatabaseVersion;//getVersionNumber

                int valDayEndOrChangeRoute = 1;
                mDataSource.UpdateTblDayStartEndDetails(Integer.parseInt(rID), valDayEndOrChangeRoute);
            }

            DASFA.export(mDataSource.dbHelper.DATABASE_NAME, newfullFileName, routeID);
            if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 &&(CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 0)) {// || CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 2
                mDataSource.delDayEnd();
            }

            if (hmapStoreListToProcessWithoutAlret != null) {

                Set set2 = hmapStoreListToProcessWithoutAlret.entrySet();
                Iterator iterator = set2.iterator();
                //mDataSource.open();
                while (iterator.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator.next();
                    String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                    mDataSource.UpdateStoreFlagAtDayEndOrChangeRouteWithOnlyVistOrCollection(StoreIDToProcessWithoutAlret, 5);
                   /* String  StoreVisitCode=mDataSource.fnGetStoreVisitCode(StoreIDToProcessWithoutAlret);
                    String TmpInvoiceCodePDA=mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret,StoreVisitCode);
                    mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4,StoreVisitCode,TmpInvoiceCodePDA);*/
                }
                //mDataSource.close();;

                Set set3 = hmapStoreListToProcessWithoutAlret.entrySet();
                Iterator iterator1 = set3.iterator();

                while (iterator1.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator1.next();
                    String StoreIDToProcessWithoutAlret = me2.getKey().toString();
                    String StoreVisitCode = mDataSource.fnGetStoreVisitCode(StoreIDToProcessWithoutAlret);
                    String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(StoreIDToProcessWithoutAlret, StoreVisitCode);
                    String strGetJointVisitId = mDataSource.fnGetJointVisitId(StoreIDToProcessWithoutAlret);
                    mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 5, StoreVisitCode, TmpInvoiceCodePDA,strGetJointVisitId);
                }

            }


            mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");
            //mDataSource.open();
            mDataSource.UpdateStoreImage("0", 5);
            for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
                String valSN = (String) mSelectedItems.get(nosSelected);
                int valID = stNames.indexOf(valSN);
                String stIDneeded = stIDs.get(valID);

                mDataSource.UpdateStoreFlagAtDayEndOrChangeRoute(stIDneeded, 5);
                // mDataSource.UpdateStoreImage(stIDneeded, 5);

                mDataSource.UpdateStoreMaterialphotoFlag(stIDneeded.trim(), 5);
                mDataSource.UpdateStoreCheckinFlg(stIDneeded.trim(), 5);

                mDataSource.UpdateStoreReturnphotoFlag(stIDneeded.trim(), 5);
                mDataSource.UpdateStoreClosephotoFlag(stIDneeded.trim(), 5);

                // mDataSource.UpdateNewAddedStorephotoFlag(stIDneeded.trim(), 5);


                if (mDataSource.fnchkIfStoreHasInvoiceEntry(stIDneeded) == 1) {
                    mDataSource.updateStoreQuoteSubmitFlgInStoreMstrInChangeRouteCase(stIDneeded, 0);
                }


            }

            //mDataSource.close();
            for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
                String valSN = (String) mSelectedItems.get(nosSelected);
                int valID = stNames.indexOf(valSN);
                String stIDneeded = stIDs.get(valID);
                String StoreVisitCode = mDataSource.fnGetStoreVisitCode(stIDneeded);
                String TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDAWhileSync(stIDneeded, StoreVisitCode);
                String strGetJointVisitId = mDataSource.fnGetJointVisitId(stIDneeded);
                mDataSource.UpdateStoreVisitWiseTables(stIDneeded, 4, StoreVisitCode, TmpInvoiceCodePDA,strGetJointVisitId);
                mDataSource.updateflgFromWhereSubmitStatusAgainstStore(stIDneeded, 1, StoreVisitCode);
            }
            flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;
           /* if(isOnline())
            {
                Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", whereTo);
                startActivity(syncIntent);
                finish();
            }

          else
            {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
            }*/

            if (isOnline()) {
                flgClkdBtn = 2;

                if (mDataSource.fnCheckForPendingImages() == 1) {
                    ImageSync task = new ImageSync(AllButtonActivity.this);
                    task.execute();

                } else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                    new FullSyncDataNow(AllButtonActivity.this).execute();

                } else if (CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATCycleEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgStockUnloadAtDayEnd")) {
                    if ((CommonInfo.hmapAppMasterFlags.get("flgCollReqATCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgCollReqATCycleEnd == 1)) {
                        Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                        refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);

                        startActivity(refresh);
                        finish();
                    } else if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && (CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgStockUnloadAtCycleEnd == 1)) {
                        Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);
                        intent.putExtra("IntentFrom", 2);
                        intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(intent);
                        finish();
                    } else if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && (CommonInfo.hmapAppMasterFlags.get("flgCollReqATDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                        Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                        refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(refresh);
                        finish();
                    } else if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && (CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                        Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);
                        intent.putExtra("IntentFrom", 2);
                        intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(intent);
                        finish();
                    } else {
                        valDayEndOrChangeRoute = 1;
                        flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;
                        Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                        syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                        syncIntent.putExtra("OrigZipFileName", newfullFileName);
                        syncIntent.putExtra("whereTo", whereTo);
                        startActivity(syncIntent);
                        finish();
                    }


                } else {
                    valDayEndOrChangeRoute = 1;
                    flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;

                    Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                    syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                    syncIntent.putExtra("OrigZipFileName", newfullFileName);
                    syncIntent.putExtra("whereTo", whereTo);
                    startActivity(syncIntent);
                    finish();
                }


            } else {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
            }


            /*  */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void confirmationForSubmission(String number) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(AllButtonActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Cancel..");

        // Setting Dialog Message
        if (1 < Integer.valueOf(number)) {
            alertDialog.setMessage("Are you sure you want " + number + " orders are to be cancelled ?");
        } else {
            alertDialog.setMessage("Are you sure you want " + number + " order to be cancelled ?");
        }


        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.cancel_hover);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                whatTask = 2;

                try {

                    new bgTasker().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } catch (ExecutionException e) {
                    e.printStackTrace();

                }

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void DayEnd() {


        android.app.AlertDialog.Builder alertDialogSubmitConfirm = new android.app.AlertDialog.Builder(AllButtonActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.titlebar, null);
        alertDialogSubmitConfirm.setCustomTitle(view);
        TextView title_txt = (TextView) view.findViewById(R.id.title_txt);
        title_txt.setText(getText(R.string.PleaseConformMsg));


        View view1 = inflater.inflate(R.layout.custom_alert_dialog, null);
        view1.setBackgroundColor(Color.parseColor("#1D2E3C"));
        TextView msg_txt = (TextView) view1.findViewById(R.id.msg_txt);
        msg_txt.setText(getText(R.string.genTermCycleEndAlert));
        alertDialogSubmitConfirm.setView(view1);
        alertDialogSubmitConfirm.setInverseBackgroundForced(true);


        alertDialogSubmitConfirm.setNeutralButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //mDataSource.open();

                if (mDataSource.GetLeftStoresChk() == true) {

                    //mDataSource.close();

                    whatTask = 3;

                    try {

                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }

                } else {

                    try {
                        //mDataSource.close();
                        whatTask = 1;
                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();

                    }


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
        android.app.AlertDialog alert = alertDialogSubmitConfirm.create();
        alert.show();

    }

    public void DayEndWithoutalert() {

        //mDataSource.open();

        //mDataSource.close();

        SyncNow();

    }

    public void dialogLogout() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllButtonActivity.this);

        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setMessage(R.string.LogoutMsg);
        alertDialog.setPositiveButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonInfo.AnyVisit = 0;
                CommonInfo.ActiveRouteSM = "0";
              /*  File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);
                if (!OrderXMLFolder.exists())
                {
                    OrderXMLFolder.mkdirs();
                }

                File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);
                deleteNon_EmptyDir(del);

                File ImagesFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);
                if (!ImagesFolder.exists())
                {
                    ImagesFolder.mkdirs();
                }

                File del1 = new File(Environment.getExternalStorageDirectory(),  CommonInfo.ImagesFolder);
                deleteNon_EmptyDir(del1);

                File TextFileFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.TextFileFolder);
                if (!ImagesFolder.exists())
                {
                    ImagesFolder.mkdirs();
                }

                File del2 = new File(Environment.getExternalStorageDirectory(),  CommonInfo.TextFileFolder);
                deleteNon_EmptyDir(del2);*/
              /*  try {
                    mDataSource.deleteViewAddedStore();
                    mDataSource.deletetblStoreList();
                }
                catch(Exception e)
                {

                }
*/
                dialog.dismiss();

                finishAffinity();
            }
        });

        alertDialog.setNegativeButton(R.string.AlertDialogNoButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void midPart() {
        String tempSID;
        String tempSNAME;

        stIDs = new ArrayList<String>(StoreList2Procs.length);
        stNames = new ArrayList<String>(StoreList2Procs.length);

        for (int x = 0; x < (StoreList2Procs.length); x++) {
            StringTokenizer tokens = new StringTokenizer(String.valueOf(StoreList2Procs[x]), "%");
            tempSID = tokens.nextToken().trim();
            tempSNAME = tokens.nextToken().trim();

            stIDs.add(x, tempSID);
            stNames.add(x, tempSNAME);
        }
    }

    void changelaguage() {
        ll_changelagugae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialogLanguage = new Dialog(AllButtonActivity.this);
                dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialogLanguage.setCancelable(false);
                dialogLanguage.setContentView(R.layout.language_popup);

                TextView textviewEnglish = (TextView) dialogLanguage.findViewById(R.id.textviewEnglish);
                TextView textviewHindi = (TextView) dialogLanguage.findViewById(R.id.textviewHindi);
                TextView textviewGujrati = (TextView) dialogLanguage.findViewById(R.id.textviewGujrati);

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
       /* Intent refresh = new Intent(StoreSelection.this, LauncherActivity.class);
        startActivity(refresh);
        finish();*/

        finish();
        startActivity(getIntent());

    }

    public void saveLocale(String lang) {


        SharedPreferences prefs = getSharedPreferences("LanguagePref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", lang);
        editor.commit();
    }

    void DistributorWorking() {
        ll_distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // int flgStockOut=0;

                if (isOnline()) {
                    flgClkdBtn = 1;

                    if (mDataSource.fnCheckForPendingImages() == 1) {
                        ImageSync task = new ImageSync(AllButtonActivity.this);
                        task.execute();

                    } else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                        new FullSyncDataNow(AllButtonActivity.this).execute();

                    } else {

                        // if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 )
                        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) {
                            CommonFunction.getStockData(AllButtonActivity.this, imei, CommonInfo.RegistrationID, "", 0);
                        } else {
                            Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                            i.putExtra("imei", imei);
                            i.putExtra("CstmrNodeId", CstmrNodeId);
                            i.putExtra("CstomrNodeType", CstomrNodeType);
                            i.putExtra("fDate", fDate);
                            i.putExtra("From", "AllButtonActivity");

                            startActivity(i);
                            finish();
                        }
                    }
                } else {
                    showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
                }
            }
        });
    }

    void marketVisitWorking() {
        ll_marketVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fnCheckPreviousDayDayEndDone()) {
                    openMarketVisitAlert();
                }

            }
        });
    }

    void openMarketVisitAlert() {

        if(hmapdsrIdAndDescr_details!=null && hmapdsrIdAndDescr_details.size()<2)
        {
            String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();

            CommonInfo.PersonNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            CommonInfo.PersonNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);

            String CoverageAreaNodeIDType= mDataSource.fnGetDSRNodeIdAndNodeTypeSO( ""+CommonInfo.PersonNodeType);
            slctdCoverageAreaNodeID=Integer.parseInt(CoverageAreaNodeIDType.split(Pattern.quote("^"))[0]);
            slctdCoverageAreaNodeType=Integer.parseInt(CoverageAreaNodeIDType.split(Pattern.quote("^"))[1]);

            CommonInfo.CoverageAreaNodeID=slctdCoverageAreaNodeID;
            CommonInfo.CoverageAreaNodeType=slctdCoverageAreaNodeType;
            CommonInfo.FlgDSRSO=1;

            slctdDSrSalesmanNodeId=CommonInfo.PersonNodeID;//Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            slctdDSrSalesmanNodeType=CommonInfo.PersonNodeType;//Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

            flgVisitTypeForFlow = 1;
            int routeExist = mDataSource.fnGetRouteExistOrNot(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);
            if (routeExist == 0) {
                showAlertSingleButtonInfo("There are no Routes Available for You.");
                return;
            }

            rID = mDataSource.GetActiveRouteIDCrntDSR(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

            CommonInfo.FlgDSRSO = 1;

            shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);
            //  if (mDataSource.isDataAlreadyExist(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType)) {
            shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

            shardPrefForSalesman(slctdDSrSalesmanNodeId, slctdDSrSalesmanNodeType);

            flgDataScopeSharedPref(1);
            flgDSRSOSharedPref(1);
            Intent intent = new Intent(AllButtonActivity.this, StoreSelection.class);
            intent.putExtra("imei", imei);
            intent.putExtra("userDate", userDate);
            intent.putExtra("pickerDate", fDate);
            intent.putExtra("rID", rID);
            intent.putExtra("JOINTVISITID", "NA");
            startActivity(intent);
            finish();


        }
        else {
            final AlertDialog.Builder alert = new AlertDialog.Builder(AllButtonActivity.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.market_visit_alert, null);
            alert.setView(view);

            alert.setCancelable(false);

            final RadioButton rb_myVisit = (RadioButton) view.findViewById(R.id.rb_myVisit);
            final RadioButton rb_dsrVisit = (RadioButton) view.findViewById(R.id.rb_dsrVisit);
            final RadioButton rb_jointWorking = (RadioButton) view.findViewById(R.id.rb_jointWorking);
            //rb_jointWorking.setVisibility(View.GONE);
            final Spinner spinner_ddlDistributorWorkingWith = (Spinner) view.findViewById(R.id.spinner_ddlDistributorWorkingWith);
            final Spinner spinner_dsrVisit = (Spinner) view.findViewById(R.id.spinner_dsrVisit);
            final Spinner spinner_jointWorking = (Spinner) view.findViewById(R.id.spinner_jointWorking);

            Button btn_proceed = (Button) view.findViewById(R.id.btn_proceed);
            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

            final AlertDialog dialog = alert.create();
            dialog.show();

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //dbengine.deletetblStoreList();
                    if (rb_myVisit.isChecked()) {
                        String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                        CommonInfo.PersonNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        CommonInfo.PersonNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);


                   /* String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    int tempSalesmanNodeId=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    int tempSalesmanNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                    shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                    flgDataScopeSharedPref(1);

                    shardPrefForCoverageArea(0,0);
                    flgDSRSOSharedPref(1);
                    Intent i=new Intent(AllButtonActivity.this,LauncherActivity.class);
                    startActivity(i);
                    finish();*/

                        String CoverageAreaNodeIDType= mDataSource.fnGetDSRNodeIdAndNodeTypeSO( ""+CommonInfo.PersonNodeType);
                        slctdCoverageAreaNodeID=Integer.parseInt(CoverageAreaNodeIDType.split(Pattern.quote("^"))[0]);
                        slctdCoverageAreaNodeType=Integer.parseInt(CoverageAreaNodeIDType.split(Pattern.quote("^"))[1]);

                        CommonInfo.CoverageAreaNodeID=slctdCoverageAreaNodeID;
                        CommonInfo.CoverageAreaNodeType=slctdCoverageAreaNodeType;
                        CommonInfo.FlgDSRSO=1;

                        //   String DSRPersonNodeIdAndNodeType= mDataSource.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        slctdDSrSalesmanNodeId=CommonInfo.PersonNodeID;//Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        slctdDSrSalesmanNodeType=CommonInfo.PersonNodeType;//Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                        flgVisitTypeForFlow = 1;
                        int routeExist = mDataSource.fnGetRouteExistOrNot(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);
                        if (routeExist == 0) {
                            showAlertSingleButtonInfo("There are no Routes Available for You.");
                            return;
                        }

                        rID = mDataSource.GetActiveRouteIDCrntDSR(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

                    /*CommonInfo.CoverageAreaNodeID = 0;
                    CommonInfo.CoverageAreaNodeType = 0;*/
                        CommonInfo.FlgDSRSO = 1;

                        shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);
                        //  if (mDataSource.isDataAlreadyExist(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType)) {
                        shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

                        shardPrefForSalesman(slctdDSrSalesmanNodeId, slctdDSrSalesmanNodeType);

                        flgDataScopeSharedPref(1);
                        flgDSRSOSharedPref(1);
                        Intent intent = new Intent(AllButtonActivity.this, StoreSelection.class);
                        intent.putExtra("imei", imei);
                        intent.putExtra("userDate", userDate);
                        intent.putExtra("pickerDate", fDate);
                        intent.putExtra("rID", rID);
                        intent.putExtra("JOINTVISITID", "NA");
                        startActivity(intent);
                        finish();
                  /*  } else {
                        // temp commented new GetStoresForDay(AllButtonActivity.this).execute();
                    }*/


                    /*int chkDistributorDataExistsOrNot=0;
                    int chkDistributorStockTakeMustOrNot=0;
                    if(SelectedDistributorValue.equals("") || SelectedDistributorValue.equals("Select Distributor") || SelectedDistributorValue.equals("No Distributor") )
                    {
                        showAlertForEveryOne("Please select Distributor name first then click on proceed");
                    }
*/
                    /*else if(!SelectedDistributorValue.equals("") && !SelectedDistributorValue.equals("Select Distributor") && !SelectedDistributorValue.equals("No Distributor") )
                    {
                        String DistributorIDNodeType=hmapDistrbtrList.get(SelectedDistributorValue);
                        chkDistributorDataExistsOrNot=dbengine.countDataIntblDistributorCheckIn(Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[0]),Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[1]),0);
                        chkDistributorStockTakeMustOrNot=dbengine.fnCheckflgSODistributorFirstVisit(Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[0]),Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[1]));

                        if(chkDistributorDataExistsOrNot==0)
                        {
                            //alert for distributor Stock
                            if(chkDistributorStockTakeMustOrNot==0){
                                showAlertForEveryOne("Please do Distributor visit first: "+SelectedDistributorValue + " and take Stock");
                            }
                            else{
                                showAlertForEveryOne("Please do Distributor visit first:");
                            }

                        }
                        else
                        {
                            if(dbengine.isDataAlreadyExist(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType))
                            {
                                shardPrefForCoverageArea(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType);

                                shardPrefForSalesman(slctdDSrSalesmanNodeId,slctdDSrSalesmanNodeType);

                                flgDataScopeSharedPref(1);
                                flgDSRSOSharedPref(1);

                            }
                            else
                            {
                                if(dbengine.isDBOpen())
                                {
                                    dbengine.close();
                                }


                                new GetStoresForDay(AllButtonActivity.this).execute();
                            }


                        }
                    }*/


                    } else if (rb_dsrVisit.isChecked()) {
                        if (!SelectedDSRValue.equals("")
                                && !SelectedDSRValue.equals("Select DSM")
                                && !SelectedDSRValue.equals("No DSM")) {

                            //  int flgSODistributorFirstVisit=0;
                            // flgSODistributorFirstVisit=dbengine.fnCheckflgSODistributorFirstVisit(Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[0]),Integer.parseInt(DistributorIDNodeType.split(Pattern.quote("^"))[1]));

                       /* String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        int tempCoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempCoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);


                        dbengine.open();

                        rID= dbengine.GetActiveRouteIDCrntDSR(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        dbengine.updateActiveRoute(rID, 1);
                        dbengine.close();
                        shardPrefForCoverageArea(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        flgDSRSOSharedPref(2);

                        String DSRPersonNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        int tempSalesmanNodeId=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempSalesmanNodeType=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                        flgDataScopeSharedPref(2);


                        if(dbengine.isDataAlreadyExist(tempCoverageAreaNodeID,tempCoverageAreaNodeType))
                        {
                            dbengine.open();
                            rID= dbengine.GetActiveRouteIDCrntDSR(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                            dbengine.close();

                            Date date1 = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            fDate = sdf.format(date1).toString().trim();

                            // Date date=new Date();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            String fDateNew = sdf1.format(date1).toString();
                            //fDate = passDate.trim().toString();


                        }
                        else
                        {


                            String dsrRouteID=hmapdsrIdAndDescr_details.get(SelectedDSRValue);

                            Intent i = new Intent(AllButtonActivity.this, LauncherActivity.class);
                            i.putExtra("RouteID",dsrRouteID);
                            startActivity(i);
                            finish();
                        }*/


                            String DSRNodeIdAndNodeType = mDataSource.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                            slctdCoverageAreaNodeID = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                            slctdCoverageAreaNodeType = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                            CommonInfo.CoverageAreaNodeID = slctdCoverageAreaNodeID;
                            CommonInfo.CoverageAreaNodeType = slctdCoverageAreaNodeType;
                            CommonInfo.FlgDSRSO = 2;

                            String DSRPersonNodeIdAndNodeType = mDataSource.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                            slctdDSrSalesmanNodeId = Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                            slctdDSrSalesmanNodeType = Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                            rID = mDataSource.GetActiveRouteIDCrntDSR(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

                            if (rID.equals("0")) {

                            }
                            flgVisitTypeForFlow = 2;

                            //if (mDataSource.isDataAlreadyExist(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType)) {
                            shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

                            shardPrefForSalesman(slctdDSrSalesmanNodeId, slctdDSrSalesmanNodeType);

                            flgDataScopeSharedPref(2);
                            flgDSRSOSharedPref(2);
                            Intent intent = new Intent(AllButtonActivity.this, StoreSelection.class);
                            intent.putExtra("imei", imei);
                            intent.putExtra("userDate", userDate);
                            intent.putExtra("pickerDate", fDate);
                            intent.putExtra("rID", rID);
                            intent.putExtra("JOINTVISITID", "NA");
                            startActivity(intent);
                            finish();
                            // }
                    /* else {

                            new GetStoresForDay(AllButtonActivity.this).execute();
                        }*/


                        } else {
                            showAlertSingleButtonInfo("Please select DSM to Proceed.");
                        }
                    } else if (rb_jointWorking.isChecked()) {
                        if (!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select DSM") && !SelectedDSRValue.equals("No DSM")) {
                /*        String DSRNodeIdAndNodeType = mDataSource.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        int tempCoverageAreaNodeID = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempCoverageAreaNodeType = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForCoverageArea(tempCoverageAreaNodeID, tempCoverageAreaNodeType);
                        flgDSRSOSharedPref(4);
                        flgVisitTypeForFlow = 3;*/

                            String DSRNodeIdAndNodeType = mDataSource.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                            slctdCoverageAreaNodeID = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                            slctdCoverageAreaNodeType = Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                            CommonInfo.CoverageAreaNodeID = slctdCoverageAreaNodeID;
                            CommonInfo.CoverageAreaNodeType = slctdCoverageAreaNodeType;


                            String DSRPersonNodeIdAndNodeType = mDataSource.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                            slctdDSrSalesmanNodeId = Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                            slctdDSrSalesmanNodeType = Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                            shardPrefForCoverageArea(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);
                            flgDSRSOSharedPref(4);
                            flgVisitTypeForFlow = 3;
                            rID = mDataSource.GetActiveRouteIDCrntDSR(slctdCoverageAreaNodeID, slctdCoverageAreaNodeType);

                            if (rID.equals("0")) {

                            }

                            // Find GPS
                            //surbhi loc code
                            boolean isGPSokCheckInResume = false;
                            boolean isNWokCheckInResume = false;
                            isGPSokCheckInResume = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            isNWokCheckInResume = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                            if (!isGPSokCheckInResume && !isNWokCheckInResume) {
                                try {
                                    showSettingsAlert();
                                } catch (Exception e) {

                                }
                                isGPSokCheckInResume = false;
                                isNWokCheckInResume = false;
                            } else {
                                locationRetrievingAndDistanceCalculating();
                            }
                        } else {
                            showAlertSingleButtonInfo("Please select DSM to Proceed.");
                        }
                    } else {
                        showAlertSingleButtonInfo("Please select atleast one option to Proceeds.");
                    }
                }
            });

            rb_myVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rb_myVisit.isChecked()) {
                        spinner_ddlDistributorWorkingWith.setVisibility(View.GONE);
                        rb_dsrVisit.setChecked(false);
                        rb_jointWorking.setChecked(false);
                        spinner_dsrVisit.setVisibility(View.GONE);
                        spinner_jointWorking.setVisibility(View.GONE);


                        ArrayAdapter adapterCategory = new ArrayAdapter(AllButtonActivity.this, android.R.layout.simple_spinner_item, DbrArray);
                        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_ddlDistributorWorkingWith.setAdapter(adapterCategory);
                        spinner_ddlDistributorWorkingWith.setVisibility(View.GONE);

                        spinner_ddlDistributorWorkingWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                SelectedDistributorValue = spinner_ddlDistributorWorkingWith.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                }
            });

            rb_dsrVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rb_dsrVisit.isChecked()) {
                        rb_myVisit.setChecked(false);
                        rb_jointWorking.setChecked(false);
                        spinner_jointWorking.setVisibility(View.GONE);
                        spinner_ddlDistributorWorkingWith.setVisibility(View.GONE);

                        ArrayAdapter adapterCategory = new ArrayAdapter(AllButtonActivity.this, android.R.layout.simple_spinner_item, drsNames);
                        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_dsrVisit.setAdapter(adapterCategory);
                        spinner_dsrVisit.setVisibility(View.VISIBLE);

                        spinner_dsrVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                SelectedDSRValue = spinner_dsrVisit.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                }
            });

            rb_jointWorking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rb_jointWorking.isChecked()) {
                        rb_myVisit.setChecked(false);
                        rb_dsrVisit.setChecked(false);
                        spinner_dsrVisit.setVisibility(View.GONE);
                        spinner_ddlDistributorWorkingWith.setVisibility(View.GONE);

                        ArrayAdapter adapterCategory = new ArrayAdapter(AllButtonActivity.this, android.R.layout.simple_spinner_item, drsNames);
                        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_jointWorking.setAdapter(adapterCategory);
                        spinner_jointWorking.setVisibility(View.VISIBLE);

                        spinner_jointWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                SelectedDSRValue = spinner_jointWorking.getSelectedItem().toString();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

                    }
                }
            });

            dialog.show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (GPSONOFFAlert != null && GPSONOFFAlert.isShowing()) {
            GPSONOFFAlert.dismiss();
        }
    }

    void reportsWorking() {
        ll_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (CommonInfo.VanLoadedUnloaded == 1) {
                    showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

                } else {*/
                    SharedPreferences.Editor editor = sharedPrefReport.edit();
                    editor.putString("fromPage", "1");
                    editor.commit();


                if(hmapdsrIdAndDescr_details!=null && hmapdsrIdAndDescr_details.size()<2)
                {
                    String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    int tempSalesmanNodeId= Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    int tempSalesmanNodeType= Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                    shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);

                    flgDataScopeSharedPref(1);
                    CommonInfo.SalesmanNodeId=0;
                    CommonInfo.SalesmanNodeType=0;
                    CommonInfo.flgDataScope=1;
                    Intent i=new Intent(AllButtonActivity.this,DetailReportSummaryActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                   /* Intent intent = new Intent(AllButtonActivity.this, DetailReportSummaryActivity.class);

                    intent.putExtra("imei", imei);
                    intent.putExtra("userDate", currSysDate);
                    intent.putExtra("pickerDate", fDate);
                    intent.putExtra("rID", rID);
                    intent.putExtra("back", "0");
                    // intent.putExtra("fromPage","AllButtonActivity");

                    startActivity(intent);
                    finish();*/

                    Intent storeIntent = new Intent(AllButtonActivity.this, DialogActivity_Report.class);

                    startActivity(storeIntent);
                }
               // }


            }
        });
    }

    void storeValidationWorking() {
        if (fnCheckPreviousDayDayEndDone()) {
            ll_storeVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (CommonInfo.hmapAppMasterFlags != null && CommonInfo.hmapAppMasterFlags.size() > 0 && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
                        if (CommonInfo.VanLoadedUnloaded == 1) {
                            showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

                        } else {
                            Intent intent = new Intent(AllButtonActivity.this, StorelistActivity.class);
                            intent.putExtra("activityFrom", "AllButtonActivity");
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(AllButtonActivity.this, StorelistActivity.class);
                        intent.putExtra("activityFrom", "AllButtonActivity");
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }
    }

    void distributorCheckInWorking() {
        ll_distrbtrCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalDis = mDataSource.counttblSupplierMstrList();
                int alreadyLocFind = mDataSource.fetchtblIsDBRStockSubmitted();
                if (alreadyLocFind == 0) {
                    //mDataSource.open();
                    mDataSource.maintainPDADate();
                    String getPDADate = mDataSource.fnGetPdaDate();
                    String getServerDate = mDataSource.fnGetServerDate();

                    //mDataSource.close();


                    //changes
                    if (imei == null) {
                        imei = CommonInfo.imei;
                    }
                    if (fDate == null) {
                        Date date1 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        fDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);
                    }

                    Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                    i.putExtra("imei", imei);
                    i.putExtra("From", "AllButtonActivity");
                    i.putExtra("fDate", fDate);
                    startActivity(i);
                    finish();
                } else {
                    if (totalDis > 1) {
                        //mDataSource.open();
                        mDataSource.maintainPDADate();
                        String getPDADate = mDataSource.fnGetPdaDate();
                        String getServerDate = mDataSource.fnGetServerDate();

                        //mDataSource.close();


                        //changes
                        if (imei == null) {
                            imei = CommonInfo.imei;
                        }
                        if (fDate == null) {
                            Date date1 = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            fDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);
                        }

                        Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                        i.putExtra("imei", imei);
                        i.putExtra("From", "AllButtonActivity");
                        i.putExtra("fDate", fDate);
                        startActivity(i);
                        finish();
                    } else {
                        showAlertSingleButtonInfo(getResources().getString(R.string.DistributorCheckInAlrady));
                    }

                }


            }
        });
    }

    void warehouseCheckInWorking() {
        ll_warehose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalDis = mDataSource.counttblSupplierMstrList();
                int alreadyLocFind = mDataSource.fetchtblIsDBRStockSubmitted();
                if (alreadyLocFind == 0) {
                    //mDataSource.open();
                    mDataSource.maintainPDADate();
                    String getPDADate = mDataSource.fnGetPdaDate();
                    String getServerDate = mDataSource.fnGetServerDate();

                    //mDataSource.close();


                    //changes
                    if (imei == null) {
                        imei = CommonInfo.imei;
                    }
                    if (fDate == null) {
                        Date date1 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        fDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);
                    }

                    Intent i = new Intent(AllButtonActivity.this, WarehouseCheckInSecondActivity.class);
                    i.putExtra("imei", imei);
                    i.putExtra("From", "AllButtonActivity");
                    i.putExtra("fDate", fDate);
                    startActivity(i);
                    finish();
                } else {
                    if (totalDis > 1) {
                        //mDataSource.open();
                        mDataSource.maintainPDADate();
                        String getPDADate = mDataSource.fnGetPdaDate();
                        String getServerDate = mDataSource.fnGetServerDate();

                        //mDataSource.close();


                        //changes
                        if (imei == null) {
                            imei = CommonInfo.imei;
                        }
                        if (fDate == null) {
                            Date date1 = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            fDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);
                        }

                        Intent i = new Intent(AllButtonActivity.this, WarehouseCheckInSecondActivity.class);
                        i.putExtra("imei", imei);
                        i.putExtra("From", "AllButtonActivity");
                        i.putExtra("fDate", fDate);
                        startActivity(i);
                        finish();
                    } else {
                        showAlertSingleButtonInfo(getResources().getString(R.string.DistributorCheckInAlrady));
                    }

                }


            }
        });
    }

    void executionWorking() {
        ll_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_execution.setEnabled(true);
                Intent storeIntent = new Intent(AllButtonActivity.this, InvoiceStoreSelection.class);
                storeIntent.putExtra("imei", imei);
                storeIntent.putExtra("userDate", currSysDate);
                storeIntent.putExtra("pickerDate", fDate);
                storeIntent.putExtra("activityFrom", "AllButtonActivity");
                storeIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                startActivity(storeIntent);
                // finish();
            }
        });
    }

    public void shardPrefForCoverageArea(int coverageAreaNodeID, int coverageAreaNodeType) {


        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CoverageAreaNodeID", coverageAreaNodeID);
        editor.putInt("CoverageAreaNodeType", coverageAreaNodeType);


        editor.commit();

    }

    public void shardPrefForSalesman(int salesmanNodeId, int salesmanNodeType) {


        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("SalesmanNodeId", salesmanNodeId);
        editor.putInt("SalesmanNodeType", salesmanNodeType);

        editor.commit();

    }

    public void flgDataScopeSharedPref(int _flgDataScope) {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDataScope", _flgDataScope);
        editor.commit();


    }

    public void flgDSRSOSharedPref(int _flgDSRSO) {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDSRSO", _flgDSRSO);
        editor.commit();


    }

    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialogGps = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle("Information");
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage("GPS is not enabled. \nPlease select all settings on the next page!");

        // On pressing Settings button
        alertDialogGps.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        GPSONOFFAlert = alertDialogGps.create();
        GPSONOFFAlert.show();
    }

    public void locationRetrievingAndDistanceCalculating() {

        String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();

        String SONodeID=SONodeIdAndNodeType.split(Pattern.quote("^"))[0];
        String SONodeType=SONodeIdAndNodeType.split(Pattern.quote("^"))[1];


        String DSRPersonNodeIdAndNodeType = mDataSource.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
        String DSrSalesmanNodeId = DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0];
        String DSrSalesmanNodeType = DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1];


        String DSRCoverageNodeIdAndNodeType = mDataSource.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
        int CoverageAreaNodeID = Integer.parseInt(DSRCoverageNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
        int CoverageAreaNodeType = Integer.parseInt(DSRCoverageNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        int existJointVisitID=mDataSource.fnCheckAlreadyJointVisitIDMarrked(DSrSalesmanNodeId,DSrSalesmanNodeType,SelectedDSRValue);
        String uniqueJointVisitId="NA";
        if(existJointVisitID==1)
        {
            uniqueJointVisitId=mDataSource.fnGetCurrentJointVisitIDMarked(DSrSalesmanNodeId,DSrSalesmanNodeType,SelectedDSRValue);
            CommonInfo.FlgDSRSO = 3;
            flgDataScopeSharedPref(3);
            flgDSRSOSharedPref(3);

            Intent intent = new Intent(AllButtonActivity.this, StoreSelection.class);
            intent.putExtra("imei", imei);
            intent.putExtra("userDate", userDate);
            intent.putExtra("pickerDate", fDate);
            intent.putExtra("rID", rID);
            intent.putExtra("JOINTVISITID", uniqueJointVisitId);
            startActivity(intent);
            finish();
        }
        else {

            LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
            llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 20, 1);
        }


    }

    public void setStoresList() {

        //mDataSource.open();

        //System.out.println("Arjun has rID :"+rID);

     /*   storeList = mDataSource.FetchStoreList();
        storeStatus = mDataSource.FetchStoreStatus(rID);

        storeCloseStatus = mDataSource.FetchStoreStoreCloseStatus(rID);

        storeNextDayStatus = mDataSource.FetchStoreStoreNextDayStatus();
        StoreflgSubmitFromQuotation = mDataSource.FetchStoreStatusflgSubmitFromQuotation();
        hmapStoreLatLongDistanceFlgRemap = mDataSource.fnGeStoreList(CommonInfo.DistanceRange);
        //mDataSource.close();

        storeCode = new String[storeList.length];
        storeName = new String[storeList.length];

        for (int splitval = 0; splitval <= (storeList.length - 1); splitval++) {
            StringTokenizer tokens = new StringTokenizer(String.valueOf(storeList[splitval]), "_");
            storeCode[splitval] = tokens.nextToken().trim();
            storeName[splitval] = tokens.nextToken().trim();

        }


        float density = getResources().getDisplayMetrics().density;

        TableRow.LayoutParams paramRB = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, (int) (10 * density));


        LayoutInflater inflater = getLayoutInflater();

        for (int current = 0; current < storeList.length; current++) {

            final TableRow row = (TableRow) inflater.inflate(R.layout.table_row1, tl2, false);

            final RadioButton rb1 = (RadioButton) row.findViewById(R.id.rg1StoreName);
            final CheckBox check1 = (CheckBox) row.findViewById(R.id.check1);

            final CheckBox check2 = (CheckBox) row.findViewById(R.id.check2);

            rb1.setTag(storeCode[current]);
            rb1.setText("  " + storeName[current]);
            rb1.setTextSize(14.0f);
            rb1.setChecked(false);

            check1.setTag(storeCode[current]);
            check1.setChecked(false);
            check1.setEnabled(false);

            check2.setTag(storeCode[current]);
            check2.setChecked(false);
            check2.setEnabled(false);

            if ((storeCloseStatus[current].equals("1"))) {
                check1.setChecked(true);
            }

            if ((storeNextDayStatus[current].equals("1"))) {
                check2.setChecked(true);
            }

            if ((((storeStatus[current].split(Pattern.quote("~"))[0]).equals("3")) || ((storeStatus[current].split(Pattern.quote("~"))[0]).equals("4"))) && (StoreflgSubmitFromQuotation[current]).equals("0") || ((storeStatus[current].split(Pattern.quote("~"))[0]).equals("5")) || ((storeStatus[current].split(Pattern.quote("~"))[0]).equals("6"))) {
                //StoreflgSubmitFromQuotation
                rb1.setEnabled(false);
                rb1.setTypeface(null, Typeface.BOLD);
                rb1.setTextColor(this.getResources().getColor(R.color.green_submitted));
            } else {
            }

            if (((storeStatus[current].split(Pattern.quote("~"))[0]).equals("1"))) {
                if ((storeStatus[current].split(Pattern.quote("~"))[1]).equals("1")) {
                    rb1.setTypeface(null, Typeface.BOLD);
                    rb1.setTextColor(Color.BLUE);
                } else {
                    rb1.setTypeface(null, Typeface.BOLD);
                    rb1.setTextColor(Color.RED);
                }
            }


            rb1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    for (int xc = 0; xc < storeList.length; xc++) {
                        TableRow dataRow = (TableRow) tl2.getChildAt(xc);

                        RadioButton child1;
                        CheckBox child2;
                        CheckBox child3;

                        child1 = (RadioButton) dataRow.findViewById(R.id.rg1StoreName);
                        child2 = (CheckBox) dataRow.findViewById(R.id.check1);
                        child3 = (CheckBox) dataRow.findViewById(R.id.check2);


                        child1.setChecked(false);
                        child2.setEnabled(false);
                        child3.setEnabled(false);

                    }

                    check1.setEnabled(true);
                    check2.setEnabled(true);

                    selStoreID = arg0.getTag().toString();

                    //mDataSource.open();
                    selStoreName = mDataSource.FetchStoreName(selStoreID);
                    //mDataSource.close();

                    RadioButton child2get12 = (RadioButton) arg0;
                    child2get12.setChecked(true);
                    check1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            int checkStatus = 0;
                            CheckBox child2get = (CheckBox) v;
                            String Sid = v.getTag().toString().trim();
                            boolean ch = false;
                            ch = child2get.isChecked();
                            if ((ch == true)) {
                                // checkStatus=1;
                                //System.out.println("1st checked  with Store ID :"+ Sid);
                                long syncTIMESTAMP = System.currentTimeMillis();
                                Date dateobj = new Date(syncTIMESTAMP);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                                String startTS = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_TIME_FORMAT);

                                Date currDate = new Date();
                                SimpleDateFormat currDateFormat = new SimpleDateFormat(
                                        "dd-MM-yyyy", Locale.ENGLISH);
                                String currSysDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);
                                if (!currSysDate.equals(fDate)) {
                                    fullFileName1 = fDate + " 12:00:00";
                                }
                                //mDataSource.open();
                                mDataSource.updateCloseflg(Sid, 1);
                                System.out.println("DateTimeNitish 1");
                                mDataSource.UpdateStoreStartVisit(selStoreID, startTS);
                                // mDataSource.UpdateStoreEndVisit(selStoreID,
                                // fullFileName1);

                                //mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "0.00", "" + "0.00", "" + "0.00","" + "NA");

                                String passdLevel = battLevel + "%";
                                mDataSource.UpdateStoreVisitBatt(selStoreID, passdLevel);

                                mDataSource.UpdateStoreEndVisit(selStoreID, startTS);
                                //mDataSource.close();

                            } else {
                                //System.out.println("1st unchecked with Store ID :"+ Sid);
                                //mDataSource.open();
                                mDataSource.updateCloseflg(Sid, 0);
                                //mDataSource.delStoreCloseNextData(selStoreID);

                                //mDataSoureUpdateCloseNextStoreData(Sid);

								*//*mDataSource.UpdateStoreStartVisit(selStoreID,"");
								mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "", "" + "", "" + "","" + "");
								mDataSource.UpdateStoreVisitBatt(selStoreID,"");
								mDataSource.UpdateStoreEndVisit(selStoreID,"");*//*

                                //mDataSource.close();
                            }

                        }
                    });

                    check2.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            int checkStatus = 0;
                            CheckBox child2get = (CheckBox) v;
                            boolean ch = false;
                            ch = child2get.isChecked();
                            String Sid = v.getTag().toString().trim();
                            if ((ch == true)) {
                                // checkStatus=1;
                                //System.out.println("2nd checked with Store ID :"+ Sid);
                                long syncTIMESTAMP = System.currentTimeMillis();
                                Date dateobj = new Date(syncTIMESTAMP);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                                String startTS = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_TIME_FORMAT);

                                Date currDate = new Date();
                                SimpleDateFormat currDateFormat = new SimpleDateFormat(
                                        "dd-MM-yyyy", Locale.ENGLISH);
                                String currSysDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

                                if (!currSysDate.equals(fDate)) {
                                    fullFileName1 = fDate + " 12:00:00";
                                }
                                //mDataSource.open();
                                System.out.println("DateTimeNitish2");
                                mDataSource.updateNextDayflg(Sid, 1);

                                mDataSource.UpdateStoreStartVisit(selStoreID,
                                        startTS);
                                // mDataSource.UpdateStoreEndVisit(selStoreID,
                                // fullFileName1);

                                //mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "0.00", "" + "0.00", "" + "0.00","" + "NA");

                                String passdLevel = battLevel + "%";
                                mDataSource.UpdateStoreVisitBatt(selStoreID,
                                        passdLevel);

                                mDataSource.UpdateStoreEndVisit(selStoreID,
                                        startTS);

                                //mDataSource.close();

                            } else {
                                System.out
                                        .println("2nd unchecked with Store ID :"
                                                + Sid);
                                //mDataSource.open();
                                mDataSource.updateNextDayflg(Sid, 0);
                                //mDataSource.delStoreCloseNextData(selStoreID);

                                //mDataSource.UpdateCloseNextStoreData(Sid);

								*//*mDataSource.UpdateStoreStartVisit(selStoreID,"");
								mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "", "" + "", "" + "","" + "");
								mDataSource.UpdateStoreVisitBatt(selStoreID,"");
								mDataSource.UpdateStoreEndVisit(selStoreID,"");*//*

                                //mDataSource.close();
                            }

                        }
                    });

                }
            });


            tl2.addView(row);

        }*/
    }

    public void showAlertStockOut(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllButtonActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alertDialog.show();
    }

    public void showDayEndProcess(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllButtonActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        alertDialog.show();
    }

    public void uploadImage(String sourceFileUri, String fileName, String tempIdImage) throws IOException {
        BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
        IMGoptions01.inDither = false;
        IMGoptions01.inPurgeable = true;
        IMGoptions01.inInputShareable = true;
        IMGoptions01.inTempStorage = new byte[16 * 1024];

        //finalBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fnameIMG,IMGoptions01), 640, 480, false);

        Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(sourceFileUri).getPath(), IMGoptions01);

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
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        ////System.out.println("image_str: "+image_str);

        stream.flush();
        stream.close();
        //buffer.clear();
        //buffer = null;
        bitmap.recycle();
  /*      nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
        nameValuePairs.add(new BasicNameValuePair("TempID", tempIdImage));*/
        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        String onlyDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, "dd-MMM-yyyy HH:mm:ss.SSS");

        nameValuePairs.add(new BasicNameValuePair("image", image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
        nameValuePairs.add(new BasicNameValuePair("comment", "NA"));
        nameValuePairs.add(new BasicNameValuePair("storeID", "0"));
        nameValuePairs.add(new BasicNameValuePair("date", onlyDate));
        nameValuePairs.add(new BasicNameValuePair("routeID", "0"));
        try {

            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);

            //HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);

            HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout + 2000);


            HttpClient httpclient = new DefaultHttpClient(httpParams);

            HttpPost httppost = new HttpPost(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

            String the_string_response = convertResponseToString(response);
            if (the_string_response.equals("SUCCESS")) {
                mDataSource.updateSSttImage(fileName, 4);
                mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);

                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + fileName;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }

            }

        } catch (Exception e) {

            System.out.println(e);
            //	IMGsyOK = 1;

        }
    }

    public String BitMapToString(Bitmap bitmap) {
        int h1 = bitmap.getHeight();
        int w1 = bitmap.getWidth();

        if (w1 > 768 || h1 > 1024) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1024, 768, true);

        } else {

            bitmap = Bitmap.createScaledBitmap(bitmap, w1, h1, true);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] arr = baos.toByteArray();
        String result = android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
        return result;
    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0) {
        } else {
            byte[] data = new byte[512];
            int len = 0;
            try {
                while (-1 != (len = inputStream.read(data))) {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close(); // closing the stream…..
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(AllButtonActivity.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            AllButtonActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public int upLoad2Server(String sourceFileUri, String fileUri) {

        fileUri = fileUri.replace(".xml", "");

        String fileName = fileUri;
        String zipFileName = fileUri;

        String newzipfile = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + fileName + ".zip";

        sourceFileUri = newzipfile;

        xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + fileName + ".xml";


        try {
            zip(xmlForWeb, newzipfile);
        } catch (Exception e1) {
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
        String urlString = "";
        if (zipFileName.contains(".xml")) {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName;

        } else {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + zipFileName + ".xml";

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

            while (bytesRead > 0) {
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

            if (serverResponseCode == 200) {
                syncFLAG = 1;


                mDataSource.upDateTblXmlFile(fileName);
                delXML(xmlForWeb[0].toString());


            } else {
                syncFLAG = 0;
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return serverResponseCode;

    }

    public void delXML(String delPath) {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
    }

    public void showAlertSingleWareHouseStockconfirButtonInfo(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AllButtonActivity.this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(AllButtonActivity.this, AllButtonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public void DayEndCodeAfterSummary() {
        isDayEndClicked = false;

        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        // check number of files in folder
        final String[] AllFilesNameNotSync = checkNumberOfFiles(del);

        String xmlfileNames = mDataSource.fnGetXMLFile("3");
        // String xmlfileNamesStrMap=mDataSoureSo.fnGetXMLFile("3");

        // mDataSource.open();
        String[] SaveStoreList = mDataSource.SaveStoreList();
        // mDataSource.close();
        if (xmlfileNames.length() > 0 || SaveStoreList.length != 0) {
            if (isOnline()) {


                whereTo = "11";
                //////System.out.println("Abhinav store Selection  Step 1");
                //////System.out.println("StoreList2Procs(before): " + StoreList2Procs.length);

                //////System.out.println("StoreList2Procs(after): " + StoreList2Procs.length);
                // mDataSource.open();

                StoreList2Procs = mDataSource.ProcessStoreReq();
                if (StoreList2Procs.length != 0) {
                    //whereTo = "22";
                    //////System.out.println("Abhinav store Selection  Step 2");
                    midPart();
                    dayEndCustomAlert(1);
                    //showPendingStorelist(1);
                    //  mDataSource.close();

                } else if (mDataSource.GetLeftStoresChk() == true) {
                    //////System.out.println("Abhinav store Selection  Step 7");
                    //enableGPSifNot();
                    // showChangeRouteConfirm();
                    DayEnd();
                    // mDataSource.close();
                } else {
                    DayEndWithoutalert();
                }
            } else {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));


            }
        } else {
            //showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));
            if (isOnline()) {
                whereTo = "11";
                DayEndWithoutalert();
            } else {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
            }

        }
    }

    @Override
    public void success(int flgCalledFrom) {
        if (flgCalledFrom == 0) {
            //  if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 )
            if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
                int flgStockOut = mDataSource.fetchtblStockUploadedStatus();
                int statusId = mDataSource.confirmedStock();
                //  flgStockOut=1;

                if (flgStockOut == 0) {
                    showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection), getResources().getString(R.string.AlertVANStockStockOutWareHouse) + " " + tv_Warehouse.getText().toString());
                } else if (statusId == 3) {
                    showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection), getResources().getString(R.string.AlertVANStockConfrmDstrbtr));
                } else {
                    if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp") == 1) {
                        Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                        i.putExtra("imei", imei);
                        i.putExtra("From", "AllButtonActivity");
                        i.putExtra("fDate", fDate);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(AllButtonActivity.this, WarehouseCheckInFirstActivity.class);

                        i.putExtra("imei", imei);
                        i.putExtra("CstmrNodeId", CstmrNodeId);
                        i.putExtra("CstomrNodeType", CstomrNodeType);
                        i.putExtra("fDate", fDate);

                        startActivity(i);
                        finish();
                    }
                }
            } else {
                Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                i.putExtra("imei", imei);
                i.putExtra("From", "AllButtonActivity");
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();
            }
        } else if (flgCalledFrom == 1) {
            int flgStockRqst = mDataSource.fetchtblStockUploadedStatusForRqstStatus();
            //  flgStockOut=1;
            if (serviceException) {
                serviceException = false;
                showAlertStockOut("Error", "Error While Retrieving Data.");
                // showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData));
                //    Toast.makeText(AllButtonActivity.this,"Please fill Stock out first for starting your market visit.",Toast.LENGTH_SHORT).show();
                //  showSyncError();
            } else if (flgStockRqst == 0 || flgStockRqst == 2) {
                Intent intent = new Intent(AllButtonActivity.this, StockRequestActivity.class);
                startActivity(intent);
                finish();


            } else if (flgStockRqst == 4) {

                showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection), getResources().getString(R.string.AlertDayEndCnfrmForRqstStk));

            } else {
                showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection), getResources().getString(R.string.AlertVANStockForRqstStk));
            }


        }

    }

    @Override
    public void failure(int flgCalledFrom) {


        showAlertStockOut("Error", "Error While Retrieving Data.");

    }

    public void cycleEndCode() {


        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

// check number of files in folder
        final String[] AllFilesNameNotSync = checkNumberOfFiles(del);

        String xmlfileNames = mDataSource.fnGetXMLFile("3");
        // String xmlfileNamesStrMap=mDataSoureSo.fnGetXMLFile("3");

        //mDataSource.open();
        String[] SaveStoreList = mDataSource.SaveStoreList();
        //mDataSource.close();
        if (xmlfileNames.length() > 0 || SaveStoreList.length != 0) {
            if (isOnline()) {


                whereTo = "11";

                //mDataSource.open();

                StoreList2Procs = mDataSource.ProcessStoreReq();
                if (StoreList2Procs.length != 0) {

                    midPart();
                    dayEndCustomAlert(1);
                    //mDataSource.close();

                } else if (mDataSource.GetLeftStoresChk() == true) {
                    DayEnd();

                } else {
                    DayEndWithoutalert();
                }
            } else {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));


            }
        } else {
            //showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));
            if (isOnline()) {
                whereTo = "11";
                DayEndWithoutalert();
            } else {
                showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
            }

        }


    }

    public void showDialogForFlag() {
        flgStockUnloadAtCycleEnd = 1;
        flgCollReqATCycleEnd = 1;
        final Dialog dialogLanguage = new Dialog(AllButtonActivity.this);
        dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        dialogLanguage.setCancelable(false);
        dialogLanguage.setContentView(R.layout.dayend_flag_popup);
        final CheckBox cb_collection = (CheckBox) dialogLanguage.findViewById(R.id.cb_collection);
        final CheckBox cb_stockUnload = (CheckBox) dialogLanguage.findViewById(R.id.cb_stockUnload);
        final Button button_Ok = (Button) dialogLanguage.findViewById(R.id.button_Ok);
        final Button btncncle = (Button) dialogLanguage.findViewById(R.id.btncncle);
        cb_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_collection.isChecked()) {
                    cb_collection.setChecked(true);
                    flgCollReqATCycleEnd = 1;
                } else {
                    cb_collection.setChecked(false);
                    flgCollReqATCycleEnd = 0;
                }

            }
        });

        cb_stockUnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_stockUnload.isChecked()) {
                    cb_stockUnload.setChecked(true);
                    flgStockUnloadAtCycleEnd = 1;
                } else {
                    cb_stockUnload.setChecked(false);
                    flgStockUnloadAtCycleEnd = 0;
                }
            }
        });
        //------------------------------flgStockUnloadAtCycleEnd
        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStockUnloadAtCycleEnd")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd") == 1) {

            } else {
                cb_stockUnload.setChecked(false);
                cb_stockUnload.setVisibility(View.GONE);
                flgStockUnloadAtCycleEnd = 0;

            }
        } else {
            cb_stockUnload.setChecked(false);
            cb_stockUnload.setVisibility(View.GONE);
            flgStockUnloadAtCycleEnd = 0;
        }
        //------------------------------flgCollReqATCycleEnd
        if (CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATCycleEnd")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgCollReqATCycleEnd") == 1) {

            } else {
                cb_collection.setChecked(false);
                cb_collection.setVisibility(View.GONE);
                flgCollReqATCycleEnd = 0;
            }
        } else {
            cb_collection.setChecked(false);
            cb_collection.setVisibility(View.GONE);
            flgCollReqATCycleEnd = 0;
        }
        button_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLanguage.dismiss();
                cycleEndCode();
            }
        });

        btncncle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLanguage.dismiss();
            }
        });
        dialogLanguage.show();
    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOff = 0;
        flgGPSOnOff = 0;
        flgNetworkOnOff = 0;
        flgFusedOnOff = 0;
        flgInternetOnOffWhileLocationTracking = 0;

        if (isGooglePlayServicesAvailable()) {
            flgFusedOnOff = 1;
        }
        if (isOnline()) {
            flgInternetOnOffWhileLocationTracking = 1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
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
        checkHighAccuracyLocationMode(AllButtonActivity.this);
       // mDataSource.deleteLocationTable();
        String SONodeIdAndNodeType= mDataSource.fnGetPersonNodeIDAndPersonNodeTypeForSO();

        String SONodeID=SONodeIdAndNodeType.split(Pattern.quote("^"))[0];
        String SONodeType=SONodeIdAndNodeType.split(Pattern.quote("^"))[1];


        String DSRPersonNodeIdAndNodeType = mDataSource.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
        String DSrSalesmanNodeId = DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0];
        String DSrSalesmanNodeType = DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1];


        String DSRCoverageNodeIdAndNodeType = mDataSource.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
        int CoverageAreaNodeID = Integer.parseInt(DSRCoverageNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
        int CoverageAreaNodeType = Integer.parseInt(DSRCoverageNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        int existJointVisitID=mDataSource.fnCheckAlreadyJointVisitIDMarrked(DSrSalesmanNodeId,DSrSalesmanNodeType,SelectedDSRValue);
        String uniqueJointVisitId="NA";
        if(existJointVisitID==0)
        {
            uniqueJointVisitId = AppUtils.getIMEI(AllButtonActivity.this) + "_" + System.currentTimeMillis();


            JointVisitDetail jointVisitDetail = new JointVisitDetail();
            jointVisitDetail.setJointVisitId(uniqueJointVisitId);
            jointVisitDetail.setImei(AppUtils.getIMEI(AllButtonActivity.this));
            jointVisitDetail.setManagerNodeId(Integer.parseInt(SONodeID));
            jointVisitDetail.setManagerNodeType(Integer.parseInt(SONodeType));

            jointVisitDetail.setRouteId(0);
            jointVisitDetail.setRouteType(0);
            jointVisitDetail.setCoverageId(CoverageAreaNodeID);
            jointVisitDetail.setCoverageType(CoverageAreaNodeType);
            jointVisitDetail.setActualLatitude(Double.parseDouble(fnLati));
            jointVisitDetail.setActualLongitude(Double.parseDouble(fnLongi));
            jointVisitDetail.setLocProvider(fnAccurateProvider);
            jointVisitDetail.setAccuracy(fnAccuracy);
            jointVisitDetail.setVisitDate(AppUtils.getCurrentDate(AllButtonActivity.this));
            jointVisitDetail.setVisitStartTime(AppUtils.getCurrentTime(AllButtonActivity.this));
            jointVisitDetail.setSstat(3);
            jointVisitDetail.setIsCompleted(0);


            jointVisitDetail.setGpsAccuracy(Double.parseDouble(NetwAccuracy));
            jointVisitDetail.setGpsLatitude(Double.parseDouble(NetwLat));
            jointVisitDetail.setGpsLongitude(Double.parseDouble(NetwLong));
            jointVisitDetail.setGpsAddress(GpsAddress);
            jointVisitDetail.setFlgGpsOff(flgGPSOnOff);
            jointVisitDetail.setFlgFusedOnOff(flgFusedOnOff);

            jointVisitDetail.setFlgNetworkOff(flgNetworkOnOff);
            jointVisitDetail.setFusedAccuracy(Double.parseDouble(FusedAccuracy));
            jointVisitDetail.setFusedAddress(FusedAddress);
            jointVisitDetail.setFusedLatitude(Double.parseDouble(fnLati));
            jointVisitDetail.setFusedLongitude(Double.parseDouble(fnLongi));


            jointVisitDetail.setAccuracy(fnAccuracy);
            jointVisitDetail.setAddress(AllProvidersLocation);
            jointVisitDetail.setAllProviderData(AllProvidersLocation);
            jointVisitDetail.setLocProvider(fnAccurateProvider);


            mDataSource.createJointVisit(jointVisitDetail);

            if (uniqueJointVisitId != null && uniqueJointVisitId.length() > 0) {
                JointVisitMemberDetail jointVisitMemberDetail = new JointVisitMemberDetail();
                jointVisitMemberDetail.setJointVisitId(uniqueJointVisitId);

                    jointVisitMemberDetail.setFellowPersonNodeId(Integer.parseInt(DSrSalesmanNodeId));
                    jointVisitMemberDetail.setFellowPersonNodeType(Integer.parseInt(DSrSalesmanNodeType));
                    jointVisitMemberDetail.setFellowPersonName(SelectedDSRValue);
                    mDataSource.insertJointVisitFellowDetails(jointVisitMemberDetail);

            }

           // mDataSource.saveTblLocationDetails("0",fnLati, fnLongi, String.valueOf(fnAccuracy), address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt,DSrSalesmanNodeId,DSrSalesmanNodeType,SONodeID,SONodeType,getDateAndTimeInMilliSecond(),1,0,"NA",uniqueJointVisitId,0,0,3,0,0);
            mDataSource.saveTblLocationDetails(fnLati, fnLongi, finalAccuracy, address, city, pincode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);

        }



            if(flgVisitTypeForFlow==3)
            {
                String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

                String VisitDate = TimeUtils.getNetworkDateTime(AllButtonActivity.this, TimeUtils.DATE_FORMAT);

                Intent mMyServiceIntent = new Intent(AllButtonActivity.this, SyncJobService.class);  //is any of that needed?  idk.
                mMyServiceIntent.putExtra("whereTo", "Regular");//
                mMyServiceIntent.putExtra("storeID", selStoreID);
                mMyServiceIntent.putExtra("routeID", presentRoute);//

                SyncJobService.enqueueWork(AllButtonActivity.this, mMyServiceIntent);
                alertJointVisitSubmit();
            }
            else {
                CommonInfo.FlgDSRSO = 3;
                flgDataScopeSharedPref(3);
                flgDSRSOSharedPref(3);
                Intent intent = new Intent(AllButtonActivity.this, StoreSelection.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", userDate);
                intent.putExtra("pickerDate", fDate);
                intent.putExtra("rID", rID);
                intent.putExtra("JOINTVISITID", uniqueJointVisitId);
                startActivity(intent);
                finish();
            }

       /* hmapOutletListForNear = mDataSource.fnGetALLOutletMstr();
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
                    System.out.println("Shvam Distance = " + DistanceNear);
                }
                if (!DistanceNear.equals("")) {
                    //853399-81752acdc662-NA
                    if (outID.equals("853399-a1445e87daf4-NA")) {
                        System.out.println("Shvam Distance = " + DistanceNear);
                    }
                    mDataSource.UpdateStoreDistanceNear(outID, Integer.parseInt(DistanceNear));
                }
            }
            //mDataSource.close();
        }
        //send to storeListpage page
        //From, addr,zipcode,city,state,errorMessageFlag,username,totaltarget,todayTarget
        int flagtoShowStorelistOrAddnewStore = mDataSource.fncheckCountNearByStoreExistsOrNot(CommonInfo.DistanceRange);


        if (flagtoShowStorelistOrAddnewStore == 1) {
            //getDataFromDatabaseToHashmap();
            if (tl2.getChildCount() > 0) {
                tl2.removeAllViews();
                // dynamcDtaContnrScrollview.removeAllViews();
                //addViewIntoTable();
                setStoresList();
            } else {
                //addViewIntoTable();
                setStoresList();
            }


        } else {


        }
        if (pDialog2STANDBY.isShowing()) {
            pDialog2STANDBY.dismiss();
        }

        Intent intent = new Intent(AllButtonActivity.this, StorelistActivity.class);
        intent.putExtra("activityFrom", "AllButtonActivity");
        startActivity(intent);
        finish();*/
    }

    public void alertJointVisitSubmit()
    {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(AllButtonActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.DataSent));

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.JointvisitMarkedSucc));

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed

                alertDialog.dismiss();
                finishAffinity();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void success() {
        AppUtils.showAlertSingleButtonInfo(this,"Data Successfully Refreshed");

    }

    @Override
    public void failure() {
           AppUtils.showAlertForError(this,"Error while retrieving data from server");
    }

    private class bgTasker extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {
                //mDataSource.open();
             /*   String rID=mDataSource.GetActiveRouteID();

                mDataSource.UpdateTblDayStartEndDetails(Integer.parseInt(rID), valDayEndOrChangeRoute);*/
                //mDataSource.close();


                if (whatTask == 2) {
                    whatTask = 0;

                    //mDataSource.open();
                    mDataSource.UpdateStoreImage("0", 3);
                    for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
                        String valSN = (String) mSelectedItems.get(nosSelected);
                        int valID = stNames.indexOf(valSN);
                        String stIDneeded = stIDs.get(valID);


                        mDataSource.UpdateStoreFlagAtDayEndOrChangeRoute(stIDneeded, 3);
                        //  mDataSource.UpdateStoreImage(stIDneeded, 3);

                        mDataSource.UpdateNewAddedStorephotoFlag(stIDneeded.trim(), 3);
                        mDataSource.insertTblSelectedStoreIDinChangeRouteCase(stIDneeded);

                      /*  String  StoreVisitCode=mDataSource.fnGetStoreVisitCode(stIDneeded);
                        String TmpInvoiceCodePDA=mDataSource.fnGetInvoiceCodePDA(stIDneeded,StoreVisitCode);
                        mDataSource.UpdateStoreVisitWiseTables(stIDneeded, 3,StoreVisitCode,TmpInvoiceCodePDA);*/
                        if (mDataSource.fnchkIfStoreHasInvoiceEntry(stIDneeded) == 1) {
                            mDataSource.updateStoreQuoteSubmitFlgInStoreMstrInChangeRouteCase(stIDneeded, 0);
                        }
                    }

                    //mDataSource.close();

                    pDialog2.dismiss();


                    SyncNow();


                } else if (whatTask == 3) {
                    // sync rest
                    whatTask = 0;

                    pDialog2.dismiss();

                    SyncNow();

                } else if (whatTask == 1) {
                    // clear all
                    whatTask = 0;

                    SyncNow();

                    //mDataSource.open();
                    //String rID=mDataSource.GetActiveRouteID();
                    //mDataSource.updateActiveRoute(rID, 0);
                    // mDataSource.reCreateDB();

                    //mDataSource.close();
                }


            } catch (Exception e) {
                Log.i("bgTasker", "bgTasker Execution Failed!", e);

            } finally {

                Log.i("bgTasker", "bgTasker Execution Completed...");

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(AllButtonActivity.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
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
            whatTask = 0;

        }
    }

    private class ImageSync extends AsyncTask<Void, Void, Boolean> {
        // ProgressDialog pDialogGetStores;
        public ImageSync(AllButtonActivity activity) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress(getResources().getString(R.string.SubmittingPndngDataMsg));

        }

        @Override
        protected Boolean doInBackground(Void... args) {
            boolean isErrorExist = false;


            try {
                //mDataSource.upDateCancelTask("0");
                ArrayList<String> listImageDetails = new ArrayList<String>();

                listImageDetails = mDataSource.getImageDetails(5);

                if (listImageDetails != null && listImageDetails.size() > 0) {
                    for (String imageDetail : listImageDetails) {
                        String tempIdImage = imageDetail.split(Pattern.quote("^"))[0].toString();
                        String imagePath = imageDetail.split(Pattern.quote("^"))[1].toString();
                        String imageName = imageDetail.split(Pattern.quote("^"))[2].toString();
                        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
                        File fImage = new File(file_dj_path);
                        if (fImage.exists()) {
                            uploadImage(imagePath, imageName, tempIdImage);
                        }


                    }
                }


            } catch (Exception e) {
                isErrorExist = true;
            } finally {
                Log.i("SvcMgr", "Service Execution Completed...");
            }

            return isErrorExist;
        }

        @Override
        protected void onPostExecute(Boolean resultError) {
            super.onPostExecute(resultError);


            dismissProgress();


            mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
            if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
                new FullSyncDataNow(AllButtonActivity.this).execute();
            } else {
                if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0  && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
                    if (flgClkdBtn == 1) {
                        //  if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 )
                        if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
                            CommonFunction.getStockData(AllButtonActivity.this, imei, CommonInfo.RegistrationID, "", 0);
                        } else {
                            Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                            i.putExtra("imei", imei);

                            i.putExtra("fDate", fDate);
                            i.putExtra("From", "AllButtonActivity");
                            startActivity(i);
                            finish();
                        }

                    } else if (CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATCycleEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgStockUnloadAtDayEnd")) {
                        if ((CommonInfo.hmapAppMasterFlags.get("flgCollReqATCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgCollReqATCycleEnd == 1)) {
                            Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                            refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                            refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                            startActivity(refresh);
                            finish();
                        } else if ((CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgStockUnloadAtCycleEnd == 1)) {
                            Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);
                            intent.putExtra("IntentFrom", 2);
                            intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                            intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                            startActivity(intent);
                            finish();
                        } else if ((CommonInfo.hmapAppMasterFlags.get("flgCollReqATDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                            Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                            refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                            refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                            startActivity(refresh);
                            finish();
                        } else if ((CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                            Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);

                            intent.putExtra("IntentFrom", 2);
                            intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                            intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                            startActivity(intent);
                            finish();
                        } else {
                            valDayEndOrChangeRoute = 1;
                            flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;
                            Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                            syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                            syncIntent.putExtra("OrigZipFileName", newfullFileName);
                            syncIntent.putExtra("whereTo", whereTo);
                            startActivity(syncIntent);
                            finish();
                        }


                    } else {
                        valDayEndOrChangeRoute = 1;
                        flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;

                        Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                        syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                        syncIntent.putExtra("OrigZipFileName", newfullFileName);
                        syncIntent.putExtra("whereTo", whereTo);
                        startActivity(syncIntent);
                        finish();
                    }
                    /*
                    Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                    startActivity(refresh);
                    finish();*/

                }
                if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0 &&(CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 0 || CommonInfo.hmapAppMasterFlags.get("flgDayEnd") == 2)) {
                    valDayEndOrChangeRoute = 1;
                    flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;

                    Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                    //syncIntent.putExtra("xmlPathForSync",Environment.getExternalStorageDirectory() + "/TJUKIndirectSFAxml/" + newfullFileName + ".xml");
                    syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                    syncIntent.putExtra("OrigZipFileName", newfullFileName);
                    syncIntent.putExtra("whereTo", whereTo);
                    startActivity(syncIntent);
                    finish();
                }

            }


        }
    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        int responseCode = 0;

        public FullSyncDataNow(AllButtonActivity activity) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            File LTFoodXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!LTFoodXMLFolder.exists()) {
                LTFoodXMLFolder.mkdirs();
            }


            showProgress(getResources().getString(R.string.SubmittingPndngDataMsg));

        }

        @Override

        protected Void doInBackground(Void... params) {


            try {


                File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                // check number of files in folder
                String[] AllFilesName = checkNumberOfFiles(del);


                if (AllFilesName != null && AllFilesName.length > 0) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


                    for (int vdo = 0; vdo < AllFilesName.length; vdo++) {
                        String fileUri = AllFilesName[vdo];


                        //System.out.println("Sunil Again each file Name :" +fileUri);

                        if (fileUri.contains(".zip")) {
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri);
                            file.delete();
                        } else {
                            String f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri;
                            // System.out.println("Sunil Again each file full path"+f1);
                            try {
                                responseCode = upLoad2Server(f1, fileUri);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (responseCode != 200) {
                            break;
                        }

                    }

                } else {
                    responseCode = 200;
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissProgress();

            if (responseCode == 200) {

                mDataSource.deleteXmlTable("4");
                mDataSource.UpdateStoreVisitWiseTablesAfterSync(4);
                // if(CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")==1) {
                if (flgClkdBtn == 1) {
                    //  if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 )
                    if (CommonInfo.hmapAppMasterFlags!= null &&  CommonInfo.hmapAppMasterFlags.size()>0  && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") == 1) {
                        CommonFunction.getStockData(AllButtonActivity.this, imei, CommonInfo.RegistrationID, "", 0);
                    } else {
                        Intent i = new Intent(AllButtonActivity.this, DistributorCheckInSecondActivity.class);
                        i.putExtra("imei", imei);
                        i.putExtra("From", "AllButtonActivity");
                        i.putExtra("fDate", fDate);
                        startActivity(i);
                        finish();
                    }

                } else if (CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATCycleEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqATDayEnd") || CommonInfo.hmapAppMasterFlags.containsKey("flgStockUnloadAtDayEnd")) {
                    if ((CommonInfo.hmapAppMasterFlags.get("flgCollReqATCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgCollReqATCycleEnd == 1)) {
                        Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                        refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(refresh);
                        finish();
                    } else if ((CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd") == 1) && (dayEndButtonOrCycleEnd == 2) && (flgStockUnloadAtCycleEnd == 1)) {
                        Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);
                        intent.putExtra("IntentFrom", 2);
                        intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(intent);
                        finish();
                    } else if ((CommonInfo.hmapAppMasterFlags.get("flgCollReqATDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                        Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                        refresh.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        refresh.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(refresh);
                        finish();
                    } else if ((CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd") == 1) && (dayEndButtonOrCycleEnd == 1)) {
                        Intent intent = new Intent(AllButtonActivity.this, StockUnloadEndClosure.class);
                        intent.putExtra("IntentFrom", 2);
                        intent.putExtra("flgCollReqATCycleEnd", flgCollReqATCycleEnd);
                        intent.putExtra("flgStockUnloadAtCycleEnd", flgStockUnloadAtCycleEnd);
                        startActivity(intent);
                        finish();
                    } else {
                        valDayEndOrChangeRoute = 1;
                        flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;
                        Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                        syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                        syncIntent.putExtra("OrigZipFileName", newfullFileName);
                        syncIntent.putExtra("whereTo", whereTo);
                        startActivity(syncIntent);
                        finish();
                    }


                } else {
                    valDayEndOrChangeRoute = 1;
                    flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;
                    Intent syncIntent = new Intent(AllButtonActivity.this, SyncMaster.class);
                    syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                    syncIntent.putExtra("OrigZipFileName", newfullFileName);
                    syncIntent.putExtra("whereTo", whereTo);
                    startActivity(syncIntent);
                    finish();
                }
                //}

                   /* Intent refresh = new Intent(AllButtonActivity.this, DayCollectionReport.class);
                    startActivity(refresh);
                    finish();*/

            } else {
                showAlertSingleButtonError(getString(R.string.uploading_error_data));
            }


        }
    }

    private class GetStoreAllData extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showProgress(getResources().getString(R.string.RetrivingDataMsg));
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mDataSource.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                int DatabaseVersion = CommonInfo.DATABASE_VERSIONID;
                int ApplicationID = CommonInfo.Application_TypeID;
                //newservice = newservice.getAvailableAndUpdatedVersionOfApp(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);

                mDataSource.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
               /* for(int mm = 1; mm<6; mm++)
                {
                    if(mm==2)
                    {
                        newservice = newservice.getStoreAllDetails(getApplicationContext(), imei, fDate, DatabaseVersion, ApplicationID);
                        if (!newservice.director.trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }


                    }
                    if(mm==3)
                    {
                        newservice = newservice.callfnSingleCallAllWebService(getApplicationContext(),ApplicationID,imei);
                        if (!newservice.director.trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }

                    }
                    if(mm==4)
                    {
                        newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, "0");
                        if (!newservice.director.trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }

                    }
                    if(mm==1)
                    {
                        newservice = newservice.getSOSummary(getApplicationContext(), imei, fDate, DatabaseVersion, ApplicationID);
                        if (!newservice.director.trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }
                    }

                    if(mm==5)
                    {


                        newservice = newservice.fnGetStateCityListMstr(AllButtonActivity.this,imei, fDate,CommonInfo.Application_TypeID);
                        if(!newservice.director.trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                chkFlgForErrorToCloseApp=1;
                                break;
                            }

                        }
                    }
                }*/






            } catch (Exception e)
            {
                dismissProgress();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissProgress();

            if (chkFlgForErrorToCloseApp == 1)   // if Webservice showing exception or not excute complete properly
            {
                chkFlgForErrorToCloseApp = 0;
                SharedPreferences sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor ed;
                if(sharedPreferences.contains("ServerDate"))
                {
                    ed = sharedPreferences.edit();
                    ed.putString("ServerDate", "0");
                    ed.commit();
                }
                //clear sharedpreferences
                // intentPassToLauncherActivity("Internet connection is slow ,please try again.");//, "0", "0", "0"
                showAlertSingleButtonInfo(getResources().getString(R.string.internetError));
            }
            else
            {

                mDataSource.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                Intent intent =new Intent(AllButtonActivity.this,StorelistActivity.class);
                AllButtonActivity.this.startActivity(intent);
                finish();


            }
        }

    }
}
