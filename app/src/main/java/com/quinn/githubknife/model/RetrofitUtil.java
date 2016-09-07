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
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Quinn on 10/12/15.
 */
public class RetrofitUtil {

    private final static String TAG = "RetrofitUtil";

    private volatile static Retrofit jsonInstance;
    private volatile static Retrofit stringInstance;
    private volatile static Retrofit jsonInstance_withoutToken;

    public static String token;

    public static Retrofit getStringRetrofitInstance(final Context context){
        if (stringInstance == null) {
            synchronized (Retrofit.class) {
                if (stringInstance == null) {
                    OkHttpClient client = new OkHttpClient();
                    client.networkInterceptors().add(new Interceptor() {
                        @Override
                        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

                            L.i(TAG,"------getStringRetrofitInstance intercept start-------");
                            Request request = chain.request();
                            GitHubAccount gitHubAccount = GitHubAccount.getInstance(context);
                            token = gitHubAccount.getAuthToken();
                            //此处build之后要返回request覆盖
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Token " + token)
                                    .addHeader("User-Agent", "Leaking/1.0")
                                            //.addHeader("Accept", "application/vnd.github.beta+json")
                                    .addHeader("Accept", "application/vnd.github.v3.raw")
                                    .build();
                            // L.i(TAG, "Interceptor header = " + request.headers());
                            L.i(TAG, "Interceptor token = " + token);
                            L.i(TAG, "Interceptor request = " + request.toString());
                            L.i(TAG,"------getStringRetrofitInstance intercept end-------");
                            return chain.proceed(request);
                        }
                    });

                    stringInstance = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(new ToStringConverter())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return stringInstance;
    }



    // Returns singleton class instance
    public static Retrofit getJsonRetrofitInstance(final Context context) {
        if (jsonInstance == null) {
            synchronized (Retrofit.class) {
                if (jsonInstance == null) {
                    OkHttpClient client = new OkHttpClient();
                    client.networkInterceptors().add(new Interceptor() {
                        @Override
                        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                            L.i(TAG,"------getJsonRetrofitInstance intercept start-------");
                            Request request = chain.request();
                            GitHubAccount gitHubAccount = GitHubAccount.getInstance(context);
                            token = gitHubAccount.getAuthToken();
                            request = request.newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("Authorization", "Token " + token)
                                    .addHeader("User-Agent", "Leaking/1.0")
                                    //.addHeader("Accept", "application/vnd.github.beta+json")
                                    .addHeader("Accept", "application/vnd.github.v3.raw")
                                            .build();
                            L.i(TAG, "Interceptor token = " + token);
                            L.i(TAG, "Interceptor request = " + request.toString());
                            L.i(TAG,"------getJsonRetrofitInstance intercept end-------");
                            return chain.proceed(request);
                        }
                    });

                    Gson gson = null;
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Event.class, new EventFormatter());
                    gson = builder.create();
                    jsonInstance = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();

                }
            }
        }
        return jsonInstance;
    }

    // Returns singleton class instance
    public static Retrofit getRetrofitWithoutTokenInstance(final Context context) {
        if (jsonInstance_withoutToken == null) {
            synchronized (Retrofit.class) {
                if (jsonInstance_withoutToken == null) {
                    OkHttpClient client = new OkHttpClient();
                    client.networkInterceptors().add(new Interceptor() {
                        @Override
                        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                            L.i(TAG,"------getRetrofitWithoutTokenInstance intercept start-------");

                            Request request = chain.request();
                            request = request.newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("User-Agent", "Leaking/1.0")
                                     //.addHeader("Accept", "application/vnd.github.beta+json")
                                    .addHeader("Accept", "application/vnd.github.v3.raw")
                                    .build();
                            //此处build之后要返回request覆盖
                            L.i(TAG, "Interceptor header = " + request.headers());
                            L.i(TAG, "Interceptor request = " + request.toString());
                            L.i(TAG,"------getRetrofitWithoutTokenInstance intercept end-------");
                            return chain.proceed(request);
                        }
                    });


                    Gson gson = null;
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Event.class, new EventFormatter());
                    gson = builder.create();
                    jsonInstance_withoutToken = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();

                }
            }
        }
        return jsonInstance_withoutToken;
    }

    public static void printResponse(Response response){
        L.i(TAG,"response = " + response.raw().toString());
    }

    public static void printThrowable(Throwable throwable){
        L.i(TAG,"response Throwable " + throwable.toString());
    }

}
