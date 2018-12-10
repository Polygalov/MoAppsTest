package ua.com.adr.android.moapps.api;


public class ApiUtils {

    public static final String BASE_URL = "https://html5.mo-apps.com/";

    // Get API Service for login
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    // Get API Service for apps data
    public static AppListApi getAppListApi() {
        return RetrofitClient.getClient(BASE_URL).create(AppListApi.class);
    }

}
