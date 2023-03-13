package com.astix.allanasosfa;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.sync.SyncJobService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class ProductInvoiceReview extends BaseActivity implements  OnClickListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationInterface,focusLostCalled{
	HashMap<String, String> hmapProductIdLastOrder=new HashMap<String, String>();
	public ProductFilledDataModel prdctModelArrayList=new ProductFilledDataModel();
	LinkedHashMap<String,String> hampGetLastProductExecution=new LinkedHashMap<String,String>();
	HashMap<String, String> hmapProductExtraOrder=new HashMap<String, String>();
	TextView txt_Lststock;
	public EditText ed_extraQty;
	String lastStockDate="";
	LinkedHashMap<String,String> hmapFetchPDASavedData=new LinkedHashMap<>();
	HashMap<String, String> hmapProductIdLastStock=new HashMap<String, String>();
	private static final String TAG = "LocationActivity";
	private static final long INTERVAL = 1000 * 10;
	private static final long FASTEST_INTERVAL = 1000 * 5;
	private static final long MIN_TIME_BW_UPDATES = 1000  * 1; //1 second
	private final long startTime = 10000;
	private final long interval = 200;
	public int flgTransferStatus=0;
	public String VisitTimeInSideStore="NA";
	public String StoreVisitCode="NA";
	//public TextView tvRateHeading;
	public String VisitTypeStatus="1";
	public int PriceApplyDiscountLevelType=0;
	public Double cutoffvalue=0.0;
	public String TmpInvoiceCodePDA="NA";
	public String strFinalAllotedInvoiceIds="NA";
	public int chkflgInvoiceAlreadyGenerated=0;
	public boolean flgAllTotalOrderValueCrossesCutOff=false;
	public  int flgLocationServicesOnOffOrderReview=0;
	public  int flgGPSOnOffOrderReview=0;
	public  int flgNetworkOnOffOrderReview=0;
	public  int flgFusedOnOffOrderReview=0;
	public  int flgInternetOnOffWhileLocationTrackingOrderReview=0;
	public  int flgRestartOrderReview=0;
	public  int flgStoreOrderOrderReview=0;
	public int powerCheck=0;
	public  PowerManager.WakeLock wl;
	public int StoreCurrentStoreType=0;
	public String Noti_text="Null";
	public int MsgServerID=0;
	public  String[] arrSchId;
	public String SchemeDesc="NA";
	public TextView spinner_product;
	public String ProductIdOnClickedEdit;
	public String CtaegoryIddOfClickedView;
	public String condtnOddEven;
	public String storeID;
	public String imei;
	public String date;
	public String pickerDate;
	int flgOrderType=0;
	public String SN;
	//public ProgressDialog pDialogSync;
	public String productID;
	public String strGlobalOrderID="0";
	RecyclerView rv_prdct_detal;
	//Invoice TextView
	public TextView tv_NetInvValue;
	public TextView tvTAmt;
	public TextView tvDis;
	public TextView tv_GrossInvVal;
	public TextView tvFtotal;
	public TextView tvAddDisc;
	public TextView tv_NetInvAfterDiscount;
	public TextView tvPreAmtOutstandingVALNew;
	public TextView tvAmtPrevDueVAL;
	public EditText etAmtCollVAL;
	public TextView tvAmtOutstandingVAL;
	public TextView tvCredAmtVAL;
	public TextView tvTotalAmtCollected;
	public TextView tvINafterCredVAL;
	public TextView textView1_CredAmtVAL_new;
	public TextView tvNoOfCouponValue;
	public TextView txttvCouponAmountValue;
	public String lastKnownLocLatitude="";
	public String lastKnownLocLongitude="";
	public String accuracy="0";
	public String locationProvider="Default";
	public double glbNetOrderLevelPercentDiscount=0.00;
	public double glbNetOderLevelFlatDiscount=0.00;
	public double glbGrossOrderLevelPercentDiscount=0.00;
	public double glbGrossOrderLevelFlatDiscount=0.00;
	public String newfullFileName;
	public int butClickForGPS=0;
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

	public String fnaddress="NA";
	public String AllProvidersLocation="NA";
	public String GpsAddress="NA";
	public String NetwAddress="NA";
	public String FusedAddress="NA";
	public String FusedLocationLatitudeWithFirstAttempt="0";
	public String FusedLocationLongitudeWithFirstAttempt="0";
	public String FusedLocationAccuracyWithFirstAttempt="0";

	public CoundownClass2 countDownTimer2;
	public String fnAccurateProvider="";
	public String fnLati="0";
	public String fnLongi="0";
	public Double fnAccuracy=0.0;
	public EditText   ed_search;
	public ImageView  btn_go;
	public String[] prductId;
	public int flgApplyFreeProductSelection=0;
	public int isbtnExceptionVisible=0;
	public LocationManager locationManager;
	public Location location;
	// PowerManager.WakeLock wl;
	public ProgressDialog pDialog2STANDBY;
	public int flgProDataCalculation=1;
	public int StoreCatNodeId=0;
	public ListView lvProduct;
	public EditText inputSearch;
	HashMap<String, Integer> hmapVanPrdctStockCount;// = new HashMap<String, Integer>();
	TextView txt_TotalLineCount_Value,txt_TotalInvoiceCount_Value;
	//hmapProductStock= key =ProductID         value=ProductSelectedUOMId
	HashMap<String, String> hmapProductSelectedUOMId=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=LineValBfrTxAftrDscnt
	HashMap<String, String> hmapLineValBfrTxAftrDscnt=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=LineValAftrTxAftrDscnt
	HashMap<String, String> hmapLineValAftrTxAftrDscnt=new HashMap<String, String>();
	LinkedHashMap<String,String> hmapPerBaseQty;
	//nitika
	CustomKeyboard mCustomKeyboardNum,mCustomKeyboardNumWithoutDecimal;
	HashMap<String,Integer> hmapDistPrdctStockCount =new HashMap<String,Integer>();
	HashMap<String,String> hmapPrdctIdOutofStock=new HashMap<String,String> ();
	String distID="";
	ArrayList<String> productFullFilledSlabGlobal=new ArrayList<String>();
	int countSubmitClicked=0;
	LocationRequest mLocationRequest;
	String defaultValForAlert;
	boolean disValClkdOpenAlert=false;
	String progressTitle;
	boolean alertOpens=false;
	int flagClkdButton=0;
	String spinnerCategorySelected;
	String spinnerCategoryIdSelected;
	Location lastKnownLoc;
	int countParentView;
	String alrtSlctPrdctNameId,alrtSpnr_EditText_Value;
	String alrtPrdctId;
	String productIdOnLastEditTextVal="0";
	String alrtValueSpnr;
	int alrtObjectTypeFlag=1; //1=spinner,edittext=0;
	// Decimal Format
	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	int CheckIfStoreExistInStoreProdcutPurchaseDetails=0;
	int CheckIfStoreExistInStoreProdcutInvoiceDetails=0;
	ProgressDialog mProgressDialog;
	//****************************************************************
	//Field
	String fusedData;
	String mLastUpdateTime;
	Location mCurrentLocation;
	GoogleApiClient mGoogleApiClient;
	EditText ed_LastEditextFocusd;
	View viewProduct;
	TextView txtVw_schemeApld;
	EditText alrtcrntEditTextValue;
	TextView alrtPrvsPrdctSlctd;
	Spinner alrtPrvsSpnrSlctd;
	Spinner spinner_category;
	ImageView btn_bck;
	TextView txtVwRate;
	TextView textViewFreeQty,textViewRate,textViewDiscount;
	LinearLayout ll_scheme_detail;
	int isReturnClkd=0;
	//****************************************************************
	//Arrays, HashMap
	View[] hide_View;
	String[] categoryNames;
	LinkedHashMap<String, String> hmapctgry_details=new LinkedHashMap<String, String>();
	HashMap<String, String> hmapProductToBeFree=new HashMap<String, String>();    //Not is use
	ArrayList<HashMap<String, String>> arrLstHmapPrdct=new ArrayList<HashMap<String,String>>();
	//hmapSchemeIDandDescr= key=SchId,val=SchDescr
	HashMap<String, String> hmapSchemeIDandDescr=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=CategoryID
	HashMap<String, String> hmapCtgryPrdctDetail=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=Volume^Rate^TaxAmount
	HashMap<String, String> hmapPrdctVolRatTax=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=OrderQty
	HashMap<String, String> hmapPrdctOdrQty=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=ProductSample
	HashMap<String, String> hmapPrdctSmpl=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=ProductFreeQty
	HashMap<String, String> hmapPrdctFreeQty=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=ProductName
	HashMap<String, String> hmapPrdctIdPrdctName=new HashMap<String, String>();
	HashMap<String, String> hmapPrdctIdPrdctNameVisible=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=ProductDiscount
	HashMap<String, String> hmapPrdctIdPrdctDscnt=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied Schemes,Slabs and other details
	HashMap<String, String> hmapProductRelatedSchemesList=new HashMap<String, String>();
	// hmapSchemeIdStoreID= key=SchemeId value StoreId
	HashMap<String, String> hmapSchemeIdStoreID=new HashMap<String, String>();
	// hmapSchmSlabIdSchmID key=SchemeSlabId value = SchemeID
	HashMap<String, String> hmapSchmSlabIdSchmID=new HashMap<String, String>();
	//HashMap<String, String> hmapSchemeStoreID=new HashMap<String, String>();
	// hmaSchemeSlabIdSlabDes key=SchemeSlabId value=SchemeSlab Description
	HashMap<String, String> hmapSchemeSlabIdSlabDes=new HashMap<String, String>();
	// hmapSchemeSlabIdBenifitDes key=SchemeSlabId value = BenifitDescription
	HashMap<String, String> hmapSchemeSlabIdBenifitDes=new HashMap<String, String>();
	// hmapProductIdOrdrVal key=Product Id value=ProductOrderVal
	HashMap<String, String> hmapProductIdOrdrVal =new HashMap<String, String>();
	// hmapProductIdStock key = ProductID value= Stock
	HashMap<String, String> hmapProductIdStock=new HashMap<String, String>();
	// hmapProductIdStock key = ProductID value= flgPriceAva
	HashMap<String, String> hmapProductflgPriceAva=new HashMap<String, String>();
	ArrayList<HashMap<String, String>> arrayListSchemeSlabDteail=new ArrayList<HashMap<String,String>>();
	//hmapSchmeSlabIdSchemeId= key =SchemeSlabId         value=SchemeID
	HashMap<String, String> hmapSchmeSlabIdSchemeId=new HashMap<String, String>();
	//hmapSchmeSlabIdSlabDes= key =SchemeSlabId         value=SchemeSlabDes
	HashMap<String, String> hmapSchmeSlabIdSlabDes=new HashMap<String, String>();
	//hmapSchmeSlabIdBenifitDes= key = SchemeSlabId        value=BenifitDescription
	HashMap<String, String> hmapSchmeSlabIdBenifitDes=new HashMap<String, String>();
	//hmapProductRetailerMarginPercentage= key =ProductID         value=RetailerMarginPercentage
	HashMap<String, String> hmapProductRetailerMarginPercentage=new HashMap<String, String>();
	//hmapProductVatTaxPerventage= key =ProductID         value=VatTaxPercentage
	HashMap<String, String> hmapProductVatTaxPerventage=new HashMap<String, String>();
	//hmapProductVatTaxPerventage= key =ProductID         value=ProductMRP
	HashMap<String, String> hmapProductMRP=new HashMap<String, String>();
	//hmapProductVolumePer= key =ProductID         value=Per
	HashMap<String, String> hmapProductVolumePer=new HashMap<String, String>();
	//hmapProductDiscountPercentageGive= key =ProductID         value=DiscountPercentageGivenOnProduct
	HashMap<String, String> hmapProductDiscountPercentageGive=new HashMap<String, String>();
	//hmapProductVolumePer= key =ProductID         value=TaxValue
	HashMap<String, String> hmapProductTaxValue=new HashMap<String, String>();
	//hmapProductVolumePer= key =ProductID         value=TaxValue
	HashMap<String, String> hmapProductViewTag=new HashMap<String, String>();
	//hmapProductVolumePer= key =ProductID         value=LODQty
	HashMap<String, String> hmapProductLODQty=new HashMap<String, String>();
	//hmapProductStandardRate= key =ProductID         value=StandardRate
	HashMap<String, String> hmapProductStandardRate=new HashMap<String, String>();
	//hmapProductStandardRateBeforeTax= key =ProductID         value=StandardRateBeforeTax
	HashMap<String, String> hmapProductStandardRateBeforeTax=new HashMap<String, String>();
	//hmapProductStandardTax= key =ProductID         value=StandardTax
	HashMap<String, String> hmapProductStandardTax=new HashMap<String, String>();
	LinkedHashMap<String, String> hmapFilterProductList=new LinkedHashMap<String, String>();
	HashMap<String, HashMap<String, HashMap<String, String>>> hmapPrdtAppliedSchIdsAppliedSlabIdsDefination;
	//hmapMinDlvrQty= key =ProductID         value=MinQty
	LinkedHashMap<String, Integer> hmapMinDlvrQty=new LinkedHashMap<String, Integer>();
	//hmapMinDlvrQty= key =ProductID         value=QPBT
	LinkedHashMap<String, String> hmapMinDlvrQtyQPBT=new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> hmapMinDlvrQtyQPAT=new LinkedHashMap<String, String>();
	LinkedHashMap<String,String> hmapPerUnitName;

	//Database
	//
	//hmapMinDlvrQty= key =ProductID         value=QPTaxAmount
	LinkedHashMap<String, String> hmapMinDlvrQtyQPTaxAmount=new LinkedHashMap<String, String>();
	//hmapProductStock= key =ProductID         value=flgWholeSellApplicable
	HashMap<String, String> hmapflgWholeSellApplicable=new HashMap<String, String>();

	//Common Controls Box
	//hmapProductStock= key =ProductID         value=flgPriceRangeWholeSellApplicable
	HashMap<String, String> hmapPriceRangeWholeSellApplicable=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardRateWholeSale
	HashMap<String, String> hmapStandardRateWholeSale=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardRateBeforeTaxWholeSell
	HashMap<String, String> hmapStandardRateBeforeTaxWholeSell=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardTaxWholeSale
	HashMap<String, String> hmapStandardTaxWholeSale=new HashMap<String, String>();
	//hmapMinDlvrQty= key =ProductID         value=ProductListOnWhichWholePriceNeedsToApplyIfRequired
	LinkedHashMap<String, String> hmapProductListOnWhichWholePriceNeedsToApplyIfRequired=new LinkedHashMap<String, String>();
	ArrayList<String> arredtboc_OderQuantityFinalSchemesToApply;
	EditText edtBox;
	TextView viewBox;
	String viewCurrentBoxValue="";
	ImageView img_return;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	PowerManager pm;
	LocationListener locationListener;
	double latitude;
	double longitude;
	View convertView;
	ArrayAdapter<String> adapter;
	AlertDialog ad;
	String[] products;
	private boolean alrtStopResult = false;
	Context ctx;
	private DatabaseAssistant DA;
	//private MyService mMyService;
	public Context getCtx() {
		return ctx;
	}
	public void showSettingsAlert()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
		alertDialog.setIcon(R.drawable.error_info_ico);
		alertDialog.setCancelable(false);
		// Setting Dialog Message
		alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.genTermGPSDisablePleaseEnable));

		// On pressing Settings button
		alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {

				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{

			mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
			mCustomKeyboardNum.hideCustomKeyboard();

			return false;


//			return true;
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


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list_review);
		ctx=this;
		//Toast.makeText(OrderReview.this, "OrderReview Page is called", Toast.LENGTH_SHORT).show();
		locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
		DA=new DatabaseAssistant(this);

		boolean isGPSok = false;
		boolean isNWok=false;
		isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		rv_prdct_detal=(RecyclerView)findViewById(R.id.rv_prdct_detal);
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
		VisitTimeInSideStore= mDataSource.fnGetDateTimeString();
		initializeFields();
		if(powerCheck==0)
		{
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
			wl.acquire();
		}
			/*LocalBroadcastManager.getInstance(ProductList.this).registerReceiver(
			mLocationReceiver, new IntentFilter("GPSLocationUpdates"));*/
	}

	public String[] changeHmapToArrayKey(HashMap hmap)
	{
		String[] stringArray=new String[hmap.size()];
		int index=0;
		if(hmap!=null)
		{
			Set set2 = hmap.entrySet();
			Iterator iterator = set2.iterator();
			while(iterator.hasNext())
			{
				Entry me2 = (Entry)iterator.next();
				stringArray[index]=me2.getKey().toString();
				index=index+1;
			}
		}
		return stringArray;
	}

	public String[] changeHmapToArrayValue(HashMap hmap)
	{
		String[] stringArray=new String[hmap.size()];
		int index=0;
		if(hmap!=null)
		{
			Set set2 = hmap.entrySet();
			Iterator iterator = set2.iterator();
			while(iterator.hasNext()) {
				Entry me2 = (Entry)iterator.next();
				stringArray[index]=me2.getValue().toString();
				System.out.println("Betu Slab = "+stringArray[index]);
				index=index+1;
			}
		}
		return stringArray;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}


	public void onDestroy()
	{
		super.onDestroy();
		wl.release();
		// LocalBroadcastManager.getInstance(ProductList.this).unregisterReceiver(mLocationReceiver);

	}

	public void loadPurchaseProductDefault()
	{
		hmapFilterProductList.clear();
		hmapPrdctIdPrdctNameVisible.clear();



		hmapFilterProductList= mDataSource.getFileredOrderReviewProductListMap(storeID,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
		System.out.println("hmapFilterProductListCount :-"+ hmapFilterProductList.size());

		Iterator it11new = hmapPrdctIdPrdctName.entrySet().iterator();
		String crntPID="0";
		int cntPsize=0;

		if(hmapFilterProductList.size()>0) {
			String[] listProduct = new String[hmapFilterProductList.size()];
			int index = 0;
			for (Entry<String, String> entry : hmapFilterProductList.entrySet()) {
				listProduct[index] = entry.getKey() + "^" + entry.getValue();
				index++;
			}

			OrderAdapter orderAdapter=new OrderAdapter(ProductInvoiceReview.this,listProduct,hmapFilterProductList,hmapProductStandardRate,hmapProductMRP,hmapProductIdStock,hmapProductIdLastStock,hampGetLastProductExecution,hmapDistPrdctStockCount,hmapVanPrdctStockCount,prdctModelArrayList,1,hmapProductIdLastOrder);
			rv_prdct_detal.setAdapter(orderAdapter);
			rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
			int InvoiceReviewLineCount = mDataSource.fetchtblInvoiceReviewLineCount(storeID, TmpInvoiceCodePDA);
			txt_TotalLineCount_Value.setText("" + InvoiceReviewLineCount);
			int TotalInvoiceCount = mDataSource.fetchtblInvoiceReviewCount(storeID, TmpInvoiceCodePDA);
			txt_TotalInvoiceCount_Value.setText("" + TotalInvoiceCount);
		}
		// createProductPrepopulateDetail(CheckIfStoreExistInStoreProdcutPurchaseDetails);
		setInvoiceTableView();
		orderBookingTotalCalc();

	}

	public void	initializeFields() {

		getDataFromIntent();
		//StoreVisitCode=mDataSource.fnGetStoreVisitCode(storeID);
		//nitika
		mCustomKeyboardNum= new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num );
		mCustomKeyboardNumWithoutDecimal= new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal );
		txt_Lststock= (TextView) findViewById(R.id.txt_Lststock);
			/*if(!TextUtils.isEmpty(lastStockDate))
			{
				txt_Lststock.setText("Stk On "+lastStockDate);
			}
			else
			{
				txt_Lststock.setText("Last Stk NA");
			}*/

		if(CommonInfo.hmapAppMasterFlags.containsKey("flgSchemeAvailable") && CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartSchemeDetails"))
		{
			if(CommonInfo.hmapAppMasterFlags.get("flgSchemeAvailable")==1 && CommonInfo.hmapAppMasterFlags.get("flgVisitStartSchemeDetails")==1)
			{
				RelativeLayout ll_schm_applcbl = (RelativeLayout)findViewById(R.id.ll_schm_applcbl);
				ll_schm_applcbl.setVisibility(View.VISIBLE);
			}
		}
		TextView order_detailHeading=(TextView)findViewById(R.id.order_detail);
		TextView txt_TotalInvoiceCount=(TextView)findViewById(R.id.txt_TotalInvoiceCount);
		TextView txt_detalis=(TextView)findViewById(R.id.txt_detalis);

		//	TextView lbl_InvOrderHeader=(TextView)findViewById(R.id.lbl_InvOrderHeader);
		TextView tv_EntryInvValHeader=(TextView)findViewById(R.id.tv_EntryInvValHeader);

		if(CommonInfo.flgDrctslsIndrctSls==2)
		{
			order_detailHeading.setText("Order Review");
			txt_TotalInvoiceCount.setText("Total Order Qty");
			txt_detalis.setText("Order Total");
			//lbl_InvOrderHeader.setText("O.Qty.");
			tv_EntryInvValHeader.setText("O.Val");
		}
		if(!TextUtils.isEmpty(lastStockDate))
		{
			txt_Lststock.setText(getResources().getString(R.string.stkOn)+lastStockDate);
		}
		else
		{
			txt_Lststock.setText(getResources().getString(R.string.LastStk));
		}
		txt_TotalLineCount_Value=(TextView) findViewById(R.id.txt_TotalLineCount_Value);

		txt_TotalInvoiceCount_Value=(TextView) findViewById(R.id.txt_TotalInvoiceCount_Value);


		//	spinner_product=(TextView) findViewById(R.id.spinner_product);
		//tvRateHeading=(TextView) findViewById(R.id.tvRateHeading);
		ed_search=(EditText) findViewById(R.id.ed_search);
		btn_go=(ImageView) findViewById(R.id.btn_go);
		txtVw_schemeApld=(TextView) findViewById(R.id.txtVw_schemeApld);
		txtVw_schemeApld.setText("");
		txtVw_schemeApld.setTag("0");

		//productIdOnLastEditText

		btn_go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {}


		});

	/*	TextView tv_InvOrderHeader=(TextView) findViewById(R.id.lbl_InvOrderHeader) ;
		tv_InvOrderHeader.setText(R.string.OQty);
		if(CommonInfo.flgDrctslsIndrctSls==2)
		{

			lbl_InvOrderHeader.setText("O.Qty.");
		}*/
		Button btn_OrderReview=(Button) findViewById(R.id.btn_OrderReview);
		btn_OrderReview.setVisibility(View.GONE);
		btn_OrderReview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent storeOrderReviewIntent = new Intent(ProductInvoiceReview.this, ProductOrderReview.class);
				storeOrderReviewIntent.putExtra("storeID", storeID);
				storeOrderReviewIntent.putExtra("SN", SN);
				storeOrderReviewIntent.putExtra("bck", 1);
				storeOrderReviewIntent.putExtra("imei", imei);
				storeOrderReviewIntent.putExtra("userdate", date);
				storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
				storeOrderReviewIntent.putExtra("OrderPDAID", strGlobalOrderID);
				storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
				startActivity(storeOrderReviewIntent);
				finish();

			}
		});
		TextView txt_RefreshOdrTot=(TextView) findViewById(R.id.txt_RefreshOdrTot);
		txt_RefreshOdrTot.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{


				orderBookingTotalCalc();

			}
		});

			/*Double outstandingvalue=mDataSource.fnGetStoretblLastOutstanding(storeID);
			outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));


			Double cntInvoceValue=mDataSource.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
			cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));*/


		Double CollectionAmt= mDataSource.fnTotCollectionAmtAgainstStore(storeID,TmpInvoiceCodePDA,StoreVisitCode);
		CollectionAmt=Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmt));



		Double cntInvoceValue= mDataSource.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
		cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

		Double cntAllOustandings= mDataSource.fetch_Store_AllOustandings(storeID);
		cntAllOustandings=Double.parseDouble(new DecimalFormat("##.##").format(cntAllOustandings));


		Double cntTotCollectionAmtAgainstStoreIrespectiveOfVisit= mDataSource.fnTotCollectionAmtAgainstStoreIrespectiveOfVisit(storeID);
		cntTotCollectionAmtAgainstStoreIrespectiveOfVisit=Double.parseDouble(new DecimalFormat("##.##").format(cntTotCollectionAmtAgainstStoreIrespectiveOfVisit));




		Double cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit= mDataSource.fnTotInvoicesAmtAgainstStoreIrespectiveOfVisit(storeID);
		cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit=Double.parseDouble(new DecimalFormat("##.##").format(cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit));



		Double totOutstandingValue=cntAllOustandings+cntTotInvoicesAmtAgainstStoreIrespectiveOfVisit-cntTotCollectionAmtAgainstStoreIrespectiveOfVisit;
		totOutstandingValue=Double.parseDouble(new DecimalFormat("##.##").format(totOutstandingValue));

		final Button btn_AmountCollect=(Button) findViewById(R.id.btn_collectAmount);
		btn_AmountCollect.setVisibility(View.GONE);


		final Button btn_Submit=(Button) findViewById(R.id.btn_sbmt);
		final Button btn_Print=(Button) findViewById(R.id.btn_print);
		btn_Submit.setTag("0_0");
		btn_Print.setTag("2_2");

		btn_Submit.setVisibility(View.GONE);
		btn_Print.setVisibility(View.GONE);

		final Button  btn_Save=(Button)findViewById(R.id.btn_save);
		btn_Save.setTag("0_0");


		btn_Save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
						{

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,7);
							}
							else
							{

								if(mProgressDialog!=null) {
									if (mProgressDialog.isShowing() == true) {
										mProgressDialog.dismiss();
									}
								}


								nextStepAfterRetailerCreditBal(7);
							}

						}
						else
						{


							nextStepAfterRetailerCreditBal(7);
						}
					}


					else
					{
						nextStepAfterRetailerCreditBal(7);
					}



				}
				else
				{
					nextStepAfterRetailerCreditBal(7);
				}



			}
		});


		final Button btn_OrderPayment=(Button) findViewById(R.id.btn_OrderPayment);
		if(CommonInfo.flgDrctslsIndrctSls==2)
		{
			btn_OrderPayment.setText("Order Delivery Details");
		}
		if(CommonInfo.hmapAppMasterFlags.containsKey("flgShowDeliveryAddressButtonOnOrder"))
		{
			if(CommonInfo.hmapAppMasterFlags.get("flgShowDeliveryAddressButtonOnOrder")==1)
			{
				btn_OrderPayment.setVisibility(View.VISIBLE);
			}
		}
		btn_OrderPayment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
     /*Intent AmtCollectIntent = new Intent(OrderReview.this, Delivery_Details_Activity.class);
     AmtCollectIntent.putExtra("storeID", storeID);
     AmtCollectIntent.putExtra("imei", imei);
     AmtCollectIntent.putExtra("userdate", date);
     AmtCollectIntent.putExtra("pickerDate", pickerDate);
     AmtCollectIntent.putExtra("SN", SN);
     AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
     startActivity(AmtCollectIntent);
     finish();*/

				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
						{

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,7);
							}
							else
							{

								if(mProgressDialog!=null) {
									if (mProgressDialog.isShowing() == true) {
										mProgressDialog.dismiss();
									}
								}


								nextStepAfterRetailerCreditBal(6);
							}

						}
						else
						{


							nextStepAfterRetailerCreditBal(6);
						}
					}


					else
					{
						nextStepAfterRetailerCreditBal(6);
					}



				}
				else
				{
					nextStepAfterRetailerCreditBal(6);
				}


			}
		});
		final Button btn_NextToCollection=(Button) findViewById(R.id.btn_NextToCollection);

		if(totOutstandingValue==0.0 && cntInvoceValue==0.0)
		{
			btn_NextToCollection.setVisibility(View.GONE);

			//mDataSource.open();
			mDataSource.deleteWhereStoreId(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
			//mDataSource.close();
			btn_Submit.setVisibility(View.VISIBLE);
			btn_Print.setVisibility(View.GONE);
		}
		else
		{
			if(flgOrderType==0)
			{
				btn_NextToCollection.setVisibility(View.GONE);
				btn_Submit.setVisibility(View.VISIBLE);
				btn_Print.setVisibility(View.GONE);
			}
			else {
				btn_NextToCollection.setVisibility(View.VISIBLE);
				btn_Submit.setVisibility(View.GONE);
				btn_Print.setVisibility(View.GONE);
			}
		}

		//flgCollRequired //flgCollReqOrdr
		//	CommonInfo.hmapAppMasterFlags.put("flgCollRequired",1);
		if(CommonInfo.hmapAppMasterFlags.containsKey("flgCollRequired")){
			if(CommonInfo.hmapAppMasterFlags.get("flgCollRequired")==1) {
				if (CommonInfo.hmapAppMasterFlags.containsKey("flgCollReqOrdr")) {

					if (CommonInfo.hmapAppMasterFlags.get("flgCollReqOrdr") == 1) {

						if(totOutstandingValue==0.0 && cntInvoceValue==0.0)
						{
							btn_NextToCollection.setVisibility(View.GONE);
							mDataSource.deleteWhereStoreId(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
							btn_Submit.setVisibility(View.VISIBLE);
							btn_Print.setVisibility(View.GONE);
						}
						else{

							if(flgOrderType==0)
							{
								btn_NextToCollection.setVisibility(View.GONE);
								btn_Submit.setVisibility(View.VISIBLE);
								btn_Print.setVisibility(View.GONE);
							}
							else {
								btn_NextToCollection.setVisibility(View.VISIBLE);
								btn_Submit.setVisibility(View.GONE);
							}
						}


					}
					if (CommonInfo.hmapAppMasterFlags.get("flgCollReqOrdr") == 0) {
						btn_NextToCollection.setVisibility(View.GONE);
						btn_Submit.setVisibility(View.VISIBLE);
						btn_Submit.setEnabled(true);
					}
				}
			}
			if(CommonInfo.hmapAppMasterFlags.get("flgCollRequired")==0){
				btn_NextToCollection.setVisibility(View.GONE);
				btn_Submit.setVisibility(View.VISIBLE);
				btn_Submit.setEnabled(true);
			}
		}


		btn_NextToCollection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
						{

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,7);
							}
							else
							{

								if(mProgressDialog!=null) {
									if (mProgressDialog.isShowing() == true) {
										mProgressDialog.dismiss();
									}
								}


								nextStepAfterRetailerCreditBal(7);
							}

						}
						else
						{


							nextStepAfterRetailerCreditBal(7);
						}
					}


					else
					{
						nextStepAfterRetailerCreditBal(7);
					}



				}
				else
				{
					nextStepAfterRetailerCreditBal(7);
				}



			}
		});

		btn_AmountCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
						{

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,5);
							}
							else
							{

								if(mProgressDialog!=null) {
									if (mProgressDialog.isShowing() == true) {
										mProgressDialog.dismiss();
									}
								}


								nextStepAfterRetailerCreditBal(5);
							}

						}
						else
						{


							nextStepAfterRetailerCreditBal(5);
						}
					}


					else
					{
						nextStepAfterRetailerCreditBal(5);
					}



				}
				else
				{
					nextStepAfterRetailerCreditBal(5);
				}



			}
		});





		final Button btn_Cancel=(Button) findViewById(R.id.btn_Cancel);
		btn_Cancel.setOnClickListener(new OnClickListener() {
			//  wer
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub



				long StartClickTime = System.currentTimeMillis();
				Date dateobj1 = new Date(StartClickTime);
				SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
				String StartClickTimeFinal = df1.format(dateobj1);




				String fileName=imei+"_"+storeID;

				File file = new File("/sdcard/"+ CommonInfo.TextFileFolder+"/"+fileName);

				if (!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Cancel Button Click on Product List"+StartClickTimeFinal;


				FileWriter fw;
				try
				{
					fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(CommonInfo.fileContent);
					bw.close();

					//mDataSource.open();
					mDataSource.savetblMessageTextFileContainer(fileName,0);
					//mDataSource.close();


				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(ProductInvoiceReview.this);
				alertDialogSyncError.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialogSyncError.setCancelable(false);  // try submitting the details from outside the door
				alertDialogSyncError.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertCancelOrder));
				alertDialogSyncError.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{

								int flag=0;
								String[] imageToBeDeletedFromSdCard= mDataSource.deletFromSDcCardPhotoValidation(storeID.trim());
								if(!imageToBeDeletedFromSdCard[0].equals("No Data"))
								{
									for(int i=0;i<imageToBeDeletedFromSdCard.length;i++)
									{
										flag=1;
										//String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageToBeDeletedFromSdCard[i].toString().trim();
										String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageToBeDeletedFromSdCard[i].toString().trim();

										File fdelete = new File(file_dj_path);
										if (fdelete.exists()) {
											if (fdelete.delete()) {
												Log.e("-->", "file Deleted :" + file_dj_path);
												callBroadCast();
											} else {
												Log.e("-->", "file not Deleted :" + file_dj_path);
											}
										}
									}


								}

								mDataSource.deleteProductBenifitSlabApplieddeleteProductBenifitSlabApplied(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
								mDataSource.deleteAllStoreAlertValueProduct(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
								//mDataSource.open();
								mDataSource.UpdateStoreFlag(storeID.trim(), 0);
								mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 0,strGlobalOrderID,TmpInvoiceCodePDA);
								mDataSource.deleteStoreTblsRecordsInCaseCancelOrderInOrderBooking(storeID.trim(),flag,strGlobalOrderID,TmpInvoiceCodePDA);
								//mDataSource.close();
								mDataSource.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0,StoreVisitCode);


								Intent storeSaveIntent = new Intent(ProductInvoiceReview.this, LauncherActivity.class);
								startActivity(storeSaveIntent);
								finish();
							}

						});
				alertDialogSyncError.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogCancelButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {


								dialog.dismiss();

							}
						});
				alertDialogSyncError.setIcon(R.drawable.info_ico);

				AlertDialog alert = alertDialogSyncError.create();
				alert.show();



			}
		});






			/*btn_Submit.setEnabled(false);
			btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));*/



		if(totOutstandingValue<0.0)
		{
			btn_Submit.setEnabled(true);
			btn_Print.setEnabled(true);
			//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
		}
		else if(totOutstandingValue==0.0)
		{
			btn_Submit.setEnabled(true);
			btn_Print.setEnabled(true);
			//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
		}
			/*else if(Math.ceil(CollectionAmt)>Math.ceil(totOutstandingValue+cntInvoceValue))
			{
				if(CommonInfo.hmapAppMasterFlags.containsKey("flgCollRequired")){
					if(CommonInfo.hmapAppMasterFlags.get("flgCollRequired")==1) {
						btn_Submit.setEnabled(false);
						btn_Submit.setBackgroundColor(Color.parseColor("#D3D3D3"));
					}
					else{

					}
				}
				else{
					btn_Submit.setEnabled(false);
					btn_Submit.setBackgroundColor(Color.parseColor("#D3D3D3"));
				}


				btn_Print.setEnabled(false);
				btn_Print.setBackgroundColor(Color.parseColor("#D3D3D3"));

				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(totOutstandingValue>0.0 && CollectionAmt==0.0)
			{
				btn_Submit.setEnabled(false);
				btn_Submit.setBackgroundColor(Color.parseColor("#D3D3D3"));
				btn_Print.setEnabled(false);
				btn_Print.setBackgroundColor(Color.parseColor("#D3D3D3"));
				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(Math.ceil(totOutstandingValue+ cntInvoceValue)==Math.ceil(CollectionAmt))
			{
				btn_Submit.setEnabled(true);
				btn_AmountCollect.setBackgroundColor(Color.parseColor("#FFFF33"));
			}
			else if(totOutstandingValue>0.0 && CollectionAmt>0.0)
			{
				btn_Submit.setEnabled(true);
				btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
				btn_Print.setEnabled(true);
				btn_Print.setBackgroundColor(Color.parseColor("#2E7D32"));
			}*/

		btn_Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub


				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {
							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							}
							else
							{

								if(mDataSource.isFlgCrediBalSubmitted(storeID))
								{
									nextStepAfterRetailerCreditBal(2);
								}
								else
								{
									alertForRetailerCreditLimit(2);
								}
							}
						}
						else
						{
							if(mDataSource.isFlgCrediBalSubmitted(storeID))
							{
								nextStepAfterRetailerCreditBal(2);
							}
							else
							{
								alertForRetailerCreditLimit(2);
							}
						}
					}
					else
					{
						if(mDataSource.isFlgCrediBalSubmitted(storeID))
						{
							nextStepAfterRetailerCreditBal(2);
						}
						else
						{
							alertForRetailerCreditLimit(2);
						}
					}
				}
				else
				{
					if(mDataSource.isFlgCrediBalSubmitted(storeID))
					{
						nextStepAfterRetailerCreditBal(2);
					}
					else
					{
						alertForRetailerCreditLimit(2);
					}
				}
			}
		});

		img_return=(ImageView) findViewById(R.id.img_return);
		img_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);
							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							}
							else
							{

								if(mDataSource.isFlgCrediBalSubmitted(storeID))
								{
									nextStepAfterRetailerCreditBal(3);
								}
								else
								{
									alertForRetailerCreditLimit(3);
								}
							}
						}
						else
						{
							if(mDataSource.isFlgCrediBalSubmitted(storeID))
							{
								nextStepAfterRetailerCreditBal(3);
							}
							else
							{
								alertForRetailerCreditLimit(3);
							}
						}
					}
					else
					{
						if(mDataSource.isFlgCrediBalSubmitted(storeID))
						{
							nextStepAfterRetailerCreditBal(3);
						}
						else
						{
							alertForRetailerCreditLimit(3);
						}
					}



				}
				else
				{
					if(mDataSource.isFlgCrediBalSubmitted(storeID))
					{

						nextStepAfterRetailerCreditBal(3);

					}
					else
					{

						alertForRetailerCreditLimit(3);
					}
				}



			}
		});
			 /* spinner_category=(Spinner) findViewById(R.id.spinner_category);
			  spinner_category.setTag("0_0");
			  spinner_category.setOnFocusChangeListener(this);
			  */
		btn_bck=(ImageView) findViewById(R.id.btn_bck);
		btn_bck.setTag("0_0");
		// btn_bck.setOnFocusChangeListener(this);

		ll_scheme_detail=(LinearLayout) findViewById(R.id.ll_scheme_detail);



		btn_bck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(ed_LastEditextFocusd!=null)
				{
					String tag=ed_LastEditextFocusd.getTag().toString();
					if(tag.contains("etOrderQty"))
					{
						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {

							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							}
							else
							{

								if(mDataSource.isFlgCrediBalSubmitted(storeID))
								{

									nextStepAfterRetailerCreditBal(4);

								}
								else
								{

									alertForRetailerCreditLimit(4);
								}
							}

						}
						else
						{
							if(mDataSource.isFlgCrediBalSubmitted(storeID))
							{

								nextStepAfterRetailerCreditBal(4);

							}
							else
							{

								alertForRetailerCreditLimit(4);
							}
						}
					}


					else
					{
						if(mDataSource.isFlgCrediBalSubmitted(storeID))
						{

							nextStepAfterRetailerCreditBal(4);

						}
						else
						{

							alertForRetailerCreditLimit(4);
						}
					}



				}
				else
				{
					if(mDataSource.isFlgCrediBalSubmitted(storeID))
					{

						nextStepAfterRetailerCreditBal(4);

					}
					else
					{
						alertForRetailerCreditLimit(4);
					}
				}
			}
		});
		// spinner_category.setOnItemSelectedListener(this);

		new GetData().execute();

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
	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	private void getDataFromIntent() {


		Intent passedvals = getIntent();

		storeID = passedvals.getStringExtra("storeID");
		imei = passedvals.getStringExtra("imei");
		date = passedvals.getStringExtra("userdate");
		pickerDate = passedvals.getStringExtra("pickerDate");
		SN = passedvals.getStringExtra("SN");
		flgOrderType= passedvals.getIntExtra("flgOrderType",0);
		//StoreVisitCode=mDataSource.fnGetStoreVisitCode(storeID);
		if(CommonInfo.flgDrctslsIndrctSls==2)
		{

			StoreVisitCode= mDataSource.fnGetStoreVisitCodeInCaseOfIndrectSales(storeID);
		}
		else
		{

			StoreVisitCode= mDataSource.fnGetStoreVisitCode(storeID);
		}
		// //mDataSource.open();
		StoreCurrentStoreType=Integer.parseInt(mDataSource.fnGetStoreTypeOnStoreIdBasis(storeID));
		//	//mDataSource.close();
		//mDataSource.fnProductWiseAppliedScehmeSlabDetails(storeIdProd);
		hmapProductRelatedSchemesList= mDataSource.fnProductRelatedSchemesList();
		//hmapPrdtAppliedSchIdsAppliedSlabIdsDefination=mDataSource.fnProductWiseAppliedScehmeSlabDetails(StoreID);

		if(CommonInfo.flgDrctslsIndrctSls==2)
		{

			chkflgInvoiceAlreadyGenerated= mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,CommonInfo.flgDrctslsIndrctSls);
			if(chkflgInvoiceAlreadyGenerated==1)
			{
				TmpInvoiceCodePDA= mDataSource.fnGetInvoiceCodePDA(storeID,StoreVisitCode);

			}
			else if(mDataSource.fnCheckForNewInvoiceOrPreviousValueFromPermanentTable(storeID,StoreVisitCode)==1)
			{
				TmpInvoiceCodePDA= mDataSource.fnGetInvoiceCodePDAFromPermanentTable(storeID,StoreVisitCode);
			}
			else
			{
				TmpInvoiceCodePDA = genTempInvoiceCodePDA();
			}
		}
		else {
			chkflgInvoiceAlreadyGenerated= mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,CommonInfo.flgDrctslsIndrctSls);
			if (chkflgInvoiceAlreadyGenerated == 1) {
				TmpInvoiceCodePDA = mDataSource.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

			} else {


				TmpInvoiceCodePDA = genTempInvoiceCodePDA();//mDataSource.fnGettblInvoiceCaption(storeID);
			}
		}
		CheckIfStoreExistInStoreProdcutPurchaseDetails= mDataSource.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
		CheckIfStoreExistInStoreProdcutInvoiceDetails= mDataSource.fnCheckIfStoreExistInStoreProdcutInvoiceDetails(storeID,TmpInvoiceCodePDA);
		strGlobalOrderID=TmpInvoiceCodePDA;
			/*if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1 || CheckIfStoreExistInStoreProdcutInvoiceDetails==1)
            {
                strGlobalOrderID=mDataSource.fngetOrderIDAganistStore(storeID,TmpInvoiceCodePDA);
            }
            else
            {
                strGlobalOrderID= genOutOrderID();
            }*/
	}


	private void getProductData() {
		// CategoryID,ProductID,ProductShortName,ProductRLP,Date/Qty)

		arrLstHmapPrdct= mDataSource.fetch_catgry_prdctsData(storeID,StoreCurrentStoreType);
		//hmapSchemeIDandDescr=mDataSource.fnSchemeIDandDescr();
		lastStockDate= mDataSource.fnGetLastStockDate(storeID);
		hmapProductIdLastStock= mDataSource.fnGetLastStockByDMS_Or_SFA(storeID);
		hampGetLastProductExecution= mDataSource.fnGetHampGetLastProductExecution(storeID);
		hmapProductIdLastOrder=mDataSource.fnGetLastOrderByDMS_Or_SFA(storeID);
		HashMap<String, String>  hmapTempProd= mDataSource.FetchLODqty(storeID);
		if(arrLstHmapPrdct.size()>0)
		{
			hmapCtgryPrdctDetail=arrLstHmapPrdct.get(0);
			hmapPrdctVolRatTax=arrLstHmapPrdct.get(1);
			hmapPrdctOdrQty=arrLstHmapPrdct.get(2);
			hmapPrdctSmpl=arrLstHmapPrdct.get(3);
			hmapPrdctFreeQty=arrLstHmapPrdct.get(4);
			hmapPrdctIdPrdctName=arrLstHmapPrdct.get(5);
			hmapPrdctIdPrdctDscnt=arrLstHmapPrdct.get(6);
			hmapProductRetailerMarginPercentage=arrLstHmapPrdct.get(7);
			hmapProductVatTaxPerventage=arrLstHmapPrdct.get(8);
			hmapProductMRP=arrLstHmapPrdct.get(9);
			hmapProductDiscountPercentageGive=arrLstHmapPrdct.get(10);
			hmapProductVolumePer=arrLstHmapPrdct.get(11);
			hmapProductTaxValue=arrLstHmapPrdct.get(12);
			hmapProductLODQty=arrLstHmapPrdct.get(13);
			hmapProductIdOrdrVal=arrLstHmapPrdct.get(14);

			hmapProductStandardRate=arrLstHmapPrdct.get(15);

			hmapProductStandardRateBeforeTax=arrLstHmapPrdct.get(16);

			hmapProductStandardTax=arrLstHmapPrdct.get(17);
			hmapProductIdStock=arrLstHmapPrdct.get(18);
			hmapProductflgPriceAva=arrLstHmapPrdct.get(19);
			hmapflgWholeSellApplicable=arrLstHmapPrdct.get(20);
			hmapPriceRangeWholeSellApplicable=arrLstHmapPrdct.get(21);
			hmapStandardRateWholeSale=arrLstHmapPrdct.get(22);
			hmapStandardRateBeforeTaxWholeSell=arrLstHmapPrdct.get(23);
			hmapStandardTaxWholeSale=arrLstHmapPrdct.get(24);

			hmapProductSelectedUOMId=arrLstHmapPrdct.get(25);
			hmapLineValBfrTxAftrDscnt=arrLstHmapPrdct.get(26);
			hmapLineValAftrTxAftrDscnt=arrLstHmapPrdct.get(27);
			hmapProductExtraOrder= arrLstHmapPrdct.get(28);


			Iterator it = hmapProductLODQty.entrySet().iterator();
			while (it.hasNext())
			{
				Entry pair = (Entry)it.next();
				if(hmapTempProd.containsKey(pair.getKey().toString()))
				{
					hmapProductLODQty.put(pair.getKey().toString(), hmapTempProd.get(pair.getKey().toString()));
				}

			}

			Iterator it11 = hmapPrdctIdPrdctName.entrySet().iterator();
			int pSize=hmapPrdctIdPrdctName.size();
			products=new String[pSize];
			int cntPsize=0;
			while (it11.hasNext())
			{
				Entry pair = (Entry)it11.next();

				products[cntPsize]=pair.getValue().toString();
				cntPsize++;

			}


		}

		hmapPrdctOdrQty= mDataSource.fnGetProductPurchaseList(storeID,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
		if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.size()>0)
		{
			for(Entry<String,String> entry:hmapPrdctOdrQty.entrySet())
			{
				if(Integer.parseInt(entry.getValue())>0)
				{
					prdctModelArrayList.setPrdctQty(entry.getKey(),entry.getValue());
				}

			}

		}

	}

	private void getCutOffDetailsForProduct()
	{
		PriceApplyDiscountLevelType= mDataSource.fnGettblPriceApplyDiscountLevelType(storeID);
		if(PriceApplyDiscountLevelType==0) {


		}
		else
		{
			cutoffvalue= mDataSource.fnGettblPriceApplycutoffvalue(storeID);
		}
	}
	private void getCategoryDetail() {

		hmapctgry_details= mDataSource.fetch_Category_List();

		int index=0;
		if(hmapctgry_details!=null)
		{
			categoryNames=new String[hmapctgry_details.size()];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
			Set set2 = map.entrySet();
			Iterator iterator = set2.iterator();
			while(iterator.hasNext()) {
				Entry me2 = (Entry)iterator.next();
				categoryNames[index]=me2.getKey().toString();
				index=index+1;
			}
		}


	}





	@Override
	public void onClick(View v) {


		String ProductIdOnClickedControl=v.getTag().toString().split(Pattern.quote("_"))[1];
		if(hmapProductRelatedSchemesList.size()>0)
		{
			if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl))
			{

				fnUpdateSchemeNameOnScehmeControl(ProductIdOnClickedControl);
			}
			else
			{
				//SchemeNameOnScehmeControl="No Scheme Applicable";
				txtVw_schemeApld.setText("No Scheme Applicable");
				txtVw_schemeApld.setTag("0");
			}
		}
		else
		{
			//SchemeNameOnScehmeControl="No Scheme Applicable";
			txtVw_schemeApld.setText("No Scheme Applicable");
			txtVw_schemeApld.setTag("0");
		}

		if(v instanceof EditText)
		{
			System.out.println("Abhinav Edit Clkd");
			edtBox=(EditText) v;
			if(v.getId()==R.id.et_OrderQty)
			{
				mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
				mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

				edtBox.setHint("");
				viewCurrentBoxValue=edtBox.getText().toString().trim();


			}

			if(v.getId()==R.id.et_SampleQTY)
			{
				mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
				mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

				edtBox.setHint("");



			}
			if(v.getId()==R.id.et_Stock)
			{
				mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
				mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

				edtBox.setHint("");



			}
		}
	}


	private void setInvoiceTableView() {

		LayoutInflater inflater = getLayoutInflater();
		View row123 = null;//(View)inflater.inflate(R.layout.activity_detail_scheme, ll_scheme_detail , false);
		if(CommonInfo.flgDrctslsIndrctSls==1)
		{
			row123 = (View)inflater.inflate(R.layout.activity_detail_scheme, ll_scheme_detail , false);
		}
		if(CommonInfo.flgDrctslsIndrctSls==2)
		{
			row123 = (View)inflater.inflate(R.layout.activity_detail_order_scheme, ll_scheme_detail , false);
		}
		decimalFormat.applyPattern(pattern);
		//tvCurrentProdName = (TextView) findViewById(R.id.textView1_schemeVAL1111);

		//tv_NetInvAfterDiscount

		if(row123!=null)
		{
			tvCredAmtVAL =  (TextView) row123.findViewById(R.id.textView1_CredAmtVAL);
			tvINafterCredVAL =  (TextView) row123.findViewById(R.id.textView1_INafterCredVAL);
			textView1_CredAmtVAL_new = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL_new);

			tvTotalAmtCollected=(TextView) row123.findViewById(R.id.tvTotalAmtCollected);
			tv_NetInvValue = (TextView)row123.findViewById(R.id.tv_NetInvValue);
			tvTAmt = (TextView)row123.findViewById(R.id.textView1_v2);
			tvDis = (TextView)row123.findViewById(R.id.textView1_v3);
			tv_GrossInvVal = (TextView)row123.findViewById(R.id.tv_GrossInvVal);
			tvFtotal = (TextView)row123.findViewById(R.id.textView1_v5);
			tvAddDisc =  (TextView)row123.findViewById(R.id.textView1_AdditionalDiscountVAL);
			tv_NetInvAfterDiscount =  (TextView)row123.findViewById(R.id.tv_NetInvAfterDiscount);

			tvAmtPrevDueVAL =  (TextView)row123.findViewById(R.id.tvAmtPrevDueVAL);
			tvAmtOutstandingVAL =  (TextView)row123.findViewById(R.id.tvAmtOutstandingVAL);
			etAmtCollVAL = (EditText)row123.findViewById(R.id.etAmtCollVAL);

			tvNoOfCouponValue = (EditText)row123.findViewById(R.id.tvNoOfCouponValue);
			txttvCouponAmountValue = (EditText)row123.findViewById(R.id.tvCouponAmountValue);

			tvPreAmtOutstandingVALNew=(TextView)row123.findViewById(R.id.tvPreAmtOutstandingVALNew);
			ll_scheme_detail.addView(row123);


			Double outstandingvalue= mDataSource.fnGetStoretblLastOutstanding(storeID);
			tvPreAmtOutstandingVALNew.setText(""+String.format("%.2f", outstandingvalue));

			Double TotalAmtCollected= mDataSource.fnTotCollectionAmtAgainstStore(storeID,TmpInvoiceCodePDA,StoreVisitCode);
			tvTotalAmtCollected.setText(""+TotalAmtCollected);

			if(CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartOutstandingDetails")) {
				if (CommonInfo.hmapAppMasterFlags.get("flgVisitStartOutstandingDetails") == 0) {
					TableRow table_trPreviousOutStanding = (TableRow)findViewById(R.id.table_trPreviousOutStanding);
					table_trPreviousOutStanding.setVisibility(View.GONE);
				}
			}
		}



	}





	public void fnUpdateSchemeNameOnScehmeControl(String ProductIdOnClickedControl)
	{
		txtVw_schemeApld.setText("");
		txtVw_schemeApld.setTag("0");

		txtVw_schemeApld.setVisibility(View.GONE);
	}


	public void fnSaveFilledDataToDatabase(int valBtnClickedFrom)
	{



		if(valBtnClickedFrom==3)//Clicked By Btn Submitt
		{
			//Send Data for Sync

			// Changes By Sunil
			AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductInvoiceReview.this);
			alertDialogSubmitConfirm.setTitle(getText(R.string.genTermNoDataConnection));
			alertDialogSubmitConfirm.setMessage(getText(R.string.submitConfirmAlert));
			alertDialogSubmitConfirm.setCancelable(false);

			alertDialogSubmitConfirm.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which)
				{
					butClickForGPS=3;
					//mDataSource.open();
					dialog.dismiss();
					if ((mDataSource.PrevLocChk(storeID.trim(),StoreVisitCode)) )
					{
						//mDataSource.close();

						FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
						task.execute();
					}
					else
					{
						//mDataSource.close();

								/* pm = (PowerManager) getSystemService(POWER_SERVICE);
								   wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
							                | PowerManager.ACQUIRE_CAUSES_WAKEUP
							                | PowerManager.ON_AFTER_RELEASE, "INFO");
							        wl.acquire();*/


					/*	pDialog2STANDBY=ProgressDialog.show(ProductInvoiceReview.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
						pDialog2STANDBY.setIndeterminate(true);

						pDialog2STANDBY.setCancelable(false);
						pDialog2STANDBY.show();

						if(isGooglePlayServicesAvailable()) {
							createLocationRequest();

							mGoogleApiClient = new GoogleApiClient.Builder(ProductInvoiceReview.this)
									.addApi(LocationServices.API)
									.addConnectionCallbacks(ProductInvoiceReview.this)
									.addOnConnectionFailedListener(ProductInvoiceReview.this)
									.build();
							mGoogleApiClient.connect();
						}
						countDownTimer2 = new CoundownClass2(startTime, interval);
						countDownTimer2.start();*/

						LocationRetreivingGlobal locationRetreivingGlobal = new LocationRetreivingGlobal();
						locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(ProductInvoiceReview.this, true, true, 100, 0);


								/* LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
								 llaaa.locationRetrievingAndDistanceCalculating(ProductInvoiceReview.this,false,20);*/

					}


				}
			});

			alertDialogSubmitConfirm.setNegativeButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

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

		else if(valBtnClickedFrom==4)//Next Button Click For Collection
		{
					/*butClickForGPS=8;
					//mDataSource.open();
					if ((mDataSource.PrevLocChk(storeID.trim(),StoreVisitCode)) )
					{
						//mDataSource.close();*/
			Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);   //
			AmtCollectIntent.putExtra("storeID", storeID);
			AmtCollectIntent.putExtra("imei", imei);
			AmtCollectIntent.putExtra("userdate", date);
			AmtCollectIntent.putExtra("pickerDate", pickerDate);
			AmtCollectIntent.putExtra("SN", SN);
			AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
			AmtCollectIntent.putExtra("FromPlace", "1");
			AmtCollectIntent.putExtra("flgOrderType", flgOrderType);

			startActivity(AmtCollectIntent);
			finish();



		}
	}



	public void TransactionTableDataDeleteAndSaving(int Outstat)
	{


		mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,strGlobalOrderID,TmpInvoiceCodePDA);

		int childcount =hmapPrdctIdPrdctName.size();// hmapPrdctIdPrdctNameVisible.size();

		for (Entry<String, String> entry:hmapPrdctIdPrdctName.entrySet() )
		{
			//  View vRow = ll_prdct_detal.getChildAt(index);

			int PCateId=Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
			String PName =entry.getValue();

			PName=PName.replaceAll("&","-");

			String ProductID=entry.getKey();
			String ProductStock =hmapProductIdStock.get(ProductID);
			int ProductExtraOrder=Integer.parseInt(hmapProductExtraOrder.get(ProductID));
			double TaxRate=0.00;
			double TaxValue=0.00;
			if(ProductStock.equals(""))
			{
				ProductStock="0";
			}
			String SampleQTY =hmapPrdctSmpl.get(ProductID);
			if(SampleQTY.equals(""))
			{
				SampleQTY="0";
			}
			String OrderQTY =hmapPrdctOdrQty.get(ProductID);

			if(OrderQTY.equals(""))
			{
				OrderQTY="0";

			}
			String OrderValue="0";
			if(Integer.parseInt(OrderQTY)>0)
			{
				OrderValue =hmapProductIdOrdrVal.get(ProductID);// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
				if(OrderValue.equals(""))
				{
					OrderValue="0";
				}
			}

			String OrderFreeQty =hmapPrdctFreeQty.get(ProductID);
			if(OrderFreeQty.equals(""))
			{
				OrderFreeQty="0";
			}

			String OrderDisVal= "0";
			if(Integer.parseInt(OrderQTY)>0) {
				// hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
				OrderDisVal= hmapPrdctIdPrdctDscnt.get(ProductID);
				if (OrderDisVal.equals("0.00")) {
					OrderDisVal = "0";
				}
			}
			String PRate="0.00";
			int flgIsQuoteRateApplied=0;
			if(PriceApplyDiscountLevelType==0) {
				PRate=hmapProductStandardRate.get(ProductID);
				TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
				TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));

				if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}
			}
			if(PriceApplyDiscountLevelType==1) {
				if(flgAllTotalOrderValueCrossesCutOff==true)
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
				else
				{
					PRate=hmapProductStandardRate.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			if(PriceApplyDiscountLevelType==2) {
				if(Double.parseDouble(OrderValue)>=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID)))
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}

			if(Integer.valueOf(OrderFreeQty)>0 || Integer.valueOf(SampleQTY)>0 || Integer.valueOf(OrderQTY)>0 || Integer.valueOf(OrderValue)>0 || Integer.valueOf(OrderDisVal)>0 || Integer.valueOf(ProductStock)>0 || ProductExtraOrder>0)
			{
				//mDataSource.open();
				StoreCatNodeId= mDataSource.fnGetStoreCatNodeId(storeID);
				int flgRuleTaxVal=1;
				mDataSource.fnsaveStoreTempOrderEntryDetails(TmpInvoiceCodePDA,storeID,""+PCateId,ProductID,Double.parseDouble(PRate),TaxRate,flgRuleTaxVal,Integer.parseInt(OrderQTY),Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)),Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,TaxValue,strGlobalOrderID,flgIsQuoteRateApplied,PriceApplyDiscountLevelType,distID,Outstat,ProductExtraOrder);
				//mDataSource.close();
			}



		}
		flgAllTotalOrderValueCrossesCutOff=false;
	}



	public void InvoiceTableDataDeleteAndSaving(int Outstat,int flgTransferStatus)
	{
		Double INval;

		if(!tv_GrossInvVal.getText().toString().isEmpty()){


			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}


		mDataSource.deleteOldStoreInvoice(storeID,strGlobalOrderID,TmpInvoiceCodePDA);

		Double TBtaxDis;
		Double TAmt;
		Double Dis;


		Double AddDis;
		Double InvAfterDis;

		Double INvalCreditAmt;
		Double INvalInvoiceAfterCreditAmt;

		Double INvalInvoiceOrginal=0.00;


		int Ftotal;
		String mrouteID=mDataSource.fnGetRouteIDAgainstStoreID(storeID);
		String mrouteType=mDataSource.FetchRouteType(mrouteID);



		if(!tv_NetInvValue.getText().toString().isEmpty()){
			TBtaxDis = Double.parseDouble(tv_NetInvValue.getText().toString().trim());
		}
		else{
			TBtaxDis = 0.00;
		}
		if(!tvTAmt.getText().toString().isEmpty()){
			TAmt = Double.parseDouble(tvTAmt.getText().toString().trim());
		}
		else{
			TAmt = 0.00;
		}
		if(!tvDis.getText().toString().isEmpty()){
			Dis = Double.parseDouble(tvDis.getText().toString().trim());
		}
		else{
			Dis = 0.00;
		}

		if(!tvFtotal.getText().toString().isEmpty()){
			Double FtotalValue=Double.parseDouble(tvFtotal.getText().toString().trim());
			Ftotal =FtotalValue.intValue();
		}
		else{
			Ftotal = 0;
		}

		if(!tv_NetInvAfterDiscount.getText().toString().isEmpty()){
			InvAfterDis = Double.parseDouble(tv_NetInvAfterDiscount.getText().toString().trim());
		}
		else{
			InvAfterDis = 0.00;
		}
		if(!tvAddDisc.getText().toString().isEmpty()){
			AddDis = Double.parseDouble(tvAddDisc.getText().toString().trim());
		}
		else{
			AddDis = 0.00;
		}


		Double AmtPrevDueVA=0.00;
		Double AmtCollVA=0.00;
		Double AmtOutstandingVAL=0.00;
		if(!tvAmtPrevDueVAL.getText().toString().isEmpty()){
			AmtPrevDueVA = Double.parseDouble(tvAmtPrevDueVAL.getText().toString().trim());
		}
		else{
			AmtPrevDueVA = 0.00;
		}
		if(!etAmtCollVAL.getText().toString().isEmpty()){
			AmtCollVA = Double.parseDouble(etAmtCollVAL.getText().toString().trim());
		}
		else{
			AmtCollVA = 0.00;
		}

		if(!tvAmtOutstandingVAL.getText().toString().isEmpty()){
			AmtOutstandingVAL = Double.parseDouble(tvAmtOutstandingVAL.getText().toString().trim());
		}
		else{
			AmtOutstandingVAL = 0.00;
		}

		int NoOfCouponValue=0;
		/*if(!txttvNoOfCouponValue.getText().toString().isEmpty()){
			NoOfCouponValue = Integer.parseInt(txttvNoOfCouponValue.getText().toString().trim());
		}
		else{
			NoOfCouponValue = 0;
		}
		*/
		Double TotalCoupunAmount=0.00;
		if(!txttvCouponAmountValue.getText().toString().isEmpty()){
			TotalCoupunAmount = Double.parseDouble(txttvCouponAmountValue.getText().toString().trim());
		}
		else{
			TotalCoupunAmount = 0.00;
		}

		int flgRuleTaxVal=1;
		int flgTransType=1;
		//mDataSource.open();
		//mDataSource.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,TmpInvoiceCodePDA,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);

		mDataSource.saveStoreTempInvoice(StoreVisitCode,TmpInvoiceCodePDA,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis,  NoOfCouponValue, TotalCoupunAmount,pickerDate,flgTransType,PriceApplyDiscountLevelType,flgRuleTaxVal,Outstat,flgTransferStatus,mrouteID,mrouteType);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
		//mDataSource.close();



	}

	public void SyncNow()
	{

		//mDataSource.open();
		String presentRoute= mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
		//mDataSource.close();


		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

		String newfullFileName=imei+"."+presentRoute+"."+ df.format(dateobj);




		try
		{

			File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

			if (!OrderXMLFolder.exists())
			{
				OrderXMLFolder.mkdirs();

			}

			String routeID= mDataSource.GetActiveRouteIDSunil();

			DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);



			Intent syncIntent = new Intent(ProductInvoiceReview.this, SyncMaster.class);
			//syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + newfullFileName + ".xml");
			syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");

			syncIntent.putExtra("OrigZipFileName", newfullFileName);
			syncIntent.putExtra("whereTo", "Regular");
			startActivity(syncIntent);


		} catch (IOException e) {

			e.printStackTrace();
		}

	}



	public String genOutOrderID()
	{
		//store ID generation <x>
		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String VisitStartTS = df.format(dateobj);
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

						/*TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tManager.getDeviceId();*/
		String IMEIid =  CommonInfo.imei.substring(9);
		//cxz = IMEIid +"-"+cxz;
		cxz = "OrdID" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();
		//System.out.println("cxz: "+cxz);

		return cxz;
		//-_
	}

	public void callBroadCast() {
		if (Build.VERSION.SDK_INT >= 14) {
			Log.e("-->", " >= 14");
			MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

				public void onScanCompleted(String path, Uri uri) {

				}
			});
		} else {
			Log.e("-->", " < 14");
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnected(Bundle arg0)
	{
		// TODO Auto-generated method stub

		startLocationUpdates();
	}

	/*	public void hideSoftKeyboard(View view){
			  InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}

		public void showSoftKeyboard(View view){
		    if(view.requestFocus()){
		        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
		    }

		}*/

	protected void startLocationUpdates()
	{
		PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

	}

	@Override
	public void onConnectionSuspended(int arg0)
	{
		// TODO Auto-generated method stub

	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);

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

	public void showAlertForEveryOne(String msg)
	{
		AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductInvoiceReview.this);
		alertDialogNoConn.setTitle(getText(R.string.genTermNoDataConnection));
		alertDialogNoConn.setMessage(msg);
		alertDialogNoConn.setCancelable(false);
		alertDialogNoConn.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				FusedLocationLatitude="0";
				FusedLocationLongitude="0";
				FusedLocationProvider="0";
				FusedLocationAccuracy="0";

				GPSLocationLatitude="0";
				GPSLocationLongitude="0";
				GPSLocationProvider="0";
				GPSLocationAccuracy="0";

				NetworkLocationLatitude="0";
				NetworkLocationLongitude="0";
				NetworkLocationProvider="0";
				NetworkLocationAccuracy="0";


				String GpsLat="0";
				String GpsLong="0";
				String GpsAccuracy="0";
				String GpsAddress="0";
				String NetwLat="0";
				String  NetwLong="0";
				String NetwAccuracy="0";
				String NetwAddress="0";
				String  FusedLat="0";
				String FusedLong="0";
				String FusedAccuracy="0";
				String FusedAddress="0";
				checkHighAccuracyLocationMode(ProductInvoiceReview.this);
				//mDataSource.open();
				mDataSource.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);


				//mDataSource.close();

				if(butClickForGPS==1)
				{
					butClickForGPS=0;


					if(!alertOpens)
					{
						progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

						new SaveData().execute("1");
					}


				}
				else  if(butClickForGPS==2)
				{
					butClickForGPS=0;



					if(!alertOpens)
					{
						progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);
						new SaveData().execute("2");
					}

				}
				else  if(butClickForGPS==3)
				{
					butClickForGPS=0;
					try
					{
						FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
						task.execute();
					}
					catch (Exception e) {
						// TODO Autouuid-generated catch block
						e.printStackTrace();
						//System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
					}
				}

				else  if(butClickForGPS==6)
				{
					butClickForGPS=0;



					if(!alertOpens)
					{
						progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

						new SaveData().execute("6");
					}

				}

			}
		});
		alertDialogNoConn.setIcon(R.drawable.info_ico);
		AlertDialog alert = alertDialogNoConn.create();
		alert.show();
	}

	@Override
	public void onLocationChanged(Location args0) {
		// TODO Auto-generated method stub
		mCurrentLocation = args0;
		mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
		updateUI();

	}

	public SpannableStringBuilder textWithMandatory(String text_Value)

	{
		String simple = text_Value;
		String colored = "**";
		SpannableStringBuilder builder = new SpannableStringBuilder();

		builder.append(simple);
		int start = builder.length();
		builder.append(colored);
		int end = builder.length();

		builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		//text.setText(builder);

		return builder;

	}

	public Double getAccAsignValue(int schSlabSubBucketType,int chkflgProDataCalculation,double BenSubBucketValue,double schSlabSubBucketValue,int totalProductQty,int totalProductLine,double totalProductValue,double totalProductVol,double totalInvoice,double per,String productIdForFree,boolean isLastPrdct)
	{
		Double accAsignVal=0.0;
		//1. Product Quantity
		//5. Product Volume
		//2. Invoice Value
		//3. Product Lines
		//4. Product Value

		//Product Quantity
		if(schSlabSubBucketType==1)
		{
			if(chkflgProDataCalculation==1)
			{

				if(isLastPrdct)
				{
					accAsignVal=BenSubBucketValue*(totalProductQty/schSlabSubBucketValue);
					accAsignVal=  Double.valueOf(Math.abs(accAsignVal.intValue())) ;
				}
				//accAsignVal=Double.valueOf(Double.valueOf(hmapPrdctOdrQty.get(productIdForFree))*accAsignVal/totalProductQty);

			}
			else
			{
				if(isLastPrdct)
				{
					accAsignVal=BenSubBucketValue;
				}

			}

		}
		//Invoice Value
		else if(schSlabSubBucketType==2)
		{

			if(chkflgProDataCalculation==1)
			{
				accAsignVal=BenSubBucketValue*(totalInvoice/schSlabSubBucketValue);

			}
			else
			{
				accAsignVal=BenSubBucketValue;
			}
		}
		//Product Lines
		else if(schSlabSubBucketType==3)
		{
			if(chkflgProDataCalculation==1)
			{
				accAsignVal=BenSubBucketValue*(totalProductLine/schSlabSubBucketValue);
				accAsignVal=Double.valueOf(Double.valueOf(productFullFilledSlabGlobal.size())*accAsignVal/totalProductLine);
			}
			else
			{
				accAsignVal=BenSubBucketValue;
			}
		}
		//Product Value
		else if(schSlabSubBucketType==4)
		{
			if(chkflgProDataCalculation==1)
			{
				accAsignVal=BenSubBucketValue*(totalProductValue/schSlabSubBucketValue);

				//product value

				Double prodRate= Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[1]);
				Double oderRateOfCurrentMapedProduct=prodRate *Double.valueOf(hmapPrdctOdrQty.get(productIdForFree)) ;
				//oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
				double singleProductVal=oderRateOfCurrentMapedProduct;

				if(per>0.0)
				{
					singleProductVal=singleProductVal/per;
				}
				accAsignVal=Double.valueOf(Double.valueOf(singleProductVal)*(accAsignVal/totalProductValue));


			}
			else
			{

				accAsignVal=BenSubBucketValue;
			}

		}
		// Product Volume
		else if(schSlabSubBucketType==5)
		{
			if(chkflgProDataCalculation==1)
			{

				accAsignVal=BenSubBucketValue*(totalProductVol/(schSlabSubBucketValue*1000));
				// product volume
				Double prodVolume= Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[0]);
				Double oderVolumeOfCurrentMapedProduct=prodVolume * Double.valueOf(hmapPrdctOdrQty.get(productIdForFree)) ;
				Double singleProductVol=oderVolumeOfCurrentMapedProduct;
				if(per>0.0)
				{
					singleProductVol=singleProductVol/per;
				}
				accAsignVal=Double.valueOf(Double.valueOf(singleProductVol)*accAsignVal/totalProductVol);

			}
			else
			{
				accAsignVal=BenSubBucketValue;
			}
		}
		return accAsignVal;
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
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("GPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		}
	}

	public void UpdateLocationAndProductAllData()
	{

		//mDataSource.close();

		if(butClickForGPS==1)
		{
			butClickForGPS=0;


			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

				new SaveData().execute("1");
			}


		}
		else  if(butClickForGPS==2)
		{
			butClickForGPS=0;



			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);

				new SaveData().execute("2");
			}

		}
		else  if(butClickForGPS==3)
		{
			butClickForGPS=0;
			try
			{
				FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
				task.execute();
			}
			catch (Exception e) {
				// TODO Autouuid-generated catch block
				e.printStackTrace();
				//System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
			}
		}

		else  if(butClickForGPS==6)
		{
			butClickForGPS=0;



			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

				new SaveData().execute("6");
			}

		}
		else  if(butClickForGPS==8)
		{
			butClickForGPS=0;



			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

				new SaveData().execute("1~7");
			}

		}
	}

	public void checkHighAccuracyLocationMode(Context context) {
		int locationMode = 0;
		String locationProviders;

		flgLocationServicesOnOffOrderReview=0;
		flgGPSOnOffOrderReview=0;
		flgNetworkOnOffOrderReview=0;
		flgFusedOnOffOrderReview=0;
		flgInternetOnOffWhileLocationTrackingOrderReview=0;

		if(isGooglePlayServicesAvailable())
		{
			flgFusedOnOffOrderReview=1;
		}
		if(isOnline())
		{
			flgInternetOnOffWhileLocationTrackingOrderReview=1;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			//Equal or higher than API 19/KitKat
			try {
				locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
				if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY){
					flgLocationServicesOnOffOrderReview=1;
					flgGPSOnOffOrderReview=1;
					flgNetworkOnOffOrderReview=1;
					//flgFusedOnOff=1;
				}
				if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING){
					flgLocationServicesOnOffOrderReview=1;
					flgGPSOnOffOrderReview=0;
					flgNetworkOnOffOrderReview=1;
					// flgFusedOnOff=1;
				}
				if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY){
					flgLocationServicesOnOffOrderReview=1;
					flgGPSOnOffOrderReview=1;
					flgNetworkOnOffOrderReview=0;
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

	public void alertForOrderExceedStock(final String productOIDClkd, final EditText edOrderCurrent, final EditText edOrderCurrentLast, final int flagClkdButton)
	{

		final Dialog listDialog = new Dialog(ProductInvoiceReview.this);
		listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		listDialog.setContentView(R.layout.custom_stock_alert);
		listDialog.setCanceledOnTouchOutside(false);
		listDialog.setCancelable(false);
		WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
		parms.gravity =Gravity.CENTER;
		parms.width=WindowManager.LayoutParams.MATCH_PARENT;
		//there are a lot of settings, for dialog, check them all out!
		parms.dimAmount = (float) 0.5;


		final int avilabQty=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

		TextView order_detail=(TextView) listDialog.findViewById(R.id.order_detail);

		order_detail.setText(hmapPrdctIdPrdctName.get(ProductIdOnClickedEdit)+"\n\n"+ProductInvoiceReview.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductInvoiceReview.this.getResources().getString(R.string.RqrdQty)+hmapPrdctOdrQty.get(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));


		Button btn_done= (Button) listDialog.findViewById(R.id.btn_done);
		final EditText editText_prdctQty= (EditText) listDialog.findViewById(R.id.editText_prdctQty);
		editText_prdctQty.setText(""+avilabQty);
		editText_prdctQty.setVisibility(View.GONE);
		ed_extraQty= (EditText) listDialog.findViewById(R.id.ed_extraQty);
		ed_extraQty.setVisibility(View.GONE);


		btn_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listDialog.dismiss();
				hmapPrdctOdrQty.put(productOIDClkd,"0");
				hmapProductExtraOrder.put(productOIDClkd,"0");
				edOrderCurrentLast.setText("");

				edOrderCurrent.clearFocus();
				edOrderCurrentLast.requestFocus();
				//alertForOrderExceedStock(productOIDClkd,edOrderCurrent,edOrderCurrentLast,-1);

			}
		});




		//now that the dialog is set up, it's time to show it
		listDialog.show();

	}
	public void alertForRetailerCreditLimit(final int btnClkd)
	{
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductInvoiceReview.this);
		alertDialogSubmitConfirm.setTitle(getText(R.string.genTermNoDataConnection));
		alertDialogSubmitConfirm.setMessage(getText(R.string.credit_retailer_balance));
		alertDialogSubmitConfirm.setCancelable(false);

		alertDialogSubmitConfirm.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				mDataSource.updateFlgCrediBal(storeID,1);

				nextStepAfterRetailerCreditBal(btnClkd);


			}
		});

		alertDialogSubmitConfirm.setNegativeButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method
				mDataSource.updateFlgCrediBal(storeID,0);
				dialog.dismiss();
				nextStepAfterRetailerCreditBal(btnClkd);



			}
		});

		alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSubmitConfirm.create();

		alert.show();


	}

	public void nextStepAfterRetailerCreditBal(int btnClkd)
	{



		if(btnClkd==0) // save
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"Save Button Click on Product List"+StartClickTimeFinal);
			File file = new File("/sdcard/"+ CommonInfo.TextFileFolder+"/"+fileName);

			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Save Button Click on Product List"+StartClickTimeFinal;


			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				//mDataSource.open();
				mDataSource.savetblMessageTextFileContainer(fileName,0);
				//mDataSource.close();


			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}




			butClickForGPS=1;
			flagClkdButton=5;



			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

			new SaveData().execute("1");

		}

		else if(btnClkd==1) // btn save&Exit clkd
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
			File file = new File("/sdcard/"+ CommonInfo.TextFileFolder+"/"+fileName);

			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal;


			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				//mDataSource.open();
				mDataSource.savetblMessageTextFileContainer(fileName,0);
				//mDataSource.close();


			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			butClickForGPS=2;
			flagClkdButton=2;
			//mDataSource.open();
			mDataSource.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2,StoreVisitCode);
			if ((mDataSource.PrevLocChk(storeID.trim(),StoreVisitCode)) )
			{
				//mDataSource.close();



				if(!alertOpens)
				{
					progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);
					locationManager=(LocationManager) ProductInvoiceReview.this.getSystemService(LOCATION_SERVICE);

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
						new SaveData().execute("2");
					}
				}
			}
			else
			{
				//mDataSource.close();

				/*pDialog2STANDBY=ProgressDialog.show(ProductInvoiceReview.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
				pDialog2STANDBY.setIndeterminate(true);

				pDialog2STANDBY.setCancelable(false);
				pDialog2STANDBY.show();

				if(isGooglePlayServicesAvailable()) {
					createLocationRequest();

					mGoogleApiClient = new GoogleApiClient.Builder(ProductInvoiceReview.this)
							.addApi(LocationServices.API)
							.addConnectionCallbacks(ProductInvoiceReview.this)
							.addOnConnectionFailedListener(ProductInvoiceReview.this)
							.build();
					mGoogleApiClient.connect();
				}
				//startService(new Intent(DynamicActivity.this, AppLocationService.class));
				countDownTimer2 = new CoundownClass2(startTime, interval);
				countDownTimer2.start();*/
				LocationRetreivingGlobal locationRetreivingGlobal = new LocationRetreivingGlobal();
				locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(ProductInvoiceReview.this, true, true, 100, 0);

			}

		}

		else if(btnClkd==2) // btn submit clkd
		{

			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);

			String fileName=imei+"_"+storeID;

			File file = new File("/sdcard/"+ CommonInfo.TextFileFolder+"/"+fileName);
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			CommonInfo.fileContent= CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Submit Button Click on Product List"+StartClickTimeFinal;

			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				//mDataSource.open();
				mDataSource.savetblMessageTextFileContainer(fileName,0);
				//mDataSource.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			flagClkdButton=3;
			//mDataSource.open();
			mDataSource.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2,StoreVisitCode);
			//mDataSource.close();


			if(!alertOpens)
			{
				boolean isGPSEnabled2 = false;
				boolean isNetworkEnabled2=false;
				isGPSEnabled2 = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNetworkEnabled2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if(!isGPSEnabled2)
				{
					isGPSEnabled2 = false;
				}
				if(!isNetworkEnabled2)
				{
					isNetworkEnabled2 = false;
				}
				if(!isGPSEnabled2 && !isNetworkEnabled2)
				{
					try
					{
						showSettingsAlert();
					}
					catch(Exception e)
					{

					}

					isGPSEnabled2 = false;
					isNetworkEnabled2=false;
				}
				else{
					fnSaveFilledDataToDatabase(3);
				}
			}
		}
		else if(btnClkd==3) // btn return clkd
		{
			boolean isStockAvilable=false;
			for (Entry<String, String> entry : hmapProductIdStock.entrySet())
			{
				if(!entry.getValue().equals("0") )
				{
					isStockAvilable=true;
					break;
				}

			}
			if(isStockAvilable)
			{
				flagClkdButton=1;


				if(!alertOpens)
				{
					progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.genTermInformation);
					new SaveData().execute("1~3");
				}
			}

			else
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

				// Setting Dialog Title
				alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialog.setIcon(R.drawable.error_info_ico);
				alertDialog.setCancelable(false);
				// Setting Dialog Message
				alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.NoStocks));

				// On pressing Settings button
				alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});

				// Showing Alert Message
				alertDialog.show();

			}

		}

		else if(btnClkd==4) // back button clkd
		{
			flagClkdButton=4;


			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSave);

				new SaveData().execute("1~2");



			}
		}

		else if(btnClkd==5)// Collection Amount
		{
			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1~5");
		}
		else if(btnClkd==6)    //Delivery_Details_Activity
		{
			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1~6");
		}

		else if(btnClkd==7) // btn submit clkd
		{
			fnSaveFilledDataToDatabase(4);

		}

	}

	public String genTempInvoiceCodePDA()
	{
		//store ID generation <x>
		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String VisitStartTS = df.format(dateobj);
		String cxz;
		cxz = UUID.randomUUID().toString();
		/*cxz.split("^([^-]*,[^-]*,[^-]*,[^-]*),(.*)$");*/

		StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

		String val1 = tokens.nextToken().trim();
		String val2 = tokens.nextToken().trim();
		String val3 = tokens.nextToken().trim();
		String val4 = tokens.nextToken().trim();
		cxz = tokens.nextToken().trim();



						/*TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tManager.getDeviceId();*/
		String IMEIid =  CommonInfo.imei.substring(9);
		//cxz = IMEIid +"-"+cxz;
		cxz = "TmpInvoiceCodePDA" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


		return cxz;
		//-_
	}

	public void fnVisitFinalInvoiceDetailsSaving(int Outstat,String FinalInvoiceNumberGenerated)
	{


		int childcount =hmapPrdctIdPrdctName.size();

		for (Entry<String, String> entry:hmapPrdctIdPrdctName.entrySet() )
		{
			//  View vRow = ll_prdct_detal.getChildAt(index);

			int PCateId=Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
			String PName =entry.getValue();
			String ProductID=entry.getKey();
			String ProductStock =hmapProductIdStock.get(ProductID);
			double TaxRate=0.00;
			double TaxValue=0.00;
			if(ProductStock.equals(""))
			{
				ProductStock="0";
			}
			String SampleQTY =hmapPrdctSmpl.get(ProductID);
			if(SampleQTY.equals(""))
			{
				SampleQTY="0";
			}
			String OrderQTY =hmapPrdctOdrQty.get(ProductID);

			if(OrderQTY.equals(""))
			{
				OrderQTY="0";

			}
			String OrderValue="0";
			if(Integer.parseInt(OrderQTY)>0)
			{
				OrderValue =hmapProductIdOrdrVal.get(ProductID);// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
				if(OrderValue.equals(""))
				{
					OrderValue="0";
				}
			}

			String OrderFreeQty =hmapPrdctFreeQty.get(ProductID);
			if(OrderFreeQty.equals(""))
			{
				OrderFreeQty="0";
			}

			String OrderDisVal= "0";
			if(Integer.parseInt(OrderQTY)>0) {
				// hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
				OrderDisVal= hmapPrdctIdPrdctDscnt.get(ProductID);
				if (OrderDisVal.equals("0.00")) {
					OrderDisVal = "0";
				}
			}
			String PRate="0.00";
			int flgIsQuoteRateApplied=0;
			if(PriceApplyDiscountLevelType==0) {
				PRate=hmapProductStandardRate.get(ProductID);
				TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
				TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));

				if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}
			}
			if(PriceApplyDiscountLevelType==1) {
				if(flgAllTotalOrderValueCrossesCutOff==true)
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
				else
				{
					PRate=hmapProductStandardRate.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			if(PriceApplyDiscountLevelType==2) {
				if(Double.parseDouble(OrderValue)>=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID)))
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}

			if(Integer.valueOf(OrderFreeQty)>0 || Integer.valueOf(SampleQTY)>0 || Integer.valueOf(OrderQTY)>0 || Integer.valueOf(OrderValue)>0 || Integer.valueOf(OrderDisVal)>0 || Integer.valueOf(ProductStock)>0)
			{
				//mDataSource.open();
				StoreCatNodeId= mDataSource.fnGetStoreCatNodeId(storeID);
				int flgRuleTaxVal=1;
				mDataSource.saveStoreFinalInvoiceDetails(TmpInvoiceCodePDA,storeID,""+PCateId,ProductID,Double.parseDouble(PRate),TaxRate,flgRuleTaxVal,Integer.parseInt(OrderQTY),Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)),Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,TaxValue,strGlobalOrderID,flgIsQuoteRateApplied,PriceApplyDiscountLevelType,distID,Outstat,FinalInvoiceNumberGenerated);
				//mDataSource.close();
			}



		}
		flgAllTotalOrderValueCrossesCutOff=false;
	}

	public void fnVisitFinalInvoiceHeaderSaving(int Outstat,String FinalInvoiceNumberGenerated)
	{
		Double INval;

		if(!tv_GrossInvVal.getText().toString().isEmpty()){


			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}



		Double TBtaxDis;
		Double TAmt;
		Double Dis;


		Double AddDis;
		Double InvAfterDis;

		Double INvalCreditAmt;
		Double INvalInvoiceAfterCreditAmt;

		Double INvalInvoiceOrginal=0.00;


		int Ftotal;
		String routeID=mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
		String routeType=mDataSource.FetchRouteType(routeID);




		if(!tv_NetInvValue.getText().toString().isEmpty()){
			TBtaxDis = Double.parseDouble(tv_NetInvValue.getText().toString().trim());
		}
		else{
			TBtaxDis = 0.00;
		}
		if(!tvTAmt.getText().toString().isEmpty()){
			TAmt = Double.parseDouble(tvTAmt.getText().toString().trim());
		}
		else{
			TAmt = 0.00;
		}
		if(!tvDis.getText().toString().isEmpty()){
			Dis = Double.parseDouble(tvDis.getText().toString().trim());
		}
		else{
			Dis = 0.00;
		}

		if(!tvFtotal.getText().toString().isEmpty()){
			Double FtotalValue=Double.parseDouble(tvFtotal.getText().toString().trim());
			Ftotal =FtotalValue.intValue();
		}
		else{
			Ftotal = 0;
		}

		if(!tv_NetInvAfterDiscount.getText().toString().isEmpty()){
			InvAfterDis = Double.parseDouble(tv_NetInvAfterDiscount.getText().toString().trim());
		}
		else{
			InvAfterDis = 0.00;
		}
		if(!tvAddDisc.getText().toString().isEmpty()){
			AddDis = Double.parseDouble(tvAddDisc.getText().toString().trim());
		}
		else{
			AddDis = 0.00;
		}


		Double AmtPrevDueVA=0.00;
		Double AmtCollVA=0.00;
		Double AmtOutstandingVAL=0.00;
		if(!tvAmtPrevDueVAL.getText().toString().isEmpty()){
			AmtPrevDueVA = Double.parseDouble(tvAmtPrevDueVAL.getText().toString().trim());
		}
		else{
			AmtPrevDueVA = 0.00;
		}
		if(!etAmtCollVAL.getText().toString().isEmpty()){
			AmtCollVA = Double.parseDouble(etAmtCollVAL.getText().toString().trim());
		}
		else{
			AmtCollVA = 0.00;
		}

		if(!tvAmtOutstandingVAL.getText().toString().isEmpty()){
			AmtOutstandingVAL = Double.parseDouble(tvAmtOutstandingVAL.getText().toString().trim());
		}
		else{
			AmtOutstandingVAL = 0.00;
		}

		int NoOfCouponValue=0;
		/*if(!txttvNoOfCouponValue.getText().toString().isEmpty()){
			NoOfCouponValue = Integer.parseInt(txttvNoOfCouponValue.getText().toString().trim());
		}
		else{
			NoOfCouponValue = 0;
		}
		*/
		Double TotalCoupunAmount=0.00;
		if(!txttvCouponAmountValue.getText().toString().isEmpty()){
			TotalCoupunAmount = Double.parseDouble(txttvCouponAmountValue.getText().toString().trim());
		}
		else{
			TotalCoupunAmount = 0.00;
		}

		int flgRuleTaxVal=1;
		int flgTransType=1;
		//mDataSource.open();
		//mDataSource.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,TmpInvoiceCodePDA,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);

		mDataSource.fnsaveStoreFinalInvoiceSummaryEntry(StoreVisitCode,TmpInvoiceCodePDA,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis,  NoOfCouponValue, TotalCoupunAmount,pickerDate,flgTransType,PriceApplyDiscountLevelType,flgRuleTaxVal,Outstat,FinalInvoiceNumberGenerated,routeID,routeType);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
		//mDataSource.close();



	}

	@Override
	public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
		this.FusedLocationLatitude=FusedLat;
		this.FusedLocationLongitude=FusedLong;
		this.FusedLocationProvider="Fused";
		this.FusedLocationAccuracy=FusedAccuracy;
		this.GPSLocationLatitude=GpsLat;
		this.GPSLocationLongitude=GpsLong;
		this.GPSLocationProvider="GPS";
		this.GPSLocationAccuracy=GpsAccuracy;
		this.NetworkLocationLatitude=NetwLat;
		this.NetworkLocationLongitude=NetwLong;
		this.NetworkLocationProvider="Network";
		this.NetworkLocationAccuracy=NetwAccuracy;

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



		checkHighAccuracyLocationMode(ProductInvoiceReview.this);
		//mDataSource.open();
		mDataSource.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);
		mDataSource.saveLatLngToTxtFile(storeID,StoreVisitCode,fnLati, fnLongi, "" +fnAccuracy, fnAccurateProvider, GPSLocationLatitude, GPSLocationLongitude, GPSLocationAccuracy, NetworkLocationLatitude, NetworkLocationLongitude, NetworkLocationAccuracy, FusedLocationLatitude, FusedLocationLongitude, FusedLocationAccuracy, 1, "0", fnaddress, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt
				, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);

		if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
		{

			fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
			UpdateLocationAndProductAllData();
		}
		else
		{
			countSubmitClicked++;
			if(countSubmitClicked==1)
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

				// Setting Dialog Title
				alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialog.setIcon(R.drawable.error_info_ico);
				alertDialog.setCancelable(false);
				// Setting Dialog Message
				alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertSameLoc));

				// On pressing Settings button
				alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						countSubmitClicked++;
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

				// Showing Alert Message
				alertDialog.show();



			}
			else
			{
				UpdateLocationAndProductAllData();
			}


		}
	}

	@Override
	public void fcsLstCld(boolean hasFocus, EditText editText) {

	}

	private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


		ProgressDialog pDialogGetStores;
		public FullSyncDataNow(ProductInvoiceReview activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.SubmittingInvoiceOrderDetails));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();


		}

		@Override

		protected Void doInBackground(Void... params) {

			int Outstat=1;
			flgTransferStatus=1;
			InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
			/*	TransactionTableDataDeleteAndSaving(Outstat);*/
			Double cntInvoceValue= mDataSource.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
			cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

			Outstat=3;
			long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = TimeUtils.getNetworkDateTime(ProductInvoiceReview.this,TimeUtils.DATE_TIME_FORMAT);

			if(Outstat==3) {
				if (cntInvoceValue > 0.0) {
					if(strFinalAllotedInvoiceIds.equals("NA"))
					{
						mDataSource.fnTransferDataFromTempToPermanent(storeID,StoreVisitCode,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls);

						int chkflgTransferStatus= mDataSource.fnCheckflgTransferStatus(storeID,StoreVisitCode,TmpInvoiceCodePDA);
						if(chkflgTransferStatus==2)
						{
							mDataSource.deleteOldStoreInvoice(storeID, strGlobalOrderID, TmpInvoiceCodePDA);
							mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID, strGlobalOrderID, TmpInvoiceCodePDA);
							mDataSource.UpdateStoreVisitMStrTable(storeID, 3, StoreVisitCode);
							mDataSource.fnupdatetblStoreLastDeliveryNoteNumber();
							mDataSource.UpdatetblStoreOrderVisitDayActivitySstat(storeID,3,StoreVisitCode,LastVisitDetails.TempStoreVisitCode);
							mDataSource.UpdateStoreEndVisittblStoreOrderVisitDayActivity(storeID,StampEndsTime,LastVisitDetails.TempStoreVisitCode);



						}
						else
						{
							mDataSource.deleteMasterTblFromParmanentInvoiceTables(storeID,TmpInvoiceCodePDA);
						}

					}

				}
			}

			mDataSource.UpdateStoreVisitWiseTables(storeID, 3,StoreVisitCode,TmpInvoiceCodePDA,"NA");


			mDataSource.UpdateStoreEndVisit(storeID, StampEndsTime);
			mDataSource.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"3",strGlobalOrderID,TmpInvoiceCodePDA);
			mDataSource.UpdateStoreStoreReturnDetail(storeID.trim(),"3",strGlobalOrderID,TmpInvoiceCodePDA);
			mDataSource.UpdateStoreFlag(storeID.trim(), 3);
			mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3,strGlobalOrderID,TmpInvoiceCodePDA);

			Double CollectionAmtAgainstStore= mDataSource.fnTotCollectionAmtAgainstStore(storeID.trim(),TmpInvoiceCodePDA,StoreVisitCode);

			mDataSource.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0,StoreVisitCode);
			if(mDataSource.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,TmpInvoiceCodePDA)==0)
			{
				String strDefaultPaymentStageForStore= mDataSource.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					//mDataSource.open();
					mDataSource. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"3",TmpInvoiceCodePDA);
					//mDataSource.close();
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
			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}
			showOrderSSubmissionAlert();

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
						dialog.dismiss();
						try
						{
							StoreSelection.flgChangeRouteOrDayEnd=0;
							String presentRoute = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

							Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
							mMyServiceIntent.putExtra("routeID", presentRoute);//
							mMyServiceIntent.putExtra("storeID", storeID);
							mMyServiceIntent.putExtra("whereTo", "Regular");//
							mMyServiceIntent.putExtra("StoreVisitCode", StoreVisitCode);//

							SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);




							Intent prevP2 = new Intent(ProductInvoiceReview.this, StoreSelection.class);
							//Location_Getting_Service.closeFlag = 0;
							prevP2.putExtra("imei", imei);
							prevP2.putExtra("userDate", date);
							prevP2.putExtra("pickerDate", pickerDate);
							prevP2.putExtra("rID", presentRoute);
							startActivity(prevP2);
							finish();
				/*Intent syncIntent = new Intent(ProductInvoiceReview.this, SyncMaster.class);
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
				});
		alertDialogOrderSubmission.setIcon(R.drawable.info_icon);
		alertDialogOrderSubmission.show();

	}

	public class GetData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(ProductInvoiceReview.this);
			mProgressDialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.genTermPleaseWaitNew));
			mProgressDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.Loading));
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			hmapProductRelatedSchemesList= mDataSource.fnProductRelatedSchemesList();



			chkflgInvoiceAlreadyGenerated= mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,CommonInfo.flgDrctslsIndrctSls);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
			if(chkflgInvoiceAlreadyGenerated==1)
			{
				TmpInvoiceCodePDA= mDataSource.fnGetInvoiceCodePDA(storeID,StoreVisitCode);
			}
			else
			{
				TmpInvoiceCodePDA=genTempInvoiceCodePDA();
			}
			CheckIfStoreExistInStoreProdcutPurchaseDetails= mDataSource.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
			CheckIfStoreExistInStoreProdcutInvoiceDetails= mDataSource.fnCheckIfStoreExistInStoreProdcutInvoiceDetails(storeID,TmpInvoiceCodePDA);
			strGlobalOrderID=TmpInvoiceCodePDA;
			 /* if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1 || CheckIfStoreExistInStoreProdcutInvoiceDetails==1)
			  {
				  strGlobalOrderID=mDataSource.fngetOrderIDAganistStore(storeID,TmpInvoiceCodePDA);
			  }
			  else
			  {
				  strGlobalOrderID= genOutOrderID();
			  }*/
			getCategoryDetail();

			getProductData();

			getCutOffDetailsForProduct();
			//mDataSource.open();
			hmapFetchPDASavedData= mDataSource.fetchActualVisitData(storeID);

			//mDataSource.close();

			if(hmapFetchPDASavedData!=null && hmapFetchPDASavedData.size()>0)
			{

				hmapProductIdStock.putAll(hmapFetchPDASavedData);

			}

			distID= mDataSource.getDisId(storeID);
			hmapProductIdStock= mDataSource.fetchActualVisitData(storeID);

			hmapVanPrdctStockCount= mDataSource.fnGetFinalInvoiceQtyProductWise(CommonInfo.flgDrctslsIndrctSls);


			hmapDistPrdctStockCount= mDataSource.getStockAsPerFlg(distID);
			hmapPerUnitName= mDataSource.getPerUnitName();
			hmapPerBaseQty= mDataSource.getPerBaseQty();
			return null;
		}
		@Override
		protected void onPostExecute(Void args) {
			loadPurchaseProductDefault();
			mProgressDialog.dismiss();

		}

	}

	public class SaveData extends AsyncTask<String, String, Void>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//Text need to e changed according to btn Click


			if(mProgressDialog.isShowing()==false)
			{
				mProgressDialog = new ProgressDialog(ProductInvoiceReview.this);
				mProgressDialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.genTermPleaseWaitNew));
				mProgressDialog.setMessage(progressTitle);
				mProgressDialog.setIndeterminate(true);
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
			}
		}

		@Override
		protected Void doInBackground(String... params)
		{
			String executedData=params[0];

			int btnClkd;
			if(executedData.contains("~"))
			{
				btnClkd=Integer.parseInt(executedData.split(Pattern.quote("~"))[0]);
				isReturnClkd=Integer.parseInt(executedData.split(Pattern.quote("~"))[1]);
			}

			else
			{
				btnClkd=Integer.parseInt(executedData);
			}


			fnSaveFilledDataToDatabase(btnClkd);
			return null;
		}
		@Override
		protected void onPostExecute(Void args) {
			if(mProgressDialog!=null) {
				if (mProgressDialog.isShowing() == true) {
					mProgressDialog.dismiss();
				}
			}
			long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
			String startTS = TimeUtils.getNetworkDateTime(ProductInvoiceReview.this,TimeUtils.DATE_TIME_FORMAT);
			//mDataSource.open();
			mDataSource.UpdateStoreEndVisit(storeID,startTS);
			//mDataSource.close();
			if(isReturnClkd==3)
			{
				Intent fireBackDetPg=new Intent(ProductInvoiceReview.this,ReturnActivity.class);
				fireBackDetPg.putExtra("storeID", storeID);
				fireBackDetPg.putExtra("SN", SN);
				fireBackDetPg.putExtra("bck", 1);
				fireBackDetPg.putExtra("imei", imei);
				fireBackDetPg.putExtra("userdate", date);
				fireBackDetPg.putExtra("pickerDate", pickerDate);
				fireBackDetPg.putExtra("OrderPDAID", strGlobalOrderID);
				fireBackDetPg.putExtra("flgPageToRedirect", "2");
				fireBackDetPg.putExtra("flgOrderType", flgOrderType);

				// fireBackDetPg.putExtra("rID", routeID);

				startActivity(fireBackDetPg);
				finish();
			}

			else if(isReturnClkd==2)
			{
				//Intent fireBackDetPg=new Intent(ProductOrderSearch.this,POSMaterialActivity.class);
				Intent fireBackDetPg=new Intent(ProductInvoiceReview.this,ProductEntryForm.class);
				fireBackDetPg.putExtra("storeID", storeID);
				fireBackDetPg.putExtra("SN", SN);
				fireBackDetPg.putExtra("bck", 1);
				fireBackDetPg.putExtra("imei", imei);
				fireBackDetPg.putExtra("userdate", date);
				fireBackDetPg.putExtra("pickerDate", pickerDate);
				fireBackDetPg.putExtra("flgOrderType", flgOrderType);
				//fireBackDetPg.putExtra("rID", routeID);
				startActivity(fireBackDetPg);
				finish();
			}
			else if(isReturnClkd==5)
			{
				Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);
				AmtCollectIntent.putExtra("storeID", storeID);
				AmtCollectIntent.putExtra("imei", imei);
				AmtCollectIntent.putExtra("userdate", date);
				AmtCollectIntent.putExtra("pickerDate", pickerDate);
				AmtCollectIntent.putExtra("SN", SN);
				AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				AmtCollectIntent.putExtra("FromPlace", "1");
				AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				startActivity(AmtCollectIntent);
				finish();
			}
			else if(isReturnClkd==6)
			{
				Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, Delivery_Details_Activity.class);   //
				AmtCollectIntent.putExtra("storeID", storeID);
				AmtCollectIntent.putExtra("imei", imei);
				AmtCollectIntent.putExtra("userdate", date);
				AmtCollectIntent.putExtra("pickerDate", pickerDate);
				AmtCollectIntent.putExtra("SN", SN);
				AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				startActivity(AmtCollectIntent);
				finish();
			}
			else if(isReturnClkd==7)
			{
				Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);   //
				AmtCollectIntent.putExtra("storeID", storeID);
				AmtCollectIntent.putExtra("imei", imei);
				AmtCollectIntent.putExtra("userdate", date);
				AmtCollectIntent.putExtra("pickerDate", pickerDate);
				AmtCollectIntent.putExtra("SN", SN);
				AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				AmtCollectIntent.putExtra("FromPlace", "1");
				AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				startActivity(AmtCollectIntent);
				finish();
			}
		}


	}

	public class standBYtask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			// TODO Auto-generated method stub

			// Thread ttt = new Thread();
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

	public class CoundownClass2 extends CountDownTimer{

		public CoundownClass2(long startTime, long interval) {
			super(startTime, interval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {

		}

		@Override
		public void onFinish() {

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

					GPSLocationLatitude=""+lattitude;
					GPSLocationLongitude=""+longitude;
					GPSLocationProvider="GPS";
					GPSLocationAccuracy=""+accuracy;
					System.out.println("LOCATION(GPS)  LATTITUDE: " +lattitude + "LONGITUDE:" + longitude+ "accuracy:" + accuracy);
					//text2.setText(" LOCATION(GPS) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude+ "\naccuracy:" + accuracy);
					//Toast.makeText(getApplicationContext(), " LOCATION(NW) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude+ "\naccuracy:" + accuracy, Toast.LENGTH_LONG).show();
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

				NetworkLocationLatitude=""+lattitude1;
				NetworkLocationLongitude=""+longitude1;
				NetworkLocationProvider="Network";
				NetworkLocationAccuracy=""+accuracy1;
				System.out.println("LOCATION(N/W)  LATTITUDE: " +lattitude1 + "LONGITUDE:" + longitude1+ "accuracy:" + accuracy1);
				// Toast.makeText(this, " LOCATION(NW) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude, Toast.LENGTH_LONG).show();
				//text1.setText(" LOCATION(N/W) \n LATTITUDE: " +lattitude1 + "\nLONGITUDE:" + longitude1+ "\naccuracy:" + accuracy1);

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
					if(Double.parseDouble(GPSLocationAccuracy)<=fnAccuracy)
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
					if(Double.parseDouble(NetworkLocationAccuracy)<=fnAccuracy)
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
				if(pDialog2STANDBY.isShowing())
				{
					pDialog2STANDBY.dismiss();
				}
				//alert ... try again nothing found // return back

				// Toast.makeText(getApplicationContext(), "Please try again, No Fused,GPS or Network found.", Toast.LENGTH_LONG).show();

				showAlertForEveryOne(ProductInvoiceReview.this.getResources().getString(R.string.AlertTryAgain));
			}
			else
			{


				if(pDialog2STANDBY.isShowing())
				{
					pDialog2STANDBY.dismiss();
				}
				if(!GpsLat.equals("0") )
				{
					fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
				}

				if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
				{

					fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
					UpdateLocationAndProductAllData();
				}
				else
				{countSubmitClicked++;
					if(countSubmitClicked==1)
					{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

						// Setting Dialog Title
						alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
						alertDialog.setIcon(R.drawable.error_info_ico);
						alertDialog.setCancelable(false);
						// Setting Dialog Message
						alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertSameLoc));

						// On pressing Settings button
						alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								countSubmitClicked++;
								Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(intent);
							}
						});

						// Showing Alert Message
						alertDialog.show();



					}
					else
					{
						UpdateLocationAndProductAllData();
					}


				}

			}

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


	public void orderBookingTotalCalc() {



		Double StandardRate = 0.00;
		Double StandardRateBeforeTax = 0.00;

		Double ActualRateAfterDiscountBeforeTax = 0.00;
		Double DiscountAmount = 0.00;
		Double ActualTax = 0.00;
		Double ActualRateAfterDiscountAfterTax = 0.00;


		Double TotalFreeQTY = 0.00;
		Double TotalProductLevelDiscount = 0.00;
		Double TotalOrderValBeforeTax = 0.00;

		Double TotTaxAmount = 0.00;
		Double TotOderValueAfterTax = 0.00;

		LinkedHashMap<String,String> hmapPrdctOrderQty=prdctModelArrayList.getHmapPrdctOrderQty();
		if(hmapPrdctOrderQty!=null)
		{
			for (Entry<String,String> entry:hmapPrdctOrderQty.entrySet()) {



				String ProductID=entry.getKey();
				String prdctQty=entry.getValue();
				//((TextView) (vRow).findViewById(R.id.txtVwRate)).setText("" + hmapProductStandardRate.get(ProductID).toString());

				if ((!TextUtils.isEmpty(prdctQty)) &&(Integer.parseInt(prdctQty)>0)) {

					StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
					StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

					//If No Percentage Discount or Flat Discount is Applicable Code Starts Here
					ActualRateAfterDiscountBeforeTax = StandardRateBeforeTax;
					DiscountAmount = 0.00;
					ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
					ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

					Double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(prdctQty);

					Double DiscAmtOnPreQtyBasicToDisplay = DiscAmtOnPreQtyBasic;
					DiscAmtOnPreQtyBasicToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));



					TotalProductLevelDiscount = TotalProductLevelDiscount + DiscAmtOnPreQtyBasic;
					TotTaxAmount = TotTaxAmount + (ActualTax * Double.parseDouble(prdctQty));

					Double TaxValue = ActualTax * Double.parseDouble(prdctQty);
					TaxValue = Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
					hmapProductTaxValue.put(ProductID, "" + TaxValue);

					Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(prdctQty);
					hmapLineValAftrTxAftrDscnt.put(ProductID, "" + OrderValPrdQtyBasis);

					TotalOrderValBeforeTax = TotalOrderValBeforeTax + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
					hmapLineValBfrTxAftrDscnt.put(ProductID, "" + ActualRateAfterDiscountBeforeTax * Double.parseDouble(prdctQty));
					TotOderValueAfterTax = TotOderValueAfterTax + OrderValPrdQtyBasis;

					//If No Percentage Discount or Flat Discount is Applicable Code Ends Here


				}

			}
		}

		//Now the its Time to Show the OverAll Summary Code Starts Here

		tvFtotal.setText(("" + TotalFreeQTY).trim());

		TotalProductLevelDiscount = Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
		tvDis.setText(("" + TotalProductLevelDiscount).trim());

		TotalOrderValBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));
		tv_NetInvValue.setText(("" + TotalOrderValBeforeTax).trim());

		String percentBenifitMax = mDataSource.fnctnGetMaxAssignedBen8DscntApld1(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
		Double percentMax = 0.00;
		Double percentMaxGross = 0.0;
		Double amountMaxGross = 0.0;

		String amountBenfitMaxGross = mDataSource.fnctnGetMaxAssignedBen9DscntApld2(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
		String percentBenifitMaxGross = mDataSource.fnctnGetMaxAssignedBen8DscntApld2(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);

		if (percentBenifitMaxGross.equals("")) {
			percentMaxGross = 0.0;
		} else {
			percentMaxGross = Double.parseDouble(percentBenifitMaxGross.split(Pattern.quote("^"))[0]);
		}
		if (percentBenifitMax.equals("")) {
			percentMax = 0.00;
		} else {
			percentMax = Double.parseDouble(percentBenifitMax.split(Pattern.quote("^"))[0]);
		}

		String amountBenifitMax = mDataSource.fnctnGetMaxAssignedBen9DscntApld1(storeID, TmpInvoiceCodePDA, TmpInvoiceCodePDA);
		Double amountMax = 0.00;
		if (percentBenifitMax.equals("")) {
			amountMax = 0.0;
		} else {
			amountMax = Double.parseDouble(amountBenifitMax.split(Pattern.quote("^"))[0]);
		}


		tvAddDisc.setText("" + "0.00");

		tv_NetInvAfterDiscount.setText("" + TotalOrderValBeforeTax);

		TotTaxAmount = Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		tvTAmt.setText("" + TotTaxAmount);

		Double totalGrossVALMaxPercentage = TotalOrderValBeforeTax - TotalOrderValBeforeTax * (percentMaxGross / 100);
		Double totalGrossrVALMaxAmount = TotalOrderValBeforeTax - amountMaxGross;
		Double totalGrossVALAfterDiscount = 0.0;
		if (totalGrossVALMaxPercentage != totalGrossrVALMaxAmount) {
			totalGrossVALAfterDiscount = Math.min(totalGrossrVALMaxAmount, totalGrossVALMaxPercentage);
		} else {
			totalGrossVALAfterDiscount = totalGrossrVALMaxAmount;
		}

		if (totalGrossVALAfterDiscount == totalGrossrVALMaxAmount && totalGrossrVALMaxAmount != 0.0) {
			mDataSource.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]), TmpInvoiceCodePDA, TmpInvoiceCodePDA);
		} else if (totalGrossVALAfterDiscount == totalGrossVALMaxPercentage && percentMaxGross != 0.0) {
			mDataSource.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]), TmpInvoiceCodePDA, TmpInvoiceCodePDA);
		}

		Double GrossInvValue = totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue = Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		tv_GrossInvVal.setText("" + String.format("%.2f", GrossInvValue) );
		//tvAfterTaxValue.setText("" + String.format("%.2f", GrossInvValue));

		Double CollectionAmt = mDataSource.fnTotCollectionAmtAgainstStore(storeID, TmpInvoiceCodePDA, StoreVisitCode);
		CollectionAmt = Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmt));

		if (GrossInvValue > 0.0) {
			VisitTypeStatus = "2";
		}
		if (CollectionAmt > 0.0) {
			VisitTypeStatus = "3";
		}
		if (CollectionAmt > 0.0 && GrossInvValue > 0.0) {
			VisitTypeStatus = "4";
		}

		mDataSource.updateVisitTypeStatusOfStore(storeID, VisitTypeStatus, StoreVisitCode);

	}

}