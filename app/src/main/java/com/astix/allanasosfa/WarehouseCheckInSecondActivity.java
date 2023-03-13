package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.model.ConfirmVanStock;
import com.astix.allanasosfa.model.PDAConfirmVanStockModel;
import com.astix.allanasosfa.model.TblPDAConfirmVanStockResult;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseCheckInSecondActivity extends BaseActivity implements CategoryCommunicator,MultipleInterfaceRetrofit {
    ApiInterface apiService;
    public int flgCheckAnyStockInMinus=0;
    LinkedHashMap<String, String> hmapctgry_details = new LinkedHashMap<String, String>();
    public int StoreCurrentStoreType=0;
    LinkedHashMap<String, String> hmapctgry_detaeils=new LinkedHashMap<String, String>();

    List<String> categoryNames;
    String defaultCatName_Id="0";
    Thread myThread;
    public ProgressDialog pDialog2STANDBYabhi;
    int progressBarStatus=0;
    public EditText ed_search;
    public ImageView  btn_go;
    ImageView img_ctgry;
    String previousSlctdCtgry="";
    CustomKeyboard mCustomKeyboardNum,mCustomKeyboardNumWithoutDecimal;


LinearLayout lLayout_main,ll_MsgIfNoRecords;
ImageView imgVw_back;
Button btn_save,btn_Refresh;
    public int CounttblDistributorProductLeft=0;
    String imei, fDate;
    public int syncFLAG = 0;
    String userId;
    String From="";
    public ProgressDialog pDialog2STANDBY;
  // SyncXMLfileData task2;
    public String[] xmlForWeb = new String[1];
    int serverResponseCode = 0;
    ProgressDialog  pDialogGetStores;
public TextView tv_MsgIfNoRecords;
    public String newfullFileName;
    LinkedHashMap<String,String> hmapDistPrdctStockCount =new LinkedHashMap<String,String>();

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            return true;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_entry);

        hmapDistPrdctStockCount= mDataSource.getDistStockCountName();
        CounttblDistributorProductLeft= mDataSource.fnCounttblDistributorProductLeft();
        userId= mDataSource.getUserId();
        getDataFromIntent();
        initializeAllViews();

        backBtn();
        conFirmBtn();
    }
    public void initializeAllViews(){

        btn_save= (Button) findViewById(R.id.btn_save);
        btn_Refresh= (Button) findViewById(R.id.btn_Refresh);
        lLayout_main= (LinearLayout) findViewById(R.id.lLayout_main);
        imgVw_back= (ImageView) findViewById(R.id.imgVw_back);
        tv_MsgIfNoRecords=(TextView) findViewById(R.id.tv_MsgIfNoRecords);
        ll_MsgIfNoRecords=(LinearLayout) findViewById(R.id.ll_MsgIfNoRecords);
        String cycleStatrtTime= mDataSource.fetchtblVanCycStartTime();
        if(!TextUtils.isEmpty(cycleStatrtTime))
        {
            tv_MsgIfNoRecords.setVisibility(View.VISIBLE);
            tv_MsgIfNoRecords.setText("Current Stock Loaded at : ");
        }
        else
        {
            tv_MsgIfNoRecords.setVisibility(View.GONE);
        }

        if(mDataSource.flgConfirmedWareHouse()==0)
        {
            btn_save.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_save.setVisibility(View.GONE);
        }

       /* if(CounttblDistributorProductLeft==0)
        {
            ll_MsgIfNoRecords.setVisibility(View.VISIBLE);
            tv_MsgIfNoRecords.setText(R.string.WareHouseMsgWithoutInvoiceNumber);
        }*/
        ed_search=(EditText) findViewById(R.id.ed_search);
        ed_search.setEnabled(false);
        ed_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
                mCustomKeyboardNum.hideCustomKeyboard();

                return false;
            }
        });

        img_ctgry=(ImageView) findViewById(R.id.img_ctgry);


        img_ctgry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                img_ctgry.setEnabled(false);
                customAlertStoreList(categoryNames,"Select Category");
            }
        });
        btn_go=(ImageView) findViewById(R.id.btn_go);

        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(ed_search.getText().toString().trim()))
                {

                    if(!ed_search.getText().toString().trim().equals(""))
                    {
                        searchProduct(ed_search.getText().toString().trim(),"");

                    }


                }

                else
                {

                }

            }


        });

        inflateRows();

    }
    void backBtn(){
        imgVw_back=(ImageView) findViewById(R.id.img_back_Btn);
        imgVw_back.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // finish();
             /*   Intent i=new Intent(WarehouseCheckInSecondActivity.this,WarehouseCheckInFirstActivity.class);
                i.putExtra("imei", imei);
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();*/

                if (From.equals("AllButtonActivity")) {
                    Intent intent=new Intent(WarehouseCheckInSecondActivity.this,AllButtonActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (From.equals("DayStart")) {
                    if(flgCheckAnyStockInMinus==1) {
                        showAlertSingleButtonSubmissionSuccessfullAndExit(getResources().getString(R.string.alrtForNegativeStock));
                    }
                    else
                    {
                        Intent i=new Intent(WarehouseCheckInSecondActivity.this,AllButtonActivity.class);
                        i.putExtra("imei", imei);

                        startActivity(i);
                        finish();
                    }

                }
                if(From.equals("WarehouseCheckInFirstActivity"))
                {
                    Intent i=new Intent(WarehouseCheckInSecondActivity.this,WarehouseCheckInFirstActivity.class);
                    i.putExtra("imei", imei);
                    i.putExtra("fDate", fDate);
                    startActivity(i);
                    finish();
                }

            }
        });

    }

    void getDataFromIntent(){
        Intent i=getIntent();
        imei=i.getStringExtra("imei");
        fDate=i.getStringExtra("fDate");
        From=i.getStringExtra("From");
        getCategoryDetail();
    }

