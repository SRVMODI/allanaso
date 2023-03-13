package com.astix.allanasosfa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;




import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class POSMaterialActivity extends BaseActivity implements OnItemSelectedListener, OnClickListener, DeletePic,OnFocusChangeListener
{
	public String storeID;
	public String imei;
	public String date;
	public String pickerDate;
	
	public ImageView btn_bck;
	public String selStoreName;
	
	public View[] hide_View;
	public String[] Material_ID;
	public  View viewProduct;
	 
	 public LinearLayout ll_prdct_detal;
	 
	
	 HashMap<String,String> hmapKPhotoNameVProductIdRsn=new HashMap<String, String>();
	 
	 public LinkedHashMap<String, String> hmapMaterial_details=new LinkedHashMap<String, String>();
	
	 public HashMap<String,String> hmapPhotoDetail=new HashMap<String, String>();
	 
	 
	 HashMap<String, String> hmapMatIDExistingStock=new HashMap<String, String>();
	 HashMap<String, String> hmapMatIDReturntoDistributor=new HashMap<String, String>();
	 HashMap<String, String> hmapMatIDFreshOrder=new HashMap<String, String>();
	 HashMap<String, String> hmapMatIDDiscardDamage=new HashMap<String, String>();
	 
	 
	 HashMap<String, String> hmapMatIDAndAllEditTextVal=new HashMap<String, String>();
	 
	 
	 ArrayList<HashMap<String, String>> arrLstHmapMatID=new ArrayList<HashMap<String,String>>();
	 
	 HashMap<String, String> hmapImageName_ImgPath=new HashMap<String,String>();
	 
	 
	// HashMap<String,String> hmapKPhotoNameFlagLocCreativr=new HashMap<String, String>();

	
	// New Changes
	 public String materialIdPhoto;
	 public Uri uriSavedImage;
	
	
	 public String onlyDate;
	 public String photoClickedDateTime="";
	 public String imageName;
	 public File imageF;
	 public int picAddPosition=0;
	 public int removePicPositin=0;
	
	 public String encodedImage;
	
	 public ImageAdapterMaterial adapterImage;
	 public ProgressDialog mProgressDialogA;
	 public ProgressDialog mProgressDialog;
	 public String routeID;
	 
	 LinearLayout recycler_view_images;
	// LinearLayout ll_imageView;
	 
	 private String FilePathStrings;
		private String FileNameStrings;
		private File[] listFile;
	 File fileintial;
	 
	 
	 public String LastVisitDateForTable[]=new String[1];
	 
	 
	 
	 ArrayList<LinkedHashMap<String, String>> arrLstHmapPrdct=new ArrayList<LinkedHashMap<String,String>>();
		
		LinkedHashMap<String, String> hmapStoreIDPOSMaterialID=new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, String> hmapPOSMaterialIDPOSMaterialDescr=new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, String> hmapPOSMaterialIDCurrentStockQty=new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, String> hmapPOSMaterialIDNewOrderQty=new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, String> hmapPOSMaterialIDReturnQty=new LinkedHashMap<String, String>();
		
		LinkedHashMap<String, String> hmapPOSMaterialIDDamageQty=new LinkedHashMap<String, String>();
		
		
	 
	 
	 
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK)
		  {
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME)
		  {
		 // finish();
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_MENU)
		  {
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_SEARCH)
		  {
			  return true;
		  }

		  return super.onKeyDown(keyCode, event);
		 }
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pos);
		
		 ll_prdct_detal=(LinearLayout) findViewById(R.id.ll_prdct_detal);
		 
		 
		

		 getDataFromIntent();
		 
		 getLastVisitDataInHashMap();
		
		 intialization();
		 
		 getMaterialData();
		 
		 createMaterialDetail();
		  
		// getProductData();
		 
		 
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				
			}
			else
			{
				// Locate the image folder in your SD Card
				fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + "MeijiIndirectSKUImages");
				// Create a new folder if no folder named SDImageTutorial exist
				fileintial.mkdirs();
			}
	 
			if (fileintial.isDirectory()) 
			{
				listFile = fileintial.listFiles();
				
				hmapKPhotoNameVProductIdRsn= mDataSource.getProductPicInfoMaterial(listFile,storeID);
				String productIdtoBeSetOnAdapter;
	 
				for (int i = 0; i < listFile.length; i++) 
				{

					
						if(hmapKPhotoNameVProductIdRsn.containsKey(listFile[i].getName()))
						{
							 FilePathStrings = listFile[i].getAbsolutePath();
							   Bitmap bmp = decodeSampledBitmapFromFile(FilePathStrings, 100, 100);
					            
							   
							   ByteArrayOutputStream stream = new ByteArrayOutputStream();

					                bmp.compress(Bitmap.CompressFormat.JPEG, 40, stream);
					                byte[] byteArray = stream.toByteArray();

					                // Convert ByteArray to Bitmap::

					                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					                        byteArray.length);
						//	Bitmap bmp = BitmapFactory.decodeFile(FilePathStrings);
							// Get the name image file
							FileNameStrings = listFile[i].getName();
						
							setSavedImageToScrollView(hmapKPhotoNameVProductIdRsn.get(listFile[i].getName()), bitmap,listFile[i].getName());
						
			            	

						}
					 				
					
				}
			}
		 
			 
				
			   final TextView textView_LastVisit=(TextView) findViewById(R.id.textView_LastVisit);
				SpannableString content = new SpannableString("POS Material Last Visit Details");
				content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
				textView_LastVisit.setText(content);
				
				textView_LastVisit.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						
						
						int checkDateCount= mDataSource.counttblStorePOSLastVisitDateDetail(storeID);
						if(checkDateCount==0)
						{

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(POSMaterialActivity.this);
							alertDialogBuilder.setTitle("Information");
			                alertDialogBuilder.setMessage("There is no Data Available For Visit.");
							
							 alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {}
							});
							

							alertDialogBuilder.setIcon(R.drawable.info_ico);
					        // create an alert dialog
					       AlertDialog alert = alertDialogBuilder.create();
					       alert.show();
						
						}
						else
						{
							int checkMaterialCount= mDataSource.counttblStorePOSLastVisitALLMaterialDetails(storeID);
							if(checkMaterialCount==0)
							{
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(POSMaterialActivity.this);
								alertDialogBuilder.setTitle("Information");
				                alertDialogBuilder.setMessage("There is no Data Available For Visit.");
								
								 alertDialogBuilder
								.setCancelable(false)
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {}
								});
								

								alertDialogBuilder.setIcon(R.drawable.info_ico);
						        // create an alert dialog
						       AlertDialog alert = alertDialogBuilder.create();
						       alert.show();	
							}
							else
							{
								alertForlastVisitData();
							}
						}
						
						
					
						
					
						
					}
				});
		
	}
	
	
