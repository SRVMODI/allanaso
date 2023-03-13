package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;
import com.astix.sancussosfa.database.AppDataSource;
import com.sancussosfa.truetime.TimeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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
import java.util.UUID;
import java.util.regex.Pattern;

public class ProductEntryForm extends BaseActivity implements View.OnClickListener,CategoryCommunicator,focusLostCalled {
    HashMap<String, String> hmapProductIdLastOrder=new HashMap<String, String>();
    public int isbtnExceptionVisible=0;
    String ProductIdOnClickedControl123=null;
    //Invoice TextView
    public int flgIsAnySchemeMappedAgainstStore=0;
    public TextView tv_NetInvValue,tvTAmt,tvDis,tv_GrossInvVal,tvFtotal,tvAddDisc,tv_NetInvAfterDiscount,tvAmtPrevDueVAL,etAmtCollVAL
            ,tvAfterTaxValue,tvPreAmtOutstandingVALNew,tvAmtOutstandingVAL,tvCredAmtVAL,tvINafterCredVAL,textView1_CredAmtVAL_new,tvNoOfCouponValue,txttvCouponAmountValue;
    TextView txt_Lststock;
    String lastStockDate="";
    RecyclerView rv_prdct_detal;
    public TableLayout tbl1_dyntable_For_ExecutionDetails;
    public TableLayout tbl1_dyntable_For_OrderDetails;
    String progressTitle;
    ProgressDialog mProgressDialog;
    //Database
    //Intent Data
    public String storeID,imei,date,pickerDate,SN;
    int flgOrderType=0;
    //Custom
    CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;

    //Initialize fields
    ImageView img_ctgry,btn_go,img_return,btn_bck,btn_erase;
    EditText ed_search;
    TextView txt_RefreshOdrTot;
    Button btn_InvoiceReview,btn_OrderReview;
    LinearLayout ll_scheme_detail;

    //hmap for Products Info and Saving
    LinkedHashMap<String, String> hmapFilterProductList ;
    LinkedHashMap<String, String> hmapctgry_details;
    HashMap<String, String> hmapProductIdStock;
    ArrayList<HashMap<String, String>> arrLstHmapPrdct;
    HashMap<String, String> hmapProductStandardRateBeforeTax;
    HashMap<String, String> hmapProductMRP;
    HashMap<String, Integer> hmapDistPrdctStockCount;
    HashMap<String, Integer> hmapVanPrdctStockCount;// = new HashMap<String, Integer>();
    HashMap<String, String> hmapProductVatTaxPerventage;
    HashMap<String, String> hmapCtgryPrdctDetail;
    HashMap<String, String> hmapPrdctIdPrdctName;
    HashMap<String, String> hmapProductStandardRate;// = new HashMap<String, String>();
    HashMap<String, String> hmapProductTaxValue;
    HashMap<String, String> hmapProductSelectedUOMId ;
    HashMap<String, String> hmapLineValBfrTxAftrDscnt;
    HashMap<String, String> hmapLineValAftrTxAftrDscnt;
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String,String> hampGetLastProductExecution;
    HashMap<String, String> hmapPrdctOdrQty;

    //ModelClass
    public int PriceApplyDiscountLevelType = 0, flgTransferStatus = 0;;

    public ProductFilledDataModel prdctModelArrayList=new ProductFilledDataModel();

    public int StoreCurrentStoreType = 0,chkflgInvoiceAlreadyGenerated=0;
    public String strFinalAllotedInvoiceIds = "NA",TmpInvoiceCodePDA = "NA",StoreVisitCode = "NA",defaultCatName_Id = "0",previousSlctdCtgry,distID = "",VisitTypeStatus = "1";
    List<String> categoryNames;


    //Scheme Variable Declarations Starts Here

    ArrayList<HashMap<String, String>> arrayListSchemeSlabDteail=new ArrayList<HashMap<String,String>>();
    // hmapSchemeIdStoreID= key=SchemeId value StoreId
    HashMap<String, String> hmapSchemeIdStoreID=new HashMap<String, String>();

    //hmapSchmeSlabIdSchemeId= key =SchemeSlabId         value=SchemeID
    HashMap<String, String> hmapSchmeSlabIdSchemeId=new HashMap<String, String>();
    //hmapSchmeSlabIdSlabDes= key =SchemeSlabId         value=SchemeSlabDes
    HashMap<String, String> hmapSchmeSlabIdSlabDes=new HashMap<String, String>();
    //hmapSchmeSlabIdBenifitDes= key = SchemeSlabId        value=BenifitDescription
    HashMap<String, String> hmapSchmeSlabIdBenifitDes=new HashMap<String, String>();

    //hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied Schemes,Slabs and other details
    HashMap<String, String> hmapProductRelatedSchemesList=new HashMap<String, String>();

    //hmapSlabIDArrRowID= key=SlabID,val=RowID Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, ArrayList<Integer>> hmapSlabIDArrRowID=new LinkedHashMap<Integer, ArrayList<Integer>>();

    //hmapRowIDProductIds= key=RowID,val=ProductIds Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, ArrayList<Integer>> hmapRowIDProductIds=new LinkedHashMap<Integer, ArrayList<Integer>>();

    //hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied ADD On Schemes,Slabs and other details
    HashMap<String, String> hmapProductAddOnSchemesList=new HashMap<String, String>();


    //hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3= key=SchemeSlabID,val=BucketType^BucketValue^BucketValueType Applied Schemes,Slabs and other details
    LinkedHashMap<Integer, String> hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3=new LinkedHashMap<Integer,String>();

    //hmapProductQtyVolumeValue= key=ProductID,val=Qty^Volume^Value Applied Schemes,Slabs and other details
    HashMap<Integer, String> hmapProductQtyVolumeValue=new HashMap<Integer,String>();


    //Scheme Variable Declarations Ends Here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        setContentView(R.layout.activity_product_entry_form);

        mProgressDialog = new ProgressDialog(ProductEntryForm.this);
        initializeFields();
        getDataFromatabase();
        setInvoiceTableView();
        /*
        orderBookingTotalCalc();*/

