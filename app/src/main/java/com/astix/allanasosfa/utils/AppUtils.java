package com.astix.allanasosfa.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.allanasosfa.truetime.TimeUtils;
import com.allanasosfa.truetime.TrueTime;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.allanasosfa.truetime.TimeUtils;
import com.allanasosfa.truetime.TrueTime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.reactivex.annotations.NonNull;

import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;


public class AppUtils {

    public static String BusinessUnitId="businessUnitId";
    public static String BusinessUnitType="businessUnitType";

    public static String SPOKE_ID_INTENT="spokeId";
    public static String SPOKE_NAME_INTENT="spokeName";

    public static String StoreId="storeId";
    public static String StoreName="storename";
    public static String APICALLSTATUS="apicallstatus";
    public static String PDADATE="pdadate";
    public static String FLGJOINTVISIT="flgjointvisit";
    public static String JOINTVISITID="jointVisitId";
    public static String FEEDBACKTYPE="feedbackType";
    public static String ROUTEID="routeId";
    public static String ROUTE="route";
    public static String PTRPTC_MARKED="ptrptcmarked";
    public static String FeedbackType="feedbackType";
    public static String IS_STOCK_AVAILABLE="isStockAvailable";
    public static String IS_COMPETITOR_AVAILABLE="isCompetitorAvailable";
    public static String IS_STORE_CHECK_LAST_PAGE="IS_STORE_CHECK_LAST_PAGE";
    public static String PERSON_NODE_ID="personnodeid";
    public static String PERSON_NODE_TYPE="personnodetype";
    public static String PERSON_NAME ="username";
    public static String Flg_Attendance="IS_Attendance_Marked";
    public static String FlgBoughtLast3Months ="flgBoughtLast3Months";
    public static String Server_LastSyncDateTime="Server_LastSyncDateTime";
    public  static  String flgGenerateRouteIDOrNot="flgGenerateRouteIDOrNot";
    public  static  String RouteSessionCode="RouteSessionCode";
    public  static  String ATTENDANCETIME="attandancetime";
    public  static  String USERAGREEMENT="useragreement";



    public static Boolean val=false;
    public static AlertDialog mAlertDialog;
    public void callParentMethod(Activity context){
        context.onBackPressed();
    }

    public static ImageButton createImageButton(Activity activity,int resId, Float weightf, Boolean margin, Boolean isHeader) {
        ImageButton imageButton = new ImageButton(activity);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, weightf);
        imageButton.setLayoutParams(layoutParams1);
        if(Build.VERSION.SDK_INT>=23)
        imageButton.setForegroundGravity(Gravity.CENTER);
        imageButton.setImageResource(resId);
        imageButton.setBackgroundResource(android.R.drawable.editbox_background_normal);

//        imageButton.setBackgroundColor(activity.getResources().getColor(R.color.white));
        imageButton.setPadding(5,5,5,5);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.rightMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;
//        imageButton.setLayoutParams(params);


