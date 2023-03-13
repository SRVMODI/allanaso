package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.camera.CustomCamera;
import com.astix.allanasosfa.location.LocationInterface;
import com.astix.allanasosfa.location.LocationRetreivingGlobal;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.utils.AppUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.allanasosfa.truetime.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class StoreClosedActivity extends BaseActivity implements LocationInterface,DeletePic,Camera.PictureCallback{

    private static final String TAG = "StoreClosedActivity";
    DatabaseAssistant DA ;
    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static String address,pincode,city,state;
    private final LinkedHashMap<String ,ArrayList<String>> hmapCtgryPhotoSection=new LinkedHashMap<>();
    private final LinkedHashMap<String ,Integer> hmapCtgry_Imageposition=new LinkedHashMap<>();
    private final LinkedHashMap<String ,ImageAdapter> hmapImageAdapter=new LinkedHashMap<>();
    private final LinkedHashMap<String ,String> hmapPhotoDetailsForSaving=new LinkedHashMap<>();
    private final ArrayList<Object> arrImageData=new ArrayList<>();
    private final long startTime = 15000;
    private final long interval = 200;
    public String StoreVisitCode="NA";
    public LocationManager locationManager;
    public PowerManager pm;
    public	 PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;
    String FusedLat = "0";
    String FusedLong = "0";
    String FusedAccuracy = "0";
    String FusedAddress = "0";
    String NetwLat = "0";
    String NetwLong = "0";
    String NetwAccuracy = "0";
    String NetwAddress = "0";
    String GpsLat = "0";
    String GpsLong = "0";
    String GpsAccuracy = "0";
    String GpsAddress = "0";
    public Location location;
    public String FusedLocationLatitudeWithFirstAttempt="0";
    public String FusedLocationLongitudeWithFirstAttempt="0";
    public String FusedLocationAccuracyWithFirstAttempt="0";
    public String AllProvidersLocation="";
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
    public boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;
    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;
    public String AccuracyFromLauncher="NA";
    public String ProviderFromLauncher="NA";
    public String storeID;
    public String selStoreName;
    public String imei;
    public String date;
    public String pickerDate;
    TextView drpdwn_closeReason;
    EditText et_OtherReason;
    ImageView btn_bck;
    Button btn_clickPic,btn_save;
    ExpandableHeightGridView expnd_GridView;
    LinkedHashMap<String,String> hmap_StoreCloseReasons;
    LinkedHashMap<String,String> hmap_ReasonsDescID = new LinkedHashMap<>();
    ArrayList<String> list_Reasons=new ArrayList<>();
    AlertDialog GPSONOFFAlert=null;
    String fusedData;
    int countSubmitClicked=0;
    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    ImageAdapter adapterImage=null;
    boolean flgListEmpty=false;
    ArrayList<String> list_ImgName;
    private Uri uriSavedImage;
    private float mDist=0;
    private String imageName;

    private int picAddPosition=0;
    private int removePicPositin=0;
    private String clickedTagPhoto="";

    private static File getOutputMediaFile()
    {
        //make a new file directory inside the "sdcard" folder
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder;
        File mediaStorageDir = new File(file_dj_path);
        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        // mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        //mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +CommonInfo.imei+"$"+ timeStamp + ".jpg");
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + CommonInfo.imei+timeStamp + ".jpg");
        return mediaFile;
    }

    private static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_closed);


        DA = new DatabaseAssistant(this);
        Intent passedvals = getIntent();
        selStoreName = passedvals.getStringExtra("SN");
        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate= passedvals.getStringExtra("pickerDate");

        imei= AppUtils.getIMEI(StoreClosedActivity.this);
        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");

        String startTS = TimeUtils.getNetworkDateTime(StoreClosedActivity.this,TimeUtils.DATE_TIME_FORMAT);
        StoreVisitCode= mDataSource.fnGetStoreVisitCode(storeID);
        //mDataSource.open();
        mDataSource.UpdateStoreStartVisit(storeID,startTS);
        //mDataSource.close();


        btn_clickPic= (Button) findViewById(R.id.btn_clickPic);
        et_OtherReason = (EditText) findViewById(R.id.et_OtherReason);
        et_OtherReason.setVisibility(View.GONE);

        expnd_GridView= (ExpandableHeightGridView) findViewById(R.id.expnd_GridView);
        expnd_GridView.setExpanded(true);

        btn_bck= (ImageView) findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bckBtnAlert();
            }
        });

        drpdwn_closeReason = (TextView) findViewById(R.id.drpdwn_closeReason);
        drpdwn_closeReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlertStoreList(getResources().getString(R.string.reason));
            }
        });

        btn_save= (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBtnAlert();
            }
        });

        //locationFetching();

        getDataFromDatabase();
        fetchPhotoDetails();

        clickImage();
    }

    void getDataFromDatabase()
    {
        hmap_StoreCloseReasons= mDataSource.getStoreClosedReasons();
        list_ImgName = mDataSource.getStoreClosedImgNameByStoreId(storeID,StoreVisitCode);

        if(hmap_StoreCloseReasons != null && hmap_StoreCloseReasons.size()>0)
        {
            for(Map.Entry<String,String> entry:hmap_StoreCloseReasons.entrySet())
            {
                hmap_ReasonsDescID.put(entry.getValue(),entry.getKey());
            }
        }

        String fetchedValue= mDataSource.getOtherReason(storeID,StoreVisitCode);

        String selectedDrpDwnValue="NA",selectedTextID="00";
        selectedTextID=fetchedValue.split(Pattern.quote("^"))[0];
        selectedDrpDwnValue=fetchedValue.split(Pattern.quote("^"))[1];

        if(selectedTextID.equals("-99"))
        {
            et_OtherReason.setVisibility(View.VISIBLE);
            drpdwn_closeReason.setText(hmap_StoreCloseReasons.get("-99"));
            et_OtherReason.setText(selectedDrpDwnValue);
        }
        else
        {
            drpdwn_closeReason.setText(hmap_StoreCloseReasons.get(selectedTextID));
        }

        btn_clickPic.setTag(storeID+"_"+selectedTextID);
        clickedTagPhoto=storeID+"_"+selectedTextID;
    }

    void bckBtnAlert()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(StoreClosedActivity.this);
        alert.setMessage(getText(R.string.BackButConfirm));
        alert.setTitle(getText(R.string.genTermInformation));
        alert.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();




                Intent nxtP4=new Intent(StoreClosedActivity.this,LastVisitDetails.class);
                nxtP4.putExtra("storeID", storeID);
                nxtP4.putExtra("SN", selStoreName);
                nxtP4.putExtra("imei", imei);
                nxtP4.putExtra("userdate", date);
                nxtP4.putExtra("pickerDate", pickerDate);
                nxtP4.putExtra("bck", 1);
                startActivity(nxtP4);
                finish();

            }
        });

        alert.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();

    }

    void saveBtnAlert()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(StoreClosedActivity.this);
        alert.setMessage(getText(R.string.alertAskSaveMsg));
        alert.setTitle(getText(R.string.genTermInformation));
        alert.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(savingDataAndValidate())
                {
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date dateobj = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                    String startTS = TimeUtils.getNetworkDateTime(StoreClosedActivity.this,TimeUtils.DATE_TIME_FORMAT);
                    //mDataSource.open();
                    mDataSource.UpdateStoreEndVisit(storeID,startTS);
                    mDataSource.UpdateStoreStoreClose(storeID,1,StoreVisitCode);
                    mDataSource.UpdateStoreSstat(storeID,3,StoreVisitCode);
                    mDataSource.UpdatetblStoreOrderVisitDayActivitySstat(storeID,3,StoreVisitCode,LastVisitDetails.TempStoreVisitCode);
                    //mDataSource.close();

                    if (hmapPhotoDetailsForSaving != null && hmapPhotoDetailsForSaving.size() > 0)
                    {
                        String drpdwnSelectedValue = "NA";
                        String drpdwnSelectedID="NA";

                        if (!hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("00")) //select
                        {
                            if (hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99")) //other
                            {
                                drpdwnSelectedValue = et_OtherReason.getText().toString();
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                            else
                            {
                                drpdwnSelectedValue = "NA";
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                        }
                        else
                        {
                            drpdwnSelectedValue="NA";
                            drpdwnSelectedID="00";
                        }

                        mDataSource.inserttblStoreCloseReasonSaving(storeID,drpdwnSelectedID,drpdwnSelectedValue,3,StoreVisitCode);

                        for (Map.Entry<String, String> entry : hmapPhotoDetailsForSaving.entrySet())
                        {
                            String photoName = entry.getKey();
                            String photoPath = entry.getValue().split(Pattern.quote("~"))[1];
                            String clickedDateTime = entry.getValue().split(Pattern.quote("~"))[2];

                            mDataSource.inserttblStoreClosedPhotoDetail(storeID, clickedDateTime, photoName, photoPath, 3,StoreVisitCode);
                            System.out.println("SAVING..."+storeID+"-"+clickedDateTime+"-"+photoName+"-"+photoPath);
                        }



                        locationFetching();

                    }
                    else
                    {
                        String drpdwnSelectedValue = "NA";
                        String drpdwnSelectedID="NA";

                        if (!hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("00"))
                        {
                            if (hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99"))
                            {
                                drpdwnSelectedValue = et_OtherReason.getText().toString();
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                            else
                            {
                                drpdwnSelectedValue = "NA";
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                        }
                        else
                        {
                            drpdwnSelectedValue="NA";
                            drpdwnSelectedID="00";
                        }

                        mDataSource.upDateCloseStoreReason(storeID,drpdwnSelectedID,drpdwnSelectedValue,StoreVisitCode);
                        System.out.println("SAVING UPDATE..."+storeID+"-"+drpdwnSelectedID+"-"+drpdwnSelectedValue);


                        locationFetching();
                    }
                }
            }
        });

        alert.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();

    }

    public void customAlertStoreList(String sectionHeader)
    {

        final Dialog listDialog = new Dialog(StoreClosedActivity.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.list_store_close);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        parms.dimAmount = (float) 0.5;

        TextView txt_header = (TextView) listDialog.findViewById(R.id.txt_header);
        txt_header.setText(sectionHeader);

        TextView txtVwCncl = (TextView) listDialog.findViewById(R.id.txtVwCncl);

        final LinearLayout ll_parentRows = (LinearLayout) listDialog.findViewById(R.id.ll_parentRows);
        if( hmap_StoreCloseReasons != null && hmap_StoreCloseReasons.size()>0)
        {
            for (Map.Entry<String,String> entry:hmap_StoreCloseReasons.entrySet())
            {
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.text_view_ques, null);

                final TextView txtVw_ques = (TextView) convertView.findViewById(R.id.txtVw_ques);
                txtVw_ques.setText(entry.getValue());
                txtVw_ques.setTag(entry.getKey());

                txtVw_ques.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDialog.dismiss();
                        if (hmap_ReasonsDescID.get(txtVw_ques.getText().toString()).equals("-99"))
                        {
                            drpdwn_closeReason.setText(txtVw_ques.getText());
                            et_OtherReason.setVisibility(View.VISIBLE);
                        } else {
                            drpdwn_closeReason.setText(txtVw_ques.getText());
                            et_OtherReason.setVisibility(View.GONE);
                        }
                    }
                });

                ll_parentRows.addView(convertView);
            }
        }

        txtVwCncl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
            }
        });

        listDialog.show();

    }

    void locationFetching() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

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
    }

    public void showSettingsAlert(){
        AppUtils.doCheckGPSEnable(StoreClosedActivity.this);
    }

    public void locationRetrievingAndDistanceCalculating()
    {
        LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
        llaaa.locationRetrievingAndDistanceCalculating(this,true,true,100,0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(GPSONOFFAlert!=null && GPSONOFFAlert.isShowing())
        {
            GPSONOFFAlert.dismiss();
        }
        if(wl!=null && wl.isHeld())
        {
            wl.release();
        }

    }



    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy){
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





    void clickImage()
    {
        /*String selectedTextID="00";

        selectedTextID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
        */
        if(list_ImgName == null || list_ImgName.size()==0)
        {
            adapterImage = new ImageAdapter(StoreClosedActivity.this);
            expnd_GridView.setAdapter(adapterImage);
            hmapImageAdapter.put(clickedTagPhoto,adapterImage);
        }

        btn_clickPic.setTag(clickedTagPhoto);
        btn_clickPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clickedTagPhoto=v.getTag().toString();
                openCustomCamara();
            }
        });
    }

    boolean savingDataAndValidate()
    {
        ImageAdapter adapterImage=hmapImageAdapter.get(clickedTagPhoto);
        if(adapterImage.getCount()==0)
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.alertClickPic));
            return false;
        }
       /* else if(flgListEmpty)
        {
            showAlertSingleButtonInfo("Please click atleast one pic of the store");
            return false;
        }*/
        else if((hmap_ReasonsDescID != null && hmap_ReasonsDescID.size()>0) && hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99") && TextUtils.isEmpty(et_OtherReason.getText()))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.alertOtherReason));
            return false;
        }
        else
        {
            return true;
        }
    }

    void fetchPhotoDetails()
    {
        if (list_ImgName != null && list_ImgName.size() > 0)
        {
            adapterImage = new ImageAdapter(StoreClosedActivity.this);
            expnd_GridView.setAdapter(adapterImage);
            hmapImageAdapter.put(clickedTagPhoto,adapterImage);

            hmapCtgryPhotoSection.put(clickedTagPhoto, list_ImgName);

            int picPosition=0;
            for (int i=0;i<list_ImgName.size();i++)
            {
                String imgName=list_ImgName.get(i);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imgName;
                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists())
                {
                    Bitmap bmp = decodeSampledBitmapFromFile(fImageShow.getAbsolutePath(), 80, 80);
                    adapterImage.add(i,bmp,imgName+"^"+clickedTagPhoto);

                    hmapCtgry_Imageposition.put(clickedTagPhoto,picPosition);
                    picPosition++;
                }
            }
        }
    }

    //custom camera
    private void openCustomCamara()
    {

        CustomCamera customCamera=new CustomCamera(StoreClosedActivity.this);
        customCamera.open(0);
    }




    private void setSavedImageToScrollView(Bitmap bitmap, String imageName, String valueOfKey, String clickedTagPhoto)
    {
        if(hmapCtgry_Imageposition!=null && hmapCtgry_Imageposition.size()>0)
        {
            if(hmapCtgry_Imageposition.containsKey(clickedTagPhoto))
            {
                picAddPosition= hmapCtgry_Imageposition.get(clickedTagPhoto);
                if(picAddPosition == -1)
                {
                    flgListEmpty=false;
                    picAddPosition=0;
                }
            }
            else
            {
                picAddPosition=0;
            }
        }
        else
        {
            picAddPosition=0;
        }

        removePicPositin=picAddPosition;
        ArrayList<String> listClkdPic=new ArrayList<String>();
        if(hmapCtgryPhotoSection!=null && hmapCtgryPhotoSection.containsKey(clickedTagPhoto))
        {
            listClkdPic=hmapCtgryPhotoSection.get(clickedTagPhoto);
        }

        listClkdPic.add(imageName);
        hmapCtgryPhotoSection.put(clickedTagPhoto,listClkdPic);
        ImageAdapter adapterImage=hmapImageAdapter.get(clickedTagPhoto);
        adapterImage.add(picAddPosition,bitmap,imageName+"^"+clickedTagPhoto);
        System.out.println("REMOVE AND PIC ADD..."+removePicPositin+"__"+picAddPosition);
        System.out.println("Picture Adapter"+picAddPosition);
        picAddPosition++;
        hmapCtgry_Imageposition.put(clickedTagPhoto,picAddPosition);

        String photoPath=valueOfKey.split(Pattern.quote("~"))[2];
        String clickedDateTime=valueOfKey.split(Pattern.quote("~"))[3];

        //key- imagName
        //value- businessId^CatID^TypeID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo

        hmapPhotoDetailsForSaving.put(imageName,clickedTagPhoto+"~"+photoPath+"~"+clickedDateTime);
        System.out.println("Hmap Photo..."+imageName+"^"+clickedTagPhoto+"^"+photoPath+"^"+clickedDateTime);

    }

    @Override
    public void delPic(Bitmap bmp, String imageNameToDel)
    {
        String  imageNameToDelVal=imageNameToDel.split(Pattern.quote("^"))[0];
        String tagPhoto=imageNameToDel.split(Pattern.quote("^"))[1];

        picAddPosition= hmapCtgry_Imageposition.get(tagPhoto);
        if(picAddPosition>1)
        {
            removePicPositin=picAddPosition-1;
        }
        else
        {
            removePicPositin=picAddPosition;
        }

        removePicPositin=removePicPositin-1;
        picAddPosition=picAddPosition-1;
        System.out.println("REMOVE AND PIC ADD DEL..."+removePicPositin+"__"+picAddPosition);
        hmapCtgry_Imageposition.put(tagPhoto,picAddPosition);
        //	String photoToBeDletedFromPath=mDataSource.getPdaPhotoPath(imageNameToDel);

        // mDataSource.updatePhotoValidation("0", imageNameToDel);

        ArrayList<String> listClkdPic=new ArrayList<String>();
        if(hmapCtgryPhotoSection!=null && hmapCtgryPhotoSection.containsKey(tagPhoto))
        {
            listClkdPic=hmapCtgryPhotoSection.get(tagPhoto);
        }

        if(listClkdPic.contains(imageNameToDelVal))
        {
            listClkdPic.remove(imageNameToDelVal);
            ImageAdapter adapterImage=hmapImageAdapter.get(tagPhoto);
            adapterImage.remove(bmp);
            hmapCtgryPhotoSection.put(tagPhoto,listClkdPic);
            if(listClkdPic.size()<1)
            {
                hmapCtgryPhotoSection.remove(tagPhoto);
            }
        }
        if(listClkdPic.size()==0)
        {
            flgListEmpty=true;
        }
        if(hmapPhotoDetailsForSaving.containsKey(imageNameToDelVal))
        {
            hmapPhotoDetailsForSaving.remove(imageNameToDelVal);
        }
        //  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageNameToDelVal;
        mDataSource.validateAndDelStoreClosePic(storeID,imageNameToDelVal,StoreVisitCode);
        File fdelete = new File(file_dj_path);
        if (fdelete.exists())
        {
            if (fdelete.delete())
            {
                callBroadCast();
            }
            else
            {
            }
        }
    }

    @Override
    public void getProductPhotoDetail(String productIdTag) {
    }

    private void callBroadCast() {
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
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFile = getOutputMediaFile();

        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);

        if (pictureFile == null) {
            return;
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();

            arrImageData.add(0,pictureFile);
            arrImageData.add(1,pictureFile.getName());
            if(pictureFile!=null)
            {
                File file=pictureFile;
                System.out.println("File +++"+pictureFile);
                imageName=pictureFile.getName();
                Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                uriSavedImage = Uri.fromFile(pictureFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteArray = stream.toByteArray();

                // Convert ByteArray to Bitmap::\
                //
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                String clkdTime = TimeUtils.getNetworkDateTime(StoreClosedActivity.this,TimeUtils.DATE_TIME_FORMAT);

                String valueOfKey=clickedTagPhoto+"~"+AddNewStore_DynamicSectionWise.selStoreID+"~"+uriSavedImage.toString()+"~"+clkdTime+"~"+"1";

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                setSavedImageToScrollView(bitmap, imageName,valueOfKey,clickedTagPhoto);
                System.out.println("merch data..."+imageName+"~~"+valueOfKey+"~~"+clickedTagPhoto);
            }
//Show dialog here
//...
//Hide dialog here

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        //refresh camera to continue preview--------------------------------------------------------------
        //	mPreview.refreshCamera(mCamera);
        //if want to release camera
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera=null;
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
    @Override
    public void onLocationRetrieved(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state, String fnAddress) {

     /*   this.GpsLat = GpsLat;
        this.GpsLong = GpsLong;
        this.GpsAccuracy = GpsAccuracy;
        this.GpsAddress = GpsAddress;

        this.NetwLat = NetwLat;
        this.NetwLong = NetwLong;
        this.NetwAccuracy = NetwAccuracy;
        this.NetwAddress = NetwAddress;

        this.FusedLat = FusedLat;
        this.FusedLong = FusedLong;
        this.FusedAccuracy = FusedAccuracy;
        this.FusedAddress = FusedAddress;*/
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String StampEndsTime = TimeUtils.getNetworkDateTime(StoreClosedActivity.this,TimeUtils.DATE_TIME_FORMAT);
        //mDataSource.open();
        mDataSource.deleteStorecloseLocationTableBasedOnStoreID(storeID,StoreVisitCode);
        mDataSource.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + finalAccuracy,fnAccurateProvider,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,0,0,StoreVisitCode,StampEndsTime);

        mDataSource.saveTblStorecloseLocationDetails(storeID,fnLati, fnLongi, String.valueOf(finalAccuracy), address, city, pincode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt,3,StoreVisitCode);

        mDataSource.UpdateStoreEndVisit(storeID, StampEndsTime);
        mDataSource.UpdateStoreFlag(storeID.trim(), 3);




        String presentRoute= mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

        SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

        String newfullFileName=imei+"."+presentRoute+"."+df1.format(dateobj);
        try {
            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists())
            {
                OrderXMLFolder.mkdirs();

            }
            String routeID= mDataSource.GetActiveRouteIDSunil();
            DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);
            mDataSource.savetbl_XMLfiles(newfullFileName, "3","1");
            mDataSource.UpdateXMLCreatedFilesTablesFlag(5);
        } catch (Exception e) {

            e.printStackTrace();

        }

        Intent in = new Intent(StoreClosedActivity.this, StoreSelection.class);
        in.putExtra("imei", imei);
        in.putExtra("userDate", date);
        in.putExtra("pickerDate", pickerDate);
        startActivity(in);
        finish();


    }

}