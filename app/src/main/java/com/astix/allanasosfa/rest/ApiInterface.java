package com.astix.allanasosfa.rest;



import com.astix.allanasosfa.model.AllAddedOutletSummaryReportModel;
import com.astix.allanasosfa.model.AllMasterTablesModel;
import com.astix.allanasosfa.model.AllSummaryReportDay;
import com.astix.allanasosfa.model.AllSummarySKUWiseDay;
import com.astix.allanasosfa.model.AllSummaryStoreSKUWiseDay;
import com.astix.allanasosfa.model.AllSummaryStoreWiseDay;
import com.astix.allanasosfa.model.AllTargetVsAchieved;
import com.astix.allanasosfa.model.ConfirmVanStock;
import com.astix.allanasosfa.model.Data;
import com.astix.allanasosfa.model.DistributorStockData;
import com.astix.allanasosfa.model.DistributorTodaysStock;
import com.astix.allanasosfa.model.ExecutionModelsData;
import com.astix.allanasosfa.model.IMEIVersionDetails;
import com.astix.allanasosfa.model.IMEIVersionParentModel;
import com.astix.allanasosfa.model.PDAConfirmVanStockModel;
import com.astix.allanasosfa.model.PersonInfo;
import com.astix.allanasosfa.model.RegistrationValidation;
import com.astix.allanasosfa.model.ReportsAddedOutletSummary;
import com.astix.allanasosfa.model.ReportsInfo;
import com.astix.allanasosfa.model.StatusInfo;
import com.astix.allanasosfa.model.StockData;
import com.astix.allanasosfa.model.StockUploadedStatus;
import com.astix.allanasosfa.model.TblCurrentServerDateTimeData;
import com.astix.allanasosfa.model.TblPDAVanDayEndDetResult;
import com.astix.allanasosfa.model.TblSaveVanStockRequestResult;
import com.astix.allanasosfa.model.VanDayEnd;
import com.astix.allanasosfa.model.VanStockRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {



    @POST("Home/PersonValidation")
    Call<IMEIVersionParentModel> Call_IMEIVersionDetailStatus(@Body IMEIVersionDetails IMEIVersionDetails);

    @POST("Home/AllData")
    Call<AllMasterTablesModel> Call_AllMasterData(@Body Data data);

    @POST("Home/GetAllDaySummary")
    Call<AllSummaryReportDay> Call_AllSummaryReportDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseDaySummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseMTDSummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseDaySummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseMTDSummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreSKUWiseDaySummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseDay(@Body ReportsInfo reportsInfo);


    @POST("Home/GetStoreSKUWiseMTDSummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseMTDDay(@Body ReportsInfo reportsInfo);


    @POST("Home/GetTargetVsAchieved")
    Call<AllTargetVsAchieved> Call_AllTargetVsAchieved(@Body Data data);


    @POST("Home/GetPDAGetAddedOutletSummaryReport")
    Call<AllAddedOutletSummaryReportModel> Call_AllPDAGetAddedOutletSummaryReport(@Body ReportsAddedOutletSummary reportsAddedOutletSummary);

    @POST("Home/GetPersonDetail")
    Call<RegistrationValidation> Call_GetRegistrationDetails(@Body PersonInfo personInfo);


    @POST("Home/SaveVanStockRequest")
    Call<TblSaveVanStockRequestResult> Call_SaveVanStockRequest(@Body VanStockRequest VanStockRequest);

    @POST("Home/PDAConfirmVanStock")
    Call<PDAConfirmVanStockModel> Call_PDAConfirmVanStock(@Body ConfirmVanStock ConfirmVanStock);

    @POST("Home/PDAVanDayEnd")
    Call<TblPDAVanDayEndDetResult> Call_PDAVanDayEnd(@Body VanDayEnd VanDayEnd);

    @POST("Home/StockMaster")
    Call<StockData> Call_StockData(@Body Data data);

    @POST("Home/GetDistributorTodaysStock")
    Call<DistributorStockData> Call_DistributorTodayStockData(@Body DistributorTodaysStock data);

    @POST("Home/GetServerTime")
    Call<TblCurrentServerDateTimeData> Call_TblCurrentServerDateTimeData();

    @POST("Home/GetStockUploadStatus")
    Call<StockUploadedStatus> Call_GetStockUploadStatus(@Body StatusInfo statusInfo);

    @POST("Home/getinvoicelist")
    Call<ExecutionModelsData> Call_AllExecutionData(@Body Data data);
}