        // edit_text.setPadding(3,3,3,3);
        //edit_text.setHint(hint);
        if (margin) {
            layoutParams1.setMargins(1, 1, 1, 1);
        }
        if (isHeader) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.WRAP_CONTENT, weightf);
            imageButton.setLayoutParams(layoutParams);
            layoutParams.setMargins(0, 0, 1, 0);
        }

        return imageButton;
    }


    public static TextView createTextView(Activity activity,Float weightf, Boolean margin, Boolean isHeader,boolean isWrapContent) {
        TextView textView= new TextView(activity);
        LinearLayout.LayoutParams layoutParams1;
        if(weightf==0 || weightf==0f) {
            layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        }   else {
            if(isWrapContent)
            layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.WRAP_CONTENT, weightf);
            else
                layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, weightf);
        }

        textView.setLayoutParams(layoutParams1);
        //edit_text.setTag(tagVal);
        textView.setBackgroundResource(android.R.drawable.editbox_background_normal);
        textView.setTextSize(11);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);


        // edit_text.setPadding(3,3,3,3);
        //edit_text.setHint(hint);
        if (margin) {
            layoutParams1.setMargins(1, 1, 1, 1);
        }
        if (isHeader) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.WRAP_CONTENT, weightf);
            textView.setLayoutParams(layoutParams);
            layoutParams.setMargins(0, 0, 1, 0);
        }

        return textView;
    }


    public static String getDeviceId(Context context) {
        TelephonyManager aPhoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
       String aDeviceId="";
       /* if(true) {
            CommonInfo.imei= "351893080684874"; //"352801088236109";//"352801088236109"; //"0987654321";//1234567890//354872106396280

            return CommonInfo.imei;
        }*/

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                if (aPhoneMgr.getDeviceId() != null) {
                    aDeviceId = aPhoneMgr.getDeviceId().trim();
                    aDeviceId="354010084603910";
                } else {
                    aDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    aDeviceId="354010084603910";
                }

                System.out.println("0000 IMEI is"+aDeviceId);
            }
        return aDeviceId;
    }

