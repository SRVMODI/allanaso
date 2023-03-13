package com.astix.allanasosfa;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.printer.DeviceListActivity;
import com.astix.allanasosfa.printer.UnicodeFormatter;
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
import com.allanasosfa.truetime.TimeUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class CollectionActivityNew extends BaseActivity implements DatePickerDialog.OnDateSetListener, LocationInterface, View.OnFocusChangeListener, Runnable {
    //Bluetooth Code

    public String fnaddress="NA";
    public String AllProvidersLocation="NA";
    public String GpsAddress="NA";
    public String NetwAddress="NA";
    public String FusedAddress="NA";
    public String FusedLocationLatitudeWithFirstAttempt="0";
    public String FusedLocationLongitudeWithFirstAttempt="0";
    public String FusedLocationAccuracyWithFirstAttempt="0";


    ImageView btn_bck;
    TextView tv_crntInvoiceText,tv_minCollectiontext;
    private CoundownClass countDownTimer;
    //protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc, mPrintImage, usbPrint;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    int aletrDialogFlag = 0;
    public boolean rstl = false;
    public int flgOnlySubmitOrPrint = 0;
    //Bluetooth end
    ArrayList<LinkedHashMap<String, ArrayList<String>>> arrAllPrintResult;

    /* For Location Srvices Start*/

    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;
    public Double overallOutStand = 0.0, curntCol = 0.0;
    public String VisitTimeInSideStore = "NA";
    public int flgRestartOrderReview = 0;
    public int flgStoreOrderOrderReview = 0;

    String fusedData;
    String mLastUpdateTime;
    int countSubmitClicked = 0;
    Location mCurrentLocation;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    PowerManager pm;
    LocationListener locationListener;
    double latitude;
    double longitude;
    public LocationManager locationManager;
    public Location location;
    // PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;

    public int butClickForGPS = 0;
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
    public String fnAccurateProvider = "";
    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;


    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; //1 second
    private final long startTime = 10000;
    private final long interval = 200;

    public int flgLocationServicesOnOffOrderReview = 0;
    public int flgGPSOnOffOrderReview = 0;
    public int flgNetworkOnOffOrderReview = 0;
    public int flgFusedOnOffOrderReview = 0;
    public int flgInternetOnOffWhileLocationTrackingOrderReview = 0;

    public int powerCheck = 0;
    public PowerManager.WakeLock wl;
    /* Fro Location Services Ends*/

    public int flgTransferStatus = 0;
    public String strFinalAllotedInvoiceIds = "NA";
    public String newfullFileName;
    public String StoreVisitCode = "NA";
    public String CollectionCode = "NA";
    public String strGlobalInvoiceNumber = "NA";
    public String TmpInvoiceCodePDA = "NA";
    public int chkflgInvoiceAlreadyGenerated = 0;
    public CheckBox cb_collection;
    LinearLayout lnCollection, ll_collectionMandatory;
    public int flgVisitCollectionMarkedStatus = 0;
    TextView totaltextview, dateTextViewFirst, dateTextViewSecond, dateTextViewThird, pymtModeTextView, AmountTextview, chequeNoTextview, DateLabelTextview, BankLabelTextview;

    TextView BankSpinnerSecond, BankSpinnerThird;
    Double OverAllAmountCollected = 0.0;
    TextView paymentModeSpinnerFirst, paymentModeSpinnerSecond, paymentModeSpinnerThird;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    View convertView;
    ListView listviewPaymentModeFirst, listviewPaymentModeSecond, listviewBankFirst, listviewBankSecond, listviewBankThird;
    ArrayAdapter<String> adapterPaymentModeFirst, adapterPaymentModeSecond, adapterBankFirst, adapterBankSecond, adapterBankThird;
    String[] pymtModeFirstList, pymtModeSecondList, bankfirstList, bankSecondList;
    String[] pymtModeThirdList, bankThirdList;
    LinkedHashMap<String, String> hashmapPymtMdFirst, hashmapPymtMdSecond, hashmapBank, linkedHmapBankID, linkedHmapPaymentModeID;
    EditText inputSearch;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    boolean calFirst = false;
    boolean calSecond = false;
    boolean calThird = false;
    EditText amountEdittextFirst, amountEdittextSecond, amountEdittextThird, checqueNoEdittextSecond, checqueNoEdittextThird;


    Button Done_btn, btn_print, PrintOnly;
    String storeIDGlobal;
    String flagNew_orallDataFromDatabase, PaymentModeUpdate, PaymentModeSecondUpdate, AmountUpdate, ChequeNoUpdte, DateUpdate;
    String BankNameUpdate, BankNameSecondUpdate = "0", BankNameThirdUpdate = "0";
    public String storeID;
    public int flgFromPlace = 0;
    public String imei;
    int onlyPrintFlag = 0;
    public String date;
    public String pickerDate;
    public String SN;
    public String strGlobalOrderID = "0";
    int flgOrderType = 0;
    public EditText et_SelfCreditNote;
    public TextView tv_outstandingvalue, tv_MinCollectionvalue, tv_cntInvoceValue, tv_totOutstandingValue;

    HashMap<String, Integer> hmapDistPrdctStockCount = new HashMap<String, Integer>();
    HashMap<String, String> hmapPrdctIdOutofStock = new HashMap<String, String>();
    Context ctx;

    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
            mCustomKeyboardNum.hideCustomKeyboard();

            return false;


//			return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // finish();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        setContentView(R.layout.collection_activity);
        ctx = this;
        StoreSelection.flgChangeRouteOrDayEnd = 0;
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isGPSok = false;
        boolean isNWok = false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSok) {
            isGPSok = false;
        }
        if (!isNWok) {
            isNWok = false;
        }
        if (!isGPSok && !isNWok) {
            try {
                showSettingsAlert();
            } catch (Exception e) {

            }

            isGPSok = false;
            isNWok = false;
        }
        VisitTimeInSideStore = mDataSource.fnGetDateTimeString();
        if (powerCheck == 0) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            wl.acquire();
        }

        hashmapBank = mDataSource.fnGettblBankMaster();
        mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        mCustomKeyboardNumWithoutDecimal = new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal);

        getDataFromIntent();

        TextviewInitialization();

        EdittextInitialization();

        DropDownInitialization();

        CalendarInitialization();

        ButtonInialization();


        if(CommonInfo.flgDrctslsIndrctSls==2)
        {

            chkflgInvoiceAlreadyGenerated= mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode, CommonInfo.flgDrctslsIndrctSls);
            if(chkflgInvoiceAlreadyGenerated==1)
            {
                TmpInvoiceCodePDA= mDataSource.fnGetInvoiceCodePDA(storeID,StoreVisitCode);

            }
            else if(mDataSource.fnCheckForNewInvoiceOrPreviousValueFromPermanentTable(storeID,StoreVisitCode)==1)
            {
                TmpInvoiceCodePDA= mDataSource.fnGetInvoiceCodePDAFromPermanentTable(storeID,StoreVisitCode);
            }

        }
        else {
            chkflgInvoiceAlreadyGenerated= mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode, CommonInfo.flgDrctslsIndrctSls);
            if (chkflgInvoiceAlreadyGenerated == 1) {
                TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

            }
        }
       /* if (chkflgInvoiceAlreadyGenerated == 1) {
            TmpInvoiceCodePDA = mDataSource.fnGetExistingInvoiceNumber(storeID);
        }*/


        cb_collection = (CheckBox) findViewById(R.id.cb_collection);
        tv_outstandingvalue = (TextView) findViewById(R.id.tv_outstandingvalue);
        Double outstandingvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        outstandingvalue = Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));
        //String.format("%.2f", outstandingvalue)
        tv_outstandingvalue.setText(String.format("%.2f", outstandingvalue));

        tv_MinCollectionvalue = (TextView) findViewById(R.id.tv_MinCollectionvalue);
        Double MinCollectionvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        MinCollectionvalue = Double.parseDouble(new DecimalFormat("##.##").format(MinCollectionvalue));
        tv_MinCollectionvalue.setText(String.format("%.2f", MinCollectionvalue));

        tv_cntInvoceValue = (TextView) findViewById(R.id.tv_cntInvoceValue);
        Double cntInvoceValue = mDataSource.fetch_Store_InvValAmount(storeID, TmpInvoiceCodePDA);
        cntInvoceValue = Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));
        tv_cntInvoceValue.setText(String.format("%.2f", cntInvoceValue));


        Double cntAllOustandings = mDataSource.fetch_Store_AllOustandings(storeID);
        cntAllOustandings = Double.parseDouble(new DecimalFormat("##.##").format(cntAllOustandings));


        Double cntTotCollectionAmtAgainstStoreIrespectiveOfVisit = mDataSource.fnTotCollectionAmtAgainstStoreIrespectiveOfVisit(storeID);
        cntTotCollectionAmtAgainstStoreIrespectiveOfVisit = Double.parseDouble(new DecimalFormat("##.##").format(cntTotCollectionAmtAgainstStoreIrespectiveOfVisit));


        Double cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit = mDataSource.fnTotInvoicesAmtAgainstStoreIrespectiveOfVisit(storeID);
        cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit = Double.parseDouble(new DecimalFormat("##.##").format(cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit));


        tv_totOutstandingValue = (TextView) findViewById(R.id.tv_totOutstandingValue);
        Double totOutstandingValue = cntAllOustandings + cntInvoceValue + cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit - cntTotCollectionAmtAgainstStoreIrespectiveOfVisit;
        //Double totOutstandingValue=cntAllOustandings+cntInvoceValue-cntTotCollectionAmtAgainstStoreIrespectiveOfVisit;
        totOutstandingValue = Double.parseDouble(new DecimalFormat("##.##").format(totOutstandingValue));
        tv_totOutstandingValue.setText(String.format("%.2f", totOutstandingValue));

        linkedHmapPaymentModeID = mDataSource.fnGetPaymentMode();
        linkedHmapBankID = mDataSource.fnGetBankIdData();
        fetchData();

        lnCollection = (LinearLayout) findViewById(R.id.lnCollection);
        ll_collectionMandatory = (LinearLayout) findViewById(R.id.ll_collectionMandatory);
        flgVisitCollectionMarkedStatus = mDataSource.checkflgVisitCollectionMarkedStatus(storeID, TmpInvoiceCodePDA);
        //lnCollection.setVisibility(View.GONE);
        // ll_collectionMandatory.setVisibility(View.GONE);

        if (outstandingvalue > 0.0 && cntInvoceValue > 0.0) {
            ll_collectionMandatory.setVisibility(View.VISIBLE);
            lnCollection.setVisibility(View.VISIBLE);
        } else if (outstandingvalue == 0.0 && cntInvoceValue > 0.0) {
            ll_collectionMandatory.setVisibility(View.VISIBLE);
            lnCollection.setVisibility(View.VISIBLE);
        } else if (outstandingvalue > 0.0 && cntInvoceValue == 0.0) {
            ll_collectionMandatory.setVisibility(View.VISIBLE);
            lnCollection.setVisibility(View.VISIBLE);
        }

        if (totOutstandingValue > 0.0 && cntInvoceValue== 0.0) {
            ll_collectionMandatory.setVisibility(View.VISIBLE);
            lnCollection.setVisibility(View.VISIBLE);
            btn_print.setVisibility(View.GONE);
        }


        if (flgVisitCollectionMarkedStatus == 1) {

            cb_collection.setChecked(true);
        }
        cb_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_collection.isChecked() == true) {
                    lnCollection.setVisibility(View.INVISIBLE);
                    flgVisitCollectionMarkedStatus = 1;
                    mDataSource.fnUpdateflgVisitCollectionMarkedStatus(storeID, StoreVisitCode, TmpInvoiceCodePDA, flgVisitCollectionMarkedStatus);
                }
                if (cb_collection.isChecked() == false) {
                    lnCollection.setVisibility(View.VISIBLE);
                    flgVisitCollectionMarkedStatus = 0;
                    mDataSource.fnUpdateflgVisitCollectionMarkedStatus(storeID, StoreVisitCode, TmpInvoiceCodePDA, flgVisitCollectionMarkedStatus);
                }
            }
        });

        Double valSelfCreditNote = 0.0;
        valSelfCreditNote = mDataSource.fnGetStoreVisitSelfCreditNote(storeIDGlobal, StoreVisitCode);
        valSelfCreditNote = Double.parseDouble(new DecimalFormat("##.##").format(valSelfCreditNote));
        et_SelfCreditNote.setText("" + valSelfCreditNote);

        if(CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartOutstandingDetails")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgVisitStartOutstandingDetails") == 0) {
                LinearLayout ll_outstanding = (LinearLayout)findViewById(R.id.ll_outstanding);
                ll_outstanding.setVisibility(View.GONE);
            }
        }
    }

    public void fetchData() {
        try {
            String DataFromDatabase = mDataSource.fnRetrieveCollectionDataBasedOnStoreID(StoreVisitCode, storeIDGlobal, strGlobalOrderID, TmpInvoiceCodePDA);
            if (!DataFromDatabase.equals("0")) {
                if (DataFromDatabase.contains("$")) {
                    String[] collectionData = DataFromDatabase.split(Pattern.quote("$"));
                    for (int i = 0; i <= collectionData.length; i++) {
                        String collectionDataString = collectionData[i];
                        SetDataToLayout(collectionDataString);
                    }
                } else {
                    SetDataToLayout(DataFromDatabase);
                }

               /* if(!DataFromDatabase.split(Pattern.quote("^"))[0].equals("0"))
                {
                    amountEdittextFirst.setText(DataFromDatabase.split(Pattern.quote("^"))[0]);
                }
                else
                {
                    amountEdittextFirst.setText("");
                }
                if(!DataFromDatabase.split(Pattern.quote("^"))[1].equals("0")) {
                    amountEdittextSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[1]);
                }
                else
                {
                    amountEdittextSecond.setText("");
                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[2].equals("0")) {
                    checqueNoEdittextSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[2]);
                }
                else
                {
                    checqueNoEdittextSecond.setText("");
                }
                dateTextViewSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[3]);
                BankNameUpdate = DataFromDatabase.split(Pattern.quote("^"))[4];
                if (BankNameUpdate.equals("0"))
                {
                    BankSpinnerSecond.setText(getResources().getString(R.string.txtSelect));
                } else
                {
                    BankSpinnerSecond.setText(linkedHmapBankID.get(BankNameUpdate));

                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[5].equals("0")) {
                    amountEdittextThird.setText(DataFromDatabase.split(Pattern.quote("^"))[5]);
                }
                else
                {
                    amountEdittextThird.setText("");
                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[6].equals("0")) {
                    checqueNoEdittextThird.setText(DataFromDatabase.split(Pattern.quote("^"))[6]);
                }
                else
                {
                    checqueNoEdittextThird.setText("");
                }
                dateTextViewThird.setText(DataFromDatabase.split(Pattern.quote("^"))[7]);
                BankNameUpdate = DataFromDatabase.split(Pattern.quote("^"))[8];
                if (BankNameUpdate.equals("0")) {
                    BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
                } else {
                    BankSpinnerThird.setText(linkedHmapBankID.get(BankNameUpdate));

                }*/

            }


        } catch (Exception e) {

        }
    }

    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");
        flgOrderType = passedvals.getIntExtra("flgOrderType", 0);
        strGlobalOrderID = passedvals.getStringExtra("OrderPDAID");
        flgFromPlace = Integer.parseInt(passedvals.getStringExtra("FromPlace"));

        storeIDGlobal = storeID;
        //StoreVisitCode = mDataSource.fnGetStoreVisitCode(storeID);
        ArrayList<Object> arrayList=  mDataSource.FetchStoreCollectionFLag(storeID);
        CommonInfo.flgCollDefControl=(Integer) arrayList.get(0);
        CommonInfo.CollectionPer=(Double) arrayList.get(1);

        if(CommonInfo.flgDrctslsIndrctSls==2)
        {

            StoreVisitCode= mDataSource.fnGetStoreVisitCodeInCaseOfIndrectSales(storeID);
        }
        else
        {

            StoreVisitCode= mDataSource.fnGetStoreVisitCode(storeID);
        }
    }

    public void EdittextInitialization() {

        et_SelfCreditNote = (EditText) findViewById(R.id.et_SelfCreditNote);
        amountEdittextFirst = (EditText) findViewById(R.id.amountEdittextFirst);
        amountEdittextSecond = (EditText) findViewById(R.id.amountEdittextSecond);
        amountEdittextThird = (EditText) findViewById(R.id.amountEdittextThird);

        amountEdittextFirst.setOnFocusChangeListener(this);
        amountEdittextFirst.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(amountEdittextFirst.getWindowToken(),0);
                return false;
            }
        });

        amountEdittextFirst.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(amountEdittextFirst.getWindowToken(),0);
            }
        });


        amountEdittextSecond.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(amountEdittextSecond.getWindowToken(),0);
            }
        });

        amountEdittextThird.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(amountEdittextThird.getWindowToken(),0);
            }
        });


        amountEdittextSecond.setOnFocusChangeListener(this);
        amountEdittextThird.setOnFocusChangeListener(this);
        et_SelfCreditNote.setOnFocusChangeListener(this);
        checqueNoEdittextSecond = (EditText) findViewById(R.id.checqueNoEdittextSecond);
        checqueNoEdittextSecond.setOnFocusChangeListener(this);
        checqueNoEdittextThird = (EditText) findViewById(R.id.checqueNoEdittextThird);
        checqueNoEdittextThird.setOnFocusChangeListener(this);

        checqueNoEdittextSecond.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(checqueNoEdittextSecond.getWindowToken(),0);
            }
        });

        checqueNoEdittextThird.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(checqueNoEdittextThird.getWindowToken(),0);
            }
        });

        amountEdittextFirst.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst = 0.00;
                double amntSecond = 0.00;
                double amntThird = 0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if (!amountEdittextFirst.getText().toString().trim().equals("")) {
                    // amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }

                }
                if (!amountEdittextSecond.getText().toString().trim().equals("")) {
                    //amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }
                if (!amountEdittextThird.getText().toString().trim().equals("")) {
                    //amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst + amntSecond + amntThird));
                OverAllAmountCollected = amntFirst + amntSecond + amntThird;

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

        amountEdittextSecond.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst = 0.00;
                double amntSecond = 0.00;
                double amntThird = 0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if (!amountEdittextSecond.getText().toString().trim().equals("")) {
                    // amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }
                if (!amountEdittextFirst.getText().toString().trim().equals("")) {
                    // amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }

                }

                if (!amountEdittextThird.getText().toString().trim().equals("")) {
                    // amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst + amntSecond + amntThird));
                OverAllAmountCollected = amntFirst + amntSecond + amntThird;
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


        amountEdittextThird.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst = 0.00;
                double amntSecond = 0.00;
                double amntThird = 0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if (!amountEdittextSecond.getText().toString().trim().equals("")) {
                    //amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }
                if (!amountEdittextFirst.getText().toString().trim().equals("")) {
                    //amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }

                }

                if (!amountEdittextThird.getText().toString().trim().equals("")) {
                    //amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    } catch (ParseException e) {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst + amntSecond + amntThird));
                OverAllAmountCollected = amntFirst + amntSecond + amntThird;
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

    }


    public void DropDownInitialization() {
        paymentModeSpinnerFirst = (TextView) findViewById(R.id.paymentModeSpinnerFirst);
        paymentModeSpinnerSecond = (TextView) findViewById(R.id.paymentModeSpinnerSecond);
        paymentModeSpinnerThird = (TextView) findViewById(R.id.paymentModeSpinnerThird);

        BankSpinnerSecond = (TextView) findViewById(R.id.BankSpinnerSecond);
        BankSpinnerThird = (TextView) findViewById(R.id.BankSpinnerThird);


        paymentModeSpinnerFirst.setText("Cash");
        amountEdittextFirst.setEnabled(true);

        paymentModeSpinnerSecond.setText("Cheque/DD");
        amountEdittextSecond.setEnabled(true);

        paymentModeSpinnerThird.setText("Electronic");
        //paymentModeSpinnerThird.setText("Digital");
        amountEdittextThird.setEnabled(true);


        if (paymentModeSpinnerFirst.equals("Cash")) {
            amountEdittextFirst.setEnabled(true);
            dateTextViewFirst.setBackgroundResource(R.drawable.outside_boundry_gray);
            BankNameUpdate = "0";
            dateTextViewFirst.setEnabled(false);
            dateTextViewFirst.setText("");
        }

        if (paymentModeSpinnerSecond.equals("Cheque/DD")) {
            dateTextViewSecond.setBackgroundResource(R.drawable.outside_boundry);
            checqueNoEdittextSecond.setBackgroundResource(R.drawable.edittex_with_white_background);
            BankSpinnerSecond.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
            amountEdittextSecond.setEnabled(true);
            checqueNoEdittextSecond.setEnabled(true);
            dateTextViewSecond.setEnabled(true);
            BankSpinnerSecond.setEnabled(true);
        }

        if(paymentModeSpinnerThird.equals("Electronic"))
        {
            paymentModeSpinnerThird.setText("Electronic");
            dateTextViewThird.setBackgroundResource(R.drawable.outside_boundry);
            checqueNoEdittextThird.setBackgroundResource(R.drawable.edittex_with_white_background);
            BankSpinnerThird.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
            amountEdittextThird.setEnabled(true);
            checqueNoEdittextThird.setEnabled(true);
            dateTextViewThird.setEnabled(true);
            BankSpinnerThird.setEnabled(true);
        }

        /*if (paymentModeSpinnerThird.equals("Digital")) {
            paymentModeSpinnerThird.setText("Digital");
            dateTextViewThird.setBackgroundResource(R.drawable.outside_boundry_gray);
            checqueNoEdittextThird.setBackgroundResource(R.drawable.edittext_with_gray_background);//.edittex_with_white_background);
            BankSpinnerThird.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
            amountEdittextThird.setEnabled(true);
            checqueNoEdittextThird.setEnabled(false);
            dateTextViewThird.setEnabled(false);
            BankSpinnerThird.setEnabled(false);
            dateTextViewThird.setText("");


        }*/


        BankSpinnerSecond.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!amountEdittextSecond.getText().toString().trim().equals("") && !checqueNoEdittextSecond.getText().toString().trim().equals("") && !dateTextViewSecond.getText().toString().trim().equals("21-mar-16")) {
                    BankLabelTextview.setError(null);
                    BankLabelTextview.clearFocus();


                    alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = (View) inflater.inflate(R.layout.activity_list,
                            null);

                    listviewBankSecond = (ListView) convertView.findViewById(R.id.list_view);

                    int index = 0;
                    if (hashmapBank != null) {
                        bankSecondList = new String[hashmapBank.size() + 1];
                        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(
                                hashmapBank);
                        Set set2 = map.entrySet();
                        Iterator iterator = set2.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry me2 = (Map.Entry) iterator.next();
                            if (index == 0) {
                                bankSecondList[index] = "Select";

                                index = index + 1;

                                bankSecondList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            } else {
                                bankSecondList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            }

                        }

                    }
                    adapterBankSecond = new ArrayAdapter<String>(CollectionActivityNew.this,
                            R.layout.list_item, R.id.product_name, bankSecondList);

                    listviewBankSecond.setAdapter(adapterBankSecond);
                    inputSearch = (EditText) convertView
                            .findViewById(R.id.inputSearch);
                    inputSearch.setVisibility(View.VISIBLE);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Bank");
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                            // TODO Auto-generated method stub
                            adapterBankSecond.getFilter().filter(arg0.toString().trim());

                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                    listviewBankSecond.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            String abc = listviewBankSecond.getItemAtPosition(arg2)
                                    .toString().trim();
                            BankSpinnerSecond.setText(abc);
                            inputSearch.setText("");

                            ad.dismiss();

                            if (abc.equals("Select")) {


                            } else {
                            }
                        }
                    });
                    ad = alertDialog.show();


                } else {
                    if (amountEdittextSecond.getText().toString().trim().equals("")) {
                        // allMessageAlert("Please Enter the Amount.");
                        showAlertSingleButtonError("Please Enter the Amount.");
                    } else if (checqueNoEdittextSecond.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    } else if (dateTextViewSecond.getText().toString().trim().equals("21-mar-16")) {
                        showAlertSingleButtonError("Please Select the Date.");
                    } else {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo. or Select Date");
                    }


                }
            }

        });


        BankSpinnerThird.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!amountEdittextThird.getText().toString().trim().equals("") && !checqueNoEdittextThird.getText().toString().trim().equals("") && !dateTextViewThird.getText().toString().trim().equals("21-mar-16")) {
                    BankLabelTextview.setError(null);
                    BankLabelTextview.clearFocus();


                    alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = (View) inflater.inflate(R.layout.activity_list,
                            null);

                    listviewBankThird = (ListView) convertView.findViewById(R.id.list_view);

                    int index = 0;
                    if (hashmapBank != null) {
                        bankThirdList = new String[hashmapBank.size() + 1];
                        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(
                                hashmapBank);
                        Set set2 = map.entrySet();
                        Iterator iterator = set2.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry me2 = (Map.Entry) iterator.next();
                            if (index == 0) {
                                bankThirdList[index] = "Select";

                                index = index + 1;

                                bankThirdList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            } else {
                                bankThirdList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            }

                        }

                    }
                    adapterBankThird = new ArrayAdapter<String>(CollectionActivityNew.this,
                            R.layout.list_item, R.id.product_name, bankThirdList);

                    listviewBankThird.setAdapter(adapterBankThird);
                    inputSearch = (EditText) convertView
                            .findViewById(R.id.inputSearch);
                    inputSearch.setVisibility(View.VISIBLE);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Bank");
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                            // TODO Auto-generated method stub
                            adapterBankThird.getFilter().filter(arg0.toString().trim());

                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                    listviewBankThird.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            String abc = listviewBankThird.getItemAtPosition(arg2).toString().trim();
                            BankSpinnerThird.setText(abc);
                            inputSearch.setText("");

                            ad.dismiss();

                            if (abc.equals("Select")) {


                            } else {
                            }
                        }
                    });
                    ad = alertDialog.show();

                } else {
                    if (amountEdittextThird.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    } else if (checqueNoEdittextThird.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    } else if (dateTextViewThird.getText().toString().trim().equals("21-mar-16")) {
                        showAlertSingleButtonError("Please Select the Date.");
                    } else {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo. or Select Date");
                    }

                }


            }
        });

    }

    public void CalendarInitialization() {

        dateTextViewSecond = (TextView) findViewById(R.id.dateTextViewSecond);
        dateTextViewSecond.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!amountEdittextSecond.getText().toString().trim().equals("") && !checqueNoEdittextSecond.getText().toString().trim().equals("")) {
                    DateLabelTextview.setError(null);
                    DateLabelTextview.clearFocus();

                    calSecond = true;
                    calendar = Calendar.getInstance();

                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = DatePickerDialog.newInstance(
                            CollectionActivityNew.this, Year, Month, Day);

                    datePickerDialog.setThemeDark(false);

                    datePickerDialog.showYearPickerFirst(false);

                    Calendar calendarForSetDate = Calendar.getInstance();
                    calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                    // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.setAccentColor(Color.parseColor("#5c5696"));

                    datePickerDialog.setTitle("SELECT DATE");
                    datePickerDialog.setMinDate(calendarForSetDate);
                    /*
                     * Calendar calendar = Calendar.getInstance();
                     * calendar.setTimeInMillis
                     * (System.currentTimeMillis()+24*60*60*1000);
                     */
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                } else {
                    if (amountEdittextSecond.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    } else if (checqueNoEdittextSecond.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    } else {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo.");
                    }
                }

            }
        });


        dateTextViewThird = (TextView) findViewById(R.id.dateTextViewThird);
        dateTextViewThird.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!amountEdittextThird.getText().toString().trim().equals("") && !checqueNoEdittextThird.getText().toString().trim().equals("")) {
                    DateLabelTextview.setError(null);
                    DateLabelTextview.clearFocus();

                    calThird = true;
                    calendar = Calendar.getInstance();

                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = DatePickerDialog.newInstance(
                            CollectionActivityNew.this, Year, Month, Day);

                    datePickerDialog.setThemeDark(false);

                    datePickerDialog.showYearPickerFirst(false);

                    Calendar calendarForSetDate = Calendar.getInstance();
                    calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                    // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.setAccentColor(Color.parseColor("#5c5696"));

                    datePickerDialog.setTitle("SELECT DATE");
                    datePickerDialog.setMinDate(calendarForSetDate);
                    /*
                     * Calendar calendar = Calendar.getInstance();
                     * calendar.setTimeInMillis
                     * (System.currentTimeMillis()+24*60*60*1000);
                     */
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                } else {
                    if (amountEdittextThird.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    } else if (checqueNoEdittextThird.getText().toString().trim().equals("")) {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    } else {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo.");
                    }

                }


            }
        });


    }


    public void TextviewInitialization() {
        pymtModeTextView = (TextView) findViewById(R.id.pymtModeTextView);
        AmountTextview = (TextView) findViewById(R.id.AmountTextview);
        chequeNoTextview = (TextView) findViewById(R.id.chequeNoTextview);
        DateLabelTextview = (TextView) findViewById(R.id.DateLabelTextview);
        BankLabelTextview = (TextView) findViewById(R.id.BankLabelTextview);
        totaltextview = (TextView) findViewById(R.id.totaltextview);
        tv_crntInvoiceText = (TextView) findViewById(R.id.tv_crntInvoiceText);
        tv_minCollectiontext = (TextView) findViewById(R.id.tv_minCollectiontext);
        if(CommonInfo.flgDrctslsIndrctSls==2)
        {
            tv_crntInvoiceText.setText(R.string.CntOrderVal);
            tv_minCollectiontext.setText(R.string.MinAmtToColecForOrder);
        }


    }


    public void ButtonInialization() {
        btn_bck = (ImageView) findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (flgFromPlace == 2) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductOrderReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);

                    startActivity(ide);
                    finish();
                }
                if (flgFromPlace == 1) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductInvoiceReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);

                    startActivity(ide);
                    finish();
                }
            }
        });
        mDisc = (Button) findViewById(R.id.dis);
        mDisc.setOnClickListener(new OnClickListener() {
            public void onClick(View mView) {
                if (mBluetoothAdapter != null)
                    mBluetoothAdapter.disable();
            }
        });
        PrintOnly = (Button) findViewById(R.id.PrintOnly);
        if(CommonInfo.hmapAppMasterFlags.containsKey("flgInvoicePrint"))
        {
            if(CommonInfo.hmapAppMasterFlags.get("flgInvoicePrint")==1)
            {
                PrintOnly.setVisibility(View.VISIBLE);
            }
        }
        PrintOnly.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyPrintFlag = 1;
                PrintOnlyAlert(getResources().getString(R.string.onlyprintMsg));

            }
        });
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_print.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ArrayList<String> arrResult=  mDataSource.fnFetch_tblWarehouseMstr();
                arrAllPrintResult= mDataSource.fnFetch_InvoiceReportForPrint(StoreVisitCode,storeID, Integer.parseInt(arrResult.get(0)),Integer.parseInt(arrResult.get(1)));
                MakePrintRecipt();*/
                onlyPrintFlag = 0;
                fnCallSaveDataFromTempToPermanetWithPrint("Do you want to submit visit data and print Invoice");


            }
        });
        Done_btn = (Button) findViewById(R.id.Done_btn);
        Done_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if( flgVisitCollectionMarkedStatus==0)
                {
                    fnCallSaveDataFromTempToPermanetWithoutPrint("Do you want to submit visit data.");
                    // saveDataToDatabase();

                }
                else {

                    if(onlyPrintFlag==1){
                        StoreSelection.flgChangeRouteOrDayEnd = 0;
                        DayStartActivity.flgDaySartWorking = 0;
                        String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                        Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
                        mMyServiceIntent.putExtra("whereTo", "Regular");//
                        mMyServiceIntent.putExtra("routeID", presentRoute);//
                        mMyServiceIntent.putExtra("storeID", storeID);
                        SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);


                        Intent prevP2 = new Intent(CollectionActivityNew.this, StoreSelection.class);
                        //Location_Getting_Service.closeFlag = 0;
                        prevP2.putExtra("imei", imei);
                        prevP2.putExtra("userDate", date);
                        prevP2.putExtra("pickerDate", pickerDate);
                        prevP2.putExtra("rID", presentRoute);
                        startActivity(prevP2);
                        finish();
                    }
                    else{
                        onlyPrintFlag = 0;
                        if(CommonInfo.flgDrctslsIndrctSls==1) {
                            fnCallSaveDataFromTempToPermanetWithoutPrint("Do you want to submit visit data without printing Invoice");
                        }
                        if(CommonInfo.flgDrctslsIndrctSls==2) {
                            fnCallSaveDataFromTempToPermanetWithoutPrint("Do you want to submit visit data.");
                        }
                    }
                }




            }

        });


    }


    public void fnCallSaveDataFromTempToPermanetWithoutPrint(String msgForAlert) {
      /* if (validate() && validateCollectionAmt()) {

       }*/
        if (validate() && validate_flgCollDefControl()) {
//
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    CollectionActivityNew.this);
            alertDialog.setTitle("Information");

            alertDialog.setCancelable(false);
            alertDialog.setMessage(msgForAlert);
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();

                            saveDataToDatabase();
                                      /* FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                                       task.execute();*/


                            butClickForGPS = 3;
                            //mDataSource.open();
                            if ((mDataSource.PrevLocChk(storeID.trim(), StoreVisitCode))) {
                                //mDataSource.close();

                                FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                                task.execute();
                            } else {
                                //mDataSource.close();

								/* pm = (PowerManager) getSystemService(POWER_SERVICE);
								   wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
							                | PowerManager.ACQUIRE_CAUSES_WAKEUP
							                | PowerManager.ON_AFTER_RELEASE, "INFO");
							        wl.acquire();*/

                                if(pDialog2STANDBY!=null)
                                    pDialog2STANDBY=null;
                                pDialog2STANDBY = ProgressDialog.show(CollectionActivityNew.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.genTermRetrivingLocation), true);
                                pDialog2STANDBY.setIndeterminate(true);

                                pDialog2STANDBY.setCancelable(false);
                                pDialog2STANDBY.show();


                                LocationRetreivingGlobal locationRetreivingGlobal = new LocationRetreivingGlobal();
                                locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(CollectionActivityNew.this, false, true, 20, 0);

                            }


                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();


        }
    }


    public void fnCallSaveDataFromTempToPermanetWithPrint(String msgForAlert) {
        if (validate() && validate_flgCollDefControl()) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
            alertDialog.setTitle("Information");

            alertDialog.setCancelable(false);
            alertDialog.setMessage(msgForAlert);
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                            PrintALlFuctionality();

                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();


        }
    }
    public void PrintOnlyAlert(String msgForAlert) {
        if (validate() && validate_flgCollDefControl()) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
            alertDialog.setTitle("Information");

            alertDialog.setCancelable(false);
            alertDialog.setMessage(msgForAlert);
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            PrintOnlyALlFuctionality();
                            dialog.dismiss();


                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            onlyPrintFlag = 0;
                            dialog.dismiss();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();


        }
        else{
            onlyPrintFlag = 0;
        }
    }

    public void saveDataToDatabase() {
        String paymentModeFirstString = "Cash";
        String AmountFirstString = "0";
        String ChequeNoFirstString = "0";
        String DateFirstString = "0";
        String BankFirstString = "0";

        String paymentModeSecondString = "Cheque/DD";
        String AmountSecondString = "0";
        String ChequeNoSecondString = "0";
        String DateSecondString = "0";
        String BankSecondString = "0";

        String paymentModeThirdString = "Electronic";
        String AmountThirdString = "0";
        String ChequeNoThirdString = "0";
        String DateThirdString = "0";
        String BankThirdString = "0";
        Double valSelfCreditNote = 0.0;
        if (!TextUtils.isEmpty(et_SelfCreditNote.getText().toString())) ;
        {
            valSelfCreditNote = Double.parseDouble(et_SelfCreditNote.getText().toString());
            valSelfCreditNote = Double.parseDouble(new DecimalFormat("##.##").format(valSelfCreditNote));
        }

        // First row data
        if (!TextUtils.isEmpty(amountEdittextFirst.getText().toString())) {
            AmountFirstString = amountEdittextFirst.getText().toString().trim();
        }


        // Second row data
        if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString())) {
            AmountSecondString = amountEdittextSecond.getText().toString().trim();
        }
        if (!checqueNoEdittextSecond.getText().toString().trim().equals("")) {
            ChequeNoSecondString = checqueNoEdittextSecond.getText().toString().trim();
        }
        if (!dateTextViewSecond.getText().toString().trim().equals("21-mar-2016")) {
            DateSecondString = dateTextViewSecond.getText().toString().trim();
        }

        if (dateTextViewSecond.getText().toString().trim().equals("21-mar-2016")) {
            DateSecondString = "0";
        }
        if (hashmapBank != null) {
            if (hashmapBank.containsKey(BankSpinnerSecond.getText().toString().trim())) {
                BankSecondString = hashmapBank.get(BankSpinnerSecond.getText().toString().trim());
            } else {
                BankSecondString = "0";
            }


        } else {
            BankSecondString = BankNameSecondUpdate;
        }


        // Third row data
        if (!TextUtils.isEmpty(amountEdittextThird.getText().toString())) {
            AmountThirdString = amountEdittextThird.getText().toString().trim();
        }

        if(!checqueNoEdittextThird.getText().toString().trim().equals("")){
            ChequeNoThirdString =checqueNoEdittextThird.getText().toString().trim();
        }
        if(!dateTextViewThird.getText().toString().trim().equals("21-mar-2016")){
            DateThirdString =dateTextViewThird.getText().toString().trim();
        }

        if(dateTextViewThird.getText().toString().trim().equals("21-mar-2016")){
            DateThirdString ="0";
        }

        if (hashmapBank != null)
        {
            if(hashmapBank.containsKey(BankSpinnerThird.getText().toString().trim()))
            {
                BankThirdString = hashmapBank.get(BankSpinnerThird.getText().toString().trim());
            }
            else
            {
                BankThirdString="0";
            }


        }
        else
        {
            BankThirdString = BankNameThirdUpdate;
        }


        if (!TextUtils.isEmpty(amountEdittextFirst.getText().toString()) || !TextUtils.isEmpty(amountEdittextSecond.getText().toString()) || !TextUtils.isEmpty(amountEdittextThird.getText().toString())) {
            CollectionCode = getCollectionCode();
            //mDataSource.open();
            mDataSource.deleteWhereStoreId(storeIDGlobal, strGlobalOrderID, TmpInvoiceCodePDA);

            if (!AmountFirstString.equals("0")) {
                mDataSource.savetblAllCollectionData(StoreVisitCode, storeIDGlobal, paymentModeFirstString, "1", AmountFirstString, ChequeNoFirstString, "0", BankFirstString, TmpInvoiceCodePDA, CollectionCode);

            }
            if (!AmountSecondString.equals("0")) {
                mDataSource.savetblAllCollectionData(StoreVisitCode, storeIDGlobal, paymentModeSecondString, "2", AmountSecondString, ChequeNoSecondString, DateSecondString, BankSecondString, TmpInvoiceCodePDA, CollectionCode);

            }
            if (!AmountThirdString.equals("0")) {
                mDataSource.savetblAllCollectionData(StoreVisitCode, storeIDGlobal, paymentModeThirdString, "4", AmountThirdString, ChequeNoThirdString, DateThirdString, BankThirdString, TmpInvoiceCodePDA, CollectionCode);
            }

        } else {

        }


        mDataSource.UpdateStoreVisitSelfCreditNote(storeIDGlobal, StoreVisitCode, valSelfCreditNote);



    }


   /* private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialogNoConn.setTitle("Information");
        alertDialogNoConn.setMessage(message);
        alertDialogNoConn.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }*/


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
                          int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];
        if (calFirst) {
            dateTextViewFirst.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst = false;
            calSecond = false;

        }

        if (calSecond) {
            dateTextViewSecond.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst = false;
            calSecond = false;
            calThird = false;

        }
        if (calThird) {
            dateTextViewThird.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst = false;
            calSecond = false;
            calThird = false;

        }


    }

    private boolean validateCollectionAmt() {
        Double OverAllAmountCollectedLimit = mDataSource.fetch_Store_MaxCollectionAmount(storeID, TmpInvoiceCodePDA);
        OverAllAmountCollectedLimit = Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollectedLimit));
        OverAllAmountCollected = Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollected));

        Double MinCollectionvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        MinCollectionvalue = Double.parseDouble(new DecimalFormat("##.##").format(MinCollectionvalue));



        if (ll_collectionMandatory.getVisibility() == View.VISIBLE && cb_collection.isChecked() == false && lnCollection.getVisibility() == View.VISIBLE && OverAllAmountCollected == 0.0) {
            showAlertSingleButtonError(CollectionActivityNew.this.getResources().getString(R.string.CollectionAlert1));

            //Collected amount is less than the minimum collection amount , current invoice cannot be made.Click CANCEL & EXIT to close Invoice and exit current visit, Click on UPDATE PAYMENT to update Collection amount

            return false;
        } else {
            if (Math.ceil(OverAllAmountCollected) < Math.ceil(MinCollectionvalue))
            //if(Math.ceil(OverAllAmountCollected) < Math.ceil(totOutstandingValue))
            {
                // showAlertSingleButtonError("Collection Amount can not be less then "+MinCollectionvalue);
                if(CommonInfo.hmapAppMasterFlags.get("flgControlCollection")==1) {
                    showAlertSingleAfterCostumValidationForAmountCollection(CollectionActivityNew.this.getResources().getString(R.string.CollectionAlert2));
                    return false;
                }
                else
                {
                    return true;
                }
            } else if (Math.ceil(OverAllAmountCollected) >= Math.ceil(MinCollectionvalue))// && Math.ceil(OverAllAmountCollected)<=Math.ceil(OverAllAmountCollectedLimit))
            //else if(Math.ceil(OverAllAmountCollected)>=Math.ceil(totOutstandingValue) && Math.ceil(OverAllAmountCollected)<=Math.ceil(OverAllAmountCollectedLimit))
            //else if(Math.ceil(OverAllAmountCollected)>=Math.ceil(totOutstandingValue) && Math.ceil(OverAllAmountCollected)<=Math.ceil(OverAllAmountCollectedLimit))
            {
             /*   if(Math.ceil(valSelfCreditNote)<=Math.ceil(totOutstandingValue))
                {
                    return true;
                }
                else
                {*/
                   /* showAlertSingleButtonError("Self Credit Amt can not be greater then overall Total Outstanding.");
                    return false;*/
                // }
                return true;
            } else {
                // showAlertSingleButtonError("Collection Amount can not be greater then "+OverAllAmountCollectedLimit);
                // showAlertSingleAfterCostumValidationForAmountCollection("Collection amount exceeds then required and current Invoice can not be made, Click Cancel & Exit to close Invoice and Exit current visit, Click on Update Payment to update Collection amount.");
                if(CommonInfo.hmapAppMasterFlags.get("flgControlCollection")==1) {
                    boolean rst = showWarningAlertIfCollectionsGraterThenTotalOutStandings(CollectionActivityNew.this.getResources().getString(R.string.CollectionAlert3));
                    return rst;
                }
                else {
                    return true;
                }
                //Collected amount is less than the minimum collection amount , current invoice cannot be made.Click CANCEL & EXIT to close Invoice and exit current visit, Click on UPDATE PAYMENT to update Collection amount

                //return false;
            }

        }


    }

    private boolean validate() {

        // Start Second Row
        if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && checqueNoEdittextSecond.getText().toString().trim().equals("")) {
            checqueNoEdittextSecond.clearFocus();
            checqueNoEdittextSecond.requestFocus();
            String estring = "RefNo/chequeNo/TrnNo is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            checqueNoEdittextSecond.setError(ssbuilder);

            return false;
        } else if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Date is Empty");
            return false;
        } else if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && amountEdittextSecond.getText().toString().trim().equals("")) {
            amountEdittextSecond.clearFocus();
            amountEdittextSecond.requestFocus();

            String estring = "Amount is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            amountEdittextSecond.setError(ssbuilder);

            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Date is Empty");
            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && BankSpinnerSecond.getText().toString().trim().equals("Select")) {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        // Second row end
        // Start Second Row
        if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && checqueNoEdittextSecond.getText().toString().trim().equals("")) {
            checqueNoEdittextSecond.clearFocus();
            checqueNoEdittextSecond.requestFocus();
            String estring = "RefNo/chequeNo/TrnNo is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            checqueNoEdittextSecond.setError(ssbuilder);

            return false;
        } else if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Date is Empty");
            return false;
        } else if (!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && amountEdittextSecond.getText().toString().trim().equals("")) {
            amountEdittextSecond.clearFocus();
            amountEdittextSecond.requestFocus();

            String estring = "Amount is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            amountEdittextSecond.setError(ssbuilder);

            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals("")) {
            showAlertSingleButtonError("Date is Empty");
            return false;
        } else if (!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && BankSpinnerSecond.getText().toString().trim().equals("Select")) {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        // Second row end

        // Third Second Row
        else if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && checqueNoEdittextThird.getText().toString().trim().equals(""))
        {
            checqueNoEdittextThird.clearFocus();
            checqueNoEdittextThird.requestFocus();
            String estring = "RefNo/chequeNo/TrnNo is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            checqueNoEdittextThird.setError(ssbuilder);

            return false;
        }
        else if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        else if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && amountEdittextThird.getText().toString().trim().equals(""))//
        {
            amountEdittextThird.clearFocus();
            amountEdittextThird.requestFocus();

            String estring = "Amount is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            amountEdittextThird.setError(ssbuilder);

            return false;
        }
        else if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && BankSpinnerThird.getText().toString().trim().equals("Select"))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        else {
            return true;
        }


    }

    public void showAlertSingleAfterCostumValidationForAmountCollection(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialog.setTitle("Validation");
        alertDialog.setIcon(R.drawable.error_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Update Payment", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel & Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                /*Intent ide=new Intent(CollectionActivityNew.this,ProductOrderReview.class);
                ide.putExtra("SN", SN);
                ide.putExtra("storeID", storeID);
                ide.putExtra("imei", imei);
                ide.putExtra("userdate", date);
                ide.putExtra("pickerDate", pickerDate);
                ide.putExtra("flgOrderType", flgOrderType);
                ide.putExtra("OrderPDAID", strGlobalOrderID);

                startActivity(ide);
                finish();*/

                if (flgFromPlace == 2) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductOrderReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }
                if (flgFromPlace == 1) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductInvoiceReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }
            }
        });

        alertDialog.show();

    }


    public boolean showWarningAlertIfCollectionsGraterThenTotalOutStandings(String msg) {
        rstl = false;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialog.setTitle("Validation");
        alertDialog.setIcon(R.drawable.error_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                rstl = true;

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                rstl = false;
                /*Intent ide=new Intent(CollectionActivityNew.this,ProductOrderReview.class);
                ide.putExtra("SN", SN);
                ide.putExtra("storeID", storeID);
                ide.putExtra("imei", imei);
                ide.putExtra("userdate", date);
                ide.putExtra("pickerDate", pickerDate);
                ide.putExtra("flgOrderType", flgOrderType);
                ide.putExtra("OrderPDAID", strGlobalOrderID);

                startActivity(ide);
                finish();*/

                /*if(flgFromPlace==2) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductOrderReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }
                if(flgFromPlace==1) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductInvoiceReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }*/
            }
        });

        alertDialog.show();
        return rstl;
    }

    public void SetDataToLayout(String collectionDataString) {
        String paymentMode = collectionDataString.split(Pattern.quote("^"))[0];
        String PaymentModeID = collectionDataString.split(Pattern.quote("^"))[1];
        String Amount = collectionDataString.split(Pattern.quote("^"))[2];
        String RefNoChequeNoTrnNo = collectionDataString.split(Pattern.quote("^"))[3];
        String Date = collectionDataString.split(Pattern.quote("^"))[4];
        String BankID = collectionDataString.split(Pattern.quote("^"))[5];
        //paymentMode 1 means Cash
        if (PaymentModeID.equals("1")) {
            amountEdittextFirst.setText(Amount);
        }
        //paymentMode 2 means Check/DD
        if (PaymentModeID.equals("2")) {
            amountEdittextSecond.setText(Amount);
            checqueNoEdittextSecond.setText(RefNoChequeNoTrnNo);
            dateTextViewSecond.setText(Date);
            if (BankID.equals("0")) {
                BankSpinnerSecond.setText(getResources().getString(R.string.txtSelect));
            } else {
                BankSpinnerSecond.setText(linkedHmapBankID.get(BankID));

            }
        }
        //paymentMode 4 means Electronics
        if (PaymentModeID.equals("4")) {
            amountEdittextThird.setText(Amount);
            checqueNoEdittextThird.setText(RefNoChequeNoTrnNo);
            dateTextViewThird.setText(Date);
            if (BankID.equals("0"))
            {
                BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
            } else
            {
                BankSpinnerThird.setText(linkedHmapBankID.get(BankID));

            }
        /*    checqueNoEdittextThird.setText("");
            dateTextViewThird.setText("");*/
            BankID = "0";
            if (BankID.equals("0")) {
                BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
            } else {
                BankSpinnerThird.setText(linkedHmapBankID.get(BankID));

            }
        }

    }

    public String genCollectionCode() {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String VisitStartTS = TimeUtils.getNetworkDateTime(CollectionActivityNew.this,TimeUtils.DATE_TIME_FORMAT);
        String cxz;
        cxz = UUID.randomUUID().toString();


        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        String IMEIid = CommonInfo.imei.substring(9);
        cxz = "Collection" + "-" + IMEIid + "-" + cxz + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;

    }


    public String getCollectionCode() {
        int StoreCurrentOutsStat = mDataSource.fnGetCollectionOutSstat(storeID);
        if (StoreCurrentOutsStat != 1) {

            CollectionCode = genCollectionCode();

        } else {
            CollectionCode = mDataSource.fnGetStoreCollectionCode(storeID);
        }
        return CollectionCode;
    }





    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) {
            mCustomKeyboardNumWithoutDecimal.hideCustomKeyboardNum(v);
            mCustomKeyboardNum.hideCustomKeyboardNum(v);
            if (v instanceof EditText) {
                EditText edtBox = (EditText) v;
                if (v.getId() == R.id.amountEdittextFirst || v.getId() == R.id.amountEdittextSecond || v.getId() == R.id.amountEdittextThird || v.getId() == R.id.et_SelfCreditNote) {
                    mCustomKeyboardNum.registerEditText(edtBox);
                    mCustomKeyboardNum.showCustomKeyboard(v);
                } else if (v.getId() == R.id.checqueNoEdittextSecond) {
                    mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
                    mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);
                }
                else if (v.getId() == R.id.checqueNoEdittextThird) {
                    mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
                    mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);
                }


            }
        }


    }

    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
        FusedLocationLatitude=FusedLat;
        FusedLocationLongitude=FusedLong;
        FusedLocationProvider="Fused";
        FusedLocationAccuracy=FusedAccuracy;
        GPSLocationLatitude=GpsLat;
        GPSLocationLongitude=GpsLong;
        GPSLocationProvider="GPS";
        GPSLocationAccuracy=GpsAccuracy;
        NetworkLocationLatitude=NetwLat;
        NetworkLocationLongitude=NetwLong;
        NetworkLocationProvider="Network";
        NetworkLocationAccuracy=NetwAccuracy;

        this.fnAccurateProvider=fnAccurateProvider;
        this.fnLati=fnLati;
        this.fnLongi=fnLongi;
        this.fnAccuracy=Double.parseDouble(finalAccuracy);

        this.fnaddress=FusedAddress;
        this.AllProvidersLocation=AllProvidersLocation;
        this.GpsAddress=GpsAddress;
        this.NetwAddress=NetwAddress;
        this.FusedAddress=FusedAddress;
        this.FusedLocationLatitudeWithFirstAttempt=FusedLocationLatitudeWithFirstAttempt;
        this.FusedLocationLongitudeWithFirstAttempt=FusedLocationLongitudeWithFirstAttempt;
        this.FusedLocationAccuracyWithFirstAttempt=FusedLocationAccuracyWithFirstAttempt;
        checkHighAccuracyLocationMode(CollectionActivityNew.this);
        //mDataSource.open();
        mDataSource.UpdateStoreActualLatLongi(storeID, String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy, fnAccurateProvider, flgLocationServicesOnOffOrderReview, flgGPSOnOffOrderReview, flgNetworkOnOffOrderReview, flgFusedOnOffOrderReview, flgInternetOnOffWhileLocationTrackingOrderReview, flgRestartOrderReview, flgStoreOrderOrderReview, StoreVisitCode, VisitTimeInSideStore);
        mDataSource.saveLatLngToTxtFile(storeID,StoreVisitCode,fnLati, fnLongi, "" +fnAccuracy, fnAccurateProvider, GPSLocationLatitude, GPSLocationLongitude, GPSLocationAccuracy, NetworkLocationLatitude, NetworkLocationLongitude, NetworkLocationAccuracy, FusedLocationLatitude, FusedLocationLongitude, FusedLocationAccuracy, 1, "0", fnaddress, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt
                , FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);

        if (!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy))) {

            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
            UpdateLocationAndProductAllData();
        } else {
            countSubmitClicked++;
            if (countSubmitClicked == 1) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);

                // Setting Dialog Title
                alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage(CollectionActivityNew.this.getResources().getString(R.string.AlertSameLoc));

                // On pressing Settings button
                alertDialog.setPositiveButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            } else {
                UpdateLocationAndProductAllData();
            }


        }
    }


    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;

        public FullSyncDataNow(CollectionActivityNew activity) {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(pDialog2STANDBY!=null) {
                if (pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                    pDialog2STANDBY=null;
                }
            }
            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            if(CommonInfo.flgDrctslsIndrctSls==1) {
                pDialogGetStores.setMessage(CollectionActivityNew.this.getResources().getString(R.string.SubmittingOrderDetails));
            }
            else
            {
                pDialogGetStores.setMessage("Saving Order Details...");

            }
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

            if (TextUtils.isEmpty(tv_totOutstandingValue.getText().toString().replace(":", "").trim())) {
                overallOutStand = 0.0;
            } else {
                overallOutStand = Double.parseDouble(tv_totOutstandingValue.getText().toString().replace(":", "").trim());
            }
            if (TextUtils.isEmpty(totaltextview.getText().toString().replace(":", "").trim())) {
                curntCol = 0.0;
            } else {
                curntCol = Double.parseDouble(totaltextview.getText().toString().replace(":", "").trim());
            }
            //curntCol

        }

        @Override

        protected Void doInBackground(Void... params) {

            int Outstat = 1;
            flgTransferStatus = 1;
            //mDataSource.open();
            mDataSource.fnUpdateflgTransferStatusInInvoiceHeader(storeID, StoreVisitCode, TmpInvoiceCodePDA, flgTransferStatus);
            //mDataSource.close();
            //InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
            //TransactionTableDataDeleteAndSaving(Outstat);
            Double cntInvoceValue = mDataSource.fetch_Store_InvValAmount(storeID, TmpInvoiceCodePDA);
            cntInvoceValue = Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

            Outstat = 3;

            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String StampEndsTime = TimeUtils.getNetworkDateTime(CollectionActivityNew.this,TimeUtils.DATE_TIME_FORMAT);
            if (Outstat == 3) {
                if (cntInvoceValue > 0.0) {
                    if (strFinalAllotedInvoiceIds.equals("NA")) {

                        mDataSource.fnTransferDataFromTempToPermanent(storeID, StoreVisitCode, TmpInvoiceCodePDA, CommonInfo.flgDrctslsIndrctSls);

                        int chkflgTransferStatus = mDataSource.fnCheckflgTransferStatus(storeID, StoreVisitCode, TmpInvoiceCodePDA);
                        if (chkflgTransferStatus == 2) {
                            mDataSource.deleteOldStoreInvoice(storeID, strGlobalOrderID, TmpInvoiceCodePDA);
                            mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID, strGlobalOrderID, TmpInvoiceCodePDA);
                            mDataSource.UpdateStoreVisitMStrTable(storeID, 3, StoreVisitCode);
                            mDataSource.fnupdatetblStoreLastDeliveryNoteNumber();
                            mDataSource.UpdatetblStoreOrderVisitDayActivitySstat(storeID,3,StoreVisitCode,LastVisitDetails.TempStoreVisitCode);
                            mDataSource.UpdateStoreEndVisittblStoreOrderVisitDayActivity(storeID,StampEndsTime,LastVisitDetails.TempStoreVisitCode);

                        } else {
                            mDataSource.deleteMasterTblFromParmanentInvoiceTables(storeID, TmpInvoiceCodePDA);
                        }

                    }

                }
            }

            mDataSource.UpdateStoreVisitWiseTables(storeID, 3, StoreVisitCode, TmpInvoiceCodePDA,"NA");



            //mDataSource.open();
            mDataSource.UpdateStoreEndVisit(storeID, StampEndsTime);


            mDataSource.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(), "3", strGlobalOrderID, TmpInvoiceCodePDA);
            mDataSource.UpdateStoreStoreReturnDetail(storeID.trim(), "3", strGlobalOrderID, TmpInvoiceCodePDA);
            mDataSource.UpdateStoreFlag(storeID.trim(), 3);
            mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3, strGlobalOrderID, TmpInvoiceCodePDA);


            //mDataSource.UpdateStoreReturnphotoFlag(storeID.trim(), 5);

            //mDataSource.close();


            // overallOutStand=0.0;
           /* Double outstandingvalue=mDataSource.fnGetStoretblLastOutstanding(storeID);
            outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));*/


            Double FinalOutStand = overallOutStand - curntCol;
            FinalOutStand = Double.parseDouble(new DecimalFormat("##.##").format(FinalOutStand));
            mDataSource.updateOutstandingOfStore(storeID, FinalOutStand);

            //mDataSource.updateOutstandingOfStore(storeID,0.0);
            // Double CollectionAmtAgainstStore=mDataSource.fnTotCollectionAmtAgainstStore(storeID.trim(),TmpInvoiceCodePDA,StoreVisitCode);

            mDataSource.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(), 0, StoreVisitCode);
            if (mDataSource.checkCountIntblStoreSalesOrderPaymentDetails(storeID, strGlobalOrderID, TmpInvoiceCodePDA) == 0) {
                String strDefaultPaymentStageForStore = mDataSource.fnGetDefaultStoreOrderPAymentDetails(storeID);
                if (!strDefaultPaymentStageForStore.equals("")) {
                    //mDataSource.open();
                    mDataSource.fnsaveStoreSalesOrderPaymentDetails(storeID, strGlobalOrderID, strDefaultPaymentStageForStore, "3", TmpInvoiceCodePDA);
                    //mDataSource.close();
                }
            }


            if (flgOnlySubmitOrPrint == 1) {
                //mDataSource.open();
                ArrayList<String> arrResult = mDataSource.fnFetch_tblWarehouseMstr();
                // mDataSource.close();
                arrAllPrintResult = null;
                if ((arrResult != null) && (arrResult.size() > 0)) {
                    arrAllPrintResult = mDataSource.fnFetch_InvoiceReportForPrint(StoreVisitCode, storeID, Integer.parseInt(arrResult.get(0)), Integer.parseInt(arrResult.get(1)));
                    if ((arrAllPrintResult != null) && (arrAllPrintResult.size() > 0)) {
                        // PrintAll_Code();
                        PrintAll_CodeFromASyncTask();
                    } else {
                        Toast.makeText(CollectionActivityNew.this, "All print data is blank", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CollectionActivityNew.this, "NodeID Blank", Toast.LENGTH_SHORT).show();
                }

            }

            return null;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            try {
                if(onlyPrintFlag==1) {
                    Toast.makeText(CollectionActivityNew.this, "Please submit your data ", Toast.LENGTH_SHORT).show();
                    PrintOnly.setEnabled(false);
                    btn_bck.setEnabled(false);
                    btn_print.setEnabled(false);
                    cb_collection.setEnabled(false);
                    amountEdittextFirst.setEnabled(false);
                    amountEdittextSecond.setEnabled(false);
                    amountEdittextThird.setEnabled(false);
                    checqueNoEdittextSecond.setEnabled(false);
                    dateTextViewSecond.setEnabled(false);
                    dateTextViewThird.setEnabled(false);
                    BankSpinnerSecond.setEnabled(false);
                    BankSpinnerThird.setEnabled(false);
                    if (pDialogGetStores.isShowing()) {
                        pDialogGetStores.dismiss();
                    }
                    Toast.makeText(CollectionActivityNew.this, "Please submit your data", Toast.LENGTH_SHORT).show();

                    // Toast.makeText(CollectionActivityNew.this, "NodeID Blank", Toast.LENGTH_SHORT).show();
                }

                if(onlyPrintFlag==0) {
                    StoreSelection.flgChangeRouteOrDayEnd = 0;
                    DayStartActivity.flgDaySartWorking = 0;
                    /*  String presentRoute = mDataSource.GetActiveRouteID();*/
                    Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);
                    mMyServiceIntent.putExtra("storeID", storeID);
                    mMyServiceIntent.putExtra("whereTo", "Regular");//
                    mMyServiceIntent.putExtra("StoreVisitCode", StoreVisitCode);//
                    SyncJobService.enqueueWork(getCtx(),mMyServiceIntent);
                    flgOnlySubmitOrPrint=0;

                    if (pDialogGetStores.isShowing()) {
                        pDialogGetStores.dismiss();
                    }
                    showOrderSSubmissionAlert();

                }
                //onlyPrintFlag=0;
                flgOnlySubmitOrPrint=0;
               /* Intent syncIntent = new Intent(CollectionActivityNew.this, SyncMaster.class);
                //syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + newfullFileName + ".xml");
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", "Regular");
                startActivity(syncIntent);
                finish();*/
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }

    public void showOrderSSubmissionAlert()
    {
        AlertDialog.Builder alertDialogOrderSubmission = new AlertDialog.Builder(this);
        alertDialogOrderSubmission.setTitle(R.string.genTermInformation);
        alertDialogOrderSubmission.setMessage(R.string.DataSucc);
        alertDialogOrderSubmission.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        try
                        {

                            String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                            Intent prevP2 = new Intent(CollectionActivityNew.this, StoreSelection.class);
                            //Location_Getting_Service.closeFlag = 0;
                            prevP2.putExtra("imei", imei);
                            prevP2.putExtra("userDate", date);
                            prevP2.putExtra("pickerDate", pickerDate);
                            prevP2.putExtra("rID", presentRoute);
                            startActivity(prevP2);
                            dialog.dismiss();
                            finish();
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });
        alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
        AlertDialog alert = alertDialogOrderSubmission.create();
        alert.show();

    }

    public void showAlertForEveryOne(String msg) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialogNoConn.setTitle(getText(R.string.genTermNoDataConnection));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FusedLocationLatitude = "0";
                FusedLocationLongitude = "0";
                FusedLocationProvider = "0";
                FusedLocationAccuracy = "0";

                GPSLocationLatitude = "0";
                GPSLocationLongitude = "0";
                GPSLocationProvider = "0";
                GPSLocationAccuracy = "0";

                NetworkLocationLatitude = "0";
                NetworkLocationLongitude = "0";
                NetworkLocationProvider = "0";
                NetworkLocationAccuracy = "0";


                String GpsLat = "0";
                String GpsLong = "0";
                String GpsAccuracy = "0";
                String GpsAddress = "0";
                String NetwLat = "0";
                String NetwLong = "0";
                String NetwAccuracy = "0";
                String NetwAddress = "0";
                String FusedLat = "0";
                String FusedLong = "0";
                String FusedAccuracy = "0";
                String FusedAddress = "0";
                checkHighAccuracyLocationMode(CollectionActivityNew.this);
                //mDataSource.open();
                mDataSource.UpdateStoreActualLatLongi(storeID, String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy, fnAccurateProvider, flgLocationServicesOnOffOrderReview, flgGPSOnOffOrderReview, flgNetworkOnOffOrderReview, flgFusedOnOffOrderReview, flgInternetOnOffWhileLocationTrackingOrderReview, flgRestartOrderReview, flgStoreOrderOrderReview, StoreVisitCode, VisitTimeInSideStore);


                if (butClickForGPS == 3) {
                    butClickForGPS = 0;
                    try {
                        FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                        task.execute();
                    } catch (Exception e) {
                        // TODO Autouuid-generated catch block
                        e.printStackTrace();
                        //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
                    }

                }

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOffOrderReview = 0;
        flgGPSOnOffOrderReview = 0;
        flgNetworkOnOffOrderReview = 0;
        flgFusedOnOffOrderReview = 0;
        flgInternetOnOffWhileLocationTrackingOrderReview = 0;

        if (isGooglePlayServicesAvailable()) {
            flgFusedOnOffOrderReview = 1;
        }
        if (isOnline()) {
            flgInternetOnOffWhileLocationTrackingOrderReview = 1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 0;
                    flgNetworkOnOffOrderReview = 1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 0;
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

                flgLocationServicesOnOffOrderReview = 0;
                flgGPSOnOffOrderReview = 0;
                flgNetworkOnOffOrderReview = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOffOrderReview = 1;
                flgGPSOnOffOrderReview = 1;
                flgNetworkOnOffOrderReview = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 0;
                    flgNetworkOnOffOrderReview = 1;
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



    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy) {

        try {

            JSONArray jArray = new JSONArray();
            JSONObject jsonObjMain = new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put("chekLastGPSLat", chekLastGPSLat);
            jOnew.put("chekLastGPSLong", chekLastGPSLong);
            jOnew.put("chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.AppLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "GPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.AppLatLngJsonFile + "/" + txtFileNamenew;


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
				//fileOutputStream=contextcopy.openFileOutput("GPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }

    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy) {
        boolean repeatedLoction = false;

        try {

            String chekLastGPSLat = "0";
            String chekLastGPSLong = "0";
            String chekLastGpsAccuracy = "0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer = new StringBuffer();
                String myjson_stampiGPSLastLocation = "";
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

                myjson_stampiGPSLastLocation = sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat = jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong = jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy = jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if (currentLat != null) {
                    if (currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy)) {
                        repeatedLoction = true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }

    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy) {

        try {

            JSONArray jArray = new JSONArray();
            JSONObject jsonObjMain = new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put("chekLastGPSLat", chekLastGPSLat);
            jOnew.put("chekLastGPSLong", chekLastGPSLong);
            jOnew.put("chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists()) {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew = "FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder, txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory() + "/" + CommonInfo.FinalLatLngJsonFile + "/" + txtFileNamenew;


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
        } finally {

        }
    }

    public void UpdateLocationAndProductAllData() {

        //mDataSource.close();


        if (butClickForGPS == 3) {
            butClickForGPS = 0;
            try {
                FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                task.execute();
            } catch (Exception e) {
                // TODO Autouuid-generated catch block
                e.printStackTrace();
                //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
            }
        }

    }

    public void showSettingsAlert() {
        AppUtils.doCheckGPSEnable(CollectionActivityNew.this);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public ArrayList<String> fnGetOutStandingDetailsForPrint() {


        ArrayList<String> arrCollectionDetailsForPrint = new ArrayList<String>();

        if (TextUtils.isEmpty(totaltextview.getText().toString().replace(":", "").trim())) {
            curntCol = 0.0;
        } else {
            curntCol = Double.parseDouble(totaltextview.getText().toString().replace(":", "").trim());
            curntCol = Double.parseDouble(new DecimalFormat("##.##").format(curntCol));
        }


        Double outstandingvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        outstandingvalue = Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));


        Double cntInvoceValue = mDataSource.fetch_Store_InvValAmount(storeID, TmpInvoiceCodePDA);
        cntInvoceValue = Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));


        Double cntAllOustandings = mDataSource.fetch_Store_AllOustandings(storeID);
        cntAllOustandings = Double.parseDouble(new DecimalFormat("##.##").format(cntAllOustandings));

        arrCollectionDetailsForPrint.add("" + String.format("%.2f", outstandingvalue));
        arrCollectionDetailsForPrint.add("" + String.format("%.2f", cntInvoceValue));
        arrCollectionDetailsForPrint.add("" + String.format("%.2f", curntCol));
        Double fnOutStand = outstandingvalue + cntInvoceValue - curntCol;
        fnOutStand = Double.parseDouble(new DecimalFormat("##.##").format(fnOutStand));
        arrCollectionDetailsForPrint.add("" + String.format("%.2f", fnOutStand));

        return arrCollectionDetailsForPrint;
    }

    //Printer functions start here----------------------------------------------------------------
    public void PrintALlFuctionality() {
        {

            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (!mBluetoothAdapter.isEnabled()) {

                flgOnlySubmitOrPrint = 0;
                aletrDialogFlag = 1;
                AlertDialogCommonFunction("Bluetooth Printer is not connected.Do you want to  connect to Printer? ");

            } else {
                if ((mBluetoothSocket != null) && (mBluetoothSocket.isConnected())) {
                    flgOnlySubmitOrPrint = 1;
                    //Abhinav Will first transer the data from tem to Permanent table and then call for Print
                    retrieveLocationAndSubmitData();

                } else {
                    flgOnlySubmitOrPrint = 0;
                    aletrDialogFlag = 2;
                    AlertDialogCommonFunction("Bluetooth Printer is not connected.Do you want to connect to Printer");

                }
            }


        }

    }

    public void PrintOnlyALlFuctionality() {
        {

            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (!mBluetoothAdapter.isEnabled()) {

                flgOnlySubmitOrPrint = 0;
                aletrDialogFlag = 1;
                AlertDialogCommonFunction("Bluetooth Printer is not connected.Do you want to  connect to Printer? ");

            } else {
                if ((mBluetoothSocket != null) && (mBluetoothSocket.isConnected())) {
                    flgOnlySubmitOrPrint = 1;
                    retrieveLocationAndSubmitData();
                    //Abhinav Will first transer the data from tem to Permanent table and then call for Print

                } else {
                    flgOnlySubmitOrPrint = 0;
                    aletrDialogFlag = 2;
                    AlertDialogCommonFunction("Bluetooth Printer is not connected.Do you want to connect to Printer");

                }
            }


        }

    }


    public class CoundownClass extends CountDownTimer {

        CoundownClass(long startTime, long interval) {
            super(startTime, interval);
            // TODO Auto-generated constructor stub
            if (mBluetoothSocket != null) {
                if (mBluetoothSocket.isConnected()) {
                    countDownTimer.cancel();
                }
            }

        }

        @Override
        public void onFinish() {

            if (mBluetoothConnectProgressDialog != null) {
                if (mBluetoothConnectProgressDialog.isShowing()) {
                    mBluetoothConnectProgressDialog.dismiss();
                    try {
                        if (mBluetoothSocket != null)
                            mBluetoothSocket.close();
                        onlyPrintFlag = 0;
                        Toast.makeText(CollectionActivityNew.this, "Not Connected", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("Tag", "Exe ", e);
                    }
                }
            }


        }

        @Override
        public void onTick(long arg0) {
            // TODO Auto-generated method stub

        }
    }

    public void AlertDialogCommonFunction(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                CollectionActivityNew.this);
        alertDialog.setTitle(getResources().getString(R.string.genTermInformation));

        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogYesButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        dialog.dismiss();
                        if ((aletrDialogFlag == 1)) {
                            Intent enableBtIntent = new Intent(
                                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent,
                                    REQUEST_ENABLE_BT);
                        }
                        if ((aletrDialogFlag == 2)) {
                            ListPairedDevices();
                            Intent connectIntent = new Intent(CollectionActivityNew.this,
                                    DeviceListActivity.class);
                            startActivityForResult(connectIntent,
                                    REQUEST_CONNECT_DEVICE);
                        }


                    }
                });
        alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        onlyPrintFlag = 0;
                        dialog.dismiss();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    public void PrintAll_CodeFromASyncTask() {

        try {
                    /*String data="";
                    for(int i=1;i<10;i++){
                        if(i==0){
                            data  ="\n";
                        }
                        else{
                            data=data+"\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "Shivam-001", "5", "10", "50.00");
                        }
                    }*/
            OutputStream os = mBluetoothSocket
                    .getOutputStream();
            byte[] format = {29, 10, 35}; // manipulate your font size in the second parameter
            byte[] center = {0x1b, 'a', 0x01}; // center alignment


            byte[] printformat = new byte[]{0x1b, 0x21, 0x01};//center bold
                           /*  byte[] printformat = new byte[]{0x1,'a',0x01};//center normal
                            os.write(printformat);*/
            //os.write(printformat);
            //  os.write(format);

            String billDatatoprint = MakePrintRecipt();
            os.write(printformat);//for small text
            os.write(center);//for center
            os.write(billDatatoprint.getBytes());
            //This is printer specific code you can comment ==== > Start

            // Setting height
                /*    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));*/


        } catch (Exception e) {
            Log.e("MainActivity", "Exe ", e);

        }

    }

    public void PrintAll_Code() {
        Thread t = new Thread() {
            public void run() {
                try {
                    /*String data="";
                    for(int i=1;i<10;i++){
                        if(i==0){
                            data  ="\n";
                        }
                        else{
                            data=data+"\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "Shivam-001", "5", "10", "50.00");
                        }
                    }*/
                    OutputStream os = mBluetoothSocket
                            .getOutputStream();
                    byte[] format = {29, 10, 35}; // manipulate your font size in the second parameter
                    byte[] center = {0x1b, 'a', 0x01}; // center alignment


                    byte[] printformat = new byte[]{0x1b, 0x21, 0x01};//center bold
                           /*  byte[] printformat = new byte[]{0x1,'a',0x01};//center normal
                            os.write(printformat);*/
                    //os.write(printformat);
                    //  os.write(format);

                    String billDatatoprint = MakePrintRecipt();
                    os.write(printformat);//for small text
                    os.write(center);//for center
                    os.write(billDatatoprint.getBytes());
                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                /*    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));*/


                } catch (Exception e) {
                    Log.e("MainActivity", "Exe ", e);

                }
            }
        };
        t.start();
    }

    public String MakePrintRecipt() {

        LinkedHashMap<String, ArrayList<String>> hmapWareHouseDetails = arrAllPrintResult.get(0);
        LinkedHashMap<String, ArrayList<String>> hmapStoreBasicDetails = arrAllPrintResult.get(1);
        LinkedHashMap<String, ArrayList<String>> hmapInvoiceRecodsToPrint = arrAllPrintResult.get(2);
        LinkedHashMap<String, ArrayList<String>> hmapTotalBfrAftrTaxVal = arrAllPrintResult.get(3);
        LinkedHashMap<String, ArrayList<String>> hmapTaxWisePrdctDtlt = arrAllPrintResult.get(4);
        LinkedHashMap<String, ArrayList<String>> hmapOverAllProductOrderQtyValue = arrAllPrintResult.get(5);

        //ware house details starts
        ArrayList<String> arrListWarehouse = hmapWareHouseDetails.get("WarehouseDetails");
        String INVOICE_HEADER = "";

        String shopName = arrListWarehouse.get(0);
        String shopAddress = arrListWarehouse.get(2);//",GT ROAD, NEAR STATE BANK, GOPIGANJ BHADOHI"
        String placeOfSuppllyAddress = arrListWarehouse.get(3) + ", " + arrListWarehouse.get(1) + ", " + arrListWarehouse.get(4);//"BHADOHI, UTTARPRADESH,221409 ";
        String phoneNumber = arrListWarehouse.get(5);//"9716082084";
        if (phoneNumber.equals("0")) {
            phoneNumber = "";
        }
        String gstNumber = arrListWarehouse.get(6);//"1231222ASSSMM99";
        if (shopAddress.length() > 50) {
            shopAddress = insertPeriodically(shopAddress, "^$", 50);
            String[] shopAddress50Digit = shopAddress.split(Pattern.quote("^$"));
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < shopAddress50Digit.length; j++) {
                String addersss = shopAddress50Digit[j];
                stringBuilder.append(addersss);

                if ((j + 1) == shopAddress50Digit.length) {
                    //means in last line dont add \n beacause it will create more space
                } else {
                    stringBuilder.append("\n");
                }
            }
            shopAddress = stringBuilder.toString();
        }
        //ware house details ends

        //Store details starts
        ArrayList<String> arrListStoreData = hmapStoreBasicDetails.get("StoreDetails");
        String custName = arrListStoreData.get(0);//"CHANCHAL PAN";
        String custAddress = arrListStoreData.get(1);//"B-166/48 GANDHI  TRAFFIC CHAURAHA MISSION ROAD  ";
        if (custAddress.length() > 50) {
            custAddress = insertPeriodically(custAddress, "^$", 50);
            String[] custAddress50Digit = custAddress.split(Pattern.quote("^$"));
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < custAddress50Digit.length; j++) {
                String addersss = custAddress50Digit[j];
                stringBuilder.append(addersss);

                if ((j + 1) == custAddress50Digit.length) {
                    //means in last line dont add \n beacause it will create more space
                } else {
                    stringBuilder.append("\n");
                }
            }
            custAddress = stringBuilder.toString();
        }
        String custStateCityPin = arrListStoreData.get(3) + ", " + arrListStoreData.get(2) + ", " + arrListStoreData.get(4);//"BHADOHI, UTTARPRADESH,210205";
        String delNo = mDataSource.fnGetExistingInvoiceNumberAgainstInvoiceNumebr(storeID, StoreVisitCode);//mDataSource.fnGettblDeliveryNoteNumber();
        //delNo=delNo+1;
        String deliveryNumber = "" + delNo;//"12345678900";
        String gstNumberCustomer = arrListStoreData.get(6);

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String currentDateTime = TimeUtils.getNetworkDateTime(CollectionActivityNew.this,TimeUtils.DATE_TIME_FORMAT);
        String date = currentDateTime;//"09-Aug-2018-10:45:17 AM";
        String compositeScheme = arrListWarehouse.get(7);//"No";//or yes
        if (compositeScheme.equals("No")) {
            INVOICE_HEADER = "TAX INVOICE";
        }
        if (compositeScheme.equals("Yes")) {
            INVOICE_HEADER = "BILL OF SUPPLY";
        }
        //Store details ends

        StringBuilder data = new StringBuilder();
        data.append("");
        String TotalQty = "0";
        String TotalValue = "0.00";
        ArrayList<String> arrOverAllProductOrderQtyValue = hmapOverAllProductOrderQtyValue.get("OverAllProductOrderQtyValue");
        if ((arrOverAllProductOrderQtyValue != null) && (arrOverAllProductOrderQtyValue.size() > 0)) {
            TotalQty = arrOverAllProductOrderQtyValue.get(0);
            TotalValue = arrOverAllProductOrderQtyValue.get(1);

        }


        //loop for product
        //Product details Starts
        int i = 1;
        for (Map.Entry<String, ArrayList<String>> entry : hmapInvoiceRecodsToPrint.entrySet()) {

            String storeID = entry.getKey().trim();
            ArrayList<String> arrProductInvoiceDetailsForPrint = entry.getValue();
            String sr = (i < 10 ? "0" : "") + i + " ";
            i++;
            String HSNCode = arrProductInvoiceDetailsForPrint.get(0);//"00000"+i;
            String itemDscr = arrProductInvoiceDetailsForPrint.get(1).toString().trim();//"item-11111133"+i;
            itemDscr = itemDscr.replace(Html.fromHtml("&nbsp;"), "");


       /* Spanned itemDscr1=  Html.fromHtml(itemDscr);// itemDscr.replace("   ","");
        itemDscr=  itemDscr.replace("  ","");*/

       /* if(itemDscr.contains("  ") || itemDscr.contains("   ")){
            if(itemDscr.contains("  ") ){
                itemDscr=  itemDscr.replace("  ","");
            }
            if(itemDscr.contains("   ")){
                itemDscr=   itemDscr.replace("   ","");
            }
        }*/
            //String rate="\u20B9"+"50.00"+i;
            double rate = 0.0;
            double ValueText = 0.0;
            if (!arrProductInvoiceDetailsForPrint.get(2).toString().trim().equals("")) {
                rate = Double.parseDouble(arrProductInvoiceDetailsForPrint.get(2).toString().trim());//""+"50.0"+i;
            }

            String taxRate = arrProductInvoiceDetailsForPrint.get(3);//"6%";
            String Qty = arrProductInvoiceDetailsForPrint.get(4);//"10"+i;
            // String ValueText="\u20B9"+"500.00"+i;
            if (!arrProductInvoiceDetailsForPrint.get(5).toString().trim().equals("")) {
                ValueText = Double.parseDouble(arrProductInvoiceDetailsForPrint.get(5).toString().trim());//""+"500.0"+i;
            }
            if (itemDscr.length() > 19) {
                itemDscr = insertPeriodically(itemDscr, "^$", 19);
                if (itemDscr.contains("^$")) {
                    String[] itemDesc21Digit = itemDscr.split(Pattern.quote("^$"));
                    for (int j = 0; j < itemDesc21Digit.length; j++) {
                        String itemDscr2 = itemDesc21Digit[j];
                        if (j == 0) {
                            data.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr + HSNCode, itemDscr2, String.format("%.2f", rate), taxRate, Qty, String.format("%.2f", ValueText)));
                            // data = data + "\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr+HSNCode,itemDscr2,String.format("%.2f", rate), taxRate, Qty,String.format("%.2f", ValueText));
                        } else {
                            data.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "", itemDscr2, "", "", "", ""));
                            // data = data + "\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "",itemDscr2, "", "", "","");
                        }

                    }
                } else {
                    data.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr + HSNCode, itemDscr, String.format("%.2f", rate), taxRate, Qty, String.format("%.2f", ValueText)));
                    //  data = data + "\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr+HSNCode,itemDscr, String.format("%.2f", rate), taxRate, Qty,String.format("%.2f", ValueText));
                }

            } else {
                data.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr + HSNCode, itemDscr, String.format("%.2f", rate), taxRate, Qty, String.format("%.2f", ValueText)));
                // data = data + "\n" + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", sr+HSNCode,itemDscr, String.format("%.2f", rate), taxRate, Qty,String.format("%.2f", ValueText));
            }


        }
        //Product details Ends

        //Tax details Starts
        ArrayList<String> arrTaxWisePrdctDtlt = hmapTotalBfrAftrTaxVal.get("TotalInvoiceBeforeAfterTax");
        double valueBeforeTax = 0.0;
        double valueAfterTax = 0.0;
        double roundingOff = 0.0;
        double netValue = 0.0;
        if (!arrTaxWisePrdctDtlt.get(0).equals("")) {
            valueBeforeTax = Double.parseDouble(arrTaxWisePrdctDtlt.get(0));//"1000.00";
        }
        if (!arrTaxWisePrdctDtlt.get(1).equals("")) {
            valueAfterTax = Double.parseDouble(arrTaxWisePrdctDtlt.get(1));//"1040.00";
        }
        if (!arrTaxWisePrdctDtlt.get(2).equals("")) {
            roundingOff = Double.parseDouble(arrTaxWisePrdctDtlt.get(2));//"1040.00";
        }
        if (!arrTaxWisePrdctDtlt.get(3).equals("")) {
            netValue = Double.parseDouble(arrTaxWisePrdctDtlt.get(3));//"1040.00";
        }


        //loop for tax
        StringBuilder taxData = new StringBuilder();
        taxData.append("");
        int length = 2;
        double totalTaxValue = 0.0;
        for (Map.Entry<String, ArrayList<String>> entry : hmapTaxWisePrdctDtlt.entrySet()) {
            String taxPercent = entry.getKey().trim();
            ArrayList<String> arrTaxValue = entry.getValue();
            String tax = taxPercent;//"5% Tax";
            double taxAmount = Double.parseDouble(arrTaxValue.get(0));//"20.00";
            totalTaxValue = totalTaxValue + taxAmount;
            if (hmapTaxWisePrdctDtlt.size() > 1) {
                taxData.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$13s %6$3s", "", "  "+tax, "", "", String.format("%.2f", taxAmount), ""));
                //taxData = taxData +"\n"+ String.format("%1$-11s %2$-19s %3$9s %4$3s %5$13s %6$3s",  "", tax, "","",String.format("%.2f", taxAmount), "");
            } else {
                taxData.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "  "+tax, "", "", "", String.format("%.2f", taxAmount)));
                //taxData = taxData +"\n"+  String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s",  "", tax, "","","", String.format("%.2f", taxAmount));
            }


        }

        String totalTax = "" + String.format("%.2f", totalTaxValue);//"40.00";
        if (hmapTaxWisePrdctDtlt.size() > 1) {
            taxData.append("\n" + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Tax Value", "", "", "", totalTax));
            //taxData = taxData +"\n"+ String.format("%1$-11s %2$-19s %3$9s %4$3s %5$13s %6$3s",  "", "Tax Value", "","",totalTax, "");
        }
        //Tax details Ends
        //loop for tax end

        //OutStandingsDetails Starts here
        ArrayList<String> arrCollectionDetailsForPrint = fnGetOutStandingDetailsForPrint();
        String PreviousOutStandings = arrCollectionDetailsForPrint.get(0);//"150.00";
        String currentInvoice = arrCollectionDetailsForPrint.get(1);//"1040.00";
        String collection = arrCollectionDetailsForPrint.get(2);//"500.00";
        String currentOutStanding = arrCollectionDetailsForPrint.get(3);//"750.00";
        //OutStandingsDetails Ends here
        //SalesMan Details
        String salesman_mob_no = "";
        String salesman_name = "";
        String PersonNameAndFlgRegistered = "0";
        PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
        if (!PersonNameAndFlgRegistered.equals("0")) {
            salesman_name = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
            salesman_mob_no = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[2];
            if (salesman_mob_no.equals("0")) {
                salesman_mob_no = "";
            }

        }

        //End here

        StringBuilder BILL = new StringBuilder();
        BILL.append("");
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append(INVOICE_HEADER + "\n");
        //BILL = BILL + INVOICE_HEADER+"\n";
        BILL.append("---------------------------------------------------------------\n" + shopName + "\n" + shopAddress + "\n");
        /*BILL = BILL
                 +"---------------------------------------------------------------\n" +
                  ""+shopName+"\n" +
                ""+shopAddress+"\n";
               // ""+ placeOfSuppllyAddress+" \n";*/
        BILL.append("Phone: " + phoneNumber + "  GSTIN. No.:" + gstNumber + " \n");
        //BILL = BILL + "Phone: " + phoneNumber + "  GSTIN. No.:" + gstNumber + " \n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append("" + custName + "\n");
        //BILL = BILL + "" + custName + "\n";
        BILL.append("" + custAddress + "\n");
        //BILL = BILL + "" + custAddress + "\n";
        //BILL = BILL + ""+custStateCityPin+"\n";
        BILL.append("INVOICE  NO: " + deliveryNumber + "\n");
        //BILL = BILL + "INVOICE  NO: " + deliveryNumber + "\n";
        BILL.append("GST  NO: " + gstNumberCustomer + "\n");
        //BILL = BILL + "GST  NO: " + gstNumberCustomer + "\n";
        BILL.append("INVOICE DATE & Time:  " + date + "\n");
        //BILL = BILL + "INVOICE DATE & Time:  " + date + "\n";

        //BILL = BILL + "Register under composite scheme? "+ compositeScheme+"\n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";

        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "Sr HSNCode", "Item Descr", "Rate ", "TaxRt", "Qty", "Value"));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "Sr HSNCode", "Item Descr", "Rate ", "TaxRt", "Qty", "Value");
        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append("---------------------------------------------------------------");
        //BILL = BILL + "---------------------------------------------------------------";
   /* BILL = BILL + "\n" + String.format("%1$-12s %2$-25s %3$6s %4$5s %5$4s %6$7s", "01 111111","item-111111", "\u20B9"+"50", "6%", "10","200.00");

    BILL = BILL + "\n" + String.format("%1$-12s %2$-25s %3$6s %4$5s %5$4s %6$5s", "01 111111","item-11", "\u20B9"+"50", "6%", "10","₹200.00");
    BILL = BILL + "\n" + String.format("%1$-12s %2$-25s %3$6s %4$5s %5$4s %6$5s", "01 111111","item-111111333", "\u20B9"+"50", "6%", "10","200.00");
    BILL = BILL + "\n" + String.format("%1$-12s %2$-25s %3$6s %4$5s %5$4s %6$5s", "01 111111","item-111455555555", "\u20B9"+"50", "6%", "10","200.00");
    */
        BILL.append(data);
        //BILL = BILL + data;

        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "", "Total               ", "", "", TotalQty, TotalValue));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$5s %5$5s %6$9s", "", "Total               ", "", "", TotalQty, TotalValue);
        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append("                           Tax Details                            \n");
        //BILL = BILL + "                           Tax Details                            \n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Value Before Tax", "", "", "", String.format("%.2f", valueBeforeTax)));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Value Before Tax", "", "", "", String.format("%.2f", valueBeforeTax));
        BILL.append(taxData);
        //BILL = BILL + taxData;
        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Value After Tax", "", "", "", String.format("%.2f", valueAfterTax)));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Value After Tax", "", "", "", String.format("%.2f", valueAfterTax));
        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Rounding Off", "", "", "", String.format("%.2f", roundingOff)));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Rounding Off", "", "", "", String.format("%.2f", roundingOff));
        BILL.append("\n");
        //BILL = BILL + "\n";
        BILL.append(String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Net Value", "", "", "", String.format("%.2f", netValue)));
        //BILL = BILL + String.format("%1$-11s %2$-19s %3$9s %4$3s %5$3s %6$13s", "", "Net Value", "", "", "", String.format("%.2f", netValue));
        BILL.append("\n");
        //BILL = BILL + "\n";
        /*
        BILL = BILL
                + "---------------------------------------------------------------\n";
        BILL = BILL
                + "                       OutStanding(s) Details                    \n";
        BILL = BILL
                + "---------------------------------------------------------------\n";

        BILL = BILL +"\n" + String.format("%1$-8s %2$-24s %3$9s %4$3s %5$3s %6$13s",  "", "Previous OutStanding(s)", "","","", PreviousOutStandings);
        BILL = BILL +"\n" + String.format("%1$-8s %2$-24s %3$9s %4$3s %5$3s %6$13s",  "", "Current Invoice", "","","", currentInvoice);
        BILL = BILL +"\n" + String.format("%1$-8s %2$-24s %3$9s %4$3s %5$3s %6$13s",  "", "Collection", "","","", collection);
        BILL = BILL +"\n" + String.format("%1$-8s %2$-24s %3$9s %4$3s %5$3s %6$13s",  "", "Current OutStanding(s)", "","","", currentOutStanding);
        BILL = BILL + "\n";
        */
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append("SALESMAN NAME:" + salesman_name + "\n");
        //BILL = BILL + "SALESMAN NAME:" + salesman_name + "\n";
        BILL.append("SALESMAN MOBILE NO:" + salesman_mob_no + " SALESMAN SIGN:______\n");
        //BILL = BILL + "SALESMAN MOBILE NO:" + salesman_mob_no + " SALESMAN SIGN:______\n";
        BILL.append("*Thank You*\n");
        //BILL = BILL "*Thank You*\n";
        BILL.append("---------------------------------------------------------------\n");
        //BILL = BILL + "---------------------------------------------------------------\n";
        BILL.append("");
        //BILL = BILL + "";
        System.out.println("SHIVAMJAYSAWAL" + BILL);
        return BILL.toString();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    long interval = 200;
                    long startTime = 25000;
                    countDownTimer = new CoundownClass(startTime, interval);
                    countDownTimer.start();
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(CollectionActivityNew.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(CollectionActivityNew.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            if ((countDownTimer != null)) {
                countDownTimer.cancel();
            }
            Toast.makeText(CollectionActivityNew.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
            retrieveLocationAndSubmitData();


           /* } else {
                //means print only
                //mDataSource.open();
                ArrayList<String> arrResult = mDataSource.fnFetch_tblWarehouseMstr();
                // mDataSource.close();
                arrAllPrintResult = null;
                if ((arrResult != null) && (arrResult.size() > 0)) {
                    arrAllPrintResult = mDataSource.fnFetch_InvoiceReportForPrint(StoreVisitCode, storeID, Integer.parseInt(arrResult.get(0)), Integer.parseInt(arrResult.get(1)));
                    if ((arrAllPrintResult != null) && (arrAllPrintResult.size() > 0)) {
                        PrintAll_Code();
                    } else {
                        Toast.makeText(CollectionActivityNew.this, "All print data is blank", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CollectionActivityNew.this, "NodeID Blank", Toast.LENGTH_SHORT).show();
                }

            }
*/

        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if ((countDownTimer != null) && (mBluetoothConnectProgressDialog != null)) {

                if (mBluetoothConnectProgressDialog.isShowing()) {
                    mBluetoothConnectProgressDialog.dismiss();
                    countDownTimer.cancel();
                }
            }
            if (mBluetoothSocket != null){
                mBluetoothSocket.close();
            }

        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if ((countDownTimer != null) && (mBluetoothConnectProgressDialog != null)) {

                if (mBluetoothConnectProgressDialog.isShowing()) {
                    mBluetoothConnectProgressDialog.dismiss();
                    countDownTimer.cancel();
                }
            }
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    public String insertPeriodically(
            String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(
                text.length() + insert.length() * (text.length() / period) + 1);

        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            // Don't put the insert in the very first iteration.
            // This is easier than appending it *after* each substring
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index,
                    Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }
    //Printer functions start here----------------------------------------------------------------

    public void retrieveLocationAndSubmitData(){
        flgOnlySubmitOrPrint = 1;

        /* if (onlyPrintFlag == 0) {*/
        //means print and submit both
        saveDataToDatabase();
        butClickForGPS = 3;

        if ((mDataSource.PrevLocChk(storeID.trim(), StoreVisitCode))) {


            FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
            task.execute();
        } else {

            pDialog2STANDBY = ProgressDialog.show(CollectionActivityNew.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.genTermRetrivingLocation), true);
            pDialog2STANDBY.setIndeterminate(true);

            pDialog2STANDBY.setCancelable(false);
            pDialog2STANDBY.show();


        }
    }
    //flgCollDefControl
    public boolean validate_flgCollDefControl(){
     /*  CommonInfo.flgCollDefControl=3;
        CommonInfo.CollectionPer=10.50;*/

        Double OverAllAmountCollectedLimit = mDataSource.fetch_Store_MaxCollectionAmount(storeID, TmpInvoiceCodePDA);
        OverAllAmountCollectedLimit = Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollectedLimit));
        OverAllAmountCollected = Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollected));

        Double MinCollectionvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        MinCollectionvalue = Double.parseDouble(new DecimalFormat("##.##").format(MinCollectionvalue));



        if (ll_collectionMandatory.getVisibility() == View.VISIBLE && cb_collection.isChecked() == false && lnCollection.getVisibility() == View.VISIBLE && OverAllAmountCollected == 0.0) {
            showAlertSingleButtonError(CollectionActivityNew.this.getResources().getString(R.string.CollectionAlert1));

            //Collected amount is less than the minimum collection amount , current invoice cannot be made.Click CANCEL & EXIT to close Invoice and exit current visit, Click on UPDATE PAYMENT to update Collection amount

            return false;
        }
        else if(CommonInfo.flgCollDefControl==0){
            return true;
        }
        else if(CommonInfo.flgCollDefControl==1){
            if((Double.parseDouble(tv_cntInvoceValue.getText().toString())==Double.parseDouble(totaltextview.getText().toString())) || (Double.parseDouble(tv_cntInvoceValue.getText().toString())<Double.parseDouble(totaltextview.getText().toString()))){
                return true;

            }
            else{
                showAlertSingleButtonError("Full Amount to be collected for invoice submission");
                return false;
            }

        }
        else if(CommonInfo.flgCollDefControl==2){
            if((Double.parseDouble(tv_MinCollectionvalue.getText().toString())==Double.parseDouble(totaltextview.getText().toString())) || (Double.parseDouble(tv_MinCollectionvalue.getText().toString())<Double.parseDouble(totaltextview.getText().toString()))){
                return true;

            }
            else{
                showAlertSingleButtonError("Minimum Amount to be collected for invoice submission");
                return false;
            }
        }
        else if(CommonInfo.flgCollDefControl==3){
            Double currentInvoice=  Double.parseDouble(tv_cntInvoceValue.getText().toString());
            Double outStanding=  Double.parseDouble(tv_outstandingvalue.getText().toString());
            Double result = (currentInvoice * CommonInfo.CollectionPer) / 100;
            Double total=result+outStanding;
            if((total==Double.parseDouble(totaltextview.getText().toString())) || (total<Double.parseDouble(totaltextview.getText().toString()))){
                return true;

            }
            else{
                showAlertSingleButtonError("(Previous outstanding + " + CommonInfo.CollectionPer+"% of current invoice value) Amount to be collected for invoice submission");
                return false;
            }
        }
           /* else if(CommonInfo.flgCollDefControl==4){
                if((Double.parseDouble(tv_MinCollectionvalue.getText().toString())==Double.parseDouble(totaltextview.getText().toString())) || (Double.parseDouble(tv_MinCollectionvalue.getText().toString())<Double.parseDouble(totaltextview.getText().toString()))){
                    return true;

                }
                else{
                    showAlertSingleButtonError("Outstanding Amount must to be collected for invoice submission");
                    return false;
                }
            }*/
        else{
            return true;
        }



    }
}