package com.likeview.csm.api;

//import com.aswdc.archdaily.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.likeview.csm.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

//    private static final String AUTH = "Basic " + Base64.encodeToString(("belalkhan:123456").getBytes(), Base64.NO_WRAP);

    private static final String BASE_URL = "http://cms.likeview.in/api/";
    private static RetrofitClient mInstance;
    private static Retrofit retrofit = null;

    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }


    public static Retrofit getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null) {
            OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
            okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
            okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okhttpClientBuilder.addInterceptor(logging);
            }
            OkHttpClient client = okhttpClientBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client( client )
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}












//    private RetrofitClient() {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(
//                        new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                Request original = chain.request();
//
//                                Request.Builder requestBuilder = original.newBuilder()
//                                        .addHeader("Authorization", AUTH)
//                                        .method(original.method(), original.body());
//
//                                Request request = requestBuilder.build();
//                                return chain.proceed(request);
//                            }
//                        }
//                ).build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    public static synchronized RetrofitClient getInstance() {
//        if (mInstance == null) {
//            mInstance = new RetrofitClient();
//        }
//        return mInstance;
//    }
//
//    public Api getApi() {
//        return retrofit.create(Api.class);
//    }
//}
