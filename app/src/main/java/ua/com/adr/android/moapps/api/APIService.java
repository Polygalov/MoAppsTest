package ua.com.adr.android.moapps.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.com.adr.android.moapps.model.Post;
import ua.com.adr.android.moapps.model.UserAccount;


public interface APIService {

    @POST("/api/account/login")
    Call<Post> savePost(@Body UserAccount userAccount);
}

