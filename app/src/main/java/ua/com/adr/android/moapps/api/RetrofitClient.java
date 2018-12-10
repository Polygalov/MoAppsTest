package ua.com.adr.android.moapps.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {

        private static Retrofit retrofit = null;

        // Get Retrofit Instance
        public static Retrofit getClient(String baseUrl) {

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }