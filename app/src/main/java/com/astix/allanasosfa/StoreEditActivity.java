package com.astix.allanasosfa;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.astix.Common.CommonInfo;


import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by User on 31-Jul-18.
 */

public class StoreEditActivity extends BaseActivity implements Camera.PictureCallback{
    //image
    float mDist=0;
    String uriStringPath="";
    Dialog dialog;
    String globalImageName=   "";
    String globalImagePath=      "" ;
    String userName,imageName,imagButtonTag,onlyDate;
    Uri uriSavedImage;
    String clickedTagPhoto;
    View viewStoreLocDetail;
    LinkedHashMap<String,String> hashMapImages=new LinkedHashMap<String,String>();
//

    SharedPreferences pref;
    public String newfullFileName;
    EditText ET_ownername;
    RadioButton  rb_addImage,  rb_replaceImage;
    EditText ET_storecontactno;
    EditText ET_store_address;
    EditText ET_salespersonname;
    EditText ET_salespersoncontact;
    LinearLayout ll_ParentOfImages;
    String storeID = "";
    String activityFrom = "";
    String userdate = "";
    String pickerDate = "";
    String imei = "";
    String rID = "";
    ArrayList store_Data = new ArrayList();
    ImageView backIcon;
    Button btnSubmit,btn_clickImage;

    Context ctx;

