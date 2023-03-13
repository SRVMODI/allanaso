package com.astix.allanasosfa.location;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.utils.AppUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Shivam on 1/4/2018.
 */

public class LocationRetreivingGlobal implements android.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {


    private int attemptCount = 0;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 0;

    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    public ProgressDialog pDialog2STANDBY;
    Activity context;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 5;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    public LocationManager locationManager;
    public Location location;

    private final long startTime = 10000;
    private final long interval = 1000;
    public CoundownClass countDownTimer;
    public boolean isGPSEnabled = false;
    public boolean isNetworkEnabled = false;

    public String FusedLocationLatitude = "0";
    public String FusedLocationLongitude = "0";
    public String FusedLocationProvider = "";
    public String FusedLocationAccuracy = "0";

    public String GPSLocationLatitude = "0";
    public String GPSLocationLongitude = "0";
    public String GPSLocationProvider = "";
    public String GPSLocationAccuracy = "0";

    public String NetworkLocationLatitude = "0";
    public String NetworkLocationLongitude = "0";
    public String NetworkLocationProvider = "";
    public String NetworkLocationAccuracy = "0";
    public String AllProvidersLocation = "";

    public String finalAccuracy = "0";
    public String fnAccurateProvider = "";
    private String fnAddress = "";
    public String fnLati = "0";
    public String fnLongi = "0";
    public Double fnAccuracy = 0.0;
    public String FusedLocationLatitudeWithFirstAttempt = "0";
    public String FusedLocationLongitudeWithFirstAttempt = "0";
    public String FusedLocationAccuracyWithFirstAttempt = "0";
    public int flgLocationServicesOnOff = 0;
    public int flgGPSOnOff = 0;
    public int flgNetworkOnOff = 0;
    public int flgFusedOnOff = 0;
    public int flgInternetOnOffWhileLocationTracking = 0;
    public int flgRestart = 0;
    public String address="NA", pincode="0", city="NA", state="NA";
    Location mCurrentLocation;
    String mLastUpdateTime;
    Activity activity;
    int checkAccuracy = 0;
    boolean fetchAdressFlag = true;// if true means fetch address ,if false dont fetch address

    boolean isLocationFetched = false;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;


    private boolean isShowDialognew;


    public void locationRetrievingAndDistanceCalculating(Activity context, boolean isShowDialog, boolean fetchAdressFlag, int checkAccuracy, int flgtxtMsgType) {
        activity = (Activity) context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.context = context;
        this.fetchAdressFlag = fetchAdressFlag;
        this.checkAccuracy = checkAccuracy;

        if (pDialog2STANDBY != null) {
            if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
                pDialog2STANDBY.dismiss();
            }
        }
        this.isShowDialognew = isShowDialog;



        if (isShowDialognew) {
            if (flgtxtMsgType == 1) {
                pDialog2STANDBY = ProgressDialog.show(context, context.getText(R.string.PleaseWaitMsg), context.getText(R.string.RetrivingDataMsg), true);
            } else if (flgtxtMsgType == 4) {
                pDialog2STANDBY = ProgressDialog.show(context, context.getText(R.string.PleaseWaitMsg), context.getText(R.string.submittingdetails), true);
            } else {
                pDialog2STANDBY = ProgressDialog.show(context, context.getText(R.string.genTermPleaseWaitNew), context.getText(R.string.rtrvng_loc), true);
            }
            pDialog2STANDBY.setIndeterminate(true);

            pDialog2STANDBY.setCancelable(false);
            pDialog2STANDBY.show();
        }


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                System.out.println("0000 location result is " + locationResult);
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        mCurrentLocation = location;
                        System.out.println("0000 location provider is " + location.getProvider());
                        System.out.println("0000 location accuracy is " + location.getAccuracy());

                        FusedLocationLatitude = String.valueOf(location.getLatitude());
                        FusedLocationLongitude = String.valueOf(location.getLongitude());
                        FusedLocationProvider = location.getProvider();
                        FusedLocationAccuracy = "" + location.getAccuracy();
                        attemptCount++;

