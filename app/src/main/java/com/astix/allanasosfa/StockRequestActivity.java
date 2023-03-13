package com.astix.allanasosfa;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
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

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.model.TblSaveVanStockRequestResult;
import com.astix.allanasosfa.model.VanStockRequest;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;
import com.allanasosfa.truetime.TimeUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockRequestActivity extends BaseActivity implements MultipleInterfaceRetrofit{

    ApiInterface apiService;
    boolean serviceException=false;
    ArrayAdapter<String> dataAdapter = null;
    String[] storeNames;

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
    String fDate;
    LinkedHashMap<String,String> hmapprdctQtyFilled=new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> hmapprdctQtyPrvsFilled=new LinkedHashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_request);

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

        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = TimeUtils.getNetworkDateTime(StockRequestActivity.this,TimeUtils.DATE_FORMAT);
        getAllStoreListDetail();

        initialization();
    }


    private void getAllStoreListDetail()
    {

        hmapStore_details= mDataSource.fetch_Store_Req();
        hmapPrdct_details= mDataSource. fetch_Store_Req_Prdct();
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
        btn_sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline())
                {
                    if(dataSaved())
                    {
                       // new GetRqstStockForDay(StockRequestActivity.this).execute();
                        GetRqstStockForDayRetrofit();
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
                Intent intent=new Intent(StockRequestActivity.this,AllButtonActivity.class);
                startActivity(intent);
                finish();

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
                        }
                        else
                        {
                            strReqStockToSend.append("|").append(prdctId+"^"+valueInBaseUnit+"$"+hmapBaseUOMID.get(prdctId)+"^"+uomIDSlctd);
                        }
                    }
                }//end  if(TextUtils.isEmpty(edRqrdStk.getText().toString()))

            }//end if(edRqrdStk!=null)


           // hmapBaseUOMCalcValue
            index++;
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

                viewProduct=inflater.inflate(R.layout.list_stock_request,null);

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

                final TextView tvUOM=(TextView) viewProduct.findViewById(R.id.tvUOM);
                tvUOM.setTag(prdctId+"_tvUOM");
                final TextView tvFnlStock=(TextView) viewProduct.findViewById(R.id.tvFnlStock);
                tvFnlStock.setTag(prdctId+"_tvFnlStock");

                if(hmapStore_details.containsKey(prdctId))
                {
                    tvFnlStock.setText(hmapStore_details.get(prdctId));
                }
                else
                {
                    tvFnlStock.setText("0");
                }

                if(hmapDfltUOMMstrPrdtWise.containsKey(prdctId))
                {
                    tvUOM.setText(hmapUOMMstrIdName.get(hmapDfltUOMMstrPrdtWise.get(prdctId)));
                    hmapprdctUOMSlctd.put(prdctId,hmapDfltUOMMstrPrdtWise.get(prdctId));
                    if(hmapStore_details.containsKey(prdctId))
                    {
                       String uomIdSlctdDft= hmapDfltUOMMstrPrdtWise.get(prdctId);
                        Double conversionUnitSlctdUOM=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIdSlctdDft));
                        Double valueOfStock=Double.parseDouble(hmapStore_details.get(prdctId));
                       Double crntStockVal=valueOfStock/conversionUnitSlctdUOM;
                        crntStockVal= Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(crntStockVal)));
                        tvOpnStk.setText(""+crntStockVal);



                        tvFnlStock.setText(""+crntStockVal);
                    }


                }
                else
                {
                    tvUOM.setText(hmapUOMMstrIdName.get(hmapBaseUOMID.get(prdctId)));
                    hmapprdctUOMSlctd.put(prdctId,hmapBaseUOMID.get(prdctId));
                }




                tvReqStk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(hmapUOMPrdctWise.containsKey(prdctId))
                        {
                            customReqStock(prdctId );
                        }

                    }
                });

                tvUOM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog listDialog = new Dialog(StockRequestActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                       View convertView = (View) inflater.inflate(R.layout.activity_list, null);
                        EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                        inputSearch.setVisibility(View.GONE);
                        TextView txt_header= (TextView) convertView.findViewById(R.id.txt_header);
                        txt_header.setText("Unit Of Measurments");
                        final ListView   listUOM = (ListView)convertView. findViewById(R.id.list_view);

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
                                ArrayAdapter  adapterUOM = new ArrayAdapter<String>(StockRequestActivity.this, R.layout.list_item, R.id.product_name, UOMArray);
                                listUOM.setAdapter(adapterUOM);
                                listDialog.setContentView(convertView);
                                listDialog.setTitle(getResources().getString(R.string.txtQualification));



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
                                            TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                                            TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                                            if((txtRqstVw!=null) && (txtFinalStock!=null) && (txtOpnStck!=null))
                                            {
                                              Double valueToPut=  Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapTotalCalcOnUOMSlctd.get(prdctId))));
                                                txtRqstVw.setText(""+valueToPut);
                                                Double realStock=Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                                                Double finalStock=realStock+Double.parseDouble(txtOpnStck.getText().toString());

                                                finalStock=Double.parseDouble(new DecimalFormat("##.##").format(finalStock));


                                                txtFinalStock.setText(""+finalStock);
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

            }



        }



    }


    private class GetRqstStockForDay extends AsyncTask<Void, Void, Void>
    {

        int flgStockOut=0;
        public GetRqstStockForDay(StockRequestActivity activity)
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

                String RouteType="0";

                for(int mm = 1; mm < 2  ; mm++)
                {



                    // System.out.println("Excecuted function : "+newservice.flagExecutedServiceSuccesfully);
                    if (mm == 1) {
                       String prsnCvrgId_NdTyp= mDataSource.fngetSalesPersonCvrgIdCvrgNdTyp();
                        newservice = newservice.getConfirmtionRqstStock(getApplicationContext(), strReqStockToSend.toString(),imei,prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0],prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1],3);

                        if (!newservice.director.toString().trim().equals("1")) {

                            serviceException = true;
                            break;

                        }
                    }



                }
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



            //  flgStockOut=1;
            if(serviceException)
            {
                serviceException=false;
                //showAlertStockOut("Error","Error While Retrieving Data.");
                 //showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData));
                //Toast.makeText(StockRequestActivity.this,"Please fill Stock out first for starting your market visit.", Toast.LENGTH_SHORT).show();
                //  showSyncError();
                showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogRequstionStock));
            }

            else  {
               // new GetVanStockForDay(StockRequestActivity.this).execute();

                CommonFunction.getStockData(StockRequestActivity.this,imei, CommonInfo.RegistrationID,"",1);
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
                Intent intent=new Intent(StockRequestActivity.this,AllButtonActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Showing Alert Message
        alertDialogGps.create();
        alertDialogGps.show();
    }

    public void showAlertStockOut(String title,String msg)
    {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(StockRequestActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void customReqStock(final String prdctId)
    {

        final Dialog listDialog = new Dialog(StockRequestActivity.this);
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
                            addValue(hmapprdctQtyFilled.get(tagVal),uomIdSlctd,UomId,prdctId,tagVal);

                        }
                        else
                        {

                        }




                    }


                }

                if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                {
                    //prdctId+"_edRqstStk"
                    TextView txtRqstVw= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
                    //  tvFnlStock.setTag(prdctId+"_tvFnlStock");
                    TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                    TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                    if((txtRqstVw!=null) && (txtFinalStock!=null) && (txtOpnStck!=null) && (hmapTotalCalcOnUOMSlctd!=null))
                    {
                        Double realStock=Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                        Double finalStock=realStock+Double.parseDouble(txtOpnStck.getText().toString());
                     Double requestedStock=Double.parseDouble(new DecimalFormat("##.##").format(realStock));
                        finalStock=Double.parseDouble(new DecimalFormat("##.##").format(finalStock));

                        txtRqstVw.setText(""+requestedStock);
                        txtFinalStock.setText(""+finalStock);


                    }



                }
                else
                {
                    //prdctId+"_edRqstStk"
                    TextView txtRqstVw= (TextView) listView.findViewWithTag(prdctId+"_edRqstStk");
                    //  tvFnlStock.setTag(prdctId+"_tvFnlStock");
                    TextView txtFinalStock=(TextView) listView.findViewWithTag(prdctId+"_tvFnlStock");


                    TextView txtOpnStck=(TextView) listView.findViewWithTag(prdctId+"_openStk");
                    if((txtRqstVw!=null) && (txtFinalStock!=null) && (txtOpnStck!=null))
                    {


                        txtRqstVw.setText("0");
                        txtFinalStock.setText(txtOpnStck.getText().toString());


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


        TextView txtVw_ques=new TextView(StockRequestActivity.this);
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
        LinearLayout lay = new LinearLayout(StockRequestActivity.this);

        lay.setOrientation(LinearLayout.HORIZONTAL);
      //  lay.setBackgroundResource(R.drawable.card_background_white);

        lay.addView(tv);
        lay.setPadding(0,5,0,0);
       lay.addView(edText);

        return lay;

    }

        public void addValue(String editTextvalue,String uomIdSlctd,String UomId,String prdctId,String tagVal)
        {
            if(!TextUtils.isEmpty(editTextvalue) && (Integer.parseInt(editTextvalue)>0))
            {



                if(uomIdSlctd.equals(UomId))
                {
                    if(hmapTotalCalcOnUOMSlctd.containsKey(prdctId))
                    {
                        Double value= Double.parseDouble(hmapTotalCalcOnUOMSlctd.get(prdctId));
                        Double totalVaue= value+Double.parseDouble(editTextvalue);
                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }
                    else
                    {
                        hmapTotalCalcOnUOMSlctd.put(prdctId,editTextvalue);
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

                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }
                    else
                    {

                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
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

                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(totalVaue));
                    }
                    else
                    {

                        hmapTotalCalcOnUOMSlctd.put(prdctId,String.valueOf(valueInBaseUnit));
                    }

                }

            }
        }





    public void GetRqstStockForDayRetrofit(){
        if(apiService==null){
            apiService= ApiClient.getClient().create(ApiInterface.class);
        }
        Date currDate= new Date();
        SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String currSysDate;
        currSysDate = currDateFormat.format(currDate).toString();
        String crntDate = TimeUtils.getNetworkDateTime(StockRequestActivity.this,TimeUtils.DATE_FORMAT);
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
                       // new GetVanStockForDay(StockRequestActivity.this).execute();
                        CommonFunction.getStockData(StockRequestActivity.this,imei, CommonInfo.RegistrationID,"",1);
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
    public void showAlertForError(String msg)
    {
        // AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(SplashScreen.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(StockRequestActivity.this);

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
    @Override
    public void success(int flgCalledFrom) {
        showAlertForSubmission(getString(R.string.DataSucc));
    }

    @Override
    public void failure(int flgCalledFrom) {
        showAlertForSubmission(getString(R.string.DataSucc));
    }
}
