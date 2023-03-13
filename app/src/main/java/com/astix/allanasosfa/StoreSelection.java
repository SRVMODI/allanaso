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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.database.DBHelper;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.model.TblCoverageDsr;
import com.astix.allanasosfa.model.TblStoreListMaster;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.utils.PreferenceManager;
import com.example.gcm.MainActivity;
import com.example.gcm.NotificationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import java.text.DateFormat;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.astix.sfatju.R;

public class StoreSelection extends BaseActivity implements LocationInterface, InterfaceRetrofit {
	public static String JointVisitId="NA";
	public static final String DATASUBDIRECTORYForText = CommonInfo.TextFileFolder;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second
	private static final String TAG = "LocationActivity";
	public static ScheduledExecutorService schPHSTATS;
	//String[] Manager_names= { "Select Market Location", "sec-20", "sec-24", "other"};
	static String selected_manager = "NA";
	static String seleted_routeIDType = "0";
	static int Selected_manager_Id = 0;
	static int flgChangeRouteOrDayEnd = 0;
	static ScheduledExecutorService scheduler;
	private final Context mContext = this;
	//public static HashMap<String, String> hmapStoreIdSstat=new HashMap<String, String>();
	public boolean addStoreBtnClick = false;
	public String[] xmlForWeb = new String[1];
	public int syncFLAG = 0;
	public String currSysDate;
	public int chkFlgForErrorToCloseApp = 0;
	public RelativeLayout ll_manager;
	public String passDate;
	public SimpleDateFormat sdf;
	public String fDate;
	public String userDate;
	public String pickerDate;
	public String imei;
	public String[] storeList;
	public String[] storeRouteIdType;
	public TableLayout tl2;
	public TextView txtview_selectstoretext;
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
	public String fnAccurateProvider = "";
	public String fnLati = "0";
	public String fnLongi = "0";
	public Double fnAccuracy = 0.0;
	// Declaring a Location Manager
	protected LocationManager locationManager;
	int serverResponseCode = 0;
	InputStream inputStream;
	Spinner spinner_manager;
	Spinner spinner_RouteList;
	String[] Manager_names = null;
	String[] Route_names = null;
	RelativeLayout rl_for_other;
	EditText ed_Street;
	HashMap<String, String> hmapManagerNameManagerIdDetails = new HashMap<String, String>();
	HashMap<String, String> hmapRouteIdNameDetails = new HashMap<String, String>();
	boolean serviceException = false;
	Dialog dialog;
	CheckBox check1, check2;
	RelativeLayout relativeLayout1;
	int battLevel = 0;
	LinkedHashMap<String, String> hmapOutletListForNear = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> hmapOutletListForNearUpdated = new LinkedHashMap<String, String>();
	ProgressDialog pDialogGetStores;
	ProgressDialog mProgressDialog;
	boolean bool = true;
	DatabaseAssistant DA;
	ImageView img_side_popUp;
	int closeList = 0;
	int whatTask = 0;
	String whereTo = "11";
	ArrayList mSelectedItems = new ArrayList();
	int prevSel = 0;
	int prevID;
	ArrayList<String> stIDs;
	ArrayList<String> stNames;
	ProgressDialog pDialog2;
	String FWDCLname;
	String BCKCLname;
	standBYtask task_STANDBY = new standBYtask();
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	double latitude;
	double longitude;
	String mLastUpdateTime;
	String fusedData;
	LinkedHashMap<String, String> hmapStoreLatLongDistanceFlgRemap = new LinkedHashMap<String, String>();
	private int selected = 0;
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {

			battLevel = intent.getIntExtra("level", 0);

		}
	};

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
			byte[] data = new byte[BUFFER_SIZE];

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

	public void locationRetrievingAndDistanceCalculating() {
		LocationRetreivingGlobal llaaa = new LocationRetreivingGlobal();
		llaaa.locationRetrievingAndDistanceCalculating(this, true, true, 20, 1);


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
	public void success() {
		showAlertRefreshSucessFully("Information", "Data Refreshed Sucessfully");

	}


	// *****SYNC******

	@Override
	public void failure() {
		showAlertException(getResources().getString(R.string.txtError), getResources().getString(R.string.txtErrRetrieving));
	}

	/*private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {



		@Override
		protected Void doInBackground(Void... params) {

			try {

			}

			catch (Exception e) {
			//	Log.i("Sync ASync", "Sync ASync Failed!", e);

			}

			finally {

			}
			return null;
		}

		@Override
		protected void onCancelled() {

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			 pDialogSync.dismiss();

			//finish();
		}
	}*/

	// *****SYNC******

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
				mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4, StoreVisitCode, TmpInvoiceCodePDA,"NA");
				mDataSource.updateflgFromWhereSubmitStatusAgainstStore(StoreIDToProcessWithoutAlret, 1, StoreVisitCode);
			}
		}

		try {

			File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

			if (!OrderXMLFolder.exists()) {
				OrderXMLFolder.mkdirs();
			}

			String routeID = mDataSource.GetActiveRouteIDSunil();

			DA.export(DBHelper.DATABASE_NAME, newfullFileName, routeID);


			if (hmapStoreListToProcessWithoutAlret != null) {

				Set set2 = hmapStoreListToProcessWithoutAlret.entrySet();
				Iterator iterator = set2.iterator();
				//mDataSource.open();
				while (iterator.hasNext()) {
					Map.Entry me2 = (Map.Entry) iterator.next();
					String StoreIDToProcessWithoutAlret = me2.getKey().toString();
					mDataSource.UpdateStoreFlagAtDayEndOrChangeRouteWithOnlyVistOrCollection(StoreIDToProcessWithoutAlret, 4);
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
					mDataSource.UpdateStoreVisitWiseTables(StoreIDToProcessWithoutAlret, 4, StoreVisitCode, TmpInvoiceCodePDA,"NA");
				}

			}


			mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");
			//mDataSource.open();
			mDataSource.UpdateStoreImage("0", 5);
			for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
				String valSN = (String) mSelectedItems.get(nosSelected);
				int valID = stNames.indexOf(valSN);
				String stIDneeded = stIDs.get(valID);

				mDataSource.UpdateStoreFlagAtDayEndOrChangeRoute(stIDneeded, 4);
				mDataSource.UpdateStoreImage(stIDneeded, 5);

				mDataSource.UpdateStoreMaterialphotoFlag(stIDneeded.trim(), 5);
				mDataSource.UpdateStoreReturnphotoFlag(stIDneeded.trim(), 5);
				mDataSource.UpdateStoreClosephotoFlag(stIDneeded.trim(), 5);

				mDataSource.UpdateNewAddedStorephotoFlag(stIDneeded.trim(), 5);


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
				mDataSource.UpdateStoreVisitWiseTables(stIDneeded, 4, StoreVisitCode, TmpInvoiceCodePDA,"NA");
				mDataSource.updateflgFromWhereSubmitStatusAgainstStore(stIDneeded, 1, StoreVisitCode);
			}
			flgChangeRouteOrDayEnd = valDayEndOrChangeRoute;

			Intent syncIntent = new Intent(StoreSelection.this, SyncMaster.class);
			syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
			syncIntent.putExtra("OrigZipFileName", newfullFileName);
			syncIntent.putExtra("whereTo", whereTo);
			startActivity(syncIntent);
			finish();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
		alertDialog.setIcon(R.drawable.error_info_ico);
		alertDialog.setCancelable(false);
		alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);

		alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});

		alertDialog.show();
	}


	public void enableGPSifNot() {

		boolean isGPSok = false;
		isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!isGPSok) {
			showSettingsAlert();
			isGPSok = false;
		}
	}


	public void DayEnd() {


		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(StoreSelection.this);

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
		AlertDialog alert = alertDialogSubmitConfirm.create();
		alert.show();

	}


	public void DayEndWithoutalert() {

		//mDataSource.open();
		String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

		//	mDataSource.UpdateTblDayStartEndDetails(Integer.parseInt(rID), valDayEndOrChangeRoute);
		//mDataSource.close();

		SyncNow();

	}


	public void showChangeRouteConfirm() {

		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(StoreSelection.this);
		alertDialogSubmitConfirm.setTitle(R.string.PleaseConformMsg);
		if (flgDayEndOrChangeRoutenew == 1) {
			alertDialogSubmitConfirm.setMessage(getText(R.string.genTermDayEndAlertWithoutStoreSubmit));
		} else if (flgDayEndOrChangeRoutenew == 2) {
			alertDialogSubmitConfirm.setMessage(getText(R.string.genTermchangeRouteAlert));
		}

		alertDialogSubmitConfirm.setCancelable(false);

		alertDialogSubmitConfirm.setNeutralButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				// Location_Getting_Service.closeFlag = 1;
				//enableGPSifNot();

				// run bgTasker()!

				// if(!scheduler.isTerminated()){
				// scheduler.shutdownNow();
				// }
				//mDataSource.open();

				if (mDataSource.GetLeftStoresChk() == true) {
					// run bgTasker()!

					// Location_Getting_Service.closeFlag = 1;
					// scheduler.shutdownNow();

					//enableGPSifNot();
					// scheduler.shutdownNow();

					//mDataSource.close();

					whatTask = 3;
					// -- Route Info Exec()
					try {

						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
						//System.out.println(e);
					} catch (ExecutionException e) {
						e.printStackTrace();
						//System.out.println(e);
					}
					// --
				} else {
					// show dialog for clear..clear + tranx to launcher

					// -- Route Info Exec()
					try {
						//mDataSource.close();

						whatTask = 1;
						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
						//System.out.println(e);
					} catch (ExecutionException e) {
						e.printStackTrace();
						//System.out.println(e);
					}
					// --

							/*Intent revupOldFriend = new Intent(StoreSelection.this, LauncherActivity.class);
							 revupOldFriend.putExtra("imei", imei);
							startActivity(revupOldFriend);
							finish();*/
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


	public void showChangeRouteConfirmWhenNoStoreisLeftToSubmit() {

		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(StoreSelection.this);
		alertDialogSubmitConfirm.setTitle(R.string.PleaseConformMsg);
		alertDialogSubmitConfirm.setMessage(getText(R.string.genTermchangeRouteAlertWhenNoStoreisLeftToSubmit));
		alertDialogSubmitConfirm.setCancelable(false);

		alertDialogSubmitConfirm.setNeutralButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				// Location_Getting_Service.closeFlag = 1;
				//enableGPSifNot();

				// run bgTasker()!

				// if(!scheduler.isTerminated()){
				// scheduler.shutdownNow();
				// }
				//mDataSource.open();

				if (mDataSource.GetLeftStoresChk() == true) {
					// run bgTasker()!

					// Location_Getting_Service.closeFlag = 1;
					// scheduler.shutdownNow();

					//enableGPSifNot();
					// scheduler.shutdownNow();

					//mDataSource.close();

					whatTask = 3;
					// -- Route Info Exec()
					try {

						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
						//System.out.println(e);
					} catch (ExecutionException e) {
						e.printStackTrace();
						//System.out.println(e);
					}
					// --
				} else {
					// show dialog for clear..clear + tranx to launcher

					// -- Route Info Exec()
					try {
						//mDataSource.close();

						whatTask = 1;
						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
						//System.out.println(e);
					} catch (ExecutionException e) {
						e.printStackTrace();
						//System.out.println(e);
					}
					// --

							/*Intent revupOldFriend = new Intent(
									StoreSelection.this, LauncherActivity.class);
							 revupOldFriend.putExtra("imei", imei);
							startActivity(revupOldFriend);
							finish();*/
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


	public void showPendingStorelist(int flgDayEndOrChangeRoute) {

		// final CharSequence[] items =
		// {"cat1","cat2","cat3","cat4","cat5","cat6","cat7","cat8","cat9","cat10","cat11","cat12","cat13","cat14","cat15","cat16","cat17","cat18","cat19","cat20","cat21","cat22","cat23","cat24"
		// };

		//flgDayEndOrChangeRoutenew=1-DayEnd,2-Change Route
		flgDayEndOrChangeRoutenew = flgDayEndOrChangeRoute;
		ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.AlertDialogTheme);


		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		//builder.setTitle(R.string.genTermSelectStoresPendingToComplete);
		TextView content = new TextView(this);
		if (flgDayEndOrChangeRoutenew == 1) {
			content.setText(R.string.genTermSelectStoresPendingToCompleteDayEnd);
		} else if (flgDayEndOrChangeRoutenew == 2) {
			content.setText(R.string.genTermSelectStoresPendingToCompleteChangeRoute);
		}
		//content.setText(R.string.genTermSelectStoresPendingToComplete);
		content.setTextSize(16);
		content.setTextColor(Color.WHITE);
		builder.setCustomTitle(content);
		mSelectedItems.clear();

		final String[] stNames4List = new String[stNames.size()];
		checks = new boolean[stNames.size()];
		stNames.toArray(stNames4List);
		for (int cntPendingList = 0; cntPendingList < stNames4List.length; cntPendingList++) {
			mSelectedItems.add(stNames4List[cntPendingList]);
			checks[cntPendingList] = true;
		}

		builder.setMultiChoiceItems(stNames4List, checks, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {

				////System.out.println("Abhinav store Selection  Step 5");
				if (isChecked) {
					mSelectedItems.add(stNames4List[which]);

				} else mSelectedItems.remove(stNames4List[which]);
			}
		});

		builder.setPositiveButton(R.string.genTermSubmitSelected, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (mSelectedItems.size() == 0) {
					Toast.makeText(getApplicationContext(), R.string.genTermNoStroeSelectedOnSubmit, Toast.LENGTH_SHORT).show();
					showPendingStorelist(flgDayEndOrChangeRoutenew);
				} else {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(),
					// "User Selected : " + mSelectedItems.toString(),
					// Toast.LENGTH_SHORT).show();
					//System.out.println("User Selected : "+ mSelectedItems.toString());

					// Location_Getting_Service.closeFlag = 1;
					//enableGPSifNot();
					// doing stuff here

					// scheduler.shutdownNow();
					// if(!scheduler.isTerminated()){
					// scheduler.shutdownNow();
					// }
					// run bgTasker()!
					whatTask = 2;
					// -- Route Info Exec()
					try {

						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
						//System.out.println(e);
					} catch (ExecutionException e) {
						e.printStackTrace();
						//System.out.println(e);
					}
					// --

				}

			}
		});
		builder.setNeutralButton(R.string.genTermDirectlyChangeRoute, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				closeList = 1;
				// showChangeRouteConfirm();
				//mDataSource.open();

				if (mDataSource.GetLeftStoresChk() == true) {
					// run bgTasker()!

					// Location_Getting_Service.closeFlag = 1;
					// scheduler.shutdownNow();
					// if(!scheduler.isTerminated()){
					//enableGPSifNot();
					// scheduler.shutdownNow();
					// }

					whatTask = 3;
					// -- Route Info Exec()
					try {
						new bgTasker().execute().get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}

					//mDataSource.close();

				} else {
					//mDataSource.close();
					// show dialog for clear..clear + tranx to launcher
					showChangeRouteConfirm();
				}
			}
		});
		builder.setNegativeButton(R.string.txtOnChangeRouteDayEndCancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				closeList = 1;
				valDayEndOrChangeRoute = 0;
			}
		});

		AlertDialog alert = builder.create();
		if (closeList == 1) {
			closeList = 0;
			alert.dismiss();

		} else {
			alert.show();
			alert.setCancelable(false);
		}
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


	public void onDestroy() {
		super.onDestroy();
		// unregister receiver
		this.unregisterReceiver(this.mBatInfoReceiver);

		//this.unregisterReceiver(this.KillME);
	}


	public void setUpVariable() {


		txtview_selectstoretext = findViewById(R.id.txtview_selectstoretext);
		String PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
		String personName = "";


		if (!PersonNameAndFlgRegistered.equals("0")) {
			personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
			txtview_selectstoretext.setText(personName);
   /*personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
   FlgRegistered = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[1];*/
		}

		Button btn_nearStores = findViewById(R.id.btn_nearStores);
		btn_nearStores.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pDialog2STANDBY != null) {
					if (pDialog2STANDBY.isShowing()) {


					} else {
						boolean isGPSok = false;
						boolean isNWok = false;
						isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
						isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

						if (!isGPSok && !isNWok) {
							try {
								showSettingsAlert();
							} catch (Exception e) {

							}
							isGPSok = false;
							isNWok = false;
						} else {
							locationRetrievingAndDistanceCalculating();
						}
					}
				} else {
					boolean isGPSok = false;
					boolean isNWok = false;
					isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
					isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

					if (!isGPSok && !isNWok) {
						try {
							showSettingsAlert();
						} catch (Exception e) {

						}
						isGPSok = false;
						isNWok = false;
					} else {
						locationRetrievingAndDistanceCalculating();
					}

				}
			}
		});

		ImageView image_Notification = findViewById(R.id.image_Notification);
		image_Notification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				Intent intent = new Intent(StoreSelection.this, NotificationActivity.class);

				StoreSelection.this.startActivity(intent);
				finish();

			}
		});


		Button add_new_store = findViewById(R.id.but_add_store);
		add_new_store.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

					/*Intent intent =new Intent(StoreSelection.this,StorelistActivity.class);
					intent.putExtra("activityFrom", "StoreSelection");
					startActivity(intent);
					finish();*/
				//mDataSource.open();
				if (true) {
					addStoreBtnClick = true;
					firstTimeLocationTrack();
				}


			}
		});

			/*Button but_SalesSummray = (Button) findViewById(R.id.btnSalesSummary);
			but_SalesSummray.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub


				// Intent intent = new Intent(StoreSelection.this, My_Summary.class);
				 Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
					intent.putExtra("imei", imei);
					intent.putExtra("userDate", userDate);
					intent.putExtra("pickerDate", pickerDate);
					StoreSelection.this.startActivity(intent);
					finish();

				}
			});

			Button but_day_end = (Button) findViewById(R.id.mainImg1);
			but_day_end.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					closeList = 0;
					valDayEndOrChangeRoute=1;

					if(isOnline())
					{

					}
					 else
					 {
						showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
						 return;

					 }

					//mDataSource.open();
					whereTo = "11";
					////System.out.println("Abhinav store Selection  Step 1");
						////System.out.println("StoreList2Procs(before): " + StoreList2Procs.length);
					StoreList2Procs = mDataSource.ProcessStoreReq();
					////System.out.println("StoreList2Procs(after): " + StoreList2Procs.length);

					if (StoreList2Procs.length != 0) {
						//whereTo = "22";
						////System.out.println("Abhinav store Selection  Step 2");
						midPart();

						showPendingStorelist();

					} else if (mDataSource.GetLeftStoresChk() == true)
					{
						////System.out.println("Abhinav store Selection  Step 7");
						//enableGPSifNot();
						// showChangeRouteConfirm();
						DayEnd();

					}

					else {
						DayEnd();
					}

					//mDataSource.close();

				}
			});*/


		Button btn_telephonic = findViewById(R.id.btn_telephonic);
		btn_telephonic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (!selStoreID.isEmpty()) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreSelection.this);

					// Setting Dialog Title
					alertDialog.setTitle(getText(R.string.ConfirmOrderType));

					// Setting Dialog Message
					alertDialog.setMessage(getText(R.string.AlertTelephonic));

					alertDialog.setCancelable(false);

					// Setting Positive "Yes" Button
					alertDialog.setPositiveButton(getText(R.string.txtProceed), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							long StartClickTime = System.currentTimeMillis();
							Date dateobj1 = new Date(StartClickTime);
							SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
							String StartClickTimeFinal = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);

							CommonInfo.fileContent = imei + "_" + selStoreID + "_" + "Start Button Click on Store Selection " + StartClickTimeFinal;

							File dirORIGimg = new File(Environment.getExternalStorageDirectory(), DATASUBDIRECTORYForText);
							if (!dirORIGimg.exists()) {
								dirORIGimg.mkdirs();
							}
							mDataSource.updateflgOrderTypeIntblStoreList(selStoreID, 0);

							if (Selected_manager_Id != -99) {
								String allData = mDataSource.fetchtblManagerMstr("" + Selected_manager_Id);

								StringTokenizer token = new StringTokenizer(String.valueOf(allData), "^");
								int chk = mDataSource.counttblSelectedManagerDetails();
								if (chk == 1) {
									mDataSource.deletetblSelectedManagerDetails();
									mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
											token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
											, token.nextToken().trim(), token.nextToken().trim(), "NA");
								} else {
									mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
											token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
											, token.nextToken().trim(), token.nextToken().trim(), "NA");
								}
							} else if (Selected_manager_Id == -99) {

								if (TextUtils.isEmpty(ed_Street.getText().toString().trim())) {

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StoreSelection.this);
									alertDialogBuilder.setTitle(getResources().getString(R.string.genTermNoDataConnection));
									alertDialogBuilder.setCancelable(false);
									alertDialogBuilder.setIcon(R.drawable.info_ico);

									// set dialog message
									alertDialogBuilder

											.setMessage(getResources().getString(R.string.txtEnterManagerName))
											.setCancelable(false)
											.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int id) {
													dialog.cancel();
												}
											});


									// create alert dialog
									AlertDialog alertDialog = alertDialogBuilder.create();

									alertDialog.show();
									return;

								} else {
									String allData = mDataSource.fetchtblManagerMstr("" + Selected_manager_Id);

									StringTokenizer token = new StringTokenizer(String.valueOf(allData), "^");
									int chk = mDataSource.counttblSelectedManagerDetails();
									if (chk == 1) {
										mDataSource.deletetblSelectedManagerDetails();
										mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
												token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
												, token.nextToken().trim(), token.nextToken().trim(), ed_Street.getText().toString().trim());
									} else {
										mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
												token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
												, token.nextToken().trim(), token.nextToken().trim(), ed_Street.getText().toString().trim());
									}
								}

							}

							whereTo = "11";

							syncTIMESTAMP = System.currentTimeMillis();
							Date dateobj = new Date(syncTIMESTAMP);
							SimpleDateFormat df = new SimpleDateFormat(
									"dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
							fullFileName1 = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);
							/*String checkClosrOrNext[] = dbengine.checkStoreCloseOrNextMethod(selStoreID);

							StringTokenizer tokensInvoice = new StringTokenizer(String.valueOf(checkClosrOrNext[0]), "_");

							int close = Integer.parseInt(tokensInvoice.nextToken().toString().trim());
							int next = Integer.parseInt(tokensInvoice.nextToken().toString().trim());


							if (close == 0 && next == 0)
							{*/


							String[] checkClosrOrNext = mDataSource.checkStoreCloseOrNextMethod(selStoreID);
							//mDataSource.close();
							int close = 0;
							int next = 0;

							if (checkClosrOrNext.length > 0) {
								//StringTokenizer tokensInvoice = new StringTokenizer(String.valueOf(checkClosrOrNext[0]), "_");
								close = Integer.parseInt(checkClosrOrNext[0]);
								next = 0;//Integer.parseInt(tokensInvoice.nextToken().toString().trim());
							}

							close = 0;
							if (close == 0) {
								if (!selStoreID.isEmpty()) {


									int valSstatValueAgainstStore = 0;
									int ISNewStore = 0;
									int IsNewStoreDataCompleteSaved = 0;
									try {
										//mDataSource.open();
										ISNewStore = mDataSource.fncheckStoreIsNewOrOld(selStoreID);
										IsNewStoreDataCompleteSaved = mDataSource.fncheckStoreIsNewStoreDataCompleteSaved(selStoreID);
										valSstatValueAgainstStore = mDataSource.fnGetStatValueagainstStore(selStoreID);
									} catch (Exception e) {

									} finally {
										//mDataSource.close();
									}

									if (ISNewStore == 0) {
										//Code If Starts Here


										//Intent nxtP4 = new Intent(StoreSelection.this,ProductOrderFilterSearch.class);
										Intent nxtP4 = new Intent(StoreSelection.this, ProductEntryForm.class);
										nxtP4.putExtra("storeID", selStoreID);
										nxtP4.putExtra("SN", selStoreName);
										nxtP4.putExtra("imei", imei);
										nxtP4.putExtra("userdate", userDate);
										nxtP4.putExtra("pickerDate", pickerDate);
										nxtP4.putExtra("flgOrderType", 0);
										nxtP4.putExtra("fromPage", "StoreSelection");

										locStat = 0;
										startActivity(nxtP4);
										finish();

									} else {
										//Code Else Starts Here

										if (IsNewStoreDataCompleteSaved == 1) {
											// TODO Auto-generated method stub
											CommonInfo.flgNewStoreORStoreValidation=1;
											Intent intent = new Intent(StoreSelection.this, AddNewStore_DynamicSectionWise.class);
											intent.putExtra("storeID", selStoreID);
											intent.putExtra("activityFrom", "StoreSelection");
											intent.putExtra("userdate", userDate);
											intent.putExtra("pickerDate", pickerDate);
											intent.putExtra("imei", imei);
											intent.putExtra("rID", rID);
											StoreSelection.this.startActivity(intent);
											finish();
										} else if (IsNewStoreDataCompleteSaved == 0) {

											Intent ready4GetLoc = new Intent(StoreSelection.this, ProductEntryForm.class);
											ready4GetLoc.putExtra("storeID", selStoreID);
											ready4GetLoc.putExtra("selStoreName", selStoreName);
											ready4GetLoc.putExtra("imei", imei);
											ready4GetLoc.putExtra("userDate", userDate);
											ready4GetLoc.putExtra("pickerDate", pickerDate);
											ready4GetLoc.putExtra("startTS", fullFileName1);
											ready4GetLoc.putExtra("bck", 0);
											ready4GetLoc.putExtra("flgOrderType", 0);
											ready4GetLoc.putExtra("fromPage", "StoreSelection");


											locStat = 0;


											startActivity(ready4GetLoc);
											finish();
											//}
										}

										//Code Else Ends Here
									}


								} else {
									Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectStore, Toast.LENGTH_SHORT).show();
								}
								// end else
							} else {
								//Toast.makeText(getApplicationContext(),R.string.genTermPleaseSelectDiffrentStore,Toast.LENGTH_SHORT).show();
								showAlertSingleButtonInfo(getResources().getString(R.string.genTermPleaseSelectDiffrentStore));
							}

						}
					});

					// Setting Negative "NO" Button
					alertDialog.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							mDataSource.updateflgOrderTypeIntblStoreList(selStoreID, -1);
							dialog.cancel();
						}
					});

					// Showing Alert Message
					alertDialog.show();


				} else {
					Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectStore, Toast.LENGTH_SHORT).show();
				}

			}
		});


		Button btnActualVisit = findViewById(R.id.btnActualVisit);
		btnActualVisit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (!selStoreID.isEmpty()) {

					long StartClickTime = System.currentTimeMillis();
					Date dateobj1 = new Date(StartClickTime);
					SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
					String StartClickTimeFinal = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);
					CommonInfo.fileContent = imei + "_" + selStoreID + "_" + "Start Button Click on Store Selection " + StartClickTimeFinal;

					File dirORIGimg = new File(Environment.getExternalStorageDirectory(), DATASUBDIRECTORYForText);
					if (!dirORIGimg.exists()) {
						dirORIGimg.mkdirs();
					}
								/*if(Selected_manager_Id!=0)
								{*/
					if (Selected_manager_Id != -99) {
						String allData = mDataSource.fetchtblManagerMstr("" + Selected_manager_Id);

						StringTokenizer token = new StringTokenizer(String.valueOf(allData), "^");
						//mDataSource.open();
						int chk = mDataSource.counttblSelectedManagerDetails();
						//mDataSource.close();
						if (chk == 1) {
							mDataSource.deletetblSelectedManagerDetails();
							//mDataSource.open();
							mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
									token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
									, token.nextToken().trim(), token.nextToken().trim(), "NA");
							//mDataSource.close();
						} else {
							//mDataSource.open();
							mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
									token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
									, token.nextToken().trim(), token.nextToken().trim(), "NA");
							//mDataSource.close();
						}
					} else if (Selected_manager_Id == -99) {

						if (TextUtils.isEmpty(ed_Street.getText().toString().trim())) {

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StoreSelection.this);
							alertDialogBuilder.setTitle(getResources().getString(R.string.genTermNoDataConnection));
							alertDialogBuilder.setCancelable(false);
							alertDialogBuilder.setIcon(R.drawable.info_ico);

							// set dialog message
							alertDialogBuilder

									.setMessage(getResources().getString(R.string.txtEnterManagerName))
									.setCancelable(false)
									.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});


							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();

							alertDialog.show();
							return;

						} else {
							String allData = mDataSource.fetchtblManagerMstr("" + Selected_manager_Id);

							StringTokenizer token = new StringTokenizer(String.valueOf(allData), "^");
							//mDataSource.open();
							int chk = mDataSource.counttblSelectedManagerDetails();
							//mDataSource.close();
							if (chk == 1) {
								mDataSource.deletetblSelectedManagerDetails();
								//mDataSource.open();
								mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
										token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
										, token.nextToken().trim(), token.nextToken().trim(), ed_Street.getText().toString().trim());
								//mDataSource.close();
							} else {
								//mDataSource.open();
								mDataSource.savetblSelectedManagerDetails(imei, StartClickTimeFinal, token.nextToken().trim(),
										token.nextToken().trim(), token.nextToken().trim(), token.nextToken().trim()
										, token.nextToken().trim(), token.nextToken().trim(), ed_Street.getText().toString().trim());
								//mDataSource.close();
							}
						}

					}
					//}


					whereTo = "11";

					syncTIMESTAMP = System.currentTimeMillis();
					Date dateobj = new Date(syncTIMESTAMP);
					SimpleDateFormat df = new SimpleDateFormat(
							"dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
					fullFileName1 = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);
					//mDataSource.open();
					String[] checkClosrOrNext = mDataSource.checkStoreCloseOrNextMethod(selStoreID);
					//mDataSource.close();
					int close = 0;
					int next = 0;

					if (checkClosrOrNext.length > 0) {
						//StringTokenizer tokensInvoice = new StringTokenizer(String.valueOf(checkClosrOrNext[0]), "_");
						close = Integer.parseInt(checkClosrOrNext[0]);
						next = 0;//Integer.parseInt(tokensInvoice.nextToken().toString().trim());
					}

					close=0;
					if (close == 0) {

						storeVisit();
					} else {
						//Toast.makeText(getApplicationContext(),R.string.genTermPleaseSelectDiffrentStore,Toast.LENGTH_SHORT).show();
						showAlertForClosedStore(getResources().getString(R.string.genTermPleaseSelectDiffrentStore));
					}
				} else {
					Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectStore, Toast.LENGTH_SHORT).show();
				}

			}
		});

		if (CommonInfo.flgDrctslsIndrctSls == 1) {
			TextView tv_SoreCalHead = findViewById(R.id.tv_SoreCalHead);
			tv_SoreCalHead.setVisibility((View.GONE));
			btn_telephonic.setVisibility(View.GONE);
		}

	}

	public void showAlertForClosedStore(String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
				.setMessage(msg)
				.setCancelable(false)
				.setIcon(R.drawable.info_ico)
				.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
						mDataSource.UpdateStoreStoreClose(selStoreID,0);
						storeVisit();
					}
				}).setNegativeButton(getResources().getString(R.string.txtCancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}


	private void storeVisit() {
		if (!selStoreID.isEmpty()) {

							/*if(isMyServiceRunning())
		            		{

		            		}
							else
							{
								startService(new Intent(StoreSelection.this,GPSTrackerService.class));
							}
							*/
			//startService(new Intent(StoreSelection.this,FusedTrackerService.class));
			//System.out.println("Sun new Value 0");
			int valSstatValueAgainstStore = 0;
			int ISNewStore = 0;
			int IsNewStoreDataCompleteSaved = 0;
			try {
				////mDataSource.open();
				ISNewStore = mDataSource.fncheckStoreIsNewOrOld(selStoreID);
				IsNewStoreDataCompleteSaved = mDataSource.fncheckStoreIsNewStoreDataCompleteSaved(selStoreID);
				valSstatValueAgainstStore = mDataSource.fnGetStatValueagainstStore(selStoreID);
			} catch (Exception e) {

			} finally {
				////mDataSource.close();
			}
						/*	String chID = ((mDataSource
									.getChainIDBasedOnStoreID(selStoreID)) + "")
									.toString().trim();

							int pgFWDCLname2getID = mDataSource
									.getFwdPgIdonNextBtnClick(selStoreID, "2", chID);
							FWDCLname = mDataSource.getCustomPGid(pgFWDCLname2getID);

							int pgBCKCLname2getID = mDataSource
									.getFwdPgIdonBackBtnClick(selStoreID, "2", chID);
							BCKCLname = mDataSource.getCustomPGid(pgBCKCLname2getID);*/

			//System.out.println("PREV. LOC CHK sop: "+ mDataSource.PrevLocChk(selStoreID.trim()));

							/*if ((mDataSource.PrevLocChk(selStoreID.trim()))
									|| locStat == 1)
							{*/
							 	/*//mDataSource.open();
							 	System.out.println("DtateTimeNitish3");
						        mDataSource.UpdateStoreStartVisit(selStoreID, fullFileName1);
						        String passdLevel = battLevel+"%";
								mDataSource.UpdateStoreVisitBatt(selStoreID, passdLevel);
						        //mDataSource.close();*/

			if (ISNewStore == 0) {


				long syncTIMESTAMP = System.currentTimeMillis();
				Date dateobjNew = new Date(syncTIMESTAMP);
				SimpleDateFormat dfnew = new SimpleDateFormat(
						"dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
				String startTS = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);
				//dbengine.open();

				mDataSource.UpdateStoreStartVisit(selStoreID, startTS);

				String passdLevel = battLevel + "%";
				mDataSource.UpdateStoreVisitBatt(selStoreID, passdLevel);
				mDataSource.updateflgOrderTypeIntblStoreList(selStoreID, 1);

				mDataSource.UpdateStoreEndVisit(selStoreID, startTS);
				//mDataSource.close();
				Intent ready4GetLoc = new Intent(StoreSelection.this, LastVisitDetails.class);

				ready4GetLoc.putExtra("storeID", selStoreID);
				ready4GetLoc.putExtra("selStoreName", selStoreName);
				ready4GetLoc.putExtra("imei", imei);
				ready4GetLoc.putExtra("userDate", userDate);
				ready4GetLoc.putExtra("pickerDate", pickerDate);
				ready4GetLoc.putExtra("startTS", fullFileName1);
				ready4GetLoc.putExtra("bck", 0);

				locStat = 0;


				startActivity(ready4GetLoc);
				finish();
				//}
				//Code If Ends Here
			} else {
				//Code Else Starts Here

				if (IsNewStoreDataCompleteSaved == 1) {
					// TODO Auto-generated method stub
					CommonInfo.flgNewStoreORStoreValidation=1;
					Intent intent = new Intent(StoreSelection.this, AddNewStore_DynamicSectionWise.class);
					//Intent intent = new Intent(StoreSelection.this, Add_New_Store_NewFormat.class);
					//Intent intent = new Intent(StoreSelection.this, Add_New_Store.class);
					intent.putExtra("storeID", selStoreID);
					intent.putExtra("activityFrom", "StoreSelection");
					intent.putExtra("userdate", userDate);
					intent.putExtra("pickerDate", pickerDate);
					intent.putExtra("imei", imei);
					intent.putExtra("rID", rID);
					StoreSelection.this.startActivity(intent);
					finish();
				} else if (IsNewStoreDataCompleteSaved == 0) {
									/*if(valSstatValueAgainstStore==1)
									{
										//Intent nxtP4 = new Intent(StoreSelection.this,ProductList.class);
										//ProductOrderSearch
										Intent nxtP4 = new Intent(StoreSelection.this,ProductOrderFilterSearch.class);
										nxtP4.putExtra("storeID", selStoreID);
										nxtP4.putExtra("SN", selStoreName);
										nxtP4.putExtra("imei", imei);
										nxtP4.putExtra("userdate", userDate);
										nxtP4.putExtra("pickerDate", pickerDate);
										startActivity(nxtP4);
										finish();
									}
									else
									{*/

					long syncTIMESTAMP = System.currentTimeMillis();
					Date dateobjNew = new Date(syncTIMESTAMP);
					SimpleDateFormat dfnew = new SimpleDateFormat(
							"dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
					String startTS = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);
					//dbengine.open();

					mDataSource.UpdateStoreStartVisit(selStoreID, startTS);
					// mDataSource.UpdateStoreEndVisit(selStoreID,
					// fullFileName1);

					//mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "0.00", "" + "0.00", "" + "0.00","" + "NA");

					String passdLevel = battLevel + "%";
					mDataSource.UpdateStoreVisitBatt(selStoreID, passdLevel);
					mDataSource.updateflgOrderTypeIntblStoreList(selStoreID, 1);

					mDataSource.UpdateStoreEndVisit(selStoreID, startTS);
					//mDataSource.close();
					Intent ready4GetLoc = new Intent(StoreSelection.this, LastVisitDetails.class);

					//enableGPSifNot();


					ready4GetLoc.putExtra("storeID", selStoreID);
					ready4GetLoc.putExtra("selStoreName", selStoreName);
					ready4GetLoc.putExtra("imei", imei);
					ready4GetLoc.putExtra("userDate", userDate);
					ready4GetLoc.putExtra("pickerDate", pickerDate);
					ready4GetLoc.putExtra("startTS", fullFileName1);
					ready4GetLoc.putExtra("bck", 0);

					locStat = 0;


					startActivity(ready4GetLoc);
					finish();
					//}
				}

				//Code Else Ends Here
			}


		} else {
			Toast.makeText(getApplicationContext(), R.string.genTermPleaseSelectStore, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_selection);
		tl2 = findViewById(R.id.dynprodtable);
		imei = AppUtils.getIMEI(StoreSelection.this);
		pickerDate = getDateInMonthTextFormat();
		userDate = getDateInMonthTextFormat();
		DA = new DatabaseAssistant(this);

		CommonInfo.ActiveRouteSM = "0";
		locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


		relativeLayout1 = findViewById(R.id.relativeLayout1);

		//TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = AppUtils.getIMEI(StoreSelection.this); /*tManager.getDeviceId();

		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei= CommonInfo.imei.trim();
		}*/


		if (CommonInfo.VanLoadedUnloaded == 1) {
			showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

		} else {


			//mDataSource.open();
			rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
			if (rID.equals("0")) {
				rID = mDataSource.GetNotActiveRouteID();
			}

			ll_manager = findViewById(R.id.ll_manager);
			if (CommonInfo.hmapAppMasterFlags.containsKey("flgShowManagerOnStoreList")) {
				if (CommonInfo.hmapAppMasterFlags.get("flgShowManagerOnStoreList") == 0) {
					ll_manager.setVisibility(View.GONE);
				}
			}
			//mDataSource.close();
			mProgressDialog = new ProgressDialog(StoreSelection.this);
			mProgressDialog.setTitle(getResources().getString(R.string.genTermPleaseWaitNew));
			mProgressDialog.setMessage(getResources().getString(R.string.txtRefreshingData));

			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);

			Date date1 = new Date();
			sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
			passDate = sdf.format(date1);

			//System.out.println("Selctd Date: "+ passDate);

			fDate = passDate.trim();

			img_side_popUp = findViewById(R.id.img_side_popUp);
			img_side_popUp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					open_pop_up();
				}
			});


			getManagersDetail();
			getRouteDetail();

			spinner_manager = findViewById(R.id.spinner_manager);
			ArrayAdapter adapterCategory = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Manager_names);
			adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_manager.setAdapter(adapterCategory);

			spinner_RouteList = findViewById(R.id.spinner_RouteList);
			ArrayAdapter adapterRouteList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Route_names);
			adapterRouteList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_RouteList.setAdapter(adapterRouteList);

			spinner_RouteList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/*seleted_routeIDType=hmapRouteIdNameDetails.get(arg0.getItemAtPosition(arg2).toString());
				//mDataSource.open();
				mDataSource.fnSetAllRouteActiveStatus();
				mDataSource.updateActiveRoute(seleted_routeIDType.split(Pattern.quote("_"))[0], 1);
				//mDataSource.close();

				try {
					fnCreateStoreListOnLoad();
				}
				catch (Exception e)
				{

				}*/
					seleted_routeIDType = hmapRouteIdNameDetails.get(arg0.getItemAtPosition(arg2).toString());
					//mDataSource.open();
					mDataSource.fnSetAllRouteActiveStatus();
					mDataSource.updateActiveRoute(seleted_routeIDType.split(Pattern.quote("_"))[0], 1);
					//mDataSource.close();
					rID = seleted_routeIDType.split(Pattern.quote("_"))[0];

					//fnCreateStoreListOnLoad();
					setStoresList();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});


			rl_for_other = findViewById(R.id.rl_for_other);
			rl_for_other.setVisibility(RelativeLayout.GONE);

			ed_Street = findViewById(R.id.streetid);

			spinner_manager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
										   int arg2, long arg3) {

					selected_manager = arg0.getItemAtPosition(arg2).toString();

					String ManagerID = hmapManagerNameManagerIdDetails.get(selected_manager);


					if (ManagerID.equals("0")) {
						rl_for_other.setVisibility(RelativeLayout.GONE);
						ed_Street.setText("");
						Selected_manager_Id = 0;

					} else if (ManagerID.equals("-99")) {
						Selected_manager_Id = -99;
						rl_for_other.setVisibility(RelativeLayout.VISIBLE);
					} else {
						Selected_manager_Id = Integer.parseInt(ManagerID);

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


			//mDataSource.open();
			int chk = mDataSource.counttblSelectedManagerDetails();
			//mDataSource.close();
			if (chk == 1) {
				String abcd = mDataSource.Fetch_tblSelectedManagerDetails();

				StringTokenizer tokens = new StringTokenizer(String.valueOf(abcd), "_");

				String as = tokens.nextToken().trim();


				String ManagerName = tokens.nextToken().trim();

				int ManagerID = Integer.parseInt(as);

				int selected_choice_index = 0;

				if (ManagerID == 0) {
					spinner_manager.setSelection(0);

				} else if (ManagerID != -99) {
					for (int i1 = 0; i1 < Manager_names.length; i1++) {
						if (Manager_names[i1].equals(ManagerName)) {
							selected_choice_index = i1;
						}
					}
					spinner_manager.setSelection(selected_choice_index);

				} else {
					for (int i1 = 0; i1 < Manager_names.length; i1++) {
						if (Manager_names[i1].equals(ManagerName)) {
							selected_choice_index = i1;
						}
					}
					spinner_manager.setSelection(selected_choice_index);

					//mDataSource.open();
					String OtherName = mDataSource.fetchOtherNameBasicOfManagerID(ManagerID);
					//mDataSource.close();
					rl_for_other.setVisibility(RelativeLayout.VISIBLE);
					ed_Street.setText(OtherName);


				}
			}


			String prsnCvrgId_NdTyp = mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
			int CoverageNodeId = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0]);
			int CoverageNodeType = Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]);
			CommonInfo.flgDrctslsIndrctSls = mDataSource.fnGetflgDrctslsIndrctSlsForDSR(CoverageNodeId, CoverageNodeType);
			if (CommonInfo.hmapAppMasterFlags != null && CommonInfo.hmapAppMasterFlags.size() == 0) {
				CommonInfo.hmapAppMasterFlags = mDataSource.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);

			}

			setUpVariable();


			//
			String routeNametobeSelectedInSpinner = mDataSource.GetActiveRouteDescr();
			int index = 0;
			if (hmapRouteIdNameDetails != null) {


				Set set2 = hmapRouteIdNameDetails.entrySet();
				Iterator iterator = set2.iterator();
				boolean isRouteSelected = false;
				while (iterator.hasNext()) {
					Map.Entry me2 = (Map.Entry) iterator.next();
					if (routeNametobeSelectedInSpinner.equals(me2.getKey())) {
						isRouteSelected = true;
						//Do Nothing
						break;
					}
					index = index + 1;
				}
				if (isRouteSelected) {
					spinner_RouteList.setSelection(index);
				} else {
					spinner_RouteList.setSelection(0);
				}
			}

			setStoresList();


		}


	}

	public void firstTimeLocationTrack() {
		if (pDialog2STANDBY != null) {
			if (pDialog2STANDBY.isShowing()) {


			} else {
				boolean isGPSok = false;
				boolean isNWok = false;
				isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if (!isGPSok && !isNWok) {
					try {
						showSettingsAlert();
					} catch (Exception e) {

					}
					isGPSok = false;
					isNWok = false;
				} else {
					locationRetrievingAndDistanceCalculating();
				}
			}
		} else {
			boolean isGPSok = false;
			boolean isNWok = false;
			isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSok && !isNWok) {
				try {
					showSettingsAlert();
				} catch (Exception e) {

				}
				isGPSok = false;
				isNWok = false;
			} else {
				locationRetrievingAndDistanceCalculating();
			}

		}
	}


	public void setStoresList() {
		if (tl2 != null) {
			tl2.removeAllViews();
		}
		//mDataSource.open();

		//System.out.println("Arjun has rID :"+rID);

		storeList = mDataSource.FetchStoreList(rID);
		storeRouteIdType = mDataSource.FetchStoreRouteIdType(rID);
		storeStatus = mDataSource.FetchStoreStatus(rID);

		hmapStoreLatLongDistanceFlgRemap = mDataSource.fnGeStoreList(CommonInfo.DistanceRange);
		//mDataSource.close();

		storeCode = new String[storeList.length];
		storeName = new String[storeList.length];

		for (int splitval = 0; splitval <= (storeList.length - 1); splitval++) {
			StringTokenizer tokens = new StringTokenizer(String.valueOf(storeList[splitval]), "_");

			storeCode[splitval] = tokens.nextToken().trim();
			storeName[splitval] = tokens.nextToken().trim() + "_" + storeList[splitval].split(Pattern.quote("_"))[2];

		}


		float density = getResources().getDisplayMetrics().density;

		LinearLayout.LayoutParams paramRB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int) (10 * density));


		LayoutInflater inflater = getLayoutInflater();
		for (int current = 0; current < storeList.length; current++) {

			final TableRow row = (TableRow) inflater.inflate(R.layout.table_row1, tl2, false);

			final RadioButton rb1 = row.findViewById(R.id.rg1StoreName);
			final CheckBox check1 = row.findViewById(R.id.check1);
			final ImageView edit_store = row.findViewById(R.id.edit_store);
			edit_store.setTag(storeCode[current]);
			if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreDetailsEdit")) {
				if (CommonInfo.hmapAppMasterFlags.get("flgStoreDetailsEdit") == 1) {
					edit_store.setVisibility(View.VISIBLE);
				} else {
					edit_store.setVisibility(View.GONE);
				}
			} else {
				edit_store.setVisibility(View.GONE);
			}
			edit_store.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(StoreSelection.this, StoreEditActivity.class);
					//Intent intent = new Intent(StoreSelection.this, Add_New_Store_NewFormat.class);
					//Intent intent = new Intent(StoreSelection.this, Add_New_Store.class);
					intent.putExtra("storeID", edit_store.getTag().toString());
					intent.putExtra("activityFrom", "StoreSelection");
					intent.putExtra("userdate", userDate);
					intent.putExtra("pickerDate", pickerDate);
					intent.putExtra("imei", imei);
					intent.putExtra("rID", rID);
					StoreSelection.this.startActivity(intent);
					finish();
				}
			});
			final CheckBox check2 = row.findViewById(R.id.check2);

			rb1.setTag(storeCode[current]);
			rb1.setText("  " + storeName[current].split(Pattern.quote("_"))[0]);
			rb1.setTextSize(14.0f);
			rb1.setChecked(false);

			check1.setTag(storeCode[current]);
			check1.setChecked(false);
			check1.setEnabled(false);

			check2.setTag(storeCode[current]);
			check2.setChecked(false);
			check2.setEnabled(false);
			row.setTag(storeRouteIdType[current]);

			if ((storeStatus[current].split(Pattern.quote("~"))[0]).equals("4")) {
				rb1.setEnabled(false);
				rb1.setTypeface(null, Typeface.BOLD);
				rb1.setTextColor(this.getResources().getColor(R.color.green_submitted));
			}

			if ((storeStatus[current].split(Pattern.quote("~"))[0]).equals("3") || (storeStatus[current].split(Pattern.quote("~"))[0]).equals("5") || (storeStatus[current].split(Pattern.quote("~"))[0]).equals("6")) {
				if ((storeStatus[current].split(Pattern.quote("~"))[1]).equals("1")) {
					//rb1.setTypeface(null, Typeface.BOLD);
					// rb1.setTextColor(Color.BLUE);
				}
				else{
					rb1.setTypeface(null, Typeface.BOLD);
				}
				rb1.setTextColor(this.getResources().getColor(R.color.static_text_color));
			}

			if (storeName[current].split(Pattern.quote("_"))[1].equals("1")) {
				rb1.setTypeface(null, Typeface.BOLD);
				rb1.setTextColor(this.getResources().getColor(android.R.color.holo_orange_dark));
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

			rb1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					for (int xc = 0; xc < storeList.length; xc++) {
						TableRow dataRow = (TableRow) tl2.getChildAt(xc);

						RadioButton child1;
						CheckBox child2;
						CheckBox child3;

						child1 = dataRow.findViewById(R.id.rg1StoreName);
						child2 = dataRow.findViewById(R.id.check1);
						child3 = dataRow.findViewById(R.id.check2);

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
					check1.setOnClickListener(new OnClickListener() {

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
								SimpleDateFormat df = new SimpleDateFormat(
										"dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
								String startTS = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);

								Date currDate = new Date();
								SimpleDateFormat currDateFormat = new SimpleDateFormat(
										"dd-MM-yyyy", Locale.ENGLISH);
								String currSysDate = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_FORMAT);

								if (!currSysDate.equals(pickerDate)) {
									fullFileName1 = pickerDate + " 12:00:00";
								}
								//mDataSource.open();
								mDataSource.updateCloseflg(Sid, 1);
								System.out.println("DateTimeNitish 1");
								mDataSource.UpdateStoreStartVisit(selStoreID, startTS);

								String passdLevel = battLevel + "%";
								mDataSource.UpdateStoreVisitBatt(selStoreID, passdLevel);

								mDataSource.UpdateStoreEndVisit(selStoreID, startTS);
								//mDataSource.close();

							} else {

								//mDataSource.open();
								mDataSource.updateCloseflg(Sid, 0);
								//mDataSource.delStoreCloseNextData(selStoreID);

								//mDataSoureUpdateCloseNextStoreData(Sid);

								/*mDataSource.UpdateStoreStartVisit(selStoreID,"");
								mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "", "" + "", "" + "","" + "");
								mDataSource.UpdateStoreVisitBatt(selStoreID,"");
								mDataSource.UpdateStoreEndVisit(selStoreID,"");*/

								//mDataSource.close();
							}

						}
					});

					check2.setOnClickListener(new OnClickListener() {

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
								SimpleDateFormat df = new SimpleDateFormat(
										"dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
								String startTS = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);

								Date currDate = new Date();
								SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
								String currSysDate = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_FORMAT);

								if (!currSysDate.equals(pickerDate)) {
									fullFileName1 = pickerDate + " 12:00:00";
								}
								//mDataSource.open();
								//System.out.println("DateTimeNitish2");
								mDataSource.updateNextDayflg(Sid, 1);

								mDataSource.UpdateStoreStartVisit(selStoreID, startTS);
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
								System.out.println("2nd unchecked with Store ID :" + Sid);
								//mDataSource.open();
								mDataSource.updateNextDayflg(Sid, 0);
								//mDataSource.delStoreCloseNextData(selStoreID);

								//mDataSource.UpdateCloseNextStoreData(Sid);

								/*mDataSource.UpdateStoreStartVisit(selStoreID,"");
								mDataSource.UpdateStoreActualLatLongi(selStoreID,"" + "", "" + "", "" + "","" + "");
								mDataSource.UpdateStoreVisitBatt(selStoreID,"");
								mDataSource.UpdateStoreEndVisit(selStoreID,"");*/

								//mDataSource.close();
							}

						}
					});

				}
			});


			tl2.addView(row);

		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {

			//mDataSource.open();
			String Noti_textWithMsgServerID = mDataSource.fetchNoti_textFromtblPDANotificationMaster();
			//mDataSource.close();
			System.out.println("Sunil Tty Noti_textWithMsgServerID :" + Noti_textWithMsgServerID);
			if (!Noti_textWithMsgServerID.equals("Null")) {
				StringTokenizer token = new StringTokenizer(Noti_textWithMsgServerID, "_");

				MsgServerID = Integer.parseInt(token.nextToken().trim());
				Noti_text = token.nextToken().trim();


				if (Noti_text.equals("") || Noti_text.equals("Null")) {

				} else {


					final AlertDialog builder = new AlertDialog.Builder(StoreSelection.this).create();


					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View openDialog = inflater.inflate(R.layout.custom_dialog, null);
					openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

					builder.setCancelable(false);
					TextView header_text = openDialog.findViewById(R.id.txt_header);
					final TextView msg = openDialog.findViewById(R.id.msg);

					final Button ok_but = openDialog.findViewById(R.id.but_yes);
					final Button cancel = openDialog.findViewById(R.id.but_no);

					cancel.setVisibility(View.GONE);
					header_text.setText(getResources().getString(R.string.AlertDialogHeaderMsg));
					msg.setText(Noti_text);

					ok_but.setText(getResources().getString(R.string.AlertDialogOkButton));

					builder.setView(openDialog, 0, 0, 0, 0);

					ok_but.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub

							long syncTIMESTAMP = System.currentTimeMillis();
							Date dateobj = new Date(syncTIMESTAMP);
							SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
							String Noti_ReadDateTime = TimeUtils.getNetworkDateTime(StoreSelection.this, TimeUtils.DATE_TIME_FORMAT);

							//mDataSource.open();
							mDataSource.updatetblPDANotificationMaster(MsgServerID, Noti_text, 0, Noti_ReadDateTime, 3);
							//mDataSource.close();

							try {
								//mDataSource.open();
								int checkleftNoti = mDataSource.countNumberOFNotificationtblPDANotificationMaster();
								if (checkleftNoti > 0) {
									String Noti_textWithMsgServerID = mDataSource.fetchNoti_textFromtblPDANotificationMaster();
									System.out.println("Sunil Tty Noti_textWithMsgServerID :" + Noti_textWithMsgServerID);
									if (!Noti_textWithMsgServerID.equals("Null")) {
										StringTokenizer token = new StringTokenizer(Noti_textWithMsgServerID, "_");

										MsgServerID = Integer.parseInt(token.nextToken().trim());
										Noti_text = token.nextToken().trim();

										//mDataSource.close();
										if (Noti_text.equals("") || Noti_text.equals("Null")) {

										} else {
											msg.setText(Noti_text);
										}
									}

								} else {
									builder.dismiss();
								}

							} catch (Exception e) {

							} finally {
								//mDataSource.close();

							}


						}
					});


					builder.show();


				}
			}
		} catch (Exception e) {

		}

		imei = AppUtils.getIMEI(mContext);



		/*String allLoctionDetails=  mDataSource.getLocationDetails();

		if(allLoctionDetails.equals("0"))
		{
			firstTimeLocationTrack();
		}*/



		/*//mDataSource.open();
		String getPDADate = mDataSource.fnGetPdaDate();
		String getServerDate = mDataSource.fnGetServerDate();
		//mDataSource.close();
		if (!getPDADate.equals(""))
		{
			if(!getServerDate.equals(getPDADate))
			{

				if(mDataSource.fnCheckForPendingImages()==1)
				{
					getPrevioisDateData();
					return;
				}
				else if(checkImagesInFolder()>0)
				{
					getPrevioisDateData();
					return;
				}
				else if(mDataSource.fnCheckForPendingXMLFilesInTable()==1)
				{
					getPrevioisDateData();
					return;
				}
				else if(checkXMLFilesInFolder()>0)
				{
					getPrevioisDateData();
					return;
				}
				else
				{
                 finish();
				}


			}

		}*/

	}

	public int checkImagesInFolder() {
		int totalFiles = 0;
		File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

		String[] AllFilesName = checkNumberOfFiles(del);

		if (AllFilesName != null && AllFilesName.length > 0) {
			totalFiles = AllFilesName.length;
		}
		return totalFiles;
	}

	public int checkXMLFilesInFolder() {
		int totalFiles = 0;
		File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

		String[] AllFilesName = checkNumberOfFiles(del);

		if (AllFilesName != null && AllFilesName.length > 0) {
			totalFiles = AllFilesName.length;
		}
		return totalFiles;
	}

	public void getPrevioisDateData() {
		//mDataSource.open();
		String getPDADate = mDataSource.fnGetPdaDate();
		//mDataSource.close();
		if (!getPDADate.equals("")) {
            /*Date date2 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String fDate = sdf.format(date2).toString().trim();
            if(!fDate.equals(getPDADate))
            {*/
			if (isOnline()) {
				try {
					if (mDataSource.fnCheckForPendingImages() == 1) {
						new ImageUploadAsyncTask(this).execute();
					} else if (checkImagesInFolder() > 0) {
						new ImageUploadFromFolderAsyncTask(this).execute();
					} else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
						new XMLFileUploadAsyncTask(this).execute();
					} else if (checkXMLFilesInFolder() > 0) {
						new XMLFileUploadFromFolderAsyncTask(this).execute();
					} else {
						//mDataSource.open();
						mDataSource.reCreateDB();
						//mDataSource.close();
						//SplashScreen.CheckUpdateVersion cuv = new SplashScreen.CheckUpdateVersion();
						//cuv.execute();
						finish();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

			}

			// }
		}
	}


	protected void open_pop_up() {
		dialog = new Dialog(StoreSelection.this);
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
		butn_Census_report.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StoreSelection.this, AddedOutletSummaryReportActivity.class);
				startActivity(intent);
				// finish();
			}
		});

		final Button btn_uploadPending_data = dialog.findViewById(R.id.btn_uploadPending_data);


		final Button butn_refresh_data = dialog.findViewById(R.id.butn_refresh_data);
		final Button but_day_end = dialog.findViewById(R.id.mainImg1);
		final Button changeRoute = dialog.findViewById(R.id.changeRoute);
		changeRoute.setVisibility(View.GONE);
		final Button btnewAddedStore = dialog.findViewById(R.id.btnewAddedStore);


		final Button btnExecution = dialog.findViewById(R.id.btnExecution);
		if (CommonInfo.flgDrctslsIndrctSls == 2) {
			// btnExecution.setVisibility(View.VISIBLE);
		}
		btnExecution.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				Date currDate = new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

				currSysDate = currDateFormat.format(currDate);
				Intent storeIntent = new Intent(StoreSelection.this, InvoiceStoreSelection.class);
				storeIntent.putExtra("imei", imei);
				storeIntent.putExtra("userDate", currSysDate);
				storeIntent.putExtra("pickerDate", fDate);

				startActivity(storeIntent);
			}
		});


		final Button btnRemainingStockStatus = dialog.findViewById(R.id.btnRemainingStockStatus);
		if (CommonInfo.flgDrctslsIndrctSls == 2) {
			btnRemainingStockStatus.setVisibility(View.GONE);
		}
		btnRemainingStockStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				btnRemainingStockStatus.setBackgroundColor(Color.GREEN);
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, RemainingStockStatusReport.class);
				// Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("back", "0");
				startActivity(intent);
				finish();

			}
		});


		final Button butHome = dialog.findViewById(R.id.butHome);
		butHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, AllButtonActivity.class);
				startActivity(intent);
				finish();
			}
		});

		final Button btnTargetVsAchieved = dialog.findViewById(R.id.btnTargetVsAchieved);
		if (CommonInfo.flgDrctslsIndrctSls == 2) {
			btnTargetVsAchieved.setVisibility(View.VISIBLE);
		}
		btnTargetVsAchieved.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, TargetVsAchievedActivity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("Pagefrom", "1");
				//intent.putExtra("back", "0");
				startActivity(intent);
				finish();


			}
		});


		btnewAddedStore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, ViewAddedStore.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				//intent.putExtra("back", "0");
				startActivity(intent);
				finish();

			}
		});
		final Button btnVersion = dialog.findViewById(R.id.btnVersion);
		btnVersion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				btnVersion.setBackgroundColor(Color.GREEN);
				dialog.dismiss();
			}
		});

		//mDataSource.open();
		String ApplicationVersion = DBHelper.AppVersionID;
		//mDataSource.close();
		btnVersion.setText(getResources().getString(R.string.VersionNo) + ApplicationVersion);

		// Version No-V12

		final Button but_SalesSummray = dialog.findViewById(R.id.btnSalesSummary);
		but_SalesSummray.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				but_SalesSummray.setBackgroundColor(Color.GREEN);
				dialog.dismiss();

				SharedPreferences sharedPrefReport = getSharedPreferences("Report", MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPrefReport.edit();
				editor.putString("fromPage", "2");
				editor.commit();

				Intent intent = new Intent(StoreSelection.this, DetailReportSummaryActivity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("back", "0");
				intent.putExtra("fromPage", "StoreSelection");
				startActivity(intent);
				finish();

			}
		});


		final Button btnChangeLanguage = dialog.findViewById(R.id.btnChangeLanguage);
		if (CommonInfo.flgLangChangeReuired == 1) {
			btnChangeLanguage.setVisibility(View.VISIBLE);
		} else {
			btnChangeLanguage.setVisibility(View.GONE);
		}
		btnChangeLanguage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				final Dialog dialogLanguage = new Dialog(StoreSelection.this);
				dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

				dialogLanguage.setCancelable(false);
				dialogLanguage.setContentView(R.layout.language_popup);

				TextView textviewEnglish = dialogLanguage.findViewById(R.id.textviewEnglish);
				TextView textviewHindi = dialogLanguage.findViewById(R.id.textviewHindi);
				TextView textviewGujrati = dialogLanguage.findViewById(R.id.textviewGujrati);

				textviewEnglish.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogLanguage.dismiss();
						setLanguage("en");
					}
				});
				textviewHindi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogLanguage.dismiss();
						setLanguage("hi");
					}
				});
				textviewGujrati.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogLanguage.dismiss();
						setLanguage("gu");
					}
				});
				dialogLanguage.show();


			}
		});


		final Button btnCheckTodayInvoice = dialog.findViewById(R.id.btnCheckTodayInvoice);
		if (CommonInfo.flgDrctslsIndrctSls == 2) {
			btnCheckTodayInvoice.setVisibility(View.GONE);
		}
		btnCheckTodayInvoice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				but_SalesSummray.setBackgroundColor(Color.GREEN);
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, CheckDatabaseData.class);
				// Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("back", "0");
				startActivity(intent);
				finish();

			}
		});


		final Button btnCheckTodayOrder = dialog.findViewById(R.id.btnCheckTodayOrder);
		if (CommonInfo.flgDrctslsIndrctSls == 1) {
			btnCheckTodayOrder.setVisibility(View.GONE);
		}
		btnCheckTodayOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				but_SalesSummray.setBackgroundColor(Color.GREEN);
				dialog.dismiss();
				Intent intent = new Intent(StoreSelection.this, CheckDatabaseDataOrderBooking.class);
				// Intent intent = new Intent(StoreSelection.this, DetailReport_Activity.class);
				intent.putExtra("imei", imei);
				intent.putExtra("userDate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("rID", rID);
				intent.putExtra("back", "0");
				startActivity(intent);
				finish();

			}
		});
		btn_uploadPending_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
				if (isOnline()) {


					/*if(mDataSource.fnCheckForPendingImages()==1)
					{
						ImageSync task = new ImageSync(StoreSelection.this);
						task.execute();

					}
					else if(mDataSource.fnCheckForPendingXMLFilesInTable()==1)
					{
						new FullSyncDataNow(StoreSelection.this).execute();

					}
					else
					{
						showInfoSingleButtonError(getResources().getString(R.string.NoPendingDataMsg));
					}
*/
					if (mDataSource.fnCheckForPendingImages() > 0) {
						ImageSync task = new ImageSync(StoreSelection.this);
						task.execute();

					} else if (mDataSource.fnCheckForPendingXMLFilesInTable() == 1) {
						new FullSyncDataNow(StoreSelection.this).execute();

					} else {
						showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));
					}
				} else {
					showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
				}
			}
		});

		but_day_end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//checking that dsr fill registration form or not for flag 0
				String PersonNameAndFlgRegistered = mDataSource.fnGetPersonNameAndFlgRegistered();
				String personName = "";
				String FlgRegistered = "";
				int DsrRegTableCount = 0;
				DsrRegTableCount = mDataSource.fngetcounttblDsrRegDetails();
				if (!PersonNameAndFlgRegistered.equals("0")) {
					personName = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
					FlgRegistered = PersonNameAndFlgRegistered.split(Pattern.quote("^"))[1];
				}

				if (FlgRegistered.equals("0") && DsrRegTableCount == 0) {
					AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(StoreSelection.this);
					alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));
					alertDialogNoConn.setMessage(getResources().getString(R.string.Dsrmessage));
					alertDialogNoConn.setCancelable(false);
					alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(StoreSelection.this, DSR_Registration.class);
							intent.putExtra("IntentFrom", "DAYEND");
							intent.putExtra("imei", imei);
							intent.putExtra("userDate", userDate);
							intent.putExtra("pickerDate", pickerDate);

							startActivity(intent);
							finish();


						}
					});
					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();

				} else {
					but_day_end.setBackgroundColor(Color.GREEN);
					closeList = 0;
					valDayEndOrChangeRoute = 1;
					//checkbuttonclick=2;

						/*if(isOnline())
						{

						}
						else
						{
							showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
							return;

						}
						//mDataSource.open();
						whereTo = "11";
						//////System.out.println("Abhinav store Selection  Step 1");
						//////System.out.println("StoreList2Procs(before): " + StoreList2Procs.length);
						StoreList2Procs = mDataSource.ProcessStoreReq();
						//////System.out.println("StoreList2Procs(after): " + StoreList2Procs.length);

						if (StoreList2Procs.length != 0) {
							//whereTo = "22";
							//////System.out.println("Abhinav store Selection  Step 2");
							midPart();
							dayEndCustomAlert(1);
							//showPendingStorelist(1);
							//mDataSource.close();

						} else if (mDataSource.GetLeftStoresChk() == true)
						{
							//////System.out.println("Abhinav store Selection  Step 7");
							//enableGPSifNot();
							// showChangeRouteConfirm();
							DayEnd();
							//mDataSource.close();
						}

						else {
							DayEndWithoutalert();
						}*/


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
						showAlertSingleButtonInfo(getResources().getString(R.string.NoPendingDataMsg));

					}
					dialog.dismiss();
				}


			}
		});


		changeRoute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeRoute.setBackgroundColor(Color.GREEN);
				valDayEndOrChangeRoute = 2;

				if (isOnline()) {
				} else {
					showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
					return;

				}
				closeList = 0;
				whereTo = "11";
				//checkbuttonclick=1;

				// ////System.out.println("closeList: "+closeList);
				// chk if flag 2/3 found
				//mDataSource.open();
				StoreList2Procs = mDataSource.ProcessStoreReq();

				// int picsCHK = mDataSource.getExistingPicNosOnRemStore();
				// String[] sIDs2Alert =
				// mDataSource.getStoreNameExistingPicNosOnRemStore();

				if (StoreList2Procs.length != 0) {// && picsCHK <= 0


					midPart();
					dayEndCustomAlert(2);
					//showPendingStorelist(2);

				} else if (mDataSource.GetLeftStoresChk() == true) {// && picsCHK
					// <= 0

					//enableGPSifNot();


					showChangeRouteConfirmWhenNoStoreisLeftToSubmit();
					//showChangeRouteConfirm();

				} else {
					// show dialog for clear..clear + tranx to launcher
					//showChangeRouteConfirmWhenNoStoreisLeftToSubmit();
					DayEndWithoutalert();
					//showChangeRouteConfirm();
				}

				//mDataSource.close();
				dialog.dismiss();
			}
		});


		butn_refresh_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				butn_refresh_data.setBackgroundColor(Color.GREEN);
				if (CommonInfo.VanLoadedUnloaded == 1) {
					showAlertSingleWareHouseStockconfirButtonInfo("Stock is updated, please confirm the warehouse stock first.");

				} else {
					if (isOnline()) {
						fnStartProcedureOfRefreshData();

					} else {
						showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
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
		AlertDialog.Builder alertDialogBuilderNEw11 = new AlertDialog.Builder(StoreSelection.this);

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
						CommonFunction.getAllMasterTableModelData(StoreSelection.this, imei, CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

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
	/* public void dayEndCustomAlert(int flagWhichButtonClicked)
	 {
		 final Dialog dialog = new Dialog(StoreSelection.this,R.style.AlertDialogDayEndTheme);

		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.day_end_custom_alert);
			if(flagWhichButtonClicked==1)
			{
				dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteDayEnd);
			}
			else if(flagWhichButtonClicked==2)
			{
				dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteChangeRoute);
			}



				LinearLayout ll_product_not_submitted=(LinearLayout) dialog.findViewById(R.id.ll_product_not_submitted);
				mSelectedItems.clear();
				final String[] stNames4List = new String[stNames.size()];
				 checks=new boolean[stNames.size()];
				stNames.toArray(stNames4List);

				for(int cntPendingList=0;cntPendingList<stNames4List.length;cntPendingList++)
				{
					LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View viewAlertProduct=inflater.inflate(R.layout.day_end_alrt,null);
					final TextView txtVw_product_name=(TextView) viewAlertProduct.findViewById(R.id.txtVw_product_name);
					txtVw_product_name.setText(stNames4List[cntPendingList]);
					txtVw_product_name.setTextColor(this.getResources().getColor(R.color.green_submitted));
					final ImageView img_to_be_submit=(ImageView) viewAlertProduct.findViewById(R.id.img_to_be_submit);
					img_to_be_submit.setTag(cntPendingList);

					final ImageView img_to_be_cancel=(ImageView) viewAlertProduct.findViewById(R.id.img_to_be_cancel);
					img_to_be_cancel.setTag(cntPendingList);
					img_to_be_submit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {


							if(!checks[Integer.valueOf(img_to_be_submit.getTag().toString())])
							{
								img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_hover) );
								img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_normal) );
								txtVw_product_name.setTextColor(getResources().getColor(R.color.green_submitted));
								mSelectedItems.add(stNames4List[Integer.valueOf(img_to_be_submit.getTag().toString())]);
								checks[Integer.valueOf(img_to_be_submit.getTag().toString())]=true;
							}


						}
					});

					img_to_be_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mSelectedItems.contains(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())]))
							{
								if(checks[Integer.valueOf(img_to_be_cancel.getTag().toString())])
								{

									img_to_be_submit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.select_normal) );
									img_to_be_cancel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_hover) );
									txtVw_product_name.setTextColor(Color.RED);
									mSelectedItems.remove(stNames4List[Integer.valueOf(img_to_be_cancel.getTag().toString())]);
									checks[Integer.valueOf(img_to_be_cancel.getTag().toString())]=false;
								}

							}

						}
					});
					mSelectedItems.add(stNames4List[cntPendingList]);
					 checks[cntPendingList]=true;
					 ll_product_not_submitted.addView(viewAlertProduct);
				}


				Button btnSubmit=(Button) dialog.findViewById(R.id.btnSubmit);
				Button btn_cancel_Back=(Button) dialog.findViewById(R.id.btn_cancel_Back);
				btnSubmit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mSelectedItems.size() == 0) {

							DayEnd();
						//surbhi

						}

						else {

							int countOfOrderNonSelected=0;
							for(int countForFalse=0;countForFalse<checks.length;countForFalse++)
							{
								if(checks[countForFalse]==false)
								{
									countOfOrderNonSelected++;
								}

							}
							if(countOfOrderNonSelected>0)
							{
								confirmationForSubmission(String.valueOf(countOfOrderNonSelected));
							}

							else
							{


								whatTask = 2;
								// -- Route Info Exec()
								try {

									new bgTasker().execute().get();
								} catch (InterruptedException e) {
									e.printStackTrace();
									//System.out.println(e);
								} catch (ExecutionException e) {
									e.printStackTrace();
									//System.out.println(e);
								}
								// --
							}

						}

					}
				});

				btn_cancel_Back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						valDayEndOrChangeRoute=0;
						dialog.dismiss();
					}
				});

				dialog.setCanceledOnTouchOutside(false);


				dialog.show();




	 }*/

	public void dayEndCustomAlert(int flagWhichButtonClicked) {
		final Dialog dialog = new Dialog(StoreSelection.this, R.style.AlertDialogDayEndTheme);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.day_end_custom_alert);
		if (flagWhichButtonClicked == 1) {
			dialog.setTitle(R.string.genStoreListWhoseDataIsNotYetSubmitted);

			//dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteDayEnd);
		} else if (flagWhichButtonClicked == 2) {
			dialog.setTitle(R.string.genTermSelectStoresPendingToCompleteChangeRoute);
		}


		LinearLayout ll_product_not_submitted = dialog.findViewById(R.id.ll_product_not_submitted);
		mSelectedItems.clear();
		final String[] stNames4List = new String[stNames.size()];
		checks = new boolean[stNames.size()];
		stNames.toArray(stNames4List);

		for (int cntPendingList = 0; cntPendingList < stNames4List.length; cntPendingList++) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View viewAlertProduct = inflater.inflate(R.layout.day_end_alrt, null);
			final TextView txtVw_product_name = viewAlertProduct.findViewById(R.id.txtVw_product_name);
			txtVw_product_name.setText(stNames4List[cntPendingList]);
			txtVw_product_name.setTextColor(this.getResources().getColor(R.color.green_submitted));
			final ImageView img_to_be_submit = viewAlertProduct.findViewById(R.id.img_to_be_submit);
			img_to_be_submit.setTag(cntPendingList);
			img_to_be_submit.setVisibility(View.INVISIBLE);
			final ImageView img_to_be_cancel = viewAlertProduct.findViewById(R.id.img_to_be_cancel);
			img_to_be_cancel.setTag(cntPendingList);

			img_to_be_cancel.setVisibility(View.INVISIBLE);
			img_to_be_submit.setOnClickListener(new OnClickListener() {

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

			img_to_be_cancel.setOnClickListener(new OnClickListener() {

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


		Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
		Button btn_cancel_Back = dialog.findViewById(R.id.btn_cancel_Back);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSelectedItems.size() == 0) {

					DayEnd();


				} else {

					int countOfOrderNonSelected = 0;
					for (int countForFalse = 0; countForFalse < checks.length; countForFalse++) {
						if (checks[countForFalse] == false) {
							countOfOrderNonSelected++;
						}

					}
					if (countOfOrderNonSelected > 0) {
						confirmationForSubmission(String.valueOf(countOfOrderNonSelected));
					} else {


						whatTask = 2;
						// -- Route Info Exec()
						try {

							new bgTasker().execute().get();
						} catch (InterruptedException e) {
							e.printStackTrace();
							//System.out.println(e);
						} catch (ExecutionException e) {
							e.printStackTrace();
							//System.out.println(e);
						}
						// --
					}

				}

			}
		});

		btn_cancel_Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				valDayEndOrChangeRoute = 0;
				dialog.dismiss();
			}
		});

		dialog.setCanceledOnTouchOutside(false);


		dialog.show();


	}

	public void confirmationForSubmission(String number) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreSelection.this);

		// Setting Dialog Title
		alertDialog.setTitle(getResources().getString(R.string.txtConfirmCancel));

		// Setting Dialog Message
		if (1 < Integer.valueOf(number)) {
			alertDialog.setMessage(getResources().getString(R.string.txtYouWant) + number + getResources().getString(R.string.txtOrderCancel));
		} else {
			alertDialog.setMessage(getResources().getString(R.string.txtYouWant) + number + getResources().getString(R.string.txtOrderCancelQues));
		}


		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.cancel_hover);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {


				whatTask = 2;
				// -- Route Info Exec()
				try {

					new bgTasker().execute().get();
				} catch (InterruptedException e) {
					e.printStackTrace();
					//System.out.println(e);
				} catch (ExecutionException e) {
					e.printStackTrace();
					//System.out.println(e);
				}
				// --


			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();
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

	public void showAlertRefreshSucessFully(String title, String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreSelection.this);

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(msg);
		alertDialog.setIcon(R.drawable.info_icon);
		alertDialog.setCancelable(false);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {


				dialog.dismiss();
				Intent i = new Intent(StoreSelection.this, LauncherActivity.class);
				i.putExtra("imei", imei);
				startActivity(i);
				finish();
			}
		});


		// Showing Alert Message
		alertDialog.show();
	}

	public void showAlertException(String title, String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreSelection.this);

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
					CommonFunction.getAllMasterTableModelData(StoreSelection.this, imei, CommonInfo.RegistrationID, "Please wait Refreshing data.", 1);

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
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.AlertDialogHeaderMsg))
				.setMessage(msg)
				.setCancelable(false)
				.setIcon(R.drawable.info_ico)
				.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
						Intent intent = new Intent(StoreSelection.this, AllButtonActivity.class);
						startActivity(intent);
						finish();
					}
				}).create().show();
	}


	private void getManagersDetail() {

		hmapManagerNameManagerIdDetails = mDataSource.fetch_Manager_List();

		int index = 0;
		if (hmapManagerNameManagerIdDetails != null) {
			Manager_names = new String[hmapManagerNameManagerIdDetails.size()];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapManagerNameManagerIdDetails);
			Set set2 = map.entrySet();
			Iterator iterator = set2.iterator();
			while (iterator.hasNext()) {
				Map.Entry me2 = (Map.Entry) iterator.next();
				Manager_names[index] = me2.getKey().toString();
				index = index + 1;
			}
		}


	}


	public void saveLocale(String lang) {


		SharedPreferences prefs = getSharedPreferences("LanguagePref", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("Language", lang);
		editor.commit();
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
		Intent refresh = new Intent(StoreSelection.this, LauncherActivity.class);
		startActivity(refresh);
		finish();

	}

	private void getRouteDetail() {

		hmapRouteIdNameDetails = mDataSource.fetch_Route_List();

		int index = 0;
		if (hmapRouteIdNameDetails != null) {
			Route_names = new String[hmapRouteIdNameDetails.size()];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapRouteIdNameDetails);
			Set set2 = map.entrySet();
			Iterator iterator = set2.iterator();
			while (iterator.hasNext()) {
				Map.Entry me2 = (Map.Entry) iterator.next();
				Route_names[index] = me2.getKey().toString();
				index = index + 1;
			}
		}


	}

	public String convertExponential(double firstNumber) {
		String secondNumberAsString = String.format("%.10f", firstNumber);
		return secondNumberAsString;
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
		nameValuePairs.add(new BasicNameValuePair("image", image_str));
		nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
		nameValuePairs.add(new BasicNameValuePair("storeID", tempIdImage));

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
			if(response.getStatusLine().getStatusCode()==200)
			{
				mDataSource.updateSSttImage(fileName, 4);
				mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
				mDataSource.updateSSttStoreCheckImageImage(fileName, 4);
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
			MediaScannerConnection.scanFile(StoreSelection.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

				public void onScanCompleted(String path, Uri uri) {

				}
			});
		} else {
			Log.e("-->", " < 14");
			StoreSelection.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
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
				delXML(xmlForWeb[0]);


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
		File file1 = new File(delPath.replace(".xml", ".zip"));
		file1.delete();
	}

	@Override
	public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {
		//mDataSource.open();

		mDataSource.deleteLocationTable();
		mDataSource.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), address, city, pincode, state, fnAccurateProvider, GpsLat, GpsLong, GpsAccuracy, NetwLat, NetwLong, NetwAccuracy, FusedLat, FusedLong, FusedAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt);
		//mDataSource.close();
		if (addStoreBtnClick) {
			if (pDialog2STANDBY != null) {
				if (pDialog2STANDBY.isShowing()) {
					pDialog2STANDBY.dismiss();
					pDialog2STANDBY = null;
				}
			}
			addStoreBtnClick = false;
			String slctdRouteName = mDataSource.GetRouteNameBasedOnRouteID(rID);
			//mDataSource.close();
			CommonInfo.flgNewStoreORStoreValidation=1;
			Intent intent = new Intent(StoreSelection.this, AddNewStore_DynamicSectionWise.class);
			//Intent intent = new Intent(StoreSelection.this, Add_New_Store_NewFormat.class);
			//Intent intent = new Intent(StoreSelection.this, Add_New_Store.class);
			intent.putExtra("storeID", "0");
			intent.putExtra("StoreName", "NA");
			intent.putExtra("activityFrom", "StoreSelection");
			intent.putExtra("userdate", userDate);
			intent.putExtra("pickerDate", pickerDate);
			intent.putExtra("imei", imei);
			intent.putExtra("rID", rID);
			intent.putExtra("FLAG_NEW_UPDATE", "NEW");
			intent.putExtra("CurrntRouteName", slctdRouteName);
			//intent.putExtra("activityFrom", "StoreSelection");
			StoreSelection.this.startActivity(intent);
			finish();
		} else {

			hmapOutletListForNear = mDataSource.fnGetALLOutletMstr();
			System.out.println("SHIVAM" + hmapOutletListForNear);
			if (hmapOutletListForNear != null) {

				for (Map.Entry<String, String> entry : hmapOutletListForNear.entrySet()) {
					int DistanceBWPoint = 1000;
					String outID = entry.getKey().trim();
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
					String outID = entry.getKey().trim();
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

                       /* Intent intent =new Intent(LauncherActivity.this,StorelistActivity.class);
                        LauncherActivity.this.startActivity(intent);
                        finish();*/

			} else {


				if (tl2.getChildCount() > 0) {
					tl2.removeAllViews();
					// dynamcDtaContnrScrollview.removeAllViews();
					//addViewIntoTable();
					setStoresList();
				} else {
					//addViewIntoTable();
					setStoresList();
				}

			}
			if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
				pDialog2STANDBY.dismiss();
			}
		}


	}

	public class standBYtask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private class bgTasker extends AsyncTask<Void, Void, Void> {


		@Override
		protected Void doInBackground(Void... params) {

			try {
				//mDataSource.open();
				String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

				//	mDataSource.UpdateTblDayStartEndDetails(Integer.parseInt(rID), valDayEndOrChangeRoute);
				//mDataSource.close();


				if (whatTask == 2) {
					whatTask = 0;

					//mDataSource.open();

					for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) {
						String valSN = (String) mSelectedItems.get(nosSelected);
						int valID = stNames.indexOf(valSN);
						String stIDneeded = stIDs.get(valID);


						mDataSource.UpdateStoreFlagAtDayEndOrChangeRoute(stIDneeded, 3);
						mDataSource.UpdateStoreImage(stIDneeded, 3);

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
					mDataSource.reCreateDB();

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

			pDialog2 = ProgressDialog.show(StoreSelection.this, getText(R.string.PleaseWaitMsg), getText(R.string.genTermProcessingRequest), true);
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
		public ImageSync(StoreSelection activity) {

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
						String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
						String imagePath = imageDetail.split(Pattern.quote("^"))[1];
						String imageName = imageDetail.split(Pattern.quote("^"))[2];
						String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
						File fImage = new File(file_dj_path);
						if (fImage.exists()) {
							uploadImage(imagePath, imageName, tempIdImage);
						}
						else
						{
							mDataSource.updateSSttImage(imageName, 4);
							mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
						}

					}
				}


				ArrayList<String> listImageStoreCheckIn = new ArrayList<String>();

				listImageStoreCheckIn = mDataSource.getStoreCheckInImages(5);

				if (listImageStoreCheckIn != null && listImageStoreCheckIn.size() > 0) {
					for (String imageDetail : listImageStoreCheckIn) {
						String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
						String imagePath = imageDetail.split(Pattern.quote("^"))[1];
						String imageName = imageDetail.split(Pattern.quote("^"))[2];
						String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
						File fImage = new File(file_dj_path);
						if (fImage.exists()) {
							uploadImage(imagePath, imageName, tempIdImage);
						}
						else
						{
							mDataSource.updateSSttImage(imageName, 4);
							mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
						}

					}
				}


				ArrayList<String> listImageStoreClosed = new ArrayList<String>();

				listImageStoreClosed = mDataSource.getStoreClosedImages(5);

				if (listImageStoreClosed != null && listImageStoreClosed.size() > 0) {
					for (String imageDetail : listImageStoreClosed) {
						String tempIdImage = imageDetail.split(Pattern.quote("^"))[0];
						String imagePath = imageDetail.split(Pattern.quote("^"))[1];
						String imageName = imageDetail.split(Pattern.quote("^"))[2];
						String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;
						File fImage = new File(file_dj_path);
						if (fImage.exists()) {
							uploadImage(imagePath, imageName, tempIdImage);
						}
						else
						{
							mDataSource.updateSSttImage(imageName, 4);
							mDataSource.fndeleteSbumittedStoreImagesOfSotre(4);
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
				new FullSyncDataNow(StoreSelection.this).execute();
			}


		}
	}

	private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


		int responseCode = 0;

		public FullSyncDataNow(StoreSelection activity) {

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			File XMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

			if (!XMLFolder.exists()) {
				XMLFolder.mkdirs();
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
							String f1 = "";
							if (fileUri.contains(".xml")) {
								f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri;
							} else {
								f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri + ".xml";
							}
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
				showAlertSingleButtonInfo("Pending Data Uploaded Suceessfully");


			}


		}
	}

}
