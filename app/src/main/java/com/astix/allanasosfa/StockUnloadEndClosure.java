package com.astix.allanasosfa;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.model.FlgDayEndRequestAccept;
import com.astix.allanasosfa.model.TblPDAVanDayEndDetResult;
import com.astix.allanasosfa.model.TblSaveVanStockRequestResult;
import com.astix.allanasosfa.model.VanDayEnd;
import com.astix.allanasosfa.model.VanStockRequest;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;
import com.astix.allanasosfa.sync.DatabaseAssistant;
import com.astix.allanasosfa.utils.AppUtils;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockUnloadEndClosure extends BaseActivity {
    ApiInterface apiService;

    public String[] xmlForWeb = new String[1];
    int serverResponseCode = 0;
    public int syncFLAG = 0;

    public int flgUnloading=0;
    DatabaseAssistant DASFA;
    String serviceException="NA";
    ArrayAdapter<String> dataAdapter = null;
    String[] storeNames;
    int intentFrom=0;
    LinkedHashMap<String, String> hmapStore_details=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapPrdct_details=new LinkedHashMap<String, String>();

    LinkedHashMap<String, String> hmapUOMMstrNameId=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapUOMMstrIdName=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapBaseUOMCalcValue=new LinkedHashMap<String, String>();
    LinkedHashMap<String,ArrayList<String>> hmapUOMPrdctWise;
    LinkedHashMap<String, String> hmapDfltUOMMstrPrdtWise;
    StringBuilder strReqStockToSend=new StringBuilder();

    View viewProduct;
    String date_value="";
    String imei="";
    String rID;
    LinkedHashMap<String,String> hmapBaseUOMID;
    String pickerDate="";
    public LinearLayout ll_product_stock;
    public String back="0";
    public int bck = 0;
    public LinearLayout listView;
    LinkedHashMap<String,String> hmapTotalCalcOnUOMSlctd=new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> hmapprdctUOMSlctd=new LinkedHashMap<String,String>();

    LinkedHashMap<String,String> hmapprdctQtyFilled=new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> hmapprdctQtyPrvsFilled=new LinkedHashMap<String,String>();
    SharedPreferences sharedPref;
    String fDate;
    TextView txt_Skip;
    Context ctx;
    Integer flgCollReqATCycleEnd=1;
    Integer flgStockUnloadAtCycleEnd=1;
    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }
    public int dayEndButtonOrCycleEndForTable=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_unload_end_closure);
        ctx = this;

            imei = AppUtils.getIMEI(ctx);
        DASFA= new DatabaseAssistant(this);
        SharedPreferences sharePrefCycleOrDayEnd =getSharedPreferences(CommonInfo.CycleOrDayEndPreference,ctx.MODE_PRIVATE);

        if(sharePrefCycleOrDayEnd.contains("dayEndButtonOrCycleEnd"))
        {
            dayEndButtonOrCycleEndForTable=sharePrefCycleOrDayEnd.getInt("dayEndButtonOrCycleEnd",0);
        }
        Intent intent=getIntent();
        intentFrom=intent.getIntExtra("IntentFrom",0);
        flgCollReqATCycleEnd=    intent.getIntExtra("flgCollReqATCycleEnd",0);
        flgStockUnloadAtCycleEnd= intent.getIntExtra("flgStockUnloadAtCycleEnd",0);
        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);

        getAllStoreListDetail();

        initialization();
    }


    private void getAllStoreListDetail()
    {

        hmapStore_details= mDataSource.fetch_Store_Req();
        hmapPrdct_details= mDataSource. fetch_Store_Req_Prdct();
        //LinkedHashMap<String,String> hmapPrdct_detailswqwwqw=mDataSource. fnCountVanStockProdct();
        ArrayList<LinkedHashMap<String,String>> listUOMData= mDataSource.getUOMMstrForRqstStock();
        hmapUOMMstrNameId=listUOMData.get(0);
        hmapUOMMstrIdName=listUOMData.get(1);
        hmapUOMPrdctWise= mDataSource.getPrdctMpngWithUOM();
        hmapBaseUOMID= mDataSource.getBaseUOMId();
        hmapDfltUOMMstrPrdtWise= mDataSource.getPrdctDfltMpngWithUOM();
        hmapBaseUOMCalcValue= mDataSource.getBaseUOMCalcValue();

    }

    public void initialization()
    {

        ImageView but_back=(ImageView)findViewById(R.id.backbutton);
        Button btn_sbmt= (Button) findViewById(R.id.btn_sbmt);
        txt_Skip= (TextView) findViewById(R.id.txt_Skip);
        String htmlString="<u><I>SKIP<I></u>";
        txt_Skip.setText(Html.fromHtml(htmlString));
        txt_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flgUnloading=0;
                //  new DayEndClosureForDay(StockUnloadEndClosure.this).execute();
                DayEndClosureForDayRetrofit();

            }
        });
        btn_sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline())
                {
                    flgUnloading=1;
                    if(dataSaved())
                    {
                        // new GetRqstStockForDay(StockUnloadEndClosure.this).execute();
                        GetRqstStockForDayRetrofit();
                    }

                    else
                    {
                        // new DayEndClosureForDay(StockUnloadEndClosure.this).execute();
                        DayEndClosureForDayRetrofit();


                      /*  SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("FinalSubmit", 1);
                        editor.commit();
                        Intent intent=new Intent(StockUnloadEndClosure.this,AllButtonActivity.class);

                        startActivity(intent);
                        finish();*/
                    }

                }
                else
                {
                    showNoConnAlert();
                }

            }
        });
        but_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(intentFrom==0)
                {
                    Intent intent=new Intent(StockUnloadEndClosure.this,DayCollectionReport.class);
                    intent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                    intent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                    startActivity(intent);
                    finish();
                }
                if(intentFrom==2)
                {
                    Intent intent=new Intent(StockUnloadEndClosure.this,AllButtonActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {

                    Intent intent=new Intent(StockUnloadEndClosure.this,DayEndStoreCollectionsChequeReport.class);
                    intent.putExtra("flgCollReqATCycleEnd",flgCollReqATCycleEnd);
                    intent.putExtra("flgStockUnloadAtCycleEnd",flgStockUnloadAtCycleEnd);
                    startActivity(intent);
                    finish();
                }


            }
        });

        createProductDetail();
    }

    public boolean dataSaved()
    {
        boolean isDataSaved=false;
        int index=0;
        strReqStockToSend=new StringBuilder();
        for(Map.Entry<String,String> entry:hmapPrdct_details.entrySet())
        {
            String prdctId=entry.getKey();
            TextView edRqrdStk= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
            if(edRqrdStk!=null)
            {
                if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))

                {
                    Double requiredStk=Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                    if(requiredStk>0)
                    {
                        TextView tvUOM= (TextView) listView.findViewWithTag (prdctId+"_tvUOM");
                        String uomIDSlctd="0";
                        if(tvUOM!=null)
                        {
                            Double valueInBaseUnit=0.0;
                            uomIDSlctd=hmapUOMMstrNameId.get(tvUOM.getText().toString());
                            if(!uomIDSlctd.equals(hmapBaseUOMID.get(prdctId)))
                            {
                                Double conversionUnit=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIDSlctd));
                                valueInBaseUnit=conversionUnit*requiredStk;

                            }
                            else
                            {
                                valueInBaseUnit=Double.parseDouble(String.valueOf(requiredStk));
                            }
                            isDataSaved=true;
                            if(index==0)
                            {
                                strReqStockToSend.append(prdctId+"^"+valueInBaseUnit+"$"+hmapBaseUOMID.get(prdctId)+"^"+uomIDSlctd);
                                index++;
                            }
                            else
                            {
                                strReqStockToSend.append("|").append(prdctId+"^"+valueInBaseUnit+"$"+hmapBaseUOMID.get(prdctId)+"^"+uomIDSlctd);
                            }

                        }
                    }

                }//end  if(TextUtils.isEmpty(edRqrdStk.getText().toString()))

            }//end if(edRqrdStk!=null)


            // hmapBaseUOMCalcValue

        }

        return isDataSaved;
    }

    public void createProductDetail() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = (LinearLayout) findViewById(R.id.listView1);


        if(hmapPrdct_details!=null && hmapPrdct_details.size()>0)
        {
            int index=1;
            for(Map.Entry<String,String> entryPrdct:hmapPrdct_details.entrySet())
            {

                viewProduct=inflater.inflate(R.layout.list_stock_unload,null);

                if (index % 2 == 0) {
                    viewProduct.setBackgroundResource(R.drawable.card_background);
                } else {
                    //ll_prdct_detal.getChildAt(position).setBackgroundColor(Color.parseColor("#ffffff"));
                    //ll_prdct_detal.getChildAt(position).setBackgroundColor(Color.parseColor("#F2F2F2"));
                    viewProduct.setBackgroundResource(R.drawable.card_background_white);

                }
                final String prdctId=entryPrdct.getKey();
                final String prdctName=entryPrdct.getValue();
                TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);
                tv_product_name.setText(prdctName);
                tv_product_name.setTag(prdctId+"_PrdctName");
                final TextView tvOpnStk=(TextView) viewProduct.findViewById(R.id.tvOpnStk);
                tvOpnStk.setTag(prdctId+"_openStk");
                if(hmapStore_details.containsKey(prdctId))
                {

                    tvOpnStk.setText(hmapStore_details.get(prdctId));
                }
                else
                {
                    tvOpnStk.setText("0");
                }

                final TextView tvReqStk=(TextView) viewProduct.findViewById(R.id.tvReqStk);
                tvReqStk.setTag(prdctId+"_edRqstStk");
                if(hmapStore_details.containsKey(prdctId))
                {

                    tvReqStk.setText(hmapStore_details.get(prdctId));
                }
                else
                {
                    tvReqStk.setText("0");
                }

                final TextView tvUOM=(TextView) viewProduct.findViewById(R.id.tvUOM);
                tvUOM.setTag(prdctId+"_tvUOM");
                //hmapBaseUOMID.get(prdctId)
                if(hmapBaseUOMID.containsKey(prdctId))
                {
                    tvUOM.setText(hmapUOMMstrIdName.get(hmapBaseUOMID.get(prdctId)));
                    hmapprdctUOMSlctd.put(prdctId,hmapBaseUOMID.get(prdctId));
                    if(hmapStore_details.containsKey(prdctId))
                    {
                        String uomIdSlctdDft= hmapBaseUOMID.get(prdctId);
                        Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctdDft));
                        Double valueOfStock=Double.parseDouble(hmapStore_details.get(prdctId));
                        Double tmpcrntStockVal=valueOfStock/conversionUnitSlctdUOM;
                        if(tmpcrntStockVal>0)
                        {
                            hmapTotalCalcOnUOMSlctd.put(prdctId,""+tmpcrntStockVal);
                        }

                        Double crntStockVal= Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(tmpcrntStockVal)));
                        tvOpnStk.setText(""+crntStockVal);

                    }


                }
                else
                {
                    tvUOM.setText(hmapUOMMstrIdName.get(hmapBaseUOMID.get(prdctId)));
                    hmapprdctUOMSlctd.put(prdctId,hmapBaseUOMID.get(prdctId));
                }


              /*  final TextView tvFnlStock=(TextView) viewProduct.findViewById(R.id.tvFnlStock);
                tvFnlStock.setTag(prdctId+"_tvFnlStock");
                if(hmapStore_details.containsKey(prdctId))
                {
                    tvFnlStock.setText(hmapStore_details.get(prdctId));
                }
                else
                {
                    tvFnlStock.setText("0");
                }
*/

                tvReqStk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(hmapUOMPrdctWise.containsKey(prdctId))
                        {
                            customReqStock(prdctId);
                        }

                    }
                });

                tvUOM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog listDialog = new Dialog(StockUnloadEndClosure.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.activity_list, null);
                        EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                        inputSearch.setVisibility(View.GONE);
                        TextView txt_header= (TextView) convertView.findViewById(R.id.txt_header);
                        txt_header.setText("Unit Of Measurments");
                        final ListView listUOM = (ListView)convertView. findViewById(R.id.list_view);

                        String[] UOMArray;



                        if(hmapUOMPrdctWise.containsKey(prdctId))
                        {
                            UOMArray=new String[(hmapUOMPrdctWise.get(prdctId)).size()];
                            //  LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapUOM);
                            ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);
                            int UomIndex=0;
                            for(String uomToSpinner:listPrdctUOM)
                            {
                                if(hmapUOMMstrIdName.containsKey(uomToSpinner))
                                {
                                    UOMArray[UomIndex]=hmapUOMMstrIdName.get(uomToSpinner);


                                    UomIndex++;
                                }
                            }
                            if(UOMArray!=null && UOMArray.length>0)
                            {
                                ArrayAdapter  adapterUOM = new ArrayAdapter<String>(StockUnloadEndClosure.this, R.layout.list_item, R.id.product_name, UOMArray);
                                listUOM.setAdapter(adapterUOM);
                                listDialog.setContentView(convertView);
                                // listDialog.setTitle(getResources().getString(R.string.txtQualification));



                                listUOM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String abc=listUOM.getItemAtPosition(position).toString().trim();
                                        tvUOM.setText(abc);

                                        String uomIdSlctd= hmapUOMMstrNameId.get(abc);

                                        String prdctId=tvUOM.getTag().toString().split(Pattern.quote("_"))[0];
                                        if(hmapStore_details.containsKey(prdctId))
                                        {

                                            View viewproductName=listView.findViewWithTag(prdctId+"_PrdctName");
                                            if(viewproductName!=null && viewproductName instanceof TextView)
                                            {
                                                Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctd));
                                                String productName=((TextView) viewproductName).getText().toString();
                                                Double valueOfStock=Double.parseDouble(hmapStore_details.get(prdctId));
                                                Double crntStockVal=valueOfStock/conversionUnitSlctdUOM;
                                                crntStockVal= Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(crntStockVal)));
                                                tvOpnStk.setText(""+crntStockVal);
                                            }

                                        }
                                        if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                                        {
                                            String prvsUomIdSlctd= hmapprdctUOMSlctd.get(prdctId);
                                            Double conversionUnit=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+prvsUomIdSlctd));
                                            Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctd));
                                            Double valueInBaseUnit=conversionUnit/conversionUnitSlctdUOM;
                                            valueInBaseUnit=valueInBaseUnit*Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                                            // valueInBaseUnit=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(valueInBaseUnit)));
                                            hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                                            TextView txtRqstVw= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
                                            //  TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                                            TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                                            if((txtRqstVw!=null) && (txtOpnStck!=null))
                                            {
                                                Double valueToPut=  Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapTotalCalcOnUOMSlctd.get(prdctId))));
                                                txtRqstVw.setText(""+valueToPut);
                                                Double realStock=Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                                                Double finalStock=realStock+Double.parseDouble(txtOpnStck.getText().toString());

                                                finalStock=Double.parseDouble(new DecimalFormat("##.##").format(finalStock));



                                            }







                                        }

                                        hmapprdctUOMSlctd.put(prdctId,hmapUOMMstrNameId.get(abc));
                                        listDialog.dismiss();

                                    }
                                });
                            }



                        }






                        listDialog.show();

                    }
                });

                index++;
                listView.addView(viewProduct);

                if(hmapUOMPrdctWise!=null && hmapUOMPrdctWise.containsKey(prdctId))
                {
                    ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);
                    if(listPrdctUOM!=null && listPrdctUOM.size()>0)
                    {
                        valueToSet(prdctId,listPrdctUOM);
                    }
                }


            }



        }



    }


    public void DayEndXMLFileUpload()
    {
        rID= mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);//CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType

        if(rID.equals("0"))
        {
            rID= mDataSource.GetNotActiveRouteID();
        }
        mDataSource.updateActiveRoute(rID, 1);

        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy.HH.mm.ss",Locale.ENGLISH);
        String startTS = TimeUtils.getNetworkDateTime(StockUnloadEndClosure.this,TimeUtils.DATE_TIME_FORMAT);

        int DayEndFlg=0;
        int ChangeRouteFlg=0;

        int DatabaseVersion= mDataSource.dbHelper.DATABASE_VERSION;
        String AppVersionID= mDataSource.dbHelper.AppVersionID;
        int dayEndButtonOrCycleEndForTable=0;
        SharedPreferences sharePrefCycleOrDayEnd =getSharedPreferences(CommonInfo.CycleOrDayEndPreference,ctx.MODE_PRIVATE);
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
        new FullSyncDataNow(StockUnloadEndClosure.this).execute();
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


               /* Intent intent=new Intent(StockUnloadEndClosure.this,AllButtonActivity.class);

                startActivity(intent);
                finish();*/
                finishAffinity();

            }
        });

        // Showing Alert Message
        alertDialogGps.create();
        alertDialogGps.show();
    }

    public void showAlertStockOut(String title, String msg, final boolean isStockValidationAlrt, final String prdctId)
    {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(StockUnloadEndClosure.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                if(isStockValidationAlrt)
                {
                    if(hmapUOMPrdctWise!=null && hmapUOMPrdctWise.containsKey(prdctId))
                    {
                        ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);
                        if(listPrdctUOM!=null && listPrdctUOM.size()>0)
                        {
                            valueToSet(prdctId,listPrdctUOM);
                            if(hmapStore_details.containsKey(prdctId))
                            {
                                TextView txtUnloadStk= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");

                                String prvsUomIdSlctd= hmapprdctUOMSlctd.get(prdctId);
                                Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+prvsUomIdSlctd));
                                Double valueOfStock=Double.parseDouble(hmapStore_details.get(prdctId));
                                Double tmpcrntStockVal=valueOfStock/conversionUnitSlctdUOM;
                                if(tmpcrntStockVal>0)
                                {
                                    hmapTotalCalcOnUOMSlctd.put(prdctId,""+tmpcrntStockVal);
                                }
                                Double crntStockVal= Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(tmpcrntStockVal)));
                                if(txtUnloadStk!=null)
                                {
                                    txtUnloadStk.setText(""+crntStockVal);
                                }



                            }
                        }
                    }

                }
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void customReqStock(final String prdctId)
    {

        final Dialog listDialog = new Dialog(StockUnloadEndClosure.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.custom_req_stock);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        parms.width=   WindowManager.LayoutParams.MATCH_PARENT;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;


        LinearLayout ll_reqStockViews= (LinearLayout) listDialog.findViewById(R.id.ll_reqStockViews);

        Button btn_Cancel=(Button) listDialog.findViewById(R.id.btn_Cancel);
        Button btn_Done=(Button) listDialog.findViewById(R.id.btn_Done);
        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);



        //  LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapUOM);
        ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);

        for(String uomToSpinner:listPrdctUOM)
        {
            if(hmapUOMMstrIdName.containsKey(uomToSpinner))
            {
                String UOMDesc=hmapUOMMstrIdName.get(uomToSpinner);
                TextView txtVw= getTextView(UOMDesc);
                //prdctId+"^"+uomIDSlctd
                View edText=getEditTextView(4,prdctId+"^"+uomToSpinner);
                LinearLayout linearLayout=getLinearLayoutHorizontal(txtVw,edText);
                ll_reqStockViews.addView(linearLayout);

            }


        }



        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);
                hmapTotalCalcOnUOMSlctd.remove(prdctId);
                boolean isUnloadExceeds=false;
                for(String uomToSpinner:listPrdctUOM)
                {
                    if(hmapUOMMstrIdName.containsKey(uomToSpinner))
                    {

                        String UomId=uomToSpinner;
                        String uomIdSlctd= hmapprdctUOMSlctd.get(prdctId);
                        String tagVal=prdctId+"^"+UomId;

                        if( hmapprdctQtyFilled.containsKey(tagVal))
                        {
                            //(String editTextvalue,String uomIdSlctd,String UomId,String prdctId,String tagVal)
                            if(!isUnloadExceeds)
                            {
                                isUnloadExceeds= addValue(hmapprdctQtyFilled.get(tagVal),uomIdSlctd,UomId,prdctId,tagVal);
                            }


                        }
                        else
                        {

                        }




                    }


                }

                if((hmapTotalCalcOnUOMSlctd.containsKey(prdctId)) && (!isUnloadExceeds))
                {
                    //prdctId+"_edRqstStk"
                    TextView txtRqstVw= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
                    //  tvFnlStock.setTag(prdctId+"_tvFnlStock");
                    //TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                    TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                    if((txtRqstVw!=null)  && (txtOpnStck!=null) && (hmapTotalCalcOnUOMSlctd!=null))
                    {
                        Double realStock=Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                        Double finalStock=realStock+Double.parseDouble(txtOpnStck.getText().toString());
                        Double requestedStock=Double.parseDouble(new DecimalFormat("##.##").format(realStock));
                        finalStock=Double.parseDouble(new DecimalFormat("##.##").format(finalStock));

                        txtRqstVw.setText(""+requestedStock);
                        //txtFinalStock.setText(""+finalStock);


                    }



                }

                else if(!isUnloadExceeds)
                {
                    //prdctId+"_edRqstStk"
                    TextView txtRqstVw= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
                    //  tvFnlStock.setTag(prdctId+"_tvFnlStock");
                    //TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                    TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                    if((txtRqstVw!=null) && (txtOpnStck!=null))
                    {


                        txtRqstVw.setText("0");
                        // txtFinalStock.setText(txtOpnStck.getText().toString());


                    }

                }

                listDialog.dismiss();

            }
        });





        //	editText.setBackgroundResource(R.drawable.et_boundary);





        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();


            }
        });




        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }


    public TextView getTextView(String uomDes)
    {


        TextView txtVw_ques=new TextView(StockUnloadEndClosure.this);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 1f);
        txtVw_ques.setLayoutParams(layoutParams1);
        //  txtVw_ques.setTag(tagVal);

        txtVw_ques.setTextColor(getResources().getColor(R.color.blue));
        txtVw_ques.setText(uomDes);




        return txtVw_ques;
    }

    public View getEditTextView(int maxLength, final String tagVal)
    {
        View viewEditText = null;


        viewEditText=getLayoutInflater().inflate(R.layout.edit_text_ans_numeric, null);
        final EditText editText=(EditText) viewEditText.findViewById(R.id.et_numeric);
        // editText.setHint(ed_hint);

        // System.out.println("AnsCntrolType = "+ansControlInputTypeID);
        //	editText.setBackgroundResource(R.drawable.et_boundary);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 1f);
        editText.setLayoutParams(layoutParams1);
        editText.setTag(tagVal);

        if(hmapprdctQtyFilled.containsKey(tagVal))
        {
            editText.setText(hmapprdctQtyFilled.get(tagVal));
        }


        //et_alphabet.setHint(ed_hint);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(FilterArray);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!TextUtils.isEmpty(editText.getText().toString()))
                {
                    hmapprdctQtyFilled.put(tagVal,editText.getText().toString());

                }
                else
                {
                    if(hmapprdctQtyFilled.containsKey(tagVal))
                    {

                        hmapprdctQtyFilled.remove(tagVal);
                    }

                }
            }

        });
        return viewEditText;
    }


    private LinearLayout getLinearLayoutHorizontal(TextView tv,View edText) {
        LinearLayout lay = new LinearLayout(StockUnloadEndClosure.this);

        lay.setOrientation(LinearLayout.HORIZONTAL);
        //  lay.setBackgroundResource(R.drawable.card_background_white);

        lay.addView(tv);
        lay.setPadding(0,5,0,0);
        lay.addView(edText);

        return lay;

    }

    public boolean addValue(String editTextvalue,String uomIdSlctd,String UomId,String prdctId,String tagVal)
    {
        boolean isUnloadExceeds=false;
        if(!TextUtils.isEmpty(editTextvalue) && (Integer.parseInt(editTextvalue)>0))
        {
            TextView txtOpngStck= (TextView) listView.findViewWithTag(prdctId+"_openStk");
            Double stckInVan=0.0;
            if(txtOpngStck!=null)
            {
                stckInVan=Double.parseDouble(txtOpngStck.getText().toString());
            }


            if(uomIdSlctd.equals(UomId))
            {
                if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                {
                    Double value= Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                    Double totalVaue= value+Double.parseDouble(editTextvalue);

                    if(totalVaue>stckInVan)
                    {
                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }

                }
                else
                {

                    if(Double.parseDouble(editTextvalue)>stckInVan)
                    {

                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,editTextvalue);
                    }
                    // hmapTotalCalcOnUOMSlctd.put(prdctId,editTextvalue);
                }
            }
            else  if(UomId.equals(hmapBaseUOMID.get(prdctId)))
            {
                Double requiredStk=Double.parseDouble(editTextvalue);
                Double conversionUnit=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctd));
                Double valueInBaseUnit=requiredStk/conversionUnit;
                if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                {
                    Double value= Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                    Double totalVaue= value+valueInBaseUnit;

                    if(totalVaue>stckInVan)
                    {
                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }
                    //hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                }
                else
                {

                    if(valueInBaseUnit>stckInVan)
                    {
                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                    }
                    // hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                }


            }
            else
            {
                Double requiredStk=Double.parseDouble(editTextvalue);
                Double conversionUnit=Double.parseDouble(hmapBaseUOMCalcValue.get(tagVal));
                Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctd));
                Double valueInBaseUnit=conversionUnit/conversionUnitSlctdUOM;
                valueInBaseUnit=valueInBaseUnit*requiredStk;
                if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                {
                    Double value= Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                    Double totalVaue= value+valueInBaseUnit;
                    if(totalVaue>stckInVan)
                    {
                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }
                    // hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                }
                else
                {
                    if(valueInBaseUnit>stckInVan)
                    {
                        isUnloadExceeds=true;
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadVaidation),true,prdctId);
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                    }
                    //hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                }

            }

        }
        return isUnloadExceeds;
    }


    public void valueToSet(String prdctId,ArrayList<String> listPrdctUOM)
    {
        if(hmapStore_details.containsKey(prdctId))
        {
            Double prdctStckCount=Double.parseDouble(hmapStore_details.get(prdctId));

            if(prdctStckCount>0.0)
            {
                for(String uomToSpinner:listPrdctUOM)
                {
                    Double baseValue=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomToSpinner));
                    if(prdctStckCount>0)
                    {
                        if(baseValue<=prdctStckCount)
                        {
                            Double valueToSet=prdctStckCount/baseValue;
                            if(valueToSet.intValue()>0)
                            {
                                hmapprdctQtyFilled.put(prdctId+"^"+uomToSpinner,String.valueOf(valueToSet.intValue()));
                            }
                            prdctStckCount=prdctStckCount%baseValue;

                        }
                    }
                    else
                    {
                        break;
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


    private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
    {



        int responseCode=0;
        public FullSyncDataNow(StockUnloadEndClosure activity)
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


                if(AllFilesName.length>0)
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
                        new FullSyncDataNow(StockUnloadEndClosure.this).execute();
                    }
                }).create().show();
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

    public void delXML(String delPath)
    {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
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
        fDate = TimeUtils.getNetworkDateTime(StockUnloadEndClosure.this,TimeUtils.DATE_FORMAT);
        Date currDate= new Date();
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat(
                "dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String EndTS = TimeUtils.getNetworkDateTime(StockUnloadEndClosure.this,TimeUtils.DATE_TIME_FORMAT);

        int cycleId= mDataSource.fetchtblVanCycleId();
        if(cycleId==-1)
        {
            cycleId=0;

        }

        SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String rID= mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);//CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType
        currSysDate = currDateFormat.format(currDate).toString();
        String crntDate = TimeUtils.getNetworkDateTime(StockUnloadEndClosure.this,TimeUtils.DATE_FORMAT);
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
    public void showAlertForError(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(StockUnloadEndClosure.this);

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
    public void GetRqstStockForDayRetrofit(){
        if(apiService==null){
            apiService= ApiClient.getClient().create(ApiInterface.class);
        }
        Date currDate= new Date();
        SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String currSysDate;
        currSysDate = currDateFormat.format(currDate).toString();
        String crntDate = TimeUtils.getNetworkDateTime(StockUnloadEndClosure.this,TimeUtils.DATE_FORMAT);
         String prsnCvrgId_NdTyp= mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();

        VanStockRequest vanStockRequest =new VanStockRequest();
        vanStockRequest.setIMEINo(imei);
        vanStockRequest.setCoverageAreaNodeId(Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0]));
        vanStockRequest.setCoverageAreaNodeType(Integer.parseInt(prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]));
        vanStockRequest.setProductString(strReqStockToSend.toString());
        vanStockRequest.setStatusId(4);
        vanStockRequest.setTrnDate(crntDate);
        Call<TblSaveVanStockRequestResult> tblSaveVanStockRequestResultCall=apiService.Call_SaveVanStockRequest(vanStockRequest);
        tblSaveVanStockRequestResultCall.enqueue(new Callback<TblSaveVanStockRequestResult>() {
            @Override
            public void onResponse(Call<TblSaveVanStockRequestResult> call, Response<TblSaveVanStockRequestResult> response) {
                if(response.code()==200){
                    Integer flgRequestAccept=0;
                    TblSaveVanStockRequestResult tblSaveVanStockRequestResult=  response.body();
                    flgRequestAccept= tblSaveVanStockRequestResult.getFlgRequestAccept();
                    if(flgRequestAccept==1){
                        DayEndClosureForDayRetrofit();
                    }
                    else if(flgRequestAccept==0){
                        showAlertStockOut(getString(R.string.genTermNoDataConnection),getString(R.string.AlertVANStockConfrmDstrbtr),false,"0");
                    }
                    else{
                        showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogUnloadStock),false,"0");
                    }
                }
                else{

                    showAlertForError("Error while retreiving data from server");
                }

            }

            @Override
            public void onFailure(Call<TblSaveVanStockRequestResult> call, Throwable t) {
                dismissProgress();
                showAlertForError("Error while retreiving data from server");

            }
        });
    }
}
