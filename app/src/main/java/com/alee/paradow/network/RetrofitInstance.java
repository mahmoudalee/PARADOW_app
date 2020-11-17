package com.alee.paradow.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alee.paradow.utils.Links.BASE_URL;

public class RetrofitInstance {

        private static Retrofit retrofit;
        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

}