                        if (isShowDialognew || location.getAccuracy()<40) {
                            stopLocationUpdates();
                         /*   mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                            updateUI();
                            LocationSyncSuccess locationSyncSuccess = new LocationSyncSuccess();
                            AppUtils.executeAsyncTask(locationSyncSuccess);*/

                        }
                        if (attemptCount < 2) {
                            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                            updateUI();
                            LocationSyncSuccess locationSyncSuccess = new LocationSyncSuccess();

                            AppUtils.executeAsyncTask(locationSyncSuccess);
                        }


                        if (attemptCount >1) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }

                    }
                }
            }


        };

        fetchLocationFromGpsAndNetwork();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(LocationRetreivingGlobal.this)
                    .addOnConnectionFailedListener(LocationRetreivingGlobal.this)
                    .build();
            mGoogleApiClient.connect();


        }


        if (isShowDialognew) {
            countDownTimer = new CoundownClass(startTime, interval);
            countDownTimer.start();
        } else {
            countDownTimer = new CoundownClass(1000, 10000);
            countDownTimer.start();
        }


    }

    public static void checkGpsState(final Activity context) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(context);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("GPS","Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS","Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e("GPS","checkLocationSettings -> onCanceled");
                    }
                });

    }

    private boolean checkGPSAndNetwork() {
        boolean isGPSok = false;
        boolean isNWok = false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        boolean isWorking = true;

        if (!isGPSok) {
            isGPSok = false;

        }

        if (!isNWok) {
            isNWok = false;
        }

        if (!isGPSok && !isNWok) {
            isGPSok = false;
            isNWok = false;
            isWorking = false;

        }
        return isWorking;
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, activity, 0).show();
            return false;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    protected void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
        if (locationManager != null)
            locationManager.removeUpdates(this);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("LocationRetrievingGlbl", "0000 On Connected");
        startLocationUpdates();

    }

    protected void startLocationUpdates() {
        Log.d("LocationRetrievingGlbl", "0000 Location Updates Started");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location args0) {

        if (args0 != null)
            return;

        //attemptCount++;
        mCurrentLocation = args0;
        if (isShowDialognew){
            if (pDialog2STANDBY != null) {
                if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
                    pDialog2STANDBY.dismiss();
                }

            }
        }
      /*  if (attemptCount < 2) {
            updateUI();
            LocationSyncSuccess locationSyncSuccess = new LocationSyncSuccess();
            AppUtils.executeAsyncTask(locationSyncSuccess);
        }*/
        if (isShowDialognew || location.getAccuracy()<40) {
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            stopLocationUpdates();
            attemptCount=4;
           /* attemptCount++;
            mCurrentLocation = args0;

            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateUI();
                LocationSyncSuccess locationSyncSuccess = new LocationSyncSuccess();
                AppUtils.executeAsyncTask(locationSyncSuccess);*/
        }




    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void updateUI() {
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());

            FusedLocationLatitude = lat;
            FusedLocationLongitude = lng;
            FusedLocationProvider = mCurrentLocation.getProvider();
            FusedLocationAccuracy = "" + mCurrentLocation.getAccuracy();

        } else {

        }
    }


    private void fetchLocationFromGpsAndNetwork() {
        if (locationManager == null)
            return;
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, LocationRetreivingGlobal.this);
        }

        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, LocationRetreivingGlobal.this);
        }
    }

    public class CoundownClass extends CountDownTimer {

        public CoundownClass(long startTime, long interval) {
            super(startTime, interval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {
            System.out.println("Shivam" + FusedLocationAccuracy);

            if (!isLocationFetched)
                fetchLocationFromGpsAndNetwork();

        }

        @Override
        public void onFinish() {
            System.out.println("0000 On Timer Timer Called");
//            stopAllProcesses();

            LocationSyncSuccess locationSyncSuccess = new LocationSyncSuccess();
            AppUtils.executeAsyncTask(locationSyncSuccess);
        }


    }


    private class LocationSyncSuccess extends AsyncTask<String, String, String> {




        @Override
        protected String doInBackground(String... strings) {

            onLocationSuccess();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            /*if(address==null)
                address="NA";

            if(pincode==null)
                pincode="";

            if(city==null)
                city="NA";

           if(state==null)
               state="NA";


*/
            if(attemptCount==2)
            {
                attemptCount=3;
                LocationInterface intrfc = (LocationInterface) context;
                if (intrfc != null)
                    intrfc.onLocationRetrieved(fnLati, fnLongi, finalAccuracy, fnAccurateProvider, GPSLocationLatitude, GPSLocationLongitude, GPSLocationAccuracy, NetworkLocationLatitude, NetworkLocationLongitude, NetworkLocationAccuracy, FusedLocationLatitude, FusedLocationLongitude, FusedLocationAccuracy, AllProvidersLocation, GpsAddress, NetwAddress, FusedAddress, FusedLocationLatitudeWithFirstAttempt, FusedLocationLongitudeWithFirstAttempt, FusedLocationAccuracyWithFirstAttempt, flgLocationServicesOnOff, flgGPSOnOff, flgNetworkOnOff, flgFusedOnOff, flgInternetOnOffWhileLocationTracking, address, pincode, city, state, fnAddress);

            }


        }
    }


    String GpsAddress="NA";
    String NetwAddress="NA";
    String FusedAddress="NA";


    public void onLocationSuccess() {
        AllProvidersLocation = "";
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (isGPSEnabled) {

//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location nwLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(nwLocation!=null){
                double lattitude=nwLocation.getLatitude();
                double longitude=nwLocation.getLongitude();
                double accuracy= nwLocation.getAccuracy();
                GPSLocationLatitude=""+lattitude;
                GPSLocationLongitude=""+longitude;
                if(isOnline() && (fetchAdressFlag))
                {
                    GpsAddress=getAddressOfProviders(GPSLocationLatitude, GPSLocationLongitude);
                }
                else
                {
                    GpsAddress="NA";
                }
                GPSLocationLatitude=""+lattitude;
                GPSLocationLongitude=""+longitude;
                GPSLocationProvider="GPS";
                GPSLocationAccuracy=""+accuracy;
                AllProvidersLocation="GPS=Lat:"+lattitude+"Long:"+longitude+"Acc:"+accuracy;

            }
        }


        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isNetworkEnabled) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (gpsLocation != null) {
                double lattitude1 = gpsLocation.getLatitude();
                double longitude1 = gpsLocation.getLongitude();
                double accuracy1 = gpsLocation.getAccuracy();

                NetworkLocationLatitude = "" + lattitude1;
                NetworkLocationLongitude = "" + longitude1;
                if (isOnline() && (fetchAdressFlag)) {
                    NetwAddress = getAddressOfProviders(NetworkLocationLatitude, NetworkLocationLongitude);
                } else {
                    NetwAddress = "NA";
                }

                NetworkLocationProvider = "Network";
                NetworkLocationAccuracy = "" + accuracy1;

                if (!AllProvidersLocation.equals("")) {
                    AllProvidersLocation = AllProvidersLocation + "$Network=Lat:" + lattitude1 + "Long:" + longitude1 + "Acc:" + accuracy1;
                } else {
                    AllProvidersLocation = "Network=Lat:" + lattitude1 + "Long:" + longitude1 + "Acc:" + accuracy1;
                }
            }
									 /* TextView accurcy=(TextView) findViewById(R.id.Acuracy);
									  accurcy.setText("GPS:"+GPSLocationAccuracy+"\n"+"NETWORK"+NetworkLocationAccuracy+"\n"+"FUSED"+fusedData);*/
        }


        if(!FusedLocationProvider.equals(""))
        {
            fnAccurateProvider="Fused";
            fnLati=FusedLocationLatitude;
            fnLongi=FusedLocationLongitude;
            fnAccuracy= Double.parseDouble(FusedLocationAccuracy);

            FusedLocationLatitudeWithFirstAttempt=FusedLocationLatitude;
            FusedLocationLongitudeWithFirstAttempt=FusedLocationLongitude;
            FusedLocationAccuracyWithFirstAttempt=FusedLocationAccuracy;

            if(isOnline() && (fetchAdressFlag))
            {
                FusedAddress=getAddressOfProviders(FusedLocationLatitude, FusedLocationLongitude);
            }
            else
            {
                FusedAddress="NA";
            }

            if(!AllProvidersLocation.equals(""))
            {
                AllProvidersLocation=AllProvidersLocation+"$Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
            }
            else
            {
                AllProvidersLocation="Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
            }
        }



        fnAccurateProvider="";
        fnLati="0";
        fnLongi="0";
        fnAccuracy=0.0;
        fnAddress ="";





        if(!fnAccurateProvider.equals(""))
        {
            if(!GPSLocationProvider.equals(""))
            {
                if(Double.parseDouble(GPSLocationAccuracy)<fnAccuracy)
                {
                    fnAccurateProvider="Gps";
                    fnLati=GPSLocationLatitude;
                    fnLongi=GPSLocationLongitude;
                    fnAddress =GpsAddress;
                    fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
                }
            }
        }
        else
        {
            if(!GPSLocationProvider.equals(""))
            {
                fnAccurateProvider="Gps";
                fnLati=GPSLocationLatitude;
                fnLongi=GPSLocationLongitude;
                fnAddress =GpsAddress;
                fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
            }
        }

        if(!fnAccurateProvider.equals(""))
        {
            if(!NetworkLocationProvider.equals(""))
            {
                if(Double.parseDouble(NetworkLocationAccuracy)<fnAccuracy)
                {
                    fnAccurateProvider="Network";
                    fnLati=NetworkLocationLatitude;
                    fnLongi=NetworkLocationLongitude;
                    fnAddress =NetwAddress;
                    fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
                }
            }
        }
        else
        {
            if(!NetworkLocationProvider.equals(""))
            {
                fnAccurateProvider="Network";
                fnLati=NetworkLocationLatitude;
                fnLongi=NetworkLocationLongitude;
                fnAddress = NetwAddress;
                fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
            }
        }

        if(!FusedLocationProvider.equals(""))
        {
            fnAccurateProvider="Fused";
            fnLati=FusedLocationLatitude;
            fnLongi=FusedLocationLongitude;
            fnAddress = FusedAddress;
            fnAccuracy= Double.parseDouble(FusedLocationAccuracy);
        }
        // fnAccurateProvider="";

        checkHighAccuracyLocationMode(context);

        Log.d("LocationRetrievalGlobal","0000 Provider "+fnAccurateProvider);

        if(fnAccurateProvider.equals(""))
        {

            if(isShowDialognew) {
                if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
                    pDialog2STANDBY.dismiss();
                }
            }
            fnLati="0";
            fnLongi="0";
            //  String.valueOf(fnAccuracy)="NA";
            finalAccuracy="0";
            fnAccurateProvider="NA";
            AllProvidersLocation="NA";
            GpsAddress="NA";
            NetwAddress="NA";
            FusedAddress="NA";
            FusedLocationLatitudeWithFirstAttempt="0";
            FusedLocationLongitudeWithFirstAttempt="0";
            FusedLocationAccuracyWithFirstAttempt="0";

            fnCreateLastKnownGPSLoction(fnLati,fnLongi,finalAccuracy);

        }
        else
        {
            if(isOnline() && (fetchAdressFlag))
            {
                getAddressForDynamic(fnLati,fnLongi);
            }
            if(isShowDialognew) {
                if (pDialog2STANDBY != null && pDialog2STANDBY.isShowing()) {
                    pDialog2STANDBY.dismiss();
                }
            }

            finalAccuracy=  String.valueOf(fnAccuracy);

        }
        if(attemptCount==0 || attemptCount==1)
        attemptCount++;


    }

    public  boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        FULLADDRESS2=  FULLADDRESS2.append("");
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context,  Locale.ENGLISH);



        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

            if (addresses == null || addresses.size()  == 0 || addresses.get(0).getAddressLine(0)==null)
            {
                FULLADDRESS2=  FULLADDRESS2.append("NA");
            }
            else
            {
                FULLADDRESS2 =FULLADDRESS2.append(addresses.get(0).getAddressLine(0));
            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Crashlytics.logException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Crashlytics.logException(e);
        } // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        return FULLADDRESS2.toString();

    }
    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOff=0;
        flgGPSOnOff=0;
        flgNetworkOnOff=0;
        flgFusedOnOff=0;
        flgInternetOnOffWhileLocationTracking=0;

        if(isGooglePlayServicesAvailable())
        {
            flgFusedOnOff=1;
        }
        if(isOnline())
        {
            flgInternetOnOffWhileLocationTracking=1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=0;
                    flgNetworkOnOff=1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                Crashlytics.logException(e);
            }
        }
        else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;

                flgLocationServicesOnOff = 0;
                flgGPSOnOff = 0;
                flgNetworkOnOff = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOff = 1;
                flgGPSOnOff = 1;
                flgNetworkOnOff = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

    }
    public String getAddressForDynamic(String latti,String longi){


        String areaToMerge="NA";
        Address addressTemp=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

           /* AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];*/
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0){
                if(addresses.get(0).getAddressLine(1)!=null){
                    addr=addresses.get(0).getAddressLine(1);

                }

                if(addresses.get(0).getLocality()!=null){
                    city=addresses.get(0).getLocality();

                }

                if(addresses.get(0).getAdminArea()!=null){
                    state=addresses.get(0).getAdminArea();

                }


                for(int i=0 ;i<addresses.size();i++){
                    addressTemp = addresses.get(i);
                    if(addressTemp.getPostalCode()!=null){
                        zipcode=addressTemp.getPostalCode();

                        break;
                    }
                }

                if(addresses.get(0).getAddressLine(0)!=null && addr.equals("NA")){
                    String countryname="NA";
                    if(addresses.get(0).getCountryName()!=null){
                        countryname=addresses.get(0).getCountryName();
                    }

                    addr=  getAddressNewWay(addresses.get(0).getAddressLine(0),city,state,zipcode,countryname);

                }
                if(city!=null)
                    this.city=city;
                else
                    this.city="NA";
                if(state!=null)
                    this.state=state;
                else
                    this.state="NA";
                if(zipcode!=null)
                    this.pincode=zipcode;
                else
                    this.pincode="0";
                if(this.address!=null)
                    this.address=addr;
                else
                    this.address="NA";
              /*  NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(null != recFragment)
                {
                    recFragment.setFreshAddress();
                }*/
            }
            else{FULLADDRESS3.append("NA");}
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Crashlytics.logException(e);
            e.printStackTrace();
        }
        finally{
            return fullAddress=addr+"^"+zipcode+"^"+city+"^"+state;
        }
    }
    public String getAddressNewWay(String ZeroIndexAddress,String city,String State,String pincode,String country){
        String editedAddress=ZeroIndexAddress;
        if(editedAddress.contains(city)){
            editedAddress= editedAddress.replace(city,"");

        }
        if(editedAddress.contains(State)){
            editedAddress=editedAddress.replace(State,"");

        }
        if(editedAddress.contains(pincode)){
            editedAddress= editedAddress.replace(pincode,"");

        }
        if(editedAddress.contains(country)){
            editedAddress=editedAddress.replace(country,"");

        }
        if(editedAddress.contains(",")){
            editedAddress=editedAddress.replace(","," ");

        }
        return editedAddress;
    }
    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
    {

        try {

            JSONArray jArray=new JSONArray();
            JSONObject jsonObjMain=new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put( "chekLastGPSLat",chekLastGPSLat);
            jOnew.put( "chekLastGPSLong",chekLastGPSLong);
            jOnew.put( "chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Crashlytics.logException(e);
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("GPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Crashlytics.logException(e);
        }
        finally{

        }
    }



    /**
     * Uses a {@link LocationSettingsRequest.Builder} to build
     * a {@link LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} method, with the results provided through a {@code PendingResult}.
     */
    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                    Crashlytics.logException(e);
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }

    }


    public Location getLatestLocation(){
        return mCurrentLocation;
    }


    public void stopAllProcesses(){
        if(countDownTimer!=null)
            countDownTimer.cancel();
        try {
            if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
            {
                stopLocationUpdates();
                mGoogleApiClient.unregisterConnectionCallbacks(this);
                mGoogleApiClient.disconnect();
            }
            locationManager.removeUpdates(this);

        }
        catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
            mGoogleApiClient=null;
            locationManager=null;
        }
    }


}