public void inflateRows(){

        if(hmapDistPrdctStockCount!=null && hmapDistPrdctStockCount.size()>0){
            for (Map.Entry<String, String> entry : hmapDistPrdctStockCount.entrySet())
            {
                String key = entry.getKey();
                String finlQty = entry.getValue().split(Pattern.quote("^"))[0];

                String openingQty = entry.getValue().split(Pattern.quote("^"))[1];
                String addedQty = entry.getValue().split(Pattern.quote("^"))[2];
                String NetStockQty= entry.getValue().split(Pattern.quote("^"))[3];
                String CategoryID= entry.getValue().split(Pattern.quote("^"))[4];
                String StockOutQty= entry.getValue().split(Pattern.quote("^"))[5];
                //        System.out.println("RETRIEVE DATA :" + key + "  " + value);
                LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_rows = inflater1.inflate(R.layout.inflate_warehouse_row_checkin, null);

                TextView textView_Shortname= (TextView) view_rows.findViewById(R.id.textView_Shortname);
                TextView edittxt_sampleStck= (TextView) view_rows.findViewById(R.id.edittxt_sampleStck);
                TextView edittxt_stckOutQty= (TextView) view_rows.findViewById(R.id.edittxt_stckOutQty);

                TextView edittxt_openingStock= (TextView) view_rows.findViewById(R.id.edittxt_openingStock);
                TextView edittxt_addedStock= (TextView) view_rows.findViewById(R.id.edittxt_addedStock);

                TextView edittxt_NetStockQty= (TextView) view_rows.findViewById(R.id.edittxt_NetStockQty);

                textView_Shortname.setText(key.toString().split(Pattern.quote("^"))[1]);

                edittxt_sampleStck.setText(String.valueOf(finlQty));
                edittxt_openingStock.setText(String.valueOf(openingQty));
                edittxt_addedStock.setText(String.valueOf(addedQty));
                edittxt_NetStockQty.setText(String.valueOf(NetStockQty));
                edittxt_stckOutQty.setText(String.valueOf(StockOutQty));
                if(Integer.parseInt(finlQty)<0)
                {
                    flgCheckAnyStockInMinus=1;
                }
                view_rows.setTag(CategoryID);

                lLayout_main.addView(view_rows);


            }

        }

}

