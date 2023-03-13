package com.astix.allanasosfa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

class ImageUploadAsyncTask extends AsyncTask<Void,Void,Boolean>
{
    AppDataSource dbengine;

    InputStream inputStream;
   // public String imei=null;
   // public String fDate=null;
    private TaskListner mTaskListner=null;
   // public boolean serviceException=false;

    private Context mContext;

    public ImageUploadAsyncTask(Context context)
    {
        mContext = context;
        dbengine = AppDataSource.getInstance(mContext);
        //this.imei=imei;
       // this.fDate=fDate;
        mTaskListner=(TaskListner)context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... args)
    {

        boolean isErrorExist=false;


        try
        {

            ArrayList<String> listImageDetails=new ArrayList<String>();

            listImageDetails=dbengine.getImageDetails(5);

            if(listImageDetails!=null && listImageDetails.size()>0)
            {
                for(String imageDetail:listImageDetails)
                {
                    String tempIdImage=imageDetail.split(Pattern.quote("^"))[0].toString();
                    String imagePath=imageDetail.split(Pattern.quote("^"))[1].toString();
                    String imageName=imageDetail.split(Pattern.quote("^"))[2].toString();
                    String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+imageName;
                    File fImage = new File(file_dj_path);
                    if (fImage.exists())
                    {
                        uploadImage(imagePath, imageName, tempIdImage);
                    }

                }
            }


        }
        catch (Exception e)
        {
            isErrorExist=true;
        }

        finally
        {
            Log.i("SvcMgr", "Service Execution Completed...");
        }

        return isErrorExist;

    }


    @Override
    protected void onPostExecute(Boolean isErrorExist)
    {
        if(checkImagesInFolder()>0)
        {
            new ImageUploadFromFolderAsyncTask(mContext).execute();
        }
       else if(dbengine.fnCheckForPendingXMLFilesInTable()==1)
        {
            new XMLFileUploadAsyncTask(mContext).execute();
        }
       else if(checkXMLFilesInFolder()>0)
        {
            new XMLFileUploadFromFolderAsyncTask(mContext).execute();
        }
        else
        {
            mTaskListner.onTaskFinish(isErrorExist,1);
        }
    }

    public int checkImagesInFolder()
    {
        int totalFiles=0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

        String [] AllFilesName= checkNumberOfFiles(del);

        if(AllFilesName!=null && AllFilesName.length>0)
        {
            totalFiles=AllFilesName.length;
        }
        return totalFiles;
    }

    public int checkXMLFilesInFolder()
    {
        int totalFiles=0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        String [] AllFilesName= checkNumberOfFiles(del);

        if(AllFilesName!=null && AllFilesName.length>0)
        {
            totalFiles=AllFilesName.length;
        }
        return totalFiles;
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


    public void uploadImage(String sourceFileUri,String fileName,String tempIdImage) throws IOException
    {
        BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
        IMGoptions01.inDither=false;
        IMGoptions01.inPurgeable=true;
        IMGoptions01.inInputShareable=true;
        IMGoptions01.inTempStorage = new byte[16*1024];

        Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(sourceFileUri).getPath(),IMGoptions01);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream); //compress to which format you want.

        String image_str = BitMapToString(bitmap);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

        stream.flush();
        stream.close();

        bitmap.recycle();
        nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
        nameValuePairs.add(new BasicNameValuePair("TempID", tempIdImage));
        try
        {

            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);


            HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout + 2000);


            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppost = new HttpPost(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath );



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

            String the_string_response = convertResponseToString(response);
            if(the_string_response.equals("SUCCESS"))
            {
                dbengine.updateSSttImage(fileName, 4);
                dbengine.fndeleteSbumittedStoreImagesOfSotre(4);

                String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+fileName;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }

            }

        }catch(Exception e)
        {

            System.out.println(e);
            //	IMGsyOK = 1;

        }
    }
    public void callBroadCast()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(mContext, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {

            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }
    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
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
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
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
}