    //private MyService mMyService;
    public Context getCtx() {
        return ctx;
    }


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_edit_activity);
        pref=getSharedPreferences(CommonInfo.LastTrackPreference, MODE_PRIVATE);
        initilizeAllViews();
        get_Intent();
        get_Data_And_Set_To_Layout();
        buttonInitialize();
    }

    private void get_Intent() {
        Intent intent=getIntent();
        storeID = intent.getStringExtra("storeID");
        activityFrom = intent.getStringExtra("activityFrom");
        userdate = intent.getStringExtra("userdate");
        pickerDate = intent.getStringExtra("pickerDate");
        imei = intent.getStringExtra("imei");
        rID = intent.getStringExtra("rID");
    }

    public void initilizeAllViews(){
        ET_ownername = (EditText) findViewById(R.id.ET_ownername);
        ET_storecontactno = (EditText) findViewById(R.id.ET_storecontactno);
        ET_store_address = (EditText) findViewById(R.id.ET_store_address);
        ET_salespersonname = (EditText) findViewById(R.id.ET_salespersonname);
        ET_salespersoncontact=(EditText) findViewById(R.id.ET_salespersoncontact);
        rb_addImage = (RadioButton) findViewById(R.id.rb_addImage);
        rb_replaceImage = (RadioButton) findViewById(R.id.rb_replaceImage);
        ll_ParentOfImages = (LinearLayout) findViewById(R.id.ll_ParentOfImages);
        btn_clickImage = (Button) findViewById(R.id.btn_clickImage);
        rb_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_replaceImage.isChecked()){
                    rb_replaceImage.setChecked(false);
                }
                btn_clickImage.setVisibility(View.VISIBLE);
            }
        });
        rb_replaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_addImage.isChecked()){
                    rb_addImage.setChecked(false);
                }
                btn_clickImage.setVisibility(View.VISIBLE);

            }
        });
        btn_clickImage.setTag("Camera");
        btn_clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedTagPhoto=v.getTag().toString();
                openCustomCamara();
            }
        });

    }

    public void get_Data_And_Set_To_Layout(){
        store_Data = mDataSource.fnGetStoreInfoFrom_StoreEdit_Table(storeID);
        if(store_Data.size()>0){
            ET_ownername.setText(store_Data.get(1).toString());
            ET_storecontactno.setText(store_Data.get(2).toString());
            ET_store_address.setText(store_Data.get(3).toString());
            if(!store_Data.get(4).toString().equals("NA"))
            ET_salespersonname.setText(store_Data.get(4).toString());
            if(!store_Data.get(5).toString().equals("NA"))
            ET_salespersoncontact.setText(store_Data.get(5).toString());
            if(!store_Data.get(6).toString().equals("4")){
                //means data sent to server
            }

            if(store_Data.get(7).toString().equals("1")){
                rb_addImage.setChecked(true);
                btn_clickImage.setVisibility(View.VISIBLE);
            }
            else if(store_Data.get(8).equals("1")){
                rb_replaceImage.setChecked(true);
                btn_clickImage.setVisibility(View.VISIBLE);
            }


        }

        else {
            store_Data = mDataSource.fnGetStoreInfoFrom_StoreList_Table(storeID);
            ET_ownername.setText(store_Data.get(1).toString());
            ET_storecontactno.setText(store_Data.get(2).toString());
            ET_store_address.setText(store_Data.get(3).toString());
            if(!store_Data.get(4).toString().equals("NA"))
                ET_salespersonname.setText(store_Data.get(4).toString());
            if(!store_Data.get(5).toString().equals("NA"))
                ET_salespersoncontact.setText(store_Data.get(5).toString());
        }
       hashMapImages= mDataSource.fnGetImageDataFrom_StoreEditImageTable(storeID);
        if(!hashMapImages.isEmpty()){
            for(Map.Entry<String, String> entry:hashMapImages.entrySet())
            {
                String imageName= entry.getKey().trim();
                String imagePath= entry.getValue().trim();
                Bitmap bitmap=null;
                try{
                    String PATH = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/";
                    File file2 = new File(PATH + imageName);
                    if (file2.exists()) {

                        // final int THUMBSIZE = 170;//change quality
                        final int THUMBSIZE = 130;
                        bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(PATH + imageName),
                                THUMBSIZE, THUMBSIZE);
                    }

                }
                catch (Exception e){

                }
                setSavedImageToScrollView(bitmap, imageName,imagePath,"Camera");

            }
        }

    }
        public void buttonInitialize(){
            backIcon=(ImageView)   findViewById(R.id.backIcon);
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent storeIntent = new Intent(StoreEditActivity.this, StoreSelection.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", userdate);
                    storeIntent.putExtra("pickerDate", pickerDate);
                    storeIntent.putExtra("rID", rID);
                    storeIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                    startActivity(storeIntent);
                    finish();
                }
            });
            btnSubmit=(Button)   findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validate()){
                     String  ET_ownernameString=  ET_ownername.getText().toString().trim();
                        String  ET_storecontactnoString=  ET_storecontactno.getText().toString().trim();
                        String  ET_store_addressString=  ET_store_address.getText().toString().trim();
                        String  ET_salespersonnameString=  ET_salespersonname.getText().toString().trim();
                        String  ET_salespersoncontactString=  ET_salespersoncontact.getText().toString().trim();
                        int AddImageFlag=0;
                        int ReplaceImageFlag=0;
                        if(rb_addImage.isChecked()){
                            AddImageFlag=1;
                        }
                        else if(rb_replaceImage.isChecked()){
                            ReplaceImageFlag=1;
                        }



                        mDataSource.deleteStoreEditTable(storeID);
                        mDataSource.insertStoreEditTable(storeID,ET_ownernameString,ET_storecontactnoString,ET_store_addressString,ET_salespersonnameString,ET_salespersoncontactString,3,AddImageFlag,ReplaceImageFlag);
                        for(Map.Entry<String, String> entry:hashMapImages.entrySet())
                        {
                            String imageName= entry.getKey().trim();
                            String imagePath= entry.getValue().trim();
                            mDataSource.insertStoreEditImagesTable(storeID,imageName,imagePath,3);
                        }

                        try {

                            String routeID= mDataSource.GetActiveRouteIDSunil();
                                String lstDataSync = TimeUtils.getNetworkDateTime(StoreEditActivity.this,"dd/MMM HH:mm:ss");
                                pref.edit().putString("LastInvoice",lstDataSync ).commit();
                                StoreSelection.flgChangeRouteOrDayEnd = 0;
                                DayStartActivity.flgDaySartWorking = 0;
                            Intent mMyServiceIntent = new Intent(getCtx(), SyncJobService.class);  //is any of that needed?  idk.
                            mMyServiceIntent.putExtra("routeID", routeID);//
                                mMyServiceIntent.putExtra("storeID", storeID);
                                mMyServiceIntent.putExtra("whereTo", "Regular");//
                            SyncJobService.enqueueWork(getCtx(), mMyServiceIntent);

                                Intent storeIntent = new Intent(StoreEditActivity.this, StoreSelection.class);
                                storeIntent.putExtra("imei", imei);
                                storeIntent.putExtra("userDate", userdate);
                                storeIntent.putExtra("pickerDate", pickerDate);
                                storeIntent.putExtra("rID", rID);
                            storeIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
                                startActivity(storeIntent);
                                finish();


                        }
                        catch (Exception e){

                        }


                    }

                }
            });

        }
        public boolean validate(){
            try{
                Double.parseDouble(ET_storecontactno.getText().toString());
            }
            catch (Exception e){
                ET_storecontactno.setText("");
            }
            try{
                Double.parseDouble(ET_salespersoncontact.getText().toString());
            }
            catch (Exception e){
                ET_salespersoncontact.setText("");
            }
            if(ET_ownername.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(),"Please Enter Owner Name",Toast.LENGTH_SHORT).show();
                return false;
            }
           else if(ET_storecontactno.getText().toString().trim().equals("") ){
                Toast.makeText(getApplicationContext(),"Please Enter Store Contact No.",Toast.LENGTH_SHORT).show();
                return false;
            }

            else if(Double.parseDouble(ET_storecontactno.getText().toString().trim())==0.0){
                Toast.makeText(getApplicationContext(),"Please Enter Proper Store Contact No.",Toast.LENGTH_SHORT).show();
                return false;
            }

            else if(ET_store_address.getText().toString().trim().equals("") || (ET_store_address.getText().toString().trim().length()<10)){
                Toast.makeText(getApplicationContext(),"Please Enter Proper Store Address",Toast.LENGTH_SHORT).show();
                return false;

            }
            else if(ET_salespersonname.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(),"Please Enter Sales Person Name",Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(ET_salespersoncontact.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(),"Please Enter Sales Person Contact No.",Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(Double.parseDouble(ET_salespersoncontact.getText().toString().trim())==0.0){
                Toast.makeText(getApplicationContext(),"Please Enter Proper Sales Person Contact No.",Toast.LENGTH_SHORT).show();
                return false;
            }
            else if((btn_clickImage.getVisibility()==View.VISIBLE) && (ll_ParentOfImages.getChildCount()==0)){
                Toast.makeText(getApplicationContext(),"Please Click at least one Image",Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                return true;
            }


        }
    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }



    public void   openCustomCamara()
    {
        if(dialog!=null)
        {
            if(!dialog.isShowing())
            {
                CustomCamera customCamera=new CustomCamera(this);
                customCamera.open(0);


            }

        }
        else
        {
            CustomCamera customCamera=new CustomCamera(this);
            customCamera.open(0);

        }

    }
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + CommonInfo.imei+ timeStamp + ".jpg");

        return mediaFile;
    }
    @Override
    public void onStop() {
        super.onStop();
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.dismiss();

                }
            }

    }
    public  Bitmap normalizeImageForUri(Context context, Uri uri) {
        Bitmap rotatedBitmap = null;

        try {

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            rotatedBitmap = rotateBitmap(bitmap, orientation);
            if (!bitmap.equals(rotatedBitmap)) {
                saveBitmapToFile(context, rotatedBitmap, uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }
    private  Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    private  void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {

            } finally {
                closeSilently(outputStream);
                croppedImage.recycle();
            }
        }
    }

    private  void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public void setSavedImageToScrollView(Bitmap bitmap,String imageValidName,String filePathImage,String clickedTagPhoto){

        if((bitmap!=null) && (imageValidName!=null) ){
            LayoutInflater inflater=(LayoutInflater) StoreEditActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      final  View    viewStoreLocDetail=inflater.inflate(R.layout.store_loc_display,null);
            final RelativeLayout rl_photo=(RelativeLayout) viewStoreLocDetail.findViewById(R.id.rl_photo);
            final ImageView img_thumbnail=(ImageView)viewStoreLocDetail.findViewById(R.id.img_thumbnail);
            img_thumbnail.setImageBitmap(bitmap);
            img_thumbnail.setTag(filePathImage);
            img_thumbnail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        String filePathName="";
                        if(view.getTag().toString().contains("file:")){
                            filePathName=view.getTag().toString().replace("file:","");
                        }
                        else {
                            filePathName=view.getTag().toString();

                        }
                        File file = new File(filePathName);
                        Uri intentUri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext().getPackageName() + ".provider", file);
                        intent.setDataAndType(intentUri, "image/*");
                        startActivity(intent);

                    }
                    else{
                        Uri intentUri = Uri.parse(view.getTag().toString());
                        intent.setDataAndType(intentUri, "image/*");
                        startActivity(intent);
                    }


                }

            });

            final ImageView imgCncl=(ImageView) viewStoreLocDetail.findViewById(R.id.imgCncl);
            imgCncl.setTag(imageValidName);
            imgCncl.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    String file_dj_path = img_thumbnail.getTag().toString();
                    if(file_dj_path.contains("file:")){
                        file_dj_path=    file_dj_path .replace("file:","");
                    }
                    File fdelete = new File(file_dj_path);
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {

                            callBroadCast();
                        } else {

                        }
                    }
                    if(hashMapImages.containsKey(imgCncl.getTag().toString())){
                        hashMapImages.remove(imgCncl.getTag().toString());
                    }
                    ll_ParentOfImages.removeView(viewStoreLocDetail);
                }
            });






            if(ll_ParentOfImages!=null)
            {
                hashMapImages.put(imageValidName,filePathImage);
                ll_ParentOfImages.addView(viewStoreLocDetail);
            }


        }


    }
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(StoreEditActivity.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            StoreEditActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
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

            //Toast toast = Toast.makeText(getActivity(), "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
            //toast.show();
            //put data here

                  /*  arrImageData.add(0,pictureFile);
                    arrImageData.add(1,pictureFile.getName());*/
           // dialog.dismiss();
            if(pictureFile!=null)
            {
                File file=pictureFile;
                System.out.println("File +++"+pictureFile);
                imageName=pictureFile.getName();
                normalizeImageForUri(StoreEditActivity.this, Uri.fromFile(pictureFile));


                // Convert ByteArray to Bitmap::\
                //
                uriSavedImage = Uri.fromFile(pictureFile);
                long syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
                String clkdTime = df.format(dateobj);
                //	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
                String filePathImage=uriSavedImage.toString();
                //   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);

                globalImageName=   imageName;
                globalImagePath=      uriSavedImage.toString() ;
                //
                Bitmap bitmap=null;
                try{
                    String PATH = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/";
                    File file2 = new File(PATH + imageName);
                    if (file2.exists()) {

                        // final int THUMBSIZE = 170;//change quality
                        final int THUMBSIZE = 130;
                        bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(PATH + imageName),
                                THUMBSIZE, THUMBSIZE);
                    }

                }
                catch (Exception e){

                }
                setSavedImageToScrollView(bitmap, imageName,filePathImage,clickedTagPhoto);
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
}
