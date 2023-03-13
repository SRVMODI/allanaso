package com.astix.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.multidex.BuildConfig;

import com.allanasosfa.truetime.TimeUtils;
import com.astix.allanasosfa.InterfaceRetrofit;
import com.astix.allanasosfa.MultipleInterfaceForDayEndStatus;
import com.astix.allanasosfa.MultipleInterfaceRetrofit;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.model.*;
import com.astix.allanasosfa.rest.ApiClient;
import com.astix.allanasosfa.rest.ApiInterface;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CommonFunction {


    public static Bitmap normalizeImageForUri(Context context, Uri uri) {
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

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
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

    private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
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

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static void getAllMasterTableModelData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        CommonInfo.flgDrctslsIndrctSls = dbengine.fnGetflgDrctslsIndrctSlsForDSR(Integer.parseInt(CoverageNodeId), Integer.parseInt(CoverageNodeType));
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setFlgAllRouteData(1);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
     /*   data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));*/
        data.setCoverageAreaNodeId(0);
        data.setCoverageAreaNodeType(0);

        Call<AllMasterTablesModel> call = apiService.Call_AllMasterData(data);
        call.enqueue(new Callback<AllMasterTablesModel>() {
            @Override
            public void onResponse(Call<AllMasterTablesModel> call, Response<AllMasterTablesModel> response) {
                if (response.code() == 200) {
                    AllMasterTablesModel allMasterTablesModel = response.body();
                    System.out.println("DATAENSERTEDSP");

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            //table 1
                            if (allMasterTablesModel.getTblDayStartAttendanceOptions() != null) {
                                dbengine.deletetblDayStartAttendanceOptions();
                                List<TblDayStartAttendanceOption> tblDayStartAttendanceOption = allMasterTablesModel.getTblDayStartAttendanceOptions();
                                if (tblDayStartAttendanceOption.size() > 0) {

                                    int AutoIdStore = 0;
                                    for (TblDayStartAttendanceOption dayStartAttendanceOption : tblDayStartAttendanceOption) {
                                        AutoIdStore = AutoIdStore++;
                                        dbengine.savetblDayStartAttendanceOptions(AutoIdStore, "" + dayStartAttendanceOption.getReasonId(), dayStartAttendanceOption.getReasonDescr(), dayStartAttendanceOption.getFlgToShowTextBox(), dayStartAttendanceOption.getFlgSOApplicable(), dayStartAttendanceOption.getFlgDSRApplicable(), dayStartAttendanceOption.getFlgNoVisitOption(), dayStartAttendanceOption.getSeqNo(), dayStartAttendanceOption.getFlgDelayedReason(), dayStartAttendanceOption.getFlgMarketVisit());
                                    }
                                } else {
                                    blankTablearrayList.add("tblDayStartAttendanceOptions");
                                }
                            }
                            //table 2
                            if (allMasterTablesModel.getTblRouteListMaster() != null) {
                                dbengine.Delete_tblRouteMasterMstr();
                                List<TblRouteListMaster> tblRouteListMaster = allMasterTablesModel.getTblRouteListMaster();
                                if (tblRouteListMaster.size() > 0) {

                                    int AutoIdStore = 0;
                                    for (TblRouteListMaster RouteListMaster : tblRouteListMaster) {

                                        dbengine.saveRoutesInfo("" + RouteListMaster.getID(), "" + RouteListMaster.getRouteType(), RouteListMaster.getDescr(), Integer.parseInt(RouteListMaster.getActive().toString()), Integer.parseInt(RouteListMaster.getActive().toString()), fDate);
                                    }
                                } else {
                                    blankTablearrayList.add("tblRouteListMaster");
                                }
                            }

                            if (allMasterTablesModel.getTblSalesPersonTodaysTarget() != null) {
                                CommonInfo.SalesPersonTodaysTargetMsg = "";
                                List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget = allMasterTablesModel.getTblSalesPersonTodaysTarget();
                                if (tblSalesPersonTodaysTarget.size() > 0) {
                                    for (TblSalesPersonTodaysTarget SalesPersonTodaysTarget : tblSalesPersonTodaysTarget) {

                                        CommonInfo.SalesPersonTodaysTargetMsg = SalesPersonTodaysTarget.getValueTarget();
                                    }
                                }
                            }


                            if (allMasterTablesModel.getTblIsDBRStockSubmitted() != null) {
                                dbengine.deletetblIsDBRStockSubmitted();
                                List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted = allMasterTablesModel.getTblIsDBRStockSubmitted();
                                if (tblIsDBRStockSubmitted.size() > 0) {
                                    for (TblIsDBRStockSubmitted IsDBRStockSubmitted : tblIsDBRStockSubmitted) {

                                        dbengine.savetblIsDBRStockSubmitted(IsDBRStockSubmitted.getIsDBRStockSubmitted());
                                    }
                                }
                            }

                            //table 3
                            if (allMasterTablesModel.getTblBloodGroup() != null) {
                                dbengine.Delete_bloodGroupMstr();
                                List<TblBloodGroup> tblBloodGroup = allMasterTablesModel.getTblBloodGroup();

                                if (tblBloodGroup.size() > 0) {
                                    int AutoIdStore = 0;
                                    for (TblBloodGroup BloodGroup : tblBloodGroup) {
                                        dbengine.savetblBloodGroup(BloodGroup.getBloddGroups());

                                    }
                                } else {
                                    blankTablearrayList.add("tblBloodGroup");
                                }
                            }
                            //table 4

                            if (allMasterTablesModel.getTblEducationQuali() != null) {
                                dbengine.Delete_tblEducationQuali();
                                List<TblEducationQuali> tblEducationQuali = allMasterTablesModel.getTblEducationQuali();

                                if (tblEducationQuali.size() > 0) {
                                    int AutoIdStore = 0;
                                    for (TblEducationQuali EducationQuali : tblEducationQuali) {
                                        dbengine.savetblEducationQuali(EducationQuali.getQualification());

                                    }
                                } else {
                                    blankTablearrayList.add("tblEducationQuali");
                                }
                            }
                            //table 5
                            if (allMasterTablesModel.getTblQuestIDForOutChannel() != null) {
                                dbengine.Delete_tblQuestIDForOutChannel();
                                List<TblQuestIDForOutChannel> tblQuestIDForOutChannel = allMasterTablesModel.getTblQuestIDForOutChannel();

                                if (tblQuestIDForOutChannel.size() > 0) {
                                    for (TblQuestIDForOutChannel QuestIDForOutChannel : tblQuestIDForOutChannel) {
                                        AppDataSource.saveOutletChammetQstnIdGrpId(QuestIDForOutChannel.getGrpQuestID(), QuestIDForOutChannel.getQuestID(), QuestIDForOutChannel.getOptionID(), QuestIDForOutChannel.getSectionCount());

                                    }

                                } else {
                                    blankTablearrayList.add("tblQuestIDForOutChannel");
                                }
                            }
                            //table 6
                            if (allMasterTablesModel.getTblGetPDAQuestMstr() != null) {
                                dbengine.Delete_tblGetPDAQuestMstr();
                                List<TblGetPDAQuestMstr> tblGetPDAQuestMstr = allMasterTablesModel.getTblGetPDAQuestMstr();

                                if (tblGetPDAQuestMstr.size() > 0) {
                                    int AutoIdStore = 0;

                                    dbengine.savetblQuestionMstrRetroFit(tblGetPDAQuestMstr);


                                } else {
                                    blankTablearrayList.add("tblGetPDAQuestMstr");
                                }
                            }

                            if (allMasterTablesModel.getTblStoreCloseReasonMaster() != null) {
                                dbengine.deletetblStoreCloseReasonMaster();
                                List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster = allMasterTablesModel.getTblStoreCloseReasonMaster();


                                if (tblStoreCloseReasonMaster.size() > 0) {

                                    for (TblStoreCloseReasonMaster StoreCloseReasonMaster : tblStoreCloseReasonMaster) {
                                        dbengine.savetblStoreCloseReasonMaster(StoreCloseReasonMaster.getCloseReasonID(), StoreCloseReasonMaster.getCloseReasonDescr());
                                    }

                                } else {
                                    blankTablearrayList.add("tblStoreCloseReasonMaster");
                                }
                            }


                            //table 7
                            if (allMasterTablesModel.getTblQuestIDForName() != null) {
                                dbengine.Delete_tblQuestIDForName();
                                List<TblQuestIDForName> tblQuestIDForName = allMasterTablesModel.getTblQuestIDForName();

                                if (tblQuestIDForName.size() > 0) {

                                    for (TblQuestIDForName QuestIDForName : tblQuestIDForName) {
                                        dbengine.savetblQuestIDForName(QuestIDForName.getID(), QuestIDForName.getGrpQuestID(), QuestIDForName.getQuestID(), QuestIDForName.getQuestDesc());
                                    }

                                } else {
                                    blankTablearrayList.add("tblQuestIDForName");
                                }
                            }

                            //table 8----------------
                            if (allMasterTablesModel.getTblGetPDAQuestGrpMapping() != null) {
                                dbengine.Delete_tblPDAQuestGrpMappingMstr();
                                List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping = allMasterTablesModel.getTblGetPDAQuestGrpMapping();

                                if (tblGetPDAQuestGrpMapping.size() > 0) {


                                    dbengine.savetblPDAQuestGrpMappingMstr(tblGetPDAQuestGrpMapping);
                                } else {
                                    blankTablearrayList.add("tblGetPDAQuestGrpMapping");
                                }
                            }

                            //table 9-------------------------------
                            if (allMasterTablesModel.getTblGetPDAQuestOptionMstr() != null) {
                                dbengine.Delete_tblOptionMstr();
                                List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr = allMasterTablesModel.getTblGetPDAQuestOptionMstr();

                                if (tblGetPDAQuestOptionMstr.size() > 0) {
                                    dbengine.savetblOptionMstrRetrofit(tblGetPDAQuestOptionMstr);


                                } else {
                                    blankTablearrayList.add("tblGetPDAQuestOptionMstr");
                                }
                            }
                            //table 10-------------------------------
                            if (allMasterTablesModel.getTblGetPDAQuestionDependentMstr() != null) {
                                dbengine.Delete_tblQuestionDependentMstr();
                                List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr = allMasterTablesModel.getTblGetPDAQuestionDependentMstr();

                                if (tblGetPDAQuestionDependentMstr.size() > 0) {
                                    for (TblGetPDAQuestionDependentMstr GetPDAQuestionDependentMstr : tblGetPDAQuestionDependentMstr) {
                                        dbengine.savetblQuestionDependentMstr(GetPDAQuestionDependentMstr.getQuestID(), GetPDAQuestionDependentMstr.getOptID(), GetPDAQuestionDependentMstr.getDependentQuestID(), GetPDAQuestionDependentMstr.getGrpQuestID(), GetPDAQuestionDependentMstr.getGrpDepQuestID());
                                    }

                                } else {
                                    blankTablearrayList.add("tblGetPDAQuestionDependentMstr");
                                }
                            }

                            //table 11-------------------------------
                            if (allMasterTablesModel.getTblPDAQuestOptionDependentMstr() != null) {
                                dbengine.Delete_tblPDAQuestOptionDependentMstr();
                                List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr = allMasterTablesModel.getTblPDAQuestOptionDependentMstr();

                                if (tblPDAQuestOptionDependentMstr.size() > 0) {
                                    for (TblPDAQuestOptionDependentMstr PDAQuestOptionDependentMstr : tblPDAQuestOptionDependentMstr) {
                                        dbengine.savetblPDAQuestOptionDependentMstr(PDAQuestOptionDependentMstr.getQstId(), PDAQuestOptionDependentMstr.getDepQstId(), PDAQuestOptionDependentMstr.getQstId(), PDAQuestOptionDependentMstr.getGrpDepQuestID());
                                    }

                                } else {
                                    blankTablearrayList.add("tblPDAQuestOptionDependentMstr");
                                }
                            }
                            //table 12-------------------------------
                            if (allMasterTablesModel.getTblPDAQuestOptionValuesDependentMstr() != null) {
                                dbengine.Delete_tblPDAQuestOptionValuesDependentMstr();
                                List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr = allMasterTablesModel.getTblPDAQuestOptionValuesDependentMstr();

                                if (tblPDAQuestOptionValuesDependentMstr.size() > 0) {
                                    for (TblPDAQuestOptionValuesDependentMstr PDAQuestOptionValuesDependentMstr : tblPDAQuestOptionValuesDependentMstr) {
                                        dbengine.savetblPDAQuestOptionValuesDependentMstr(PDAQuestOptionValuesDependentMstr.getDepQstId(), PDAQuestOptionValuesDependentMstr.getDepOptID(), PDAQuestOptionValuesDependentMstr.getQuestId(), PDAQuestOptionValuesDependentMstr.getOptID(), PDAQuestOptionValuesDependentMstr.getOptDescr(), PDAQuestOptionValuesDependentMstr.getSequence(), PDAQuestOptionValuesDependentMstr.getGrpQuestID(), PDAQuestOptionValuesDependentMstr.getGrpDepQuestID());
                                    }

                                } else {
                                    blankTablearrayList.add("tblPDAQuestOptionValuesDependentMstr");
                                }
                            }
                            //table 13-------------------------------
                            if (allMasterTablesModel.getTblPDANotificationMaster() != null) {
                                dbengine.Delete_tblNotificationMstr();
                                List<TblPDANotificationMaster> tblPDANotificationMaster = allMasterTablesModel.getTblPDANotificationMaster();

                                if (tblPDANotificationMaster.size() > 0) {
                                    int SerialNo = 0;
                                    for (TblPDANotificationMaster PDANotificationMaster : tblPDANotificationMaster) {
                                        SerialNo = SerialNo++;
                                        dbengine.inserttblNotificationMstr(SerialNo, imei, PDANotificationMaster.getNotificationMessage(), PDANotificationMaster.getMsgSendingTime(), 0, 0, "0", 0, PDANotificationMaster.getMsgServerID());
                                    }

                                } else {
                                    blankTablearrayList.add("tblPDANotificationMaster");
                                }
                            }
                            //table 14-------------------------------
                            if (allMasterTablesModel.getTblUserName() != null) {
                                dbengine.delete_all_storeDetailTables();//deleting all tables related to
                                List<TblUserName> tblUserName = allMasterTablesModel.getTblUserName();

                                if (tblUserName.size() > 0) {

                                    for (TblUserName UserName : tblUserName) {
                                        dbengine.saveTblUserName(UserName.getUserName());
                                    }

                                } else {
                                    blankTablearrayList.add("tblUserName");
                                }
                            }
                            //table 15-------------------------------


                            //table 31-------------------------------
                            if (allMasterTablesModel.getTblAppMasterFlags() != null) {
                                dbengine.Delete_tblAppMasterFlags();
                                List<TblAppMasterFlags> tblAppMasterFlags = allMasterTablesModel.getTblAppMasterFlags();

                                if (tblAppMasterFlags.size() > 0) {
                                    dbengine.saveAppMasterFlagsRetro(tblAppMasterFlags);
                                    CommonInfo.hmapAppMasterFlags = dbengine.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);
                                } else {
                                    blankTablearrayList.add("tblAppMasterFlags");
                                }
                            }

                            if (allMasterTablesModel.getTblStoreCountDetails() != null) {
                                List<TblStoreCountDetails> tblStoreCountDetails = allMasterTablesModel.getTblStoreCountDetails();

                                if (tblStoreCountDetails.size() > 0) {

                                    for (TblStoreCountDetails StoreCountDetails : tblStoreCountDetails) {

                                        dbengine.saveTblStoreCountDetails("" + StoreCountDetails.getTotStoreAdded(), "" + StoreCountDetails.getTodayStoreAdded());
                                    }

                                } else {
                                    blankTablearrayList.add("tblStoreCountDetails");
                                }
                            }
                            //table 16-------------------------------
                            //already deleted above

                            if (allMasterTablesModel.getTblPreAddedStores() != null) {
                                List<TblPreAddedStores> tblPreAddedStores = allMasterTablesModel.getTblPreAddedStores();

                                HashMap<String, String> hmapPreAddedStoreIdSstat = new HashMap<String, String>();
                                hmapPreAddedStoreIdSstat = dbengine.checkForPreAddedStoreIdSstat();
                                if (tblPreAddedStores.size() > 0) {

                                    dbengine.saveTblPreAddedStoresRetrofit(tblPreAddedStores, hmapPreAddedStoreIdSstat);
                                } else {
                                    blankTablearrayList.add("tblPreAddedStores");
                                }


                                if (hmapPreAddedStoreIdSstat != null && hmapPreAddedStoreIdSstat.size() > 0) {
                                    hmapPreAddedStoreIdSstat.clear();
                                    hmapPreAddedStoreIdSstat = null;
                                }
                                //table 17-------------------------------
                                //already deleted above
                                List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails = allMasterTablesModel.getTblPreAddedStoresDataDetails();

                                if (tblPreAddedStoresDataDetails.size() > 0) {

                                    dbengine.saveTblPreAddedStoresDataDetailsRetrofit(tblPreAddedStoresDataDetails);
                                } else {
                                    blankTablearrayList.add("tblPreAddedStoresDataDetails");
                                }

                                List<TblStoreImageList> tblStoreImageListDetails = allMasterTablesModel.getTblStoreImageList();

                                if (tblStoreImageListDetails.size() > 0) {

                                    dbengine.savetblStoreImageListDetails(tblStoreImageListDetails);
                                } else {
                                    blankTablearrayList.add("tblStoreImageListDetails");
                                }
                            }

                            //table 18-------------------------------
                            if (allMasterTablesModel.getTblStateCityMaster() != null) {
                                dbengine.deletetblStateCityMaster();
                                List<TblStateCityMaster> tblStateCityMaster = allMasterTablesModel.getTblStateCityMaster();

                                if (tblStateCityMaster.size() > 0) {
                                    for (TblStateCityMaster StateCityMaster : tblStateCityMaster) {
                                        dbengine.fnsavetblStateCityMaster("" + StateCityMaster.getStateID(), StateCityMaster.getState(), "" + StateCityMaster.getCityID(), StateCityMaster.getCity(), StateCityMaster.getCityDefault());
                                    }
                                } else {
                                    blankTablearrayList.add("tblStateCityMaster");
                                }
                            }

                            //table 19-------------------------------
                            if (allMasterTablesModel.getTblProductListMaster() != null) {
                                dbengine.Delete_tblProductList_for_refreshData();
                                List<TblProductListMaster> tblProductListMaster = allMasterTablesModel.getTblProductListMaster();

                                if (tblProductListMaster.size() > 0) {

                                    dbengine.saveSOAPdataProductListRetrofit(tblProductListMaster);

                                } else {
                                    blankTablearrayList.add("tblProductListMaster");
                                }
                            }

                            //table 20-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblProductSegementMap() != null) {
                                List<TblProductSegementMap> tblProductSegementMap = allMasterTablesModel.getTblProductSegementMap();

                                if (tblProductSegementMap.size() > 0) {

                                    dbengine.saveProductSegementMapRetrofit(tblProductSegementMap);

                                } else {
                                    blankTablearrayList.add("tblProductSegementMap");
                                }
                            }

                            //table 21-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblPriceApplyType() != null) {
                                List<TblPriceApplyType> tblPriceApplyType = allMasterTablesModel.getTblPriceApplyType();

                                if (tblPriceApplyType.size() > 0) {
                                    for (TblPriceApplyType priceApplyType : tblPriceApplyType) {
                                        dbengine.savetblPriceApplyType(priceApplyType.getDiscountLevel(), priceApplyType.getCutoffvalue());
                                    }

                                } else {
                                    blankTablearrayList.add("tblPriceApplyType");
                                }
                            }

                            //table 22-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblUOMMaster() != null) {
                                List<TblUOMMaster> tblUOMMaster = allMasterTablesModel.getTblUOMMaster();

                                if (tblUOMMaster.size() > 0) {
                                    for (TblUOMMaster priceApplyType : tblUOMMaster) {
                                        dbengine.insertUOMMstr(priceApplyType.getBUOMID(), priceApplyType.getBUOMName(), priceApplyType.getFlgRetailUnit());
                                    }

                                } else {
                                    blankTablearrayList.add("tblUOMMaster");
                                }
                            }
                            //table 23-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblUOMMapping() != null) {
                                List<TblUOMMapping> tblUOMMapping = allMasterTablesModel.getTblUOMMapping();

                                if (tblUOMMapping.size() > 0) {
                                    for (TblUOMMapping UOMMapping : tblUOMMapping) {
                                        dbengine.insertUOMMapping(UOMMapping.getNodeID(), UOMMapping.getNodeType(), UOMMapping.getBaseUOMID(), UOMMapping.getPackUOMID(), UOMMapping.getRelConversionUnits(), UOMMapping.getFlgVanLoading());
                                    }

                                } else {
                                    blankTablearrayList.add("tblUOMMapping");
                                }
                            }

                            //table 24-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblManagerMstr() != null) {
                                dbengine.delete_tblManagerMstr();
                                List<TblManagerMstr> tblManagerMstr = allMasterTablesModel.getTblManagerMstr();

                                if (tblManagerMstr.size() > 0) {
                                    for (TblManagerMstr ManagerMstr : tblManagerMstr) {
                                        dbengine.savetblManagerMstr("" + ManagerMstr.getPersonID(), "" + ManagerMstr.getPersonType(), ManagerMstr.getPersonName(), "" + ManagerMstr.getManagerID(), "" + ManagerMstr.getManagerType(), ManagerMstr.getManagerName());
                                    }

                                } else {
                                    blankTablearrayList.add("tblManagerMstr");
                                }
                            }
                            //table 25-------------------------------
                            //deleted above
                            if (allMasterTablesModel.getTblCategoryMaster() != null) {
                                dbengine.Delete_tblCategory_for_refreshData();
                                List<TblCategoryMaster> tblCategoryMaster = allMasterTablesModel.getTblCategoryMaster();

                                if (tblCategoryMaster.size() > 0) {
                                    for (TblCategoryMaster CategoryMaster : tblCategoryMaster) {
                                        dbengine.saveCategory(CategoryMaster.getNODEID(), CategoryMaster.getCATEGORY(), 0);
                                    }

                                } else {
                                    blankTablearrayList.add("tblCategoryMaster");
                                }
                            }

                            //table 26-------------------------------
                            if (allMasterTablesModel.getTblBankMaster() != null) {
                                dbengine.deletetblBankMaster();
                                List<TblBankMaster> tblBankMaster = allMasterTablesModel.getTblBankMaster();

                                if (tblBankMaster.size() > 0) {

                                    dbengine.savetblBankMaster(tblBankMaster);

                                } else {
                                    blankTablearrayList.add("tblBankMaster");
                                }
                            }
                            //table 27-------------------------------
                            //deleted above

                            if (allMasterTablesModel.getTblInstrumentMaster() != null) {
                                List<TblInstrumentMaster> tblInstrumentMaster = allMasterTablesModel.getTblInstrumentMaster();

                                if (tblInstrumentMaster.size() > 0) {
                                    for (TblInstrumentMaster instrumentMaster : tblInstrumentMaster) {
                                        dbengine.savetblInstrumentMaster(instrumentMaster.getInstrumentModeId(), instrumentMaster.getInstrumentMode(), instrumentMaster.getInstrumentType());
                                    }

                                } else {
                                    blankTablearrayList.add("tblInstrumentMaster");
                                }
                            }


                            dbengine.delete_tblRptDistribution();
                            List<TblRptDistribution> tblRptDistributionList = allMasterTablesModel.getTblRptDistribution();
                            if (tblRptDistributionList!=null && tblRptDistributionList.size() > 0) {

                                dbengine.insert_tblRptDistribution(tblRptDistributionList);
                            } else {
                                blankTablearrayList.add("TblRptDistribution");
                            }


                            dbengine.delete_tblRptSecVol();
                            List<TblRptSecVol> tblRptSecVolList = allMasterTablesModel.getTblRptSecVol();
                            if (tblRptSecVolList!=null && tblRptSecVolList.size() > 0) {

                                dbengine.insert_tblRptSecVol(tblRptSecVolList);
                            } else {
                                blankTablearrayList.add("tblRptSecVol");
                            }


                            dbengine.delete_tblRptManDays();
                            List<TblRptManDays> tblRptManDaysList = allMasterTablesModel.getTblRptManDays();
                            if (tblRptManDaysList!=null && tblRptManDaysList.size() > 0) {

                                dbengine.insert_tblRptManDays(tblRptManDaysList);
                            } else {
                                blankTablearrayList.add("tblRptManDays");
                            }
                            //table 28-------------------------------

                            List<TblRptDistributionDate> tblRptDistributionDates = allMasterTablesModel.getTblRptDistributionDate();
                            // mPreferenceManager.setValue(SPConstants.YESTERDAY_DATE,tblRptDistributionDates.get(0).getRptDate());


                            //table 29-------------------------------
                            if (allMasterTablesModel.getTblCycleID() != null) {
                                dbengine.deleteCompleteDataDistStock();

                                List<TblCycleID> tblCycleID = allMasterTablesModel.getTblCycleID();
                                if (CommonInfo.flgDrctslsIndrctSls == 1) {
                                    if (tblCycleID.size() > 0) {
                                        for (TblCycleID CycleID : tblCycleID) {
                                            dbengine.insertCycleId(CycleID.getCycleID(), CycleID.getCycStartTime(), CycleID.getCycleTime());
                                        }
                                    } else {
                                        blankTablearrayList.add("tblCycleID");
                                    }
                                }
                            }

                            dbengine.delete_tblCoverageDsr();
                            List<TblCoverageDsr> tblCoverageDsr = allMasterTablesModel.getTblCoverageDsr();
                            if (tblCoverageDsr!=null && tblCoverageDsr.size() > 0) {

                                dbengine.insertintotblCoveragedsr(tblCoverageDsr);
                            } else {
                                blankTablearrayList.add("tblCoverage");
                            }
                            List<TblCoverageMaster> tblCoverageMaster = allMasterTablesModel.getTblCoverage();
                            if (tblCoverageMaster!=null && tblCoverageMaster.size() > 0) {

                                dbengine.insertintotblCoverageMstr(tblCoverageMaster);
                            } else {
                                blankTablearrayList.add("tblCoverageMaster");
                            }

                            //table 30-------------------------------
                            //deleted above

                            if(CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")!=null && CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")!=0)
                            {

                                dbengine.Delete_tblStockUploadedStatus();

                                List<TblStockUploadedStatus> tblStockUploadedStatus=  allMasterTablesModel.getTblStockUploadedStatus();

                                if(tblStockUploadedStatus.size()>0){
                                    for(TblStockUploadedStatus StockUploadedStatus:tblStockUploadedStatus){
                                        dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(),StockUploadedStatus.getVanLoadUnLoadCycID(),StockUploadedStatus.getCycleTime(),StockUploadedStatus.getStatusID(),StockUploadedStatus.getFlgDayEnd());
                                    }

                                }
                                else{
                                    blankTablearrayList.add("tblStockUploadedStatus");
                                }

                                List<TblVanStockOutFlg> tblVanStockOutFlg=  allMasterTablesModel.getTblVanStockOutFlg();

                                if(tblVanStockOutFlg.size()>0){
                                    for(TblVanStockOutFlg VanStockOutFlg:tblVanStockOutFlg){
                                        dbengine.insertStockOut(VanStockOutFlg.getFlgStockOutEntryDone());
                                    }
                                }
                                else{
                                    blankTablearrayList.add("tblDistributorStockOutFlg");
                                }