//PersonCurrentPhoneNumber

    public static String getIMEI(Context context)
    {
        String imei=null;

        if(CommonInfo.imei == null || CommonInfo.imei.equals(""))
        {
            imei=getDeviceId(context);
            CommonInfo.imei=imei;
        }
        else
        {
            imei= CommonInfo.imei;
        }
        System.out.println("0000 IMEI is"+imei);

        return imei;
    }

    public void showToast(Context _context,String textdata) {
        Toast.makeText(_context, textdata, Toast.LENGTH_SHORT).show();
    }
    public static void showNoConnAlert(Context context) {
        if(context==null)
            return;
        try {
            if (mAlertDialog != null)
                mAlertDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name)
                    .setMessage(context.getResources().getString(R.string.txtErrorInternetConnection))
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertDialog = builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showAlertDialog(Context context, String alertdata) {

        if(context==null)
            return;
        try {
            if (mAlertDialog != null)
                mAlertDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name)
                    .setMessage(alertdata)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            mAlertDialog = builder.show();
        } catch (Exception e) {
e.printStackTrace();
        }


    }

    public static <P, T extends AsyncTask<P, ?, ?>> void executeAsyncTask(T task) {
        executeAsyncTask(task, (P[]) null);
    }

    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void executeAsyncTask(T task, P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(getExecutor(), params);
        } else {
            task.execute(params);
        }
    }

    private static Executor getExecutor() {
        int corePoolSize = 60;
        int maximumPoolSize = 80;
        int keepAliveTime = 10;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(maximumPoolSize);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        return threadPoolExecutor;
    }

    public static String convertDateToDMMMYY(String cdate) {
        String oldFormat = "dd/MM/yyyy";
        String newFormat = "dd-MMM-yy";

        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat,Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat,Locale.getDefault());


        String formattedDate = cdate;
        try {
            formattedDate = sdf2.format(sdf1.parse(cdate));

//            Date date=new Date(cdate);
//            if(isTomorrow(date)){
//                formattedDate="Tomorrow,"+formattedDate.split(",")[1];
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static LinearLayout createVerticalLayout(Context context,String tagVal) {
        LinearLayout llayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        llayout.setLayoutParams(layoutParams1);
        //layoutParams1.setMargins(4,4,4,4);
        llayout.setTag(tagVal);
        llayout.setOrientation(LinearLayout.VERTICAL);

        return llayout;
    }

    public static LinearLayout createHorizontalLinLayout(Context context,Boolean isPadding) {
        LinearLayout llayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        llayout.setLayoutParams(layoutParams1);
        //  layoutParams1.setMargins(4,4,4,4);
        //llayout.setTag(tagVal);
        if (isPadding) {
            llayout.setPadding(3, 3, 3, 3);
        }
        llayout.setOrientation(LinearLayout.HORIZONTAL);

        return llayout;
    }

    public static String convertDMMMYYtoddMMyyyy(String cdate) {
        String newFormat = "dd/MM/yyyy";
        String oldFormat = "dd-MMM-yy";

        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat,Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat,Locale.getDefault());


        String formattedDate = cdate;
        try {
            formattedDate = sdf2.format(sdf1.parse(cdate));

//            Date date=new Date(cdate);
//            if(isTomorrow(date)){
//                formattedDate="Tomorrow,"+formattedDate.split(",")[1];
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static TextView createTextView(Context context,String textName, Float weightf, Boolean isDate, Boolean margin, int dateIndex,boolean isWrapContent)
    {
        TextView txtVw_ques=new TextView(context);
        LinearLayout.LayoutParams layoutParams1;
        if (weightf == 0 || weightf == 0f) {
            layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        } else {
            if(isWrapContent)
            layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.WRAP_CONTENT, weightf);
            else
                layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, weightf);
        }
        layoutParams1.gravity=Gravity.CENTER;
        txtVw_ques.setLayoutParams(layoutParams1);
        //txtVw_ques.setTag(tagVal);
        txtVw_ques.setGravity(Gravity.CENTER);
        txtVw_ques.setTextSize(8);
        txtVw_ques.setPadding(1,1,1,2);
        //txtVw_ques.setTextColor(getResources().getColor(R.color.blue));
        txtVw_ques.setTextColor(Color.BLACK);
        txtVw_ques.setText(textName);
        if(margin)
        {
            layoutParams1.setMargins(1,1,1,1);
        }

        if(isDate)
        {
            txtVw_ques.setBackgroundResource(R.drawable.shadow_with_5dp);
            txtVw_ques.setText("Date");
            txtVw_ques.setTextColor(Color.BLACK);
            txtVw_ques.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else
        {
//            if(dateIndex==2)
//            {
//                txtVw_ques.setText("XXXX");
//                txtVw_ques.setTextColor(Color.BLACK);
//            }
        }


        return  txtVw_ques;
    }


    public static EditText createEditText(Context context,Float weightf, Boolean margin, Boolean isHeader)
    {
        EditText edit_text=new EditText(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT,weightf);
        layoutParams1.gravity=Gravity.CENTER;
        edit_text.setLayoutParams(layoutParams1);
        //edit_text.setTag(tagVal);
        edit_text.setBackgroundResource(R.drawable.custom_edittext);
        edit_text.setTextSize(11);
        edit_text.setGravity(Gravity.CENTER);
        edit_text.setTextColor(Color.BLACK);
        edit_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        // edit_text.setPadding(3,3,3,3);
        //edit_text.setHint(hint);
        if(margin)
        {
            layoutParams1.setMargins(1,1,1,1);
        }
        if(isHeader)
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.WRAP_CONTENT,weightf);
            edit_text.setLayoutParams(layoutParams);
            layoutParams.setMargins(0,0,1,0);
        }

        return  edit_text;
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for(String child:children){
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

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


    public static void showAlertForError(Context context,String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(context);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // finish();
            }
        });
        //alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public static String getCurrentDate(Context context) {
        return TimeUtils.getNetworkDate(context,TimeUtils.DATE_FORMAT);
