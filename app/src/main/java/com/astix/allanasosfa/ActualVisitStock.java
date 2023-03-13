package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.BaseActivity;
import com.astix.allanasosfa.CategoryCommunicator;
import com.astix.allanasosfa.StockFilledDataModel;
import com.astix.allanasosfa.camera.CustomCamera;
import com.astix.allanasosfa.focusLostCalled;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.allanasosfa.truetime.TimeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class ActualVisitStock extends BaseActivity implements CategoryCommunicator, focusLostCalled, Camera.PictureCallback {

    public EditText ed_search;
    public ImageView btn_go;
    public String storeID;
    public String imei;
    public String date;
    public String pickerDate;
    public String selStoreName;
    public Dialog dialog = null;
    public int StoreCurrentStoreType = 0;
    String imageName;
    File imageF;
    StockFilledDataModel stockFilledDataModel = new StockFilledDataModel();
    Uri uriSavedImage;
    float mDist = 0;
    ArrayList<Object> arrImageData = new ArrayList<Object>();
    LinkedHashMap<String, String> hmapPhotoDetailsForSaving = new LinkedHashMap<>();
    //Custom Keyboard
    com.astix.allanasosfa.customwidgets.CustomKeyboard mCustomKeyboardNum, mCustomKeyboardNumWithoutDecimal;
    RecyclerView rv_main;
    Button btnNext;
    List<String> categoryNames;
    int progressBarStatus = 0;
    LinkedHashMap<String, String> hmapctgry_details = new LinkedHashMap<String, String>();
    ImageView img_ctgry;
    String previousSlctdCtgry = "", clickedTagPhoto;
    Button btnClickPic, btnViewPic;
    LinearLayout ll_StockPicData;
    LinkedHashMap<String, String> hmapPrdctData = new LinkedHashMap<>();
    LinkedHashMap<String, String> hmapFilterProductList = new LinkedHashMap<String, String>();
    LinkedHashMap<String, ArrayList<String>> hmapStockPhotoSection = new LinkedHashMap<String, ArrayList<String>>();
    LinkedHashMap<String, String> hmapFetchPDASavedData = new LinkedHashMap<>();
    LinkedHashMap<String, String> hmapSaveDataInPDA = new LinkedHashMap<>();
    LinkedHashMap<String, String> hmapProductStockFromPurchaseTable = new LinkedHashMap<>();
    private Context myContext;

    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", CommonInfo.ImagesFolder);

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss.SSS", Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + CommonInfo.imei + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;

        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {

        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_visit_stock);

        initializeallViews();
        getDataFromIntent();
        fetchDataFromDatabase();
    }

    public void initializeallViews() {

        mCustomKeyboardNum = new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num);
        mCustomKeyboardNumWithoutDecimal = new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal);

        LinearLayout ll_StoreCheckPhoto = (LinearLayout) findViewById(R.id.ll_StoreCheckPhoto);
        if (CommonInfo.hmapAppMasterFlags.containsKey("flgStoreCheckInPhotoCompulsory")) {
            if (CommonInfo.hmapAppMasterFlags.get("flgStoreCheckInPhotoCompulsory") == 1) {
                ll_StoreCheckPhoto.setVisibility(View.VISIBLE);
            }
        }

        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        ImageView img_back_Btn = (ImageView) findViewById(R.id.img_back_Btn);
        btnNext = (Button) findViewById(R.id.btnNext);


        img_ctgry = (ImageView) findViewById(R.id.img_ctgry);
        ed_search = (EditText) findViewById(R.id.ed_search);
        btn_go = (ImageView) findViewById(R.id.btn_go);
        btnClickPic = (Button) findViewById(R.id.btnClickPic);
        btnViewPic = (Button) findViewById(R.id.btnViewPic);


        btnViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRemarksAlert(storeID, false);
            }
        });
        btnClickPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRemarksAlert(storeID, true);
            }
        });

        img_ctgry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_ctgry.setEnabled(false);
                customAlertStoreList(categoryNames, "Select Category");
            }
        });


        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(ed_search.getText().toString().trim())) {

                    if (!ed_search.getText().toString().trim().equals("")) {
                        searchProduct(ed_search.getText().toString().trim(), "");

                    }


                } else {
                    searchProduct("All", "");
                }

            }


        });

        img_back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fireBackDetPg = new Intent(ActualVisitStock.this, LastVisitDetails.class);
                fireBackDetPg.putExtra("storeID", storeID);
                fireBackDetPg.putExtra("SN", selStoreName);
                fireBackDetPg.putExtra("bck", 1);
                fireBackDetPg.putExtra("imei", imei);
                fireBackDetPg.putExtra("userdate", date);
                fireBackDetPg.putExtra("pickerDate", pickerDate);
                fireBackDetPg.putExtra("flgOrderType", 1);
                //fireBackDetPg.putExtra("rID", routeID);
                startActivity(fireBackDetPg);
                finish();
                //aa

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.deleteActualVisitData(storeID);
                LinkedHashMap<String, String> hmapSaveStockData = stockFilledDataModel.getHmapPrdctStock();
                if (hmapSaveStockData != null && hmapSaveStockData.size() > 0) {
                    for (Map.Entry<String, String> entry : hmapSaveStockData.entrySet()) {

                        mDataSource.saveTblActualVisitStock(storeID, entry.getKey(), entry.getValue(), 1);


                    }
                }

                if (hmapPhotoDetailsForSaving != null && hmapPhotoDetailsForSaving.size() > 0) {
                    mDataSource.insertStoreCheckInPic(storeID, hmapPhotoDetailsForSaving);
                }


                passIntentToProductOrderFilter();

            }
        });

    }

    public void passIntentToProductOrderFilter() {
        Intent nxtP4 = new Intent(ActualVisitStock.this, ProductEntryForm.class);
        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
        nxtP4.putExtra("storeID", storeID);
        nxtP4.putExtra("SN", selStoreName);
        nxtP4.putExtra("imei", imei);
        nxtP4.putExtra("userdate", date);
        nxtP4.putExtra("pickerDate", pickerDate);
        nxtP4.putExtra("flgOrderType", 1);
        startActivity(nxtP4);
        finish();
    }

    public void inflatePrdctStockData() {


        if (hmapFilterProductList != null && hmapFilterProductList.size() > 0) {

            String[] listProduct = new String[hmapFilterProductList.size()];
            int index = 0;
            for (Map.Entry<String, String> entry : hmapFilterProductList.entrySet()) {
                listProduct[index] = entry.getKey() + "^" + entry.getValue();
                index++;
            }
            StoreCheckInAdapter storeCheckInAdapter = new StoreCheckInAdapter(ActualVisitStock.this, listProduct, hmapFilterProductList, stockFilledDataModel);
            rv_main.setAdapter(storeCheckInAdapter);
            rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        }
    }

    public void fetchDataFromDatabase() {
        //mDataSource.open();
        hmapPrdctData = mDataSource.fetchProductDataForActualVisit();
        hmapFetchPDASavedData = mDataSource.fetchActualVisitData(storeID);
        if (hmapFetchPDASavedData != null && hmapFetchPDASavedData.size() > 0) {
            for (Map.Entry<String, String> entry : hmapFetchPDASavedData.entrySet()) {
                stockFilledDataModel.setPrdctStock(entry.getKey(), entry.getValue());
            }

        }
        hmapProductStockFromPurchaseTable = mDataSource.fetchProductStockFromPurchaseTable(storeID);
        StoreCurrentStoreType = Integer.parseInt(mDataSource.fnGetStoreTypeOnStoreIdBasis(storeID));

        ArrayList<String> list_ImgName = mDataSource.getImageNameForStoreCheckIn(storeID);
        if (list_ImgName != null && list_ImgName.size() > 0) {
            hmapStockPhotoSection.put(storeID, list_ImgName);


        }


        if (hmapStockPhotoSection != null && hmapStockPhotoSection.size() > 0) {
            btnViewPic.setVisibility(View.VISIBLE);
            //btnClickPic.setText("Edit/Add Stock Pic");
            btnClickPic.setText(getResources().getString(R.string.AddEditStorePicture));
        } else {
            btnViewPic.setVisibility(View.GONE);
            btnClickPic.setText(getResources().getString(R.string.AddStorePicture));
        }

        //mDataSource.close();

        getCategoryDetail();

        Iterator it11new = hmapProductStockFromPurchaseTable.entrySet().iterator();
        String crntPID = "0";
        int cntPsize = 0;
        while (it11new.hasNext()) {
            Map.Entry pair = (Map.Entry) it11new.next();

            hmapFetchPDASavedData.put(pair.getKey().toString(), pair.getValue().toString());
        }


        searchLoadDefaultProduct("All", "");//********WE load defualt product on Oncreate

    }

    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        if (passedvals != null) {

            storeID = passedvals.getStringExtra("storeID");
            imei = passedvals.getStringExtra("imei");
            date = passedvals.getStringExtra("userdate");
            pickerDate = passedvals.getStringExtra("pickerDate");
            selStoreName = passedvals.getStringExtra("SN");

        }

    }

    public void customAlertStoreList(final List<String> listOption, String sectionHeader) {

        final Dialog listDialog = new Dialog(ActualVisitStock.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.search_list);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        //there are a lot of settings, for dialog, check them all out!
        parms.dimAmount = (float) 0.5;


        TextView txt_section = (TextView) listDialog.findViewById(R.id.txt_section);
        txt_section.setText(sectionHeader);
        TextView txtVwCncl = (TextView) listDialog.findViewById(R.id.txtVwCncl);
        //    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);


        final ListView list_store = (ListView) listDialog.findViewById(R.id.list_store);
        final CardArrayAdapterCategory cardArrayAdapter = new CardArrayAdapterCategory(ActualVisitStock.this, listOption, listDialog, previousSlctdCtgry);

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
        previousSlctdCtgry = selectedCategory;

        //  img_ctgry.setText(selectedCategory);

        if (hmapctgry_details.containsKey(selectedCategory)) {
            searchProduct(selectedCategory, hmapctgry_details.get(selectedCategory));
        } else {
            searchProduct(selectedCategory, "");
        }


    }

    public void searchProduct(String filterSearchText, String ctgryId) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBarStatus = 0;


        hmapFilterProductList.clear();


        hmapFilterProductList = mDataSource.getFileredProductListMap(filterSearchText.trim(), StoreCurrentStoreType, ctgryId);


        if (hmapFilterProductList.size() > 0) {
            inflatePrdctStockData();
        } else {
            allMessageAlert(ActualVisitStock.this.getResources().getString(R.string.AlertFilter));
        }

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ActualVisitStock.this);
        alertDialogNoConn.setTitle(ActualVisitStock.this.getResources().getString(R.string.genTermInformation));
        alertDialogNoConn.setMessage(message);
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(ActualVisitStock.this.getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

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
                index = index + 1;
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    public void searchLoadDefaultProduct(String filterSearchText, String ctgryId) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBarStatus = 0;


        hmapFilterProductList.clear();


        hmapFilterProductList = mDataSource.fetchProductListLastvisitAndOrderBasis(storeID);
        if (hmapFilterProductList != null && hmapFilterProductList.isEmpty()) {
            hmapFilterProductList = mDataSource.getFileredProductListMap(filterSearchText.trim(), StoreCurrentStoreType, ctgryId);

        }

        if (hmapFilterProductList.size() > 0) {
            inflatePrdctStockData();
        } else {
            allMessageAlert(ActualVisitStock.this.getResources().getString(R.string.AlertFilter));
        }


        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    void openRemarksAlert(final String tagVal, boolean isToEditPic) {
        final Dialog listDialogMulti = new Dialog(ActualVisitStock.this);
        listDialogMulti.requestWindowFeature(Window.FEATURE_NO_TITLE);
      /*  // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
*/

        // inflate and adjust layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        listDialogMulti.setContentView(R.layout.stock_check_pic);
        // View layout = inflater.inflate(R.layout.stock_check_pic, null);
        WindowManager.LayoutParams parms = listDialogMulti.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        parms.width = WindowManager.LayoutParams.FILL_PARENT;
        parms.height = WindowManager.LayoutParams.WRAP_CONTENT;
       /* layout.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));*/
        Button btn_clkCamera = (Button) listDialogMulti.findViewById(R.id.btn_clkCamera);
        btn_clkCamera.setTag(tagVal);
        if (isToEditPic) {
            btn_clkCamera.setVisibility(View.VISIBLE);
        } else {
            btn_clkCamera.setVisibility(View.GONE);
        }
        final LinearLayout ll_RemarkImage = (LinearLayout) listDialogMulti.findViewById(R.id.ll_RemarkImage);
        Button btn_done = (Button) listDialogMulti.findViewById(R.id.btn_done);


        if (hmapStockPhotoSection != null && hmapStockPhotoSection.containsKey(tagVal)) {
            String selectedImageName = "";
            ArrayList<String> listImage = hmapStockPhotoSection.get(tagVal);
            for (String imageName : listImage) {
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageName;

                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists()) {
                    //  Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fImageShow.getAbsolutePath()), 120, 120);
                    //    adapterImage.add(i,bmp,imgName);
                    //setSavedImageWareHouseRemark(Bitmap bitmap, String imageName, String valueOfKey, final String clickedTagPhoto, final LinearLayout ll_imgToSet,boolean isClkdPic)
                    setSavedImageWareHouseRemark(fImageShow.getAbsolutePath(), imageName, "", tagVal, ll_RemarkImage, false, isToEditPic);
                    selectedImageName = imageName;
                }
            }

        }
        btn_clkCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_StockPicData = ll_RemarkImage;
                clickedTagPhoto = v.getTag().toString();
                CustomCamera customCamera = new CustomCamera(ActualVisitStock.this);
                customCamera.open(0);

            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listDialogMulti.dismiss();
            }
        });

        listDialogMulti.setCanceledOnTouchOutside(true);

        listDialogMulti.show();
    }

    void setSavedImageWareHouseRemark(String imagePath, String imageName, String valueOfKey, final String clickedTagPhoto, final LinearLayout ll_imgToSet, boolean isClkdPic, boolean isToPicEdit) {
        LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View convertView = inflate.inflate(R.layout.images_return_grid, null);

        //tagVal= catId+"_"+prodID+"_RemarkImage"


        ImageView img_thumbnail = (ImageView) convertView.findViewById(R.id.img_thumbnail);

        Uri uri = Uri.fromFile(new File(imagePath));

        Glide.with(this)
                .load(uri)
                .override(120, 120)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_not_found)
                .into(img_thumbnail);


        ImageView imgCncl = (ImageView) convertView.findViewById(R.id.imgCncl);
        imgCncl.setTag(imageName);

        if (!isToPicEdit) {
            imgCncl.setVisibility(View.GONE);
        } else {
            imgCncl.setVisibility(View.VISIBLE);
        }
        if (isClkdPic) {
            ArrayList<String> listClkdPic = new ArrayList<String>();
            if (hmapStockPhotoSection != null && hmapStockPhotoSection.containsKey(clickedTagPhoto)) {
                listClkdPic = hmapStockPhotoSection.get(clickedTagPhoto);
            }

            listClkdPic.add(imageName);
            hmapStockPhotoSection.put(clickedTagPhoto, listClkdPic);
            System.out.println("Hmap Photo category..." + clickedTagPhoto + "^" + imageName);

            String photoPath = valueOfKey.split(Pattern.quote("~"))[0];
            String clickedDataTime = valueOfKey.split(Pattern.quote("~"))[1];


            //key- imagName
            //value- businessId^CatID^TypeID^templateID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo
        /*    savetbWareHousePhotoDetails(String StoreID,String PhotoName,
                    String PhotoPath,String ClickedDateTime,
                    String ClickTagPhoto,String Sstat)*/
            //  tagVal+"_edReason";

            hmapPhotoDetailsForSaving.put(imageName, photoPath + "^" + clickedDataTime + "^" + clickedTagPhoto);


        }

        ll_imgToSet.addView(convertView);
        if (hmapStockPhotoSection != null && hmapStockPhotoSection.size() > 0) {
            btnViewPic.setVisibility(View.VISIBLE);
            btnClickPic.setText("Edit/Add Stock Pic");
        } else {
            btnViewPic.setVisibility(View.GONE);
            btnClickPic.setText("Add Stock Pic");
        }
        imgCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageNameToDelVal = v.getTag().toString();

                ll_imgToSet.removeView(convertView);
                ArrayList listClkdPic = new ArrayList();
                if (hmapStockPhotoSection != null && hmapStockPhotoSection.containsKey(clickedTagPhoto)) {
                    listClkdPic = hmapStockPhotoSection.get(clickedTagPhoto);
                }

                if (listClkdPic.contains(imageNameToDelVal)) {
                    listClkdPic.remove(imageNameToDelVal);

                    hmapStockPhotoSection.put(clickedTagPhoto, listClkdPic);

                    mDataSource.validateStoreCheckIn(storeID, imageNameToDelVal);
                    if (hmapPhotoDetailsForSaving.containsKey(imageNameToDelVal)) {
                        hmapPhotoDetailsForSaving.remove(imageNameToDelVal);
                    }

                    if (listClkdPic.size() < 1) {
                        hmapStockPhotoSection.remove(clickedTagPhoto);
                    }
                }

                //  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageNameToDelVal;

                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        // Log.e("-->", "file Deleted :" + file_dj_path);
                        callBroadCast();
                    } else {
                        // Log.e("-->", "file not Deleted :" + file_dj_path);
                    }
                }
                if (hmapStockPhotoSection != null && hmapStockPhotoSection.size() > 0) {
                    btnViewPic.setVisibility(View.VISIBLE);
                    btnClickPic.setText("Edit/Add Stock Pic");
                } else {
                    btnViewPic.setVisibility(View.GONE);
                    btnClickPic.setText("Add Stock Pic");
                }
            }
        });
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
    public void fcsLstCld(boolean hasFocus, EditText editText) {

        mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
        if (hasFocus) {
            if (editText.getTag().toString().contains("_etStock")) {

                mCustomKeyboardNumWithoutDecimal.registerEditText(editText);
                mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(editText);
            }
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
        try {
            //write the file
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();

            arrImageData.add(0, pictureFile);
            arrImageData.add(1, pictureFile.getName());
            dialog.dismiss();
            if (pictureFile != null) {
                System.out.println("File +++" + pictureFile);
                imageName = pictureFile.getName();
                CommonFunction.normalizeImageForUri(ActualVisitStock.this, Uri.fromFile(pictureFile));
                // Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                uriSavedImage = Uri.fromFile(pictureFile);
                // bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                // byte[] byteArray = stream.toByteArray();

                // Convert ByteArray to Bitmap::\
                //
                String clkdTime = TimeUtils.getNetworkDateTime(ActualVisitStock.this, TimeUtils.DATE_TIME_FORMAT);
                ;
                //	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
                String valueOfKey = uriSavedImage.toString() + "~" + clkdTime + "~" + "1";
                //   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);
                //  Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


                setSavedImageWareHouseRemark(pictureFile.getAbsolutePath(), imageName, valueOfKey, clickedTagPhoto, ll_StockPicData, true, true);


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
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
