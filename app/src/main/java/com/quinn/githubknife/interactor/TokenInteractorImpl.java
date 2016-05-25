package com.quinn.githubknife.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.quinn.githubknife.R;
import com.quinn.githubknife.listener.OnTokenCreatedListener;
import com.quinn.githubknife.model.GithubService;
import com.quinn.githubknife.model.RetrofitUtil;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Empty;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Token;
import com.quinn.httpknife.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Quinn on 8/1/15.
 */
public class TokenInteractorImpl implements TokenInteractor {

    public static final String TAG = TokenInteractorImpl.class.getSimpleName();
    private final static int TOKEN_CREATED = 1;
    private final static int ERROR = 2;
    public final static String TOKEN_NOTE = "WeGit APP Token";
    public final static String[] SCOPES = {"public_repo","repo", "user", "gist"};


    private OnTokenCreatedListener listener;
    private Context context;
    private GithubService service;

    public TokenInteractorImpl(Context context, final OnTokenCreatedListener listener){
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getRetrofitWithoutTokenInstance(context).create(GithubService.class);
    }

    public void createToken(final String username,final String password){

        JSONObject json = new JSONObject();
        try {
            json.put("note", TOKEN_NOTE);
            JSONArray jsonArray = new JSONArray(Arrays.asList(SCOPES));
            json.put("scopes",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Token token = new Token();
        token.setNote(TOKEN_NOTE);
        token.setScopes(Arrays.asList(SCOPES));

        service.createToken(token,"Basic " + Base64.encode(username + ':' + password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Token>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitUtil.printThrowable(e);
                        listener.onError(context.getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(Response<Token> tokenResponse) {
                        RetrofitUtil.printResponse(tokenResponse);
                        if(tokenResponse.isSuccess()){
                            L.i(TAG, "Token created sucessfully-(new)");
                            listener.onTokenCreated(tokenResponse.body().getToken());
                        }else if(tokenResponse.code() == 401){
                            L.i(TAG,"Token created fail: username or password is incorrect");
                            listener.onError(context.getResources().getString(R.string.auth_error));
                        }else if(tokenResponse.code() == 403){
                            L.i(TAG,"Token created fail: auth over-try");
                            listener.onError(context.getResources().getString(R.string.over_auth_error));
                        }else if(tokenResponse.code() == 422){
                            L.i(TAG,"Token created fail: try to delete existing token");
                            findCertainTokenID(username,password);
                        }
                    }
                });

    }

    public void findCertainTokenID(final String username, final String password){
        L.i(TAG,"Find certain token in existing tokens");
        service.listToken("Basic " + Base64.encode(username + ':' + password))
                .flatMap(new Func1<Response<List<Token>>, Observable<Response<Empty>>>() {
                    @Override
                    public Observable<Response<Empty>> call(Response<List<Token>> listResponse) {
                        RetrofitUtil.printResponse(listResponse);
                        for(Token token : listResponse.body()){
                            L.i(TAG,"Find certain token in existing tokens : " +token.getNote() );
                            if(TOKEN_NOTE.equals(token.getNote())){
                                return service.removeToken("Basic " + Base64.encode(username + ':' + password), String.valueOf(token.getId()));
                            }
                        }
                        return Observable.empty();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Empty>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitUtil.printThrowable(e);
                        listener.onError(context.getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(Response<Empty> listResponse) {
                        RetrofitUtil.printResponse(listResponse);
                        if(listResponse.code() == 204){
                            L.i(TAG,"Deteled token successfully");
                            L.i(TAG,"Try to get an entirely new token");
                            createToken(username, password);
                        }else{
                            listener.onError(context.getResources().getString(R.string.network_error));
                        }
                    }
                });
    }


}