        if(flgIsAnySchemeMappedAgainstStore==1)
        {

            hmapProductRelatedSchemesList= mDataSource.fnProductRelatedSchemesList();
            hmapProductAddOnSchemesList= mDataSource.fnProductADDOnScheme();
            getSchemeSlabDetails();
        }
        if(flgIsAnySchemeMappedAgainstStore==1)
        {
            //First Calculate Scheme and the do Order Calculation
            isbtnExceptionVisible=0;
            if(ProductIdOnClickedControl123!=null) {
                getOrderData(ProductIdOnClickedControl123);
            }
            orderBookingTotalCalc();
        }
        else {
            orderBookingTotalCalc();
        }
    }

    private void getOrderData(String ProductIdOnClickedControl123)
    {

        isbtnExceptionVisible=0;

        if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl123) || hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl123))
        {

            String SchIdsCompleteSchemeIdListOnProductID="";
            if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl123))
            {
                SchIdsCompleteSchemeIdListOnProductID=hmapProductRelatedSchemesList.get(ProductIdOnClickedControl123);
            }

            if(hmapProductAddOnSchemesList.containsKey(ProductIdOnClickedControl123))
            {
                if(!TextUtils.isEmpty(SchIdsCompleteSchemeIdListOnProductID))
                {
                    SchIdsCompleteSchemeIdListOnProductID=SchIdsCompleteSchemeIdListOnProductID+"#"+hmapProductAddOnSchemesList.get(ProductIdOnClickedControl123);
                }
                else
                {
                    SchIdsCompleteSchemeIdListOnProductID =  hmapProductAddOnSchemesList.get(ProductIdOnClickedControl123);
                }

            }
            // fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID,ProductIdOnClickedControl123);

        }
        else if(mDataSource.isFreeProductIdExist(Integer.parseInt(ProductIdOnClickedControl123)))
        {
            String productIdAgaingtFreeProductId= mDataSource.getFreeProductIdAgainstFreeProductId(Integer.parseInt(ProductIdOnClickedControl123));
            String SchIdsCompleteSchemeIdListOnProductID=hmapProductRelatedSchemesList.get(productIdAgaingtFreeProductId);
            if(hmapProductAddOnSchemesList.containsKey(productIdAgaingtFreeProductId))
            {
                if((!SchIdsCompleteSchemeIdListOnProductID.equals("null")) && (!SchIdsCompleteSchemeIdListOnProductID.equals("")) && (SchIdsCompleteSchemeIdListOnProductID!=null)) {
                    SchIdsCompleteSchemeIdListOnProductID = SchIdsCompleteSchemeIdListOnProductID + "#" + hmapProductAddOnSchemesList.get(productIdAgaingtFreeProductId);
                }
                else
                {
                    SchIdsCompleteSchemeIdListOnProductID =  hmapProductAddOnSchemesList.get(productIdAgaingtFreeProductId);
                }
            }
            //  fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID,productIdAgaingtFreeProductId);
        }
    }

    public void fnCheckNewSchemeIDsAppliedAfterValueChange(String SchIdsCompleteListOnProductID,String ProductIdOnClicked)
    {
        //Example :-1075_1_0_1!1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
        if(SchIdsCompleteListOnProductID!=null)
        {
            int pSchIdsAppliCount=0;
            String[] arrSchIdsListOnProductID=SchIdsCompleteListOnProductID.split("#");
            for(pSchIdsAppliCount=0;pSchIdsAppliCount<arrSchIdsListOnProductID.length;pSchIdsAppliCount++)
            {
                //35_1_0_2 where 35=shcemId, 1= SchAppRule, 2= schemeTypeId
                String schOverviewDetails=arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[0];   //Example :-1075_1_0_1
                String schOverviewOtherDetails=arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[1]; //Example :-1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
                int schId=Integer.parseInt(schOverviewDetails.split("_")[0]);                           //Example :-1075
                int schAppRule=Integer.parseInt(schOverviewDetails.split("_")[1]);                     //Example :-1
                int schApplicationId=Integer.parseInt(schOverviewDetails.split("_")[2]);               //Example :-0
                int SchTypeId=Integer.parseInt(schOverviewDetails.split("_")[3]);                      //Example :-1 // 1=Check Combined Skus, 2=Bundle,3=Simple with Check on Individual SKU
                String[] arrschSlbIDsOnSchIdBasis=schOverviewOtherDetails.split("@");   //Split for multiple slabs Example :-1026$1^1|1^23^1^10^0, 1025$1^1|1^22^1^20^0
                if(hmapSchemeIdStoreID.containsKey(""+schId))
                {
                    int pSchSlbCount=0;
                    for(pSchSlbCount=0;pSchSlbCount<arrschSlbIDsOnSchIdBasis.length;pSchSlbCount++)
                    {
                        //Exmaple Slab:- 1026$1^1|1^23^1^10^0
                        int schSlabId=Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
                        String schSlabOtherDetails=arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
                        String[] arrSchSlabBuckWiseDetails=schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets (OR Condition)
                        if(SchTypeId==1 || SchTypeId==3)//Loop Starts For SchTypeId==3 || SchTypeId==1
                        {
                            String BucketTypeBucketValue=hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3.get(schSlabId);
                            int SlabBucketType=Integer.parseInt(BucketTypeBucketValue.split(Pattern.quote("^"))[0]);
                            Double SlabBucketValue=Double.parseDouble(BucketTypeBucketValue.split(Pattern.quote("^"))[1]);
                            int SubBucketValType=Integer.parseInt(BucketTypeBucketValue.split(Pattern.quote("^"))[2]);

                            ArrayList<Integer> listPrdctMpdWithRowId=new ArrayList<Integer>();
                            if(SchTypeId==3)
                            {
                                listPrdctMpdWithRowId.add(Integer.parseInt(ProductIdOnClicked));
                            }
                            else
                            {
                                if(hmapSlabIDArrRowID!=null && hmapSlabIDArrRowID.containsKey(schSlabId))
                                {
                                    ArrayList<Integer> listRowId=hmapSlabIDArrRowID.get(schSlabId);
                                    for(Integer rowId:listRowId)
                                    {
                                        if(hmapRowIDProductIds!=null && hmapRowIDProductIds.containsKey(rowId))
                                        {
                                            ArrayList<Integer> listPrdId=new ArrayList<Integer>();
                                            listPrdId=hmapRowIDProductIds.get(rowId);
                                            for(Integer PrdctId:listPrdId)
                                            {
                                                listPrdctMpdWithRowId.add(PrdctId);
                                            }

                                        }

                                    }
                                }
                            }
                            int SlabOverAllQty=0;
                            Double SlabOverAllVolume=0.0;
                            Double SlabOverAllValue=0.0;
                            int SlabOverAllLineCount=0;
                            ArrayList<Integer> productFullFilledSlab=new ArrayList<Integer>();
                            if(listPrdctMpdWithRowId!=null && listPrdctMpdWithRowId.size()>0)
                            {
                                for(Integer prdID:listPrdctMpdWithRowId)
                                {
                                    if(!TextUtils.isEmpty(prdctModelArrayList.getPrdctOrderQty(String.valueOf(prdID))))
                                    {
                                        int curntProdQty = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(String.valueOf(prdID)));
                                        if(curntProdQty>(0))
                                        {
                                            SlabOverAllLineCount=SlabOverAllLineCount+1;
                                            SlabOverAllQty=SlabOverAllQty+curntProdQty;
                                            Double curntProdRate=Double.parseDouble(hmapProductStandardRate.get(""+prdID));
                                            Double currentProductValue=curntProdRate * curntProdQty;
                                            SlabOverAllValue=SlabOverAllValue+currentProductValue;
                                            Double currentProductVolumePurchased=Double.parseDouble(hmapProductQtyVolumeValue.get(""+prdID))*curntProdQty;
                                            SlabOverAllVolume=SlabOverAllVolume+currentProductVolumePurchased;
                                            hmapProductQtyVolumeValue.put(prdID,curntProdQty+"^"+currentProductVolumePurchased+"^"+currentProductValue);
                                            productFullFilledSlab.add(prdID);
                                        }
                                    }
                                }

                                //Now Check if the Slab is fullfilled or Not
                                if(SlabBucketType==1)
                                {
                                    if(Double.parseDouble(""+SlabOverAllQty)>=SlabBucketValue)
                                    {

                                        break;
                                    }
                                }
                                if(SlabBucketType==5)
                                {
                                    if(Double.parseDouble(""+SlabOverAllVolume)>=SlabBucketValue)
                                    {
                                        break;
                                    }
                                }//
                                if(SlabBucketType==4)
                                {
                                    if(Double.parseDouble(""+SlabOverAllValue)>=SlabBucketValue)
                                    {
                                        break;
                                    }
                                }
                                if(SlabBucketType==3)
                                {
                                    if(Double.parseDouble(""+SlabOverAllLineCount)>=SlabBucketValue)
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                        else if(SchTypeId==2)//Loop Ends For SchTypeId==3 || SchTypeId==1 AND //Loop Starts For SchTypeId==2
                        {
                            //Example :-1075_1_0_1!1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
                            for(int pSchSlbBuckCnt=0;pSchSlbBuckCnt<arrSchSlabBuckWiseDetails.length;pSchSlbBuckCnt++)
                            {
                                String schSlbBuckDetails=arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
                                String schSlbBuckOtherDetails=arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
                                int schSlbBuckId=Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
                                int schSlbBuckCnt=Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);  //Example Number of Buckets under this Slab, Count:-1
                                int schSlbBuckCntInLoop=0;
                                String[] arrSubBucketDetails=schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets(AND Condition)
                                String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId=new String[schSlbBuckCnt];  //Example Length of Buckets in Slab and which condition is true in case of OR

                                ArrayList<Integer> productFullFilledSlab=new ArrayList<Integer>();
                                ArrayList<String> schSlabRowIdFullFilledSlab=new ArrayList<String>();
                                ArrayList<String> productFullFilledSlabForInvoice=new ArrayList<String>();


                                LinkedHashMap<String, String> hmapSubBucketDetailsData=new LinkedHashMap<String, String>();
                                LinkedHashMap<String, String> hmapSubBucketTotalQntty=new LinkedHashMap<String, String>();
                                LinkedHashMap<String, String> hmapSubBucketTotalValue=new LinkedHashMap<String, String>();
                                LinkedHashMap<String, String> hmapSubBucketTotalVolume=new LinkedHashMap<String, String>();
                                for(int cntSubBucket=0;cntSubBucket<arrSubBucketDetails.length;cntSubBucket++) {
                                    // Eaxmple:-1^23^1^10^0
                                    int schSlbSubBuckID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[0]); //Slab Sub BucketID Eaxmple:-1  subBucketId
                                    int schSlbSubRowID = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[1]);  //Slab Sub Bucket RowID Eaxmple:-23  rowid
                                    int schSlabSubBucketType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[2]);  ///Slab Sub Bucket Type Eaxmple:-1

                                    Double schSlabSubBucketValue = Double.parseDouble(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[3]);  ///Slab Sub Bucket Value Eaxmple:-10
                                    int schSubBucketValType = Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[4]); ///Slab Sub Bucket Value Type Eaxmple:-0
                                    ArrayList<String> arrProductIDMappedInSchSlbSubBukRowId=new ArrayList<String>();
                                    arrProductIDMappedInSchSlbSubBukRowId= mDataSource.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);
                                    if(arrProductIDMappedInSchSlbSubBukRowId.size()>0) {

                                        int SlabOverAllQty=0;
                                        Double SlabOverAllVolume=0.0;
                                        Double SlabOverAllValue=0.0;
                                        int SlabOverAllLineCount=0;

                                        for (String productMappedWithScheme : arrProductIDMappedInSchSlbSubBukRowId) {
                                            int prdID=Integer.parseInt(productMappedWithScheme);
                                            schSlabRowIdFullFilledSlab.add(productMappedWithScheme+"^"+schSlbSubRowID);
                                            // productFullFilledSlab.add(productMappedWithScheme+"^"+schId);// productLine
                                            //String hmapSubBucketDetailsData_Value=	schId+"^"+schSlabId+"^"+schSlbBuckId+"^"+schSlabSubBucketValue+"^"+schSubBucketValType+"^"+schSlabSubBucketType+"^"+ProductIdOnClicked +"^"+valForVolumetQTYToMultiply+"^"+schSlbSubRowID+"^"+SchTypeId;
                                            //hmapSubBucketDetailsData.put(productMappedWithScheme+"^"+schSlbSubRowID,hmapSubBucketDetailsData_Value );
                                            //New Code Style For Products Against Sceheme  Type ID 2 of indivual Row IDs  Starts  Here
                                            productFullFilledSlab.add(prdID);


                                            if(!TextUtils.isEmpty(prdctModelArrayList.getPrdctOrderQty(String.valueOf(prdID))))
                                            {
                                                int curntProdQty = Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(String.valueOf(prdID)));
                                                if(curntProdQty>(0))
                                                {
                                                    SlabOverAllLineCount=SlabOverAllLineCount+1;
                                                    SlabOverAllQty=SlabOverAllQty+curntProdQty;
                                                    Double curntProdRate=Double.parseDouble(hmapProductStandardRate.get(""+prdID));
                                                    Double currentProductValue=curntProdRate * curntProdQty;
                                                    SlabOverAllValue=SlabOverAllValue+currentProductValue;
                                                    Double currentProductVolumePurchased=Double.parseDouble(hmapProductQtyVolumeValue.get(""+prdID))*curntProdQty;
                                                    SlabOverAllVolume=SlabOverAllVolume+currentProductVolumePurchased;
                                                    hmapProductQtyVolumeValue.put(prdID,curntProdQty+"^"+currentProductVolumePurchased+"^"+currentProductValue);
                                                    productFullFilledSlab.add(prdID);
                                                }
                                            }









                                            //New Code Style For Products Against Sceheme  Type ID 2 of indivual Row IDs  Ends  Here

                                        }

                                        //Now Check if the Slab is fullfilled or Not
                                        if(schSubBucketValType==1)
                                        {
                                            if(Double.parseDouble(""+SlabOverAllQty)>=schSlabSubBucketValue)
                                            {

                                                break;
                                            }
                                        }
                                        if(schSubBucketValType==5)
                                        {
                                            if(Double.parseDouble(""+SlabOverAllVolume)>=schSlabSubBucketValue)
                                            {
                                                break;
                                            }
                                        }//
                                        if(schSubBucketValType==4)
                                        {
                                            if(Double.parseDouble(""+SlabOverAllValue)>=schSlabSubBucketValue)
                                            {
                                                break;
                                            }
                                        }
                                        if(schSubBucketValType==3)
                                        {
                                            if(Double.parseDouble(""+SlabOverAllLineCount)>=schSlabSubBucketValue)
                                            {
                                                break;
                                            }
                                        }
                                    }

                                }
                            }

                        }//Loop Ends For SchTypeId==2

                    }
                }
            }
        }
    }

    private void initializeFields() {

        getDataFromIntent();
        mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        mCustomKeyboardNumWithoutDecimal = new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal);


        txt_Lststock= (TextView) findViewById(R.id.txt_Lststock);
        if(!TextUtils.isEmpty(lastStockDate))
        {
            txt_Lststock.setText("Stk On "+lastStockDate);
        }
        else
        {
            txt_Lststock.setText("Last Stk NA");
        }

        TextView order_detailHeading=(TextView)findViewById(R.id.order_detail);

        TextView txt_detalis=(TextView)findViewById(R.id.txt_detalis);

        //  TextView lbl_InvOrderHeader=(TextView)findViewById(R.id.lbl_InvOrderHeader);
        TextView tv_EntryInvValHeader=(TextView)findViewById(R.id.tv_EntryInvValHeader);

        if(CommonInfo.hmapAppMasterFlags.containsKey("flgSchemeAvailable") && CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartSchemeDetails"))
        {
            if(CommonInfo.hmapAppMasterFlags.get("flgSchemeAvailable")==1 && CommonInfo.hmapAppMasterFlags.get("flgVisitStartSchemeDetails")==1)
            {
                RelativeLayout ll_schm_applcbl = (RelativeLayout)findViewById(R.id.ll_schm_applcbl);
                ll_schm_applcbl.setVisibility(View.VISIBLE);
            }
        }
        if(CommonInfo.flgDrctslsIndrctSls==2)
        {
            order_detailHeading.setText("Order Details");
            txt_detalis.setText("Order Total");
            //   lbl_InvOrderHeader.setText("O.Qty.");
            tv_EntryInvValHeader.setText("O.Val");
        }


        img_ctgry = (ImageView) findViewById(R.id.img_ctgry);
        ed_search = (EditText) findViewById(R.id.ed_search);
        btn_erase= (ImageView) findViewById(R.id.btn_erase);
        btn_go = (ImageView) findViewById(R.id.btn_go);
        txt_RefreshOdrTot = (TextView) findViewById(R.id.txt_RefreshOdrTot);
        btn_InvoiceReview = (Button) findViewById(R.id.btn_InvoiceReview);
        btn_OrderReview = (Button) findViewById(R.id.btn_OrderReview);
        img_return = (ImageView) findViewById(R.id.img_return);
        btn_bck = (ImageView) findViewById(R.id.btn_bck);
        rv_prdct_detal=(RecyclerView)findViewById(R.id.rv_prdct_detal);
        ll_scheme_detail = (LinearLayout) findViewById(R.id.ll_scheme_detail);

        if(CommonInfo.flgDrctslsIndrctSls==2)
        {
            btn_InvoiceReview.setText(getResources().getString(R.string.OrderReview));
        }

        img_ctgry.setOnClickListener(this);
        btn_bck.setOnClickListener(this);
        btn_InvoiceReview.setOnClickListener(this);
        btn_OrderReview.setOnClickListener(this);
        ed_search.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        btn_erase.setOnClickListener(this);



        ImageView executionDetails_butn = (ImageView) findViewById(R.id.txt_execution_Details);


        if(CommonInfo.flgDrctslsIndrctSls==2)
        {

            executionDetails_butn.setVisibility(View.VISIBLE);
            if(CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
                if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                    img_return.setVisibility(View.VISIBLE);
                } else {
                    img_return.setVisibility(View.GONE);
                }
            }
            else {
                img_return.setVisibility(View.VISIBLE);
            }

        }
        else  if(CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable") == 1) {
                img_return.setVisibility(View.VISIBLE);
            }
            else
            {
                img_return.setVisibility(View.GONE);
            }
        }
        else {
            img_return.setVisibility(View.VISIBLE);
        }
        img_return.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ed_LastEditextFocusd=prdctModelArrayList.getLastEditText();

                fnCreditAndStockCal(4,ed_LastEditextFocusd,hmapDistPrdctStockCount);


            }
        });
        //	tvRateHeading = (TextView) findViewById(R.id.tvRateHeading);
        executionDetails_butn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub

                LayoutInflater layoutInflater = LayoutInflater.from(ProductEntryForm.this);
                View promptView = layoutInflater.inflate(R.layout.lastsummary_execution, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProductEntryForm.this);


                alertDialogBuilder.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));


                //mDataSource.open();

                String DateResult[] = mDataSource.fetchOrderDateFromtblForPDAGetExecutionSummary(storeID);
                String LastexecutionDetail[] = mDataSource.fetchAllDataFromtbltblForPDAGetExecutionSummary(storeID);

                String PrdNameDetail[] = mDataSource.fetchPrdNameFromtblForPDAGetExecutionSummary(storeID);

                String ProductIDDetail[] = mDataSource.fetchProductIDFromtblForPDAGetExecutionSummary(storeID);


                //System.out.println("Ashish and Anuj LastexecutionDetail : "+LastexecutionDetail.length);
                //mDataSource.close();

                if (DateResult.length > 0) {
                    TextView FirstDate = (TextView) promptView.findViewById(R.id.FirstDate);
                    TextView SecondDate = (TextView) promptView.findViewById(R.id.SecondDate);
                    TextView ThirdDate = (TextView) promptView.findViewById(R.id.ThirdDate);

                    TextView lastExecution = (TextView) promptView.findViewById(R.id.lastExecution);
                    lastExecution.setText(ProductEntryForm.this.getResources().getString(R.string.lastvisitdetails_last)
                            + DateResult.length + ProductEntryForm.this.getResources().getString(R.string.ExecSummary));


                    if (DateResult.length == 1) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setVisibility(View.GONE);
                        ThirdDate.setVisibility(View.GONE);
                    } else if (DateResult.length == 2) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setText("" + DateResult[1]);
                        ThirdDate.setVisibility(View.GONE);
                    } else if (DateResult.length == 3) {
                        FirstDate.setText("" + DateResult[0]);
                        SecondDate.setText("" + DateResult[1]);
                        ThirdDate.setText("" + DateResult[2]);
                    }
                }

                LayoutInflater inflater = getLayoutInflater();

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
                double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
                double screenInches = Math.sqrt(x + y);
                if (LastexecutionDetail.length > 0) {
                    alertDialogBuilder.setView(promptView);


                    tbl1_dyntable_For_ExecutionDetails = (TableLayout) promptView.findViewById(R.id.dyntable_For_ExecutionDetails);
                    TableRow row1 = (TableRow) inflater.inflate(R.layout.table_execution_head, tbl1_dyntable_For_OrderDetails, false);

                    TextView firstDateOrder = (TextView) row1.findViewById(R.id.firstDateOrder);
                    TextView firstDateInvoice = (TextView) row1.findViewById(R.id.firstDateInvoice);
                    TextView secondDateOrder = (TextView) row1.findViewById(R.id.secondDateOrder);
                    TextView secondDateInvoice = (TextView) row1.findViewById(R.id.secondDateInvoice);
                    TextView thirdDateOrder = (TextView) row1.findViewById(R.id.thirdDateOrder);
                    TextView thirdDateInvoice = (TextView) row1.findViewById(R.id.thirdDateInvoice);
                    if (DateResult.length > 0) {
                        if (DateResult.length == 1) {

                            secondDateOrder.setVisibility(View.GONE);
                            secondDateInvoice.setVisibility(View.GONE);
                            thirdDateOrder.setVisibility(View.GONE);
                            thirdDateInvoice.setVisibility(View.GONE);
                        } else if (DateResult.length == 2) {
                            thirdDateOrder.setVisibility(View.GONE);
                            thirdDateInvoice.setVisibility(View.GONE);
                        }
                    }

                    tbl1_dyntable_For_ExecutionDetails.addView(row1);


                    for (int current = 0; current <= (PrdNameDetail.length - 1); current++) {


                        final TableRow row = (TableRow) inflater.inflate(R.layout.table_execution_row, tbl1_dyntable_For_OrderDetails, false);

                        TextView tv1 = (TextView) row.findViewById(R.id.skuName);
                        TextView tv2 = (TextView) row.findViewById(R.id.firstDateOrder);
                        TextView tv3 = (TextView) row.findViewById(R.id.firstDateInvoice);
                        TextView tv4 = (TextView) row.findViewById(R.id.secondDateOrder);
                        TextView tv5 = (TextView) row.findViewById(R.id.secondDateInvoice);
                        TextView tv6 = (TextView) row.findViewById(R.id.thirdDateOrder);
                        TextView tv7 = (TextView) row.findViewById(R.id.thirdDateInvoice);

                        tv1.setText(PrdNameDetail[current]);

                        if (DateResult.length > 0) {
                            if (DateResult.length == 1) {
                                tv4.setVisibility(View.GONE);
                                tv5.setVisibility(View.GONE);
                                tv6.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);
                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();

                                //System.out.println("Check Value Number "+abc.length);
                                //System.out.println("Check Value Number12 "+DateResult[0]);
                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }
                            } else if (DateResult.length == 2) {
                                tv6.setVisibility(View.GONE);
                                tv7.setVisibility(View.GONE);

                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();

                                //System.out.println("Check Value Number "+abc.length);
                                //System.out.println("Check Value Number12 "+DateResult[0]);
                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }

                                //mDataSource.open();
                                String abc1[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[1], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc1.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
                                    tv4.setText(tokens.nextToken().trim());
                                    tv5.setText(tokens.nextToken().trim());
                                } else {
                                    tv4.setText("0");
                                    tv5.setText("0");
                                }


                            } else if (DateResult.length == 3) {
                                //mDataSource.open();
                                String abc[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[0], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
                                    tv2.setText(tokens.nextToken().trim());
                                    tv3.setText(tokens.nextToken().trim());
                                } else {
                                    tv2.setText("0");
                                    tv3.setText("0");
                                }

                                //mDataSource.open();
                                String abc1[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[1], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc1.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
                                    tv4.setText(tokens.nextToken().trim());
                                    tv5.setText(tokens.nextToken().trim());
                                } else {
                                    tv4.setText("0");
                                    tv5.setText("0");
                                }

                                //mDataSource.open();
                                String abc2[] = mDataSource.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID, DateResult[2], ProductIDDetail[current]);
                                //mDataSource.close();


                                if (abc2.length > 0) {
                                    StringTokenizer tokens = new StringTokenizer(String.valueOf(abc2[0]), "_");
                                    tv6.setText(tokens.nextToken().trim());
                                    tv7.setText(tokens.nextToken().trim());
                                } else {
                                    tv6.setText("0");
                                    tv7.setText("0");
                                }


                            } else {

                            }
                        }

					/*if(screenInches>6.5)
					{
						tv1.setTextSize(14);
						tv2.setTextSize(14);
						tv3.setTextSize(14);
						tv4.setTextSize(14);
						tv5.setTextSize(14);
						tv6.setTextSize(14);
						tv7.setTextSize(14);
					}
					else
					{

					}*/


					/*StringTokenizer tokens = new StringTokenizer(String.valueOf(LastexecutionDetail[current]), "_");

					tv1.setText(tokens.nextToken().trim());
					tv2.setText(tokens.nextToken().trim());
					tokens.nextToken().trim();
					tv3.setText(tokens.nextToken().trim());*/
					/*tv4.setText(tokens.nextToken().trim());
					tv5.setText(tokens.nextToken().trim());*/
                        tbl1_dyntable_For_ExecutionDetails.addView(row);

                    }

                } else {
                    alertDialogBuilder.setMessage(ProductEntryForm.this.getResources().getString(R.string.AlertExecNoSum));
                }
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });


                alertDialogBuilder.setIcon(R.drawable.info_ico);
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();


            }
        });



    }
    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");
        flgOrderType= passedvals.getIntExtra("flgOrderType",0);


    }

    public void searchProduct(String filterSearchText,String ctgryId)
    {

        if(hmapFilterProductList!=null)
        {
            hmapFilterProductList.clear();
        }

        hmapFilterProductList= mDataSource.getFileredProductListMap(filterSearchText.trim(),StoreCurrentStoreType,ctgryId);

        if(hmapFilterProductList.size()>0)
        {
            String[] listProduct=new String[hmapFilterProductList.size()];
            int index=0;
            for(Map.Entry<String,String> entry:hmapFilterProductList.entrySet())
            {
                listProduct[index]=entry.getKey()+"^"+entry.getValue();
                index++;
            }

            OrderAdapter orderAdapter=new OrderAdapter(ProductEntryForm.this,listProduct,hmapFilterProductList,hmapProductStandardRate,hmapProductMRP,hmapProductIdStock,hmapProductIdLastStock,hampGetLastProductExecution,hmapDistPrdctStockCount,hmapVanPrdctStockCount,prdctModelArrayList,0,hmapProductIdLastOrder);
            rv_prdct_detal.setAdapter(orderAdapter);
            rv_prdct_detal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
        else
        {
            allMessageAlert(ProductEntryForm.this.getResources().getString(R.string.AlertFilter));
        }


    }

    private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductEntryForm.this);
        alertDialogNoConn.setTitle(ProductEntryForm.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(ProductEntryForm.this.getResources().getString(R.string.AlertDialogOkButton),
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

    public void getDataFromatabase()
    {

        //StoreVisitCode = mDataSource.fnGetStoreVisitCode(storeID);


        flgIsAnySchemeMappedAgainstStore= mDataSource.fnflgCheckAnySchemeStoreID(storeID);

        if(CommonInfo.flgDrctslsIndrctSls==2)
        {

            StoreVisitCode= mDataSource.fnGetStoreVisitCodeInCaseOfIndrectSales(storeID);
        }
        else
        {

            StoreVisitCode= mDataSource.fnGetStoreVisitCode(storeID);
        }
        StoreCurrentStoreType=Integer.parseInt(mDataSource.fnGetStoreTypeOnStoreIdBasis(storeID));
        // chkflgInvoiceAlreadyGenerated=mDataSource.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,CommonInfo.flgDrctslsIndrctSls);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number


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
        distID= mDataSource.getDisId(storeID);
        hmapProductIdStock= mDataSource.fetchActualVisitData(storeID);

        hmapVanPrdctStockCount= mDataSource.fnGetFinalInvoiceQtyProductWise(CommonInfo.flgDrctslsIndrctSls);


        hmapDistPrdctStockCount= mDataSource.getStockAsPerFlg(distID);


        //distID=mDataSource.getDisId(storeID);
        getCategoryDetail();
        getPrductInfoDetail();


        if(defaultCatName_Id.contains("^"))
        {
            ed_search.setText(defaultCatName_Id.split(Pattern.quote("^"))[0]);

            searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0],defaultCatName_Id.split(Pattern.quote("^"))[1]);
        }

    }

    public void getPrductInfoDetail()
    {
        hmapProductIdLastStock= mDataSource.fnGetLastStockByDMS_Or_SFA(storeID);
        arrLstHmapPrdct = mDataSource.fetch_catgry_prdctsData(storeID, StoreCurrentStoreType);
        hampGetLastProductExecution= mDataSource.fnGetHampGetLastProductExecution(storeID);
        hmapProductIdLastOrder=mDataSource.fnGetLastOrderByDMS_Or_SFA(storeID);
        lastStockDate= mDataSource.fnGetLastStockDate(storeID);
        if(arrLstHmapPrdct!=null && arrLstHmapPrdct.size()>0)
        {
            hmapCtgryPrdctDetail = arrLstHmapPrdct.get(0);

            hmapPrdctIdPrdctName = arrLstHmapPrdct.get(5);
            hmapProductVatTaxPerventage = arrLstHmapPrdct.get(8);
            hmapProductMRP = arrLstHmapPrdct.get(9);
            hmapProductTaxValue = arrLstHmapPrdct.get(12);
            hmapProductStandardRate = arrLstHmapPrdct.get(15);
            hmapProductStandardRateBeforeTax = arrLstHmapPrdct.get(16);
            hmapProductSelectedUOMId = arrLstHmapPrdct.get(25);
            hmapLineValBfrTxAftrDscnt = arrLstHmapPrdct.get(26);
            hmapLineValAftrTxAftrDscnt = arrLstHmapPrdct.get(27);

        }
        hmapPrdctOdrQty= mDataSource.fnGetProductPurchaseList(storeID,TmpInvoiceCodePDA,CommonInfo.flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
        if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.size()>0)
        {
            for(Map.Entry<String,String> entry:hmapPrdctOdrQty.entrySet())
            {
                if(Integer.parseInt(entry.getValue())>0)
                {
                    prdctModelArrayList.setPrdctQty(entry.getKey(),entry.getValue());
                }

            }

        }

    }

    private void getCategoryDetail() {

        hmapctgry_details = mDataSource.fetch_Category_List();

        int index = 0;
        if (hmapctgry_details != null) {
            categoryNames = new ArrayList<String>();
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while (iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry) iterator.next();
                categoryNames.add(me2.getKey().toString());
                if (index == 0) {
                    defaultCatName_Id = me2.getKey().toString() + "^" + me2.getValue().toString();
                }
                index = index + 1;
            }
        }


    }

    public String genTempInvoiceCodePDA()
    {
        //store ID generation <x>
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String VisitStartTS = TimeUtils.getNetworkDateTime(ProductEntryForm.this,TimeUtils.DATE_TIME_FORMAT);
        String cxz;
        cxz = UUID.randomUUID().toString();
        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();
        String IMEIid =  CommonInfo.imei.substring(9);
        //cxz = IMEIid +"-"+cxz;
        cxz = "TmpInvoiceCodePDA" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;
        //-_
    }

    @Override
    public void onClick(View v) {
        EditText ed_LastEditextFocusd=prdctModelArrayList.getLastEditText();
        switch (v.getId())
        {

            case R.id.img_ctgry:
                img_ctgry.setEnabled(false);
                customAlertStoreList(categoryNames, "Select Brands");
                break;
            case R.id.btn_bck:
                if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(5,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }

                else
                {
                    nextStepAfterRetailerCreditBal(5);
                }

                break;
            case R.id.btn_InvoiceReview:
                if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(0,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }

                else
                {
                    nextStepAfterRetailerCreditBal(0);
                }

                break;
            case R.id.btn_OrderReview:
                if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapDistPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
                {
                    fnCreditAndStockCal(1,ed_LastEditextFocusd,hmapVanPrdctStockCount);
                }
                else
                {
                    nextStepAfterRetailerCreditBal(1);
                }

                break;
            case R.id.ed_search:

                mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
                // mCustomKeyboardNum.hideCustomKeyboard();
                break;
            case R.id.btn_erase:
                ed_search.setText("");
                break;
            case R.id.btn_go:
                if (!TextUtils.isEmpty(ed_search.getText().toString().trim())) {
                    if (!ed_search.getText().toString().trim().equals("")) {
                        searchProduct(ed_search.getText().toString().trim(), "");

                    }


                }
        }


    }

    public void customAlertStoreList(final List<String> listOption, String sectionHeader)
    {

        final Dialog listDialog = new Dialog(ProductEntryForm.this);
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
        final CardArrayAdapterCategory cardArrayAdapter = new CardArrayAdapterCategory(ProductEntryForm.this,listOption,listDialog,previousSlctdCtgry);

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

    public void fnCreditAndStockCal(int butnClkd, EditText ed_LastEditextFocusd,HashMap<String, Integer> hmapPrdctStockCount)
    {

        if(ed_LastEditextFocusd!=null)
        {

            String ProductIdOnClickedEdit=ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[0];
            String tag=ed_LastEditextFocusd.getTag().toString();
            if(tag.contains("etOrderQty"))
            {
                String prdctQty=   prdctModelArrayList.getPrdctOrderQty(ProductIdOnClickedEdit);

                if(!TextUtils.isEmpty(prdctQty) && (hmapPrdctStockCount!=null) && (hmapPrdctStockCount.size()>0))
                {


                    int originalNetQntty=Integer.parseInt(prdctQty);
                    int totalStockLeft=0;
                    if( hmapPrdctStockCount.containsKey(ProductIdOnClickedEdit))
                    {
                        totalStockLeft=hmapPrdctStockCount.get(ProductIdOnClickedEdit);
                    }


                    if (originalNetQntty>totalStockLeft)
                    {
                        EditText edOrderCurrent=prdctModelArrayList.getLastEditText();
                        if(edOrderCurrent!=null)
                        {
                            alertForOrderExceedStock(ProductIdOnClickedEdit,edOrderCurrent,ed_LastEditextFocusd,butnClkd,hmapPrdctStockCount);
                        }
                        else
                        {
                            alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,butnClkd,hmapPrdctStockCount);
                        }


                    }
                    else
                    {

                        nextStepAfterRetailerCreditBal(butnClkd);



                    }

                }
                else
                {


                    nextStepAfterRetailerCreditBal(butnClkd);


                }

            }


            else
            {

                nextStepAfterRetailerCreditBal(butnClkd);


            }
        }
        else
        {
            nextStepAfterRetailerCreditBal(butnClkd);

        }


    }

    public void nextStepAfterRetailerCreditBal(int btnClkd)
    {
        if(btnClkd!=-1)
        {
            if(btnClkd==0 || btnClkd==1|| btnClkd==4) // Invoice Review
            {
                orderInvoiceReviewClkd(btnClkd);
            }

            if(btnClkd==5)// btn back pressed
            {
                orderInvoiceReviewClkd(btnClkd);

            }
        }

    }

    public void orderInvoiceReviewClkd(int btnClkd)
    {
        long StartClickTime = System.currentTimeMillis();
        Date dateobj1 = new Date(StartClickTime);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String StartClickTimeFinal = TimeUtils.getNetworkDateTime(ProductEntryForm.this,TimeUtils.DATE_TIME_FORMAT);

        String fileName=imei+"_"+storeID;
        //StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
        //File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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




        progressTitle=ProductEntryForm.this.getResources().getString(R.string.WhileWeSaveExit);
        //  progressTitle="While we save your data then review Order";
        new SaveData().execute(String.valueOf(btnClkd));
    }

    public void alertForOrderExceedStock(final String productOIDClkd, final EditText edOrderCurrent, final EditText edOrderCurrentLast, final int flagClkdButton,HashMap<String, Integer> hmapPrdctStockCount)
    {

        final Dialog listDialog = new Dialog(ProductEntryForm.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.custom_stock_alert);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity =Gravity.CENTER;
        parms.width=WindowManager.LayoutParams.MATCH_PARENT;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;

        int avilabQty=0;
        if(hmapPrdctStockCount.containsKey(productOIDClkd))
        {
            avilabQty=hmapPrdctStockCount.get(productOIDClkd);
        }

        //final int avilabQty=hmapDistPrdctStockCount.get(productOIDClkd);

        TextView order_detail=(TextView) listDialog.findViewById(R.id.order_detail);



        Button btn_done= (Button) listDialog.findViewById(R.id.btn_done);
        Button btn_cancel= (Button) listDialog.findViewById(R.id.btn_cancel);

        final EditText editText_prdctQty= (EditText) listDialog.findViewById(R.id.editText_prdctQty);
        editText_prdctQty.setText(""+avilabQty);
        editText_prdctQty.setVisibility(View.GONE);
        EditText ed_extraQty= (EditText) listDialog.findViewById(R.id.ed_extraQty);
        ed_extraQty.setVisibility(View.GONE);

        if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) ||((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0)&& (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1)))
        {
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.alert_order_exceeds_stock));
            btn_cancel.setVisibility(View.VISIBLE);
            btn_done.setText("Change Qty");
        }
        else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0)&& (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1)))
        {
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.alert_order_exceeds_stock));
            btn_cancel.setVisibility(View.VISIBLE);
            btn_done.setText("Change Qty");
        }
        else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2)&& ((CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))|| (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0)))
        {
            int dbrStock=hmapDistPrdctStockCount.get(productOIDClkd);
            int orderQtyFilled=Integer.parseInt(prdctModelArrayList.getPrdctOrderQty(productOIDClkd));
            if(orderQtyFilled>dbrStock)
            {
                btn_cancel.setVisibility(View.GONE);
                order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));

            }
            else
            {
                order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.alert_order_exceeds_stock));
                btn_cancel.setVisibility(View.VISIBLE);
                btn_done.setText("Change Qty");
            }

        }
        else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0)&& (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2)))
        {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));
        }
        else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)&& (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2)))
        {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));
        }
        else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2)&& (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2)))
        {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));
        }
        else
        {
            btn_cancel.setVisibility(View.GONE);
            order_detail.setText(hmapFilterProductList.get(productOIDClkd)+"\n\n"+ProductEntryForm.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductEntryForm.this.getResources().getString(R.string.RqrdQty)+prdctModelArrayList.getPrdctOrderQty(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagClkdButton!=-1)
                {
                    listDialog.dismiss();
                    nextStepAfterRetailerCreditBal(flagClkdButton);
                }
                else
                {
                    listDialog.dismiss();
                }
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
                prdctModelArrayList.removePrdctQty(productOIDClkd);

                edOrderCurrentLast.setText("");

                edOrderCurrent.clearFocus();
                edOrderCurrentLast.requestFocus();
                //alertForOrderExceedStock(productOIDClkd,edOrderCurrent,edOrderCurrentLast,-1);

            }
        });




        //now that the dialog is set up, it's time to show it
        listDialog.show();

    }

    @Override
    public void fcsLstCld(boolean hasFocus, EditText editText) {
        mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
        if(!hasFocus)
        {
            EditText edtFcsLst=prdctModelArrayList.getFocusLostEditText();
            if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapDistPrdctStockCount);
            }
            else if(((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1) || (CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2))  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==0))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapDistPrdctStockCount);
            }
            else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapVanPrdctStockCount);
            }
            else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==1))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapVanPrdctStockCount);
            }
            else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapVanPrdctStockCount);
            }
            else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==1)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapVanPrdctStockCount);
            }
            else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockControl")==2)  && (CommonInfo.hmapAppMasterFlags.get("flgVanStockControl")==2))
            {
                fnCreditAndStockCal(-1,edtFcsLst,hmapVanPrdctStockCount);
            }
            else
            {
                nextStepAfterRetailerCreditBal(-1);
            }
            if(flgIsAnySchemeMappedAgainstStore==1)
            {
                //First Calculate Scheme and the do Order Calculation

                orderBookingTotalCalc();
            }
            else {
                orderBookingTotalCalc();
            }

        }
        else
        {
            if(editText.getTag().toString().contains("etOrderQty"))
            {

                mCustomKeyboardNumWithoutDecimal.registerEditText(editText);
                mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(editText);
            }
            else
            {


            }

        }

    }


    public class SaveData extends AsyncTask<String, String, Void>
    {
        int btnClkd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Text need to e changed according to btn Click
            if(flgIsAnySchemeMappedAgainstStore==1)
            {
                //First Calculate Scheme and the do Order Calculation

                orderBookingTotalCalc();
            }
            else {
                orderBookingTotalCalc();
            }

            if(mProgressDialog.isShowing()==false)
            {
                mProgressDialog = new ProgressDialog(ProductEntryForm.this);
                mProgressDialog.setTitle(ProductEntryForm.this.getResources().getString(R.string.Loading));
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



            btnClkd=Integer.parseInt(executedData);



            fnSaveFilledDataToDatabase(btnClkd);
            return null;
        }
        @Override
        protected void onPostExecute(Void args) {

            if(mProgressDialog.isShowing()==true)
            {
                mProgressDialog.dismiss();
            }
            long syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
            String startTS = TimeUtils.getNetworkDateTime(ProductEntryForm.this,TimeUtils.DATE_TIME_FORMAT);
            mDataSource.UpdateStoreEndVisit(storeID,startTS);
            //mDataSource.close();


            if(btnClkd==0) {
                Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductInvoiceReview.class);
                storeOrderReviewIntent.putExtra("storeID", storeID);
                storeOrderReviewIntent.putExtra("SN", SN);
                storeOrderReviewIntent.putExtra("bck", 1);
                storeOrderReviewIntent.putExtra("imei", imei);
                storeOrderReviewIntent.putExtra("userdate", date);
                storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                startActivity(storeOrderReviewIntent);
                finish();
            }
            if(btnClkd==1) {
                Intent storeOrderReviewIntent = new Intent(ProductEntryForm.this, ProductOrderReview.class);
                storeOrderReviewIntent.putExtra("storeID", storeID);
                storeOrderReviewIntent.putExtra("SN", SN);
                storeOrderReviewIntent.putExtra("bck", 1);
                storeOrderReviewIntent.putExtra("imei", imei);
                storeOrderReviewIntent.putExtra("userdate", date);
                storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
                storeOrderReviewIntent.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                storeOrderReviewIntent.putExtra("flgOrderType", flgOrderType);
                startActivity(storeOrderReviewIntent);
                finish();
            }
            if(btnClkd==5) {
                if(flgOrderType==0)
                {
                    Intent prevP2 = new Intent(ProductEntryForm.this, StoreSelection.class);
                    String rID= mDataSource.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
                    //Location_Getting_Service.closeFlag = 0;
                    prevP2.putExtra("imei", imei);
                    prevP2.putExtra("userDate", date);
                    prevP2.putExtra("pickerDate", pickerDate);
                    prevP2.putExtra("rID", rID);
                    startActivity(prevP2);
                    finish();
                }
                else
                {
                    if(CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInApplicable"))
                    {
                        if(CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInApplicable")==1)
                        {

                            Intent nxtP4 = new Intent(ProductEntryForm.this,ActualVisitStock.class);
                            nxtP4.putExtra("storeID", storeID);
                            nxtP4.putExtra("SN", SN);
                            nxtP4.putExtra("imei", imei);
                            nxtP4.putExtra("userdate", date);
                            nxtP4.putExtra("pickerDate", pickerDate);
                            nxtP4.putExtra("StoreVisitCode",StoreVisitCode);
                            startActivity(nxtP4);
                            finish();
                        }
                        else
                        {
                            Intent fireBackDetPg=new Intent(ProductEntryForm.this,LastVisitDetails.class);
                            fireBackDetPg.putExtra("storeID", storeID);
                            fireBackDetPg.putExtra("SN", SN);
                            fireBackDetPg.putExtra("bck", 1);
                            fireBackDetPg.putExtra("imei", imei);
                            fireBackDetPg.putExtra("userdate", date);
                            fireBackDetPg.putExtra("pickerDate", pickerDate);
                            fireBackDetPg.putExtra("flgOrderType", 1);
                            //fireBackDetPg.putExtra("rID", routeID);
                            startActivity(fireBackDetPg);
                            finish();
                        }

                    }
                    else
                    {
                        Intent fireBackDetPg=new Intent(ProductEntryForm.this,LastVisitDetails.class);
                        fireBackDetPg.putExtra("storeID", storeID);
                        fireBackDetPg.putExtra("SN", SN);
                        fireBackDetPg.putExtra("bck", 1);
                        fireBackDetPg.putExtra("imei", imei);
                        fireBackDetPg.putExtra("userdate", date);
                        fireBackDetPg.putExtra("pickerDate", pickerDate);
                        fireBackDetPg.putExtra("flgOrderType", 1);
                        //fireBackDetPg.putExtra("rID", routeID);
                        startActivity(fireBackDetPg);
                        finish();
                    }
                }

            }
//For Button 4

            if(btnClkd==4) // btn return clkd
            {
                Intent fireBackDetPg=new Intent(ProductEntryForm.this,ReturnActivity.class);
                fireBackDetPg.putExtra("storeID", storeID);
                fireBackDetPg.putExtra("SN", SN);
                fireBackDetPg.putExtra("bck", 1);
                fireBackDetPg.putExtra("imei", imei);
                fireBackDetPg.putExtra("userdate", date);
                fireBackDetPg.putExtra("pickerDate", pickerDate);
                fireBackDetPg.putExtra("OrderPDAID", TmpInvoiceCodePDA);
                fireBackDetPg.putExtra("flgPageToRedirect", "1");
                fireBackDetPg.putExtra("flgOrderType", flgOrderType);
                // fireBackDetPg.putExtra("rID", routeID);

                startActivity(fireBackDetPg);
                finish();
            }
        }


    }


    public void fnSaveFilledDataToDatabase(int valBtnClickedFrom)
    {

        int Outstat=1;
        TransactionTableDataDeleteAndSaving(Outstat);
        InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
        //mDataSource.open();
        mDataSource.UpdateStoreFlag(storeID.trim(), 1);
        mDataSource.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,TmpInvoiceCodePDA,TmpInvoiceCodePDA);
        mDataSource.UpdateStoreStoreReturnDetail(storeID.trim(),"1",TmpInvoiceCodePDA,TmpInvoiceCodePDA);
        mDataSource.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",TmpInvoiceCodePDA,TmpInvoiceCodePDA);
        //mDataSource.close();

        if(mDataSource.checkCountIntblStoreSalesOrderPaymentDetails(storeID,TmpInvoiceCodePDA,TmpInvoiceCodePDA)==0)
        {
            String strDefaultPaymentStageForStore= mDataSource.fnGetDefaultStoreOrderPAymentDetails(storeID);
            if(!strDefaultPaymentStageForStore.equals(""))
            {
                //mDataSource.open();
                mDataSource. fnsaveStoreSalesOrderPaymentDetails(storeID,TmpInvoiceCodePDA,strDefaultPaymentStageForStore,"1",TmpInvoiceCodePDA);
                //mDataSource.close();
            }
        }
        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String StampEndsTime = df.format(dateobj);



    }


    public void TransactionTableDataDeleteAndSaving(int Outstat)
    {

        mDataSource.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,TmpInvoiceCodePDA,TmpInvoiceCodePDA);

        //LinkedHashMap<String,String> hmapPrdctOrderQty=prdctModelArrayList.getHmapPrdctOrderQty();
        if(hmapPrdctIdPrdctName!=null)
        {
            for (Map.Entry<String, String> entry:hmapPrdctIdPrdctName.entrySet() )
            {
                int PCateId=Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
                String PName =entry.getValue();
                PName= PName.replaceAll("&","-");
                String ProductID=entry.getKey();
                String ProductStock ="0";
                if(hmapProductIdStock!=null && hmapProductIdStock.containsKey(ProductID))
                {
                    ProductStock= hmapProductIdStock.get(ProductID);
                }


                int ProductExtraOrder=0;
                double TaxRate=0.00;
                double TaxValue=0.00;
                if(ProductStock==null)
                {
                    ProductStock="0";
                }
                String SampleQTY ="0";
                if(SampleQTY.equals(""))
                {
                    SampleQTY="0";
                }
                String OrderQTY =prdctModelArrayList.getPrdctOrderQty(ProductID);

                if(OrderQTY.equals(""))
                {
                    OrderQTY="0";

                }
                String OrderValue="0";
                if(Integer.parseInt(OrderQTY)>0)
                {
                    OrderValue = String.valueOf(prdctModelArrayList.getPrdctOrderVal(ProductID));// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
                    if(OrderValue.equals(""))
                    {
                        OrderValue="0";
                    }
                }

                String OrderFreeQty ="0";

                String OrderDisVal= "0";

                String PRate="0.00";
                int flgIsQuoteRateApplied=0;
                PRate=hmapProductStandardRate.get(ProductID);
                TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
                if(hmapProductTaxValue!=null && hmapProductTaxValue.containsKey(ProductID))
                {
                    TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
                }


                if(Integer.valueOf(OrderFreeQty)>0 || Integer.valueOf(SampleQTY)>0 || Integer.valueOf(OrderQTY)>0 || Integer.valueOf(OrderValue)>0 || Integer.valueOf(OrderDisVal)>0 || ProductExtraOrder>0)// || Integer.valueOf(ProductStock)>0
                {

                    int flgRuleTaxVal=1;
                    mDataSource.fnsaveStoreTempOrderEntryDetails(TmpInvoiceCodePDA,storeID,""+PCateId,ProductID,Double.parseDouble(PRate),TaxRate,flgRuleTaxVal,Integer.parseInt(OrderQTY),Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)),Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,TaxValue,TmpInvoiceCodePDA,flgIsQuoteRateApplied,PriceApplyDiscountLevelType,distID,Outstat,ProductExtraOrder);
                    //mDataSource.close();
                }


            }
        }


    }
    public void InvoiceTableDataDeleteAndSaving(int Outstat,int flgTransferStatus)
    {

        mDataSource.deleteOldStoreInvoice(storeID,TmpInvoiceCodePDA,TmpInvoiceCodePDA);

        Double TBtaxDis;
        Double TAmt;
        Double Dis;
        Double INval;

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
        if(!tv_GrossInvVal.getText().toString().isEmpty()){


            INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
        }
        else{
            INval = 0.00;
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

        mDataSource.saveStoreTempInvoice(StoreVisitCode, TmpInvoiceCodePDA, storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, NoOfCouponValue, TotalCoupunAmount, pickerDate, flgTransType, PriceApplyDiscountLevelType, flgRuleTaxVal, Outstat,flgTransferStatus,mrouteID,mrouteType);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);

        //mDataSource.close();



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
            for (Map.Entry<String,String> entry:hmapPrdctOrderQty.entrySet()) {



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
        tvAfterTaxValue.setText("" + String.format("%.2f", GrossInvValue));

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

    private void setInvoiceTableView() {

        LayoutInflater inflater = getLayoutInflater();
        final View row123 = (View) inflater.inflate(R.layout.activity_detail_productfilter, ll_scheme_detail, false);


        tvCredAmtVAL = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL);
        tvINafterCredVAL = (TextView) row123.findViewById(R.id.textView1_INafterCredVAL);
        textView1_CredAmtVAL_new = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL_new);


        tv_NetInvValue = (TextView) row123.findViewById(R.id.tv_NetInvValue);
        tvTAmt = (TextView) row123.findViewById(R.id.textView1_v2);
        tvDis = (TextView) row123.findViewById(R.id.textView1_v3);
        tv_GrossInvVal = (TextView) row123.findViewById(R.id.tv_GrossInvVal);
        tvFtotal = (TextView) row123.findViewById(R.id.textView1_v5);
        tvAddDisc = (TextView) row123.findViewById(R.id.textView1_AdditionalDiscountVAL);
        tv_NetInvAfterDiscount = (TextView) row123.findViewById(R.id.tv_NetInvAfterDiscount);

        tvAmtPrevDueVAL = (TextView) row123.findViewById(R.id.tvAmtPrevDueVAL);
        tvAmtOutstandingVAL = (TextView) row123.findViewById(R.id.tvAmtOutstandingVAL);
        etAmtCollVAL = (EditText) row123.findViewById(R.id.etAmtCollVAL);

        tvNoOfCouponValue = (EditText) row123.findViewById(R.id.tvNoOfCouponValue);
        txttvCouponAmountValue = (EditText) row123.findViewById(R.id.tvCouponAmountValue);

        tvPreAmtOutstandingVALNew = (TextView) row123.findViewById(R.id.tvPreAmtOutstandingVALNew);
        tvAfterTaxValue = (TextView) row123.findViewById(R.id.tvAfterTaxValue);
        ll_scheme_detail.addView(row123);
        Double outstandingvalue = mDataSource.fnGetStoretblLastOutstanding(storeID);
        tvPreAmtOutstandingVALNew.setText("" + String.format("%.2f", outstandingvalue));

        if(CommonInfo.hmapAppMasterFlags.containsKey("flgVisitStartOutstandingDetails")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgVisitStartOutstandingDetails") == 0) {
                TableRow table_trPreviousOutStanding = (TableRow)findViewById(R.id.table_trPreviousOutStanding);
                table_trPreviousOutStanding.setVisibility(View.GONE);
            }
        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showSoftKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }

    }

    private void getSchemeSlabDetails()
    {

        arrayListSchemeSlabDteail= mDataSource.fnctnSchemeSlabIdSchmVal(storeID);

        hmapSchemeIdStoreID= mDataSource.fnctnSchemeStoreID(storeID);
        hmapSlabIDArrRowID= mDataSource.fngetSlabIDArrRowID(storeID);
        hmapRowIDProductIds= mDataSource.fngetRowIDProductIds(storeID);
        hmapSchemeSlabIDBucketTypeBucketValueForSchemeType1And3= mDataSource.fngetSchemeSlabIDBucketTypeBucketValueForSchemeType1And3(storeID);
        // hmapSchemeStoreID=mDataSource.fnctnSchemeStoreID(storeID);
        if(arrayListSchemeSlabDteail!=null && arrayListSchemeSlabDteail.size()>0)
        {
            hmapSchmeSlabIdSchemeId=arrayListSchemeSlabDteail.get(0);


            hmapSchmeSlabIdSlabDes=arrayListSchemeSlabDteail.get(1);

            hmapSchmeSlabIdBenifitDes=arrayListSchemeSlabDteail.get(2);
        }
    }
}
