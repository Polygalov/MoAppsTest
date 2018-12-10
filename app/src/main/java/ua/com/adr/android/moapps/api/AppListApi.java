package ua.com.adr.android.moapps.api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.com.adr.android.moapps.model.AppListRequest;
import ua.com.adr.android.moapps.model.JSONResponse;

public interface AppListApi {

    @POST("/api/application")
    Call<JSONResponse> getApps(@Body AppListRequest appListRequest);

}
