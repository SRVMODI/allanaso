package com.astix.allanasosfa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.allanasosfa.truetime.TimeUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class InvoiceSyncMaster extends BaseActivity
{


	public String routeID="0";
	private File[] listFile;
	public  File fileintial;
	public String fnameIMG;
	private String FilePathStrings;
	public String UploadingImageName;
	SyncImageData task1;
	public String imei;
	public String StoreName;
	public String currSysDate;
	public String pickerDate;

	public String[] xmlForWeb = new String[1];

	//public TextView chkString;
	HttpEntity resEntity;
	private InvoiceSyncMaster _activity;

	private static final String DATASUBDIRECTORY = "NMPphotos";


	public int syncFLAG = 0;
	public int res_code;
	public String zipFileName;
	ProgressDialog PDpicTasker;
	public String whereTo;
	public int IMGsyOK = 0;
	public static String activityFrom="";
	//ProgressDialog pDialog2;
	InputStream inputStream;
	ProgressDialog pDialogGetStores;
	ArrayList mSelectedItems = new ArrayList();
	ArrayList mSelectedItemsConfornInvoiceOrders= new ArrayList();

	ArrayList<String> stIDs;
	ArrayList<String> stNames;

	public void showSyncError()
	{
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncError.setTitle("Sync Error!");
		alertDialogSyncError.setCancelable(false);
		/*alertDialogSyncError
				.setMessage("Sync was not successful! Please try again.");*/
		alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsg));
		alertDialogSyncError.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {


						dialog.dismiss();
						Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
						submitStoreIntent.putExtra("imei", imei);
						submitStoreIntent.putExtra("currSysDate", currSysDate);
						submitStoreIntent.putExtra("pickerDate", pickerDate);
						submitStoreIntent.putExtra("activityFrom", activityFrom);
						submitStoreIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
						startActivity(submitStoreIntent);
						finish();
						//SyncMaster.this.finish();
					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncErrorStart() {
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncError.setTitle("Sync Error!");
		alertDialogSyncError.setCancelable(false);
		alertDialogSyncError.setMessage("Sync Error! \n\n Please check your Internet Connectivity & Try Again!");
		alertDialogSyncError.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {


						dialog.dismiss();

						/*destroyNcleanup(1);
						imgs = null;
						uComments.clear();*/

						//finish();
					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncSuccessStart() {
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncOK.setTitle("Information");
		alertDialogSyncOK.setCancelable(false);
		alertDialogSyncOK.setMessage(getText(R.string.syncAlertInvoiceOKMsg));
		alertDialogSyncOK.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

						//db.open();
						//System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
					/*if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
					{
						db.reTruncateRouteTbl();
					}*/


						//db.reCreateDB();
						//db.close();

						Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
						submitStoreIntent.putExtra("imei", imei);
						submitStoreIntent.putExtra("currSysDate", currSysDate);
						submitStoreIntent.putExtra("pickerDate", pickerDate);
						submitStoreIntent.putExtra("activityFrom", activityFrom);
						submitStoreIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
						startActivity(submitStoreIntent);
						finish();
					/*destroyNcleanup(1);
					imgs = null;
					uComments.clear();*/

						//	finish();


					}
				});
		alertDialogSyncOK.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSyncOK.create();
		alert.show();

	}

	public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{

		String res = "";
		StringBuffer buffer = new StringBuffer();
		inputStream = response.getEntity().getContent();
		int contentLength = (int) response.getEntity().getContentLength(); //getting content length�..
		//System.out.println("contentLength : " + contentLength);
		//Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
		if (contentLength < 0){
		}
		else{
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

	public void sysncStart()
	{
		/*if(isOnline()){*/

		String[] fp2s; // = "/mnt/sdcard/NMPphotos/1539_24-05-2013_1.jpg";

		try {
			//db.open();
			String[] sySTidS = mDataSource.getStoreIDTblSelectedStoreIDinChangeRouteCase();
			//String date= db.GetPickerDate();
			//db.close();
					/*for(int chkCountstore=0; chkCountstore < sySTidS.length;chkCountstore++)
					{
						db.open();
						int syUPlimit = db.getExistingPicNos(sySTidS[chkCountstore].toString());
						String[] syP2F = db.getImgsPath(sySTidS[chkCountstore].toString());
						String[] syC4P = db.getImgsComment(sySTidS[chkCountstore].toString());

						String actRid = db.GetActiveRouteID();
						db.close();

						//fp2s = new String[syUPlimit];
						fp2s = new String[5];

						for(int syCOUNT = 0; syCOUNT < syUPlimit; syCOUNT++){
							//int arrCOUNT = 0;
							fp2s[0] = syP2F[syCOUNT];
							fp2s[1] = syC4P[syCOUNT];
							fp2s[2] = sySTidS[chkCountstore];
							fp2s[3] = date;
							fp2s[4] = actRid;
							//
							new SyncImgTasker().execute(fp2s).get();

							if(IMGsyOK == 1){
								//System.out.println("Breaking here..error occured! XoX");
								break;
							}
						}


					}*/
					/*if(IMGsyOK == 1){
						IMGsyOK = 0;

						showSyncErrorStart();
					}
					else{


					showSyncSuccessStart();
					}*/
			showSyncSuccessStart();



		} catch (Exception e) {
			// TODO Auto-generated catch block
			//db.close();
			e.printStackTrace();
		} /*catch (ExecutionException e) {
					// TODO Auto-generated catch block
					db.close();
					e.printStackTrace();
				}*/

				/*}
			else{

				Toast.makeText(getApplicationContext(), "No Active Internet Connection! \n\nPlease check your Internet Connectivity & Try Again", Toast.LENGTH_SHORT).show();
			}*/
	}


	public static boolean deleteFolderFiles(File path)
	{

	/*  // Check if file is directory/folder
	  if(file.isDirectory())
	  {
	  // Get all files in the folder
	  File[] files=file.listFiles();

	   for(int i=0;i<files.length;i++)
	   {

	   // Delete each file in the folder
	 //  `	(files[i]);
		   file.delete();
	   }

	  // Delete the folder


	  }*/

		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteFolderFiles(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return(path.delete());

	}
	public void showSyncSuccess() {
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(
				InvoiceSyncMaster.this);
		alertDialogSyncOK.setTitle("Information");
		alertDialogSyncOK.setCancelable(false);
		/*alertDialogSyncOK
				.setMessage("Sync was successful!");*/
		alertDialogSyncOK.setMessage(getText(R.string.syncAlertInvoiceOKMsg));
		alertDialogSyncOK.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {


						dialog.dismiss();
						// finishing activity & stepping back
						/*if(whereTo.contentEquals("11"))
						{



								Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, LauncherActivity.class);
								submitStoreIntent.putExtra("imei", imei);
								//submitStoreIntent.putExtra("currSysDate", currSysDate);
								//submitStoreIntent.putExtra("pickerDate", pickerDate);
								startActivity(submitStoreIntent);
								finish();


						}

						else{*/
						Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
						submitStoreIntent.putExtra("imei",imei);
						submitStoreIntent.putExtra("currSysDate",currSysDate);
						submitStoreIntent.putExtra("pickerDate",pickerDate);
						submitStoreIntent.putExtra("activityFrom",activityFrom);
						submitStoreIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
						startActivity(submitStoreIntent);
						finish();
						//}
						//finish();
						//SyncMaster.this.finish();
					}
				});
		alertDialogSyncOK.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSyncOK.create();
		alert.show();
		// alertDialogLowbatt.show();
	}

	//
	public void delXML(String delPath)
	{
		//System.out.println("Deleting..: " + delPath);
		File file = new File(delPath);
		file.delete();
		File file1 = new File(delPath.toString().replace(".xml", ".zip"));
		file1.delete();
	}
	//
	//
	public static void zip(String[] files, String zipFile) throws IOException{
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
	//

	private class SyncPROdata extends AsyncTask<Void, Void, Void> {


		ProgressDialog pDialogGetStores;
		public SyncPROdata(InvoiceSyncMaster activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}


		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			Log.i("SyncMaster","Ready to Sync Data");

	           /* pDialog2 = ProgressDialog.show(_activity,"Please wait...", "Data Sync in Progress...", true);
				pDialog2.setIndeterminate(true);
				//pDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);

				//pDialog2.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		        pDialog2.setCancelable(false);
		        pDialog2.show();*/

	        	/*progressDialog = ProgressDialog.show(_activity, null, null);
	        	progressDialog.setContentView(R.layout.loader);
	            progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	            */

			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage("Submitting Details...");
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();

		}

		@Override
		protected Void doInBackground(Void... params)
		{

			/* try
			 {
				Thread.currentThread().sleep(2000);
			 }
			 catch (InterruptedException e)
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }*/


			// ZIPPING XML FILE HERE
			//String newzipfile = Environment.getExternalStorageDirectory() + "/TJUKSFAInvoicexml/" + zipFileName + ".zip";
			String newzipfile = Environment.getExternalStorageDirectory() + "/"+CommonInfo.InvoiceXMLFolder+"/" + zipFileName + ".zip";

			try {
				zip(xmlForWeb,newzipfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			File file2send = new File(newzipfile);


			// It is for Testing Purpose
			//  String urlString = "http://115.124.126.184/ReadXML_PragaInvoiceTestSFA/Default.aspx?CLIENTFILENAME=" + zipFileName;



			// It is for Live
			// String urlString = "http://115.124.126.184/ReadXMLForParagSFA_Invoicing/Default.aspx?CLIENTFILENAME=" + zipFileName;


			String urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameInvoiceSyncPath + "&CLIENTFILENAME=" + zipFileName;



			HttpParams httpParams = new BasicHttpParams();
			int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);

			// HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);

			HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout+2000);

			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost post = new HttpPost(urlString);

			////System.out.println("SYNC'ing USING METHOD: " + post.getMethod().toString());
			try {

				//publishProgress("Sync in Progress...");

	              /* MultipartEntity rEntity = new MultipartEntity();
	               rEntity.addPart();*/

				//OLD FILE-ENTITY MECHANISM >
				FileEntity fEntity = new FileEntity(file2send, "binary/octet-stream");

				//NEW (v1.5) INPUTSTREAM-ENTITY MECHANISM >
				//InputStreamEntity fEntity = new InputStreamEntity(new FileInputStream(newzipfile), -1);
				// fEntity.setContentType("application/zip");

				post.setEntity(fEntity);
				post.addHeader("zipFileName", zipFileName);
				//post.setHeader("zipFileName", zipFileName);
				////System.out.println(post.containsHeader("zipFileName"));
				////System.out.println(zipFileName);

				HttpResponse response = client.execute(post);
				resEntity = response.getEntity();

				res_code = response.getStatusLine().getStatusCode();

				System.out.println("Mobile Execution :"+res_code);
				// http response code >> chk for response and update syncd' records..
				final String response_str = EntityUtils.toString(resEntity);
				if (resEntity != null) {		// **** check for response >> if OK >> update records in db as "synced" 0->1  || ELSE toast= sync error...
					Log.i("RESPONSE",response_str);
					runOnUiThread(new Runnable(){
						public void run() {
							try {
								System.out.println("After Sync Successful res_code: " + res_code);

								if(res_code==200){
									syncFLAG = 1;
									System.out.println("After Sync Successful res_code: if " + res_code);
	                                		/*db.open();
	                                		System.out.println("After Sync Successful mSelectedItems.size(): " + mSelectedItems.size());

	                                		for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++)
	                						{
	                							String valSN = (String) mSelectedItems.get(nosSelected);
	                							int valID = stNames.indexOf(valSN);
	                							String stIDneeded = stIDs.get(valID);

	                							System.out.println("After Sync Successful StoreID: " + stIDneeded);
	                							System.out.println("After Sync Successful valSN: " + valSN);
	                							db.UpdateInvoiceButtonCancelStoreSynFlag(stIDneeded, 4,1);


	                						}
	                                		db.close();*/
									//db.open();
									if(whereTo.contentEquals("7"))
									{
										mDataSource.updateInvoiceButtonRecordsSyncd("7");
									}
									else if(whereTo.contentEquals("9"))
									{

										mDataSource.updateInvoiceButtonRecordsSyncd("9");		// update syncd' records

									}
									else if(whereTo.contentEquals("11"))
									{

										mDataSource.updateInvoiceButtonRecordsSyncd("3");		// update syncd' records

									}
									//db.close();
									//delete recently synced xml (not zip)
									delXML(xmlForWeb[0].toString());
									//System.out.println("inside runonUIthread() - Sync OK");
								}
								else {}

								//Toast.makeText(getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
								//System.out.println("inside runonUIthread() - Sync executed");
							} catch (Exception e) {

								e.printStackTrace();
								//showSyncError();

							}
						}
					});
				}

			} catch (Exception e) {
				Log.i("SyncMaster", "Sync Failed!", e);
				//return "Finished with failure";
				//showSyncError();

			}

			// Log.i("SyncMaster", "Sync Completed...");
			// return "Sync Completed...";
			 /*progressDialog2.dismiss();*/
			finally {

				Log.i("SyncMaster", "Sync Completed...");
				client.getConnectionManager().shutdown();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			Log.i("SyncMaster", "Sync Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.i("SyncMaster", "Sync cycle completed");

	        	/*progressDialog.dismiss();*/
			//pDialog2.dismiss();
			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}


			if(syncFLAG == 0)
			{
				showSyncError();
			}
			else
			{
	        		/*db.open();
	        		if(whereTo.contentEquals("11"))
            		{
            			db.updateInvoiceButtonRecordsSyncd("7");
            		}
            		else
            		{

            		db.updateInvoiceButtonRecordsSyncd("3");		// update syncd' records

            		}		// update syncd' records
            		db.close();*/
				showSyncSuccess();
			}
		}

	        /*protected void onProgressUpdate(String... values) {
	           // super.onProgressUpdate(values);
	        }*/

	}


	/*//lockdown_KEYS STARTS
	private Runnable mUpdateUiMsg = new Runnable() {
        public void run() {
            getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
         }
    };
	@Override
	 public void onAttachedToWindow() {
	  // TODO Auto-generated method stub
	     super.onAttachedToWindow();
	     Handler lockdownhandler = new android.os.Handler();

	     lockdownhandler.postDelayed(mUpdateUiMsg, 100);
	 }


	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
	  if(keyCode==KeyEvent.KEYCODE_BACK){
	   return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_HOME){
	   return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_MENU){
		  return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_SEARCH){
		  return true;
	  }

	  return super.onKeyDown(keyCode, event);
	 }

	//lockdown ENDS
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_master);

		_activity = this;
		//System.out.println("Induwati 0");
		Intent syncIntent = getIntent();
		xmlForWeb[0] = syncIntent.getStringExtra("xmlPathForSync");
		zipFileName = syncIntent.getStringExtra("OrigZipFileName");
		whereTo = syncIntent.getStringExtra("whereTo");
		activityFrom = syncIntent.getStringExtra("activityFrom");
		mSelectedItems = syncIntent.getStringArrayListExtra("mSelectedItems");
		//mSelectedItemsConfornInvoiceOrders = syncIntent.getStringArrayListExtra("mSelectedItemsConfornInvoiceOrders");
		imei = syncIntent.getStringExtra("imei");

		currSysDate = syncIntent.getStringExtra("currSysDate");
		pickerDate = syncIntent.getStringExtra("pickerDate");

		System.out.println("Induwati whereTo :"+whereTo);
		//System.out.println("XML path: " + xmlForWeb);

		/*chkString = (TextView)findViewById(R.id.textview1_testString);
		chkString.setText(xmlForWeb);*/
		try {
			//new SyncPROdata().execute().get();
			task1 = new SyncImageData(InvoiceSyncMaster.this);
			task1.execute();

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_launcher, menu);
		return true;
	}*/
	private class SyncImageData extends AsyncTask<Void, Void, Void>
	{
		String[] fp2s;
		String[] NoOfOutletID;

		public SyncImageData(InvoiceSyncMaster activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			//db.open();
			routeID="0";//db.GetActiveRouteID();
			//db.close();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getResources().getString(R.string.SubmittingExeImgMsg));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();


			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{

			}
			else
			{
				// Locate the image folder in your SD Card
				fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
				// Create a new folder if no folder named SDImageTutorial exist
				fileintial.mkdirs();


					/*// Locate the image folder in your SD Card
					fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
					// Create a new folder if no folder named SDImageTutorial exist
					fileintial.mkdirs();*/
			}


			if (fileintial.isDirectory())
			{
				listFile = fileintial.listFiles();
			}





		}

		@Override
		protected Void doInBackground(Void... params)
		{





			// Sync Execution Images

			try
			{

				try
				{

					NoOfOutletID = mDataSource.getAllStoreIdOftblExecutionImages();

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(NoOfOutletID.length>0)
				{
					for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
					{
						int NoOfImages = mDataSource.getImageCOunt(NoOfOutletID[chkCountstore].toString());
						String[] NoOfImgsPath = mDataSource.getImgsCount(NoOfOutletID[chkCountstore].toString());

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

								long syncTIMESTAMP = System.currentTimeMillis();
								Date datefromat = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
								String onlyDate= TimeUtils.getNetworkDateTime(InvoiceSyncMaster.this,"dd-MMM-yyyy HH:mm:ss.SSS");


								nameValuePairs.add(new BasicNameValuePair("image", image_str));
								nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
								nameValuePairs.add(new BasicNameValuePair("comment","NA"));
								nameValuePairs.add(new BasicNameValuePair("storeID",stID));
								nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
								nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

								try
								{

									HttpParams httpParams = new BasicHttpParams();
									HttpConnectionParams.setSoTimeout(httpParams, 0);
									HttpClient httpclient = new DefaultHttpClient(httpParams);
									HttpPost httppost = new HttpPost(CommonInfo.COMMON_SYNC_PATH_URL.trim() + CommonInfo.ClientFileNameImageSyncPath );


									httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
									HttpResponse response = httpclient.execute(httppost);
									String the_string_response = convertResponseToString(response);

									System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

									//  if(serverResponseCode == 200)
									if(the_string_response.equals("SUCCESS"))
									{

										System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
										mDataSource.updatetblExecutionImages(UploadingImageName.toString().trim());
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
			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}

			if(IMGsyOK == 1)
			{
				IMGsyOK = 0;


				showSyncErrorStart();
			}
			else
			{
				mDataSource.updateExecutionImageRecordsSyncd();

				//showSyncSuccess();

				//showSyncSuccessStart();


				try
				{
					SyncPROdata task = new SyncPROdata(InvoiceSyncMaster.this);
					task.execute();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
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

		//bmp
  /*try {
//          load the bitmap from its path


  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();

  }
*/


  /*if(actualWidth > 768 || h1 > 1024)
  {
   bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);
  }
  else
  {

   bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
  }*/
		//Bitmap bitmap=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		//  bmp =BitmapFactory.decodeFile(filePath, options);//Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);;//

		try {
//          load the bitmap from its path
			bmp = BitmapFactory.decodeFile(filePath, options);

			//bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
			//scaledBitmap=Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
			// bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}

		//Bitmap scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
		//bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		//scaledBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
		bmp.compress(Bitmap.CompressFormat.JPEG,100, baos);

		byte [] arr=baos.toByteArray();
		String result= android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
		return result;

  /*try {
//          load the bitmap from its path
   bmp = BitmapFactory.decodeFile(filePath, options);
  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();

  }
  try {
   scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();
  }*/

  /*float ratioX = actualWidth / (float) options.outWidth;
  float ratioY = actualHeight / (float) options.outHeight;
  float middleX = actualWidth / 2.0f;
  float middleY = actualHeight / 2.0f;*/


		//return filename;

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

}