/*	
	LinkedHashMap<String, String> hmapStoreIDPOSMaterialID=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hmapPOSMaterialIDPOSMaterialDescr=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hmapPOSMaterialIDCurrentStockQty=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hmapPOSMaterialIDNewOrderQty=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hmapPOSMaterialIDReturnQty=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hmapPOSMaterialIDDamageQty=new LinkedHashMap<String, String>();
	*/
	
	
	
	public void getLastVisitDataInHashMap()
	{
		arrLstHmapPrdct= mDataSource.fetch_tblStorePOSLastVisitALLMaterialDetails(storeID);
		
		if(arrLstHmapPrdct.size()>0)
		{
			hmapStoreIDPOSMaterialID=arrLstHmapPrdct.get(0);
			hmapPOSMaterialIDPOSMaterialDescr=arrLstHmapPrdct.get(1);
			hmapPOSMaterialIDCurrentStockQty=arrLstHmapPrdct.get(2);
			hmapPOSMaterialIDNewOrderQty=arrLstHmapPrdct.get(3);
			hmapPOSMaterialIDReturnQty=arrLstHmapPrdct.get(4);
			hmapPOSMaterialIDDamageQty=arrLstHmapPrdct.get(5);
			
			
		}
	}
	
	
	public void alertForlastVisitData()
	{
		String[] Material_IDNew;
		
		LayoutInflater layoutInflater = LayoutInflater.from(POSMaterialActivity.this);
		View promptView = layoutInflater.inflate(R.layout.list_item_pos_alert_head, null);
		
		TextView VisitDate = (TextView)promptView.findViewById(R.id.VisitDateId);
		LastVisitDateForTable= mDataSource.fetchDateFromtblStorePOSLastVisitDateDetail(storeID);
		
		VisitDate.setText("("+LastVisitDateForTable[0]+")");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(POSMaterialActivity.this);
		
		
		alertDialogBuilder.setTitle("Information");
		
		ScrollView scroll = new ScrollView(POSMaterialActivity.this);
		scroll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		scroll.addView(promptView);

		alertDialogBuilder.setView(scroll);
		
		LayoutInflater inflater = getLayoutInflater();
		
		
	//	changeHmapToArrayKey
		Material_IDNew=changeHmapToArrayKey(hmapStoreIDPOSMaterialID);
		
	//	Material_IDNew=changeHmapToArrayValue(hmapStoreIDPOSMaterialID);
		
          for(int position=0;position<hmapStoreIDPOSMaterialID.size();position++)
            {
                viewProduct=inflater.inflate(R.layout.list_item_pos_alert,null);
                
                if(position%2==0)
				{
                	viewProduct.setBackgroundResource(R.drawable.card_background_odd);
				}
		 		else
				   {
		 			viewProduct.setBackgroundResource(R.drawable.card_background_even);
		 		   }
                
              
                TextView tvProdctName=(TextView) viewProduct.findViewById(R.id.tvProdctName);
                tvProdctName.setText(" "+hmapPOSMaterialIDPOSMaterialDescr.get(Material_IDNew[position]));
                
               
         	   
         	   final EditText et_ReturntoDistributor=(EditText) viewProduct.findViewById(R.id.et_ReturntoDistributor);
         	    et_ReturntoDistributor.setText(" "+hmapPOSMaterialIDReturnQty.get(Material_IDNew[position]));
              
         	   final EditText et_FreshOrder=(EditText) viewProduct.findViewById(R.id.et_FreshOrder);
         	    et_FreshOrder.setText(" "+hmapPOSMaterialIDNewOrderQty.get(Material_IDNew[position]));
         	  
         	  
        	   final EditText et_DiscardDamage=(EditText) viewProduct.findViewById(R.id.et_DiscardDamage);
        	   et_DiscardDamage.setText(" "+hmapPOSMaterialIDDamageQty.get(Material_IDNew[position]));
        	   ((ViewGroup) promptView).addView(viewProduct);
            }
	
	
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {}
		});
		

		alertDialogBuilder.setIcon(R.drawable.info_ico);