void conFirmBtn(){
    btn_Refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flgCheckAnyStockInMinus=0;
          //  new GetVanStockForDay(WarehouseCheckInSecondActivity.this).execute();
            CommonFunction.getStockData(WarehouseCheckInSecondActivity.this,imei, CommonInfo.RegistrationID,"",1);

        }
    });
    btn_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(flgCheckAnyStockInMinus==0) {
                if (!userId.equals("0")) {
                    //
                    //new FullSyncDataNow(WarehouseCheckInSecondActivity.this).execute();
                    FullSyncDataNowRetrofit();
                }
            }
            else if(flgCheckAnyStockInMinus==0) {
                //Negative Stock can not be confirmed, Kindly contact dealer.
                showAlertSingleButtonSubmissionSuccessfull(getResources().getString(R.string.alrtForNegativeStock));
            }

        }
    });
}

    public void showAlertSingleButtonSubmissionSuccessfull(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.genTermInformation))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_icon)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();

                    }
                }).create().show();
    }

    public void showAlertSingleButtonSubmissionSuccessfullAndExit(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.genTermInformation))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.info_icon)
                .setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                        finishAffinity();

                    }
                }).create().show();
    }

    @Override
    public void success(int flgCalledFrom) {

            Intent i=new Intent(WarehouseCheckInSecondActivity.this,WarehouseCheckInSecondActivity.class);


            i.putExtra("imei", imei);

            i.putExtra("fDate", fDate);
            i.putExtra("From", From);
            startActivity(i);
            finish();

    }

    @Override
    public void failure(int flgCalledFrom) {
        alertToShow("Error","Error while Retrieving Data.");
    }

    private class FullSyncDataNow extends AsyncTask<Void, Void, Integer>
    {


        ProgressDialog pDialogGetStores;
        public FullSyncDataNow(WarehouseCheckInSecondActivity activity)
        {

            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage("Submitting Warehouse Details...");
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Integer doInBackground(Void... params)
        {

            int flgDataConfirm=-1;


            try
            {
                 flgDataConfirm = newservice.fnConfirmStockUpdate(getApplicationContext());



            }
            catch (Exception e) {

                e.printStackTrace();
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }
            }
            return flgDataConfirm;
        }

        @Override
        protected void onCancelled()
        {

        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }
            if(result==-1)
            {
                showAlertSingleButtonError("Error while Submitting Data. Please retry.");
            }
            else if(result==0)
            {
                alertToRePopulateData();
            }
            else {
                mDataSource.insertConfirmWArehouse(userId,"1");
                mDataSource.inserttblDayCheckIn(1);
                Intent i=new Intent(WarehouseCheckInSecondActivity.this,AllButtonActivity.class);
                i.putExtra("imei", imei);

                startActivity(i);
                finish();
            }



        }
    }



    public void showAlertSingleButtonError(String msg)
    {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.AlertDialogHeaderErrorMsg))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(R.drawable.error_ico)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // new FullSyncDataNow(WarehouseCheckInSecondActivity.this).execute();
                        FullSyncDataNowRetrofit();
                    }
                }).create().show();
    }

    public void alertToRePopulateData()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(WarehouseCheckInSecondActivity.this);
        alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
        alertDialogNoConn.setMessage("There is change in stock. Please confirm it again to continue..");
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                     //  new GetVanStockForDay(WarehouseCheckInSecondActivity.this).execute();
                        flgCheckAnyStockInMinus=0;
                        //  new GetVanStockForDay(WarehouseCheckInSecondActivity.this).execute();
                        CommonFunction.getStockData(WarehouseCheckInSecondActivity.this,imei, CommonInfo.RegistrationID,"",1);
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }



    public void customAlertStoreList(final List<String> listOption, String sectionHeader)
    {

        final Dialog listDialog = new Dialog(WarehouseCheckInSecondActivity.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.search_list);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;




        TextView txt_section=(TextView) listDialog.findViewById(R.id.txt_section);
        txt_section.setText(sectionHeader);
        TextView txtVwCncl=(TextView) listDialog.findViewById(R.id.txtVwCncl);
        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);

        final EditText ed_search=(AutoCompleteTextView) listDialog.findViewById(R.id.ed_search);
        ed_search.setVisibility(View.GONE);
        final ListView list_store=(ListView) listDialog.findViewById(R.id.list_store);
        final CardArrayAdapterCategory cardArrayAdapter = new CardArrayAdapterCategory(WarehouseCheckInSecondActivity.this,listOption,listDialog,previousSlctdCtgry);

        //img_ctgry.setText(previousSlctdCtgry);

        list_store.setAdapter(cardArrayAdapter);
        //	editText.setBackgroundResource(R.drawable.et_boundary);
        img_ctgry.setEnabled(true);





        txtVwCncl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
                img_ctgry.setEnabled(true);


            }
        });




        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }


    @Override
    public void selectedOption(String selectedCategory, Dialog dialog) {
        dialog.dismiss();
        previousSlctdCtgry=selectedCategory;
        String lastTxtSearch=ed_search.getText().toString().trim();
        //img_ctgry.setText(selectedCategory);
        ed_search.setText(previousSlctdCtgry);
        if(hmapctgry_details.containsKey(selectedCategory))
        {
            searchProduct(selectedCategory,hmapctgry_details.get(selectedCategory));
        }
        else
        {
            searchProduct(selectedCategory,"");
        }



    }


    public void searchProduct(String filterSearchText,String ctgryId)
    {
        progressBarStatus = 0;

        defaultCatName_Id=ctgryId;
        /*hmapFilterProductList.clear();
        hmapPrdctIdPrdctNameVisible.clear();
        ll_prdct_detal.removeAllViews();
        hmapFilterProductList=mDataSource.getFileredProductListMap(filterSearchText.trim(),StoreCurrentStoreType,ctgryId);
        */

        if(hmapDistPrdctStockCount.size()>0)
        {
            pDialog2STANDBYabhi=ProgressDialog.show(WarehouseCheckInSecondActivity.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.Loading), false,true);
            myThread = new Thread(myRunnable);
            myThread.setPriority(Thread.MAX_PRIORITY);
            myThread.start();
        }
        else
        {
            allMessageAlert(WarehouseCheckInSecondActivity.this.getResources().getString(R.string.AlertFilter));
        }

		/*}

		else
		{
			allMessageAlert("Please put some extra filter on Search-Box to fetch related product");
		}*/




    }

    private void getCategoryDetail()
    {

        hmapctgry_details= mDataSource.fetch_Category_ForWarehouseCheckIn();
       // hmapctgry_details.put("Abhinav","1");
        int index=0;
        if(hmapctgry_details!=null)
        {
            categoryNames=new ArrayList<String>();
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                categoryNames.add(me2.getKey().toString());
                if(index==0)
                {
                    defaultCatName_Id=me2.getKey().toString()+"^"+me2.getValue().toString();
                }
                index=index+1;
            }
        }


    }

    private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(WarehouseCheckInSecondActivity.this);
        alertDialogNoConn.setTitle(WarehouseCheckInSecondActivity.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(WarehouseCheckInSecondActivity.this.getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        ed_search.requestFocus();
	                     /*if(isMyServiceRunning())
	               		{
	                     stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
	               		}
	                     finish();*/
                        //finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }
    Runnable myRunnable = new Runnable(){

        @Override
        public void run() {

            runOnUiThread(new Runnable(){

                @Override
                public void run() {


                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if(pDialog2STANDBYabhi != null && pDialog2STANDBYabhi.isShowing())
                            {
                                pDialog2STANDBYabhi.setCancelable(false);

                            }
                        }

                        public void onFinish() {
		               /*try {
		          Thread.sleep(2000);
		         } catch (InterruptedException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		         }*/
                            new IAmABackgroundTask().execute();

                            // createProductPrepopulateDetailWhileSearch(CheckIfStoreExistInStoreProdcutPurchaseDetails);
                        }
                    }.start();

                    //pDialog2STANDBYabhi.show(ProductOrderFilterSearch.this,getText(R.string.genTermPleaseWaitNew) ,"Loading Data Abhinav", true);
                    //pDialog2STANDBYabhi.getCurrentFocus();
                    // pDialog2STANDBYabhi.show();
                }

            });

        }
    };

    class IAmABackgroundTask extends
            AsyncTask<String, Integer, Boolean> {
        @SuppressWarnings("static-access")
        @Override
        protected void onPreExecute() {
         //   createProductPrepopulateDetailWhileSearch(CheckIfStoreExistInStoreProdcutPurchaseDetails);
            fnShowProductCategoryWise();
        }

        @Override
        protected void onPostExecute(Boolean result) {


            fnAbhinav(1000);
        }

        @Override
        protected Boolean doInBackground(String... params) {


            return true;

        }

    }

    public void countUp(int start)
    {

        if(pDialog2STANDBYabhi != null && pDialog2STANDBYabhi.isShowing())
        {

            pDialog2STANDBYabhi.setTitle(WarehouseCheckInSecondActivity.this.getResources().getString(R.string.MyNameAbhinav));
            pDialog2STANDBYabhi.setCancelable(true);
            pDialog2STANDBYabhi.cancel();
            pDialog2STANDBYabhi.dismiss();

        }

        else
        {
            countUp(start + 1);
        }
    }


    public void fnAbhinav(int mytimeval)
    {
        countUp(1);
    }
    public void fnShowProductCategoryWise(){

        if(hmapDistPrdctStockCount!=null && hmapDistPrdctStockCount.size()>0){
            for(int position=0;position<hmapDistPrdctStockCount.size();position++)
            {
                View llRowView=lLayout_main.getChildAt(position+1);
                if(defaultCatName_Id.equals("") || defaultCatName_Id.equals("0"))
                {
                    llRowView.setVisibility(View.VISIBLE);
                }
                else {
                    if (llRowView.getTag().toString().equals(defaultCatName_Id)) {
                        llRowView.setVisibility(View.VISIBLE);
                    } else {
                        llRowView.setVisibility(View.GONE);
                    }
                }
            }

        }

    }



    private class GetVanStockForDay extends AsyncTask<Void, Void, Void>
    {

        int flgStockOut=0;
        boolean serviceException=false;
        public GetVanStockForDay(WarehouseCheckInSecondActivity activity)
        {

        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();




            // Base class method for Creating ProgressDialog
            showProgress(getResources().getString(R.string.RetrivingDataMsg));


        }

        @Override
        protected Void doInBackground(Void... args)
        {


            try
            {



            }
            catch (Exception e)
            {
                Log.i("SvcMgr", "Service Execution Failed!", e);
            }
            finally
            {
                Log.i("SvcMgr", "Service Execution Completed...");
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);


            dismissProgress();   // Base class method for dismissing ProgressDialog

            flgStockOut= mDataSource.fetchtblStockUploadedStatus();
            //  flgStockOut=1;
            if(serviceException)
            {
                serviceException=false;
                alertToShow("Error","Error while Retrieving Data.");
                //    Toast.makeText(AllButtonActivity.this,"Please fill Stock out first for starting your market visit.",Toast.LENGTH_SHORT).show();
                //  showSyncError();
            }

            else
            {
                Intent i=new Intent(WarehouseCheckInSecondActivity.this,WarehouseCheckInSecondActivity.class);


                i.putExtra("imei", imei);

                i.putExtra("fDate", fDate);

                startActivity(i);
                finish();
            }


        }
    }


    public void alertToShow(String title,String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(WarehouseCheckInSecondActivity.this);
        alertDialogNoConn.setTitle(title);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }
    public void FullSyncDataNowRetrofit(){

        if(apiService==null){
            apiService= ApiClient.getClient().create(ApiInterface.class);
        }
        int vanCycleId= mDataSource.fetchtblVanCycleId();
        String CycStartTime= mDataSource.fetchtblVanCycleTime();
        ConfirmVanStock confirmVanStock=new ConfirmVanStock();
        confirmVanStock.setVanLoadUnLoadCycleId(vanCycleId);
        confirmVanStock.setPDACycleTime(CycStartTime);
        Call<PDAConfirmVanStockModel> tblPDAConfirmVanStockResultCall= apiService.Call_PDAConfirmVanStock(confirmVanStock);
        tblPDAConfirmVanStockResultCall.enqueue(new Callback<PDAConfirmVanStockModel>() {
            @Override
            public void onResponse(Call<PDAConfirmVanStockModel> call, Response<PDAConfirmVanStockModel> response) {
                if(response.code()==200){
                    Integer flgDataConfirmed=-1;
                    PDAConfirmVanStockModel pDAConfirmVanStockModel= response.body();
                    if(pDAConfirmVanStockModel.getTblPDAConfirmVanStockResult().size()>0){
                     TblPDAConfirmVanStockResult tblPDAConfirmVanStockResult=   pDAConfirmVanStockModel.getTblPDAConfirmVanStockResult().get(0);

                        flgDataConfirmed= tblPDAConfirmVanStockResult.getFlgDataConfirmed();
                    }
                    if(flgDataConfirmed==-1)
                    {
                        showAlertSingleButtonError("Error while Submitting Data. Please retry.");
                    }
                    else if(flgDataConfirmed==0)
                    {
                        alertToRePopulateData();
                    }
                    else {
                        mDataSource.insertConfirmWArehouse(userId,"1");
                        mDataSource.inserttblDayCheckIn(1);
                        Intent i=new Intent(WarehouseCheckInSecondActivity.this,AllButtonActivity.class);
                        i.putExtra("imei", imei);

                        startActivity(i);
                        finish();
                    }


                }
                else{
                    showAlertForError("Error while retreiving data from server");
                }

            }

            @Override
            public void onFailure(Call<PDAConfirmVanStockModel> call, Throwable t) {
                showAlertForError("Error while retreiving data from server");

            }
        });
    }
    public void showAlertForError(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(WarehouseCheckInSecondActivity.this);

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
}
