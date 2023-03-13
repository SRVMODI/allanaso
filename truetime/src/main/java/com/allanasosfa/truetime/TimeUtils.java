package com.allanasosfa.truetime;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.supermax.truetime.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

    public static String DATE_TIME_FORMAT="dd-MMM-yyyy HH:mm:ss";
    public static String DATE_FORMAT="dd-MMM-yyyy";
    public static String TIME_FORMAT="HH:mm:ss";
    public static String TIME_FORMAT1="dd.MM.yyyy.HH.mm.ss";
    public static String TAG="TimeUtils";
    public static int REQUEST_CODE =901;

    public static String getCurrentDeviceTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String currentTime = df.format(calendar.getTime());
        return currentTime;
    }

    public static String getCurrentDeviceDate(String format) {
        Date pdaDate = new Date();
        SimpleDateFormat sdfPDaDate = new SimpleDateFormat(format, Locale.ENGLISH);
        String fDatePda = sdfPDaDate.format(pdaDate).trim();
        return fDatePda;
    }

    public static String getDeviceBeforeAfterDate(String format,int days) {
        Date pdaDate = new Date();
         Calendar calendar=Calendar.getInstance();
        calendar.setTime(pdaDate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        pdaDate=calendar.getTime();
        SimpleDateFormat sdfPDaDate = new SimpleDateFormat(format, Locale.ENGLISH);
        String fDatePda = sdfPDaDate.format(pdaDate).trim();

        return fDatePda;
    }

    public static String getCurrentDeviceDateTime(String format){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDateTime = sdf.format(c.getTime());
        return strDateTime;
    }

    public static String getNetworkBeforeAfterDate(Context context,String dateFormat,int days) {
        if (!TrueTime.isInitialized() || isTimeAutomatic(context)) {
            return getDeviceBeforeAfterDate(dateFormat,days);
        }
        Date trueTime = TrueTime.now();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(trueTime);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        trueTime=calendar.getTime();
        return  _formatDate(trueTime, dateFormat, TimeZone.getTimeZone("GMT+5:30"));
    }

    public static String getNetworkDate(Context context,String dateFormat) {
        if (!TrueTime.isInitialized() || isTimeAutomatic(context)) {
            return getCurrentDeviceDate(dateFormat);
        }
        Date trueTime = TrueTime.now();
        return  _formatDate(trueTime, dateFormat, TimeZone.getTimeZone("GMT+5:30"));
    }

    public static String getNetworkTime(Context context,String timeFormat) {
        if (!TrueTime.isInitialized() || isTimeAutomatic(context)) {
            return getCurrentDeviceTime(timeFormat);
        }
        Date trueTime = TrueTime.now();
        return  _formatDate(trueTime, timeFormat, TimeZone.getTimeZone("GMT+5:30"));
    }

    public static int isNetworkTimeTaken(){
        if(!TrueTime.isInitialized())
            return 0;
        else
            return 1;
    }



    public static String getNetworkDateTime(Context context,String dateFormat) {
        if (!TrueTime.isInitialized() || isTimeAutomatic(context)) {
            return getCurrentDeviceDateTime(dateFormat);
        }
        Date trueTime = TrueTime.now();
        return  _formatDate(trueTime, dateFormat, TimeZone.getTimeZone("GMT+5:30"));
    }

    public static String _formatDate(Date date, String pattern, TimeZone timeZone) {
        DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        format.setTimeZone(timeZone);
        return format.format(date.getTime());
    }


    public static String getTimeDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return String.valueOf(elapsedDays).replaceAll("-","")+"-"+String.valueOf(elapsedHours).replaceAll("-","")+"-"+String.valueOf(elapsedMinutes).replaceAll("-","");
    }

    public static void checkTimeDifference(Activity activity,String format,boolean isMandatory){

        if(format==null)
            format=DATE_TIME_FORMAT;
        String deviceTime=getCurrentDeviceDateTime(format);
        String networkTime=getNetworkDateTime(activity,DATE_TIME_FORMAT);

        Date deviceDateObj=new Date(deviceTime);
        Date networkDateObj=new Date(networkTime);

        Log.d(TAG,deviceTime+"--"+networkTime);
        String timeDifference=getTimeDifference(deviceDateObj,networkDateObj);
        Log.d(TAG,"Time Difference is--"+timeDifference);

        String[] differenceArray=timeDifference.split("-");
        int days=Integer.parseInt(differenceArray[0]);
        int hours=Integer.parseInt(differenceArray[1]);
        int minutes=Integer.parseInt(differenceArray[2]);

        if(days>0 || hours>0 || minutes>5){

            if(isMandatory){
                showMandatoryTimeChangeAlertDialog(activity,activity.getResources().getString(R.string.device_time_incorrect));
            }else
                showOptionalTimeChangeAlertDialog(activity,activity.getResources().getString(R.string.device_time_incorrect));
        }



    }

    public static int calculateDaysDifferenceBetweenTwoDates(String aDate,String bDate){

        Date aDateObj=new Date(aDate);
        Date bDateObj=new Date(bDate);

        String timeDifference=getTimeDifference(aDateObj,bDateObj);
        Log.d(TAG,"Time Difference is--"+timeDifference);

        String[] differenceArray=timeDifference.split("-");
        int days=Integer.parseInt(differenceArray[0]);
       return days;
    }



    public static void showMandatoryTimeChangeAlertDialog(final Activity context, String alertdata) {

        if(context==null)
            return;
        try {
            String DeviceTime="\n\nDevice Time :: "+getCurrentDeviceDateTime(DATE_TIME_FORMAT)+"\n";
            String networkTime="Network Time :: "+getNetworkDateTime(context,DATE_TIME_FORMAT)+"\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name)
                    .setMessage(alertdata+DeviceTime+networkTime)
                    .setCancelable(false)
                    .setPositiveButton(R.string.oks, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), REQUEST_CODE);
                                }
                            }
                    );
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void showOptionalTimeChangeAlertDialog(final Activity context, String alertdata) {

        if(context==null)
            return;
        try {
            String DeviceTime="\n\nDevice Time :: "+getCurrentDeviceDateTime(DATE_TIME_FORMAT)+"\n";
            String networkTime="Network Time :: "+getNetworkDateTime(context,DATE_TIME_FORMAT)+"\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.app_name)
                    .setMessage(alertdata+DeviceTime+networkTime)
                    .setCancelable(false)
                    .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), REQUEST_CODE);
                                }
                            }
                    );

            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getDateFromDateTime(String date){
        String newFormat = "dd-MMM-yyyy";

        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_TIME_FORMAT,Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat,Locale.getDefault());


        String formattedDate = date;
        try {
            formattedDate = sdf2.format(sdf1.parse(date));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formattedDate;
    }


    public static boolean isTimeAutomatic(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }


}
