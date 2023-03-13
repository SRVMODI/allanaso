package com.astix.allanasosfa;

import android.os.AsyncTask;
import android.os.Environment;

import com.astix.Common.CommonInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

class DownloadImage extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        downLoadingSelfieImage(strings[0], strings[1]);
        return null;
    }


    public void downLoadingSelfieImage(String SelfieNameURL, String SelfieName) {
        String URL_String = SelfieNameURL;
        String Video_Name = SelfieName;

        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(URL_String)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            String PATH = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolderServer + "/";

            File file2 = new File(PATH + Video_Name);
            if (file2.exists()) {
                file2.delete();
            }

            File file1 = new File(PATH);
            if (!file1.exists()) {
                file1.mkdirs();
            }


            File file = new File(file1, Video_Name);

            if (response != null && response.body() != null) {

                InputStream inputStream = response.body().byteStream();

                OutputStream output = new FileOutputStream(file);
                try {
                    byte[] buffer = new byte[2 * 1024]; // or other buffer size
                    int read;

                    while ((read = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }

                    output.flush();
                } finally {
                    output.close();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
