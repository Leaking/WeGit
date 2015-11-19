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
    private Github github;
    private Handler handler;

    private GithubService service;



    public TokenInteractorImpl(Context context, final OnTokenCreatedListener listener){
        this.context = context;
        this.listener = listener;
        this.service = RetrofitUtil.getRetrofitWithoutTokenInstance(context).create(GithubService.class);
        github = new GithubImpl(context);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case TOKEN_CREATED:
                        String token = (String)msg.obj;
                        listener.onTokenCreated(token);
                        break;
                    case ERROR:
                        String errorMsg = (String)msg.obj;
                        listener.onError(errorMsg);
                        break;
                }

            }
        };
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

        Token token = new Token();
        token.setNote(TOKEN_NOTE);
        token.setScopes(Arrays.asList(SCOPES));




        Call<Token> call = service.createToken(token,"Basic " + Base64.encode(username + ':' + password));
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if(response.isSuccess()){
                    L.i(TAG, "Token created sucessfully-(new)");
                    listener.onTokenCreated(response.body().getToken());
                }else if(response.code() == 401){
                    L.i(TAG,"Token created fail: username or password is incorrect");
                    listener.onError(context.getResources().getString(R.string.auth_error));
                }else if(response.code() == 403){
                    L.i(TAG,"Token created fail: auth over-try");
                    listener.onError(context.getResources().getString(R.string.over_auth_error));
                }else if(response.code() == 422){
                    L.i(TAG,"Token created fail: try to delete existing token");
                    findCertainTokenID(username,password);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);
                listener.onError(context.getResources().getString(R.string.network_error));
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String token = github.createToken(username,password);
//                    Message msg = new Message();
//                    msg.what = TOKEN_CREATED;
//                    msg.obj = token;
//                    handler.sendMessage(msg);
//                } catch (GithubError githubError) {
//                    githubError.printStackTrace();
//                    Message msg = new Message();
//                    msg.what = ERROR;
//                    msg.obj = context.getResources().getString(R.string.network_error);
//                    handler.sendMessage(msg);
//                } catch (AuthError authError) {
//                    authError.printStackTrace();
//                    Message msg = new Message();
//                    msg.what = ERROR;
//                    msg.obj = context.getResources().getString(R.string.auth_error);
//                    handler.sendMessage(msg);
//                } catch (OverAuthError overAuthError) {
//                    overAuthError.printStackTrace();
//                    Message msg = new Message();
//                    msg.what = ERROR;
//                    msg.obj = context.getResources().getString(R.string.over_auth_error);
//                    handler.sendMessage(msg);
//                }
//            }
//        }).start();


    }

    public String findCertainTokenID(final String username, final String password){
        L.i(TAG,"Find certain token in existing tokens");
        Call<List<Token>> call = service.listToken("Basic " + Base64.encode(username + ':' + password));
        call.enqueue(new Callback<List<Token>>() {
            @Override
            public void onResponse(Response<List<Token>> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                for(Token token : response.body()){
                    L.i(TAG,"Find certain token in existing tokens : " +token.getNote() );
                    if(TOKEN_NOTE.equals(token.getNote())){
                        removeToken(username,password,String.valueOf(token.getId()));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);
                listener.onError(context.getResources().getString(R.string.network_error));
            }
        });

        return "";
    }

    public void removeToken(final String username, final String password,String id){
        L.i(TAG,"Try to delete token : id = " + id);
        Call<Empty> call = service.removeToken("Basic " + Base64.encode(username + ':' + password), id);
        call.enqueue(new Callback<Empty>() {
            @Override
            public void onResponse(Response<Empty> response, Retrofit retrofit) {
                RetrofitUtil.printResponse(response);
                if(response.code() == 204){
                    L.i(TAG,"Deteled token successfully");
                    L.i(TAG,"Try to get an entirely new token");

                    createToken(username, password);
                }else{
                    listener.onError(context.getResources().getString(R.string.network_error));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RetrofitUtil.printThrowable(t);
                listener.onError(context.getResources().getString(R.string.network_error));

            }
        });

    }


}