//deleted above
                                List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft=  allMasterTablesModel.getTblVanIDOrderIDLeft();

                                if(tblVanIDOrderIDLeft.size()>0){
                                    for(TblVanIDOrderIDLeft DistributorIDOrderIDLeft:tblVanIDOrderIDLeft){
                                        dbengine.insertDistributorLeftOrderId(DistributorIDOrderIDLeft.getCustomer(),DistributorIDOrderIDLeft.getPDAOrderId(),DistributorIDOrderIDLeft.getFlgInvExists());
                                    }
                                }
                                else{
                                    blankTablearrayList.add("tblVanIDOrderIDLeft");
                                }

                                List<TblVanProductStock> tblVanProductStock=  allMasterTablesModel.getTblVanProductStock();

                                if(tblVanProductStock.size()>0){
                                    // if(CommonInfo.flgDrctslsIndrctSls==1) {
                                    dbengine.insertDistributorStock(tblVanProductStock);
                                    //  if(CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate")==1 ) {
                                    int statusId = dbengine.confirmedStock();
                                    if (statusId == 2) {
                                        dbengine.insertConfirmWArehouse(tblVanProductStock.get(0).getCustomer(), "1");
                                        dbengine.inserttblDayCheckIn(1);
                                    }
                                    // }
                                    //  }

                                }
                                else{
                                    blankTablearrayList.add("tblVanProductStock");
                                }
                            }
                            //deleted above
                            if (allMasterTablesModel.getTblVanIDOrderIDLeft() != null) {
                                List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = allMasterTablesModel.getTblVanIDOrderIDLeft();

                                if (tblVanIDOrderIDLeft.size() > 0) {
                                    for (TblVanIDOrderIDLeft DistributorIDOrderIDLeft : tblVanIDOrderIDLeft) {
                                        dbengine.insertDistributorLeftOrderId(DistributorIDOrderIDLeft.getCustomer(), DistributorIDOrderIDLeft.getPDAOrderId(), DistributorIDOrderIDLeft.getFlgInvExists());
                                    }
                                } else {
                                    blankTablearrayList.add("tblVanIDOrderIDLeft");
                                }
                            }
                            //table 32-------------------------------
                            if (allMasterTablesModel.getTblInvoiceCaption() != null) {
                                dbengine.Delete_tblInvoiceCaption();
                                List<TblInvoiceCaption> tblInvoiceCaption = allMasterTablesModel.getTblInvoiceCaption();

                                if (tblInvoiceCaption.size() > 0) {
                                    for (TblInvoiceCaption InvoiceCaption : tblInvoiceCaption) {
                                        dbengine.savetblInvoiceCaption(InvoiceCaption.getInvPrefix(), InvoiceCaption.getVanIntialInvoiceIds(), InvoiceCaption.getInvSuffix());
                                    }
                                } else {
                                    blankTablearrayList.add("tblInvoiceCaption");
                                }
                            }

                            //table 33-------------------------------
                            if (allMasterTablesModel.getTblGetReturnsReasonForPDAMstr() != null) {
                                dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                                List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr = allMasterTablesModel.getTblGetReturnsReasonForPDAMstr();

                                if (tblGetReturnsReasonForPDAMstr.size() > 0) {
                                    for (TblGetReturnsReasonForPDAMstr GetReturnsReasonForPDAMstr : tblGetReturnsReasonForPDAMstr) {
                                        dbengine.fnInsertTBLReturnRsn(GetReturnsReasonForPDAMstr.getStockStatusId(), GetReturnsReasonForPDAMstr.getStockStatus());
                                    }
                                } else {
                                    blankTablearrayList.add("tblGetReturnsReasonForPDAMstr");
                                }
                            }
                            //table 34-------------------------------
                            if (allMasterTablesModel.getTblIsSchemeApplicable() != null) {
                                dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                                List<TblIsSchemeApplicable> tblIsSchemeApplicable = allMasterTablesModel.getTblIsSchemeApplicable();

                                if (tblIsSchemeApplicable.size() > 0) {
                                    for (TblIsSchemeApplicable IsSchemeApplicable : tblIsSchemeApplicable) {
                                        dbengine.SavePDAIsSchemeApplicable(IsSchemeApplicable.getIsSchemeApplicable());
                                    }
                                } else {
                                    blankTablearrayList.add("tblIsSchemeApplicable");
                                }
                            }
                            //table 35-------------------------------

                            //table 36-------------------------------
                            if (allMasterTablesModel.getTblSupplierMstrList() != null) {
                                dbengine.Delete_tblSupplierMstrList();
                                List<TblSupplierMstrList> tblSupplierMstrList = allMasterTablesModel.getTblSupplierMstrList();

                                if (tblSupplierMstrList.size() > 0) {
                                    for (TblSupplierMstrList SupplierMstrList : tblSupplierMstrList) {
                                        dbengine.saveSuplierMstrData(SupplierMstrList.getNodeID(), SupplierMstrList.getNodeType(), SupplierMstrList.getDescr(), SupplierMstrList.getLatCode(), SupplierMstrList.getLongCode(), SupplierMstrList.getFlgMapped(), SupplierMstrList.getAddress(), SupplierMstrList.getState(), SupplierMstrList.getCity(), SupplierMstrList.getPinCode(), SupplierMstrList.getPhoneNo(), SupplierMstrList.getTaxNumber(), SupplierMstrList.getEMailID(), SupplierMstrList.getFlgStockManage(), SupplierMstrList.getFlgDefault());
                                    }
                                } else {
                                    blankTablearrayList.add("tblSupplierMstrList");
                                }
                            }
                            //Nitish--------------------------------------------------

                            //table 51-------------------------------
                            if (allMasterTablesModel.getTblLastOutstanding() != null) {
                                dbengine.Delete_tblLastOutstanding_for_refreshData();
                                //deleted above
                                List<TblLastOutstanding> tblLastOutstanding = allMasterTablesModel.getTblLastOutstanding();

                                if (tblLastOutstanding.size() > 0) {

                                    dbengine.savetblLastOutstanding(tblLastOutstanding);

                                } else {
                                    blankTablearrayList.add("tblLastOutstanding");
                                }
                            }
                            //table 50-------------------------------
                            if (allMasterTablesModel.getTblInvoiceLastVisitDetails() != null) {
                                List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails = allMasterTablesModel.getTblInvoiceLastVisitDetails();

                                if (tblInvoiceLastVisitDetails.size() > 0) {

                                    dbengine.savetblInvoiceLastVisitDetails(tblInvoiceLastVisitDetails);

                                } else {
                                    blankTablearrayList.add("tblInvoiceLastVisitDetails");
                                }
                            }


                            //table 49-------------------------------
                            if (allMasterTablesModel.getTblPDAGetExecutionSummary() != null) {
                                dbengine.deltblPDAGetExecutionSummary();
                                //deleted above
                                List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary = allMasterTablesModel.getTblPDAGetExecutionSummary();

                                if (tblPDAGetExecutionSummary.size() > 0) {

                                    dbengine.inserttblForPDAGetExecutionSummary(tblPDAGetExecutionSummary);

                                } else {
                                    blankTablearrayList.add("tblPDAGetExecutionSummary");
                                }
                            }


                            //table 48-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLastOrderDetailsTotalValues() != null) {
                                dbengine.deletetblPDAGetLastOrderDetailsTotalValues();
                                //deleted above
                                List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues = allMasterTablesModel.getTblPDAGetLastOrderDetailsTotalValues();

                                if (tblPDAGetLastOrderDetailsTotalValues.size() > 0) {

                                    dbengine.inserttblspForPDAGetLastOrderDetails_TotalValues(tblPDAGetLastOrderDetailsTotalValues);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLastOrderDetailsTotalValues");
                                }
                            }


                            //table 47-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLastOrderDetails() != null) {
                                dbengine.deltblPDAGetLastOrderDetailsData();
                                //deleted above
                                List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails = allMasterTablesModel.getTblPDAGetLastOrderDetails();

                                if (tblPDAGetLastOrderDetails.size() > 0) {

                                    dbengine.inserttblForPDAGetLastOrderDetails(tblPDAGetLastOrderDetails);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLastOrderDetails");
                                }
                            }

                            //table 46-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLastVisitDetails() != null) {
                                dbengine.deletetblPDAGetLastVisitDetails();
                                //deleted above
                                List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails = allMasterTablesModel.getTblPDAGetLastVisitDetails();

                                if (tblPDAGetLastVisitDetails.size() > 0) {

                                    dbengine.inserttblForPDAGetLastVisitDetails(tblPDAGetLastVisitDetails);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLastVisitDetails");
                                }
                            }

                            //table 45-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLastOrderDate() != null) {
                                dbengine.deletetblPDAGetLastOrderDateData();
                                //deleted above
                                List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate = allMasterTablesModel.getTblPDAGetLastOrderDate();

                                if (tblPDAGetLastOrderDate.size() > 0) {

                                    dbengine.inserttblForPDAGetLastOrderDate(tblPDAGetLastOrderDate);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLastOrderDate");
                                }
                            }
                            //table 44-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLastVisitDate() != null) {
                                dbengine.deletetblPDAGetLastVisitDate();
                                //deleted above
                                List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate = allMasterTablesModel.getTblPDAGetLastVisitDate();

                                if (tblPDAGetLastVisitDate.size() > 0) {

                                    dbengine.inserttblForPDAGetLastVisitDate(tblPDAGetLastVisitDate);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLastVisitDate");
                                }
                            }
                            //table 43-------------------------------
                            if (allMasterTablesModel.getTblPDAGetLODQty() != null) {
                                dbengine.deletetblPDAGetLODQty();
                                //deleted above
                                List<TblPDAGetLODQty> tblPDAGetLODQty = allMasterTablesModel.getTblPDAGetLODQty();

                                if (tblPDAGetLODQty.size() > 0) {

                                    dbengine.inserttblLODOnLastSalesSummary(tblPDAGetLODQty);

                                } else {
                                    blankTablearrayList.add("tblPDAGetLODQty");
                                }
                            }
                            //table 42-------------------------------
                            if (allMasterTablesModel.getTblProductListLastVisitStockOrOrderMstr() != null) {
                                dbengine.deletetblProductListLastVisitStockOrOrderMstr();
                                //deleted above
                                List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr = allMasterTablesModel.getTblProductListLastVisitStockOrOrderMstr();

                                if (tblProductListLastVisitStockOrOrderMstr.size() > 0) {

                                    dbengine.savetblProductListLastVisitStockOrOrderMstr(tblProductListLastVisitStockOrOrderMstr);

                                } else {
                                    blankTablearrayList.add("tblProductListLastVisitStockOrOrderMstr");
                                }
                            }


                            //table 41-------------------------------
                            if (allMasterTablesModel.getTblStoreListMaster() != null) {
                                HashMap<String, String> hmapStoreIdSstat = new HashMap<String, String>();
                                hmapStoreIdSstat = dbengine.checkForStoreIdSstat();
                                HashMap<String, String> hmapflgOrderType = new HashMap<String, String>();
                                hmapflgOrderType = dbengine.checkForStoreflgOrderType();
                                HashMap<String, String> hmapStoreIdNewStore = new HashMap<String, String>();
                                hmapStoreIdNewStore = dbengine.checkForStoreIdIsNewStore();
                                //  dbengine.Delete_tblStore_for_refreshDataButNotNewStore();
                                //    dbengine.fndeleteStoreAddressMapDetailsMstr();

                                //deleted above
                                List<TblStoreListMaster> tblStoreListMaster = allMasterTablesModel.getTblStoreListMaster();

                                if (tblStoreListMaster.size() > 0) {

                                    dbengine.saveSOAPdataStoreList(tblStoreListMaster, hmapStoreIdSstat, hmapflgOrderType, hmapStoreIdNewStore);

                                } else {
                                    blankTablearrayList.add("tblStoreListMaster");
                                }

                                if (hmapStoreIdSstat != null && hmapStoreIdSstat.size() > 0) {
                                    hmapStoreIdSstat.clear();
                                    hmapflgOrderType.clear();
                                    hmapStoreIdNewStore.clear();
                                    hmapStoreIdSstat = null;
                                    hmapflgOrderType = null;
                                    hmapStoreIdNewStore = null;
                                }
                            }


                            //table 40-------------------------------
                            if (allMasterTablesModel.getTblStoreListWithPaymentAddress() != null) {
                                //deleted above
                                List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress = allMasterTablesModel.getTblStoreListWithPaymentAddress();

                                if (tblStoreListWithPaymentAddress.size() > 0) {

                                    dbengine.saveSOAPdataStoreListAddressMap(tblStoreListWithPaymentAddress);

                                } else {
                                    blankTablearrayList.add("tblStoreListWithPaymentAddressMR");
                                }
                            }


                            //table 40-------------------------------

                            //deleted above
                            if (allMasterTablesModel.getTblStoreSomeProdQuotePriceMstr() != null) {
                                List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr = allMasterTablesModel.getTblStoreSomeProdQuotePriceMstr();

                                if (tblStoreSomeProdQuotePriceMstr.size() > 0) {

                                    dbengine.insertMinDelQty(tblStoreSomeProdQuotePriceMstr);

                                } else {
                                    blankTablearrayList.add("tblStoreSomeProdQuotePriceMstr");
                                }
                            }

                            //table 39-------------------------------

                            //deleted above
                            if (allMasterTablesModel.getTblStoreLastDeliveryNoteNumber() != null) {
                                List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber = allMasterTablesModel.getTblStoreLastDeliveryNoteNumber();

                                if (tblStoreLastDeliveryNoteNumber.size() > 0) {

                                    for (TblStoreLastDeliveryNoteNumber tblStoreLastDeliveryNoteNumberData : tblStoreLastDeliveryNoteNumber) {
                                        int LastDeliveryNoteNumber = 0;
                                        LastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumberData.getLastDeliveryNoteNumber();
                                        int valExistingDeliveryNoteNumber = 0;
                                        valExistingDeliveryNoteNumber = dbengine.fnGettblStoreLastDeliveryNoteNumber();
                                        if (valExistingDeliveryNoteNumber < LastDeliveryNoteNumber) {
                                            dbengine.Delete_tblStoreLastDeliveryNoteNumber();
                                            dbengine.savetblStoreLastDeliveryNoteNumber(LastDeliveryNoteNumber);
                                        }
                                    }


                                } else {
                                    blankTablearrayList.add("tblStoreLastDeliveryNoteNumber");
                                }
                            }


                            HashMap<String, String> hmapInvoiceOrderIDandStatus = new HashMap<String, String>();
                            hmapInvoiceOrderIDandStatus = dbengine.fetchHmapInvoiceOrderIDandStatus();
                            if (allMasterTablesModel.getTblPendingInvoices() != null) {
                                List<TblPendingInvoices> tblPendingInvoices = allMasterTablesModel.getTblPendingInvoices();

                                if (tblPendingInvoices.size() > 0) {

                                    dbengine.inserttblPendingInvoices(tblPendingInvoices, hmapInvoiceOrderIDandStatus);

                                } else {
                                    blankTablearrayList.add("tblPendingInvoices");
                                }
                            }


                            if (allMasterTablesModel.getTblInvoiceExecutionProductList() != null) {

                                List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = allMasterTablesModel.getTblInvoiceExecutionProductList();
                                dbengine.fnDeletetblInvoiceExecutionProductList();
                                if (tblInvoiceExecutionProductList.size() > 0) {

                                    dbengine.inserttblInvoiceExecutionProductList(tblInvoiceExecutionProductList);

                                } else {
                                    blankTablearrayList.add("tblInvoiceExecutionProductList");
                                }
                            }


                            if (allMasterTablesModel.getTblProductWiseInvoice() != null) {
                                List<TblProductWiseInvoice> tblProductWiseInvoice = allMasterTablesModel.getTblProductWiseInvoice();
                                if (tblProductWiseInvoice.size() > 0) {

                                    dbengine.inserttblProductWiseInvoice(tblProductWiseInvoice, hmapInvoiceOrderIDandStatus);

                                } else {
                                    blankTablearrayList.add("tblProductWiseInvoice");
                                }
                            }

                            //nitishtable
                            boolean isDistributorStockSave = false;
                    if(flgCldFrm==0)
                    {
                        isDistributorStockSave=true;
                    }
                    else if((flgCldFrm==1) &&(CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate")==1))
                    {
                        isDistributorStockSave=true;
                    }
                            if (isDistributorStockSave) {
                                if (allMasterTablesModel.getTblDistributorIDOrderIDLeft() != null) {
                                    dbengine.deleteDistStock();
                                    List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft = allMasterTablesModel.getTblDistributorIDOrderIDLeft();
                                    if (tblDistributorIDOrderIDLeft.size() > 0) {

                                        for (TblDistributorIDOrderIDLeft tblDistributorIDOrderIDLeftData : tblDistributorIDOrderIDLeft) {
                                            dbengine.inserttblDistributorIDOrderIDLeft(tblDistributorIDOrderIDLeftData.getCustomer(), tblDistributorIDOrderIDLeftData.getPDAOrderId());
                                        }


                                    } else {
                                        blankTablearrayList.add("tblDistributorIDOrderIDLeft");
                                    }
                                }

                                if (allMasterTablesModel.getTblDistributorProductStock() != null) {
                                    List<TblDistributorProductStock> tblDistributorProductStock = allMasterTablesModel.getTblDistributorProductStock();
                                    if (tblDistributorProductStock.size() > 0) {


                                        dbengine.inserttblDistributorProductStock(tblDistributorProductStock);

                                    } else {
                                        blankTablearrayList.add("tblDistributorProductStock");
                                    }
                                }

                            }


                            if (allMasterTablesModel.getTblReasonOrderCncl() != null) {
                                dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();

                                hmapInvoiceOrderIDandStatus = null;

                                dbengine.delTblReasonOrderCncl();
                                List<TblReasonOrderCncl> tblReasonOrderCncl = allMasterTablesModel.getTblReasonOrderCncl();

                                if (tblReasonOrderCncl.size() > 0) {
                                    for (TblReasonOrderCncl tblReasonOrderCnclModel : tblReasonOrderCncl) {
                                        dbengine.insertReasonCanclOrder(tblReasonOrderCnclModel.getReasonCodeID(), tblReasonOrderCnclModel.getReasonDescr());
                                    }
                                } else {
                                    blankTablearrayList.add("tblReasonOrderCncl");
                                }
                            }


                /*    dbengine.deleteIncentivesTbles();


                    List<TblIncentiveMainMaster> tblIncentiveMaster=  allMasterTablesModel.getTblIncentiveMainMaster();

                    if(tblIncentiveMaster.size()>0){
                        for(TblIncentiveMainMaster tblIncentiveMasterModel:tblIncentiveMaster){
                            dbengine.savetblIncentiveMaster(tblIncentiveMasterModel.getIncId(),tblIncentiveMasterModel.getOutputType(),tblIncentiveMasterModel.getIncentiveName(),""+tblIncentiveMasterModel.getFlgAcheived(),""+tblIncentiveMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveMaster");
                    }

                    List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster=  allMasterTablesModel.getTblIncentiveSecondaryMaster();

                    if(tblIncentiveSecondaryMaster.size()>0){
                        for(TblIncentiveSecondaryMaster tblIncentiveSecondaryMasterModel:tblIncentiveSecondaryMaster){
                            dbengine.savetblIncentiveSeondaryMaster(tblIncentiveSecondaryMasterModel.getIncSlabId(),tblIncentiveSecondaryMasterModel.getIncId(), tblIncentiveSecondaryMasterModel.getOutputType(), tblIncentiveSecondaryMasterModel.getIncSlabName(),""+tblIncentiveSecondaryMasterModel.getFlgAcheived(),""+tblIncentiveSecondaryMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveSecondaryMaster");
                    }
                    Object tblIncentiveDetailsData=allMasterTablesModel.getTblIncentiveDetailsData();
                    ArrayList<HashMap<String,String>> tblIncentiveDetailsDataTable=(ArrayList<HashMap<String,String>>) tblIncentiveDetailsData;

                    if( tblIncentiveDetailsDataTable.size()>0) {
                        for(int i=0;i<10;i++)
                        {
                           String sdqeqwe= tblIncentiveDetailsDataTable.get(0).toString();
                        }
                    }


*/

                            if (allMasterTablesModel.getTblQuestionsSurvey() != null) {

                                dbengine.deleteSurveyTables();
                                List<TblQuestionsSurvey> tblQuestionsSurvey = allMasterTablesModel.getTblQuestionsSurvey();

                                if (tblQuestionsSurvey.size() > 0) {
                                    for (TblQuestionsSurvey tblQuestionsSurveyModel : tblQuestionsSurvey) {
                                        dbengine.fnsavetblQuestionsSurvey("" + tblQuestionsSurveyModel.getQstnID(), tblQuestionsSurveyModel.getQstnText(), "" + tblQuestionsSurveyModel.getFlgActive(), "" + tblQuestionsSurveyModel.getFlgOrder());
                                    }
                                } else {
                                    blankTablearrayList.add("tblQuestionsSurvey");
                                }
                            }

                            if (allMasterTablesModel.getTblOptionSurvey() != null) {
                                List<TblOptionSurvey> tblOptionSurvey = allMasterTablesModel.getTblOptionSurvey();

                                if (tblOptionSurvey.size() > 0) {
                                    for (TblOptionSurvey tblOptionSurveyModel : tblOptionSurvey) {
                                        dbengine.fnsavetblOptionSurvey("" + tblOptionSurveyModel.getQstnID(), tblOptionSurveyModel.getOptionText(), "" + tblOptionSurveyModel.getQstnID(), "" + tblOptionSurveyModel.getFlgaActive());
                                    }
                                } else {
                                    blankTablearrayList.add("tblOptionSurvey");
                                }
                            }


                            dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            if (progressHUD != null && progressHUD.isShowing())
                                progressHUD.dismiss();
                            interfaceRetrofit.success();
                        }
                    }.execute();
                    // sendIntentToOtherActivityAfterAllDataFetched();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllMasterTablesModel> call, Throwable t) {
                System.out.println();
                t.printStackTrace();
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();//t
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllSummaryReportData(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryReportDay> call = apiService.Call_AllSummaryReportDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryReportDay>() {
            @Override
            public void onResponse(Call<AllSummaryReportDay> call, Response<AllSummaryReportDay> response) {
                if (response.code() == 200) {
                    AllSummaryReportDay allSummaryReportDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateAllSummaryDayDataTable();
                    List<TblAllSummaryDay> tblAllSummaryDay = allSummaryReportDayModel.getTblAllSummaryDay();
                    if (tblAllSummaryDay.size() > 0) {
                        dbengine.savetblAllSummaryDayAndMTD(tblAllSummaryDay);
                    } else {
                        blankTablearrayList.add("tblAllSummaryDay");
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryReportDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummarySKUWiseDay> call = apiService.Call_AllSummarySKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummarySKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummarySKUWiseDay> call, Response<AllSummarySKUWiseDay> response) {
                if (response.code() == 200) {
                    AllSummarySKUWiseDay allSummarySKUWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateSKUDataTable();
                    List<TblSKUWiseDaySummary> tblSKUWiseDaySummary = allSummarySKUWiseDayModel.getTblSKUWiseDaySummary();
                    if (tblSKUWiseDaySummary.size() > 0) {
                        dbengine.savetblSKUWiseDaySummary(tblSKUWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummarySKUWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllStoreWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreWiseDay> call = apiService.Call_AllSummaryStoreWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreWiseDay> call, Response<AllSummaryStoreWiseDay> response) {
                if (response.code() == 200) {
                    AllSummaryStoreWiseDay allSummaryStoreWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreWiseDataTable();
                    List<TblStoreWiseDaySummary> tblStoreWiseDaySummary = allSummaryStoreWiseDayModel.getTblStoreWiseDaySummary();
                    if (tblStoreWiseDaySummary.size() > 0) {
                        dbengine.savetblStoreWiseDaySummary(tblStoreWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllStoreSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreSKUWiseDay> call = apiService.Call_AllSummaryStoreSKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreSKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreSKUWiseDay> call, Response<AllSummaryStoreSKUWiseDay> response) {
                if (response.code() == 200) {
                    AllSummaryStoreSKUWiseDay allSummaryStoreSKUWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreAndSKUWiseDataTable();
                    List<TblStoreSKUWiseDaySummary> tblStoreSKUWiseDaySummary = allSummaryStoreSKUWiseDayModel.getTblStoreSKUWiseDaySummary();
                    if (tblStoreSKUWiseDaySummary.size() > 0) {
                        dbengine.savetblStoreSKUWiseDaySummary(tblStoreSKUWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblStoreSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreSKUWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getAllTargetVsAcheivedData(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<AllTargetVsAchieved> call = apiService.Call_AllTargetVsAchieved(data);
        call.enqueue(new Callback<AllTargetVsAchieved>() {
            @Override
            public void onResponse(Call<AllTargetVsAchieved> call, Response<AllTargetVsAchieved> response) {
                if (response.code() == 200) {
                    AllTargetVsAchieved allTargetVsAchievedModel = response.body();


                    dbengine.truncatetblTargetVsAchievedSummary();

                    List<TblActualVsTargetReport> tblActualVsTargetReport = allTargetVsAchievedModel.getTblActualVsTargetReport();
                    if (tblActualVsTargetReport != null) {
                        if (tblActualVsTargetReport.size() > 0) {
                            dbengine.savetblTargetVsAchievedSummary(tblActualVsTargetReport);

                        } else {
                            blankTablearrayList.add("tblActualVsTargetReport");
                        }
                    }

                    //table 29-------------------------------

                    List<TblValueVolumeTarget> tblValueVolumeTarget = allTargetVsAchievedModel.getTblValueVolumeTarget();

                    if (tblValueVolumeTarget.size() > 0) {
                        dbengine.saveValueVolumeTarget(tblValueVolumeTarget);

                    } else {
                        blankTablearrayList.add("tblValueVolumeTarget");
                    }

                    List<TblActualVsTargetNote> tblActualVsTargetNote = allTargetVsAchievedModel.getTblActualVsTargetNote();
                    if (tblActualVsTargetNote != null) {
                        if (tblValueVolumeTarget.size() > 0) {
                            for (TblActualVsTargetNote tblActualVsTargetNoteData : tblActualVsTargetNote) {
                                dbengine.savetblTargetVsAchievedNote(tblActualVsTargetNoteData.getMsgToDisplay());
                            }

                        } else {
                            blankTablearrayList.add("tblActualVsTargetNote");
                        }
                    }


                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllTargetVsAchieved> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getAllAddedOutletSummaryReportModel(Context context, final String imei, String RegistrationID, String msgToShow, Integer flgDrillLevel) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsAddedOutletSummary reportsAddedOutletSummary = new ReportsAddedOutletSummary();
        reportsAddedOutletSummary.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsAddedOutletSummary.setIMEINo(imei);
        reportsAddedOutletSummary.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsAddedOutletSummary.setFlgDrillLevel(flgDrillLevel);
        reportsAddedOutletSummary.setForDate(fDate);

        Call<AllAddedOutletSummaryReportModel> call = apiService.Call_AllPDAGetAddedOutletSummaryReport(reportsAddedOutletSummary);
        call.enqueue(new Callback<AllAddedOutletSummaryReportModel>() {
            @Override
            public void onResponse(Call<AllAddedOutletSummaryReportModel> call, Response<AllAddedOutletSummaryReportModel> response) {
                if (response.code() == 200) {
                    AllAddedOutletSummaryReportModel allAddedOutletSummaryReportModelModel = response.body();


                    dbengine.droptblDAGetAddedOutletSummaryReport();
                    dbengine.createtblDAGetAddedOutletSummaryReport();

                    List<TblDAGetAddedOutletSummaryReport> tblDAGetAddedOutletSummaryReport = allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryReport();

                    if (tblDAGetAddedOutletSummaryReport.size() > 0) {
                        dbengine.savetblDAGetAddedOutletSummaryReport(tblDAGetAddedOutletSummaryReport);

                    } else {
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryReport");
                    }

                    //table 29-------------------------------

                    List<TblDAGetAddedOutletSummaryOverallReport> tblDAGetAddedOutletSummaryOverallReport = allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryOverallReport();

                    if (tblDAGetAddedOutletSummaryOverallReport.size() > 0) {
                        dbengine.savetblDAGetAddedOutletSummaryOverallReport(tblDAGetAddedOutletSummaryOverallReport);

                    } else {
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryOverallReport");
                    }
                    dbengine.fetchtblDAGetAddedOutletSummaryReport();
                    dbengine.fetchtblDAGetAddedOutletOverAllData();

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllAddedOutletSummaryReportModel> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getStockData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final MultipleInterfaceRetrofit multplinterfaceRetrofit = (MultipleInterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);

        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<StockData> call = apiService.Call_StockData(data);
        call.enqueue(new Callback<StockData>() {
            @Override
            public void onResponse(Call<StockData> call, Response<StockData> response) {
                if (response.code() == 200) {
                    StockData stockData = response.body();
                    System.out.println("DATAENSERTEDSP");


                    //table 28-------------------------------
                    if (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {
                        dbengine.Delete_tblStockUploadedStatus();
                        dbengine.deleteVanConfirmFlag();
                        List<TblStockUploadedStatus> tblStockUploadedStatus = stockData.getTblStockUploadedStatus();

                        if (tblStockUploadedStatus.size() > 0) {
                            for (TblStockUploadedStatus StockUploadedStatus : tblStockUploadedStatus) {
                                dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(), StockUploadedStatus.getVanLoadUnLoadCycID(), StockUploadedStatus.getCycleTime(), StockUploadedStatus.getStatusID(), StockUploadedStatus.getFlgDayEnd());

                            }

                        } else {
                            blankTablearrayList.add("tblStockUploadedStatus");
                        }

                        //table 29-------------------------------

                        dbengine.deleteCompleteDataDistStock();

                        List<TblCycleID> tblCycleID = stockData.getTblCycleID();
                        if (CommonInfo.flgDrctslsIndrctSls == 1) {
                            if (tblCycleID.size() > 0) {
                                for (TblCycleID CycleID : tblCycleID) {
                                    dbengine.insertCycleId(CycleID.getCycleID(), CycleID.getCycStartTime(), CycleID.getCycleTime());
                                }
                            } else {
                                blankTablearrayList.add("tblCycleID");
                            }
                        }

                        //table 30-------------------------------
                        //deleted above
                        List<TblVanStockOutFlg> tblVanStockOutFlg = stockData.getTblVanStockOutFlg();

                        if (tblVanStockOutFlg.size() > 0) {
                            for (TblVanStockOutFlg VanStockOutFlg : tblVanStockOutFlg) {
                                dbengine.insertStockOut(VanStockOutFlg.getFlgStockOutEntryDone());
                            }
                        } else {
                            blankTablearrayList.add("tblVanStockOutFlg");
                        }
                        //table 31-------------------------------

                        List<TblVanProductStock> tblVanProductStock = stockData.getTblVanProductStock();

                        if (tblVanProductStock.size() > 0) {


                            //  if(CommonInfo.flgDrctslsIndrctSls==1) {
                            dbengine.insertDistributorStock(tblVanProductStock);
                            // if(CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate")==1 ) {
                            int statusId = dbengine.confirmedStock();
                            if (statusId == 2) {
                                dbengine.insertConfirmWArehouse(tblVanProductStock.get(0).getCustomer(), "1");
                                dbengine.inserttblDayCheckIn(1);
                            }
                            // }
                            // }


                        } else {
                            blankTablearrayList.add("tblVanProductStock");
                        }

                        //deleted above
                        List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = stockData.getTblVanIDOrderIDLeft();

                        if (tblVanIDOrderIDLeft.size() > 0) {
                            for (TblVanIDOrderIDLeft VanIDOrderIDLeft : tblVanIDOrderIDLeft) {
                                dbengine.insertDistributorLeftOrderId(VanIDOrderIDLeft.getCustomer(), VanIDOrderIDLeft.getPDAOrderId(), VanIDOrderIDLeft.getFlgInvExists());
                            }
                        } else {
                            blankTablearrayList.add("tblVanIDOrderIDLeft");
                        }

                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    //  interfaceRetrofit.success();
                    multplinterfaceRetrofit.success(flgCldFrm);
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    //  interfaceRetrofit.failure();
                    multplinterfaceRetrofit.success(flgCldFrm);
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<StockData> call, Throwable t) {

                // dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                // interfaceRetrofit.failure();
                multplinterfaceRetrofit.success(flgCldFrm);
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getDistributorTodayStock(Context context, final String imei, final int customerNodeId, final int customerNodeType) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        DistributorTodaysStock data = new DistributorTodaysStock();

        data.setiMEINo(imei);
        data.setCustomerNodeId(customerNodeId);
        data.setCustomerNodeType(customerNodeType);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);


        Call<DistributorStockData> call = apiService.Call_DistributorTodayStockData(data);
        call.enqueue(new Callback<DistributorStockData>() {
            @Override
            public void onResponse(Call<DistributorStockData> call, Response<DistributorStockData> response) {
                if (response.code() == 200) {
                    DistributorStockData stockData = response.body();
                    System.out.println("DATAENSERTEDSP");


                    List<TblDistributorDayReport> tblDistributorDayReport = stockData.getTblDistributorDayReport();

                    if (tblDistributorDayReport.size() > 0) {
                        for (TblDistributorDayReport tblDistributorDayReportData : tblDistributorDayReport) {
//   savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);
                            dbengine.savetblDistributorDayReport(tblDistributorDayReportData.getProductNodeID(), tblDistributorDayReportData.getProductNodeType(), tblDistributorDayReportData.getSKUName(), tblDistributorDayReportData.getFlvShortName(), tblDistributorDayReportData.getStockDate(), customerNodeId, customerNodeType);
                        }

                    } else {
                        blankTablearrayList.add("tblDistributorDayReport");
                    }


                    List<TblDistributorDayReportColumnsDesc> tblDistributorDayReportColumnsDesc = stockData.getTblDistributorDayReportColumnsDesc();

                    if (tblDistributorDayReportColumnsDesc.size() > 0) {
                        for (TblDistributorDayReportColumnsDesc tblDistributorDayReportColumnsDescData : tblDistributorDayReportColumnsDesc) {
                            //savetblDistributorDayReportColumnsDesc(DistDayReportCoumnName, DistDayReportColumnDisplayName,CustomerNodeID,CustomerNodeType);
                            dbengine.savetblDistributorDayReportColumnsDesc(tblDistributorDayReportColumnsDescData.getDistDayReportCoumnName(), tblDistributorDayReportColumnsDescData.getDistDayReportColumnDisplayName(), customerNodeId, customerNodeType);
                        }
                    } else {
                        blankTablearrayList.add("tblDistributorDayReportColumnsDesc");
                    }


                    //table 30-------------------------------
                    //deleted above
                    List<TblDistributorDayReportDisplayMessage> tblDistributorDayReportDisplayMessage = stockData.getTblDistributorDayReportDisplayMessage();

                    if (tblDistributorDayReportDisplayMessage.size() > 0) {
                        /*for(TblDistributorDayReportDisplayMessage tblDistributorDayReportDisplayMessageData:tblDistributorDayReportDisplayMessage){
                            dbengine.insertStockOut(DistributorStockOutFlg.getFlgStockOutEntryDone());
                        }*/
                    } else {
                        blankTablearrayList.add("tblDistributorDayReportDisplayMessage");
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<DistributorStockData> call, Throwable t) {

                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getGetServerTime(Context context, final String imei) {

        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TblCurrentServerDateTimeData> call = apiService.Call_TblCurrentServerDateTimeData();
        call.enqueue(new Callback<TblCurrentServerDateTimeData>() {
            @Override
            public void onResponse(Call<TblCurrentServerDateTimeData> call, Response<TblCurrentServerDateTimeData> response) {
                if (response.code() == 200) {
                    TblCurrentServerDateTimeData CurrentServerDateTimeData = response.body();
                    System.out.println("DATAENSERTEDSP");
                    List<TblServerTime> tblServerTime = CurrentServerDateTimeData.getTblServerTime();

                    if (tblServerTime.size() > 0) {
                        for (TblServerTime tblServerTimeData : tblServerTime) {
//   savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);
                            CommonInfo.crntServerTimecrntAttndncTime = tblServerTimeData.getServerTime();
                        }
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<TblCurrentServerDateTimeData> call, Throwable t) {

                //    dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getStockUploadedStatus(final Context context, final String imei, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final MultipleInterfaceForDayEndStatus multipleInterfaceForDayEndStatus = (MultipleInterfaceForDayEndStatus) context;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setIMEINo(imei);
        statusInfo.setAppVersionNo(BuildConfig.VERSION_NAME);

        Call<StockUploadedStatus> call = apiService.Call_GetStockUploadStatus(statusInfo);
        call.enqueue(new Callback<StockUploadedStatus>() {
            @Override
            public void onResponse(Call<StockUploadedStatus> call, Response<StockUploadedStatus> response) {
                if (response.code() == 200) {
                    StockUploadedStatus stockUploadedStatus = response.body();
                    System.out.println("DATAENSERTEDSP");


                    //table 28-------------------------------
                    if (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {
                        dbengine.Delete_tblStockUploadedStatus();

                        List<TblStockUploadedStatus> tblStockUploadedStatus = stockUploadedStatus.getTblStockUploadedStatus();
                        int flgDayEnd = 0;
                        if (tblStockUploadedStatus.size() > 0) {
                            for (TblStockUploadedStatus StockUploadedStatus : tblStockUploadedStatus) {
                                flgDayEnd = StockUploadedStatus.getFlgDayEnd();
                                dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(), StockUploadedStatus.getVanLoadUnLoadCycID(), StockUploadedStatus.getCycleTime(), StockUploadedStatus.getStatusID(), StockUploadedStatus.getFlgDayEnd());

                            }

                        } else {
                            // blankTablearrayList.add("tblStockUploadedStatus");
                        }

                        SharedPreferences sharedPref = context.getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
                        SharedPreferences.Editor editorFinalSubmit = sharedPref.edit();
                        editorFinalSubmit.putInt("FinalSubmit", flgDayEnd);
                        editorFinalSubmit.commit();
                        multipleInterfaceForDayEndStatus.DayEndServerStatussuccess(flgCldFrm);
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<StockUploadedStatus> call, Throwable t) {
                multipleInterfaceForDayEndStatus.DayEndServerStatusfailure(flgCldFrm);

            }
        });


    }
    public static void getAllExeutionData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm){
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD=KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Refreshing data..")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit)context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context,TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService= ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        CommonInfo.flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSlsForDSR(Integer.parseInt(CoverageNodeId),Integer.parseInt(CoverageNodeType));
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew=dbengine.getDistinctInvoiceNumbersNew();
        Data data=new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setFlgAllRouteData(1);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<ExecutionModelsData> call= apiService.Call_AllExecutionData(data);
        call.enqueue(new Callback<ExecutionModelsData>() {
            @Override
            public void onResponse(Call<ExecutionModelsData> call, Response<ExecutionModelsData> response) {
                if(response.code()==200){
                    ExecutionModelsData allMasterTablesModel=  response.body();

                    HashMap<String,String> hmapInvoiceOrderIDandStatus=new HashMap<String, String>();
                    hmapInvoiceOrderIDandStatus=dbengine.fetchHmapInvoiceOrderIDandStatus();
                    if(allMasterTablesModel.getTblPendingInvoices()!=null) {
                        List<TblPendingInvoices> tblPendingInvoices = allMasterTablesModel.getTblPendingInvoices();

                        if (tblPendingInvoices.size() > 0) {

                            dbengine.inserttblPendingInvoices(tblPendingInvoices, hmapInvoiceOrderIDandStatus);

                        } else {
                            blankTablearrayList.add("tblPendingInvoices");
                        }
                    }



                    if(allMasterTablesModel.getTblInvoiceExecutionProductList()!=null) {

                        List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = allMasterTablesModel.getTblInvoiceExecutionProductList();
                        dbengine.fnDeletetblInvoiceExecutionProductList();
                        if (tblInvoiceExecutionProductList.size() > 0) {

                            dbengine.inserttblInvoiceExecutionProductList(tblInvoiceExecutionProductList);

                        } else {
                            blankTablearrayList.add("tblInvoiceExecutionProductList");
                        }
                    }


                    if(allMasterTablesModel.getTblProductWiseInvoice()!=null) {
                        List<TblProductWiseInvoice> tblProductWiseInvoice = allMasterTablesModel.getTblProductWiseInvoice();
                        if (tblProductWiseInvoice.size() > 0) {

                            dbengine.inserttblProductWiseInvoice(tblProductWiseInvoice, hmapInvoiceOrderIDandStatus);

                        } else {
                            blankTablearrayList.add("tblProductWiseInvoice");
                        }
                    }




                   /* if(allMasterTablesModel.getTblReasonOrderCncl()!=null) {
                        dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();

                        hmapInvoiceOrderIDandStatus = null;

                        dbengine.delTblReasonOrderCncl();
                        List<TblReasonOrderCncl> tblReasonOrderCncl = allMasterTablesModel.getTblReasonOrderCncl();

                        if (tblReasonOrderCncl.size() > 0) {
                            for (TblReasonOrderCncl tblReasonOrderCnclModel : tblReasonOrderCncl) {
                                dbengine.insertReasonCanclOrder(tblReasonOrderCnclModel.getReasonCodeID(), tblReasonOrderCnclModel.getReasonDescr());
                            }
                        } else {
                            blankTablearrayList.add("tblReasonOrderCncl");
                        }
                    }*/


                /*    dbengine.deleteIncentivesTbles();


                    List<TblIncentiveMainMaster> tblIncentiveMaster=  allMasterTablesModel.getTblIncentiveMainMaster();

                    if(tblIncentiveMaster.size()>0){
                        for(TblIncentiveMainMaster tblIncentiveMasterModel:tblIncentiveMaster){
                            dbengine.savetblIncentiveMaster(tblIncentiveMasterModel.getIncId(),tblIncentiveMasterModel.getOutputType(),tblIncentiveMasterModel.getIncentiveName(),""+tblIncentiveMasterModel.getFlgAcheived(),""+tblIncentiveMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveMaster");
                    }

                    List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster=  allMasterTablesModel.getTblIncentiveSecondaryMaster();

                    if(tblIncentiveSecondaryMaster.size()>0){
                        for(TblIncentiveSecondaryMaster tblIncentiveSecondaryMasterModel:tblIncentiveSecondaryMaster){
                            dbengine.savetblIncentiveSeondaryMaster(tblIncentiveSecondaryMasterModel.getIncSlabId(),tblIncentiveSecondaryMasterModel.getIncId(), tblIncentiveSecondaryMasterModel.getOutputType(), tblIncentiveSecondaryMasterModel.getIncSlabName(),""+tblIncentiveSecondaryMasterModel.getFlgAcheived(),""+tblIncentiveSecondaryMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveSecondaryMaster");
                    }
                    Object tblIncentiveDetailsData=allMasterTablesModel.getTblIncentiveDetailsData();
                    ArrayList<HashMap<String,String>> tblIncentiveDetailsDataTable=(ArrayList<HashMap<String,String>>) tblIncentiveDetailsData;

                    if( tblIncentiveDetailsDataTable.size()>0) {
                        for(int i=0;i<10;i++)
                        {
                           String sdqeqwe= tblIncentiveDetailsDataTable.get(0).toString();
                        }
                    }


*/                  dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                    if(progressHUD!=null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                }
                else{
                    if(progressHUD!=null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<ExecutionModelsData> call, Throwable t) {
                System.out.println();
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if(progressHUD!=null && progressHUD.isShowing())
                    progressHUD.dismiss();

                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }

}
