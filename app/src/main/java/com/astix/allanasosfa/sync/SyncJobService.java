package com.astix.allanasosfa.sync;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.LastVisitDetails;
import com.astix.allanasosfa.LastVisitDetails;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.utils.AppUtils;
import com.allanasosfa.truetime.TimeUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SyncJobService extends JobIntentService {

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private static final String TAG = "SyncJobService";

    public String[] xmlForWeb = new String[1];
    public String zipFileName;
    public int IMGsyOK = 0;
    InputStream inputStream;
    SyncImageData task1;
    String whereTo="Regular";
    String storeID="0";
    SyncXMLfileData task2;
    private String FilePathStrings;
    public String fnameIMG;

    public String UploadingImageName;

    private File[] listFile;
    public  File fileintial;
    public String routeID="0";
    String xmlFileName;
    int serverResponseCode = 0;
    private AppDataSource db;
    private String storeVisitCode;

    private Handler mHandler=new Handler();
    private DataSyncRunnable mDataSyncRunnable;




    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SyncJobService.class, JOB_ID, work);
    }



    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        db= AppDataSource.getInstance(this);
        whereTo = intent.getStringExtra("whereTo");
        storeID= intent.getStringExtra("storeID");
        if(intent.hasExtra("routeID"))
            routeID =intent.getStringExtra("routeID");
        if(intent.hasExtra("StoreVisitCode"))
            storeVisitCode =intent.getStringExtra("StoreVisitCode");

        exportDataToXML();

    }


    private void exportDataToXML(){

        String newfullFileName= AppUtils.getIMEI(this)+"."+routeID+"."+ TimeUtils.getNetworkDateTime(this,"dd.MMM.yyyy.HH.mm.ss");

        DatabaseAssistant databaseAssistant=new DatabaseAssistant(this);
        try {
            databaseAssistant.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);
        }catch (Exception e){
            e.printStackTrace();
        }

        xmlForWeb[0]= Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml";
        zipFileName = newfullFileName;

        db.savetbl_XMLfiles(newfullFileName, "3","1");
        updateSstat();

        startSyncTasks();



    }


    private void updateSstat(){
        db.fnSettblAttandanceDetails();
        db.UpdateXMLCreatedFilesTablesFlag(5);
        if(storeID!=null && storeID.length()>0) {
            db.UpdateStorWhileAdding(storeID, 5);
            db.UpdateSstatSurvey(storeID);
            db.UpdateTableStoreEdit(5,storeID);
            db.UpdatetblStoreOrderVisitDayActivitySstat(storeID,5,storeVisitCode, LastVisitDetails.TempStoreVisitCode);
        }

    }

    private void startSyncTasks(){
        try
        {

            mDataSyncRunnable=new DataSyncRunnable();
            mHandler.post(mDataSyncRunnable);
        }
        catch(Exception e)
        {
e.printStackTrace();
        }

    }

    private class DataSyncRunnable implements Runnable{

        @Override
        public void run() {

            if(AppUtils.isInternetAvailable()) {
                task1 = new SyncImageData();
                AppUtils.executeAsyncTask(task1);
            }else
                mHandler.postDelayed(mDataSyncRunnable,1000*60*10);



        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class SyncImageData extends AsyncTask<Void, Void, Void>
    {
        String[] fp2s;
        String[] NoOfOutletID;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            routeID=db.GetActiveRouteID(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);





            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {

            }
            else
            {
                // Locate the image folder in your SD Card
                fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
                // Create a new folder if no folder named SDImageTutorial exist
                fileintial.mkdirs();

            }


            if (fileintial.isDirectory())
            {
                listFile = fileintial.listFiles();
            }





        }

        @Override
        protected Void doInBackground(Void... params)
        {


            // Sync POS Images

            try
            {

                try
                {

                    NoOfOutletID = db.getAllStoreIDIntblStoreMaterialPhotoDetail();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
                for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                {

                    int NoOfImages = db.getExistingPicNos(NoOfOutletID[chkCountstore].toString());
                    String[] NoOfImgsPath = db.getImgsPath(NoOfOutletID[chkCountstore].toString());


                    fp2s = new String[2];

                    for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                    {
                        fp2s[0] = NoOfImgsPath[syCOUNT];
                        fp2s[1] = NoOfOutletID[chkCountstore];

                        // New Way

                        fnameIMG = fp2s[0];
                        UploadingImageName=fp2s[0];


                        String stID = fp2s[1];
                        String currentImageFileName=fnameIMG;

                        boolean isImageExist=false;
                        for (int i = 0; i < listFile.length; i++)
                        {
                            FilePathStrings = listFile[i].getAbsolutePath();
                            if(listFile[i].getName().equals(fnameIMG))
                            {
                                fnameIMG=listFile[i].getAbsolutePath();
                                isImageExist=true;
                                break;
                            }
                        }
                        if(isImageExist)
                        {
							ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            String image_str= compressImage(fnameIMG);// BitMapToString(bmp);


                            try
                            {
                                stream.flush();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                stream.close();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }

                           String onlyDate= TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");


                            try
                            {
                                uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,routeID);

                            }catch(Exception e)
                            {
                                IMGsyOK = 1;

                            }
                        }


                    }


                }
            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }


            // Sync Return Images

            try
            {

                try
                {


                    NoOfOutletID = db.getAllStoreIDIntblStoreReturnPhotoDetail();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }

                for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                {

                    int NoOfImages = db.getExistingPicNosForReturn(NoOfOutletID[chkCountstore].toString());
                    String[] NoOfImgsPath = db.getImgsPathForReturn(NoOfOutletID[chkCountstore].toString());


                    fp2s = new String[2];

                    for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                    {
                        fp2s[0] = NoOfImgsPath[syCOUNT];
                        fp2s[1] = NoOfOutletID[chkCountstore];

                        // New Way

                        fnameIMG = fp2s[0];
                        UploadingImageName=fp2s[0];


                        String stID = fp2s[1];
                        String currentImageFileName=fnameIMG;

                        boolean isImageExist=false;
                        for (int i = 0; i < listFile.length; i++)
                        {
                            FilePathStrings = listFile[i].getAbsolutePath();
                            if(listFile[i].getName().equals(fnameIMG))
                            {
                                fnameIMG=listFile[i].getAbsolutePath();
                                isImageExist=true;
                                break;
                            }
                        }
                        if(isImageExist)
                        {
									/* Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
						             ByteArrayOutputStream stream = new ByteArrayOutputStream();

						             String image_str=  BitMapToString(bmp);*/
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            String image_str= compressImage(fnameIMG);// BitMapToString(bmp);


                            try
                            {
                                stream.flush();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                stream.close();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }

                            String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");


                            try
                            {



                            }catch(Exception e)
                            {
                                IMGsyOK = 1;

                            }
                        }


                    }


                }

            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }


            // Sync Add New Stores Images

            try
            {

                try
                {


                    NoOfOutletID = db.getAllStoreIDIntblNewAddedStorePhotoDetail();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }

                for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                {

                    int NoOfImages = db.getExistingPicNosForNewAddedStore(NoOfOutletID[chkCountstore].toString());
                    String[] NoOfImgsPath = db.getImgsPathForNewAddedStore(NoOfOutletID[chkCountstore].toString());


                    fp2s = new String[2];

                    for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                    {
                        fp2s[0] = NoOfImgsPath[syCOUNT];
                        fp2s[1] = NoOfOutletID[chkCountstore];

                        // New Way

                        fnameIMG = fp2s[0];
                        UploadingImageName=fp2s[0];


                        String stID = fp2s[1];
                        String currentImageFileName=fnameIMG;

                        boolean isImageExist=false;
                        for (int i = 0; i < listFile.length; i++)
                        {
                            FilePathStrings = listFile[i].getAbsolutePath();
                            if(listFile[i].getName().equals(fnameIMG))
                            {
                                fnameIMG=listFile[i].getAbsolutePath();
                                isImageExist=true;
                                break;
                            }
                        }
                        if(isImageExist)
                        {
                            //Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                            ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();


                            try
                            {
                                stream.flush();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                stream.close();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }

                            long syncTIMESTAMP = System.currentTimeMillis();
                            Date datefromat = new Date(syncTIMESTAMP);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
                            String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");



                            try
                            {

                                uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,routeID);

                            }catch(Exception e)
                            {
                                IMGsyOK = 1;

                            }
                        }


                    }


                }

            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }

            // Sync Stores Close Images

            try
            {

                try
                {


                    NoOfOutletID = db.getAllStoreClosePhotoDetail();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }

                for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                {

                    int NoOfImages = db.getExistingPicNosForStoreClose(NoOfOutletID[chkCountstore].toString());
                    String[] NoOfImgsPath = db.getImgsPathForStoreClose(NoOfOutletID[chkCountstore].toString());


                    fp2s = new String[2];

                    for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                    {
                        fp2s[0] = NoOfImgsPath[syCOUNT];
                        fp2s[1] = NoOfOutletID[chkCountstore];

                        // New Way

                        fnameIMG = fp2s[0];
                        UploadingImageName=fp2s[0];


                        String stID = fp2s[1];
                        String currentImageFileName=fnameIMG;

                        boolean isImageExist=false;
                        for (int i = 0; i < listFile.length; i++)
                        {
                            FilePathStrings = listFile[i].getAbsolutePath();
                            if(listFile[i].getName().equals(fnameIMG))
                            {
                                fnameIMG=listFile[i].getAbsolutePath();
                                isImageExist=true;
                                break;
                            }
                        }
                        if(isImageExist)
                        {
								/*Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str=  BitMapToString(bmp);*/

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                            ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();


                            try
                            {
                                stream.flush();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                stream.close();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }

                             String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");

                            try
                            {

                                uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,routeID);
                            }catch(Exception e)
                            {
                                IMGsyOK = 1;

                            }
                        }


                    }


                }

            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }





            // Sync Signature And selfi Images

            try
            {

                try
                {


                    NoOfOutletID = db.getAllDSRSignatureAndSelfi();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
                if(NoOfOutletID.length>0)
                {
                    for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                    {

                        int NoOfImages = db.getExistingPicNosForSignatureAndSelfi();
                        String[] NoOfImgsPath = db.getImgsPathForSignatureAndSelfi();


                        fp2s = new String[2];

                        for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                        {
                            fp2s[0] = NoOfImgsPath[syCOUNT];
                            fp2s[1] = NoOfOutletID[chkCountstore];

                            // New Way

                            fnameIMG = fp2s[0];
                            UploadingImageName=fp2s[0];


                            String stID = fp2s[1];
                            String currentImageFileName=fnameIMG;

                            boolean isImageExist=false;
                            for (int i = 0; i < listFile.length; i++)
                            {
                                FilePathStrings = listFile[i].getAbsolutePath();
                                if(listFile[i].getName().equals(fnameIMG))
                                {
                                    fnameIMG=listFile[i].getAbsolutePath();
                                    isImageExist=true;
                                    break;
                                }
                            }
                            if(isImageExist)
                            {
				/*Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				String image_str=  BitMapToStringDSSelfiAndSignature(bmp);*/

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                                ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();


                                try
                                {
                                    stream.flush();
                                }
                                catch (IOException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                try
                                {
                                    stream.close();
                                }
                                catch (IOException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                                String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");


                                    try
                                {

                                    uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,routeID);
                                }catch(Exception e)
                                {
                                    IMGsyOK = 1;

                                }
                            }


                        }


                    }
                }


            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }



            // Sync Store Edit Images

            try
            {

                try
                {

                    NoOfOutletID = db.getAllStoreIdOfEditStoteImage();


                } catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(NoOfOutletID.length>0)
                {
                    for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                    {
                        int NoOfImages = db.getImageCountForStoreEdit(NoOfOutletID[chkCountstore].toString());
                        String[] NoOfImgsPath = db.getImgsCountStoreEdit(NoOfOutletID[chkCountstore].toString());


                        fp2s = new String[2];

                        for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                        {
                            fp2s[0] = NoOfImgsPath[syCOUNT];
                            fp2s[1] = NoOfOutletID[chkCountstore];

                            // New Way

                            fnameIMG = fp2s[0];
                            UploadingImageName=fp2s[0];


                            String stID = fp2s[1];
                            String currentImageFileName=fnameIMG;

                            boolean isImageExist=false;
                            for (int i = 0; i < listFile.length; i++)
                            {
                                FilePathStrings = listFile[i].getAbsolutePath();
                                if(listFile[i].getName().equals(fnameIMG))
                                {
                                    fnameIMG=listFile[i].getAbsolutePath();
                                    isImageExist=true;
                                    break;
                                }
                            }
                            if(isImageExist)
                            {
				/*Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				String image_str=  BitMapToStringDSSelfiAndSignature(bmp);
				ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
*/
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                                ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

                                try
                                {
                                    stream.flush();
                                }
                                catch (IOException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                try
                                {
                                    stream.close();
                                }
                                catch (IOException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                                 String onlyDate=TimeUtils.getNetworkDateTime(getBaseContext(),"dd-MMM-yyyy HH:mm:ss.SSS");


                                      try
                                {

                                    uploadImage(image_str,currentImageFileName,"NA",stID,onlyDate,routeID);

                                }catch(Exception e)
                                {
                                    IMGsyOK = 1;

                                }
                            }


                        }


                    }
                }


            }
            catch(Exception e)
            {
                IMGsyOK = 1;

            }


            return null;
        }

        @Override
        protected void onCancelled()
        {
            Log.i("SvcMgr", "Service Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
          /*  if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }*/

            if(IMGsyOK == 1)
            {
                IMGsyOK = 0;

            }
            else
            {

                db.updateImageRecordsSyncd();



                try
                {
                    task2 = new SyncXMLfileData();
                    AppUtils.executeAsyncTask(task2);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

    public String BitMapToString(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();

        if(w1 > 768 || h1 > 1024)
        {
            bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);
        }
        else
        {

            bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
        }

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }


    public String compressImage(String imageUri) {
        String filePath = imageUri;//getRealPathFromURI(imageUri);
        Bitmap scaledBitmap=null;
        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 1024.0f;
        float maxWidth = 768.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[768*1024];

           try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);

              } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }

        //Bitmap scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        //bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //scaledBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        bmp.compress(Bitmap.CompressFormat.JPEG,80, baos);

        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;


    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String BitMapToStringDSSelfiAndSignature(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();

        if(w1 > 768 || h1 > 1024){
            bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);

        }


        else {

            bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
        }

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    private class SyncXMLfileData extends AsyncTask<Void, Void, Integer>
    {


/*
        public SyncXMLfileData(SyncMaster activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }*/

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists())
            {
                OrderXMLFolder.mkdirs();
            }


        }

        @Override
        protected Integer doInBackground(Void... params)
        {



            try {

                String xmlfileNames = db.fnGetXMLFile("3");

                int index=0;
                if(!xmlfileNames.equals("")) {
                    String[] xmlfileArray = xmlfileNames.split(Pattern.quote("^"));
                    for (int i = 0; i < xmlfileArray.length; i++) {
                        System.out.println("index" + index);
                        xmlFileName = xmlfileArray[i];


//
                        //String newzipfile = Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".zip";
                        //  xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".xml";

                        String newzipfile = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip";
                        xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml";


                        try {
                            zip(xmlForWeb, newzipfile);
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        File file2send = new File(newzipfile);

                        // It is for Testing Purpose
                        //String urlString = "http://115.124.126.184/ReadXML_PragaSFAForTest/default.aspx?CLIENTFILENAME=" + zipFileName;

                        // It is for Live Purpose
                        // String urlString = "http://115.124.126.184/ReadXML_PragaSFA/default.aspx?CLIENTFILENAME=" + zipFileName;


                        String urlString="";
                        if(xmlFileName.contains(".xml"))
                        {
                            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName;

                        }
                        else
                        {
                            urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName+".xml";

                        }

                        try {


                            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                    .connectTimeout(2, TimeUnit.MINUTES)
                                    .readTimeout(60, TimeUnit.SECONDS)
                                    .writeTimeout(60, TimeUnit.SECONDS)
                                    .build();
                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("zipFileName", xmlFileName,RequestBody.create(MediaType.parse("zip"),file2send))
                                     .build();

                            Request request = new Request.Builder()
                                    .url(urlString)
                                    .post(requestBody)
                                    .build();

                            Response response = okHttpClient.newCall(request).execute();




                            if (response!=null && response.code() == 200 && response.body().string().equalsIgnoreCase("SUCCESS")) {

                                db.upDateTblXmlFile(xmlFileName);
                                db.deleteXmlTable("4");

                                deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml");
                                deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip");
                                System.out.println("ShivamDELETE" + xmlFileName);
                             index++;}
                        }  catch (Exception e) {

                            //   pDialogGetStores.dismiss();

                        }

                        // pDialogGetInvoiceForDay.dismiss();


                    }
                }
                else
                {
                    serverResponseCode=200;
                }
            }
            catch (Exception e) {

                e.printStackTrace();
            }
            return serverResponseCode;
        }

        @Override
        protected void onCancelled()
        {
            Log.i("SyncMaster", "Sync Cancelled");
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);

            if(result!=200)
            {

                if(whereTo.contentEquals("11"))
                {
                    // showSyncError();
                }
                else
                {

                        /*   Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
                        startActivity(submitStoreIntent);
                        finish();*/
                    stopSelf();
                }
            }
            else
            {

                db.UpdateStoreVisitWiseTablesAfterSync(4);


            }
        }



    }

    public void deleteViewdXml(String file_dj_path)
    {
        File dir=   Environment.getExternalStorageDirectory();
        File fdelete=new File(dir,file_dj_path);
        // File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
        }


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

    public String convertHttpUrlResponseToString(HttpURLConnection conn){

        String res = "";
        BufferedReader br;
        try {
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            for (String line; (line = br.readLine()) != null; res += line);

            return res;
        }catch (Exception e){
            e.printStackTrace();
        }

return res;
    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length�..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0)
        {
        }
        else
        {
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer�..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream�..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string�..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }

    public void zip(String[] files, String zipFile) throws IOException
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

    private void uploadImage(String image_str,String currentImageFileName,String comment,String stID,String onlyDate,String routeID){

        try {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", image_str)
                .addFormDataPart("FileName",currentImageFileName)
                .addFormDataPart("comment","NA")
                .addFormDataPart("storeID",stID)
                .addFormDataPart("date",onlyDate)
                .addFormDataPart("routeID",routeID)
                .build();

        Request request = new Request.Builder()
                .url(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();


        //  if(serverResponseCode == 200)
        if(response!=null && response.code()==200 && response.body().string().equalsIgnoreCase("SUCCESS"))
        {

            db.updateImageRecordsSyncdForReturn(UploadingImageName.toString().trim());
            // String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
            String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

            File fdelete = new File(file_dj_path);
            if (fdelete.exists())
            {
                if (fdelete.delete())
                {
                    Log.e("-->", "file Deleted :" + file_dj_path);
                    callBroadCast();
                }
                else
                {
                    Log.e("-->", "file not Deleted :" + file_dj_path);
                }
            }
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