// create an alert dialog
AlertDialog alert = alertDialogBuilder.create();
alert.show();
Window window = alert.getWindow();
window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
	}
	
	public void getDataFromIntent() 
	    {
		  
			  Intent passedvals = getIntent();
	
			  storeID = passedvals.getStringExtra("storeID");
			  imei = passedvals.getStringExtra("imei");
			  date = passedvals.getStringExtra("userdate");
			  pickerDate = passedvals.getStringExtra("pickerDate");
			  selStoreName = passedvals.getStringExtra("selStoreName");
			  selStoreName = passedvals.getStringExtra("selStoreName");
			 
	    }
	
	 public void setSavedImageToScrollView(String flagLocOrCreative,Bitmap bitmap,String imageValidName)
	 {
     	
     	
     	 LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewStoreLocDetail=inflater.inflate(R.layout.images_return_grid,null);
          
            
           final RelativeLayout rl_photo=(RelativeLayout) viewStoreLocDetail.findViewById(R.id.rl_photo);
           final ImageView img_thumbnail=(ImageView)viewStoreLocDetail.findViewById(R.id.img_thumbnail);
           img_thumbnail.setImageBitmap(bitmap);
           img_thumbnail.setTag(imageValidName);
          hmapImageName_ImgPath.put(imageValidName, Environment.getExternalStorageDirectory() + "/MeijiIndirectSKUImages/"+imageValidName);
           final ImageView imgCncl=(ImageView) viewStoreLocDetail.findViewById(R.id.imgCncl);
         
           img_thumbnail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					Uri intentUri = Uri.parse("file://"+hmapImageName_ImgPath.get(view.getTag().toString()));
				    
				     Intent intent = new Intent();
				     intent.setAction(Intent.ACTION_VIEW);
				     intent.setDataAndType(intentUri, "image/*");
				     startActivity(intent);
					
				}
			});
           
           imgCncl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					hmapImageName_ImgPath.remove(img_thumbnail.getTag());
					
				
					mDataSource.updatePhotoValidationMaterial("0", img_thumbnail.getTag().toString());
					
					//mDataSource.deleteImageData(img_thumbnail.getTag().toString(),storeId);
					 String file_dj_path = Environment.getExternalStorageDirectory() + "/MeijiIndirectSKUImages/"+img_thumbnail.getTag().toString();
				        File fdelete = new File(file_dj_path);
				        if (fdelete.exists()) {
				            if (fdelete.delete()) {
				               
				                callBroadCast();
				            } else {
				               
				            }
				        }
					ViewGroup parent = (ViewGroup) rl_photo.getParent();
					parent.removeView(rl_photo);
				}
			});
           
          
           recycler_view_images.addView(viewStoreLocDetail);
           
          
          
     }
	
	public void intialization() 
	{
		
		 recycler_view_images=(LinearLayout) findViewById(R.id.recycler_view_images);
		 adapterImage = new ImageAdapterMaterial(this);
		// recycler_view_images.setAdapter(adapterImage);
		
		 Button btn_SaveExit=(Button) findViewById(R.id.btn_saveExit);
		 btn_SaveExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				saveTableData();
				//mDataSource.open();
				mDataSource.UpdateStoreFlag(storeID.trim(), 1);
				//mDataSource.close();
				
				Intent storeIntent = new Intent(POSMaterialActivity.this, StoreSelection.class);
    			storeIntent.putExtra("imei", imei);
    			storeIntent.putExtra("userDate", date);
    			storeIntent.putExtra("pickerDate", pickerDate);
				storeIntent.putExtra("JOINTVISITID", StoreSelection.JointVisitId);
    			startActivity(storeIntent);
    			finish();
				
			}
		});
		
		
		  btn_bck=(ImageView) findViewById(R.id.btn_bck);
		  btn_bck.setOnClickListener(new OnClickListener()
			  {
				
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					    Intent fireBackDetPg=new Intent(POSMaterialActivity.this,LastVisitDetails.class);
					    fireBackDetPg.putExtra("storeID", storeID);
					    fireBackDetPg.putExtra("selStoreName", selStoreName);
					    fireBackDetPg.putExtra("bck", 1);
					    fireBackDetPg.putExtra("imei", imei);
					    fireBackDetPg.putExtra("userdate", date);
					    fireBackDetPg.putExtra("pickerDate", pickerDate);
					    startActivity(fireBackDetPg);
					    finish();
					
				}
			});
		  
		  
		  
		  
		  Button btn_continue = (Button)findViewById(R.id.btn_continue);
		  btn_continue.setOnClickListener(new OnClickListener()
			  {
						
						@Override
						public void onClick(View arg0)
						{
							
							saveTableData();
							
							Intent nxtP4 = new Intent(POSMaterialActivity.this,ProductEntryForm.class);
							nxtP4.putExtra("storeID", storeID);
							nxtP4.putExtra("SN", selStoreName);
							nxtP4.putExtra("imei", imei);
							nxtP4.putExtra("userdate", date);
							nxtP4.putExtra("pickerDate", pickerDate);
							startActivity(nxtP4);
							finish();
						}
			 });
		  
		  
		  
	}
	
	public void getMaterialData() 
    {
		//mDataSource.open();
		hmapMaterial_details= mDataSource.fetch_MaterialMstr_List(storeID);
		//mDataSource.close();
		
		
		arrLstHmapMatID= mDataSource.fetch_matID_ReturnData(storeID);
		
		if(arrLstHmapMatID.size()>0)
		{
			hmapMatIDExistingStock=arrLstHmapMatID.get(0);
			hmapMatIDReturntoDistributor=arrLstHmapMatID.get(1);
			hmapMatIDFreshOrder=arrLstHmapMatID.get(2);
			hmapMatIDDiscardDamage=arrLstHmapMatID.get(3);
		}
		
		
    }
	
	public void createMaterialDetail()
	{

		Material_ID=changeHmapToArrayKey(hmapMaterial_details);
		
		 LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          for(int position=0;position<hmapMaterial_details.size();position++)
            {
                viewProduct=inflater.inflate(R.layout.list_item_pos,null);
                
                if(position%2==0)
				{
                	viewProduct.setBackgroundResource(R.drawable.card_background_odd);
				}
		 		else
				   {
		 			viewProduct.setBackgroundResource(R.drawable.card_background_even);
		 		   }
                
                if(arrLstHmapMatID.size()<=0)
        		{
               // hmapMatIDExistingStock.put(""+Material_ID[position],"0");
                hmapMatIDReturntoDistributor.put(""+Material_ID[position],"0");
                hmapMatIDFreshOrder.put(""+Material_ID[position],"0");
                hmapMatIDDiscardDamage.put(""+Material_ID[position],"0");
        		}
                
            
                TextView tvProdctName=(TextView) viewProduct.findViewById(R.id.tvProdctName);
                tvProdctName.setText(" "+hmapMaterial_details.get(Material_ID[position]));
                
                ImageView imgVw_Photo=(ImageView) viewProduct.findViewById(R.id.imgVw_Photo);
         	    imgVw_Photo.setTag("imgVwPhoto"+"_"+Material_ID[position]);
         	    imgVw_Photo.setOnClickListener(this);
         	    
         	    
         	   final EditText et_ExistingStock=(EditText) viewProduct.findViewById(R.id.et_ExistingStock);
         	   et_ExistingStock.setTag("etExistingStock"+"_"+Material_ID[position]);
         	  if(hmapMatIDExistingStock.containsKey(Material_ID[position]))
         		{
         		  if(Integer.parseInt(hmapMatIDExistingStock.get(Material_ID[position]))==0)
         		  {
         			 et_ExistingStock.setHint("0");
         		  }
         		  else
         		  {
         		 et_ExistingStock.setText(hmapMatIDExistingStock.get(Material_ID[position]));
         		  }
         		}
         		else
         		{
         			//et_ExistingStock.setText("0");	
         			et_ExistingStock.setHint("0");
         		}
         	  
         	  
         	 
         	   
         	   
         	  // et_ExistingStock.setOnFocusChangeListener(this);
         	   
         	   final EditText et_ReturntoDistributor=(EditText) viewProduct.findViewById(R.id.et_ReturntoDistributor);
         	   et_ReturntoDistributor.setTag("etReturntoDistributor"+"_"+Material_ID[position]);
         	  if(hmapMatIDReturntoDistributor.containsKey(Material_ID[position]))
       		{
         		
         		if(Integer.parseInt(hmapMatIDReturntoDistributor.get(Material_ID[position]))==0)
       		     {
         			et_ReturntoDistributor.setHint("0");
       		      }
       		  else
       		     {
       			 et_ReturntoDistributor.setText(hmapMatIDReturntoDistributor.get(Material_ID[position]));
       		     }
       		}
       		else
       		{
       			//et_ReturntoDistributor.setText("0");
       			et_ReturntoDistributor.setHint("0");
       		}
         	  
         	   final EditText et_FreshOrder=(EditText) viewProduct.findViewById(R.id.et_FreshOrder);
         	   et_FreshOrder.setTag("etFreshOrder"+"_"+Material_ID[position]);
         	  if(hmapMatIDFreshOrder.containsKey(Material_ID[position]))
         		{
         		 
         		if(Integer.parseInt(hmapMatIDFreshOrder.get(Material_ID[position]))==0)
      		     {
         			et_FreshOrder.setHint("0");
      		      }
      		  else
      		     {
      			et_FreshOrder.setText(hmapMatIDFreshOrder.get(Material_ID[position]));
      		     }
         		}
         		else
         		{
         			//et_FreshOrder.setText("0");
         			et_FreshOrder.setHint("0");
         		}
         	  
        	   final EditText et_DiscardDamage=(EditText) viewProduct.findViewById(R.id.et_DiscardDamage);
        	   et_DiscardDamage.setTag("etDiscardDamage"+"_"+Material_ID[position]);
        	   if(hmapMatIDDiscardDamage.containsKey(Material_ID[position]))
        		{
        		  
        		   if(Integer.parseInt(hmapMatIDDiscardDamage.get(Material_ID[position]))==0)
        		     {
        			   et_DiscardDamage.setHint("0");
        		      }
        		  else
        		     {
        			  et_DiscardDamage.setText(hmapMatIDDiscardDamage.get(Material_ID[position]));
        		     }
        		}
        		else
        		{
        			//et_DiscardDamage.setText("0");
        			et_DiscardDamage.setHint("0");
        		} 
        	   
        	   et_ExistingStock.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) 
				{
					// TODO Auto-generated method stub
					
					  int getPIDTag=Integer.parseInt(et_ExistingStock.getTag().toString().split("_")[1].toString());
						et_ReturntoDistributor.setText("");
                	  	et_ReturntoDistributor.setHint("0");
                	  	et_ReturntoDistributor.setError(null);
                      if(!et_ExistingStock.getText().equals(""))
                           {             
                    	  
                    	  			int boxValue=0;
                    	  			if(et_ExistingStock.getText().toString().equals("") || et_ExistingStock.getText().equals("0") )
                    	  				{
                    	  				    boxValue=0;
                    	  				}
                    	  			else
                    	  				{
                    	  				     boxValue =Integer.parseInt(et_ExistingStock.getText().toString());
                    	  				}
                    	  			hmapMatIDExistingStock.put(""+getPIDTag, ""+boxValue);
                           }
                      else
                      		{
                    	  		et_ExistingStock.setText("");
                    	  		et_ExistingStock.setHint("");
                    	  		hmapMatIDExistingStock.put(""+getPIDTag, "0");
                      		}
					
				}
			});
        	   
        	   
        	  et_ReturntoDistributor.addTextChangedListener(new TextWatcher() {
   				
   				@Override
   				public void onTextChanged(CharSequence s, int start, int before, int count) 
   				{
   					// TODO Auto-generated method stub
   					
    					
   				
   					
   					
   				}
   				
   				@Override
   				public void beforeTextChanged(CharSequence s, int start, int count,
   						int after) {
   					// TODO Auto-generated method stub
   					
   				}
   				
   				@Override
   				public void afterTextChanged(Editable s)
   				{
   					// TODO Auto-generated method stub
   					int boxValue=0;
   					if(!TextUtils.isEmpty(et_ReturntoDistributor.getText().toString()) )
   					{
   						if(et_ReturntoDistributor.getText().toString().equals("") || et_ReturntoDistributor.getText().equals("0") )
   	   	  				{
   	   	  			        boxValue=0;
   	   	  				}
   	   	  			else
   	   	  				{
   	   	  			        boxValue =Integer.parseInt(et_ReturntoDistributor.getText().toString().trim());
   	   	  				}
   	   					if(hmapMatIDExistingStock.containsKey(((et_ReturntoDistributor.getTag().toString()).split(Pattern.quote("_")))[1]))
   	   					{
   	   					 if(Integer.parseInt(hmapMatIDExistingStock.get(((et_ReturntoDistributor.getTag().toString()).split(Pattern.quote("_")))[1]))<(boxValue))
   	 					{
   	 						et_ReturntoDistributor.setText("");
   	 						et_ReturntoDistributor.setHint("");
   	 						et_ReturntoDistributor.setFocusable(true);
   	 					
   	 						String estring = "Return Quantity exceeds Stock";
   	 						ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
   	 						SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
   	 						ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
   	 					
   	 						et_ReturntoDistributor.setError(ssbuilder);
   	 					}
   	 					else
   	 					{
   	 					 int getPIDTag=Integer.parseInt(et_ReturntoDistributor.getTag().toString().split("_")[1].toString());
   	 						hmapMatIDReturntoDistributor.put(""+getPIDTag, ""+boxValue);
   	 						et_ReturntoDistributor.setError(null);
   	 					}
   	   					}
   	   					else
   	   					{
   	   					et_ReturntoDistributor.setText("");
	 						et_ReturntoDistributor.setHint("");
	 						et_ReturntoDistributor.setFocusable(true);
	 					
	 						String estring = "Please fill return quantity";
	 						ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
	 						SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
	 						ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
	 					
	 						et_ReturntoDistributor.setError(ssbuilder);
   	   					}
   	   					
   					}
   				
   					
    					
   				
   					
   				}
   			});
        	   
        	   et_FreshOrder.addTextChangedListener(new TextWatcher() {
      				
      				@Override
      				public void onTextChanged(CharSequence s, int start, int before, int count) {
      					// TODO Auto-generated method stub
      					
      				}
      				
      				@Override
      				public void beforeTextChanged(CharSequence s, int start, int count,
      						int after) {
      					// TODO Auto-generated method stub
      					
      				}
      				
      				@Override
      				public void afterTextChanged(Editable s) 
      				{
      					// TODO Auto-generated method stub
      					
      					 int getPIDTag=Integer.parseInt(et_FreshOrder.getTag().toString().split("_")[1].toString());
      	                 if(!et_FreshOrder.getText().equals(""))
      	                      {             
      	               	  			int boxValue=0;
      	               	  			if(et_FreshOrder.getText().toString().equals("") || et_FreshOrder.getText().equals("0") )
      	               	  				{
      	               	  			        boxValue=0;
      	               	  				}
      	               	  			else
      	               	  				{
      	               	  			        boxValue =Integer.parseInt(et_FreshOrder.getText().toString());
      	               	  				}
      	               	  			
      	               	  		           hmapMatIDFreshOrder.put(""+getPIDTag, ""+boxValue);
      	                      }
      	                 else
      	                 		{
      	                	              et_FreshOrder.setText("");
      	                	              et_FreshOrder.setHint("");
      	               	  		         hmapMatIDFreshOrder.put(""+getPIDTag, "0");
      	                 		}
      	   					
      					
      				}
      			});
        	   
        	   
        	   et_DiscardDamage.addTextChangedListener(new TextWatcher() {
     				
     				@Override
     				public void onTextChanged(CharSequence s, int start, int before, int count) 
     				{
     					// TODO Auto-generated method stub
     					
     					
     					
     					
     				}
     				
     				@Override
     				public void beforeTextChanged(CharSequence s, int start, int count,
     						int after) {
     					// TODO Auto-generated method stub
     					
     				}
     				
     				@Override
     				public void afterTextChanged(Editable s) 
     				{
     					// TODO Auto-generated method stub
     					
     					int boxValue=0;
     					if(!TextUtils.isEmpty(et_DiscardDamage.getText().toString()))
     					{
     						if(et_DiscardDamage.getText().toString().equals("") || et_DiscardDamage.getText().equals("0") )
           	  				{
           	  			        boxValue=0;
           	  				}
           	  			else
           	  				{
           	  			        boxValue =Integer.parseInt(et_DiscardDamage.getText().toString().trim());
           	  				}
           					if(hmapMatIDExistingStock.containsKey(((et_DiscardDamage.getTag().toString()).split(Pattern.quote("_")))[1]))
           					{
           						if(Integer.parseInt(hmapMatIDExistingStock.get(((et_DiscardDamage.getTag().toString()).split(Pattern.quote("_")))[1]))<(boxValue))
               					{
               						et_DiscardDamage.setText("");
               						et_DiscardDamage.setHint("");
               						et_DiscardDamage.setFocusable(true);
               					
               						String estring = "Discard/Damage Quantity exceeds Stock";
               						ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
               						SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
               						ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
               					
               						et_DiscardDamage.setError(ssbuilder);
               					}
               					else
               					{
               						int getPIDTag=Integer.parseInt(et_DiscardDamage.getTag().toString().split("_")[1].toString());
               					    hmapMatIDDiscardDamage.put(""+getPIDTag, ""+boxValue);
               						et_DiscardDamage.setError(null);
               					}
           					}
           					else
           					{
           						String estring = "Please fill Stock for Discard/Damage";
           						ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
           						SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
           						ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
           					
           						et_DiscardDamage.setError(ssbuilder);
           					}
           					
     					}
       					
      					
      					
     				}
     			});
        	   
        	   
        	   if(arrLstHmapMatID.size()<=0)
       		 {
           		//et_ExistingStock.setText(hmapPOSMaterialIDCurrentStockQty.get(Material_ID[position])); 
           		
           		 if(hmapPOSMaterialIDCurrentStockQty.containsKey(Material_ID[position]))
            		{
            		  if(Integer.parseInt(hmapPOSMaterialIDCurrentStockQty.get(Material_ID[position]))==0)
            		  {
            			 et_ExistingStock.setHint("0");
            		  }
            		  else
            		  {
            		 et_ExistingStock.setText(hmapPOSMaterialIDCurrentStockQty.get(Material_ID[position]));
            		  }
            		}
            		else
            		{
            			//et_ExistingStock.setText("0");	
            			et_ExistingStock.setHint("0");
            		}
       		}
               
                
                ll_prdct_detal.addView(viewProduct);
            }
	}
	
	public String[] changeHmapToArrayKey(HashMap hmap)
	{
		String[] stringArray=new String[hmap.size()];
		int index=0;
		if(hmap!=null)
    	{
            Set set2 = hmap.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) 
            {
            	 Map.Entry me2 = (Map.Entry)iterator.next();
            	 stringArray[index]=me2.getKey().toString();
                 index=index+1;
            }
    	}
		return stringArray;
	}
	
	public String[] changeHmapToArrayValue(HashMap hmap)
	{
		String[] stringArray=new String[hmap.size()];
		int index=0;
		if(hmap!=null)
    	{
            Set set2 = hmap.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
            	 Map.Entry me2 = (Map.Entry)iterator.next();
            	 stringArray[index]=me2.getValue().toString();
            	 System.out.println("Betu Slab = "+stringArray[index]);
                 index=index+1;
            }
    	}
		return stringArray;
	}
	
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId()== R.id.imgVw_Photo)
		{
			//"tvPhoto"+"_"+prductId[position]
			materialIdPhoto=(v.getTag().toString()).split(Pattern.quote("_"))[1].toString();
			Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			
			 saveImage();
			 imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
	         startActivityForResult(imageIntent,100);
		}
		
		if(v.getId()==R.id.btn_saveExitRtrn)
		{

		    // TODO Auto-generated method stub
			
			  /* if(hmapRtrnPrdctDetail!=null)
			   {
				   if(editTextLastFocus!=null)
				   {
					   productID=(editTextLastFocus.getTag().toString()).split(Pattern.quote("_"))[1];
					   
						 spnrRsnSave=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productID);
						 String selected_text;
							int position=0;
							if (spnrRsnSave.getSelectedItem() != null)
							{
							    selected_text = spnrRsnSave.getSelectedItem().toString();
							    position=adapterReason.getPosition(selected_text);
							}
							else
							{
								selected_text="Please Select Reason";
							}
		//routeID,storeID,returnProductID,prodReturnQty,prodReturnReason, prodReturnReasonIndex,returnDate, _outstat
							hmapRtrnPrdctDetail.put(productID, routeID+"~"+storeID+"~"+editTextLastFocus.getText().toString()+"~"+selected_text+"~"+String.valueOf(position)+"~"+date+"~"+"1");//"0");
				   }
				   
				
				
				
				    new SetDataToDatabase().execute("1~2");
			   }
			   else
			   {
				   Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductList.class);
		           fireBackDetPg.putExtra("storeID", storeID);
		           fireBackDetPg.putExtra("SN", SN);
		           fireBackDetPg.putExtra("bck", 1);
		           fireBackDetPg.putExtra("imei", imei);
		           fireBackDetPg.putExtra("userdate", date);
		           fireBackDetPg.putExtra("pickerDate", pickerDate);
		           fireBackDetPg.putExtra("rID", routeID);
		         
		           startActivity(fireBackDetPg);
		           finish(); 
			   }*/
			   
		}
				
	
		
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	
		
		
			/*spinnerCategorySelected = spinner_category.getSelectedItem().toString();
			
			filterProduct(spinnerCategorySelected);*/
		
		
		
	}
	
	
	public void saveImage()
	{
			long syncTIMESTAMP = System.currentTimeMillis();
		   Date datefromat = new Date(syncTIMESTAMP);
		   SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		   SimpleDateFormat dfClickedDateTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
		   onlyDate=df.format(datefromat);
		   photoClickedDateTime=dfClickedDateTime.format(datefromat);
		   onlyDate=onlyDate.replace(":","").trim().replace("-", "").replace(" ","").trim().toString();
		 
		   File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeijiIndirectSKUImages");
			if (!imagesFolder.exists()) 
			{
				 imagesFolder.mkdirs();
				 
			}   
			
			
			imageName=imei+"~"+storeID+"~"+materialIdPhoto+"~"+onlyDate+".jpg";
			
			imageF = new File(imagesFolder,imageName);
		
			try {
				FileOutputStream fo = new FileOutputStream(imageF);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	        uriSavedImage = Uri.fromFile(imageF);
	        
	       
	       
	                      
	   
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (requestCode == 100)
	    {
	        if (resultCode == this.RESULT_OK)
	        {
	        	File file=imageF;
	        	System.out.println("File +++"+imageF);
	        	
	             Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 160, 160);
	             ByteArrayOutputStream stream = new ByteArrayOutputStream();

	                bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
	                byte[] byteArray = stream.toByteArray();

	                // Convert ByteArray to Bitmap::

	                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,byteArray.length);


	                if(bitmap!=null)
	                {
	                    if(true)
	                    {
	                    	mDataSource.insertMaterialPhotoDetail(storeID,materialIdPhoto,photoClickedDateTime,imageName,"NA","1",uriSavedImage.toString(),1);
	                    	removePicPositin=picAddPosition;
	                    	
	                    	//hmapPhotoDetail.put(materialIdPhoto, "NA"+"~"+hmapPrdctIdPrdctName.get(productIdPhoto));
	                    	adapterImage.add(picAddPosition,bitmap,imageName);
	                    	System.out.println("Picture Adapter"+picAddPosition);
	                    	picAddPosition++;
	                    	setSavedImageToScrollView("0",bitmap,imageName);
	                    }
	                }
	                
	                	encodedImage=Base64.encodeToString(byteArray, Base64.DEFAULT);
	             
	                	//new SyncImgTasker().execute();

	           
	        }
	    }        
	}  
	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) 
	{ // BEST QUALITY MATCH
	     
	    //First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);
	 
	    // Calculate inSampleSize, Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    options.inPreferredConfig = Bitmap.Config.RGB_565;
	    int inSampleSize = 1;
	 
	    if (height > reqHeight) 
	    {
	        inSampleSize = Math.round((float)height / (float)reqHeight);
	    }
	    int expectedWidth = width / inSampleSize;
	 
	    if (expectedWidth > reqWidth) 
	    {
	        //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
	        inSampleSize = Math.round((float)width / (float)reqWidth);
	    }
	 
	    options.inSampleSize = inSampleSize;
	 
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	 
	    return BitmapFactory.decodeFile(path, options);
	}
	

	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		// TODO Auto-generated method stub
		
		/*String MaterialIdOnClickedControl=v.getTag().toString().split("_")[1];
		
		if(!hasFocus)
		{
			View viewRow=(View) v.getParent();
			
			hideSoftKeyboard(v);
			if(v instanceof EditText)
			{
				
				EditText et_ValueOnFocuslost=(EditText) v;
			}
			
		}*/
		
	}
	
	
	public void saveTableData()
	{
		
		
		if(Material_ID.length>0)
		{
			mDataSource.deleteOldtblStoreMaterialDetail(storeID);
			
			 for(int position=0;position<hmapMaterial_details.size();position++)
               {
               
				String ExistStockVal= hmapMatIDExistingStock.get(Material_ID[position]);
				String ReturntoDistributorVal= hmapMatIDReturntoDistributor.get(Material_ID[position]);
				String FreshOrderVal= hmapMatIDFreshOrder.get(Material_ID[position]);
				String DiscardDamageVal= hmapMatIDDiscardDamage.get(Material_ID[position]);
				
				if(ExistStockVal==null)
				{
					ExistStockVal="0";
				}
				if(ReturntoDistributorVal==null)
				{
					ReturntoDistributorVal="0";
				}
				if(FreshOrderVal==null)
				{
					FreshOrderVal="0";
				}
				if(DiscardDamageVal==null)
				{
					DiscardDamageVal="0";
				}
				
				String MatID=Material_ID[position];
				
				mDataSource.insertStoreMaterialDetail(storeID,MatID,Integer.parseInt(ExistStockVal),Integer.parseInt(ReturntoDistributorVal),
						Integer.parseInt(FreshOrderVal),Integer.parseInt(DiscardDamageVal),1);
               
               }
			 
			
		}
	}
	
	
	public void hideSoftKeyboard(View view)
	{
		  InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	public void showSoftKeyboard(View view)
	{
	    if(view.requestFocus())
	    {
	        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
	    }
	}
	
	 public void callBroadCast() 
	 {
	        if (Build.VERSION.SDK_INT >= 14) 
	        {
	            Log.e("-->", " >= 14");
	            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() 
	            {
	               public void onScanCompleted(String path, Uri uri)
	                {
	                }
	            });
	        } 
	        else 
	        {
	            Log.e("-->", " < 14");
	            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	        }
	    }


	@Override
	public void delPic(Bitmap bmp, String imageNameToDel)
	{
		// TODO Auto-generated method stub

		removePicPositin=removePicPositin-1;
		picAddPosition=picAddPosition-1;
	

        mDataSource.updatePhotoValidationMaterial("0", imageNameToDel);
		adapterImage.remove(bmp);
		
		    String file_dj_path = Environment.getExternalStorageDirectory() + "/MeijiIndirectSKUImages/"+imageNameToDel;
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
	
	
		
	}

	@Override
	public void getProductPhotoDetail(String productIdTag) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
		// TODO Auto-generated method stub
		
	}
	

	

}