//        Date pdaDate = new Date();
//        SimpleDateFormat sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//        String fDatePda = sdfPDaDate.format(pdaDate).trim();
//        return fDatePda;
    }

    public static void downLoadingSelfieImage(String SelfieNameURL,String SelfieName){
        String URL_String=  SelfieNameURL;
        String Video_Name=  SelfieName;

        try {

            URL url = new URL(URL_String);
            URLConnection connection = url.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolderServer + "/";

            File file2 = new File(PATH + Video_Name);
            if (file2.exists()) {
                file2.delete();
            }

            File file1 = new File(PATH);
            if (!file1.exists()) {
                file1.mkdirs();
            }


            File file = new File(file1, Video_Name);

            int size = connection.getContentLength();


            FileOutputStream fileOutput = new FileOutputStream(file);

            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[size];
            int bufferLength = 0;
            long total = 0;
            int current = 0;
            while ((bufferLength = inputStream.read(buffer)) != -1) {
                total += bufferLength;

                fileOutput.write(buffer, 0, bufferLength);
            }

            fileOutput.close();

        }
        catch (Exception e){

        }

    }

    public static void showAlertSingleButtonError(Context context,String msg)
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    /*****
     * Show PlayServices Not Available dialog
     *
     * @since 21/07/16
     */
    public static void showPlaySerivesNotAvailableDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name)
                .setMessage(context.getString(R.string.install_playservices))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPlaySerivesPage(context);
                    }
                })
                .show();
    }

    /**
     * @param context
     * @author Yogesh
     * @date 21/07/16
     * @purpose Open the Google Play Services webpage
     */
    public static void openPlaySerivesPage(Context context) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms"));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms"));
            webIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(webIntent);
        }
    }


    public static void showAlertSingleButtonInfo(Context context,String msg)
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.genTermInformation))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }


    public static boolean hasGPSDevice(Context context)
    {
        final LocationManager mgr = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if ( mgr == null ) return false;
        final List<String> providers = mgr.getAllProviders();
        if ( providers == null ) return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }
    public static String getDateInMonthTextFormat(Context context)
    {
        return TimeUtils.getNetworkDate(context,TimeUtils.DATE_FORMAT);
//        long  syncTIMESTAMP = System.currentTimeMillis();
//        Date dateobj = new Date(syncTIMESTAMP);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//        String curTime = df.format(dateobj);
//        return curTime;
    }

    public static void showInfoSingleButtonError(Context context,String msg)
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_ico)
                .setPositiveButton(context.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public static boolean isOnline(AppCompatActivity context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }



    public static Fragment getActiveFragment(AppCompatActivity context) {
        if (context.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = context.getSupportFragmentManager().getBackStackEntryAt(context.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (Fragment) context.getSupportFragmentManager().findFragmentByTag(tag);
    }

    public static String getDateAndTimeInMilliSecond(Context context)
    {
        return TimeUtils.getNetworkDateTime(context,"dd-MMM-yyyy HH:mm:ss.SSS");
//        long  syncTIMESTAMP = System.currentTimeMillis();
//        Date dateobj = new Date(syncTIMESTAMP);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
//        String curTime = df.format(dateobj);
//        return curTime;
    }
    public static String getSyncDateAndTimeSecond(Context context)
    {
        return TimeUtils.getNetworkDateTime(context,"dd-MMM-yyyy HH:mm:ss");
//        long  syncTIMESTAMP = System.currentTimeMillis();
//        Date dateobj = new Date(syncTIMESTAMP);
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
//        String curTime = df.format(dateobj);
//        return curTime;
    }

    public static String getCurrentTime(Context context) {
        return TimeUtils.getNetworkTime(context,"HH:mm:ss");
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        int second = calendar.get(Calendar.SECOND);
//
//        String currentTime = df.format(calendar.getTime());
//        return currentTime;
    }

    public static String downLoadingCmpttrImage(String SelfieNameURL, String SelfieName,String ImagesFolder) {
        String URL_String = SelfieNameURL;
        String Video_Name = SelfieName;
        String PATH = "";
        PATH = Environment.getExternalStorageDirectory() + "/" + ImagesFolder + "/" + SelfieName;
        File fdelete = new File(PATH);
        if (fdelete.exists()) {

        } else {
            try {
                PATH = "";
                URL url = new URL(URL_String);
                URLConnection connection = url.openConnection();
                HttpURLConnection urlConnection = (HttpURLConnection) connection;
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                PATH = Environment.getExternalStorageDirectory() + "/" + ImagesFolder + "/";

                File file2 = new File(PATH + Video_Name);
                if (file2.exists()) {
                    file2.delete();
                }

                File file1 = new File(PATH);
                if (!file1.exists()) {
                    file1.mkdirs();
                }

                File file = new File(file1, Video_Name);
                int size = connection.getContentLength();

                FileOutputStream fileOutput = new FileOutputStream(file);

                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[size];
                int bufferLength = 0;
                long total = 0;
                int current = 0;
                while ((bufferLength = inputStream.read(buffer)) != -1) {
                    total += bufferLength;
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();
                PATH += Video_Name;

            } catch (Exception e) {
                PATH = "";
            }
        }
        return PATH;
    }


    public String getAddressForDynamic(Context context,String latti,String longi){


        String areaToMerge="NA";
        Address address=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
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


    public static String getAddressOfProviders(Context mContext,String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mContext, Locale.ENGLISH);



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
		      /* //String address = addresses.get(0).getAddressLine(0);
		       String address = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
		       String city = addresses.get(0).getLocality();
		       String state = addresses.get(0).getAdminArea();
		       String country = addresses.get(0).getCountryName();
		       String postalCode = addresses.get(0).getPostalCode();
		       String knownName = addresses.get(0).getFeatureName();
		       FULLADDRESS=address+","+city+","+state+","+country+","+postalCode;
		      Toast.makeText(contextcopy, "ADDRESS"+address+"city:"+city+"state:"+state+"country:"+country+"postalCode:"+postalCode, Toast.LENGTH_LONG).show();*/

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

    public static String convertExponential(double firstNumber){
        String secondNumberAsString = String.format("%.10f",firstNumber);
        return secondNumberAsString;
    }

    public static String getGreetingFortheDay() {

        String wishString="";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            wishString="Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            wishString= "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            wishString="Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            wishString= "Good Night";
        }
        return wishString;
    }

    public static String doGetTime(String dateFormat) {
        if (!TrueTime.isInitialized()) {
//            Toast.makeText(MainActivity.this, "Sorry TrueTime not yet initialized. Trying again.", Toast.LENGTH_SHORT).show();
            return TimeUtils.getCurrentDeviceDateTime(dateFormat);
        }

        Date trueTime = TrueTime.now();
//        Date deviceTime = new Date();

//        Toast.makeText(MainActivity.this, ""+ _formatDate(trueTime, "yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("GMT+5:30")), Toast.LENGTH_SHORT).show();
        return  _formatDate(trueTime, dateFormat, TimeZone.getTimeZone("GMT+5:30"));
    }
    public static String _formatDate(Date date, String pattern, TimeZone timeZone) {
        DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        format.setTimeZone(timeZone);
        return format.format(date.getTime());
    }


    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    private static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

        public static  String fnGetFileNameToSave(Context ctnxt)
        {
            String GetFileNameToSave=getIMEI(ctnxt) + "." + 0 + "." + doGetTime("dd.MMM.yyyy.HH.mm.ss");
            return GetFileNameToSave;
        }

    public static void showGPSSettingsAlert(final Context context) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(context.getText(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    private static SettingsClient mSettingsClient;
    private static LocationSettingsRequest mLocationSettingsRequest;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    static boolean isGpsEnabled=false;
    public static boolean doCheckGPSEnable(final Context context) {

        isGpsEnabled=false;
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        mSettingsClient = LocationServices.getSettingsClient(context);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                        isGpsEnabled=true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isGpsEnabled=false;
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {

                                    Log.e("GPS","Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS","Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        isGpsEnabled=false;
                        Log.e("GPS","checkLocationSettings -> onCanceled");
                    }
                });
return isGpsEnabled;
    }

    /***
     * check the Api level and make the system bar transparent so that
     * it can cover whole systembar
     * @since 2/25/2016
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void makeStatusBarTranslucent(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

}
