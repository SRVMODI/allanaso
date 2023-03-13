package com.astix.allanasosfa;


//import com.newrelic.agent.android.NewRelic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.rest.ApiInterface;
import com.astix.allanasosfa.model.IMEIVersionDetails;
import com.astix.allanasosfa.model.IMEIVersionParentModel;
import com.astix.allanasosfa.model.TblAvailableVersion;
import com.astix.allanasosfa.model.TblUserAuthentication;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.utils.PreferenceManager;
import com.crashlytics.android.Crashlytics;
import com.example.gcm.ApplicationConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;
import com.allanasosfa.truetime.TimeUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreen extends BaseActivity implements  TaskListner,InterfaceRetrofit,MultipleInterfaceForDayEndStatus
{
    public int dayEndButtonOrCycleEndForTable=0;
    ArrayList blankTablearrayList=new ArrayList();
    ApiInterface apiService;
    public static final String REG_ID = "regId";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static SharedPreferences sPrefVanStockChanged,sharePrefCycleOrDayEnd;
    public String[] xmlForWeb = new String[1];
    public String newfullFileName;
    public int syncFLAG = 0;
    public int flgTodaySalesTargetToShow=0;
    public String imei;
    public SimpleDateFormat sdf;
    public String fDate;
    public int chkFlgForErrorToCloseApp=0;
    public String RegistrationID="NotGettingFromServer";
    SyncXMLfileData task2;
    int serverResponseCode = 0;
    String serverDateForSPref;
    SharedPreferences sPref,sPrefAttandance;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    String regId = "";
    AsyncTask<Void, Void, String> createRegIdTask;

    public static void zip(String[] files, String zipFile) throws IOException
    {
        BufferedInputStream origin = null;
        final int BUFFER_SIZE = 2048;

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try
        {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++)
            {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try
                {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1)
                    {
                        out.write(data, 0, count);
                    }
                }
                finally
                {
                    origin.close();
                }
            }
        }
        finally
        {
            out.close();
        }
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

    @Override
    public void onTaskFinish(boolean serviceException,int returnFrom)
    {

        if(returnFrom==1)  // 1---> means uploading images Based on table and get Response
        {
            if(serviceException)
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError), Toast.LENGTH_LONG).show();
            }
            else
            {
                afterversioncheck();
            }
        }

        if(returnFrom==2)  // 2---> means uploading images From the Folder and get Response
        {
            if(serviceException)
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError), Toast.LENGTH_LONG).show();
            }
            else
            {
                afterversioncheck();
            }
        }

        if(returnFrom==3)  // 3---> means uploading XML Files Async Task Response
        {
            if(serviceException)
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError), Toast.LENGTH_LONG).show();
            }
            else
            {
                afterversioncheck();
            }
        }
        if(returnFrom==4) // 4---> means uploading XML From the Folder and get Response
        {
            if(serviceException)
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError), Toast.LENGTH_LONG).show();
            }
            else
            {
                afterversioncheck();
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();

        }
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {

        }
        if(keyCode==KeyEvent.KEYCODE_MENU)
        {
            return true;
        }
        if(keyCode== KeyEvent.KEYCODE_SEARCH)
        {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public int checkImagesInFolder()
    {
        int totalFiles=0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

        String [] AllFilesName= checkNumberOfFiles(del);

        if(AllFilesName!=null && AllFilesName.length>0)
        {
            totalFiles=AllFilesName.length;
        }
        return totalFiles;
    }

    public int checkXMLFilesInFolder()
    {
        int totalFiles=0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        String [] AllFilesName= checkNumberOfFiles(del);

        if(AllFilesName!=null && AllFilesName.length>0)
        {
            totalFiles=AllFilesName.length;
        }
        return totalFiles;
    }

    public void getPrevioisDateData()
    {
        //mDataSource.open();
        String getPDADate= mDataSource.fnGetPdaDate();
        //mDataSource.close();
        if(!getPDADate.equals(""))
        {
            /*Date date2 = new Date();
            DATE_FORMAT sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String fDate = sdf.format(date2).toString().trim();
            if(!fDate.equals(getPDADate))
            {*/
            if (isOnline())
            {
                try
                {
                    if(mDataSource.fnCheckForPendingImages()==1)
                    {
                        new ImageUploadAsyncTask(this).execute();
                    }
                    else if(checkImagesInFolder()>0)
                    {
                        new ImageUploadFromFolderAsyncTask(this).execute();
                    }
                    else if(mDataSource.fnCheckForPendingXMLFilesInTable()==1)
                    {
                        new XMLFileUploadAsyncTask(this).execute();
                    }
                    else if(checkXMLFilesInFolder()>0)
                    {
                        new XMLFileUploadFromFolderAsyncTask(this).execute();
                    }
                    else
                    {

                        int checkUserAuthenticate = mDataSource.FetchflgUserAuthenticated();

                        if (checkUserAuthenticate == 0)   // 0 means-->New user        1 means-->Exist User
                        {
                            showAlertForEveryOne(getResources().getString(R.string.phnRegisterError));
                            return;

                        }
                        else
                        {
                            //mDataSource.open();

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
                                        afterversioncheck();
                                    }


                                }
                                else
                                {
                                    afterversioncheck();
                                }

                            }
                            else
                            {
                                afterversioncheck();
                            }



                        }



                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {

            }

            // }
        }
    }

    public void onCreateFunctionalityAllcode()
    {
        imei = AppUtils.getIMEI(SplashScreen.this);

        // imei="860409048884870";

        //  imei="356808071065136";

        sPref=getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        sharePrefCycleOrDayEnd = getSharedPreferences(CommonInfo.CycleOrDayEndPreference, MODE_PRIVATE);
        if(sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd"))
        {
            dayEndButtonOrCycleEndForTable=sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd",0);
        }
        //imei = "359670066016988"; //  Varun Sir Testing phone imei

        //imei="354010084603910";  // New Testing Phoine

        //imei="911383400331938";  // Test User Anil Kumar


        // imei="862856033025653";   // Live user

        //imei="359670066016988";

        // imei="865735032952256";

        // imei="863661037439754";

        //imei="352801088236109";

        // imei="911560353114284";

        //imei="911560353114284";

        //imei="863408031291603";  // paras imei like Godrej

        // imei="868622032054183";
        //   imei="352801088236109";//Order Book App
        // imei="354733070991110";//Direct Sales

        CommonInfo.imei = imei;

        sPrefAttandance=getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);
        sPrefVanStockChanged = getSharedPreferences(CommonInfo.sPrefVanLoadedUnloaded, 0);


        Date date1 = new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(SplashScreen.this, TimeUtils.DATE_FORMAT);


/*
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.DATE, 1);

        Date resultdate = new Date(c.getTimeInMillis());
        fDate = sdf.format(resultdate);*/

        if (sPrefVanStockChanged.contains("isVanLoadedUnloaded")) {
            CommonInfo.VanLoadedUnloaded = 1;
        }

      /*int checkDataNotSync = mDataSource.CheckUserDoneGetStoreOrNot();
       if (checkDataNotSync == 1)
        {
            //mDataSource.open();
            String rID = mDataSource.GetActiveRouteID();
            //mDataSource.close();

            // Date date=new Date();
            sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String fDateNew = sdf.format(date1).toString();
            //fDate = passDate.trim().toString();
            // In Splash Screen SP, we are sending this Format "dd-MMM-yyyy"
            // But InLauncher Screen SP, we are sending this Format "dd-MM-yyyy"
            Intent storeIntent = new Intent(SplashScreen.this, StoreSelection.class);
            storeIntent.putExtra("imei", imei);
            storeIntent.putExtra("userDate", fDate);
            storeIntent.putExtra("pickerDate", fDateNew);
            storeIntent.putExtra("rID", rID);
            startActivity(storeIntent);
            finish();
        }
        else
        {*/
        if (isOnline())
        {
            try
            {
               /* CheckUpdateVersion cuv = new CheckUpdateVersion();
                cuv.execute();*/

                startGettingDataFromServer();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            int flgHasRecordsRetrofitApiCalledSucessfullyOrNot= mDataSource.fnhasRecodsRetrofitApiCalledSucessfullyOrNot();
            if(flgHasRecordsRetrofitApiCalledSucessfullyOrNot==0)
            {
                showNoConnAlert();
            }
            else {

                int flgcheckCheckRetrofitApiCalledSucessfullyOrNot = mDataSource.fnCheckRetrofitApiCalledSucessfullyOrNot();
                if (flgcheckCheckRetrofitApiCalledSucessfullyOrNot == 0) {
                    //mDataSource.open();

                    String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                    //mDataSource.close();

                    // Date date=new Date();
                    Date date3 = new Date();
                    sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String fDateNew = TimeUtils.getNetworkDateTime(SplashScreen.this, TimeUtils.DATE_FORMAT);
                    //fDate = passDate.trim().toString();
                    // In Splash Screen SP, we are sending this Format "dd-MMM-yyyy"
                    // But InLauncher Screen SP, we are sending this Format "dd-MM-yyyy"
                    Intent storeIntent = new Intent(SplashScreen.this, AllButtonActivity.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDateNew);
                    storeIntent.putExtra("rID", rID);
                    startActivity(storeIntent);
                    finish();
                } else {
                    showNoConnAlert();
                }
            }

        }
        //}
    }

    public void onCreateFunctionality()
    {


        if(CommonInfo.flgLangChangeReuired==1)
        {
            SharedPreferences sharedPreferences=getSharedPreferences("LanguagePref", MODE_PRIVATE);
            SharedPreferences.Editor ed;

            if(!sharedPreferences.contains("Language")){
                final Dialog dialogLanguage = new Dialog(SplashScreen.this);
                dialogLanguage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLanguage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));

                dialogLanguage.setCancelable(false);
                dialogLanguage.setContentView(R.layout.language_popup);

                TextView textviewEnglish=(TextView) dialogLanguage.findViewById(R.id.textviewEnglish);
                TextView textviewHindi=(TextView) dialogLanguage.findViewById(R.id.textviewHindi);
                TextView textviewGujarati=(TextView) dialogLanguage.findViewById(R.id.textviewGujrati);
                textviewEnglish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("en");
                        onCreateFunctionalityAllcode();
                    }
                });
                textviewHindi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("hi");
                        onCreateFunctionalityAllcode();
                    }
                });
                textviewGujarati.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLanguage.dismiss();
                        setLanguage("gu");
                        onCreateFunctionalityAllcode();
                    }
                });


                dialogLanguage.show();


            }
            else
            {
                loadLocale();
                onCreateFunctionalityAllcode();
            }
        }
        else
        {

            setLanguage("en");
            onCreateFunctionalityAllcode();
        }

    }

    public void loadLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("LanguagePref", Activity.MODE_PRIVATE);
        String language = prefs.getString("Language", "");
        changeLang(language);
    }

    public void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale  myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // updateTexts();
    }

    public void saveLocale(String lang)
    {
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
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
        saveLocale(language);

    }

    public void fnShowAlertBeforeRedirectingToLauncher()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SplashScreen.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage(Html.fromHtml(CommonInfo.SalesPersonTodaysTargetMsg));
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        int checkNoDataInTable=0;
                        try {
                            checkNoDataInTable= mDataSource.counttblDistributorSavedData();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try
                        {
                            if(checkNoDataInTable==0)
                            {
                                mDataSource.deleteDistributorStockTbles();
                                Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                                intent.putExtra("imei", imei);
                                SplashScreen.this.startActivity(intent);
                                finish();
                            }
                            else
                            {
                                FullSyncDataNow task = new FullSyncDataNow(SplashScreen.this);
                                task.execute();
                            }
                        }
                        catch(Exception e)
                        {

                        }


                    }
                });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);


        //      BugSenseHandler.setup(this, "1f12b9fe");

        // Initalization New Relic Tool
        // NewRelic.withApplicationToken("AA650dd750f0d8d87061719815bd7514decb5fb129").start(this.getApplication());

        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= 23)
        {

            if(checkingPermission())
            {
                onCreateFunctionality();
            }



            else
            {
                ActivityCompat.requestPermissions(SplashScreen.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        else{
            onCreateFunctionality();

        }



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  boolean checkingPermission()
    {

        if ((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) )
        {
            return false;
            //onCreateFunctionality();
        }
        else if((checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else if((checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else if((checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else if((checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else if((checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else{
            return true;
        }


    }

    private void funGetRegistrationID(final String emailID)
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... params)
            {
                String msg = "";
                try
                {
                    if (gcmObj == null)
                    {
                        gcmObj = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcmObj.register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = regId;
                }
                catch(IOException ex)
                {
                    msg = getResources().getString(R.string.errorTxt) + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg)
            {

                RegistrationID=msg;
                CommonInfo.RegistrationID=RegistrationID;
                // System.out.println("Sunil Reg id msg SplashScreen:"+RegistrationID);
                try
                {
                    // new GetRouteInfo().execute();
                    CommonFunction.getAllMasterTableModelData(SplashScreen.this,imei,RegistrationID,"Please wait loading data.",0);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }.execute(null, null, null);
    }

    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.errorPlayServices),Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        else
        {
            //Toast.makeText(applicationContext,"This device supports Play services, App will work normally",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void showAlertBox(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SplashScreen.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);

        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                //mDataSource.open();
                mDataSource.reTruncateRouteMstrTbl();
                //mDataSource.close();
                finish();

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    public void showAlertForEveryOne(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SplashScreen.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
            }
        });
        //alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public void showNoConnAlert()
    {
        //AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SplashScreen.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        //mDataSource.open();
                        int cntRoute= mDataSource.counttblCountRoute();
                        //mDataSource.close();
                        if(cntRoute==0)
                        {
                            finish();
                        }
                        else
                        {
                            //mDataSource.open();
                            serverDateForSPref=	mDataSource.fnGetServerDate();
                            //mDataSource.close();

                            if(sPref.contains("DatePref"))
                            {

                                if(sPref.getString("DatePref", "").equals(serverDateForSPref))
                                {
                                    Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                                    intent.putExtra("imei", imei);
                                    SplashScreen.this.startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    SharedPreferences.Editor editor=sPref.edit();
                                    editor.clear();
                                    editor.commit();
                                    sPref.edit().putString("DatePref", serverDateForSPref).commit();
                                    fnShowAlertBeforeRedirectingToLauncher();
                                }
                            }
                            else
                            {
                                sPref.edit().putString("DatePref", serverDateForSPref).commit();
                                fnShowAlertBeforeRedirectingToLauncher();
                            }
                        }

                    }
                });
        // alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public void showNewVersionAvailableAlert()
    {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setMessage(getText(R.string.NewVersionMsg));
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                GetUpdateInfo task = new GetUpdateInfo(SplashScreen.this);
                task.execute();
                dialog.dismiss();
            }
        });

        // alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    private void downloadapk()
    {
        try
        {

            //ParagIndirectTest
            // URL url = new URL("http://115.124.126.184/downloads/ParagIndirect.apk");
            //  URL url = new URL("http://115.124.126.184/downloads/ParagIndirectTest.apk");
            URL url = new URL(CommonInfo.VersionDownloadPath.trim()+ CommonInfo.VersionDownloadAPKName);
            URLConnection connection = url.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            //urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(false);
            // urlConnection.setInstanceFollowRedirects(false);
            urlConnection.connect();

            //if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            // {
            File sdcard = Environment.getExternalStorageDirectory();

            //  //System.out.println("sunil downloadapk sdcard :" +sdcard);
            //File file = new File(sdcard, "neo.apk");

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            // File file2 = new File(PATH+"ParagIndirect.apk");
            File file2 = new File(PATH+ CommonInfo.VersionDownloadAPKName);
            if(file2.exists())
            {
                file2.delete();
            }

            File file1 = new File(PATH);
            file1.mkdirs();


            File file = new File(file1, CommonInfo.VersionDownloadAPKName);

            int size = connection.getContentLength();


            FileOutputStream fileOutput = new FileOutputStream(file);

            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[10240];
            int bufferLength = 0;
            int current = 0;
            while ( (bufferLength = inputStream.read(buffer)) != -1 )
            {
                fileOutput.write(buffer, 0, bufferLength);
            }

            fileOutput.close();




        } catch (MalformedURLException e)
        {

        } catch (IOException e)
        {

        }
    }

    /* private void installApk()
     {
         this.deleteDatabase(AppDataSource.DATABASE_NAME);
         Intent intent = new Intent(Intent.ACTION_VIEW);
         Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + CommonInfo.VersionDownloadAPKName));
         intent.setDataAndType(uri, "application/vnd.android.package-archive");
         startActivity(intent);
         finish();


     }
 */
    private void installApk()
    {
        this.deleteDatabase(mDataSource.dbHelper.DATABASE_NAME);

        File file = new File(Environment.getExternalStorageDirectory() + "/download/", CommonInfo.VersionDownloadAPKName);
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (file.exists()) {
                Uri contentUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                intent = new Intent(Intent.ACTION_INSTALL_PACKAGE, contentUri);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            if (file.exists()) {
                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + CommonInfo.VersionDownloadAPKName));
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
        }
        startActivity(intent);
        finish();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((grantResults[0]== PackageManager.PERMISSION_GRANTED) && (grantResults[1]== PackageManager.PERMISSION_GRANTED) && (grantResults[2]== PackageManager.PERMISSION_GRANTED) && (grantResults[3]== PackageManager.PERMISSION_GRANTED)&& (grantResults[4]== PackageManager.PERMISSION_GRANTED))
        {
            onCreateFunctionality();
        }
        else
        {
            finish();

        }
    }

    public  int upLoad2ServerXmlFiles(String sourceFileUri,String fileUri)
    {

        fileUri=fileUri.replace(".xml", "");

        String fileName = fileUri;
        String zipFileName=fileUri;

        String newzipfile = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.DistributorStockXMLFolder+"/" + fileName + ".zip";
        ///storage/sdcard0/PrabhatDirectSFAXml/359648069495987.2.21.04.2016.12.44.02.zip

        sourceFileUri=newzipfile;

        xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.DistributorStockXMLFolder+"/" + fileName + ".xml";
        //[/storage/sdcard0/PrabhatDirectSFAXml/359648069495987.2.21.04.2016.12.44.02.xml]

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
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameDistributorSyncPath + "&CLIENTFILENAME=" + zipFileName;

        }
        else {
            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameDistributorSyncPath + "&CLIENTFILENAME=" + zipFileName+".xml";
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

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

            if(serverResponseCode == 200)
            {
							  /* //mDataSource.open();
							   mDataSource.upDateXMLFileFlag(fileUri, 4);
							   //mDataSource.close();*/

                //new File(dir, fileUri).delete();
                syncFLAG=1;

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                // editor.remove(xmlForWeb[0]);
                editor.putString(fileUri, ""+4);
                editor.commit();

                String FileSyncFlag=pref.getString(fileUri, ""+1);

                delXML(xmlForWeb[0].toString());
							   		/*//mDataSource.open();
						            mDataSource.deleteXMLFileRow(fileUri);
						            //mDataSource.close();*/

            }
            else
            {
                syncFLAG=0;
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

    public void delXML(String delPath)
    {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
    }

    @Override
    public void success() {
        sendIntentToOtherActivityAfterAllDataFetched();
    }

    @Override
    public void failure() {//Object t
        showAlertForError("Error while retreiving data from server");
    }



    public void afterversioncheck()
    {

        int checkUserAuthenticate = mDataSource.FetchflgUserAuthenticated();
        //mDataSource.close();

        if (checkUserAuthenticate == 0)   // 0 means-->New user        1 means-->Exist User
        {
            showAlertForEveryOne(getResources().getString(R.string.phnRegisterError));
            return;

        }
        else {
            //mDataSource.open();
            int check= mDataSource.FetchVersionDownloadStatus();  // 0 means-->new version installed  1 means-->new version not install
            //mDataSource.close();
            if(check==1)
            {
                showNewVersionAvailableAlert();
            }
            else
            {


                int flgcheckCheckRetrofitApiCalledSucessfullyOrNot= mDataSource.fnCheckRetrofitApiCalledSucessfullyOrNot();


                if (flgcheckCheckRetrofitApiCalledSucessfullyOrNot == 1)
                {
                    //mDataSource.open();
//                    String rID = mDataSource.GetActiveRouteID();
//                    //mDataSource.close();
//
//                    // Date date=new Date();
//                    Date date1 = new Date();
//                    sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//                    String fDateNew = sdf.format(date1).toString();
                    //fDate = passDate.trim().toString();
                    // In Splash Screen SP, we are sending this Format "dd-MMM-yyyy"
                    // But InLauncher Screen SP, we are sending this Format "dd-MM-yyyy"
                   /* Intent storeIntent = new Intent(SplashScreen.this, AllButtonActivity.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDateNew);
                    storeIntent.putExtra("rID", rID);
                    startActivity(storeIntent);
                    finish();*/
                    sendIntentToOtherActivityAfterAllDataFetched();
                }
                else
                {

                    //mDataSource.open();
                    mDataSource.maintainSplashPDADate();
                    String getPDADate= mDataSource.fnGetPdaDate();
                    String getServerDate= mDataSource.fnGetServerDate();

                    //mDataSource.close();
                    if(!getServerDate.equals(getPDADate))
                    {
                        showAlertBox(getResources().getString(R.string.phnDateError));
                        //mDataSource.open();
                        mDataSource.reCreateDB();
                        //mDataSource.close();
                        return;
                    }

                    //mDataSource.open();
                  /*  mDataSource.fnFinaldropRoutesTBL();
                    mDataSource.createRouteTBL();*/
                    //mDataSource.close();
                    try
                    {
                        funGetRegistrationID(getResources().getString(R.string.regID));

                        checkPlayServices();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }







            }
        }

    }


    private class GetUpdateInfo extends AsyncTask<Void, Void, Void>
    {
        KProgressHUD progressHUD;
        Activity activity;
        public GetUpdateInfo(SplashScreen activity)
        {
            this.activity=activity;
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressHUD=KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(getResources().getString(R.string.UpdatingNewVersionMsg))
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {

            try
            {
                downloadapk();
            }
            catch(Exception e)
            {}

            finally
            {}

            return null;
        }


        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if(progressHUD!=null && progressHUD.isShowing())
            {
                progressHUD.dismiss();
            }

            installApk();
        }
    }



    public void callDayStartActivity()
    {
        //mDataSource.open();
        if(isOnline())
        {
            funStockUploadedStatus();
        }



    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
    {


        ProgressDialog pDialogGetStores;
        public FullSyncDataNow(SplashScreen activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(getResources().getString(R.string.genTermLoadData));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Void doInBackground(Void... params)
        {

            int Outstat=3;

            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
            String StampEndsTime = TimeUtils.getNetworkDateTime(SplashScreen.this, TimeUtils.DATE_TIME_FORMAT);



            //mDataSource.open();
            String presentRoute="0";
            //mDataSource.close();

            SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);
            newfullFileName=imei+"."+presentRoute+"."+ TimeUtils.getNetworkDateTime(SplashScreen.this, TimeUtils.TIME_FORMAT1);
            try
            {

                File MeijiDistributorEntryXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);
                if (!MeijiDistributorEntryXMLFolder.exists())
                {
                    MeijiDistributorEntryXMLFolder.mkdirs();
                }

                int checkNoFiles= mDataSource.counttblDistributorSavedData();
                if(checkNoFiles==1)
                {
                    String routeID= mDataSource.GetActiveRouteIDSunil();
                    DatabaseAssistant DA=new DatabaseAssistant(SplashScreen.this);
                    DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);

                }



                //mDataSource.deleteDistributorStockTbles();




            }
            catch (Exception e)
            {

                e.printStackTrace();
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }
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
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            try
            {

                task2 = new SyncXMLfileData(SplashScreen.this);
                task2.execute();
            }
            catch(Exception e)
            {

            }


        }
    }

    private class SyncXMLfileData extends AsyncTask<Void, Void, Integer>
    {

        KProgressHUD progressHUD;
        Activity activity;

        public SyncXMLfileData(SplashScreen activity)
        {
            this.activity=activity;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);

            if (!MeijiIndirectSFAxmlFolder.exists())
            {
                MeijiIndirectSFAxmlFolder.mkdirs();
            }
            progressHUD=KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(getResources().getString(R.string.genTermPleaseWaitNew))
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

        }

        @Override
        protected Integer doInBackground(Void... params)
        {


            // This method used for sending xml from Folder without taking records in DB.

            // Sending only one xml at a times

            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);

            // check number of files in folder
            String [] AllFilesName= checkNumberOfFiles(del);


            if(AllFilesName.length>0)
            {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


                for(int vdo=0;vdo<AllFilesName.length;vdo++)
                {
                    String fileUri=  AllFilesName[vdo];


                    System.out.println("Sunil Again each file Name :" +fileUri);

                    if(fileUri.contains(".zip"))
                    {
                        File file = new File(fileUri);
                        file.delete();
                    }
                    else
                    {
                        String f1=Environment.getExternalStorageDirectory().getPath()+"/"+ CommonInfo.DistributorStockXMLFolder+"/"+fileUri;
                        System.out.println("Sunil Again each file full path"+f1);
                        try
                        {
                            upLoad2ServerXmlFiles(f1,fileUri);
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }
            else
            {

            }



            // pDialogGetStores.dismiss();

            return serverResponseCode;
        }

        @Override
        protected void onCancelled()
        {
            // Log.i("SyncMasterForDistributor","Sync Cancelled");
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            if(!isFinishing())
            {

                // Log.i("SyncMasterForDistributor", "Sync cycle completed");



                if(progressHUD!=null && progressHUD.isShowing())
                {
                    progressHUD.dismiss();
                }

            }
            mDataSource.deleteDistributorStockTbles();
				/* showAlertBox("Your Phone  Date is not correct.Please Correct it.");
					return;*/

          /*  //mDataSource.open();
            mDataSource.reCreateDB();
            //mDataSource.close();*/
            Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
            intent.putExtra("imei", imei);
            SplashScreen.this.startActivity(intent);
            finish();


        }





    }
    public void startGettingDataFromServer()
    {

        startGettingDataFromServerForIMEIDetails();
    }
    public void startGetStockData(){

        funStockUploadedStatus();
    }
    public void startGettingDataFromServerForIMEIDetails(){

        final KProgressHUD progressHUD=KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.txtValidatingUser))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        apiService = com.astix.allanasosfa.rest.ApiClient.getClient().create(ApiInterface.class);


        IMEIVersionDetails imeiVersionDetail=new IMEIVersionDetails();
        imeiVersionDetail.setApplicationType(CommonInfo.Application_TypeID);
        imeiVersionDetail.setIMEINo(imei);
        imeiVersionDetail.setVersionId(CommonInfo.DATABASE_VERSIONID);
        Call<IMEIVersionParentModel> call= apiService.Call_IMEIVersionDetailStatus(imeiVersionDetail);

        call.enqueue(new Callback<IMEIVersionParentModel>() {
            @Override
            public void onResponse(Call<IMEIVersionParentModel> call, Response<IMEIVersionParentModel> response) {
                if(progressHUD!=null && progressHUD.isShowing())
                    progressHUD.dismiss();
                if(response.code()==200){
                    IMEIVersionParentModel imeiVersionParentModel= response.body();

                    String flgAppStatus="1";
                    String DisplayMessage="No Message";
                    String flgValidApplication="1";
                    String MessageForInvalid="No Message";
                    String flgPersonTodaysAtt="0";





                    List<TblUserAuthentication> listTblUserAuthentication= imeiVersionParentModel.getTblUserAuthentication();
                    if(listTblUserAuthentication!=null && listTblUserAuthentication.size()>0)
                    {
                        TblUserAuthentication tblUserAuthentication=  listTblUserAuthentication.get(0);

                        if(tblUserAuthentication.getSelfieNameURL()!=null && tblUserAuthentication.getSelfieName()!=null){
                            if((!tblUserAuthentication.getSelfieNameURL().equals("")) && (!tblUserAuthentication.getSelfieName().equals("")) && (!tblUserAuthentication.getSelfieNameURL().equals("0")) && (!tblUserAuthentication.getSelfieName().equals("0"))){
                                DownloadImage downloadImage=new DownloadImage();
                                AppUtils.executeAsyncTask(downloadImage,new String[]{tblUserAuthentication.getSelfieNameURL(),tblUserAuthentication.getSelfieName()});

                            }
                        }
                        int workingtype=0;
                        //
                        workingtype=2;
                        if(tblUserAuthentication.getWorkingType()!=null) {
                            mDataSource.savetblUserAuthenticationMstr("" + tblUserAuthentication.getFlgUserAuthenticated(), tblUserAuthentication.getPersonName(), "" + tblUserAuthentication.getFlgRegistered(), flgAppStatus, DisplayMessage, flgValidApplication, MessageForInvalid, "" + tblUserAuthentication.getFlgPersonTodaysAtt(), tblUserAuthentication.getPersonNodeID(), tblUserAuthentication.getPersonNodeType(), tblUserAuthentication.getContactNo(), tblUserAuthentication.getDOB(), tblUserAuthentication.getSelfieName(), tblUserAuthentication.getSelfieNameURL(), tblUserAuthentication.getSalesAreaName(), tblUserAuthentication.getCoverageAreaNodeID(), tblUserAuthentication.getCoverageAreaNodeType(), tblUserAuthentication.getFlgToShowAllRoutesData(), workingtype);
                            CommonInfo.CoverageAreaNodeID = tblUserAuthentication.getCoverageAreaNodeID();
                            CommonInfo.CoverageAreaNodeType = tblUserAuthentication.getCoverageAreaNodeType();
                        }
                    }

                    List<TblAvailableVersion> listTblAvailableVersion= imeiVersionParentModel.getTblAvailableVersion();
                    if(listTblAvailableVersion!=null && listTblAvailableVersion.size()>0) {
                        TblAvailableVersion tblAvailableVersion = listTblAvailableVersion.get(0);
                        mDataSource.savetblAvailbUpdatedVersion("" + tblAvailableVersion.getVersionID(), tblAvailableVersion.getVersionSerialNo(), "" + tblAvailableVersion.getVersionDownloadStatus(), tblAvailableVersion.getServerDate());

                    }

                    //start check version check


                    String getPDADate = mDataSource.fnGetPdaDate();
                    String getServerDate = mDataSource.fnGetServerDate();

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
                                afterversioncheck();
                            }


                        }
                        else
                        {
                            afterversioncheck();
                        }

                    }
                    else
                    {
                        afterversioncheck();
                    }

//End check version check

                }
                else{
                    showAlertSingleButtonError("Error while retrieving Version Detail data. ");
                }

            }

            @Override
            public void onFailure(Call<IMEIVersionParentModel> call, Throwable t) {

                showAlertSingleButtonError("Error while retrieving Version Detail data. ");
            }
        });

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




    public void sendIntentToOtherActivityAfterAllDataFetched(){

        String prsnCvrgId_NdTyp=  mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
        int  CoverageNodeId=Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0]);
        int   CoverageNodeType=Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]);
        CommonInfo.flgDrctslsIndrctSls= mDataSource.fnGetflgDrctslsIndrctSlsForDSR(CoverageNodeId,CoverageNodeType);
        CommonInfo.hmapAppMasterFlags= mDataSource.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);
        //mDataSource.open();
        serverDateForSPref=	mDataSource.fnGetServerDate();
        //mDataSource.close();
        //setSharedPreferencesDate(serverDateForSPref);

        if(sPref.contains("DatePref"))
        {

            if(sPref.getString("DatePref", "").equals(serverDateForSPref))
            {
                if(!sPrefAttandance.contains("AttandancePref"))
                {
                    callDayStartActivity();
                }
                else {


                    startGetStockData();

                        /*Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                        intent.putExtra("imei", imei);
                        SplashScreen.this.startActivity(intent);
                        finish();*/
                }

            }
            else
            {

                SharedPreferences.Editor editor=sPref.edit();
                editor.clear();
                editor.commit();
                sPref.edit().putString("DatePref", serverDateForSPref).commit();

            /*    SharedPreferences.Editor editor1=sPrefAttandance.edit();
                editor1.clear();
                editor1.commit();*/

                if(!sPrefAttandance.contains("AttandancePref"))
                {
                    callDayStartActivity();
                }
                else {

                      /*  Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                        intent.putExtra("imei", imei);
                        SplashScreen.this.startActivity(intent);
                        finish();*/
                    startGetStockData();
                }
            }
        }
        else
        {
            sPref.edit().putString("DatePref", serverDateForSPref).commit();

            if(!sPrefAttandance.contains("AttandancePref"))
            {
                callDayStartActivity();
            }
            else {
                startGetStockData();

                  /*  Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                    intent.putExtra("imei", imei);
                    SplashScreen.this.startActivity(intent);
                    finish();*/

            }

        }
    }

    public void showAlertForError(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(SplashScreen.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
            }
        });
        //alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }
    private void funStockUploadedStatus()
    {

        if(CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")!=0)
        {

            CommonFunction.getStockUploadedStatus(SplashScreen.this, imei, 1);
        }
        else
        {
            int flgPersonTodaysAtt = mDataSource.FetchflgPersonTodaysAtt();


            if (flgPersonTodaysAtt == 0) {
                if (!sPrefAttandance.contains("AttandancePref")) {
                    Intent intent = new Intent(SplashScreen.this, DSR_Registration.class);
                    intent.putExtra("IntentFrom", "SPLASH");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                    intent.putExtra("imei", imei);
                    SplashScreen.this.startActivity(intent);
                    finish();
                }

            } else {
                SharedPreferences.Editor editor = sPrefAttandance.edit();
                editor.clear();
                editor.commit();
                sPrefAttandance.edit().putString("AttandancePref", fDate).commit();

                Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                intent.putExtra("imei", imei);
                SplashScreen.this.startActivity(intent);
                finish();

            }
//            }
//            else
//            {
              /*  int flgPersonTodaysAtt=dbengine.FetchflgPersonTodaysAtt();
                //dbengine.close();

                if(flgPersonTodaysAtt==0)
                {
           *//* Intent intent=new Intent(this,DayStartActivity.class);
            startActivity(intent);
            finish();*//*
                    Intent intent=new Intent(SplashScreen.this,DSR_Registration.class);
                    intent.putExtra("IntentFrom", "SPLASH");
                    startActivity(intent);
                    finish();
                }
                else
                {*/
//                Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
//                intent.putExtra("imei", imei);
//                SplashScreen.this.startActivity(intent);
//                finish();
            /*}*/
//            }

        }

    }

    @Override
    public void DayEndServerStatussuccess(int flgCalledFrom) {
        if(mDataSource.fetchtblDayEndStatus() == 2)
        {
            mDataSource.reCreateDB();


            // showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
            if(dayEndButtonOrCycleEndForTable==2)
            {
                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtCycleEndProcess));
            }
            else {
                showDayEndProcess(getString(R.string.AlertDialogHeaderMsg), getString(R.string.alrtDayEndProcess));
            }
        }
        else
        {
            int flgPersonTodaysAtt = mDataSource.FetchflgPersonTodaysAtt();


            if (flgPersonTodaysAtt == 0) {
                if (!sPrefAttandance.contains("AttandancePref")) {
                    Intent intent = new Intent(SplashScreen.this, DSR_Registration.class);
                    intent.putExtra("IntentFrom", "SPLASH");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                    intent.putExtra("imei", imei);
                    SplashScreen.this.startActivity(intent);
                    finish();
                }

            } else {
                SharedPreferences.Editor editor = sPrefAttandance.edit();
                editor.clear();
                editor.commit();
                sPrefAttandance.edit().putString("AttandancePref", fDate).commit();

                Intent intent = new Intent(SplashScreen.this, AllButtonActivity.class);
                intent.putExtra("imei", imei);
                SplashScreen.this.startActivity(intent);
                finish();

            }

        }
    }

    @Override
    public void DayEndServerStatusfailure(int flgCalledFrom) {

    }
    public void showDayEndProcess(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreen.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {

                finishAffinity();
            }
        });

        alertDialog.show();
    }

}
