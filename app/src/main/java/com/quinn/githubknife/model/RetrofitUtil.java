package com.quinn.githubknife.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quinn.githubknife.account.GitHubAccount;
import com.quinn.githubknife.utils.Constants;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.payload.EventFormatter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Quinn on 10/12/15.
 */
public class RetrofitUtil {

    private volatile static Retrofit instance;
    public static String token;
    private final static String TAG = RetrofitUtil.class.getSimpleName();


    // Returns singleton class instance
    public static Retrofit getInstance(final Context context) {
        if (instance == null) {
            synchronized (Retrofit.class) {
                if (instance == null) {
                    OkHttpClient client = new OkHttpClient();
                    client.networkInterceptors().add(new Interceptor() {
                        @Override
                        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            GitHubAccount gitHubAccount = GitHubAccount.getInstance(context);

                            token = gitHubAccount.getAuthToken();
                            request = request.newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("Authorization", "Token " + token)
                                    .addHeader("User-Agent", "Leaking/1.0")
                                    //.addHeader("Accept", "application/vnd.github.beta+json")
                                    .addHeader("Accept", "application/vnd.github.VERSION.raw")
                                            .build();
                            //此处build之后要返回request覆盖
                            L.i(TAG, "Interceptor header = " + request.headers());
                            L.i(TAG, "Interceptor method = " + request.method());
                            L.i(TAG, "Interceptor urlString = " + request.urlString());
                            return chain.proceed(request);
                        }
                    });


                    Gson gson = new Gson();
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Event.class, new EventFormatter());
                    gson = builder.create();
                    instance = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();

                }
            }
        }
        return instance;
    }



    public static void printResponse(Response response){
        L.i(TAG,"response headers " + response.headers());
        L.i(TAG,"response code " + response.code());
        L.i(TAG,"response isSuccess " + response.isSuccess());
        L.i(TAG,"response message " + response.message());
        L.i(TAG,"response body " + response.body());
    }

    public static void printThrowable(Throwable throwable){
        L.i(TAG,"response Throwable " + throwable.toString());

    }

}
