package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.model.FlgDayEndRequestAccept;
import com.astix.allanasosfa.model.TblPDAVanDayEndDetResult;
import com.astix.allanasosfa.model.VanDayEnd;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.allanasosfa.truetime.TimeUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayCollectionReport extends BaseActivity  {
    ApiInterface apiService;

    public String[] xmlForWeb = new String[1];
    int serverResponseCode = 0;
    public int syncFLAG = 0;
    String fDate;
    SharedPreferences sharedPref;
    public int flgUnloading=0;
    DatabaseAssistant DASFA ;
    private ProgressDialog mProgressDialog;
    ArrayList<LinkedHashMap<String,ArrayList<String>>> arrList=new ArrayList<LinkedHashMap<String,ArrayList<String>>>();
    LinkedHashMap<String,ArrayList<String>> hmapFirstSection=new LinkedHashMap<String,ArrayList<String>>();
    LinkedHashMap<String,ArrayList<String>> hmapSecondSection=new LinkedHashMap<String,ArrayList<String>>();
    TextView txt_invoice,txt_cllctn,txt_chq_cllctn,txt_blncClctn,btn_UpdateCollection;
    LinearLayout ll_chequeDetails;
    Button btn_details,btn_Next;
    ImageView imgVw_back;
    String date_value="";
    String imei="";
    String rID;
    Integer flgCollReqATCycleEnd=1;
    Integer flgStockUnloadAtCycleEnd=1;
    Context ctx;
    public int dayEndButtonOrCycleEndForTable=0;
    public Context getCtx() {
        return ctx;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_collection_report);
        ctx = this;
        Intent intent=  getIntent();
        flgCollReqATCycleEnd=    intent.getIntExtra("flgCollReqATCycleEnd",0);
        flgStockUnloadAtCycleEnd= intent.getIntExtra("flgStockUnloadAtCycleEnd",0);
        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        arrList=mDataSource.fnGetDayEndOverAllCollectionReport();

        DASFA = new DatabaseAssistant(this);
        SharedPreferences sharePrefCycleOrDayEnd =getSharedPreferences(CommonInfo.CycleOrDayEndPreference,ctx.MODE_PRIVATE);

        if(sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd"))
        {
            dayEndButtonOrCycleEndForTable=sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd",0);
        }
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // imei = tManager.getDeviceId();

        if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
        {
            imei = tManager.getDeviceId();
            CommonInfo.imei=imei;
        }
        else
        {
            imei= CommonInfo.imei.trim();
        }
        TextView  txt_invoice=(TextView) findViewById(R.id.txt_invoice);
        TextView  txt_cllctn=(TextView) findViewById(R.id.txt_cllctn);
        TextView  txt_chq_cllctn=(TextView) findViewById(R.id.txt_chq_cllctn);
        TextView  txt_blncClctn=(TextView) findViewById(R.id.txt_blncClctn);
        LinearLayout ll_chequeDetails=(LinearLayout) findViewById(R.id.ll_chequeDetails);
        btn_Next= (Button) findViewById(R.id.btn_Next);

        if(CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd")==0 && CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd")==0)
        {
            btn_Next.setText("Done");
        }
        btn_details= (Button) findViewById(R.id.btn_details);
        btn_UpdateCollection=(Button) findViewById(R.id.btn_UpdateCollection);
        btn_UpdateCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date1 = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String fDate = TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_FORMAT);

                sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String fDateNew = TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_FORMAT);
                String rID = mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
               Intent storeIntent = new Intent(DayCollectionReport.this, collectionReportStoreList.class);
                storeIntent.putExtra("imei", CommonInfo.imei);
                storeIntent.putExtra("userDate", fDate);
                storeIntent.putExtra("pickerDate", fDateNew);
                storeIntent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                storeIntent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                storeIntent.putExtra("rID", rID);
                storeIntent.putExtra("PageFrom", "1");
                startActivity(storeIntent);
                finish();
            }
        });
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtDayEnd")==1 && flgStockUnloadAtCycleEnd==1) || (CommonInfo.hmapAppMasterFlags.get("flgStockUnloadAtCycleEnd")==1 && flgCollReqATCycleEnd==1 )) {
                    Intent intent = new Intent(DayCollectionReport.this, StockUnloadEndClosure.class);
                    intent.putExtra("IntentFrom", 0);
                    intent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                    intent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    DayEndClosureForDayRetrofit();
                }
            }
        });
        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent refresh = new Intent(DayCollectionReport.this, DayEndStoreCollectionsChequeReport.class);
                refresh.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                refresh.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                startActivity(refresh);
                finish();
            }
        });
        imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        imgVw_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayCollectionReport.this, AllButtonActivity.class);
                intent.putExtra("imei", CommonInfo.imei);
                DayCollectionReport.this.startActivity(intent);
                finish();
            }
        });

        if(arrList!=null)
        {
            hmapFirstSection=arrList.get(0);
            hmapSecondSection=arrList.get(1);
        }
        if(hmapFirstSection!=null && hmapFirstSection.size()>0)
        {
            ArrayList<String> arrFistrSectionDetails=hmapFirstSection.get("FirstSectionDetails");
            txt_invoice.setText(arrFistrSectionDetails.get(0));
            txt_cllctn.setText(arrFistrSectionDetails.get(1));
            txt_chq_cllctn.setText(arrFistrSectionDetails.get(2));
            txt_blncClctn.setText(arrFistrSectionDetails.get(3));
        }
        if(hmapSecondSection!=null && hmapSecondSection.size()>0)
        {
            LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>(hmapSecondSection);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                String RefNoChequeNoTrnNo=me2.getKey().toString();
                ArrayList<String> CheequeFulleDetails=(ArrayList<String>)me2.getValue();
                TextView txtChqName=getTextView(RefNoChequeNoTrnNo,false);
                TextView txtChqAmount=getTextView(CheequeFulleDetails.get(0).split(Pattern.quote("^"))[0],true);
                TextView txtChqDate=getTextView(CheequeFulleDetails.get(0).split(Pattern.quote("^"))[1],true);
                TextView txtChqBank=getTextView(CheequeFulleDetails.get(0).split(Pattern.quote("^"))[2],true);
                LinearLayout layoutChq=getLinearLayoutHorizontal(txtChqName,txtChqAmount,txtChqDate,txtChqBank);
                ll_chequeDetails.addView(layoutChq);
            }
        }

    }

    private LinearLayout getLinearLayoutHorizontal(TextView tvChqName,TextView tvChqAmount,TextView tvChqDate,TextView tvChqBank) {
        LinearLayout lay = new LinearLayout(DayCollectionReport.this);

        lay.setOrientation(LinearLayout.HORIZONTAL);
        //  lay.setBackgroundResource(R.drawable.card_background_white);

        lay.addView(tvChqName);
        lay.setPadding(0,1,0,5);
        lay.addView(tvChqAmount);
        lay.setBackgroundResource(R.drawable.card_background_white);
        lay.addView(tvChqDate);
        lay.addView(tvChqBank);
        return lay;

    }
    public TextView getTextView(String uomDes,boolean isMarginToSet)
    {


        TextView txtVw_ques=new TextView(DayCollectionReport.this);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 1f);
        if(isMarginToSet)
        {
            layoutParams1.setMargins(2,0,0,0);
        }
        txtVw_ques.setLayoutParams(layoutParams1);
        //  txtVw_ques.setTag(tagVal);

        txtVw_ques.setTextColor(getResources().getColor(R.color.blue));
        txtVw_ques.setText(uomDes);
        txtVw_ques.setPadding(5,0,0,0);
        txtVw_ques.setBackgroundResource(R.drawable.table_cell_bg_left);



        return txtVw_ques;
    }
    public void DayEndClosureForDayRetrofit(){
        String currSysDate;
        if(dayEndButtonOrCycleEndForTable==2)
        {
            showProgress(getResources().getString(R.string.txtEndingCycle));
        }
        else {
            showProgress(getResources().getString(R.string.txtEndingDay));
        }


        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_FORMAT);
        Date currDate= new Date();
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat(
                "dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String EndTS = TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_TIME_FORMAT);

        int cycleId=mDataSource.fetchtblVanCycleId();
        if(cycleId==-1)
        {
            cycleId=0;

        }

        SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String rID=mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
        currSysDate = TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_FORMAT);
        String crntDate = currSysDate.trim().toString();
        if(apiService==null){
            apiService= ApiClient.getClient().create(ApiInterface.class);
        }


        VanDayEnd vanDayEnd=new VanDayEnd();
        vanDayEnd.setIMEINo(CommonInfo.imei);
        vanDayEnd.setFlgUnloading(flgUnloading);
        vanDayEnd.setDayEndTime(EndTS);
        vanDayEnd.setAppVersionId(CommonInfo.DATABASE_VERSIONID);
        vanDayEnd.setVanLoadUnLoadCycleId(cycleId);
        vanDayEnd.setVisitDate(crntDate);
        vanDayEnd.setDayEndButtonOrCycleEnd(dayEndButtonOrCycleEndForTable);
        Call<TblPDAVanDayEndDetResult> tblPDAVanDayEndDetResultCall=apiService.Call_PDAVanDayEnd(vanDayEnd);
        tblPDAVanDayEndDetResultCall.enqueue(new Callback<TblPDAVanDayEndDetResult>() {
            @Override
            public void onResponse(Call<TblPDAVanDayEndDetResult> call, Response<TblPDAVanDayEndDetResult> response) {
                dismissProgress();
                if(response.code()==200){
                    Integer flgRequestAccept=0;
                    TblPDAVanDayEndDetResult TblPDAVanDayEndDetResult=  response.body();
                    List<FlgDayEndRequestAccept> flgDayEndRequestAccepts=  TblPDAVanDayEndDetResult.getTblPDAVanDayEndDetResult();
                    if(flgDayEndRequestAccepts.size()>0){
                        FlgDayEndRequestAccept flgDayEndRequestAccept=  flgDayEndRequestAccepts.get(0);

                        flgRequestAccept=flgDayEndRequestAccept.getFlgDayEndRequestAccept();
                        if(flgRequestAccept==1){
                            DayEndXMLFileUpload();
                        }
                        else{

                        }
                    }
                    else{
                        showAlertForError("No response for flgDayEndRequestAccept from server");
                    }

                }
                else{
                    showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<TblPDAVanDayEndDetResult> call, Throwable t) {
                dismissProgress();
                showAlertForError("Error while retreiving data from server");
            }
        });
    }
    protected void showProgress(String msg)
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            dismissProgress();
        }
        else
        {
            mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.genTermPleaseWaitNew), msg);
        }
    }
    protected void dismissProgress()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    public void showAlertForError(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(DayCollectionReport.this);

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
        android.support.v7.app.AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }
    public void DayEndXMLFileUpload()
    {
        rID=mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);

        if(rID.equals("0"))
        {
            rID=mDataSource.GetNotActiveRouteID();
        }
        mDataSource.updateActiveRoute(rID, 1);

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy.HH.mm.ss",Locale.ENGLISH);
        String startTS =TimeUtils.getNetworkDateTime(DayCollectionReport.this,TimeUtils.DATE_TIME_FORMAT);

        int DayEndFlg=0;
        int ChangeRouteFlg=0;

        int DatabaseVersion=mDataSource.dbHelper.DATABASE_VERSION;
        String AppVersionID=mDataSource.dbHelper.AppVersionID;

        int dayEndButtonOrCycleEndForTable=0;
        SharedPreferences sharePrefCycleOrDayEnd =getSharedPreferences(CommonInfo.CycleOrDayEndPreference,Context.MODE_PRIVATE);
        if(sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd"))
        {
            dayEndButtonOrCycleEndForTable=sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd",0);
        }
        mDataSource.insertTblDayStartEndDetails(imei,startTS,rID,DayEndFlg,ChangeRouteFlg,fDate,AppVersionID,dayEndButtonOrCycleEndForTable);//DatabaseVersion;//getVersionNumber

        int valDayEndOrChangeRoute=1;
        mDataSource.UpdateTblDayStartEndDetails(Integer.parseInt(rID), valDayEndOrChangeRoute);


        File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        if (!OrderXMLFolder.exists()) {
            OrderXMLFolder.mkdirs();
        }

        String routeID = mDataSource.GetActiveRouteIDSunil();
        StoreSelection.flgChangeRouteOrDayEnd = 0;


        String newfullFileName = imei + "." + routeID + "." + df.format(dateobj);
        try {
            DASFA.export(mDataSource.dbHelper.DATABASE_NAME, newfullFileName, routeID);
        }
        catch(Exception ex)
        {

        }

        mDataSource.savetbl_XMLfiles(newfullFileName, "3", "1");
        mDataSource.delDayEnd();
        new FullSyncDataNow(DayCollectionReport.this).execute();
    }
    private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
    {



        int responseCode=0;
        public FullSyncDataNow(DayCollectionReport activity)
        {

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


            if(dayEndButtonOrCycleEndForTable==2)
            {
                showProgress(getResources().getString(R.string.txtEndingCycle));
            }
            else {
                showProgress(getResources().getString(R.string.txtEndingDay));
            }

        }

        @Override

        protected Void doInBackground(Void... params)
        {


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
                            File file = new File(Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.OrderXMLFolder + "/" +fileUri);
                            file.delete();
                        }
                        else
                        {
                            String f1=fileUri;
                            if(fileUri.contains(".xml"))
                            {
                                f1=Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.OrderXMLFolder + "/" +fileUri;
                            }
                            else {
                                f1 = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.OrderXMLFolder + "/" + fileUri + ".xml";
                            }
                            // System.out.println("Sunil Again each file full path"+f1);
                            try
                            {
                                responseCode= upLoad2Server(f1,fileUri);
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

            if(responseCode == 200)
            {
                if(CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")!=0) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("FinalSubmit", 1);
                    editor.commit();
                }
                showAlertForSubmission(getString(R.string.DataSucc));



            }
            else
            {
                showDayEndError(getString(R.string.uploading_error_data));
                //showAlertSingleButtonError(getString(R.string.uploading_error_data));
                // checkXMLFilesInFolder();
            }



        }
    }
    public void showAlertForSubmission(String msg){
        AlertDialog.Builder alertDialogGps = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle("Information");
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage(msg);

        // On pressing Settings button
        alertDialogGps.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


              /*  Intent intent=new Intent(DayCollectionReport.this,AllButtonActivity.class);

                startActivity(intent);
                finish();*/
                finishAffinity();

            }
        });

        // Showing Alert Message
        alertDialogGps.create();
        alertDialogGps.show();
    }

    public void showDayEndError(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                        new FullSyncDataNow(DayCollectionReport.this).execute();
                    }
                }).create().show();
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
    public void delXML(String delPath)
    {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
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
}
