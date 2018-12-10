package ua.com.adr.android.moapps.model;


public class AppListRequest {

    final int skip;
    final int osType;
    final int take;
    final String userToken;


    public AppListRequest(int skip, int osType, int take, String userToken) {
        this.skip = skip;
        this.osType = osType;
        this.take = take;
        this.userToken = userToken;
    }

}